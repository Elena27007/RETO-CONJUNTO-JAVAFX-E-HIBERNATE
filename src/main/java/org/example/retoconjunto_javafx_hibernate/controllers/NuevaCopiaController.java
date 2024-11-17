package org.example.retoconjunto_javafx_hibernate.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import org.example.retoconjunto_javafx_hibernate.HibernateUtil;
import org.example.retoconjunto_javafx_hibernate.dao.CopiaDAO;
import org.example.retoconjunto_javafx_hibernate.dao.PeliculaDAO;
import org.example.retoconjunto_javafx_hibernate.models.Copia;
import org.example.retoconjunto_javafx_hibernate.models.Pelicula;
import org.example.retoconjunto_javafx_hibernate.models.Usuario;

/**
 * Clase que controla la vista de añadir una nueva copia de una película
 */
public class NuevaCopiaController {

    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtEstado;
    @FXML
    private TextField txtSoporte;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    @Setter
    private Usuario usuario;

    @Setter
    private MainController mainController;

    /**
     * Método que guarda una nueva copia en la base de datos
     */
    @FXML
    private void guardarCopia() {
        PeliculaDAO peliculaDAO = new PeliculaDAO(HibernateUtil.getSessionFactory());
        Pelicula pelicula = peliculaDAO.findByTitulo(txtTitulo.getText());

        if (pelicula != null) {
            Copia copia = new Copia();
            copia.setPelicula(pelicula);
            copia.setEstado(txtEstado.getText());
            copia.setSoporte(txtSoporte.getText());
            copia.setUsuario(usuario);

            CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());
            copiaDAO.save(copia);

            mainController.actualizarTabla();

            Stage stage = (Stage) btnGuardar.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No se ha podido añadir la copia");
            alert.setHeaderText(null);
            alert.setContentText("La película no existe en la base de datos.");
            alert.showAndWait();        }
    }

    /**
     * Método que cierra la ventana de añadir una nueva copia
     */
    @FXML
    private void cancelar() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}
