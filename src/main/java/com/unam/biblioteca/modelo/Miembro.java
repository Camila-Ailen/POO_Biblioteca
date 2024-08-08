package com.unam.biblioteca.modelo;

import java.io.Serializable;
import java.util.ArrayList;

import jakarta.persistence.*;

@Entity
public class Miembro implements Serializable {
    //atributos
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;
    private String clave;
    private String apellido;
    private String nombre;
    private String telefono;
    private String email;
    private boolean activo = true;

    //relaciones
    @OneToMany (mappedBy = "unMiembro")
    private ArrayList<Prestamo> listaPrestamos;

    @ManyToOne
    @JoinColumn(name = "fk_Rol")
    private Rol unRol;

    //controladores, getters y setters

    public Miembro() {
    }

    public Miembro(String clave, String apellido, String nombre, String telefono, String email, boolean activo, Rol unRol) {
        this.clave = clave;
        this.apellido = apellido;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.activo = activo;
        this.unRol = unRol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public ArrayList<Prestamo> getListaPrestamos() {
        return listaPrestamos;
    }

    public void setListaPrestamos(ArrayList<Prestamo> listaPrestamos) {
        this.listaPrestamos = listaPrestamos;
    }

    public Rol getUnRol() {
        return unRol;
    }

    public void setUnRol(Rol unRol) {
        this.unRol = unRol;
    }



}
