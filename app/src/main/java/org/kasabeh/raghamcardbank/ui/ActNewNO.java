package org.kasabeh.raghamcardbank.ui;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.BankDB;
import org.kasabeh.raghamcardbank.db.DBConn;
import org.kasabeh.raghamcardbank.db.PersonDB;
import org.kasabeh.raghamcardbank.logical.Bank;
import org.kasabeh.raghamcardbank.logical.NumberManager;
import org.kasabeh.raghamcardbank.logical.Person;
import org.kasabeh.raghamcardbank.utils.IDataSetChanger;
import org.kasabeh.raghamcardbank.utils.MessDlg;
import org.kasabeh.raghamcardbank.utils.StrPross;
import org.kasabeh.raghamcardbank.utils.UIRefresher;

/**
 * Created by Saeed on 06/17/2018.
 */
public abstract class ActNewNO extends ActFormHlp implements View.OnClickListener, IDataSetChanger {

    private static final int REQ_BANK_ID = 100;
    private static final String BANK_ID = "ActNewNO.BANK_ID";

    protected DBConn dbConn;
    protected Bank bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbConn = DBConn.createConn(this);
        Cursor c = new PersonDB(this).readAllRecords(dbConn, "fullName");
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.select_dialog_item,
                c,
                new String[]{"fullName"},
                new int[]{android.R.id.text1});

        adapter.setCursorToStringConverter(
                new SimpleCursorAdapter.CursorToStringConverter() {

                    @Override
                    public CharSequence convertToString(Cursor c) {
                        return c.getString(c.getColumnIndex("fullName"));
                    }
                });
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence str) {
                return new PersonDB(ActNewNO.this).readRecords(dbConn,
                        "fullName like " + StrPross.Qoute("%" + str + "%"), "fullName");
            }
        });
        AutoCompleteTextView txtAu = findViewById(R.id.edtPersonName);
        txtAu.setAdapter(adapter);

        //findViewById(R.id.edtBankName).setOnClickListener(this);
        findViewById(R.id.edtBankName).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent i = new Intent(ActNewNO.this, ActBanksSelection.class);
                    startActivityForResult(i, REQ_BANK_ID);
                }
                return false;
            }
        });
        findViewById(R.id.imgBankLogo).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        dbConn = DBConn.createConn(this);
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (bank!=null) outState.putInt(BANK_ID, bank.get_id());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(BANK_ID)){
            int bankId = savedInstanceState.getInt(BANK_ID);
            try {
                bank = new BankDB(this).loadObject(bankId);
                loadBank();
            } catch (Exception e) {
                MessDlg.simpleMess(this, StrPross.readableErr(e, this));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.mnu_save){
            try {
                dbConn.getWritableDatabase().beginTransaction();
                try {
                    NumberManager a = getNumberManager();
                    a.save(dbConn);
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

    protected abstract void showSuccMess();

    protected abstract NumberManager getNumberManager() throws Exception;


    protected Person getPersonObj() throws Exception{
        AutoCompleteTextView txtAu = findViewById(R.id.edtPersonName);
        String personName = txtAu.getText().toString();
        Person person = new Person(this, personName);
        if (!person.exists(dbConn)){
            person.save(dbConn);
        }
        PersonDB personDB = new PersonDB(this);
        return personDB.loadObject(personName, dbConn);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.edtBankName||v.getId()==R.id.imgBankLogo){
            Intent i = new Intent(this, ActBanksSelection.class);
            startActivityForResult(i, REQ_BANK_ID);
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQ_BANK_ID&&resultCode==RESULT_OK){
            int bankId = data.getIntExtra(ActBanksSelection.BANK_ID, -1);
            try {
                bank = new BankDB(this).loadObject(bankId);
                loadBank();
            } catch (Exception e) {
                MessDlg.simpleMess(this, StrPross.readableErr(e, this));
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void loadBank() {
        EditText edt = findViewById(R.id.edtBankName);
        edt.setText(bank.getBankName());
        ImageView img = findViewById(R.id.imgBankLogo);
        img.setImageDrawable(bank.getBankLogo());
    }

    protected String getNum() {
        EditText edt = findViewById(R.id.edtAccountNO);
        return edt.getText().toString();
    }

    protected String getDesc() {
        EditText edt = findViewById(R.id.edtDesc);
        return edt.getText().toString();
    }

    @Override
    public void notifyActivities() {
        UIRefresher.ActMainRefresh = true;
    }


}
