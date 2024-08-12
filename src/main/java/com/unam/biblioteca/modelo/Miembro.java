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
    @Column(nullable = false, length = 100)
    private String clave;
    @Column(nullable = false, length = 100)
    private String apellido;
    @Column(nullable = false, length = 100)
    private String nombre;
    @Column(nullable = true, length = 15)
    private String telefono;
    @Column(nullable = false, length = 100)
    private String email;
    @Column(nullable = false)
    private boolean activo = true;

    //relaciones
    @OneToMany (orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "unMiembro")
    private ArrayList<Prestamo> listaPrestamos;

    @ManyToOne
    @JoinColumn(name = "fk_Rol", nullable = false)
    private Rol unRol;

    //constructores, getters y setters

    public Miembro() {
    }

    public Miembro(String clave, String apellido, String nombre, String telefono, String email, boolean activo, Rol unRol) {
        if (clave.isEmpty()) {
            throw new IllegalArgumentException("La clave no puede estar vacía");
        }

        nombre = nombre.trim().toUpperCase();
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (nombre.length() > 100) {
            throw new IllegalArgumentException("El nombre no puede tener más de 100 caracteres");
        }

        apellido = apellido.trim().toUpperCase();
        if (apellido.isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }
        if (apellido.length() > 100) {
            throw new IllegalArgumentException("El apellido no puede tener más de 100 caracteres");
        }

        if (telefono.length() > 15) {
            throw new IllegalArgumentException("El telefono no puede tener más de 15 caracteres");
        }

        email = email.trim().toUpperCase();
        if (email.isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (email.length() > 100) {
            throw new IllegalArgumentException("El email no puede tener más de 100 caracteres");
        }

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
        if (telefono != null && !telefono.matches("[0-9]+")) {
            throw new IllegalArgumentException("El numero de telefono solo puede contener números");
        }
        if (telefono != null && telefono.length() > 15) {
            throw new IllegalArgumentException("El telefono no puede tener más de 15 caracteres");
        }
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


    //metodos adicionales
    public String getRolNombre() {
        return unRol != null ? unRol.getNombre() : "Sin rol";
    }

    public String getEstado() {
        return activo ? "Activo" : "Inactivo";
    }


}
