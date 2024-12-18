package org.example.retoconjunto_javafx_hibernate.dao;

import org.example.retoconjunto_javafx_hibernate.models.Copia;
import org.example.retoconjunto_javafx_hibernate.models.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Clase que implementa la interfaz y contiene los métodos para acceder a la base de datos de la tabla Copia
 */
public class CopiaDAO implements DAO<Copia>{
    private final SessionFactory sessionFactory;

    /**
     * Constructor de la clase
     * @param sessionFactory
     */
    public CopiaDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Método que devuelve una lista con todas las copias de la tabla
     * @return
     */
    @Override
    public List<Copia> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Copia", Copia.class).list();
        }
    }

    /**
     * Método que devuelve una lista con todas las copias de un usuario
     * @param user
     * @return
     */
    public List<Copia> findByUser(Usuario user){
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select c from Copia c join c.usuario u where u.id = :userid", Copia.class)
                    .setParameter("userid", user.getId()).list();
        }
    }

    /**
     * Método que devuelve una copia por su id
     * @param id
     * @return
     */
    @Override
    public Copia findById(Long id) {
        return null;
    }

    /**
     * Método que guarda una copia en la base de datos
     * @param copia
     */
    @Override
    public void save(Copia copia) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(copia);
            session.getTransaction().commit();
        }
    }

    /**
     * Método que actualiza una copia en la base de datos
     * @param copia
     */
    @Override
    public void update(Copia copia) {
        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        session.merge(copia);
        transaction.commit();
        session.close();
    }

    /**
     * Método que elimina una copia de la base de datos
     * @param copia
     */
    @Override
    public void delete(Copia copia) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.remove(copia);
            session.getTransaction().commit();
        }
    }

    /**
     * Método que devuelve una lista con todas las copias que tienen un estado concreto
     * @param estado
     * @return
     */
    public List<Copia> findByEstado(String estado) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Copia where estado = :estado", Copia.class)
                    .setParameter("estado", estado)
                    .list();
        }
    }

    /**
     * Método que devuelve una lista con todas las copias que tienen más de una copia
     * @return
     */
    public List<Copia> findWithMoreThanOneCopy() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Copia group by pelicula having count(*) > 1", Copia.class)
                    .list();
        }
    }
}
