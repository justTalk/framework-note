package com.example.framework_note.ui.slideshow;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.util.Log;

import com.example.framework_note.BuildConfig;
import com.example.framework_note.R;
import com.example.framework_note.opengl.GlUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20.*;


/**
 * @Description: java类作用描述
 * @Author: Andy
 * @CreateDate: 2020/10/22 21:08
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/22 21:08
 * @Warn: 更新说明
 * @Version: 1.0
 */
public class GlSurfaceRender implements GLSurfaceView.Renderer {

    //Tips 1：OpenGl相关Api都只能在渲染线程中触发 不能在主线程回调 主线程中想调用OpenGl相关方法时 可以使用queueEvent
    // -> Render的接口方法都是在RenderThread中触发的

    private static final String TAG = GlSurfaceRender.class.getSimpleName();
    //每个float数据占字节数
    private static final int BYTES_PER_FLOAT = 4;
    //每个顶点的分量个数
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final String U_COLOR = "u_Color";
    private static final String A_POSITION = "a_Position";

    //逆时针顺序定义顶点
    private float[] tableVertices = {
            -0.5f,-0.5f,
            0.5f,0.5f,
            -0.5f, 0.5f,

            -0.5f,-0.5f,
            0.5f,-0.5f,
            0.5f, 0.5f,

            //line
            -0.5f, 0f,
            0.5f, 0f,

            //ball
            0f, -0.25f,
            0f, 0.25f,

            //ice ball
            0f, 0f,
    };

    private FloatBuffer floatBuffer;
    private int program;
    private int uColorLocation;
    private int aPositionLocation;
    private Context context;

    public GlSurfaceRender(Context context){
        floatBuffer = ByteBuffer.allocateDirect(tableVertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        floatBuffer.put(tableVertices);
        this.context = context;
    }


    private void initShader(){
        //获取顶点着色器脚本
        String vertecShader = GlUtils.readTextFileFromRawResource(context, R.raw.simple_vertex_shader);
        //获取片段着色器脚本
        String fragmentShader = GlUtils.readTextFileFromRawResource(context, R.raw.simple_fragment_shader);
        //编译顶点着色器
        int vertecShaderObj = GlUtils.compileShader(GLES20.GL_VERTEX_SHADER, vertecShader);
        //编译片段着色器
        int fragmentShaderObj = GlUtils.compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);
        //创建程序并链接着色器
        program = GlUtils.linkProgram(vertecShaderObj, fragmentShaderObj);
        if (BuildConfig.DEBUG) {
            //检验程序
            GlUtils.validateProgram(program);
        }
        //使用程序
        GLES20.glUseProgram(program);
        //获取color位置
        uColorLocation = GLES20.glGetUniformLocation(program, U_COLOR);
        //获取属性位置
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        //充值数据读取位置
        floatBuffer.position(0);
        //告诉OpenGl a_Position属性的数据来源 每个顶点的分量数量 分量数据类型
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT,
                GLES20.GL_FLOAT, false, 0, floatBuffer);
        GLES20.glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d(TAG, "onSurfaceCreated  -> ");
        //设置清屏颜色rgba
        GLES20.glClearColor(0f, 0f, 0f, 0f);
        initShader();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d(TAG, "onSurfaceChanged   -> " + width + " height -> " + height);
        //设置渲染（Surface）窗口大小
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.d(TAG, "onDrawFrame   -> ");
        //清屏 该操作之后就会设置屏幕为之前设置的清屏颜色
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

        GLES20.glUniform4f(uColorLocation, 1.0f, 0f, 0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);

        GLES20.glUniform4f(uColorLocation, 0f, 0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);
        GLES20.glUniform4f(uColorLocation, 1.0f, 0f, 0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);

        GLES20.glUniform4f(uColorLocation, 0.5f, 0.5f, 0.5f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 10, 1);
    }
}
