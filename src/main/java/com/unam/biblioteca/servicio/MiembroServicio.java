package com.unam.biblioteca.servicio;

import com.unam.biblioteca.modelo.Miembro;
import com.unam.biblioteca.repositorio.Repositorio;

public class MiembroServicio {
    private Repositorio repositorio;

    //Busca un miembro segun su id y devuelve el objeto
    public Miembro buscarMiembro(String id) {
        return this.repositorio.buscar(Miembro.class, id);
    }

}
