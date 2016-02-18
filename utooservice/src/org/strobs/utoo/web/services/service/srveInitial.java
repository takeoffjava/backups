package org.strobs.utoo.web.services.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.strobs.utoo.web.services.base.utoo;
import org.strobs.utoo.web.services.classes.lookup;
import org.strobs.utoo.web.services.dao.baseDAO;
import org.strobs.utoo.web.services.eClasses.driverapp_status;
import org.strobs.utoo.web.services.eClasses.drivers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class srveInitial extends utoo
{
	public Map<String,Object> lookup(lookup oLookup)
	{
		if(oLookup==null)
			 return map_response(false,response_codes.Invalid_JSON_Format.getCode()); 
		
		
		Map<String,Object> response_lookup=new HashMap<String,Object>();
		
		if(oLookup.isIs_response_codes()==true || oLookup.isIs_all()==true)
			response_lookup.put("response_code", baseDAO.getInstance().getEntityClassList("From response_codes ", 0));
		
		if(oLookup.isIs_server_details()==true || oLookup.isIs_all()==true)
			response_lookup.put("server_path", baseDAO.getInstance().getEntityClassList("From server_path order by server_path_id desc", 0));
		
		if(oLookup.isIs_car_model_details()==true || oLookup.isIs_all()==true)
			response_lookup.put("car_model", baseDAO.getInstance().getEntityClassList("From car_models where is_deleted=false order by car_model_id asc", 0));
		
		if(oLookup.isIs_car_features_details()==true || oLookup.isIs_all()==true)
			response_lookup.put("car_features", baseDAO.getInstance().getEntityClassList("From car_features where is_deleted=false order by cars_feature_ID desc", 0));
		
		if(oLookup.isIs_tariff_details()==true || oLookup.isIs_all()==true)
			response_lookup.put("tariff", baseDAO.getInstance().getEntityClassList("From tariff order by tariff_id desc", 0));
		
		if(oLookup.isIs_need_driver_list()==true || oLookup.isIs_all()==true)
			response_lookup.put("drivers", baseDAO.getInstance().getEntityClassList("From drivers where is_deleted=false order by driver_id desc",0));
		
		if(oLookup.isIs_need_unbook_reason_list()==true || oLookup.isIs_all()==true)
			response_lookup.put("unbook_reasons", baseDAO.getInstance().getEntityClassList("From unbook_reasons order by reason_ID desc",0));
		
		if(oLookup.isIs_need_blocked_list()==true || oLookup.isIs_all()==true)
			response_lookup.put("blocked_reasons", baseDAO.getInstance().getEntityClassList("From blocked_reasons order by blocked_reason_id desc",0));
		
		if(oLookup.isIs_need_location_list()==true || oLookup.isIs_all()==true)
			response_lookup.put("location", baseDAO.getInstance().getEntityClassList("From location order by location_id desc",0));
		
		if(oLookup.getAccess_id()!=null)
		{
			if(!baseDAO.getInstance().isValidAccessID(oLookup.getAccess_id(),2))
			{
				return map_response(false,5025);
			}
			long passengerID=baseDAO.getInstance().getLongKeyValue("select passenger_id from passenger where access_id='"+oLookup.getAccess_id()+"'");
			if(passengerID!=0)
			{
			response_lookup.put("blocked_drivers", baseDAO.getInstance().getEntityClassList("From blocked_drivers where obooking.opassenger.passenger_id="+passengerID+" and obooking.odrivers.is_deleted=false and status=1",0));
			
			response_lookup.put("favourites", baseDAO.getInstance().getEntityClassList("From favourites where opassenger.passenger_id="+passengerID+" and is_removed=false ",0));
			}
			else
			{
				return map_response(false,5025);
			}
		}
		
		return map_response(response_lookup,response_codes.Unable_to_reach_server.getCode()); 
	}
	
	public Map<String,Object> getServerDetails()
	{
		ResourceBundle BaseInfo = ResourceBundle.getBundle("utoo");
		Map<String,Object> response_details=new HashMap<String,Object>();
		response_details.put("baseDirectoryPath",BaseInfo.getString("baseDirectoryPath"));
		response_details.put("FTP_SERVER_HOST_NAME",BaseInfo.getString("FTP_SERVER_HOST_NAME"));
		response_details.put("FTP_USER_NAME",BaseInfo.getString("FTP_USER_NAME"));
		response_details.put("FTP_PASSWORD",BaseInfo.getString("FTP_PASSWORD"));
		response_details.put("IOS_USER",BaseInfo.getString("IOS_USER"));
		response_details.put("IOS_KEY_FILE",BaseInfo.getString("IOS_KEY_FILE"));
		response_details.put("ANDROID_KEY_CODE",BaseInfo.getString("ANDROID_KEY_CODE"));
		response_details.put("PASSENGER_ANDROID_KEY_CODE",BaseInfo.getString("PASSENGER_ANDROID_KEY_CODE"));
		response_details.put("QR_PDF_FILE",BaseInfo.getString("QR_PDF_FILE"));
		
	return response_details;	
	
	}
	public Map<String,Object> driverAppStatusService(driverapp_status odriverapp_status)
	{
		if(odriverapp_status==null)
			 return map_response(false,response_codes.Invalid_JSON_Format.getCode()); 
		if(baseDAO.getInstance().isValidAccessID(odriverapp_status.getOdrivers().getAccess_id(),1))
		{

		drivers odriver=baseDAO.getInstance().getdriverdetails(odriverapp_status.getOdrivers().getAccess_id());
		odriverapp_status.setOdrivers(odriver);
		if(baseDAO.getInstance().SaveEntityClass(odriverapp_status))
		{
			return map_response(true, odriverapp_status);
		}
		else
			return map_response(false, 5070);
		}
		else
			return map_response(false, 5025);
	}
	public Map<String,Object> getDriverAppStatus(driverapp_status odriverapp_status)
	{
		List<driverapp_status> driverappList=new ArrayList<driverapp_status>();
		if(odriverapp_status==null)
			 return map_response(false,response_codes.Invalid_JSON_Format.getCode()); 
		if(baseDAO.getInstance().isValidAccessID(odriverapp_status.getOdrivers().getAccess_id(),1))
		{

			driverappList=baseDAO.getInstance().getDriverAppStatusList("from driverapp_status where odrivers.access_id='"+odriverapp_status.getOdrivers().getAccess_id()+"'", 0);
			return (driverappList.isEmpty()) ?  map_response(false,5070) :  map_response(true,driverappList);
		
		}
		else
			return map_response(false, 5025);
	}

	
}
