package com.qingguoguo.flipBoardView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author :qingguoguo
 * @datetime ：2017/11/13 16:30
 * @describe :
 * <p>
 * 动画分左右两部分实现：
 * 左：平面逆时针旋转270度，再绕X轴三维旋转30度
 * 右：右边先绕Y轴旋转45度 ，再逆时针旋转270度
 */

public class FlipBoardView extends View {
    private static String TAG = FlipBoardView.class.getSimpleName();

    private Paint mPaint;
    private Bitmap bitmap;
    private Camera camera;
    private AnimatorSet animatorSet;
    private Handler handler = new Handler();

    /**
     * 动画第一部分，沿Y轴旋转角度
     */
    private float degreeY;
    /**
     * 动画第二部分，canvas水平面内的旋转角度
     */
    private float degreeZ;
    /**
     * 动画第三部分，沿X轴旋转角度
     */
    private float degreeX;


    public void setDegreeY(float degreeY) {
        this.degreeY = degreeY;
        invalidate();
    }

    public void setDegreeZ(float degreeZ) {
        this.degreeZ = degreeZ;
        invalidate();
    }

    public void setDegreeX(float degreeX) {
        this.degreeX = degreeX;
        invalidate();
    }

    public FlipBoardView(Context context) {
        super(context);
        initData();
    }

    public FlipBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    private void initData() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        camera = new Camera();
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.flip);
        animatorSet = new AnimatorSet();

        //糊脸修正
        camera.setLocation(0, 0, -getResources().getDisplayMetrics().density * 6);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "degreeY", 0, -45);
        objectAnimator.setDuration(1000);

        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(this, "degreeZ", 0, 270);
        objectAnimator2.setDuration(800);

        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(this, "degreeX", 0, -30);
        objectAnimator3.setDuration(500);

        //设置监听，动画结束后再延迟并启动动画
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        start();
                    }
                }, 500);
            }
        });
        //按序执行动画
        animatorSet.playSequentially(objectAnimator, objectAnimator2, objectAnimator3);
    }

    public void start() {
        degreeY = 0;
        degreeZ = 0;
        degreeX = 0;
        animatorSet.start();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animatorSet.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animatorSet.end();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;

        //1.右半边先Y轴旋转45度后，canvas一边旋转一边裁切
        canvas.save();
        camera.save();
        canvas.translate(centerX, centerY);
        camera.rotateY(degreeY);
        canvas.rotate(-degreeZ);
        camera.applyToCanvas(canvas);
        camera.restore();
        //canvas已经平移了，裁切参数也要平移
        canvas.clipRect(0, -centerY, centerX, centerY);
        canvas.rotate(degreeZ);
        canvas.translate(-centerX, -centerY);
        canvas.drawBitmap(bitmap, x, y, mPaint);
        canvas.restore();

        //2.左半边一边旋转一边裁切
        canvas.save();
        canvas.translate(centerX, centerY);
        //3沿X轴旋转30度
        camera.save();
        camera.rotateX(degreeX);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.rotate(-degreeZ);
        //canvas已经平移了，裁切参数也要平移
        canvas.clipRect(-centerX, -centerY, 0, getHeight());
        canvas.rotate(degreeZ);
        canvas.translate(-centerX, -centerY);
        canvas.drawBitmap(bitmap, x, y, mPaint);
        canvas.restore();
    }
}
