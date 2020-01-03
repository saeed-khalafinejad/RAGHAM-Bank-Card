package org.kasabeh.raghamcardbank.backup;

/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.kasabeh.raghamcardbank.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class FolderAdap extends BaseAdapter {
	private Activity act;
	private File folder;
	private ArrayList<String> foldersList = new ArrayList<String>();
	private ArrayList<String> fullList = new ArrayList<String>();
	private ArrayList<String> filesList = new ArrayList<String>();

	public FolderAdap(Activity act, File f) {
		this.act = act;
		if (f.getAbsolutePath().contains("..")){								
			try {
				String path = f.getCanonicalPath();
				if (path.length()==0) path = "/";
				folder = new File(path);
			} catch (IOException e) {
				folder = f;
			}			
		} else {
			folder = f;		
		}
		for(int i=0;i<folder.list().length;i++){
			File tmpf = new File
					(folder.getAbsolutePath()+"/"+folder.list()[i]);
			if (tmpf!=null && tmpf.isDirectory()){
				foldersList.add(folder.list()[i]);
			}else{
				if (tmpf!=null && tmpf.toString().endsWith(".bkc"))
					filesList.add(folder.list()[i]);
			}
		}		
		Collections.sort(foldersList, new Comparator<String>() {

			@Override
			public int compare(String lhs, String rhs) {
				return lhs.compareToIgnoreCase(rhs);
			}
		});
		Collections.sort(filesList, new Comparator<String>() {

			@Override
			public int compare(String lhs, String rhs) {
				return lhs.compareToIgnoreCase(rhs);
			}
		});
		fullList.add("..");
		fullList.addAll(foldersList);
		fullList.addAll(filesList);
	}

	@Override
	public int getCount() {		
		return fullList.size();
	}

	@Override
	public Object getItem(int position) {
		return new File(folder.getAbsolutePath()+"/"+
				fullList.get(position));
	}

	@Override
	public long getItemId(int arg0) {		
		return 0;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		File tmpf = new File(folder.getAbsolutePath()+"/"+
				fullList.get(position));
		LayoutInflater inflater = act.getLayoutInflater();
		View row;
		if (tmpf.isDirectory()){
	       	if (position==0){
	    		row = inflater.inflate(R.layout.f_item2up, null);
	    	}else{
	    		row = inflater.inflate(R.layout.f_item2, null);
	    	}
		}else{
        	row = inflater.inflate(R.layout.f_item1, null);
		}
        TextView txtFName = row.findViewById(R.id.rowData);
        txtFName.setText(fullList.get(position));
		return row;
	}

	public String getCurrentPath(){
		return folder.getAbsolutePath();
	}
}
