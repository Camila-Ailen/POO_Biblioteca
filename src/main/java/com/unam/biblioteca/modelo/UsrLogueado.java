package com.unam.biblioteca.modelo;

public class UsrLogueado {
    private static UsrLogueado instancia;
    private Object variableGlobal;

    private UsrLogueado() {
        // Constructor privado para prevenir instanciación
    }

    public static synchronized UsrLogueado getInstancia() {
        if (instancia == null) {
            instancia = new UsrLogueado();
        }
        return instancia;
    }

    public Object getVariableGlobal() {
        return variableGlobal;
    }

    public void setVariableGlobal(Object variableGlobal) {
        this.variableGlobal = variableGlobal;
    }
}