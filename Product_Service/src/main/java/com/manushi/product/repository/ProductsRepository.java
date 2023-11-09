package com.manushi.product.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manushi.product.repository.entity.Category;
import com.manushi.product.repository.entity.Products;

public interface ProductsRepository extends JpaRepository<Products, Long> {

	List<Products> findByCategoryAndBidStartTimeLessThanEqualAndBidEndTimeGreaterThanEqual(Category category, LocalDateTime now, LocalDateTime now2);

	List<Products> findProductsByCategory(Category existingCategory);

}
