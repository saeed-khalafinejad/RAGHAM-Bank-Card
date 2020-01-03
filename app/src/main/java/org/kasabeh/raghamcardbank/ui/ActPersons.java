package org.kasabeh.raghamcardbank.ui;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.PersonDB;
import org.kasabeh.raghamcardbank.logical.Person;
import org.kasabeh.raghamcardbank.utils.MessDlg;
import org.kasabeh.raghamcardbank.utils.RaghamConst;
import org.kasabeh.raghamcardbank.utils.StrPross;
import org.kasabeh.raghamcardbank.utils.UIRefresher;

/**
 * Created by Saeed on 06/10/2018.
 */
public class ActPersons extends ActSearchHlp{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void cancelRefresh() {
        UIRefresher.ActPersonsRefresh = false;
    }

    @Override
    protected boolean refreshNeeded() {
        return UIRefresher.ActPersonsRefresh;
    }

    @Override
    protected void onRowSingleTouch(int pos) {
        editPerson(pos);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        adap = new AdapPersons(this, getCursor(getEdtSearchValue()));
        return adap;
    }

    private void editPerson(int pos) {
        Person p = (Person) adap.getItemAt(pos);
        Intent i = new Intent(ActPersons.this, ActEditPerson.class);
        i.putExtra(ActEditPerson.PERSON_ID, p.get_id());
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_act_persons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.mnu_new){
            Intent i = new Intent(this, ActNewPerson.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Cursor getCursor(String filter) {
        PersonDB personDB = new PersonDB(this);
        if (filter.length()==0) return personDB.readAllRecords(dbConn, "fullName");
        return personDB.readRecords(dbConn, "fullName like "+StrPross.Qoute("%"+filter+"%"), "fullName");
    }

    @Override
    public void choiceAction(int theOperation, int selected, int pos) {
        if (theOperation== RaghamConst.DEL_EDIT_OPERATION){
            if (selected==RaghamConst.EDIT_IX){
                editPerson(pos);
            } else if (selected==RaghamConst.DEL_IX){
                MessDlg.yesNoDlg(this, RaghamConst.DELETE_OPERATION, pos, this, R.string.sureToDelPerson);
            }
        }
    }

    @Override
    public void yesAction(int theOperation, int pos) {
        if (theOperation==RaghamConst.DELETE_OPERATION){
            Person p = (Person) adap.getItemAt(pos);
            try {
                p.remove(dbConn);
                refreshDataSet();
            } catch (Exception e) {
                MessDlg.simpleMess(this, StrPross.readableErr(e, this));
            }
        }

    }

    @Override
    public void noAction(int theOperation, int pos) {
    }
}
