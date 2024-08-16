package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.*;
import com.unam.biblioteca.servicio.Servicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.List;

public class LibroAbmController {
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    //Campos de texto
    @FXML
    private TextField txtIsbn;
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtPrecio;

    @FXML
    private ComboBox<String> cmbAutor;
    @FXML
    private ComboBox<String> cmbTematica;
    @FXML
    private ComboBox<String> cmbIdioma;
    @FXML
    private ComboBox<String> cmbEditorial;


    //Etiquetas
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblId;

    private Servicio servicio;
    private Libro libro;


    @FXML
    private void initialize() {
        servicio = App.getServicio();

        txtIsbn.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if (!keyEvent.getCharacter().matches("\\d")) {
                keyEvent.consume();}
        });

        txtPrecio.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if (!keyEvent.getCharacter().matches("\\d")) {
                keyEvent.consume();}
        });

        cargarCombos();
    }

    private void cargarCombos() {
        cargarAutores();
        cargarTematicas();
        cargarIdiomas();
        cargarEditoriales();
    }

    private void cargarAutores() {
        cmbAutor.getItems().clear();
        List<Autor> autores = servicio.listarAutores();
        for (Autor autor : autores) {
            cmbAutor.getItems().add(autor.getNombre());
        }
    }

    private void cargarTematicas() {
        cmbTematica.getItems().clear();
        List<Tematica> tematicas = servicio.listarTematicas();
        for (Tematica tematica : tematicas) {
            cmbTematica.getItems().add(tematica.getNombre());
        }
    }

    private void cargarIdiomas() {
        cmbIdioma.getItems().clear();
        List<Idioma> tematicas = servicio.listarIdiomas();
        for (Idioma tematica : tematicas) {
            cmbIdioma.getItems().add(tematica.getNombre());
        }
    }

    private void cargarEditoriales() {
        cmbEditorial.getItems().clear();
        List<Editorial> editoriales = servicio.listarEditoriales();
        for (Editorial editorial : editoriales) {
            cmbEditorial.getItems().add(editorial.getNombre());
        }
    }



    public void setDatosLibro(Libro libro) {
        this.libro = libro;

        lblId.setText(String.valueOf(libro.getId()));
        txtIsbn.setText(libro.getIsbn());
        txtTitulo.setText(libro.getTitulo());
        txtPrecio.setText(String.valueOf(libro.getPrecio()));
        cmbAutor.setValue(libro.getUnAutor().getNombre());
        cmbTematica.setValue(libro.getUnTematica().getNombre());
        cmbIdioma.setValue(libro.getUnIdioma().getNombre());
        cmbEditorial.setValue(libro.getUnEditorial().getNombre());
    }

    @FXML
    private void click (ActionEvent event) {
        Button boton = (Button) event.getSource();
        Stage stage = (Stage) boton.getScene().getWindow();

        if (boton.equals(btnGuardar)) {
            if (!txtIsbn.getText().isEmpty() && !txtTitulo.getText().isEmpty() && !txtPrecio.getText().isEmpty()) {
                String isbn = txtIsbn.getText();
                String titulo = txtTitulo.getText();
                double precio = Double.parseDouble(txtPrecio.getText());
                String autorNombre = cmbAutor.getValue();
                String tematicaNombre = cmbTematica.getValue();
                String idiomaNombre = cmbIdioma.getValue();
                String editorialNombre = cmbEditorial.getValue();

                Autor autor = servicio.buscarAutorPorNombre(autorNombre);
                Tematica tematica = servicio.buscarTematicaPorNombre(tematicaNombre);
                Idioma idioma = servicio.buscarIdiomaPorNombre(idiomaNombre);
                Editorial editorial = servicio.buscarEditorialPorNombre(editorialNombre);

                //Rol rol = servicio.buscarRolPorNombre(rolNombre);

                if (lblId.getText().equals("Generado automagicamente")) {
                    servicio.insertarLibro(titulo, isbn, precio, tematica, autor, idioma, editorial);
                } else {
                    int id = Integer.parseInt(lblId.getText());
                    servicio.modificarLibro(id, titulo, isbn, precio, tematica, autor, idioma, editorial);
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
}
