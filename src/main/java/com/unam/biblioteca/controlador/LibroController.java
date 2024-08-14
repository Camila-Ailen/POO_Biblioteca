package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.Libro;
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

public class LibroController {
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnRecargar;

    //Tabla
    @FXML
    private TableView<Libro> tblVista;
    @FXML
    private TableColumn<Libro, String> colId;
    @FXML
    private TableColumn<Libro, String> colIsbn;
    @FXML
    private TableColumn<Libro, String> colTitulo;
    @FXML
    private TableColumn<Libro, String> colPrecio;
    @FXML
    private TableColumn<Libro, String> colAutor;
    @FXML
    private TableColumn<Libro, String> colTematica;
    @FXML
    private TableColumn<Libro, String> colIdioma;
    @FXML
    private TableColumn<Libro, String> colEditorial;

    private Servicio servicio;

    @FXML
    private void initialize() {
        servicio = App.getServicio();

        //Inicializar tabla
        try {
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            colAutor.setCellValueFactory(new PropertyValueFactory<>("nombreAutor"));
            colTematica.setCellValueFactory(new PropertyValueFactory<>("nombreTematica"));
            colIdioma.setCellValueFactory(new PropertyValueFactory<>("nombreIdioma"));
            colEditorial.setCellValueFactory(new PropertyValueFactory<>("nombreEditorial"));
        } catch (Exception e) {
            System.out.println("No se pudieron inicializar las columnas");
            throw new RuntimeException(e);
        }

        try {
            actualizarTabla();
        } catch (Exception e) {
            System.out.println("No se pudo actualizar la tabla");
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar los datos", "Ocurrió un error al cargar los datos de los Libros.", e.getMessage());
        }
    }


    @FXML
    private void crear (ActionEvent event) throws IOException {
        App.setRoot("crudLibro");
        actualizarTabla();
    }

    @FXML
    private void modificar (ActionEvent event) throws IOException {
        Libro libroSeleccionado = tblVista.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("crudLibro.fxml"));
            Parent root = loader.load();

            LibroAbmController controlador = loader.getController();
            controlador.setDatosLibro(libroSeleccionado);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modificar Libro");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            actualizarTabla();
        } else {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al modificar", "No se ha seleccionado un Libro", "Por favor, seleccione un Libro de la tabla.");
        }
    }

    @FXML
    private void eliminar (ActionEvent event) {
        Libro libroSeleccionado = tblVista.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            Alert respuesta = new Alert(Alert.AlertType.CONFIRMATION);
            respuesta.setTitle("Eliminar Libro");
            respuesta.setHeaderText("¿Está seguro que desea eliminar el Libro?");
            respuesta.setContentText("Esta acción no podra revertirse posteriormente.");
            if (respuesta.showAndWait().get() == ButtonType.OK) {
                servicio.borrarLibro(libroSeleccionado.getId());
                actualizarTabla();
            }
        } else {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al eliminar", "Ocurrió un error al eliminar el Libro.", "Por favor, seleccione un Libro de la tabla.");
        }
    }

    private void actualizarTabla() {
        tblVista.getItems().clear();
        tblVista.getItems().addAll(servicio.listarTodosLosLibros());
    }

    @FXML
    private void recargar(ActionEvent event) {
        actualizarTabla();
    }

}
