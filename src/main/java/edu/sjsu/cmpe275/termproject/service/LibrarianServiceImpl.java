package edu.sjsu.cmpe275.termproject.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.termproject.DAO.DAO;
import edu.sjsu.cmpe275.termproject.exceptions.BadUserRequestException;
import edu.sjsu.cmpe275.termproject.exceptions.ExistingEmailException;
import edu.sjsu.cmpe275.termproject.exceptions.ExistingSjsuIdException;
import edu.sjsu.cmpe275.termproject.exceptions.UserNotFoundException;
import edu.sjsu.cmpe275.termproject.exceptions.UserNotVerifiedException;
import edu.sjsu.cmpe275.termproject.model.Librarian;
import edu.sjsu.cmpe275.termproject.model.Patron;

@Service
public class LibrarianServiceImpl implements LibrarianService{

	@Autowired	
	DAO dao;
	@Autowired
	VerificationService verificationService;
	@Autowired
	MailService mailService;	
	
	@Override
	public Librarian signUpLibrarian(Librarian librarian) throws Exception {
		
		Librarian temp = dao.getLibrarianByEmail(librarian.getSjsuEmail());
		if(temp != null) throw new ExistingEmailException("");
		
		temp = dao.getLibrarianBySjsuId(librarian.getSjsuId());
		if(temp != null) throw new ExistingSjsuIdException("");
		
		librarian.setVerified(0);		
		librarian.setVerificationCode(String.valueOf(verificationService.generateRandomNumber()));
		mailService.sendLibrarianVerificationCode(librarian);
		
		
		dao.createLibrarian(librarian);
		return librarian;
	}

	@Override
	public Librarian signInLibrarian(String email, String password) throws Exception {
			
		Librarian librarian = dao.getLibrarianByEmail(email);
		if(librarian == null) throw new UserNotFoundException(email + " does not exist");		
		if(!password.equals(librarian.getPassword()))	throw new BadUserRequestException("Email and Password Don't Match");
		if(librarian.getVerified() == 0)	throw new UserNotVerifiedException(librarian.getSjsuEmail() + " user not verified. Please check you email for verification code");
		
		System.out.println("DAO: FINDING LIBRARIAN");
		System.out.println("returning " + librarian.getSjsuEmail());		
		return librarian;
	}

	@Override
	public Librarian getLibrarianByEmail(String email) throws Exception {
		
		Librarian librarian = dao.getLibrarianByEmail(email);
		if(librarian == null) throw new UserNotFoundException("");
		
		return librarian;
	}

	@Override
	public List<Librarian> getAllLibrarians() throws Exception {
		
		List<Librarian> librarians = dao.getAllLibrarians();
		return librarians;
	}
	
	
	@Override
	public void setLibrarianVerified(String code, String librarianEmail) throws Exception {
		
		Librarian librarian = dao.getLibrarianByEmail(librarianEmail);
		
		if(code.trim().equals(librarian.getVerificationCode().trim())){
			dao.setLibrarianVerifiedFlag(librarianEmail);
			mailService.sendRegistrationConfirmation(librarianEmail);
			System.out.println("Verified the user!");
		}	
		else{
			throw new BadUserRequestException("Verification Code Does not Match");
		}
	}
}



















