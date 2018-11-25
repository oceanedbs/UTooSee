package fr.volvo.utc.utoosee;

import java.util.Date;


public class Components {
    private int number;
    private String name;
    private String date;
    private int vehicule;

    public Components(){}
    public Components(int num, String Pname, String Pdate, int PVehicule){
        this.number=num;
        this.name=Pname;
        this.date=Pdate;
        this.vehicule=PVehicule;
    }

    public void setNumber(int num){
        this.number=num;
    }

    public int getNumber(){
        return this.number;
    }

    public void setName(String Pname){
        this.name=Pname;
    }

    public String getName(){
        return this.name;
    }

    public void setDate(String Pdate){
        this.date=Pdate;
    }

    public String getDate(){
        return  this.date;
    }

    public void setVehicule(int Pvehicule){
        this.vehicule=Pvehicule;
    }

    public int getVehicule(){
        return  this.vehicule;
    }
}
