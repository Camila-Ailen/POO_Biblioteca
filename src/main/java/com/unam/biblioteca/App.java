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
    private static EntityManagerFactory emf;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Inicializar JPA
        emf = Persistence.createEntityManagerFactory("bibliotecaPU");
        Repositorio repositorio = new Repositorio(emf);
        Servicio servicio = new Servicio(repositorio);

        // Lanzar la aplicación JavaFX
        launch();
    }
}
