package edu.sjsu.cmpe275.termproject.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class ISBNServiceImpl implements ISBNService{

	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String apiKey ="AIzaSyAPb49Q7MIBmK1foAAMMBsvUQDzgIo-O98";
	
	@Override
	public String getBookByISBN(String ISBNNumber) {
		String response;
		String URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:"+ISBNNumber+"&key="+apiKey;
		try {
			response = sendGET(URL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return response;
	}
	

	public String sendGET(String passedURL) throws IOException {
		String bookResponse;
		URL obj = new URL(passedURL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			bookResponse = response.toString();
			
		} else {
			System.out.println("GET request not worked");
			return null;
		}
		return  bookResponse;
	}
	
}
