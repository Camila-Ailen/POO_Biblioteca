package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.Miembro;
import com.unam.biblioteca.modelo.UsrLogueado;
import com.unam.biblioteca.servicio.Servicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class NavegacionController {

    //Botones de navegacion
    @FXML
    private Button btnNavLibros;
    @FXML
    private Button btnNavCopias;
    @FXML
    private Button btnNavPrestamos;
    @FXML
    private Button btnNavRegistro;
    @FXML
    private Button btnNavDevolucion;
    @FXML
    private Button btnNavUsuario;
    @FXML
    private Button btnNavParametros;
    @FXML
    private Button btnNavAutor;
    @FXML
    private Button btnNavTematica;
    @FXML
    private Button btnNavEditorial;
    @FXML
    private Button btnNavRack;
    @FXML
    private Hyperlink btnNavLogout;

    //Nombre del usuario de la sesion
    @FXML
    private Label lblNombreUsuario;

    //Contenedor stackpane
    @FXML
    private StackPane contenedor;

    //Pantallas a conectar
    private GridPane rootAutor;
    private GridPane rootTematica;
    private VBox rootAutor2;
    private VBox rootTematica2;

    private Servicio servicio;

    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    public void navegar(ActionEvent e) throws IOException {
        Object evt = e.getSource();

        if (evt.equals(btnNavAutor)) {
            rootAutor.setVisible(true);
            rootTematica.setVisible(false);
        } else if (evt.equals(btnNavTematica)) {
            rootAutor.setVisible(false);
            rootTematica.setVisible(true);
        }

    }


    @FXML
    private void initialize() {
        System.out.println("Inicializando NavegacionController");
        servicio = App.getServicio();
        System.out.println("Servicio: " + servicio);
        Miembro miembro = (Miembro) UsrLogueado.getInstancia().getVariableGlobal();
        if (miembro != null) {
            lblNombreUsuario.setText("Bienvenido " + miembro.getNombre());
        }

        System.out.println("ahora intentaremos la magia");
        try {
            System.out.println("Entrando 1");
            rootAutor = loadForm("/com/unam/biblioteca/vistaAutor.fxml");
            System.out.println("Entrando 2");
            rootTematica = loadForm("/com/unam/biblioteca/vistaTematica.fxml");
            System.out.println("Entrando 3");
            contenedor.getChildren().addAll(rootAutor, rootTematica);
            System.out.println("Entrando 4");
            rootAutor.setVisible(true);
            System.out.println("Entrando 5");
            rootTematica.setVisible(false);
            System.out.println("Entrando 6");
        } catch (IOException e) {
            System.out.println("Hasta aca llegamos");
            throw new RuntimeException(e);
        }
    }


    private GridPane loadForm (String url) throws IOException {
        return (GridPane) FXMLLoader.load(getClass().getResource(url));
    }


    @FXML
    private void navegarAutor(ActionEvent event) throws IOException {
        App.setRoot("vistaAutor");
    }

    private void seleccionarBotonNav(Button button) {
        button.setOnAction(event -> {
            resetearEstilos();

            button.setStyle("-fx-background-color: #4682b4; -fx-font-weight: bold; fx-border-color: WHITE;");
        });
    }

    private void resetearEstilos() {
        Miembro miembro = (Miembro) UsrLogueado.getInstancia().getVariableGlobal();
        if (miembro.getUnRol().getNombre().equals("BIBLIOTECARIO")){
            btnNavCopias.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            btnNavPrestamos.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            btnNavRegistro.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            btnNavDevolucion.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            btnNavParametros.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            btnNavAutor.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            btnNavTematica.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            btnNavEditorial.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            btnNavRack.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        }
        btnNavLibros.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        btnNavUsuario.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
    }

    //Acciones de navegacion




}
