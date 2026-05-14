package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class AuditService {
    private static AuditService instance;
    private final Path auditPath = Paths.get("audit.csv");

    private AuditService() {
        initializeFile();
    }

    public static synchronized AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public synchronized void logAction(String actionName) {
        String line = actionName + "," + LocalDateTime.now() + System.lineSeparator();
        try {
            Files.writeString(auditPath, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Eroare la scrierea in audit.csv: " + e.getMessage());
        }
    }

    private void initializeFile() {
        if (Files.exists(auditPath)) {
            return;
        }

        try {
            Files.writeString(
                    auditPath,
                    "nume_actiune,timestamp" + System.lineSeparator(),
                    StandardOpenOption.CREATE
            );
        } catch (IOException e) {
            System.err.println("Eroare la initializarea audit.csv: " + e.getMessage());
        }
    }
}
