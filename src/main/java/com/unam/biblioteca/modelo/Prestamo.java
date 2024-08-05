package com.unam.biblioteca.modelo;


import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Prestamo implements Serializable {
    //atributos
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRetiro;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDevuelto;

    private double multa;

    //relaciones
    @ManyToOne
    private Miembro idMiembro;

    @ManyToOne
    private Copia idCopia;

    //controladores, getters y setters

    public Prestamo() {
    }

    public Prestamo(int id, Date fechaRetiro, Date fechaDevuelto, double multa, Miembro idMiembro, Copia idCopia) {
        this.id = id;
        this.fechaRetiro = fechaRetiro;
        this.fechaDevuelto = fechaDevuelto;
        this.multa = multa;
        this.idMiembro = idMiembro;
        this.idCopia = idCopia;
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

    public Miembro getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(Miembro idMiembro) {
        this.idMiembro = idMiembro;
    }

    public Copia getIdCopia() {
        return idCopia;
    }

    public void setIdCopia(Copia idCopia) {
        this.idCopia = idCopia;
    }


}
