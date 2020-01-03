package org.kasabeh.raghamcardbank.db;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Context;
import android.database.Cursor;

import org.kasabeh.raghamcardbank.logical.ShabaNO;
import org.kasabeh.raghamcardbank.utils.StrPross;

/**
 * Created by Saeed on 06/17/2018.
 */
public class ShabaNODB extends DBAdap {


    public ShabaNODB(Context ctx) {
        super(ctx);
    }

    @Override
    public void insert(Object o, DBConn db) throws Exception {
        ShabaNO s = (ShabaNO) o;
        db.getWritableDatabase().execSQL("insert into ShabaNOs (personId, bankId, num, desc) values(" +
                s.getPerson().get_id() + "," + s.getBank().get_id() + "," +
                StrPross.Qoute(s.getNum()) + "," + StrPross.Qoute(s.getDesc()) + ")");
    }

    @Override
    public void update(Object o, DBConn db) throws Exception {
        ShabaNO a = (ShabaNO) o;
        db.getWritableDatabase().execSQL("update ShabaNOs set personId = "+a.getPerson().get_id()+
                ", bankId = "+a.getBank().get_id()+", num = "+StrPross.Qoute(a.getNum())+
                ", desc = "+StrPross.Qoute(a.getDesc())+" where _id = "+a.get_id());
    }

    @Override
    public void delete(int _id, DBConn db) throws Exception {
        db.getWritableDatabase().execSQL("delete from ShabaNOs where _id = "+_id);
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
