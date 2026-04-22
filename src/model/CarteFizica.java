package model;

public class CarteFizica extends Carte{
    private int nrPagini;
    public CarteFizica(int id, String titlu, Autor autor, Sectiune sectiune, int anPublicatie, boolean disponibila, int nrPagini){
        super( id, titlu, autor, sectiune, anPublicatie, disponibila);
        this.nrPagini=nrPagini;
    }
    public int getNrPagini(){
        return this.nrPagini;
    }
    @Override public String toString(){
        return "*****************\nCarteFizica"+"\n id="+this.id+"\ntitlu="+this.titlu+"\nautor="+this.autor.getNume()+"\nsectiune="+this.sectiune.getNume()+"\nanPublicatie="+this.anPublicatie+"\ndisponibila="+this.disponibila+"\nnrPagini="+this.nrPagini+"\n*****************";
    }
}
