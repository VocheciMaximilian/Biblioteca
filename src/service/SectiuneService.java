package service;

import Database.DatabaseConnection;
import Database.DatabaseInitializer;
import model.Sectiune;
import repository.SectiuneRepository;

import java.sql.Connection;
import java.util.List;

public class SectiuneService implements CrudService<Sectiune> {
    private static SectiuneService instance;
    private final SectiuneRepository sectiuneRepository;
    private final AuditService auditService = AuditService.getInstance();

    private SectiuneService() {
        Connection connection = DatabaseConnection.getInstance();
        DatabaseInitializer.createTables(connection);
        sectiuneRepository = new SectiuneRepository(connection);
    }

    public static synchronized SectiuneService getInstance() {
        if (instance == null) {
            instance = new SectiuneService();
        }
        return instance;
    }

    @Override
    public void create(Sectiune sectiune) {
        sectiuneRepository.create(sectiune);
        auditService.logAction("create_sectiune");
    }

    @Override
    public Sectiune readById(int id) {
        auditService.logAction("read_sectiune_by_id");
        return sectiuneRepository.findById(id);
    }

    @Override
    public List<Sectiune> readAll() {
        auditService.logAction("read_all_sectiuni");
        return sectiuneRepository.findAll();
    }

    @Override
    public void update(Sectiune sectiune) {
        sectiuneRepository.update(sectiune);
        auditService.logAction("update_sectiune");
    }

    @Override
    public void deleteById(int id) {
        sectiuneRepository.deleteById(id);
        auditService.logAction("delete_sectiune");
    }
}
