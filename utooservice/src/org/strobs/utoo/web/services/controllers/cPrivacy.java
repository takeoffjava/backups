package org.strobs.utoo.web.services.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.strobs.utoo.web.services.base.common;
import org.strobs.utoo.web.services.classes.block_drivers;
import org.strobs.utoo.web.services.classes.ices;
import org.strobs.utoo.web.services.classes.rating;
import org.strobs.utoo.web.services.eClasses.ice;
import org.strobs.utoo.web.services.eClasses.passenger;
import org.strobs.utoo.web.services.service.srvePrivacy;


@RestController
@RequestMapping("/service/api/privacy/*")
public class cPrivacy extends srvePrivacy
{
	@RequestMapping(value = "/rating", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> rating(@RequestBody String s_request_JSON_body_content) 
	{
		return super.ratingPoints(common.convertJSON2EntityClass(s_request_JSON_body_content,rating.class));
	}
	@RequestMapping(value = "/passengerrating", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> passengerRating(@RequestBody String s_request_JSON_body_content) 
	{
		return super.passengerRating(common.convertJSON2EntityClass(s_request_JSON_body_content,rating.class));
	}
	
	@RequestMapping(value = "/blockdriver", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> blockDriver(@RequestBody String s_request_JSON_body_content) 
	{
		return super.blockDriver(common.convertJSON2EntityClass(s_request_JSON_body_content,block_drivers.class));
	}
	@RequestMapping(value = "/blockpassenger", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> blockPassenger(@RequestBody String s_request_JSON_body_content) 
	{
		return super.blockPassenger(common.convertJSON2EntityClass(s_request_JSON_body_content,block_drivers.class));
	}
	
	@RequestMapping(value = "/blockeddrivers", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> getBlockedDriverForPassenger(@RequestBody String s_request_JSON_body_content) 
	{
		return super.getBlockedDriverForPassenger(common.convertJSON2EntityClass(s_request_JSON_body_content,passenger.class));
	}
	@RequestMapping(value = "/deactivate", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> deactivate(@RequestBody String s_request_JSON_body_content) 
	{
		return super.deactivateMyAccount(common.convertJSON2EntityClass(s_request_JSON_body_content,passenger.class));
	}
	@RequestMapping(value = "/latlonupdate", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> latlonUpdate(@RequestBody String s_request_JSON_body_content) 
	{
		return super.latlonUpdate(common.convertJSON2EntityClass(s_request_JSON_body_content,passenger.class));
	}
	@RequestMapping(value = "/addice", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> addICE(@RequestBody String s_request_JSON_body_content) 
	{
		return super.addICE(common.convertJSON2EntityClass(s_request_JSON_body_content,ices.class));
	}
	@RequestMapping(value = "/getice", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> getICE(@RequestBody String s_request_JSON_body_content) 
	{
		return super.getICE(common.convertJSON2EntityClass(s_request_JSON_body_content,ice.class));
	}
	@RequestMapping(value = "/removeice", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> removeICE(@RequestBody String s_request_JSON_body_content) 
	{
		return super.removeICE(common.convertJSON2EntityClass(s_request_JSON_body_content,ice.class));
	}
	@RequestMapping(value = "/changeprimaryice", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> changePrimaryICE(@RequestBody String s_request_JSON_body_content) 
	{
		return super.changePrimaryICE(common.convertJSON2EntityClass(s_request_JSON_body_content,ice.class));
	}
	
	@RequestMapping(value = "/validpromocode", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> validPromoCode(@RequestBody String s_request_JSON_body_content) 
	{
		return super.validPromoCode(common.convertJSON2EntityClass(s_request_JSON_body_content,passenger.class));
	}	
	
	@RequestMapping(value = "/notifyice", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> notifyICE(@RequestBody String s_request_JSON_body_content) 
	{
		return super.notifyICE(common.convertJSON2EntityClass(s_request_JSON_body_content,ice.class));
	}
}
