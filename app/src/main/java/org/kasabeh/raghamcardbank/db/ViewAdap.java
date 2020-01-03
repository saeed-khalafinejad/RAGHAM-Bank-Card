package org.kasabeh.raghamcardbank.db;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Context;
import android.database.Cursor;

/**
 * Created by Saeed on 06/24/2018.
 */
public abstract class ViewAdap {

        protected Context ctx;

        public ViewAdap(Context ctx) {
            this.ctx = ctx;
        }

        public abstract Object loadObject(Cursor cr) throws Exception;
        public abstract Object loadObject(int _id) throws Exception;
        public abstract Cursor readAllRecords(DBConn db, String orderBy);
        public abstract Cursor readRecords(DBConn db, String whereClause, String orderBy);
        public abstract Cursor readRecords(DBConn db, String whereClause);
}
