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
import com.clienteservidor.preciosclaros.serialization.IdToCompanyDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Indexed
public class Firm extends GenericEntity {

	@NotNull
	@Size(min=2, max=100)
	@Field
	private String name;

	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonSerialize(using = EntityToIdSerializer.class)
	@JsonDeserialize(using = IdToCompanyDeserializer.class)
	private Company company;
	
	@OneToMany(mappedBy="firm", fetch=FetchType.LAZY)
	@JsonIgnore
	private Collection<Branch> branches;

	public Firm(){}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Collection<Branch> getBranches() {
		return branches;
	}

	public void setBranches(Collection<Branch> branches) {
		this.branches = branches;
	}

}
