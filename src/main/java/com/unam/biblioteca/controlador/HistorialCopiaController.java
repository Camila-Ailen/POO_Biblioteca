package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.Copia;
import com.unam.biblioteca.modelo.Miembro;
import com.unam.biblioteca.modelo.Prestamo;
import com.unam.biblioteca.servicio.Servicio;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistorialCopiaController {
    @FXML
    private Label lblId;

    @FXML
    private Button btnVolver;

    //tabla
    @FXML
    private TableView<Prestamo> tblVista;
    @FXML
    private TableColumn<Prestamo, Integer> colId;
    @FXML
    private TableColumn<Prestamo, String> colApellido;
    @FXML
    private TableColumn<Prestamo, String> colNombre;
    @FXML
    private TableColumn<Prestamo, String> colTipo;
    @FXML
    private TableColumn<Prestamo, String> colFechaRetiro;
    @FXML
    private TableColumn<Prestamo, String> colFechaDevolucion;

    private Servicio servicio;
    private Copia copia;


    @FXML
    private void initialize() {
        servicio = App.getServicio();

        //Inicializar tabla de usuarios
        colId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUnMiembro().getId()));
        colApellido.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUnMiembro().getApellido()));
        colNombre.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUnMiembro().getNombre()));
        colTipo.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUnCopia().getTipo().toString()));
        colFechaRetiro.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(formatearFecha(cellData.getValue().getFechaRetiro())));
        colFechaDevolucion.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFechaDevolucion() != null ? formatearFecha(cellData.getValue().getFechaDevolucion()) : ""));

        colFechaRetiro.setCellValueFactory(cellData -> {
            Prestamo prestamo = cellData.getValue();
            return new ReadOnlyObjectWrapper<>(prestamo.getFormatoFecha(prestamo.getFechaRetiro()));
        });
    }

    private String formatearFecha(Date fecha) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(fecha);
    }

    private void actualizarTabla(){
        if (copia != null) {
            tblVista.getItems().clear();
            tblVista.getItems().addAll(servicio.listarPrestamosPorCopia(copia.getId()));
        }else {
            System.out.println("no carga la tabla");
        }
    }

    public void setIdCopia(Copia copia) {
        this.copia = copia;
        lblId.setText(String.valueOf(copia.getId()) + ", " + String.valueOf(copia.getUnLibro().getTitulo()) + ", " + String.valueOf(copia.getUnLibro().getUnAutor().getNombre()));
        actualizarTabla();
    }

    @FXML
    private void volver(ActionEvent event) {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        stage.close();
    }
}
