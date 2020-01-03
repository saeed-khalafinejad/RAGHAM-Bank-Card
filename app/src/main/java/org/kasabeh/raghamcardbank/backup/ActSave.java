package org.kasabeh.raghamcardbank.backup;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group> 
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.DBConn;
import org.kasabeh.raghamcardbank.utils.DirectoryMng;
import org.kasabeh.raghamcardbank.utils.MessDlg;
import org.kasabeh.raghamcardbank.utils.PrefMng;
import org.kasabeh.raghamcardbank.utils.Tools;

import java.io.File;

public class ActSave extends AppCompatActivity implements OnClickListener {
	private static final int CReqSelFolder = 100;
	protected String path;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLayout();
		ActionBar br = getSupportActionBar();
		if (br!=null){
			br.setDisplayHomeAsUpEnabled(true);
			br.setHomeAsUpIndicator(R.drawable.back);
		}
        path = PrefMng.getBackupPath(this);
        path+= getFileName();
        TextView txt = findViewById(R.id.txtPath);
        txt.setText(path);
        findViewById(R.id.btnSave).setOnClickListener(this);
        findViewById(R.id.btnPath).setOnClickListener(this);
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId()==android.R.id.home){
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

    private String getFileName() {
        return "cards.bkc";
	}


	protected void loadLayout() {
    	setContentView(R.layout.activity_act_save);		
	}


	@Override
	public void onClick(View v) {
		if (v.getId()==R.id.btnSave){			
			String dir = new File(path).getParent();
			File f_dir = new File(dir);
			if (f_dir.canWrite()){
				DBConn.destroyConnection();
				new Thread(new MakeBackup()).start();
			} else {
			   MessDlg.simpleMess(this, getString(R.string.invalidPath));
			   String path = DirectoryMng.getStorageDir()+ getFileName();
		       TextView txt = findViewById(R.id.txtPath);
		       txt.setText(path);				
			}
		} else if (v.getId()==R.id.btnPath){
			Intent i = new Intent(this, ActSelFolder.class);
			i.putExtra(ActSelFolder.CCurrentDir, path);
			startActivityForResult(i, CReqSelFolder);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode==RESULT_OK && requestCode == CReqSelFolder){
			path = data.getStringExtra(ActSelFolder.CSelFolder);
			path = path + getFileName();
	        TextView txt = findViewById(R.id.txtPath);
	        txt.setText(path);			
			return ;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	class MakeBackup implements Runnable {

		@Override
		public void run() {
			boolean backupStatus;
			final ProgressBar pg = findViewById(R.id.pgBar);
			final TextView txt = findViewById(R.id.txtResult);
			txt.post(new Runnable() {
				@Override
				public void run() {				
					txt.setText("");
					txt.setVisibility(View.GONE);
				}
			});				
			pg.post(new Runnable() {
				@Override
				public void run() {				
					pg.setVisibility(View.VISIBLE);
				}
			});			
			final String txtStr;
			if (Tools.copyfile(DirectoryMng.getDataDir() +
					DBConn.DB_NAME, path, ActSave.this)){
				backupStatus = true;
				txtStr = getString(R.string.saveSucc);
			}else{
				backupStatus = false;
				txtStr = getString(R.string.saveUnsucc);
			}
			pg.post(new Runnable() {
				@Override
				public void run() {				
					pg.setVisibility(View.GONE);
				}
			});
			txt.post(new Runnable() {
				@Override
				public void run() {				
					txt.setText(txtStr);
					txt.setVisibility(View.VISIBLE);
				}
			});
			if (backupStatus) shareBackup();
		}
		
	}

	protected void shareBackup() {
		String dir = new File(path).getParent();
		PrefMng.saveBackupPath(ActSave.this, dir);
	}
}
