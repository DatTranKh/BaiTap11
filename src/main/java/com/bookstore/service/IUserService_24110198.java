package com.bookstore.service;

import com.bookstore.model.User_24110198;

public interface IUserService_24110198 {
    User_24110198 login(String email, String password);
    User_24110198 findByEmail(String email);
    boolean register(User_24110198 user);
}