package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.Autor;
import com.unam.biblioteca.modelo.Tematica;
import com.unam.biblioteca.servicio.Servicio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TematicaController {
    //Botones
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;

    //Tabla
    @FXML
    private TableView<Tematica> tblVista;
    @FXML
    private TableColumn<Tematica, String> colId;
    @FXML
    private TableColumn<Tematica, String> colNombre;


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
            tblVista.getItems().addAll(servicio.listarTematica());
        } catch (Exception e) {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar los datos", "Ocurrió un error al cargar los datos de las tematicas.", e.getMessage());
        }
    }
}
