package org.kasabeh.raghamcardbank.logical;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.content.Context;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.CardNODB;
import org.kasabeh.raghamcardbank.db.DBAdap;
import org.kasabeh.raghamcardbank.db.DBConn;
import org.kasabeh.raghamcardbank.db.NOsView;
import org.kasabeh.raghamcardbank.utils.RaghamException;

/**
 * Created by Saeed on 06/17/2018.
 */
public class CardNO extends NumberManager {

    private String CVV;
    private String expirationDate;

    public CardNO(Context ctx, Person person, Bank bank, String num, String desc, String CVV, String expirationDate) throws RaghamException {
        super(ctx, person, bank, num, desc);
        this.CVV = CVV;
        this.expirationDate = expirationDate;
        validateInputs();
    }

    public CardNO(Context ctx, int _id, Person person, Bank bank, String num, String desc, String CVV, String expirationDate) throws RaghamException {
        super(ctx, _id, person, bank, num, desc);
        this.CVV = CVV;
        this.expirationDate = expirationDate;
        validateInputs();
    }

    @Override
    public void save(DBConn dbConn) throws Exception {
        DBAdap dbAdap = new CardNODB(ctx);
        dbAdap.insert(this, dbConn);
    }

    @Override
    public void edit(DBConn dbConn) throws Exception {
        CardNODB dbAdap = new CardNODB(ctx);
        dbAdap.update(this, dbConn);
    }

    @Override
    public void remove(DBConn dbConn) throws Exception {
        CardNODB dbAdap = new CardNODB(ctx);
        dbAdap.delete(_id, dbConn);
    }

    @Override
    public boolean exists(DBConn dbConn) {
        return false;
    }

    public String getCVV() {
        return CVV;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    @Override
    protected void validateInputs() throws RaghamException {
        super.validateInputs();
        if (num==null||num.length()==0)
            throw new RaghamException(ctx.getString(R.string.noCardNum));
        if (num.length()!=16&&num.length()!=20)
            throw new RaghamException(ctx.getString(R.string.wrongCardNum));

        if (CVV!=null&&CVV.length()>0) {
            if (CVV.length() != 3 && CVV.length() != 4)
                throw new RaghamException(ctx.getString(R.string.wrongCVV));
        }

        if (expirationDate!=null&&expirationDate.length()>0) {
            try {
                String[] date = expirationDate.split("/");
                int month = Integer.parseInt(date[1]);
                if (month > 12 || month < 1)
                    throw new RaghamException(ctx.getString(R.string.wrongMonth));
                String year = date[0];
                if (year.length() != 4 && year.length() != 2) {
                    throw new RaghamException(ctx.getString(R.string.wrongYear));
                }
            } catch (RaghamException e){
                throw e;
            } catch (Exception e) {
                throw new RaghamException(ctx.getString(R.string.wrongDate));
            }
        }
    }

    @Override
    public String getKindStr() {
        return ctx.getString(R.string.cardNO);
    }

    @Override
    public int getKindInt() {
        return NOsView.ROW_KIND_CARD_NO;
    }

    @Override
    public String getCopy() {
        return ctx.getString(R.string.cardNO)+": "+num+"\n"+
                ctx.getString(R.string.bank)+" "+bank.getBankName()+"\n"+
                ctx.getString(R.string.owned_by)+" "+person.getFullName();
    }

}
