package model;

public class Bibliotecar extends Persoana{
    private String idAngajat;
    private double salariu;
    public Bibliotecar(){}
    public Bibliotecar(int id, String nume, String email, String idAngajat, double salariu){
        super(id,nume,email);
        this.idAngajat=idAngajat;
        this.salariu=salariu;
    }
    public String getIdAngajat(){
        return this.idAngajat;
    }
    public double getSalariu(){
        return this.salariu;
    }
    public void setIdAngajat(String idAngajat){
        this.idAngajat=idAngajat;
    }
    public void setSalariu(double salariu){
        this.salariu=salariu;
    }
    @Override public String toString(){
        return "*****************\nBibliotecar\nid="+this.id+"\nnume="+this.nume+"\nemail="+this.email+"\nidAngajat="+this.idAngajat+"\nsalariu="+this.salariu+"\n*****************";
    }
}
