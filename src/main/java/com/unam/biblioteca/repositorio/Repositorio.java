package com.unam.biblioteca.repositorio;

import com.unam.biblioteca.modelo.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        try {
            return em.createQuery("SELECT l FROM Libro l WHERE l.isbn = :isbn", Libro.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Return null if no result is found
        }
    }

    public List<Libro> buscarLibroPorTitulo(String titulo) {
        TypedQuery<Libro> consulta = em.createQuery("FROM Libro l WHERE l.titulo LIKE :titulo", Libro.class);
        consulta.setParameter("titulo", titulo);
        return consulta.getResultList();
    }

    public List<Libro> buscarLibroPorAutor(String autor) {
        TypedQuery<Libro> consulta = em.createQuery("FROM Libro l WHERE l.autor = :autor", Libro.class);
        consulta.setParameter("autor", autor);
        return consulta.getResultList();
    }

    public List<Libro> buscarLibroPorTematica(String tematica) {
        TypedQuery<Libro> consulta = em.createQuery("FROM Libro l WHERE l.tematica = :tematica", Libro.class);
        consulta.setParameter("tematica", tematica);
        return consulta.getResultList();
    }

    public List<Copia> listarTodasLasCopias() {
        TypedQuery<Copia> consulta = em.createQuery("SELECT c FROM Copia c", Copia.class);
        return consulta.getResultList();
    }

    public List<Copia> listarCopiasDisponibles() {
        System.out.println("Entramos a listar las copias disponibles del repo");
        TypedQuery<Copia> consulta = em.createQuery(
                "SELECT c FROM Copia c WHERE c.estado = :estado AND c.referencia = false", Copia.class
        );
        consulta.setParameter("estado", CopiaEstado.DISPONIBLE);
        System.out.println("Parámetro estado establecido: " + consulta.getParameterValue("estado"));
        List<Copia> resultado = consulta.getResultList();
        System.out.println("Resultado de la consulta: " + resultado);
        return resultado;
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

    public int contarDiasDePrestamo(Prestamo prestamo) {
        Date fechaRetiro = prestamo.getFechaRetiro();
        Date fechaActual = new Date();

        long diferencia = Math.abs(fechaActual.getTime() - fechaRetiro.getTime());
        long diff = TimeUnit.DAYS.convert(diferencia, TimeUnit.MILLISECONDS);

        return (int) diff;

    }

    public void registrarPrestamo(int idMiembro, int idCopia) {
        em.getTransaction().begin();
        try {
            Copia copia = em.find(Copia.class, idCopia);
            if (copia != null && CopiaEstado.DISPONIBLE.equals(copia.getEstado())) {
                copia.setEstado(CopiaEstado.PRESTADA);
                em.merge(copia);

                Prestamo prestamo = new Prestamo();
                prestamo.setUnCopia(copia);
                prestamo.setUnMiembro(em.find(Miembro.class, idMiembro));
                prestamo.setFechaRetiro(new Date());
                prestamo.setActivo(true);
                em.persist(prestamo);

                em.getTransaction().commit();
            } else {
                em.getTransaction().rollback();
                throw new RuntimeException("No se pudo registrar el prestamo");
            }
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    public void actualizarPrestamo (Prestamo prestamo) {
        em.getTransaction().begin();
        em.merge(prestamo);
        em.getTransaction().commit();
    }

    public void registrarDevolucion (int idPrestamo){
        em.getTransaction().begin();
        try {
            Prestamo prestamo = em.find(Prestamo.class, idPrestamo);
            if (prestamo != null && prestamo.getFechaDevolucion() == null) {
                prestamo.setFechaDevolucion(new Date());
                //prestamo.calcularMulta();
                prestamo.setActivo(false);
                em.merge(prestamo);

                //cambiar el estado de la copia
                Copia copia = prestamo.getUnCopia();
                copia.setEstado(CopiaEstado.DISPONIBLE);
                em.merge(copia);

                em.getTransaction().commit();
            } else {
                em.getTransaction().rollback();
                throw new RuntimeException("No se pudo registrar la devolución ya que se encuentra realizada");
            }
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    public List<Prestamo> listarPrestamosActivos() {
        TypedQuery<Prestamo> consulta = em.createQuery("SELECT p FROM Prestamo p WHERE p.activo = true", Prestamo.class);
        return consulta.getResultList();
    }

    public List<Prestamo> listarPrestamosPorMiembro(int idMiembro) {
        TypedQuery<Prestamo> consulta = em.createQuery("SELECT p FROM Prestamo p WHERE p.unMiembro.id = :idMiembro", Prestamo.class);
        consulta.setParameter("idMiembro", idMiembro);
        return consulta.getResultList();
    }

    public List<Prestamo> listarPrestamosPorCopia(int idCopia) {
        TypedQuery<Prestamo> consulta = em.createQuery("SELECT p FROM Prestamo p WHERE p.unCopia.id = :idCopia", Prestamo.class);
        consulta.setParameter("idCopia", idCopia);
        return consulta.getResultList();
    }

}

















