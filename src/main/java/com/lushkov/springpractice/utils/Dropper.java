package com.lushkov.springpractice.utils;

import com.lushkov.springpractice.config.HibernateUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.io.BufferedReader;
import java.io.FileReader;

public class Dropper {
    /**
     * Field to use logging functions.
     */
    private static final Logger LOG = LogManager
            .getLogger(Dropper.class.getName());

    String sql = "drop table if exist usr_table";
    /**
     * Starting point of Dropper. Executes {@code dropTables} method.
     *
     * @param args console arguments.
     */
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        String dropQuery = null;
        String scriptFile = "19_jpa_hibernate/src/main/resources/dropSchema.sql";

        try (BufferedReader br = new BufferedReader(new FileReader(scriptFile))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            dropQuery = sb.toString();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        session.createSQLQuery(dropQuery).executeUpdate();

        session.getTransaction().commit();
        HibernateUtil.getSessionFactory().close();

        LOG.info("Tables in DB were dropped.");
    }
}
