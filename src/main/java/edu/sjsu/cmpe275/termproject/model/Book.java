package edu.sjsu.cmpe275.termproject.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@NamedQuery(name = "Book.findBookByLibrarian", query = "select b from Book b where b.addedBy like ?1")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer bookId;
	private String ISBN;
	private String author;
	private String title;
	private String callNumber;
	private String publisher;
	private String yearOfPublication;
	private String location;
	private Integer numberOfCopies;
	private Integer currentNumberOfCopies;
	private Integer currentStatus; 							// 0 - unavailable 1- available 2-reserved
	private String addedBy;
	private String updatedBy;
	private String[] keywordArray;
	
	
	@JsonIgnore	
	@OneToMany(mappedBy = "checkoutBook", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	private List<BookCheckout> bookCheckouts;

	
	@JsonIgnore
	@ManyToMany(cascade = {CascadeType.ALL})		
	@JoinTable(name="Book_Keyword", joinColumns = {@JoinColumn(name = "bookId", referencedColumnName = "bookId")}, 
									inverseJoinColumns = {@JoinColumn(name = "keywordId", referencedColumnName = "keywordId")})
	@LazyCollection(LazyCollectionOption.FALSE)		
	private Set<Keyword> keywordList;	
	
	
	@JsonIgnore	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name="Book_Waitlist", joinColumns = {@JoinColumn(name = "bookId", referencedColumnName = "bookId") }, 
									inverseJoinColumns = {@JoinColumn(name = "patronEmail", referencedColumnName = "email") })
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderColumn
	private List<Patron> waitList;							
		
	
	@OneToOne(cascade=CascadeType.REMOVE, orphanRemoval=true)
	private Patron reservedForPatron;
	private Date reservedUntil;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
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
		Book other = (Book) obj;
		if (bookId == null) {
			if (other.bookId != null)
				return false;
		} else if (!bookId.equals(other.bookId))
			return false;
		return true;
	}

	public Set<Keyword> getKeywordList() {
		return keywordList;
	}

	public void setKeywordList(Set<Keyword> keywordList) {
		this.keywordList = keywordList;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getYearOfPublication() {
		return yearOfPublication;
	}

	public void setYearOfPublication(String yearOfPublication) {
		this.yearOfPublication = yearOfPublication;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getNumberOfCopies() {
		return numberOfCopies;
	}

	public void setNumberOfCopies(Integer numberOfCopies) {
		this.numberOfCopies = numberOfCopies;
	}

	public Integer getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Integer currentStatus) {
		this.currentStatus = currentStatus;
	}

	public List<BookCheckout> getBookCheckouts() {
		return bookCheckouts;
	}

	public void setBookCheckouts(List<BookCheckout> bookCheckouts) {
		this.bookCheckouts = bookCheckouts;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public Integer getCurrentNumberOfCopies() {
		return currentNumberOfCopies;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setCurrentNumberOfCopies(Integer currentNumberOfCopies) {
		this.currentNumberOfCopies = currentNumberOfCopies;
	}

	public String[] getKeywordArray() {
		return keywordArray;
	}

	public void setKeywordArray(String[] keywordArray) {
		this.keywordArray = keywordArray;
	}

	public List<Patron> getWaitList() {
		return waitList;
	}

	public void setWaitList(List<Patron> waitList) {	
		this.waitList = waitList;
	}

	public Patron getReservedForPatron() {
		return reservedForPatron;
	}

	public void setReservedForPatron(Patron reservedForPatron) {
		this.reservedForPatron = reservedForPatron;
	}

	public Date getReservedUntil() {
		return reservedUntil;
	}

	public void setReservedUntil(Date reservedUntil) {
		this.reservedUntil = reservedUntil;
	}
}









