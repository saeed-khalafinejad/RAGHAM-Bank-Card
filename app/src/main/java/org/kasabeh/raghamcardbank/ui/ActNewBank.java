package org.kasabeh.raghamcardbank.ui;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.DBConn;
import org.kasabeh.raghamcardbank.logical.Bank;
import org.kasabeh.raghamcardbank.utils.IDataSetChanger;
import org.kasabeh.raghamcardbank.utils.MessDlg;
import org.kasabeh.raghamcardbank.utils.RaghamConst;
import org.kasabeh.raghamcardbank.utils.RaghamException;
import org.kasabeh.raghamcardbank.utils.StrPross;
import org.kasabeh.raghamcardbank.utils.Tools;
import org.kasabeh.raghamcardbank.utils.UIRefresher;

/**
 * Created by Saeed on 06/13/2018.
 */
public class ActNewBank extends ActFormHlp implements IDataSetChanger, View.OnClickListener{

    private static final int REQ_LOGO = 100;
    private static final String LOGO_FILE_NAME = "ActNewBank.LOGO_FILE_NAME";

    protected String logoFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        logoFileName = RaghamConst.BNK_DEF;
        findViewById(R.id.imgBankLogo).setOnClickListener(this);
        findViewById(R.id.txtSelLogo).setOnClickListener(this);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState!=null){
            logoFileName = savedInstanceState.getString(LOGO_FILE_NAME, RaghamConst.BNK_DEF);
            loadLogo();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(LOGO_FILE_NAME, logoFileName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.mnu_save){
            DBConn dbConn = DBConn.createConn(this);
            try {
                EditText edt = findViewById(R.id.edtBankName);
                String bankName = edt.getText().toString();
                Bank b = new Bank(this, bankName, logoFileName);
                if (b.exists(dbConn))
                    throw new RaghamException(getString(R.string.bankRepeated));
                dbConn.getWritableDatabase().beginTransaction();
                try {
                    b.save(dbConn);
                    dbConn.getWritableDatabase().setTransactionSuccessful();
                } finally {
                    dbConn.getWritableDatabase().endTransaction();
                }
                finish();
                notifyActivities();
                Toast.makeText(this, R.string.bankSaved, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                MessDlg.simpleMess(this, StrPross.readableErr(e, this));
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_act_new_bank);
    }


    @Override
    public void notifyActivities() {
        UIRefresher.ActBanksRefresh = true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.imgBankLogo||v.getId()==R.id.txtSelLogo){
            Intent i = new Intent(this, ActBankLogos.class);
            startActivityForResult(i, REQ_LOGO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQ_LOGO&&resultCode==RESULT_OK){
            logoFileName = data.getStringExtra(ActBankLogos.SEL_LOGO_NAME);
            loadLogo();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void loadLogo() {
        ImageView img = findViewById(R.id.imgBankLogo);
        img.setImageDrawable(Tools.getDrawableFromName(this, logoFileName));
        findViewById(R.id.txtSelLogo).setVisibility(View.GONE);
    }
}
