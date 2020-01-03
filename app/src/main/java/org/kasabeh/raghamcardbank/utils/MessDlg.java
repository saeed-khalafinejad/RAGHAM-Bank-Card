package org.kasabeh.raghamcardbank.utils;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import org.kasabeh.raghamcardbank.R;

/**
 * Created by Saeed on 06/09/2018.
 */
public class MessDlg {

     public static void simpleMess(Context c, String s){
            new AlertDialog.Builder(c).setMessage(s).setPositiveButton(R.string.OK, null).show();
     }

    public static void choiceMess(Context c, final int theOperation, final int pos, int arrChoices, final IFeedback f){
        new AlertDialog.Builder(c).setItems(arrChoices,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        f.choiceAction(theOperation, which, pos);
                    }
                }).show();
    }

    public static void yesNoDlg(Context c, final int theOperation, final int pos, final IFeedback f, int messStrId){
        new AlertDialog.Builder(c).setMessage(messStrId).
                setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        f.yesAction(theOperation, pos);

                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                f.noAction(theOperation, pos);

            }
        }).show();

    }
}
