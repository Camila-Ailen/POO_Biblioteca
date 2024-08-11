package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.Editorial;
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

public class EditorialController {
    //Botones
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;

    //Tabla
    @FXML
    private TableView<Editorial> tblVista;
    @FXML
    private TableColumn<Editorial, String> colId;
    @FXML
    private TableColumn<Editorial, String> colNombre;


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
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar los datos", "Ocurrió un error al cargar los datos de las editoriales.", e.getMessage());
        }
    }


    @FXML
    private void crear (ActionEvent event) throws IOException {
        App.setRoot("crudEditorial");
        actualizarTabla();
    }

    @FXML
    private void modificar (ActionEvent event) throws IOException {
        Editorial editorialSeleccionado = tblVista.getSelectionModel().getSelectedItem();
        if (editorialSeleccionado != null) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("crudEditorial.fxml"));
            Parent root = loader.load();

            EditorialAbmController controlador = loader.getController();
            controlador.setDatosEditorial(editorialSeleccionado);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modificar Editorial");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            actualizarTabla();
        } else {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al modificar", "No se ha seleccionado una Editorial", "Por favor, seleccione una Editorial de la tabla.");
        }
    }

    @FXML
    private void eliminar (ActionEvent event) {
        Editorial editorialSeleccionado = tblVista.getSelectionModel().getSelectedItem();
        if (editorialSeleccionado != null) {
            Alert respuesta = new Alert(Alert.AlertType.CONFIRMATION);
            respuesta.setTitle("Eliminar Editorial");
            respuesta.setHeaderText("¿Está seguro que desea eliminar la Editorial?");
            respuesta.setContentText("Esta acción no se puede deshacer.");
            if (respuesta.showAndWait().get() == ButtonType.OK) {
                servicio.borrarEditorial(editorialSeleccionado.getId());
                actualizarTabla();
            }
        } else {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al eliminar", "Ocurrió un error al eliminar la Editorial.", "Por favor, seleccione ua Editorial de la tabla.");
        }
    }

    private void actualizarTabla() {
        tblVista.getItems().clear();
        tblVista.getItems().addAll(servicio.listarEditoriales());
    }
}
