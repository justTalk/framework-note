//具有x\y\z\w四个方向属性的向量
attribute vec4 a_Position;
attribute vec4 a_Color;
varying vec4 v_Color;

void main() {
    //这里将a_Color赋值给v_Color后 varying会对值进行混合 之后会应用到每个片段着色器中
    v_Color = a_Color;
    //必须要复制给gl_Position
    gl_Position = a_Position;
    gl_PointSize = 10.0;
}
