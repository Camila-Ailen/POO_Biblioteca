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
public class Copia implements Serializable {
    //atributos
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;
    private boolean referencia;

    //enums
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @Enumerated(EnumType.STRING)
    private Estado estado;

    //relaciones
    @ManyToOne
    private Rack idRack;

    @ManyToOne
    private Libro idLibro;

    @OneToMany (mappedBy = "idCopia")
    private ArrayList<Prestamo> listaPrestamos;

    //definiciones de los enums
    public enum Tipo {
        TAPA_DURA, LIBRO_EN_RUSTICA, AUDIOLIBRO, LIBRO_ELECTRONICO
    }
    public enum Estado {
        DISPONIBLE, PRESTADA, PERDIDA
    }

    //controladores, getters y setters

    public Copia() {
    }

    public Copia(int id, boolean referencia, Tipo tipo, Estado estado, Rack idRack, Libro idLibro, ArrayList<Prestamo> listaPrestamos) {
        this.id = id;
        this.referencia = referencia;
        this.tipo = tipo;
        this.estado = estado;
        this.idRack = idRack;
        this.idLibro = idLibro;
        this.listaPrestamos = listaPrestamos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isReferencia() {
        return referencia;
    }

    public void setReferencia(boolean referencia) {
        this.referencia = referencia;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Rack getIdRack() {
        return idRack;
    }

    public void setIdRack(Rack idRack) {
        this.idRack = idRack;
    }

    public Libro getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Libro idLibro) {
        this.idLibro = idLibro;
    }

    public ArrayList<Prestamo> getListaPrestamos() {
        return listaPrestamos;
    }

    public void setListaPrestamos(ArrayList<Prestamo> listaPrestamos) {
        this.listaPrestamos = listaPrestamos;
    }



}
