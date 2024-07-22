package com.lenah.GestaoProjetos.model;

public enum UserRole {
	ADMIN("ADMIN"),
	USER("User");
	
	private String role;
	
	UserRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
}
