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

public class CopiaModificarController {
    //botones
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAgregarRack;


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




    private Servicio servicio;
    private Copia copia;


    @FXML
    private void initialize() {
        servicio = App.getServicio();


        cargarCombos();
    }

    private void cargarCombos() {
        cargarRacks();
        cargarEstado();
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




    public void setDatosCopia(Copia copia) {
        this.copia = copia;

        lblId.setText(String.valueOf(copia.getId()));
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
    }

    @FXML
    private void click (ActionEvent event) {
        Button boton = (Button) event.getSource();
        Stage stage = (Stage) boton.getScene().getWindow();

        if (!lblIsbn.getText().contains("xxxx") && cmbRack.getValue() != null && cmbEstado.getValue() != null) {
        /*
        var item = tblLibro.getSelectionModel().getSelectedItem();

        if (boton.equals(btnGuardar)) {
            if (!lblIsbn.getText().contains("xxxx") && item != null  && cmbRack.getValue() != null) {


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

         */
                String rackDescripcion = cmbRack.getValue();
                boolean referencia = chkReferencia.isSelected();
                String estadoNombre = cmbEstado.getValue();

                Rack rack = servicio.buscarRackPorDescripcion(rackDescripcion);
                CopiaEstado estado = servicio.buscarEstadoPorNombre(estadoNombre);

                    int id = Integer.parseInt(lblId.getText());
                    servicio.modificarCopia(id, rack, referencia, estado);

                stage.close();

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
