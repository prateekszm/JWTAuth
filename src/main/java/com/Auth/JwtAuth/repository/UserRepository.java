package com.Auth.JwtAuth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Auth.JwtAuth.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
