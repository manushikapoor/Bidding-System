package com.manushi.product.service.categories;

import java.util.List;

import com.manushi.product.model.request.CategoryRequestVO;
import com.manushi.product.model.response.CategoryVO;

import jakarta.validation.Valid;

public interface CategoryService {
	
	List<CategoryVO> getAllCategories();

	void createCategory(@Valid CategoryRequestVO categoryDetails);

	void updateCategory(Long categoryId, @Valid CategoryRequestVO categoryDetails);

	void deleteCategory(Long categoryId);

}
