package com.clienteservidor.preciosclaros.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.clienteservidor.preciosclaros.serialization.ProductToEanSerializer;
import com.clienteservidor.preciosclaros.serialization.eanToProductDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
public class BranchProduct extends GenericEntity {

	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private Branch branch;

	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonSerialize(using = ProductToEanSerializer.class)
	@JsonDeserialize(using = eanToProductDeserializer.class)
	private Product product;

	@NotNull
	private double price;
	
	@NotNull
	@JsonIgnore
	private boolean expired;

	@JsonIgnore
	public int getId() {
		return id;
	}
	
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
