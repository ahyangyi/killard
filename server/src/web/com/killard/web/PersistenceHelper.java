package com.killard.web;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public final class PersistenceHelper {

    private static final PersistenceManagerFactory pmfInstance =
            JDOHelper.getPersistenceManagerFactory("transactional");

    private static final ThreadLocal<PersistenceManager> persistenceManager = new ThreadLocal<PersistenceManager>();

    private PersistenceHelper() {
    }

    public static void openSession() {
        persistenceManager.set(pmfInstance.getPersistenceManager());
        persistenceManager.get().currentTransaction().begin();
    }

    public static PersistenceManager getPersistenceManager() {
        return persistenceManager.get();
    }

    public static void closeSession() {
        if (persistenceManager.get().currentTransaction().isActive())
            persistenceManager.get().currentTransaction().commit();
        persistenceManager.get().close();
    }

    public static void rollback() {
        if (persistenceManager.get().currentTransaction().isActive())
            persistenceManager.get().currentTransaction().rollback();
    }

    public static void doTransaction() {
        if (persistenceManager.get().currentTransaction().isActive())
            persistenceManager.get().currentTransaction().commit();
        persistenceManager.get().currentTransaction().begin();
    }
}
