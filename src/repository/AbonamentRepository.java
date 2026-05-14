package repository;

import model.Abonament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AbonamentRepository {
    private final Connection connection;

    public AbonamentRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Abonament abonament) {
        String sql = """
                INSERT INTO abonamente (id, tip, pret, durata_luni, max_imprumuturi)
                VALUES (?, ?, ?, ?, ?)
                ON CONFLICT (id) DO NOTHING
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, abonament.getId());
            statement.setString(2, abonament.getTip());
            statement.setDouble(3, abonament.getPret());
            statement.setInt(4, abonament.getDurataLuni());
            statement.setInt(5, abonament.getMaxImprumuturi());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la salvarea abonamentului: " + e.getMessage());
        }
    }
}
