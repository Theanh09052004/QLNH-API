package com.nhahang.nhahang_api.service;

import com.nhahang.nhahang_api.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> login(String username, String password);
    Optional<User> findByUsername(String username);
    User save(User user);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    List<User> search(String keyword);
    Optional<User> findById(int id);
    User changeRole(int id);
}
