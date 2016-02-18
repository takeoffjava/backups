package org.strobs.utoo.web.services.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.strobs.utoo.web.services.base.pubnubnotify;
import org.strobs.utoo.web.services.base.utoo;
import org.strobs.utoo.web.services.classes.block_drivers;
import org.strobs.utoo.web.services.classes.ices;
import org.strobs.utoo.web.services.classes.rating;
import org.strobs.utoo.web.services.dao.baseDAO;
import org.strobs.utoo.web.services.eClasses.blocked_drivers;
import org.strobs.utoo.web.services.eClasses.blocked_passengers;
import org.strobs.utoo.web.services.eClasses.booking;
import org.strobs.utoo.web.services.eClasses.ice;
import org.strobs.utoo.web.services.eClasses.passenger;
public class srvePrivacy extends utoo
{
	pubnubnotify opubnubnotify=new pubnubnotify();
	public Map<String,Object> ratingPoints(rating oRating)
	{
		if(oRating==null)
			 return map_response(false,5000); //Invalid JSON Format for Class
		
	if(oRating.getBooking_number()==null)
		return map_response(false,5030);
	if(oRating.getDriver_access_id()==null)
		return map_response(false,5025);
	if(!baseDAO.getInstance().isValidAccessID(oRating.getDriver_access_id(),1))
	{
		return map_response(false,5066);
	}	if(oRating.getRating_points()>5)
		return map_response(false,5036);

	float response_rate=baseDAO.getInstance().giveDriverRating(oRating);
		if(response_rate==-1)
			return map_response(false,5005); //Server is busy...
		if(response_rate==-2)
			return map_response(false,5025); //Invalid Access ID
		return map_response(true,response_rate); 
	}
	
	public Map<String,Object> passengerRating(rating oRating)
	{
		if(oRating==null)
			 return map_response(false,5000); //Invalid JSON Format for Class
		
	if(oRating.getBooking_number()==null)
		return map_response(false,5030);
	if(oRating.getPassenger_access_id()==null)
		return map_response(false,5025);
	if(oRating.getRating_points()>5)
		return map_response(false,5036);
	if(!baseDAO.getInstance().isValidAccessID(oRating.getPassenger_access_id(),2))
	{
		return map_response(false,5066);
	}
	float response_rate=baseDAO.getInstance().givePassengerRating(oRating);
		if(response_rate==-1)
			return map_response(false,5005); //Server is busy...
		if(response_rate==-2)
			return map_response(false,5025); //Invalid Access ID
		return map_response(true,response_rate); 
	}
	
