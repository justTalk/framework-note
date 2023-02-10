package com.example.framework_note.exoplayer.cache;

import android.content.Context;
import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Clock;

/**
 * <pre>
 *     author : scam
 *     e-mail : mingming.liu@quvideo.com
 *     time   : 2022/04/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class SimpleExoPlayerImpl extends SimpleExoPlayer {
  public SimpleExoPlayerImpl(Context context,
      RenderersFactory renderersFactory,
      TrackSelector trackSelector,
      LoadControl loadControl,
      BandwidthMeter bandwidthMeter,
      @Nullable
          DrmSessionManager<FrameworkMediaCrypto> drmSessionManager,
      Looper looper) {
    super(context, renderersFactory, trackSelector, loadControl, bandwidthMeter, drmSessionManager,
        looper);
  }

  public SimpleExoPlayerImpl(Context context,
      RenderersFactory renderersFactory,
      TrackSelector trackSelector, LoadControl loadControl, @Nullable
      DrmSessionManager<FrameworkMediaCrypto> drmSessionManager,
      BandwidthMeter bandwidthMeter,
      AnalyticsCollector.Factory analyticsCollectorFactory,
      Looper looper) {
    super(context, renderersFactory, trackSelector, loadControl, drmSessionManager, bandwidthMeter,
        analyticsCollectorFactory, looper);
  }

  public SimpleExoPlayerImpl(Context context,
      RenderersFactory renderersFactory,
      TrackSelector trackSelector, LoadControl loadControl, @Nullable
      DrmSessionManager<FrameworkMediaCrypto> drmSessionManager,
      BandwidthMeter bandwidthMeter,
      AnalyticsCollector.Factory analyticsCollectorFactory,
      Clock clock, Looper looper) {
    super(context, renderersFactory, trackSelector, loadControl, drmSessionManager, bandwidthMeter,
        analyticsCollectorFactory, clock, looper);
  }
}
