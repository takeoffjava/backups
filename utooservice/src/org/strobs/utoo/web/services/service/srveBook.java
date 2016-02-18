package org.strobs.utoo.web.services.service;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.Future;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.json.JSONObject;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.strobs.utoo.web.services.base.pubnubnotify;
import org.strobs.utoo.web.services.classes.bookings;
import org.strobs.utoo.web.services.dao.baseDAO;
import org.strobs.utoo.web.services.eClasses.booking;
import org.strobs.utoo.web.services.eClasses.drivers;
import org.strobs.utoo.web.services.eClasses.passenger;
import org.strobs.utoo.web.services.eClasses.qrcode;
import org.strobs.utoo.web.services.pservice.GPSServiceInf;
import org.strobs.utoo.web.services.pservice.MailServerApiInf;

public class srveBook extends QuartzJob
{
	@Autowired
	private GPSServiceInf reportService;
	@Autowired
	private MailServerApiInf mailService;
	
	
	pubnubnotify opubnubnotify=new pubnubnotify();
	public Map<String,Object> addBooks(bookings oBookings)
	{
		if(oBookings==null)  
			return map_response(false,5000);
		
		Map<String,Object> map_booked_status=new HashMap<String,Object>();
		Map<String,Object> map_response_status=new HashMap<String,Object>();;
		Map<String,String> map_accessID=new HashMap<String,String>();;
		String groupBookKey=getRandomWord(10);
		int nIdx=1;
		for(booking oBooking : oBookings.getBook_list())
		{
			drivers oDrivers=null;
			passenger oPassenger=baseDAO.getInstance().
					getPassengers("From passenger where access_id='" + oBooking.getOpassenger().getAccess_id() + "'");
			
			if(oBooking.getBooked_dat()==null)
			{
				return map_response(false,5000);
			}
			
			if(oPassenger==null)
			{
				oBookings=null;
				map_booked_status.clear();
				map_response_status.clear();
				map_accessID.clear();
				map_booked_status=null;
				map_response_status=null;
				map_accessID=null;
				groupBookKey=null;
				nIdx=0;
				return map_response(false,5025);
			}
			if(oBooking.getOpassenger()!=null){
			if(!baseDAO.getInstance().isValidAccessID(oBooking.getOpassenger().getAccess_id(),2))
			{
				return map_response(false,5025);
			}
			}
		    if((oBooking.getBooking_type()==1 || oBooking.getBooking_type()==5))
			{
		    	if(baseDAO.getInstance().getLongKeyValue("select booking_id from booking where booking_type in (1,5) and status not in (3,4) and opassenger.access_id= '"+oBooking.getOpassenger().getAccess_id()+"'")!=0)
		    	{
		    		return map_response(false , 5068);
		    	}
		    	if(oBooking.getDriver_list().size()!=0)
		    	{
		    	  oDrivers=getNearestDriver(oBooking.getDriver_list());
		    	}
		    	else
		    	{

					oBookings=null;
					map_booked_status.clear();
					map_response_status.clear();
					map_accessID.clear();
					map_booked_status=null;
					map_response_status=null;
					map_accessID=null;
					groupBookKey=null;
					nIdx=0;
					return map_response(false,5035);
		    	}
				
				if(oDrivers==null)
				{
					oBookings=null;
					map_booked_status.clear();
					map_response_status.clear();
					map_accessID.clear();
					map_booked_status=null;
					map_response_status=null;
					map_accessID=null;
					groupBookKey=null;
					nIdx=0;
					return map_response(false,5035);
				}
				oBooking.setOdrivers(oDrivers);
			}
			else
				oBooking.setStatus(5);
			
			if(oBooking.getBooking_type()==6)
			{
				if(oBooking.getQrcode_unique_number()==null || oBooking.getQrcode_unique_number()=="")
				{
					oBookings=null;
					map_booked_status.clear();
					map_response_status.clear();
					map_accessID.clear();
					map_booked_status=null;
					map_response_status=null;
					map_accessID=null;
					groupBookKey=null;
					nIdx=0;
					return map_response(false,5000);
				}
				qrcode objQrcode= null;
				objQrcode = baseDAO.getInstance().getQRdetails("from qrcode where qrcode_unique_number='"+oBooking.getQrcode_unique_number()+"'");
				if(objQrcode==null)
				{
					oBookings=null;
					map_booked_status.clear();
					map_response_status.clear();
					map_accessID.clear();
					map_booked_status=null;
					map_response_status=null;
					map_accessID=null;
					groupBookKey=null;
					nIdx=0;
					objQrcode=null;
					return map_response(false, 5056); // Invalid QR Code
				}
				
				else if(objQrcode.getQr_status()==0)
				{
					if(baseDAO.getInstance().executeHQLQuery("update qrcode set qr_status=1 where qrcode_unique_number='"+oBooking.getQrcode_unique_number()+"'"))
					{
						map_booked_status.put("qr_status", 1);	
					}
					
				}
				else if(objQrcode.getQr_status()==1)
				{
					oBookings=null;
					map_booked_status.clear();
					map_response_status.clear();
					map_accessID.clear();
					map_booked_status=null;
					map_response_status=null;
					map_accessID=null;
					groupBookKey=null;
					nIdx=0;
					objQrcode=null;
					return map_response(false, 5058); // QR Code is Invalid
				}
				else
				{
					oBookings=null;
					map_booked_status.clear();
					map_response_status.clear();
					map_accessID.clear();
					map_booked_status=null;
					map_response_status=null;
					map_accessID=null;
					groupBookKey=null;
					nIdx=0;
					objQrcode=null;
					return map_response(false, 5056); // Invalid QR Code
				}
			}
		    
			oBooking.setOpassenger(oPassenger);
			oBooking.setBooking_number(UUID.randomUUID().toString().replace("-",""));
			oBooking.setPbr_number(getRandomNumber(6));
			oBooking.setGroup_book_key(groupBookKey);
			oBooking.setBooked_source(IsNullContentOrSetDefault(oBooking.getBooked_source(),""));
			if(baseDAO.getInstance().SaveEntityClass(oBooking))
			{
				
				/*
				 * Push Notification to Driver
				 */ 
					sendBookingNotification(oBooking);
					
					
					map_response_status=new HashMap<String,Object>();
					map_accessID=new HashMap<String,String>();
					map_response_status.put("mobile" + nIdx, oBooking.getBto_mobile()+"");
					map_response_status.put("booking_number", oBooking.getBooking_number());
					map_response_status.put("pbr_number", oBooking.getPbr_number());
					map_response_status.put("group_book_key", oBooking.getGroup_book_key());
				
					map_response_status.put("status",""+1);
					
					if(oBooking.getBooking_type()==1 || oBooking.getBooking_type()==5)
					{
						map_accessID.put("access_id", oBooking.getOdrivers().getAccess_id());
						map_response_status.put("oDrivers", map_accessID);
						updateDriverStatus(oDrivers.getAccess_id());
					}
					
					/*String createdON=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());*/
					
					DateFormat formatterIST = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					Date currentDate= new Date();
					formatterIST.setTimeZone(TimeZone.getTimeZone("IST"));
					String createdON=formatterIST.format(currentDate);
					
					map_booked_status.put("book" + nIdx,map_response_status);
					String SMS_GATEWAY_RESPONSE=SendMessage(oBooking.getOpassenger().getMobile(),"Your Booking number " + oBooking.getPbr_number() + " is confirmed on "+  createdON +". Happy to have you Onboard");
					if(SMS_GATEWAY_RESPONSE.contains("Invalid Mobile Number"))
					{
						baseDAO.getInstance().logAnError("srveBook","Message Not Send Invalid Mobile Number");
						SMS_GATEWAY_RESPONSE=null;
					}
			}
			else
			{
				map_response_status=new HashMap<String,Object>();
				map_response_status.put("mobile" + nIdx, oBooking.getBto_mobile()+"");
				map_response_status.put("status",""+0);
				map_booked_status.put("book" + nIdx,map_response_status);
			}
			nIdx++;	
		
		}  
		oBookings=null;
	/*	map_accessID.clear();
		map_accessID=null;*/
		groupBookKey=null;
		nIdx=0;
		return map_response(map_booked_status,5005); //
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getDriversByLocation(bookings oBookings) 
	{
		
		if(oBookings==null)  
			return map_response(false,5000);
		
		String usedDriverAccessIDs="",queryCommand="";
		String groupBookKey=getRandomWord(10);
		Map<String,Object> map_booked_status=new HashMap<String,Object>();
		Map<String,Object> map_response_status=new HashMap<String,Object>();
		Map<String, String> map_accessID=new HashMap<String,String>();
		Map<String,Object> response_map= new HashMap<String, Object>();
		int nIdx=1;
	    passenger oPassenger=null;
	 //   Map<String,Object> driverCheck=new HashMap<String,Object>();
		for(booking oBooking : oBookings.getBook_list())
		{
			if(oBooking.getOpassenger()==null)
			{
				return map_response(false,5025);
			}
			if(oPassenger==null)
			{
	         oPassenger=baseDAO.getInstance().getPassengers("From passenger where access_id='" + oBooking.getOpassenger().getAccess_id() + "'");
	         if(oPassenger!=null)
	         {
				if(!baseDAO.getInstance().getbookinghistory("From booking where opassenger.access_id='"+oPassenger.getAccess_id()+"' and booking_type=2 and status not in (3,4)", 0).isEmpty())
				{
					return map_response(false,5071);
				}
	         }
			}

			  if(oPassenger==null)
		  	  {
				groupBookKey=null;
				map_accessID.clear();
				map_accessID=null;
				usedDriverAccessIDs=null;
				queryCommand=null;
				nIdx=0;
				return map_response(false,5025);
				}
			drivers oDriver=null;
			double maxIncrement=5000;
			Map<String,Object> listMap=new HashMap<String,Object>();
				/*oDriver=baseDAO.getInstance().getdrivers("FROM drivers where status=0 " + queryCommand + " and is_deleted=false order by (abs(abs(latitude-"+
					    oBooking.getDeparture_latitude()+")+abs(longitude-"+oBooking.getDeparture_longitude()+")))asc",1);*/
				
				String requestGPSJson="{\"maxDistance\":"+maxIncrement+",\"latitude\":\""+oBooking.getDeparture_latitude()+"\",\"longitude\":\""+oBooking.getDeparture_longitude()+"\"}";
				listMap=ResponseData(requestGPSJson, "getNearestDriver", "POST","");
				List<Map<String,Object>> driverList=new ArrayList<Map<String,Object>>();
				
				if(listMap!=null )
				{
					if(!listMap.isEmpty())
					{
						int status=(int) listMap.get("status");
						if(status==1)
						{
							driverList=(List<Map<String, Object>>) listMap.get("driverList");
							if(!driverList.isEmpty())
							{
								inner : for(int i=0;i<driverList.size();i++)
								{
								oDriver=baseDAO.getInstance().getdrivers("from drivers where driver_id="+driverList.get(i).get("driverID")+"" + queryCommand + " and is_deleted=false and status=0", 0);
								if(oDriver!=null)
								{
									break inner;
								}
								
								}
								}
						}
						/*else
						{
							response_map = new HashMap<String,Object>();
						response_map.put("mobile", oBooking.getBto_mobile());
						response_map.put("error", 5035);
						response_map.put("status", 0);
						map_booked_status.put("book" + nIdx,response_map);
						nIdx++;}*/
					}
				}
		
			if(oDriver==null)
			{
				response_map = new HashMap<String,Object>();
				response_map.put("mobile", oBooking.getBto_mobile());
				response_map.put("error", 5035);
				response_map.put("status", 0);
				map_booked_status.put("book" + nIdx,response_map);
				nIdx++;
				
			}
			else
			{
				usedDriverAccessIDs +="'" + oDriver.getAccess_id() + "',";
				queryCommand=" and access_id not in(" + usedDriverAccessIDs.substring(0,usedDriverAccessIDs.length()-1) + ")";
				oBooking.setOdrivers(oDriver);
				oBooking.setOpassenger(oPassenger);
				oBooking.setBooking_number(UUID.randomUUID().toString().replace("-",""));
				oBooking.setPbr_number(getRandomWord(6));
				oBooking.setGroup_book_key(groupBookKey);
				if(baseDAO.getInstance().SaveEntityClass(oBooking))
				{
					
						sendBookingNotification(oBooking);
						map_response_status=new HashMap<String,Object>();
						map_accessID=new HashMap<String,String>();
						map_accessID.put("access_id", oBooking.getOdrivers().getAccess_id());
						map_response_status.put("mobile", oBooking.getBto_mobile());
						map_response_status.put("booking_number", oBooking.getBooking_number());
						map_response_status.put("pbr_number", oBooking.getPbr_number());
						map_response_status.put("group_book_key", oBooking.getGroup_book_key());
						map_response_status.put("oDrivers", map_accessID);
						map_response_status.put("status",""+1);
						map_booked_status.put("book" + nIdx,map_response_status);
						baseDAO.getInstance().executeHQLQuery("update drivers set status=1 where access_id='" + oDriver.getAccess_id() + "'");
						String SMS_GATEWAY_RESPONSE=SendMessage(oBooking.getOpassenger().getMobile(),"Congrats! Your Friend had booked a Cab through UTOO.  Your Confirmation Number " + oBooking.getPbr_number() + ", Driver Name : " + oDriver.getDriver_name() + " (" + oDriver.getMobile() +  ") . Pickup Time : " + baseDAO.getInstance().utcToISTFormat(oBooking.getBooked_dat()) + ". Pickup Location : " + oBooking.getBooked_source() + ". Happy UTOO Ride.");
						String SMS_GATEWAY_RESPONSE_FRIEND=SendMessage(oBooking.getBto_mobile(),"Congrats! Your Friend "+oBooking.getOpassenger().getPassenger_name()+" had booked a Cab to you through UTOO.  Your Confirmation Number " + oBooking.getPbr_number() + ", Driver Name : " + oDriver.getDriver_name() + " (" + oDriver.getMobile() +  ") . Pickup Time : " + baseDAO.getInstance().utcToISTFormat(oBooking.getBooked_dat()) + ". Pickup Location : " + oBooking.getBooked_source() + ". Happy UTOO Ride.");
						if(SMS_GATEWAY_RESPONSE.contains("Invalid Mobile Number"))
							baseDAO.getInstance().logAnError("srveBook","Message Not Send Invalid Mobile Number");
						
						if(SMS_GATEWAY_RESPONSE_FRIEND.contains("Invalid Mobile Number"))
							baseDAO.getInstance().logAnError("srveBook","Message Not Send Invalid Mobile Number For Friend");
				}
				else
				{
					response_map=new HashMap<String,Object>();
					response_map.put("mobile", oBooking.getBto_mobile());
					response_map.put("error", 5035);
					response_map.put("status", 0);
					map_booked_status.put("book" + nIdx,response_map);
					
				}
				 nIdx++;
				}			
				
		}
		groupBookKey=null;
		usedDriverAccessIDs=null;
		queryCommand=null;
		nIdx=0;
		return (map_booked_status != null)?map_response(true, map_booked_status):map_response(false, 5006);
	}
	
	public Map<String,Object> unBooked(booking oBooking)
	{
		String channel="";
		if(oBooking==null)  
			return map_response(false,5000);
		
		oBooking=baseDAO.getInstance().getBooking(oBooking.getBooking_number());
		if(oBooking==null)  
			return map_response(false,5030);
		if(oBooking.getOdrivers()!=null){
		if(!baseDAO.getInstance().isValidAccessID(oBooking.getOdrivers().getAccess_id(),1))
		{
			return map_response(false,5025);
		}}
		if(oBooking.getStatus()==2)
		{   
			oBooking=null;
			return map_response(false,5062);}
		if(baseDAO.getInstance().executeHQLQuery("update booking set status=4 , reason_ID="+oBooking.getReason_ID()+" where booking_id=" + oBooking.getBooking_id()))
		{
			if(oBooking.getOdrivers()!=null)
				baseDAO.getInstance().executeHQLQuery("update drivers set status=0 where access_id='" + oBooking.getOdrivers().getAccess_id() + "'");
			
			String SMS_GATEWAY_RESPONSE=SendMessage(oBooking.getOpassenger().getMobile(),"As per your request, Your Booking Ref-No:"+ oBooking.getPbr_number() +" is cancelled.");
			if(SMS_GATEWAY_RESPONSE.contains("Invalid Mobile Number"))
			{
				baseDAO.getInstance().logAnError("srveBook","Message Not Send Invalid Mobile Number");
			}
			oBooking.setStatus(4);
			sendCancelBookingNotification(oBooking);
			if(!srveAdmin.notify_user.isEmpty()){
				opubnubnotify.publish(srveAdmin.notify_user.get(0).toString(),oBooking.getOpassenger().getPassenger_name()+" Cancelled the Booking.",false);
				channel=srveAdmin.notify_user.get(0).toString();
				srveAdmin.notify_user.remove(srveAdmin.notify_user.get(0));
				srveAdmin.notify_user.add(channel);
				}
			oBooking=null;
			SMS_GATEWAY_RESPONSE=null;
			return map_response(true,5051);
		}
		return map_response(false,5005);
	}
	public Map<String, Object> getBookingHistory(String JFaccessID) 
	{
		try
		{
		//	mailService.SendMailToServer("karthika.ravindran@strobilanthes.com","TEST","TEST");
			if(JFaccessID==null)
			{
				return map_response(false, 5000);
			}
			
			JSONObject requestJson=new JSONObject(JFaccessID);
			JFaccessID=null;
			List<booking> bookingList= null;
		   if(!requestJson.has("access_id")){
				return map_response(false,5025);
			}
			else{
				if(!baseDAO.getInstance().isValidAccessID( requestJson.getString("access_id"),2))
				{
					return map_response(false,5025);
				}
				bookingList=baseDAO.getInstance().getbookinghistory("from booking where opassenger.access_id ='"+ requestJson.getString("access_id") + "' order by booking_id desc", 0);
				if(!bookingList.isEmpty())
				return map_response(true, bookingList);
				else
					return map_response(false, 5006);
			}
           }
			catch(Exception expBookingHistory)
			{
				JFaccessID=null;
		  	  	baseDAO.getInstance().logAnError("srveAdmin", baseDAO.getInstance().stackTraceToString(expBookingHistory));
				return map_response(false,5005);
			}
	}
	
    public boolean sendBookingNotification(booking obooking) 
    {
    	@SuppressWarnings("unused")
		Future<String> app_push_async=null;
    	Map<String, Object> push_map =null;
    	try
    	{
    		push_map = new HashMap<String, Object>();
    		push_map.put("passenger_access_id", obooking.getOpassenger().getAccess_id());
    		if(obooking.getBooking_type()==2)
    		{
    			push_map.put("passenger_name", obooking.getBto_name());
    			push_map.put("passenger_mobile", obooking.getBto_mobile());
    		}
    		else
    		{
			push_map.put("passenger_name", obooking.getOpassenger().getPassenger_name());
			push_map.put("passenger_mobile", obooking.getOpassenger().getMobile());
    		}
			push_map.put("pickup_passenger_lat", obooking.getDeparture_latitude());
			push_map.put("pickup_passenger_lon", obooking.getDeparture_longitude());
			push_map.put("booked_passenger_lat", obooking.getPassenger_latitude());	
			push_map.put("booked_passenger_lon", obooking.getPassenger_longitude());
			push_map.put("pickup_time", obooking.getBooked_dat());
			push_map.put("booking_number", obooking.getBooking_number());
			push_map.put("booking_type", obooking.getBooking_type());
			push_map.put("book_status", obooking.getStatus());
			push_map.put("booked_source", obooking.getBooked_source());
			push_map.put("booked_destination", obooking.getBooked_destination());
		    push_map.put("drop_passenger_lat", obooking.getReaching_latitude());
			push_map.put("drop_passenger_long", obooking.getReaching_longitude());
			push_map.put("pbr_number", obooking.getPbr_number());
			
			if(obooking.getBooking_type()==1 || obooking.getBooking_type()==2 || obooking.getBooking_type()==5)
			{
				app_push_async=reportService.SendMessageToAndroidMobile(push_map,obooking.getOdrivers().getDevice_id(),false);
				obooking=null;
				app_push_async=null;
				/*push_map.clear();
				push_map=null;*/
				return true;
			}
    	}
    	catch(Exception expPushNotifyObject)
    	{
			obooking=null;
			app_push_async=null;
	  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(expPushNotifyObject));
    	}
		obooking=null;
		app_push_async=null;
		push_map.clear();
		push_map=null;
		return false;
    }
    public Map<String,Object> ridelaterService(booking oBooking)
	{
		if(oBooking==null)  
			return map_response(false,5000);
		
		if(oBooking.getBooked_dat()==null)
		{
			return map_response(false,5000);
		}
		
		Map<String,Object> map_booked_status=new HashMap<String,Object>();
		Map<String,Object> map_response_status=new HashMap<String,Object>();
		String groupBookKey=getRandomWord(10);
		int nIdx=1;
		
		try{
			
			if(oBooking.getOpassenger()!=null)
			{
			if(!baseDAO.getInstance().isValidAccessID( oBooking.getOpassenger().getAccess_id(),2))
			{
				return map_response(false,5025);
			}
			}
			if(!baseDAO.getInstance().getbookinghistory("from booking where opassenger.access_id='"+oBooking.getOpassenger().getAccess_id()+"' and booking_type=3 and status=5", 0).isEmpty())
			{
				return map_response(false,5068);
			}
			
			passenger oPassenger=baseDAO.getInstance().getPassengers("From passenger where access_id='" + oBooking.getOpassenger().getAccess_id() + "'");
			if(oPassenger==null)
			{
				oBooking=null;
				map_booked_status.clear();
				map_booked_status=null;
				map_response_status.clear();
				map_response_status=null;
				nIdx=0;
				groupBookKey=null;
				return map_response(false,5025);
			}
		    oBooking.setStatus(5);
		    oBooking.setOpassenger(oPassenger);
			oBooking.setBooking_number(UUID.randomUUID().toString().replace("-",""));
			oBooking.setPbr_number(getRandomNumber(6));
			oBooking.setGroup_book_key(groupBookKey);
			oBooking.setBooked_source(IsNullContentOrSetDefault(oBooking.getBooked_source(),""));
			CronTriggerExample(formCronExpression(oBooking.getBooked_dat()),oBooking);
			if(baseDAO.getInstance().SaveEntityClass(oBooking))
			{	
					map_response_status=new HashMap<String,Object>();
					map_response_status.put("mobile" + nIdx, oBooking.getBto_mobile()+"");
					map_response_status.put("booking_number", oBooking.getBooking_number());
					map_response_status.put("pbr_number", oBooking.getPbr_number());
					map_response_status.put("group_book_key", oBooking.getGroup_book_key());
					map_response_status.put("status",""+1);
			}
		}
		catch(Exception ex)
		{
			oBooking=null;
			map_booked_status.clear();
			map_booked_status=null;
			map_response_status.clear();
			map_response_status=null;
			nIdx=0;
			groupBookKey=null;
			return map_response(map_booked_status,5005); //
		}
		oBooking=null;
		/*map_booked_status.clear();
		map_booked_status=null;*/
		nIdx=0;
		groupBookKey=null;
		return map_response(true,map_response_status); //
	}
    public static void CronTriggerExample(String expression,booking obooking) throws SchedulerException {
        String randomString=UUID.randomUUID().toString().replace("-","");
 		JobDetail job = newJob(QuartzJob.class).withIdentity(
         		"identity"+randomString, "job"+randomString).build();

         Trigger trigger = newTrigger()
                         .withIdentity("trigger"+randomString, "job"+randomString)
                         .withSchedule(CronScheduleBuilder.cronSchedule(expression))
                         .build();
         randomString=null;
         Scheduler scheduler = new StdSchedulerFactory().getScheduler();
         scheduler.start();
         job.getJobDataMap().put("objectToSave", obooking);
         scheduler.scheduleJob(job, trigger);
 }
 	public String formCronExpression(Date dateOfAdd)
 	{
 		String CronExp="";
 		try{
 			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 	    	 sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
 	    	String pattern = "yyyy-MM-dd HH:mm:ss";
 	       SimpleDateFormat formatter;
 	       formatter = new SimpleDateFormat(pattern);
 	    	    Date date = null;
 				try {
 		 	       String formattedDate= formatter.format(dateOfAdd);
 					date = sdf.parse(formattedDate);
 				} catch (ParseException e) {
 					// TODO Auto-generated catch block 
 					//2013-01-09 04:02:49 UTC
 					//2013-01-09 09:32:49

 					e.printStackTrace();
 				} 
 	    	   
 		Calendar cal = Calendar.getInstance();
 	    cal.setTime(date);
 	   // cal.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
 	    dateOfAdd=null;
 	    int year = cal.get(Calendar.YEAR);
 	    int month = cal.get(Calendar.MONTH)+1;
 	    int day = cal.get(Calendar.DATE);
 	     cal.add(Calendar.MINUTE, -10);
 	     CronExp="0 "+cal.get(Calendar.MINUTE)+" "+cal.get(Calendar.HOUR_OF_DAY)+" "+day+" "+month+" ? "+year+"";
 	    month=0;
 	    year=0;
 	    day=0;
 	   }
 		catch(Exception ex)
 		{
 			
 		} 	return CronExp;}
 	
 	public drivers getNearestDriver(List<drivers> driver_list)
 	{
 		int index=0;
 		drivers oDriver=null;
 		for(int i=index;i<driver_list.size();i++)
 		{
 			oDriver=baseDAO.getInstance().getdrivers("From drivers where status=0 and access_id='" + driver_list.get(i).getAccess_id() + "' and is_deleted=false",0);
 			
 			if(oDriver!=null){
 			return oDriver;
 			}
 			else
 				index++;
 		}
		return oDriver;
     }
 	
 	public boolean updateDriverStatus(String accessID){
 		if(baseDAO.getInstance().executeHQLQuery("update drivers set status=1 where access_id='" + accessID  + "'"))
 		{
 			return true;	
 		}
 		
 		else
 		return false;
 	}
}