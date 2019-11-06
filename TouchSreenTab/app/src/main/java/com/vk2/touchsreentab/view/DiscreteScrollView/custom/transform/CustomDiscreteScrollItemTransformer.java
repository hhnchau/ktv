package com.vk2.touchsreentab.view.DiscreteScrollView.custom.transform;

import android.view.View;

public interface CustomDiscreteScrollItemTransformer {
    float SCALE_X_CENTER = 1.3f;
    float SCALE_Y_CENTER = 1f;
    float SCALE_X_SUB_1 = 1.1f;
    float SCALE_Y_SUB_1 = 0.7f;
    float SCALE_X_SUB_2 = 1f;
    float SCALE_Y_SUB_2 = 0.5f;
    void transformItem(View item, float position, int i, boolean isGoToRight);
    void transformItemsLeft(View item, int i);
    void transformItemsRight(View item, int i);
}
