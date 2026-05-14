## Sistem de gestionare a unei biblioteci
Un sistem simplu implementat pentru administrarea unei biblioteci care foloseste programarea orientata pe obiecte in Java. Scopul aplicatiei este sa permita gestionarea entitatilor principale dintr-o biblioteca si salvarea datelor intr-o baza de date PostgreSQL.

## 1. Obiectivul proiectului
Obiectivul proiectului este realizarea unei aplicatii pentru gestionarea unei biblioteci.
Aplicatia permite administrarea cartilor, autorilor, sectiunilor, cititorilor, bibliotecarilor, imprumuturilor, rezervarilor, penalizarilor si abonamentelor.

## 2. Structura proiectului
```text
src/
|-- Database/
|   |-- DatabaseConnection.java
|   `-- DatabaseInitializer.java
|
|-- model/
|   |-- Abonament.java
|   |-- Autor.java
|   |-- Bibliotecar.java
|   |-- Carte.java
|   |-- CarteDigitala.java
|   |-- CarteFizica.java
|   |-- Cititor.java
|   |-- Imprumut.java
|   |-- Penalizare.java
|   |-- Persoana.java
|   |-- Rezervare.java
|   `-- Sectiune.java
|
|-- repository/
|   |-- AbonamentRepository.java
|   |-- AutorRepository.java
|   |-- BibliotecarRepository.java
|   |-- CarteRepository.java
|   |-- CititorRepository.java
|   |-- CrudRepository.java
|   |-- ImprumutRepository.java
|   |-- PenalizareRepository.java
|   |-- RezervareRepository.java
|   `-- SectiuneRepository.java
|
|-- service/
|   |-- AuditService.java
|   |-- AutorService.java
|   |-- BibliotecaJdbcService.java
|   |-- BibliotecaService.java
|   |-- CarteService.java
|   |-- CititorService.java
|   |-- CrudService.java
|   `-- SectiuneService.java
|
|-- ui/
|   `-- BibliotecaApp.java
|
|-- Etapa1.java
`-- Main.java
```

## 3. Rolul pachetelor
### 3.1. `model`
Contine obiectele principale ale aplicatiei.
### 3.2. `repository`
Contine clasele care fac operatii directe cu baza de date.
### 3.3. `service`
Contine logica aplicatiei si operatiile cu obiectele din `model`.
### 3.4. `Database`
Contine conectarea la baza de date si crearea tabelelor.
### 3.5. `ui`
Contine interfata grafica a aplicatiei.
### 3.6. `Main`
Aici se porneste aplicatia.

## 4. Descrierea claselor
### 4.1. `Autor`
#### Rol
Reprezinta autorul uneia sau mai multor carti din sistem.
#### Atribute
- `id` - identificator unic pentru autor;
- `nume` - numele autorului;
- `nationalitate` - nationalitatea autorului.

### 4.2. `Sectiune`
#### Rol
Reprezinta categoria sau sectiunea bibliotecii in care este incadrata cartea.
#### Atribute
- `id` - identificator unic pentru sectiune;
- `nume` - denumirea sectiunii.

### 4.3. `Carte`
#### Rol
Reprezinta clasa de baza pentru toate tipurile de carti din sistem.
#### Atribute
- `id` - identificatorul unic al cartii;
- `titlu` - titlul cartii;
- `autor` - referinta catre obiectul `Autor`;
- `sectiune` - referinta catre obiectul `Sectiune`;
- `disponibila` - retine starea cartii;
- `anPublicatie` - anul in care a fost publicata cartea.

### 4.4. `CarteFizica`
#### Rol
Reprezinta o carte in format fizic.
#### Atribute
Mosteneste atributele clasei `Carte`.
- `nrPagini` - numarul de pagini al cartii.

### 4.5. `CarteDigitala`
#### Rol
Reprezinta o carte in format digital.
#### Atribute
Mosteneste atributele clasei `Carte`.
- `marimeMB` - marimea fisierului in MB.

### 4.6. `Persoana`
#### Rol
Reprezinta clasa de baza pentru celelalte tipuri de persoane (`Bibliotecar` si `Cititor` in cazul nostru).
#### Atribute
- `id` - identificatorul unic;
- `nume` - numele persoanei;
- `email` - email-ul persoanei.

