package com.vk2.touchsreentab.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.vk2.touchsreentab.utils.FontUtils;

public class SourceHanSansSCLight extends android.support.v7.widget.AppCompatTextView {

    public SourceHanSansSCLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SourceHanSansSCLight(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public SourceHanSansSCLight(Context context) {
        super(context);
    }


    @Override
    public void setTypeface(Typeface tf) {
        //String fontName = "SourceHanSansSC-Light.otf";
        String fontName = "OpenSans-Light.ttf";
        super.setTypeface(FontUtils.getFont(getContext(), fontName));
    }

}