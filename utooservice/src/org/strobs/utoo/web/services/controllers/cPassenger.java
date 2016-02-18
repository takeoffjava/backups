package org.strobs.utoo.web.services.controllers;

import java.io.InputStream;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.strobs.utoo.web.services.base.common;
import org.strobs.utoo.web.services.eClasses.favourites;
import org.strobs.utoo.web.services.eClasses.passenger;
import org.strobs.utoo.web.services.service.srvePassenger;

@RestController
@RequestMapping("/service/api/passenger/*")
public class cPassenger extends srvePassenger
{
	@RequestMapping(value = "/register", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> withsecurity(@RequestBody String s_request_JSON_body_content) 
	{
		return super.register(common.convertJSON2EntityClass(s_request_JSON_body_content,passenger.class));
	}
	@RequestMapping(value = "/otpverify", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> otpverify(@RequestBody String s_request_JSON_body_content) 
	{
		return super.otpverify(common.convertJSON2EntityClass(s_request_JSON_body_content,passenger.class),true);
	}
	@RequestMapping(value = "/resendotp", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> resendotp(@RequestBody String s_request_JSON_body_content) 
	{
		return super.resendOTP(common.convertJSON2EntityClass(s_request_JSON_body_content,passenger.class));
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> login(@RequestBody String s_request_JSON_body_content) 
	{
		return super.login(common.convertJSON2EntityClass(s_request_JSON_body_content,passenger.class));
	}
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> forgotpassword(@RequestBody String s_request_JSON_body_content) 
	{
		return super.forgotpassword(common.convertJSON2EntityClass(s_request_JSON_body_content,passenger.class));
	}
	@RequestMapping(value = "/fpotpverify", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> fpotpverify(@RequestBody String s_request_JSON_body_content) 
	{
		return super.otpverify(common.convertJSON2EntityClass(s_request_JSON_body_content,passenger.class),false);
	}
	@RequestMapping(value = "/updateprofile", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> updateProfile(@RequestBody String s_request_JSON_body_content) 
	{
		return super.profileUpdate(common.convertJSON2EntityClass(s_request_JSON_body_content,passenger.class));
	}
	@RequestMapping(value = "/getratingcount", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> getratingcount(@RequestBody String s_request_JSON_body_content) 
	{
		return super.getratingcount(common.convertJSON2EntityClass(s_request_JSON_body_content,passenger.class));
	}
	
	@RequestMapping(value = "/uploadprofilephoto", method = RequestMethod.POST,headers="Accept=application/json")
    public Map<String,Object> UploadProfilePhoto(InputStream InLogo, @RequestHeader(value="access_id") String access_id) 
    {
		return super.uploadProfilePhoto(InLogo,"0xf_passenger_profile_photo",access_id);
    }
	@RequestMapping(value = "/logout", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> logout(@RequestBody String accessID) 
	{
		return super.logout(accessID);
	}
	
	@RequestMapping(value = "/addfavourites", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> insertFavourite(@RequestBody String s_request_JSON_body_content) 
	{
		return super.insertFavourite(common.convertJSON2EntityClass(s_request_JSON_body_content,favourites.class));
	}
	@RequestMapping(value = "/getfavourites", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> selectFavourites(@RequestBody String s_request_JSON_body_content) 
	{
		return super.selectFavourites(common.convertJSON2EntityClass(s_request_JSON_body_content,favourites.class));
	}
	@RequestMapping(value = "/removefavourites", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> removefavourites(@RequestBody String s_request_JSON_body_content) 
	{
		return super.removefavourites(common.convertJSON2EntityClass(s_request_JSON_body_content,favourites.class));
	}
	@RequestMapping(value = "/updatefavourites", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String,Object> updatefavourites(@RequestBody String s_request_JSON_body_content) 
	{
		return super.updatefavourites(common.convertJSON2EntityClass(s_request_JSON_body_content,favourites.class));
	}
	
}
