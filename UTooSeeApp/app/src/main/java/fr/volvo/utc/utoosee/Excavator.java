package fr.volvo.utc.utoosee;

import android.database.sqlite.SQLiteOpenHelper;

import android.database.sqlite.SQLiteDatabase;


public class Excavator {

    private int SerieNumber;
    private String Name;

    public Excavator(){}
    public Excavator(int num, String name){
        this.SerieNumber=num;
        this.Name=name;
    }

    public void setNumber(int num){
        this.SerieNumber=num;
    }

    public int getNumber(){
        return this.SerieNumber;
    }

    public void setName(String name){
        this.Name=name;
    }

    public String getName(){
        return this.Name;
    }

 /*   public void addExcavator(Excavator excavator){
        SQLiteDatabase bdd=db.getWritableDatabase();
    }*/


}
