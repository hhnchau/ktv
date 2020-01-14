package com.vk2.touchsreentab.view.DiscreteScrollView.custom.transform;

import android.os.Build;
import android.view.View;

import androidx.annotation.FloatRange;
import androidx.annotation.RequiresApi;

import com.yarolegovich.discretescrollview.transform.Pivot;

public class CustomScaleTransformer implements CustomDiscreteScrollItemTransformer {

    private Pivot pivotX;
    private Pivot pivotY;
    private float minScale;
    private float maxMinDiff;

    public CustomScaleTransformer() {
        pivotX = Pivot.X.CENTER.create();
        pivotY = Pivot.Y.CENTER.create();
        minScale = 0.8f;
        maxMinDiff = 0.2f;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void transformItem(View item, float position, int i, boolean isGoToRight) {
        pivotX.setOn(item);
        pivotY.setOn(item);
//        3 2 1 0 7 8 9, 0 là vị trí center
        if (position == 0){
//            trạng thái ban đầu khi chưa kéo
            if (i == 0){
                item.setScaleX(SCALE_X_CENTER);
                item.setScaleY(SCALE_Y_CENTER);
                item.setZ(SCALE_Y_CENTER);
//                scale 2 item bên cạnh item center
            } else if (i == 1|| i == 7){
                item.setScaleX(SCALE_X_SUB_1);
                item.setScaleY(SCALE_Y_SUB_1);
                item.setZ(SCALE_Y_SUB_1);
//                scale 2 item ngoài cùng
            } else if (i == 2 || i == 8){
                item.setScaleX(SCALE_X_SUB_2);
                item.setScaleY(SCALE_Y_SUB_2);
                item.setZ(SCALE_Y_SUB_2);
            } else {
//                các item còn lại scale theo item ngoài cùng
                item.setScaleX(SCALE_X_SUB_2);
                item.setScaleY(SCALE_Y_SUB_2);
                item.setZ(SCALE_Y_SUB_2);
            }
        }else if (position > 0 && position < 0.9){
            if (i == 0) {
                if (position <= 0.5){
                    if (SCALE_X_CENTER - position > SCALE_X_SUB_1) {
                        item.setScaleX(SCALE_X_CENTER - position);
                    }
                    if (SCALE_Y_CENTER - position > SCALE_Y_SUB_1){
                        item.setScaleY(SCALE_Y_CENTER - position);
                        item.setZ(SCALE_Y_CENTER - position);
                    }
                }
            }
            if (isGoToRight) {
//                scale sub 1 trái lên center
                if (i == 1) {
                    if (position <= 0.5){
                        if (SCALE_X_SUB_1 + position < SCALE_X_CENTER) {
                            item.setScaleX(SCALE_X_SUB_1 + position);
                        }
                        if (SCALE_Y_SUB_1 + position < SCALE_Y_CENTER) {
                            item.setScaleY(SCALE_Y_SUB_1 + position);
                            item.setZ(SCALE_Y_SUB_1 + position);
                        }
                    }
                }
//                scale sub 1 phải xuống sub 2
                else if (i == 7) {
                    if (position <= 0.5){
                        if (SCALE_X_SUB_1 - position > SCALE_X_SUB_2) {
                            item.setScaleX(SCALE_X_SUB_1 - position);
                        }
                        if (SCALE_Y_SUB_1 - position > SCALE_Y_SUB_2) {
                            item.setScaleY(SCALE_Y_SUB_1 - position);
                            item.setZ(SCALE_Y_SUB_1 - position);
                        }
                    }
                }
//                scale sub 2 trái lên sub 1
                else if (i == 2) {
                    if (position <= 0.5){
                        if (SCALE_X_SUB_2 + position < SCALE_X_SUB_1) {
                            item.setScaleX(SCALE_X_SUB_2 + position);
                        }
                        if (SCALE_Y_SUB_2 + position < SCALE_Y_SUB_1) {
                            item.setScaleY(SCALE_Y_SUB_2 + position);
                            item.setZ(SCALE_Y_SUB_2 + position);
                        }
                    }
                }
            }else {
//                kéo sang trái
//                scale sub 1 bên trái xuống sub 2
                if (i == 1){
                    if (position <= 0.5){
                        if (SCALE_X_SUB_1 -  position > SCALE_X_SUB_2) {
                            item.setScaleX(SCALE_X_SUB_1 -  position);
                        }
                        if (SCALE_Y_SUB_1 -  position > SCALE_Y_SUB_2){
                            item.setScaleY(SCALE_Y_SUB_1 -  position);
                            item.setZ(SCALE_Y_SUB_1 -  position);
                        }
                    }
                }
//                scale sub 1, phải, lên center
                else if (i == 7){
                    if (position <= 0.5){
                        if (SCALE_X_SUB_1 +  position < SCALE_X_CENTER){
                            item.setScaleX(SCALE_X_SUB_1 +  position);
                        }
                        if (SCALE_Y_SUB_1 +  position < SCALE_Y_CENTER){
                            item.setScaleY(SCALE_Y_SUB_1 +  position);
                            item.setZ(SCALE_Y_SUB_1 +  position);
                        }
                    }
                }
//                scale sub 2 trái lên sub 1
                else if (i == 8){
                    if (position <= 0.5){
                        if (SCALE_X_SUB_2 +  position < SCALE_X_SUB_1){
                            item.setScaleX(SCALE_X_SUB_2 +  position);
                        }
                        if (SCALE_Y_SUB_2 +  position < SCALE_Y_SUB_1){
                            item.setScaleY(SCALE_Y_SUB_2 +  position);
                            item.setZ(SCALE_Y_SUB_2 +  position);
                        }
                    }
                }
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void transformItemsLeft(View item, int i) {
        if (i == 0 || i == 7) {
            item.setScaleX(SCALE_X_SUB_1);
            item.setScaleY(SCALE_Y_SUB_1);
            item.setZ(SCALE_Y_SUB_1);
        }
        else if (i == 6){
            item.setScaleX(SCALE_X_CENTER);
            item.setScaleY(SCALE_Y_CENTER);
            item.setZ(SCALE_Y_CENTER);
        }
        else {
            item.setScaleX(SCALE_X_SUB_2);
            item.setScaleY(SCALE_Y_SUB_2);
            item.setZ(SCALE_Y_SUB_2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void transformItemsRight(View item, int i) {
        if (i == 1) {
            item.setScaleX(SCALE_X_CENTER);
            item.setScaleY(SCALE_Y_CENTER);
            item.setZ(SCALE_Y_CENTER);
        }
        else if (i == 2 || i == 0) {
            item.setScaleX(SCALE_X_SUB_1);
            item.setScaleY(SCALE_Y_SUB_1);
            item.setZ(SCALE_Y_SUB_1);
        }
        else {
            item.setScaleX(SCALE_X_SUB_2);
            item.setScaleY(SCALE_Y_SUB_2);
            item.setZ(SCALE_Y_SUB_2);
        }
    }

    public static class Builder {

        private CustomScaleTransformer transformer;
        private float maxScale;

        public Builder() {
            transformer = new CustomScaleTransformer();
            maxScale = 1f;
        }

        public CustomScaleTransformer.Builder setMinScale(@FloatRange(from = 0.01) float scale) {
            transformer.minScale = scale;
            return this;
        }

        public CustomScaleTransformer.Builder setMaxScale(@FloatRange(from = 0.01) float scale) {
            maxScale = scale;
            return this;
        }

        public CustomScaleTransformer.Builder setPivotX(Pivot.X pivotX) {
            return setPivotX(pivotX.create());
        }

        public CustomScaleTransformer.Builder setPivotX(Pivot pivot) {
            assertAxis(pivot, Pivot.AXIS_X);
            transformer.pivotX = pivot;
            return this;
        }

        public CustomScaleTransformer.Builder setPivotY(Pivot.Y pivotY) {
            return setPivotY(pivotY.create());
        }

        public CustomScaleTransformer.Builder setPivotY(Pivot pivot) {
            assertAxis(pivot, Pivot.AXIS_Y);
            transformer.pivotY = pivot;
            return this;
        }

        public CustomScaleTransformer build() {
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
