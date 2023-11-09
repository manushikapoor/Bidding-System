package com.manushi.bidding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manushi.bidding.repository.entity.Auctions;
import com.manushi.bidding.repository.entity.Products;

public interface AuctionRepository extends JpaRepository<Auctions, Long> {

	Auctions findByProduct(Products product);

}
