package com.unam.biblioteca.controlador;

import com.unam.biblioteca.App;
import com.unam.biblioteca.modelo.Miembro;
import com.unam.biblioteca.modelo.Rol;
import com.unam.biblioteca.servicio.Servicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class MiembroAbmController {
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    //Campos de texto
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtClave;

    @FXML
    private ComboBox<String> cmbRol;

    @FXML
    private CheckBox chkActivo;

    //Etiquetas
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblId;

    private Servicio servicio;
    private Miembro miembro;


    @FXML
    private void initialize() {
        servicio = App.getServicio();
        cargarRoles();
        chkActivo.setSelected(true);
    }

    private void cargarRoles() {
        cmbRol.getItems().clear();
        List<Rol> roles = servicio.listarRoles();
        boolean usuarioEncontrado = false;
        for (Rol rol : roles) {
            cmbRol.getItems().add(rol.getNombre());
            if (rol.getNombre().equals("USUARIO")){
                usuarioEncontrado = true;
            }
        }
        if (usuarioEncontrado){
            cmbRol.setValue("USUARIO");
        } else {
            cmbRol.setValue(roles.get(0).getNombre());
        }

    }

    public void setDatosMiembro(Miembro miembro) {
        this.miembro = miembro;
        lblId.setText(String.valueOf(miembro.getId()));
        txtNombre.setText(miembro.getNombre());
        txtApellido.setText(miembro.getApellido());
        txtClave.setText(miembro.getClave());
        txtEmail.setText(miembro.getEmail());
        txtTelefono.setText(miembro.getTelefono());
        cmbRol.setValue(miembro.getUnRol().getNombre());
        chkActivo.setSelected(miembro.getActivo());
    }

    @FXML
    private void click (ActionEvent event) {
        Button boton = (Button) event.getSource();
        Stage stage = (Stage) boton.getScene().getWindow();

        if (boton.equals(btnGuardar)) {
            if (!txtNombre.getText().isEmpty() && !txtApellido.getText().isEmpty() && !txtEmail.getText().isEmpty() && cmbRol.getValue() != null) {
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                String clave = txtClave.getText();
                String email = txtEmail.getText();
                String telefono = txtTelefono.getText();
                String rolNombre = cmbRol.getValue();
                boolean activo = chkActivo.isSelected();

                Rol rol = servicio.buscarRolPorNombre(rolNombre);

                if (lblId.getText().equals("Generado automagicamente")) {
                    servicio.insertarMiembro(clave, apellido, nombre, telefono, email, activo, rol);
                } else {
                    int id = Integer.parseInt(lblId.getText());
                    servicio.modificarMiembro(id, clave, apellido, nombre, telefono, email, activo, rol);
                }
                stage.close();
            } else {
                Alerta.mostrarAlerta(Alert.AlertType.WARNING, "Datos incompletos", "Por favor, complete los campos requeridos.",
                        "Los campos del Nombre, Apellido, Email y Rol son obligatorios, por lo que no pueden estar vacíos.");
            }
        } else {
            stage.close();
        }
    }
}
