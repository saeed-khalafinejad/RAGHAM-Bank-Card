package org.kasabeh.raghamcardbank.db;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Context;
import android.database.Cursor;

import org.kasabeh.raghamcardbank.logical.AccountNO;
import org.kasabeh.raghamcardbank.utils.StrPross;

/**
 * Created by Saeed on 06/14/2018.
 */
public class AccountNODB extends DBAdap {

    public AccountNODB(Context ctx) {
        super(ctx);
    }

    @Override
    public void insert(Object o, DBConn db) throws Exception {
        AccountNO a = (AccountNO) o;
        db.getWritableDatabase().execSQL("insert into AccountNOs (personId, bankId, num, desc) values(" +
                a.getPerson().get_id() + "," + a.getBank().get_id() + "," +
                StrPross.Qoute(a.getNum()) + "," + StrPross.Qoute(a.getDesc()) + ")");
    }

    @Override
    public void update(Object o, DBConn db) throws Exception {
        AccountNO a = (AccountNO) o;
        db.getWritableDatabase().execSQL("update AccountNOs set personId = "+a.getPerson().get_id()+
         ", bankId = "+a.getBank().get_id()+", num = "+StrPross.Qoute(a.getNum())+
         ", desc = "+StrPross.Qoute(a.getDesc())+" where _id = "+a.get_id());
    }

    @Override
    public void delete(int _id, DBConn db) throws Exception {
        db.getWritableDatabase().execSQL("delete from AccountNOs where _id = "+_id);
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
        return db.getReadableDatabase().rawQuery("select * from AccountNOs order by "+orderBy, null);
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
