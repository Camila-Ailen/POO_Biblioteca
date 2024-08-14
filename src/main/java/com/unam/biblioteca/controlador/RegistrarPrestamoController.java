package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.*;
import com.unam.biblioteca.servicio.Servicio;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class RegistrarPrestamoController {
    @FXML
    private Button btnAgregarUsuario;
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnRecargar;

    //tabla de usuarios
    @FXML
    private TableView<Miembro> tblUsuario;
    @FXML
    private TableColumn<Miembro, String> colIdUsuario;
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
    private TableColumn<Miembro, Integer> colLibro;

    //tabla de copias
    @FXML
    private TableView<Copia> tblCopia;
    @FXML
    private TableColumn<Copia, String> colIdCopia;
    @FXML
    private TableColumn<Copia, String> colIsbn;
    @FXML
    private TableColumn<Copia, String> colTitulo;
    @FXML
    private TableColumn<Copia, String> colAutor;
    @FXML
    private TableColumn<Copia, String> colIdioma;
    @FXML
    private TableColumn<Copia, String> colEditorial;
    @FXML
    private TableColumn<Copia, String> colTipo;

    private Servicio servicio;

    @FXML
    private void initialize() {
        servicio = App.getServicio();

        //Inicializar tabla de usuarios
        try {
            colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("id"));
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
            colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colRol.setCellValueFactory(new PropertyValueFactory<>("rolNombre"));
            colLibro.setCellValueFactory(cellData -> {
                Miembro miembro = cellData.getValue();
                int numCopias = servicio.contarCopiasPorMiembro(miembro);
                return new ReadOnlyObjectWrapper<>(numCopias);
            });
        } catch (Exception e) {
            System.out.println("No se pudieron inicializar las columnas de usuario");
            throw new RuntimeException(e);
        }

        //Inicializar tabla de copias
        try {
            colIdCopia.setCellValueFactory(new PropertyValueFactory<>("id"));
            colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            colAutor.setCellValueFactory(new PropertyValueFactory<>("nombreAutor"));
            colIdioma.setCellValueFactory(new PropertyValueFactory<>("nombreIdioma"));
            colEditorial.setCellValueFactory(new PropertyValueFactory<>("nombreEditorial"));
            colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        } catch (Exception e) {
            System.out.println("No se pudieron inicializar las columnas de copia");
            throw new RuntimeException(e);
        }

        try {
            actualizarTablas();
        } catch (Exception e) {
            System.out.println("No se pudo actualizar la tabla");
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar los datos", "Ocurrió un error al cargar los datos de las Copias o de los Miembros.", e.getMessage());
        }
    }

    private void actualizarTablas(){
        actualizarTablaUsuarios();
        actualizarTablaCopias();
    }

    private void actualizarTablaUsuarios(){
        tblUsuario.getItems().clear();
        tblUsuario.getItems().addAll(servicio.listarMiembros());
    }

    private void actualizarTablaCopias(){
        tblCopia.getItems().clear();
        tblCopia.getItems().addAll(servicio.listarCopiasDisponibles());
    }

    @FXML
    private void agregarUsuario (ActionEvent event) throws IOException {
        App.setRoot("crudMiembro");
        actualizarTablas();
    }

    @FXML
    private void click (ActionEvent event) throws IOException {
        Miembro itemUsuario = tblUsuario.getSelectionModel().getSelectedItem();
        Copia itemCopia = tblCopia.getSelectionModel().getSelectedItem();


            if (itemUsuario != null && itemCopia != null) {
                try {
                    servicio.registrarPrestamo(itemUsuario.getId(), itemCopia.getId());
                    actualizarTablas();
                    Alerta.mostrarAlerta(Alert.AlertType.INFORMATION, "Prestamo registrado", "El prestamo se ha registrado correctamente", "El prestamo se ha registrado correctamente");
                } catch (Exception e) {
                    Alerta.mostrarAlerta(Alert.AlertType.WARNING, "Error al registrar prestamo", "Ocurrió un error al registrar el prestamo", e.getMessage());
                }
            } else {
                Alerta.mostrarAlerta(Alert.AlertType.WARNING, "Error al registrar prestamo", "Datos incompletos", "Debe seleccionar un usuario y una copia");
            }
    }

    @FXML
    private void recargar(ActionEvent event) {
        actualizarTablas();
    }


}
