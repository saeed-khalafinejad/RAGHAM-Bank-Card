package org.kasabeh.raghamcardbank.logical;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Context;

import org.kasabeh.raghamcardbank.db.DBConn;
import org.kasabeh.raghamcardbank.utils.RaghamException;

/**
 * Created by Saeed on 06/06/2018.
 */
public abstract class DBObject {

    public static final int DB_ID_UNKNOWN = -1;

    protected int _id;
    protected Context ctx;

    public DBObject(Context ctx, int _id){
        this._id = _id;
        this.ctx = ctx;
    }

    public int get_id(){
        return _id;
    }

    public abstract void save(DBConn dbConn) throws Exception;
    public abstract void edit(DBConn dbConn) throws Exception;
    public abstract void remove(DBConn dbConn) throws Exception;
    protected abstract void validateInputs() throws RaghamException;
    public abstract boolean exists(DBConn dbConn);

}
