package com.nutansreport.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	private ResourceBundle rsbAR = ResourceBundle
			.getBundle("ApplicationResource");
	 Utilities utilities =new Utilities();
	 private String _DEFALUT_PAGE_NAME="login"; 
	 private String _LOGINSTATUS="";
	 private String IsExpiredLoginCountry(HttpSession session,String PageName)
	    {
	      Object locsession=session.getAttribute("islogin");
	        if(locsession==null)
	            return _DEFALUT_PAGE_NAME;
	        if(locsession.equals("1"))
	            return PageName;
	        return _DEFALUT_PAGE_NAME;
	    }
	@RequestMapping("/")  
	public String index(HttpSession session) 
	{  
		session.setAttribute("islogin","0"); 
		return "login";  
	}
	@RequestMapping("/logout")  
	public String login(HttpSession session) 
	{   
		//session.removeAttribute("islogin");
		return "login";  
	}
	@RequestMapping("/appstatus")  
	public String appstatus(HttpSession session) 
	{   
		return IsExpiredLoginCountry(session,"appstatus"); 
	}
	@RequestMapping("/reportsearch")  
	public String reportsearch(HttpSession session) 
	{   
		return IsExpiredLoginCountry(session,"reportsearch"); 
	}
	@RequestMapping("/dataanalytics")  
	public String dataanalytics(HttpSession session) 
	{   
		return IsExpiredLoginCountry(session,"dataanalytics"); 
	}
	
	@RequestMapping("/home")  
	public String home(HttpSession session) 
	{   
			
		   return IsExpiredLoginCountry(session,"home");	 
	}
	
	@RequestMapping("/admin")  
	public String admin(HttpSession session) 
	{   
			return  IsExpiredLoginCountry(session,"admin");
		
	}
	/*@RequestMapping("/reportsearch")  
	public String reportsearch(HttpSession session) 
	{   
			return  IsExpiredLoginCountry(session,"reportsearch");
		
	}*/
	@RequestMapping("/records")  
	public String records(HttpSession session) 
	{   
			return IsExpiredLoginCountry(session,"records");
		
	}
	@RequestMapping("/compare")  
	public String compare(HttpSession session) 
	{   
		
			return "compare";
		
	}
	/*@RequestMapping("/logout")  
	public String logout(HttpSession session) 
	{ 
		session.setAttribute("sessionid", 0);
		//session.removeAttribute("sessionid");
		return "login"; 
	}*/
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> userlogin(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("user_login");
		Map<String,Object>login_response=new HashMap<String,Object>();
		login_response=utilities.ResponseData(json, userlogin_Path, "POST","");
		int loginresponse=HomeController.toInteger(login_response);
		if(loginresponse==1)
				session.setAttribute("islogin","1"); 
		else
				session.setAttribute("islogin","0"); 
		return login_response;

	}
	public static int toInteger(Map<String,Object> loginresponse){
		if(loginresponse==null)
		return 0;
		int loginresponseint=Integer.parseInt(loginresponse.get("status").toString());
			return loginresponseint;
	}
	@RequestMapping(value = "/dashboard", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> dashboard(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {

		String userlogin_Path = rsbAR.getString("dashboard");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/reports", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> reports(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {

		String userlogin_Path = rsbAR.getString("reports");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/analytics", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> analytics(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {

		String userlogin_Path = rsbAR.getString("dataanalytics");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/reportsdate", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> reportsdate(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {

		String userlogin_Path = rsbAR.getString("reportsdate");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/datedashboard", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> datedashboard(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {

		String userlogin_Path = rsbAR.getString("dashboarddate");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/dashboardcount", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> dashboardcount(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {

		String userlogin_Path = rsbAR.getString("dashboard_count");
		return utilities.ResponseData(json, userlogin_Path, "POST","");
	}
	@RequestMapping(value = "/getappdata", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getappdata(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {

		String userlogin_Path = rsbAR.getString("getappdata");
		return utilities.ResponseData(json, userlogin_Path, "POST","");
	}
	@RequestMapping(value = "/daywiseappdata", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> datewisegetappdata(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {

		String userlogin_Path = rsbAR.getString("daywiseappdata");
		return utilities.ResponseData(json, userlogin_Path, "POST","");
	}
	@RequestMapping(value = "/alldatacompare", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> alldatacompare(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {

		String userlogin_Path = rsbAR.getString("alldatacompare");
		return utilities.ResponseData(json, userlogin_Path, "POST","");
	}
	@RequestMapping(value = "/datedatacompare", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> datedatacompare(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {

		String userlogin_Path = rsbAR.getString("datedatacompare");
		return utilities.ResponseData(json, userlogin_Path, "POST","");
	}
	@RequestMapping(value = "/appstatus", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> appstatus(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {

		String userlogin_Path = rsbAR.getString("appstatus");
		return utilities.ResponseData(json, userlogin_Path, "POST","");
	}
	/*@ResponseBody
	@RequestMapping(value = "/getUserIDForSession/{userID}")
	public String getUserIDFromSession(@PathVariable String userID, HttpSession session) {

		userIdfromSession = userID;
		session.setAttribute("userIDforSessionKey", userIdfromSession);
		
		return "";
	}*/
	 
}
