package com.qingguoguo.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author :qingguoguo
 * @datetime ：2017/11/13 16:30
 * @describe :
 */

public class FlipBoardView extends View {

    Paint mPaint;
    Bitmap bitmap;
    Camera camera;

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
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.flip_board);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //1.沿Y轴旋转45度
        canvas.save();
        camera.save();
        camera.setLocation(0,0,-1500);

        canvas.translate(getWidth() / 2, getHeight() / 2);
        camera.rotateY(-45);
        camera.applyToCanvas(canvas);
        camera.restore();

        canvas.translate(-getWidth() / 2, -getHeight() / 2);
        canvas.drawBitmap(bitmap, getWidth() / 2 - bitmap.getWidth() / 2, getHeight() / 2 - bitmap.getHeight() / 2, mPaint);
        canvas.restore();

        //2.
        //3.沿X轴旋转45度
    }
}
