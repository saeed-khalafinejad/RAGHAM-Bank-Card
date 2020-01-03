package org.kasabeh.raghamcardbank.ui;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Intent;
import android.os.Bundle;

import org.kasabeh.raghamcardbank.logical.Bank;

/**
 * Created by Saeed on 06/14/2018.
 */
public class ActBanksSelection extends ActBanks {

    public static final String BANK_ID = "ActBanksSelection.BANK_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setResult(RESULT_CANCELED);
    }

    @Override
    protected void onRowSingleTouch(int pos) {
        Bank b = (Bank) adap.getItemAt(pos);
        Intent i = new Intent();
        i.putExtra(BANK_ID, b.get_id());
        setResult(RESULT_OK, i);
        finish();
    }

}
