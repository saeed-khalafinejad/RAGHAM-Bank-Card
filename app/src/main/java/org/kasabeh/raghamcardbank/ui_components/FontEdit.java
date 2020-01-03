package org.kasabeh.raghamcardbank.ui_components;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by Saeed on 12/03/2016.
 */
public class FontEdit extends AppCompatEditText {
    public FontEdit(Context context) {
        super(context);
        ApplyFont.applyFont(context, this);
    }

    public FontEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        ApplyFont.applyFont(context, this);
    }

    public FontEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ApplyFont.applyFont(context, this);
    }
}
