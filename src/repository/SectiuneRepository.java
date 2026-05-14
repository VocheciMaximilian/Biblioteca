package repository;

import model.Sectiune;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SectiuneRepository implements CrudRepository<Sectiune> {
    private final Connection connection;

    public SectiuneRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Sectiune sectiune) {
        String sql = """
                INSERT INTO sectiuni (id, nume)
                VALUES (?, ?)
                ON CONFLICT (id) DO NOTHING
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, sectiune.getId());
            statement.setString(2, sectiune.getNume());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la salvarea sectiunii: " + e.getMessage());
        }
    }

    public void save(Sectiune sectiune) {
        create(sectiune);
    }

    @Override
    public Sectiune findById(int id) {
        String sql = "SELECT id, nume FROM sectiuni WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapSectiune(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Eroare la citirea sectiunii: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Sectiune> findAll() {
        List<Sectiune> sectiuni = new ArrayList<>();
        String sql = "SELECT id, nume FROM sectiuni ORDER BY id";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                sectiuni.add(mapSectiune(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Eroare la citirea sectiunilor: " + e.getMessage());
        }

        return sectiuni;
    }

    @Override
    public void update(Sectiune sectiune) {
        String sql = "UPDATE sectiuni SET nume = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, sectiune.getNume());
            statement.setInt(2, sectiune.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la actualizarea sectiunii: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM sectiuni WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la stergerea sectiunii: " + e.getMessage());
        }
    }

    private Sectiune mapSectiune(ResultSet resultSet) throws SQLException {
        return new Sectiune(
                resultSet.getInt("id"),
                resultSet.getString("nume")
        );
    }
}
