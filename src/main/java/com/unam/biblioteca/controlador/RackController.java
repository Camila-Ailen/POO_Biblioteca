package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.Rack;
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

public class RackController {
    //Botones
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;

    //Tabla
    @FXML
    private TableView<Rack> tblVista;
    @FXML
    private TableColumn<Rack, String> colId;
    @FXML
    private TableColumn<Rack, String> colDescripcion;


    private Servicio servicio;

    @FXML
    private void initialize() {
        servicio = App.getServicio();

        //Inicializar tabla

        try {
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            actualizarTabla();
        } catch (Exception e) {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar los datos", "Ocurrió un error al cargar los datos de los Rack.", e.getMessage());
        }
    }


    @FXML
    private void crear (ActionEvent event) throws IOException {
        App.setRoot("crudRack");
        actualizarTabla();
    }

    @FXML
    private void modificar (ActionEvent event) throws IOException {
        Rack rackSeleccionado = tblVista.getSelectionModel().getSelectedItem();
        if (rackSeleccionado != null) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("crudRack.fxml"));
            Parent root = loader.load();

            RackAbmController controlador = loader.getController();
            controlador.setDatosRack(rackSeleccionado);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modificar Rack");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            actualizarTabla();
        } else {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al modificar", "No se ha seleccionado un Rack", "Por favor, seleccione un Rack de la tabla.");
        }
    }

    @FXML
    private void eliminar (ActionEvent event) {
        Rack rackSeleccionado = tblVista.getSelectionModel().getSelectedItem();
        if (rackSeleccionado != null) {
            Alert respuesta = new Alert(Alert.AlertType.CONFIRMATION);
            respuesta.setTitle("Eliminar Rack");
            respuesta.setHeaderText("¿Está seguro que desea eliminar el Rack?");
            respuesta.setContentText("Esta acción no se puede deshacer.");
            if (respuesta.showAndWait().get() == ButtonType.OK) {
                servicio.borrarRack(rackSeleccionado.getId());
                actualizarTabla();
            }
        } else {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al eliminar", "Ocurrió un error al eliminar el Rack.", "Por favor, seleccione un Rack de la tabla.");
        }
    }

    private void actualizarTabla() {
        tblVista.getItems().clear();
        tblVista.getItems().addAll(servicio.listarRacks());
    }
}
