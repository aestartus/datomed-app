package cl.wisse.datomed.core.persistence.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EntityManagerHelper {
    public static final String UNIT_PERSISTENCE_PROP="clase2.persistence.unit";
    private static final EntityManagerFactory emf;
    private static final ThreadLocal<EntityManager> threadLocal;
    private static final Logger logger;

    static {
        String persistenceUnit = System.getProperty(UNIT_PERSISTENCE_PROP);
        if (persistenceUnit==null)
            persistenceUnit ="clase2.persistence.unit";
        emf = Persistence.createEntityManagerFactory(persistenceUnit);
        threadLocal = new ThreadLocal<EntityManager>();
        logger = Logger.getLogger("clase2.persistence.unit");
        logger.setLevel(Level.SEVERE);
    }

    public static EntityManager getEntityManager() {
        EntityManager manager = threadLocal.get();
        if (manager == null || !manager.isOpen()) {
            manager = emf.createEntityManager();
            threadLocal.set(manager);
        }
        return manager;
    }

    public static void closeTransaction() {
        getEntityManager().close();
    }

    public static boolean isTransactionActive() {
        return getEntityManager().getTransaction().isActive();
    }

    public static void commitAndCloseTransaction() {
        commit();
        if (EntityManagerHelper.isTransactionActive()) {
            closeTransaction();
        }
    }

    public static void closeEntityManager() {
        EntityManager em = threadLocal.get();
        threadLocal.set(null);
        if (em != null) em.close();
    }

    public static void beginTransaction() {
        getEntityManager().getTransaction().begin();
    }

    public static void commit() {
        getEntityManager().getTransaction().commit();
    }

    public static void rollback() {
        getEntityManager().getTransaction().rollback();
    }

    public static Query createQuery(String query) {
        return getEntityManager().createQuery(query);
    }

    public static void log(String info, Level level, Throwable ex) {
        logger.log(level, info, ex);
    }

}
