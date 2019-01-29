package org.succlz123.commoncode.kantu;

import org.succlz123.commoncode.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by succlz123 on 15/12/10.
 */
public class CustomCanvasView extends ImageView {
    private GestureDetector mGestureDetector;
    private float mImageWidth;
    private float mImageHeight;

    private int mMode = 0;
    private static final int MODE_DRAG = 1;
    private static final int MODE_ZOOM = 2;

    private float mMaxScale = 6f;
    private float mInitScale = 1f;
    private float mDoubleClickScale = 2f;

    // 缩放开始时的手指间距
    private float mStartDis;
    // 用于记录开始时候的坐标位置
    private PointF startPoint = new PointF();

    private Matrix mMatrix = new Matrix();
    private Matrix mCurrentMatrix = new Matrix();

    public CustomCanvasView(Context context) {
        this(context, null);
    }

    public CustomCanvasView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomCanvasView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
        BitmapFactory.decodeResource(getResources(), R.drawable.liu, options);

        options.inSampleSize = calculateInSampleSize(options, w, h);
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.liu, options);

        setImageBitmap(bmp);
        setScaleType(ScaleType.MATRIX);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);

        mMatrix.set(getMatrix());
        float[] values = new float[9];
        mMatrix.getValues(values);
        mGestureDetector = new GestureDetector(getContext(), new GestureListener());

//        mImageWidth = getWidth() / values[Matrix.MSCALE_X];
//        mImageHeight = (getHeight() - values[Matrix.MTRANS_Y] * 2) / values[Matrix.MSCALE_Y];
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mMode = MODE_DRAG;
                startPoint.set(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                minCheckMatrix();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mMode == MODE_ZOOM) {
                    setZoomMatrix(event);
                } else if (mMode == MODE_DRAG) {
                    setDragMatrix(event);
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mMode = MODE_ZOOM;
                mStartDis = distance(event);
                break;
            default:
                break;
        }
        return mGestureDetector.onTouchEvent(event);
    }


    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        public GestureListener() {
            super();
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            onDoubleClick();
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            return super.onContextClick(e);
        }
    }

    /**
     * 设置缩放Matrix
     */
    private void setZoomMatrix(MotionEvent event) {
        //只有同时触屏两个点的时候才执行
        if (event.getPointerCount() < 2) {
            return;
        }
        // 结束距离
        float endDis = distance(event);
        // 两个手指并拢在一起的时候像素大于10
        if (endDis > 10f) {
            // 得到缩放倍数
            float scale = endDis / mStartDis;
            mStartDis = endDis;
            //初始化Matrix
            mCurrentMatrix.set(getImageMatrix());
            float[] values = new float[9];
            mCurrentMatrix.getValues(values);

            if (scale * values[Matrix.MSCALE_X] > mMaxScale) {
                scale = mMaxScale / values[Matrix.MSCALE_X];
            }
            mCurrentMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);

            setImageMatrix(mCurrentMatrix);
        }
    }

    /**
     * 计算两个手指间的距离
     */
    private float distance(MotionEvent event) {
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
        /** 使用勾股定理返回两点之间的距离 */
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * 检验scale，使图像缩放后不会超出最大倍数
     */
    private float checkMaxScale(float scale, float[] values) {
        if (scale * values[Matrix.MSCALE_X] > mMaxScale) {
            scale = mMaxScale / values[Matrix.MSCALE_X];
        }
        mCurrentMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
        return scale;
    }

    /**
     * 判断是否需要重置
     *
     * @return 当前缩放级别小于模板缩放级别时，重置
     */
    private boolean checkRest() {
        float[] values = new float[9];
        getImageMatrix().getValues(values);
        //获取当前X轴缩放级别
        float scale = values[Matrix.MSCALE_X];
        //获取模板的X轴缩放级别，两者做比较
        mMatrix.getValues(values);
        return scale < values[Matrix.MSCALE_X];
    }

    /**
     * 重置Matrix
     */
    private void minCheckMatrix() {
        float[] values = new float[9];
        getImageMatrix().getValues(values);
        //获取当前X轴缩放级别
        float scale = values[Matrix.MSCALE_X];
        //获取模板的X轴缩放级别，两者做比较
        mMatrix.getValues(values);

        boolean isOk = scale < values[Matrix.MSCALE_X];

        if (isOk) {
            mCurrentMatrix.set(mMatrix);
            setImageMatrix(mCurrentMatrix);
        }
    }

    /**
     * 双击时触发
     */
    public void onDoubleClick() {
        float scale = isZoomChanged() ? 1 : mDoubleClickScale;
        mCurrentMatrix.set(mMatrix);//初始化Matrix
        mCurrentMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
        setImageMatrix(mCurrentMatrix);
    }

    /**
     * 判断缩放级别是否是改变过
     *
     * @return true表示非初始值, false表示初始值
     */
    private boolean isZoomChanged() {
        float[] values = new float[9];
        getImageMatrix().getValues(values);
        //获取当前X轴缩放级别
        float scale = values[Matrix.MSCALE_X];
        //获取模板的X轴缩放级别，两者做比较
        mMatrix.getValues(values);
        return scale != values[Matrix.MSCALE_X];
    }

    public void setDragMatrix(MotionEvent event) {
        if (isZoomChanged()) {
            // 得到x轴的移动距离
            float dx = event.getX() - startPoint.x;
            // 得到x轴的移动距离
            float dy = event.getY() - startPoint.y;
            // 避免和双击冲突,大于10f才算是拖动
            if (Math.sqrt(dx * dx + dy * dy) > 10f) {
                startPoint.set(event.getX(), event.getY());
                //在当前基础上移动
                mCurrentMatrix.set(getImageMatrix());
                float[] values = new float[9];
                mCurrentMatrix.getValues(values);
//                dx = checkDxBound(values, dx);
//                dy = checkDyBound(values, dy);
                mCurrentMatrix.postTranslate(dx, dy);
                setImageMatrix(mCurrentMatrix);
            }
        }
    }

    /**
     * 和当前矩阵对比，检验dx，使图像移动后不会超出ImageView边界
     */
    private float checkDxBound(float[] values, float dx) {
        float width = getWidth();
        if (mImageWidth * values[Matrix.MSCALE_X] < width)
            return 0;
        if (values[Matrix.MTRANS_X] + dx > 0)
            dx = -values[Matrix.MTRANS_X];
        else if (values[Matrix.MTRANS_X] + dx < -(mImageWidth * values[Matrix.MSCALE_X] - width))
            dx = -(mImageWidth * values[Matrix.MSCALE_X] - width) - values[Matrix.MTRANS_X];
        return dx;
    }

    /**
     * 和当前矩阵对比，检验dy，使图像移动后不会超出ImageView边界
     */
    private float checkDyBound(float[] values, float dy) {
        float height = getHeight();
        if (mImageHeight * values[Matrix.MSCALE_Y] < height)
            return 0;
        if (values[Matrix.MTRANS_Y] + dy > 0)
            dy = -values[Matrix.MTRANS_Y];
        else if (values[Matrix.MTRANS_Y] + dy < -(mImageHeight * values[Matrix.MSCALE_Y] - height))
            dy = -(mImageHeight * values[Matrix.MSCALE_Y] - height) - values[Matrix.MTRANS_Y];
        return dy;
    }


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight | (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
