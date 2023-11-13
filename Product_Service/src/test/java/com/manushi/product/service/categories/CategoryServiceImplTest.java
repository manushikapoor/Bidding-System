package com.manushi.product.service.categories;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.manushi.product.exceptions.DataNotFoundException;
import com.manushi.product.model.request.CategoryRequestVO;
import com.manushi.product.model.response.CategoryVO;
import com.manushi.product.repository.AuctionRepository;
import com.manushi.product.repository.BidsRepository;
import com.manushi.product.repository.CategoryRepository;
import com.manushi.product.repository.ProductsRepository;
import com.manushi.product.repository.entity.Category;
import com.manushi.product.repository.entity.Products;
import com.manushi.product.util.RequestValidator;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private ProductsRepository productsRepository;

	@Mock
	private AuctionRepository auctionRepository;

	@Mock
	private BidsRepository bidRepository;

	@Mock
	private RequestValidator requestValidator;

	@InjectMocks
	private CategoryServiceImpl categoryService;

	@Test
	public void testGetAllCategories() {
		// Arrange
		Category category = new Category(1L, "TestCategory");
		when(categoryRepository.findAll()).thenReturn(Collections.singletonList(category));

		// Act
		Iterable<CategoryVO> result = categoryService.getAllCategories();

		// Assert
		assertNotNull(result);
		assertEquals(1, ((List<CategoryVO>) result).size());
		assertEquals(category.getName(), ((List<CategoryVO>) result).get(0).getName());
	}

	@Test
	public void testCreateCategory() {
		// Arrange
		CategoryRequestVO categoryRequestVO = new CategoryRequestVO();

		// Act
		assertDoesNotThrow(() -> categoryService.createCategory(categoryRequestVO));

	}

	@Test
	public void testUpdateCategory() {
		// Arrange
		long categoryId = 1L;
		CategoryRequestVO categoryRequestVO = new CategoryRequestVO();
		Category existingCategory = new Category(categoryId, "TestCategory");
		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));

		// Act
		assertDoesNotThrow(() -> categoryService.updateCategory(categoryId, categoryRequestVO));

		// Assert
		assertEquals(categoryRequestVO.getName(), existingCategory.getName());
	}

	@Test
	public void testUpdateCategoryThrowsDataNotFoundException() {
		// Arrange
		long categoryId = 1L;
		CategoryRequestVO categoryRequestVO = new CategoryRequestVO();
		when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

		// Act & Assert
		DataNotFoundException exception = assertThrows(DataNotFoundException.class,
				() -> categoryService.updateCategory(categoryId, categoryRequestVO));
		assertEquals("Category not found1", exception.getMessage());
	}

	@Test
	public void testDeleteCategory() {
		// Arrange
		long categoryId = 1L;
		Category existingCategory = new Category(categoryId, "TestCategory");
		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
		when(productsRepository.findProductsByCategory(existingCategory)).thenReturn(Collections.emptyList());

		// Act & Assert
		assertDoesNotThrow(() -> categoryService.deleteCategory(categoryId));
	}

	@Test
	public void testDeleteCategoryWithProducts() {
		// Arrange
		long categoryId = 1L;
		Category existingCategory = new Category(categoryId, "TestCategory");
		Products product = new Products();
		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
		when(productsRepository.findProductsByCategory(existingCategory)).thenReturn(Collections.singletonList(product));
		when(bidRepository.findByProductId(product.getId())).thenReturn(Collections.emptyList());
		when(auctionRepository.findByProductId(product.getId())).thenReturn(null);

		// Act & Assert
		assertDoesNotThrow(() -> categoryService.deleteCategory(categoryId));
	}

	@Test
	public void testDeleteCategoryThrowsDataNotFoundException() {
		// Arrange
		long categoryId = 1L;
		when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

		// Act & Assert
		DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> categoryService.deleteCategory(categoryId));
		assertEquals("Category not found1", exception.getMessage());
	}
}
