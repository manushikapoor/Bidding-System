package com.manushi.product.service.products;

import static com.manushi.product.util.Constants.ERROR_MESSAGE_CATEGORY_NOT_FOUND;
import static com.manushi.product.util.Constants.ERROR_MESSAGE_INVALID_DATES;
import static com.manushi.product.util.Constants.ERROR_MESSAGE_PRODUCT_NOT_FOUND;
import static com.manushi.product.util.Constants.ERROR_MESSAGE_USERNAME_NOT_FOUND;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manushi.product.exceptions.DataNotFoundException;
import com.manushi.product.exceptions.InvalidRequestException;
import com.manushi.product.model.request.ProductRequestVO;
import com.manushi.product.model.request.ProductUpdateRequestVO;
import com.manushi.product.model.response.ProductVO;
import com.manushi.product.repository.AuctionRepository;
import com.manushi.product.repository.BidsRepository;
import com.manushi.product.repository.CategoryRepository;
import com.manushi.product.repository.ProductsRepository;
import com.manushi.product.repository.UsersRepository;
import com.manushi.product.repository.entity.Auctions;
import com.manushi.product.repository.entity.Bids;
import com.manushi.product.repository.entity.Category;
import com.manushi.product.repository.entity.Products;
import com.manushi.product.repository.entity.Users;
import com.manushi.product.repository.enums.ProductStatus;
import com.manushi.product.util.RequestValidator;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class ProductsServiceImpl implements ProductsService {

	@Autowired
	private final RequestValidator requestValidator;

	@Autowired
	private final ProductsRepository productsRepository;

	@Autowired
	private final CategoryRepository categoryRepository;

	@Autowired
	private final AuctionRepository auctionRepository;

	@Autowired
	private final BidsRepository bidRepository;

	@Autowired
	private final UsersRepository usersRepository;

	@Override
	@Transactional
	public void createProduct(ProductRequestVO productRequestVO) {

		requestValidator.validateProductRequestVO(productRequestVO.getBasePrice());

		if (!requestValidator.isDateRangeValid(productRequestVO.getBidStartTime(), productRequestVO.getBidEndTime())) {
			throw new InvalidRequestException(ERROR_MESSAGE_INVALID_DATES);
		}
		Category category = categoryRepository.findByName(productRequestVO.getCategory());
		if (category == null) {
			throw new DataNotFoundException(ERROR_MESSAGE_CATEGORY_NOT_FOUND);
		}
		Users user = usersRepository.findByUserName(productRequestVO.getUsername());
		if (user == null) {
			throw new DataNotFoundException(ERROR_MESSAGE_USERNAME_NOT_FOUND);
		}
		Products product = Products.builder().user(user).name(productRequestVO.getName()).basePrice(productRequestVO.getBasePrice())
				.bidEndTime(productRequestVO.getBidEndTime()).bidStartTime(productRequestVO.getBidStartTime()).category(category)
				.listingDate(LocalDateTime.now()).status(ProductStatus.ACTIVE).build();

		productsRepository.save(product);
		log.info("Product created successfully - {}", product);

	}

	@Override
	public List<ProductVO> getAllProductsByCategory(String categoryName) {
		Category category = categoryRepository.findByName(categoryName);
		if (category == null) {
			throw new DataNotFoundException(ERROR_MESSAGE_CATEGORY_NOT_FOUND + categoryName);
		}
		List<Products> products = productsRepository.findByCategoryAndBidStartTimeLessThanEqualAndBidEndTimeGreaterThanEqual(category,
				LocalDateTime.now(), LocalDateTime.now());
		log.debug("Products details - {}", products);
		return products.stream().map(this::convertToProductVO).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void updateProduct(Long productId, @Valid ProductUpdateRequestVO productDetails) {
		Products existingProduct = productsRepository.findById(productId)
				.orElseThrow(() -> new DataNotFoundException(ERROR_MESSAGE_PRODUCT_NOT_FOUND + productId));

		// Update only the fields that can be modified
		existingProduct.setName(productDetails.getName());
		existingProduct.setBasePrice(productDetails.getBasePrice());

		existingProduct.setBidEndTime(existingProduct.getBidEndTime());
		existingProduct.setBidStartTime(existingProduct.getBidStartTime());
		existingProduct.setListingDate(existingProduct.getListingDate());
		log.debug("Updated Product - {}", existingProduct);
		productsRepository.save(existingProduct);
		log.info("Product successfully updated - {}", existingProduct);
	}

	@Override
	@Transactional
	public void deleteProduct(Long productId) {
		Products existingProduct = productsRepository.findById(productId)
				.orElseThrow(() -> new DataNotFoundException(ERROR_MESSAGE_PRODUCT_NOT_FOUND + productId));

		// Find all related bids for the product
		List<Bids> productBids = bidRepository.findByProductId(existingProduct.getId());
		log.debug("Bids related to the product - {}", productBids);
		if (productBids != null) {
			bidRepository.deleteAll(productBids);
		}
		// Find the related auction for the product (if exists) and delete it
		Auctions productAuction = auctionRepository.findByProductId(existingProduct.getId());
		log.debug("Auctions related to the product - {}", productAuction);
		if (productAuction != null) {
			auctionRepository.delete(productAuction);
		}

		// Delete the product
		productsRepository.delete(existingProduct);
		log.info("Product successfully deleted - {}", existingProduct);
	}

	@Override
	public ProductVO getProductById(Long productId) {
		Products product = productsRepository.findById(productId)
				.orElseThrow(() -> new DataNotFoundException(ERROR_MESSAGE_PRODUCT_NOT_FOUND + productId));
		log.debug("product - {}", product);
		return convertToProductVO(product);
	}

	@Override
	public List<ProductVO> getAllProducts() {
		List<Products> products = productsRepository.findAll();
		log.debug("products - {}", products);
		return products.stream().map(this::convertToProductVO).collect(Collectors.toList());
	}

	private ProductVO convertToProductVO(Products product) {

		ProductVO productVO = ProductVO.builder().id(product.getId()).name(product.getName()).basePrice(product.getBasePrice().toString())
				.bidEndTime(product.getBidEndTime()).bidStartTime(product.getBidStartTime()).listingDate(product.getListingDate())
				.status(product.getStatus().name()).build();
		if (product.getUser() != null) {
			productVO.setVendor(product.getUser().getName());
		}
		if (product.getCategory() != null) {
			productVO.setCategory(product.getCategory().getName());
		}
		return productVO;
	}

}
