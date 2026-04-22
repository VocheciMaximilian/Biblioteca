package model;

public abstract class Persoana {
    protected int id;
    protected String nume;
    protected String email;

    public Persoana(){}
    public Persoana(int id, String nume, String email){
        this.id=id;
        this.nume=nume;
        this.email=email;
    }
    public int getId(){
        return this.id;
    }
    public String getNume(){
        return this.nume;
    }
    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setNume(String nume){
        this.nume=nume;
    }
}
