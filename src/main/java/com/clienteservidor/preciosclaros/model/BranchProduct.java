package com.clienteservidor.preciosclaros.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
	@ManyToOne(fetch=FetchType.EAGER)
	@JsonIgnore
	private Branch branch;

	@ElementCollection
	@CollectionTable(name="Promotion", joinColumns=@JoinColumn(name="branchProduct_id"))
	@Column(name="description")
	private Set<String> promotions = new HashSet<String>();
	
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
	@JsonSerialize(using = ProductToEanSerializer.class)
	@JsonDeserialize(using = eanToProductDeserializer.class)
	private Product product;

	@NotNull
	private double price;
	
	@NotNull
	@JsonIgnore
	private boolean expired;

	@JsonIgnore
	public Integer getId() {
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

	public Set<String> getPromotions() {
		return promotions;
	}

	public void setPromotions(Set<String> promotions) {
		this.promotions = promotions;
	}
}
