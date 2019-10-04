package com.vk2.touchsreentab.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.vk2.touchsreentab.utils.FontUtils;

public class ArialRegular extends android.support.v7.widget.AppCompatTextView {

    public ArialRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ArialRegular(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public ArialRegular(Context context) {
        super(context);
    }


    @Override
    public void setTypeface(Typeface tf) {
        String fontName = "Arial-Regular.ttf";
        super.setTypeface(FontUtils.getFont(getContext(), fontName));
    }

}