package com.lushkov.springpractice.dao;

import com.lushkov.springpractice.models.Role;

public interface RoleDao extends Dao<Role> {

    Role findByName(String name);
}
