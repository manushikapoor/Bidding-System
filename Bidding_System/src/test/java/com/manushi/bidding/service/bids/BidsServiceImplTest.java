package com.manushi.bidding.service.bids;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.manushi.bidding.model.request.BidRequestVO;
import com.manushi.bidding.model.response.BidVO;
import com.manushi.bidding.repository.AuctionRepository;
import com.manushi.bidding.repository.BidsRepository;
import com.manushi.bidding.repository.ProductsRepository;
import com.manushi.bidding.repository.UsersRepository;
import com.manushi.bidding.repository.entity.Bids;
import com.manushi.bidding.repository.entity.Products;
import com.manushi.bidding.repository.entity.Users;

@ExtendWith(MockitoExtension.class)
public class BidsServiceImplTest {

	@InjectMocks
	private BidsServiceImpl bidsService;

	@Mock
	private BidsRepository bidsRepository;

	@Mock
	private ProductsRepository productsRepository;

	@Mock
	private UsersRepository usersRepository;

	@Mock
	private AuctionRepository auctionRepository;

	@Test
	public void testCreateBid() {
		// Mock product and user data
		Products product = new Products();
		product.setId(1L);
		product.setBidStartTime(LocalDateTime.now().minusHours(1));
		product.setBidEndTime(LocalDateTime.now().plusHours(1));
		product.setBasePrice(BigDecimal.valueOf(100.0));

		Users user = new Users();
		user.setId(1L);

		BidRequestVO bidRequestVO = new BidRequestVO();
		bidRequestVO.setProductId(1L);
		bidRequestVO.setUserId(1L);
		bidRequestVO.setAmount(BigDecimal.valueOf(150.0));

		// Mock repository methods
		when(productsRepository.findById(1L)).thenReturn(Optional.of(product));
		when(usersRepository.findById(1L)).thenReturn(Optional.of(user));
		when(bidsRepository.save(any(Bids.class))).thenReturn(new Bids()); // You can use any() here, as it's a void method.

		// Call the method to be tested
		assertDoesNotThrow(() -> bidsService.createBid(bidRequestVO));

	}

	@Test
	public void testGetAllBids() {
		Users user = new Users();
		user.setUserName("testuser");
		Products product = new Products();
		product.setName("Test Product");

		Bids bid1 = new Bids();
		bid1.setUser(user); // Set the user for the bid
		bid1.setProduct(product);
		bid1.setAmount(new BigDecimal("100.00"));
		// Set up bid1 data
		Bids bid2 = new Bids();
		bid2.setUser(user); // Set the user for the bid
		bid2.setProduct(product);
		bid2.setAmount(new BigDecimal("100.00"));
		// Set up bid2 data
		List<Bids> bidsList = Arrays.asList(bid1, bid2);

		// Mock repository methods
		when(bidsRepository.findAll()).thenReturn(bidsList);

		// Call the method to be tested
		List<BidVO> bidVOList = bidsService.getAllBids();

		// Add assertions to verify the result
		assertEquals(2, bidVOList.size());
	}

	@Test
	public void testGetBidsByUserName() {
		// Mock a user and a list of Bids by user
		Users user = new Users();
		user.setUserName("testuser");
		Products product = new Products();
		product.setName("Test Product");

		Bids bid1 = new Bids();
		bid1.setUser(user); // Set the user for the bid
		bid1.setProduct(product);
		bid1.setAmount(new BigDecimal("100.00"));
		// Set up bid1 data
		Bids bid2 = new Bids();
		bid2.setUser(user); // Set the user for the bid
		bid2.setProduct(product);
		bid2.setAmount(new BigDecimal("100.00"));
		// Set up bid2 data
		List<Bids> bidsList = Arrays.asList(bid1, bid2);

		// Mock repository methods
		when(usersRepository.findByUserName("testuser")).thenReturn(user);
		when(bidsRepository.findByUserId(user)).thenReturn(bidsList);

		// Call the method to be tested
		List<BidVO> bidVOList = bidsService.getBidsByUserName("testuser");

		// Add assertions to verify the result
		assertEquals(2, bidVOList.size());
	}
}
