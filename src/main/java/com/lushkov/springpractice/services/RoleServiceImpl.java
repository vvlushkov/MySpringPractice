package com.lushkov.springpractice.services;

import com.lushkov.springpractice.dao.RoleDao;
import com.lushkov.springpractice.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }

    @Override
    public void create(Role entity) {
        roleDao.create(entity);
    }

    @Override
    public void update(Role entity) {
        roleDao.update(entity);
    }

    @Override
    public void delete(Role entity) {
        roleDao.delete(entity);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Role read(Long id) {
        return roleDao.read(id);
    }
}
