package org.example.retoconjunto_javafx_hibernate.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import org.example.retoconjunto_javafx_hibernate.HibernateUtil;
import org.example.retoconjunto_javafx_hibernate.dao.CopiaDAO;
import org.example.retoconjunto_javafx_hibernate.dao.PeliculaDAO;
import org.example.retoconjunto_javafx_hibernate.models.Copia;
import org.example.retoconjunto_javafx_hibernate.models.Pelicula;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que controla la vista de detalle de una película y permite modificarla
 */
public class DetalleController implements Initializable {

    private Copia copia;
    @FXML
    private Label lblSoporte1;
    @FXML
    private Label lblDirector1;
    @FXML
    private Label lblAño1;
    @FXML
    private Label lblDescripcion1;
    @FXML
    private Label lblGenero1;
    @FXML
    private Label lblTitulo1;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private Label lblEstado1;
    @FXML
    private TextField txtDirector;
    @FXML
    private TextField txtSoporte;
    @FXML
    private TextField txtAño;
    @FXML
    private TextField txtGenero;
    @FXML
    private Button btnVolver;
    @FXML
    private TextField txtEstado;
    @FXML
    private Button btnModificar;
    @FXML
    private TextField txtTitulo;

    @Setter
    private MainController mainController;


    /**
     * Método que recibe una copia y la muestra en la vista
     * @param copia
     */
    public void setCopia(Copia copia) {
        this.copia = copia;
        txtTitulo.setText(copia.getPelicula().getTitulo());
        txtDirector.setText(copia.getPelicula().getDirector());
        txtGenero.setText(copia.getPelicula().getGenero());
        txtEstado.setText(copia.getEstado());
        txtSoporte.setText(copia.getSoporte());
        txtAño.setText(copia.getPelicula().getAño().toString());
        txtDescripcion.setText(copia.getPelicula().getDescripcion());
    }

    /**
     * Método que cierra la ventana actual
     */
    @FXML
    private void volver() {
        Stage stage = (Stage) txtTitulo.getScene().getWindow();
        stage.close();
    }

    /**
     * Método que modifica los datos de la copia seleccionada
     */
    @FXML
    private void editar() {
        PeliculaDAO peliculaDAO = new PeliculaDAO(HibernateUtil.getSessionFactory());
        Pelicula pelicula = peliculaDAO.findByTitulo(txtTitulo.getText());
        Copia copia = this.copia;
        copia.setPelicula(pelicula);
        copia.setEstado(txtEstado.getText());
        copia.setSoporte(txtSoporte.getText());
        copia.setUsuario(this.copia.getUsuario());

        CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());
        copiaDAO.update(copia);

        mainController.actualizarTabla();
    }

    /**
     * Método que inicializa la vista
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}