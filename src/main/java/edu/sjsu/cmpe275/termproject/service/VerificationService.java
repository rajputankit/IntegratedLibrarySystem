package edu.sjsu.cmpe275.termproject.service;

public interface VerificationService {

	int generateRandomNumber();
	void userVerified();
	void resendVerificationCode();
}
