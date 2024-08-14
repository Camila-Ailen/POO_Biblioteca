package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.*;
import com.unam.biblioteca.servicio.Servicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Map;


public class PrincipalController {
    @FXML
    private Button btnRecargar;

    @FXML
    private ComboBox cmbTitulo;
    @FXML
    private ComboBox cmbAutor;
    @FXML
    private ComboBox cmbTematica;

    //Tabla
    @FXML
    private TableView<Libro> tblLibros;
    @FXML
    private TableColumn<Libro, String> colIsbn;
    @FXML
    private TableColumn<Libro, String> colTitulo;
    @FXML
    private TableColumn<Libro, String> colAutor;
    @FXML
    private TableColumn<Libro, String> colTematica;
    @FXML
    private TableColumn<Libro, String> colIdioma;
    @FXML
    private TableColumn<Libro, String> colEditorial;

    //Etiquetas
    @FXML
    private Label lblTapaDura;
    @FXML
    private Label lblRustica;
    @FXML
    private Label lblAudiolibro;
    @FXML
    private Label lblElectronico;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblAutor;
    @FXML
    private Label lblPrecio;

    private Servicio servicio;

    @FXML
    private void initialize() {
        servicio = App.getServicio();

        //Inicializar tabla
        try {
            colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            colAutor.setCellValueFactory(new PropertyValueFactory<>("nombreAutor"));
            colTematica.setCellValueFactory(new PropertyValueFactory<>("nombreTematica"));
            colIdioma.setCellValueFactory(new PropertyValueFactory<>("nombreIdioma"));
            colEditorial.setCellValueFactory(new PropertyValueFactory<>("nombreEditorial"));

            tblLibros.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            actualizarTabla();
        } catch (Exception e) {
            System.out.println("No se pudo actualizar la tabla");
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar los datos", "Ocurrió un error al cargar los datos de los Libros en la pantalla principal.", e.getMessage());
        }
        cargarCombos();
    }

    private void cargarCombos() {
        cargarTitulo();
        cargarAutor();
        cargarTematica();
    }

    private void cargarTitulo() {
        cmbTitulo.getItems().clear();
        List<Libro> libros = servicio.listarTodosLosLibros();
        for (Libro libro : libros) {
            cmbTitulo.getItems().add(libro.getTitulo());
        }
    }

    private void cargarAutor() {
        cmbAutor.getItems().clear();
        List<Autor> autores = servicio.listarAutores();
        for (Autor autor : autores) {
            cmbAutor.getItems().add(autor.getNombre());
        }
    }

    private void cargarTematica() {
        cmbTematica.getItems().clear();
        List<Tematica> tematicas = servicio.listarTematicas();
        for (Tematica tematica : tematicas) {
            cmbTematica.getItems().add(tematica.getNombre());
        }
    }


    @FXML
    private void recargar (ActionEvent event) {
        actualizarTabla();
    }

    private void actualizarTabla() {
        tblLibros.getItems().clear();
        tblLibros.getItems().addAll(servicio.listarTodosLosLibros());
        cargarCombos();
    }


    private void cargarDatos() {
        var unLibro = tblLibros.getSelectionModel().getSelectedItem();

        if (unLibro != null) {
            List<Copia> copias = servicio.listarCopiasPorLibro(unLibro);
            Map<CopiaTipo, Integer> conteo = Copia.contarCopiasPorTipo(copias);

            lblTitulo.setText(unLibro.getTitulo());
            lblAutor.setText(unLibro.getNombreAutor());
            lblPrecio.setText(unLibro.getPrecioLibro());
            lblTapaDura.setText(String.valueOf(conteo.getOrDefault(CopiaTipo.TAPA_DURA, 0)));
            lblRustica.setText(String.valueOf(conteo.getOrDefault(CopiaTipo.LIBRO_EN_RUSTICA, 0)));
            lblAudiolibro.setText(String.valueOf(conteo.getOrDefault(CopiaTipo.AUDIOLIBRO, 0)));
            lblElectronico.setText(String.valueOf(conteo.getOrDefault(CopiaTipo.LIBRO_ELECTRONICO, 0)));
        } else {
            lblTitulo.setText("");
            lblAutor.setText("");
            lblPrecio.setText("");
            lblTapaDura.setText("CANTIDAD DISPONIBLE");
            lblRustica.setText("CANTIDAD DISPONIBLE");
            lblAudiolibro.setText("CANTIDAD DISPONIBLE");
            lblElectronico.setText("CANTIDAD DISPONIBLE");
        }


    }
}
