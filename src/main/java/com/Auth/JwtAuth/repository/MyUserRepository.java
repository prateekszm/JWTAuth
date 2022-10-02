package com.Auth.JwtAuth.repository;


import com.Auth.JwtAuth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface MyUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
