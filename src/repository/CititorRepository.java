package repository;

import model.Abonament;
import model.Cititor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CititorRepository implements CrudRepository<Cititor> {
    private final Connection connection;

    public CititorRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Cititor cititor) {
        String sql = """
                INSERT INTO cititori (id, nume, email, abonament_id)
                VALUES (?, ?, ?, ?)
                ON CONFLICT (id) DO NOTHING
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cititor.getId());
            statement.setString(2, cititor.getNume());
            statement.setString(3, cititor.getEmail());
            statement.setInt(4, cititor.getAbonament().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la salvarea cititorului: " + e.getMessage());
        }
    }

    public void save(Cititor cititor) {
        create(cititor);
    }

    @Override
    public Cititor findById(int id) {
        String sql = """
                SELECT c.id, c.nume, c.email,
                       a.id AS abonament_id, a.tip, a.pret, a.durata_luni, a.max_imprumuturi
                FROM cititori c
                JOIN abonamente a ON c.abonament_id = a.id
                WHERE c.id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapCititor(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Eroare la citirea cititorului: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Cititor> findAll() {
        List<Cititor> cititori = new ArrayList<>();
        String sql = """
                SELECT c.id, c.nume, c.email,
                       a.id AS abonament_id, a.tip, a.pret, a.durata_luni, a.max_imprumuturi
                FROM cititori c
                JOIN abonamente a ON c.abonament_id = a.id
                ORDER BY c.id
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                cititori.add(mapCititor(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Eroare la citirea cititorilor: " + e.getMessage());
        }

        return cititori;
    }

    @Override
    public void update(Cititor cititor) {
        String sql = "UPDATE cititori SET nume = ?, email = ?, abonament_id = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cititor.getNume());
            statement.setString(2, cititor.getEmail());
            statement.setInt(3, cititor.getAbonament().getId());
            statement.setInt(4, cititor.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la actualizarea cititorului: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM cititori WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la stergerea cititorului: " + e.getMessage());
        }
    }

    private Cititor mapCititor(ResultSet resultSet) throws SQLException {
        Abonament abonament = new Abonament(
                resultSet.getInt("abonament_id"),
                resultSet.getString("tip"),
                resultSet.getDouble("pret"),
                resultSet.getInt("durata_luni"),
                resultSet.getInt("max_imprumuturi")
        );

        return new Cititor(
                resultSet.getInt("id"),
                resultSet.getString("nume"),
                resultSet.getString("email"),
                abonament
        );
    }
}
