package model;

import java.time.LocalDate;

public class Rezervare {
    private int id;
    private Cititor cititor;
    private Carte carte;
    private LocalDate dataRezervare;
    private String status;

    public Rezervare(int id, Cititor cititor, Carte carte, LocalDate dataRezervare, String status) {
        this.id = id;
        this.cititor = cititor;
        this.carte = carte;
        this.dataRezervare = dataRezervare;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cititor getCititor() {
        return cititor;
    }

    public void setCititor(Cititor cititor) {
        this.cititor = cititor;
    }

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public LocalDate getDataRezervare() {
        return dataRezervare;
    }

    public void setDataRezervare(LocalDate dataRezervare) {
        this.dataRezervare = dataRezervare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void anuleazaRezervare() {
        this.status = "Anulata";
    }

    public void finalizeazaRezervare() {
        this.status = "Finalizata";
    }

    @Override
    public String toString() {
        return "*****************\nRezervare" +"\nid=" + id +"\ncititor=" + cititor.getNume() +"\ncarte=" + carte.getTitlu() +"\ndataRezervare=" + dataRezervare +"\nstatus=" + status +"\n*****************";
    }
}