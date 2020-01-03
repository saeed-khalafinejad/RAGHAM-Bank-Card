package org.kasabeh.raghamcardbank.backup;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group> 
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import org.kasabeh.raghamcardbank.R;

import java.io.File;

public class ActSaveSend extends ActSave {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }
    
    @Override
    protected void loadLayout() {
    	setContentView(R.layout.activity_act_save_send);
    }

    @Override
    protected void shareBackup() {	
    	super.shareBackup();
		String to = "YourEmail@somewhere.com";
		String subject = "Ragham card bank backup";
		String message = "Backup etelaat.\nRagham Card Bank\nhttp://opensource.kasabeh.org";
		Intent email = new Intent(Intent.ACTION_SEND);
		email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
		email.putExtra(Intent.EXTRA_SUBJECT, subject);
		email.putExtra(Intent.EXTRA_TEXT, message);
		File f = new File(path);
		email.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
		email.setType("text/*");
		startActivity(Intent.createChooser(email, "Send"));
    }
}
