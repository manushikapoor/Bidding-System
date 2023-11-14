package com.manushi.product.service.products;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.manushi.product.exceptions.DataNotFoundException;
import com.manushi.product.exceptions.InvalidRequestException;
import com.manushi.product.model.request.ProductRequestVO;
import com.manushi.product.model.request.ProductUpdateRequestVO;
import com.manushi.product.model.response.ProductVO;
import com.manushi.product.repository.AuctionRepository;
import com.manushi.product.repository.BidsRepository;
import com.manushi.product.repository.CategoryRepository;
import com.manushi.product.repository.ProductsRepository;
import com.manushi.product.repository.UsersRepository;
import com.manushi.product.repository.entity.Category;
import com.manushi.product.repository.entity.Products;
import com.manushi.product.repository.entity.Users;
import com.manushi.product.repository.enums.ProductStatus;
import com.manushi.product.util.RequestValidator;

@ExtendWith(MockitoExtension.class)
public class ProductsServiceImplTest {

	@Mock
	private ProductsRepository productsRepository;

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private AuctionRepository auctionRepository;

	@Mock
	private BidsRepository bidRepository;

	@Mock
	private UsersRepository usersRepository;

	@Mock
	private RequestValidator requestValidator;

	@InjectMocks
	private ProductsServiceImpl productService;

	@BeforeEach
	public void setUp() {
		// Reset mocks before each test
		reset(categoryRepository, usersRepository, requestValidator, productsRepository, bidRepository, auctionRepository);
	}

	@Test
	public void testCreateProduct() {
		// Arrange
		ProductRequestVO productRequestVO = new ProductRequestVO();
		Category category = new Category(1L, "TestCategory");
		Users user = new Users();
		user.setUserName("TestUser");
		productRequestVO.setUsername("TestUser");
		productRequestVO.setCategory("TestCategory");
		when(categoryRepository.findByName("TestCategory")).thenReturn(category);
		when(usersRepository.findByUserName("TestUser")).thenReturn(user);
		when(requestValidator.isDateRangeValid(productRequestVO.getBidStartTime(), productRequestVO.getBidEndTime())).thenReturn(true);

		// Act
		assertDoesNotThrow(() -> productService.createProduct(productRequestVO));

	}

	@Test
	public void testCreateProductWithInvalidDates() {
		// Arrange
		ProductRequestVO productRequestVO = new ProductRequestVO();
		productRequestVO.setCategory("TestCategory");
		productRequestVO.setName("TestUser");
		productRequestVO.setBidStartTime(LocalDateTime.of(2022, 12, 8, 10, 0, 0));
		productRequestVO.setBidEndTime(LocalDateTime.of(2021, 12, 8, 10, 0, 0));

		// Act & Assert
		InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> productService.createProduct(productRequestVO));
		assertEquals("Invalid dates", exception.getMessage());
	}

	@Test
	public void testCreateProductWithCategoryNotFound() {
		// Arrange
		ProductRequestVO productRequestVO = new ProductRequestVO();
		Category category = new Category();
		category.setName("TestCategory");
		productRequestVO.setCategory("TestCategory");

		when(categoryRepository.findByName("TestCategory")).thenReturn(null);
		when(requestValidator.isDateRangeValid(productRequestVO.getBidStartTime(), productRequestVO.getBidEndTime())).thenReturn(true);
		// Act & Assert
		DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> productService.createProduct(productRequestVO));
		assertEquals("Category not found", exception.getMessage());
	}

	@Test
	public void testGetAllProductsByCategoryWithCategoryNotFound() {
		// Arrange
		when(categoryRepository.findByName("TestCategory")).thenReturn(null);

		// Act & Assert
		DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> productService.getAllProductsByCategory("TestCategory"));
		assertEquals("Category not foundTestCategory", exception.getMessage());
	}

	@Test
	public void testUpdateProduct() {
		// Arrange
		Long productId = 1L;
		ProductUpdateRequestVO productDetails = new ProductUpdateRequestVO();

		Products existingProduct = new Products();

		when(productsRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

		// Act
		assertDoesNotThrow(() -> productService.updateProduct(productId, productDetails));

		// Assert
		assertEquals(productDetails.getName(), existingProduct.getName());

	}

	@Test
	public void testUpdateProductWithProductNotFound() {
		// Arrange
		Long productId = 1L;
		ProductUpdateRequestVO productDetails = new ProductUpdateRequestVO();

		when(productsRepository.findById(productId)).thenReturn(Optional.empty());

		// Act & Assert
		DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> productService.updateProduct(productId, productDetails));
		assertEquals("Product not found1", exception.getMessage());
	}

	@Test
	public void testDeleteProduct() {
		// Arrange
		Long productId = 1L;
		Products existingProduct = new Products();
		existingProduct.setId(1L);

		when(productsRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
		when(bidRepository.findByProductId(productId)).thenReturn(Collections.emptyList());
		when(auctionRepository.findByProductId(productId)).thenReturn(null);

		// Act
		assertDoesNotThrow(() -> productService.deleteProduct(productId));

	}

	@Test
	public void testDeleteProductWithProductNotFound() {
		// Arrange
		Long productId = 1L;

		when(productsRepository.findById(productId)).thenReturn(Optional.empty());

		// Act & Assert
		DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> productService.deleteProduct(productId));
		assertEquals("Product not found1", exception.getMessage());
	}

	@Test
	public void testGetProductById() {
		// Arrange
		Long productId = 1L;
		Products existingProduct = new Products();
		existingProduct.setId(productId);
		existingProduct.setStatus(ProductStatus.ACTIVE);
		BigDecimal basePrice = new BigDecimal(100.00);
		existingProduct.setBasePrice(basePrice);

		when(productsRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

		// Act
		ProductVO result = productService.getProductById(productId);

		// Assert
		assertNotNull(result);
		assertEquals(existingProduct.getName(), result.getName());
		// Additional assertions for other properties can be added
	}

	@Test
	public void testGetProductByIdWithProductNotFound() {
		// Arrange
		Long productId = 1L;

		when(productsRepository.findById(productId)).thenReturn(Optional.empty());

		// Act & Assert
		DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> productService.getProductById(productId));
		assertEquals("Product not found1", exception.getMessage());
	}

	@Test
	public void testGetAllProducts() {
		// Arrange
		Products product1 = new Products();
		Products product2 = new Products();
		BigDecimal basePrice = new BigDecimal(100.00);
		product1.setBasePrice(basePrice);
		product1.setStatus(ProductStatus.ACTIVE);
		product2.setBasePrice(basePrice);
		product2.setStatus(ProductStatus.ACTIVE);

		when(productsRepository.findAll()).thenReturn(List.of(product1, product2));

		// Act
		List<ProductVO> result = productService.getAllProducts();

		// Assert
		assertNotNull(result);
		assertEquals(2, result.size());

	}
}
