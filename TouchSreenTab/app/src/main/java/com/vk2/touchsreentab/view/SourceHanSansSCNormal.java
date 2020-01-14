package com.vk2.touchsreentab.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.vk2.touchsreentab.utils.FontUtils;

public class SourceHanSansSCNormal extends AppCompatTextView {

    public SourceHanSansSCNormal(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SourceHanSansSCNormal(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public SourceHanSansSCNormal(Context context) {
        super(context);
    }


    @Override
    public void setTypeface(Typeface tf) {
        //String fontName = "SourceHanSansSC-Normal.otf";
        String fontName = "OpenSans-Regular.ttf";
        super.setTypeface(FontUtils.getFont(getContext(), fontName));
    }

}