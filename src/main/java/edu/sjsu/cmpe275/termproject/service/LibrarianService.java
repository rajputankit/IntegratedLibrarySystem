package edu.sjsu.cmpe275.termproject.service;

import java.util.List;

import edu.sjsu.cmpe275.termproject.model.Librarian;

public interface LibrarianService {

	Librarian signUpLibrarian(Librarian librarian) throws Exception;
	Librarian signInLibrarian(String email, String password) throws Exception;
	Librarian getLibrarianByEmail(String email) throws Exception;
	List<Librarian> getAllLibrarians() throws Exception;
	void setLibrarianVerified(String code, String librarianEmail) throws Exception;
}
