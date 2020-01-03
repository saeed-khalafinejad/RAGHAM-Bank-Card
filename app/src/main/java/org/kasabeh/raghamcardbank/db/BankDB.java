package org.kasabeh.raghamcardbank.db;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.content.Context;
import android.database.Cursor;

import org.kasabeh.raghamcardbank.logical.Bank;
import org.kasabeh.raghamcardbank.utils.StrPross;

/**
 * Created by Saeed on 06/12/2018.
 */
public class BankDB extends DBAdap {


    public BankDB(Context ctx) {
        super(ctx);
    }

    @Override
    public void insert(Object o, DBConn db) throws RuntimeException {
        Bank b = (Bank) o;
        db.getWritableDatabase().execSQL("insert into banks (bankName, bankLogo) values("+
                StrPross.Qoute(b.getBankName())+","+
                StrPross.Qoute(b.getBankLogoFileName())+")");
    }

    @Override
    public void update(Object o, DBConn db) throws RuntimeException {
        Bank b = (Bank) o;
        db.getWritableDatabase().execSQL("update Banks set bankName = "+StrPross.Qoute(b.getBankName())+
                ", bankLogo = "+StrPross.Qoute(b.getBankLogoFileName())+
                " where _id = "+b.get_id());
    }

    @Override
    public void delete(int _id, DBConn db) throws RuntimeException {
        db.getWritableDatabase().execSQL("delete from Banks where _id = "+_id);
    }

    @Override
    public Bank loadObject(Cursor cr) throws Exception {
        return new Bank(ctx, cr.getInt(cr.getColumnIndex("_id")),
                cr.getString(cr.getColumnIndex("bankName")),
                cr.getString(cr.getColumnIndex("bankLogo")));
    }

    @Override
    public Bank loadObject(int _id) throws Exception {
        DBConn dbConn = DBConn.createConn(ctx);
        Cursor cr = dbConn.getReadableDatabase().rawQuery("select * from Banks where _id = " + _id, null);
        try{
            if (cr.moveToFirst()) return loadObject(cr);
            return null;
        } finally {
            cr.close();
        }
    }

    public Bank loadBankByName(String bankName) throws Exception {
        DBConn dbConn = DBConn.createConn(ctx);
        Cursor cr = dbConn.getReadableDatabase().rawQuery("select * from Banks where bankName = " + StrPross.Qoute(bankName), null);
        try{
            if (cr.moveToFirst()) return loadObject(cr);
            return null;
        } finally {
            cr.close();
        }
    }

    @Override
    public Cursor readAllRecords(DBConn db, String orderBy) {
        return db.getReadableDatabase().rawQuery("select * from banks order by "+orderBy, null);
    }

    @Override
    public Cursor readRecords(DBConn db, String whereClause, String orderBy) {
        return db.getReadableDatabase().rawQuery("select * from banks where "+
                whereClause+" order by "+orderBy, null);
    }

    @Override
    public Cursor readRecords(DBConn db, String whereClause) {
        return readRecords(db, whereClause, "bankName");
    }
}
