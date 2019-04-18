package com.bkav.android.notetablet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
    // TODO TrungTH copy ở đâu đấy?  đã debug để hiểu nó chưa ? cái long click không dùng ko cần tạo làm gì ?
    public interface ClickListener {

        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    private GestureDetector gestureDetector;
    ClickListener clickListener;

    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {

        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                // TODO TrungTH longCLick ko dùng
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null)
                    clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));

            }

        });

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e))
            clickListener.onClick(child, rv.getChildAdapterPosition(child));
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}

