package org.kasabeh.raghamcardbank.ui;
/*
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.BankDB;
import org.kasabeh.raghamcardbank.logical.Bank;

/**
 * Created by Saeed on 06/13/2018.
 */
public class AdapBanks extends AdapUI<AdapBanks.RowHolder> {

    AdapBanks(Context ctx, Cursor cr) {
        super(ctx, cr);
    }

    @Override
    public Bank getItemAt(int pos){
        if (cr.moveToPosition(pos)){
            try {
                Bank b = new BankDB(ctx).loadObject(cr);
                return b;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public RowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.row_banks, parent, false);
        return new RowHolder(v);
    }

    @Override
    public void onBindViewHolder(RowHolder holder, int position) {
        if (cr.moveToPosition(position)){
            try {
                Bank b = new BankDB(ctx).loadObject(cr);
                holder.imgBank.setImageDrawable(b.getBankLogo());
                holder.txtBankName.setText(b.getBankName());
            } catch (Exception e) {
                holder.setToDefaults();
            }
        } else {
            holder.setToDefaults();
        }
    }

    class RowHolder extends RecyclerView.ViewHolder{

        ImageView imgBank;
        TextView txtBankName;

        RowHolder(View itemView) {
            super(itemView);
            txtBankName = itemView.findViewById(R.id.txtBankName);
            imgBank = itemView.findViewById(R.id.imgBankLogo);
        }

        void setToDefaults() {
            txtBankName.setText(ctx.getString(R.string.error));
            imgBank.setImageResource(R.drawable.bnk_def);
        }
    }
}
