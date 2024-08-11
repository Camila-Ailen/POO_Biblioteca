package com.unam.biblioteca.servicio;


import com.unam.biblioteca.modelo.*;
import com.unam.biblioteca.repositorio.Repositorio;

import java.util.ArrayList;
import java.util.List;

public class Servicio {
    private Repositorio repositorio;

    public Servicio(Repositorio p) {
        this.repositorio = p;
    }

    //MIEMBRO

    //Busca un miembro segun su id y devuelve el objeto
    public Miembro buscarMiembro(int id) {
        return this.repositorio.buscar(Miembro.class, id);
    }

    //LISTADOS

    //Listado de miembros activos unicamente
    public List<Miembro> listarMiembros() {
        var miembros = this.repositorio.buscarTodos(Miembro.class);
        var listado = new ArrayList<Miembro>();
        for (var miembro : miembros) {
            if (miembro.getActivo() == true) {
                listado.add(miembro);
            }
        }
        return listado;
    }

    //Listado de todos los miembros
    public List<Miembro> listarTodosLosMiembros() {
        return this.repositorio.buscarTodos(Miembro.class);
    }

    //Insertar un miembro
    public void insertarMiembro(String clave, String apellido, String nombre, String telefono, String email, Boolean activo, Rol unRol) {
        try {
            this.repositorio.iniciarTransaccion();
            var miembro = new Miembro(clave, apellido, nombre, telefono, email, activo, unRol);
            this.repositorio.insertar(miembro);
            this.repositorio.confirmarTransaccion();
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            // lanzo nuevamente la excepción para que sea manejada en la capa superior
            throw e;
        }
    }

    //Modificar un miembro
    public void modificarMiembro(int id, String clave, String apellido, String nombre, String telefono, String email, Boolean activo, ArrayList<Prestamo> listaPrestamos, Rol unRol) {
        try {
            this.repositorio.iniciarTransaccion();
            var miembro = this.repositorio.buscar(Miembro.class, id);
            if (miembro != null) {
                miembro.setClave(clave);
                miembro.setApellido(apellido);
                miembro.setNombre(nombre);
                miembro.setTelefono(telefono);
                miembro.setEmail(email);
                miembro.setActivo(activo);
                miembro.setListaPrestamos(listaPrestamos);
                miembro.setUnRol(unRol);
                this.repositorio.modificar(miembro);
                this.repositorio.confirmarTransaccion();
            } else {
                this.repositorio.descartarTransaccion();
            }
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }

    //Eliminar un miembro (dar de baja)
    public void borrarMiembro(int id) {
        try {
            this.repositorio.iniciarTransaccion();
            var miembro = this.repositorio.buscar(Miembro.class, id);
            // se controla que exista el miembro y que no se encuentre de baja
            if (miembro != null && miembro.getActivo() == true) {
                miembro.setActivo(false);
                // Pregunta: no deben cancelarse los pedidos abiertos de dicho proveedor
                this.repositorio.modificar(miembro);
                this.repositorio.confirmarTransaccion();
            } else {
                this.repositorio.descartarTransaccion();
            }
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }


    //LIBRO
    //Busca un libro segun su id y devuelve el objeto
    public Libro buscarLibro(int id) {
        return this.repositorio.buscar(Libro.class, id);
    }

    //Listado de todos los libros
    public List<Libro> listarTodosLosLibros() {
        return this.repositorio.buscarTodos(Libro.class);
    }

    //Insertar un libro
    public void insertarLibro(String titulo, String isbn, double precio, boolean activo, Tematica unTematica, Autor unAutor, Idioma unIdioma, Editorial unEditorial, ArrayList<Copia> listaCopias) {
        try {
            this.repositorio.iniciarTransaccion();
            var libro = new Libro(titulo, isbn, precio, activo, unTematica, unAutor, unIdioma, unEditorial, listaCopias);
            this.repositorio.insertar(libro);
            this.repositorio.confirmarTransaccion();
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            // lanzo nuevamente la excepción para que sea manejada en la capa superior
            throw e;
        }
    }

    public List<Copia> listarCopiasPorLibro(Libro unLibro) {
        return this.repositorio.buscarCopiasPorLibro(unLibro);
    }



    //Modificar un libro


    //Eliminar un libro (dar de baja)




    //AUTORES

    public List<Autor> listarAutores() {
        var autores = this.repositorio.buscarTodos(Autor.class);
        var listado = new ArrayList<Autor>();
        for (var autor : autores) {
            if (autor.getActivo()) {
                listado.add(autor);
            }
        }
        return listado;
    }



    //TEMATICA
    //Listar
    public List<Tematica> listarTematica() {
        var tematicas = this.repositorio.buscarTodos(Tematica.class);
        var listado = new ArrayList<Tematica>();
        for (var tematica : tematicas) {
            if (tematica.getActivo()) {
                listado.add(tematica);
            }
        }
        return listado;
    }



}