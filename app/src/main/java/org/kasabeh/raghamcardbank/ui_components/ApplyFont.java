package org.kasabeh.raghamcardbank.ui_components;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by Saeed on 12/03/2016.
 */
class ApplyFont {

    private static Typeface tf = null;

    static void applyFont(Context context, TextView component){
        tf = getFont(context);
        component.setTypeface(tf);
    }

    private static Typeface getFont(Context context){
        if (tf==null)
            tf = Typeface.createFromAsset(context.getAssets(), "fonts/BYekan.ttf");
        return tf;
    }


}
