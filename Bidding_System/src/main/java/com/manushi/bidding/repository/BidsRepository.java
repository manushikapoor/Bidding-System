package com.manushi.bidding.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manushi.bidding.repository.entity.Bids;
import com.manushi.bidding.repository.entity.Products;
import com.manushi.bidding.repository.entity.Users;

public interface BidsRepository extends JpaRepository<Bids, Long> {

	List<Bids> findByUser(Users userId);

	List<Bids> findByProduct(Products productId);

}
