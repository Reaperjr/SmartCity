package nuno.estg.smartcity.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class NotesManagerDB {

        Context c;

        SQLiteDatabase db;
        NotesDBHelper helper;

        public NotesManagerDB(Context c) {
            this.c = c;
            helper= new NotesDBHelper(c);
        }

        //OPEN DATABASE
        public NotesManagerDB openDB()
        {
            try {
                db=helper.getWritableDatabase();

            }catch (SQLException e)
            {
                e.printStackTrace();
            }

            return this;
        }

        //CLOSE DATABASE
        public void closeDB()
        {
            try {
                helper.close();

            }catch (SQLException e)
            {
                e.printStackTrace();
            }


        }

        //INSERT
        public long insert(String assunto,String rua, String local, String codpostal, String data, String obs)
        {
            try
            {
                ContentValues cv=new ContentValues();
                cv.put(NotesContract.NotesEntry.COLUMN_ASSUNTO, assunto);
                cv.put(NotesContract.NotesEntry.COLUMN_MORADA, rua);
                cv.put(NotesContract.NotesEntry.COLUMN_LOCAL, local);
                cv.put(NotesContract.NotesEntry.COLUMN_CODPOSTAL, codpostal);
                cv.put(NotesContract.NotesEntry.COLUMN_DATA, data);
                cv.put(NotesContract.NotesEntry.COLUMN_OBSERVACOES, obs);

                return db.insert(NotesContract.NotesEntry.TABLE_NAME, null,cv);

            }catch (SQLException e)
            {
                e.printStackTrace();
            }

            return 0;
        }
    public long update(int id,String assunto,String rua, String local, String codpostal, String data, String obs)
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put(NotesContract.NotesEntry.COLUMN_ASSUNTO, assunto);
            cv.put(NotesContract.NotesEntry.COLUMN_MORADA, rua);
            cv.put(NotesContract.NotesEntry.COLUMN_LOCAL, local);
            cv.put(NotesContract.NotesEntry.COLUMN_CODPOSTAL, codpostal);
            cv.put(NotesContract.NotesEntry.COLUMN_DATA, data);
            cv.put(NotesContract.NotesEntry.COLUMN_OBSERVACOES, obs);

            return db.update(NotesContract.NotesEntry.TABLE_NAME,cv,NotesContract.NotesEntry._ID + " = ?",new String[]{String.valueOf(id)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }


    public int delete(int position){

            try{
                return db.delete(NotesContract.NotesEntry.TABLE_NAME, NotesContract.NotesEntry._ID + " = ?",new String[]{String.valueOf(position)});
            }catch (SQLException e)
            {
                e.printStackTrace();
            }
            return 0;
        }
    public Cursor getAllNotes()
    {
        return db.query(NotesContract.NotesEntry.TABLE_NAME,null,null,null,null,null, NotesContract.NotesEntry.COLUMN_TIMESTAMP + " DESC");

    }



}
