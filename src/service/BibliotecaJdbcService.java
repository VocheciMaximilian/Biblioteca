package service;

import Database.DatabaseConnection;
import Database.DatabaseInitializer;
import model.Abonament;
import model.Autor;
import model.Bibliotecar;
import model.Carte;
import model.CarteDigitala;
import model.CarteFizica;
import model.Cititor;
import model.Imprumut;
import model.Penalizare;
import model.Rezervare;
import model.Sectiune;
import repository.AbonamentRepository;
import repository.AutorRepository;
import repository.BibliotecarRepository;
import repository.CarteRepository;
import repository.CititorRepository;
import repository.ImprumutRepository;
import repository.PenalizareRepository;
import repository.RezervareRepository;
import repository.SectiuneRepository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BibliotecaJdbcService {
    private final List<Abonament> abonamente = new ArrayList<>();
    private final List<Autor> autori = new ArrayList<>();
    private final List<Sectiune> sectiuni = new ArrayList<>();
    private final List<Cititor> cititori = new ArrayList<>();
    private final List<Bibliotecar> bibliotecari = new ArrayList<>();
    private final List<Carte> carti = new ArrayList<>();
    private final List<Imprumut> imprumuturi = new ArrayList<>();
    private final List<Rezervare> rezervari = new ArrayList<>();
    private final List<Penalizare> penalizari = new ArrayList<>();

    private AutorService autorService;
    private SectiuneService sectiuneService;
    private CititorService cititorService;
    private CarteService carteService;

    private Connection connection;
    private AbonamentRepository abonamentRepository;
    private AutorRepository autorRepository;
    private SectiuneRepository sectiuneRepository;
    private CititorRepository cititorRepository;
    private BibliotecarRepository bibliotecarRepository;
    private CarteRepository carteRepository;
    private ImprumutRepository imprumutRepository;
    private RezervareRepository rezervareRepository;
    private PenalizareRepository penalizareRepository;

    public boolean initialize() {
        connection = DatabaseConnection.getInstance();
        if (connection == null) {
            return false;
        }

        DatabaseInitializer.createTables(connection);
        autorService = AutorService.getInstance();
        sectiuneService = SectiuneService.getInstance();
        cititorService = CititorService.getInstance();
        carteService = CarteService.getInstance();

        abonamentRepository = new AbonamentRepository(connection);
        autorRepository = new AutorRepository(connection);
        sectiuneRepository = new SectiuneRepository(connection);
        cititorRepository = new CititorRepository(connection);
        bibliotecarRepository = new BibliotecarRepository(connection);
        carteRepository = new CarteRepository(connection);
        imprumutRepository = new ImprumutRepository(connection);
        rezervareRepository = new RezervareRepository(connection);
        penalizareRepository = new PenalizareRepository(connection);
        seedDemoData();
        refreshCrudDataFromDatabase();
        return true;
    }

    public void close() {
        DatabaseConnection.closeConnection();
    }

    public List<Abonament> getAbonamente() {
        return Collections.unmodifiableList(abonamente);
    }

    public List<Autor> getAutori() {
        return Collections.unmodifiableList(autori);
    }

    public List<Sectiune> getSectiuni() {
        return Collections.unmodifiableList(sectiuni);
    }

    public List<Cititor> getCititori() {
        return Collections.unmodifiableList(cititori);
    }

    public List<Bibliotecar> getBibliotecari() {
        return Collections.unmodifiableList(bibliotecari);
    }

    public List<Carte> getCarti() {
        return Collections.unmodifiableList(carti);
    }

    public List<Imprumut> getImprumuturi() {
        return Collections.unmodifiableList(imprumuturi);
    }

    public List<Rezervare> getRezervari() {
        return Collections.unmodifiableList(rezervari);
    }

    public List<Penalizare> getPenalizari() {
        return Collections.unmodifiableList(penalizari);
    }

    public void adaugaAbonament(Abonament abonament) {
        abonamentRepository.save(abonament);
        abonamente.add(abonament);
    }

    public void adaugaAutor(Autor autor) {
        autorService.create(autor);
        autori.add(autor);
    }

    public void adaugaSectiune(Sectiune sectiune) {
        sectiuneService.create(sectiune);
        sectiuni.add(sectiune);
    }

    public void adaugaCititor(Cititor cititor) {
        cititorService.create(cititor);
        cititori.add(cititor);
    }

    public void adaugaBibliotecar(Bibliotecar bibliotecar) {
        bibliotecarRepository.save(bibliotecar);
        bibliotecari.add(bibliotecar);
    }

    public void adaugaCarte(Carte carte) {
        carteService.create(carte);
        carti.add(carte);
    }

    public Autor cautaAutorDupaId(int id) {
        return autorService.readById(id);
    }

    public Sectiune cautaSectiuneDupaId(int id) {
        return sectiuneService.readById(id);
    }

    public Cititor cautaCititorDupaId(int id) {
        return cititorService.readById(id);
    }

    public Carte cautaCarteDupaId(int id) {
        return carteService.readById(id);
    }

    public void actualizeazaAutor(Autor autor) {
        autorService.update(autor);
        refreshCrudDataFromDatabase();
    }

    public void actualizeazaSectiune(Sectiune sectiune) {
        sectiuneService.update(sectiune);
        refreshCrudDataFromDatabase();
    }

    public void actualizeazaCititor(Cititor cititor) {
        cititorService.update(cititor);
        refreshCrudDataFromDatabase();
    }

    public void actualizeazaCarte(Carte carte) {
        carteService.update(carte);
        refreshCrudDataFromDatabase();
    }

    public void stergeAutor(int id) {
        autorService.deleteById(id);
        refreshCrudDataFromDatabase();
    }

    public void stergeSectiune(int id) {
        sectiuneService.deleteById(id);
        refreshCrudDataFromDatabase();
    }

    public void stergeCititor(int id) {
        cititorService.deleteById(id);
        refreshCrudDataFromDatabase();
    }

    public void stergeCarte(int id) {
        carteService.deleteById(id);
        refreshCrudDataFromDatabase();
    }

    public void adaugaImprumut(Imprumut imprumut) {
        imprumutRepository.save(imprumut);
        imprumuturi.add(imprumut);
    }

    public void adaugaRezervare(Rezervare rezervare) {
        rezervareRepository.save(rezervare);
        rezervari.add(rezervare);
    }

    public void adaugaPenalizare(Penalizare penalizare) {
        penalizareRepository.save(penalizare);
        penalizari.add(penalizare);
    }

    private void seedDemoData() {
        if (!abonamente.isEmpty()) {
            return;
        }

        Abonament standard = new Abonament(1, "Standard", 40.0, 1, 2);
        Abonament premium = new Abonament(2, "Premium", 70.0, 1, 5);
        Autor rebreanu = new Autor(1, "Liviu Rebreanu", "Romana");
        Autor creanga = new Autor(2, "Ion Creanga", "Romana");
        Autor orwell = new Autor(3, "George Orwell", "Britanica");
        Sectiune roman = new Sectiune(1, "Roman");
        Sectiune copii = new Sectiune(2, "Copii");
        Sectiune distopie = new Sectiune(3, "Distopie");
        Cititor ana = new Cititor(1, "Ana Pop", "ana@gmail.com", standard);
        Cititor mihai = new Cititor(2, "Mihai Ionescu", "mihai@gmail.com", premium);
        Bibliotecar maria = new Bibliotecar(1, "Maria Libraru", "maria@biblioteca.ro", "B001", 4500);
        Bibliotecar andrei = new Bibliotecar(2, "Andrei Stan", "andrei@biblioteca.ro", "B002", 4800);
        Carte carte1 = new CarteFizica(1, "Ion", rebreanu, roman, 1920, true, 300);
        Carte carte2 = new CarteFizica(2, "Amintiri din copilarie", creanga, copii, 1892, true, 180);
        Carte carte3 = new CarteDigitala(3, "1984", orwell, distopie, 1949, true, 5);
        Carte carte4 = new CarteDigitala(4, "Animal Farm", orwell, distopie, 1945, true, 3);

        abonamentRepository.save(standard);
        abonamentRepository.save(premium);
        autorRepository.save(rebreanu);
        autorRepository.save(creanga);
        autorRepository.save(orwell);
        sectiuneRepository.save(roman);
        sectiuneRepository.save(copii);
        sectiuneRepository.save(distopie);
        cititorRepository.save(ana);
        cititorRepository.save(mihai);
        bibliotecarRepository.save(maria);
        bibliotecarRepository.save(andrei);
        carteRepository.save(carte1);
        carteRepository.save(carte2);
        carteRepository.save(carte3);
        carteRepository.save(carte4);

        abonamente.add(standard);
        abonamente.add(premium);
        autori.add(rebreanu);
        autori.add(creanga);
        autori.add(orwell);
        sectiuni.add(roman);
        sectiuni.add(copii);
        sectiuni.add(distopie);
        cititori.add(ana);
        cititori.add(mihai);
        bibliotecari.add(maria);
        bibliotecari.add(andrei);
        carti.add(carte1);
        carti.add(carte2);
        carti.add(carte3);
        carti.add(carte4);
    }

    private void refreshCrudDataFromDatabase() {
        autori.clear();
        autori.addAll(autorService.readAll());

        sectiuni.clear();
        sectiuni.addAll(sectiuneService.readAll());

        cititori.clear();
        cititori.addAll(cititorService.readAll());

        carti.clear();
        carti.addAll(carteService.readAll());
    }
}
