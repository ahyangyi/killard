package com.killard.board.jdo;

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
public enum PersistenceHelper {

    helper;

    private final PersistenceManagerFactory pmfInstance =
            JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private final ThreadLocal<PersistenceManager> persistenceManager = new ThreadLocal<PersistenceManager>();

    private PersistenceHelper() {
    }

    public static void open() {
        PersistenceManager manager = helper.pmfInstance.getPersistenceManager();
        helper.persistenceManager.set(manager);
    }

    public static PersistenceManager getPersistenceManager() {
        PersistenceManager pm = helper.persistenceManager.get();
        if (pm == null || pm.isClosed()) open();
        return helper.persistenceManager.get();
    }

    public static void close() {
        PersistenceManager manager = getPersistenceManager();
        if (manager.isClosed()) return;
        if (manager.currentTransaction().isActive()) manager.currentTransaction().commit();
        manager.close();
    }

    public static void rollback() {
        PersistenceManager manager = getPersistenceManager();
        if (manager.currentTransaction().isActive()) manager.currentTransaction().rollback();
    }

    public static void commit() {
        PersistenceManager manager = getPersistenceManager();
        if (manager.currentTransaction().isActive()) manager.currentTransaction().commit();
        manager.currentTransaction().begin();
    }
}