### 4.7. `Bibliotecar`
#### Rol
Reprezinta angajatul bibliotecii care administreaza operatiile si poate autoriza imprumuturile.
#### Atribute
Mosteneste atributele clasei `Persoana`.
- `idAngajat` - identificatorul unic pentru angajat;
- `salariu` - salariul angajatului.

### 4.8. `Cititor`
#### Rol
Reprezinta persoana care poate imprumuta carti din biblioteca.
#### Atribute
Mosteneste atributele clasei `Persoana`.
- `abonament` - abonamentul detinut de cititor.

### 4.9. `Imprumut`
#### Rol
Reprezinta operatia prin care o carte este oferita temporar unui cititor.
#### Atribute
- `id` - identificatorul unic al tranzactiei;
- `cititor` - obiectul cititor care imprumuta cartea;
- `bibliotecar` - obiectul bibliotecar care autorizeaza imprumutul;
- `carte` - obiectul carte imprumutat;
- `dataImprumut` - data la care a fost imprumutata cartea;
- `dataReturnare` - data la care trebuie returnata cartea;
- `activ` - arata daca imprumutul mai este activ.

### 4.10. `Abonament`
#### Rol
Reprezinta tipul de abonament pe care un cititor il poate achizitiona.
#### Atribute
- `id` - identificatorul unic al abonamentului;
- `tip` - tipul abonamentului;
- `pret` - pretul abonamentului;
- `durataLuni` - durata abonamentului in luni;
- `maxImprumuturi` - numarul maxim de imprumuturi permise.

### 4.11. `Rezervare`
#### Rol
Reprezinta rezervarea unei carti de catre un cititor.
#### Atribute
- `id` - identificatorul unic al rezervarii;
- `cititor` - cititorul care face rezervarea;
- `carte` - cartea rezervata;
- `dataRezervare` - data la care a fost facuta rezervarea;
- `status` - starea rezervarii.

### 4.12. `Penalizare`
#### Rol
Reprezinta o penalizare primita de un cititor pentru un imprumut.
#### Atribute
- `id` - identificatorul unic al penalizarii;
- `cititor` - cititorul penalizat;
- `imprumut` - imprumutul pentru care se aplica penalizarea;
- `suma` - suma de plata;
- `motiv` - motivul penalizarii;
- `platita` - arata daca penalizarea a fost platita;
- `dataPenalizare` - data la care a fost adaugata penalizarea.

## 5. Functionalitati
- adaugare, cautare, actualizare si stergere pentru autori, sectiuni, cititori si carti;
- adaugare de bibliotecari si abonamente;
- creare de imprumuturi, rezervari si penalizari;
- afisarea datelor in interfata grafica;
- salvarea datelor in PostgreSQL;
- logarea actiunilor in fisierul `audit.csv`.

## 6. Baza de date
Aplicatia foloseste PostgreSQL si driverul din folderul `lib`.
Datele pentru conectare se citesc din fisierul `.env`.

Exemplu de configurare:
```text
DB_URL=jdbc:postgresql://localhost:5432/biblioteca
DB_USER=postgres
DB_PASSWORD=parola_ta
```

Tabelele sunt create automat la pornirea aplicatiei daca nu exista deja.

## 7. Rulare
Aplicatia se poate rula din clasa `Main`.

Exemplu de compilare si rulare din terminal:
```text
javac -cp "lib/postgresql-42.7.11.jar" -d bin src/Main.java src/Etapa1.java src/Database/*.java src/model/*.java src/repository/*.java src/service/*.java src/ui/*.java
java -cp "bin;lib/postgresql-42.7.11.jar" Main
```

Pe Linux/Mac separatorul pentru classpath este `:` in loc de `;`.

## 8. Concepte OOP folosite
- mostenire: `CarteFizica` si `CarteDigitala` mostenesc `Carte`, iar `Cititor` si `Bibliotecar` mostenesc `Persoana`;
- incapsulare: atributele sunt private/protected si sunt accesate prin metode;
- polimorfism: obiectele de tip `CarteFizica` si `CarteDigitala` sunt tratate ca obiecte de tip `Carte`;
- abstractizare: logica este impartita in pachete separate pentru model, service, repository si interfata.
