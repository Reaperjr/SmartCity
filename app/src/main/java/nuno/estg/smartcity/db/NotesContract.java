package nuno.estg.smartcity.db;

import android.provider.BaseColumns;

public class NotesContract {

    private NotesContract() {
    }

    public static final class NotesEntry implements BaseColumns {
        public static final String TABLE_NAME = "Notes";
        public static final String COLUMN_ASSUNTO = "Assunto";
        public static final String COLUMN_MORADA = "Morada";
        public static final String COLUMN_LOCAL = "Localidade";
        public static final String COLUMN_CODPOSTAL = "CodPostal";
        public static final String COLUMN_DATA = "Data";
        public static final String COLUMN_OBSERVACOES = "Observacoes";
        public static final String COLUMN_TIMESTAMP = "Timestamp";
    }


    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NotesContract.NotesEntry.TABLE_NAME + "(" +
                    NotesContract.NotesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NotesContract.NotesEntry.COLUMN_ASSUNTO + " TEXT, " +
                    NotesContract.NotesEntry.COLUMN_MORADA + " TEXT, " +
                    NotesContract.NotesEntry.COLUMN_LOCAL + " TEXT, " +
                    NotesContract.NotesEntry.COLUMN_CODPOSTAL + " TEXT, " +
                    NotesContract.NotesEntry.COLUMN_DATA + " TEXT, " +
                    NotesContract.NotesEntry.COLUMN_OBSERVACOES + " TEXT, " +
                    NotesContract.NotesEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ");";
}
