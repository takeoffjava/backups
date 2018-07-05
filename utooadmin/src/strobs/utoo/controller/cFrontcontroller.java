package strobs.utoo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class cFrontcontroller
{
	private ResourceBundle rsbAR = ResourceBundle.getBundle("ApplicationResource");
	 Utilities utilities =new Utilities();
	 private String _DEFALUT_PAGE_NAME="login"; 
	@RequestMapping("/")  
	public String index(HttpSession session) 
	{  
		session.setAttribute("islogin","0"); 
		return "login";  //test push 
	}
	@RequestMapping("/logout_main")  
	public String logout_main(HttpSession session) 
	{  
		session.setAttribute("islogin","0"); 
		return "login";  
	}
	@RequestMapping("/1")  
	public String analytic(HttpSession session) 
	{  
		return IsExpiredLoginCountry(session,"analytic");  
	}
	@RequestMapping("/2")  
	public String analytic2(HttpSession session) 
	{  
		return IsExpiredLoginCountry(session,"analytic2");  
	}
	@RequestMapping("/3")  
	public String analytic3(HttpSession session) 
	{  
		return IsExpiredLoginCountry(session,"analytic3");  
	}
	@RequestMapping("/4")  
	public String analytic4(HttpSession session) 
	{  
		return IsExpiredLoginCountry(session,"analytic4");  
	}
	@RequestMapping(value="/track")
	public String trackx(HttpSession session) 
	{  
		return "track";  
	}
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> logout(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("logout");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> userlogin(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("login");
		Map<String,Object>login_response=new HashMap<String,Object>();
		login_response=utilities.ResponseData(json, userlogin_Path, "POST","");
		int loginresponse=cFrontcontroller.toInteger(login_response);
		if(loginresponse==1)
				session.setAttribute("islogin","1"); 
		else
				session.setAttribute("islogin","0"); 
		return login_response;
	}
	
	
	@RequestMapping(value = "/verifyotp", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> verifyotp(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("verifyotp");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	
	@RequestMapping(value = "/getall_Initialdrivers", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getall_initial_driver(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("getdriverinitial");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/track", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> track(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("track");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/getInvoiceDetails", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getInvoiceDetails(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("getinvoicedetails");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/getcarmodels", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> getcarmodels(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("getcarmodels");
		return utilities.ResponseData(json, userlogin_Path, "GET","");

	}
	/*@RequestMapping(value = "/getpassengers", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> getpassenger(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("getpassenger");
		return utilities.ResponseData(json, userlogin_Path, "GET","");

	}*/
	@RequestMapping(value = "/setcarmodels", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> setcarmodels(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("setcarmodels");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/deletecarmodels", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> deletecarmodels(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("deletecarmodels");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	
	
	@RequestMapping(value = "/updatecarmodels", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> updatecarmodels(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("updatecarmodels");
		return utilities.ResponseData(json, userlogin_Path, "POST","");
	}
	@RequestMapping(value = "/setcarfeatures", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> setcarfeatures(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("setcarfeatures");
		return utilities.ResponseData(json, userlogin_Path, "POST","");
	}
	@RequestMapping(value = "/deletecarfeatures", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> deletecarfeatures(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("deletecarfeatures");
		return utilities.ResponseData(json, userlogin_Path, "POST","");
	}
	@RequestMapping(value = "/updatecarfeatures", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> updatecarfeatures(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("updatecarfeatures");
		return utilities.ResponseData(json, userlogin_Path, "POST","");
	}
	@RequestMapping(value = "/setlocation", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> setlocation(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("setlocation");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/deletelocation", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> deletelocation(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("deletelocation");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/updatelocation", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> updatelocation(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("updatelocation");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/settariff", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> settariff(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("settariff");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/setwallet", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> setwallet(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("insertwallet");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/updatetariff", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> updatetariff(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("updatetariff");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/getall_driverspagination", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getall_driverspagination(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("getdriverpagination");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/getall_driverslocation", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getall_driverslocation(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("getdriverlocation");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	
	@RequestMapping(value = "/add_user", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> add_user(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("add_User");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	
	@RequestMapping(value = "/getUserType", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> getUserTypeController(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		String userlogin_Path = rsbAR.getString("get_userType");
		return utilities.ResponseData("", userlogin_Path, "GET","");

	}
	
	@RequestMapping(value = "/update_user", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> update_user(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("update_user");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> deleteUser(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("delete_user");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	//deletedriver
	@RequestMapping(value = "/deletedriver", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> deletedriver(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("deletedriver");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	//addcomments
	@RequestMapping(value = "/comments", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> addcomments(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("comments");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/getLookup", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getLookup(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("getLookup");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/getDriverProfile", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getDriverProfile(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("driverProfile");
		return utilities.ResponseData(json, userlogin_Path, "POST","");

	}
	@RequestMapping(value = "/gettrackbookingno", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> gettrackbookingno(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("trackbookingno");
		return utilities.ResponseData(json, userlogin_Path, "POST","");
	}
	@RequestMapping(value = "/getqrcode", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getqrcode(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("createqrcode");
		return utilities.ResponseData(json, userlogin_Path, "POST","");
	}
	@RequestMapping(value = "/getqrcodeimages", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getqrcodeimages(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String qrcodeid) {
		String userlogin_Path = rsbAR.getString("getqrcode");
		return utilities.ResponseData(qrcodeid, userlogin_Path, "POST","");
	}
	//getdriverProfile
	@RequestMapping(value = "/getDriverBooking", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getLastBooking(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("getdriverbooking");
		return utilities.ResponseData(json, userlogin_Path, "POST","");
	}
	@RequestMapping(value = "/invoice", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getinvoice(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,@RequestBody String json) {
		String userlogin_Path = rsbAR.getString("invoice");
		return utilities.ResponseData(json, userlogin_Path, "POST","");
	}
	@RequestMapping("/home")  
	public String home(HttpSession session) 
	{   
	  return IsExpiredLoginCountry(session,"home");
		
	}
	@RequestMapping("/calculator")  
	public String calculation(HttpSession session) 
	{   
	  return IsExpiredLoginCountry(session,"calculation");
		
	}
	@RequestMapping("/history")  
	public String history(HttpSession session) 
	{  
		  return IsExpiredLoginCountry(session,"history");
	}
	@RequestMapping("/booking")  
	public String booking(HttpSession session) 
	{   
	  return IsExpiredLoginCountry(session,"booking");
		
	}
	@RequestMapping("/drivers")  
	public String drivers(HttpSession session) 
	{   
	  return IsExpiredLoginCountry(session,"drivers");
		
	}
	@RequestMapping("/tariff")  
	public String tariff(HttpSession session) 
	{   
	  return IsExpiredLoginCountry(session,"tariff");
	}
	@RequestMapping("/promo")  
	public String promo(HttpSession session) 
	{   
	  return IsExpiredLoginCountry(session,"promo");
		
	}
	@RequestMapping("/accident")  
	public String accident(HttpSession session) 
	{   
	  return IsExpiredLoginCountry(session,"accident");
		
	}
	@RequestMapping("/lost")  
	public String lost(HttpSession session) 
	{   
	  return IsExpiredLoginCountry(session,"lost");
		
	}
	@RequestMapping("/info")  
	public String info(HttpSession session) 
	{   
	  return IsExpiredLoginCountry(session,"info");
		
		
	}
	@RequestMapping("/user")  
	public String user(HttpSession session) 
	{   
	  return IsExpiredLoginCountry(session,"user");
		
		
	}
	@RequestMapping("/passenger")  
	public String passenger(HttpSession session) 
	{   
	  return IsExpiredLoginCountry(session,"passenger");
		
		
	}
	@RequestMapping("/qrcode")  
	public String qrcode(HttpSession session) 
	{  
		 
		return IsExpiredLoginCountry(session,"qrcode");  
	}
	/* Common Functions Start */
	
	public static int toInteger(Map<String,Object> loginresponse){
		if(loginresponse==null)
		return 0;
		int loginresponseint=Integer.parseInt(loginresponse.get("status").toString());
			return loginresponseint;
	}
	private String IsExpiredLoginCountry(HttpSession session,String PageName)
    {
      Object locsession=session.getAttribute("islogin");
      session.setMaxInactiveInterval(14400);
        if(locsession==null)
            return _DEFALUT_PAGE_NAME;
        if(locsession.equals("1"))
            return PageName;
        return _DEFALUT_PAGE_NAME;
    }
	
	/* Common Functions End */
}
