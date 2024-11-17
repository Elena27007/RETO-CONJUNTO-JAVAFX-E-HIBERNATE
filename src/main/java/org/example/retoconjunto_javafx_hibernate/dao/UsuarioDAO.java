package org.example.retoconjunto_javafx_hibernate.dao;

import org.example.retoconjunto_javafx_hibernate.HibernateUtil;
import org.example.retoconjunto_javafx_hibernate.models.Usuario;
import org.hibernate.Session;

import java.util.List;

/**
 * Clase que implementa la interfaz DAO y se encarga de realizar las operaciones
 * de acceso a datos de la entidad Usuario.
 */
public class UsuarioDAO implements DAO<Usuario> {

    /**
     * Método que valida si un usuario existe en la base de datos
     * @param nombre
     * @param contra
     * @return
     */
    public Usuario validarUsuario(String nombre, String contra) {
        Usuario output = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Usuario> query = session.createQuery("FROM Usuario WHERE nombreUsuario = :email AND contraseña = :password", Usuario.class)
                    .setParameter("email", nombre)
                    .setParameter("password", contra)
                    .list();
            if (!query.isEmpty()) {
                output = query.get(0);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return output;
    }

    /**
     * Método que devuelve una lista con todos los usuarios de la base de datos
     * @return
     */
    @Override
    public List<Usuario> findAll() {
        return List.of();
    }

    /**
     * Método que devuelve un usuario por su id
     * @param id
     * @return
     */
    @Override
    public Usuario findById(Long id) {
        return null;
    }

    /**
     * Método que guarda un usuario en la base de datos
     * @param usuario
     */
    @Override
    public void save(Usuario usuario) {

    }

    /**
     * Método que actualiza un usuario en la base de datos
     * @param usuario
     */
    @Override
    public void update(Usuario usuario) {

    }

    /**
     * Método que elimina un usuario de la base de datos
     * @param usuario
     */
    @Override
    public void delete(Usuario usuario) {

    }
}
