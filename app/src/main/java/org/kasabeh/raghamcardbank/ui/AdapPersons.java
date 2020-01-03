package org.kasabeh.raghamcardbank.ui;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.kasabeh.raghamcardbank.R;
import org.kasabeh.raghamcardbank.db.PersonDB;
import org.kasabeh.raghamcardbank.logical.Person;

/**
 * Created by Saeed on 06/11/2018.
 */
public class AdapPersons extends AdapUI<AdapPersons.RowHolder> {

    public AdapPersons(Context ctx, Cursor cr){
        super(ctx, cr);
    }

    @Override
    public RowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.row_persons, parent, false);
        return new RowHolder(v);
    }

    @Override
    public void onBindViewHolder(RowHolder holder, int position) {
        if (cr.moveToPosition(position)){
            try {
                Person p = new PersonDB(ctx).loadObject(cr);
                holder.txtPersonName.setText(p.getFullName());
            } catch (Exception e) {
                holder.setToDefaults();
            }
        } else {
            holder.setToDefaults();
        }
    }

    @Override
    public Person getItemAt(int pos){
        if (cr.moveToPosition(pos)){
            try {
                Person p = new PersonDB(ctx).loadObject(cr);
                return p;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public class RowHolder extends RecyclerView.ViewHolder {

        public TextView txtPersonName;

        public RowHolder(View row) {
            super(row);
            txtPersonName = row.findViewById(R.id.txtPersonName);
        }

        public void setToDefaults() {
            txtPersonName.setText(ctx.getString(R.string.error));
        }
    }
}
