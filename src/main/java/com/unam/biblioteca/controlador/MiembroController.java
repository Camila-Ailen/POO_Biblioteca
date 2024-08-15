package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.Miembro;
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

public class MiembroController {
    //Botones
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnVerHistorial;
    @FXML
    private Button btnRecargar;

    //Tabla
    @FXML
    private TableView<Miembro> tblVista;
    @FXML
    private TableColumn<Miembro, String> colId;
    @FXML
    private TableColumn<Miembro, String> colNombre;
    @FXML
    private TableColumn<Miembro, String> colApellido;
    @FXML
    private TableColumn<Miembro, String> colEmail;
    @FXML
    private TableColumn<Miembro, String> colTelefono;
    @FXML
    private TableColumn<Miembro, String> colRol;
    @FXML
    private TableColumn<Miembro, String> colEstado;


    private Servicio servicio;

    @FXML
    private void initialize() {
        servicio = App.getServicio();

        //Inicializar tabla
        try {
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
            colRol.setCellValueFactory(new PropertyValueFactory<>("rolNombre"));
            colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        tblVista.setRowFactory(tv -> new TableRow<Miembro>(){
            @Override
            protected void updateItem(Miembro item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else if ("Inactivo".equals(item.getEstado())){
                    setStyle("-fx-background-color: red;");
                } else {
                    setStyle("");
                }
            }
        });

        try {
            actualizarTabla();
        } catch (Exception e) {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar los datos", "Ocurrió un error al cargar los datos de los miembros.", e.getMessage());
        }
    }


    @FXML
    private void crear (ActionEvent event) throws IOException {
        App.setRoot("crudMiembro");
        actualizarTabla();
    }

    @FXML
    private void modificar (ActionEvent event) throws IOException {
        Miembro miembroSeleccionado = tblVista.getSelectionModel().getSelectedItem();
        if (miembroSeleccionado != null) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("crudMiembro.fxml"));
            Parent root = loader.load();

            MiembroAbmController controlador = loader.getController();
            controlador.setDatosMiembro(miembroSeleccionado);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modificar Miembro");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            actualizarTabla();
        } else {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al modificar", "No se ha seleccionado un Miembro", "Por favor, seleccione un Miembro de la tabla.");
        }
    }

    @FXML
    private void eliminar (ActionEvent event) {
        Miembro miembroSeleccionado = tblVista.getSelectionModel().getSelectedItem();
        if (miembroSeleccionado != null) {
            Alert respuesta = new Alert(Alert.AlertType.CONFIRMATION);
            respuesta.setTitle("Eliminar Miembro");
            respuesta.setHeaderText("¿Está seguro que desea eliminar el Miembro?");
            respuesta.setContentText("Esta acción marcara como INACTIVO al miembro y podra revertirse posteriormente.");
            if (respuesta.showAndWait().get() == ButtonType.OK) {
                servicio.borrarMiembro(miembroSeleccionado.getId());
                actualizarTabla();
            }
        } else {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al eliminar", "Ocurrió un error al eliminar el Miembro.", "Por favor, seleccione un Miembro de la tabla.");
        }
    }

    private void actualizarTabla() {
        tblVista.getItems().clear();
        tblVista.getItems().addAll(servicio.listarTodosLosMiembros());
    }

    @FXML
    private void recargar(ActionEvent event) {
        actualizarTabla();
    }

    @FXML
    private void historial(ActionEvent event){
        try {
            Miembro miembroSeleccionado = tblVista.getSelectionModel().getSelectedItem();
            if (miembroSeleccionado != null) {
                FXMLLoader loader = new FXMLLoader(App.class.getResource("crudHistorialUsuario.fxml"));
                Parent root = loader.load();

                HistorialUsuarioController controlador = loader.getController();
                controlador.setIdMiembro(miembroSeleccionado);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Historial Usuario");
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } else {
                Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar la ventana", "Ocurrió un error al cargar la ventana de Historial de Usuario.", "Debe seleccionar un Miembro");
            }

        } catch (IOException e) {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar la ventana", "Ocurrió un error al cargar la ventana de Historial de Usuario.", e.getMessage());
        }
    }
}
