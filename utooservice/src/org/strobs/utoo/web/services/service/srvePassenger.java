package org.strobs.utoo.web.services.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.strobs.utoo.web.services.base.utoo;
import org.strobs.utoo.web.services.dao.baseDAO;
import org.strobs.utoo.web.services.eClasses.favourites;
import org.strobs.utoo.web.services.eClasses.passenger;
import org.strobs.utoo.web.services.eClasses.passenger_login_histories;
import org.strobs.utoo.web.services.eClasses.passenger_otp;

import sys.liz.provider.s5.algorithms.basic.s5security;

public class srvePassenger extends utoo
{
	public Map<String,Object> register(passenger Opassenger)
	{
		if(Opassenger==null)
			 return map_response(false,response_codes.Invalid_JSON_Format.getCode()); //Invalid JSON Format for Class

		StringBuilder queryBuilder = new StringBuilder();
		passenger ODBpassenger=baseDAO.getInstance().getPassengers(queryBuilder.append("From passenger where mobile=").append(Opassenger.getMobile()).toString());
		queryBuilder=null;
		if(ODBpassenger!=null)
		{
			if(ODBpassenger.getIs_active()==0){
				  Opassenger=null;
				  ODBpassenger=null;
				 return map_response(false,5011); //OTP is pending
			}

			if(ODBpassenger.getIs_active()==1){
				  Opassenger=null;
				  ODBpassenger=null;
				 return map_response(false,5001); //Mobile number is already exists
			}
		}
		
		ODBpassenger=null;
		passenger_otp Opassenger_otp=new passenger_otp(getOTP());
		queryBuilder= new StringBuilder();
		String SMS_GATEWAY_RESPONSE=SendMessage(Opassenger.getMobile(),queryBuilder.append("Your authentication code is ").append(Opassenger_otp.getOtp()).toString());
		queryBuilder=null;
		if(SMS_GATEWAY_RESPONSE.contains("Message GID="))
		{
			SMS_GATEWAY_RESPONSE=null;
			Opassenger.setAccess_id(UUID.randomUUID().toString().replace("-",""));
			Opassenger.setPhoto_file_id("");
			Opassenger.setPassword(s5security.passwordMask_Min6CHR(Opassenger.getPassword()));
			if(baseDAO.getInstance().SaveEntityClass(Opassenger))
			{
				Opassenger_otp.setOpassenger(Opassenger);
				Opassenger=null;
				if(baseDAO.getInstance().SaveEntityClass(Opassenger_otp))
				{
					Opassenger_otp=null;
					return map_response(true,5002); //"OTP has been successfully..."
				}
				else{
					Opassenger_otp=null;
					return map_response(false, 5005);
				}
			}
			else{
				Opassenger=null;
				Opassenger_otp=null;
				return map_response(false, 5005);
			}
		}
		else if(SMS_GATEWAY_RESPONSE.contains("Invalid Mobile Number")){
			SMS_GATEWAY_RESPONSE=null;
			Opassenger=null;
		    Opassenger_otp=null;
			return map_response(false,5003); //"Invalid Mobile Number"
			}
		else{
			SMS_GATEWAY_RESPONSE=null;
			Opassenger=null;
			Opassenger_otp=null;
			return map_response(false,5004); //"SMS server is busy..."
		}
	}
	public Map<String,Object> otpverify(passenger Opassenger,boolean IsRegisterCompleted)
	{
		if(Opassenger==null)
			 return map_response(false,5000); //Invalid JSON Format for Class
		StringBuilder queryBuilder = new StringBuilder();
	
		passenger_otp Opassenger_otp=baseDAO.getInstance().getPassenger_OTP(queryBuilder.append("From passenger_otp where opassenger.mobile=").
				append(Opassenger.getMobile()).append(" order by passenger_otp_id desc").toString(),1);
		queryBuilder=null;
		if(Opassenger_otp==null){
			  IsRegisterCompleted=false;
			  Opassenger=null;
			 return map_response(false,5003); //Mobile number is not exists
		}
		if(IsRegisterCompleted)
		{
			/*IsRegisterCompleted=false;*/
			if(Opassenger_otp.getOpassenger().getIs_active()==1)
				 return map_response(false,5001); //Mobile number is exists
			
			if(Opassenger_otp.getIs_Verify()==1)
				 return map_response(false,5014); //OTP is already verified
		}	
		if(Opassenger_otp.getOtp()==Opassenger.getOtp())
		{
			queryBuilder=new StringBuilder();
			if(baseDAO.getInstance().executeHQLQuery(queryBuilder.append("update passenger_otp set is_Verify=1,verified_on=now() where passenger_otp_id=").append(Opassenger_otp.getPassenger_otp_id()).toString()))
			{
				queryBuilder=null;
				if(IsRegisterCompleted)
				{
			/*		IsRegisterCompleted=false;*/
					queryBuilder= new StringBuilder();
					if(baseDAO.getInstance().executeHQLQuery(queryBuilder.append("update passenger set is_active=1,promo_code='").append(Opassenger.getPromo_code()).append("',updated_on=now() where passenger_id=").append(Opassenger_otp.getOpassenger().getPassenger_id()).toString()))
					{
						queryBuilder=null;
						Map<String,Object> map_response=new HashMap<String,Object>();
						//login_history(Opassenger,Opassenger_otp.getOpassenger());
						passenger oPassenger2=Opassenger_otp.getOpassenger();
						Opassenger_otp=null;
						oPassenger2.setPromo_code(Opassenger.getPromo_code());
						map_response.put("passenger",oPassenger2);
						oPassenger2=null;
						if(Opassenger.isIs_need_driver_list())
							map_response.put("drivers", baseDAO.getInstance().getEntityClassList("From drivers where is_deleted=false",0));
						Opassenger=null;
						return map_response(true,map_response);
					}
				}
				else
				{
					queryBuilder = new StringBuilder();
					if(baseDAO.getInstance().executeHQLQuery(queryBuilder.append("Update passenger set password='").append(s5security.passwordMask_Min6CHR(Opassenger.getPassword())).append("' where passenger_id=").append(Opassenger_otp.getOpassenger().getPassenger_id()).toString()))
					{
						queryBuilder=null;
						Opassenger_otp=null;
						Opassenger=null;
						return map_response(true,5009); //"Otp verified
					}
					else
					{
						queryBuilder=null;
						Opassenger_otp=null;
						Opassenger=null;
						return map_response(false,5005); //"Server is busy..."
					}
				}
			}
		}
		else{
			queryBuilder=new StringBuilder();
			if(baseDAO.getInstance().getLongKeyValue(queryBuilder.append("select passenger_otp_id from passenger_otp where opassenger.mobile=").
					append(Opassenger.getMobile()).append(" and otp=").append(Opassenger.getOtp()).append(" order by passenger_otp_id desc").toString()) !=0)
			{

			IsRegisterCompleted=false;
			Opassenger_otp=null;
			Opassenger=null;
			return map_response(false,5067); // Otp is Expired
			}
			else
			{
				IsRegisterCompleted=false;
				Opassenger_otp=null;
				Opassenger=null;
				return map_response(false,5010); // OTP is not valid
				

			}
		}
		return map_response(false,5005); // Server busy
	}
	public Map<String,Object> resendOTP(passenger Opassenger)
	{
		if(Opassenger==null)
			 return map_response(false,5000); //Invalid JSON Format for Class
		StringBuilder queryBuilder = new StringBuilder();
		passenger_otp ODBuser_otp=baseDAO.getInstance().getPassenger_OTP(queryBuilder.append("From passenger_otp where opassenger.mobile=").append(Opassenger.getMobile()).append(" order by passenger_otp_id desc").toString(),0);
		queryBuilder = null;
		if(ODBuser_otp==null)
		{
			 Opassenger=null;
			 return map_response(false,5008); //Mobile number is not exists
		}
		
		if(ODBuser_otp.getOpassenger().getIs_active()==1)
		{
			ODBuser_otp=null;
			Opassenger=null;
			return map_response(false,5001); //Mobile number is  exists
		}
		queryBuilder = new StringBuilder();
		int countValue=baseDAO.getInstance().getIntKeyValue(queryBuilder.append("SELECT count(*) FROM passenger_otp where opassenger.passenger_id=").append(ODBuser_otp.getOpassenger().getPassenger_id()).append(" and Date(sent_on)=Date(now())").toString());
		queryBuilder=null;
		if(countValue>2)
		{
			ODBuser_otp=null;
			Opassenger=null;
			return map_response(false,5032);//Chance Expired
		}
		queryBuilder= new StringBuilder();
		baseDAO.getInstance().executeHQLQuery(queryBuilder.append("update passenger_otp set is_Verify=2,verified_on=now() where opassenger.passenger_id=").append(ODBuser_otp.getOpassenger().getPassenger_id()).toString());
		queryBuilder= null;
		passenger_otp Ouser_otp_N=new passenger_otp(getOTP());
		queryBuilder = new StringBuilder();
		String SMS_GATEWAY_RESPONSE=SendMessage(Opassenger.getMobile(),queryBuilder.append("Your authentication code is ").append(Ouser_otp_N.getOtp()).toString());
		queryBuilder=null;
		//String SMS_GATEWAY_RESPONSE="Message GID=";
		Opassenger=null;
		if(SMS_GATEWAY_RESPONSE.contains("Message GID="))
		{
			SMS_GATEWAY_RESPONSE=null;
			Ouser_otp_N.setOpassenger(ODBuser_otp.getOpassenger());
			ODBuser_otp=null;
			if(baseDAO.getInstance().SaveEntityClass(Ouser_otp_N))
			{
				Ouser_otp_N=null;
				switch (countValue)
				{
					case 0:
						return map_response(false,5033); //"Two More Chance is Left"
					case 1:
						return map_response(false,5034); //"One More Chance is Left"
					case 2:
						return map_response(false,5033); //"Chance is Finished"	
				}
				if(countValue==1)
					return map_response(false,5034); //"One More Chance is Left"
				    
				else
					return map_response(false,5033); //"Chance is Finished"
			}
			return map_response(false,5005);
		}
		
		else if(SMS_GATEWAY_RESPONSE.contains("Invalid Mobile Number"))
		{
			SMS_GATEWAY_RESPONSE=null;
			ODBuser_otp=null;
			Ouser_otp_N=null;	
			return map_response(false,5003); //"Invalid Mobile Number"
		}
		else
		{ 
			SMS_GATEWAY_RESPONSE=null;
			ODBuser_otp=null;
			Ouser_otp_N=null;
			return map_response(false,5004); //"SMS server is busy..."
		}
	}
	public Map<String,Object> login(passenger Opassenger)
	{
		if(Opassenger==null)
			 return map_response(false,5000); //Invalid JSON Format for Class

		passenger newpassenger=null;
		newpassenger=checkAuthentication(Opassenger);

		if(newpassenger==null)
			return map_response(false,5013); //INvalid 

		if(newpassenger.getIs_active()==0)
		{
		     Opassenger=null;
			 newpassenger=null;
			 return map_response(false,5011); //  Otp pending
		}
		
		baseDAO.getInstance().executeHQLQuery("update passenger set device_id='' where device_id='"+Opassenger.getDevice_id()+"'" );
		newpassenger.setAccess_id(UUID.randomUUID().toString().replace("-",""));
		StringBuilder queryBuilder = new StringBuilder();
		if(baseDAO.getInstance().executeHQLQuery(queryBuilder.append("Update passenger set device_type_name='").append(Opassenger.getDevice_type_name()).append("',device_id='").append(Opassenger.getDevice_id()).append("',status=1,access_id='"+newpassenger.getAccess_id()+"' where passenger_id=").append(newpassenger.getPassenger_id()).toString()))
		{
			queryBuilder=null;
			Map<String,Object> map_login_response=new HashMap<String,Object>();
			login_history(Opassenger,newpassenger);
			map_login_response.put("passenger", newpassenger);
			if(Opassenger.isIs_need_driver_list())
				map_login_response.put("drivers", baseDAO.getInstance().getEntityClassList("From drivers where is_deleted=false",0));
			
			if(Opassenger.isIs_need_blocked_list())
				map_login_response.put("blocked_drivers", baseDAO.getInstance().getEntityClassList("From blocked_drivers where obooking.opassenger.passenger_id="+newpassenger.getPassenger_id()+" and status=1 group by obooking.odrivers.access_id ",0));
			
			if(Opassenger.isIs_need_ice_list())
				map_login_response.put("ice_list",baseDAO.getInstance().getEntityClassList("From ice where is_deleted<>1 and opassenger.access_id='"+ newpassenger.getAccess_id() +"'",0));
			
				map_login_response.put("favourites", baseDAO.getInstance().getEntityClassList("From favourites where opassenger.passenger_id="+newpassenger.getPassenger_id()+" and is_removed=false ",0));
			
			
			 Opassenger=null;
			 newpassenger=null;
			return map_response(true,map_login_response);
		}
		else
		{
			 queryBuilder=null;
			 Opassenger=null;
			 newpassenger=null;
			return map_response(false,5005);
		}
	}
	private void login_history(passenger Opassenger,passenger newpassenger)
	{
		passenger_login_histories oPassenger_login_histories=new passenger_login_histories();
		oPassenger_login_histories.setLatitude(Opassenger.getLatitude());
		oPassenger_login_histories.setLongitude(Opassenger.getLongitude());
		oPassenger_login_histories.setLogin_time(new java.util.Date());
		oPassenger_login_histories.setDevice_type_name(Opassenger.getDevice_type_name());
		oPassenger_login_histories.setOpassenger(newpassenger);
		newpassenger=null;
		Opassenger=null;
		baseDAO.getInstance().SaveEntityClass(oPassenger_login_histories);
		oPassenger_login_histories=null;
	}
	public Map<String,Object> logout(String JFaccessID) // JSON FORMAT ---> JF
	{
		try
		{
			JSONObject requestJson=new JSONObject(JFaccessID);
			return  requestJson==null?map_response(false,5000):
				    !requestJson.has("access_id")?map_response(false,5025):
				    baseDAO.getInstance().executeHQLQuery("Update passenger set status=0 where access_id='" + 
				    requestJson.getString("access_id") + "'")?
					map_response(true,5026):map_response(false,5025);
		}
		catch(Exception ExpLogout)
		{
	  	  	baseDAO.getInstance().logAnError("srvePassenger", baseDAO.getInstance().stackTraceToString(ExpLogout));
			return map_response(true,5005);
		}
	}
	public Map<String,Object> forgotpassword(passenger Opassenger)
	{
		if(Opassenger==null)
			 return map_response(false,5000); //Invalid JSON Format for Class
		StringBuilder queryBuilder = new StringBuilder();
		passenger ODB_passenger=baseDAO.getInstance().getPassengers(queryBuilder.append("From passenger where mobile=").append(Opassenger.getMobile()).toString());
		queryBuilder=null;
		
		if(ODB_passenger==null)
		{
			Opassenger=null;
			return map_response(false,5008); //Mobile number is not exists
		}
		
		if(ODB_passenger.getIs_active()==0)
		{
			 Opassenger=null;
			 ODB_passenger=null;
			 return map_response(false,5021);  //Mobile number is not activated
		}
		queryBuilder= new StringBuilder();

		int countValue=baseDAO.getInstance().getIntKeyValue(queryBuilder.append("SELECT count(*) FROM passenger_otp where opassenger.passenger_id=").append(ODB_passenger.getPassenger_id()).append(" and Date(sent_on)=Date(now())").toString());
		queryBuilder=null;
		
		if(countValue>2)
		{
			 Opassenger=null;
			 ODB_passenger=null;
			return map_response(false,5032);//Chance Expired
		}
		
		passenger_otp Ouser_otp=new passenger_otp(getOTP());
		queryBuilder= new StringBuilder();
		String SMS_GATEWAY_RESPONSE=SendMessage(Opassenger.getMobile(),queryBuilder.append("Your authentication code is ").append(Ouser_otp.getOtp()).toString());
		queryBuilder=null;
		Opassenger=null;
		//String SMS_GATEWAY_RESPONSE="Message GID=";
		if(SMS_GATEWAY_RESPONSE.contains("Message GID="))
		{
			SMS_GATEWAY_RESPONSE=null;
				Ouser_otp.setOpassenger(ODB_passenger);
				ODB_passenger=null;
				if(baseDAO.getInstance().SaveEntityClass(Ouser_otp))
				{
					Ouser_otp=null;
					if(countValue==1)
						return map_response(true,5034); //"One More Chance is Left"
					    
					else
						return map_response(true,5033); //"Chance is Finished"
				}
					return map_response(false,5005); //"Server is Busy"
		}
		else if(SMS_GATEWAY_RESPONSE.contains("Invalid Mobile Number"))
		{
			SMS_GATEWAY_RESPONSE=null;
			ODB_passenger=null;
			Ouser_otp=null;
			return map_response(false,5003); //"Invalid Mobile Number"
		}
		else
		{

			SMS_GATEWAY_RESPONSE=null;
			ODB_passenger=null;
			Ouser_otp=null;
			return map_response(false,5004); //"SMS server is busy..."
		}
		
	}
	public Map<String,Object> profileUpdate(passenger Opassenger)
	{
		if(Opassenger==null)
			 return map_response(false,5000); //Invalid JSON Format for Class
		StringBuilder queryBuilder = new StringBuilder(); 
		if(baseDAO.getInstance().getPassengers(queryBuilder.append("from passenger where access_id='").append(Opassenger.getAccess_id()).append("'").toString())==null)
		{
			   Opassenger=null;
			   queryBuilder=null;
			 return map_response(false,5025);
		}
		
		
		 queryBuilder= new StringBuilder();
		 
		if(Opassenger.getEmail()!=null)
			queryBuilder.append("email='").append(Opassenger.getEmail()).append("',");
		
		if(Opassenger.getPassenger_name()!=null)
			queryBuilder.append("passenger_name='").append(Opassenger.getPassenger_name()).append("',");

		if(Opassenger.getAddress()!=null)
			queryBuilder.append("address='").append(Opassenger.getAddress()).append("',");
		
		queryBuilder.append("gender=").append(Opassenger.isGender()).append(",");
		
		String queryCommand=queryBuilder.toString();
		queryBuilder=null;
		
		if(queryCommand.length()>2)
			queryCommand=queryCommand.substring(0,queryCommand.length()-1);
		else
		{
			Opassenger=null;
			return map_response(false,5007); //Invalid JSON Format for Class
		}
		 queryBuilder= new StringBuilder();
		if(baseDAO.getInstance().executeHQLQuery(queryBuilder.append("update passenger set ").append(queryCommand).append(" ,updated_on=now() where access_id='").append(Opassenger.getAccess_id()).append("'").toString()))
		{
			queryCommand=null;
			Opassenger=null;
			return map_response(true,5020); //"Updated Successfully..."
		}
		else
		{
			queryCommand=null;
			Opassenger=null;
			return map_response(false,5005); //"Server is busy..."
		}

	}
		
