package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;


public class loginController {

    /*
    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtPassword;
    */

    @FXML
    private Button btnIngresar;

    /*
    private Miembro miembro;
    private Repositorio repositorio;*/

    /*
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliotecaPU");
        this.repositorio = new Repositorio(emf);
    }*/

    /*
    @FXML
    private void ingresarSistema(ActionEvent e) {
        Object evt = e.getSource();

        if (!txtUsuario.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
            try {
                int usuarioId = Integer.parseInt(txtUsuario.getText());
                System.out.println("Usuario ID: " + usuarioId);
                miembro = repositorio.buscar(Miembro.class, usuarioId);
            } catch (NumberFormatException ex) {
                System.out.println("El ID de usuario debe ser un número entero");
            }
            if (miembro != null) {
                if (miembro.getClave().equals(txtPassword.getText())) {
                    JOptionPane.showMessageDialog(null, "Bienvenido " + miembro.getNombre());
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No hay usuarios registrados");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Llene los campos");
        }
    }*/

    @FXML
    private void ingresarSistema (ActionEvent event) throws IOException {
        System.out.println("Pasando parte 1");
        App.setRoot("principalBibliotecario");
        System.out.println("Finalizando parte 1");
    }

    /*
    @FXML
    private void cargarUsuario(KeyEvent e) {
        String c = e.getCharacter();
        if (c.equalsIgnoreCase(" ")) {
            e.consume();
        }
    }*/
}