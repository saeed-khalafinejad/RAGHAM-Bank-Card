package org.kasabeh.raghamcardbank.utils;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.content.Context;

import org.kasabeh.raghamcardbank.R;


/**
 * Created by Saeed on 06/06/2018.
 */
public class StrPross {

    public static String Qoute(String s){
        return "'"+s+"'";
    }

    public static String readableErr(Exception e, Context contx){
        if (e instanceof RaghamException){
            return e.getMessage();
        }else if (e instanceof NumberFormatException){
            return contx.getString(R.string.wrongNO);
        }else{
            return contx.getString(R.string.error)+"\n"+e.getMessage();
        }
    }


}
