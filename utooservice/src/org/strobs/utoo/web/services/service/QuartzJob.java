package org.strobs.utoo.web.services.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.strobs.utoo.web.services.base.utoo;
import org.strobs.utoo.web.services.dao.baseDAO;
import org.strobs.utoo.web.services.eClasses.booking;
import org.strobs.utoo.web.services.eClasses.drivers;

public class QuartzJob extends utoo implements Job{
	
        @SuppressWarnings("unchecked")
		public void execute(JobExecutionContext context)
                        throws JobExecutionException {

        	try{
        		JobDataMap jdMap = context.getJobDetail().getJobDataMap();
        		booking obooking=(booking) jdMap.get("objectToSave"); 
        		drivers odriver=obooking.getOdrivers();
                if(odriver==null)
    			{
                	/*odriver=baseDAO.getInstance().getdrivers("FROM drivers where status=0 and ocars_feature.ocars_model.car_model_id="+obooking.getOcars_model().getCar_model_id()+" and is_deleted=false order by (abs(abs(latitude-"+
                	obooking.getDeparture_latitude()+")+abs(longitude-"+obooking.getDeparture_longitude()+")))asc",1);*/
                	
               
                	
        			double maxIncrement=5000;
        			Map<String,Object> listMap=new HashMap<String,Object>();
        				
        				
        				String requestGPSJson="{\"maxDistance\":"+maxIncrement+",\"latitude\":\""+obooking.getDeparture_latitude()+"\",\"longitude\":\""+obooking.getDeparture_longitude()+"\"}";
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
        								odriver=baseDAO.getInstance().getdrivers("from drivers where driver_id="+driverList.get(i).get("driverID")+" and ocars_feature.ocars_model.car_model_id="+obooking.getOcars_model().getCar_model_id()+" and is_deleted=false and status=0", 0);
        								if(odriver!=null)
        								{
        									break inner;
        								}
        								
        								}
        								}
        						}
        						
        					}
        				}
                	if(odriver==null)
                	{
                	obooking.setStatus(4);
                	if(baseDAO.getInstance().executeHQLQuery("update booking set status=4 , reason_ID=2 where booking_id=" + obooking.getBooking_id()))
                	{
                	
        			     sendCancelBookingNotification(obooking);
        			     String SMS_GATEWAY_RESPONSE=SendMessage(obooking.getOpassenger().getMobile(),"Sorry for the inconvenience. Your Booking number: "+obooking.getPbr_number()+" is cancelled by the driver. Kindly call us on +9144 4001 1028 for any issues or queries.");
        			     
        			 	if(SMS_GATEWAY_RESPONSE.contains("Invalid Mobile Number"))
        				{
        					SMS_GATEWAY_RESPONSE=null;
        					baseDAO.getInstance().logAnError("srveBook","Message Not Send Invalid Mobile Number");
        				}  		
                	}
                	}
                	else{
                	baseDAO.getInstance().executeHQLQuery("update booking set odrivers.driver_id='"+odriver.getDriver_id()+"' , status=0 where booking_number='"+obooking.getBooking_number()+"' ");
					baseDAO.getInstance().executeHQLQuery("update drivers set status=1 where access_id='" + odriver.getAccess_id() + "'");
					obooking.setOdrivers(odriver);
					sendMessage(obooking);
                	}
    			}
               

        	}
        	catch(Exception ex)
        	{
		  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(ex));
        		JobExecutionException e2 = 
                        new JobExecutionException(ex);
                    // this job will refire immediately
                    e2.refireImmediately();
                    
        	}
                }
    	public void sendMessage(booking oBooking)
    	{
    		sendBookingNotification(oBooking);
    		String SMS_GATEWAY_RESPONSE=SendMessage(oBooking.getOpassenger().getMobile(),"Your Booking number " + oBooking.getPbr_number() + " is confirmed on "+ baseDAO.getInstance().utcToISTFormat(oBooking.getBooked_dat()) +". Happy to have you Onboard");
			if(SMS_GATEWAY_RESPONSE.contains("Invalid Mobile Number"))
			{
				SMS_GATEWAY_RESPONSE=null;
				baseDAO.getInstance().logAnError("srveBook","Message Not Send Invalid Mobile Number");
			}  		
    	}
    	   public boolean sendBookingNotification(booking obooking) 
    	    {
    	    	Map<String, Object> push_map =null;
    	    	try
    	    	{
    	    		push_map = new HashMap<String, Object>();
    	    		push_map.put("passenger_access_id", obooking.getOpassenger().getAccess_id());
    				push_map.put("passenger_name", obooking.getOpassenger().getPassenger_name());
    				push_map.put("passenger_mobile", obooking.getOpassenger().getMobile());	
    				push_map.put("pickup_passenger_lat", obooking.getDeparture_latitude());
    				push_map.put("pickup_passenger_lon", obooking.getDeparture_longitude());
    				push_map.put("booked_passenger_lat", obooking.getPassenger_latitude());	
    				push_map.put("booked_passenger_lon", obooking.getPassenger_longitude());
    				push_map.put("pickup_time", obooking.getBooked_dat());
    				push_map.put("booking_number", obooking.getBooking_number());
    				push_map.put("booking_type", obooking.getBooking_type());
    				push_map.put("book_status", obooking.getStatus());
    				push_map.put("pbr_number", obooking.getPbr_number());
    				push_map.put("booked_source", obooking.getBooked_source());
    				push_map.put("pbr_number", obooking.getPbr_number());
    				
    				if(obooking.getBooking_type()==1 || obooking.getBooking_type()==2 || obooking.getBooking_type()==5 ||obooking.getBooking_type()==3)
    				{
    					return SendMessageToAndroidMobileNotSync(push_map,obooking.getOdrivers().getDevice_id(),false);
    				}
    	    	}
    	    	catch(Exception expPushNotifyObject)
    	    	{
    		  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(expPushNotifyObject));
    	    	}
    			return false;
    	    }
}