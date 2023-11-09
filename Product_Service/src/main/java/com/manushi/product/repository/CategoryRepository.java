package com.manushi.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manushi.product.repository.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	Category findByName(String name);

}
