module org.example.retoconjunto_javafx_hibernate {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires jakarta.persistence;
    requires java.naming;
    requires static lombok;
    requires java.desktop;
    requires jasperreports;

    opens org.example.retoconjunto_javafx_hibernate.models to org.hibernate.orm.core, javafx.base;
    opens org.example.retoconjunto_javafx_hibernate.controllers to javafx.fxml;

    exports org.example.retoconjunto_javafx_hibernate;
    exports org.example.retoconjunto_javafx_hibernate.controllers;
    exports org.example.retoconjunto_javafx_hibernate.models;
}