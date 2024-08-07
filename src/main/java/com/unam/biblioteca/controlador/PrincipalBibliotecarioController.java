package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;

import com.unam.biblioteca.modelo.Libro;
import com.unam.biblioteca.modelo.Miembro;
import com.unam.biblioteca.modelo.UsrLogueado;
import com.unam.biblioteca.servicio.Servicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class PrincipalBibliotecarioController {

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
    private Button btnNavLogout;

    //Botones de accion
    @FXML
    private Button btnCrearLibro;
    @FXML
    private Button btnModificarLibro;
    @FXML
    private Button btnEliminarLibro;
    @FXML
    private Button btnRecargarTabla;

    //Tabla
    @FXML
    private TableColumn<Libro, String> colIsbn;
    @FXML
    private TableColumn<Libro, String> colTitulo;
    @FXML
    private TableColumn<Libro, String> colAutor;
    @FXML
    private TableColumn<Libro, String> colEditorial;
    @FXML
    private TableColumn<Libro, String> colTematica;
    @FXML
    private TableColumn<Libro, String> colIdioma;
    @FXML
    private TableView<Libro> tblLibros;

    @FXML
    private Label lblNombreUsuario;

    private Servicio servicio;


    @FXML
    private void initialize() {
        servicio = App.getServicio();
        Miembro miembro = (Miembro) UsrLogueado.getInstancia().getVariableGlobal();
        if (miembro != null) {
            lblNombreUsuario.setText("Bienvenido " + miembro.getNombre());
        }

        //inicilizar tabla
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("nombreAutor"));
        colEditorial.setCellValueFactory(new PropertyValueFactory<>("nombreEditorial"));
        colTematica.setCellValueFactory(new PropertyValueFactory<>("nombreTematica"));
        colIdioma.setCellValueFactory(new PropertyValueFactory<>("nombreIdioma"));

        tblLibros.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());

        try {
            tblLibros.getItems().addAll(servicio.listarTodosLosLibros());
        } catch (Exception e) {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al iniciar", e.getMessage());
        }

    }

    //Acciones de navegacion
    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void navLibros(ActionEvent event) throws IOException {
        App.setRoot("libros");
    }

    @FXML
    private void navCopias(ActionEvent event) throws IOException {
        App.setRoot("copias");
    }

    @FXML
    private void navPrestamos(ActionEvent event) throws IOException {
        App.setRoot("prestamos");
    }

    @FXML
    private void navRegistro(ActionEvent event) throws IOException {
        App.setRoot("registro");
    }

    @FXML
    private void navDevolucion(ActionEvent event) throws IOException {
        App.setRoot("devolucion");
    }

    @FXML
    private void navUsuario(ActionEvent event) throws IOException {
        App.setRoot("usuario");
    }

    @FXML
    private void navParametros(ActionEvent event) throws IOException {
        App.setRoot("parametros");
    }

    @FXML
    private void navAutor(ActionEvent event) throws IOException {
        App.setRoot("autor");
    }

    @FXML
    private void navTematica(ActionEvent event) throws IOException {
        App.setRoot("tematica");
    }

    @FXML
    private void navEditorial(ActionEvent event) throws IOException {
        App.setRoot("editorial");
    }

    @FXML
    private void navRack(ActionEvent event) throws IOException {
        App.setRoot("rack");
    }


    //Acciones de la tabla
    @FXML
    private void crear(ActionEvent event) {

    }

    @FXML
    private void modificar(ActionEvent event) {

    }

    @FXML
    private void eliminar(ActionEvent event) {

    }

    @FXML
    private void recargarTabla(ActionEvent event) {

    }


    // agregado a mano (no definido en el FXML)
    private void cargarDatos() {
        var libro = tblLibros.getSelectionModel().getSelectedItem();
        //aca agregar para cargar el cuadrito de abajo (?
    }



}

