	public Map<String, Object> blockDriver(block_drivers oBlockDrivers) 
	{
		if (oBlockDrivers == null)
			return map_response(false, 5000); // Invalid JSON Format for Class

		booking oBooking=new booking();
		oBooking=baseDAO.getInstance().getBooking(oBlockDrivers.getBooking_number());
		String channel="";
		if(oBooking==null)
			return map_response(false, 5030); // Invalid Booking Number
		blocked_drivers oBlocked_drivers=new blocked_drivers();
		oBlocked_drivers.setObooking(oBooking);
		oBlocked_drivers.setBlock_Reason(oBlockDrivers.getBlock_reason());
		oBlocked_drivers.setStatus(oBlockDrivers.getStatus());
		long blocked_id=baseDAO.getInstance().getLongKeyValue("select block_id from blocked_drivers where obooking.odrivers.driver_id ="+oBooking.getOdrivers().getDriver_id()+" and obooking.opassenger.passenger_id="+oBooking.getOpassenger().getPassenger_id()+" and obooking.odrivers.is_deleted=false");
		if(blocked_id!=0)
		{
			if(baseDAO.getInstance().executeHQLQuery("update blocked_drivers set status="+oBlockDrivers.getStatus()+" , Block_Reason='"+oBlockDrivers.getBlock_reason()+"' where block_id="+blocked_id)){
				if(!srveAdmin.notify_user.isEmpty()){
				opubnubnotify.publish(srveAdmin.notify_user.get(0).toString(),"Passenger block this driver "+oBlocked_drivers.getObooking().getOdrivers().getDriver_name()+""
						+ " for "+oBlocked_drivers.getBlock_Reason(),false);
				channel=srveAdmin.notify_user.get(0).toString();
				srveAdmin.notify_user.remove(srveAdmin.notify_user.get(0));
				srveAdmin.notify_user.add(channel);
				}
				return map_response(true, oBlockDrivers.getStatus()==1?5029:5052); 
		}// You have been blocked the driver.
			else
				return map_response(false, 5005); // "Server is busy..."
		}
		else{
		if (baseDAO.getInstance().SaveEntityClass(oBlocked_drivers)){
			if(!srveAdmin.notify_user.isEmpty()){
			opubnubnotify.publish(srveAdmin.notify_user.get(0).toString(),"Passenger block this driver "+oBlocked_drivers.getObooking().getOdrivers().getDriver_name()+""
					+ " for "+oBlocked_drivers.getBlock_Reason(),false);
			channel=srveAdmin.notify_user.get(0).toString();
			srveAdmin.notify_user.remove(srveAdmin.notify_user.get(0));
			srveAdmin.notify_user.add(channel);
			}
			return map_response(true, oBlockDrivers.getStatus()==1?5029:5052); // You have been blocked the driver.
			
		}
		else
			return map_response(false, 5005); // "Server is busy..."
		}
	}
	public Map<String, Object> 	blockPassenger(block_drivers oBlockPassengers) 
	{
		if (oBlockPassengers == null)
			return map_response(false, 5000); // Invalid JSON Format for Class

		booking oBooking=new booking();
		oBooking.setBooking_id(baseDAO.getInstance().getLongKeyValue("Select booking_id from booking where booking_number='" + oBlockPassengers.getBooking_number() + "' order by booking_id desc"));
		
		if(oBooking.getBooking_id()==0)
			return map_response(false, 5030); // Invalid Booking Number
		
		blocked_passengers oBlocked_passengers=new blocked_passengers();
		oBlocked_passengers.setObooking(oBooking);
		oBlocked_passengers.setBlock_Reason(oBlockPassengers.getBlock_reason());
		oBlocked_passengers.setStatus(oBlockPassengers.getStatus());
		
		if (baseDAO.getInstance().SaveEntityClass(oBlocked_passengers))
			return map_response(true, oBlockPassengers.getStatus()==1?5043:5053); // You have been blocked the Passenger.
		else
			return map_response(false, 5005); // "Server is busy..."
	}
	
	
	public Map<String, Object> getBlockedDriverForPassenger(passenger oPassenger) 
	{
		if (oPassenger == null) 
			return map_response(false, 5006);
		if(!baseDAO.getInstance().isValidAccessID(oPassenger.getAccess_id(),2))
		{
			return map_response(false,5025);
		}
		return map_response(baseDAO.getInstance().getEntityClassList("from blocked_drivers where obooking.opassenger.access_id='"+ oPassenger.getAccess_id() +"' and obooking.odrivers.is_deleted=false and status=1", 0),5006);
	}
	public Map<String,Object> deactivateMyAccount(passenger oPassenger)
	{
		if(oPassenger==null)
			 return map_response(false,5000); //Invalid JSON Format for Class
		if(!baseDAO.getInstance().isValidAccessID(oPassenger.getAccess_id(),2))
		{
			return map_response(false,5025);
		}
		if(baseDAO.getInstance().executeHQLQuery("update passenger set is_active=2,updated_on=now() where access_id='" + oPassenger.getAccess_id() + "'"))
			 return map_response(false,5028); //Account has been deactivated successfully.
		else
			 return map_response(false,5005); //Server Busy
	}
	public Map<String,Object> latlonUpdate(passenger oPassenger)
	{
		if(oPassenger==null)
			 return map_response(false,5000); //Invalid JSON Format for Class
		if(!baseDAO.getInstance().isValidAccessID(oPassenger.getAccess_id(),2))
		{
			return map_response(false,5025);
		}
		
		if(baseDAO.getInstance().executeHQLQuery("update passenger set latitude=" + oPassenger.getLatitude() + ",longitude=" + oPassenger.getLongitude() + ",updated_on=now() where access_id='" + oPassenger.getAccess_id() + "'"))
			 return map_response(false,5020); //Updated Successfully
		else
			 return map_response(false,5005); //Server Busy
	}
	public Map<String,Object> addICE(ices oIces)
	{
		if(oIces==null)
			 return map_response(false,5000); //Invalid JSON Format for Class
		
		if(oIces.getIces_list()==null)
			 return map_response(false,5039); //Ice contacts list empty
		
		if(oIces.getIces_list().size()==0)
			return map_response(false,5039); //Ice contacts list empty
		
		/*if(baseDAO.getInstance().getIntKeyValue("Select count(*) From ice where is_deleted<>1 and opassenger.access_id='"+ oIces.getAccess_id() +"'")>5)
		    return map_response(false,5040); //Only allowed 5 contacts
*/		
		if(!baseDAO.getInstance().isValidAccessID(oIces.getPassenger_access_id(),2))
		{
			return map_response(false,5025);
		}
		passenger oPassenger=baseDAO.getInstance().getPassengers("From passenger where access_id='" + oIces.getPassenger_access_id()+"'");
		
		if(oPassenger==null)
		    return map_response(false,5025); //Invalid Driver Id
			
		Map<Long,Integer> avoid_dup_ice=new HashMap<Long,Integer>();
		String SMS_GATEWAY_RESPONSE="";
		boolean bStatus=false;
		List<ice> lst_ice=new ArrayList<ice>();
		for(ice oIce : oIces.getIces_list())
		{
			if(!avoid_dup_ice.containsKey(oIce.getMobile()))
			{
				//String SMS_GATEWAY_RESPONSE=SendMessage(oIce.getMobile(),"Your authentication code is " + 55555);
				SMS_GATEWAY_RESPONSE="Message GID=";
				if(SMS_GATEWAY_RESPONSE.contains("Message GID="))
					bStatus=true;
				else if(SMS_GATEWAY_RESPONSE.contains("Invalid Mobile Number"))
					bStatus=false;
				else
					bStatus=false;
			}
			else
				bStatus=false;
			
			avoid_dup_ice.put(oIce.getMobile(),1);
			
			oIce.setOpassenger(oPassenger);
			lst_ice.add(oIce);
		}
		if(!bStatus)
			return map_response(false,5044);
		
		bStatus=false;
		for(ice ices: lst_ice)
		{
			if(baseDAO.getInstance().SaveEntityClass(ices))
				bStatus=true;
		}
		if(!bStatus)
			return map_response(false,5044);
		else
			return map_response(true,lst_ice);
	}
	public Map<String,Object> getICE(ice oIce)
	{
		if(oIce==null)  
			return map_response(false,5000);
		
		if(oIce.getPassenger_access_id()==null)
			return map_response(false,5025);
		if(!baseDAO.getInstance().isValidAccessID(oIce.getPassenger_access_id(),2))
		{
			return map_response(false,5025);
		}
		return map_response(baseDAO.getInstance().getEntityClassList("From ice where is_deleted<>1 and opassenger.access_id='"+ oIce.getPassenger_access_id() +"'",0),5006);
	}
	public Map<String,Object> removeICE(ice oIce)
	{
		if(oIce==null)
			 return map_response(false,5000); //Invalid JSON Format for Class
		if(!baseDAO.getInstance().isValidAccessID(oIce.getPassenger_access_id(),2))
		{
			return map_response(false,5025);
		}
		
		if(baseDAO.getInstance().executeHQLQuery("update ice set is_deleted=1,updated_on=now() where mobile=" + oIce.getMobile()+" and opassenger.passenger_id=(select passenger_id from passenger where access_id='"+oIce.getPassenger_access_id()+"')"))
		{
			if(oIce.getPrimary_mobile()==0)
				return map_response(true,5042); 
			
			if(baseDAO.getInstance().executeHQLQuery("update ice set is_primary=1,updated_on=now() where mobile=" + oIce.getPrimary_mobile()+" and opassenger.passenger_id=(select passenger_id from passenger where access_id='"+oIce.getPassenger_access_id()+"')"))
				return map_response(true,5042); 
			else
				return map_response(false,5005); //Server Busy
		}
		else
			 return map_response(false,5003); //Server Busy
	}
	public Map<String,Object> changePrimaryICE(ice oIce)
	{
		if(oIce==null)
			 return map_response(false,5000); //Invalid JSON Format for Class
		if(!baseDAO.getInstance().isValidAccessID(oIce.getPassenger_access_id(),2))
		{
			return map_response(false,5025);
		}
		if(baseDAO.getInstance().executeHQLQuery("update ice set is_primary=1,updated_on=now() where mobile=" + oIce.getPrimary_mobile()+" and opassenger.passenger_id=(select passenger_id from passenger where access_id='"+oIce.getPassenger_access_id()+"')"))
		{
			if(baseDAO.getInstance().executeHQLQuery("update ice set is_primary=0,updated_on=now() where mobile=" + oIce.getMobile()+" and opassenger.passenger_id=(select passenger_id from passenger where access_id='"+oIce.getPassenger_access_id()+"')"))
				return map_response(true,5041); 
			else
				return map_response(false,5005); //Server Busy
		}
		else
			 return map_response(false,5025); //Server Busy
	}
	public Map<String,Object> validPromoCode(passenger oPassenger)
	{
		if(oPassenger==null)
			 return map_response(false,5000); //Invalid JSON Format for Class
		if(!baseDAO.getInstance().isValidAccessID(oPassenger.getAccess_id(),2))
		{
			return map_response(false,5025);
		}
		if(baseDAO.getInstance().getIntKeyValue("Select count(*) from passenger where access_id='" + oPassenger.getAccess_id() + "' and promo_code='"+  oPassenger.getPromo_code() + "'")==1)
			return map_response(true,5045);
		return map_response(false,5046);
	}
	public Map<String,Object> notifyICE(ice oIce)
	{
		if(oIce==null)
			 return map_response(false,5000); // 
		String siteUrl ="http:///www.utootaxi.com/utooadmin/track?" + oIce.getBooking_number();
		String SMS_GATEWAY_RESPONSE=SendMessage(oIce.getPrimary_mobile()," This is to inform your friend "+oIce.getName()+" is started ride with UTOO service , you can track his ride by clicking on "+siteUrl);
		
		if(SMS_GATEWAY_RESPONSE.contains("Message GID="))
				return map_response(true, 5048); //Notify ICE has been successfully
		else if(SMS_GATEWAY_RESPONSE.contains("Invalid Mobile Number"))
			return map_response(false,5003); //"Invalid Mobile Number"
		else
			return map_response(false,5004); //"SMS server is busy..."
	}
}
