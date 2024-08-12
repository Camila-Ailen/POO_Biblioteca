package com.unam.biblioteca.modelo;

import java.io.Serializable;
import java.util.ArrayList;

import jakarta.persistence.*;

@Entity
public class Idioma implements Serializable {
    //atributos
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;
    @Column(nullable = false, length = 100)
    private String nombre;
    @Column(nullable = false)
    private boolean activo = true;

    //relaciones
    @OneToMany (orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "unIdioma")
    private ArrayList<Libro> listaLibros;

    //constructores, getters y setters

    public Idioma() {
    }

    public Idioma(String nombre) {
        nombre = nombre.trim().toUpperCase();
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (nombre.length() > 100) {
            throw new IllegalArgumentException("El nombre no puede tener más de 100 caracteres");
        }
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        nombre = nombre.trim().toUpperCase();
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (nombre.length() > 100) {
            throw new IllegalArgumentException("El nombre no puede tener más de 100 caracteres");
        }
        this.nombre = nombre;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public ArrayList<Libro> getListaLibros() {
        return listaLibros;
    }

    public void setListaLibros(ArrayList<Libro> listaLibros) {
        this.listaLibros = listaLibros;
    }



}
