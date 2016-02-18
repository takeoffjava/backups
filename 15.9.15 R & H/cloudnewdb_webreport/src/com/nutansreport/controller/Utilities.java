package com.nutansreport.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.codehaus.jackson.map.ObjectMapper;

public class Utilities {

	private final String forresttiBackendURL = rsbAR
			.getString("nutansbackend.url");
	ForresttiBackendUtil forresttiBackendUtil=new ForresttiBackendUtil();
	
	public static ResourceBundle rsbAR = ResourceBundle
			.getBundle("ApplicationResource");

	@SuppressWarnings("unchecked")
	public Map<String, Object> ResponseData(String json, String ServiceURL,
			String Method,String heardeName) {
		String url = null, respJson = null;
		StringBuilder urlBuilder = null;
		Map<String, Object> mapResponse = null;
		ObjectMapper mapper = new ObjectMapper();

		try {

			urlBuilder = new StringBuilder();

			url = urlBuilder.append(forresttiBackendURL).append(ServiceURL)
					.toString();
			
			respJson = (String) forresttiBackendUtil.URLFetch(url, Method,
					"application/json", json,heardeName);

			mapResponse = mapper.readValue(respJson, HashMap.class);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return mapResponse;
	}

}
