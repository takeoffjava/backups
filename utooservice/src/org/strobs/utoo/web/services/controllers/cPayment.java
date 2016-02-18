package org.strobs.utoo.web.services.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.strobs.utoo.web.services.base.common;
import org.strobs.utoo.web.services.eClasses.passenger;
import org.strobs.utoo.web.services.service.srvePayment;

@RestController
@RequestMapping("/service/api/payment/*")
public class cPayment extends srvePayment {
	@RequestMapping(value = "/cashpayment", method = RequestMethod.POST, headers="Accept=application/json")
	public  @ResponseBody Map<String,Object> cashpayment(@RequestBody String s_request_JSON_body_content) 
	{
		//return super.cash_Payment(common.convertJSON2EntityClass(s_request_JSON_body_content,payment_transaction.class));
		return super.payment_wallet(common.convertJSON2EntityClass(s_request_JSON_body_content,passenger.class));
	}
	@RequestMapping(value = "/get_cashpayment", method = RequestMethod.POST, headers="Accept=application/json")
	public  @ResponseBody Map<String,Object> get_cashpayment(@RequestBody String s_request_JSON_body_content) 
	{
		//return super.getpayment(common.convertJSON2EntityClass(s_request_JSON_body_content,payment_transaction.class));
		return super.getpayment_wallet(common.convertJSON2EntityClass(s_request_JSON_body_content,passenger.class));
	}
}
