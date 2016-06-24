package com.clienteservidor.preciosclaros.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
public class Province extends GenericEntity {

	@NotNull
	@Column(unique=true)
	@Size(min=2, max=30)
	@Field
	private String name;

	@JsonIgnore
	@OneToMany(mappedBy="province", fetch=FetchType.LAZY)
	private Collection<Department> departments;
	
	public Province(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Collection<Department> departments) {
		this.departments = departments;
	}

}
