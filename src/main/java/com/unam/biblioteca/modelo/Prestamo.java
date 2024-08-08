package com.unam.biblioteca.modelo;


import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Prestamo implements Serializable {
    //atributos
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRetiro;
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDevuelto;
    @Column(nullable = true)
    private double multa;
    @Column(nullable = false)
    private boolean activo = true;

    //relaciones
    @ManyToOne
    @JoinColumn(name = "fk_Miembro", nullable = false)
    private Miembro unMiembro;

    @ManyToOne
    @JoinColumn(name = "fk_Copia", nullable = false)
    private Copia unCopia;

    //constructores, getters y setters

    public Prestamo() {
    }

    public Prestamo(int id, Date fechaRetiro, Date fechaDevuelto, double multa, boolean activo, Miembro unMiembro, Copia unCopia) {
        this.id = id;
        this.fechaRetiro = fechaRetiro;
        this.fechaDevuelto = fechaDevuelto;
        this.multa = multa;
        this.activo = activo;
        this.unMiembro = unMiembro;
        this.unCopia = unCopia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public Date getFechaDevuelto() {
        return fechaDevuelto;
    }

    public void setFechaDevuelto(Date fechaDevuelto) {
        this.fechaDevuelto = fechaDevuelto;
    }

    public double getMulta() {
        return multa;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Miembro getUnMiembro() {
        return unMiembro;
    }

    public void setUnMiembro(Miembro unMiembro) {
        this.unMiembro = unMiembro;
    }

    public Copia getUnCopia() {
        return unCopia;
    }

    public void setUnCopia(Copia unCopia) {
        this.unCopia = unCopia;
    }


}
