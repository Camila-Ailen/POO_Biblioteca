package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.*;
import com.unam.biblioteca.servicio.Servicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.List;

public class CopiaAbmController {
    //botones
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAgregarLibro;
    @FXML
    private Button btnAgregarRack;

    //Campos de texto
    @FXML
    private TextField txtIsbn;
    @FXML
    private TextField txtCantidad;

    @FXML
    private ComboBox<String> cmbTipo;
    @FXML
    private ComboBox<String> cmbRack;
    @FXML
    private ComboBox<String> cmbEstado;

    @FXML
    private CheckBox chkReferencia;


    //Etiquetas
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
    private Label lblTematica;
    @FXML
    private Label lblIdioma;
    @FXML
    private Label lblEditorial;

    //Tabla
    @FXML
    private TableColumn<Libro, String> colIsbn;
    @FXML
    private TableColumn<Libro, String> colTitulo;
    @FXML
    private TableColumn<Libro, String> colAutor;
    @FXML
    private TableColumn<Libro, String> colEditorial;
    @FXML
    private TableColumn<Libro, String> colPrecio;
    @FXML
    private TableColumn<Libro, String> colTematica;
    @FXML
    private TableColumn<Libro, String> colIdioma;
    @FXML
    private TableView<Libro> tblLibro;



    private Servicio servicio;
    private Copia copia;


    @FXML
    private void initialize() {
        servicio = App.getServicio();

        txtCantidad.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if (!keyEvent.getCharacter().matches("\\d")) {
                keyEvent.consume();}
        });

        //inicializar tabla
        try {
            colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            colAutor.setCellValueFactory(new PropertyValueFactory<>("nombreAutor"));
            colEditorial.setCellValueFactory(new PropertyValueFactory<>("nombreEditorial"));
            colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            colTematica.setCellValueFactory(new PropertyValueFactory<>("nombreTematica"));
            colIdioma.setCellValueFactory(new PropertyValueFactory<>("nombreIdioma"));

            tblLibro.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());

            try {
                tblLibro.getItems().addAll(servicio.listarTodosLosLibros());
            } catch (Exception e) {
                Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al iniciar tabla de Libros", e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("No se pudieron inicializar las columnas");
            throw new RuntimeException(e);
        }

        cargarCombos();
    }

    private void cargarCombos() {
        cargarTipos();
        cargarRacks();
        cargarEstado();
    }

    private void cargarTipos() {
        cmbTipo.getItems().clear();
        for (CopiaTipo tipo : CopiaTipo.values()) {
            cmbTipo.getItems().add(tipo.name());
        }
    }

    private void cargarRacks() {
        cmbRack.getItems().clear();
        List<Rack> racks = servicio.listarRacks();
        for (Rack rack : racks) {
            cmbRack.getItems().add(rack.getDescripcion());
        }
    }

    private void cargarEstado() {
        cmbEstado.getItems().clear();
        for (CopiaEstado estado : CopiaEstado.values()) {
            cmbEstado.getItems().add(estado.name());
        }
        cmbEstado.setValue(CopiaEstado.DISPONIBLE.name());
    }


    private void cargarDatos() {
        if (tblLibro.getSelectionModel().getSelectedItem() != null) {
            Libro libro = tblLibro.getSelectionModel().getSelectedItem();
            lblIsbn.setText(libro.getIsbn());
            lblTitulo.setText(libro.getTitulo());
            lblAutor.setText(libro.getUnAutor().getNombre());
            lblPrecio.setText(String.valueOf(libro.getPrecio()));
            lblTematica.setText(libro.getUnTematica().getNombre());
            lblIdioma.setText(libro.getUnIdioma().getNombre());
            lblEditorial.setText(libro.getUnEditorial().getNombre());
        }
    }

    public void setDatosCopia(Copia copia) {
        this.copia = copia;

        lblId.setText(String.valueOf(copia.getId()));
        txtIsbn.setText(copia.getUnLibro().getIsbn());
        cmbTipo.setValue(copia.getTipo().name());
        cmbRack.setValue(copia.getUnRack().getDescripcion());
        cmbEstado.setValue(copia.getEstado().name());
        lblIsbn.setText(copia.getUnLibro().getIsbn());
        lblTitulo.setText(copia.getUnLibro().getTitulo());
        lblAutor.setText(copia.getUnLibro().getUnAutor().getNombre());
        lblPrecio.setText(String.valueOf(copia.getUnLibro().getPrecio()));
        lblTematica.setText(copia.getUnLibro().getUnTematica().getNombre());
        lblIdioma.setText(copia.getUnLibro().getUnIdioma().getNombre());
        lblEditorial.setText(copia.getUnLibro().getUnEditorial().getNombre());

        chkReferencia.setSelected(copia.isReferencia());
        txtCantidad.setDisable(true);
    }

    @FXML
    private void click (ActionEvent event) {
        Button boton = (Button) event.getSource();
        Stage stage = (Stage) boton.getScene().getWindow();

        var item = tblLibro.getSelectionModel().getSelectedItem();

        if (boton.equals(btnGuardar)) {
            if (!txtIsbn.getText().contains("xxxx") && item != null && cmbTipo.getValue() != null && cmbRack.getValue() != null) {


                String isbn = lblIsbn.getText();
                int cantidad = Integer.parseInt(txtCantidad.getText());
                String tipoNombre = cmbTipo.getValue();
                String rackDescripcion = cmbRack.getValue();
                boolean referencia = chkReferencia.isSelected();
                String estadoNombre = cmbEstado.getValue();

                System.out.println("pasada la parte 1");
                Libro libro = servicio.buscarLibroPorIsbn(isbn);
                System.out.println("buscamos por isbn");
                CopiaTipo tipo = servicio.buscarTipoPorNombre(tipoNombre);
                System.out.println("buscamos por tipo");
                Rack rack = servicio.buscarRackPorDescripcion(rackDescripcion);
                System.out.println("buscamos por descripcion");
                CopiaEstado estado = servicio.buscarEstadoPorNombre(estadoNombre);
                System.out.println("pasada la parte 2");

                if (lblId.getText().equals("Generado automagicamente")) {
                    System.out.println("entramos al if");
                    estado = CopiaEstado.DISPONIBLE;
                    System.out.println("Llamaremos al servicio");
                    servicio.insertarCopia(libro, cantidad, tipo, rack, referencia, estado);
                    System.out.println("Llamamos al servicio");
                } else {
                    int id = Integer.parseInt(lblId.getText());
                    servicio.modificarCopia(id, tipo, rack, referencia, estado);
                }
                stage.close();
            } else {
                Alerta.mostrarAlerta(Alert.AlertType.WARNING, "Datos incompletos", "Por favor, complete los campos requeridos.",
                        "Los campos son obligatorios, por lo que no pueden estar vacíos.");
            }
        } else {
            stage.close();
        }
    }

    @FXML
    private void agregarLibro(ActionEvent event) {
        try {
            App.setRoot("crudLibro");
        } catch (Exception e) {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar la ventana", "Ocurrió un error al cargar la ventana de Libros.", e.getMessage());
        }
    }

    @FXML
    private void agregarRack(ActionEvent event) {
        try {
            App.setRoot("crudRack");
        } catch (Exception e) {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar la ventana", "Ocurrió un error al cargar la ventana de Racks.", e.getMessage());
        }
    }
}
