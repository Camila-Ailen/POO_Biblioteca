package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.Idioma;
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

public class IdiomaController {
    //Botones
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;

    //Tabla
    @FXML
    private TableView<Idioma> tblVista;
    @FXML
    private TableColumn<Idioma, String> colId;
    @FXML
    private TableColumn<Idioma, String> colNombre;


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
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar los datos", "Ocurrió un error al cargar los datos de los Idiomas.", e.getMessage());
        }
    }


    @FXML
    private void crear (ActionEvent event) throws IOException {
        App.setRoot("crudIdioma");
        actualizarTabla();
    }

    @FXML
    private void modificar (ActionEvent event) throws IOException {
        Idioma idiomaSeleccionado = tblVista.getSelectionModel().getSelectedItem();
        if (idiomaSeleccionado != null) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("crudIdioma.fxml"));
            Parent root = loader.load();

            IdiomaAbmController controlador = loader.getController();
            controlador.setDatosIdioma(idiomaSeleccionado);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modificar Idioma");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            actualizarTabla();
        } else {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al modificar", "No se ha seleccionado un Idioma", "Por favor, seleccione un Idioma de la tabla.");
        }
    }

    @FXML
    private void eliminar (ActionEvent event) {
        Idioma idiomaSeleccionado = tblVista.getSelectionModel().getSelectedItem();
        if (idiomaSeleccionado != null) {
            Alert respuesta = new Alert(Alert.AlertType.CONFIRMATION);
            respuesta.setTitle("Eliminar Idioma");
            respuesta.setHeaderText("¿Está seguro que desea eliminar el Idioma?");
            respuesta.setContentText("Esta acción no se puede deshacer.");
            if (respuesta.showAndWait().get() == ButtonType.OK) {
                servicio.borrarIdioma(idiomaSeleccionado.getId());
                actualizarTabla();
            }
        } else {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al eliminar", "Ocurrió un error al eliminar el Idioma.", "Por favor, seleccione un Idioma de la tabla.");
        }
    }

    private void actualizarTabla() {
        tblVista.getItems().clear();
        tblVista.getItems().addAll(servicio.listarIdiomas());
    }
}
