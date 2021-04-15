package model;

import java.io.Serializable;

public class Borrower implements Serializable {

	private static final long serialVersionUID = 1L;
	
	int id;
	String name;
	String contact;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}

	
}
