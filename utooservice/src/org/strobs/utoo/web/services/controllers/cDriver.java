package org.strobs.utoo.web.services.controllers;

import java.io.InputStream;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.strobs.utoo.web.services.base.common;
import org.strobs.utoo.web.services.eClasses.accident;
import org.strobs.utoo.web.services.eClasses.bills;
import org.strobs.utoo.web.services.eClasses.booking;
import org.strobs.utoo.web.services.eClasses.driver_login_histories;
import org.strobs.utoo.web.services.eClasses.driver_miles;
import org.strobs.utoo.web.services.eClasses.drivers;
import org.strobs.utoo.web.services.eClasses.invoice;
import org.strobs.utoo.web.services.service.srveDriver;

@RestController
@RequestMapping("/service/api/driver/*")
public class cDriver extends srveDriver
{
	@RequestMapping(value = "/register", method = RequestMethod.POST,headers="Accept=application/json")
    public Map<String,Object> driverRegister(@RequestBody String s_request_JSON_body_content) 
    {
		return super.driverRegister(common.convertJSON2EntityClass(s_request_JSON_body_content,drivers.class));
    }
	
	
	@RequestMapping(value = "/drivercardetails", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> driverCarDetails(@RequestBody String s_request_JSON_body_content) 
	{
		return super.update(common.convertJSON2EntityClass(s_request_JSON_body_content,drivers.class));
	}
	@RequestMapping(value = "/uploaddriverlicense", method = RequestMethod.POST,headers="Accept=application/json")
    public Map<String,Object> driver_license(InputStream InpStrmContent,@RequestHeader(value="access_id") String access_id,@RequestHeader(value="licence_side") String license_side) 
    {
		return super.uploadLicence(InpStrmContent,"0xf_licence_images",access_id,license_side);
    }
	@RequestMapping(value = "/uploadcarimage", method = RequestMethod.POST,headers="Accept=application/json")
    public Map<String,Object> driverCarDetails(InputStream InLogo, @RequestHeader(value="access_id") String access_id) 
    {
		return super.uploadCarImage(InLogo,"0xf_car_images",access_id);
    }
	@RequestMapping(value = "/uploadprofilephoto", method = RequestMethod.POST,headers="Accept=application/json")
    public Map<String,Object> UploadProfilePhoto(InputStream InLogo, @RequestHeader(value="access_id") String access_id) 
    {
		return super.uploadProfilePhoto(InLogo,"0xf_driver_profile_photo",access_id);
    }
	@RequestMapping(value = "/login", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> login(@RequestBody String s_request_JSON_body_content) 
	{
		return super.login(common.convertJSON2EntityClass(s_request_JSON_body_content,driver_login_histories.class));
	}
	@RequestMapping(value = "/driver_profile", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> driver_profile(@RequestBody String s_request_JSON_body_content) 
	{
		return super.driver_profile(common.convertJSON2EntityClass(s_request_JSON_body_content,drivers.class));
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> update(@RequestBody String s_request_JSON_body_content) 
	{
		return super.update(common.convertJSON2EntityClass(s_request_JSON_body_content,drivers.class));
	}
	/*@RequestMapping(value = "/updateString", method = RequestMethod.POST, headers="Accept=application/json")
	public String updateString(@RequestBody String s_request_JSON_body_content) 
	{
		return super.updateString(common.convertJSON2EntityClass(s_request_JSON_body_content,drivers.class));
	}*/
	@RequestMapping(value = "/settings", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> settings(@RequestBody String s_request_JSON_body_content) 
	{
		return super.updateSettings(common.convertJSON2EntityClass(s_request_JSON_body_content,drivers.class));
	}
	@RequestMapping(value = "/driver_status", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> driver_status(@RequestBody String s_request_JSON_body_content) 
	{
		return super.updateDriverStatus(common.convertJSON2EntityClass(s_request_JSON_body_content,drivers.class));
	}
	
	@RequestMapping(value = "/accident_details", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> accident_details(@RequestBody String s_request_JSON_body_content) 
	{
		return super.accident_details(common.convertJSON2EntityClass(s_request_JSON_body_content,accident.class));
	}
	
	@RequestMapping(value = "/upload_accident_image", method = RequestMethod.POST,headers="Accept=application/json")
    public Map<String,Object> upload_accident_image(InputStream InLogo, @RequestHeader(value="access_id") String access_id, @RequestHeader(value="accident_id") String accident_id,@RequestHeader(value="upload_type") String upload_type,@RequestHeader(value="extension") String extension,@RequestHeader(value="isAudio") String isAudio) 
    {
		String server_path="";
		if(upload_type.equals("audio"))
			server_path="0xf_accident_audio";
		else if(upload_type.equals("image"))
			server_path="0xf_accident_image";
		else 
			return map_response(true,5000); 
		return super.uploadAccidentDetails(InLogo,server_path,access_id,accident_id,extension,Boolean.valueOf(isAudio));
    }
	@RequestMapping(value = "/driver_job_history", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> driver_history(@RequestBody String s_request_JSON_body_content) 
	{
		return super.driver_job_history(common.convertJSON2EntityClass(s_request_JSON_body_content,booking.class));
	}

	@RequestMapping(value = "/driver_miles", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> driver_miles_details(@RequestBody String s_request_JSON_body_content) 
	{
		return super.driver_miles_details(common.convertJSON2EntityClass(s_request_JSON_body_content,driver_miles.class));
	}
	@RequestMapping(value="/driver_guide", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> driver_guide_details(@RequestBody String s_request_JSON_body_content) 
	{
		return super.driver_guide_details(common.convertJSON2EntityClass(s_request_JSON_body_content,booking.class));
	}
	@RequestMapping(value="/driver_start_trip", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> driver_start_trip(@RequestBody String s_request_JSON_body_content) 
	{
		return super.driverStartTrip(common.convertJSON2EntityClass(s_request_JSON_body_content,booking.class));
	}
	@RequestMapping(value="/driver_end_trip", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> driver_end_trip(@RequestBody String s_request_JSON_body_content) 
	{
		return super.driver_end_trip(common.convertJSON2EntityClass(s_request_JSON_body_content,invoice.class));
	}
	@RequestMapping(value="/get_driver_rating", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> get_driver_rating(@RequestBody String s_request_JSON_body_content) 
	{
		return super.get_driver_rating(common.convertJSON2EntityClass(s_request_JSON_body_content,drivers.class));
	}
	@RequestMapping(value="/getinvoicehistory", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> getBookingHistory(@RequestBody String s_request_JSON_body_content) 
	{
		return super.getBookingHistory(common.convertJSON2EntityClass(s_request_JSON_body_content,booking.class));
	}
	@RequestMapping(value="/billentry", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> setBillEntry(@RequestBody String s_request_JSON_body_content) 
	{
		return super.setBillEntry(common.convertJSON2EntityClass(s_request_JSON_body_content,bills.class));
	}
	@RequestMapping(value = "/driverdetails", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> driverdetails(@RequestBody String s_request_JSON_body_content) 
	{
		return super.driverdetails(common.convertJSON2EntityClass(s_request_JSON_body_content,drivers.class));
	}
	@RequestMapping(value="/getinvoice", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> getInvoiceDetails(@RequestBody String s_request_JSON_body_content) 
	{
		return super.getInvoiceDetails(common.convertJSON2EntityClass(s_request_JSON_body_content,booking.class));
	}
	@RequestMapping(value="/updatecurrentlatlon", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> updatecurrentlatlon(@RequestBody String s_request_JSON_body_content) 
	{
		return super.updateCurrentLatlon(common.convertJSON2EntityClass(s_request_JSON_body_content,drivers.class));
	}
	@RequestMapping(value="/getDriverPassCode", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> getDriverPassCode(@RequestBody String s_request_JSON_body_content) 
	{
		return super.getDriverPassCode(common.convertJSON2EntityClass(s_request_JSON_body_content,drivers.class));
	}
}
