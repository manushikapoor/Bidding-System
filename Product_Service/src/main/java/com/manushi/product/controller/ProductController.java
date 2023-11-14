package com.manushi.product.controller;

import static com.manushi.product.util.Constants.CATEGORY;
import static com.manushi.product.util.Constants.PRODUCT;
import static com.manushi.product.util.Constants.V1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manushi.product.model.request.ProductRequestVO;
import com.manushi.product.model.request.ProductUpdateRequestVO;
import com.manushi.product.model.response.OperationSuccessVO;
import com.manushi.product.model.response.ProductVO;
import com.manushi.product.service.products.ProductsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(V1 + PRODUCT)
public class ProductController {

	@Autowired
	private final ProductsService productService;

	@PreAuthorize("@tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerServiceImpl.getAuthorizationHeader(#request), 'vendor')== true")
	@PostMapping()
	public ResponseEntity<OperationSuccessVO> createProduct(HttpServletRequest request, @Valid @RequestBody ProductRequestVO productDetails) {
		productService.createProduct(productDetails);
		return ResponseEntity.status(HttpStatus.CREATED).body(new OperationSuccessVO());

	}

	@PreAuthorize("@tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerServiceImpl.getAuthorizationHeader(#request), 'admin')== true "
			+ "or @tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerServiceImpl.getAuthorizationHeader(#request), 'vendor')== true "
			+ "or @tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerServiceImpl.getAuthorizationHeader(#request), 'user')== true")
	@GetMapping(CATEGORY)
	public ResponseEntity<List<ProductVO>> getProductsByCategory(HttpServletRequest request, @RequestParam("category") String category) {

		return ResponseEntity.ok(productService.getAllProductsByCategory(category));
	}

	@PreAuthorize("@tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerServiceImpl.getAuthorizationHeader(#request), 'vendor')== true "
			+ "or @tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerServiceImpl.getAuthorizationHeader(#request), 'admin')== true")
	@GetMapping("/{productId}")
	public ResponseEntity<ProductVO> getProductById(HttpServletRequest request, @PathVariable Long productId) {
		ProductVO product = productService.getProductById(productId);
		return ResponseEntity.ok(product);
	}

	@PreAuthorize("@tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerServiceImpl.getAuthorizationHeader(#request), 'admin')== true "
			+ "or @tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerServiceImpl.getAuthorizationHeader(#request), 'vendor')== true "
			+ "or @tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerServiceImpl.getAuthorizationHeader(#request), 'user')== true")
	@GetMapping()
	public ResponseEntity<List<ProductVO>> getAllProducts(HttpServletRequest request) {
		List<ProductVO> products = productService.getAllProducts();
		return ResponseEntity.ok(products);
	}

	@PreAuthorize("@tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerServiceImpl.getAuthorizationHeader(#request), 'vendor')== true")
	@PatchMapping("/{productId}")
	public ResponseEntity<OperationSuccessVO> updateProduct(HttpServletRequest request, @PathVariable Long productId,
			@Valid @RequestBody ProductUpdateRequestVO productDetails) {
		productService.updateProduct(productId, productDetails);
		return ResponseEntity.ok(new OperationSuccessVO());
	}

	@PreAuthorize("@tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerServiceImpl.getAuthorizationHeader(#request), 'vendor')== true")
	@DeleteMapping("/{productId}")
	public ResponseEntity<OperationSuccessVO> deleteProduct(HttpServletRequest request, @PathVariable Long productId) {
		productService.deleteProduct(productId);
		return ResponseEntity.ok(new OperationSuccessVO());
	}

}
