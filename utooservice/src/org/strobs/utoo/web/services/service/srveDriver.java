package org.strobs.utoo.web.services.service;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;

import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.strobs.utoo.web.services.base.utoo;
import org.strobs.utoo.web.services.dao.baseDAO;
import org.strobs.utoo.web.services.dto.tdriver;
import org.strobs.utoo.web.services.eClasses.accident;
import org.strobs.utoo.web.services.eClasses.billing;
import org.strobs.utoo.web.services.eClasses.bills;
import org.strobs.utoo.web.services.eClasses.booking;
import org.strobs.utoo.web.services.eClasses.driver_login_histories;
import org.strobs.utoo.web.services.eClasses.driver_miles;
import org.strobs.utoo.web.services.eClasses.drivers;
import org.strobs.utoo.web.services.eClasses.invoice;
import org.strobs.utoo.web.services.eClasses.passenger;
import org.strobs.utoo.web.services.pservice.GPSServiceInf;
import org.strobs.utoo.web.services.pservice.MailServerApiInf;

public class srveDriver extends utoo
{
	@Autowired
	private GPSServiceInf reportService;
	@Autowired
	private MailServerApiInf mailService;

	public Map<String,Object> driverRegister(drivers oDriver)
	{
		if(oDriver==null)
			 return map_response(false,5000); //Invalid JSON Format for Class
		 
		if(oDriver.getMobile()==0)
			 return map_response(false,5003); //Invalid mobile number
		StringBuilder queryBuilder=new StringBuilder();
		drivers ofdbDriver=baseDAO.getInstance().getdrivers(queryBuilder
				.append("From drivers where mobile=").append(oDriver.getMobile())
				.append( " or license_number='")
				.append(oDriver.getLicense_number()).append("' and is_deleted=false").toString(),1);
		queryBuilder=null;
		if(ofdbDriver!=null)
		{
			if(ofdbDriver.getMobile()==0)
			{	 ofdbDriver=null;
				 return map_response(false,5003);
			}//Invalid mobile number
			
			if(IsNullContent(ofdbDriver.getLicense_front_file_ID()).equals(""))
			{	 ofdbDriver=null;
				 return map_response(false,5019);} //License details are pending
			
			if(IsNullContent(ofdbDriver.getCar_file_ID()).equals(""))
			{
				 ofdbDriver=null;
				 return map_response(false,5018); 
			}//Car details are pending
			ofdbDriver=null;
			return map_response(false,5027); // Mobile number or License Number is already exists

		}
		oDriver.setIs_active(1);  // Account --------> 0- Deactivate 1 - Activate
		oDriver.setStatus(1);
		oDriver.setPass_code(getRandomWord(4));
		oDriver.setAccess_id(UUID.randomUUID().toString().replace("-",""));
		oDriver.setPhoto_file_id("");
		if(baseDAO.getInstance().SaveEntityClass(oDriver))
		{
			String requestGPSJson="{\"driverID\":"+oDriver.getDriver_id()+",\"latitude\":\""+oDriver.getLatitude()+"\",\"longitude\":\""+oDriver.getLongitude()+"\"}";
			ResponseData(requestGPSJson, "insertDriverLoc", "POST","");
			return map_response(true,oDriver);}
		else
		{return map_response(false,5005);}
	}
	public Map<String,Object> drivercarDetails(drivers Odriver)
	{
		if(Odriver==null)  
			return map_response(false,5000);
		if(Odriver.getAccess_id()==null)  
		{   Odriver=null;
			return map_response(false,5006);
		}
		StringBuilder queryBuilder=new StringBuilder();
		if(Odriver!=null){
		if(!baseDAO.getInstance().isValidAccessID(Odriver.getAccess_id(),1))
		{
			return map_response(false,5025);
		}}
		if(baseDAO.getInstance().executeHQLQuery((queryBuilder.append("update drivers set  ocars_feature.cars_feature_ID=").append(Odriver.getOcars_feature().getCars_feature_ID()).append(" where access_id='").append(Odriver.getAccess_id()).append("'")).toString()))
		{   
			Odriver=null;
			queryBuilder=null;
			return map_response(true,5020);	
		}
		else
		{   Odriver=null;
			queryBuilder=null;
			return map_response(false,5006);}	
	}
	public Map<String,Object> uploadLicence(InputStream InpStrmContent,String SUB_FOLDER_PATH,String access_id,String license_side)
	{
		if(license_side==null)
		{   access_id=null;
		    SUB_FOLDER_PATH=null;
			return map_response(true,5031);
		}// License side should not be empty
		if(access_id==null)
		{ license_side=null;
	      SUB_FOLDER_PATH=null;
		  return map_response(true,5025);
		}// invalid access id
		if(!baseDAO.getInstance().isValidAccessID(access_id,1))
		{
			return map_response(false,5025);
		}
		String response_filename=uploadFile(InpStrmContent, SUB_FOLDER_PATH, access_id + ".png");
		if(response_filename.equals(""))
		{	license_side=null;
	        SUB_FOLDER_PATH=null;
	        response_filename=null;
            access_id=null;
			return map_response(false,5016); }// Upload Failure
		else
		{
		StringBuilder queryBuilder=new StringBuilder();
		if(baseDAO.getInstance().executeHQLQuery((queryBuilder.append("update drivers set license_").append(license_side).append("_file_ID='").append(response_filename).append("' where access_id='").append(access_id).append("'")).toString()))
			{   license_side=null;
	            SUB_FOLDER_PATH=null;
	            response_filename=null;
	            access_id=null;
	            queryBuilder=null;
				return map_response(true,5017);} // Upload Success
			else
			{	license_side=null;
				SUB_FOLDER_PATH=null;
				response_filename=null;
				access_id=null;
				queryBuilder=null;
				return map_response(false,5005);}
		}
      
	}
	public Map<String,Object> uploadCarImage(InputStream InpStrmContent,String SUB_FOLDER_PATH,String access_id)
	{
		if(access_id==null)
		{	SUB_FOLDER_PATH=null;
			return map_response(true,5025); }// invalid access id
		if(!baseDAO.getInstance().isValidAccessID(access_id,1))
		{
			return map_response(false,5025);
		}
		String response_filename=uploadFile(InpStrmContent, SUB_FOLDER_PATH, access_id + ".png");
		SUB_FOLDER_PATH=null;
		if(response_filename.equals(""))
		{
			response_filename=null;
			access_id=null;
			SUB_FOLDER_PATH=null;
			return map_response(false,5016); // Upload Failure
		}
			StringBuilder queryBuilder=new StringBuilder();
			if(baseDAO.getInstance().executeHQLQuery((queryBuilder.append("update drivers set car_file_ID='").append(response_filename).append("' where access_id='").append(access_id).append("'")).toString()))
			{ 	response_filename=null;
				access_id=null;
				SUB_FOLDER_PATH=null;
				queryBuilder=null;
				return map_response(true,5017); }// Upload Success
			else
			{response_filename=null;
			access_id=null;
			SUB_FOLDER_PATH=null;
			queryBuilder=null;
				return map_response(false,5005);
			}
		
	}
	public Map<String,Object> uploadProfilePhoto(InputStream InpStrmContent,String SUB_FOLDER_PATH,String access_id)
	{
		 Map<String,Object> map_response=new HashMap<String,Object>();
			StringBuilder queryBuilder=new StringBuilder();

			if(access_id==null)
			{	map_response.clear();
			    map_response=null;
			    SUB_FOLDER_PATH=null;
				return map_response(true,5025); 
				}// invalid access id
			if(!baseDAO.getInstance().isValidAccessID(access_id,1))
			{
				return map_response(false,5025);
			}
		String response_filename=uploadFile(InpStrmContent, SUB_FOLDER_PATH, queryBuilder.append(access_id).append(".png").toString());
		queryBuilder=null;
		if(response_filename.equals(""))
		{
			map_response.clear();
		    map_response=null;
		    SUB_FOLDER_PATH=null;
		    response_filename=null;
			return map_response(false,5016); // Upload Failure
		}
		else
		{
			queryBuilder=new StringBuilder();
			if(baseDAO.getInstance().executeHQLQuery(queryBuilder.append("update drivers set photo_file_id='").append(response_filename).append("' where access_id='").append(access_id).append("'").toString()))
			{
				map_response.put("photo_file_id", response_filename);
			    SUB_FOLDER_PATH=null;
			    response_filename=null;
			    access_id=null;
			    queryBuilder=null;
				return map_response(true,map_response); // Upload Success
			}
			else
			{SUB_FOLDER_PATH=null;
		    response_filename=null;
		    access_id=null;
		    queryBuilder=null;
		    map_response.clear();
		    map_response=null;
			return map_response(false,5005);}
		}
	}
	public Map<String,Object> login(driver_login_histories Odriver_login_histories)
	{
		if(Odriver_login_histories==null)
			
			 return map_response(false,5000); //Invalid JSON Format for Class

		drivers odbDrivers=null;
		tdriver otdriver=null;
		StringBuilder queryBuilder=new StringBuilder();
		odbDrivers=baseDAO.getInstance().getdrivers((queryBuilder.append("from drivers where mobile=").append(Odriver_login_histories.getOdrivers().getMobile()).append(" and pass_code='").append(Odriver_login_histories.getOdrivers().getPass_code()).append("' and is_deleted=false")).toString(), 0);
		queryBuilder=null;
		if(odbDrivers!=null)
		{
			if(odbDrivers.getIs_active()==0)
			{
				odbDrivers=null;
				otdriver=null;
				Odriver_login_histories=null;
				return map_response(false,5021); // Mobile number is not activated
			}
			queryBuilder=new StringBuilder();			
			if(baseDAO.getInstance().executeHQLQuery(queryBuilder.append("update drivers set device_id='").append(Odriver_login_histories.getOdrivers().getDevice_id()).append("' where driver_id=").append(odbDrivers.getDriver_id()).append(" and is_deleted=false").toString()))
			{
				queryBuilder=null;
				Odriver_login_histories.setOdrivers(odbDrivers);
				if(baseDAO.getInstance().SaveEntityClass(Odriver_login_histories))
				{
					 otdriver=baseDAO.getInstance().getTdriver(odbDrivers.getAccess_id()).get(0);
					 odbDrivers=null;
					 Odriver_login_histories=null;
					 return map_response(true,otdriver); // success
				}
				else{ odbDrivers=null;
				 	Odriver_login_histories=null;
				 	otdriver=null;
					queryBuilder=null;
					return map_response(false,5005);}
			}
			odbDrivers=null;
		 	Odriver_login_histories=null;
		 	otdriver=null;
		 	queryBuilder=null;
			return map_response(false,5005); // Server Is busy
		}
		else
			return map_response(false,5013); //invalid
	}
	public Map<String,Object> driver_profile(drivers odriver)
	{
		if(odriver==null)
		{
			return map_response(false,5000);}
		if(odriver.getAccess_id()==null){
			odriver=null;
			return map_response(false,5025);}
		if(!baseDAO.getInstance().isValidAccessID(odriver.getAccess_id(),1))
		{
			return map_response(false,5025);
		}
		List<tdriver> otdriver=baseDAO.getInstance().getTdriver(odriver.getAccess_id());
		odriver=null;
		return (!otdriver.isEmpty())? map_response(true,otdriver.get(0)) : map_response(false,5006);
	}
	public Map<String,Object> update(drivers Odriver)
	{
		if(Odriver==null)
		{
		return map_response(false,5000);}
		if(Odriver.getAccess_id()==null)  {
			Odriver=null;
			return map_response(false,5025);}
		if(!baseDAO.getInstance().isValidAccessID(Odriver.getAccess_id(),1))
		{
			return map_response(false,5025);
		}
		if(Odriver.getOcars_feature().getCars_feature_ID()==0)
		{	Odriver=null;
			return map_response(false,5007);}
		return baseDAO.getInstance().updateCarDetails(Odriver);	
	}
	/*public String updateString(drivers Odriver)
	{
		try{
		if(Odriver==null)  {
			return new ObjectMapper().writeValueAsString(map_response(false,5000));}
		if(Odriver.getAccess_id()==null)  
			return new ObjectMapper().writeValueAsString(map_response(false,5025));
		if(Odriver.getOcars_feature().getCars_feature_ID()==0)
			return new ObjectMapper().writeValueAsString(map_response(false,5007));
		return new ObjectMapper().writeValueAsString( baseDAO.getInstance().updateCarDetails(Odriver));	
		}
		catch(Exception ex)
		{
			Odriver=null;
			return "";
		}
	}*/
	public Map<String,Object> updateSettings(drivers Odriver)
	{
		if(Odriver==null)  
			return map_response(false,5000);
		if(Odriver.getAccess_id()==null)  
		{	Odriver=null;
			return map_response(false,5000);}
		if(!baseDAO.getInstance().isValidAccessID(Odriver.getAccess_id(),1))
		{
			return map_response(false,5025);
		}
		return (baseDAO.getInstance().executeHQLQuery("update drivers set health_checkup_settings=" + Odriver.isHealth_checkup_settings() + " , miles_drived_settings="+Odriver.isMiles_drived_settings()+" where access_id='" + Odriver.getAccess_id()+"'"))? map_response(true,5020) : map_response(false,5006);
	}
	public Map<String,Object> updateDriverStatus(drivers Odriver)
	{
		if(Odriver==null)  
			return map_response(false,5000);
		if(Odriver.getAccess_id()==null)  
		{	Odriver=null;
			return map_response(false,5006);}
		if(!baseDAO.getInstance().isValidAccessID(Odriver.getAccess_id(),1))
		{
			return map_response(false,5025);
		}
		return (baseDAO.getInstance().executeHQLQuery("update drivers set status=" + Odriver.getStatus()+ " where access_id='" + Odriver.getAccess_id()+"'"))? map_response(true,5020) : map_response(false,5006);
	}
	public Map<String,Object> uploadAccidentDetails(InputStream InpStrmContent,String SUB_FOLDER_PATH,String access_id,String accident_id,String extension,boolean isAudio)
	{
		String response_filename="";	
		if(access_id==null)  
			return map_response(false,5000); // Upload Failure
		if(!baseDAO.getInstance().isValidAccessID(access_id,1))
		{
			return map_response(false,5025);
		}
		response_filename =	uploadFile(InpStrmContent, SUB_FOLDER_PATH, access_id +"."+ extension);
		if(!isAudio)
		{
			return (baseDAO.getInstance().executeHQLQuery("update accident set accident_photo_filename='" + response_filename+ "' where accident_id="+accident_id+"")) ? map_response(true,5020) : map_response(false,5025);
		}
		else
		{
			return (baseDAO.getInstance().executeHQLQuery("update accident set voice_note_filename='" + response_filename+ "' where accident_id="+accident_id+"")) ? map_response(true,5020) : map_response(false,5025);
		}
	}
	public Map<String,Object> accident_details(accident oaccident)
	{
		if(oaccident==null)  
			return map_response(false,5000);
		if(oaccident.getOdrivers()!=null)
		{
		if(!baseDAO.getInstance().isValidAccessID(oaccident.getOdrivers().getAccess_id(),1))
		{
			return map_response(false,5025);
		}}
		StringBuilder queryBuilder=new StringBuilder();
		oaccident.setOdrivers(baseDAO.getInstance().getdrivers((queryBuilder.append("from drivers where access_id='").append(oaccident.getOdrivers().getAccess_id()).append("'")).toString(), 1));
		queryBuilder=null;
		if(oaccident.getOdrivers()==null)
		{
			oaccident=null;
			return map_response(false,5025);
		}
		return (baseDAO.getInstance().SaveEntityClass(oaccident))?map_response(true,oaccident) : map_response(false,5006);
	}
	
