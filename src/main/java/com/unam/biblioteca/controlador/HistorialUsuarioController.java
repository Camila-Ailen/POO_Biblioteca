package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.Copia;
import com.unam.biblioteca.modelo.Libro;
import com.unam.biblioteca.modelo.Miembro;
import com.unam.biblioteca.modelo.Prestamo;
import com.unam.biblioteca.servicio.Servicio;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class HistorialUsuarioController {
    @FXML
    private Label lblId;

    @FXML
    private Button btnVolver;

    //tabla
    @FXML
    private TableView<Prestamo> tblVista;
    @FXML
    private TableColumn<Prestamo, Integer> colIdCopia;
    @FXML
    private TableColumn<Prestamo, String> colIsbn;
    @FXML
    private TableColumn<Prestamo, String> colTitulo;
    @FXML
    private TableColumn<Prestamo, String> colAutor;
    @FXML
    private TableColumn<Prestamo, String> colTipo;
    @FXML
    private TableColumn<Prestamo, String> colFechaRetiro;
    @FXML
    private TableColumn<Prestamo, String> colFechaDevolucion;
    @FXML
    private TableColumn<Prestamo, Double> colMulta;

    private Servicio servicio;
    private Miembro miembro;
    private Copia copia;

    @FXML
    private void initialize() {
        servicio = App.getServicio();

        //Inicializar tabla de usuarios
        colIdCopia.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUnCopia().getId()));
        colIsbn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUnCopia().getIsbn()));
        colTitulo.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUnCopia().getTitulo()));
        colAutor.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUnCopia().getUnLibro().getUnAutor().getNombre()));
        colTipo.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUnCopia().getTipo().toString()));
        colFechaRetiro.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(formatearFecha(cellData.getValue().getFechaRetiro())));
        colFechaDevolucion.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFechaDevolucion() != null ? formatearFecha(cellData.getValue().getFechaDevolucion()) : ""));
        colMulta.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getMulta()));

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
        if (miembro != null) {
            System.out.println("Updating table for member ID: " + miembro.getId());
            tblVista.getItems().clear();
            tblVista.getItems().addAll(servicio.listarPrestamosPorMiembro(miembro.getId()));
        }else {
            System.out.println("no carga la tabla");
        }
    }

    public void setIdMiembro(Miembro miembro) {
        this.miembro = miembro;
        lblId.setText(String.valueOf(miembro.getId()) + ", " + String.valueOf(miembro.getNombre()) + " " + String.valueOf(miembro.getApellido()));
        actualizarTabla();
    }

    @FXML
    private void volver(ActionEvent event) {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        stage.close();
    }

}
