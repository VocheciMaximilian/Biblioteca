package service;

import Database.DatabaseConnection;
import Database.DatabaseInitializer;
import model.Autor;
import repository.AutorRepository;

import java.sql.Connection;
import java.util.List;

public class AutorService implements CrudService<Autor> {
    private static AutorService instance;
    private final AutorRepository autorRepository;
    private final AuditService auditService = AuditService.getInstance();

    private AutorService() {
        Connection connection = DatabaseConnection.getInstance();
        DatabaseInitializer.createTables(connection);
        autorRepository = new AutorRepository(connection);
    }

    public static synchronized AutorService getInstance() {
        if (instance == null) {
            instance = new AutorService();
        }
        return instance;
    }

    @Override
    public void create(Autor autor) {
        autorRepository.create(autor);
        auditService.logAction("create_autor");
    }

    @Override
    public Autor readById(int id) {
        auditService.logAction("read_autor_by_id");
        return autorRepository.findById(id);
    }

    @Override
    public List<Autor> readAll() {
        auditService.logAction("read_all_autori");
        return autorRepository.findAll();
    }

    @Override
    public void update(Autor autor) {
        autorRepository.update(autor);
        auditService.logAction("update_autor");
    }

    @Override
    public void deleteById(int id) {
        autorRepository.deleteById(id);
        auditService.logAction("delete_autor");
    }
}
