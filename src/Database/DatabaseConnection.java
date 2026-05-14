package Database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConnection {

    private static Connection connection = null;
    private static final Map<String, String> env = loadEnv();

    private DatabaseConnection() {
    }

    private static Map<String, String> loadEnv() {
        Map<String, String> map = new HashMap<>();
        Path envPath = findEnvFile();

        if (envPath == null) {
            System.err.println("Avertisment: Nu s-a gasit fisierul .env.");
            return map;
        }

        try {
            for (String line : Files.readAllLines(envPath)) {
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    map.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Avertisment: Nu s-a putut citi fisierul .env - " + e.getMessage());
        }

        return map;
    }

    private static Path findEnvFile() {
        Path[] possiblePaths = {
                Paths.get(".env"),
                Paths.get("Biblioteca", ".env"),
                Paths.get("..", ".env")
        };

        for (Path path : possiblePaths) {
            if (Files.exists(path)) {
                return path;
            }
        }

        return null;
    }

    public static Connection getInstance() {
        if (connection == null) {
            try {
                String url = env.getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/biblioteca");
                String user = env.getOrDefault("DB_USER", "postgres");
                String password = env.getOrDefault("DB_PASSWORD", "parola_ta");

                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Conexiune la baza de date realizata cu succes!");
            } catch (SQLException | ClassNotFoundException e) {
                System.err.println("Eroare la conectarea la baza de date: " + e.getMessage());
            }
        }

        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Conexiunea a fost inchisa.");
            } catch (SQLException e) {
                System.err.println("Eroare la inchiderea conexiunii: " + e.getMessage());
            }
        }
    }
}
