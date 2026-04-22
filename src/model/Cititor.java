package model;

public class Cititor extends Persoana{
    private String tipAbonament;
    public Cititor(){}

    public Cititor (int id, String nume, String email, String tipAbonament){
        super(id, nume, email);
        this.tipAbonament=tipAbonament;
    }
    public String getTipAbonament(){
        return this.tipAbonament;
    }
    public void setTipAbonament(String tipAbonament){
        this.tipAbonament=tipAbonament;
    }
    @Override public String toString(){
        return "*****************\nCititor\n id="+this.id+"\nnume="+this.nume+"\nemail="+this.email+"\n*****************";
    }

}
