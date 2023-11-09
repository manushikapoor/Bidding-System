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
import com.manushi.bidding.service.email.SendGridEmailProducerImpl;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductsRepository productRepository;

	@Autowired
	private SendGridEmailProducerImpl emailService;

	@Autowired
	private AuctionRepository auctionRepository;

	@Override
	public void sendMailToWinners(LocalDateTime currentTime) {
		List<Products> products = productRepository.findProductsByStatusAndBidEndTimeLessThan("ACTIVE", currentTime);
		for (Products product : products) {
//			product.setStatus("INACTIVE");
//			productRepository.save(product);

			// Fetch the winning bid and notify users
			Auctions winningBid = auctionRepository.findByProduct(product);

			SendGridEmailMessage emailMessage = new SendGridEmailMessage();
			emailMessage.setTo(winningBid.getUserId().getEmail()); // Set the winner's email
			emailMessage.setSubject("You won the bid for " + product.getName());
			emailMessage.setContent("Congratulations! You won the bid for " + product.getName() + ".");

			emailService.sendEmail(emailMessage);

		}
	}
}