	public Map<String,Object> uploadProfilePhoto(InputStream InpStrmContent,String SUB_FOLDER_PATH,String access_id)
	{
		
		String response_filename=uploadFile(InpStrmContent, SUB_FOLDER_PATH, access_id + ".png");
		if(response_filename.equals(""))
			return map_response(false,5016); // Upload Failure
		else
		{
			if(!baseDAO.getInstance().isValidAccessID(access_id, 2))
			{
				return map_response(false,5025); 
			}
			StringBuilder queryBuilder = new StringBuilder();
			if(baseDAO.getInstance().executeHQLQuery(queryBuilder.append("update passenger set photo_file_id='").append(response_filename).append("' where access_id='").append(access_id).append("'").toString()))
			{
				queryBuilder=null;
				return map_response(true,response_filename); // Upload Success
			}
			else
			{
				queryBuilder=null;
				return map_response(false,5005);
			}
		}
	}
	public Map<String,Object> insertFavourite(favourites oFavourites)
	{
		if(oFavourites==null)
			return map_response(false,5000);
		StringBuilder queryBuilder = new StringBuilder();
		if(oFavourites.getOpassenger()!=null){
		if(!baseDAO.getInstance().isValidAccessID(oFavourites.getOpassenger().getAccess_id(), 2))
		{
			return map_response(false,5025); 
		}}
		
		int countValue=baseDAO.getInstance().getIntKeyValue(queryBuilder.append("SELECT count(*) FROM favourites where opassenger.access_id='").append(oFavourites.getOpassenger().getAccess_id()).append("' and is_removed=false").toString());
		queryBuilder=null;
		if(countValue>=3)
		{
			oFavourites = null;
			return map_response(false,5063);
		}
		oFavourites.setCreated_on(new Date());
		oFavourites.setUpdated_on(new Date());
		queryBuilder= new StringBuilder();
		oFavourites.setOpassenger(baseDAO.getInstance().getPassengers(queryBuilder.append("from passenger where access_id='").append(oFavourites.getOpassenger().getAccess_id()).append("'").toString()));
		queryBuilder=null;
		if(baseDAO.getInstance().SaveEntityClass(oFavourites))
		{
			return map_response(true, oFavourites);
		}
		return map_response(false,5005);
		
	}
	
