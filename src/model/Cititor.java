package model;

public class Cititor extends Persoana{
    private Abonament abonament;
    public Cititor(){}

    public Cititor (int id, String nume, String email, Abonament abonament){
        super(id, nume, email);
        this.abonament=abonament;
    }
    public String getTipAbonament(){
        return this.abonament.getTip();
    }
    public Abonament getAbonament(){
        return this.abonament;
    }
    public void setTipAbonament(Abonament abonament){
        this.abonament=abonament;
    }
    @Override public String toString(){
        return "*****************\nCititor\n id="+this.id+"\nnume="+this.nume+"\nemail="+this.email+"\n*****************";
    }

}
