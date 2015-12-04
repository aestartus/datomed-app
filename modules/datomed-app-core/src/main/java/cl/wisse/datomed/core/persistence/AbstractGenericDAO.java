package cl.wisse.datomed.core.persistence;


import cl.wisse.datomed.core.persistence.config.EntityManagerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class AbstractGenericDAO<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Logger logger = null;

    private Class<T> entityClass;

    public AbstractGenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
        logger = LoggerFactory.getLogger(this.entityClass);
    }

    /**
     * Crea una entidad. La transacción debe ser cerrada explicitamente a través del EntityManagerHelper. Este metodo no cierra la transacción.
     * @param entity
     * @return
     * @throws PersistenceException
     * @throws RequiereTransaccionException
     */
    public T crear(T entity) throws PersistenceException, RequiereTransaccionException {
        validaTransaccion();
        EntityManagerHelper.getEntityManager().persist(entity);
        return entity;
    }

    /**
     * Crea una entidad. Este metodo inicia y cierra la transacción.
     * @param entity
     * @return
     * @throws PersistenceException
     */
    public T crearTX(T entity) throws PersistenceException {
        EntityManagerHelper.beginTransaction();
        try {
            crear(entity);
        } catch (RequiereTransaccionException e) {
            logger.error(e.getMessage(), e);
        }
        EntityManagerHelper.getEntityManager().flush();
        EntityManagerHelper.commit();
        EntityManagerHelper.closeTransaction();
        return entity;
    }

    /**
     * Borra una entidad. La transacción debe ser cerrada explicitamente a través del EntityManagerHelper. Este método no cierra la transacción.
     * @param entity
     * @throws PersistenceException
     * @throws RequiereTransaccionException
     */
    public void borrar(T entity) throws PersistenceException, RequiereTransaccionException {
        validaTransaccion();
        T entityToBeRemoved = EntityManagerHelper.getEntityManager().merge(entity);
        EntityManagerHelper.getEntityManager().remove(entityToBeRemoved);
    }

    /**
     * Borra una entidad. Este método inicia y cierra la transacción.
     * @param entity
     * @throws PersistenceException
     */
    public void borrarTX(T entity) throws PersistenceException {
        EntityManagerHelper.beginTransaction();
        try {
            borrar(entity);
        } catch (RequiereTransaccionException e) {
            logger.error(e.getMessage(), e);
        }
        EntityManagerHelper.getEntityManager().flush();
        EntityManagerHelper.commit();
        EntityManagerHelper.closeTransaction();
    }

    /**
     * Actualiza una entidad. La transacción debe ser cerrada explicitamente a través del EntityManagerHelper. Este método no cierra la transacción.
     * @param entity
     * @return
     * @throws PersistenceException
     * @throws RequiereTransaccionException
     */
    public T actualizar(T entity) throws PersistenceException, RequiereTransaccionException {
        validaTransaccion();
        entity = EntityManagerHelper.getEntityManager().merge(entity);
        return entity;
    }

    /**
     * Actualiza una entidad. Este método inicia y cierra la transacción.
     * @param entity
     * @return
     * @throws PersistenceException
     */
    public T actualizarTX(T entity) throws PersistenceException {
        EntityManagerHelper.beginTransaction();
        try {
            entity = actualizar(entity);
        } catch (RequiereTransaccionException e) {
            logger.error(e.getMessage(), e);
        }
        EntityManagerHelper.commit();
        EntityManagerHelper.closeTransaction();
        return entity;
    }

    /**
     * Busca una entidad. La transacción debe ser cerrada explicitamente a través del EntityManagerHelper. Este método no cierra la transacción.
     * @param id
     * @return
     * @throws PersistenceException
     * @throws RequiereTransaccionException
     */
    public T buscar(Long id) throws PersistenceException, RequiereTransaccionException {
        validaTransaccion();
        return EntityManagerHelper.getEntityManager().find(entityClass, id);
    }

    /**
     * Busca una entidad. Este método inicia y cierra la transacción.
     * @param id
     * @return
     * @throws PersistenceException
     */
    public T buscarTX(Long id) throws PersistenceException {
        T entity = null;
        EntityManagerHelper.beginTransaction();
        try {
            entity = buscar(id);
        } catch (RequiereTransaccionException e) {
            logger.error(e.getMessage(), e);
        } finally {
            EntityManagerHelper.closeTransaction();
        }
        return entity;
    }

    /**
     * Busca una entidad. La transacción debe ser cerrada explicitamente a través del EntityManagerHelper. Este método no cierra la transacción.
     * @param id
     * @return
     * @throws PersistenceException
     * @throws RequiereTransaccionException
     */
    public T buscarSoloReferencia(Long id) throws PersistenceException, RequiereTransaccionException {
        validaTransaccion();
        T entity = null;
        try {
            entity = EntityManagerHelper.getEntityManager().getReference(entityClass, id);
        } catch (EntityNotFoundException e) {
            logger.warn("Se ha presentado la siguiente excepcion", e);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
        return entity;
    }

    /**
     * Retorna una entidad. Este metodo inicia y cierra la transaccion luego de obtener la entidad.
     * @param id
     * @return
     * @throws PersistenceException
     */
    public T buscarSoloReferenciaTX(Long id) throws PersistenceException, EntityNotFoundException {
        T entity = null;
        EntityManagerHelper.beginTransaction();
        try {
            entity = buscarSoloReferencia(id);
        } catch (RequiereTransaccionException e) {
            logger.error(e.getMessage(), e);
        } finally {
            EntityManagerHelper.closeTransaction();
        }
        return entity;
    }

    /**
     * Retorna una entidad. La transacción debe ser cerrada explicitamente a través del EntityManagerHelper. Este metodo no cierra la transaccion.
     * @param entityQuery
     * @param parametros
     * @return
     * @throws PersistenceException
     * @throws RequiereTransaccionException
     */
    @SuppressWarnings("unchecked")
    public T buscar(String entityQuery, Map<String, Object> parametros) throws  PersistenceException, RequiereTransaccionException {
        validaTransaccion();
        T result = null;
        try {

            Query query = EntityManagerHelper.getEntityManager().createQuery(entityQuery);
            if (parametros != null && !parametros.isEmpty()) {
                llenarParametrosQuery(query, parametros);
            }
            result = (T) query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Ningun resultado encontrado para consulta con: " + entityQuery, e);
        } catch (Exception e) {
            logger.warn("Error al ejecutar consulta: " + e.getMessage(), e);
            throw new PersistenceException(e);
        }
        return result;
    }

    /**
     * Retorna una entidade. Este metodo cierra la transaccion despues de haber obtenido la entidad.
     * @param entityQuery
     * @param parametros
     * @return
     * @throws PersistenceException
     */
    public T buscarTX(String entityQuery, Map<String, Object> parametros) throws  PersistenceException {
        T result = null;
        EntityManagerHelper.beginTransaction();
        try {
            result = buscar(entityQuery, parametros);
        } catch (RequiereTransaccionException e) {
            logger.error(e.getMessage(), e);
        } finally{
            EntityManagerHelper.closeTransaction();
        }
        return result;
    }

    /**
     * Retorna un lista de entidades. La transacción debe ser cerrada explicitamente a través del EntityManagerHelper. Este metodo no cierra la transaccion
     * @return
     * @throws PersistenceException
     * @throws RequiereTransaccionException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<T> lista() throws  PersistenceException, RequiereTransaccionException {
        validaTransaccion();
        try {
            CriteriaQuery cq = EntityManagerHelper.getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            return EntityManagerHelper.getEntityManager().createQuery(cq).getResultList();
        } catch (Exception e) {
            logger.warn("Error al ejecutar consulta: " + e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }

    /**
     * Retorna un lista de entidades.Este metodo cierra la transaccion una vez obtenida la lista.
     * @return
     * @throws PersistenceException
     * @throws RequiereTransaccionException
     */
    public List<T> listaTX() throws  PersistenceException {
        List<T> result = null;
        EntityManagerHelper.beginTransaction();
        try {
            result = lista();
        } catch (RequiereTransaccionException e) {
            logger.warn(e.getMessage(), e);
        } finally{
            EntityManagerHelper.closeTransaction();
        }
        return result;
    }

    /**
     * Retorna una lista de entidades. La transacción debe ser cerrada explicitamente a través del EntityManagerHelper. Este metodo no cierra la transaccion
     * @param entityQuery
     * @param parametros
     * @return
     * @throws PersistenceException
     * @throws RequiereTransaccionException
     */
    @SuppressWarnings("unchecked")
    public List<T> lista(String entityQuery, Map<String, Object> parametros) throws PersistenceException, RequiereTransaccionException {
        validaTransaccion();
        try {

            Query query = EntityManagerHelper.getEntityManager().createQuery(entityQuery);
            if (parametros != null && !parametros.isEmpty()) {
                llenarParametrosQuery(query, parametros);
            }
            return (List<T>) query.getResultList();

        } catch (Exception e) {
            logger.warn("Error al ejecutar consulta: " + e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }

    /**
     * Retorna un lista de entidades. Este metodo cierra la transaccion una vez que la lista es obtenida.
     * @param entityQuery
     * @param parametros
     * @return
     * @throws PersistenceException
     */
    public List<T> listaTX(String entityQuery, Map<String, Object> parametros) throws PersistenceException {
        List<T> result = null;
        EntityManagerHelper.beginTransaction();
        try {
            result = lista(entityQuery, parametros);
        } catch (RequiereTransaccionException e) {
            logger.warn(e.getMessage(), e);
        } finally{
            EntityManagerHelper.closeTransaction();
        }
        return result;
    }

    private void llenarParametrosQuery(Query query, Map<String, Object> parameters) {
        for (Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }

    /**
     * @throws RequiereTransaccionException
     *
     */
    private void validaTransaccion() throws RequiereTransaccionException {
        if (!EntityManagerHelper.isTransactionActive()) {
            throw new RequiereTransaccionException("No hay transaccion activa. Debe manejar inicio y cierre de la transaccion además de efectuar commit si la transaccion ivolucra las operaciones crear o actualizar entidades.");
        }
    }
}