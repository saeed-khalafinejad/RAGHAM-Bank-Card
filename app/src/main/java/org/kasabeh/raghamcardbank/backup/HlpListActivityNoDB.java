package org.kasabeh.raghamcardbank.backup;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

public class HlpListActivityNoDB extends AppCompatActivity {
	
	public void setListAdapter(final ListAdapter adap){
		final ListView lv = findViewById(android.R.id.list);
		final View txtNoInfo = findViewById(android.R.id.empty);
		lv.setAdapter(adap);
		if (adap.getCount()>0){
			lv.setVisibility(View.VISIBLE);
			txtNoInfo.setVisibility(View.GONE);
		} else {
			lv.setVisibility(View.GONE);
			txtNoInfo.setVisibility(View.VISIBLE);
		}

		adap.registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				super.onChanged();
				if (adap.getCount() > 0) {
					lv.setVisibility(View.VISIBLE);
					txtNoInfo.setVisibility(View.GONE);
				} else {
					lv.setVisibility(View.GONE);
					txtNoInfo.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId()==android.R.id.home){
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
