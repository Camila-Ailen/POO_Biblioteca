package com.unam.biblioteca.modelo;

import java.io.Serializable;
import java.util.ArrayList;

import jakarta.persistence.*;

@Entity
public class Editorial implements Serializable {
    //atributos
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;
    @Column(nullable = false, length = 100)
    private String nombre;
    @Column(nullable = false)
    private boolean activo = true;

    //relaciones
    @OneToMany (orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "unEditorial")
    private ArrayList<Libro> listaLibros;

    //constructores, getters y setters

    public Editorial() {
    }

    public Editorial(int id, String nombre, boolean activo, ArrayList<Libro> listaLibros) {
        this.id = id;
        this.nombre = nombre;
        this.activo = activo;
        this.listaLibros = listaLibros;
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
