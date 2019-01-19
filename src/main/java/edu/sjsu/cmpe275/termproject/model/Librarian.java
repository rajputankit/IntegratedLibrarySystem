package edu.sjsu.cmpe275.termproject.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Librarian {

	@Id
	private String sjsuEmail;
	private String sjsuId;
	private String password;
	private String firstName;
	private String lastName;
	private String verificationCode;
	private Integer verified;
	
	
	public String getSjsuEmail() {
		return sjsuEmail;
	}
	public void setSjsuEmail(String sjsuEmail) {
		this.sjsuEmail = sjsuEmail;
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
	
	
}
