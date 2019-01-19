package edu.sjsu.cmpe275.termproject.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.termproject.DAO.DAO;
import edu.sjsu.cmpe275.termproject.exceptions.BadUserRequestException;
import edu.sjsu.cmpe275.termproject.exceptions.ExistingEmailException;
import edu.sjsu.cmpe275.termproject.exceptions.ExistingSjsuIdException;
import edu.sjsu.cmpe275.termproject.exceptions.UserNotFoundException;
import edu.sjsu.cmpe275.termproject.exceptions.UserNotVerifiedException;
import edu.sjsu.cmpe275.termproject.model.Patron;

@Service
public class PatronServiceImpl implements PatronService {

	@Autowired
	DAO dao;
	@Autowired
	VerificationService verificationService;
	@Autowired
	MailService mailService;

	@Override
	public Patron signUpPatron(Patron patron) throws Exception {

		Patron temp = dao.getPatronByEmail(patron.getEmail());
		if (temp != null) throw new ExistingEmailException(patron.getEmail() + " email is unavailable");
		temp = dao.getPatronBySjsuId(patron.getSjsuId());
		if (temp != null) throw new ExistingSjsuIdException(patron.getSjsuId() + " SJSU ID already exists");
		
		patron.setVerified(0);
		patron.setCurrentlyCheckoutBooks(0);
		patron.setLastCheckoutBookCount(0);
		patron.setLastCheckoutDate(new Date());
		patron.setVerificationCode(String.valueOf(verificationService.generateRandomNumber()));
		mailService.sendVerificationCode(patron);
		dao.createPatron(patron);
		return patron;
	}

	@Override
	public Patron signInPatron(String email, String password) throws Exception {

		Patron patron = dao.getPatronByEmail(email);
		if (patron == null) throw new UserNotFoundException(email + " does not exist");
		if (!password.equals(patron.getPassword())) throw new BadUserRequestException("Email and Password Don't Match");
		if(patron.getVerified() == 0) throw new UserNotVerifiedException(patron.getEmail() + " user not verified. Please check you email for verification code");
			
		System.out.println("PATRON FOUND");
		System.out.println("returning " + patron.getEmail());
		return patron;
	}

	@Override
	public Patron getPatronByEmail(String email) throws Exception {

		Patron patron = dao.getPatronByEmail(email);
		if (patron == null)
			throw new UserNotFoundException("");

		return patron;
	}

	@Override
	public void setPatronVerified(String code, String patronEmail) throws Exception {
		
		Patron patron = dao.getPatronByEmail(patronEmail);
		
		if(code.trim().equals(patron.getVerificationCode().trim())){
			dao.setPatronVerifiedFlag(patronEmail);
			mailService.sendRegistrationConfirmation(patronEmail);
			System.out.println("Verified the user!");
		}
		else{
			throw new BadUserRequestException("Verification Code Does not Match");
		}
	}
}
