	public Map<String,Object> driver_job_history(booking obooking)
	{
		List<Object> obookinghistory=new ArrayList<Object>();
		if(obooking==null)  
			return map_response(false,5000);
		if(obooking.getOdrivers()!=null){
		if(!baseDAO.getInstance().isValidAccessID(obooking.getOdrivers().getAccess_id(),1))
		{
			return map_response(false,5025);
		}}
		StringBuilder queryBuilder=new StringBuilder();
		try{
			long driverID=baseDAO.getInstance().getLongKeyValue("select driver_id from drivers where access_id='"+obooking.getOdrivers().getAccess_id()+"'");
			obookinghistory=baseDAO.getInstance().getEntityClassList((queryBuilder.append("from booking where odrivers.driver_id=").append(driverID).append(" order by departure_dat desc").toString()), 0);
			obooking=null;
			queryBuilder=null;
			driverID=0;
			return (obookinghistory.isEmpty())?map_response(false,5006) : map_response(true,obookinghistory); 
		}
		catch(Exception exDriverJobHistory)
		{
	  	  	baseDAO.getInstance().logAnError("srveDriver", baseDAO.getInstance().stackTraceToString(exDriverJobHistory));
	  	  	obookinghistory.clear();
	  	  	obookinghistory=null;			
	  	  	obooking=null;
			queryBuilder=null;
			return map_response(false,5005); // Server Is busy

		}
	}
	@SuppressWarnings("null")
	public Map<String,Object> driver_miles_details(driver_miles odriver_miles)
	{
		Map<String,Object> map_response=new HashMap<String,Object>();
		@SuppressWarnings("rawtypes")
		List data_invoice,data_miles =null;
		int driver_id=0;
		if(odriver_miles==null)  
			return map_response(false,5025);
		if(odriver_miles.getOdrivers()!=null){
		if(!baseDAO.getInstance().isValidAccessID(odriver_miles.getOdrivers().getAccess_id(),1))
		{
			return map_response(false,5025);
		}}
		try{
			odriver_miles.setTarget_miles(5000);
			driver_id=baseDAO.getInstance().getIntKeyValue("select driver_id from drivers where access_id='"+odriver_miles.getOdrivers().getAccess_id()+"' ");
			if(driver_id==0)
			{	odriver_miles=null;
				driver_id=0;
				map_response.clear();
				map_response=null;
				return map_response(false,5025);}
				data_miles=baseDAO.getInstance().getMapListObject("select(select IFNULL(sum(clm_miles_achieved),0) from u2_0xs_tbl_mst_driver_miles where fk_clm_driver_id="+driver_id+" and "
						+ "week (clm_start_on) = week( current_date)  and year( clm_start_on) = year( current_date )) as week_milesacheived,(select ifnull(sum(clm_miles_achieved),0) from u2_0xs_tbl_mst_driver_miles where fk_clm_driver_id="+driver_id+") "
						+ "as miles_achieved,(select IFNULL(sum(clm_miles_achieved),0) from u2_0xs_tbl_mst_driver_miles where  fk_clm_driver_id="+driver_id+" and month (clm_start_on) = month( current_date) "
						+ " and year( clm_start_on) = year( current_date ))as month_milesacheived,"
						+ "(select IFNULL(sum(clm_miles_achieved),0) from u2_0xs_tbl_mst_driver_miles where  fk_clm_driver_id="+driver_id+" and date (clm_start_on) = date( current_date) "
						+ " and year( clm_start_on) = year( current_date ))as date_milesacheived");
	        
				data_invoice=baseDAO.getInstance().getMapListObject("select(select ifnull(sum(clm_amount),0) from u2_0xs_tbl_mst_invoice invoice,u2_0xs_tbl_mst_booking booking  where booking.pk_clm_booking_id=invoice.fk_clm_booking_id and booking.fk_clm_driver_id="+driver_id+") as totalamount,"
						+ "(select IFNULL(sum(clm_amount),0) from u2_0xs_tbl_mst_invoice invoice,u2_0xs_tbl_mst_booking booking "
						+ "where booking.pk_clm_booking_id=invoice.fk_clm_booking_id and booking.fk_clm_driver_id="+driver_id+"  and week (invoice.clm_created_on) = week( current_date)  and year(invoice.clm_created_on) = year( current_date ) ) as week_amount, "
						+ "(select IFNULL(sum(clm_amount),0)  from u2_0xs_tbl_mst_invoice invoice,u2_0xs_tbl_mst_booking booking where booking.pk_clm_booking_id=invoice.fk_clm_booking_id and booking.fk_clm_driver_id="+driver_id+"  and  month (invoice.clm_created_on) = month( current_date)  and year(invoice.clm_created_on) = year( current_date )) as month_amount,"
						+ "(select IFNULL(sum(clm_amount),0) from u2_0xs_tbl_mst_invoice invoice,u2_0xs_tbl_mst_booking booking where booking.pk_clm_booking_id=invoice.fk_clm_booking_id and booking.fk_clm_driver_id="+driver_id+"  and  date (invoice.clm_created_on) = date( current_date)  and year(invoice.clm_created_on) = year( current_date )) as date_amount");
	
        	 map_response.put("target_miles", odriver_miles.getTarget_miles());
	         if(data_miles!=null || !data_miles.isEmpty())
	         {
	        	 map_response.put("miles_travelled", data_miles.get(0));
	        	 	odriver_miles=null;
					driver_id=0;
					data_miles=null;
					//data_invoice=null;
					
	         }
	         if(data_invoice!=null)
	         { 		map_response.put("amount", data_invoice.get(0));
	        		odriver_miles=null;
					driver_id=0;
					data_miles=null;
					data_invoice=null;
	         }
		}
		catch(Exception ex)
		{
			odriver_miles=null;
			driver_id=0;
			data_miles=null;
			data_invoice=null;
			map_response.clear();
			map_response=null;
			return map_response(false,5005);
		}
		return map_response(true,map_response);

	}
	public Map<String,Object> driver_guide_details(booking obooking)
	{
		String sql="";

		if(obooking==null)  {
			return map_response(false,5000);
			}
		if(obooking.getOdrivers()!=null){
		if(!baseDAO.getInstance().isValidAccessID(obooking.getOdrivers().getAccess_id(),1))
		{
			return map_response(false,5025);
		}}
		StringBuilder queryBuilder=new StringBuilder();
		sql=queryBuilder.append("update drivers set status=1 where access_id='").append(obooking.getOdrivers().getAccess_id()).append("'").toString();
		queryBuilder=null;
		if(baseDAO.getInstance().executeHQLQuery(sql))
		{queryBuilder=new StringBuilder();
			if(baseDAO.getInstance().executeHQLQuery((queryBuilder.append("update booking set status=1 where booking_number='").append(obooking.getBooking_number()).append("'").toString())))
			{	queryBuilder=null;
				obooking=null;
				sql=null;
				return map_response(true,5020) ;} // updated successfully
			else{
				queryBuilder=null;
				obooking=null;
				sql=null;
				return map_response(false,5030); }// Server Is busy
			}
			else
			{
				obooking=null;
				sql=null;
				queryBuilder=null;
				return map_response(false,5025);} // Server Is busy
	}

