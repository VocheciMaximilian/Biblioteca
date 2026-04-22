package model;

public class CarteDigitala extends Carte{
    private int marimeMB;
    public CarteDigitala(int id, String titlu, Autor autor, Sectiune sectiune, int anPublicatie, boolean disponibila, int marimeMB){
        super( id, titlu, autor, sectiune, anPublicatie, disponibila);
        this.marimeMB=marimeMB;
    }
    public int getMarimeMB(){
        return this.marimeMB;
    }
    @Override public String toString(){
        return "*****************\nCarteDigitala"+"\n id="+this.id+"\ntitlu="+this.titlu+"\nautor="+this.autor.getNume()+"\nsectiune="+this.sectiune.getNume()+"\nanPublicatie="+this.anPublicatie+"\ndisponibila="+this.disponibila+"\nmarimeMB="+this.marimeMB+"\n*****************";
    }
}
