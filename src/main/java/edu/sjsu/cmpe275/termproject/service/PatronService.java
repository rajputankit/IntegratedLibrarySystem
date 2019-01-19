package edu.sjsu.cmpe275.termproject.service;

import edu.sjsu.cmpe275.termproject.model.Patron;

public interface PatronService {

	Patron signUpPatron(Patron patron) throws Exception;
	Patron signInPatron(String email, String password) throws Exception;
	Patron getPatronByEmail(String email) throws Exception;
	void setPatronVerified(String code, String patronEmail) throws Exception;

}
