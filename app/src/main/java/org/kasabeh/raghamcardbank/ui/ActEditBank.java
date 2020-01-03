package org.kasabeh.raghamcardbank.ui;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.BankDB;
import org.kasabeh.raghamcardbank.db.DBConn;
import org.kasabeh.raghamcardbank.logical.Bank;
import org.kasabeh.raghamcardbank.utils.MessDlg;
import org.kasabeh.raghamcardbank.utils.RaghamException;
import org.kasabeh.raghamcardbank.utils.StrPross;

/**
 * Created by Saeed on 06/14/2018.
 */
public class ActEditBank extends ActNewBank {

    public static final String BANK_ID = "ActEditBank.BANK_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int bankId = getIntent().getIntExtra(BANK_ID, -1);
        try {
            Bank b = new BankDB(this).loadObject(bankId);
            EditText edt = findViewById(R.id.edtBankName);
            edt.setText(b.getBankName());
            logoFileName = b.getBankLogoFileName();
            loadLogo();
        } catch (Exception e) {
            MessDlg.simpleMess(this, StrPross.readableErr(e, this));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.mnu_save){
            DBConn dbConn = DBConn.createConn(this);
            try {
                int bankId = getIntent().getIntExtra(BANK_ID, -1);
                EditText edt = findViewById(R.id.edtBankName);
                String bankName = edt.getText().toString();
                Bank b = new Bank(this, bankId, bankName, logoFileName);
                if (b.exists(dbConn))
                    throw new RaghamException(getString(R.string.bankRepeated));
                dbConn.getWritableDatabase().beginTransaction();
                try {
                    b.edit(dbConn);
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
}
