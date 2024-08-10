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

    private Servicio servicio;


    @FXML
    private void initialize() {
        servicio = App.getServicio();
        Miembro miembro = (Miembro) UsrLogueado.getInstancia().getVariableGlobal();
        if (miembro != null) {
            lblNombreUsuario.setText("Bienvenido " + miembro.getNombre());
        }

        //Agregar manejadores de eventos a los botones

        if (miembro != null && miembro.getUnRol().getNombre().equals("BIBLIOTECARIO")){
            /*seleccionarBotonNav(btnNavCopias);
            seleccionarBotonNav(btnNavPrestamos);
            seleccionarBotonNav(btnNavRegistro);
            seleccionarBotonNav(btnNavDevolucion);
            seleccionarBotonNav(btnNavParametros);
            seleccionarBotonNav(btnNavAutor);
            seleccionarBotonNav(btnNavTematica);
            seleccionarBotonNav(btnNavEditorial);
            seleccionarBotonNav(btnNavRack);*/

            btnNavAutor.setOnAction(event -> cambiarVista("vistaAutor.fxml"));
            btnNavTematica.setOnAction(event -> cambiarVista("vistaTematica.fxml"));
            btnNavEditorial.setOnAction(event -> {
                cambiarVista("com/unam/biblioteca/vistaEditorial.fxml");
            });
            btnNavRack.setOnAction(event -> {
                cambiarVista("com/unam/biblioteca/vistaRack.fxml");
            });
        }
        /*
        seleccionarBotonNav(btnNavLibros);
        seleccionarBotonNav(btnNavUsuario);*/
        btnNavLibros.setOnAction(event -> {
            cambiarVista("com/unam/biblioteca/vistaLibro.fxml");
        });
        btnNavUsuario.setOnAction(event -> {
            cambiarVista("com/unam/biblioteca/vistaUsuario.fxml");
        });

        //Cargar las pantallas
        /*
        try {
            Node rootAutor = cargarVistas("com/unam/biblioteca/vistaAutor.fxml");
            Node rootTematica = cargarVistas("com/unam/biblioteca/vistaTematica.fxml");
            if (rootAutor != null && rootTematica != null) {
                contenedor.getChildren().addAll(rootAutor, rootTematica);
                rootAutor.setVisible(true);
                rootTematica.setVisible(false);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/

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




    private Node cargarVista (String url) throws IOException {
        return FXMLLoader.load(getClass().getResource("/com/unam/biblioteca/" + url));
    }

    private void cambiarVista (String url) {
        try {
            if (url.startsWith("vista")) {
                Node vista = cargarVista(url);
                if (vista != null) {
                    contenedor.getChildren().clear();
                    contenedor.getChildren().add(vista);
                    ajustarTamanio(vista);
                }
            } else if (url.startsWith("am")) {
                abrirNuevaVentana(url);
            } else {
                App.setRoot(url);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void abrirNuevaVentana(String url) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/unam/biblioteca/" + url));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    private void ajustarTamanio(Node vista) {
        if (vista instanceof Region){
            Region region = (Region) vista;
            region.setPrefWidth(contenedor.getWidth());
            region.setPrefHeight(contenedor.getHeight());
        } else {
            throw new IllegalArgumentException("La vista no es una region");
        }
    }

}
