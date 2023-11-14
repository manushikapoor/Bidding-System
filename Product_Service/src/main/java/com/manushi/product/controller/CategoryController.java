package com.manushi.product.controller;

import static com.manushi.product.util.Constants.CATEGORY;
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
import org.springframework.web.bind.annotation.RestController;

import com.manushi.product.model.request.CategoryRequestVO;
import com.manushi.product.model.response.CategoryVO;
import com.manushi.product.model.response.OperationSuccessVO;
import com.manushi.product.service.categories.CategoryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(V1 + CATEGORY)
public class CategoryController {

	@Autowired
	private final CategoryService categoryService;

	@PreAuthorize("@tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerServiceImpl.getAuthorizationHeader(#request), 'admin')== true")
	@PostMapping()
	public ResponseEntity<OperationSuccessVO> createCategory(HttpServletRequest request, @Valid @RequestBody CategoryRequestVO categoryDetails) {
		categoryService.createCategory(categoryDetails);
		return ResponseEntity.status(HttpStatus.CREATED).body(new OperationSuccessVO());
	}

	@PreAuthorize("@tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerServiceImpl.getAuthorizationHeader(#request), 'admin')== true "
			+ "or @tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerServiceImpl.getAuthorizationHeader(#request), 'vendor')== true "
			+ "or @tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerServiceImpl.getAuthorizationHeader(#request), 'user')== true")
	@GetMapping()
	public ResponseEntity<List<CategoryVO>> getCategories(HttpServletRequest request) {

		return ResponseEntity.ok(categoryService.getAllCategories());
	}

	@PreAuthorize("@tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerServiceImpl.getAuthorizationHeader(#request), 'admin')== true")
	@PatchMapping("/{categoryId}")
	public ResponseEntity<OperationSuccessVO> updateCategory(HttpServletRequest request, @PathVariable Long categoryId,
			@Valid @RequestBody CategoryRequestVO categoryDetails) {
		categoryService.updateCategory(categoryId, categoryDetails);
		return ResponseEntity.ok(new OperationSuccessVO());
	}

	@PreAuthorize("@tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerServiceImpl.getAuthorizationHeader(#request), 'admin')== true")
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<OperationSuccessVO> deleteCategory(HttpServletRequest request, @PathVariable Long categoryId) {
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok(new OperationSuccessVO());
	}

}
