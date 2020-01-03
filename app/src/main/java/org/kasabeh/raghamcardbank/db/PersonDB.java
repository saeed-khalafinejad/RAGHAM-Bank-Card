package org.kasabeh.raghamcardbank.db;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.content.Context;
import android.database.Cursor;

import org.kasabeh.raghamcardbank.logical.Person;
import org.kasabeh.raghamcardbank.utils.StrPross;

/**
 * Created by Saeed on 06/06/2018.
 */
public class PersonDB extends DBAdap{

    public PersonDB(Context ctx) {
        super(ctx);
    }

    @Override
    public void insert(Object o, DBConn db) throws RuntimeException {
        Person p = (Person) o;
        db.getWritableDatabase().execSQL("insert into persons (fullName) values("+
                StrPross.Qoute(p.getFullName())+")");
    }

    @Override
    public void update(Object o, DBConn db) throws RuntimeException {
        Person p = (Person) o;
        db.getWritableDatabase().execSQL("update persons set fullName = "+StrPross.Qoute(p.getFullName())+
            " where _id = "+p.get_id());
    }

    @Override
    public void delete(int _id, DBConn db) throws RuntimeException {
        db.getWritableDatabase().execSQL("delete from persons where _id = "+_id);
    }

    @Override
    public Person loadObject(Cursor cr) throws Exception {
        return new Person(ctx,
                cr.getInt(cr.getColumnIndex("_id")),
                cr.getString(cr.getColumnIndex("fullName")));
    }

    @Override
    public Person loadObject(int _id) throws Exception {
        DBConn dbConn = DBConn.createConn(ctx);
        Cursor cr = dbConn.getReadableDatabase().rawQuery("select * from Persons where _id = " + _id, null);
        try{
            if (cr.moveToFirst()) return loadObject(cr);
            return null;
        } finally {
            cr.close();
        }
    }

    public Person loadObject(String fullName, DBConn dbConn) throws Exception {
        Cursor cr = dbConn.getReadableDatabase().rawQuery(
                "select * from Persons where fullName = "+StrPross.Qoute(fullName), null);
        try{
            if (cr.moveToFirst()) return loadObject(cr);
            return null;
        } finally {
            cr.close();
        }
    }

    @Override
    public Cursor readAllRecords(DBConn db, String orderBy) {
        return db.getReadableDatabase().rawQuery("select * from Persons "+
                " order by "+orderBy, null);
    }

    @Override
    public Cursor readRecords(DBConn db, String whereClause, String orderBy) {
        return db.getReadableDatabase().rawQuery("select * from Persons where "+whereClause+
                " order by "+orderBy, null);
    }

    @Override
    public Cursor readRecords(DBConn db, String whereClause) {
        return readRecords(db, whereClause, "fullName");
    }


}
