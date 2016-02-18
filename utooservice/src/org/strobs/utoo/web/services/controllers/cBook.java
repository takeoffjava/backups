package org.strobs.utoo.web.services.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.strobs.utoo.web.services.base.common;
import org.strobs.utoo.web.services.classes.bookings;
import org.strobs.utoo.web.services.eClasses.booking;
import org.strobs.utoo.web.services.service.srveBook;

@RestController
@RequestMapping("/service/api/book/*")
public class cBook extends srveBook
{
	@RequestMapping(value = "/addbook", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> addnewbook(@RequestBody String s_request_JSON_body_content) 
	{
		return super.addBooks(common.convertJSON2EntityClass(s_request_JSON_body_content,bookings.class));
	}
	@RequestMapping(value="/bto", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> getDriversByLocation(@RequestBody String s_request_JSON_body_content) 
	{
		return super.getDriversByLocation(common.convertJSON2EntityClass(s_request_JSON_body_content,bookings.class));
	}
	@RequestMapping(value = "/unbooked", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> unbooked(@RequestBody String s_request_JSON_body_content) 
	{
		return super.unBooked(common.convertJSON2EntityClass(s_request_JSON_body_content,booking.class));
	}
	
	@RequestMapping(value = "/getbookinghistory", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> getBookingHistory(@RequestBody String JSONContentofPOST) 
	{
		return super.getBookingHistory(JSONContentofPOST);
	}
	@RequestMapping(value = "/ridelater", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> ridelaterController(@RequestBody String s_request_JSON_body_content) 
	{
		return super.ridelaterService(common.convertJSON2EntityClass(s_request_JSON_body_content,booking.class));
	}
}
