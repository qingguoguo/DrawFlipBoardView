package com.qingguoguo.draw;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author :qingguoguo
 * @datetime ：2017/11/13 16:30
 * @describe :
 */

public class FlipBoardView extends View {

    private Paint mPaint;
    private Bitmap bitmap;
    private Camera camera;
    private ObjectAnimator objectAnimator;
    private int degree;

    public void setDegree(int degree) {
        this.degree = degree;
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
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float newZ = -displayMetrics.density * 6;
        camera.setLocation(0, 0, newZ);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.flip_board);
        objectAnimator = ObjectAnimator.ofInt(this, "degree", 0, 360);
        objectAnimator.setDuration(3000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        objectAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        objectAnimator.end();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //1.沿Y轴旋转45度
        //1.1 右半边Y轴旋转45度
        if (degree <= 45) {
            canvas.save();
            canvas.translate(getWidth() / 2, getHeight() / 2);
            camera.save();
            camera.rotateY(-degree);
            camera.applyToCanvas(canvas);
            camera.restore();
            canvas.translate(-getWidth() / 2, -getHeight() / 2);
            canvas.drawBitmap(bitmap, getWidth() / 2 - bitmap.getWidth() / 2, getHeight() / 2 - bitmap.getHeight() / 2, mPaint);
            canvas.restore();
            //1.2 左半边不动
            canvas.save();
            canvas.clipRect(0, 0, getWidth() / 2, getHeight());
            canvas.drawBitmap(bitmap, getWidth() / 2 - bitmap.getWidth() / 2, getHeight() / 2 - bitmap.getHeight() / 2, mPaint);
            canvas.restore();
        }

        //2.中间动画
        //3.分上下两部分
        //3.1 上半边沿X轴旋转30度
        if (degree > 45) {
            canvas.save();
            canvas.translate(getWidth() / 2, getHeight() / 2);
            camera.save();
            camera.rotateX(-degree);
            camera.applyToCanvas(canvas);
            camera.restore();
            canvas.translate(-getWidth() / 2, -getHeight() / 2);
            canvas.clipRect(0, 0, getWidth(), getHeight() / 2);
            canvas.drawBitmap(bitmap, getWidth() / 2 - bitmap.getWidth() / 2, getHeight() / 2 - bitmap.getHeight() / 2, mPaint);
            canvas.restore();

            //3.2 下半边沿X轴旋转30度后保持不动
            canvas.save();
            canvas.translate(getWidth() / 2, getHeight() / 2);
            camera.save();
            camera.rotateX(30);
            camera.applyToCanvas(canvas);
            camera.restore();
            canvas.translate(-getWidth() / 2, -getHeight() / 2);
            canvas.clipRect(0, getHeight() / 2, getWidth(), getHeight());
            canvas.drawBitmap(bitmap, getWidth() / 2 - bitmap.getWidth() / 2, getHeight() / 2 - bitmap.getHeight() / 2, mPaint);
            canvas.restore();
        }
    }
}
