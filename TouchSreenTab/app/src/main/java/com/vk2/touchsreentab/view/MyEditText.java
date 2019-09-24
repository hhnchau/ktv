package com.vk2.touchsreentab.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.vk2.touchsreentab.R;

public class MyEditText extends android.support.v7.widget.AppCompatEditText {
    private Bitmap mClearButton;
    private Paint mPaint;

    private int mInitPaddingRight;

    private int mButtonPadding = dp2px(3);

    public MyEditText(Context context) {
        super(context);
        init(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {

        mClearButton = BitmapFactory.decodeResource(context.getResources(), R.mipmap.clear_edt);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mInitPaddingRight = getPaddingRight();
    }


    private void setPadding(boolean isShow) {
        int paddingRight = mInitPaddingRight + (isShow ? mClearButton.getWidth() + mButtonPadding + mButtonPadding : 0);

        setPadding(getPaddingLeft(), getPaddingTop(), paddingRight, getPaddingBottom());
    }

    private Rect getRect(boolean isShow) {
        int left, top, right, bottom;

        right = isShow ? getMeasuredWidth() + getScrollX() - mButtonPadding - mButtonPadding : 0;
        left = isShow ? right - mClearButton.getWidth() : 0;
        top = isShow ? (getMeasuredHeight() - mClearButton.getHeight()) / 2 : 0;
        bottom = isShow ? top + mClearButton.getHeight() : 0;

        setPadding(isShow);

        return new Rect(left, top, right, bottom);
    }


    private void drawBitmap(Canvas canvas, Rect rect) {
        if (rect != null) {
            canvas.drawBitmap(mClearButton, null, rect, mPaint);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();

        if (getText() != null)
            drawBitmap(canvas, getRect(hasFocus() && getText().length() > 0));

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getX() - (getMeasuredWidth() - getPaddingRight()) >= 0) {
                setError(null);
                this.setText("");
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public int dp2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}