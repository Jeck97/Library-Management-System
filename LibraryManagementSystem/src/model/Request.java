package model;

import java.io.Serializable;

public class Request implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private char type;
	private int number;
	private Book book;
	private Borrower borrower;
	private String temperaryId;
	
	public Request(char type, int number, Borrower borrower, Book book) {
		
		this.type = type;
		this.number = number;
		this.borrower = borrower;
		this.book = book;
		this.temperaryId = "Click to select books";

	}

	public char getType() {
		return type;
	}

	public int getNumber() {
		return number;
	}

	public Book getBook() {
		return book;
	}

	public Borrower getBorrower() {
		return borrower;
	}
	
	public String getTemperaryId() {
		return temperaryId;
	}
	
	public void setTemperaryId(String temperaryId) {
		this.temperaryId = temperaryId;
	}
	
	public String getRequestNo() {
		return type + " " + number;
	}
	
}
