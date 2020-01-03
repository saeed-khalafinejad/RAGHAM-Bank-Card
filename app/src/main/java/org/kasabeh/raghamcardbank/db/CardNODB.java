package org.kasabeh.raghamcardbank.db;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Context;
import android.database.Cursor;

import org.kasabeh.raghamcardbank.logical.CardNO;
import org.kasabeh.raghamcardbank.utils.StrPross;

/**
 * Created by Saeed on 06/17/2018.
 */
public class CardNODB extends DBAdap {

    public CardNODB(Context ctx) {
        super(ctx);
    }

    @Override
    public void insert(Object o, DBConn db) throws Exception {
        CardNO c = (CardNO) o;
        db.getWritableDatabase().execSQL("insert into CardNOs (personId, bankId, num, desc, cvv, expirationDate) values(" +
                c.getPerson().get_id() + "," + c.getBank().get_id() + "," +
                StrPross.Qoute(c.getNum()) + "," + StrPross.Qoute(c.getDesc()) +","+
                StrPross.Qoute(c.getCVV())+","+StrPross.Qoute(c.getExpirationDate())+")");
    }

    @Override
    public void update(Object o, DBConn db) throws Exception {
        CardNO a = (CardNO) o;
        db.getWritableDatabase().execSQL("update CardNOs set personId = "+a.getPerson().get_id()+
                ", bankId = "+a.getBank().get_id()+", num = "+StrPross.Qoute(a.getNum())+
                ", desc = "+StrPross.Qoute(a.getDesc())+
                ", cvv = "+StrPross.Qoute(a.getCVV())+
                ", expirationDate = "+StrPross.Qoute(a.getExpirationDate())+
                " where _id = "+a.get_id());
    }

    @Override
    public void delete(int _id, DBConn db) throws Exception {
        db.getWritableDatabase().execSQL("delete from CardNOs where _id = "+_id);
    }

    @Override
    public Object loadObject(Cursor cr) throws Exception {
        return null;
    }

    @Override
    public Object loadObject(int _id) throws Exception {
        return null;
    }

    @Override
    public Cursor readAllRecords(DBConn db, String orderBy) {
        return null;
    }

    @Override
    public Cursor readRecords(DBConn db, String whereClause, String orderBy) {
        return null;
    }

    @Override
    public Cursor readRecords(DBConn db, String whereClause) {
        return null;
    }
}
