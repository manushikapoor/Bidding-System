package com.manushi.bidding.service.products;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manushi.bidding.model.request.SendGridEmailMessage;
import com.manushi.bidding.repository.AuctionRepository;
import com.manushi.bidding.repository.ProductsRepository;
import com.manushi.bidding.repository.entity.Auctions;
import com.manushi.bidding.repository.entity.Products;
import com.manushi.bidding.service.email.EmailProducerImpl;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductsRepository productRepository;

	@Autowired
	private EmailProducerImpl emailService;

	@Autowired
	private AuctionRepository auctionRepository;

	@Override
	public void sendMailToWinners(LocalDateTime currentTime) {
		List<Products> products = productRepository.findProductsByStatusAndBidEndTimeLessThan("ACTIVE", currentTime);
		for (Products product : products) {

			// Fetch the winning bid and notify users
			Auctions winningBid = auctionRepository.findByProduct(product);
			if (winningBid != null) {
				SendGridEmailMessage emailMessage = new SendGridEmailMessage();
				emailMessage.setTo(winningBid.getUserId().getEmail()); // Set the winner's email
				emailMessage.setSubject("You won the bid for " + product.getName());
				emailMessage.setContent("Congratulations! You won the bid for " + product.getName() + ".");

				emailService.sendEmail(emailMessage);
			}

		}
	}
}
