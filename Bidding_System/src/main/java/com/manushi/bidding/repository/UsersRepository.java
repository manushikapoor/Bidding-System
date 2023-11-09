package com.manushi.bidding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manushi.bidding.repository.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

	Users findByUserName(String username);

}
