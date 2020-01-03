package org.kasabeh.raghamcardbank.backup;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.utils.MessDlg;

import java.io.File;


public class ActSelFolder extends HlpListActivityNoDB implements OnClickListener {

	public static final String CSelFolder = "ActSelFolder.CSelFolder";
	public static final String CCurrentDir = "ActSelFolder.CCurrentDir";
	private FolderAdap folderAdap;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_sel_folder);
		ActionBar br = getSupportActionBar();
		if (br!=null){
			br.setDisplayHomeAsUpEnabled(true);
			br.setHomeAsUpIndicator(R.drawable.back);
		}
        setResult(RESULT_CANCELED);      
        
        String path = getIntent().getStringExtra(CCurrentDir);
        path = new File(path).getParent();
        folderAdap = new FolderAdap(this, new File(path));
        setListAdapter(folderAdap);
        TextView txt = findViewById(R.id.txtPath);
        txt.setText(folderAdap.getCurrentPath());
    
        findViewById(R.id.btnReadPath).setOnClickListener(this);
        
        ListView lv = findViewById(android.R.id.list);
        lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long dbId) {
				File f = (File) folderAdap.getItem(pos);
				if (f!=null&&f.isDirectory()&&f.list()!=null){
					folderAdap = new FolderAdap(ActSelFolder.this, f);
			        setListAdapter(folderAdap);
			        TextView txt = findViewById(R.id.txtPath);
			        txt.setText(folderAdap.getCurrentPath());			        
				}
			}        	
        }); 
        
        MessDlg.simpleMess(this, getString(R.string.strBackupLimit));
    }

	@Override
	public void onClick(View v) {
		if (v.getId()==R.id.btnReadPath){			
			String path = folderAdap.getCurrentPath();
			Intent data = new Intent();
			if (!path.endsWith("/")) path+="/";
			if (!validPath(path)){
				MessDlg.simpleMess(this, getString(R.string.invalidPath));
			} else {
				data.putExtra(CSelFolder, path);
				setResult(RESULT_OK, data);
				finish();
			}
		}		
	}

	private boolean validPath(String path) {
		File f = new File(path);
		return f.canWrite();		
	}
	
	
}
