package com.unam.biblioteca.servicio;


import com.unam.biblioteca.modelo.Miembro;
import com.unam.biblioteca.modelo.Rol;
import com.unam.biblioteca.repositorio.Repositorio;

import java.util.ArrayList;
import java.util.List;

public class Servicio {
    private Repositorio repositorio;

    public Servicio(Repositorio p) {
        this.repositorio = p;
    }

    //MIEMBRO

    // Busqueda

    //Busca un miembro segun su id y devuelve el objeto
    public Miembro buscarMiembro(int id) {
        return this.repositorio.buscar(Miembro.class, id);
    }




    // Listados

    /** Se obtienen solo los proveedores activos
     * @return List<Proveedor> listado de proveedores activos
     */
    public List<Miembro> listarProveedores() {
        var miembros = this.repositorio.buscarTodos(Miembro.class);
        var listado = new ArrayList<Miembro>();
        for (var miembro : miembros) {
            if (miembro.isActivo() == false) {
                listado.add(miembro);
            }
        }
        return listado;
    }



}