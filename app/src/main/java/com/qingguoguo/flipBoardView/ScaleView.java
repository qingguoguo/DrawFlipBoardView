package com.qingguoguo.flipBoardView;

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
 * @describe :缩放
 */

public class ScaleView extends View {
    private Paint mPaint;
    private int count;

    public ScaleView(Context context) {
        super(context);
        initData();
    }

    public ScaleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    private void initData() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        count = 10;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        for (int i = 0; i < count; i++) {
            canvas.save();
            float fraction = (float) i / count;
            canvas.scale(fraction, fraction, getWidth() / 2, getHeight() / 2);
            canvas.drawRect(new Rect(0, getHeight() / 2 - getWidth() / 2, getWidth(), getHeight() / 2 + getWidth() / 2), mPaint);
            canvas.restore();
        }
    }
}
