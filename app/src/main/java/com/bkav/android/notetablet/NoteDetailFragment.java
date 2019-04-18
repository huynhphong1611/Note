package com.bkav.android.notetablet;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import java.util.Calendar;
import java.util.TimeZone;


public class NoteDetailFragment extends Fragment  {
    public final static int QUANTRONG=1;
    public final static int KHQUANTRONG=0;
    final static String LOG="Trang thai frag_detail";
    private EditText mDetail;
    private EditText mTitel ;
    private CheckBox mQuanTrong;
    private ImageView mImageView;
    private Note mNote;
    private ActivityDetail mActivityDetail;
    public NoteDetailFragment() {

    }

    public static NoteDetailFragment getInstance()
    {
        return new NoteDetailFragment();
    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_note_detail,container,false);
        mDetail=(EditText) view.findViewById(R.id.detail);
        mTitel=(EditText) view.findViewById(R.id.titlenote);
        mQuanTrong=(CheckBox) view.findViewById(R.id.checkBox);
        mImageView=(ImageView) view.findViewById(R.id.imagenote);

        if(mNote!=null)
        {

            if(mNote.getmImportant()==QUANTRONG)
            {
                mQuanTrong.setChecked(true);
            }else mQuanTrong.setChecked(false);
            mTitel.setText(mNote.getmTitle());
            mDetail.setText(mNote.getmDetail());

            Glide.with(this)
                    .load(mNote.getmPathImg())
                    .into(mImageView);
        }

        Log.v(LOG,"onCreateView");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(ActivityDetail.mPathImgLoaded!=null)
        {
            Glide.with(this)
                    .load(ActivityDetail.mPathImgLoaded)
                    .into(mImageView);
        }
    }

    public void updateFragment(Note note){
        Log.v(LOG,"update");
        mNote=note;
    }
    public Note addNote()
    {
        int checkQuanTrong;
        if(mNote==null)
        {
            if(mTitel.getText()!=null||mDetail.getText()!=null)
            {
                Calendar calendar=Calendar.getInstance(TimeZone.getDefault());
                if(mQuanTrong.isChecked())
                {
                    checkQuanTrong=QUANTRONG;
                }else checkQuanTrong=KHQUANTRONG;
                String path = ActivityDetail.mPathImgLoaded;
                String title=mTitel.getText().toString();
                String detail=mDetail.getText().toString();
                String date= String.valueOf(calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR));
                Note note=new Note(checkQuanTrong,title,detail,date,path);
                return note;
            }
        }else{
            if(mTitel.getText()!=null||mDetail.getText()!=null)
            {
                Calendar calendar=Calendar.getInstance();
                if(mQuanTrong.isChecked())
                {
                    checkQuanTrong=QUANTRONG;
                }else checkQuanTrong=KHQUANTRONG;
                String path = ActivityDetail.mPathImgLoaded;
                String title=mTitel.getText().toString();
                String detail=mDetail.getText().toString();
                String date= String.valueOf(calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR));
                Note note=new Note(checkQuanTrong,mNote.getmId(),title,detail,date,path);
                return note;
            }
        }

        return null;
    }

}
