package repository;

import model.Imprumut;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImprumutRepository {
    private final Connection connection;

    public ImprumutRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Imprumut imprumut) {
        String sql = """
                INSERT INTO imprumuturi (
                    id, cititor_id, bibliotecar_id, carte_id,
                    data_imprumut, data_returnare, activ
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (id) DO NOTHING
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, imprumut.getId());
            statement.setInt(2, imprumut.getCititor().getId());
            statement.setInt(3, imprumut.getBibliotecar().getId());
            statement.setInt(4, imprumut.getCarte().getId());
            statement.setDate(5, Date.valueOf(imprumut.getDataImprumut()));
            statement.setDate(6, Date.valueOf(imprumut.getDataReturnare()));
            statement.setBoolean(7, imprumut.getActiv());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la salvarea imprumutului: " + e.getMessage());
        }
    }
}
