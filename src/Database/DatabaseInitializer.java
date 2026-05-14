package Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private DatabaseInitializer() {
    }

    public static void createTables(Connection connection) {
        String[] sqlStatements = {
                """
                CREATE TABLE IF NOT EXISTS abonamente (
                    id INTEGER PRIMARY KEY,
                    tip VARCHAR(50) NOT NULL UNIQUE,
                    pret NUMERIC(10, 2) NOT NULL,
                    durata_luni INTEGER NOT NULL,
                    max_imprumuturi INTEGER NOT NULL
                )
                """,
                """
                CREATE TABLE IF NOT EXISTS autori (
                    id INTEGER PRIMARY KEY,
                    nume VARCHAR(100) NOT NULL,
                    nationalitate VARCHAR(100)
                )
                """,
                """
                CREATE TABLE IF NOT EXISTS sectiuni (
                    id INTEGER PRIMARY KEY,
                    nume VARCHAR(100) NOT NULL UNIQUE
                )
                """,
                """
                CREATE TABLE IF NOT EXISTS cititori (
                    id INTEGER PRIMARY KEY,
                    nume VARCHAR(100) NOT NULL,
                    email VARCHAR(100) NOT NULL UNIQUE,
                    abonament_id INTEGER NOT NULL,
                    CONSTRAINT fk_cititori_abonamente
                        FOREIGN KEY (abonament_id)
                        REFERENCES abonamente(id)
                )
                """,
                """
                CREATE TABLE IF NOT EXISTS bibliotecari (
                    id INTEGER PRIMARY KEY,
                    nume VARCHAR(100) NOT NULL,
                    email VARCHAR(100) NOT NULL UNIQUE,
                    id_angajat VARCHAR(30) NOT NULL UNIQUE,
                    salariu NUMERIC(10, 2) NOT NULL
                )
                """,
                """
                CREATE TABLE IF NOT EXISTS carti (
                    id INTEGER PRIMARY KEY,
                    titlu VARCHAR(150) NOT NULL,
                    autor_id INTEGER NOT NULL,
                    sectiune_id INTEGER NOT NULL,
                    an_publicatie INTEGER NOT NULL,
                    disponibila BOOLEAN NOT NULL,
                    tip VARCHAR(20) NOT NULL,
                    nr_pagini INTEGER,
                    marime_mb INTEGER,
                    CONSTRAINT fk_carti_autori
                        FOREIGN KEY (autor_id)
                        REFERENCES autori(id),
                    CONSTRAINT fk_carti_sectiuni
                        FOREIGN KEY (sectiune_id)
                        REFERENCES sectiuni(id),
                    CONSTRAINT ck_carti_tip
                        CHECK (tip IN ('FIZICA', 'DIGITALA')),
                    CONSTRAINT ck_carti_detalii_tip
                        CHECK (
                            (tip = 'FIZICA' AND nr_pagini IS NOT NULL AND marime_mb IS NULL)
                            OR
                            (tip = 'DIGITALA' AND marime_mb IS NOT NULL AND nr_pagini IS NULL)
                        )
                )
                """,
                """
                CREATE TABLE IF NOT EXISTS imprumuturi (
                    id INTEGER PRIMARY KEY,
                    cititor_id INTEGER NOT NULL,
                    bibliotecar_id INTEGER NOT NULL,
                    carte_id INTEGER NOT NULL,
                    data_imprumut DATE NOT NULL,
                    data_returnare DATE NOT NULL,
                    activ BOOLEAN NOT NULL,
                    CONSTRAINT fk_imprumuturi_cititori
                        FOREIGN KEY (cititor_id)
                        REFERENCES cititori(id),
                    CONSTRAINT fk_imprumuturi_bibliotecari
                        FOREIGN KEY (bibliotecar_id)
                        REFERENCES bibliotecari(id),
                    CONSTRAINT fk_imprumuturi_carti
                        FOREIGN KEY (carte_id)
                        REFERENCES carti(id)
                )
                """,
                """
                CREATE TABLE IF NOT EXISTS rezervari (
                    id INTEGER PRIMARY KEY,
                    cititor_id INTEGER NOT NULL,
                    carte_id INTEGER NOT NULL,
                    data_rezervare DATE NOT NULL,
                    status VARCHAR(30) NOT NULL,
                    CONSTRAINT fk_rezervari_cititori
                        FOREIGN KEY (cititor_id)
                        REFERENCES cititori(id),
                    CONSTRAINT fk_rezervari_carti
                        FOREIGN KEY (carte_id)
                        REFERENCES carti(id)
                )
                """,
                """
                CREATE TABLE IF NOT EXISTS penalizari (
                    id INTEGER PRIMARY KEY,
                    cititor_id INTEGER NOT NULL,
                    imprumut_id INTEGER NOT NULL,
                    suma NUMERIC(10, 2) NOT NULL,
                    motiv VARCHAR(200) NOT NULL,
                    platita BOOLEAN NOT NULL,
                    data_penalizare DATE NOT NULL,
                    CONSTRAINT fk_penalizari_cititori
                        FOREIGN KEY (cititor_id)
                        REFERENCES cititori(id),
                    CONSTRAINT fk_penalizari_imprumuturi
                        FOREIGN KEY (imprumut_id)
                        REFERENCES imprumuturi(id)
                )
                """
        };

        try (Statement statement = connection.createStatement()) {
            for (String sql : sqlStatements) {
                statement.execute(sql);
            }
            System.out.println("Tabelele au fost create/verificate cu succes.");
        } catch (SQLException e) {
            System.err.println("Eroare la initializarea bazei de date: " + e.getMessage());
        }
    }
}
