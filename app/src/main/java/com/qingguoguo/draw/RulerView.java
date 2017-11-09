package com.qingguoguo.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author :qingguoguo
 * @datetime ：2017/11/9 18:32
 * @describe :刻度尺
 */

public class RulerView extends View {
    private Paint mPaint;
    private int left = 50;
    private int right = 50;
    /**
     * 刻度尺高度
     */
    private int height = 200;

    private int divideWidth = 15;
    private int divideHeightIndex = 100;
    private int divideHeight = 50;
    private int divideMiddleHeight = 75;
    /**
     * 刻度尺文字尺寸
     */
    private int textSize = 25;

    public RulerView(Context context) {
        super(context);
        initData();
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    private void initData() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //先绘制刻度尺外框
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(textSize);
        Rect rect = new Rect(left, getHeight() / 2 - height / 2, getWidth() - right, getHeight() / 2 + height / 2);
        canvas.drawRect(rect, mPaint);

        //绘制刻度线
        int topy;
        for (int i = 0; i < 61; i++) {
            if (i % 10 == 0) {
                topy = divideHeightIndex;
                //绘制数字
                canvas.drawText(String.valueOf(i), left + divideWidth - mPaint.measureText(String.valueOf(i)) / 2, getHeight() / 2 + height / 2 - topy - 10, mPaint);
            } else if (i % 5 == 0) {
                topy = divideMiddleHeight;
            } else {
                topy = divideHeight;
            }
            canvas.drawLine(left + divideWidth, getHeight() / 2 + height / 2, left + divideWidth, getHeight() / 2 + height / 2 - topy, mPaint);
            canvas.translate(divideWidth, 0);
        }
    }
}
