package org.kasabeh.raghamcardbank.ui;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.NOsView;
import org.kasabeh.raghamcardbank.logical.NOsFactory;
import org.kasabeh.raghamcardbank.logical.NumberManager;
import org.kasabeh.raghamcardbank.utils.MessDlg;
import org.kasabeh.raghamcardbank.utils.RaghamConst;
import org.kasabeh.raghamcardbank.utils.Tools;

/**
 * Created by Saeed on 06/16/2018.
 */
public class AdapNOs extends AdapUI<AdapNOs.RowHolder> {

    NOsView view;

    public AdapNOs(ActMain ctx, Cursor cr) {
        super(ctx, cr);
        view = new NOsView(ctx);
    }

    @Override
    public RowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.row_numbers, parent, false);
        return new RowHolder(v);
    }

    @Override
    public void onBindViewHolder(RowHolder holder, int position) {
        if (cr.moveToPosition(position)){
            try {
                NumberManager nm = view.loadObject(cr);
                holder.txtPersonName.setText(nm.getPerson().getFullName());
                holder.txtBankName.setText(nm.getBank().getBankName());
                holder.imgBankLogo.setImageDrawable(nm.getBank().getBankLogo());
                holder.txtNum.setText(nm.getSeparatedNum());
                holder.imgOverflow.setOnClickListener(new MnuHandler(nm, position));
                if (nm.getDesc().length()>0){
                    holder.txtDesc.setText(nm.getDesc());
                    holder.txtDesc.setVisibility(View.VISIBLE);
                } else {
                    holder.txtDesc.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                holder.setToDefaults();
            }
        } else {
            holder.setToDefaults();
        }
    }

    @Override
    public NumberManager getItemAt(int pos) {
        if (cr.moveToPosition(pos)){
            try {
                return view.loadObject(cr);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public class RowHolder extends RecyclerView.ViewHolder{

        ImageView imgBankLogo;
        ImageView imgOverflow;
        TextView txtBankName;
        TextView txtPersonName;
        TextView txtNum;
        TextView txtDesc;

        public RowHolder(View row) {
            super(row);
            imgBankLogo = row.findViewById(R.id.imgBankLogo);
            txtBankName = row.findViewById(R.id.txtBankName);
            txtPersonName = row.findViewById(R.id.txtPersonName);
            txtNum = row.findViewById(R.id.txtNum);
            imgOverflow = row.findViewById(R.id.imgOverFlow);
            txtDesc = row.findViewById(R.id.txtDesc);
        }

        public void setToDefaults() {

        }
    }

    public class MnuHandler implements View.OnClickListener{

        private int position;
        private NumberManager nm;
        private boolean shareImg;

        public MnuHandler(NumberManager nm, int position){
            this.position = position;
            this.nm = nm;
            this.shareImg = (nm.getKindInt()==NOsView.ROW_KIND_CARD_NO);
        }

        @Override
        public void onClick(View v) {
            PopupMenu popup = new PopupMenu(ctx, v);
            MenuInflater inflater = popup.getMenuInflater();
            if (shareImg) inflater.inflate(R.menu.mnu_overflow_img, popup.getMenu());
                else inflater.inflate(R.menu.mnu_overflow, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.mnu_edit) {
                        Intent i = NOsFactory.createEditIntent(ctx, nm);
                        ctx.startActivity(i);
                        return true;
                    }
                    if (item.getItemId() == R.id.mnu_del) {
                        MessDlg.yesNoDlg(ctx, RaghamConst.DELETE_OPERATION, position, ((ActMain) ctx), R.string.sureToDel);
                        return true;
                    }
                    if (item.getItemId() == R.id.mnu_share_txt) {
                        Intent i = NOsFactory.createShareIntent(ctx, nm);
                        ctx.startActivity(i);
                        return true;
                    }
                    if (item.getItemId() == R.id.mnu_share_img) {
                        Intent i = NOsFactory.createShareImageIntent(ctx, nm);
                        ctx.startActivity(i);
                        return true;
                    }
                    if (item.getItemId()==R.id.mnu_copy){
                        Tools.copyToClipboard(ctx, nm.getCopy());
                        Toast.makeText(ctx, R.string.copied, Toast.LENGTH_LONG).show();
                    }
                    return false;
                }
            });
            popup.show();
        }
    }
}
