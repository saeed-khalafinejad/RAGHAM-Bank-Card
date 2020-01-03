package org.kasabeh.raghamcardbank.ui;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.DBConn;
import org.kasabeh.raghamcardbank.utils.DirectoryMng;
import org.kasabeh.raghamcardbank.utils.IFeedback;
import org.kasabeh.raghamcardbank.utils.MessDlg;

import java.io.File;

public class ActDellALL extends ActFormHlp implements OnClickListener, IFeedback {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBConn.destroyConnection();
        findViewById(R.id.btnDellAll).setOnClickListener(this);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	protected void setLayout() {
		setContentView(R.layout.activity_act_dell_all);
	}


	@Override
	public void onClick(View v) {
		if (v.getId()==R.id.btnDellAll){
			EditText txt = findViewById(R.id.edtDellPass);
			String entered_pass = txt.getText().toString();
			if (entered_pass.equals("14526")){
				MessDlg.yesNoDlg(this, -1, 0, this, R.string.all_data_will_del);
			}else{
				MessDlg.simpleMess(this, getString(R.string.wrongPass));
			}
		}
		
	}
	
	class DellWorker implements Runnable {

		@Override
		public void run() {
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
			File f = new File(
					DirectoryMng.getDataDir()+DBConn.DB_NAME);
			boolean res = f.delete();
			if (res){
				txtStr = getString(R.string.dellSucc);
			}else{
				txtStr = getString(R.string.dellUnsucc);
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
	public void choiceAction(int theOperation, int selected, int pos) {}

	@Override
	public void yesAction(int theOperation, int pos) {
		new Thread(new DellWorker()).start();
	}

	@Override
	public void noAction(int theOperation, int pos) {}

}
