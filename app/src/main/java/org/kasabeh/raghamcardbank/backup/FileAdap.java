package org.kasabeh.raghamcardbank.backup;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group> 
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.kasabeh.raghamcardbank.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class FileAdap extends BaseAdapter {
	private Activity act;
	private File file;
	private ArrayList<String> filesList = new ArrayList<>();
	private ArrayList<String> foldersList = new ArrayList<>();
	private ArrayList<String> fullList = new ArrayList<>();
	private boolean[] checkedItems;

	FileAdap(Activity act, File f) {
		this.act = act;		
		file = f;		
		for(int i=0;i<f.list().length;i++){
			File tmpf = new File
					(file.getAbsolutePath()+"/"+file.list()[i]);
			if (tmpf.isDirectory()){
				foldersList.add(file.list()[i]);
			}else{
				if (tmpf.toString().endsWith(".bkc"))
					filesList.add(file.list()[i]);
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
		checkedItems = new boolean[fullList.size()];
	}

	int getCheckedCount(){
		int count = 0;
		for(int i=0;i<checkedItems.length;i++){
			if (checkedItems[i]){
				count++;
			}
		}
        return count;
	}
	
	String getCheckedItem(){
		int pos = -1;
		for(int i=0;i<checkedItems.length;i++){
			if (checkedItems[i]){
				pos = i;
				break;
			}
		}
        if (pos==-1)
			return "";
		else
			return file.getAbsolutePath()+"/"+fullList.get(pos);		
	}
	
	@Override
	public int getCount() {		
		return fullList.size();
	}

	@Override
	public Object getItem(int position) {
		/*if (fullList.get(position).toLowerCase().contains("secure")){
			return null;
		}*/
		return new File(file.getAbsolutePath()+"/"+
				fullList.get(position));
	}

	@Override
	public long getItemId(int arg0) {		
		return 0;
	}
	
	private void setCheckBox(int pos, boolean bol){
		checkedItems[pos] = bol;
	}
	
	private boolean getCheckBox(int pos){
		return checkedItems[pos];
	}	

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
        //File tmpf = new File(file.getAbsolutePath()+"/"+file.list()[position]);
		File tmpf = new File(file.getAbsolutePath()+"/"+
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
        	row = inflater.inflate(R.layout.f_item2chk, null);
        	CheckBox chk = row.findViewById(R.id.fileChk);
    		chk.setChecked(getCheckBox(position));
    		chk.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				CheckBox chk = (CheckBox) v;
    				setCheckBox(position, chk.isChecked());
    			}
    		});    		
        }
        TextView txtFName = row.findViewById(R.id.rowData);
        txtFName.setText(fullList.get(position));
		return row;
	}

}
