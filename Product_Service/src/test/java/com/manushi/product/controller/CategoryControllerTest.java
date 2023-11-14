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

import com.manushi.product.model.request.CategoryRequestVO;
import com.manushi.product.model.response.CategoryVO;
import com.manushi.product.model.response.OperationSuccessVO;
import com.manushi.product.service.authorization.AuthorizationHandlerServiceImpl;
import com.manushi.product.service.categories.CategoryService;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

	@InjectMocks
	private CategoryController categoryController;

	@Mock
	private CategoryService categoryService;

	@Mock
	private AuthorizationHandlerServiceImpl authorizationHandlerService;

	@Test
	public void createCategoryTest() {
		CategoryRequestVO categoryRequestVO = new CategoryRequestVO();
		doNothing().when(categoryService).createCategory(categoryRequestVO);
		HttpServletRequest request = new MockHttpServletRequest();
		ResponseEntity<OperationSuccessVO> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(new OperationSuccessVO());

		ResponseEntity<OperationSuccessVO> response = categoryController.createCategory(request, categoryRequestVO);

		assertEquals(expectedResponse, response);
	}

	@Test
	public void getCategoriesTest() {
		List<CategoryVO> categoryList = new ArrayList<>(); // Create a list of CategoryVO as needed
		HttpServletRequest request = new MockHttpServletRequest();

		when(categoryService.getAllCategories()).thenReturn(categoryList);

		ResponseEntity<List<CategoryVO>> response = categoryController.getCategories(request);

		assertEquals(categoryList, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void updateCategoryTest() {
		Long categoryId = 1L;
		CategoryRequestVO categoryRequestVO = new CategoryRequestVO();
		HttpServletRequest request = new MockHttpServletRequest();
		doNothing().when(categoryService).updateCategory(categoryId, categoryRequestVO);
		ResponseEntity<OperationSuccessVO> expectedResponse = ResponseEntity.ok(new OperationSuccessVO());

		ResponseEntity<OperationSuccessVO> response = categoryController.updateCategory(request, categoryId, categoryRequestVO);

		assertEquals(expectedResponse, response);
	}

	@Test
	public void deleteCategoryTest() {
		Long categoryId = 1L;
		doNothing().when(categoryService).deleteCategory(categoryId);
		HttpServletRequest request = new MockHttpServletRequest();
		ResponseEntity<OperationSuccessVO> expectedResponse = ResponseEntity.ok(new OperationSuccessVO());

		ResponseEntity<OperationSuccessVO> response = categoryController.deleteCategory(request, categoryId);

		assertEquals(expectedResponse, response);
	}
}
