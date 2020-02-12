package com.vk2.touchsreentab.utils;

import android.os.Build;
import android.view.View;

import androidx.annotation.FloatRange;

import com.yarolegovich.discretescrollview.transform.DiscreteScrollItemTransformer;
import com.yarolegovich.discretescrollview.transform.Pivot;

public class CarouselTransformer implements DiscreteScrollItemTransformer {
    private static final float SCALE_X_CENTER = 1.3f;
    private static final float SCALE_Y_CENTER = 1f;

    private static final float SCALE_X_SUB_1 = 1.2f;
    private static final float SCALE_Y_SUB_1 = 0.9f;

    private Pivot pivotX;
    private Pivot pivotY;
    private float minScale;
    private float maxMinDiff;

    public CarouselTransformer() {
        pivotX = Pivot.X.CENTER.create();
        pivotY = Pivot.Y.CENTER.create();
        minScale = 0.8f;
        maxMinDiff = 0.2f;
    }

    @Override
    public void transformItem(View item, float position) {
        pivotX.setOn(item);
        pivotY.setOn(item);

        if (position == 0){
            item.setScaleX(SCALE_X_CENTER);
            item.setScaleY(SCALE_Y_CENTER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                item.setZ(SCALE_Y_CENTER);
            }
        }else if (position < 0){
            item.setScaleX(SCALE_X_SUB_1);
            item.setScaleY(SCALE_Y_SUB_1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                item.setZ(SCALE_Y_SUB_1);
            }
        }else if (position > 0){
            item.setScaleX(SCALE_X_SUB_1);
            item.setScaleY(SCALE_Y_SUB_1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                item.setZ(SCALE_Y_SUB_1);
            }
        }

    }

    public static class Builder {

        private CarouselTransformer transformer;
        private float maxScale;

        public Builder() {
            transformer = new CarouselTransformer();
            maxScale = 1f;
        }

        public Builder setMinScale(@FloatRange(from = 0.01) float scale) {
            transformer.minScale = scale;
            return this;
        }

        public Builder setMaxScale(@FloatRange(from = 0.01) float scale) {
            maxScale = scale;
            return this;
        }

        public Builder setPivotX(Pivot.X pivotX) {
            return setPivotX(pivotX.create());
        }

        public Builder setPivotX(Pivot pivot) {
            assertAxis(pivot, Pivot.AXIS_X);
            transformer.pivotX = pivot;
            return this;
        }

        public Builder setPivotY(Pivot.Y pivotY) {
            return setPivotY(pivotY.create());
        }

        public Builder setPivotY(Pivot pivot) {
            assertAxis(pivot, Pivot.AXIS_Y);
            transformer.pivotY = pivot;
            return this;
        }

        public CarouselTransformer build() {
            transformer.maxMinDiff = maxScale - transformer.minScale;
            return transformer;
        }

        private void assertAxis(Pivot pivot, @Pivot.Axis int axis) {
            if (pivot.getAxis() != axis) {
                throw new IllegalArgumentException("You passed a Pivot for wrong axis.");
            }
        }
    }
}
