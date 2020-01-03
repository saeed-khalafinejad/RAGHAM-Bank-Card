package org.kasabeh.raghamcardbank.ui;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.NOsView;
import org.kasabeh.raghamcardbank.logical.AccountNO;
import org.kasabeh.raghamcardbank.logical.NumberManager;
import org.kasabeh.raghamcardbank.utils.MessDlg;
import org.kasabeh.raghamcardbank.utils.StrPross;

/**
 * Created by Saeed on 06/25/2018.
 */
public class ActEditAccountNO extends ActNewAccountNO {

    public static String KIND = "ActEditNOs.KIND";
    public static String ID = "ActEditNOs.ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int _id = getIntent().getIntExtra(ID, -1);
        int kind = getIntent().getIntExtra(KIND, -1);
        try {
            AccountNO an = (AccountNO) new NOsView(this).loadObject(_id, kind);
            bank = an.getBank();
            loadBank();
            EditText edt = findViewById(R.id.edtPersonName);
            edt.setText(an.getPerson().getFullName());
            edt = findViewById(R.id.edtAccountNO);
            edt.setText(an.getNum());
            edt = findViewById(R.id.edtDesc);
            edt.setText(an.getDesc());
        } catch (Exception e) {
            MessDlg.simpleMess(this, StrPross.readableErr(e, this));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.mnu_save){
            try {
                dbConn.getWritableDatabase().beginTransaction();
                try {
                    NumberManager a = getNumberManager();
                    a.edit(dbConn);
                    dbConn.getWritableDatabase().setTransactionSuccessful();
                } finally {
                    dbConn.getWritableDatabase().endTransaction();
                }
                finish();
                notifyActivities();
                showSuccMess();
            } catch (Exception e) {
                MessDlg.simpleMess(this, StrPross.readableErr(e, this));
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected NumberManager getNumberManager() throws Exception {
        int _id = getIntent().getIntExtra(ID, -1);
        return new AccountNO(this, _id, getPersonObj(), bank, getNum(), getDesc());
    }

}
