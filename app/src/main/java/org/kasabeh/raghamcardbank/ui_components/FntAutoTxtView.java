package org.kasabeh.raghamcardbank.ui_components;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Context;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;

/**
 * Created by Saeed on 01/28/2017.
 */
public class FntAutoTxtView extends AppCompatAutoCompleteTextView {

    public FntAutoTxtView(Context context) {
        super(context);
        ApplyFont.applyFont(context, this);
    }

    public FntAutoTxtView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ApplyFont.applyFont(context, this);
    }

    public FntAutoTxtView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ApplyFont.applyFont(context, this);
    }
}
