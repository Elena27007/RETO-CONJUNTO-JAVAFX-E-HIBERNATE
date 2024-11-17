package org.example.retoconjunto_javafx_hibernate.dao;

import org.example.retoconjunto_javafx_hibernate.models.Pelicula;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Clase que implementa la interfaz DAO para la entidad Pelicula y define los métodos para acceder a la base de datos
 */
public class PeliculaDAO implements DAO<Pelicula> {
    private final SessionFactory sessionFactory;

    /**
     * Constructor de la clase
     * @param sessionFactory
     */
    public PeliculaDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Método que devuelve todas las películas de la base de datos
     * @return
     */
    @Override
    public List<Pelicula> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Pelicula", Pelicula.class).list();
        }
    }

    /**
     * Método que devuelve una película por su id
     * @param id
     * @return
     */
    @Override
    public Pelicula findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Pelicula.class, id);
        }
    }

    /**
     * Método que guarda una película en la base de datos
     * @param pelicula
     */
    @Override
    public void save(Pelicula pelicula) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(pelicula);
            session.getTransaction().commit();
        }
    }

    /**
     * Método que actualiza una película en la base de datos
     * @param pelicula
     */
    @Override
    public void update(Pelicula pelicula) {

    }

    /**
     * Método que elimina una película de la base de datos
     * @param pelicula
     */
    @Override
    public void delete(Pelicula pelicula) {

    }

    /**
     * Método que devuelve una película por su título
     * @param titulo
     * @return
     */
    public Pelicula findByTitulo(String titulo) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Pelicula where titulo = :titulo", Pelicula.class)
                    .setParameter("titulo", titulo)
                    .uniqueResult();
        }
    }
}
