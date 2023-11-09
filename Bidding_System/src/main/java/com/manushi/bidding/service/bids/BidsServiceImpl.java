package com.manushi.bidding.service.bids;

import static com.manushi.bidding.util.Constants.ERROR_MESSAGE_BID_AMOUNT;
import static com.manushi.bidding.util.Constants.ERROR_MESSAGE_PRODUCT_NOT_FOUND;
import static com.manushi.bidding.util.Constants.ERROR_MESSAGE_USER_NOT_FOUND;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manushi.bidding.exceptions.DataNotFoundException;
import com.manushi.bidding.exceptions.InvalidRequestException;
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

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BidsServiceImpl implements BidsService {

	@Autowired
	private final BidsRepository bidsRepository;

	@Autowired
	private final ProductsRepository productsRepository;

	@Autowired
	private final AuctionRepository auctionRepository;

	@Autowired
	private final UsersRepository usersRepository;

	@Override
	@Transactional
	public void createBid(BidRequestVO bidRequestVO) {
		Products product = productsRepository.findById(bidRequestVO.getProductId())
				.orElseThrow(() -> new DataNotFoundException(ERROR_MESSAGE_PRODUCT_NOT_FOUND + bidRequestVO.getProductId()));

		Users user = usersRepository.findById(bidRequestVO.getUserId())
				.orElseThrow(() -> new DataNotFoundException(ERROR_MESSAGE_USER_NOT_FOUND + bidRequestVO.getUserId()));

		if (bidRequestVO.getAmount().compareTo(product.getBasePrice()) < 0) {
			throw new InvalidRequestException(ERROR_MESSAGE_BID_AMOUNT);
		}
		Bids bid = Bids.builder().product(product).user(user).amount(bidRequestVO.getAmount()).bidStartTime(LocalDateTime.now()).build();

		bidsRepository.save(bid);

		createAuction(product, user, bidRequestVO.getAmount());
	}

	@Override
	public List<BidVO> getAllBids() {
		List<Bids> bids = bidsRepository.findAll();
		return bids.stream().map(bid -> new BidVO(bid.getUser().getUserName(), bid.getProduct().getName(), bid.getProduct().getId(),
				bid.getAmount().toString(), bid.getBidStartTime())).collect(Collectors.toList());
	}

	@Override
	public List<BidVO> getBidsByUserName(String username) {
		Users user = usersRepository.findByUserName(username);
		if (user == null) {
			throw new DataNotFoundException(ERROR_MESSAGE_USER_NOT_FOUND + username);
		}
		List<Bids> bids = bidsRepository.findByUserId(user);
		return bids.stream().map(bid -> new BidVO(bid.getUser().getUserName(), bid.getProduct().getName(), bid.getProduct().getId(),
				bid.getAmount().toString(), bid.getBidStartTime())).collect(Collectors.toList());
	}

	@Override
	public List<BidVO> getBidsByProductId(Long productId) {

		Products product = productsRepository.findById(productId)
				.orElseThrow(() -> new DataNotFoundException(ERROR_MESSAGE_PRODUCT_NOT_FOUND + productId));
		List<Bids> bids = bidsRepository.findByProductId(product);
		return bids.stream().map(bid -> new BidVO(bid.getUser().getUserName(), bid.getProduct().getName(), bid.getProduct().getId(),
				bid.getAmount().toString(), bid.getBidStartTime())).collect(Collectors.toList());
	}

	private void createAuction(Products product, Users user, BigDecimal currHighestBid) {
		Auctions auction = auctionRepository.findByProduct(product);

		if (auction == null) {
			// If there is no existing auction for this product, create a new auction entry
			auction = Auctions.builder().product(product).userId(user).currHighestBid(currHighestBid).build();
		} else if (currHighestBid.compareTo(auction.getCurrHighestBid()) > 0) {
			// If an auction already exists, check if the new bid is higher
			// Update the current highest bid and user
			auction.setUserId(user);
			auction.setCurrHighestBid(currHighestBid);
		}

		auctionRepository.save(auction);
	}

}
