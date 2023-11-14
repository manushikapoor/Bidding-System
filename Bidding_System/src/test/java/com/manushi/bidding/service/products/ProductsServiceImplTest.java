package com.manushi.bidding.service.products;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.manushi.bidding.repository.AuctionRepository;
import com.manushi.bidding.repository.ProductsRepository;
import com.manushi.bidding.repository.entity.Auctions;
import com.manushi.bidding.repository.entity.Products;
import com.manushi.bidding.repository.entity.Users;
import com.manushi.bidding.service.email.EmailProducerImpl;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

	@Mock
	private ProductsRepository productsRepositoryMock;

	@Mock
	private AuctionRepository auctionRepositoryMock;

	@Mock
	private EmailProducerImpl emailProducerMock;

	@InjectMocks
	private ProductServiceImpl productService;

	@Test
	void sendMailToWinners_WinningBidExists_SendEmail() {
		// Arrange
		LocalDateTime currentTime = LocalDateTime.now();
		Products product = createTestProduct("Test Product");
		Users user = createTestUser("name", "email");
		Auctions winningBid = createTestWinningBid(product, user);

		when(productsRepositoryMock.findProductsByStatusAndBidEndTimeLessThan("ACTIVE", currentTime)).thenReturn(Arrays.asList(product));

		when(auctionRepositoryMock.findByProduct(product)).thenReturn(winningBid);

		// Act
		productService.sendMailToWinners(currentTime);

		// Assert
		verify(emailProducerMock, times(1)).sendEmail(any());
	}

	@Test
	void sendMailToWinners_NoWinningBid_NoEmailSent() {
		// Arrange
		LocalDateTime currentTime = LocalDateTime.now();
		Products product = createTestProduct("Test Product");

		when(productsRepositoryMock.findProductsByStatusAndBidEndTimeLessThan("ACTIVE", currentTime)).thenReturn(Arrays.asList(product));

		when(auctionRepositoryMock.findByProduct(product)).thenReturn(null);

		// Act
		productService.sendMailToWinners(currentTime);

		// Assert
		verify(emailProducerMock, never()).sendEmail(any());
	}

	private Products createTestProduct(String productName) {
		Products product = new Products();
		product.setId(1L);
		product.setName(productName);

		return product;
	}

	private Users createTestUser(String name, String email) {
		Users user = new Users();
		user.setName(name);
		user.setEmail(email);

		return user;
	}

	private Auctions createTestWinningBid(Products product, Users user) {
		Auctions winningBid = new Auctions();
		winningBid.setId(1L);
		winningBid.setProduct(product);
		winningBid.setUserId(user);

		return winningBid;
	}
}
