//具有x\y\z\w四个方向属性的向量
attribute vec4 a_Position;

void main() {
    //必须要复制给gl_Position
    gl_Position = a_Position;
    gl_PointSize = 10.0;
}
