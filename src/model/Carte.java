package model;

public abstract class Carte implements Comparable<Carte>{
    protected int id;
    protected String titlu;
    protected Autor autor;
    protected Sectiune sectiune;
    protected int anPublicatie;
    protected boolean disponibila;

    public Carte(){}
    public Carte(int id, String titlu, Autor autor, Sectiune sectiune, int anPublicatie, boolean disponibila){
        this.id=id; 
        this.titlu=titlu;
        this.autor=autor;
        this.sectiune=sectiune;
        this.anPublicatie=anPublicatie;
        this.disponibila=disponibila;
    }

    public int getId(){
        return this.id;
    }
    public String getTitlu(){
        return this.titlu;
    }
    public Autor getAutor(){
        return this.autor;
    }
    public Sectiune getSectiune(){
        return this.sectiune;
    }
    public int getAnPublicatie(){
        return this.anPublicatie;
    }
    public boolean getDisponibila(){
        return this.disponibila;
    }

    @Override
    public int compareTo(Carte other){
        int cmp=this.titlu.compareToIgnoreCase(other.titlu);
        if(cmp!=0) return cmp;
        return Integer.compare(this.id, other.id);
    }
    @Override public String toString(){
        return "*****************\nCarte"+"\n id="+this.id+"\ntitlu="+this.titlu+"\nautor="+this.autor.getNume()+"\nsectiune="+this.sectiune.getNume()+"\nanPublicatie="+this.anPublicatie+"\ndisponibila="+this.disponibila+"\n*****************";
    }
}
