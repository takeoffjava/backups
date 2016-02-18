package com.nutansRplus.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utilities 
{
	private final String nutansBackendURL = rsbAR
			.getString("nutansbackend.url");
	BackendUtil OBackendUtil = new BackendUtil();

	public static ResourceBundle rsbAR = ResourceBundle
			.getBundle("ApplicationResource");

	public <T> T convertJSONToEntityClass(String json,  Class<T> type) 
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.serializeNulls();
		Gson gson = gsonBuilder.create();
		try{
		return (T) gson.fromJson(json, type);}catch(Exception expJSONToClassConvertor){return null;}
	}
	@SuppressWarnings("unchecked")
	public Map<String, Object> ResponseData(String json, String ServiceURL,
			String Method, String heardeName) {
		String url = null, respJson = null;
		StringBuilder urlBuilder = null;
		Map<String, Object> mapResponse = null;
		ObjectMapper mapper = new ObjectMapper();

		try {

			urlBuilder = new StringBuilder();

			url = urlBuilder.append(nutansBackendURL).append(ServiceURL)
					.toString();

			respJson = (String) OBackendUtil.URLFetch(url, Method,
					"application/json", json, heardeName);

			mapResponse = mapper.readValue(respJson, HashMap.class);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return mapResponse;
	}
}
