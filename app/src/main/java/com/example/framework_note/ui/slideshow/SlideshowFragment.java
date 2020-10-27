package com.example.framework_note.ui.slideshow;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.framework_note.R;
import com.example.framework_note.opengl.GlUtils;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private GLSurfaceView surfaceView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        surfaceView = (GLSurfaceView) inflater.inflate(R.layout.fragment_slideshow, container, false);
        return surfaceView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initGl();
    }

    private void initGl(){
        if (GlUtils.supportES2(getContext())) {
            surfaceView.setEGLContextClientVersion(2);
            //模拟器不工作时设置
            //surfaceView.setEGLConfigChooser(8,8,8,8, 16,0);
            surfaceView.setRenderer(new GlSurfaceRender(getContext()));
            // render mode有两种：
            // RENDERMODE_CONTINUOUSLY 会一直渲染 对应的表现就是onDrawFrame方法会被一直调用 -> 默认就是这种
            // RENDERMODE_WHEN_DIRTY 只有某个区域脏掉之后 调用requestRender才会触发渲染
            // surfaceView.setRenderMode();
        }else {
            Toast.makeText(getContext(), "not support OpenGl 2.0", Toast.LENGTH_LONG).show();
        }
    }
}