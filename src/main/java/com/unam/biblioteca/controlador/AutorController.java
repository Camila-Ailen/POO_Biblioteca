package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.Autor;
import com.unam.biblioteca.servicio.Servicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private void crear (ActionEvent event) throws IOException {
        App.setRoot("crudAutor");
        actualizarTabla();
        System.out.println("Intente actualizar!!");
    }

    @FXML
    private void modificar (ActionEvent event) throws IOException {
        Autor autorSeleccionado = tblVista.getSelectionModel().getSelectedItem();
        if (autorSeleccionado != null) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("crudAutor.fxml"));
            Parent root = loader.load();

            AutorAbmController controlador = loader.getController();
            controlador.setDatosAutor(autorSeleccionado);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modificar autor");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            actualizarTabla();
        } else {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al modificar", "No se ha seleccionado un autor", "Por favor, seleccione un autor de la tabla.");
        }
    }

    @FXML
    private void eliminar (ActionEvent event) {
        Autor autorSeleccionado = tblVista.getSelectionModel().getSelectedItem();
        if (autorSeleccionado != null) {
            Alert respuesta = new Alert(Alert.AlertType.CONFIRMATION);
            respuesta.setTitle("Eliminar autor");
            respuesta.setHeaderText("¿Está seguro que desea eliminar el autor?");
            respuesta.setContentText("Esta acción no se puede deshacer.");
            if (respuesta.showAndWait().get() == ButtonType.OK) {
                servicio.borrarAutor(autorSeleccionado.getId());
                actualizarTabla();
            }
        } else {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al eliminar", "Ocurrió un error al eliminar el autor.", "Por favor, seleccione un autor de la tabla.");
        }
    }

    private void actualizarTabla() {
        tblVista.getItems().clear();
        tblVista.getItems().addAll(servicio.listarAutores());
    }








}













