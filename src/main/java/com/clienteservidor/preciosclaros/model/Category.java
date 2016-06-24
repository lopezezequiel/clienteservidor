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
import com.clienteservidor.preciosclaros.serialization.IdToCategoryDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Indexed
public class Category extends GenericEntity {

	@NotNull
	@Size(min=2, max=50)
	@Field
	private String name;

	@ManyToOne(fetch=FetchType.LAZY)
	@JsonSerialize(using = EntityToIdSerializer.class)
	@JsonDeserialize(using = IdToCategoryDeserializer.class)
	private Category parent;

	@OneToMany(mappedBy="parent", fetch=FetchType.LAZY)
	@JsonIgnore
	private Collection<Category> childs;

	private String image;

	public Category(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Collection<Category> getChilds() {
		return childs;
	}

	public void setChilds(Collection<Category> childs) {
		this.childs = childs;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}