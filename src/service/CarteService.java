package service;

import Database.DatabaseConnection;
import Database.DatabaseInitializer;
import model.Carte;
import repository.CarteRepository;

import java.sql.Connection;
import java.util.List;

public class CarteService implements CrudService<Carte> {
    private static CarteService instance;
    private final CarteRepository carteRepository;
    private final AuditService auditService = AuditService.getInstance();

    private CarteService() {
        Connection connection = DatabaseConnection.getInstance();
        DatabaseInitializer.createTables(connection);
        carteRepository = new CarteRepository(connection);
    }

    public static synchronized CarteService getInstance() {
        if (instance == null) {
            instance = new CarteService();
        }
        return instance;
    }

    @Override
    public void create(Carte carte) {
        carteRepository.create(carte);
        auditService.logAction("create_carte");
    }

    @Override
    public Carte readById(int id) {
        auditService.logAction("read_carte_by_id");
        return carteRepository.findById(id);
    }

    @Override
    public List<Carte> readAll() {
        auditService.logAction("read_all_carti");
        return carteRepository.findAll();
    }

    @Override
    public void update(Carte carte) {
        carteRepository.update(carte);
        auditService.logAction("update_carte");
    }

    @Override
    public void deleteById(int id) {
        carteRepository.deleteById(id);
        auditService.logAction("delete_carte");
    }
}
