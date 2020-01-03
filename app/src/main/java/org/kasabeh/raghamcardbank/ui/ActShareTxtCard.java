package org.kasabeh.raghamcardbank.ui;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.logical.CardNO;

/**
 * Created by Saeed on 06/27/2018.
 */
public class ActShareTxtCard extends ActShareTxt implements View.OnClickListener{


    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_act_share_txt_card);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_act_share, menu);
        return true;
    }

    @Override
    protected void setCheckBoxLabels() {
        CheckBox chk = findViewById(R.id.chkCardNO);
        chk.setText(getString(R.string.cardNO));
    }

    protected void setOnClicks() {
        findViewById(R.id.chkCardNO).setOnClickListener(this);
        findViewById(R.id.chkAccountOwner).setOnClickListener(this);
        findViewById(R.id.chkBank).setOnClickListener(this);
        findViewById(R.id.chkCVV).setOnClickListener(this);
        findViewById(R.id.chkExpire).setOnClickListener(this);
        findViewById(R.id.chkDesc).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.chkCardNO||v.getId()==R.id.chkAccountOwner||
                v.getId()==R.id.chkBank||v.getId()==R.id.chkCVV||
                v.getId()==R.id.chkExpire||v.getId()==R.id.chkDesc)
            generateContent();
    }

    @Override
    protected void generateContent() {
        CardNO nm = (CardNO) getNumberMng();
        if (nm==null) return;
        String smsTxt = "";
        smsTxt += genTxt(R.id.chkCardNO, R.string.cardNO, nm.getNum());
        smsTxt += genTxt(R.id.chkBank, R.string.bankName, nm.getBank().getBankName());
        smsTxt += genTxt(R.id.chkAccountOwner, R.string.ownerName, nm.getPerson().getFullName());
        smsTxt += genTxt(R.id.chkCVV, R.string.CVV, nm.getCVV());
        smsTxt += genTxt(R.id.chkExpire, R.string.expirationDate, nm.getExpirationDate());
        smsTxt += genTxt(R.id.chkDesc, R.string.desc, nm.getDesc());

        if (smsTxt.length()>0) smsTxt = smsTxt.substring(0, smsTxt.length()-1);
        EditText edt = findViewById(R.id.edtSmsTxt);
        edt.setText(smsTxt);
    }

}
