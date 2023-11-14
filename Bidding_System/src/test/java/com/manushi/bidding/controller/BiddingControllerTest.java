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

		HttpServletRequest request = mock(HttpServletRequest.class);

		BidRequestVO bidRequestVO = new BidRequestVO();

		doNothing().when(bidsService).createBid(bidRequestVO);

		ResponseEntity<OperationSuccessVO> response = biddingController.createBid(request, bidRequestVO);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	public void testGetAllBids() {

		HttpServletRequest request = mock(HttpServletRequest.class);

		List<BidVO> bidList = new ArrayList<>();

		when(bidsService.getAllBids()).thenReturn(bidList);

		ResponseEntity<List<BidVO>> response = biddingController.getAllBids(request);

		// Assert the HTTP status code
		assertEquals(HttpStatus.OK, response.getStatusCode());
		// Assert the returned list
		assertEquals(bidList, response.getBody());
	}

	@Test
	public void testGetBidsByUserName() {

		HttpServletRequest request = mock(HttpServletRequest.class);

		String username = "sampleUsername";

		List<BidVO> bidList = new ArrayList<>();

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

		HttpServletRequest request = mock(HttpServletRequest.class);

		Long productId = 123L;

		List<BidVO> bidList = new ArrayList<>();
		when(bidsService.getBidsByProductId(productId)).thenReturn(bidList);

		ResponseEntity<List<BidVO>> response = biddingController.getBidsByProductId(request, productId);

		// Assert the HTTP status code
		assertEquals(HttpStatus.OK, response.getStatusCode());
		// Assert the returned list
		assertEquals(bidList, response.getBody());
	}
}
