package org.kasabeh.raghamcardbank.ui;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Saeed on 06/11/2018.
 */
public abstract class AdapUI<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected Context ctx;
    protected Cursor cr;

    public AdapUI(Context ctx, Cursor cr){
        this.ctx = ctx;
        this.cr = cr;
    }

    public void changeCursor(Cursor cr){
        this.cr = cr;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cr.getCount();
    }

    public abstract Object getItemAt(int pos);
}
