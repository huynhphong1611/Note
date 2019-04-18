package com.bkav.android.notetablet;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.support.v4.content.FileProvider.getUriForFile;
import static java.security.AccessController.getContext;

public class ActivityDetail extends AppCompatActivity {
    final static String LOG="trang_thai_activ_detail";
    static final String KEY_NOTE="key_note";
    public final static int REQUEST_IMAGE=100;
    public final static int REQUEST_IMAGE_CAMERA=101;
    private NoteDetailFragment mNoteDetailFragment;
    private NoteDatabaseHepler mNoteDatabaseHepler;
    public static  String mPathImgLoaded;
    public static Bitmap mImageBitmap;
    private boolean mCheckSaveNote=false;

    public String getmPathImgLoaded() {
        return mPathImgLoaded;
    }

    public ActivityDetail() {
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        addFragmentDetail();
        Bundle bundle=getIntent().getParcelableExtra(KEY_NOTE);
        if(bundle!=null)
        {
            Note note=bundle.getParcelable(MainActivity.NOTE);

            mNoteDetailFragment.updateFragment(note);
            Log.v(LOG,"co note tu activity gui sang");
        }
        Log.v(LOG,"onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId ())
        {
            case R.id.save:{
                saveNote();
                mCheckSaveNote=true;
                return true;
            }
            case R.id.addimg:{
                choosePicture();
                return true;
            }
            case R.id.addimgcamera:{
                takeCameraImg();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public void saveNote()
    {
        mNoteDatabaseHepler=new NoteDatabaseHepler(this);
        Note note=(Note) mNoteDetailFragment.addNote();
        ContentValues contentValues=new ContentValues();
        if(note.getmTitle().isEmpty() && note.getmDetail().isEmpty())
        {
            return;
        }else{
            if(note.getmId()==0){
                    if(note.getmImportant()==1) contentValues.put(NoteContacts.IMPORTANT,NoteDetailFragment.QUANTRONG);
                    else contentValues.put(NoteContacts.IMPORTANT,NoteDetailFragment.KHQUANTRONG);
                    contentValues.put(NoteContacts.TITLE_NOTE,note.getmTitle());
                    contentValues.put(NoteContacts.CONTENT_NOTE,note.getmDetail());
                    contentValues.put(NoteContacts.DATE_DONE,note.getmDate());
                    contentValues.put(NoteContacts.PATHIMG,note.getmPathImg());
                    getContentResolver().insert(NoteContacts.CONTENT_URI,contentValues);
                    Toast.makeText(this, "Save Done", Toast.LENGTH_SHORT).show();
                }
            else{
                if(note.getmImportant()==1) contentValues.put(NoteContacts.IMPORTANT,NoteDetailFragment.QUANTRONG);
                else contentValues.put(NoteContacts.IMPORTANT,NoteDetailFragment.KHQUANTRONG);
                contentValues.put(NoteContacts.TITLE_NOTE,note.getmTitle());
                contentValues.put(NoteContacts.CONTENT_NOTE,note.getmDetail());
                contentValues.put(NoteContacts.DATE_DONE,note.getmDate());
                contentValues.put(NoteContacts.PATHIMG,note.getmPathImg());
                getContentResolver().update(NoteContacts.CONTENT_URI,contentValues,NoteContacts.ID+"=?"
                        ,new String[]{String.valueOf(note.getmId())});
                Toast.makeText(this, "Update Done", Toast.LENGTH_SHORT).show();
            }

        }


    }

    //ad dong fragment detail
    public void addFragmentDetail()
    {
        mNoteDetailFragment=new NoteDetailFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detail_note_fragment,mNoteDetailFragment);
        fragmentTransaction.commit();

    }
    //chon anh
    public void choosePicture()
    {

        Intent intent=new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQUEST_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mPathImgLoaded= cursor.getString(columnIndex);
                cursor.close();

        }
        if (requestCode == REQUEST_IMAGE_CAMERA && resultCode == RESULT_OK && null != data) {

        }
    }
    // lay anh cammera
    public void takeCameraImg()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.bkav.android.notetablet.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAMERA);
            }
        }

    }
    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        mPathImgLoaded = image.getAbsolutePath();
        return image;
    }


}
