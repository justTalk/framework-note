package com.example.framework_note.exoplayer;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.video.MediaCodecVideoRenderer;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

/**
 * <pre>
 *     author : scam
 *     e-mail : mingming.liu@quvideo.com
 *     time   : 2022/03/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class TestMediaCodecVideoRenderer extends MediaCodecVideoRenderer {
  public TestMediaCodecVideoRenderer(Context context,
      MediaCodecSelector mediaCodecSelector) {
    super(context, mediaCodecSelector);
  }

  public TestMediaCodecVideoRenderer(Context context,
      MediaCodecSelector mediaCodecSelector, long allowedJoiningTimeMs) {
    super(context, mediaCodecSelector, allowedJoiningTimeMs);
  }

  public TestMediaCodecVideoRenderer(Context context,
      MediaCodecSelector mediaCodecSelector, long allowedJoiningTimeMs,
      @Nullable Handler eventHandler, @Nullable
      VideoRendererEventListener eventListener,
      int maxDroppedFramesToNotify) {
    super(context, mediaCodecSelector, allowedJoiningTimeMs, eventHandler, eventListener,
        maxDroppedFramesToNotify);
  }

  public TestMediaCodecVideoRenderer(Context context,
      MediaCodecSelector mediaCodecSelector, long allowedJoiningTimeMs, @Nullable
      DrmSessionManager<FrameworkMediaCrypto> drmSessionManager,
      boolean playClearSamplesWithoutKeys, @Nullable Handler eventHandler,
      @Nullable VideoRendererEventListener eventListener, int maxDroppedFramesToNotify) {
    super(context, mediaCodecSelector, allowedJoiningTimeMs, drmSessionManager,
        playClearSamplesWithoutKeys, eventHandler, eventListener, maxDroppedFramesToNotify);
  }

  @Override protected void onQueueInputBuffer(DecoderInputBuffer buffer) {
    super.onQueueInputBuffer(buffer);
    //Log.d("LMM", "onQueueInputBuffer:");
  }
}
