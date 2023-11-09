package com.manushi.user.repository.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "roles", schema = "bidding system")
public class Roles {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@Column(name = "role_id", nullable = false)
	@GenericGenerator(name = "native", strategy = "native")
	private Long roleId;

	@NotNull
	@Column(name = "role_name")
	private String roleName;

	@Column(name = "description")
	private String description;

}
