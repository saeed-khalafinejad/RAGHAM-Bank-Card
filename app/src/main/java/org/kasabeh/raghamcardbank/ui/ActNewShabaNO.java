package org.kasabeh.raghamcardbank.ui;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.widget.EditText;
import android.widget.Toast;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.logical.NumberManager;
import org.kasabeh.raghamcardbank.logical.ShabaNO;

/**
 * Created by Saeed on 06/17/2018.
 */
public class ActNewShabaNO extends ActNewNO {

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_act_new_shaba_no);
    }


    @Override
    protected void showSuccMess() {
        Toast.makeText(this, R.string.ShabaNOSaved, Toast.LENGTH_LONG).show();
    }

    @Override
    protected NumberManager getNumberManager() throws Exception {
        return new ShabaNO(this, getPersonObj(), bank, getNum(), getDesc());
    }

    @Override
    protected String getNum() {
        EditText edt = findViewById(R.id.edtAccountNO);
        return "IR"+edt.getText().toString();

    }
}
