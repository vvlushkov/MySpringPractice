package com.lushkov.springpractice.dao;

import com.lushkov.springpractice.models.Role;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class RoleDaoImpl extends AbstractDaoImpl<Role> implements RoleDao {

    public Role findByName(String name) {
        return execReturn(session -> {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Role> roleCriteriaQuery =
                    criteriaBuilder.createQuery(Role.class);
            Root<Role> roleRoot = roleCriteriaQuery.from(Role.class);
            Query<Role> query = session.createQuery(roleCriteriaQuery.select(roleRoot).where(
                    criteriaBuilder.equal(roleRoot.get("name"), name)));
            return query.getSingleResult();
        });
    }
}
