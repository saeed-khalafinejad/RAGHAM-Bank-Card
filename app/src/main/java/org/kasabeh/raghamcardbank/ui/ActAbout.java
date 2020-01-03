package org.kasabeh.raghamcardbank.ui;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.utils.Tools;

public class ActAbout extends ActFormHlp {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String versionName = Tools.getVersionName(this);
        TextView txt = findViewById(R.id.txtVer);
        txt.setText(getString(R.string.version) + " " + versionName);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_act_about);
    }
}
