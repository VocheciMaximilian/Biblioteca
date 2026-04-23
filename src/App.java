import model.Abonament;
import model.Autor;
import model.Bibliotecar;
import model.CarteDigitala;
import model.CarteFizica;
import model.Cititor;
import model.Sectiune;
import service.BibliotecaService;

public class App {
    public static void main(String[] args) {
        BibliotecaService bibliotecaService = new BibliotecaService();

        Autor autor1 = new Autor(1, "Liviu Rebreanu", "Romana");
        Autor autor2 = new Autor(2, "Ion Creanga", "Romana");
        Autor autor3 = new Autor(3, "George Orwell", "Britanica");

        Sectiune sectiune1 = new Sectiune(1, "Roman");
        Sectiune sectiune2 = new Sectiune(2, "Copii");
        Sectiune sectiune3 = new Sectiune(3, "Distopie");

        Abonament abonamentStandard = new Abonament(1, "Standard", 40.0, 1, 2);
        Abonament abonamentPremium = new Abonament(2, "Premium", 70.0, 1, 5);

        Cititor cititor1 = new Cititor(1, "Ana Pop", "ana@gmail.com", abonamentStandard);
        Cititor cititor2 = new Cititor(2, "Mihai Ionescu", "mihai@gmail.com", abonamentPremium);

        Bibliotecar bibliotecar1 = new Bibliotecar(1, "Maria Libraru", "maria@biblioteca.ro", "B001", 4500);
        Bibliotecar bibliotecar2 = new Bibliotecar(2, "Andrei Stan", "andrei@biblioteca.ro", "B002", 4800);

        CarteFizica carte1 = new CarteFizica(1, "Ion", autor1, sectiune1, 1920, true, 300);
        CarteFizica carte2 = new CarteFizica(2, "Amintiri din copilarie", autor2, sectiune2, 1892, true, 180);
        CarteDigitala carte3 = new CarteDigitala(3, "1984", autor3, sectiune3, 1949, true, 5);
        CarteDigitala carte4 = new CarteDigitala(4, "Animal Farm", autor3, sectiune3, 1945, true, 3);

        bibliotecaService.adaugaAbonament(abonamentStandard);
        bibliotecaService.adaugaAbonament(abonamentPremium);

        bibliotecaService.adaugaCititor(cititor1);
        bibliotecaService.adaugaCititor(cititor2);

        bibliotecaService.adaugaBibliotecar(bibliotecar1);
        bibliotecaService.adaugaBibliotecar(bibliotecar2);

        bibliotecaService.adaugaCarte(carte1);
        bibliotecaService.adaugaCarte(carte2);
        bibliotecaService.adaugaCarte(carte3);
        bibliotecaService.adaugaCarte(carte4);

        System.out.println("\n========== DEMO ETAPA 1 ==========");

        System.out.println("\n1. Afisare toate cartile");
        bibliotecaService.afiseazaCarti();

        System.out.println("\n2. Cautare carte dupa titlu");
        bibliotecaService.cautaCarteDupaTitlu("Ion");

        System.out.println("\n3. Afisare carti disponibile");
        bibliotecaService.afiseazaCartiDisponibile();

        System.out.println("\n4. Afisare carti sortate alfabetic");
        bibliotecaService.afiseazaCartiSortate();

        System.out.println("\n5. Imprumut carte");
        bibliotecaService.imprumutaCarte(1, 1, 1, 1, 7);

        System.out.println("\n6. Afisare imprumuturi active");
        bibliotecaService.afiseazaImprumuturiActive();

        System.out.println("\n7. Rezervare carte indisponibila");
        bibliotecaService.rezervaCarte(1, 2, 1);

        System.out.println("\n8. Afisare rezervari");
        bibliotecaService.afiseazaRezervari();

        System.out.println("\n9. Filtrare carti dupa autor");
        bibliotecaService.afiseazaCartiDupaAutor("George Orwell");

        System.out.println("\n10. Filtrare carti dupa sectiune");
        bibliotecaService.afiseazaCartiDupaSectiune("Distopie");

        // imprumut separat, doar pentru a demonstra penalizarea de intarziere
        // nrZile = -3 inseamna ca termenul de returnare a trecut deja cu 3 zile
        System.out.println("\n11. Imprumut cu termen depasit (demo penalizare)");
        bibliotecaService.imprumutaCarte(2, 2, 2, 2, -3);

        System.out.println("\n12. Generare penalizare de intarziere");
        bibliotecaService.genereazaPenalizareIntarziere(1, 2, 2.5);

        System.out.println("\n13. Afisare penalizari");
        bibliotecaService.afiseazaPenalizari();

        System.out.println("\n14. Plata penalizare");
        bibliotecaService.platestePenalizare(1);

        System.out.println("\n15. Returnare carte");
        bibliotecaService.returneazaCarte(1);

        System.out.println("\n16. Anulare rezervare");
        bibliotecaService.anuleazaRezervare(1);

        System.out.println("\n17. Afisare rezervari dupa anulare");
        bibliotecaService.afiseazaRezervari();

        System.out.println("\n18. Afisare penalizari dupa plata");
        bibliotecaService.afiseazaPenalizari();
    }
}