package model;

import java.time.LocalDate;

public class Penalizare {
    private int id;
    private Cititor cititor;
    private Imprumut imprumut;
    private double suma;
    private String motiv;
    private boolean platita;
    private LocalDate dataPenalizare;

    public Penalizare(int id, Cititor cititor, Imprumut imprumut, double suma, String motiv, boolean platita, LocalDate dataPenalizare) {
        this.id = id;
        this.cititor = cititor;
        this.imprumut = imprumut;
        this.suma = suma;
        this.motiv = motiv;
        this.platita = platita;
        this.dataPenalizare = dataPenalizare;
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

    public Imprumut getImprumut() {
        return imprumut;
    }

    public void setImprumut(Imprumut imprumut) {
        this.imprumut = imprumut;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public String getMotiv() {
        return motiv;
    }

    public void setMotiv(String motiv) {
        this.motiv = motiv;
    }

    public boolean isPlatita() {
        return platita;
    }

    public void setPlatita(boolean platita) {
        this.platita = platita;
    }

    public LocalDate getDataPenalizare() {
        return dataPenalizare;
    }

    public void setDataPenalizare(LocalDate dataPenalizare) {
        this.dataPenalizare = dataPenalizare;
    }

    public void platestePenalizare() {
        this.platita = true;
    }

    @Override
    public String toString() {
        return "*****************\nPenalizare" +"\nid=" + id +"\ncititor=" + cititor.getNume() +"\nimprumutId=" + imprumut.getId() +"\nsuma=" + suma +"\nmotiv=" + motiv +"\nplatita=" + platita +"\ndataPenalizare=" + dataPenalizare +"\n*****************";
    }
}