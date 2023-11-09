package com.manushi.bidding.service.scheduler;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.manushi.bidding.service.products.ProductService;

@Service
public class ProductScheduler {

	@Autowired
	private ProductService productService;

	@Scheduled(cron = "0 0 */12 * * *") // Configurable
	public void checkProductBidEndTime() {
		LocalDateTime currentTime = LocalDateTime.now();
		productService.sendMailToWinners(currentTime);
	}
}
