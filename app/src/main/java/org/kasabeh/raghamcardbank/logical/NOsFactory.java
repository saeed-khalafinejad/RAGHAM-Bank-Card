package org.kasabeh.raghamcardbank.logical;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import org.kasabeh.raghamcardbank.db.NOsView;
import org.kasabeh.raghamcardbank.ui.ActEditAccountNO;
import org.kasabeh.raghamcardbank.ui.ActEditCardNO;
import org.kasabeh.raghamcardbank.ui.ActEditShabaNO;
import org.kasabeh.raghamcardbank.ui.ActShareImage;
import org.kasabeh.raghamcardbank.ui.ActShareTxt;
import org.kasabeh.raghamcardbank.ui.ActShareTxtCard;
import org.kasabeh.raghamcardbank.ui.ActShareTxtShaba;
import org.kasabeh.raghamcardbank.utils.RaghamException;

/**
 * Created by Saeed on 06/24/2018.
 */
public class NOsFactory {

    public static NumberManager createNumberMng(Context ctx, int kind, Person person, Bank bank, Cursor cr) throws RaghamException {
        if (kind==NOsView.ROW_KIND_ACCOUNT_NO) return new AccountNO(ctx,
                cr.getInt(cr.getColumnIndex("_id")),
                person, bank,
                cr.getString(cr.getColumnIndex("num")),
                cr.getString(cr.getColumnIndex("desc")));
        if (kind==NOsView.ROW_KIND_SHABA_NO) return new ShabaNO(ctx,
                cr.getInt(cr.getColumnIndex("_id")),
                person, bank,
                cr.getString(cr.getColumnIndex("num")),
                cr.getString(cr.getColumnIndex("desc")));
        return new CardNO(ctx,
                cr.getInt(cr.getColumnIndex("_id")),
                person, bank,
                cr.getString(cr.getColumnIndex("num")),
                cr.getString(cr.getColumnIndex("desc")),
                cr.getString(cr.getColumnIndex("cvv")),
                cr.getString(cr.getColumnIndex("expirationDate")));
    }

    public static Intent createEditIntent(Context ctx, NumberManager nm) {
        if (nm.getKindInt()==NOsView.ROW_KIND_ACCOUNT_NO){
            Intent i = new Intent(ctx, ActEditAccountNO.class);
            i.putExtra(ActEditAccountNO.KIND, nm.getKindInt());
            i.putExtra(ActEditAccountNO.ID, nm.get_id());
            return i;
        }
        if (nm.getKindInt()==NOsView.ROW_KIND_SHABA_NO){
            Intent i = new Intent(ctx, ActEditShabaNO.class);
            i.putExtra(ActEditAccountNO.KIND, nm.getKindInt());
            i.putExtra(ActEditAccountNO.ID, nm.get_id());
            return i;
        }
        Intent i = new Intent(ctx, ActEditCardNO.class);
        i.putExtra(ActEditCardNO.KIND, nm.getKindInt());
        i.putExtra(ActEditCardNO.ID, nm.get_id());
        return i;
    }

    public static Intent createShareIntent(Context ctx, NumberManager nm) {
        if (nm.getKindInt()==NOsView.ROW_KIND_CARD_NO) {
            Intent i = new Intent(ctx, ActShareTxtCard.class);
            i.putExtra(ActShareTxt.KIND, nm.getKindInt());
            i.putExtra(ActShareTxt.ID, nm.get_id());
            return i;
        }
        if (nm.getKindInt()==NOsView.ROW_KIND_SHABA_NO) {
            Intent i = new Intent(ctx, ActShareTxtShaba.class);
            i.putExtra(ActShareTxt.KIND, nm.getKindInt());
            i.putExtra(ActShareTxt.ID, nm.get_id());
            return i;
        }
        Intent i = new Intent(ctx, ActShareTxt.class);
        i.putExtra(ActShareTxt.KIND, nm.getKindInt());
        i.putExtra(ActShareTxt.ID, nm.get_id());
        return i;
    }

    public static Intent createShareImageIntent(Context ctx, NumberManager nm) {
        Intent i = new Intent(ctx, ActShareImage.class);
        i.putExtra(ActShareTxtCard.KIND, nm.getKindInt());
        i.putExtra(ActShareTxtCard.ID, nm.get_id());
        return i;
    }

}
