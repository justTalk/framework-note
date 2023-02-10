package com.example.framework_note;

import android.annotation.SuppressLint;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.framework_note.exoplayer.TestRenderersFactory;
import com.example.framework_note.exoplayer.cache.CachesUtil;
import com.example.framework_note.exoplayer.cache.MediaSourceProvider;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSinkFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;
import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private PlayerView playerView;
    SimpleExoPlayer mExoPlayer;
    private int frameCount = 0;
    @SuppressLint("SdCardPath") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        //找到播放组件
        playerView = findViewById(R.id.playerView);
        String mUserAgent = Util.getUserAgent(this, "ExoPlayer");
        TestBandwidthMeter bandwidthMeter = new TestBandwidthMeter();
        // 1. Create a default TrackSelector
        TrackSelection.Factory videoTrackSelectionFactory =
            new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        // 2. Create a default LoadControl
        LoadControl loadControl =
            new DefaultLoadControl.Builder().setBufferDurationsMs(1000, 1800,
                500, 1000).createDefaultLoadControl();
        // 3. Create the player
        RenderersFactory renderersFactory = new TestRenderersFactory(this);
        mExoPlayer =
            ExoPlayerFactory.newSimpleInstance(this, renderersFactory, trackSelector, loadControl, null, new TestBandwidthMeter
                .Builder().build());
        mExoPlayer.addListener(eventListener);
        mExoPlayer.addAnalyticsListener(analyticsListener);
        mExoPlayer.setVideoFrameMetadataListener(new VideoFrameMetadataListener() {
            @Override
            public void onVideoFrameAboutToBeRendered(long presentationTimeUs, long releaseTimeNs,
                Format format) {
                frameCount++;
                //Log.d("LMM", "onVideoFrameAboutToBeRendered: " + presentationTimeUs + " releaseTimeNs: " + releaseTimeNs + " frameCount: " + frameCount);
                //Log.d("LMM", "onVideoFrameAboutToBeRendered: " + presentationTimeUs
                //    + " getBufferedPosition: " + mExoPlayer.getBufferedPosition()
                //    + " CurrentPosition: " + mExoPlayer.getCurrentPosition() + " allow: " + loadControl.getAllocator().getTotalBytesAllocated() + " : " + loadControl.getAllocator().getIndividualAllocationLength());
            }
        });
        playerView.setPlayer(mExoPlayer);
        //播放视频
        String videoUri = "https://cdn.sharechat.com/900a6957-c99c-44a4-a74a-0f3969565261-eac50500-299b-4b0b-8bdb-0e0e9d4ac801_w_v.mp4";
        videoUri = "https://rc.camdy.cn/vcm/15/20220318/081604/256565054496768/2565650544967687.mp4";
        MediaSource sampleSource = MediaSourceProvider.createMediaSource(this, videoUri);
        // Prepare the player.
        mExoPlayer.prepare(sampleSource);
        String originStr = "/data/user/0/com.videoeditorpro.android/files/demovvc/5071F39630BF0BE6101E017F7184AB59/media/0Más borracho de….m4a";
        boolean b =originStr.endsWith("0Más borracho de….m4a");
        String[] split = originStr.split(File.separator);
        Log.d("LMM", "b is " + b + " á: "  + "á".length() + " á: " + "á".length());
        Log.d("LMM", "b is " + originStr.endsWith(split[split.length - 1]));
        new TestKt().testSplit();
    }

    @Override public void finish() {
        super.finish();
        if (SimpleCache.isCacheFolderLocked(CachesUtil.getMediaCacheFile(CachesUtil.VIDEO, this))){
            SimpleCache.disableCacheFolderLocking();
        }
    }

    private Player.EventListener eventListener = new Player.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
            Log.d("LMM", "onTimelineChanged");
        }

        @Override public void onTracksChanged(TrackGroupArray trackGroups,
            TrackSelectionArray trackSelections) {
            Log.d("LMM", "onTracksChanged");
        }

        @Override public void onLoadingChanged(boolean isLoading) {
            Log.d("LMM", "onLoadingChanged " + isLoading);
        }

        @Override public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            Log.d("LMM", "onPlayerStateChanged " + playWhenReady + " state: " + playbackState);
        }

        @Override public void onRepeatModeChanged(int repeatMode) {
            Log.d("LMM", "onRepeatModeChanged");
        }

        @Override public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
            Log.d("LMM", "onShuffleModeEnabledChanged");
        }

        @Override public void onPlayerError(ExoPlaybackException error) {
            Log.d("LMM", "onPlayerError");
        }

        @Override public void onPositionDiscontinuity(int reason) {
            Log.d("LMM", "onPositionDiscontinuity");
        }

        @Override public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            Log.d("LMM", "onPlaybackParametersChanged");
        }

        @Override public void onSeekProcessed() {
            Log.d("LMM", "onSeekProcessed");
        }
    };

    private AnalyticsListener analyticsListener = new AnalyticsListener() {
        @Override public void onPlayerStateChanged(EventTime eventTime, boolean playWhenReady,
            int playbackState) {
            Log.d("LMM", "onPlayerStateChanged " + playbackState + " currentPlaybackPositionMs: " + eventTime.currentPlaybackPositionMs
                + " totalBufferedDurationMs: " + eventTime.totalBufferedDurationMs);
        }

        @Override public void onTimelineChanged(EventTime eventTime, int reason) {
            Log.d("LMM", "onTimelineChanged2 " + " currentPlaybackPositionMs: " + eventTime.currentPlaybackPositionMs
                + " totalBufferedDurationMs: " + eventTime.totalBufferedDurationMs);
        }

        @Override public void onPositionDiscontinuity(EventTime eventTime, int reason) {
        }

        @Override public void onSeekStarted(EventTime eventTime) {
        }

        @Override public void onSeekProcessed(EventTime eventTime) {
        }

        @Override public void onPlaybackParametersChanged(EventTime eventTime,
            PlaybackParameters playbackParameters) {
        }

        @Override public void onRepeatModeChanged(EventTime eventTime, int repeatMode) {
        }

        @Override
        public void onShuffleModeChanged(EventTime eventTime, boolean shuffleModeEnabled) {
        }

        @Override public void onLoadingChanged(EventTime eventTime, boolean isLoading) {
            Log.d("LMM", "onLoadingChanged " + isLoading + " " + " currentPlaybackPositionMs: " + eventTime.currentPlaybackPositionMs
                + " totalBufferedDurationMs: " + eventTime.totalBufferedDurationMs + " duration: " +  mExoPlayer.getTotalBufferedDuration());
        }

        @Override public void onPlayerError(EventTime eventTime, ExoPlaybackException error) {
        }

        @Override public void onTracksChanged(EventTime eventTime, TrackGroupArray trackGroups,
            TrackSelectionArray trackSelections) {
        }

        @Override public void onLoadStarted(EventTime eventTime,
            MediaSourceEventListener.LoadEventInfo loadEventInfo,
            MediaSourceEventListener.MediaLoadData mediaLoadData) {
            Log.d("LMM", "onLoadStarted: length=> " + loadEventInfo.dataSpec.length + " " + loadEventInfo.dataSpec.position);
            Log.d("LMM", "onLoadStarted " + mExoPlayer.getDuration()  + " currentPlaybackPositionMs: " + eventTime.currentPlaybackPositionMs
                + " totalBufferedDurationMs: " + eventTime.totalBufferedDurationMs + " duration: " + loadEventInfo.loadDurationMs + " start: " + mediaLoadData.mediaStartTimeMs + ": time: "+(mediaLoadData.mediaEndTimeMs-mediaLoadData.mediaStartTimeMs + " bytes: " +loadEventInfo.bytesLoaded));
        }

        @Override public void onLoadCompleted(EventTime eventTime,
            MediaSourceEventListener.LoadEventInfo loadEventInfo,
            MediaSourceEventListener.MediaLoadData mediaLoadData) {
            Log.d("LMM", "onLoadCompleted: " + loadEventInfo.dataSpec.key + " " + loadEventInfo.dataSpec.flags);
            Log.d("LMM", "onLoadCompleted " + mExoPlayer.getDuration()  + " currentPlaybackPositionMs: " + eventTime.currentPlaybackPositionMs
                + " totalBufferedDurationMs: " + eventTime.totalBufferedDurationMs + " duration: " + loadEventInfo.loadDurationMs + " start: " + mediaLoadData.mediaStartTimeMs + ": time: "+(mediaLoadData.mediaEndTimeMs-mediaLoadData.mediaStartTimeMs));
        }

        @Override public void onLoadCanceled(EventTime eventTime,
            MediaSourceEventListener.LoadEventInfo loadEventInfo,
            MediaSourceEventListener.MediaLoadData mediaLoadData) {
            Log.d("LMM", "onLoadCanceled");
        }

        @Override public void onLoadError(EventTime eventTime,
            MediaSourceEventListener.LoadEventInfo loadEventInfo,
            MediaSourceEventListener.MediaLoadData mediaLoadData, IOException error,
            boolean wasCanceled) {
            Log.d("LMM", "onLoadError: " + loadEventInfo.dataSpec.key + " " + loadEventInfo.dataSpec.flags);
        }

        @Override public void onDownstreamFormatChanged(EventTime eventTime,
            MediaSourceEventListener.MediaLoadData mediaLoadData) {
            Log.d("LMM", "onDownstreamFormatChanged");
        }

        @Override public void onUpstreamDiscarded(EventTime eventTime,
            MediaSourceEventListener.MediaLoadData mediaLoadData) {
            Log.d("LMM", "onUpstreamDiscarded");
        }

        @Override public void onMediaPeriodCreated(EventTime eventTime) {
            Log.d("LMM", "onMediaPeriodCreated");
        }

        @Override public void onMediaPeriodReleased(EventTime eventTime) {
            Log.d("LMM", "onMediaPeriodReleased");
        }

        @Override public void onReadingStarted(EventTime eventTime) {
            Log.d("LMM", "onReadingStarted");
        }

        @Override public void onBandwidthEstimate(EventTime eventTime, int totalLoadTimeMs,
            long totalBytesLoaded, long bitrateEstimate) {
            Log.d("LMM", "onBandwidthEstimate " + totalLoadTimeMs + " totalBytesLoaded: " +totalBytesLoaded);
        }

        @Override public void onSurfaceSizeChanged(EventTime eventTime, int width, int height) {
            Log.d("LMM", "onSurfaceSizeChanged");
        }

        @Override public void onMetadata(EventTime eventTime, Metadata metadata) {
            Log.d("LMM", "onMetadata");
        }

        @Override public void onDecoderEnabled(EventTime eventTime, int trackType,
            DecoderCounters decoderCounters) {
            Log.d("LMM", "onDecoderEnabled: " + System.currentTimeMillis());
        }

        @Override
        public void onDecoderInitialized(EventTime eventTime, int trackType, String decoderName,
            long initializationDurationMs) {
            Log.d("LMM", "onDecoderInitialized :" + System.currentTimeMillis());
        }

        @Override
        public void onDecoderInputFormatChanged(EventTime eventTime, int trackType, Format format) {
            Log.d("LMM", "onDecoderInputFormatChanged : " + System.currentTimeMillis());
        }

        @Override public void onDecoderDisabled(EventTime eventTime, int trackType,
            DecoderCounters decoderCounters) {
            Log.d("LMM", "onDecoderDisabled");
        }

        @Override public void onAudioSessionId(EventTime eventTime, int audioSessionId) {
            Log.d("LMM", "onAudioSessionId");
        }

        @Override
        public void onAudioAttributesChanged(EventTime eventTime, AudioAttributes audioAttributes) {
            Log.d("LMM", "onAudioAttributesChanged");
        }

        @Override public void onVolumeChanged(EventTime eventTime, float volume) {
            Log.d("LMM", "onVolumeChanged");
        }

        @Override
        public void onAudioUnderrun(EventTime eventTime, int bufferSize, long bufferSizeMs,
            long elapsedSinceLastFeedMs) {
            Log.d("LMM", "onAudioUnderrun");
        }

        @Override
        public void onDroppedVideoFrames(EventTime eventTime, int droppedFrames, long elapsedMs) {
            Log.d("LMM", "onDroppedVideoFrames " + droppedFrames + " elapsedMs: " + elapsedMs);
        }

        @Override public void onVideoSizeChanged(EventTime eventTime, int width, int height,
            int unappliedRotationDegrees, float pixelWidthHeightRatio) {
            Log.d("LMM", "onVideoSizeChanged");
        }

        @Override public void onRenderedFirstFrame(EventTime eventTime, @Nullable Surface surface) {
            Log.d("LMM", "onRenderedFirstFrame " + System.currentTimeMillis());
        }

        @Override public void onDrmSessionAcquired(EventTime eventTime) {
            Log.d("LMM", "onDrmSessionAcquired");
        }

        @Override public void onDrmKeysLoaded(EventTime eventTime) {
            Log.d("LMM", "onDrmKeysLoaded");
        }

        @Override public void onDrmSessionManagerError(EventTime eventTime, Exception error) {
            Log.d("LMM", "onDrmSessionManagerError");
        }

        @Override public void onDrmKeysRestored(EventTime eventTime) {
            Log.d("LMM", "onDrmKeysRestored");
        }

        @Override public void onDrmKeysRemoved(EventTime eventTime) {
            Log.d("LMM", "onDrmKeysRemoved");
        }

        @Override public void onDrmSessionReleased(EventTime eventTime) {
            Log.d("LMM", "onDrmSessionReleased");
        }
    };


    @Override protected void onDestroy() {
        super.onDestroy();
        mExoPlayer.release();
    }
}