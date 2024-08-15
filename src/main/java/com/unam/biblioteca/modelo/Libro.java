package com.unam.biblioteca.modelo;

import java.io.Serializable;
import java.util.ArrayList;

import jakarta.persistence.*;

@Entity
public class Libro implements Serializable {
    //atributos
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;
    @Column(nullable = false, length = 100)
    private String titulo;
    @Column(nullable = false, length = 13)
    private String isbn;
    @Column(nullable = false)
    private double precio;
    @Column(nullable = false)
    private boolean activo = true;

    //relaciones
    @ManyToOne
    @JoinColumn(name = "fk_Tematica", nullable = false)
    private Tematica unTematica;

    @ManyToOne
    @JoinColumn(name = "fk_Autor", nullable = false)
    private Autor unAutor;

    @ManyToOne
    @JoinColumn(name = "fk_Idioma", nullable = false)
    private Idioma unIdioma;

    @ManyToOne
    @JoinColumn(name = "fk_Editorial", nullable = false)
    private Editorial unEditorial;

    @OneToMany (mappedBy = "unLibro", orphanRemoval = true, cascade = CascadeType.ALL)
    private ArrayList<Copia> listaCopias;

    //constructores, getters y setters

    public Libro() {
    }

    public Libro(String titulo, String isbn, double precio, boolean activo, Tematica unTematica, Autor unAutor, Idioma unIdioma, Editorial unEditorial, ArrayList<Copia> listaCopias) {
        // validaciones
        titulo = titulo.trim().toUpperCase();
        if (titulo.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (titulo.length() > 100) {
            throw new IllegalArgumentException("El nombre no puede tener más de 100 caracteres");
        }

        this.titulo = titulo;
        this.isbn = isbn;
        this.precio = precio;
        this.activo = activo;
        this.unTematica = unTematica;
        this.unAutor = unAutor;
        this.unIdioma = unIdioma;
        this.unEditorial = unEditorial;
        this.listaCopias = listaCopias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Tematica getUnTematica() {
        return unTematica;
    }

    public void setUnTematica(Tematica unTematica) {
        this.unTematica = unTematica;
    }

    public Autor getUnAutor() {
        return unAutor;
    }

    public void setUnAutor(Autor unAutor) {
        this.unAutor = unAutor;
    }

    public Idioma getUnIdioma() {
        return unIdioma;
    }

    public void setUnIdioma(Idioma unIdioma) {
        this.unIdioma = unIdioma;
    }

    public Editorial getUnEditorial() {
        return unEditorial;
    }

    public void setUnEditorial(Editorial unEditorial) {
        this.unEditorial = unEditorial;
    }

    public ArrayList<Copia> getListaCopias() {
        return listaCopias;
    }

    public void setListaCopias(ArrayList<Copia> listaCopias) {
        this.listaCopias = listaCopias;
    }

    public String getNombreAutor(){
        return unAutor != null ? unAutor.getNombre() : "";
    }

    public String getNombreEditorial(){
        return unEditorial != null ? unEditorial.getNombre() : "";
    }

    public String getNombreTematica(){
        return unTematica != null ? unTematica.getNombre() : "";
    }

    public String getNombreIdioma(){
        return unIdioma != null ? unIdioma.getNombre() : "";
    }

    public String getPrecioLibro(){
        return String.valueOf(precio);
    }


}
