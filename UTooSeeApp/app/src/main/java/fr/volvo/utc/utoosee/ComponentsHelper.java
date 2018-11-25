package fr.volvo.utc.utoosee;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;


public class ComponentsHelper extends SQLiteOpenHelper{


        private static final int DATABASE_VERSION = 3;

        private static final String DATABASE_NAME = "Components.db";

        public static final String TABLE_NAME = "Components";

        public static final String COLUMN_NUM = "number";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_DATE="date";
        public static final String COLUMN_VEHICULE="vehicule";

        //initialize the database
        private SQLiteDatabase bdd;


        public ComponentsHelper(Context context){

            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override

        public void onCreate(SQLiteDatabase db) {
            String CREATE_COMPONENT_TABLE="CREATE TABLE IF NOT EXISTS"+TABLE_NAME+" ("+COLUMN_NUM+" INTEGER PRIMARY KEY, "+COLUMN_NAME+" TEXT, "+ COLUMN_DATE + " TEXT, "+COLUMN_VEHICULE+" INTEGER)";
            db.execSQL(CREATE_COMPONENT_TABLE);
        }

        @Override

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }


  /*  public String loadHandler() {}

    public void addHandler(Excavator excavator) {}

    public Excavator findHandler(String name) {}

    public boolean deleteHandler(int ID) {}

    public boolean updateHandler(int ID, String name) {}*/

    }

