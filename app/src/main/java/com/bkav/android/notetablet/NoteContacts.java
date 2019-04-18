package com.bkav.android.notetablet;

import android.net.Uri;

public class NoteContacts {
    public static final String NOTE_DB_NAME="note_db_name";
    public static final String TITLE_NOTE="title_note";
    public static final String CONTENT_NOTE="content_note";
    public static final String NOTE="note";
    public static final  String DATE_DONE="date_done";
    public static final String ID="id";
    public static final String IMPORTANT="quan_trong";
    public static final String PATHIMG="duong_dan_file";

    static final String SINGLE_NOTE_MIME_TYPE =
            "vnd.android.cursor.item/vnd.com.bkav.android.notetablet.note";
    static final String MULTIPLE_NOTES_MIME_TYPE =
            "vnd.android.cursor.dir/vnd.com.bkav.android.notetablet.note";

    public static final String AUTHORITY = "com.bkav.android.notetablet.NoteProvider";
    public static final String CONTENT_PATH =  "note";
    public static final String URL = "content://" + AUTHORITY + "/" + CONTENT_PATH;
    public static final Uri CONTENT_URI = Uri.parse(URL);
}
