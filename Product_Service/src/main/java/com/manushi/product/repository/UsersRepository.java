package com.manushi.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manushi.product.repository.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

	Users findByUserName(String vendorName);

}
