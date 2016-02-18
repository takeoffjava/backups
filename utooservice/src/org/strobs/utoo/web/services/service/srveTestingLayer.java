package org.strobs.utoo.web.services.service;

import java.util.Map;

import org.strobs.utoo.web.services.base.utoo;
import org.strobs.utoo.web.services.dao.baseDAO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class srveTestingLayer extends utoo
{
	
	public Map<String,Object> getOTPDB(String mobile)
	{
		return map_response(true,baseDAO.getInstance().getStringKeyValue("Select otp from passenger_otp where opassenger.mobile=" + mobile + " order by passenger_otp_id desc"));
	}
}
