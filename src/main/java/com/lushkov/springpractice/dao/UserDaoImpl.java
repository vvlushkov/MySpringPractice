package com.lushkov.springpractice.dao;

import com.lushkov.springpractice.models.User;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {

    public User findByLogin(String login) {
        return execReturn(session -> {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> userCriteriaQuery =
                    criteriaBuilder.createQuery(User.class);
            Root<User> userRoot = userCriteriaQuery.from(User.class);
            Query<User> query = session.createQuery(userCriteriaQuery.select(userRoot).where(
                    criteriaBuilder.equal(userRoot.get("login"), login)));
            return query.getSingleResult();
        });
    }

    public User findByEmail(String email) {
        return execReturn(session -> {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> userCriteriaQuery =
                    criteriaBuilder.createQuery(User.class);
            Root<User> userRoot = userCriteriaQuery.from(User.class);
            Query<User> query = session.createQuery(userCriteriaQuery.select(userRoot).where(
                    criteriaBuilder.equal(userRoot.get("email"), email)));
            return query.getSingleResult();
        });
    }
}
