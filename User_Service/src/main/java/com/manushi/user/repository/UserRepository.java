package com.manushi.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manushi.user.repository.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

	Users findByUserName(String username);

	Boolean existsByUserName(String username);

	Users findByEmail(String email);

	Boolean existsByEmail(String email);

}
