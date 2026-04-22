package model;

public class Sectiune {
    private int id;
    private String nume;

    public Sectiune(){}

    public Sectiune(int id, String nume){
        this.id=id;
        this.nume=nume;
    }
    public int getId(){
        return this.id;
    }
    public String getNume(){
        return this.nume;
    }
    public void setId(int id){
        this.id=id;
    }
    public void setNume(String nume){
        this.nume=nume;
    }
    @Override public String toString(){
        return "*****************\nSectiune\n id="+this.id+"\nnume="+this.nume+"\n*****************";
    }
}
