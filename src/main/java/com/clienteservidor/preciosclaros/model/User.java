package com.clienteservidor.preciosclaros.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User extends GenericEntity {

	@NotNull
	@Column(unique=true)
	private String mail;

	@NotNull
	private String password;

	@NotNull
	@JsonIgnore
	private boolean enabled;
	
	@ElementCollection
	@CollectionTable(name="UserRole", joinColumns=@JoinColumn(name="user_id"))
	@Column(name="role")
	private Set<String> userRoles;

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

	@JsonIgnore
	public Set<String> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<String> userRoles) {
		this.userRoles = userRoles;
	}	

}

