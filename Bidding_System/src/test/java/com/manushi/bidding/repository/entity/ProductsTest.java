package com.manushi.bidding.repository.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductsTest {

	@InjectMocks
	private Products products;

	@Test
	public void setId() {
		products.setId(1L);
		assertEquals(1L, products.getId());
	}

	@Test
	public void setUserId() {
		products.setUserId(2L);
		assertEquals(2L, products.getUserId());
	}

	@Test
	public void setCategoryId() {
		products.setCategoryId(3L);
		assertEquals(3L, products.getCategoryId());
	}

	@Test
	public void setName() {
		products.setName("Test Product");
		assertEquals("Test Product", products.getName());
	}

	@Test
	public void setBasePrice() {
		products.setBasePrice(BigDecimal.valueOf(100.00));
		assertEquals(BigDecimal.valueOf(100.00), products.getBasePrice());
	}

	@Test
	public void setBidStartTime() {
		LocalDateTime bidStartTime = LocalDateTime.now();
		products.setBidStartTime(bidStartTime);
		assertEquals(bidStartTime, products.getBidStartTime());
	}

	@Test
	public void setBidEndTime() {
		LocalDateTime bidEndTime = LocalDateTime.now().plusHours(1);
		products.setBidEndTime(bidEndTime);
		assertEquals(bidEndTime, products.getBidEndTime());
	}

	@Test
	public void setStatus() {
		products.setStatus("ACTIVE");
		assertEquals("ACTIVE", products.getStatus());
	}

	@Test
	public void setListingDate() {
		LocalDateTime listingDate = LocalDateTime.now();
		products.setListingDate(listingDate);
		assertEquals(listingDate, products.getListingDate());
	}

	@Test
	public void builderTest() {
		Products product = Products.builder().id(1L).userId(2L).categoryId(3L).name("Test Product").basePrice(BigDecimal.valueOf(100.00))
				.bidStartTime(LocalDateTime.now()).bidEndTime(LocalDateTime.now().plusHours(1)).status("ACTIVE").listingDate(LocalDateTime.now())
				.build();

		assertEquals(1L, product.getId());
		assertEquals(2L, product.getUserId());
		assertEquals(3L, product.getCategoryId());
		assertEquals("Test Product", product.getName());
		assertEquals(BigDecimal.valueOf(100.00), product.getBasePrice());
		assertEquals("ACTIVE", product.getStatus());
	}

	@Test
	public void equalsAndHashCodeTest() {
		Products product1 = Products.builder().id(1L).build();
		Products product2 = Products.builder().id(1L).build();
		Products product3 = Products.builder().id(2L).build();

		assertEquals(product1, product2);
		assertEquals(product1.hashCode(), product2.hashCode());
		assertNotEquals(product1, product3);
		assertNotEquals(product1.hashCode(), product3.hashCode());
	}

	@Test
	public void toStringTest() {
		// Arrange
		Products product = Products.builder().id(1L).userId(2L).categoryId(3L).name("Test Product").basePrice(new BigDecimal("100.00"))
				.bidStartTime(LocalDateTime.parse("2023-11-14T01:51:03.518701")).bidEndTime(LocalDateTime.parse("2023-11-14T02:51:03.518718"))
				.status("ACTIVE").listingDate(LocalDateTime.parse("2023-11-14T01:51:03.518727")).build();

		// Act & Assert
		assertEquals(
				"Products(id=1, userId=2, categoryId=3, name=Test Product, basePrice=100.00, bidStartTime=2023-11-14T01:51:03.518701, bidEndTime=2023-11-14T02:51:03.518718, status=ACTIVE, listingDate=2023-11-14T01:51:03.518727)",
				product.toString());

	}

}
