package com.example;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

public class ClapDetector {

    public void startClapDetection() {
        int sampleRate = 8000;
        int bufferSize = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);

        AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);

        short[] buffer = new short[bufferSize];
        audioRecord.startRecording();
        Log.d("LMM", "size : " + buffer.length);

        boolean isClapDetected = false;
        while (!isClapDetected) {
            audioRecord.read(buffer, 0, bufferSize);
            Log.d("LMM", "start time ---> " + System.currentTimeMillis());
            int min = 0;
            int max = 0;
            for (short s : buffer) {
                min = Math.min(min, Math.abs(s));
                max = Math.max(max, Math.abs(s));
                //Log.d("LMM", "Math.abs(s): " + Math.abs(s));
                //if (Math.abs(s) > threshold) { // threshold 是您设置的检测拍手的阈值，需要根据实际情况调整
                //    isClapDetected = true;
                //    break;
                //}
            }
            Log.d("LMM", "min: " + min + " max: " + max);
            Log.d("LMM", "end time ---> " + System.currentTimeMillis());
        }

        audioRecord.stop();
        audioRecord.release();
    }
}
