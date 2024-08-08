package com.unam.biblioteca.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Rack implements Serializable {
    //atributos
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;
    private String descripcion;
    private boolean activo = true;

    //relaciones
    @OneToMany (mappedBy = "unRack")
    private ArrayList<Copia> listaCopias;

    //controladores, getters y setters

    public Rack() {
    }

    public Rack(int id, String descripcion, boolean activo, ArrayList<Copia> listaCopias) {
        this.id = id;
        this.descripcion = descripcion;
        this.activo = activo;
        this.listaCopias = listaCopias;
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
