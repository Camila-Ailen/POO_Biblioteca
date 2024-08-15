package com.unam.biblioteca.modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Prestamo implements Serializable {
    //atributos
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaRetiro;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date fechaDevolucion;

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

    public Prestamo(Date fechaRetiro, Miembro unMiembro, Copia unCopia) {
        this.fechaRetiro = fechaRetiro;
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

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
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

    //metodos extras
    public String getNombre(){
        return unMiembro.getNombre();
    }

    public String getApellido(){
        return unMiembro.getApellido();
    }

    public String getTitulo() {
        return unCopia != null ? unCopia.getUnLibro().getTitulo() : "";
    }

    public String getIsbn() {
        return unCopia != null ? unCopia.getUnLibro().getIsbn() : "";
    }

    public int getIdCopia(){
        return unCopia.getId();
    }

    public void calcularMulta(){
        if (fechaDevolucion != null && fechaRetiro != null){

            long diasRetraso = (fechaDevolucion.getTime() - fechaRetiro.getTime()) / (1000 * 60 * 60 * 24) - 10;
            System.out.println("dias de retraso: " + diasRetraso);
            if (diasRetraso > 0){
                this.multa = diasRetraso * unCopia.getUnLibro().getPrecio();
            } else {
                this.multa = 0;
            }
        }
    }

    public String getFormatoFecha (Date date) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return date != null ? formato.format(date) : "";
    }

}
