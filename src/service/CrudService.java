package service;

import java.util.List;

public interface CrudService<T> {
    void create(T entity);

    T readById(int id);

    List<T> readAll();

    void update(T entity);

    void deleteById(int id);
}
