package com.clienteservidor.preciosclaros.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Indexed
public class Company extends GenericEntity {
	
	@NotNull
	@Size(min=11, max=11)
	private String cuit;

	@NotNull
	@Size(min=2, max=100)
	@Field
	private String name;
	
	@OneToMany(mappedBy="company", fetch=FetchType.LAZY)
	@JsonIgnore
	private Collection<Firm> firms;
	
	public Company(){}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Firm> getFirms() {
		return firms;
	}

	public void setFirms(Collection<Firm> firms) {
		this.firms = firms;
	}

}
