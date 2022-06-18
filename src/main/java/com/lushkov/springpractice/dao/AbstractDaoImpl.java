package com.lushkov.springpractice.dao;

import com.lushkov.springpractice.config.HibernateUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
public abstract class AbstractDaoImpl<E> implements Dao<E> {

    static final Logger LOG = LogManager.getLogger(AbstractDaoImpl.class);

    private Class<E> inferredClass;

    private Class<E> getGenericClass() {
        if (inferredClass == null) {
            Type mySuperclass = getClass().getGenericSuperclass();
            Type tType = ((ParameterizedType) mySuperclass).getActualTypeArguments()[0];
            String className = tType.toString().split(" ")[1];
            try {
                inferredClass = (Class<E>) Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException();
            }
        }
        return inferredClass;
    }

    public void create(E entity) {
        execVoid(session -> session.save(entity));
    }

    public void update(E entity) {
        execVoid(session-> session.update(entity));
    }

    public void delete(E entity) {
        execVoid(session -> session.delete(entity));
    }

    public List<E> findAll() {
        return execReturn(session -> session.createQuery(
                "FROM " + getGenericClass().getName()).list());
    }

    public E read(Long id) {
        return execReturn(session -> session.get(getGenericClass(), id));
    }

    private void execVoid(Consumer<Session> consumer) {
        execReturn(session -> {
            consumer.accept(session);
            return null;
        });
    }

    protected <T> T execReturn(Function<Session, T> function) {
        T entity = null;
        Transaction transaction = null;
        Session session = null;

        try {
            session =
                    HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            entity = function.apply(session);
        } catch (NoResultException e) {
            //Ignore this because as per your logic this is ok!
        } catch (Exception e) {
            Optional.ofNullable(transaction)
                    .ifPresent(EntityTransaction::rollback);
            LOG.error("Message: " + e.getMessage(), e);
        } finally {
            Optional.ofNullable(transaction)
                    .ifPresent(EntityTransaction::commit);
            Optional.ofNullable(session)
                    .ifPresent(Session::close);
        }
        return entity;
    }
}
