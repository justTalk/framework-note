//定义浮点数精度
precision mediump float;
//varying类型的变量会讲给定值进行混合
varying vec4 v_Color;


void main() {
    gl_FragColor = v_Color;
}
