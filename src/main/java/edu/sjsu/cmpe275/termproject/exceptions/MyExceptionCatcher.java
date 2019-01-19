package edu.sjsu.cmpe275.termproject.exceptions;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class MyExceptionCatcher {


	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView noHandlerFound(){

		System.out.println("MY EXCEPTION: Client Requested for a UnAvailable Resource");
		ModelAndView mv = new ModelAndView("errorpage");
		return mv;	
	}		


	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadUserRequestException.class)
	public ResponseEntity<HashMap<String, String>> badUserRequest(BadUserRequestException ex){	

		System.out.println("MY EXCEPTIONN: BAD USER REQUEST " + ex.getExceptionReason());

		HashMap<String, String> errorResponse = new HashMap<String, String>();
		errorResponse.put("errorMessage", ex.getExceptionReason());
		return new ResponseEntity<HashMap<String, String>>(errorResponse, HttpStatus.BAD_REQUEST);	
	}	


	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ExistingEmailException.class)
	public ResponseEntity<HashMap<String, String>> existingEmail(ExistingEmailException ex){

		System.out.println("MY EXCEPTION: EXISTING EMAIL");		

		HashMap<String, String> errorResponse = new HashMap<String, String>();
		errorResponse.put("errorMessage", ex.getExceptionReason());

		return new ResponseEntity<HashMap<String, String>>(errorResponse, HttpStatus.BAD_REQUEST);	

	}


	@ResponseStatus(value=HttpStatus.BAD_REQUEST)	
	@ExceptionHandler(ExistingSjsuIdException.class)
	public ResponseEntity<HashMap<String, String>> existingSjsuId(ExistingSjsuIdException ex){

		System.out.println("MY EXCEPTION: EXISTING SJSU ID");	
		
		HashMap<String, String> errorResponse = new HashMap<String, String>();
		errorResponse.put("errorMessage", ex.getExceptionReason());

		return new ResponseEntity<HashMap<String, String>>(errorResponse, HttpStatus.BAD_REQUEST);	

	}


	@ResponseStatus(value=HttpStatus.NOT_FOUND)	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<HashMap<String,String>> userNotFound(UserNotFoundException ex){	

		System.out.println("MY EXCEPTION1: USER NOT FOUND");		

		HashMap<String, String> errorResponse = new HashMap<String, String>();
		errorResponse.put("errorMessage", ex.getExceptionReason());

		return new ResponseEntity<HashMap<String, String>>(errorResponse, HttpStatus.NOT_FOUND);	
	}



	@ResponseStatus(value=HttpStatus.NOT_FOUND)	
	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<HashMap<String,String>> bookNotFound(BookNotFoundException ex){	

		System.out.println("MY EXCEPTION: BOOK NOT FOUND");			

		HashMap<String, String> errorResponse = new HashMap<String, String>();
		errorResponse.put("errorMessage", ex.getExceptionReason());

		return new ResponseEntity<HashMap<String, String>>(errorResponse, HttpStatus.NOT_FOUND);	
	}

	@ResponseStatus(value=HttpStatus.BAD_REQUEST)	
	@ExceptionHandler(ExistingBookISBN.class)	
	public ResponseEntity<HashMap<String,String>> existingISBN(ExistingBookISBN ex){	

		System.out.println("MY EXCEPTION: EXISTING ISBN");			

		HashMap<String, String> errorResponse = new HashMap<String, String>();
		errorResponse.put("errorMessage", ex.getExceptionReason());

		return new ResponseEntity<HashMap<String, String>>(errorResponse, HttpStatus.BAD_REQUEST);	
	}


	

	@ResponseStatus(value=HttpStatus.UNAUTHORIZED)	
	@ExceptionHandler(UserNotVerifiedException.class)
	public ResponseEntity<HashMap<String, String>> userNotVerified(UserNotVerifiedException ex){

		System.out.println("MY EXCEPTION: USER NOT VERIFIED");	
		
		HashMap<String, String> errorResponse = new HashMap<String, String>();
		errorResponse.put("errorMessage", ex.getExceptionReason());

		return new ResponseEntity<HashMap<String, String>>(errorResponse, HttpStatus.UNAUTHORIZED);	
	}


	@ResponseStatus(value=HttpStatus.FORBIDDEN)	
	@ExceptionHandler(UserNotSignedInException.class)
	public String userNotSignedIn(UserNotSignedInException ex){

		System.out.println("MY EXCEPTION: USER NOT SIGNED IN");		
	
		HashMap<String, String> errorResponse = new HashMap<String, String>();
		errorResponse.put("errorMessage", ex.getExceptionReason());

		return "signup";	
	}


	@ResponseStatus(value=HttpStatus.BAD_REQUEST)	
	@ExceptionHandler(BookWaitlistedException.class)
	public ResponseEntity<HashMap<String, String>> bookWaitlisted(BookWaitlistedException ex){

		System.out.println("MY EXCEPTION: BOOK WAITLISTED. CAN'T RENEW");	
		
		HashMap<String, String> errorResponse = new HashMap<String, String>();
		errorResponse.put("errorMessage", ex.getExceptionReason());

		return new ResponseEntity<HashMap<String, String>>(errorResponse, HttpStatus.BAD_REQUEST);	
	}

	
}





