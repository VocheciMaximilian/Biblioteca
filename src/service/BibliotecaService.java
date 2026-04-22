package service;
import model.Bibliotecar;
import model.Carte;
import model.CarteDigitala;
import model.CarteFizica;
import model.Cititor;
import model.Imprumut;

import java.time.LocalDate;
import java.util.*;
public class BibliotecaService {
    private List<Carte> carti;
    private Map<Integer, Cititor> cititori;
    private Map<Integer, Bibliotecar> bibliotecari;
    private List<Imprumut> imprumuturi;
    private Set<Carte> cartiSortate;

    public BibliotecaService(){
        this.carti=new ArrayList<>();
        this.cititori=new HashMap<>();
        this.bibliotecari=new HashMap<>();
        this.imprumuturi=new ArrayList<>();
        this.cartiSortate=new TreeSet<>();
    }
}
