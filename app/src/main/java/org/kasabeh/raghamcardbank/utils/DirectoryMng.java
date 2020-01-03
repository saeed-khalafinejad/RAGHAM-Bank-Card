package org.kasabeh.raghamcardbank.utils;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.os.Environment;

import java.io.File;

public class DirectoryMng {

	private static final String APP_FOLDER = "Ragham_card_mng";
	private static final String DATA_FOLDER = "Data";

	private static String getSDCard(){
		String sPath = Environment.getExternalStorageDirectory().getPath();			
		if (sPath.charAt(sPath.length()-1)!='/'){
			sPath+="/";
		}
		return sPath;
	}
	
	public static String getStorageDir(){	
		return getSDCard()+ APP_FOLDER +"/";
	}

	public static String getDataDir() {
		return getStorageDir()+ DATA_FOLDER +"/";
	}

	public static void createDirectories() {
        File f = new File(DirectoryMng.getStorageDir());
        if (!(f.exists())) {
        	f.mkdir();
        }
        
        f = new File(DirectoryMng.getDataDir());        
        if (!(f.exists())) {
        	f.mkdir();
        }	
    }
}
