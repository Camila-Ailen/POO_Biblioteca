package com.unam.biblioteca.modelo;


import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Rol implements Serializable {
    //atributos
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;
    private String nombre;

    //relaciones
    @OneToMany (mappedBy = "unRol")
    private List<Miembro> listaMiembros;

    //controladores, getters y setters

    public Rol() {
    }

    public Rol(int id, String nombre, List<Miembro> listaMiembros) {
        this.id = id;
        this.nombre = nombre;
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

    public List<Miembro> getListaMiembros() {
        return listaMiembros;
    }

    public void setListaMiembros(List<Miembro> listaMiembros) {
        this.listaMiembros = listaMiembros;
    }


}
