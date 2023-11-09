package com.manushi.bidding.service.bids;

import java.util.List;

import com.manushi.bidding.model.request.BidRequestVO;
import com.manushi.bidding.model.response.BidVO;

import jakarta.validation.Valid;

public interface BidsService {

	void createBid(@Valid BidRequestVO bidDetails);

	List<BidVO> getAllBids();

	List<BidVO> getBidsByUserName(String username);

	List<BidVO> getBidsByProductId(Long productId);

}
