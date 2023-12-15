package org.utad;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

//    static {
//        try {
//            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
//        } catch (Throwable ex) {
//            throw new ExceptionInInitializerError(ex);
//        }
//    }
static {
    try {
        System.out.println("Initializing Hibernate SessionFactory...");
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        System.out.println("Hibernate SessionFactory initialized successfully.");
    } catch (Throwable ex) {
        System.err.println("Error initializing Hibernate SessionFactory: " + ex.getMessage());
        throw new ExceptionInInitializerError(ex);
    }
}

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void closeSession(Session session) {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}