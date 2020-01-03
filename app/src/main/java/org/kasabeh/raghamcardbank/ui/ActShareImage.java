package org.kasabeh.raghamcardbank.ui;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.CheckBox;
import android.widget.ImageView;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.logical.CardCanvas;
import org.kasabeh.raghamcardbank.logical.CardNO;
import org.kasabeh.raghamcardbank.utils.DirectoryMng;
import org.kasabeh.raghamcardbank.utils.MessDlg;
import org.kasabeh.raghamcardbank.utils.StrPross;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Saeed on 06/27/2018.
 */
public class ActShareImage extends ActShareTxtCard {

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_act_share_image);
    }

    protected void setOnClicks() {
        findViewById(R.id.chkAccountOwner).setOnClickListener(this);
        findViewById(R.id.chkCVV).setOnClickListener(this);
        findViewById(R.id.chkExpire).setOnClickListener(this);
    }

    @Override
    protected void setCheckBoxLabels() {}

    @Override
    protected void onResume() {
        ImageView imgCard = findViewById(R.id.imgCard);
        imgCard.setDrawingCacheEnabled(true);
        super.onResume();
    }

    @Override
    protected void generateContent() {
        CardNO nm = (CardNO) getNumberMng();
        if (nm==null) return;

        String num = nm.getNum();
        String owner = getVal(R.id.chkAccountOwner, nm.getPerson().getFullName());
        String cvv = getVal(R.id.chkCVV, nm.getCVV());
        String expirationDate = getVal(R.id.chkExpire, nm.getExpirationDate());
        String bankName;
        if (nm.getBank().hasDefaultLogo()) bankName = nm.getBank().getBankName();
                else bankName = "";

        ImageView imgCard = findViewById(R.id.imgCard);
        try {
            Bitmap bmp = nm.getBank().getBankCardCanvas();
            imgCard.setImageDrawable(new CardCanvas(bmp, num, owner, cvv, expirationDate, bankName, this));
        } catch (Exception e) {
            MessDlg.simpleMess(this, StrPross.readableErr(e, this));
        }
    }


    private String getVal(int chkId, String retStr) {
        CheckBox chk = findViewById(chkId);
        if (chk.isChecked()) return retStr;
        return "";
    }


    @Override
    protected void shareContent() {
        String filePath = DirectoryMng.getStorageDir()+"CardBank.jpg";
        File f = new File(filePath);

        try {
            ImageView imgCard = findViewById(R.id.imgCard);
            Bitmap bmp = imgCard.getDrawingCache(true);
            if (f.exists()) f.delete();
            FileOutputStream outStream = new FileOutputStream(f);
            try{
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
                outStream.flush();
            } finally {
                outStream.close	();
            }
        } catch (Exception e) {
            MessDlg.simpleMess(this, StrPross.readableErr(e, this));
        }

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Card_Bank");
        sharingIntent.putExtra(android.content.Intent.EXTRA_STREAM,	Uri.fromFile(f));
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)));
    }
}
