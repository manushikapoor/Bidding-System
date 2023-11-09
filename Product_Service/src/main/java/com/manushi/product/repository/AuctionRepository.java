package com.manushi.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manushi.product.repository.entity.Auctions;

public interface AuctionRepository extends JpaRepository<Auctions, Long> {

	Auctions findByProductId(Long productId);

}
