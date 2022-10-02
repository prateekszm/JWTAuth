package com.Auth.JwtAuth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Auth.JwtAuth.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
