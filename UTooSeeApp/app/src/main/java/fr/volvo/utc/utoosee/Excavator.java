package fr.volvo.utc.utoosee;

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


}
