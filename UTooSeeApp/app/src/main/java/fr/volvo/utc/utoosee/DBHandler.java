package fr.volvo.utc.utoosee;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;

import android.content.ContentValues;

import android.database.Cursor;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "Excavator.db";

    public static final String TABLE_NAME = "Excavator";

    public static final String COLUMN_ID = "SerieNumber";

    public static final String COLUMN_NAME = "Name";

    //initialize the database
    private SQLiteDatabase bdd;


    public DBHandler(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        String CREATE_EXCAVATOR_TABLE="CREATE TABLE"+TABLE_NAME+"("+COLUMN_ID+"INTEGER PRIMARY KEY,"+COLUMN_NAME+"TEXT"+")";
        db.execSQL(CREATE_EXCAVATOR_TABLE);
    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }


  /*  public String loadHandler() {}

    public void addHandler(Excavator excavator) {}

    public Excavator findHandler(String name) {}

    public boolean deleteHandler(int ID) {}

    public boolean updateHandler(int ID, String name) {}*/

}