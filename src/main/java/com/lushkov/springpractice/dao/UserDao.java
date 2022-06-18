package com.lushkov.springpractice.dao;

import com.lushkov.springpractice.models.User;

public interface UserDao extends Dao<User> {

    User findByLogin(String login);

    User findByEmail(String email);
}
