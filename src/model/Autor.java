package model;

public class Autor {
    private int id;
    private String nume;
    private String nationalitate;

    public Autor(){}

    public Autor(int id, String nume, String nationalitate){
        this.id=id;
        this.nume=nume;
        this.nationalitate=nationalitate;
    }
    public int getId(){
        return this.id;
    }
    public String getNume(){
        return this.nume;
    }
    public String getNationalitate(){
        return this.nationalitate;
    }
    public void setNume(String nume){
        this.nume=nume;
    }
    public void setNationalitate(String nationalitate){
        this.nationalitate=nationalitate;
    }
    @Override public String toString(){
        return "*****************\nAutor\n id="+this.id+"\nnume="+this.nume+"\nnationalitate="+this.nationalitate+"\n*****************";
    }
}
