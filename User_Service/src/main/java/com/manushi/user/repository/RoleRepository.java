package com.manushi.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manushi.user.repository.entity.Roles;

public interface RoleRepository extends JpaRepository<Roles, Long> {

	Roles findByRoleName(String roleName);
}
