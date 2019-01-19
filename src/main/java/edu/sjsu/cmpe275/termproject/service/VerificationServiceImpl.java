package edu.sjsu.cmpe275.termproject.service;
import edu.sjsu.cmpe275.termproject.service.VerificationService;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class VerificationServiceImpl implements VerificationService{

	public static final long start = 10000;
	public static final long end = 99999;
	
	@Override
	public int generateRandomNumber() {
		int randomNumber;
		System.out.println("I tried");
		randomNumber = randomInteger(10000,99999);
		System.out.println(randomNumber);
		return randomNumber;
	}

	@Override
	public void userVerified() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resendVerificationCode() {
		// TODO Auto-generated method stub
		
	}
	
	public int randomInteger(int min, int max) {

	    Random rand = new Random();

	    // nextInt excludes the top value so we have to add 1 to include the top value
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	
}
