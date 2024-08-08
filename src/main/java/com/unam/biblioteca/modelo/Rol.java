package com.unam.biblioteca.modelo;


import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Rol implements Serializable {
    //atributos
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;
    @Column(nullable = false, length = 100)
    private String nombre;
    @Column(nullable = false)
    private boolean activo = true;

    //relaciones
    @OneToMany (orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "unRol")
    private List<Miembro> listaMiembros;

    //constructores, getters y setters

    public Rol() {
    }

    public Rol(int id, String nombre, boolean activo, List<Miembro> listaMiembros) {
        this.id = id;
        this.nombre = nombre;
        this.activo = activo;
        this.listaMiembros = listaMiembros;
    }

    public long getId() {
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

    public List<Miembro> getListaMiembros() {
        return listaMiembros;
    }

    public void setListaMiembros(List<Miembro> listaMiembros) {
        this.listaMiembros = listaMiembros;
    }


}
