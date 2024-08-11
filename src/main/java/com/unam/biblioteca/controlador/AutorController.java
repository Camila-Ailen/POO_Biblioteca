package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.Autor;
import com.unam.biblioteca.servicio.Servicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class AutorController {

    //Botones
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;

    //Tabla
    @FXML
    private TableView<Autor> tblVista;
    @FXML
    private TableColumn<Autor, String> colId;
    @FXML
    private TableColumn<Autor, String> colNombre;


    private Servicio servicio;

    @FXML
    private void initialize() {
        servicio = App.getServicio();

        //Inicializar tabla
        try {
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        } catch (Exception e) {
            System.out.println("Error al inicializar la tabla 1, entrando al crud");
        }

        //tblVista.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());

        try {
            actualizarTabla();
        } catch (Exception e) {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar los datos", "Ocurrió un error al cargar los datos de los autores.", e.getMessage());
        }
    }


    @FXML
    private void crearAutor (ActionEvent event) throws IOException {
        App.setRoot("crudAutor");
        actualizarTabla();
    }

    private void actualizarTabla() {
        tblVista.getItems().clear();
        tblVista.getItems().addAll(servicio.listarAutores());
    }








}













