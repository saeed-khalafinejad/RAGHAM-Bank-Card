package org.kasabeh.raghamcardbank.logical;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Context;
import android.database.Cursor;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.DBConn;
import org.kasabeh.raghamcardbank.db.PersonDB;
import org.kasabeh.raghamcardbank.utils.RaghamException;
import org.kasabeh.raghamcardbank.utils.StrPross;

/**
 * Created by Saeed on 06/06/2018.
 */
public class Person extends DBObject{

    private String fullName;

    public Person(Context ctx, String fullName) throws RaghamException {
        super(ctx, DB_ID_UNKNOWN);
        this.fullName = fullName;
        validateInputs();
    }

    public Person(Context ctx, int _id, String fullName) throws RaghamException {
        super(ctx, _id);
        this.fullName = fullName;
        validateInputs();
    }

    @Override
    public void save(DBConn dbConn) throws Exception{
        PersonDB dbAdap = new PersonDB(ctx);
        dbAdap.insert(this, dbConn);
    }

    @Override
    public void edit(DBConn dbConn) throws Exception {
        PersonDB dbAdap = new PersonDB(ctx);
        dbAdap.update(this, dbConn);
    }

    @Override
    public void remove(DBConn dbConn) throws Exception {
        PersonDB dbAdap = new PersonDB(ctx);
        dbAdap.delete(get_id(), dbConn);
    }

    @Override
    protected void validateInputs() throws RaghamException {
        if (fullName==null||fullName.length()==0){
            throw new RaghamException(ctx.getString(R.string.errNoPersonName));
        }
    }

    @Override
    public boolean exists(DBConn dbConn) {
        PersonDB dbAdap = new PersonDB(ctx);
        Cursor cr =
                dbAdap.readRecords(dbConn, "fullName="+ StrPross.Qoute(getFullName())+" and "+
                "_id<>"+_id);
        try {
            return (cr.moveToFirst());
        } finally {
            cr.close();
        }
    }

    public String getFullName() {
        return fullName;
    }

}
