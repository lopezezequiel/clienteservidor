package com.clienteservidor.preciosclaros.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
public class Unit extends GenericEntity {

	@NotNull
	@Column(unique=true)
	@Size(min=1, max=10)
	private String symbol;

	@NotNull
	@Size(min=1, max=20)
	@Field
	private String singular;

	@NotNull
	@Size(min=1, max=20)
	@Field
	private String plural;

	public Unit(){}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public String getSingular() {
		return singular;
	}

	public void setSingular(String singular) {
		this.singular = singular;
	}

	public String getPlural() {
		return plural;
	}

	public void setPlural(String plural) {
		this.plural = plural;
	}

}
