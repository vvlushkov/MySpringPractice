package com.lushkov.springpractice.services;

import com.lushkov.springpractice.models.Role;

public interface RoleService extends Service<Role>{
    Role findByName(String name);
}