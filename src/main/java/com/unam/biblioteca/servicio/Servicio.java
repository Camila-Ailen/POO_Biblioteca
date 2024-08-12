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
    public void modificarMiembro(int id, String clave, String apellido, String nombre, String telefono, String email, Boolean activo, Rol unRol) {
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
    public void insertarLibro(String titulo, String isbn, double precio, Tematica unTematica, Autor unAutor, Idioma unIdioma, Editorial unEditorial) {
        try {
            this.repositorio.iniciarTransaccion();
            var libro = new Libro(titulo, isbn, precio, true, unTematica, unAutor, unIdioma, unEditorial, null);
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
    public void modificarLibro(int id, String titulo, String isbn, double precio, Tematica unTematica, Autor unAutor, Idioma unIdioma, Editorial unEditorial) {
        try {
            this.repositorio.iniciarTransaccion();
            var libro = this.repositorio.buscar(Libro.class, id);
            if (libro != null) {
                libro.setTitulo(titulo);
                libro.setIsbn(isbn);
                libro.setPrecio(precio);
                libro.setUnTematica(unTematica);
                libro.setUnAutor(unAutor);
                libro.setUnIdioma(unIdioma);
                libro.setUnEditorial(unEditorial);

                this.repositorio.modificar(libro);
                this.repositorio.confirmarTransaccion();
            } else {
                this.repositorio.descartarTransaccion();
            }
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }

    //Eliminar un libro (dar de baja)
    public void borrarLibro (int id){
        try {
            this.repositorio.iniciarTransaccion();
            var libro = this.repositorio.buscar(Libro.class, id);
            // se controla que exista el Libro y que no se encuentre de baja
            if (libro != null && libro.isActivo() == true) {
                libro.setActivo(false);
                this.repositorio.modificar(libro);
                this.repositorio.confirmarTransaccion();
            } else {
                this.repositorio.descartarTransaccion();
            }
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }


    //RACKS
    //guardar
    public void guardarRack(String nombre) {
        try {
            this.repositorio.iniciarTransaccion();
            var rack = new Rack(nombre);
            this.repositorio.insertar(rack);
            this.repositorio.confirmarTransaccion();
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }

    //modificar
    public void modificarRack(int idRack, String nombre) {
        try {
            this.repositorio.iniciarTransaccion();
            var rack = this.repositorio.buscar(Rack.class, idRack);
            if (rack != null) {
                rack.setDescripcion(nombre);
                this.repositorio.modificar(rack);
                this.repositorio.confirmarTransaccion();
            } else {
                this.repositorio.descartarTransaccion();
            }
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }

    //borrado logico
    public void borrarRack (int idRack) {
        try {
            this.repositorio.iniciarTransaccion();
            var rack = this.repositorio.buscar(Rack.class, idRack);
            // se controla que exista el producto y que no se encuentre de baja
            if (rack != null && rack.getActivo()) {
                rack.setActivo(false);
                this.repositorio.modificar(rack);
                this.repositorio.confirmarTransaccion();
            } else {
                this.repositorio.descartarTransaccion();
            }
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }

    //listar
    public List<Rack> listarRacks() {
        var racks = this.repositorio.buscarTodos(Rack.class);
        var listado = new ArrayList<Rack>();
        for (var rack : racks) {
            if (rack.getActivo()) {
                listado.add(rack);
            }
        }
        return listado;
    }

    //------------------------------------------------------------------------------------------------------------------
    //PARAMETROS
    //------------------------------------------------------------------------------------------------------------------

    //AUTORES
    //buscar por nombre
    public Autor buscarAutorPorNombre(String nombre) {
        return this.repositorio.buscarPorNombre(Autor.class, nombre);
    }

    //guardar
    public void guardarAutor(String nombre) {
        try {
            this.repositorio.iniciarTransaccion();
            var autor = new Autor(nombre);
            this.repositorio.insertar(autor);
            this.repositorio.confirmarTransaccion();
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }

    //modificar
    public void modificarAutor(int idAutor, String nombre) {
        try {
            this.repositorio.iniciarTransaccion();
            var autor = this.repositorio.buscar(Autor.class, idAutor);
            if (autor != null) {
                autor.setNombre(nombre);
                this.repositorio.modificar(autor);
                this.repositorio.confirmarTransaccion();
            } else {
                this.repositorio.descartarTransaccion();
            }
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }

    //borrado logico
    public void borrarAutor (int idAutor) {
        try {
            this.repositorio.iniciarTransaccion();
            var autor = this.repositorio.buscar(Autor.class, idAutor);
            // se controla que exista el producto y que no se encuentre de baja
            if (autor != null && autor.getActivo()) {
                autor.setActivo(false);
                this.repositorio.modificar(autor);
                this.repositorio.confirmarTransaccion();
            } else {
                this.repositorio.descartarTransaccion();
            }
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }

    //listar
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
    //buscar por nombre
    public Tematica buscarTematicaPorNombre(String nombre) {
        return this.repositorio.buscarPorNombre(Tematica.class, nombre);
    }

    //guardar
    public void guardarTematica(String nombre) {
        try {
            this.repositorio.iniciarTransaccion();
            var tematica = new Tematica(nombre);
            this.repositorio.insertar(tematica);
            this.repositorio.confirmarTransaccion();
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }

    //modificar
    public void modificarTematica(int idTematica, String nombre) {
        try {
            this.repositorio.iniciarTransaccion();
            var tematica = this.repositorio.buscar(Tematica.class, idTematica);
            if (tematica != null) {
                tematica.setNombre(nombre);
                this.repositorio.modificar(tematica);
                this.repositorio.confirmarTransaccion();
            } else {
                this.repositorio.descartarTransaccion();
            }
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }

    //borrado logico
    public void borrarTematica (int idTematica) {
        try {
            this.repositorio.iniciarTransaccion();
            var tematica = this.repositorio.buscar(Tematica.class, idTematica);
            // se controla que exista el producto y que no se encuentre de baja
            if (tematica != null && tematica.getActivo()) {
                tematica.setActivo(false);
                this.repositorio.modificar(tematica);
                this.repositorio.confirmarTransaccion();
            } else {
                this.repositorio.descartarTransaccion();
            }
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }

    //Listar
    public List<Tematica> listarTematicas() {
        var tematicas = this.repositorio.buscarTodos(Tematica.class);
        var listado = new ArrayList<Tematica>();
        for (var tematica : tematicas) {
            if (tematica.getActivo()) {
                listado.add(tematica);
            }
        }
        return listado;
    }



    //EDITORIAL
    //buscar por nombre
    public Editorial buscarEditorialPorNombre(String nombre) {
        return this.repositorio.buscarPorNombre(Editorial.class, nombre);
    }

    //guardar
    public void guardarEditorial(String nombre) {
        try {
            this.repositorio.iniciarTransaccion();
            var editorial = new Editorial(nombre);
            this.repositorio.insertar(editorial);
            this.repositorio.confirmarTransaccion();
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }

    //modificar
    public void modificarEditorial(int idEditorial, String nombre) {
        try {
            this.repositorio.iniciarTransaccion();
            var editorial = this.repositorio.buscar(Editorial.class, idEditorial);
            if (editorial != null) {
                editorial.setNombre(nombre);
                this.repositorio.modificar(editorial);
                this.repositorio.confirmarTransaccion();
            } else {
                this.repositorio.descartarTransaccion();
            }
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }

    //borrado logico
    public void borrarEditorial(int idEditorial) {
        try {
            this.repositorio.iniciarTransaccion();
            var editorial = this.repositorio.buscar(Editorial.class, idEditorial);
            // se controla que exista el producto y que no se encuentre de baja
            if (editorial != null && editorial.getActivo()) {
                editorial.setActivo(false);
                this.repositorio.modificar(editorial);
                this.repositorio.confirmarTransaccion();
            } else {
                this.repositorio.descartarTransaccion();
            }
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }

    //Listar
    public List<Editorial> listarEditoriales () {
        var editoriales = this.repositorio.buscarTodos(Editorial.class);
        var listado = new ArrayList<Editorial>();
        for (var editorial : editoriales) {
            if (editorial.getActivo()) {
                listado.add(editorial);
            }
        }
        return listado;
    }



    //IDIOMA
    //buscar por nombre
    public Idioma buscarIdiomaPorNombre(String nombre) {
        return this.repositorio.buscarPorNombre(Idioma.class, nombre);
    }

    //guardar
    public void guardarIdioma(String nombre) {
        try {
            this.repositorio.iniciarTransaccion();
            var idioma = new Idioma(nombre);
            this.repositorio.insertar(idioma);
            this.repositorio.confirmarTransaccion();
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }

    //modificar
    public void modificarIdioma(int idIdioma, String nombre) {
        try {
            this.repositorio.iniciarTransaccion();
            var idioma = this.repositorio.buscar(Idioma.class, idIdioma);
            if (idioma != null) {
                idioma.setNombre(nombre);
                this.repositorio.modificar(idioma);
                this.repositorio.confirmarTransaccion();
            } else {
                this.repositorio.descartarTransaccion();
            }
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }

    //borrado logico
    public void borrarIdioma (int idIdioma) {
        try {
            this.repositorio.iniciarTransaccion();
            var idioma = this.repositorio.buscar(Idioma.class, idIdioma);
            // se controla que exista el producto y que no se encuentre de baja
            if (idioma != null && idioma.getActivo()) {
                idioma.setActivo(false);
                this.repositorio.modificar(idioma);
                this.repositorio.confirmarTransaccion();
            } else {
                this.repositorio.descartarTransaccion();
            }
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }

    //listar
    public List<Idioma> listarIdiomas() {
        var idiomas = this.repositorio.buscarTodos(Idioma.class);
        var listado = new ArrayList<Idioma>();
        for (var idioma : idiomas) {
            if (idioma.getActivo()) {
                listado.add(idioma);
            }
        }
        return listado;
    }


    //ROL
    public List<Rol> listarRoles(){
        return this.repositorio.buscarTodos(Rol.class);
    }

    public Rol buscarRolPorNombre(String nombre) {
        return this.repositorio.buscarPorNombre(Rol.class, nombre);
    }



}