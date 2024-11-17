package org.example.retoconjunto_javafx_hibernate.dao;

import java.util.List;

/**
 * Interfaz DAO
 * @param <T>
 */
public interface DAO<T>{
    public List<T> findAll();
    public T findById(Long id);
    public void save(T t);
    public void update(T t);
    public void delete(T t);
}
