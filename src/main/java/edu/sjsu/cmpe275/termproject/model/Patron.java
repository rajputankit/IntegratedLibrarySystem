package edu.sjsu.cmpe275.termproject.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Patron {

	@Id
	private String email;
	private String sjsuId;
	private String password;	
	private String firstName;
	private String lastName;
	private String verificationCode;
	private Integer verified;
	private Integer totalFine;
	

	private Date lastCheckoutDate;
	private Integer lastCheckoutBookCount;
	private Integer currentlyCheckoutBooks;
		
	@OneToMany(mappedBy="checkoutPatron", fetch=FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
	private List<BookCheckout> bookCheckouts;
	
	
	@ManyToMany(mappedBy="waitList", cascade = {CascadeType.ALL})	
	@LazyCollection(LazyCollectionOption.FALSE)	
	private List<Book> waitListedBooks;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patron other = (Patron) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	public List<Book> getWaitListedBooks() {
		return waitListedBooks;
	}

	public void setWaitListedBooks(List<Book> waitListedBooks) {
		this.waitListedBooks = waitListedBooks;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSjsuId() {
		return sjsuId;
	}

	public void setSjsuId(String sjsuId) {
		this.sjsuId = sjsuId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Integer getVerified() {
		return verified;
	}

	public void setVerified(Integer verified) {
		this.verified = verified;
	}

	public Date getLastCheckoutDate() {
		return lastCheckoutDate;
	}

	public void setLastCheckoutDate(Date lastCheckoutDate) {
		this.lastCheckoutDate = lastCheckoutDate;
	}

	public Integer getLastCheckoutBookCount() {
		return lastCheckoutBookCount;
	}

	public void setLastCheckoutBookCount(Integer lastCheckoutBookCount) {
		this.lastCheckoutBookCount = lastCheckoutBookCount;
	}

	public Integer getCurrentlyCheckoutBooks() {
		return currentlyCheckoutBooks;
	}

	public void setCurrentlyCheckoutBooks(Integer currentCheckoutBooks) {
		this.currentlyCheckoutBooks = currentCheckoutBooks;
	}

	public List<BookCheckout> getBookCheckouts() {
		return bookCheckouts;
	}

	public void setBookCheckouts(List<BookCheckout> bookCheckouts) {
		this.bookCheckouts = bookCheckouts;	
	}
	public Integer getTotalFine() {
		return totalFine;
	}

	public void setTotalFine(Integer totalFine) {
		this.totalFine = totalFine;
	}
	
	
	
}
