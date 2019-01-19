package edu.sjsu.cmpe275.termproject.exceptions;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MyCustomInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, UserNotSignedInException{

		System.out.println("The requested URI is"+request.getRequestURI());
		HttpSession session = request.getSession();
		if(request.getRequestURI().equals("/patron/signin")||request.getRequestURI().equals("/librarian/signin")
				||request.getRequestURI().equals("/patron/signup")||request.getRequestURI().equals("/librarian/signup")
				|| request.getRequestURI().equals("/patron/verifyuser") ||request.getRequestURI().equals("/library/verifyuser")){
			System.out.println("intercepting signing in");
		}else{
			if(session.getAttribute("loggedInUser")==null){
				throw new UserNotSignedInException("Please sign in");
			}
		}

		return true;
	}
}
