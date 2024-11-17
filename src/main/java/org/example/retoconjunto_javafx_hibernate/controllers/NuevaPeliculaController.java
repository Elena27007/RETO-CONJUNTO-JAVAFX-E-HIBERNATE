package org.example.retoconjunto_javafx_hibernate.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.retoconjunto_javafx_hibernate.HibernateUtil;
import org.example.retoconjunto_javafx_hibernate.dao.PeliculaDAO;
import org.example.retoconjunto_javafx_hibernate.models.Pelicula;

/**
 * Clase que controla la vista de nueva película
 */
public class NuevaPeliculaController {

    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtDirector;
    @FXML
    private TextField txtGenero;
    @FXML
    private TextField txtAno;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    /**
     * Guarda una nueva película en la base de datos
     */
    @FXML
    private void guardarPelicula() {
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo(txtTitulo.getText());
        pelicula.setDirector(txtDirector.getText());
        pelicula.setGenero(txtGenero.getText());
        pelicula.setAño(Long.parseLong(txtAno.getText()));
        pelicula.setDescripcion(txtDescripcion.getText());

        PeliculaDAO peliculaDAO = new PeliculaDAO(HibernateUtil.getSessionFactory());
        peliculaDAO.save(pelicula);

        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.close();
    }

    /**
     * Cierra la ventana de nueva película
     */
    @FXML
    private void cancelar() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}
