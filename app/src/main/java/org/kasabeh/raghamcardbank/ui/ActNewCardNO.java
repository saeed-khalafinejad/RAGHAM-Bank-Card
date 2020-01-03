package org.kasabeh.raghamcardbank.ui;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.logical.CardNO;
import org.kasabeh.raghamcardbank.logical.NumberManager;
import org.kasabeh.raghamcardbank.utils.Tools;

/**
 * Created by Saeed on 06/17/2018.
 */
public class ActNewCardNO extends ActNewNO {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final EditText edtNum = findViewById(R.id.edtAccountNO);
        //TODO The text change listener anonymous class can be refactored to a non-anonymous class.
        edtNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                edtNum.removeTextChangedListener(this);
                addSeparators(s.toString());
                edtNum.addTextChangedListener(this);
            }

            private void addSeparators(String s) {
                if (s.length()==0) return;
                int selEnd = edtNum.getSelectionEnd();
                int oldSelEnd = selEnd;
                String onlyNums = "";
                for(int i=0;i<s.length();i++){
                    if (s.charAt(i)!='-'){
                        onlyNums += s.charAt(i);
                    } else {
                        if (i<oldSelEnd) selEnd--;
                    }
                }

                String finalStr = "";
                for(int i=0;i<onlyNums.length();i++){
                    if (i>0 && i % 4==0){
                        finalStr += "-" + onlyNums.charAt(i);
                        if (i<oldSelEnd) selEnd++;
                    } else {
                        finalStr += onlyNums.charAt(i);
                    }
                }
                edtNum.setText(finalStr);
                if (selEnd<0) selEnd = 0;
                    else if (selEnd>finalStr.length()) selEnd = finalStr.length();
                edtNum.setSelection(selEnd);
            }

        });

    }

    @Override
    protected void showSuccMess() {
        Toast.makeText(this, R.string.cardNOSaved, Toast.LENGTH_LONG).show();
    }

    @Override
    protected NumberManager getNumberManager() throws Exception {
        return new CardNO(this, getPersonObj(), bank, getNum(), getDesc(), getCVV(), getExpirationDate());
    }

    protected String getExpirationDate() {
        EditText edt = findViewById(R.id.edtExpirationYear);
        String res = edt.getText().toString();
        edt = findViewById(R.id.edtExpirationMonth);
        String month = edt.getText().toString();
        if (month.length()==1) month = "0" + month;
        res = res + "/" + month;
        if (res.equals("/")) res  = "";
        return res;
    }

    protected String getCVV() {
        EditText edt = findViewById(R.id.edtCVV);
        return edt.getText().toString();
    }

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_act_new_card_no);
    }

    @Override
    protected String getNum() {
        EditText edt = findViewById(R.id.edtAccountNO);
        return Tools.removeNumSeparators(edt.getText().toString());
    }
}
