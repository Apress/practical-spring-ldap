package com.inflinx.book.ldap.domain;

public class Patron {
	
	private String id;
	private String lastName;
	private String cn;
	private String email;
	
	public String getUserId() {
		return id;
	}
	public void setUserId(String userId) {
		this.id = userId;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getCn() {
		return cn;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
