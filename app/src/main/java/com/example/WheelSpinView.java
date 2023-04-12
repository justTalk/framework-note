package com.example;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 大转盘
 */
public class WheelSpinView extends View {

    private static final String TAG = WheelSpinView.class.getSimpleName();

    private int[] angles = null;
    private List<WheelData> mData = new ArrayList<>();

    /**
     * 画背景
     */
    private Paint mBgPaint;

    /**
     * 绘制扇形
     */
    private Paint mArcPaint;

    /**
     * 绘制文字
     */
    private Paint mTextPaint;

    /**
     * 半径
     */
    private int mRadius;

    /**
     * 圆心坐标
     */
    private int mCenter;

    /**
     * 弧形的起始角度
     */
    private int startAngle;

    private float mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SHIFT, 48, getResources().getDisplayMetrics());
    private RectF sectorRectF;
    private RectF subBgRectF;

    /**
     * 弧形划过的角度
     */
    private int sweepAngle;
    /**
     * 下标
     */
    private int position;
    private ObjectAnimator animator;
    private RotateListener listener;
    private int rotateToPosition;

    public WheelSpinView(Context context) {
        this(context, null);
    }

    public WheelSpinView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelSpinView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setData(List<WheelData> newData){
        post(() -> {
            mData.clear();
            mData.addAll(newData);
            angles = new int[mData.size()];
            invalidate();
        });
    }

    public void setTextSize(float textSize) {
        post(() -> {
            mTextSize = textSize;
            invalidate();
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        int width = Math.min(w, h);

        mCenter = width / 2;
        //半径
        mRadius = (width - getPaddingLeft() * 2) / 2;

        //设置框高都一样
        setMeasuredDimension(width, width);

        reRectF();
    }

    /**
     * 根据center重新绘制布局
     */
    private void reRectF(){
        //设置扇形绘制的范围
        sectorRectF.left = getPaddingLeft();
        sectorRectF.top = getPaddingLeft();
        sectorRectF.right = mCenter * 2 - getPaddingLeft();
        sectorRectF.bottom = mCenter * 2 - getPaddingLeft();

        //二级背景
        subBgRectF.left = mCenter/3f;
        subBgRectF.top = mCenter/3f;
        subBgRectF.right = mCenter * 2-mCenter/3f;
        subBgRectF.bottom = mCenter * 2-mCenter/3f;
    }

//    /**
//     * 初始化数据
//     * @param it
//     */
//    public void initDataToView(@Nullable List<RewardInfoBean> it,List<Bitmap> imgList) {
//        mRewardList = it;
//        mCount = it.size();
//        angles = new int[mCount];
//        mBitmaps = imgList;
//    }
    /**
     * 初始化
     */
    private void init() {

        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(Color.parseColor("#FE8727"));
        mBgPaint.setAlpha((int) (255*0.6));

        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.parseColor("#A5380C"));
        mTextPaint.setFakeBoldText(true);
        mTextPaint.setTextSize(mTextSize);

//        for (int i = 0; i < mCount; i++) {
//            mBitmaps[i] = BitmapFactory.decodeResource(getResources(), mImages[i % 2]);
//        }

        sectorRectF = new RectF(0,0,0,0);
        subBgRectF = new RectF(0,0,0,0);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = (event.getX() - mCenter);
            float y = (event.getY() - mCenter);
            float touchAngle = 0;
            int touchRadius = 0;
            //判断点击的范围是否在转盘内
            if (x < 0 && y > 0) {//第二象限
                touchAngle += 180;
            } else if (x < 0 && y < 0) {//第三象限
                touchAngle += 180;
            } else if (x > 0 && y < 0) {//第四象限
                touchAngle += 360;
            }
            //Math.atan(y/x) 返回正数值表示相对于 x 轴的逆时针转角，返回负数值则表示顺时针转角。
            // 返回值乘以 180/π，将弧度转换为角度。
            touchAngle += (float) Math.toDegrees(Math.atan(y / x));

            touchRadius = (int) Math.sqrt(x * x + y * y);
            if (touchRadius < mRadius) {
                position = -Arrays.binarySearch(angles, (int) touchAngle) - 1;
//                Log.d(TAG, "onTouchEvent: " + mRewardList.get(position - 1).getName());
            }

            return true;
        }
        return super.onTouchEvent(event);
    }
    public void drawWheel(Canvas canvas){
        //绘制扇形
        //设置每一个扇形的角度
        sweepAngle = 360 / mData.size();
        startAngle = -120;
        for (int i = 0; i < mData.size(); i++) {
            mArcPaint.setColor(mData.get(i).getBgColor());
            //sectorRectF 扇形绘制范围  startAngle 弧开始绘制角度 sweepAngle 每次绘制弧的角度
            // useCenter 是否连接圆心
            canvas.drawArc(sectorRectF, startAngle, sweepAngle, true, mArcPaint);
//            //绘制二级小背景
            //canvas.drawArc(subBgRectF, startAngle, sweepAngle, true, mBgPaint);

            //3.绘制文字
            drawTexts(canvas, mData.get(i).getText());
            angles[i] = startAngle;
            Log.d(TAG, "onDraw: " + angles[i] + "     " + i);
            startAngle += sweepAngle;
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if (!mData.isEmpty()){
            drawWheel(canvas);
        }
        super.onDraw(canvas);
    }

    /**
     * 以二分之一的半径的长度，扇形的一半作为图片的中心点
     * 图片的宽度为imageWidth
     */
    private void drawIcons(Canvas canvas, Bitmap mBitmap) {
        int imageWidth = mRadius / 9;
        //计算半边扇形的角度 度=Math.PI/180 弧度=180/Math.PI
        float angle = (float) ((startAngle + sweepAngle / 2) * Math.PI / 180);
        //计算中心点的坐标
        int r = mRadius / 2;
        float x = (float) (mCenter + r * Math.cos(angle));
        float y = (float) (mCenter + r * Math.sin(angle));
        Matrix matrix = new Matrix();
        matrix.postTranslate(x - imageWidth,y - imageWidth);
        matrix.postRotate(startAngle+120,x,y);
        //设置绘制图片的范围
//        RectF rectF = new RectF(x - imageWidth, y - imageWidth, x + imageWidth, y + imageWidth);
//        CoreLogUtils.d("icon绘制区域: rectF=========="+rectF);
        canvas.drawBitmap(mBitmap,matrix,null);
//        canvas.drawBitmap(mBitmap, null, rectF, null);
    }

    /**
     * 使用path添加一个路径
     * 绘制文字的路径
     */
    private void drawTexts(Canvas canvas, String mString) {
        Path path = new Path();
        //添加一个圆弧的路径
        path.addArc(sectorRectF, startAngle, sweepAngle);
        String startText = null;
        String endText = null;
        //测量文字的宽度
        float textWidth = mTextPaint.measureText(mString);
        //水平偏移
        int hOffset = (int) (mRadius * 2 * Math.PI / mData.size() / 2 - textWidth / 2);
        //计算弧长 处理文字过长换行
        int l = (int) ((360 / mData.size()) * Math.PI * mRadius / 180);
        if (textWidth > l * 4 / 5f) {
            int index = mString.length() / 2;
            startText = mString.substring(0, index);
            endText = mString.substring(index, mString.length());

            float startTextWidth = mTextPaint.measureText(startText);
            float endTextWidth = mTextPaint.measureText(endText);
            //水平偏移
            hOffset = (int) (mRadius * 2 * Math.PI / mData.size() / 2 - startTextWidth / 2);
            int endHOffset = (int) (mRadius * 2 * Math.PI / mData.size() / 2 - endTextWidth / 2);
            //文字高度
            int h = (int) ((mTextPaint.ascent() + mTextPaint.descent()) * 1.5);

            //根据路径绘制文字
            //hOffset 水平的偏移量 vOffset 垂直的偏移量
            canvas.drawTextOnPath(startText, path, hOffset, mRadius / 6f, mTextPaint);
            canvas.drawTextOnPath(endText, path, endHOffset, mRadius / 6f - h, mTextPaint);
        } else {
            //根据路径绘制文字
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < mString.length(); i++) {
                String c = ""+ mString.charAt(i);
                builder.append(c.toLowerCase());
                if(i+1 < mString.length()) {
                    builder.append("\u00A0");
                }
            }
            SpannableString finalText = new SpannableString(builder.toString());
            if(builder.toString().length() > 1) {
                for(int i = 1; i < builder.toString().length(); i+=2) {
                    finalText.setSpan(new ScaleXSpan((1+1)/10), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            canvas.drawTextOnPath(finalText.toString(), path, hOffset, mRadius / 6f, mTextPaint);
        }

    }


    public void rotate(final int i) {

        rotateToPosition = 360 / mData.size() * (mData.size() - i);
        float toDegree = 360f * 5 + rotateToPosition;

        animator = ObjectAnimator.ofFloat(this, "rotation", 0, toDegree);
        animator.setDuration(3000);
        animator.setRepeatCount(0);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setAutoCancel(true);
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                if (listener != null){
                    listener.startRotate();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //指针指向的方向为270度
                if (listener != null) {
                    rotateToPosition = 270 - rotateToPosition;
                    if (rotateToPosition < 0) {
                        rotateToPosition += 360;
                    } else if (rotateToPosition == 0) {
                        rotateToPosition = 270;
                    }
                    position = -Arrays.binarySearch(angles, rotateToPosition) - 1;
                    if (position>0){
                        listener.value(mData.get(position - 1).getText());
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {


            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    public void setListener(RotateListener listener) {
        this.listener = listener;
    }

//    public void setValue(String v1, String v2) {
//        for (int i = 0; i < 10; i++) {
//            if(i==0||i==2||i==4||i==7){
//                mStrings[i]=v2;
//            }else {
//                mStrings[i]=v1;
//            }
//        }
//    }

//    public void updateIndexData(RewardInfoBean rewardInfoBean,int position) {
//    }

    public interface RotateListener {

        void value(String infoBean);
        void startRotate();
    }
}
