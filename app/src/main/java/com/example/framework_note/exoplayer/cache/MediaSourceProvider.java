package com.example.framework_note.exoplayer.cache;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.example.framework_note.TestBandwidthMeter;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSinkFactory;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;
import java.io.File;

/**
 * <pre>
 *     author : scam
 *     e-mail : mingming.liu@quvideo.com
 *     time   : 2022/04/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public final class MediaSourceProvider {

  /**
   * 磁盘缓存最大Size:256MB
   */
  private static final int MAX_CACHE_DISK_SIZE = 256 * 1024 * 1024;

  /**
   * 单个缓存文件大小限制：3MB
   */
  private static final int MAX_CACHE_FILE_SIZE = 3 * 1024 * 1024;
  private static final boolean allowCache = true;

  public static final MediaSource createMediaSource(Context context, String videoUrl){
    String userAgent = Util.getUserAgent(context, "ExoPlayer");
    if (!allowCache) {
      return new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(context, userAgent)).createMediaSource(
          Uri.parse(videoUrl));
    }
    TestBandwidthMeter listener = new TestBandwidthMeter();
    DefaultDataSourceFactory upstreamFactory = new DefaultDataSourceFactory(context,
        listener, new DefaultHttpDataSourceFactory(userAgent, listener));
    // 获取缓存文件夹
    File file = CachesUtil.getMediaCacheFile(CachesUtil.VIDEO, context);
    Cache cache = new SimpleCache(file, new LeastRecentlyUsedCacheEvictor(MAX_CACHE_DISK_SIZE));
    // CacheDataSinkFactory 第二个参数为单个缓存文件大小，如果需要缓存的文件大小超过此限制，则会分片缓存，不影响播放
    DataSink.Factory cacheWriteDataSinkFactory = new CacheDataSinkFactoryImpl(cache, MAX_CACHE_FILE_SIZE);
    FileDataSourceFactory fileDataSourceFactory = new FileDataSourceFactory();
    CacheDataSourceFactory dataSourceFactory = new CacheDataSourceFactory(cache,
        upstreamFactory, fileDataSourceFactory, cacheWriteDataSinkFactory, CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR,
        new CacheDataSource.EventListener() {
          @Override public void onCachedBytesRead(long cacheSizeBytes, long cachedBytesRead,
              boolean isReadCache, String url) {
            Log.d("LMM", "onCachedBytesRead cacheSizeBytes=> " + cacheSizeBytes + " cachedBytesRead=> " + cachedBytesRead + " isReadCache： " + isReadCache + " url:" + url);
          }

          @Override public void onCacheIgnored(int reason) {
            Log.d("LMM", "onCacheIgnored");
          }
        });
    return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(videoUrl));
  }


}
