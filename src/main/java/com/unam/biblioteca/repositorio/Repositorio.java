package com.unam.biblioteca.repositorio;

import com.unam.biblioteca.modelo.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Repositorio {

    private final EntityManager em;

    public Repositorio(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }



    public void iniciarTransaccion() {
        em.getTransaction().begin();
    }

    public void confirmarTransaccion() {
        em.getTransaction().commit();
    }

    public void descartarTransaccion() {
        em.getTransaction().rollback();
    }

    public void insertar(Object o) {
        this.em.persist(o);
    }

    public void modificar(Object o){
        this.em.merge(o);
    }

    public void eliminar(Object o){
        this.em.remove(o);
    }

    public void refrescar(Object o) {
        this.em.refresh(o);
    }

    // Metodo generico
    // Acepta cualquier tipo (T) que extienda de Object
    // Devuelve un objeto de tipo (T)
    public <T extends Object> T buscar(Class<T> clase, Object id) {
        return (T) this.em.find(clase, id);
    }

    // Metodo generico
    // Acepta cualquier tipo (T) que extienda de Object
    // Devuelve una lista de ese tipo (T)
    public <T extends Object> List<T> buscarTodos (Class<T> clase) {
        // obtengo un CriteriaBuilder
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        // creo un CriteriaQuery
        CriteriaQuery<T> consulta = cb.createQuery(clase);
        // defino el FROM
        Root<T> origen = consulta.from(clase);
        // defino el select (opcional)
        consulta.select(origen);
        // ejecuto y obtengo el resultado
        return em.createQuery(consulta).getResultList();
    }

    // el parametro de orden a pasar se obtiene del metamodelo generado por EclipseLink
    public <T extends Object, P extends Object> List<T> buscarTodosOrdenadosPor (Class<T> clase, SingularAttribute<T, P> orden) {
        // obtengo un CriteriaBuilder
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        // creo un CriteriaQuery
        CriteriaQuery<T> consulta = cb.createQuery(clase);
        // Defino el FROM
        Root<T> origen = consulta.from(clase);
        // defino el select (opcional)
        consulta.select(origen);
        // ordenado de forma ascendente (cb.asc() )
        // el atributo de origen definido en orden
        consulta.orderBy(cb.asc(origen.get(orden)));
        // ejecuto y obtengo el resultado
        return em.createQuery(consulta).getResultList();
    }

    public List<Copia> buscarCopiasPorLibro(Libro unLibro) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<Copia> consulta = cb.createQuery(Copia.class);
        Root<Copia> origen = consulta.from(Copia.class);
        consulta.select(origen).where(cb.equal(origen.get("unLibro"), unLibro));
        return em.createQuery(consulta).getResultList();
    }

    public Rol buscarRolPorNombre (String nombre) {
        TypedQuery<Rol> consulta = em.createQuery("SELECT r FROM Rol r WHERE r.nombre = :nombre", Rol.class);
        consulta.setParameter("nombre", nombre);
        return consulta.getSingleResult();
    }

    public <T> T buscarPorNombre(Class<T> clase, String nombre) {
        String queryString = "SELECT t FROM " + clase.getSimpleName() + " t WHERE t.nombre = :nombre";
        TypedQuery<T> consulta = em.createQuery(queryString, clase);
        consulta.setParameter("nombre", nombre);
        return consulta.getSingleResult();
    }

    public Rack buscarPorDescripcion(String descripcion) {
        TypedQuery<Rack> consulta = em.createQuery("SELECT r FROM Rack r WHERE r.descripcion = :descripcion", Rack.class);
        consulta.setParameter("descripcion", descripcion);
        return consulta.getSingleResult();
    }

    public Libro buscarLibroPorIsbn(String isbn) {
        TypedQuery<Libro> consulta = em.createQuery("SELECT l FROM Libro l WHERE l.isbn = :isbn", Libro.class);
        consulta.setParameter("isbn", isbn);
        System.out.println("encontramos con isbn desde el repositorio");
        return consulta.getSingleResult();
    }

    public int contarCopiasPorMiembro(int idMiembro) {
        TypedQuery<Long> consulta = em.createQuery(
                "SELECT COUNT(c) FROM Copia c JOIN c.listaPrestamos p where p.unMiembro.id = :idMiembro", Long.class);
        consulta.setParameter("idMiembro", idMiembro);
        return consulta.getSingleResult().intValue();
    }

    public long contarPrestamosActivosPorMiembro (int idMiembro) {
        TypedQuery<Long> consulta = em.createQuery("SELECT COUNT(p) FROM Prestamo p WHERE p.unMiembro.id = :idMiembro AND p.fechaDevolucion IS NULL", Long.class);
        consulta.setParameter("idMiembro", idMiembro);
        return consulta.getSingleResult();
    }

    public boolean tienePrestamosVencidos (int idMiembro) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -10);
        Date fechaLimite = calendar.getTime();

        TypedQuery<Prestamo> consulta = em.createQuery(
                "SELECT p FROM Prestamo p WHERE p.unMiembro.id = :idMiembro AND p.fechaDevolucion IS NULL AND p.fechaRetiro < :fechaLimite",
                Prestamo.class);
        consulta.setParameter("idMiembro", idMiembro);
        consulta.setParameter("fechaLimite", fechaLimite);
        return !consulta.getResultList().isEmpty();
    }

    public void insertarPrestamo (Prestamo prestamo) {
        em.getTransaction().begin();
        em.persist(prestamo);
        em.getTransaction().commit();
    }

    public void actualizarPrestamo (Prestamo prestamo) {
        em.getTransaction().begin();
        em.merge(prestamo);
        em.getTransaction().commit();
    }

}

















