package org.kasabeh.raghamcardbank.utils;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Saeed on 07/01/2018.
 */
public class PrefMng {

    private static final String STORAGE_VALUE = "org.kasabeh.raghamcardbank.utils.ragham_storage";
    private static final String RAGHAM_BACKUP_PATH = "org.kasabeh.raghamcardbank.utils.ragham_backup_path";

    public static String getBackupPath(Context c) {
        SharedPreferences pref = c.getSharedPreferences(STORAGE_VALUE, Context.MODE_PRIVATE);
        String defPath = DirectoryMng.getStorageDir();
        String res = pref.getString(RAGHAM_BACKUP_PATH, defPath);
        if (!res.endsWith("/")) res += "/";
        return res;
    }

    public static void saveBackupPath(Context context, String path) {
        SharedPreferences pref = context.getSharedPreferences(STORAGE_VALUE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(RAGHAM_BACKUP_PATH, path);
        editor.commit();
    }

}
