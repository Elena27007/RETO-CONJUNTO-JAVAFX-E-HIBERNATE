package org.example.retoconjunto_javafx_hibernate.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.retoconjunto_javafx_hibernate.HelloApplication;
import org.example.retoconjunto_javafx_hibernate.Sesion;
import org.example.retoconjunto_javafx_hibernate.dao.UsuarioDAO;
import org.example.retoconjunto_javafx_hibernate.models.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que controla la vista de login de la aplicación y valida el usuario en la base de datos
 */
public class LoginController implements Initializable {
    @javafx.fxml.FXML
    private Button btnLogin;
    @javafx.fxml.FXML
    private TextField txtUsuario;
    @javafx.fxml.FXML
    private Button btnSalir;
    @javafx.fxml.FXML
    private PasswordField txtPassword;
    @javafx.fxml.FXML
    private Label info;

    /**
     * Método que valida el usuario en la base de datos y muestra un mensaje de error si no se encuentra
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void logear(ActionEvent actionEvent) {
        Usuario user = new UsuarioDAO().validarUsuario(
                txtUsuario.getText(),
                txtPassword.getText());

        if (user == null) {
            info.setText("Usuario no encontrado");
        } else {
            info.setText("Has iniciado sesión correctamente");
            Sesion.currentUser = user;
            HelloApplication.loadFXML("/views/main-view.fxml", "Sesión iniciada - " + user.getNombreUsuario());
        }
    }

    /**
     * Método que cierra la aplicación
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void cerrarAplicación(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Método que inicializa la vista de login
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}