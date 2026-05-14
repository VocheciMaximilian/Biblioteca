package repository;

import model.Autor;
import model.Carte;
import model.CarteDigitala;
import model.CarteFizica;
import model.Sectiune;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CarteRepository implements CrudRepository<Carte> {
    private final Connection connection;

    public CarteRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Carte carte) {
        String sql = """
                INSERT INTO carti (
                    id, titlu, autor_id, sectiune_id, an_publicatie,
                    disponibila, tip, nr_pagini, marime_mb
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (id) DO NOTHING
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setCarteStatement(statement, carte);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la salvarea cartii: " + e.getMessage());
        }
    }

    public void save(Carte carte) {
        create(carte);
    }

    @Override
    public Carte findById(int id) {
        String sql = baseSelect() + " WHERE c.id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapCarte(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Eroare la citirea cartii: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Carte> findAll() {
        List<Carte> carti = new ArrayList<>();
        String sql = baseSelect() + " ORDER BY c.id";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                carti.add(mapCarte(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Eroare la citirea cartilor: " + e.getMessage());
        }

        return carti;
    }

    @Override
    public void update(Carte carte) {
        String sql = """
                UPDATE carti
                SET titlu = ?, autor_id = ?, sectiune_id = ?, an_publicatie = ?,
                    disponibila = ?, tip = ?, nr_pagini = ?, marime_mb = ?
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, carte.getTitlu());
            statement.setInt(2, carte.getAutor().getId());
            statement.setInt(3, carte.getSectiune().getId());
            statement.setInt(4, carte.getAnPublicatie());
            statement.setBoolean(5, carte.getDisponibila());
            setCarteTypeFields(statement, carte, 6, 7, 8);
            statement.setInt(9, carte.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la actualizarea cartii: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM carti WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la stergerea cartii: " + e.getMessage());
        }
    }

    private void setCarteStatement(PreparedStatement statement, Carte carte) throws SQLException {
        statement.setInt(1, carte.getId());
        statement.setString(2, carte.getTitlu());
        statement.setInt(3, carte.getAutor().getId());
        statement.setInt(4, carte.getSectiune().getId());
        statement.setInt(5, carte.getAnPublicatie());
        statement.setBoolean(6, carte.getDisponibila());
        setCarteTypeFields(statement, carte, 7, 8, 9);
    }

    private void setCarteTypeFields(PreparedStatement statement, Carte carte, int tipIndex, int nrPaginiIndex, int marimeIndex) throws SQLException {
        if (carte instanceof CarteFizica carteFizica) {
            statement.setString(tipIndex, "FIZICA");
            statement.setInt(nrPaginiIndex, carteFizica.getNrPagini());
            statement.setNull(marimeIndex, Types.INTEGER);
        } else if (carte instanceof CarteDigitala carteDigitala) {
            statement.setString(tipIndex, "DIGITALA");
            statement.setNull(nrPaginiIndex, Types.INTEGER);
            statement.setInt(marimeIndex, carteDigitala.getMarimeMB());
        } else {
            throw new SQLException("Tip de carte necunoscut.");
        }
    }

    private String baseSelect() {
        return """
                SELECT c.id, c.titlu, c.an_publicatie, c.disponibila, c.tip, c.nr_pagini, c.marime_mb,
                       a.id AS autor_id, a.nume AS autor_nume, a.nationalitate,
                       s.id AS sectiune_id, s.nume AS sectiune_nume
                FROM carti c
                JOIN autori a ON c.autor_id = a.id
                JOIN sectiuni s ON c.sectiune_id = s.id
                """;
    }

    private Carte mapCarte(ResultSet resultSet) throws SQLException {
        Autor autor = new Autor(
                resultSet.getInt("autor_id"),
                resultSet.getString("autor_nume"),
                resultSet.getString("nationalitate")
        );
        Sectiune sectiune = new Sectiune(
                resultSet.getInt("sectiune_id"),
                resultSet.getString("sectiune_nume")
        );

        int id = resultSet.getInt("id");
        String titlu = resultSet.getString("titlu");
        int anPublicatie = resultSet.getInt("an_publicatie");
        boolean disponibila = resultSet.getBoolean("disponibila");
        String tip = resultSet.getString("tip");

        if ("FIZICA".equals(tip)) {
            return new CarteFizica(id, titlu, autor, sectiune, anPublicatie, disponibila, resultSet.getInt("nr_pagini"));
        }

        return new CarteDigitala(id, titlu, autor, sectiune, anPublicatie, disponibila, resultSet.getInt("marime_mb"));
    }
}
