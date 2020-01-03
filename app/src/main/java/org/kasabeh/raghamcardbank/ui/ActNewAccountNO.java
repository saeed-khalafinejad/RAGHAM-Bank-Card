package org.kasabeh.raghamcardbank.ui;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.widget.Toast;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.logical.AccountNO;
import org.kasabeh.raghamcardbank.logical.NumberManager;

/**
 * Created by Saeed on 06/14/2018.
 */
public class ActNewAccountNO extends ActNewNO{

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_act_new_account_no);
    }

    @Override
    protected void showSuccMess() {
        Toast.makeText(this, R.string.accountNOSaved, Toast.LENGTH_LONG).show();
    }

    @Override
    protected NumberManager getNumberManager() throws Exception {
        return new AccountNO(this, getPersonObj(), bank, getNum(), getDesc());
    }

}
