package org.kasabeh.raghamcardbank.ui;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.BankDB;
import org.kasabeh.raghamcardbank.logical.Bank;
import org.kasabeh.raghamcardbank.utils.MessDlg;
import org.kasabeh.raghamcardbank.utils.RaghamConst;
import org.kasabeh.raghamcardbank.utils.StrPross;
import org.kasabeh.raghamcardbank.utils.UIRefresher;

/**
 * Created by Saeed on 06/12/2018.
 */
public class ActBanks extends ActSearchHlp {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_act_banks, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.mnu_new){
            Intent i = new Intent(this, ActNewBank.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void cancelRefresh() {
        UIRefresher.ActBanksRefresh = false;
    }

    @Override
    protected boolean refreshNeeded() {
        return UIRefresher.ActBanksRefresh;
    }

    @Override
    protected void onRowSingleTouch(int pos) {
        editBank(pos);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        adap = new AdapBanks(this, getCursor(getEdtSearchValue()));
        return adap;
    }

    @Override
    protected Cursor getCursor(String filter) {
        if (filter.length()==0) return new BankDB(this).readAllRecords(dbConn, "bankName");
        return new BankDB(this).readRecords(dbConn, "bankName like " + StrPross.Qoute("%"+filter+"%"), "bankName");
    }

    @Override
    public void choiceAction(int theOperation, int selected, int pos) {
        if (theOperation== RaghamConst.DEL_EDIT_OPERATION){
            if (selected==RaghamConst.EDIT_IX){
                editBank(pos);
            } else if (selected==RaghamConst.DEL_IX){
                MessDlg.yesNoDlg(this, RaghamConst.DELETE_OPERATION, pos, this, R.string.sureToDelBank);
            }
        }
    }

    private void editBank(int pos) {
        Bank b = (Bank) adap.getItemAt(pos);
        Intent i = new Intent(this, ActEditBank.class);
        i.putExtra(ActEditBank.BANK_ID, b.get_id());
        startActivity(i);
    }

    @Override
    public void yesAction(int theOperation, int pos) {
        if (theOperation==RaghamConst.DELETE_OPERATION){
            Bank b = (Bank) adap.getItemAt(pos);
            try {
                b.remove(dbConn);
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
