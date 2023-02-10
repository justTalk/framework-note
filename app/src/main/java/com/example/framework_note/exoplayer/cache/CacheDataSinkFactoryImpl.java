package com.example.framework_note.exoplayer.cache;

import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;

/**
 * <pre>
 *     author : scam
 *     e-mail : mingming.liu@quvideo.com
 *     time   : 2022/04/29
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class CacheDataSinkFactoryImpl implements DataSink.Factory{
  private final Cache cache;
  private final long maxCacheFileSize;
  private final int bufferSize;

  /**
   * @see CacheDataSink#CacheDataSink(Cache, long)
   */
  public CacheDataSinkFactoryImpl(Cache cache, long maxCacheFileSize) {
    this(cache, maxCacheFileSize, CacheDataSink.DEFAULT_BUFFER_SIZE);
  }

  /**
   * @see CacheDataSink#CacheDataSink(Cache, long, int)
   */
  public CacheDataSinkFactoryImpl(Cache cache, long maxCacheFileSize, int bufferSize) {
    this.cache = cache;
    this.maxCacheFileSize = maxCacheFileSize;
    this.bufferSize = bufferSize;
  }

  @Override
  public DataSink createDataSink() {
    return new CacheDataSinkImpl(cache, maxCacheFileSize, bufferSize);
  }
}
