package com.bkav.android.notetablet;

import android.content.Context;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class NoteAdapter extends BaseCursorAdapter<NoteAdapter.ViewHolder> {
    private Context mContext;
    private ItemClickListener mItemClickListener;
    public void setOnItemClickListener(ItemClickListener mItemClickListener)
    {
        this.mItemClickListener = mItemClickListener;
    }
    public interface ItemClickListener {
        void onClick(View view, int position,boolean isLongClick,Note note);
    }

    public NoteAdapter(Cursor cursor, Context context) {
        super(cursor);
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final View itemView = layoutInflater.inflate(R.layout.note, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, Cursor cursor) {
        if(cursor!=null)
        {   final int id=cursor.getInt(cursor.getColumnIndexOrThrow(NoteContacts.ID));
            final int important= Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(NoteContacts.IMPORTANT)));
            final String title =cursor.getString(cursor.getColumnIndexOrThrow(NoteContacts.TITLE_NOTE));
            final String content =cursor.getString(cursor.getColumnIndexOrThrow(NoteContacts.CONTENT_NOTE));
            final String date =cursor.getString(cursor.getColumnIndexOrThrow(NoteContacts.DATE_DONE));
            viewHolder.txtTitle.setText(title);
            viewHolder.txtDetail.setText(content);
            viewHolder.txtDate.setText(date);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mItemClickListener !=null) {
                        Note note=new Note(important,id,title,content,date);
                        mItemClickListener.onClick(v,viewHolder.getAdapterPosition(),false,note);
                    }
                }
            });
        }

    }
    public Note getNoteItem(int position)
    {
        Cursor cursor = getItem(position);
        Note note=new Note(
                 cursor.getInt(cursor.getColumnIndexOrThrow(NoteContacts.IMPORTANT))
                ,Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(NoteContacts.ID)))
                ,cursor.getString(cursor.getColumnIndexOrThrow(NoteContacts.TITLE_NOTE))
                ,cursor.getString(cursor.getColumnIndexOrThrow(NoteContacts.CONTENT_NOTE))
                ,cursor.getString(cursor.getColumnIndexOrThrow(NoteContacts.DATE_DONE))
                ,cursor.getString(cursor.getColumnIndexOrThrow(NoteContacts.PATHIMG)));
        return note;
    }
    @Override
    public void swapCursor(Cursor newCursor) {
        super.swapCursor(newCursor);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        TextView txtTitle;
        TextView txtDetail;
        TextView txtDate;
        private ItemClickListener mItemClickListener;
        public ViewHolder(final View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.titlenote);
            txtDetail=(TextView) itemView.findViewById(R.id.contentnote);
            txtDate=(TextView) itemView.findViewById(R.id.datedone);
        }
        @Override
        public void onClick (View v) {
            mItemClickListener.onClick(v,getAdapterPosition(),false,new Note());
        }

        @Override
        public boolean onLongClick(View v) {
            mItemClickListener.onClick(v,getAdapterPosition(),true,new Note());
            return true;
        }

    }



}
