package com.manushi.bidding.service.products;

import java.time.LocalDateTime;

public interface ProductService {

	void sendMailToWinners(LocalDateTime currentTime);

}
