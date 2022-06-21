package com.lushkov.springpractice.services;

import com.lushkov.springpractice.models.User;

public interface UserService extends Service<User>{

    User findByLogin(String login);

    User findByEmail(String email);
}