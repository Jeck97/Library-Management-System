package model;

import java.io.Serializable;
import java.sql.Date;

public class Book implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	int bookId;
	String bookName;
	String author;
	String synopsis;
	Date datePublished;
	String genre;
	double loanRate;
	boolean availability;
	
	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public int getBookId() {
		return bookId;
	}
	
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	
	public String getBookName() {
		return bookName;
	}
	
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public double getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(double loanRate) {
		this.loanRate = loanRate;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	
}