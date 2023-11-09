package com.manushi.bidding.controller;

import static com.manushi.bidding.util.Constants.BIDS;
import static com.manushi.bidding.util.Constants.PRODUCT;
import static com.manushi.bidding.util.Constants.USER;
import static com.manushi.bidding.util.Constants.V1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manushi.bidding.model.request.BidRequestVO;
import com.manushi.bidding.model.response.BidVO;
import com.manushi.bidding.model.response.OperationSuccessVO;
import com.manushi.bidding.service.bids.BidsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(V1 + BIDS)
public class BiddingController {

	@Autowired
	private final BidsService bidsService;

	@PreAuthorize("@tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerService.getAuthorizationHeader(#request), 'user')== true")
	@PostMapping()
	public ResponseEntity<OperationSuccessVO> createBid(HttpServletRequest request, @Valid @RequestBody BidRequestVO bidDetails) {
		bidsService.createBid(bidDetails);
		return ResponseEntity.status(HttpStatus.CREATED).body(new OperationSuccessVO());
	}

	@PreAuthorize("@tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerService.getAuthorizationHeader(#request), 'admin')== true "
			+ "or @tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerService.getAuthorizationHeader(#request), 'vendor')== true "
			+ "or @tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerService.getAuthorizationHeader(#request), 'user')== true")
	@GetMapping()
	public ResponseEntity<List<BidVO>> getAllBids(HttpServletRequest request) {
		List<BidVO> allBids = bidsService.getAllBids();
		return ResponseEntity.ok(allBids);
	}

	@PreAuthorize("@tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerService.getAuthorizationHeader(#request), 'admin')== true")
	@GetMapping(USER)
	public ResponseEntity<List<BidVO>> getBidsByUserName(HttpServletRequest request, @RequestParam("userName") String username) {
		List<BidVO> userBids = bidsService.getBidsByUserName(username);
		return ResponseEntity.ok(userBids);
	}

	@PreAuthorize("@tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerService.getAuthorizationHeader(#request), 'admin')== true "
			+ "or @tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerService.getAuthorizationHeader(#request), 'vendor')== true "
			+ "or @tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerService.getAuthorizationHeader(#request), 'user')== true")
	@GetMapping(PRODUCT)
	public ResponseEntity<List<BidVO>> getBidsByProductId(HttpServletRequest request, @RequestParam("productId") Long productId) {
		List<BidVO> productBids = bidsService.getBidsByProductId(productId);
		return ResponseEntity.ok(productBids);
	}

}
