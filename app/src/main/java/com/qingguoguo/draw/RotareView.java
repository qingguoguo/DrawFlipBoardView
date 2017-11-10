package com.qingguoguo.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author :qingguoguo
 * @datetime ：2017/11/10 12:41
 * @describe :旋转
 */

public class RotareView extends View {

    Paint mPaint;
    Paint.FontMetrics fontMetrics;
    private Rect mBound;

    public RotareView(Context context) {
        super(context);
        initData();
    }

    public RotareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    private void initData() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(8);
        mPaint.setTextSize(40);
        fontMetrics = mPaint.getFontMetrics();
        mBound = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mPaint);

        for (int i = 1; i <= 360; i++) {
            canvas.save();
            if (i % 6 == 0 && i % 30 != 0) {
                canvas.rotate(i, getWidth() / 2, getHeight() / 2);
                drawDegree(canvas, false);
            } else if (i % 30 == 0) {
                canvas.rotate(i, getWidth() / 2, getHeight() / 2);
                drawHour(canvas, i);
                drawDegree(canvas, true);
            }
            canvas.restore();
        }
    }

    /**
     * 画刻度线
     *
     * @param canvas
     * @param b
     */
    private void drawDegree(Canvas canvas, boolean b) {
        canvas.drawLine(getWidth() / 2, getHeight() / 2 - getWidth() / 2,
                getWidth() / 2, getHeight() / 2 - getWidth() / 2 + (b ? 60 : 30), mPaint);
    }

    /**
     * 画小时文字
     *
     * @param canvas
     * @param i
     */
    private void drawHour(Canvas canvas, int i) {
        int hour = i / 30;
        mPaint.getTextBounds(String.valueOf(hour), 0, String.valueOf(hour).length(), mBound);
        if (i >= 90 && i <= 270) {
            //刻度3 9需要旋转-90度 90度，45678旋转180度 关键是找到文字的中心
            canvas.save();
            canvas.rotate((i == 90 ? -90 : (i == 270 ? 90 : 180)), getWidth() / 2,
                    getHeight() / 2 - getWidth() / 2 + 60 + (mBound.bottom - mBound.top) / 2);

            //绘制文字
            canvas.drawText(String.valueOf(hour), getWidth() / 2 - mPaint.measureText(String.valueOf(hour)) / 2,
                    getHeight() / 2 - getWidth() / 2 + 60 + (mBound.bottom - mBound.top) / 2, mPaint);
            canvas.restore();
        } else {
            //绘制文字
            canvas.drawText(String.valueOf(hour), getWidth() / 2 - mPaint.measureText(String.valueOf(hour)) / 2,
                    getHeight() / 2 - getWidth() / 2 + 60 + (mBound.bottom - mBound.top) / 2, mPaint);
        }
    }
}
