package com.bkav.android.notetablet;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import java.util.ArrayList;

public class NoteListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    final static int ID_LOADER=1;
    final static String LOG="trang thai Frag_List";
    OnSelectedListener mCallBack;
    private RecyclerView mRecyclerView;
    private NoteAdapter mNoteAdapter;
    private Cursor mCursor;
    public static CursorLoader cursorLoader;



    static interface OnSelectedListener {
        void onSelected(Note note);
    }

    public static NoteListFragment getInstance() {
        return new NoteListFragment();
    }

    private ArrayList<Note> mArrNote = new ArrayList<>();

    public NoteListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallBack = (OnSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSelectedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewlistnote);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Note note = mNoteAdapter.getNoteItem(position);
                mCallBack.onSelected(note);
            }

            @Override
            public void onLongClick(View view, int position) {
                Note note=mNoteAdapter.getNoteItem(position);
                getContext().getContentResolver().delete(NoteContacts.CONTENT_URI
                        ,NoteContacts.ID+ "=?"
                        ,new String []{String.valueOf(note.getmId())});
                // cap nhat lai list
                NoteListFragment.cursorLoader.onContentChanged();
            }
        }));
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mNoteAdapter = new NoteAdapter(null, getContext());
        mNoteAdapter.setOnItemClickListener((NoteAdapter.ItemClickListener) getActivity());
        mRecyclerView.setAdapter(mNoteAdapter);
        Log.v(LOG,"onCreateView");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(ID_LOADER,null,this);


    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(ID_LOADER,null,this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        if(i==ID_LOADER){
             cursorLoader=new CursorLoader(getContext(),NoteContacts.CONTENT_URI
                    ,new String[]{NoteContacts.ID,NoteContacts.IMPORTANT,NoteContacts.TITLE_NOTE,NoteContacts.CONTENT_NOTE,NoteContacts.DATE_DONE,NoteContacts.PATHIMG}
                    ,null,null,NoteContacts.IMPORTANT + " DESC");
            return cursorLoader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mNoteAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mNoteAdapter.swapCursor(null);
    }

    }
