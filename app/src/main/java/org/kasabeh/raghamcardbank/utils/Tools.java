package org.kasabeh.raghamcardbank.utils;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Saeed on 06/12/2018.
 */
public class Tools {

    public static void hideKeyBoard(Context context, EditText edt){
        InputMethodManager imm = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt.getWindowToken(), 0);
    }

    public static void showKeyBoard(Context context, EditText edt){
        if (edt.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edt, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static Drawable getDrawableFromName(Context ctx, String fileName){
        Resources resources = ctx.getResources();
        int resourceId = resources.getIdentifier(fileName, "drawable", ctx.getPackageName());
        return resources.getDrawable(resourceId);
    }

    public static Bitmap getBitmapFromName(Context ctx, String fileName){
        Resources resources = ctx.getResources();
        int resourceId = resources.getIdentifier(fileName, "drawable", ctx.getPackageName());
        return BitmapFactory.decodeResource(ctx.getResources(), resourceId);
    }

    public static void copyToClipboard(Context ctx, String text) {
        ClipboardManager clipboard = (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("RAGHAM_NO_LBL", text);
        clipboard.setPrimaryClip(clip);
    }

    public static String seperateNum(String num){
        String res = "";
        for(int i=0;i<num.length();i++){
            if (i>0 && i % 4==0){
                res += "-" + num.charAt(i);
            } else {
                res += num.charAt(i);
            }
        }
        return res;
    }

    public static String removeNumSeparators(String seperatedNum){
        String res = "";
        for(int i=0;i<seperatedNum.length();i++){
            if (seperatedNum.charAt(i)!='-'){
                res += seperatedNum.charAt(i);
            }
        }
        return res;
    }

    public static String getVersionName(Context ctx) {
        String versionName;
        try {
            versionName = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName;
        } catch (Exception e) {
            versionName = "-";
        }
        return versionName;
    }

    public static boolean copyfile(String srFile, String dtFile, Context contx){
        try{
            File f1 = new File(srFile);
            File f2 = new File(dtFile);
            InputStream in = new FileInputStream(f1);
            OutputStream out = new FileOutputStream(f2);
            try{
                return copyfile(in, out, contx);
            }finally{
                in.close();
                out.close();
            }
        }catch(FileNotFoundException ex){
            //MessDlg.simpleMess(contx, StrPross.readableErr(ex, contx));
        }catch(Exception e){
            //MessDlg.simpleMess(contx, StrPross.readableErr(e, contx));
        }
        return false;
    }

    private static boolean copyfile(InputStream in, OutputStream out, Context contx){
        try{
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0){
                out.write(buf, 0, len);
            }
            return true;
        }catch(IOException e){
            //MessDlg.simpleMess(contx, StrPross.readableErr(e, contx));
        }catch(Exception e){
            //MessDlg.simpleMess(contx, StrPross.readableErr(e, contx));
        }
        return false;
    }

    public static boolean copyfile(InputStream in, String dtFile, Context contx){
        try{
            File f2 = new File(dtFile);
            OutputStream out = new FileOutputStream(f2);
            try{
                return copyfile(in, out, contx);
            }finally{
                out.close();
            }
        }catch(FileNotFoundException ex){
            //MessDlg.simpleMess(contx, StrPross.readableErr(ex, contx));
        }catch(Exception e){
            //MessDlg.simpleMess(contx, StrPross.readableErr(e, contx));
        }
        return false;
    }

}
