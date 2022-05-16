package com.lushkov.springpractice.config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    static final Logger LOG = LogManager.getLogger(HibernateUtil.class);
    private static final SessionFactory sessionFactory = initSessionFactory();

    private static SessionFactory initSessionFactory() {
        try {
            return (new Configuration()).configure().buildSessionFactory();
        } catch (Throwable throwable) {
            LOG.error("Initial SessionFactory creation failed "
                    + throwable.getMessage(), throwable);
            throw new ExceptionInInitializerError(throwable);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            initSessionFactory();
        }
        return sessionFactory;
    }
}