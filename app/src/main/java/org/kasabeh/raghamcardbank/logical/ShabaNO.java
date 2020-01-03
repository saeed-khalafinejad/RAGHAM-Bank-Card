package org.kasabeh.raghamcardbank.logical;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.content.Context;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.DBAdap;
import org.kasabeh.raghamcardbank.db.DBConn;
import org.kasabeh.raghamcardbank.db.NOsView;
import org.kasabeh.raghamcardbank.db.ShabaNODB;
import org.kasabeh.raghamcardbank.utils.RaghamException;

/**
 * Created by Saeed on 06/17/2018.
 */
public class ShabaNO extends NumberManager {

    public ShabaNO(Context ctx, Person person, Bank bank, String num, String desc) throws RaghamException {
        super(ctx, person, bank, num, desc);
        validateInputs();
    }

    public ShabaNO(Context ctx, int _id, Person person, Bank bank, String num, String desc) throws RaghamException {
        super(ctx, _id, person, bank, num, desc);
        validateInputs();
    }

    @Override
    public void save(DBConn dbConn) throws Exception {
        DBAdap dbAdap = new ShabaNODB(ctx);
        dbAdap.insert(this, dbConn);
    }

    @Override
    public void edit(DBConn dbConn) throws Exception {
        ShabaNODB dbAdap = new ShabaNODB(ctx);
        dbAdap.update(this, dbConn);
    }

    @Override
    public void remove(DBConn dbConn) throws Exception {
        ShabaNODB dbAdap = new ShabaNODB(ctx);
        dbAdap.delete(_id, dbConn);
    }

    @Override
    public boolean exists(DBConn dbConn) {
        return false;
    }

    @Override
    protected void validateInputs() throws RaghamException {
        super.validateInputs();
        if (num==null||num.length()==0)
            throw new RaghamException(ctx.getString(R.string.noShabaNum));
        if (num.length()!=26)
            throw new RaghamException(ctx.getString(R.string.wrongShabaNum));
    }

    @Override
    public String getKindStr() {
        return ctx.getString(R.string.shabaNO);
    }

    @Override
    public int getKindInt() {
        return NOsView.ROW_KIND_SHABA_NO;
    }

    @Override
    public String getCopy() {
        return ctx.getString(R.string.shabaNO)+": "+num+"\n"+
                ctx.getString(R.string.bank)+" "+bank.getBankName()+"\n"+
                ctx.getString(R.string.owned_by)+" "+person.getFullName();
    }

}
