package com.manushi.product.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;

import com.manushi.product.repository.datetime.convertor.LocalDateTimeConverter;
import com.manushi.product.repository.enums.ProductStatus;
import com.manushi.product.repository.enums.convertor.ProductStatusEnumConvertor;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@Column(name = "product_id", nullable = false)
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private Users user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", referencedColumnName = "category_id")
	@NotNull
	private Category category;

	@NotNull
	@Column(name = "name")
	private String name;

	@NotNull
	@Column(name = "base_price")
	private BigDecimal basePrice;

	@NotNull
	@Convert(converter = LocalDateTimeConverter.class)
	@Column(name = "bid_start_time")
	private LocalDateTime bidStartTime;

	@NotNull
	@Convert(converter = LocalDateTimeConverter.class)
	@Column(name = "bid_end_time")
	private LocalDateTime bidEndTime;

	@NotNull
	@Convert(converter = ProductStatusEnumConvertor.class)
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ProductStatus status;

	@NotNull
	@Convert(converter = LocalDateTimeConverter.class)
	@Column(name = "listing_date")
	private LocalDateTime listingDate;

}
