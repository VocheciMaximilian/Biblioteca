## Sistem de gestionare a unei biblioteci
Un sistem simplu implementat pentru administrarea unei biblioteci care foloseste programarea oreintata pe obiecte in Java. Scopul aplicatiei este sa permita gestionarea entitatilor principale dintr-o biblioteca.

## 1. Obiectivul proiectului

## 2. Structura proiectului
```text
src/
├── model/
│   ├── Autor.java
│   ├── Sectiune.java
│   ├── Carte.java
│   ├── CarteFizica.java
│   ├── CarteDigitala.java
│   ├── Cititor.java
│   ├── Bibliotecar.java
│   └── Imprumut.java
│
├── service/
│   └── BibliotecaService.java
│
└── Main.java
```

## 3. Rolul pachetelor
### 3.1. `model`
Contine obiectele principale ale aplicatiei.
### 3.2. `service`
COntine logica aplicatiei, operatiile cu obiectele din `Model`
### 3.3. `Main`
Aici se creaza obiectele si se testeaza functionalitatea

## 4. Descrierea claselor
### 4.1. `Autor`
#### Rol 
Reprezinta autorul unei sau mai multor cartii din sistem.
#### Atribute
- `id` - identificator unic pentru autor;
- `nume` - numele autorului;
- `nationalitatea` - nationalitatea autorului.
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
Reprezinta o carte in format fizica.
#### Atribute
Mosteneste atrbutele clasei `Carte`.
- `nrPagini` - numarul de pagini al cartii.
### 4.5. `CarteDigitala
#### Rol
Reprezinta o carte in format digital.
#### Atribute 
Mosteneste atributele clasei `Carte`.
### 4.6. `Persoana`
#### Rol
Reprezinta clasa de baza pentru celelalte tipuri de persoane (`Bibliotecar` si `Cititor in cazul nostru).
#### Atribute
- `id` - identificatorul unic;
- `nume` - numele persoanei;
- `email` - email-ul persoanei.
### 4.7. `Bibliotecar`
#### Rol
Reprezinta angajatul bibliotecii care administreaza operatiile si poate autoriza imprumuturile.
#### Atribute
Mosteneste atributele clasei `Persoana`\
- 'idAngajat` - identificatorul unic pentru angajat;
- `salariu` - salariul angajatului.
### 4.8. `Cititor`
#### Rol
Reprezinta persoana care poate imprumuta cartii din biblioteca.
#### Atribute
Mosteneste atributele clasei `Persoana`.
- `tipAbonament - tipul de abonament lunar detinut de cititor.
### 4.9. `Imprumut`
#### Rol 
Reprezinta operatia prin care o carte este oferita temporar unui cititor.
#### Atributele 
- `id` - identificatorul unic al tranzactiei;
- `cititor` - obiectul cititor care imprumuta cartea;
- `bibliotecar` - obiectul bibliotecar care autorizeaza imprumutul;
- `carte` - obiectul carte imprumutat;
-