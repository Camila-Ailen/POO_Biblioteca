package com.unam.biblioteca.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.*;


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
    @JoinColumn(name = "fk_Rack")
    private Rack unRack;

    @ManyToOne
    @JoinColumn(name = "fk_Libro")
    private Libro unLibro;

    @OneToMany (mappedBy = "unCopia")
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

    public Copia(int id, boolean referencia, Tipo tipo, Estado estado, Rack unRack, Libro unLibro, ArrayList<Prestamo> listaPrestamos) {
        this.id = id;
        this.referencia = referencia;
        this.tipo = tipo;
        this.estado = estado;
        this.unRack = unRack;
        this.unLibro = unLibro;
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

    public Rack getUnRack() {
        return unRack;
    }

    public void setUnRack(Rack unRack) {
        this.unRack = unRack;
    }

    public Libro getUnLibro() {
        return unLibro;
    }

    public void setUnLibro(Libro unLibro) {
        this.unLibro = unLibro;
    }

    public ArrayList<Prestamo> getListaPrestamos() {
        return listaPrestamos;
    }

    public void setListaPrestamos(ArrayList<Prestamo> listaPrestamos) {
        this.listaPrestamos = listaPrestamos;
    }



    public static Map<Tipo, Integer> contarCopiasPorTipo(List<Copia> copias) {
        Map<Tipo, Integer> conteo = new EnumMap<>(Tipo.class);
        for (Copia copia : copias) {
            Tipo tipo = copia.getTipo();
            conteo.put(tipo, conteo.getOrDefault(tipo, 0) + 1);
        }
        System.out.println("Hasta el metodo del Copia todo ok");
        return conteo;
    }



}
