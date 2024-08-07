package com.unam.biblioteca;

import com.unam.biblioteca.repositorio.Repositorio;
import com.unam.biblioteca.servicio.Servicio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.IOException;

public class App extends Application {
    private static Scene scene;
    private static Servicio servicio;

    private static EntityManagerFactory emf;

    @Override
    public void start(Stage stage) throws IOException {
        // Inicializar JPA
        var emf = Persistence.createEntityManagerFactory("bibliotecaPU");
        servicio = new Servicio(new Repositorio(emf));

        // Cargar la escena principal
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static Servicio getServicio() {
        return servicio;
    }

    public static FXMLLoader setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        scene.setRoot(fxmlLoader.load());
        return fxmlLoader;
    }

    public static void main(String[] args) {
        // Lanzar la aplicación JavaFX
        launch();
    }
}