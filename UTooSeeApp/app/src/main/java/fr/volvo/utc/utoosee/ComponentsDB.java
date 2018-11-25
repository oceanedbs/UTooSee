package fr.volvo.utc.utoosee;

import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class ComponentsDB {
    private static final int VERSION_BDD = 1;
    private static final String NAME_BDD = "components.db";

    private static final String TABLE_COMPONENTS = "Components";
    private static final String COL_NUM = "number";
    private static final int NUM_COL_NUM = 0;
    private static final String COL_NAME = "name";
    private static final int NUM_COL_NAME = 1;
    private static final String COL_DATE="date";
    private static final int NUM_COL_DATE=2;
    private static final String COL_VEHICULE="vehicule";
    private static final int NUM_COL_VEHICULE=3;

    private SQLiteDatabase bdd;

    private ComponentsHelper db;

    public ComponentsDB(Context context){
        db=new ComponentsHelper(context);
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

    public long insertPiece(Components components){
        ContentValues values=new ContentValues();
        values.put(COL_NUM, components.getNumber());
        values.put(COL_NAME, components.getName());
        values.put(COL_DATE, components.getDate());
        values.put(COL_VEHICULE, components.getVehicule());
        return bdd.insert(TABLE_COMPONENTS, null, values);
    }

    private Components cursorToComponents(Cursor c){
        if(c.getCount()==0)
            return null;

        c.moveToFirst();
        Components components=new Components();
        components.setName(c.getString(NUM_COL_NAME));
        components.setNumber(c.getInt(NUM_COL_NUM));

        c.close();

        return components;
    }


    public Components getComponantsWithNum(int num){
        String Snum=""+num;

        String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                COL_NAME, TABLE_COMPONENTS, COL_VEHICULE);

        Cursor c=bdd.rawQuery(usersSelectQuery, new String[]{Snum});
        // rawQuery("SELECT  name, number FROM Components  WHERE vehicule = ? ", new String[] { Snum });
        return cursorToComponents(c);
    }

}
