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
public class BidsTest {

	@InjectMocks
	private Bids bids;

	@Test
	public void setId() {
		bids.setId(1L);
		assertEquals(1L, bids.getId());
	}

	@Test
	public void setProduct() {
		Products product = Products.builder().id(1L).build();
		bids.setProduct(product);
		assertEquals(product, bids.getProduct());
	}

	@Test
	public void setUser() {
		Users user = Users.builder().id(2L).build();
		bids.setUser(user);
		assertEquals(user, bids.getUser());
	}

	@Test
	public void setAmount() {
		bids.setAmount(BigDecimal.valueOf(100.00));
		assertEquals(BigDecimal.valueOf(100.00), bids.getAmount());
	}

	@Test
	public void setBidStartTime() {
		LocalDateTime bidStartTime = LocalDateTime.now();
		bids.setBidStartTime(bidStartTime);
		assertEquals(bidStartTime, bids.getBidStartTime());
	}

	@Test
	public void builderTest() {
		Products product = Products.builder().id(1L).build();
		Users user = Users.builder().id(2L).build();

		Bids bid = Bids.builder().id(1L).product(product).user(user).amount(BigDecimal.valueOf(100.00)).bidStartTime(LocalDateTime.now()).build();

		assertEquals(1L, bid.getId());
		assertEquals(product, bid.getProduct());
		assertEquals(user, bid.getUser());
		assertEquals(BigDecimal.valueOf(100.00), bid.getAmount());
	}

	@Test
	public void equalsAndHashCodeTest() {
		Bids bid1 = Bids.builder().id(1L).build();
		Bids bid2 = Bids.builder().id(1L).build();
		Bids bid3 = Bids.builder().id(2L).build();

		assertEquals(bid1, bid2);
		assertEquals(bid1.hashCode(), bid2.hashCode());
		assertNotEquals(bid1, bid3);
		assertNotEquals(bid1.hashCode(), bid3.hashCode());
	}

	@Test
	public void toStringTest() {
		// Arrange
		Users user = Users.builder().id(2L).email("test@example.com").userName("testuser").password("password").name("Test User").roleId(1L).build();
		Products product = Products.builder().id(1L).userId(user.getId()).categoryId(1L).name("Test Product").basePrice(BigDecimal.valueOf(100.00))
				.bidStartTime(LocalDateTime.parse("2023-11-14T01:51:03.518701")).bidEndTime(LocalDateTime.parse("2023-11-14T02:51:03.518718"))
				.status("ACTIVE").listingDate(LocalDateTime.parse("2023-11-14T01:51:03.518727")).build();

		Bids bid = Bids.builder().id(1L).product(product).user(user).amount(BigDecimal.valueOf(100.00))
				.bidStartTime(LocalDateTime.parse("2023-11-14T01:51:03.518701")).build();

		// Act & Assert
		assertEquals(
				"Bids(id=1, product=Products(id=1, userId=2, categoryId=1, name=Test Product, basePrice=100.0, bidStartTime=2023-11-14T01:51:03.518701, bidEndTime=2023-11-14T02:51:03.518718, status=ACTIVE, listingDate=2023-11-14T01:51:03.518727), user=Users(id=2, email=test@example.com, userName=testuser, password=password, name=Test User, roleId=1, vendorId=null), amount=100.0, bidStartTime=2023-11-14T01:51:03.518701)",
				bid.toString());
	}

}
