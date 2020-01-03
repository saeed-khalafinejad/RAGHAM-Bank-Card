package org.kasabeh.raghamcardbank.backup;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.DBConn;
import org.kasabeh.raghamcardbank.utils.DirectoryMng;
import org.kasabeh.raghamcardbank.utils.IFeedback;
import org.kasabeh.raghamcardbank.utils.MessDlg;
import org.kasabeh.raghamcardbank.utils.PrefMng;
import org.kasabeh.raghamcardbank.utils.Tools;

import java.io.File;


public class ActRestore extends HlpListActivityNoDB implements OnClickListener, IFeedback {

	private static final int CSureToLoad = 1;
	private FileAdap fileAdap;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_restore);
		ActionBar br = getSupportActionBar();
		if (br!=null){
			br.setDisplayHomeAsUpEnabled(true);
			br.setHomeAsUpIndicator(R.drawable.back);
		}

        DBConn.destroyConnection();
        String path = PrefMng.getBackupPath(this);
        fileAdap = new FileAdap(this, new File(path));
        setListAdapter(fileAdap);
    
        findViewById(R.id.btnLoadDt).setOnClickListener(this);
        
        ListView lv = findViewById(android.R.id.list);
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
									long dbId) {
				File f = (File) fileAdap.getItem(pos);
				if (f != null && f.isDirectory() && f.list() != null) {
					fileAdap = new FileAdap(ActRestore.this, f);
					setListAdapter(fileAdap);
				}
			}
		});
    }

	@Override
	public void onClick(View v) {
		if (v.getId()==R.id.btnLoadDt){
			int c = fileAdap.getCheckedCount();
			if (c==0){
				MessDlg.simpleMess(this, getString(R.string.noFileSel));
			}else if (c>1){
				MessDlg.simpleMess(this, getString(R.string.moreFileSel));
			}else{
				MessDlg.yesNoDlg(this, CSureToLoad, -1, this, R.string.strSureToLoad);
			}
		}		
	}

	class RestoreData implements Runnable {
		private String from;

		RestoreData(String aFrom) {
			from = aFrom;
		}
		@Override
		public void run() {
			//boolean status;
			final String txtStr;
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
			String db_path = DirectoryMng.getDataDir()+DBConn.DB_NAME;
			if (Tools.copyfile(from, db_path, ActRestore.this)){
				//status = true;
				txtStr = getString(R.string.loadSucc);
			}else{
				//status = false;
				txtStr = getString(R.string.loadUnsucc);
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

		}
		
	}

	@Override
	public void choiceAction(int theOperation, int selected, int pos) {
	
	}

	@Override
	public void yesAction(int theOperation, int pos) {
		if (theOperation==CSureToLoad){
			String from = fileAdap.getCheckedItem();
			if (from.length()!=0){
				new Thread(new RestoreData(from)).start();
			}			
		}
	}

	@Override
	public void noAction(int theOperation, int pos) {
	}

}
