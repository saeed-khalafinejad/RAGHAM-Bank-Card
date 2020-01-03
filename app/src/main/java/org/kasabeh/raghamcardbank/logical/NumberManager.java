package org.kasabeh.raghamcardbank.logical;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Context;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.utils.RaghamException;
import org.kasabeh.raghamcardbank.utils.Tools;

/**
 * Created by Saeed on 06/17/2018.
 */
public abstract class NumberManager extends DBObject {

    protected Person person;
    protected Bank bank;
    protected String num;
    protected String desc;

    public NumberManager(Context ctx, Person person, Bank bank, String num, String desc) throws RaghamException {
        super(ctx, DB_ID_UNKNOWN);
        this.person = person;
        this.bank = bank;
        this.num = num;
        this.desc = desc;
    }

    public NumberManager(Context ctx, int _id, Person person, Bank bank, String num, String desc) throws RaghamException {
        super(ctx, _id);
        this.person = person;
        this.bank = bank;
        this.num = num;
        this.desc = desc;
    }

    @Override
    protected void validateInputs() throws RaghamException {
        if (person==null)
            throw new RaghamException(ctx.getString(R.string.selectAccountOwner));
        if (bank==null)
            throw new RaghamException(ctx.getString(R.string.noBankName));
    }

    public abstract String getKindStr();
    public abstract int getKindInt();

    public Person getPerson() {
        return person;
    }

    public Bank getBank() {
        return bank;
    }

    public String getNum() {
        return num;
    }

    public String getSeparatedNum(){
        return Tools.seperateNum(num);
    }

    public String getDesc() {
        return desc;
    }

    public abstract String getCopy();
}
