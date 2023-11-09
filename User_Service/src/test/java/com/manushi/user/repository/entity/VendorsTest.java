package com.manushi.user.repository.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class VendorsTest {

	@Test
	public void testGettersAndSetters() {
		Vendors vendor = new Vendors();
		vendor.setVendorId(1L);
		vendor.setVendorName("Test Vendor");

		assertEquals(1L, vendor.getVendorId());
		assertEquals("Test Vendor", vendor.getVendorName());
	}
}
