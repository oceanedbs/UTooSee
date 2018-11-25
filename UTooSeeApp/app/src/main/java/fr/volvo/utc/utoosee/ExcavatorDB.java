package fr.volvo.utc.utoosee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ExcavatorDB {
    private static final int VERSION_BDD = 1;
    private static final String NAME_BDD = "excavator.db";

    private static final String TABLE_EXCAVATOR = "table_livres";
    private static final String COL_ID = "SerieNum";
    private static final int NUM_COL_NUM = 0;
    private static final String COL_NAME = "Name";
    private static final int NUM_COL_NAME = 1;

    private SQLiteDatabase bdd;

    private DBHandler db;

    public ExcavatorDB(Context context){
        db=new DBHandler(context);
    }

    public void open(){
        bdd=db.getWritableDatabase();
    }

    public void close(){
        bdd.close();
    }

    public SQLiteDatabase getDB(){
        return bdd;
    }

    public long instertExcavator(Excavator excavator){
        ContentValues values=new ContentValues();
        values.put(COL_ID, excavator.getNumber());
        values.put(COL_NAME, excavator.getNumber());
        return bdd.insert(TABLE_EXCAVATOR, null, values);
    }

    private Excavator cursorToExcavator(Cursor c){
        if(c.getCount()==0)
            return null;

        c.moveToFirst();
        Excavator excavator=new Excavator();
        excavator.setName(c.getString(NUM_COL_NAME));
        excavator.setNumber(c.getInt(NUM_COL_NUM));

        c.close();

        return excavator;
    }


    public Excavator getExcavatorWithNum(int num){
        Cursor c=bdd.rawQuery("SELECT Name FROM Excavator WHERE Num=? ", new String[] {num + ""});
        return cursorToExcavator(c);
    }

}
