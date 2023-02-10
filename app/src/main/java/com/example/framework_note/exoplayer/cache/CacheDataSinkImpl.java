package com.example.framework_note.exoplayer.cache;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ReusableBufferedOutputStream;
import com.google.android.exoplayer2.util.Util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <pre>
 *     author : scam
 *     e-mail : mingming.liu@quvideo.com
 *     time   : 2022/04/29
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class CacheDataSinkImpl implements DataSink {

  /** Default buffer size in bytes. */
  public static final int DEFAULT_BUFFER_SIZE = 20480;

  private final Cache cache;
  private final long maxCacheFileSize;
  private final int bufferSize;

  private boolean syncFileDescriptor;
  private DataSpec dataSpec;
  private File file;
  private OutputStream outputStream;
  private FileOutputStream underlyingFileOutputStream;
  private long outputStreamBytesWritten;
  private long dataSpecBytesWritten;
  private ReusableBufferedOutputStream bufferedOutputStream;

  /**
   * Thrown when IOException is encountered when writing data into sink.
   */
  public static class CacheDataSinkException extends Cache.CacheException {

    public CacheDataSinkException(IOException cause) {
      super(cause);
    }

  }

  /**
   * Constructs a CacheDataSink using the {@link #DEFAULT_BUFFER_SIZE}.
   *
   * @param cache The cache into which data should be written.
   * @param maxCacheFileSize The maximum size of a cache file, in bytes. If the sink is opened for a
   *     {@link DataSpec} whose size exceeds this value, then the data will be fragmented into
   *     multiple cache files.
   */
  public CacheDataSinkImpl(Cache cache, long maxCacheFileSize) {
    this(cache, maxCacheFileSize, DEFAULT_BUFFER_SIZE);
  }

  /**
   * @param cache The cache into which data should be written.
   * @param maxCacheFileSize The maximum size of a cache file, in bytes. If the sink is opened for a
   *     {@link DataSpec} whose size exceeds this value, then the data will be fragmented into
   *     multiple cache files.
   * @param bufferSize The buffer size in bytes for writing to a cache file. A zero or negative
   *     value disables buffering.
   */
  public CacheDataSinkImpl(Cache cache, long maxCacheFileSize, int bufferSize) {
    this.cache = Assertions.checkNotNull(cache);
    this.maxCacheFileSize = maxCacheFileSize;
    this.bufferSize = bufferSize;
    syncFileDescriptor = true;
  }

  /**
   * Sets whether file descriptors are synced when closing output streams.
   *
   * <p>This method is experimental, and will be renamed or removed in a future release. It should
   * only be called before the renderer is used.
   *
   * @param syncFileDescriptor Whether file descriptors are synced when closing output streams.
   */
  public void experimental_setSyncFileDescriptor(boolean syncFileDescriptor) {
    this.syncFileDescriptor = syncFileDescriptor;
  }

  @Override
  public void open(DataSpec dataSpec) throws CacheDataSink.CacheDataSinkException {
    if (dataSpec.length == C.LENGTH_UNSET
        && !dataSpec.isFlagSet(DataSpec.FLAG_ALLOW_CACHING_UNKNOWN_LENGTH)) {
      this.dataSpec = null;
      return;
    }
    this.dataSpec = dataSpec;
    dataSpecBytesWritten = 0;
    try {
      openNextOutputStream();
    } catch (IOException e) {
      throw new CacheDataSink.CacheDataSinkException(e);
    }
  }

  @Override
  public void write(byte[] buffer, int offset, int length) throws
      CacheDataSink.CacheDataSinkException {
    if (dataSpec == null) {
      return;
    }
    try {
      int bytesWritten = 0;
      while (bytesWritten < length) {
        if (outputStreamBytesWritten == maxCacheFileSize) {
          closeCurrentOutputStream();
          openNextOutputStream();
        }
        int bytesToWrite = (int) Math.min(length - bytesWritten,
            maxCacheFileSize - outputStreamBytesWritten);
        outputStream.write(buffer, offset + bytesWritten, bytesToWrite);
        bytesWritten += bytesToWrite;
        outputStreamBytesWritten += bytesToWrite;
        dataSpecBytesWritten += bytesToWrite;
      }
    } catch (IOException e) {
      throw new CacheDataSink.CacheDataSinkException(e);
    }
  }

  @Override
  public void close() throws CacheDataSink.CacheDataSinkException {
    if (dataSpec == null) {
      return;
    }
    try {
      closeCurrentOutputStream();
    } catch (IOException e) {
      throw new CacheDataSink.CacheDataSinkException(e);
    }
  }

  private void openNextOutputStream() throws IOException {
    long maxLength = dataSpec.length == C.LENGTH_UNSET ? maxCacheFileSize
        : Math.min(dataSpec.length - dataSpecBytesWritten, maxCacheFileSize);
    file = cache.startFile(dataSpec.key, dataSpec.absoluteStreamPosition + dataSpecBytesWritten,
        maxLength);
    underlyingFileOutputStream = new FileOutputStream(file);
    if (bufferSize > 0) {
      if (bufferedOutputStream == null) {
        bufferedOutputStream = new ReusableBufferedOutputStream(underlyingFileOutputStream,
            bufferSize);
      } else {
        bufferedOutputStream.reset(underlyingFileOutputStream);
      }
      outputStream = bufferedOutputStream;
    } else {
      outputStream = underlyingFileOutputStream;
    }
    outputStreamBytesWritten = 0;
  }

  @SuppressWarnings("ThrowFromFinallyBlock")
  private void closeCurrentOutputStream() throws IOException {
    if (outputStream == null) {
      return;
    }

    boolean success = false;
    try {
      outputStream.flush();
      if (syncFileDescriptor) {
        underlyingFileOutputStream.getFD().sync();
      }
      success = true;
    } finally {
      Util.closeQuietly(outputStream);
      outputStream = null;
      File fileToCommit = file;
      file = null;
      if (success) {
        cache.commitFile(fileToCommit);
      } else {
        fileToCommit.delete();
      }
    }
  }
}
