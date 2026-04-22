package model;

public class Abonament {
    private int id;
    private String tip;
    private double pret;
    private int durataLuni;
    private int maxImprumuturi;

    public Abonament(int id, String tip, double pret, int durataLuni, int maxImprumuturi) {
        this.id = id;
        this.tip = tip;
        this.pret = pret;
        this.durataLuni = durataLuni;
        this.maxImprumuturi = maxImprumuturi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public int getDurataLuni() {
        return durataLuni;
    }

    public void setDurataLuni(int durataLuni) {
        this.durataLuni = durataLuni;
    }

    public int getMaxImprumuturi() {
        return maxImprumuturi;
    }

    public void setMaxImprumuturi(int maxImprumuturi) {
        this.maxImprumuturi = maxImprumuturi;
    }

    @Override
    public String toString() {
        return "*****************\nAbonament" +"\nid=" + id +"\ntip=" + tip +"\npret=" + pret +"\ndurataLuni=" + durataLuni +"\nmaxImprumuturi=" + maxImprumuturi +"\n*****************";
    }
}