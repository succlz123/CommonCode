package org.succlz123.commoncode.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by succlz123 on 16/1/18.
 */

public class TuView extends View {
    private Paint mPaint;

    public TuView(Context context) {
        this(context, null);
    }

    public TuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TuView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#80000000"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int viewHeight = getMeasuredHeight();
        int viewWidth = getMeasuredWidth();

        Path path1 = new Path();
        RectF rect1 = new RectF(viewWidth / 3, 0, viewWidth, viewHeight);
        path1.addRect(rect1, Path.Direction.CCW);

        Path path2 = new Path();

        path2.moveTo(viewWidth / 3, 0);
        path2.quadTo(0, viewHeight / 2, viewWidth / 3, viewHeight);
        path2.close();

//        RectF yy = new RectF(0, 0, viewWidth / 3 * 2, viewHeight);
//        VideoDownloadContentProvider.arcTo(yy, 90, 360);
//        VideoDownloadContentProvider.close();

        canvas.drawPath(path1, mPaint);
        canvas.drawPath(path2, mPaint);
    }
}
