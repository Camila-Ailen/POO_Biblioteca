package com.unam.biblioteca.controlador;

import javafx.scene.control.Alert;

public class Alerta {
    public static void mostrarAlerta(Alert.AlertType tipo, String titulo, String cabecera, String mensaje) {
        // mostramos una alerta
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(cabecera);
        a.setContentText(mensaje);
        a.show();
    }
}
