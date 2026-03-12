package com.cognizant.entities.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingItem {

	public BillingItem(String service, Double price2) {
		this.serviceName = service;
		this.price = price2;
		// TODO Auto-generated constructor stub
	}

	private String serviceName;

	private double price;
}
