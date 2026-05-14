package repository;

import model.Rezervare;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RezervareRepository {
    private final Connection connection;

    public RezervareRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Rezervare rezervare) {
        String sql = """
                INSERT INTO rezervari (id, cititor_id, carte_id, data_rezervare, status)
                VALUES (?, ?, ?, ?, ?)
                ON CONFLICT (id) DO NOTHING
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, rezervare.getId());
            statement.setInt(2, rezervare.getCititor().getId());
            statement.setInt(3, rezervare.getCarte().getId());
            statement.setDate(4, Date.valueOf(rezervare.getDataRezervare()));
            statement.setString(5, rezervare.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la salvarea rezervarii: " + e.getMessage());
        }
    }
}
