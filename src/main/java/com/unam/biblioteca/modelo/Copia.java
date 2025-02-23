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
    @Column(nullable = false)
    private boolean referencia = false;

    //enums
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CopiaTipo tipo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CopiaEstado estado;

    //relaciones
    @ManyToOne
    @JoinColumn(name = "fk_Rack", nullable = false)
    private Rack unRack;

    @ManyToOne
    @JoinColumn(name = "fk_Libro", nullable = false)
    private Libro unLibro;

    @OneToMany (orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "unCopia")
    private ArrayList<Prestamo> listaPrestamos;

    //constructores, getters y setters

    public Copia() {
    }

    public Copia(boolean referencia, CopiaTipo tipo, CopiaEstado estado, Rack unRack, Libro unLibro) {
        this.referencia = referencia;
        this.tipo = tipo;
        this.estado = estado;
        this.unRack = unRack;
        this.unLibro = unLibro;
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

    public CopiaTipo getTipo() {
        return tipo;
    }

    public void setTipo(CopiaTipo tipo) {
        this.tipo = tipo;
    }

    public CopiaEstado getEstado() {
        return estado;
    }

    public void setEstado(CopiaEstado estado) {
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


    //metodo para contar las copias disponibles segun su tipo
    public static Map<CopiaTipo, Integer> contarCopiasPorTipo(List<Copia> copias) {
        Map<CopiaTipo, Integer> conteo = new EnumMap<>(CopiaTipo.class);
        for (Copia copia : copias) {
            if (copia.getEstado().equals(CopiaEstado.DISPONIBLE) && !copia.isReferencia()) {
                CopiaTipo tipo = copia.getTipo();
                conteo.put(tipo, conteo.getOrDefault(tipo, 0) + 1);
            }
        }
        return conteo;
    }

    public String getNombreRack() {
        return unRack != null ? unRack.getDescripcion() : "";
    }

    public String getIsbn() {
        return unLibro != null ? unLibro.getIsbn() : "";
    }

    public String getTitulo() {
        return unLibro != null ? unLibro.getTitulo() : "";
    }

    public String getNombreAutor() {
        return unLibro != null ? unLibro.getUnAutor().getNombre() : "";
    }

    public String getReferencia() {
        return referencia ? "Sí" : "No";
    }

    public String getNombreIdioma() {
        return unLibro != null ? unLibro.getUnIdioma().getNombre() : "";
    }

    public String getNombreEditorial() {
        return unLibro != null ? unLibro.getUnEditorial().getNombre() : "";
    }



}
