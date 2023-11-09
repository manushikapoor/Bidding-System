package com.manushi.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manushi.product.repository.entity.Bids;

public interface BidsRepository extends JpaRepository<Bids, Long> {

	List<Bids> findByProductId(Long productId);

}
