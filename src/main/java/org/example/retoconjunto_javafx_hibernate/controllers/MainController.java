package org.example.retoconjunto_javafx_hibernate.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.retoconjunto_javafx_hibernate.HelloApplication;
import org.example.retoconjunto_javafx_hibernate.HibernateUtil;
import org.example.retoconjunto_javafx_hibernate.Sesion;
import org.example.retoconjunto_javafx_hibernate.dao.CopiaDAO;
import org.example.retoconjunto_javafx_hibernate.models.Copia;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Clase que controla la vista principal de la aplicación y muestra las copias del usuario logueado
 */
public class MainController implements Initializable {
    @FXML
    private TableView<Copia> tableView;
    @FXML
    private TableColumn<Copia, String> colTitulo;
    @FXML
    private TableColumn<Copia, String> colEstado;
    @FXML
    private TableColumn<Copia, String> colSoporte;
    @FXML
    private ImageView btnAñadir1;

    /**
     * Método que inicializa la vista y carga las copias del usuario logueado en la tabla
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTitulo.setCellValueFactory(param -> {
            Copia copia = param.getValue();
            if (copia.getPelicula() != null) {
                return new javafx.beans.property.SimpleStringProperty(copia.getPelicula().getTitulo());
            } else {
                return new javafx.beans.property.SimpleStringProperty("No Title");
            }
        });
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colSoporte.setCellValueFactory(new PropertyValueFactory<>("soporte"));

        ObservableList<Copia> copias = FXCollections.observableArrayList(Sesion.currentUser.getCopias());
        tableView.setItems(copias);

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Copia selectedCopia = tableView.getSelectionModel().getSelectedItem();
                if (selectedCopia != null) {
                    mostrarDetalle(selectedCopia);
                }
            }
        });

        btnAñadir1.setVisible(Sesion.currentUser.getAdmin());
    }

    /**
     * Método que muestra una ventana con los detalles de la copia seleccionada
     * @param copia
     */
    private void mostrarDetalle(Copia copia) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/detalle-view.fxml"));
            Parent root = loader.load();
            DetalleController controller = loader.getController();
            controller.setCopia(copia);
            controller.setMainController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Detalle de la Copia");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que cierra la sesión del usuario y vuelve a la pantalla de login
     */
    @FXML
    private void cerrarSesion() {
        Sesion.currentUser = null;
        HelloApplication.loadFXML("/views/login-view.fxml", "Login");
    }

    /**
     * Método que cierra la aplicación
     */
    @FXML
    private void cerrarAplicacion() {
        System.exit(0);
    }

    /**
     * Método que elimina la copia seleccionada de la base de datos y de la tabla
     */
    @FXML
    private void borrarCopia() {
        Copia selectedCopia = tableView.getSelectionModel().getSelectedItem();
        if (selectedCopia != null) {
            CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());
            copiaDAO.delete(selectedCopia);
            tableView.getItems().remove(selectedCopia);
        }
    }

    /**
     * Método que abre una ventana para añadir una nueva copia
     */
    @FXML
    private void nuevaCopia() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/nueva-copia-view.fxml"));
            Parent root = loader.load();
            NuevaCopiaController controller = loader.getController();
            controller.setUsuario(Sesion.currentUser);
            controller.setMainController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Nueva Copia");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que actualiza la tabla de copias con los datos de la base de datos del usuario logueado
     */
    public void actualizarTabla() {
        CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());
        List<Copia> copias = copiaDAO.findByUser(Sesion.currentUser);
        ObservableList<Copia> observableCopias = FXCollections.observableArrayList(copias);
        tableView.setItems(observableCopias);
    }

    /**
     * Método que abre una ventana para añadir una nueva película a la base de datos si el usuario logueado es administrador
     */
    @FXML
    private void nuevaPelicula() {
        if (Sesion.currentUser.getAdmin()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/nueva-pelicula-view.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Nueva Película");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}