package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.Miembro;
import com.unam.biblioteca.modelo.UsrLogueado;
import com.unam.biblioteca.servicio.Servicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

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
    private Button btnNavAutor;
    @FXML
    private Button btnNavTematica;
    @FXML
    private Button btnNavIdioma;
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
    private GridPane rootLibros;
    private GridPane rootEditorial;
    private GridPane rootRack;
    private GridPane rootIdioma;
    private GridPane rootUsuario;
    private GridPane rootCopia;
    private GridPane rootRegistro;

    private Servicio servicio;

    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void mostrarPanel (Button boton, GridPane ... paneles) {
        //deja todos en falso (no visibles)
        for (GridPane panel : paneles) {
            panel.setVisible(false);
        }

        String seleccionado = boton.getId();
        System.out.println("Mi variables seleccionado: " + seleccionado);

        //deja visible el panel seleccionado
        switch (seleccionado) {
            case "btnNavLibros":
                rootLibros.setVisible(true);
                break;
            case "btnNavCopias":
                rootCopia.setVisible(true);
                break;
            case "btnNavRegistro":
                rootRegistro.setVisible(true);
                break;
            /*case "btnNavPrestamos":
                rootPrestamos.setVisible(true);
                break;

            case "btnNavDevolucion":
                rootDevolucion.setVisible(true);
                break;*/
            case "btnNavUsuario":
                rootUsuario.setVisible(true);
                break;
            case "btnNavAutor":
                rootAutor.setVisible(true);
                break;
            case "btnNavTematica":
                rootTematica.setVisible(true);
                break;
            case "btnNavEditorial":
                rootEditorial.setVisible(true);
                break;
            case "btnNavRack":
                rootRack.setVisible(true);
                break;
            case "btnNavIdioma":
                rootIdioma.setVisible(true);
                break;
        }
    }

    @FXML
    public void navegar(ActionEvent e) throws IOException {
        Object evt = e.getSource();

        mostrarPanel((Button) evt, rootLibros, rootAutor, rootTematica, rootEditorial, rootRack, rootIdioma, rootUsuario, rootCopia, rootRegistro);
    }


    @FXML
    private void initialize() {
        servicio = App.getServicio();
        Miembro miembro = (Miembro) UsrLogueado.getInstancia().getVariableGlobal();
        if (miembro != null) {
            lblNombreUsuario.setText("Bienvenido " + miembro.getNombre());
        }

        try {

            rootAutor = loadForm("/com/unam/biblioteca/vistaAutor.fxml");
            rootTematica = loadForm("/com/unam/biblioteca/vistaTematica.fxml");
            rootLibros = loadForm("/com/unam/biblioteca/vistaLibros.fxml");
            rootEditorial = loadForm("/com/unam/biblioteca/vistaEditorial.fxml");
            rootRack = loadForm("/com/unam/biblioteca/vistaRack.fxml");
            rootIdioma = loadForm("/com/unam/biblioteca/vistaIdioma.fxml");
            rootUsuario = loadForm("/com/unam/biblioteca/vistaMiembro.fxml");
            rootCopia = loadForm("/com/unam/biblioteca/vistaCopia.fxml");
            rootRegistro = loadForm("/com/unam/biblioteca/vistaRegistrarPrestamo.fxml");

            contenedor.getChildren().addAll(rootAutor, rootTematica, rootLibros, rootEditorial, rootRack, rootIdioma, rootUsuario, rootCopia, rootRegistro);

            //asegura ingresar con un panel a la vista
            for (GridPane panel : new GridPane[]{rootAutor, rootTematica, rootLibros, rootEditorial, rootRack, rootIdioma, rootUsuario, rootCopia, rootRegistro}) {
                panel.setVisible(false);
            }
            rootLibros.setVisible(true);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private GridPane loadForm (String url) throws IOException {
        return (GridPane) FXMLLoader.load(getClass().getResource(url));
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
            //btnNavPrestamos.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            btnNavRegistro.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            btnNavDevolucion.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            //btnNavParametros.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            btnNavAutor.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            btnNavTematica.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            btnNavEditorial.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            btnNavIdioma.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            btnNavRack.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        }
        btnNavLibros.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        btnNavUsuario.setStyle("-fx-background-color: #e5e8e8; -fx-font-weight: normal; fx-border-color: WHITE; -fx-border-radius: 5px; -fx-background-radius: 5px;");
    }

    //Acciones de navegacion




}
