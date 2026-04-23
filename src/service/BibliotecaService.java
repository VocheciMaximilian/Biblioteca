package service;

import model.Abonament;
import model.Bibliotecar;
import model.Carte;
import model.Cititor;
import model.Imprumut;
import model.Penalizare;
import model.Rezervare;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class BibliotecaService {

    private List<Carte> carti;
    private Map<Integer, Cititor> cititori;
    private Map<Integer, Bibliotecar> bibliotecari;
    private List<Imprumut> imprumuturi;
    private Set<Carte> cartiSortate;

    private List<Abonament> abonamente;
    private List<Rezervare> rezervari;
    private List<Penalizare> penalizari;

    public BibliotecaService() {
        this.carti = new ArrayList<>();
        this.cititori = new HashMap<>();
        this.bibliotecari = new HashMap<>();
        this.imprumuturi = new ArrayList<>();
        this.cartiSortate = new TreeSet<>();

        this.abonamente = new ArrayList<>();
        this.rezervari = new ArrayList<>();
        this.penalizari = new ArrayList<>();
    }

    public void adaugaAbonament(Abonament abonament) {
        if (abonament == null) {
            System.out.println("Abonamentul este null.");
            return;
        }

        for (Abonament a : abonamente) {
            if (a.getId() == abonament.getId()) {
                System.out.println("Exista deja un abonament cu id-ul " + abonament.getId());
                return;
            }
        }

        abonamente.add(abonament);
        System.out.println("Abonament adaugat: " + abonament.getTip());
    }

    public void adaugaCarte(Carte carte) {
        if (carte == null) {
            System.out.println("Cartea este null.");
            return;
        }

        if (gasesteCarteDupaId(carte.getId()) != null) {
            System.out.println("Exista deja o carte cu id-ul " + carte.getId());
            return;
        }

        carti.add(carte);
        cartiSortate.add(carte);
        System.out.println("Carte adaugata: " + carte.getTitlu());
    }

    public void adaugaCititor(Cititor cititor) {
        if (cititor == null) {
            System.out.println("Cititorul este null.");
            return;
        }

        if (cititori.containsKey(cititor.getId())) {
            System.out.println("Exista deja un cititor cu id-ul " + cititor.getId());
            return;
        }

        cititori.put(cititor.getId(), cititor);
        System.out.println("Cititor adaugat: " + cititor.getNume());
    }

    public void adaugaBibliotecar(Bibliotecar bibliotecar) {
        if (bibliotecar == null) {
            System.out.println("Bibliotecarul este null.");
            return;
        }

        if (bibliotecari.containsKey(bibliotecar.getId())) {
            System.out.println("Exista deja un bibliotecar cu id-ul " + bibliotecar.getId());
            return;
        }

        bibliotecari.put(bibliotecar.getId(), bibliotecar);
        System.out.println("Bibliotecar adaugat: " + bibliotecar.getNume());
    }

    public void afiseazaCarti() {
        if (carti.isEmpty()) {
            System.out.println("Nu exista carti in biblioteca.");
            return;
        }

        System.out.println("=== TOATE CARTILE ===");
        for (Carte carte : carti) {
            System.out.println(carte);
        }
    }

    public void afiseazaCartiDisponibile() {
        boolean exista = false;
        System.out.println("=== CARTI DISPONIBILE ===");

        for (Carte carte : carti) {
            if (!esteCarteImprumutata(carte.getId())) {
                System.out.println(carte);
                exista = true;
            }
        }

        if (!exista) {
            System.out.println("Nu exista carti disponibile.");
        }
    }

    public void cautaCarteDupaTitlu(String titluCautat) {
        boolean gasita = false;
        System.out.println("=== REZULTATE CAUTARE DUPA TITLU ===");

        for (Carte carte : carti) {
            if (carte.getTitlu().toLowerCase().contains(titluCautat.toLowerCase())) {
                System.out.println(carte);
                gasita = true;
            }
        }

        if (!gasita) {
            System.out.println("Nu exista nicio carte ce contine titlul: " + titluCautat);
        }
    }

    public void afiseazaCartiSortate() {
        if (carti.isEmpty()) {
            System.out.println("Nu exista carti pentru sortare.");
            return;
        }

        cartiSortate.clear();
        cartiSortate.addAll(carti);

        System.out.println("=== CARTI SORTATE ALFABETIC ===");
        for (Carte carte : cartiSortate) {
            System.out.println(carte);
        }
    }

    public void afiseazaCartiDupaAutor(String numeAutor) {
        boolean gasita = false;
        System.out.println("=== CARTI DUPA AUTOR ===");

        for (Carte carte : carti) {
            if (carte.getAutor().getNume().equalsIgnoreCase(numeAutor)) {
                System.out.println(carte);
                gasita = true;
            }
        }

        if (!gasita) {
            System.out.println("Nu exista carti scrise de autorul: " + numeAutor);
        }
    }

    public void afiseazaCartiDupaSectiune(String numeSectiune) {
        boolean gasita = false;
        System.out.println("=== CARTI DIN SECTIUNE ===");

        for (Carte carte : carti) {
            if (carte.getSectiune().getNume().equalsIgnoreCase(numeSectiune)) {
                System.out.println(carte);
                gasita = true;
            }
        }

        if (!gasita) {
            System.out.println("Nu exista carti in sectiunea: " + numeSectiune);
        }
    }

    public void imprumutaCarte(int idImprumut, int idCititor, int idBibliotecar, int idCarte, int nrZile) {
        Cititor cititor = cititori.get(idCititor);
        Bibliotecar bibliotecar = bibliotecari.get(idBibliotecar);
        Carte carte = gasesteCarteDupaId(idCarte);

        if (cititor == null) {
            System.out.println("Cititorul cu id " + idCititor + " nu exista.");
            return;
        }

        if (bibliotecar == null) {
            System.out.println("Bibliotecarul cu id " + idBibliotecar + " nu exista.");
            return;
        }

        if (carte == null) {
            System.out.println("Cartea cu id " + idCarte + " nu exista.");
            return;
        }

        if (gasesteImprumutDupaId(idImprumut) != null) {
            System.out.println("Exista deja un imprumut cu id-ul " + idImprumut);
            return;
        }

        if (esteCarteImprumutata(idCarte)) {
            System.out.println("Cartea \"" + carte.getTitlu() + "\" este deja imprumutata.");
            return;
        }

        int limita = getLimitaImprumuturi(cititor);
        int active = numarImprumuturiActiveCititor(idCititor);

        if (active >= limita) {
            System.out.println("Cititorul " + cititor.getNume() + " a atins limita maxima de imprumuturi.");
            return;
        }

        LocalDate azi = LocalDate.now();
        LocalDate termenReturnare = azi.plusDays(nrZile);

        Imprumut imprumut = new Imprumut(
                idImprumut,
                cititor,
                bibliotecar,
                carte,
                azi,
                termenReturnare,
                true
        );

        imprumuturi.add(imprumut);

        for (Rezervare rezervare : rezervari) {
            if (rezervare.getCititor().getId() == idCititor
                    && rezervare.getCarte().getId() == idCarte
                    && !rezervare.getStatus().equalsIgnoreCase("Anulata")
                    && !rezervare.getStatus().equalsIgnoreCase("Finalizata")) {
                rezervare.finalizeazaRezervare();
            }
        }

        System.out.println("Imprumut creat cu succes pentru cartea: " + carte.getTitlu());
    }

    public void returneazaCarte(int idImprumut) {
        Imprumut imprumut = gasesteImprumutDupaId(idImprumut);

        if (imprumut == null) {
            System.out.println("Imprumutul cu id " + idImprumut + " nu exista.");
            return;
        }

        if (!imprumut.getActiv()) {
            System.out.println("Imprumutul este deja inchis.");
            return;
        }

        imprumut.setActive(false);
        System.out.println("Cartea \"" + imprumut.getCarte().getTitlu() + "\" a fost returnata.");

        for (Rezervare rezervare : rezervari) {
            if (rezervare.getCarte().getId() == imprumut.getCarte().getId()
                    && rezervare.getStatus().equalsIgnoreCase("Activa")) {
                System.out.println("Exista o rezervare activa pentru aceasta carte: rezervarea #" + rezervare.getId());
            }
        }
    }

    public void afiseazaImprumuturi() {
        if (imprumuturi.isEmpty()) {
            System.out.println("Nu exista imprumuturi.");
            return;
        }

        System.out.println("=== TOATE IMPRUMUTURILE ===");
        for (Imprumut imprumut : imprumuturi) {
            System.out.println(imprumut);
        }
    }

    public void afiseazaImprumuturiActive() {
        boolean exista = false;
        System.out.println("=== IMPRUMUTURI ACTIVE ===");

        for (Imprumut imprumut : imprumuturi) {
            if (imprumut.getActiv()) {
                System.out.println(imprumut);
                exista = true;
            }
        }

        if (!exista) {
            System.out.println("Nu exista imprumuturi active.");
        }
    }

    public void rezervaCarte(int idRezervare, int idCititor, int idCarte) {
        Cititor cititor = cititori.get(idCititor);
        Carte carte = gasesteCarteDupaId(idCarte);

        if (cititor == null) {
            System.out.println("Cititorul cu id " + idCititor + " nu exista.");
            return;
        }

        if (carte == null) {
            System.out.println("Cartea cu id " + idCarte + " nu exista.");
            return;
        }

        if (gasesteRezervareDupaId(idRezervare) != null) {
            System.out.println("Exista deja o rezervare cu id-ul " + idRezervare);
            return;
        }

        if (!esteCarteImprumutata(idCarte)) {
            System.out.println("Cartea este disponibila. Nu este necesara rezervarea.");
            return;
        }

        for (Rezervare rezervare : rezervari) {
            if (rezervare.getCititor().getId() == idCititor
                    && rezervare.getCarte().getId() == idCarte
                    && rezervare.getStatus().equalsIgnoreCase("Activa")) {
                System.out.println("Cititorul are deja o rezervare activa pentru aceasta carte.");
                return;
            }
        }

        Rezervare rezervare = new Rezervare(
                idRezervare,
                cititor,
                carte,
                LocalDate.now(),
                "Activa"
        );

        rezervari.add(rezervare);
        System.out.println("Rezervare adaugata pentru cartea: " + carte.getTitlu());
    }

    public void anuleazaRezervare(int idRezervare) {
        Rezervare rezervare = gasesteRezervareDupaId(idRezervare);

        if (rezervare == null) {
            System.out.println("Rezervarea cu id " + idRezervare + " nu exista.");
            return;
        }

        if (rezervare.getStatus().equalsIgnoreCase("Anulata")) {
            System.out.println("Rezervarea este deja anulata.");
            return;
        }

        rezervare.anuleazaRezervare();
        System.out.println("Rezervarea #" + idRezervare + " a fost anulata.");
    }

    public void afiseazaRezervari() {
        if (rezervari.isEmpty()) {
            System.out.println("Nu exista rezervari.");
            return;
        }

        System.out.println("=== TOATE REZERVARILE ===");
        for (Rezervare rezervare : rezervari) {
            System.out.println(rezervare);
        }
    }

    public void genereazaPenalizareIntarziere(int idPenalizare, int idImprumut, double taxaPeZi) {
        Imprumut imprumut = gasesteImprumutDupaId(idImprumut);

        if (imprumut == null) {
            System.out.println("Imprumutul cu id " + idImprumut + " nu exista.");
            return;
        }

        if (gasestePenalizareDupaId(idPenalizare) != null) {
            System.out.println("Exista deja o penalizare cu id-ul " + idPenalizare);
            return;
        }

        LocalDate termen = imprumut.getDataReturnare();
        LocalDate azi = LocalDate.now();

        if (!azi.isAfter(termen)) {
            System.out.println("Imprumutul nu este intarziat. Nu se genereaza penalizare.");
            return;
        }

        long zileIntarziere = ChronoUnit.DAYS.between(termen, azi);
        double suma = zileIntarziere * taxaPeZi;

        Penalizare penalizare = new Penalizare(
                idPenalizare,
                imprumut.getCititor(),
                imprumut,
                suma,
                "Intarziere la returnare",
                false,
                azi
        );

        penalizari.add(penalizare);
        System.out.println("Penalizare generata: " + suma + " lei pentru " + zileIntarziere + " zile intarziere.");
    }

    public void platestePenalizare(int idPenalizare) {
        Penalizare penalizare = gasestePenalizareDupaId(idPenalizare);

        if (penalizare == null) {
            System.out.println("Penalizarea cu id " + idPenalizare + " nu exista.");
            return;
        }

        if (penalizare.isPlatita()) {
            System.out.println("Penalizarea este deja platita.");
            return;
        }

        penalizare.platestePenalizare();
        System.out.println("Penalizarea #" + idPenalizare + " a fost platita.");
    }

    public void afiseazaPenalizari() {
        if (penalizari.isEmpty()) {
            System.out.println("Nu exista penalizari.");
            return;
        }

        System.out.println("=== TOATE PENALIZARILE ===");
        for (Penalizare penalizare : penalizari) {
            System.out.println(penalizare);
        }
    }

    private Carte gasesteCarteDupaId(int idCarte) {
        for (Carte carte : carti) {
            if (carte.getId() == idCarte) {
                return carte;
            }
        }
        return null;
    }

    private Imprumut gasesteImprumutDupaId(int idImprumut) {
        for (Imprumut imprumut : imprumuturi) {
            if (imprumut.getId() == idImprumut) {
                return imprumut;
            }
        }
        return null;
    }

    private Rezervare gasesteRezervareDupaId(int idRezervare) {
        for (Rezervare rezervare : rezervari) {
            if (rezervare.getId() == idRezervare) {
                return rezervare;
            }
        }
        return null;
    }

    private Penalizare gasestePenalizareDupaId(int idPenalizare) {
        for (Penalizare penalizare : penalizari) {
            if (penalizare.getId() == idPenalizare) {
                return penalizare;
            }
        }
        return null;
    }

    private boolean esteCarteImprumutata(int idCarte) {
        for (Imprumut imprumut : imprumuturi) {
            if (imprumut.getCarte().getId() == idCarte && imprumut.getActiv()) {
                return true;
            }
        }
        return false;
    }

    private int numarImprumuturiActiveCititor(int idCititor) {
        int count = 0;
        for (Imprumut imprumut : imprumuturi) {
            if (imprumut.getCititor().getId() == idCititor && imprumut.getActiv()) {
                count++;
            }
        }
        return count;
    }

    private int getLimitaImprumuturi(Cititor cititor) {
        for (Abonament abonament : abonamente) {
            if (abonament.getTip().equalsIgnoreCase(cititor.getTipAbonament())) {
                return abonament.getMaxImprumuturi();
            }
        }
        return Integer.MAX_VALUE;
    }
}