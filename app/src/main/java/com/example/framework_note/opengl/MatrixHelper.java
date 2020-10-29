package com.example.framework_note.opengl;

/**
 * @Description: java类作用描述
 * @Author: Andy
 * @CreateDate: 2020/10/29 20:28
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/29 20:28
 * @Warn: 更新说明
 * @Version: 1.0
 */
public final class MatrixHelper {

    /**
     * @Author Andy
     * @Description 透视投影
     * @param m 透视矩阵
     * @param offset 矩阵偏移量
     * @param aspect 宽高比 width / height
     * @param fovy 视野角度
     * @param zFar 焦点到大端的距离
     * @param zNear 焦点到近端的距离
     */
    public static void perspectiveM(float[] m, int offset,
                                    float fovy, float aspect, float zNear, float zFar){
        float f = 1.0f / (float) Math.tan(fovy * (Math.PI / 360.0));
        float rangeReciprocal = 1.0f / (zNear - zFar);

        m[offset + 0] = f / aspect;
        m[offset + 1] = 0.0f;
        m[offset + 2] = 0.0f;
        m[offset + 3] = 0.0f;

        m[offset + 4] = 0.0f;
        m[offset + 5] = f;
        m[offset + 6] = 0.0f;
        m[offset + 7] = 0.0f;

        m[offset + 8] = 0.0f;
        m[offset + 9] = 0.0f;
        m[offset + 10] = (zFar + zNear) * rangeReciprocal;
        m[offset + 11] = -1.0f;

        m[offset + 12] = 0.0f;
        m[offset + 13] = 0.0f;
        m[offset + 14] = 2.0f * zFar * zNear * rangeReciprocal;
        m[offset + 15] = 0.0f;
    }
}
