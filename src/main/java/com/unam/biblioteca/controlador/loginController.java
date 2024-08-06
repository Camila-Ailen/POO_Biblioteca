package com.unam.biblioteca.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class loginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnIngresar;

    @FXML
    private void ingresarSistema(ActionEvent e) {
        System.out.println("Ingresando...");
    }

    @FXML
    private void cargarUsuario(KeyEvent e) {
        String c = e.getCharacter();
        if (c.equalsIgnoreCase(" ")) {
            e.consume();
        }
    }

}
