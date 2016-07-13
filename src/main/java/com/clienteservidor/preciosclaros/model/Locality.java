package com.clienteservidor.preciosclaros.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.clienteservidor.preciosclaros.serialization.EntityToIdSerializer;
import com.clienteservidor.preciosclaros.serialization.IdToDepartmentDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Indexed
public class Locality extends GenericEntity {

	@NotNull
	@Size(min=2, max=100)
	@Field
	private String name;

	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
	@JsonSerialize(using = EntityToIdSerializer.class)
	@JsonDeserialize(using = IdToDepartmentDeserializer.class)
	private Department department;

	public Locality(){}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
