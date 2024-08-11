package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.Autor;
import com.unam.biblioteca.servicio.Servicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AutorAbmController {
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    //Campos de texto
    @FXML
    private TextField txtNombre;

    //Etiquetas
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblId;

    private Servicio servicio;
    private Autor autor;


    @FXML
    private void initialize() {
        servicio = App.getServicio();
    }

    public void setDatosAutor(Autor autor) {
        this.autor = autor;
        lblId.setText(String.valueOf(autor.getId()));
        txtNombre.setText(autor.getNombre());
    }

    @FXML
    private void click (ActionEvent event) {
        Button boton = (Button) event.getSource();
        Stage stage = (Stage) boton.getScene().getWindow();

        if (boton.equals(btnGuardar)) {
            String nombre = txtNombre.getText();

            if (lblId.getText().isEmpty()){
                servicio.guardarAutor(nombre);
            } else {
                int id = Integer.parseInt(lblId.getText());
                servicio.modificarAutor(id, nombre);
            }

        }
        stage.close();
    }


}
