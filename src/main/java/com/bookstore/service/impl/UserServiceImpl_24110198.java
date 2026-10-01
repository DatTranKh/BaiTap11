package com.bookstore.service.impl;

import com.bookstore.dao.UserDAO_24110198;
import com.bookstore.model.User_24110198;
import com.bookstore.service.IUserService_24110198;

public class UserServiceImpl_24110198 implements IUserService_24110198 {
    private final UserDAO_24110198 userDAO = new UserDAO_24110198();

    @Override
    public User_24110198 login(String email, String password) {
        User_24110198 user = userDAO.checkLogin(email, password);
        if (user != null) {
            userDAO.updateLastLogin(user.getId());
        }
        return user;
    }

    @Override
    public User_24110198 findByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    @Override
    public boolean register(User_24110198 user) {
        if (userDAO.getUserByEmail(user.getEmail()) != null) {
            return false;
        }
        return userDAO.insertUser(user);
    }
}