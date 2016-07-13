package com.clienteservidor.preciosclaros.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.constraints.EAN;
import org.hibernate.validator.constraints.EAN.Type;

import com.clienteservidor.preciosclaros.serialization.EntityToIdSerializer;
import com.clienteservidor.preciosclaros.serialization.IdToCategoryDeserializer;
import com.clienteservidor.preciosclaros.serialization.IdToUnitDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@Entity
@Indexed
public class Product extends GenericEntity {
	
	@NotNull
	@Column(unique=true)
	@EAN(type=Type.EAN13)
	private String ean13;

	@NotNull
	@Size(min=2, max=100)
	@Field
	private String description;

	@NotNull
	@Size(min=2, max=50)
	@Field
	private String brand;

	@NotNull
	private double number;

	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonSerialize(using = EntityToIdSerializer.class)
	@JsonDeserialize(using = IdToUnitDeserializer.class)
	private Unit unit;

	@ManyToOne(fetch=FetchType.LAZY)
	@JsonSerialize(using = EntityToIdSerializer.class)
	@JsonDeserialize(using = IdToCategoryDeserializer.class)
	private Category category;
	
	@Transient
	private Double price;
	
	@Transient
	private Set<String> promotions;

	public Product(){}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getNumber() {
		return number;
	}

	public void setNumber(double number) {
		this.number = number;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public String getEan13() {
		return ean13;
	}

	public void setEan13(String ean13) {
		this.ean13 = ean13;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Set<String> getPromotions() {
		return promotions;
	}

	public void setPromotions(Set<String> promotions) {
		this.promotions = promotions;
	}
}
