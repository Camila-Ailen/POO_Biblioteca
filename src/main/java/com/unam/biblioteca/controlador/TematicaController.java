package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.Tematica;
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
            throw new RuntimeException(e);
        }

        try {
            actualizarTabla();
        } catch (Exception e) {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar los datos", "Ocurrió un error al cargar los datos de las tematicas.", e.getMessage());
        }
    }

    @FXML
    private void crear (ActionEvent event) throws IOException {
        App.setRoot("crudTematica");
        actualizarTabla();
    }

    @FXML
    private void modificar (ActionEvent event) throws IOException {
        Tematica tematicaSeleccionada = tblVista.getSelectionModel().getSelectedItem();
        if (tematicaSeleccionada != null) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("crudTematica.fxml"));
            Parent root = loader.load();

            TematicaAbmController controlador = loader.getController();
            controlador.setDatosTematica(tematicaSeleccionada);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modificar Tematica");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            actualizarTabla();
        } else {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al modificar", "No se ha seleccionado una tematica", "Por favor, seleccione una tematica de la tabla.");
        }
    }

    @FXML
    private void eliminar (ActionEvent event) {
        Tematica tematicaSeleccionado = tblVista.getSelectionModel().getSelectedItem();
        if (tematicaSeleccionado != null) {
            Alert respuesta = new Alert(Alert.AlertType.CONFIRMATION);
            respuesta.setTitle("Eliminar tematica");
            respuesta.setHeaderText("¿Está seguro que desea eliminar la tematica?");
            respuesta.setContentText("Esta acción no se puede deshacer.");
            if (respuesta.showAndWait().get() == ButtonType.OK) {
                servicio.borrarTematica(tematicaSeleccionado.getId());
                actualizarTabla();
            }
        } else {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al eliminar", "Ocurrió un error al eliminar la tematica.", "Por favor, seleccione una tematica de la tabla.");
        }
    }

    private void actualizarTabla() {
        tblVista.getItems().clear();
        tblVista.getItems().addAll(servicio.listarTematicas());
    }
}
