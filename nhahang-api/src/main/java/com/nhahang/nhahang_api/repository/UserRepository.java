package com.nhahang.nhahang_api.repository;

import com.nhahang.nhahang_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByHoTenContainingIgnoreCaseOrEmailContainingIgnoreCase(String hoTen, String email);
}
