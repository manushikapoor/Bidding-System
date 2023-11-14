package com.manushi.product.service.categories;

import static com.manushi.product.util.Constants.ERROR_MESSAGE_CATEGORY_NOT_FOUND;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manushi.product.exceptions.DataNotFoundException;
import com.manushi.product.model.request.CategoryRequestVO;
import com.manushi.product.model.response.CategoryVO;
import com.manushi.product.repository.AuctionRepository;
import com.manushi.product.repository.BidsRepository;
import com.manushi.product.repository.CategoryRepository;
import com.manushi.product.repository.ProductsRepository;
import com.manushi.product.repository.entity.Auctions;
import com.manushi.product.repository.entity.Bids;
import com.manushi.product.repository.entity.Category;
import com.manushi.product.repository.entity.Products;
import com.manushi.product.util.RequestValidator;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private final CategoryRepository categoryRepository;

	@Autowired
	private final ProductsRepository productsRepository;

	@Autowired
	private final AuctionRepository auctionRepository;

	@Autowired
	private final BidsRepository bidRepository;

	@Autowired
	private final RequestValidator requestValidator;

	@Override
	public List<CategoryVO> getAllCategories() {
		List<CategoryVO> categoryVOs = categoryRepository.findAll().stream().map(this::convertToCategoryVO).collect(Collectors.toList());
		log.debug("categories - {}", categoryVOs);
		return categoryVOs;
	}

	@Override
	@Transactional
	public void createCategory(@Valid CategoryRequestVO categoryDetails) {
		requestValidator.validateCategoryRequestVO(categoryDetails);
		Category category = Category.builder().name(categoryDetails.getName()).build();

		categoryRepository.save(category);
		log.info("category successfully created - {}", category);
	}

	@Override
	@Transactional
	public void updateCategory(Long categoryId, @Valid CategoryRequestVO categoryDetails) {
		Category existingCategory = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new DataNotFoundException(ERROR_MESSAGE_CATEGORY_NOT_FOUND + categoryId));

		existingCategory.setName(categoryDetails.getName());
		categoryRepository.save(existingCategory);
		log.info("category successfully updated - {}", existingCategory);
	}

	@Override
	@Transactional
	public void deleteCategory(Long categoryId) {
		Category existingCategory = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new DataNotFoundException(ERROR_MESSAGE_CATEGORY_NOT_FOUND + categoryId));
		// Find all products with the category
		List<Products> productsWithCategory = productsRepository.findProductsByCategory(existingCategory);
		log.debug("Products with category to be deleted - {}", productsWithCategory);
		for (Products product : productsWithCategory) {
			// Find all related bids for the product
			List<Bids> productBids = bidRepository.findByProductId(product.getId());
			log.debug("Bids under category to be deleted - {}", productBids);
			if (productBids != null) {
				bidRepository.deleteAll(productBids);
			}
			// Find the related auction for the product (if exists) and delete it
			Auctions productAuction = auctionRepository.findByProductId(product.getId());
			log.debug("Auctions under category to be deleted - {}", productAuction);
			if (productAuction != null) {
				auctionRepository.delete(productAuction);
			}

			// Delete the product
			productsRepository.delete(product);
		}

		// Delete the category
		categoryRepository.delete(existingCategory);
		log.info("Category successfully deleted - {}", existingCategory);
	}

	private CategoryVO convertToCategoryVO(Category category) {

		return CategoryVO.builder().id(category.getCategoryId()).name(category.getName()).build();

	}
}
