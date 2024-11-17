package org.example.retoconjunto_javafx_hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Clase que se encarga de configurar la sesión de Hibernate para la conexión con la base de datos
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    /**
     * Configura la sesión de Hibernate con las propiedades necesarias para la conexión con la base de datos
     */
    static{
        sessionFactory = new Configuration()
                .configure()
                .setProperty("hibernate.connection.password",System.getenv("hibernate_password"))
                .setProperty("hibernate.connection.username",System.getenv("hibernate_username"))
                .buildSessionFactory();
    }

    /**
     * Obtiene la sesión de Hibernate
     * @return
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
