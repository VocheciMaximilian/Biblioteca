package service;

import Database.DatabaseConnection;
import Database.DatabaseInitializer;
import model.Cititor;
import repository.CititorRepository;

import java.sql.Connection;
import java.util.List;

public class CititorService implements CrudService<Cititor> {
    private static CititorService instance;
    private final CititorRepository cititorRepository;
    private final AuditService auditService = AuditService.getInstance();

    private CititorService() {
        Connection connection = DatabaseConnection.getInstance();
        DatabaseInitializer.createTables(connection);
        cititorRepository = new CititorRepository(connection);
    }

    public static synchronized CititorService getInstance() {
        if (instance == null) {
            instance = new CititorService();
        }
        return instance;
    }

    @Override
    public void create(Cititor cititor) {
        cititorRepository.create(cititor);
        auditService.logAction("create_cititor");
    }

    @Override
    public Cititor readById(int id) {
        auditService.logAction("read_cititor_by_id");
        return cititorRepository.findById(id);
    }

    @Override
    public List<Cititor> readAll() {
        auditService.logAction("read_all_cititori");
        return cititorRepository.findAll();
    }

    @Override
    public void update(Cititor cititor) {
        cititorRepository.update(cititor);
        auditService.logAction("update_cititor");
    }

    @Override
    public void deleteById(int id) {
        cititorRepository.deleteById(id);
        auditService.logAction("delete_cititor");
    }
}
