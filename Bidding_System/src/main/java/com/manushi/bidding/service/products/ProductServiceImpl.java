package com.manushi.bidding.service.products;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manushi.bidding.model.request.QueueEmailMessage;
import com.manushi.bidding.repository.AuctionRepository;
import com.manushi.bidding.repository.ProductsRepository;
import com.manushi.bidding.repository.entity.Auctions;
import com.manushi.bidding.repository.entity.Products;
import com.manushi.bidding.service.email.EmailProducerImpl;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
		log.debug("Products whose bid time is ended - {}", products);
		for (Products product : products) {

			// Fetch the winning bid and notify users
			Auctions winningBid = auctionRepository.findByProduct(product);
			log.debug("Winning auction - {}", winningBid);
			if (winningBid != null) {
				QueueEmailMessage emailMessage = new QueueEmailMessage();
				emailMessage.setTo(winningBid.getUserId().getEmail()); // Set the winner's email
				emailMessage.setSubject("You won the bid for " + product.getName());
				emailMessage.setContent("Congratulations! You won the bid for " + product.getName() + ".");

				emailService.sendEmail(emailMessage);
				log.debug("Email message - {}", emailMessage);
				log.info("Email successfully sent to winner - {}", winningBid.getUserId().getEmail());
			}

		}
	}
}
