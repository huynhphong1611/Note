package com.bkav.android.notetablet;


import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NoteListFragment.OnSelectedListener ,  NoteAdapter.ItemClickListener {
    final static String KEY_SAVE_DETAIL="key_save_detail";
    final  static  String KEY_SAVE_TITLE="key_save_title";
    static final String NOTE="note";
    private NoteListFragment mNoteListFragment;
    private NoteDetailFragment mNoteDetailFragment;
    final static String LOG="Trang thai";

    private Note mNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View viewCheckTable = findViewById(R.id.notedetailfragment);

        initFragmentList();
        if(savedInstanceState!=null & viewCheckTable !=null)
        {
            addFragmentDetail();
            mNoteDetailFragment.updateFragment((Note) savedInstanceState.getParcelable(NOTE));

        }else if(viewCheckTable !=null)
        {
            addFragmentDetail();
            mNoteDetailFragment.updateFragment(mNote);

        }


    }
    // dung cho thanh actionbar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_action,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.add:{
                Intent intent=new Intent(this,ActivityDetail.class);
                startActivity(intent);
                Log.v(LOG,"add");
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    //Add dong fragment list note
    private void initFragmentList() {
        mNoteListFragment=new NoteListFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.notelistfragment,mNoteListFragment);
        fragmentTransaction.commit();
        //them fragment tinh
//        mNoteListFragment = (NoteListFragment)
//                getSupportFragmentManager().findFragmentById(R.id.notelistfragment);
    }
    //add dong detail note
    private void addFragmentDetail() {
        mNoteDetailFragment=new NoteDetailFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.notedetailfragment,mNoteDetailFragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onSelected(Note note) {
        mNote=note;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(NOTE,mNote);
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick,Note note) {
        View viewCheckTable = findViewById(R.id.notedetailfragment);
        if (viewCheckTable != null) {
            addFragmentDetail();
            mNoteDetailFragment.updateFragment(note);

        } else {
            Intent intent = new Intent(this, ActivityDetail.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(NOTE, mNote);
            intent.putExtra(ActivityDetail.KEY_NOTE, bundle);
            startActivity(intent);
        }
    }
}