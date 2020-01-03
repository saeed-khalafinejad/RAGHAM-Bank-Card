package org.kasabeh.raghamcardbank.logical;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.BankDB;
import org.kasabeh.raghamcardbank.db.DBConn;
import org.kasabeh.raghamcardbank.utils.RaghamConst;
import org.kasabeh.raghamcardbank.utils.RaghamException;
import org.kasabeh.raghamcardbank.utils.StrPross;
import org.kasabeh.raghamcardbank.utils.Tools;

/**
 * Created by Saeed on 06/12/2018.
 */
public class Bank extends DBObject {

    private String bankName;
    private String bankLogo;

    public Bank(Context ctx, String bankName, String bankLogo) throws RaghamException {
        super(ctx, DB_ID_UNKNOWN);
        this.bankName = bankName;
        this.bankLogo = bankLogo;
        validateInputs();
    }

    public Bank(Context ctx, int _id, String bankName, String bankLogo) throws RaghamException {
        super(ctx, _id);
        this.bankName = bankName;
        this.bankLogo = bankLogo;
        validateInputs();
    }

    public String getBankName() {
        return bankName;
    }

    public Drawable getBankLogo() {
        return Tools.getDrawableFromName(ctx, bankLogo);
    }

    public Bitmap getBankCardCanvas() {
        return Tools.getBitmapFromName(ctx, bankLogo + "_card");
    }

    @Override
    public void save(DBConn dbConn) throws Exception {
        BankDB dbAdap = new BankDB(ctx);
        dbAdap.insert(this, dbConn);
    }

    @Override
    public void edit(DBConn dbConn) throws Exception {
        BankDB dbAdap = new BankDB(ctx);
        dbAdap.update(this, dbConn);
    }

    @Override
    public void remove(DBConn dbConn) throws Exception {
        BankDB dbAdap = new BankDB(ctx);
        dbAdap.delete(get_id(), dbConn);
    }

    @Override
    protected void validateInputs() throws RaghamException {
        if (bankName==null || bankName.length()==0)
            throw new RaghamException(ctx.getString(R.string.noBankName));
    }

    @Override
    public boolean exists(DBConn dbConn) {
        BankDB dbAdap = new BankDB(ctx);
        Cursor cr =
                dbAdap.readRecords(dbConn, "bankName="+ StrPross.Qoute(getBankName())+" and "+
                        "_id<>"+_id);
        return (cr.moveToFirst());
    }

    public String getBankLogoFileName() {
        return bankLogo;
    }

    public boolean hasDefaultLogo(){
        return bankLogo.equals(RaghamConst.BNK_DEF);
    }
}
