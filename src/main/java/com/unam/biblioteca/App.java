package com.unam.biblioteca;

import com.unam.biblioteca.repositorio.Repositorio;
import com.unam.biblioteca.servicio.Servicio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
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

        // Cargar la escena inicial
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("navBibliotecario.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("BLUE LIBRARY");
        stage.setScene(scene);
        stage.show();
    }

    public static Servicio getServicio() {
        return servicio;
    }


    public static void setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();

        if (fxml.startsWith("crud")){
            //inicia cualquier pantalla de crud
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setTitle("Operaciones de CRUD");
            newStage.setScene(new Scene(root));

            newStage.showAndWait();
        } else {

            scene.setRoot(root);

            Stage stage = (Stage) scene.getWindow();

            stage.setWidth(root.prefWidth(-1));
            stage.setHeight(root.prefHeight(-1));
            stage.centerOnScreen();
        }
    }


        public static void main(String[] args) {
        // Lanzar la aplicación JavaFX
        launch();
    }
}