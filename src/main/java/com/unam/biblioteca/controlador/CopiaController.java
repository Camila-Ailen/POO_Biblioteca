package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.Copia;
import com.unam.biblioteca.modelo.CopiaEstado;
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

public class CopiaController {
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
    private TableView<Copia> tblVista;
    @FXML
    private TableColumn<Copia, String> colId;
    @FXML
    private TableColumn<Copia, String> colIsbn;
    @FXML
    private TableColumn<Copia, String> colTitulo;
    @FXML
    private TableColumn<Copia, String> colAutor;
    @FXML
    private TableColumn<Copia, String> colRack;
    @FXML
    private TableColumn<Copia, String> colTipo;
    @FXML
    private TableColumn<Copia, String> colReferencia;
    @FXML
    private TableColumn<Copia, CopiaEstado> colEstado;

    //etiquetas
    @FXML
    private Label lblId;
    @FXML
    private Label lblIsbn;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblAutor;
    @FXML
    private Label lblPrecio;
    @FXML
    private Label lblRack;
    @FXML
    private Label lblTipo;
    @FXML
    private Label lblReferencia;
    @FXML
    private Label lblEstado;
    @FXML
    private Label lblEditorial;
    @FXML
    private Label lblTematica;
    @FXML
    private Label lblIdioma;


    private Servicio servicio;

    @FXML
    private void initialize() {
        servicio = App.getServicio();

        //Inicializar tabla
        try {
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            colAutor.setCellValueFactory(new PropertyValueFactory<>("nombreAutor"));
            colRack.setCellValueFactory(new PropertyValueFactory<>("nombreRack"));
            colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            colReferencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
            colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

            tblVista.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());

            colEstado.setCellFactory(column -> new TableCell<Copia, CopiaEstado>(){
                @Override
                protected void updateItem(CopiaEstado item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item.name());
                        TableRow<Copia> currentRow = getTableRow();
                        switch (item) {
                            case PRESTADA:
                                currentRow.setStyle("-fx-background-color: lightblue");
                                break;
                            case DISPONIBLE:
                                currentRow.setStyle("-fx-background-color: lightgreen");
                                break;
                            case PERDIDA:
                                currentRow.setStyle("-fx-background-color: lightcoral");
                                break;
                            default:
                                setStyle("");
                                break;
                        }
                    }
                }
            });
            actualizarTabla();
        } catch (Exception e) {
            System.out.println("No se pudieron inicializar las columnas");
            throw new RuntimeException(e);
        }

    }


    @FXML
    private void crear (ActionEvent event) throws IOException {
        App.setRoot("crudCopia");
        actualizarTabla();
    }

    @FXML
    private void modificar (ActionEvent event) throws IOException {
        Copia copiaSeleccionado = tblVista.getSelectionModel().getSelectedItem();
        if (copiaSeleccionado != null) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("crudCopia.fxml"));
            Parent root = loader.load();

            CopiaAbmController controlador = loader.getController();
            controlador.setDatosCopia(copiaSeleccionado);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modificar Copia");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            actualizarTabla();
        } else {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al modificar", "No se ha seleccionado una Copia", "Por favor, seleccione una Copia de la tabla.");
        }
    }

    @FXML
    private void eliminar (ActionEvent event) {
        Copia copiaSeleccionado = tblVista.getSelectionModel().getSelectedItem();
        if (copiaSeleccionado != null) {
            Alert respuesta = new Alert(Alert.AlertType.CONFIRMATION);
            respuesta.setTitle("Eliminar Copia");
            respuesta.setHeaderText("¿Está seguro que desea eliminar el Copia?");
            respuesta.setContentText("Esta acción no podra revertirse posteriormente.");
            if (respuesta.showAndWait().get() == ButtonType.OK) {
                servicio.borrarCopia(copiaSeleccionado.getId());
                actualizarTabla();
            }
        } else {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al eliminar", "Ocurrió un error al eliminar el Copia.", "Por favor, seleccione un Copia de la tabla.");
        }
    }

    private void actualizarTabla() {
        tblVista.getItems().clear();
        tblVista.getItems().addAll(servicio.listarTodasLasCopias());
    }

    @FXML
    private void recargar(ActionEvent event) {
        actualizarTabla();
    }

    @FXML
    private void historial(ActionEvent event){
        try {
            Copia copiaSeleccionado = tblVista.getSelectionModel().getSelectedItem();
            if (copiaSeleccionado != null) {
                FXMLLoader loader = new FXMLLoader(App.class.getResource("crudHistorialCopia.fxml"));
                Parent root = loader.load();

                HistorialCopiaController controlador = loader.getController();
                controlador.setIdCopia(copiaSeleccionado);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Historial Usuario");
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } else {
                Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar la ventana", "Ocurrió un error al cargar la ventana de Historial de Copia.", "Debe seleccionar una Copia");
            }

        } catch (IOException e) {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar la ventana", "Ocurrió un error al cargar la ventana de Historial de Copia.", e.getMessage());
        }
    }

    private void cargarDatos() {
        if (tblVista.getSelectionModel().getSelectedItem() != null) {
            Copia copia = tblVista.getSelectionModel().getSelectedItem();
            lblId.setText(String.valueOf(copia.getId()));
            lblIsbn.setText(copia.getUnLibro().getIsbn());
            lblTitulo.setText(copia.getUnLibro().getTitulo());
            lblAutor.setText(copia.getUnLibro().getUnAutor().getNombre());
            lblPrecio.setText(String.valueOf(copia.getUnLibro().getPrecio()));
            lblTipo.setText(copia.getTipo().name());
            lblRack.setText(copia.getUnRack().getDescripcion());
            lblReferencia.setText(copia.getReferencia());
            lblEstado.setText(copia.getEstado().name());
            lblEditorial.setText(copia.getUnLibro().getUnEditorial().getNombre());
            lblTematica.setText(copia.getUnLibro().getUnTematica().getNombre());
            lblIdioma.setText(copia.getUnLibro().getUnIdioma().getNombre());

        }
    }
}
