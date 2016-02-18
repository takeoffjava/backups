package org.strobs.utoo.web.services.controllers;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.strobs.utoo.web.services.base.common;
import org.strobs.utoo.web.services.classes.lookup;
import org.strobs.utoo.web.services.eClasses.driverapp_status;
import org.strobs.utoo.web.services.service.srveInitial;

@RestController
@RequestMapping("/service/api/initial/*")
public class cInitial extends srveInitial
{
	@RequestMapping(value = "/lookup", method = RequestMethod.POST, headers="Accept=application/json")
	private Map<String,Object> lookups(@RequestBody String s_request_JSON_body_content) 
	{
		return super.lookup(common.convertJSON2EntityClass(s_request_JSON_body_content,lookup.class));
	}
	
	@RequestMapping(value = "/driver_app_status", method = RequestMethod.POST, headers="Accept=application/json")
	private Map<String,Object> driverAppStatus(@RequestBody String s_request_JSON_body_content) 
	{
		return super.driverAppStatusService(common.convertJSON2EntityClass(s_request_JSON_body_content,driverapp_status.class));
	}
	@RequestMapping(value = "/getdriver_app_status", method = RequestMethod.POST, headers="Accept=application/json")
	private Map<String,Object> getdriver_app_status(@RequestBody String s_request_JSON_body_content) 
	{
		return super.getDriverAppStatus(common.convertJSON2EntityClass(s_request_JSON_body_content,driverapp_status.class));
	}
	
	@RequestMapping(value = "/deploy", method = RequestMethod.POST, headers="Accept=application/json")
	private String lastdeployment(@RequestBody String s_request_JSON_body_content) 
	{
			
		JSONObject json_map = new JSONObject();
		try
		{
			json_map.put("Server_IP", "43.242.124.37");
			json_map.put("Date", "NOV 6 2015");
		}
		catch(JSONException jExcep)
		{
			
		}
		return json_map.toString();
	}
	@RequestMapping(value = "/getServerDetails", method = RequestMethod.POST, headers="Accept=application/json")
	private Map<String,Object> getServerDetails(@RequestBody String s_request_JSON_body_content) 
	{
		return super.getServerDetails();
	}	
}