	public Map<String,Object> selectFavourites(favourites ofavourites)
	{
		if(ofavourites==null)
			 return map_response(false,5000); //Invalid JSON Format for Class
		if(ofavourites.getOpassenger()==null)
		{
			ofavourites = null;
			return map_response(false,5025);
		}
		String accessID = ofavourites.getOpassenger().getAccess_id();
		
		if(!baseDAO.getInstance().isValidAccessID(accessID, 2))
		{
			return map_response(false,5025); 
		}
		
		ofavourites=null;
		List<favourites> favouritesLST=new ArrayList<favourites>();
		StringBuilder queryBuilder = new StringBuilder();
		favouritesLST=baseDAO.getInstance().getFavourites(queryBuilder.append("From favourites where opassenger.access_id='").append(accessID).append("' and is_removed=false").toString(),0);
		queryBuilder=null;
		accessID=null;
		if(!favouritesLST.isEmpty())
    		return map_response(true,favouritesLST);
		else
			return map_response(false,5006);
		
	}
	public Map<String,Object> removefavourites(favourites ofavourites)
	{
		if(ofavourites==null)
			 return map_response(false,5000); //Invalid JSON Format for Class
		if(ofavourites.getOpassenger()!=null){
		if(!baseDAO.getInstance().isValidAccessID(ofavourites.getOpassenger().getAccess_id(), 2))
		{
			return map_response(false,5025); 
		}}
		
		StringBuilder queryBuilder = new StringBuilder();
		boolean is_removed=baseDAO.getInstance().executeHQLQuery(queryBuilder.append("update favourites set is_removed=true, updated_on=now() where favourite_id=").append(ofavourites.getFavourite_id()).toString());
		queryBuilder=null;
		if(is_removed)
			return map_response(true,5020);
		else
			return map_response(false,5005);
	}
	//
	public Map<String,Object> updatefavourites(favourites ofavourites)
	{
		if(ofavourites==null)
			 return map_response(false,5000); //Invalid JSON Format for Class
		if(ofavourites.getOpassenger()!=null){
		if(!baseDAO.getInstance().isValidAccessID(ofavourites.getOpassenger().getAccess_id(), 2))
		{
			return map_response(false,5025); 
		}}
		
		if(baseDAO.getInstance().getFavourites("from favourites where favourite_id="+ofavourites.getFavourite_id()+"",0).isEmpty())
			 return map_response(false,5025);
		
		String queryCommand="";
		if(ofavourites.getAddress()!=null)
			queryCommand="address='" + ofavourites.getAddress() + "',";
		
		if(ofavourites.getLatitude()!=0)
			queryCommand +="latitude=" + ofavourites.getLatitude() + ",";

		if(ofavourites.getLongitude()!=0)
			queryCommand +="longitude=" + ofavourites.getLongitude() + ",";
		
		if(ofavourites.getFavourite_name()!=null)
			queryCommand +="favourite_name='" + ofavourites.getFavourite_name() + "',";
		
		
		if(queryCommand.length()>2)
			queryCommand=queryCommand.substring(0,queryCommand.length()-1);
		else
			return map_response(false,5007); //Invalid JSON Format for Class
		
		if(baseDAO.getInstance().executeHQLQuery("update favourites set "+ queryCommand +" ,updated_on=now() where favourite_id='"+ ofavourites.getFavourite_id() +"'"))
			return map_response(true,5020); //"Updated Successfully..."
		else
			return map_response(false,5005); //"Server is busy..."
	}
	//getratingcount
	public Map<String,Object> getratingcount(passenger oPassenger)
	{
		if(oPassenger==null)
			return map_response(false, 5000);
		@SuppressWarnings("rawtypes")
		List countList=new ArrayList();
		StringBuilder queryBuilder=new StringBuilder();
		if(!baseDAO.getInstance().isValidAccessID(oPassenger.getAccess_id(), 2))
		{
			return map_response(false,5025); 
		}
		
		String sql=queryBuilder.append("select(select IFNULL(clm_total_rating,0) from u2_0xs_tbl_mst_passenger where clm_access_id='").append(oPassenger.getAccess_id()).append("') as total_rating,(select IFNULL(count(*),0) from u2_0xs_tbl_mst_booking booking,u2_0xs_tbl_mst_passenger passenger where booking.clm_status=3 and booking.fk_clm_passenger_id=passenger.pk_clm_passenger_id and passenger.clm_access_id='").append(oPassenger.getAccess_id()).append("') as total_ride,(select IFNULL(count(*),0) from u2_0xs_tbl_mst_booking booking,u2_0xs_tbl_mst_passenger passenger where booking.clm_rating_from_driver <> 0 and booking.fk_clm_passenger_id=passenger.pk_clm_passenger_id and passenger.clm_access_id='").append(oPassenger.getAccess_id()).append("') as rating_count").toString();
		queryBuilder=null;
		countList=baseDAO.getInstance().getMapListObject(sql);
		if(countList.isEmpty())
		{
		countList=null;
		return map_response(false, 5006);
		}
		else
			return map_response(true, countList.get(0));
	}
}
