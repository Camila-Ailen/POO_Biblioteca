package com.unam.biblioteca.modelo;

import java.io.Serializable;
import java.util.ArrayList;

import jakarta.persistence.*;

@Entity
public class Rack implements Serializable {
    //atributos
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;
    @Column(nullable = false, length = 100)
    private String descripcion;
    @Column(nullable = false)
    private boolean activo = true;

    //relaciones
    @OneToMany (orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "unRack")
    private ArrayList<Copia> listaCopias;

    //constructores, getters y setters

    public Rack() {
    }

    public Rack(String descripcion) {
        // validaciones
        descripcion = descripcion.trim().toUpperCase();
        if (descripcion.isEmpty()) {
            throw new IllegalArgumentException("La descripcion no puede estar vacío");
        }
        if (descripcion.length() > 100) {
            throw new IllegalArgumentException("La descripcion no puede tener más de 100 caracteres");
        }
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        descripcion = descripcion.trim().toUpperCase();
        if (descripcion.isEmpty()) {
            throw new IllegalArgumentException("La descripcion no puede estar vacío");
        }
        if (descripcion.length() > 100) {
            throw new IllegalArgumentException("La descripcion no puede tener más de 100 caracteres");
        }
        this.descripcion = descripcion;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public ArrayList<Copia> getListaCopias() {
        return listaCopias;
    }

    public void setListaCopias(ArrayList<Copia> listaCopias) {
        this.listaCopias = listaCopias;
    }


}
