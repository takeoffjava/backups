package com.nutansRplus.controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Component;

@Component
public class BackendUtil {

	public Object URLFetch(String lCMSURL, String lReqMethod,
			String lReqProperty, String lJSONString,String tokenHeader) throws Exception {
		
		String lRespString = "";
		URL lURL = null;
		HttpURLConnection lConnection = null;
		OutputStreamWriter lWriter = null;
		BufferedReader lReader = null;
		int statusCode = 0;
		boolean setTimeOutLimit = false;
		//System.out.println(lCMSURL);
		try {

			lURL = new URL(lCMSURL);
			lConnection = (HttpURLConnection) lURL.openConnection();

			if ("POST".equals(lReqMethod) || "PUT".equals(lReqMethod))
				lConnection.setDoOutput(true);
			else
				lConnection.setDoOutput(false);

			lConnection.setDoInput(true);
			lConnection.setRequestMethod(lReqMethod);
			if(tokenHeader == null || tokenHeader == "" ){
			
				lConnection.setRequestProperty("Content-Type", lReqProperty);				
			}else{
			
			
				lConnection.setRequestProperty("Content-Type", lReqProperty);
				lConnection.setRequestProperty("Authorization", tokenHeader);	
			}
						
			
			lConnection.setInstanceFollowRedirects(false);

			if (setTimeOutLimit) {
				lConnection.setConnectTimeout(25000);
				lConnection.setReadTimeout(25000);
			} else {
				lConnection.setConnectTimeout(60000);
				lConnection.setReadTimeout(60000);
			}

			if ("POST".equals(lReqMethod) || "PUT".equals(lReqMethod)) {

				lWriter = new OutputStreamWriter(lConnection.getOutputStream());
				lWriter.write(lJSONString);
				lWriter.close();

			}

			statusCode = lConnection.getResponseCode();

			if (statusCode == 200) {
				lReader = new BufferedReader(new InputStreamReader(
						lConnection.getInputStream()));
				lRespString = lReader.readLine();
				lReader.close();
			} else {
				// System.out.println("statusCode : " +statusCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lRespString;
	}
	public Object URLFetch_getAccess(String lCMSURL, String lReqMethod,
			String lReqProperty, String lJSONString,String tokenHeader) throws Exception {

		String lRespString = "";
		URL lURL = null;
		HttpURLConnection lConnection = null;
		OutputStreamWriter lWriter = null;
		BufferedReader lReader = null;
		int statusCode = 0;
		boolean setTimeOutLimit = false;

		try {

			lURL = new URL(lCMSURL);
			lConnection = (HttpURLConnection) lURL.openConnection();

			if ("POST".equals(lReqMethod) || "PUT".equals(lReqMethod))
				lConnection.setDoOutput(true);
			else
				lConnection.setDoOutput(false);

			lConnection.setDoInput(true);
			lConnection.setRequestMethod(lReqMethod);
			
			
			
			if(tokenHeader == null || tokenHeader == "" ){
			
				lConnection.setRequestProperty("Content-Type", lReqProperty);				
			}else{
			
			
				lConnection.setRequestProperty("Content-Type", lReqProperty);
				lConnection.setRequestProperty("Authorization", tokenHeader);	
			}
						
			
			lConnection.setInstanceFollowRedirects(false);

			if (setTimeOutLimit) {
				lConnection.setConnectTimeout(25000);
				lConnection.setReadTimeout(25000);
			} else {
				lConnection.setConnectTimeout(60000);
				lConnection.setReadTimeout(60000);
			}

			if ("POST".equals(lReqMethod) || "PUT".equals(lReqMethod)) {

				lWriter = new OutputStreamWriter(lConnection.getOutputStream());
				lWriter.write(lJSONString);
				lWriter.close();

			}

			statusCode = lConnection.getResponseCode();
			lReader = new BufferedReader(new InputStreamReader(
					lConnection.getInputStream()));
			if (statusCode == 200 ) {
				
				lRespString = lReader.readLine();
				
			} else if (statusCode == 400 ) {
				/*lReader = new BufferedReader(new InputStreamReader(
						lConnection.getInputStream()));*/
				lRespString ="{\"error\":\"bad request\"}";
			
			}else {
				// System.out.println("statusCode : " +statusCode);
			}
			lReader.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	//	System.out.println("statusCode : " + statusCode);
		//System.out.println("lRespString for access token : " + lRespString);
		return lRespString;
	}
}