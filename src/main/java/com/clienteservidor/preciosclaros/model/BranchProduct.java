package com.clienteservidor.preciosclaros.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class BranchProduct extends GenericEntity {

	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	private Branch branch;

	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	private Product product;

	@NotNull
	private double price;
	
	@NotNull
	private boolean expired;

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}
}
