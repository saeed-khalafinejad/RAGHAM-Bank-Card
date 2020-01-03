package org.kasabeh.raghamcardbank.ui;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.utils.RecyclerTouchListener;

/**
 * Created by Saeed on 06/13/2018.
 */
public class ActBankLogos extends AppCompatActivity {

    public static final String SEL_LOGO_NAME = "ActBankLogos.SEL_LOGO_NAME";

    private AdapLogos adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_bank_logos);
        setResult(RESULT_CANCELED);

        RecyclerView rv = findViewById(R.id.rv);
        RecyclerView.LayoutManager layMng = new GridLayoutManager(this, 3);
        rv.setLayoutManager(layMng);
        rv.setItemAnimator(new DefaultItemAnimator());
        adap = new AdapLogos(this);
        rv.setAdapter(adap);

        rv.addOnItemTouchListener(new RecyclerTouchListener(this, rv) {
            @Override
            public void onItemClick(int pos) {
                String fn = adap.getLogoFileName(pos);
                Intent data = new Intent();
                data.putExtra(SEL_LOGO_NAME, fn);
                setResult(RESULT_OK, data);
                finish();
            }

            @Override
            public void onItemLongClick(int pos) {}
        });
    }
}
