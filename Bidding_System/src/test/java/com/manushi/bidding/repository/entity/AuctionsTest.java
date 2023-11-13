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
public class AuctionsTest {

	@InjectMocks
	private Auctions auctions;

	@Test
	public void setId() {
		auctions.setId(1L);
		assertEquals(1L, auctions.getId());
	}

	@Test
	public void setProduct() {
		Products product = Products.builder().id(1L).build();
		auctions.setProduct(product);
		assertEquals(product, auctions.getProduct());
	}

	@Test
	public void setUserId() {
		Users user = Users.builder().id(2L).build();
		auctions.setUserId(user);
		assertEquals(user, auctions.getUserId());
	}

	@Test
	public void setCurrHighestBid() {
		auctions.setCurrHighestBid(BigDecimal.valueOf(100.00));
		assertEquals(BigDecimal.valueOf(100.00), auctions.getCurrHighestBid());
	}

	@Test
	public void equalsAndHashCodeTest() {
		Auctions auction1 = Auctions.builder().id(1L).build();
		Auctions auction2 = Auctions.builder().id(1L).build();
		Auctions auction3 = Auctions.builder().id(2L).build();

		assertEquals(auction1, auction2);
		assertEquals(auction1.hashCode(), auction2.hashCode());
		assertNotEquals(auction1, auction3);
		assertNotEquals(auction1.hashCode(), auction3.hashCode());
	}

	@Test
	public void toStringTest() {
		// Arrange
		Users user = Users.builder().id(3L).email("test@example.com").userName("testuser").password("password").name("Test User").roleId(1L).build();
		Products product = Products.builder().id(2L).userId(user.getId()).categoryId(1L).name("Test Product").basePrice(BigDecimal.valueOf(100.00))
				.bidStartTime(LocalDateTime.parse("2023-11-14T01:51:03.518701")).bidEndTime(LocalDateTime.parse("2023-11-14T02:51:03.518718"))
				.status("ACTIVE").listingDate(LocalDateTime.parse("2023-11-14T01:51:03.518727")).build();

		Auctions auction = Auctions.builder().id(1L).product(product).userId(user).currHighestBid(BigDecimal.valueOf(100.00)).build();

		// Act & Assert
		assertEquals(
				"Auctions(id=1, product=Products(id=2, userId=3, categoryId=1, name=Test Product, basePrice=100.0, bidStartTime=2023-11-14T01:51:03.518701, bidEndTime=2023-11-14T02:51:03.518718, status=ACTIVE, listingDate=2023-11-14T01:51:03.518727), userId=Users(id=3, email=test@example.com, userName=testuser, password=password, name=Test User, roleId=1, vendorId=null), currHighestBid=100.0)",
				auction.toString());
	}

	@Test
	public void builderTest() {
		Products product = Products.builder().id(1L).build();
		Users user = Users.builder().id(2L).build();

		Auctions auction = Auctions.builder().id(1L).product(product).userId(user).currHighestBid(BigDecimal.valueOf(100.00)).build();

		assertEquals(1L, auction.getId());
		assertEquals(product, auction.getProduct());
		assertEquals(user, auction.getUserId());
		assertEquals(BigDecimal.valueOf(100.00), auction.getCurrHighestBid());
	}

}
