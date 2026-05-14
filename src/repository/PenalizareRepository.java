package repository;

import model.Penalizare;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PenalizareRepository {
    private final Connection connection;

    public PenalizareRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Penalizare penalizare) {
        String sql = """
                INSERT INTO penalizari (
                    id, cititor_id, imprumut_id, suma,
                    motiv, platita, data_penalizare
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (id) DO NOTHING
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, penalizare.getId());
            statement.setInt(2, penalizare.getCititor().getId());
            statement.setInt(3, penalizare.getImprumut().getId());
            statement.setDouble(4, penalizare.getSuma());
            statement.setString(5, penalizare.getMotiv());
            statement.setBoolean(6, penalizare.isPlatita());
            statement.setDate(7, Date.valueOf(penalizare.getDataPenalizare()));
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la salvarea penalizarii: " + e.getMessage());
        }
    }
}
