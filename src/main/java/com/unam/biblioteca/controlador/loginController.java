package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.Miembro;
import com.unam.biblioteca.modelo.UsrLogueado;
import com.unam.biblioteca.servicio.Servicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.IOException;


public class loginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtPassword;


    @FXML
    private Button btnIngresar;


    private Miembro miembro;
    private Servicio servicio;



    @FXML
    private void initialize() {
        servicio = App.getServicio();
    }

    @FXML
    private void ingresarSistema(ActionEvent event) throws IOException {
        //Object evt = event.getSource();

        if (!txtUsuario.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
            try {
                int usuarioId = Integer.parseInt(txtUsuario.getText());
                miembro = servicio.buscarMiembro(usuarioId);
            } catch (NumberFormatException ex) {
                Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error", "El ID de usuario debe ser un número entero", ex.getMessage());
            }
            if (miembro != null && miembro.getActivo()) {
                if (miembro.getClave().equals(txtPassword.getText())) {
                    UsrLogueado.getInstancia().setVariableGlobal(miembro);
                    if (miembro.getUnRol().getNombre().equalsIgnoreCase("bibliotecario")) {
                        App.setRoot("principalBibliotecario");
                    } else if (miembro.getUnRol().getNombre().equalsIgnoreCase("usuario")) {
                        App.setRoot("principalUsuario");
                    } else {
                        Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Rol no válido", "El rol del usuario no es válido");
                    }
                } else {
                    Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Contraseña incorrecta", "La contraseña ingresada no coincide con la registrada");
                }
            } else {
                Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Usuario inexistente o inactivo", "No se ha encontrado activo un usuario con el ID ingresado, o se encuentra INACTIVO");
            }
        } else {
            Alerta.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Campos incompletos", "Llene los campos vacios");
        }
    }




    @FXML
    private void cargarUsuario(KeyEvent e) {
        String c = e.getCharacter();
        if (!c.matches("\\d")) {
            e.consume();
            String usuario = txtUsuario.getText();
            if (usuario.length() > 0) {
                txtUsuario.setText(usuario.substring(0, usuario.length() - 1));
            }
            Alerta.mostrarAlerta(Alert.AlertType.INFORMATION, "Información", "Solo se permiten números", "Su usuario es el numero de usuario que se le asigno");
        }
    }
}