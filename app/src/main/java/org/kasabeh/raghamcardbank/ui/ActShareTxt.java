package org.kasabeh.raghamcardbank.ui;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.NOsView;
import org.kasabeh.raghamcardbank.logical.NumberManager;
import org.kasabeh.raghamcardbank.utils.MessDlg;
import org.kasabeh.raghamcardbank.utils.StrPross;

/**
 * Created by Saeed on 06/28/2018.
 */
public class ActShareTxt extends ActFormHlp implements View.OnClickListener{

    public static final String KIND = "ActShareTxtCard.KIND";
    public static final String ID = "ActShareTxtCard.ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setOnClicks();
        setCheckBoxLabels();
    }

    protected void setCheckBoxLabels() {
        CheckBox chk = findViewById(R.id.chkCardNO);
        chk.setText(getString(R.string.accountNO));
    }

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_act_share_txt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_act_share, menu);
        return true;
    }

    protected void setOnClicks() {
        findViewById(R.id.chkCardNO).setOnClickListener(this);
        findViewById(R.id.chkAccountOwner).setOnClickListener(this);
        findViewById(R.id.chkBank).setOnClickListener(this);
        findViewById(R.id.chkDesc).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.chkCardNO||v.getId()==R.id.chkAccountOwner||
                v.getId()==R.id.chkBank||v.getId()==R.id.chkDesc)
            generateContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        generateContent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.mnu_share_txt){
            shareContent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void generateContent() {
        NumberManager nm = getNumberMng();
        if (nm==null) return;
        String smsTxt = "";
        smsTxt += genTxt(R.id.chkCardNO, getNOLabel(), nm.getNum());
        smsTxt += genTxt(R.id.chkBank, R.string.bankName, nm.getBank().getBankName());
        smsTxt += genTxt(R.id.chkAccountOwner, R.string.ownerName, nm.getPerson().getFullName());
        smsTxt += genTxt(R.id.chkDesc, R.string.desc, nm.getDesc());

        if (smsTxt.length()>0) smsTxt = smsTxt.substring(0, smsTxt.length()-1);
        EditText edt = findViewById(R.id.edtSmsTxt);
        edt.setText(smsTxt);
    }

    protected int getNOLabel() {
        return R.string.accountNO;
    }

    protected NumberManager getNumberMng() {
        int _id = getIntent().getIntExtra(ID, -1);
        int kind = getIntent().getIntExtra(KIND, -1);
        try {
            return  new NOsView(this).loadObject(_id, kind);
        } catch (Exception e) {
            MessDlg.simpleMess(this, StrPross.readableErr(e, this));
            return null;
        }
    }

    protected void shareContent() {
        EditText edt = findViewById(R.id.edtSmsTxt);
        String smsTxt = edt.getText().toString();
        if (smsTxt.length()==0){
            MessDlg.simpleMess(this, getString(R.string.empty_sms));
            return;
        }

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, smsTxt);
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)));
    }

    protected String genTxt(int chkId, int keyStr,
                          String valStr) {
        CheckBox chk = findViewById(chkId);
        if (chk.isChecked())
            return getString(keyStr)+": "+valStr+"\n";
        return "";
    }
}
