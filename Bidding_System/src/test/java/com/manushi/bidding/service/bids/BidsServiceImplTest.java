package com.manushi.bidding.service.bids;

import static com.manushi.bidding.util.Constants.ERROR_MESSAGE_PRODUCT_NOT_FOUND;
import static com.manushi.bidding.util.Constants.ERROR_MESSAGE_USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.manushi.bidding.exceptions.DataNotFoundException;
import com.manushi.bidding.model.request.BidRequestVO;
import com.manushi.bidding.model.response.BidVO;
import com.manushi.bidding.repository.AuctionRepository;
import com.manushi.bidding.repository.BidsRepository;
import com.manushi.bidding.repository.ProductsRepository;
import com.manushi.bidding.repository.UsersRepository;
import com.manushi.bidding.repository.entity.Auctions;
import com.manushi.bidding.repository.entity.Bids;
import com.manushi.bidding.repository.entity.Products;
import com.manushi.bidding.repository.entity.Users;
import com.manushi.bidding.util.RequestValidator;

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

	@Mock
	private RequestValidator requestValidator;

	@Test
	public void testCreateBid() {
		// Mock product and user data
		Products product = new Products();
		product.setId(1L);
		product.setBidStartTime(LocalDateTime.now().minusHours(1));
		product.setBidEndTime(LocalDateTime.now().plusHours(1));
		product.setBasePrice(BigDecimal.valueOf(100.0));
		product.setCategoryId(1L);
		product.setStatus("ACTIVE");
		product.setListingDate(LocalDateTime.now());
		product.setUserId(1L);

		Users user = new Users();
		user.setId(1L);

		BidRequestVO bidRequestVO = new BidRequestVO();
		bidRequestVO.setProductId(1L);
		bidRequestVO.setUserId(1L);
		bidRequestVO.setAmount(BigDecimal.valueOf(150.0));

		// Mock repository methods
		when(productsRepository.findById(1L)).thenReturn(Optional.of(product));
		when(usersRepository.findById(1L)).thenReturn(Optional.of(user));
		when(bidsRepository.save(any(Bids.class))).thenReturn(new Bids());

		// Mock the validateBidRequestVO method to do nothing
		doNothing().when(requestValidator).validateBidRequestVO(any(), any(), any(), any());

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
		when(bidsRepository.findByUser(user)).thenReturn(bidsList);

		// Call the method to be tested
		List<BidVO> bidVOList = bidsService.getBidsByUserName("testuser");

		// Add assertions to verify the result
		assertEquals(2, bidVOList.size());
	}

	@Test
	public void testGetAllBidsWithNoBids() {
		// Mock repository method
		when(bidsRepository.findAll()).thenReturn(Collections.emptyList());

		// Call the method to be tested
		List<BidVO> bidVOList = bidsService.getAllBids();

		// Add assertions to verify the result
		assertTrue(bidVOList.isEmpty());
	}

	@Test
	public void testGetBidsByUserNameWithNoBids() {
		// Mock repository methods
		when(usersRepository.findByUserName("testuser")).thenReturn(new Users()); // User found but no bids

		// Call the method to be tested
		List<BidVO> bidVOList = bidsService.getBidsByUserName("testuser");

		// Add assertions to verify the result
		assertTrue(bidVOList.isEmpty());
	}

	@Test
	public void testGetBidsByProductIdWithNoBids() {
		// Mock repository methods
		when(productsRepository.findById(1L)).thenReturn(Optional.of(new Products())); // Product found but no bids

		// Call the method to be tested
		List<BidVO> bidVOList = bidsService.getBidsByProductId(1L);

		// Add assertions to verify the result
		assertTrue(bidVOList.isEmpty());
	}

	@Test
	public void testGetBidsByUserNameWithNullUser() {
		String username = "nonexistentuser";

		// Mock repository method to return null for user lookup
		when(usersRepository.findByUserName(username)).thenReturn(null);

		// Call the method to be tested
		DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> bidsService.getBidsByUserName(username));
		assertEquals(ERROR_MESSAGE_USER_NOT_FOUND + username, exception.getMessage());
	}

	@Test
	public void testCreateAuctionWithNullExistingAuction() {
		Products product = new Products();
		Users user = new Users();
		BigDecimal currHighestBid = new BigDecimal("150.0");

		// Mock repository method to return null for existing auction lookup
		when(auctionRepository.findByProduct(product)).thenReturn(null);

		// Call the private method to create an auction
		assertDoesNotThrow(() -> {
			Method method = BidsServiceImpl.class.getDeclaredMethod("createAuction", Products.class, Users.class, BigDecimal.class);
			method.setAccessible(true);
			method.invoke(bidsService, product, user, currHighestBid);
		});
	}

	@Test
	public void testCreateAuctionWithHigherBid() {
		Products product = new Products();
		Users user = new Users();
		BigDecimal existingHighestBid = new BigDecimal("100.0");
		BigDecimal currHighestBid = new BigDecimal("150.0");

		Auctions existingAuction = Auctions.builder().product(product).userId(user).currHighestBid(existingHighestBid).build();

		// Mock repository method to return an existing auction
		when(auctionRepository.findByProduct(product)).thenReturn(existingAuction);

		// Call the private method to create/update an auction
		assertDoesNotThrow(() -> {
			Method method = BidsServiceImpl.class.getDeclaredMethod("createAuction", Products.class, Users.class, BigDecimal.class);
			method.setAccessible(true);
			method.invoke(bidsService, product, user, currHighestBid);
		});
	}

	@Test
	public void testCreateBidWithNullProduct() {
		// Mock user data
		Users user = new Users();
		user.setId(1L);

		BidRequestVO bidRequestVO = new BidRequestVO();
		bidRequestVO.setProductId(1L);
		bidRequestVO.setUserId(1L);
		bidRequestVO.setAmount(BigDecimal.valueOf(150.0));

		// Mock repository method to return Optional.empty() for product lookup
		when(productsRepository.findById(1L)).thenReturn(Optional.empty());

		// Call the method to be tested
		DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> bidsService.createBid(bidRequestVO));
		assertEquals(ERROR_MESSAGE_PRODUCT_NOT_FOUND + bidRequestVO.getProductId(), exception.getMessage());

	}

	@Test
	public void testCreateBidWithNullUser() {
		// Mock product data
		Products product = new Products();
		product.setId(1L);

		BidRequestVO bidRequestVO = new BidRequestVO();
		bidRequestVO.setProductId(1L);
		bidRequestVO.setUserId(1L);
		bidRequestVO.setAmount(BigDecimal.valueOf(150.0));

		// Mock repository method to return Optional.empty() for product lookup
		when(productsRepository.findById(1L)).thenReturn(Optional.of(product));
		when(usersRepository.findById(1L)).thenReturn(Optional.empty());

		// Call the method to be tested
		DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> bidsService.createBid(bidRequestVO));
		assertEquals(ERROR_MESSAGE_USER_NOT_FOUND + bidRequestVO.getProductId(), exception.getMessage());
	}

}
