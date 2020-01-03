package org.kasabeh.raghamcardbank.ui;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.DBConn;
import org.kasabeh.raghamcardbank.utils.IFeedback;
import org.kasabeh.raghamcardbank.utils.MessDlg;
import org.kasabeh.raghamcardbank.utils.RaghamConst;
import org.kasabeh.raghamcardbank.utils.RecyclerTouchListener;
import org.kasabeh.raghamcardbank.utils.Tools;

/**
 * Created by Saeed on 06/09/2018.
 */
public abstract class ActSearchHlp extends AppCompatActivity implements View.OnClickListener, IFeedback {

    protected AdapUI adap;
    protected DBConn dbConn;
    protected RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndInit();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar br = getSupportActionBar();
        if (br!=null){
            br.setDisplayHomeAsUpEnabled(true);
            br.setHomeAsUpIndicator(R.drawable.back);
        }
        findViewById(R.id.imgCloseSearch).setOnClickListener(this);
        cancelRefresh();

        EditText edt = findViewById(R.id.edtSearch);
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                Cursor cr = getCursor(s.toString());
                adap.changeCursor(cr);
            }
        });

        dbConn = DBConn.createConn(this);

        rv = getRecyclerView();
        if (rv==null) return;
        rv.setAdapter(getAdapter());

        rv.addOnItemTouchListener(new RecyclerTouchListener(this, rv) {
            @Override
            public void onItemClick(int pos) {
                onRowSingleTouch(pos);
            }

            @Override
            public void onItemLongClick(int pos) {
                onRowLongTouch(pos);
            }
        });
    }

    protected abstract void cancelRefresh();

    @Override
    protected void onResume() {
        dbConn = DBConn.createConn(this);
        super.onResume();
        if (refreshNeeded()) refreshDataSet();
    }

    protected abstract boolean refreshNeeded();

    protected void onRowLongTouch(int pos) {
        MessDlg.choiceMess(this, RaghamConst.DEL_EDIT_OPERATION, pos, R.array.ArrEditDel, this);
    }

    protected abstract void onRowSingleTouch(int pos);
    protected abstract RecyclerView.Adapter getAdapter();

    protected RecyclerView getRecyclerView() {
        RecyclerView rv = findViewById(R.id.rv);
        RecyclerView.LayoutManager layMng = new LinearLayoutManager(this);
        rv.setLayoutManager(layMng);
        rv.setItemAnimator(new DefaultItemAnimator());
        return rv;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void setLayoutAndInit(){
        setContentView(R.layout.activity_act_def_report);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.imgCloseSearch){
            EditText edt = findViewById(R.id.edtSearch);
            edt.setText("");
            View laySearch = findViewById(R.id.laySearch);
            laySearch.setAnimation(AnimationUtils.makeOutAnimation(this, false));
            laySearch.setVisibility(View.GONE);
            Tools.hideKeyBoard(this, edt);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnu_search){
            View v = findViewById(R.id.laySearch);
            v.setAnimation(AnimationUtils.makeInAnimation(this, false));
            v.setVisibility(View.VISIBLE);
            EditText edt = findViewById(R.id.edtSearch);
            edt.requestFocus();
            Tools.showKeyBoard(this, edt);
            return true;
        }

        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshDataSet(){
        adap.changeCursor(getCursor(getEdtSearchValue()));
    }

    protected String getEdtSearchValue() {
        EditText edt = findViewById(R.id.edtSearch);
        return edt.getText().toString();
    }

    protected abstract Cursor getCursor(String filter);
}
