package org.example.retoconjunto_javafx_hibernate.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.retoconjunto_javafx_hibernate.HelloApplication;
import org.example.retoconjunto_javafx_hibernate.HibernateUtil;
import org.example.retoconjunto_javafx_hibernate.ReportService;
import org.example.retoconjunto_javafx_hibernate.Sesion;
import org.example.retoconjunto_javafx_hibernate.dao.CopiaDAO;
import org.example.retoconjunto_javafx_hibernate.dao.PeliculaDAO;
import org.example.retoconjunto_javafx_hibernate.models.Copia;
import org.example.retoconjunto_javafx_hibernate.models.Pelicula;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    @FXML
    private Button btnGenerarInformePeliculas;
    @FXML
    private Button btnGenerarInformeMalEstado;
    @FXML
    private Button btnGenerarInformeMasDeUnaCopia;
    @FXML
    private Button btnGenerarInformeCopia;

    private final PeliculaDAO peliculaDAO = new PeliculaDAO(HibernateUtil.getSessionFactory());
    private final CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());
    private final ReportService reportService = new ReportService();

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
        btnGenerarInformePeliculas.setOnAction(event -> generarInformePeliculas());
        btnGenerarInformeMalEstado.setOnAction(event -> generarInformePeliculasMalEstado());
        btnGenerarInformeMasDeUnaCopia.setOnAction(event -> generarInformePeliculasMasDeUnaCopia());
        btnGenerarInformeCopia.setOnAction(event -> generarInformeCopia());
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

    /**
     * Método que genera un informe en PDF con todas las películas de la base de datos
     */
    @FXML
    private void generarInformePeliculas() {
        try {
            List<Pelicula> peliculas = peliculaDAO.findAll();
            InputStream logo = getClass().getResourceAsStream("img.png");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Informe");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(btnGenerarInformePeliculas.getScene().getWindow());
            if (file != null) {
                reportService.generateReport(peliculas, "Listado Completo de Películas", logo);
                alertar("Informe generado con éxito.");
            }
        } catch (Exception e) {
            alertar("Error al generar el informe: " + e.getMessage());
        }
    }

    /**
     * Método que genera un informe en PDF con las películas en mal estado de la base de datos
     */
    @FXML
    private void generarInformePeliculasMalEstado() {
        try {
            List<Copia> copias = copiaDAO.findByEstado("Mal Estado");
            InputStream logo = getClass().getResourceAsStream("/images/logo.png");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Informe");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(btnGenerarInformeMalEstado.getScene().getWindow());
            if (file != null) {
                reportService.generateReport(copias, "Informe de peliculas en mal estado", logo);
                alertar("Informe generado con éxito.");
            }
        } catch (Exception e) {
            alertar("Error al generar el informe: " + e.getMessage());
        }
    }

    /**
     * Método que genera un informe en PDF con las películas que tienen más de una copia en la base de datos
     */
    @FXML
    private void generarInformePeliculasMasDeUnaCopia() {
        try {
            List<Copia> copias = copiaDAO.findWithMoreThanOneCopy();
            InputStream logo = getClass().getResourceAsStream("/images/logo.png");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Informe");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(btnGenerarInformeMasDeUnaCopia.getScene().getWindow());
            if (file != null) {
                reportService.generateReport(copias, "Películas con Más de una Copia", logo);
                alertar("Informe generado con éxito.");
            }
        } catch (Exception e) {
            alertar("Error al generar el informe: " + e.getMessage());
        }
    }

    /**
     * Método que genera un informe en PDF con la información detallada de la copia seleccionada
     */
    @FXML
    private void generarInformeCopia() {
        try {
            Copia copiaSeleccionada = tableView.getSelectionModel().getSelectedItem();
            if (copiaSeleccionada != null) {
                InputStream logo = getClass().getResourceAsStream("/images/logo.png");
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Guardar Informe");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
                File file = fileChooser.showSaveDialog(btnGenerarInformeCopia.getScene().getWindow());
                if (file != null) {
                    reportService.generateReport(List.of(copiaSeleccionada), "Información Detallada de la Copia", logo);
                    alertar("Informe generado con éxito.");
                }
            } else {
                alertar("Por favor, seleccione una copia.");
            }
        } catch (Exception e) {
            alertar("Error al generar el informe: " + e.getMessage());
        }
    }

    /**
     * Método que muestra una alerta con un mensaje
     * @param mensaje
     */
    private void alertar(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}