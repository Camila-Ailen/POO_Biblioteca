package com.unam.biblioteca.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

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

    //enums
    @Enumerated(EnumType.STRING)
    private Estado estado;

    //relaciones
    @OneToMany (mappedBy = "idMiembro")
    private ArrayList<Prestamo> listaPrestamos;

    @ManyToOne
    private Rol idRol;

    //definiciones de los enums
    public enum Estado {
        ACTIVO, INACTIVO
    }

    //controladores, getters y setters

    public Miembro() {
    }

    public Miembro(int id, String clave, String apellido, String nombre, String telefono, String email, Estado estado, ArrayList<Prestamo> listaPrestamos, Rol idRol) {
        this.id = id;
        this.clave = clave;
        this.apellido = apellido;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.estado = estado;
        this.listaPrestamos = listaPrestamos;
        this.idRol = idRol;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public ArrayList<Prestamo> getListaPrestamos() {
        return listaPrestamos;
    }

    public void setListaPrestamos(ArrayList<Prestamo> listaPrestamos) {
        this.listaPrestamos = listaPrestamos;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }


    public boolean isActivo() {
        boolean es = false;
        if (estado == Estado.ACTIVO) {
            es = true;
        }
        return es;
    }


}
