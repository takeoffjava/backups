package org.strobs.utoo.web.services.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.strobs.utoo.web.services.base.hashPwd;
import org.strobs.utoo.web.services.classes.json_class;
import org.strobs.utoo.web.services.classes.sub_json_class;
import org.strobs.utoo.web.services.dao.baseDAO;
import org.strobs.utoo.web.services.service.srveTestingLayer;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController
@RequestMapping("/service/api/testing/*")
public class cTestingLayer extends srveTestingLayer
{
	@RequestMapping(value = "/hashpwd", method = RequestMethod.POST, headers="Accept=application/json")
	private Map<String,Object> hashpwd(@RequestBody String password) 
	{
		hashPwd oHashPwd=new hashPwd();
		Map<String,Object> map_response=new HashMap<String,Object>();
		map_response.put("Hash_Password", oHashPwd.getHashForPassword(password));
		map_response.put("Hash_Salt", oHashPwd.getSalt());
		map_response.put("Total_Hash_Password", oHashPwd.getTotalHash(map_response.get("Hash_Password").toString(),map_response.get("Hash_Salt").toString()));
		return map_response;
	}
	@RequestMapping(value = "/getotp", method = RequestMethod.POST, headers="Accept=application/json")
	private Map<String,Object> getotp(@RequestBody String mobile) 
	{
		return super.getOTPDB(mobile);
	}
	@RequestMapping(value = "/jsonclass", method = RequestMethod.POST, headers="Accept=application/json")
	private String jsonclass(@RequestBody String mobile) 
	{
		String result="";
		sub_json_class oSub_json_class=new sub_json_class();
		oSub_json_class.setSub_id(1);
		oSub_json_class.setSub_name("CPU");
		
		json_class oJson_class=new json_class();
		oJson_class.setId(1);
		oJson_class.setName("ArunS");
		oJson_class.setMachine_code("4SA84");
		oJson_class.setMIN("Y2");
		oJson_class.setOsub_json_class(oSub_json_class);
		try
		{
			ObjectMapper mapper = new ObjectMapper();
		    mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
		    mapper.disable(MapperFeature.USE_ANNOTATIONS);
		    result = mapper.writeValueAsString(oJson_class);
		}
		catch(Exception exp)
		{
	  	  	baseDAO.getInstance().logAnError("cInitial", baseDAO.getInstance().stackTraceToString(exp));
		}
		
		return result;
	}
}
