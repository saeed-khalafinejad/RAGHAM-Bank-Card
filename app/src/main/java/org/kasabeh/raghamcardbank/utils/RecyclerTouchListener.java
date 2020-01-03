package org.kasabeh.raghamcardbank.utils;
/**
 * Copyright (C) 2018/1397  <Saeed Khalafinejad, Kasabeh group>
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Saeed on 06/11/2018.
 */
public abstract class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gd;
    private RecyclerView rv;

    public abstract void onItemClick(int pos);
    public abstract void onItemLongClick(int pos);

    public RecyclerTouchListener(Context ctx, RecyclerView theRecyclerView){
        rv = theRecyclerView;

        gd = new GestureDetector(ctx, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child==null) return true;
                int pos = rv.getChildAdapterPosition(child);
                onItemClick(pos);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child==null) return;
                int pos = rv.getChildAdapterPosition(child);
                onItemLongClick(pos);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gd.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {}
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}

}
