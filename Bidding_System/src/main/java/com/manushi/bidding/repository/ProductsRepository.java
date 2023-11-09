package com.manushi.bidding.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manushi.bidding.repository.entity.Products;

public interface ProductsRepository extends JpaRepository<Products, Long> {

	List<Products> findProductsByStatusAndBidEndTimeLessThan(String status, LocalDateTime currentTime);

}
