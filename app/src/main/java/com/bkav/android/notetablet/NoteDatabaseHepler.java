package com.bkav.android.notetablet;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteDatabaseHepler extends SQLiteOpenHelper {
    static final String NOTE_DB_NAME="note_db_name";
    static final int NOTE_DB_VERSION=1;
    static final String TITLE_NOTE="title_note";
    static final String CONTENT_NOTE="content_note";
    static final String NOTE="note";
    static final String IMPORTANT="quan_trong";
    static final String PATHIMG="duong_dan_file";
    static final  String DATE_DONE="date_done";
    static final String ID="id";
    private final String TAG = "DBManager";
    public final String DATABASE_CREATE="CREATE TABLE "
                + NOTE
                +"("
                + ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DATE_DONE + "TEXT,"
                + TITLE_NOTE + "TEXT,"
                + CONTENT_NOTE + "TEXT,"
                + IMPORTANT + "TEXT"
                + ");";
    public final String DATABASE_CREATE_A="CREATE TABLE "
            + NOTE
            +"("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "date_done TEXT,"
            + "title_note TEXT,"
            + "content_note TEXT,"
            +  "quan_trong TEXT,"
            + "duong_dan_file TEXT"
            + ");";
    public NoteDatabaseHepler(Context context) {
        super(context, NOTE_DB_NAME, null, NOTE_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_A);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion==1)
        {
           db.execSQL("ALTER TABLE "+NOTE+" ADD " + PATHIMG +" TEXT;");
        }
    }
}