	public Map<String,Object> driverStartTrip(booking obooking) 
	{
		if(obooking==null)  
			return map_response(false,5000);
		StringBuilder queryBuilder=new StringBuilder();
		
		
		 /*SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");*/
		/*   Date departureDate=   formatter.parse(obooking.getDeparture_dat().toString());*/
		   DateFormat readFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		   DateFormat writeFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
		   Date date = null;
		    try {
		       date = readFormat.parse(obooking.getDeparture_dat().toString());
		    } catch ( ParseException e ) {
		        e.printStackTrace();
		    }

		    String departureDate = "";
		    if( date != null ) {
		    	departureDate = writeFormat.format(date);
		    }
		    else
		    {
		    	return map_response(false,5000);
		    }
		    
			if(baseDAO.getInstance().executeHQLQuery(queryBuilder.append("update booking set status=2,"
					+ "reaching_latitude="+obooking.getReaching_latitude()+",reaching_longitude="
				    + obooking.getReaching_longitude()+",booked_destination="+obooking.getBooked_destination()+
					" ,actual_source_latitude="+obooking.getActual_source_latitude()+",actual_source_longitude="
					+ obooking.getActual_source_longitude()+",actual_source='"+obooking.getActual_source()+"', departure_dat='"+departureDate+"' where booking_number='").
				append(obooking.getBooking_number()).append("'").toString()))
	     	{
			queryBuilder=null;
			obooking.setStatus(2); //Start trip
			
			if(obooking.getBooking_type()!=2)
			{
			SendStartTripNotification(obooking);
			}
			return map_response(true,5020); 
		    }	
		queryBuilder=null;
		return map_response(false,5030);
	}
	public boolean SendStartTripNotification(booking obooking) 
    {
    	Map<String, Object> push_map =null;
    	@SuppressWarnings("unused")
		Future<String> app_push_async=null;
    	try
    	{
    		push_map = new HashMap<String, Object>();
			push_map.put("booking_number", obooking.getBooking_number());
			push_map.put("book_status", obooking.getStatus());
			
			StringBuilder queryBuilder=new StringBuilder();
			passenger oPassenger=baseDAO.getInstance().getPassengers((queryBuilder.append("From passenger where access_id=(Select opassenger.access_id from booking where booking_number='").append(obooking.getBooking_number()).append("')").toString()));
			queryBuilder=null;
			if(oPassenger.getDevice_type_name().equals("ios"))
				app_push_async=reportService.SendMessageToIOSMobile(push_map,oPassenger.getDevice_id(),"Your ride is started");
			else
				app_push_async=reportService.SendMessageToAndroidMobile(push_map,oPassenger.getDevice_id(),true);
			/*app_push_async=null;
			push_map.clear();
			push_map=null;*/
			oPassenger=null;
			return true;
    	}
    	catch(Exception expPushNotifyObject)
    	{	app_push_async=null;
			push_map.clear();
			push_map=null;
	  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(expPushNotifyObject));
    	}
    	return false;
    }
	public Map<String,Object> driver_end_trip(invoice oinvoice)
	{
		if(oinvoice==null)  
			return map_response(false,5000);
		
		oinvoice=baseDAO.getInstance().EndTripInvoice(oinvoice);
		if(oinvoice==null)  
				return map_response(false,5005);
		
		if(oinvoice.getError_code()==0)
		{
			if(oinvoice.getObooking()!=null)
			{
				if(oinvoice.getObooking().getBooking_type()==2)
				{
				String SMS_GATEWAY_RESPONSE=SendMessage(oinvoice.getObooking().getBto_mobile(),"Your Ride is completed and Total amount is Rs."+oinvoice.getAmount()+".Thanks For Riding UTOO");
				if(SMS_GATEWAY_RESPONSE.contains("Invalid Mobile Number"))
				baseDAO.getInstance().logAnError("srveBook","Message Not Send Invalid Mobile Number");
				}
				else
				{
				sendEndTripNotificationX(oinvoice.getObooking());
				}
				
				
				try {
					String htmlTemplate=baseDAO.getInstance().MailByVelocity(oinvoice);
					if(oinvoice.getObooking().getOpassenger().getEmail()!=null)
					mailService.SendMailToServer(htmlTemplate,oinvoice.getObooking().getOpassenger().getEmail());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return map_response(true,oinvoice);
			}
		}
		return map_response(false,oinvoice.getError_code()); 
	}
	public float InvoiceProcessing(billing obilling)
	{
		float fTotal=0.0f;
		float fpeaktimeAmt=0.0f;

		if(obilling.isPeakTime())
			fpeaktimeAmt=obilling.getnTotalMins()*obilling.getfRideChargePerMin()*obilling.getfPeakTimeAmtPerRide();
		else
			fpeaktimeAmt=obilling.getnTotalMins()*obilling.getfRideChargePerMin();
			
		fTotal=(obilling.getfBaseFare()+((obilling.getfTotalKMs()-obilling.getfBaseMinimumKM())*obilling.getfAfterMinmumKM())+fpeaktimeAmt);
		
		if(obilling.isInPromoCode())
		{
			if(obilling.isTotalAMTPromocode())
				fTotal=fTotal-obilling.getfPromoCodeAMT();
			else
				fTotal=fTotal-(fTotal*(obilling.getfPromoCodeAMT()/100));
		}
		obilling=null;
		fpeaktimeAmt=0.0f;
		return fTotal;
	}
	public Map<String,Object> get_driver_rating(drivers odriver)
	{
		if(odriver==null)  
			return map_response(false,5000);
		if(!baseDAO.getInstance().isValidAccessID(odriver.getAccess_id(),1))
		{
			return map_response(false,5025);
		}
		StringBuilder queryBuilder=new StringBuilder();
		@SuppressWarnings("deprecation")
		int currentRating=baseDAO.getInstance().
				getIntKeyValueX(queryBuilder.append("select total_rating from drivers where access_id='").append(odriver.getAccess_id()).append("'").toString());
		queryBuilder=null;
		odriver=null;
		return (currentRating==-1)?map_response(false,5025):map_response(true,currentRating);
	}
	public Map<String,Object> getBookingHistory(booking oBooking)
	{
		if(oBooking==null)  
			return map_response(false,5000);
		
		if(oBooking.getBooking_number()==null){oBooking=null;
			return map_response(false,5030);}
		
		if(oBooking.getBooking_number().equals(""))
			return map_response(false,5030);
		return map_response(baseDAO.getInstance().getInvoice("From invoice where obooking.booking_number='"+ oBooking.getBooking_number() +"' order by invoice_id desc"),5006);
	}
	public Map<String,Object> setBillEntry(bills oBills)
	{
		if(oBills==null)  
			return map_response(false,5000);
		
		if(oBills.getOinvoice()==null){oBills=null;
			return map_response(false,5049);}
		StringBuilder queryBuilder=new StringBuilder();
		invoice oInvoice=baseDAO.getInstance().getinvoice((queryBuilder.append("From invoice where invoice_no='").append(oBills.getOinvoice().getInvoice_no()).append("'").toString()));
		queryBuilder=null;
		if(oInvoice==null){oInvoice=null;
			oBills=null;
			return map_response(false,5049);}
		
		oBills.setOinvoice(oInvoice);
		if(baseDAO.getInstance().SaveEntityClass(oBills))
		{
			String SMS_GATEWAY_RESPONSE=SendMessage(oInvoice.getObooking().getBto_mobile(),"Thanks for riding with utoo. Your total bill for " + oInvoice.getObooking().getPbr_number() + " is " + oInvoice.getTotal() + ". Kindly check your email for invoice of this ride.");
			if(SMS_GATEWAY_RESPONSE.contains("Invalid Mobile Number"))
				baseDAO.getInstance().logAnError("srveBook","Message Not Send Invalid Mobile Number");
			oInvoice=null;
			oBills=null;
			SMS_GATEWAY_RESPONSE=null;
			return map_response(true,5050);
		}
		oInvoice=null;
		oBills=null;
		return map_response(false,5005);
	}
	public Map<String,Object> driverdetails(drivers oDrivers)
	{
		if(oDrivers==null)  
			return map_response(false,5000);
		
		if(oDrivers.getAccess_id()==null){oDrivers=null;
			return map_response(false,5025);}
		
		if(oDrivers.getAccess_id().equals("")){oDrivers=null;
			return map_response(false,5025);}
		if(!baseDAO.getInstance().isValidAccessID(oDrivers.getAccess_id(),1))
		{
			return map_response(false,5025);
		}
		return map_response(baseDAO.getInstance().getdriverdetails(oDrivers.getAccess_id()),5025);
	}
	public boolean sendEndTripNotificationX(booking obooking) 
    {
    	Map<String, Object> push_map =null;
    	@SuppressWarnings("unused")
    	Future<String> app_push_async=null;
    	try
    	{
    		push_map = new HashMap<String, Object>();
			push_map.put("booking_number", obooking.getBooking_number());
			push_map.put("book_status",obooking.getStatus());
			
			if(obooking.getOpassenger().getDevice_type_name().equals("ios"))
				app_push_async=reportService.SendMessageToIOSMobile(push_map,obooking.getOpassenger().getDevice_id(),"Your ride is completed");
			else
				app_push_async=reportService.SendMessageToAndroidMobile(push_map,obooking.getOpassenger().getDevice_id(),true);
	  	return true;
    	}
    	catch(Exception expPushNotifyObject)
    	{
	  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(expPushNotifyObject));
	  	  	app_push_async=null;
	  	  	obooking=null;
	  	  	push_map.clear();
	  	  	push_map=null;
    	}
		return false;
    }
	public Map<String,Object> getInvoiceDetails(booking oBooking)
	{
		if(oBooking==null)  
			return map_response(false,5000);
		
		if(oBooking.getBooking_number()==null){oBooking=null;
			return map_response(false,5030);}
	
		if(oBooking.getBooking_number().equals("")){oBooking=null;
			return map_response(false,5030);}
		return map_response(baseDAO.getInstance().getInvoice("From invoice where obooking.booking_number='"+ oBooking.getBooking_number() +"' order by created_on desc"),5006);
	}
	public Map<String,Object> updateCurrentLatlon(drivers odriver)
	{
		if(odriver==null)  
			return map_response(false,5000);
		
		if(odriver.getAccess_id()==null)
		{odriver=null;
			return map_response(false,5025);}
	
		if(odriver.getAccess_id().equals("")){odriver=null;
			return map_response(false,5025);}
		
		return map_response(baseDAO.getInstance().executeHQLQuery("Update drivers set latitude='" + odriver.getLatitude() + "',longitude='" + odriver.getLongitude() + "',status="+odriver.getStatus()+" where access_id='"+ odriver.getAccess_id() +"'"),5020,5005);
	}
	
	public Map<String,Object> getDriverPassCode(drivers odriver)
	{
		if(odriver==null)  
			return map_response(false,5000);
		
		if(odriver.getMobile()==0)
		 {
			odriver=null;
			return map_response(false,5003);
		}
		return map_response(baseDAO.getInstance().getStringKeyValue("select pass_code from drivers where mobile = " + odriver.getMobile()),5006);
	}
}
