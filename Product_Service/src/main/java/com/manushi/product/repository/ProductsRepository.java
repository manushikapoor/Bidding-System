package com.manushi.product.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.manushi.product.repository.entity.Category;
import com.manushi.product.repository.entity.Products;

public interface ProductsRepository extends JpaRepository<Products, Long> {

	List<Products> findByCategoryAndBidStartTimeLessThanEqualAndBidEndTimeGreaterThanEqual(Category category, LocalDateTime now, LocalDateTime now2);

	List<Products> findProductsByCategory(Category existingCategory);

	@Modifying
	@Transactional
	@Query(value = "UPDATE products SET name = :newName WHERE product_id = :productId", nativeQuery = true)
	void updateProductNameById(Long productId, String newName);

}
