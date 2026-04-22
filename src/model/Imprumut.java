package model;
import java.time.LocalDate;
public class Imprumut {
    private int id;
    private Cititor cititor;
    private Bibliotecar bibliotecar;
    private Carte carte;
    private LocalDate dataImprumut;
    private LocalDate dataReturnare;
    private boolean activ;

    public Imprumut(int id, Cititor cititor, Bibliotecar bibliotecar, Carte carte, LocalDate dataImprumut, LocalDate dataReturnare, boolean activ){
        this.id=id;
        this.cititor=cititor;
        this.bibliotecar=bibliotecar;
        this.carte=carte;
        this.dataImprumut=dataImprumut;
        this.dataReturnare=dataReturnare;
        this.activ=activ;
    }
    public int getId(){
        return this.id;
    }
    public Cititor getCititor(){
        return this.cititor;
    }
    public Bibliotecar getBibliotecar(){
        return this.bibliotecar;
    }
    public Carte getCarte(){
        return this.carte;
    }
    public LocalDate getDataImprumut(){
        return this.dataImprumut;
    }
    public LocalDate getDataReturnare(){
        return this.dataReturnare;
    }
    public boolean getActiv(){
        return this.activ;
    }
    public void setDataReturnare(LocalDate dataReturnare){
        this.dataReturnare=dataReturnare;
    }
    public void setActive(boolean activ){
        this.activ=activ;
    }
    @Override
    public String toString(){
        return "*****************\nImprumut\nid="+this.id+"\ncititor="+this.cititor.getNume()+"\nbibliotecar="+this.bibliotecar.getNume()+"\ncarte="+this.carte.getTitlu()+"\ndataImprumut="+this.dataImprumut+"\ndataReturnare"+this.dataReturnare+"\nactiv="+this.activ+"\n*****************";
    }
}
