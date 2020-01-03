package org.kasabeh.raghamcardbank.ui;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.DBConn;
import org.kasabeh.raghamcardbank.logical.Person;
import org.kasabeh.raghamcardbank.utils.IDataSetChanger;
import org.kasabeh.raghamcardbank.utils.MessDlg;
import org.kasabeh.raghamcardbank.utils.RaghamException;
import org.kasabeh.raghamcardbank.utils.StrPross;
import org.kasabeh.raghamcardbank.utils.UIRefresher;

/**
 * Created by Saeed on 06/09/2018.
 */
public class ActNewPerson extends ActFormHlp implements IDataSetChanger{

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.mnu_save){
            DBConn dbConn = DBConn.createConn(this);
            try {
                EditText edt = findViewById(R.id.edtPersonName);
                String personName = edt.getText().toString();
                Person p = new Person(this, personName);
                if (p.exists(dbConn))
                    throw new RaghamException(getString(R.string.personRepeated));
                dbConn.getWritableDatabase().beginTransaction();
                try {
                    p.save(dbConn);
                    dbConn.getWritableDatabase().setTransactionSuccessful();
                } finally {
                    dbConn.getWritableDatabase().endTransaction();
                }
                finish();
                notifyActivities();
                Toast.makeText(this, R.string.personSaved, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                MessDlg.simpleMess(this, StrPross.readableErr(e, this));
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_act_new_person);
    }

    @Override
    public void notifyActivities() {
        UIRefresher.ActPersonsRefresh = true;
    }
}
