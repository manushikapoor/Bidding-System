package com.manushi.product.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import com.manushi.product.model.request.ProductRequestVO;
import com.manushi.product.model.request.ProductUpdateRequestVO;
import com.manushi.product.model.response.OperationSuccessVO;
import com.manushi.product.model.response.ProductVO;
import com.manushi.product.service.authorization.AuthorizationHandlerServiceImpl;
import com.manushi.product.service.products.ProductsService;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

	@InjectMocks
	private ProductController productController;

	@Mock
	private ProductsService productService;

	@Mock
	private AuthorizationHandlerServiceImpl authorizationHandlerService;

	@Test
	public void createProductTest() {
		ProductRequestVO productRequestVO = new ProductRequestVO();
		HttpServletRequest request = new MockHttpServletRequest();
		doNothing().when(productService).createProduct(productRequestVO);
		ResponseEntity<OperationSuccessVO> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(new OperationSuccessVO());

		ResponseEntity<OperationSuccessVO> response = productController.createProduct(request, productRequestVO);

		assertEquals(expectedResponse, response);
	}

	@Test
	public void getProductsByCategoryTest() {
		String category = "exampleCategory";
		HttpServletRequest request = new MockHttpServletRequest();
		List<ProductVO> productList = new ArrayList<>(); // Create a list of ProductVO as needed

		when(productService.getAllProductsByCategory(category)).thenReturn(productList);
		ResponseEntity<List<ProductVO>> response = productController.getProductsByCategory(request, category);

		assertEquals(productList, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void getProductByIdTest() {
		Long productId = 1L;
		ProductVO product = new ProductVO();
		HttpServletRequest request = new MockHttpServletRequest();

		when(productService.getProductById(productId)).thenReturn(product);
		ResponseEntity<ProductVO> response = productController.getProductById(request, productId);

		assertEquals(product, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void getAllProductsTest() {
		List<ProductVO> productList = new ArrayList<>();
		HttpServletRequest request = new MockHttpServletRequest();

		when(productService.getAllProducts()).thenReturn(productList);
		ResponseEntity<List<ProductVO>> response = productController.getAllProducts(request);

		assertEquals(productList, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void updateProductTest() {
		Long productId = 1L;
		HttpServletRequest request = new MockHttpServletRequest();
		ProductUpdateRequestVO productUpdateRequestVO = new ProductUpdateRequestVO();
		doNothing().when(productService).updateProduct(productId, productUpdateRequestVO);
		ResponseEntity<OperationSuccessVO> expectedResponse = ResponseEntity.ok(new OperationSuccessVO());

		ResponseEntity<OperationSuccessVO> response = productController.updateProduct(request, productId, productUpdateRequestVO);

		assertEquals(expectedResponse, response);
	}

	@Test
	public void deleteProductTest() {
		Long productId = 1L;
		doNothing().when(productService).deleteProduct(productId);
		HttpServletRequest request = new MockHttpServletRequest();
		ResponseEntity<OperationSuccessVO> expectedResponse = ResponseEntity.ok(new OperationSuccessVO());

		ResponseEntity<OperationSuccessVO> response = productController.deleteProduct(request, productId);

		assertEquals(expectedResponse, response);
	}
}
