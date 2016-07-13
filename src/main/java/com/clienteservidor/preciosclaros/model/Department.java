package com.clienteservidor.preciosclaros.model;


import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.clienteservidor.preciosclaros.serialization.EntityToIdSerializer;
import com.clienteservidor.preciosclaros.serialization.IdToProvinceDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@Entity
@Indexed
public class Department extends GenericEntity {
	@NotNull
	@Size(min=2, max=30)
	@Field
	private String name;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonSerialize(using = EntityToIdSerializer.class)
	@JsonDeserialize(using = IdToProvinceDeserializer.class)
	private Province province;
	
	@OneToMany(mappedBy="department", fetch=FetchType.LAZY)
	@NotNull
	@JsonIgnore
	private Collection<Locality> localities;
	
	public Department(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public Collection<Locality> getLocalities() {
		return localities;
	}

	public void setLocalities(Collection<Locality> localities) {
		this.localities = localities;
	}
	
	
	
}