package org.kasabeh.raghamcardbank.ui_components;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import org.kasabeh.raghamcardbank.R;


/**
 * Created by Saeed on 12/04/2016.
 */
public class FntTxtView extends AppCompatTextView {

    public FntTxtView(Context context) {
        super(context);
        ApplyFont.applyFont(context, this);
        setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryText));
    }

    public FntTxtView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ApplyFont.applyFont(context, this);
        setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryText));
    }

    public FntTxtView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ApplyFont.applyFont(context, this);
        setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryText));
    }


}
