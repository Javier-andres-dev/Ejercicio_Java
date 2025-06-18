package com.demo.technicaltest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.technicaltest.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity , Long> {
     Optional<RoleEntity> findByNombre(String nombre);
}
