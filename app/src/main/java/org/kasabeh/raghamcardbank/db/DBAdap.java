package org.kasabeh.raghamcardbank.db;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.content.Context;
import android.database.Cursor;

/**
 * Created by Saeed on 06/06/2018.
 */
public abstract class DBAdap {

    protected Context ctx;

    DBAdap(Context ctx) {
        this.ctx = ctx;
    }

    public abstract void insert(Object o, DBConn db) throws Exception;
    public abstract void update(Object o, DBConn db) throws Exception;
    public abstract void delete(int _id, DBConn db) throws Exception;
    public abstract Object loadObject(Cursor cr) throws Exception;
    public abstract Object loadObject(int _id) throws Exception;
    public abstract Cursor readAllRecords(DBConn db, String orderBy);
    public abstract Cursor readRecords(DBConn db, String whereClause, String orderBy);
    public abstract Cursor readRecords(DBConn db, String whereClause);
}
