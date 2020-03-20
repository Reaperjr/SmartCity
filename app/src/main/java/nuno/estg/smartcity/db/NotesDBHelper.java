package nuno.estg.smartcity.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import nuno.estg.smartcity.ui_notes.notes.NotesModel;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NotesDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 1;

    public NotesDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NotesContract.SQL_CREATE_ENTRIES);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NotesContract.NotesEntry.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public ArrayList<NotesModel> fill() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<NotesModel> details = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NotesContract.NotesEntry.TABLE_NAME + " ORDER BY " + NotesContract.NotesEntry.COLUMN_TIMESTAMP + " DESC;", null);
        while (cursor.moveToNext()) {
            details.add(new NotesModel(
                    cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_ASSUNTO)),
                    cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_MORADA)),
                    cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_LOCAL)),
                    cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_CODPOSTAL)),
                    cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DATA)),
                    cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_OBSERVACOES)),
                    cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry._ID))
            ));
        }
        cursor.close();
        return details;
    }
}
