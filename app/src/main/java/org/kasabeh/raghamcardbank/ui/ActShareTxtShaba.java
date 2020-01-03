package org.kasabeh.raghamcardbank.ui;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.widget.CheckBox;

import org.kasabeh.raghamcardbank.R;

/**
 * Created by Saeed on 06/28/2018.
 */
public class ActShareTxtShaba extends ActShareTxt{

    @Override
    protected void setCheckBoxLabels() {
        CheckBox chk = findViewById(R.id.chkCardNO);
        chk.setText(getString(R.string.accountNO));
    }

    @Override
    protected int getNOLabel() {
        return R.string.shabaNO;
    }

}
