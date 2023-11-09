package com.manushi.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manushi.user.repository.entity.Vendors;

public interface VendorRepository extends JpaRepository<Vendors, Long> {

	Vendors findByVendorName(String vendorName);

}
