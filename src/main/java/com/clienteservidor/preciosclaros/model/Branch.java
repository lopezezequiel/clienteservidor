package com.clienteservidor.preciosclaros.model;

import java.util.Collection;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import com.clienteservidor.preciosclaros.serialization.EntityToIdSerializer;
import com.clienteservidor.preciosclaros.serialization.IdToFirmDeserializer;
import com.clienteservidor.preciosclaros.serialization.IdToLocalityDeserializer;
import com.clienteservidor.preciosclaros.serialization.LocationSerializer;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@Entity
public class Branch extends GenericEntity {

	@ManyToOne(fetch=FetchType.LAZY)
	@JsonSerialize(using = EntityToIdSerializer.class)
	@JsonDeserialize(using = IdToFirmDeserializer.class)
	private Firm firm;


	@Size(min=2, max=100)
	private String address;


	@JsonIgnore
	private Location location = new Location();

	@ManyToOne(fetch=FetchType.LAZY)
	@JsonSerialize(using = EntityToIdSerializer.class)
	@JsonDeserialize(using = IdToLocalityDeserializer.class)
	private Locality locality;

	@OneToMany(mappedBy="branch", fetch=FetchType.LAZY)
	@JsonIgnore
	private Collection<BranchProduct> products;

	public Collection<BranchProduct> getProducts() {
		return products;
	}

	public void setProducts(Collection<BranchProduct> products) {
		this.products = products;
	}

	public Branch(){}
	
	public Firm getFirm() {
		return firm;
	}

	public void setFirm(Firm firm) {
		this.firm = firm;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@JsonGetter("latitude")
	public double getLatitude() {
		return this.getLocation().getLatitude();
	}

	@JsonGetter("longitude")
	public double getLongitude() {
		return this.getLocation().getLatitude();
	}

	@JsonSetter("latitude")
	public void setLatitude(double latitude) {
		this.location.setLatitude(latitude);;
	}

	@JsonSetter("longitude")
	public void setLongitude(double longitude) {
		this.location.setLongitude(longitude);;
	}

	public Locality getLocality() {
		return locality;
	}

	public void setLocality(Locality locality) {
		this.locality = locality;
	}

}