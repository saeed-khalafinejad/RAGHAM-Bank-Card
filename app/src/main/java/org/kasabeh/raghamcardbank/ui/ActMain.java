package org.kasabeh.raghamcardbank.ui;
/*
Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

You may contact-us via info@kasabeh.org.
*/

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.backup.ActRestore;
import org.kasabeh.raghamcardbank.backup.ActSave;
import org.kasabeh.raghamcardbank.backup.ActSaveSend;
import org.kasabeh.raghamcardbank.db.NOsView;
import org.kasabeh.raghamcardbank.db.ViewAdap;
import org.kasabeh.raghamcardbank.logical.NumberManager;
import org.kasabeh.raghamcardbank.utils.DirectoryMng;
import org.kasabeh.raghamcardbank.utils.MessDlg;
import org.kasabeh.raghamcardbank.utils.RaghamConst;
import org.kasabeh.raghamcardbank.utils.StrPross;
import org.kasabeh.raghamcardbank.utils.UIRefresher;

public class ActMain extends ActSearchHlp {

    private boolean fab_opened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar br = getSupportActionBar();
        if (br!=null){
            br.setDisplayHomeAsUpEnabled(true);
            br.setHomeAsUpIndicator(R.drawable.exit);
        }

        fab_opened = false;
        FloatingActionButton fab_account = findViewById(R.id.fab_new_account_no);
        fab_account.setOnClickListener(this);
        FloatingActionButton fab_shaba = findViewById(R.id.fab_new_shaba_no);
        fab_shaba.setOnClickListener(this);
        FloatingActionButton fab_card = findViewById(R.id.fab_new_card_no);
        fab_card.setOnClickListener(this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.imgCloseSearch){
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setAnimation(AnimationUtils.makeInChildBottomAnimation(this));
            fab.show();
        }

        if (v.getId()==R.id.fab){
            if (!fab_opened) {
                showFabs(v);
            } else {
                hideFabs();
            }
            return;
        }

        if (v.getId()==R.id.fab_new_account_no){
            hideFabs();
            Intent i = new Intent(ActMain.this, ActNewAccountNO.class);
            startActivity(i);
            return;
        }

        if (v.getId()==R.id.fab_new_shaba_no){
            hideFabs();
            Intent i = new Intent(ActMain.this, ActNewShabaNO.class);
            startActivity(i);
            return;
        }

        if (v.getId()==R.id.fab_new_card_no){
            hideFabs();
            Intent i = new Intent(ActMain.this, ActNewCardNO.class);
            startActivity(i);
            return;
        }

        super.onClick(v);
    }

    private void showFabs(View v) {
        if (fab_opened) return;
        FloatingActionButton fab = (FloatingActionButton) v;
        fab.setImageDrawable(ContextCompat.getDrawable(ActMain.this, R.drawable.close));
        fab_opened = true;

        View lay = findViewById(R.id.lay_account_no);
        lay.setAnimation(AnimationUtils.makeInChildBottomAnimation(ActMain.this));
        lay.setVisibility(View.VISIBLE);

        lay = findViewById(R.id.lay_shaba_no);
        lay.setAnimation(AnimationUtils.makeInChildBottomAnimation(ActMain.this));
        lay.setVisibility(View.VISIBLE);

        lay = findViewById(R.id.lay_card_no);
        lay.setAnimation(AnimationUtils.makeInChildBottomAnimation(ActMain.this));
        lay.setVisibility(View.VISIBLE);
    }

    private void hideFabs() {
        if (!fab_opened) return;
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(ActMain.this, R.drawable.fab_add));
        fab_opened = false;

        View lay = findViewById(R.id.lay_account_no);
        lay.setAnimation(AnimationUtils.makeOutAnimation(ActMain.this, true));
        lay.setVisibility(View.GONE);

        lay = findViewById(R.id.lay_shaba_no);
        lay.setAnimation(AnimationUtils.makeOutAnimation(ActMain.this, true));
        lay.setVisibility(View.GONE);

        lay = findViewById(R.id.lay_card_no);
        lay.setAnimation(AnimationUtils.makeOutAnimation(ActMain.this, true));
        lay.setVisibility(View.GONE);
    }

    @Override
    protected void cancelRefresh() {
        UIRefresher.ActMainRefresh = false;
    }

    @Override
    protected boolean refreshNeeded() {
        return UIRefresher.ActMainRefresh = true;
    }

    @Override
    protected void onRowSingleTouch(int pos) {

    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        adap = new AdapNOs(this, getCursor(getEdtSearchValue()));
        return adap;
    }

    @Override
    protected Cursor getCursor(String filter) {
        ViewAdap view = new NOsView(this);
        if (filter.length()==0)
            return view.readAllRecords(dbConn, "fullName, bankName");
        else
            return view.readRecords(dbConn, "fullName like "+StrPross.Qoute("%"+filter+"%")+" or "+
                    "desc like "+StrPross.Qoute("%"+filter+"%"));
    }

    @Override
    protected void setLayoutAndInit() {
        setContentView(R.layout.activity_act_main);
        DirectoryMng.createDirectories();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_act_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnu_bank_management) {
            Intent i = new Intent(this, ActBanks.class);
            startActivity(i);
            return true;
        }

        if (item.getItemId() == R.id.mnu_person_management) {
            Intent i = new Intent(this, ActPersons.class);
            startActivity(i);
            return true;
        }

        if (item.getItemId() == R.id.mnu_search){
            hideFabs();
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setAnimation(AnimationUtils.makeOutAnimation(this, true));
            fab.hide();
        }

        if (item.getItemId() == R.id.mnu_download_source) {
            Intent i = new Intent(this, ActSourceCode.class);
            startActivity(i);
            return true;
        }

        if (item.getItemId() == R.id.mnu_about) {
            Intent i = new Intent(this, ActAbout.class);
            startActivity(i);
            return true;
        }

        if (item.getItemId()==R.id.mnu_bachup){
            Intent i = new Intent(this, ActSave.class);
            startActivity(i);
            return true;
        }

        if (item.getItemId()==R.id.mnu_backup_send){
            Intent i = new Intent(this, ActSaveSend.class);
            startActivity(i);
            return true;
        }

        if (item.getItemId()==R.id.mnu_restore){
            Intent i = new Intent(this, ActRestore.class);
            startActivity(i);
            return true;
        }

        if (item.getItemId()==R.id.mnu_dell_all){
            Intent i = new Intent(this, ActDellALL.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void choiceAction(int theOperation, int selected, int pos) {

    }

    @Override
    public void yesAction(int theOperation, int pos) {
        if (theOperation== RaghamConst.DELETE_OPERATION) {
            NumberManager nm = (NumberManager) adap.getItemAt(pos);
            try {
                nm.remove(dbConn);
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
