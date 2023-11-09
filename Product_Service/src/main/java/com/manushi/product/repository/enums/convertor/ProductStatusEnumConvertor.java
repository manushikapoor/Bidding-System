package com.manushi.product.repository.enums.convertor;

import com.manushi.product.repository.enums.ProductStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ProductStatusEnumConvertor implements AttributeConverter<ProductStatus, String> {

	@Override
	public String convertToDatabaseColumn(ProductStatus attribute) {
		if (attribute == null) {
			return null;
		}
		return attribute.toString().toLowerCase();
	}

	@Override
	public ProductStatus convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}
		return ProductStatus.valueOf(dbData.toUpperCase());
	}

	public ProductStatus convertStringToEnum(String status) {
		if (status == null) {
			return null;
		}
		return ProductStatus.valueOf(status.toUpperCase());
	}

}
