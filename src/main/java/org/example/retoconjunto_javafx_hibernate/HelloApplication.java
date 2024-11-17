package org.example.retoconjunto_javafx_hibernate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Esta clase es la encargada de cargar la ventana principal de la aplicación y de cargar las vistas
 */
public class HelloApplication extends Application {

    private static Stage ventana;

    /**
     * Método que carga la vista de la ventana principal de la aplicación
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        ventana = stage;
        loadFXML("/views/login-view.fxml","Películas - Login");
        stage.show();
    }

    /**
     * Método que carga una vista en la ventana principal de la aplicación
     * @param view
     * @param title
     */
    public static void loadFXML(String view, String title) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(view));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(),800,600);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ventana.setTitle(title);
        ventana.setScene(scene);
        ventana.setResizable(false);
    }

    /**
     * Método que lanza la aplicación
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}