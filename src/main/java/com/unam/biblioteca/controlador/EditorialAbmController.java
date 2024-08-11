package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.Autor;
import com.unam.biblioteca.modelo.Editorial;
import com.unam.biblioteca.servicio.Servicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditorialAbmController {
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
    private Editorial editorial;


    @FXML
    private void initialize() {
        servicio = App.getServicio();
    }

    public void setDatosEditorial(Editorial editorial) {
        this.editorial = editorial;
        lblId.setText(String.valueOf(editorial.getId()));
        txtNombre.setText(editorial.getNombre());
    }

    @FXML
    private void click (ActionEvent event) {
        Button boton = (Button) event.getSource();
        Stage stage = (Stage) boton.getScene().getWindow();

        if (boton.equals(btnGuardar)) {
            if (!txtNombre.getText().isEmpty()) {
                String nombre = txtNombre.getText();

                if (lblId.getText().equals("Generado automagicamente")) {
                    servicio.guardarEditorial(nombre);
                } else {
                    int id = Integer.parseInt(lblId.getText());
                    servicio.modificarEditorial(id, nombre);
                }
                stage.close();
            } else {
                Alerta.mostrarAlerta(Alert.AlertType.WARNING, "Datos incompletos", "Por favor, complete los campos requeridos.", "El campo nombre no puede estar vacío.");
            }
        } else {
            stage.close();
        }
    }
}
