package com.manushi.bidding.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
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

import com.manushi.bidding.model.request.BidRequestVO;
import com.manushi.bidding.model.response.BidVO;
import com.manushi.bidding.model.response.OperationSuccessVO;
import com.manushi.bidding.service.bids.BidsService;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class BiddingControllerTest {

	@InjectMocks
	private BiddingController biddingController;

	@Mock
	private BidsService bidsService;

	@Test
	public void testCreateBid() {
		// Create a mock HttpServletRequest
		HttpServletRequest request = mock(HttpServletRequest.class);

		// Create a sample BidRequestVO
		BidRequestVO bidRequestVO = new BidRequestVO();
		// Set up any required data in the bidRequestVO

		// Mock the bidsService's createBid method
		doNothing().when(bidsService).createBid(bidRequestVO);

		// Call the controller method
		ResponseEntity<OperationSuccessVO> response = biddingController.createBid(request, bidRequestVO);

		// Assert the HTTP status code
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	public void testGetAllBids() {
		// Create a mock HttpServletRequest
		HttpServletRequest request = mock(HttpServletRequest.class);

		// Create a sample list of BidVO
		List<BidVO> bidList = new ArrayList<>();
		// Add some sample BidVO objects to the list

		// Mock the bidsService's getAllBids method
		when(bidsService.getAllBids()).thenReturn(bidList);

		// Call the controller method
		ResponseEntity<List<BidVO>> response = biddingController.getAllBids(request);

		// Assert the HTTP status code
		assertEquals(HttpStatus.OK, response.getStatusCode());
		// Assert the returned list
		assertEquals(bidList, response.getBody());
	}

	@Test
	public void testGetBidsByUserName() {
		// Create a mock HttpServletRequest
		HttpServletRequest request = mock(HttpServletRequest.class);

		// Create a sample username
		String username = "sampleUsername";

		// Create a sample list of BidVO
		List<BidVO> bidList = new ArrayList<>();
		// Add some sample BidVO objects to the list

		// Mock the bidsService's getBidsByUserName method
		when(bidsService.getBidsByUserName(username)).thenReturn(bidList);

		// Call the controller method
		ResponseEntity<List<BidVO>> response = biddingController.getBidsByUserName(request, username);

		// Assert the HTTP status code
		assertEquals(HttpStatus.OK, response.getStatusCode());
		// Assert the returned list
		assertEquals(bidList, response.getBody());
	}

	@Test
	public void testGetBidsByProductId() {
		// Create a mock HttpServletRequest
		HttpServletRequest request = mock(HttpServletRequest.class);

		// Create a sample product ID
		Long productId = 123L;

		// Create a sample list of BidVO
		List<BidVO> bidList = new ArrayList<>();
		// Add some sample BidVO objects to the list

		// Mock the bidsService's getBidsByProductId method
		when(bidsService.getBidsByProductId(productId)).thenReturn(bidList);

		// Call the controller method
		ResponseEntity<List<BidVO>> response = biddingController.getBidsByProductId(request, productId);

		// Assert the HTTP status code
		assertEquals(HttpStatus.OK, response.getStatusCode());
		// Assert the returned list
		assertEquals(bidList, response.getBody());
	}
}
