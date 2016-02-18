package org.strobs.utoo.web.services.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.strobs.utoo.web.services.base.common;
import org.strobs.utoo.web.services.eClasses.booking;
import org.strobs.utoo.web.services.eClasses.car_features;
import org.strobs.utoo.web.services.eClasses.car_models;
import org.strobs.utoo.web.services.eClasses.drivers;
import org.strobs.utoo.web.services.eClasses.location;
import org.strobs.utoo.web.services.eClasses.lost_found;
import org.strobs.utoo.web.services.eClasses.passenger;
import org.strobs.utoo.web.services.eClasses.promocode;
import org.strobs.utoo.web.services.eClasses.qrcode;
import org.strobs.utoo.web.services.eClasses.tariff;
import org.strobs.utoo.web.services.eClasses.users;
import org.strobs.utoo.web.services.eClasses.usertype;
import org.strobs.utoo.web.services.service.srveAdmin;

@RestController
@RequestMapping("/service/api/admin/*")
public class cAdmin extends srveAdmin
{
									/********** Login Functions Start *****************/
	@RequestMapping(value = "/login", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> login(@RequestBody String s_request_JSON_body_content) 
	{
		return super.login(common.convertJSON2EntityClass(s_request_JSON_body_content,users.class));
	}
	@RequestMapping(value = "/verifyotp", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> verifyOTP(@RequestBody String s_request_JSON_body_content) 
	{
		return super.verifyOTP(common.convertJSON2EntityClass(s_request_JSON_body_content,users.class));
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> logout(@RequestBody String s_request_JSON_body_content) 
	{
		return super.logout(common.convertJSON2EntityClass(s_request_JSON_body_content,users.class));
	}
	
									/********** Login Functions End *****************/
	
									/********** Insert Functions Start *****************/
	
	
	@RequestMapping(value = "/insertusertype", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> insertusertyps(@RequestBody String JSONContentofPOST) 
	{
		return super.insertusertype(common.convertJSON2EntityClass(JSONContentofPOST, usertype.class));
	}
	@RequestMapping(value = "/insertusertypeStr", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> insertusertypeStr(@RequestBody String JSONContentofPOST) 
	{
		return super.insertusertype(common.convertJSON2EntityClass(JSONContentofPOST, usertype.class));
	}
	@RequestMapping(value = "/insertcarmodels", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> insertcardetails(@RequestBody String JSONContentofPOST) 
	{
		return super.insertcardetails(common.convertJSON2EntityClass(JSONContentofPOST, car_models.class));
	}
	@RequestMapping(value = "/insertcarfeatures", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> insertcarfeatures(@RequestBody String JSONContentofPOST) 
	{
		return super.insertcarfeatures(common.convertJSON2EntityClass(JSONContentofPOST, car_features.class));
	}
	@RequestMapping(value = "/inserttariff", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> inserttariff(@RequestBody String JSONContentofPOST) 
	{
		return super.inserttariff(common.convertJSON2EntityClass(JSONContentofPOST, tariff.class));
	}
	@RequestMapping(value = "/insertpromo", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> insertpromocode(@RequestBody String JSONContentofPOST) 
	{
		return super.insertpromocode(common.convertJSON2EntityClass(JSONContentofPOST, promocode.class));
	}
	@RequestMapping(value = "/insertlocation", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> insertlocation(@RequestBody String JSONContentofPOST) 
	{
		return super.insertlocation(common.convertJSON2EntityClass(JSONContentofPOST, location.class));
	}
	@RequestMapping(value = "/insertwallet", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> insertwallet(@RequestBody String JSONContentofPOST) 
	{
		return super.insertwallet_money(common.convertJSON2EntityClass(JSONContentofPOST, passenger.class));
	}
	@RequestMapping(value = "/getLastBooking", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> getLastBooking(@RequestBody String JSONContentofPOST) 
	{
		return super.getLastBooking(common.convertJSON2EntityClass(JSONContentofPOST, booking.class));
	}
	
									/********** Insert Functions End *****************/

	
									/********** Get Functions Start *****************/
	
	@RequestMapping(value = "/getcarfeatures", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> getCarfeatures(@RequestBody String JSONContentofPOST) 
	{
		return super.getCarFeatures(common.convertJSON2EntityClass(JSONContentofPOST,car_features.class));
	}
	@RequestMapping(value = "/getcarmodels", method = RequestMethod.GET, headers = "Accept=application/json")
	public Map<String, Object> getCarModels() 
	{
		return super.getCarModels();
	}
	@RequestMapping(value = "/getusertype", method = RequestMethod.GET, headers = "Accept=application/json")
	public Map<String, Object> getUserType(@RequestBody String JSONContentofPOST) 
	{
		return super.getUserTypes();
	}
		
	@RequestMapping(value = "/getall_AFBdriver", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> getallAvailabledriver(@RequestBody String JSONContentofPOST) 
	{
		return super.getAll_AFL_Drivers(common.convertJSON2EntityClass(JSONContentofPOST,drivers.class));
	}
	@RequestMapping(value = "/getall_AFBdriverpaginate", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> getall_AFBdriverpaginate(@RequestBody String JSONContentofPOST) 
	{
		return super.getAll_AFL_Driverspaginate(common.convertJSON2EntityClass(JSONContentofPOST,drivers.class));
	}
	
	@RequestMapping(value = "/getdrivers_ByLocation", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> getdrivers_ByLocation(@RequestBody String JSONContentofPOST) 
	{
		return super.getAlldrivers_Bylocation(common.convertJSON2EntityClass(JSONContentofPOST,drivers.class));
	}
	
	@RequestMapping(value = "/getblockdrivers", method = RequestMethod.GET, headers = "Accept=application/json")
	public Map<String, Object> getblockdrivers(@RequestBody String JSONContentofPOST) 
	{
		return super.getBlockdrivers();
	}
	@RequestMapping(value = "/getpromobyuser", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> getpromobypassenger(@RequestBody String JSONContentofPOST) 
	{
		return super.getpromo_Bypassenger(common.convertJSON2EntityClass(JSONContentofPOST,promocode.class));
	}
	@RequestMapping(value = "/gettrackbooking", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> gettrackbooking(@RequestBody String JSONContentofPOST) 
	{
		return super.gettrackbooking(common.convertJSON2EntityClass(JSONContentofPOST,booking.class));
	}
	@RequestMapping(value = "/gettrackbookingno", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> gettrackbookingno(@RequestBody String JSONContentofPOST) 
	{
		return super.gettrackbookingNo(common.convertJSON2EntityClass(JSONContentofPOST,booking.class));
	}
	
							/**********Data Table Services Start *****************/
	
	@RequestMapping(value = "/getuser_datatable", method = RequestMethod.GET)
	 @ResponseBody
	 Map<String,Object> getuser_datatable(HttpServletRequest request,@RequestBody String draw) {	 
	       return super.getuser_datatable(Integer.parseInt(request.getParameter("draw")),Integer.parseInt(request.getParameter("length")),
	    		   Integer.parseInt(request.getParameter("start")),request.getParameter("search[value]").toString());
	 }
	 @RequestMapping(value = "/getbookings_datatable", method = RequestMethod.GET)
	 @ResponseBody
	 Map<String,Object> getJsonData(HttpServletRequest request,@RequestBody String draw) {	 
	       return super.getBookings_datatable(Integer.parseInt(request.getParameter("draw")),Integer.parseInt(request.getParameter("length")),
	    		   Integer.parseInt(request.getParameter("start")),request.getParameter("search[value]").toString());
	 }
	 @RequestMapping(value = "/getbookingsbydate/{startdate}/{enddate}", method = RequestMethod.GET, headers = "Accept=application/json")
		public Map<String, Object> getbookingsbydate(HttpServletRequest request,@PathVariable String startdate,@PathVariable String enddate){
			return super.getbookings_Bydate(startdate, enddate,Integer.parseInt(request.getParameter("draw")),Integer.parseInt(request.getParameter("length")),
		    		   Integer.parseInt(request.getParameter("start")),request.getParameter("search[value]").toString());
		}
	 @RequestMapping(value = "/getalldriver", method = RequestMethod.GET, headers = "Accept=application/json")
		public Map<String, Object> getalldrivers(HttpServletRequest request) 
		{
 			return super.getAlldrivers(Integer.parseInt(request.getParameter("draw")),Integer.parseInt(request.getParameter("length")),
		    		   Integer.parseInt(request.getParameter("start")),request.getParameter("search[value]").toString()); 
		}
	 @RequestMapping(value = "/gettariff", method = RequestMethod.GET, headers = "Accept=application/json")
		public Map<String, Object> gettariff(HttpServletRequest request) 
		{
			return super.getAlltariff(Integer.parseInt(request.getParameter("draw")),Integer.parseInt(request.getParameter("length")),
		    		   Integer.parseInt(request.getParameter("start")),request.getParameter("search[value]").toString());
		}
	
	 @RequestMapping(value = "/getpromocode", method = RequestMethod.GET, headers = "Accept=application/json")
		public Map<String, Object> getpromo(HttpServletRequest request) 
		{
		return super.getpromo_codes(Integer.parseInt(request.getParameter("draw")),Integer.parseInt(request.getParameter("length")),
	    		   Integer.parseInt(request.getParameter("start")),request.getParameter("search[value]").toString());
		}
	 @RequestMapping(value = "/getaccidentdrivers", method = RequestMethod.GET, headers = "Accept=application/json")
		public Map<String, Object> getaccidentdrivers(HttpServletRequest request) 
		{
			return super.getAccidentdrivers(Integer.parseInt(request.getParameter("draw")),
					Integer.parseInt(request.getParameter("length")),
		    		   Integer.parseInt(request.getParameter("start")),request.getParameter("search[value]").toString());
		}
	 @RequestMapping(value = "/getlostfound", method = RequestMethod.GET, headers = "Accept=application/json")
		public Map<String, Object> getlostfound(HttpServletRequest request) 
		{
			return super.getlostfound(Integer.parseInt(request.getParameter("draw")),Integer.parseInt(request.getParameter("length")),
		    		   Integer.parseInt(request.getParameter("start")),request.getParameter("search[value]").toString());
		}
	 @RequestMapping(value = "/getcarmodels_datatables", method = RequestMethod.GET, headers = "Accept=application/json")
		public Map<String, Object> getcarmodels_datatables(HttpServletRequest request) 
		{
		 return super.getcarmodels_datatable(Integer.parseInt(request.getParameter("draw")),Integer.parseInt(request.getParameter("length")),
	    		   Integer.parseInt(request.getParameter("start")),request.getParameter("search[value]").toString());
		}
	 @RequestMapping(value = "/getcarfeatures_datatables", method = RequestMethod.GET, headers = "Accept=application/json")
		public Map<String, Object> getcarfeatures_datatables(HttpServletRequest request) 
		{
		 return super.getcarfeatures_datatable(Integer.parseInt(request.getParameter("draw")),Integer.parseInt(request.getParameter("length")),
	    		   Integer.parseInt(request.getParameter("start")),request.getParameter("search[value]").toString());
		}
	/* @RequestMapping(value = "/getHistory/{access_id}", method = RequestMethod.GET, headers = "Accept=application/json")
		public Map<String, Object> getHistory(HttpServletRequest request,@PathVariable String access_id) 
		{
		 return super.getHistory(Integer.parseInt(request.getParameter("draw")),Integer.parseInt(request.getParameter("length")),
	    		   Integer.parseInt(request.getParameter("start")),request.getParameter("search[value]").toString(),access_id);
		}*/
	 @RequestMapping(value =  "/getHistory/{access_id}", method = RequestMethod.GET)
	 @ResponseBody
	 Map<String,Object> getHistory(HttpServletRequest request,@PathVariable String access_id) {	 
	       return super.getHistory(Integer.parseInt(request.getParameter("draw")),Integer.parseInt(request.getParameter("length")),
	    		   Integer.parseInt(request.getParameter("start")),request.getParameter("search[value]").toString(),access_id);
	 }
	 @RequestMapping(value = "/getHistoryByDate/{access_id}/{fromDate}/{toDate}", method = RequestMethod.GET, headers = "Accept=application/json")
		public Map<String, Object> getHistoryByDate(HttpServletRequest request,@PathVariable String access_id,@PathVariable String fromDate,@PathVariable String toDate) 
		{
		 return super.getHistorybyDate(fromDate,toDate,Integer.parseInt(request.getParameter("draw")),Integer.parseInt(request.getParameter("length")),
	    		   Integer.parseInt(request.getParameter("start")),request.getParameter("search[value]").toString(),access_id);
		}
	 @RequestMapping(value = "/getlocations_datatables", method = RequestMethod.GET, headers = "Accept=application/json")
		public Map<String, Object> getlocations_datatables(HttpServletRequest request) 
		{
		 return super.getlocation_datatable(Integer.parseInt(request.getParameter("draw")),Integer.parseInt(request.getParameter("length")),
	    		   Integer.parseInt(request.getParameter("start")),request.getParameter("search[value]").toString());
		}
	 @RequestMapping(value = "/getqrcode", method = RequestMethod.GET, headers = "Accept=application/json")
		public Map<String, Object> getqrcode(HttpServletRequest request) 
		{
		 return super.getqrcodes_datatable(Integer.parseInt(request.getParameter("draw")),Integer.parseInt(request.getParameter("length")),
	    		   Integer.parseInt(request.getParameter("start")),request.getParameter("search[value]").toString());
		}
	 @RequestMapping(value = "/getpassenger", method = RequestMethod.GET, headers = "Accept=application/json")
		public Map<String, Object> getpassenger(HttpServletRequest request) 
		{
		 return super.getpassgenger_datatable(Integer.parseInt(request.getParameter("draw")),Integer.parseInt(request.getParameter("length")),
	    		   Integer.parseInt(request.getParameter("start")),request.getParameter("search[value]").toString());
		}
	
	 								 /**********************Data Table Services End **************************************/
		
	 								/****************************** Delete Functions Start ********************************/
	
	@RequestMapping(value = "/deletecarfeature", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> deletecarfeature(@RequestBody String JSONContentofPOST) 
	{
		return super.deleteCarFeature(common.convertJSON2EntityClass(JSONContentofPOST,car_features.class));
	}
	@RequestMapping(value = "/deletecarmodel", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> deletecarmodel(@RequestBody String JSONContentofPOST) 
	{
		return super.deleteCarModel(common.convertJSON2EntityClass(JSONContentofPOST,car_models.class));
	}
	@RequestMapping(value = "/deletetariff", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> deletetariff(@RequestBody String JSONContentofPOST) 
	{
		return super.deletetariff(common.convertJSON2EntityClass(JSONContentofPOST,tariff.class));
	}
	@RequestMapping(value = "/deletepromo", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> deletepromocode(@RequestBody String JSONContentofPOST) 
	{
		return super.deletepromocode(common.convertJSON2EntityClass(JSONContentofPOST,promocode.class));
	}
	@RequestMapping(value = "/deletelostfound", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> deletelostfound(@RequestBody String JSONContentofPOST) 
	{
		return super.deletelostfound(common.convertJSON2EntityClass(JSONContentofPOST,lost_found.class));
	}
	@RequestMapping(value = "/deleteuser", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> deleteuser(@RequestBody String JSONContentofPOST) 
	{
		return super.deleteusers(common.convertJSON2EntityClass(JSONContentofPOST,users.class));
	}
	@RequestMapping(value = "/deletelocation", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> deletelocation(@RequestBody String JSONContentofPOST) 
	{
		return super.deletelocation(common.convertJSON2EntityClass(JSONContentofPOST,location.class));
	}
	
								/****************************** Delete Functions End ********************************/
	
								/****************************** Update Functions Start ********************************/
	
	@RequestMapping(value = "/updatecarfeatures", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> updatecarfeatures(@RequestBody String s_request_JSON_body_content) 
	{
		return super.updatecarfeature(common.convertJSON2EntityClass(s_request_JSON_body_content,car_features.class));
	}
	@RequestMapping(value = "/updatecarmodals", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> updatecarmodals(@RequestBody String s_request_JSON_body_content) 
	{
		return super.updatemodals(common.convertJSON2EntityClass(s_request_JSON_body_content,car_models.class));
	}
	@RequestMapping(value = "/updatetariff", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> updatetariff(@RequestBody String s_request_JSON_body_content) 
	{
		return super.updatetariff(common.convertJSON2EntityClass(s_request_JSON_body_content,tariff.class));
	}
	@RequestMapping(value = "/updatepromocode", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> updatepromocode(@RequestBody String s_request_JSON_body_content) 
	{
		return super.updatepromocode(common.convertJSON2EntityClass(s_request_JSON_body_content,promocode.class));
	}
	@RequestMapping(value = "/updateuser", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> updateuser(@RequestBody String s_request_JSON_body_content) 
	{
		return super.updateuser(common.convertJSON2EntityClass(s_request_JSON_body_content,users.class));
	}
	@RequestMapping(value = "/updatelocation", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> updatelocation(@RequestBody String s_request_JSON_body_content) 
	{
		return super.updatelocation(common.convertJSON2EntityClass(s_request_JSON_body_content,location.class));
	}

								/****************************** Update Functions End  ********************************/
	
								/****************************** Not Used Services ***********************************/
	
	
	/*@RequestMapping(value = "/getdriverbyname", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> getdriversbyname(@RequestBody String JSONContentofPOST) 
	{
		return super.getdrivers_Byname(common.convertJSON2EntityClass(JSONContentofPOST,drivers.class));
	}*/
	@RequestMapping(value = "/getdriver", method = RequestMethod.POST, headers = "Accept=application/json")//Get Driver By AccesID
	public Map<String, Object> getdrivers(@RequestBody String JSONContentofPOST) 
	{
		return super.getdrivers(common.convertJSON2EntityClass(JSONContentofPOST,drivers.class));
	}	
	
	/**********Add User Start *****************/
	@RequestMapping(value = "/AddUser", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> insertAddUser(@RequestBody String JSONContentofPOST) 
	{
		return super.insertUser(common.convertJSON2EntityClass(JSONContentofPOST, users.class));
	}
	/****************************** Add User Ends ********************************/

	/**********QRCODE Start *****************/
	@RequestMapping(value = "/qrcode", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> insertQRDetails(@RequestBody String JSONContentofPOST) 
	{
		return super.insertQRService(common.convertJSON2EntityClass(JSONContentofPOST, qrcode.class));
	}
	
	@RequestMapping(value = "/qrverify", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> qrVerifyAndBook(@RequestBody String JSONContentofPOST) 
	{
		return super.qrVerify(common.convertJSON2EntityClass(JSONContentofPOST, qrcode.class));
	}
	@RequestMapping(value = "/getqrcode_images", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> getqrcode_images(@RequestBody String JSONContentofPOST) 
	{
		return super.getqrcode_images(common.convertJSON2EntityClass(JSONContentofPOST, qrcode.class));
	}
	
	
	/****************************** QRCODE Ends ********************************/
	/****************************** Add User Ends ********************************/
	@RequestMapping(value = "/delete_driver", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> delete_driver(@RequestBody String JSONContentofPOST) 
	{
		return super.delete_driver(common.convertJSON2EntityClass(JSONContentofPOST, drivers.class));
	}
public boolean payment_Response(String Responsecode,String Responsemessage,String unique_ID)
{
	
	return updatepayment(Responsecode,Responsemessage,unique_ID);
	//return response;
}
@RequestMapping(value = "/addcomments", method = RequestMethod.POST, headers="Accept=application/json")
public Map<String,Object> addcomments(@RequestBody String JSONContentofPOST) 
{
	return super.addcomments(common.convertJSON2EntityClass(JSONContentofPOST, booking.class));
}
}

