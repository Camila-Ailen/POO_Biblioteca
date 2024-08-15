package com.unam.biblioteca.servicio;


import com.unam.biblioteca.controlador.Alerta;
import com.unam.biblioteca.modelo.*;
import com.unam.biblioteca.repositorio.Repositorio;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class Servicio {
    private final Repositorio repositorio;

    public Servicio(Repositorio p) {
        this.repositorio = p;
    }

    //PRESTAMOS
    //registrar prestamo
    public void registrarPrestamo(int idMiembro, int idCopia) {
        if (!puedeSacarPrestamo(idMiembro)) {
            throw new RuntimeException("El miembro no puede sacar prestamos");
        }
        //Prestamo prestamo = new Prestamo(new Date(), repositorio.buscar(Miembro.class, idMiembro), repositorio.buscar(Copia.class, idCopia));
        repositorio.registrarPrestamo(idMiembro, idCopia);
    }

    //devolver prestamo
    public void devolverPrestamo (int idPrestamo) {
        Prestamo prestamo = repositorio.buscar(Prestamo.class, idPrestamo);
        if (prestamo != null){
            prestamo.calcularMulta();
            repositorio.actualizarPrestamo(prestamo);
        }
    }

    //listar prestamos
    public List<Prestamo> listarPrestamosActivos() {
        return repositorio.listarPrestamosActivos();
    }

    //contar cuantos dias de prestamo tiene un prestamo
    public int contarDiasDePrestamo (Prestamo prestamo){
        return repositorio.contarDiasDePrestamo(prestamo);
    }

    //registrar devolucion
    public void registrarDevolucion (int idPrestamo){
        repositorio.registrarDevolucion(idPrestamo);
    }


    //MIEMBRO

    //Contar copias por miembro
    public int contarCopiasPorMiembro(Miembro miembro) {
        return repositorio.contarCopiasPorMiembro(miembro.getId());
    }

    //puede sacar prestamos?
    public boolean puedeSacarPrestamo(int idMiembro) {
        long prestamosActivos = repositorio.contarPrestamosActivosPorMiembro(idMiembro);
        boolean tieneVencidos = repositorio.tienePrestamosVencidos(idMiembro);
        return prestamosActivos < 5 && !tieneVencidos;
    }

    //Busca un miembro segun su id y devuelve el objeto
    public Miembro buscarMiembro(int id) {
        return this.repositorio.buscar(Miembro.class, id);
    }

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
            if (!esEmailValido(email)) {
                Alerta.mostrarAlerta(Alert.AlertType.INFORMATION, "Email incorrecto", "El email no es válido", "Por favor, verifique el email ingresado.");
                throw new IllegalArgumentException("El email no es válido");
            }
            if (repositorio.emailExiste(email)){
                Alerta.mostrarAlerta(Alert.AlertType.INFORMATION, "Email duplicado", "El email ya existe", "Por favor, verifique el email ingresado.");
                throw new RuntimeException("El email ya existe");
            }
            repositorio.iniciarTransaccion();
            var miembro = new Miembro(clave, apellido, nombre, telefono, email, activo, unRol);
            repositorio.insertar(miembro);
            repositorio.confirmarTransaccion();
        } catch (Exception e) {
            repositorio.descartarTransaccion();
            // lanzo nuevamente la excepción para que sea manejada en la capa superior
            throw e;
        }
    }

    //Modificar un miembro
    public void modificarMiembro(int id, String clave, String apellido, String nombre, String telefono, String email, Boolean activo, Rol unRol) {
        try {
            if (!esEmailValido(email)) {
                Alerta.mostrarAlerta(Alert.AlertType.INFORMATION, "Email incorrecto", "El email no es válido", "Por favor, verifique el email ingresado.");
                throw new IllegalArgumentException("El email no es válido");
            }
            if (repositorio.emailExisteParaOtroUsuario(id, email)){
                Alerta.mostrarAlerta(Alert.AlertType.INFORMATION, "Email duplicado", "El email ya existe", "Por favor, verifique el email ingresado.");
                throw new RuntimeException("El email ya existe");
            }
            repositorio.iniciarTransaccion();
            var miembro = this.repositorio.buscar(Miembro.class, id);
            if (miembro != null) {
                miembro.setClave(clave);
                miembro.setApellido(apellido);
                miembro.setNombre(nombre);
                miembro.setTelefono(telefono);
                miembro.setEmail(email);
                miembro.setActivo(activo);
                miembro.setUnRol(unRol);
                //this.repositorio.modificar(miembro);
                repositorio.confirmarTransaccion();
            } else {
                this.repositorio.descartarTransaccion();
                throw new RuntimeException("El miembro no existe");
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

    public List<Prestamo> listarPrestamosPorMiembro(int idMiembro) {
        return repositorio.listarPrestamosPorMiembro(idMiembro);
    }

    public List<Prestamo> listarPrestamosPorCopia(int idCopia) {
        return repositorio.listarPrestamosPorCopia(idCopia);
    }

    //LIBRO
    //Busca un libro segun su id y devuelve el objeto
    public Libro buscarLibro(int id) {
        return this.repositorio.buscar(Libro.class, id);
    }

    //buscar libro por isbn
    public Libro buscarLibroPorIsbn(String isbn) {
        return repositorio.buscarLibroPorIsbn(isbn);
    }

    //buscar libro por titulo
    public List<Libro> buscarLibroPorTitulo(String titulo) {
        return repositorio.buscarLibroPorTitulo(titulo);
    }

    //buscar libro por autor
    public List<Libro> buscarLibroPorAutor(String autor) {
        return repositorio.buscarLibroPorAutor(autor);
    }

    //buscar libro por tematica
    public List<Libro> buscarLibroPorTematica(String tematica) {
        return repositorio.buscarLibroPorTematica(tematica);
    }

    //Listado de todos los libros
    public List<Libro> listarTodosLosLibros() {
        return this.repositorio.buscarTodos(Libro.class);
    }

    //Insertar un libro
    public void insertarLibro(String titulo, String isbn, double precio, Tematica unTematica, Autor unAutor, Idioma unIdioma, Editorial unEditorial) {
        if (isbn.isEmpty()){
            Alerta.mostrarAlerta(Alert.AlertType.INFORMATION, "ISBN incorrecto", "El ISBN no puede estar vacío", "Por favor, verifique el ISBN ingresado.");
            throw new RuntimeException("ISBN no válido");
        }
        if (buscarLibroPorIsbn(isbn) != null){
            Alerta.mostrarAlerta(Alert.AlertType.INFORMATION, "ISBN duplicado", "El ISBN ya existe", "Por favor, verifique el ISBN ingresado.");
            throw new RuntimeException("El libro se encuentra en la biblioteca");
        }
        if (unAutor == null){
            Alerta.mostrarAlerta(Alert.AlertType.INFORMATION, "Autor no válido", "Debe indicar al menos un autor", "Por favor, verifique el autor ingresado.");
            throw new RuntimeException("Debe indicar al menos un autor");
        }
        if (unTematica == null){
            Alerta.mostrarAlerta(Alert.AlertType.INFORMATION, "Tematica no válida", "Debe indicar al menos una temática", "Por favor, verifique la temática ingresada.");
            throw new RuntimeException("Debe indicar al menos una temática");
        }
        if (unIdioma == null){
            Alerta.mostrarAlerta(Alert.AlertType.INFORMATION, "Idioma no válido", "Debe indicar al menos un idioma", "Por favor, verifique el idioma ingresado.");
            throw new RuntimeException("Debe indicar al menos un idioma");
        }
        if (unEditorial == null){
            Alerta.mostrarAlerta(Alert.AlertType.INFORMATION, "Editorial no válida", "Debe indicar al menos una editorial", "Por favor, verifique la editorial ingresada.");
            throw new RuntimeException("Debe indicar al menos una editorial");
        }
        if (precio <= 0){
            Alerta.mostrarAlerta(Alert.AlertType.INFORMATION, "Precio no válido", "El precio debe ser mayor a 0", "Por favor, verifique el precio ingresado.");
            throw new RuntimeException("Precio no válido");
        }

        // Insert the new book
        Libro libro = new Libro(titulo, isbn, precio, true, unTematica, unAutor, unIdioma, unEditorial);
        repositorio.iniciarTransaccion();
        try {
            repositorio.insertar(libro);
            repositorio.confirmarTransaccion();
        } catch (Exception e) {
            repositorio.descartarTransaccion();
            throw e;
        }
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


    //COPIAS
    //listar copias segun un libro
    public List<Copia> listarCopiasPorLibro(Libro unLibro) {
        return this.repositorio.buscarCopiasPorLibro(unLibro);
    }

    //borrado logico copia
    public void borrarCopia (int id){
        try {
            this.repositorio.iniciarTransaccion();
            var copia = this.repositorio.buscar(Copia.class, id);
            if (copia != null && !copia.getEstado().equals(CopiaEstado.PERDIDA)) {
                copia.setEstado(CopiaEstado.PERDIDA);
                this.repositorio.modificar(copia);
                this.repositorio.confirmarTransaccion();
            } else {
                this.repositorio.descartarTransaccion();
            }
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }

    //Listado de todas las copias
    public List<Copia> listarTodasLasCopias() {
        return repositorio.listarTodasLasCopias();
    }

    //listado de las copias disponibles
    public List<Copia> listarCopiasDisponibles() {
        return repositorio.listarCopiasDisponibles();
    }

    //Insertar una copia
    public void insertarCopia(Libro libro, int cantidad, CopiaTipo tipo, Rack rack, boolean referencia, CopiaEstado estado) {
        try {
            for (int i = 0; i < cantidad; i++) {
                this.repositorio.iniciarTransaccion();
                var copia = new Copia(referencia, tipo, estado, rack, libro);
                this.repositorio.insertar(copia);
                this.repositorio.confirmarTransaccion();
                if (i == 0 && referencia == true) {
                    referencia = false;
                }
            }
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            // lanzo nuevamente la excepción para que sea manejada en la capa superior
            throw e;
        }
    }

    //Modificar una copia
    public void modificarCopia(int id, Rack rack, boolean referencia, CopiaEstado estado) {
        try {
            this.repositorio.iniciarTransaccion();
            var copia = this.repositorio.buscar(Copia.class, id);
            if (copia != null) {
                copia.setReferencia(referencia);
                copia.setUnRack(rack);
                copia.setEstado(estado);
                this.repositorio.modificar(copia);
                this.repositorio.confirmarTransaccion();
            } else {
                this.repositorio.descartarTransaccion();
            }
        } catch (Exception e) {
            this.repositorio.descartarTransaccion();
            throw e;
        }
    }


    //listar tipos
    public List<CopiaTipo> listarTipos() {
        return Arrays.asList(CopiaTipo.values());
    }

    //buscar tipo por nombre
    public CopiaTipo buscarTipoPorNombre(String nombre) {
        try {
            return CopiaTipo.valueOf(nombre);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    //listar estados
    public List<CopiaEstado> listarEstados() {
        return Arrays.asList(CopiaEstado.values());
    }

    //buscar tipo por nombre
    public CopiaEstado buscarEstadoPorNombre(String nombre) {
        try {
            return CopiaEstado.valueOf(nombre);
        } catch (IllegalArgumentException e) {
            return null;
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

    //buscar rack por descripcion
    public Rack buscarRackPorDescripcion(String descripcion) {
        return repositorio.buscarPorDescripcion(descripcion);
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




    protected boolean contieneNumeros(String string){
        return string.chars().anyMatch(Character::isDigit);
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    public boolean esEmailValido(String email) {
        if (email == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }


}