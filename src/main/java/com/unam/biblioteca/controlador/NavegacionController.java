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
    private static StackPane contenedor;

    //Pantallas a conectar
    private GridPane rootAutor;
    private GridPane rootTematica;

    private Servicio servicio;


    @FXML
    private void initialize() throws IOException {
        servicio = App.getServicio();
        Miembro miembro = (Miembro) UsrLogueado.getInstancia().getVariableGlobal();
        if (miembro != null) {
            lblNombreUsuario.setText("Bienvenido " + miembro.getNombre());
        }

        inicializarContenedor();

        //Agregar manejadores de eventos a los botones



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


    private static Node cargarVista (String url) throws IOException {
        System.out.println("Cargando vista: " + url);
        return FXMLLoader.load(NavegacionController.class.getResource("/com/unam/biblioteca/" + url + ".fxml"));
    }

    public static void cambiarVista (String url) {
        System.out.println("Estamos en cambiaVista");
        try {
            Node vista = cargarVista(url);
            System.out.println("Vista cargada");
            if (vista != null) {
                System.out.println("la vista no es nula");
                contenedor.getChildren().clear();
                System.out.println("pasamos la primera parte de contenedor");
                contenedor.getChildren().add(vista);
                System.out.println("pasamos la segunda parte de contenedor");
                ajustarTamanio(vista);
            }
            //App.setRoot(url);
        } catch (IOException e) {
            System.out.println("No se pudo en cambiar la vista");
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

    private static void ajustarTamanio(Node vista) {
        if (vista instanceof Region){
            Region region = (Region) vista;
            region.setPrefWidth(contenedor.getWidth());
            region.setPrefHeight(contenedor.getHeight());
        } else {
            throw new IllegalArgumentException("La vista no es una region");
        }
    }

    private static void inicializarContenedor(){
        if (contenedor == null){
            contenedor = new StackPane();
        }
    }

}
