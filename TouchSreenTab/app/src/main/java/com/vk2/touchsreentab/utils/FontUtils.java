package com.vk2.touchsreentab.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

public class FontUtils {
    private static final Hashtable<String, Typeface> cache = new Hashtable<>();

    public static Typeface getFont(Context context, String fontName) {
        synchronized (cache) {
            if (!cache.containsKey(fontName)) {
                try {
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName);
                    cache.put(fontName, typeface);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return cache.get(fontName);
    }
}
