package com.clienteservidor.preciosclaros.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User extends GenericEntity {

	@NotNull
	@Column(unique=true)
	@Email
	private String mail;

	@NotNull
	@Column(unique=true)
	private String name;

	@NotNull
	private String password;

	@NotNull
	@JsonIgnore
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean enabled;
	
	@ElementCollection
	@CollectionTable(name="UserRole", joinColumns=@JoinColumn(name="user_id"))
	@Column(name="role")
	@NotNull
	private Set<String> userRoles = new HashSet<String>();

	@ElementCollection
	@CollectionTable(name="Cart", joinColumns=@JoinColumn(name="user_id"))
	@Column(name="product")
	@JsonIgnore
	@NotNull
	private Set<Product> cart = new HashSet<Product>();

	@JsonIgnore
	private String hashCode;
	
	public User() {}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<String> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<String> userRoles) {
		this.userRoles = userRoles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Product> getCart() {
		return cart;
	}

	public void setCart(Set<Product> cart) {
		this.cart = cart;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}
}

