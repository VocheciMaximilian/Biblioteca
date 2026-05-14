package repository;

import model.Autor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutorRepository implements CrudRepository<Autor> {
    private final Connection connection;

    public AutorRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Autor autor) {
        String sql = """
                INSERT INTO autori (id, nume, nationalitate)
                VALUES (?, ?, ?)
                ON CONFLICT (id) DO NOTHING
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, autor.getId());
            statement.setString(2, autor.getNume());
            statement.setString(3, autor.getNationalitate());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la salvarea autorului: " + e.getMessage());
        }
    }

    public void save(Autor autor) {
        create(autor);
    }

    @Override
    public Autor findById(int id) {
        String sql = "SELECT id, nume, nationalitate FROM autori WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapAutor(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Eroare la citirea autorului: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Autor> findAll() {
        List<Autor> autori = new ArrayList<>();
        String sql = "SELECT id, nume, nationalitate FROM autori ORDER BY id";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                autori.add(mapAutor(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Eroare la citirea autorilor: " + e.getMessage());
        }

        return autori;
    }

    @Override
    public void update(Autor autor) {
        String sql = "UPDATE autori SET nume = ?, nationalitate = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, autor.getNume());
            statement.setString(2, autor.getNationalitate());
            statement.setInt(3, autor.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la actualizarea autorului: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM autori WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la stergerea autorului: " + e.getMessage());
        }
    }

    private Autor mapAutor(ResultSet resultSet) throws SQLException {
        return new Autor(
                resultSet.getInt("id"),
                resultSet.getString("nume"),
                resultSet.getString("nationalitate")
        );
    }
}
