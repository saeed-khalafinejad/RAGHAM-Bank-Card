package org.kasabeh.raghamcardbank.db;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Context;
import android.database.Cursor;

import org.kasabeh.raghamcardbank.logical.Bank;
import org.kasabeh.raghamcardbank.logical.NOsFactory;
import org.kasabeh.raghamcardbank.logical.NumberManager;
import org.kasabeh.raghamcardbank.logical.Person;

/**
 * Created by Saeed on 06/24/2018.
 */
public class NOsView extends ViewAdap {

    public static int ROW_KIND_ACCOUNT_NO = 2;
    public static int ROW_KIND_SHABA_NO = 3;
    public static int ROW_KIND_CARD_NO = 1;

    public NOsView(Context ctx) {
        super(ctx);
    }

    @Override
    public NumberManager loadObject(Cursor cr) throws Exception {
        Bank bank = new Bank(ctx,
                cr.getInt(cr.getColumnIndex("bankId")),
                cr.getString(cr.getColumnIndex("bankName")),
                cr.getString(cr.getColumnIndex("bankLogo")));
        Person person = new Person(ctx,
                cr.getInt(cr.getColumnIndex("personId")),
                cr.getString(cr.getColumnIndex("fullName")));
        int kind = cr.getInt(cr.getColumnIndex("rowKind"));
        return NOsFactory.createNumberMng(ctx, kind, person, bank, cr);
    }

    @Override
    public NumberManager loadObject(int _id) throws Exception {
        return null;
    }

    public NumberManager loadObject(int _id, int kind) throws Exception {
        DBConn dbConn = DBConn.createConn(ctx);
        Cursor cr = dbConn.getReadableDatabase().rawQuery("select * from vNumbers where _id = " + _id+
                " and rowKind = "+kind, null);
        try{
            if (cr.moveToFirst()) return loadObject(cr);
            return null;
        } finally {
            cr.close();
        }
    }

    @Override
    public Cursor readAllRecords(DBConn db, String orderBy) {
        return db.getReadableDatabase().rawQuery("select * from vNumbers order by "+orderBy+", rowKind", null);
    }

    @Override
    public Cursor readRecords(DBConn db, String whereClause, String orderBy) {
        return db.getReadableDatabase().rawQuery("select * from vNumbers " +
                "where "+whereClause+" "+
                "order by "+orderBy+", rowKind", null);
    }

    @Override
    public Cursor readRecords(DBConn db, String whereClause) {
        return readRecords(db, whereClause, "fullName, bankName");
    }
}
