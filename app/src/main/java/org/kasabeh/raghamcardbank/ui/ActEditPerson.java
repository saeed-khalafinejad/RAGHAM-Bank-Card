package org.kasabeh.raghamcardbank.ui;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.DBConn;
import org.kasabeh.raghamcardbank.db.PersonDB;
import org.kasabeh.raghamcardbank.logical.Person;
import org.kasabeh.raghamcardbank.utils.MessDlg;
import org.kasabeh.raghamcardbank.utils.RaghamException;
import org.kasabeh.raghamcardbank.utils.StrPross;

/**
 * Created by Saeed on 06/11/2018.
 */
public class ActEditPerson extends ActNewPerson {

    public static final String PERSON_ID = "ActEditPerson.PERSON_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int personId = getIntent().getIntExtra(PERSON_ID, -1);
        try {
            Person p = new PersonDB(this).loadObject(personId);
            EditText edt = findViewById(R.id.edtPersonName);
            edt.setText(p.getFullName());
        } catch (Exception e) {
            MessDlg.simpleMess(this, StrPross.readableErr(e, this));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.mnu_save){
            DBConn dbConn = DBConn.createConn(this);
            try {
                EditText edt = findViewById(R.id.edtPersonName);
                String personName = edt.getText().toString();
                int personId = getIntent().getIntExtra(PERSON_ID, -1);
                Person p = new Person(this, personId, personName);
                if (p.exists(dbConn))
                    throw new RaghamException(getString(R.string.personRepeated));
                dbConn.getWritableDatabase().beginTransaction();
                try {
                    p.edit(dbConn);
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

}
