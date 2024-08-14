package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.Prestamo;
import com.unam.biblioteca.servicio.Servicio;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class RegistrarDevolucionController {
    //botones
    @FXML
    private Button btnRegistrar;

    //Tabla de prestamos
    @FXML
    private TableView<Prestamo> tblVista;
    @FXML
    private TableColumn<Prestamo, String> colId;
    @FXML
    private TableColumn<Prestamo, String> colFechaRetiro;
    @FXML
    private TableColumn<Prestamo, String> colIsbn;
    @FXML
    private TableColumn<Prestamo, String> colTitulo;
    @FXML
    private TableColumn<Prestamo, String> colNombre;
    @FXML
    private TableColumn<Prestamo, String> colApellido;
    @FXML
    private TableColumn<Prestamo, Integer> colCantidadDias;

    //Etiquetas
    @FXML
    private Label lblIdLibro;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblAutor;
    @FXML
    private Label lblTematica;
    @FXML
    private Label lblEditorial;
    @FXML
    private Label lblIdioma;
    @FXML
    private Label lblTipo;
    @FXML
    private Label lblPrecio;
    @FXML
    private Label lblIsbn;

    @FXML
    private Label lblIdMiembro;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblApellido;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblTelefono;
    @FXML
    private Label lblRol;

    private Servicio servicio;


    @FXML
    private void initialize() {
        servicio = App.getServicio();

        //Inicializar tabla de usuarios
        try {
            colId.setCellValueFactory(new PropertyValueFactory<>("idCopia"));
            colFechaRetiro.setCellValueFactory(cellData -> {
                Prestamo prestamo = cellData.getValue();
                return new ReadOnlyObjectWrapper<>(prestamo.getFormatoFecha(prestamo.getFechaRetiro()));
            });
            colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
            colCantidadDias.setCellValueFactory(cellData -> {
                Prestamo prestamo = cellData.getValue();
                int numDias = servicio.contarDiasDePrestamo(prestamo);
                return new ReadOnlyObjectWrapper<>(numDias);
            });
        } catch (Exception e) {
            System.out.println("No se pudieron inicializar las columnas de usuario");
            throw new RuntimeException(e);
        }

        try {
            actualizarTabla();
        } catch (Exception e) {
            System.out.println("No se pudo actualizar la tabla");
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar los datos", "Ocurrió un error al cargar los datos de las Copias o de los Miembros.", e.getMessage());
        }
    }

    private void actualizarTabla(){
        tblVista.getItems().clear();
        tblVista.getItems().addAll(servicio.listarPrestamosActivos());
    }

    @FXML
    private void registrar (ActionEvent event) {
        Prestamo prestamoSeleccionado = tblVista.getSelectionModel().getSelectedItem();
        if (prestamoSeleccionado != null) {
            try {
                servicio.registrarDevolucion(prestamoSeleccionado.getId());
                servicio.devolverPrestamo(prestamoSeleccionado.getId());
                actualizarTabla();
                Alerta.mostrarAlerta(Alert.AlertType.INFORMATION, "Devolución registrada", "La devolución se ha registrado correctamente", "La devolución se ha registrado correctamente");
            } catch (Exception e) {
                Alerta.mostrarAlerta(Alert.AlertType.WARNING, "Error al registrar devolución", "Ocurrió un error al registrar la devolución", e.getMessage());
            }
        } else {
            Alerta.mostrarAlerta(Alert.AlertType.WARNING, "Error al registrar devolución", "No se ha seleccionado un prestamo", "No se ha seleccionado un prestamo");
        }
    }














}
