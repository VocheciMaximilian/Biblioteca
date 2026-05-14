package repository;

import model.Bibliotecar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BibliotecarRepository {
    private final Connection connection;

    public BibliotecarRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Bibliotecar bibliotecar) {
        String sql = """
                INSERT INTO bibliotecari (id, nume, email, id_angajat, salariu)
                VALUES (?, ?, ?, ?, ?)
                ON CONFLICT (id) DO NOTHING
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bibliotecar.getId());
            statement.setString(2, bibliotecar.getNume());
            statement.setString(3, bibliotecar.getEmail());
            statement.setString(4, bibliotecar.getIdAngajat());
            statement.setDouble(5, bibliotecar.getSalariu());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la salvarea bibliotecarului: " + e.getMessage());
        }
    }
}
