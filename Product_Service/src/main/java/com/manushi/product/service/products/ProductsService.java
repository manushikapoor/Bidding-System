package com.manushi.product.service.products;

import java.util.List;

import com.manushi.product.model.request.ProductRequestVO;
import com.manushi.product.model.request.ProductUpdateRequestVO;
import com.manushi.product.model.response.ProductVO;

import jakarta.validation.Valid;

public interface ProductsService {

	void createProduct(ProductRequestVO productRequestVO);

	List<ProductVO> getAllProductsByCategory(String category);

	void updateProduct(Long productId, @Valid ProductUpdateRequestVO productDetails);

	void deleteProduct(Long productId);

	ProductVO getProductById(Long productId);

	List<ProductVO> getAllProducts();

}
