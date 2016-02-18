package org.strobs.utoo.web.services.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;

import javax.imageio.ImageIO;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import org.springframework.stereotype.Service;
import org.strobs.utoo.web.services.base.utoo;
import org.strobs.utoo.web.services.dao.baseDAO;
import org.strobs.utoo.web.services.eClasses.accident;
import org.strobs.utoo.web.services.eClasses.booking;
import org.strobs.utoo.web.services.eClasses.car_features;
import org.strobs.utoo.web.services.eClasses.car_models;
import org.strobs.utoo.web.services.eClasses.datatablelist;
import org.strobs.utoo.web.services.eClasses.drivers;
import org.strobs.utoo.web.services.eClasses.invoice;
import org.strobs.utoo.web.services.eClasses.location;
import org.strobs.utoo.web.services.eClasses.lost_found;
import org.strobs.utoo.web.services.eClasses.passenger;
import org.strobs.utoo.web.services.eClasses.promocode;
import org.strobs.utoo.web.services.eClasses.qrcode;
import org.strobs.utoo.web.services.eClasses.qrcode_users;
import org.strobs.utoo.web.services.eClasses.tariff;
import org.strobs.utoo.web.services.eClasses.users;
import org.strobs.utoo.web.services.eClasses.usertype;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class srveAdmin extends utoo{
	datatablelist oDatatable=new datatablelist();
	static List<Object>notify_user=new ArrayList<Object>();
	public Map<String,Object> login(users Ousers)
	{
		if(Ousers==null)
			 return map_response(false,5000); //Invalid JSON Format for Class

		Ousers.setAccess_id(UUID.randomUUID().toString().replace("-",""));
		users ODB_user=baseDAO.getInstance().getUsers("from users where mobile=" + Ousers.getMobile() + " and password='" + Ousers.getPassword() + "' and isDeleted=false",0);
		if(ODB_user==null)
			return map_response(false,5013); //"Invalid Credentials
		if(ODB_user.getObjuser_type().getUsertype_name().equals("superuser"))
		{
			Ousers.setSuperuser_otp(getPIN());
			baseDAO.getInstance().executeHQLQuery("Update users set access_id='" + Ousers.getAccess_id() + "',superuser_otp='" + Ousers.getSuperuser_otp() + "' where user_id=" + ODB_user.getUser_id());
			String SMS_GATEWAY_RESPONSE=SendMessage(Ousers.getMobile(),"Your authentication code is " + Ousers.getSuperuser_otp());
			if(SMS_GATEWAY_RESPONSE.contains("Message GID="))
			{
				ODB_user.setAccess_id(Ousers.getAccess_id());
				
				return map_response(true,ODB_user); //"OTP has been successfully..."
			}
			else if(SMS_GATEWAY_RESPONSE.contains("Invalid Mobile Number"))
				return map_response(false,5003); //"Invalid Mobile Number"
			else
				return map_response(false,5004); //"SMS server is busy..."
		
		}
		
		else
		{
			
		baseDAO.getInstance().executeHQLQuery("Update users set access_id='" + Ousers.getAccess_id() + "' where user_id=" + ODB_user.getUser_id());
		
		ODB_user.setAccess_id(Ousers.getAccess_id());
		notify_user.add(ODB_user.getMobile());
		return map_response(ODB_user,5005); // Server is busy...
		}
	}
	public Map<String,Object> verifyOTP(users Ousers)
	{
		if(Ousers==null)
			 return map_response(false,5000);
		
		if(baseDAO.getInstance().getIntKeyValue("Select count(*) from users where superuser_otp=" + Ousers.getSuperuser_otp() + " and access_id='" + Ousers.getAccess_id()+"'")==1)
			return map_response(true,5009);
		else
			return map_response(false,5010);
	}
	@SuppressWarnings("unused")
	public Map<String,Object> logout(users Ousers)
	{
		users ODB_user=baseDAO.getInstance().getUsers("from users where access_id='" + Ousers.getAccess_id() + "' and isDeleted=false",0);
		if(ODB_user==null)
			 return map_response(false,5001);
		
		if(Ousers==null)
			 return map_response(false,5000);
		
		if(baseDAO.getInstance().executeHQLQuery("Update users set access_id='' where access_id='" + Ousers.getAccess_id()+"'")){
			notify_user.remove(ODB_user.getMobile());
			return map_response(true,5026,0);
			
		}
		else
			return map_response(false,0,5005);
	}
	public Map<String, Object> insertcardetails(car_models Ocar_models) {
		if (Ocar_models == null)
			return map_response(false, 5000); // Invalid JSON Format for Class

		try {
			if (baseDAO.getInstance().SaveEntityClass(Ocar_models))
				return map_response(true, Ocar_models);
			else
				return map_response(false, 5005); // "Server is busy..."
		} catch (Exception excExcep) {
			excExcep.printStackTrace();
		}
		return map_response(false, 5005); // "Server is busy..."
	}
	public Map<String, Object> insertusertype(usertype Ouser_type) {
		if (Ouser_type == null)
			return map_response(false, 5000); // Invalid JSON Format for Class

		try {
			if (baseDAO.getInstance().SaveEntityClass(Ouser_type))
			{

				return map_response(true, Ouser_type);
			}
			else
			{	
				Ouser_type=null;
				return map_response(false, 5005); // "Server is busy..."
				}
		} catch (Exception excExcep) {
			Ouser_type=null;
			excExcep.printStackTrace();
		}
		return map_response(false, 5005); // "Server is busy..."
	}
	public String insertusertypeStr(usertype Ouser_type) {
		try {
		if (Ouser_type == null)
			return new ObjectMapper().writeValueAsString(map_response(false, 5000)); // Invalid JSON Format for Class

			if (baseDAO.getInstance().SaveEntityClass(Ouser_type))
				return new ObjectMapper().writeValueAsString(map_response(true, Ouser_type));
			else
				return new ObjectMapper().writeValueAsString(map_response(false, 5005)); // "Server is busy..."
		} catch (Exception excExcep) {
			excExcep.printStackTrace();
			return "";
		}
	}
	public Map<String, Object> insertcarfeatures(car_features Ocar_features) {
		if (Ocar_features == null)
			return map_response(false, 5000); // Invalid JSON Format for Class

		try {
			if (baseDAO.getInstance().SaveEntityClass(Ocar_features))
				return map_response(true, Ocar_features);
		} catch (Exception excExcep) {
			excExcep.printStackTrace();
		}
		return map_response(false, 5005); // "Server is busy..."
	}
	public Map<String, Object> inserttariff(tariff Otariff) {

		if (Otariff == null)
			return map_response(false, 5000); // Invalid JSON Format for Class

		try {
			if (baseDAO.getInstance().SaveEntityClass(Otariff))
				return map_response(true, Otariff);
		} catch (Exception excExcep) {
			excExcep.printStackTrace();
		}
		return map_response(false, 5005); // "Server is busy..."
	}
	public Map<String, Object> insertpromocode(promocode Opromocode) {

		if (Opromocode == null)
			return map_response(false, 5000); // Invalid JSON Format for Class

		try {
			if (baseDAO.getInstance().SaveEntityClass(Opromocode))
				return map_response(true, Opromocode);
		} catch (Exception excExcep) {
			excExcep.printStackTrace();
		}
		return map_response(false, 5005); // "Server is busy..."
	}
	public Map<String, Object> getCarFeatures(car_features Ocar_features) 
	{
		car_features car_FeaturesObj = baseDAO.getInstance().getcarfeatures(Ocar_features.getCars_feature_ID());
		return (car_FeaturesObj != null)?map_response(true, car_FeaturesObj):map_response(false, 5006);
	}
	public Map<String, Object> getUserTypes() 
	{
		List<Object> user_TypeObj = baseDAO.getInstance().getEntityClassList("from usertype order by user_typeid desc ",0);
		return (!user_TypeObj .isEmpty())?map_response(true, user_TypeObj):map_response(false, 5006);
	}
	/*public Map<String, Object> getCarModels(car_models Ocar_models) 
	{
		car_models car_ModelsObj = baseDAO.getInstance().getcarmodel(Ocar_models.getCar_model_id());
		if (car_ModelsObj != null)
			return map_response(true, car_ModelsObj);
		else {
			return map_response(false, 5006);
		}
	}*/
	public Map<String, Object> insertlocation(location Olocation) {

		if (Olocation == null)
			return map_response(false, 5000); // Invalid JSON Format for Class
		Olocation.setChannel(getRandomWord(30));
		try {
			if (baseDAO.getInstance().SaveEntityClass(Olocation))
				return map_response(true, Olocation);
		} catch (Exception excExcep) {
			excExcep.printStackTrace();
		}
		return map_response(false, 5005); // "Server is busy..."
	}
	public Map<String, Object> insertwallet_money(passenger Opassenger) {

		if (Opassenger == null)
			return map_response(false, 5000); // Invalid JSON Format for Class
		String hqlQueryCommand = " update passenger set wallet="+Opassenger.getWallet()+" where mobile= "
				+ Opassenger.getMobile();
		if (baseDAO.getInstance().executeHQLQuery(hqlQueryCommand))

			return map_response(true, 5020);
		else
			return map_response(false, 5005);
		
	}
	
	public Map<String, Object> getLastBooking(booking Obooking) {

		if (Obooking == null)
			return map_response(false, 5000); // Invalid JSON Format for Class
		if(!baseDAO.getInstance().isValidAccessID(Obooking.getOdrivers().getAccess_id(),1))
		{
			return map_response(false,5025);
		}
		String hqlQueryCommand = "from booking where odrivers.access_id='"+Obooking.getOdrivers().getAccess_id()+"' and date(departure_dat)=date(now()) and status not in(3,4) order by booking_id desc";
		Obooking=baseDAO.getInstance().getLastBookingDAO(hqlQueryCommand);
		if(Obooking!=null){
			if(Obooking.getDeparture_dat()!=null)
					{
				Obooking.setAdmin_reached(baseDAO.getInstance().utcToISTFormat(Obooking.getDeparture_dat()));}
			return map_response(true, Obooking);}
		else
			return map_response(false, 5006);
		
	}
	public Map<String, Object> getCarModels() 
	{
		List<Object> user_TypeObj = baseDAO.getInstance().getEntityClassList("from car_models where is_deleted=false order by car_model_id desc",0);
		return (!user_TypeObj.isEmpty())?map_response(true, user_TypeObj):map_response(false, 5006);
	}
	public Map<String, Object> deleteCarFeature(car_features Ocar_features) {
		
		if (Ocar_features == null)
			return map_response(true, 5000);
		if(Ocar_features.getCars_feature_ID()==0)
			return map_response(true, 5000);

		String hqlQueryCommand = " update car_features set is_deleted=true where cars_feature_ID= "
				+ Ocar_features.getCars_feature_ID();
		if (baseDAO.getInstance().executeHQLQuery(hqlQueryCommand))

			return map_response(true, 5020);
		else
			return map_response(false, 5005);
	}

	public Map<String, Object> deleteCarModel(car_models Ocar_models) {
		if (Ocar_models == null)
			return map_response(false, 5000);
		if(Ocar_models.getCar_model_id()==0)
			return map_response(false, 5000);
	
		String hqlQueryCommand = " update car_models set is_deleted=true where car_model_id="
				+ Ocar_models.getCar_model_id();
		if (baseDAO.getInstance().executeHQLQuery(hqlQueryCommand))

			return map_response(true, 5020);
		else
			return map_response(false, 5005);
		
	}
	public Map<String, Object> deletetariff(tariff Otariff) {
		if (Otariff == null)
			return map_response(false, 5000);
		if(Otariff.getTariff_id()==0)
			return map_response(false, 5000);
	
		String hqlQueryCommand = " delete from tariff where tariff_id="
				+ Otariff.getTariff_id();
		if (baseDAO.getInstance().executeHQLQuery(hqlQueryCommand))
			return map_response(true, 5020);
		else
			return map_response(false, 5005);
		
	}
	public Map<String, Object> deletepromocode(promocode Opromocode) {
		if (Opromocode == null)
			return map_response(false, 5000);
		if(Opromocode.getPromo_id()==0)
			return map_response(false, 5000);
	
		String hqlQueryCommand = " delete from promocode where promo_id="
				+ Opromocode.getPromo_id();
		if (baseDAO.getInstance().executeHQLQuery(hqlQueryCommand))
			return map_response(true, 5020);
		else
			return map_response(false, 5005);
		
	}
	public Map<String, Object> deletelostfound(lost_found Olost_found) {
		if (Olost_found == null)
			return map_response(false, 5000);
		if(Olost_found.getLostfound_id()==0)
			return map_response(false, 5000);
	
		String hqlQueryCommand = " delete from lost_found where lostfound_id="
				+ Olost_found.getLostfound_id();
		if (baseDAO.getInstance().executeHQLQuery(hqlQueryCommand))
			return map_response(true, 5020);
		else
			return map_response(false, 5005);
		
	}
	//
	public Map<String, Object> deleteusers(users Ousers) {
		if (Ousers == null)
			return map_response(false, 5000);
		if(Ousers.getAccess_id()==null)
			return map_response(false, 5000);
	
		String hqlQueryCommand = " update users set isDeleted=1 where access_id='"
				+ Ousers.getAccess_id()+"'";
		if (baseDAO.getInstance().executeHQLQuery(hqlQueryCommand))
			return map_response(true, 5020);
		else
			return map_response(false, 5005);
		
	}
	
	public Map<String, Object> getdrivers(drivers Odrivers) 
	{
		drivers driver_ModelsObj = baseDAO.getInstance().getdrivers("from drivers where access_id='"+Odrivers.getAccess_id()+"'",0);
		if (driver_ModelsObj != null)
			return map_response(true, driver_ModelsObj);
		else {
			return map_response(false, 5006);
		}
	}
	
	/*public Map<String, Object> getdrivers_Byname(drivers Odrivers) 
	{
		List<Object> driver_ModelsObj = baseDAO.getInstance().getEntityClassList("from drivers where driver_name='"+Odrivers.getDriver_name()+"'",0);
		return (driver_ModelsObj != null)?map_response(true, driver_ModelsObj):map_response(false, 5006);
	}*/
	
	public Map<String, Object> getAll_AFL_Drivers(drivers Odrivers) 
	{
		List<Object> driver_ModelsObj=new ArrayList<Object>();
		
		if(Odrivers.getDriver_name().trim().length()!=0){
			driver_ModelsObj = baseDAO.getInstance().getEntityClassList("from drivers where "
					+ "driver_name like '"+Odrivers.getDriver_name()+"%' and is_deleted=false",10);	
		}else{
			driver_ModelsObj = baseDAO.getInstance().getEntityClassList("from drivers where is_deleted=false",10);	
		}
		return (!driver_ModelsObj .isEmpty())?map_response(true, driver_ModelsObj):map_response(false, 5006);
	}
	public Map<String, Object> getAll_AFL_Driverspaginate(drivers Odrivers) 
	{
		List<Object> driver_ModelsObj=new ArrayList<Object>();
		if(Odrivers.getStatus()==-1){
			if(Odrivers.getDriver_name().trim().length()!=0){
				driver_ModelsObj = baseDAO.getInstance().getEntityClassList("from drivers where is_deleted=false and driver_id >"+Odrivers.getDriver_id()+""
						+ " and driver_name like '"+Odrivers.getDriver_name()+"%' order by driver_id ",Odrivers.getMaximumRow());	
			}else{
				driver_ModelsObj = baseDAO.getInstance().getEntityClassList("from drivers where is_deleted=false and"
						+ " driver_id > "+Odrivers.getDriver_id()+""
						+ " order by driver_id",Odrivers.getMaximumRow());
			}
		}
		else if(Odrivers.getDriver_name().trim().length()!=0){
			driver_ModelsObj = baseDAO.getInstance().getEntityClassList("from drivers where is_deleted=false and status="+Odrivers.getStatus()+" "
					+ "and driver_id >"+Odrivers.getDriver_id()+""
					+ " and driver_name like '"+Odrivers.getDriver_name()+"%' order by driver_id ",Odrivers.getMaximumRow());	
		}else{
			driver_ModelsObj = baseDAO.getInstance().getEntityClassList("from drivers where is_deleted=false and status="+Odrivers.getStatus()+" "
					+ " and driver_id > "+Odrivers.getDriver_id()+""
					+ " order by driver_id",Odrivers.getMaximumRow());	
		}
		return (!driver_ModelsObj .isEmpty())?map_response(true, driver_ModelsObj):map_response(false, 5006);
	}
	
	public Map<String, Object> getAlldrivers_Bylocation(drivers Odrivers) 
	{
		List<Object> driver_ModelsObj = baseDAO.getInstance().getEntityClassList("FROM drivers where is_deleted=false order by (abs(abs(latitude-"+Odrivers.getLatitude()+")+abs(longitude-"+Odrivers.getLongitude()+"))) asc",20);
		
		return (!driver_ModelsObj.isEmpty())?map_response(true, driver_ModelsObj):map_response(false, 5006);
	}
	
	public Map<String, Object> getBlockdrivers() 
	{
		List<Object> blockeddrivers_ModelsObj = baseDAO.getInstance().getEntityClassList("from blocked_drivers order by block_id desc",0);
		return (!blockeddrivers_ModelsObj.isEmpty())?map_response(true, blockeddrivers_ModelsObj):map_response(false, 5006);
	}
	public Map<String, Object> getBookings() 
	{
		List<Object> booking_ModelsObj = baseDAO.getInstance().getEntityClassList("from booking  order by booking_id desc",0);
		return (!booking_ModelsObj.isEmpty())?map_response(true, booking_ModelsObj):map_response(false, 5006);
	}
	public Map<String, Object> getpromo_Bypassenger(promocode Opromocode) 
	{
		List<Object> passenger_ModelsObj = baseDAO.getInstance().getEntityClassList("from promocode where "
				+ "opassenger.passenger_id= "+Opromocode.getOpassenger().getPassenger_id()+" order by promo_id desc",0);
		return (!passenger_ModelsObj.isEmpty())?map_response(true, passenger_ModelsObj):map_response(false, 5006);
	}
	public Map<String, Object> gettrackbooking(booking Obooking) 
	{
		List<Object> passenger_ModelsObj = baseDAO.getInstance().getEntityClassList("from booking where "
				+ "booking_number= '"+Obooking.getBooking_number()+"'",0);
		return (!passenger_ModelsObj.isEmpty())?map_response(true, passenger_ModelsObj):map_response(false, 5006);
	}
	public Map<String, Object> gettrackbookingNo(booking Obooking) 
	{
		List<Object> passenger_ModelsObj = baseDAO.getInstance().getEntityClassList("select booking_number from booking where "
				+ "odrivers.driver_id= '"+Obooking.getOdrivers().getDriver_id()+"' and odrivers.status=1 order by created_on desc",1);
		return (!passenger_ModelsObj.isEmpty())?map_response(true, passenger_ModelsObj):map_response(false, 5006);
	}
	
					/*Data Table Start Get Functions*/
	
	public Map<String,Object> getBookings_datatable(int draw,int length,int start,String searchValue) 
	{
	    @SuppressWarnings("rawtypes")
		List countlist=null;
		List<booking> lst_booking=new ArrayList<booking>();
		booking obooking=null;
		drivers odriver=null;
		passenger opassenger=null;
		car_models ocarmodel =null;
		Map<String,Object> map_response=new HashMap<String,Object>();
/*		Map<String,Object> bookingCount=new HashMap<String,Object>();
*/		
		if(searchValue.length()==0)
			oDatatable = baseDAO.getInstance().getEntityClassList_DTbooking("from booking order by booking_id desc",length,start);
		else
			oDatatable=baseDAO.getInstance().getEntityClassList_DTbooking("from booking where booking_id like '"+searchValue+"%' or "
					+ "bto_name like '"+searchValue+"%' or "
							+ "bto_mobile like '"+searchValue+"%' or "
							+ "booking_number like '"+searchValue+"%' or "
									+ "odrivers.driver_name like '"+searchValue+"%' or "
											+ "opassenger.passenger_name like '"+searchValue+"%' or "
													+ "rating_points like '"+searchValue+"%' or "
															+ "odrivers.ocars_feature.ocars_model.car_model_name like '"+searchValue+"%'",length,start);
		
		
		
		int indx_glob=1;
		if(oDatatable!=null){
		for(int nIdx=0;nIdx<oDatatable.getGet_bookings().size();nIdx++)
		{
			obooking=new booking();
			odriver=new drivers();
			opassenger= new passenger();
			ocarmodel= new car_models();
			obooking.setBooking_id(indx_glob);
			/*obooking.setBooking_id(oDatatable.getGet_bookings().get(nIdx).getBooking_id());*/
			obooking.setPbr_number(oDatatable.getGet_bookings().get(nIdx).getPbr_number());
			if(oDatatable.getGet_bookings().get(nIdx).getBooking_number()!=null)
			obooking.setBooking_number(IsNullContent(oDatatable.getGet_bookings().get(nIdx).getBooking_number()));			
		    obooking.setStatus(oDatatable.getGet_bookings().get(nIdx).getStatus());
		    
		   // obooking.setSource(IsNullContent(oDatatable.getGet_bookings().get(nIdx).getBooked_source()));
			obooking.setDestination(IsNullContent(oDatatable.getGet_bookings().get(nIdx).getBooked_destination()));
		    obooking.setBooked_source(IsNullContent(oDatatable.getGet_bookings().get(nIdx).getBooked_source()));
		    obooking.setBooking_type(oDatatable.getGet_bookings().get(nIdx).getBooking_type());
		    obooking.setBto_name(IsNullContent(oDatatable.getGet_bookings().get(nIdx).getBto_name()));
		    obooking.setBto_mobile(oDatatable.getGet_bookings().get(nIdx).getBto_mobile());
		    obooking.setUnbook_notes(oDatatable.getGet_bookings().get(nIdx).getUnbook_notes());
		    obooking.setReason_ID(oDatatable.getGet_bookings().get(nIdx).getReason_ID());

			/*0 - Confirm Book
			1 - Guide  for meet the Customer - GUIDE
			2 - Ride with customer - Start Trip
			3 - Ride completed - End Trip
			4 - Cancel - Cancel Trip
			5 - Pending 
			*/
		  
			/* Car model */
			ocarmodel=oDatatable.getGet_bookings().get(nIdx).getOcars_model();
			if(ocarmodel==null)
				ocarmodel=new car_models();
			ocarmodel.setCar_model_name(IsNullContent(ocarmodel.getCar_model_name()));
			obooking.setOcars_model(ocarmodel);	
			
			/* Driver Details */
			odriver=oDatatable.getGet_bookings().get(nIdx).getOdrivers();
			if(odriver==null)
				odriver=new drivers();
			odriver.setDriver_name(IsNullContent(odriver.getDriver_name()));
			obooking.setOdrivers(odriver);

			/*Passenger Details */
			opassenger=oDatatable.getGet_bookings().get(nIdx).getOpassenger();
			if(opassenger==null)
				opassenger=new passenger();
			opassenger.setPassenger_name(IsNullContent(opassenger.getPassenger_name()));
			obooking.setOpassenger(opassenger);
			
			obooking.setRating_points(oDatatable.getGet_bookings().get(nIdx).getRating_points());
			obooking.setPbr_number(IsNullContent(oDatatable.getGet_bookings().get(nIdx).getPbr_number()));
			Date booked=oDatatable.getGet_bookings().get(nIdx).getBooked_dat();
			if(booked!=null)
				obooking.setAdmin_booked(baseDAO.getInstance().utcToISTFormat(booked));
				else
					obooking.setAdmin_booked("");

				Date reached=oDatatable.getGet_bookings().get(nIdx).getReaching_dat();
				if(reached!=null)
				obooking.setAdmin_reached(baseDAO.getInstance().utcToISTFormat(reached));
				else
					obooking.setAdmin_reached("");
			lst_booking.add(obooking);
			indx_glob++;
		}
		}
		  countlist=baseDAO.getInstance().getMapListObject("select(select ifnull(count(*),0) from u2_0xs_tbl_mst_booking where clm_status=0) as confirmbook,"
		    		+ "(select ifnull(count(*),0) from u2_0xs_tbl_mst_booking where clm_status=1) as guide,"
		    		+ "(select ifnull(count(*),0) from u2_0xs_tbl_mst_booking where clm_status=2) as starttrip,"
		    		+ "(select ifnull(count(*),0) from u2_0xs_tbl_mst_booking where clm_status=3) as endtrip,"
		    		+ "(select ifnull(count(*),0) from u2_0xs_tbl_mst_booking where clm_status=4) as canceltrip,"
		    		+ "(select ifnull(count(*),0) from u2_0xs_tbl_mst_booking where clm_status=3) as pending,"
		    		+ "(select ifnull(sum(clm_total),0) from u2_0xs_tbl_mst_invoice) as totalamount");
		map_response.put("draw",draw);
		map_response.put("recordsTotal",oDatatable.getSize());
		map_response.put("recordsFiltered",oDatatable.getSize());
		
		map_response.put("data",lst_booking);
		map_response.put("bookingCount",countlist.get(0));
		
		
		return map_response;
	}
	public Map<String, Object> getbookings_Bydate(String startdate, String enddate,int draw,int length,int start,String searchValue) 
	{
	    @SuppressWarnings("rawtypes")
		List countlist=null;
		List<booking> lst_booking=new ArrayList<booking>();
		Map<String,Object> map_response=new HashMap<String,Object>();
		booking obooking=null;
		passenger opassenger=null;
		car_models ocarmodel=null;
		drivers odriver=null;
		if(searchValue.length()==0){
			oDatatable = baseDAO.getInstance().getEntityClassList_DTbooking("from booking where date(created_on) between "
				+ "'"+startdate+"' and  '"+enddate+"'  order by booking_id desc ",length,start);
		}
		else{
			oDatatable=baseDAO.getInstance().getEntityClassList_DTbooking("from booking where booking_id like '"+searchValue+"%' or "
					+ "bto_name like '"+searchValue+"%' or "
							+ "bto_mobile like '"+searchValue+"%' or "
							+ "booking_number like '"+searchValue+"%' or "
									+ "odrivers.driver_name like '"+searchValue+"%' or "
											+ "opassenger.passenger_name like '"+searchValue+"%' or "
													+ "rating_points like '"+searchValue+"%' or "
															+ "odrivers.ocars_feature.ocars_model.car_model_name like '"+searchValue+"%'",length,start);
		}
				int indx_glob=1;
				if(oDatatable!=null){
			for(int nIdx=0;nIdx<oDatatable.getGet_bookings().size();nIdx++)
			{
				obooking=new booking();
				odriver=new drivers();
				opassenger= new passenger();
				ocarmodel= new car_models();
				obooking.setBooking_id(indx_glob);
				/*obooking.setBooking_id(oDatatable.getGet_bookings().get(nIdx).getBooking_id());*/
				obooking.setPbr_number(oDatatable.getGet_bookings().get(nIdx).getPbr_number());
				if(oDatatable.getGet_bookings().get(nIdx).getBooking_number()!=null)
				obooking.setBooking_number(IsNullContent(oDatatable.getGet_bookings().get(nIdx).getBooking_number()));			
			    obooking.setStatus(oDatatable.getGet_bookings().get(nIdx).getStatus());
			    
			   // obooking.setSource(IsNullContent(oDatatable.getGet_bookings().get(nIdx).getBooked_source()));
				obooking.setDestination(IsNullContent(oDatatable.getGet_bookings().get(nIdx).getBooked_destination()));
			    obooking.setBooked_source(IsNullContent(oDatatable.getGet_bookings().get(nIdx).getBooked_source()));
			    obooking.setBooking_type(oDatatable.getGet_bookings().get(nIdx).getBooking_type());
			    obooking.setBto_name(IsNullContent(oDatatable.getGet_bookings().get(nIdx).getBto_name()));
			    obooking.setBto_mobile(oDatatable.getGet_bookings().get(nIdx).getBto_mobile());
			    obooking.setUnbook_notes(oDatatable.getGet_bookings().get(nIdx).getUnbook_notes());
			    obooking.setReason_ID(oDatatable.getGet_bookings().get(nIdx).getReason_ID());
				/*0 - Confirm Book
				1 - Guide  for meet the Customer - GUIDE
				2 - Ride with customer - Start Trip
				3 - Ride completed - End Trip
				4 - Cancel - Cancel Trip
				5 - Pending 
				*/
			  
				/* Car model */
				ocarmodel=oDatatable.getGet_bookings().get(nIdx).getOcars_model();
				if(ocarmodel==null)
					ocarmodel=new car_models();
				ocarmodel.setCar_model_name(IsNullContent(ocarmodel.getCar_model_name()));
				obooking.setOcars_model(ocarmodel);	
				
				/* Driver Details */
				odriver=oDatatable.getGet_bookings().get(nIdx).getOdrivers();
				if(odriver==null)
					odriver=new drivers();
				odriver.setDriver_name(IsNullContent(odriver.getDriver_name()));
				obooking.setOdrivers(odriver);

				/*Passenger Details */
				opassenger=oDatatable.getGet_bookings().get(nIdx).getOpassenger();
				if(opassenger==null)
					opassenger=new passenger();
				opassenger.setPassenger_name(IsNullContent(opassenger.getPassenger_name()));
				obooking.setOpassenger(opassenger);
				
				obooking.setRating_points(oDatatable.getGet_bookings().get(nIdx).getRating_points());
				obooking.setPbr_number(IsNullContent(oDatatable.getGet_bookings().get(nIdx).getPbr_number()));			
				obooking.setAdmin_booked(baseDAO.getInstance().utcToISTFormat(oDatatable.getGet_bookings().get(nIdx).getBooked_dat()));
				Date booked=oDatatable.getGet_bookings().get(nIdx).getBooked_dat();
				if(booked!=null)
				obooking.setAdmin_booked(baseDAO.getInstance().utcToISTFormat(booked));
				else
					obooking.setAdmin_booked("");

				Date reached=oDatatable.getGet_bookings().get(nIdx).getReaching_dat();
				if(reached!=null)
				obooking.setAdmin_reached(baseDAO.getInstance().utcToISTFormat(reached));
				else
					obooking.setAdmin_reached("");

				lst_booking.add(obooking);
				indx_glob++;
			}
				}

			countlist=baseDAO.getInstance().getMapListObject("select(select ifnull(count(*),0) from u2_0xs_tbl_mst_booking where clm_status=0 and date(clm_created_on) between '"+startdate+"' and  '"+enddate+"') as confirmbook,"
		    		+ "(select ifnull(count(*),0) from u2_0xs_tbl_mst_booking where clm_status=1 and date(clm_created_on) between '"+startdate+"' and  '"+enddate+"') as guide,"
		    		+ "(select ifnull(count(*),0) from u2_0xs_tbl_mst_booking where clm_status=2 and date(clm_created_on) between '"+startdate+"' and  '"+enddate+"') as starttrip,"
		    		+ "(select ifnull(count(*),0) from u2_0xs_tbl_mst_booking where clm_status=3 and date(clm_created_on) between '"+startdate+"' and  '"+enddate+"') as endtrip,"
		    		+ "(select ifnull(count(*),0) from u2_0xs_tbl_mst_booking where clm_status=4 and date(clm_created_on) between '"+startdate+"' and  '"+enddate+"') as canceltrip,"
		    		+ "(select ifnull(count(*),0) from u2_0xs_tbl_mst_booking where clm_status=3 and date(clm_created_on) between '"+startdate+"' and  '"+enddate+"') as pending,"
		    		+ "(select ifnull(sum(clm_total),0) from u2_0xs_tbl_mst_invoice where date(clm_created_on) between '"+startdate+"' and  '"+enddate+"') as totalamount");
		
		map_response.put("draw",draw);
		map_response.put("recordsTotal",oDatatable.getSize());
		map_response.put("recordsFiltered",oDatatable.getSize());
		map_response.put("data",lst_booking);
		map_response.put("bookingCount",countlist.get(0));
		return map_response;
	}
	public Map<String,Object> getuser_datatable(int draw,int length,int start,String searchValue) 
	{
		List<users> lst_users=new ArrayList<users>();
		users ousers=null;
		Map<String,Object> map_response=new HashMap<String,Object>();
		
		if(searchValue.length()==0)
			oDatatable = baseDAO.getInstance().getEntityClassList_DTusers("from users where isDeleted=0  order by user_id desc",length,start);
		else
			oDatatable=baseDAO.getInstance().getEntityClassList_DTusers("from users where isDeleted=0 and (username like '"+searchValue+"%' or "
					+ "mobile like '"+searchValue+"%' or "
							+ "objuser_type.usertype_name like '"+searchValue+"%')",length,start);
		for(int nIdx=0;nIdx<oDatatable.getGet_users().size();nIdx++)
		{
			ousers=new users();
			if(oDatatable.getGet_users().get(nIdx).getAccess_id()!=null)
			ousers.setAccess_id(oDatatable.getGet_users().get(nIdx).getAccess_id());
			else
				ousers.setAccess_id("");	
			String gender=(oDatatable.getGet_users().get(nIdx).isGender())?"Female" : "Male";
			ousers.setGenderStr(gender);
			if(oDatatable.getGet_users().get(nIdx).getMobile()!=0)
			ousers.setMobile(oDatatable.getGet_users().get(nIdx).getMobile());
			else
				ousers.setMobile(0);
			ousers.setObjuser_type(oDatatable.getGet_users().get(nIdx).getObjuser_type());
			/*ousers.setUser_id(oDatatable.getGet_users().get(nIdx).getUser_id());*/
			if(oDatatable.getGet_users().get(nIdx).getUsername()!=null)
			ousers.setUsername(oDatatable.getGet_users().get(nIdx).getUsername());
			else
				ousers.setUsername("");
			lst_users.add(ousers);

		}
		map_response.put("draw",draw);
		map_response.put("recordsTotal",oDatatable.getSize());
		map_response.put("recordsFiltered",oDatatable.getSize());
		map_response.put("data",lst_users);		
		return map_response;
	}
	
	public Map<String, Object> getAlldrivers(int draw,int length,int start,String searchValue) 
	{
		List<drivers> lst_drivers=new ArrayList<drivers>();
		Map<String,Object> map_response=new HashMap<String,Object>();
		Map<String,Object> driversCount=new HashMap<String,Object>();
		drivers odrivers=null;
		car_features ocarfeatures=null;
		car_models ocarmodels=null;
		int availableDrivers=0,offlineDrivers=0,busyDrivers=0;
		oDatatable = baseDAO.getInstance().getEntityClassList_DTdrivers((searchValue.length()==0)?("from drivers where is_deleted=false order by driver_id desc"):("from drivers where is_deleted=false and driver_name like '"+searchValue+"%'"),length,start);
		int index_glob=1;
		for(int nIdx=0;nIdx<oDatatable.getGet_drivers().size();nIdx++)
		{
			odrivers=new drivers();
			ocarfeatures= new car_features();
			ocarmodels= new car_models();
			odrivers.setDriver_id(index_glob);
			/*odrivers.setDriver_id(oDatatable.getGet_drivers().get(nIdx).getDriver_id());*/
			odrivers.setDriver_name(IsNullContent(oDatatable.getGet_drivers().get(nIdx).getDriver_name()));
			odrivers.setAccess_id(IsNullContent(oDatatable.getGet_drivers().get(nIdx).getAccess_id()));
			odrivers.setMobile(oDatatable.getGet_drivers().get(nIdx).getMobile());
			odrivers.setCar_plate_no(IsNullContent(oDatatable.getGet_drivers().get(nIdx).getCar_plate_no()));
			odrivers.setStatus(oDatatable.getGet_drivers().get(nIdx).getStatus());
			odrivers.setPassword(oDatatable.getGet_drivers().get(nIdx).getPass_code());
			switch(oDatatable.getGet_drivers().get(nIdx).getStatus())
					{
					case 0:
						availableDrivers=availableDrivers+1;
						break;
					case 1:
						busyDrivers=busyDrivers+1;
						break;
					case 2:
						offlineDrivers=offlineDrivers+1;
						break;
					default :
						break;
								
					}
				
			
			ocarfeatures=oDatatable.getGet_drivers().get(nIdx).getOcars_feature();
			
			if(ocarfeatures==null)
				ocarfeatures=new car_features();
			
			    ocarfeatures.setRegister_number(IsNullContent(ocarfeatures.getRegister_number()));
				ocarmodels=ocarfeatures.getOcars_model();
			
			if(ocarmodels==null)
				ocarmodels= new car_models();
			ocarmodels.setCar_model_name(IsNullContent(ocarmodels.getCar_model_name()));	
			ocarfeatures.setOcars_model(ocarmodels);
			odrivers.setOcars_feature(ocarfeatures);
			lst_drivers.add(odrivers);
			index_glob++;
		}			
		driversCount.put("availableDrivers", availableDrivers);
		driversCount.put("busyDrivers", busyDrivers);
		driversCount.put("offlineDrivers", offlineDrivers);
	
		map_response.put("draw",draw);
		map_response.put("recordsTotal",oDatatable.getSize());
		map_response.put("recordsFiltered",oDatatable.getSize());
		map_response.put("driversCount",driversCount);
		map_response.put("data",lst_drivers);
		return map_response;
	}
	
	@SuppressWarnings("deprecation")
	public Map<String, Object> getAlltariff(int draw,int length,int start,String searchValue) 
	{
		List<tariff> lst_tariff=new ArrayList<tariff>();
		Map<String,Object> map_response=new HashMap<String,Object>();
		tariff otariff=null;
		if(searchValue.length()==0){
			oDatatable = baseDAO.getInstance().getEntityClassList_DTtariff("from tariff ",length,start);
		}else{
			oDatatable = baseDAO.getInstance().getEntityClassList_DTtariff("from tariff where  "
					+ "flat_amount like '"+searchValue+"%' or "
							+ "min_charge like '"+searchValue+"%' or "
									+ "waitingchrg_per_min like '"+searchValue+"%' or "
											+ "carmodel_id.car_model_name like '"+searchValue+"%'",length,start);
		}
				int index_glob=1;
			for(int nIdx=0;nIdx<oDatatable.getGet_tariff().size();nIdx++)
			{
				otariff=new tariff();
				otariff.setTariff_id(index_glob);
				//otariff.setAfter_minkms((oDatatable.getGet_tariff().get(nIdx).getAfter_minkms()));
				if(oDatatable.getGet_tariff().get(nIdx).getEffective_from()==null)
				otariff.setEffective_Sfrom("");
				else
					otariff.setEffective_Sfrom((oDatatable.getGet_tariff().get(nIdx).getEffective_from().toLocaleString()));
				if(oDatatable.getGet_tariff().get(nIdx).getEffective_to()==null)
					otariff.setEffective_Sto("");
				else
					otariff.setEffective_Sto((oDatatable.getGet_tariff().get(nIdx).getEffective_to().toLocaleString()));
				if(oDatatable.getGet_tariff().get(nIdx).getFlat_amount()!=0)
				otariff.setFlat_amount((oDatatable.getGet_tariff().get(nIdx).getFlat_amount()));
				else
					otariff.setFlat_amount(0);
				if(oDatatable.getGet_tariff().get(nIdx).getMin_charge()!=0)
				otariff.setMin_charge((oDatatable.getGet_tariff().get(nIdx).getMin_charge()));
				else
					otariff.setMin_charge(0);
				if(oDatatable.getGet_tariff().get(nIdx).getMin_kms()!=0)
				otariff.setMin_kms((oDatatable.getGet_tariff().get(nIdx).getMin_kms()));
				else
					otariff.setMin_kms(0);
				if(oDatatable.getGet_tariff().get(nIdx).getWaitingchrg_per_min()!=0)
				otariff.setWaitingchrg_per_min((oDatatable.getGet_tariff().get(nIdx).getWaitingchrg_per_min()));
				else
					otariff.setWaitingchrg_per_min(0);
				otariff.setCarmodel_id(oDatatable.getGet_tariff().get(nIdx).getCarmodel_id());
				lst_tariff.add(otariff);
				index_glob++;
			}			
		
		map_response.put("draw",draw);
		map_response.put("recordsTotal",oDatatable.getSize());
		map_response.put("recordsFiltered",oDatatable.getSize());
		map_response.put("data",lst_tariff);
		return map_response;
	}
	public Map<String, Object> getpromo_codes(int draw,int length,int start,String searchValue) 
	{
		List<passenger> lst_promocode=new ArrayList<passenger>();
		Map<String,Object> map_response=new HashMap<String,Object>();
		passenger opassenger=null;
		if(searchValue.length()==0)
			oDatatable = baseDAO.getInstance().getEntityClassList_DTpromocode("from passenger order by passenger_id desc ",length,start);
		else
			oDatatable = baseDAO.getInstance().getEntityClassList_DTpromocode("from passenger  where passenger_name like '"+searchValue+"%' or "
					+ "mobile like '"+searchValue+"%' or "
							+ "promo_code like '"+searchValue+"%' or device_type_name like '"+searchValue+"%' ",length,start);
				int index_glob=1;
			for(int nIdx=0;nIdx<oDatatable.getGet_promocode().size();nIdx++)
			{
				opassenger=new passenger();
				opassenger.setPassenger_id(index_glob);
				if(oDatatable.getGet_promocode().get(nIdx).getPassenger_name()!=null)
				opassenger.setPassenger_name(oDatatable.getGet_promocode().get(nIdx).getPassenger_name());
				else
					opassenger.setPassenger_name("");
				if(oDatatable.getGet_promocode().get(nIdx).getPromo_code()!=null)
				opassenger.setPromo_code(oDatatable.getGet_promocode().get(nIdx).getPromo_code());
				else
					opassenger.setPromo_code("");
				if(oDatatable.getGet_promocode().get(nIdx).getDevice_type_name()!=null)
				opassenger.setDevice_type_name(oDatatable.getGet_promocode().get(nIdx).getDevice_type_name());
				else
					opassenger.setDevice_type_name("");
				if(oDatatable.getGet_promocode().get(nIdx).getMobile()!=0)
				opassenger.setMobile(oDatatable.getGet_promocode().get(nIdx).getMobile());
				else
					opassenger.setMobile(0);
				if(oDatatable.getGet_promocode().get(nIdx).getAddress()!=null)
				opassenger.setAddress(oDatatable.getGet_promocode().get(nIdx).getAddress());
				else
					opassenger.setAddress("");
				lst_promocode.add(opassenger);
				index_glob++;
			}			
		
		map_response.put("draw",draw);
		map_response.put("recordsTotal",oDatatable.getSize());
		map_response.put("recordsFiltered",oDatatable.getSize());
		map_response.put("data",lst_promocode);
		return map_response;
	}
	@SuppressWarnings("deprecation")
	public Map<String, Object> getAccidentdrivers(int draw,int length,int start,String searchValue) 
	{
		List<accident> lst_accident=new ArrayList<accident>();
		Map<String,Object> map_response=new HashMap<String,Object>();
		accident oaccident=null;
		drivers odriver=null;
		car_features ocarfeatures=null;
		if(searchValue.length()==0)
			oDatatable = baseDAO.getInstance().getEntityClassList_DTaccident("from accident order by accident_id desc",length,start);
		else
			oDatatable = baseDAO.getInstance().getEntityClassList_DTaccident("from accident  where accident_id like '"+searchValue+"%' or "
					+ "accident_note like '"+searchValue+"%' or "
									+ "odrivers.driver_name like '"+searchValue+"%' or "
											+ "odrivers.mobile like '"+searchValue+"%' or "
																	+ "odrivers.ocars_feature.register_number like '"+searchValue+"%'",length,start);
			int index_glob=1;
			for(int nIdx=0;nIdx<oDatatable.getGet_accident().size();nIdx++)
			{
				oaccident=new accident();
 				odriver=new drivers();
				ocarfeatures= new car_features();
				oaccident.setAccident_id(index_glob);
				if(oDatatable.getGet_accident().get(nIdx).getAccident_note()!=null)
				oaccident.setAccident_note(oDatatable.getGet_accident().get(nIdx).getAccident_note());
				else
					oaccident.setAccident_note("No Notes...");
				if(oDatatable.getGet_accident().get(nIdx).getCreated_on().toLocaleString()==null)
				oaccident.setCreated_sON("");	
				else
					oaccident.setCreated_sON(oDatatable.getGet_accident().get(nIdx).getCreated_on().toLocaleString());
			
				odriver=oDatatable.getGet_accident().get(nIdx).getOdrivers();
				oaccident.setVoice_note_filename(IsNullContent(oDatatable.getGet_accident().get(nIdx).getVoice_note_filename()));
				oaccident.setAccident_photo_filename(IsNullContent(oDatatable.getGet_accident().get(nIdx).getAccident_photo_filename()));
				if(odriver==null)
					odriver= new drivers();
			
				odriver.setDriver_name(IsNullContent(odriver.getDriver_name()));
				ocarfeatures=odriver.getOcars_feature();
				
				if(ocarfeatures==null)
					ocarfeatures= new car_features();
				
				ocarfeatures.setRegister_number(IsNullContent(ocarfeatures.getRegister_number()));
				odriver.setOcars_feature(ocarfeatures);
				oaccident.setOdrivers(odriver);
				lst_accident.add(oaccident);
				index_glob++;
			}			
		
		map_response.put("draw",draw);
		map_response.put("recordsTotal",oDatatable.getSize());
		map_response.put("recordsFiltered",oDatatable.getSize());
		map_response.put("data",lst_accident);
		return map_response;
	}
	public Map<String, Object> getlostfound(int draw,int length,int start,String searchValue) 
	{
		List<lost_found> lst_lost_found=new ArrayList<lost_found>();
		Map<String,Object> map_response=new HashMap<String,Object>();
		lost_found olost_found=null;
		booking obookings=null;
		drivers odriver=null;
		passenger opassenger=null;
		car_features ocarfeatures=null;
		if(searchValue.length()==0){
			oDatatable = baseDAO.getInstance().getEntityClassList_DTlost_found("from lost_found  order by lostfound_id desc",length,start);
		}else{
			oDatatable = baseDAO.getInstance().getEntityClassList_DTlost_found("from lost_found where  lostfound_id like '"+searchValue+"%' or "
					+ " description like '"+searchValue+"%' or "
							+ " lostobject like '"+searchValue+"%' or "
									+ "obooking.odrivers.driver_name like '"+searchValue+"%' or "
											+ "obooking.odrivers.mobile like '"+searchValue+"%' or "
													+ "obooking.opassenger.passenger_name like '"+searchValue+"%' or "
															+ "obooking.opassenger.mobile like '"+searchValue+"%' or "
																	+ "obooking.odrivers.ocars_feature.register_number like '"+searchValue+"%' ",length,start);
		}
				int index_glob=1;
			for(int nIdx=0;nIdx<oDatatable.getGet_lost_found().size();nIdx++)
			{
				olost_found=new lost_found();
				if(oDatatable.getGet_lost_found().get(nIdx).getDescription()!=null)
				olost_found.setDescription(oDatatable.getGet_lost_found().get(nIdx).getDescription());
				else
					olost_found.setDescription("No Description...");
				olost_found.setLostfound_id(index_glob);
				if(oDatatable.getGet_lost_found().get(nIdx).getLostobject()!=null)
				olost_found.setLostobject((oDatatable.getGet_lost_found().get(nIdx).getLostobject()));
				else
					olost_found.setLostobject("No Lost Object");
				obookings=oDatatable.getGet_lost_found().get(nIdx).getObooking();
				odriver=obookings.getOdrivers();
				opassenger=obookings.getOpassenger();
				ocarfeatures=odriver.getOcars_feature();
				if(ocarfeatures!=null){
				if(ocarfeatures.getRegister_number()==null || ocarfeatures.getRegister_number()=="")
					ocarfeatures.setRegister_number("");
				}
				odriver.setOcars_feature(ocarfeatures);
				if(odriver.getMobile()==0)
					odriver.setMobile(0);
				if(odriver.getDriver_name()=="")
					odriver.setDriver_name("");
				obookings.setOdrivers(odriver);
				if(opassenger.getMobile()==0)
					odriver.setMobile(0);
				if(opassenger.getPassenger_name()=="")
					opassenger.setPassenger_name("");
				obookings.setOpassenger(opassenger);
				
				olost_found.setObooking(obookings);
				lst_lost_found.add(olost_found);
				index_glob++;
			}			
		
		map_response.put("draw",draw);
		map_response.put("recordsTotal",oDatatable.getSize());
		map_response.put("recordsFiltered",oDatatable.getSize());
		map_response.put("data",lst_lost_found);
		return map_response;
	}
	public Map<String, Object> getcarmodels_datatable(int draw,int length,int start,String searchValue) 
	{
		List<car_models> lst_car_models=new ArrayList<car_models>();
		Map<String,Object> map_response=new HashMap<String,Object>();
		car_models ocar_models=null;
		if(searchValue.length()==0){
			oDatatable = baseDAO.getInstance().getEntityClassList_DTcarmodels("from car_models where is_deleted=false order by car_model_id desc ",length,start);
		}
			
			for(int nIdx=0;nIdx<oDatatable.getGet_car_models().size();nIdx++)
			{
				ocar_models=new car_models();
				ocar_models.setCar_model_id(oDatatable.getGet_car_models().get(nIdx).getCar_model_id());
				if(oDatatable.getGet_car_models().get(nIdx).getCar_model_name()!=null)
				ocar_models.setCar_model_name(oDatatable.getGet_car_models().get(nIdx).getCar_model_name());
				else
					ocar_models.setCar_model_name("No Model");
				if(oDatatable.getGet_car_models().get(nIdx).getCar_model_description()!=null)
				ocar_models.setCar_model_description(oDatatable.getGet_car_models().get(nIdx).getCar_model_description());
				else
					ocar_models.setCar_model_description("No Description...");
				lst_car_models.add(ocar_models);
			}			
		
		map_response.put("draw",draw);
		map_response.put("recordsTotal",oDatatable.getSize());
		map_response.put("recordsFiltered",oDatatable.getSize());
		map_response.put("data",lst_car_models);
		return map_response;
	}
	public Map<String, Object> getcarfeatures_datatable(int draw,int length,int start,String searchValue) 
	{
		List<car_features> lst_car_features=new ArrayList<car_features>();
		Map<String,Object> map_response=new HashMap<String,Object>();
		car_features ocar_features=null;
		if(searchValue.length()==0){
			oDatatable = baseDAO.getInstance().getEntityClassList_DTcarfeatures("from car_features where is_deleted=false order by cars_feature_ID desc",length,start);
		}
			for(int nIdx=0;nIdx<oDatatable.getGet_car_features().size();nIdx++)
			{
				ocar_features=new car_features();
				ocar_features.setCars_feature_ID(oDatatable.getGet_car_features().get(nIdx).getCars_feature_ID());
				if(oDatatable.getGet_car_features().get(nIdx).getColor()!=null)
				ocar_features.setColor(oDatatable.getGet_car_features().get(nIdx).getColor());
				else
					ocar_features.setColor("");	
				if(oDatatable.getGet_car_features().get(nIdx).getRegister_number()!=null)
				ocar_features.setRegister_number(oDatatable.getGet_car_features().get(nIdx).getRegister_number());
				else
					ocar_features.setRegister_number("");
				if(oDatatable.getGet_car_features().get(nIdx).getSeat_count()!=0)
				ocar_features.setSeat_count(oDatatable.getGet_car_features().get(nIdx).getSeat_count());
				else
					ocar_features.setSeat_count(0);
				if(oDatatable.getGet_car_features().get(nIdx).getVehicle_year()!=0)
				ocar_features.setVehicle_year(oDatatable.getGet_car_features().get(nIdx).getVehicle_year());
				else
					ocar_features.setVehicle_year(0000-00-00);
				ocar_features.setOcars_model(oDatatable.getGet_car_features().get(nIdx).getOcars_model());
				
				lst_car_features.add(ocar_features);
			}			
		
		map_response.put("draw",draw);
		map_response.put("recordsTotal",oDatatable.getSize());
		map_response.put("recordsFiltered",oDatatable.getSize());
		map_response.put("data",lst_car_features);
		return map_response;
	}
	
	public Map<String, Object> getlocation_datatable(int draw,int length,int start,String searchValue) 
	{
		List<location> lst_location=new ArrayList<location>();
		Map<String,Object> map_response=new HashMap<String,Object>();
		location olocation=null;
		if(searchValue.length()==0){
			oDatatable = baseDAO.getInstance().getEntityClassList_DTlocation("from location order by location_id desc",length,start);
		}
			for(int nIdx=0;nIdx<oDatatable.getGet_location().size();nIdx++)
			{
				olocation=new location();
				olocation.setLocation_id(oDatatable.getGet_location().get(nIdx).getLocation_id());//ID
				
				if(oDatatable.getGet_location().get(nIdx).getEast_longitude()!=0)
					olocation.setEast_longitude(oDatatable.getGet_location().get(nIdx).getEast_longitude());
				else
					olocation.setEast_longitude(0.000);//East
				
				if(oDatatable.getGet_location().get(nIdx).getWest_longitude()!=0)
					olocation.setWest_longitude(oDatatable.getGet_location().get(nIdx).getWest_longitude());
				else
					olocation.setEast_longitude(0.000);//West
				
				if(oDatatable.getGet_location().get(nIdx).getSouth_latitude()!=0)
					olocation.setSouth_latitude(oDatatable.getGet_location().get(nIdx).getSouth_latitude());
				else
					olocation.setEast_longitude(0.000);//South
				
				if(oDatatable.getGet_location().get(nIdx).getNorth_latitude()!=0)
					olocation.setNorth_latitude(oDatatable.getGet_location().get(nIdx).getNorth_latitude());
				else
					olocation.setEast_longitude(0.000);//North
				
				if(oDatatable.getGet_location().get(nIdx).getLocation_name()!=null)
					olocation.setLocation_name(oDatatable.getGet_location().get(nIdx).getLocation_name());
				else
					olocation.setEast_longitude(0.000);//Location Name
				
				if(oDatatable.getGet_location().get(nIdx).getChannel()!=null)
					olocation.setChannel(oDatatable.getGet_location().get(nIdx).getChannel());
				else
					olocation.setEast_longitude(0.000);//Channel name
				
				lst_location.add(olocation);
			}			
		
		map_response.put("draw",draw);
		map_response.put("recordsTotal",oDatatable.getSize());
		map_response.put("recordsFiltered",oDatatable.getSize());
		map_response.put("data",lst_location);
		return map_response;
	}
	public Map<String, Object> getqrcodes_datatable(int draw,int length,int start,String searchValue) 
	{
		List<qrcode_users> lst_qrcode_users=new ArrayList<qrcode_users>();
		Map<String,Object> map_response=new HashMap<String,Object>();
		qrcode_users oqrcode_users=null;
		if(searchValue.length()==0){
			oDatatable = baseDAO.getInstance().getEntityClassList_DTqrcode_users("from qrcode_users order by qrcode_user_id desc",length,start);
		}
		if(oDatatable==null){
			map_response.put("draw",draw);
			map_response.put("recordsTotal",0);
			map_response.put("recordsFiltered",0);
			map_response.put("data",lst_qrcode_users);
		}else{
			for(int nIdx=0;nIdx<oDatatable.getGet_qrcodeusers().size();nIdx++)
			{
				oqrcode_users=new qrcode_users();
				oqrcode_users.setQrcode_user_id(oDatatable.getGet_qrcodeusers().get(nIdx).getQrcode_user_id());
				oqrcode_users.setQrcode_username(oDatatable.getGet_qrcodeusers().get(nIdx).getQrcode_username());
				oqrcode_users.setQrcode_mobile_number(oDatatable.getGet_qrcodeusers().get(nIdx).getQrcode_mobile_number());
				oqrcode_users.setBooked_destination(oDatatable.getGet_qrcodeusers().get(nIdx).getBooked_destination());
				oqrcode_users.setAdvance_amount(oDatatable.getGet_qrcodeusers().get(nIdx).getAdvance_amount());
			
				try
				{
					
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date from = sdf.parse(oDatatable.getGet_qrcodeusers().get(nIdx).getFrom().toString()); 
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				oqrcode_users.setFrom(sdf.format(from));
				Date to = sdf.parse(oDatatable.getGet_qrcodeusers().get(nIdx).getTo()); 
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				oqrcode_users.setTo(sdf.format(to));
				oqrcode_users.setMessage(oDatatable.getGet_qrcodeusers().get(nIdx).getMessage());
				lst_qrcode_users.add(oqrcode_users);
				
				}
				catch(Exception ex)
				{
					map_response.put("Error","Wrong Date Format");
					return map_response;
				}
			}		
		}
		map_response.put("draw",draw);
		map_response.put("recordsTotal",oDatatable.getSize());
		map_response.put("recordsFiltered",oDatatable.getSize());
		map_response.put("data",lst_qrcode_users);
		return map_response;
	}
	
					/*Data Table End Get Functions*/
	
	public Map<String,Object> updatecarfeature(car_features Ocarfeatures)
	{
		if(Ocarfeatures==null)  
			return map_response(false,5005);
		if(Ocarfeatures.getCars_feature_ID()==0)  
			return map_response(false,5006);
		car_features Oupdatecarfeature=baseDAO.getInstance().updatecarfeature(Ocarfeatures);
		
		if(Oupdatecarfeature==null)
			return map_response(false,5006);
		else
			return map_response(true,Oupdatecarfeature);	
			
	}
	public Map<String,Object> updatemodals(car_models Ocarmodels)
	{
		if(Ocarmodels==null)  
			return map_response(false,5005);
		if(Ocarmodels.getCar_model_id()==0)  
			return map_response(false,5006);
		car_models Oupdatecarmodals=baseDAO.getInstance().updatecarmodals(Ocarmodels);
		
		if(Oupdatecarmodals==null)
			return map_response(false,5006);	
		else
			return map_response(true,Oupdatecarmodals);	
			
	}
	public Map<String,Object> updatetariff(tariff Otariff)
	{
		if(Otariff==null)  
			return map_response(false,5005);
		if(Otariff.getTariff_id()==0)  
			return map_response(false,5006);
		tariff Oupdatetariff=baseDAO.getInstance().updatetariff(Otariff);
		
		if(Oupdatetariff==null)
			return map_response(false,5006);	
		else
			return map_response(true,Oupdatetariff);	
			
	}
	public Map<String,Object> updatepromocode(promocode Opromocode)
	{
		if(Opromocode==null)  
			return map_response(false,5005);
		if(Opromocode.getPromo_id()==0)  
			return map_response(false,5006);
		promocode Oupdatepromocode=baseDAO.getInstance().updatepromocode(Opromocode);
		
		if(Oupdatepromocode==null)
			return map_response(false,5006);	
		else
			return map_response(true,Oupdatepromocode);	
			
	}
	public Map<String,Object> updateuser(users Ouser)
	{
		if(baseDAO.getInstance().getusers("from users where access_id='"+Ouser.getAccess_id()+"'",0)==null)
			 return map_response(false,5025);
		
		String queryCommand="";
		if(Ouser.getMobile()!=0)
			queryCommand+=queryCommand+"mobile=" + Ouser.getMobile() + ",";
		if(Ouser.getUsername()!=null)
			queryCommand+=queryCommand+"username='" + Ouser.getUsername() + "',";
		if(Ouser.getObjuser_type().getUser_typeid()!=0)
			queryCommand+="objuser_type.user_typeid=" + Ouser.getObjuser_type().getUser_typeid() + ",";
		if(Ouser.getAdminUsertype().equals("1"))
			queryCommand+="password='" + Ouser.getPassword() + "',";
		queryCommand+="gender=" + Ouser.isGender() + ",";
		
		if(queryCommand.length()>2)
			queryCommand=queryCommand.substring(0,queryCommand.length()-1);
		else
			return map_response(false,5007); //Invalid JSON Format for Class
		
		
		if(baseDAO.getInstance().executeHQLQuery("update users set "+ queryCommand +" ,updated_on=now() where access_id='"+ Ouser.getAccess_id() +"'"))
			return map_response(true,5020); //"Updated Successfully..."
		else
			return map_response(false,5005); //"Server is busy..."
	}
	public Map<String, Object> insertUser(users Ousers) {
		if (Ousers == null)
			return map_response(false, 5000); // Invalid JSON Format for Class

		try {
			Ousers.setAccess_id(UUID.randomUUID().toString().replace("-",""));

			if (baseDAO.getInstance().SaveEntityClass(Ousers))
				return map_response(true, Ousers);
			else
				return map_response(false, 5005); // "Server is busy..."
		} catch (Exception excExcep) {
			excExcep.printStackTrace();
		}
		return map_response(false, 5005); // "Server is busy..."
	}
	//
	public Map<String, Object> insertQRService(qrcode Oqrcode) {
		if (Oqrcode == null)
			return map_response(false, 5000); // Invalid JSON Format for Class
		Map<String,Object> map_response=new HashMap<String,Object>();
		List<Object> resultList=new ArrayList<Object>();
		Properties configFile = new Properties();
		PdfPTable table=null;
		Document document = new Document();
		qrcode_users oqrcode_user=null;
		String imageName;
		ByteArrayOutputStream baosPDF=null;
		try {
			configFile.load(passenger.class.getClassLoader().getResourceAsStream("utoo.properties"));
			oqrcode_user=Oqrcode.getOqrcode_user();
			oqrcode_user.setQcr(baseDAO.getInstance().getRandomNumber(10));

			DateFormat formatterUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat formatterIST = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			formatterUTC.setTimeZone(TimeZone.getTimeZone("UTC")); // UTC timezone
			
			Date fromDate = formatterIST.parse(oqrcode_user.getFrom());
			Date toDate = formatterIST.parse(oqrcode_user.getTo());
			
			oqrcode_user.setFrom(formatterUTC.format(fromDate));
			oqrcode_user.setTo(formatterUTC.format(toDate));
			
			oqrcode_user.setMessage(oqrcode_user.getMessage());
			oqrcode_user.setLatitude(oqrcode_user.getLatitude());
			oqrcode_user.setLongitude(oqrcode_user.getLongitude());
		    imageName=oqrcode_user.getQcr()+".pdf";
			//filename=configFile.getProperty("QR_PDF_FILE")+imageName;
			 baosPDF = new ByteArrayOutputStream();	
		//	PdfWriter.getInstance(document, new FileOutputStream(filename));
			PdfWriter.getInstance(document, baosPDF);

			map_response.put("qrcode_filename", oqrcode_user.getQcr()+".pdf");
			
		}
			 catch (Exception excExcep) {
					excExcep.printStackTrace();
					return map_response(false, 5061);
				}
			 
		try
		{
		 	document.open();
			table=new PdfPTable(5);
			int len=Oqrcode.getCount();
			
			if(len%5!=0)
			{
				len=(len+(5-(len%5)));
			}
			
			if (baseDAO.getInstance().SaveEntityClass(oqrcode_user))
			{
				for(int i=0;i<len;i++)
				{
					Oqrcode.setQrcode_unique_number(baseDAO.getInstance().getRandomWord(6));
					Oqrcode.setOqrcode_user(oqrcode_user);
					Image image=getImageFromQR(Oqrcode.getQrcode_unique_number());
					if(i<Oqrcode.getCount())
					{
					if(image!=null){
					
						table.addCell(image);
					}
					else
					{
						Image image1=getEmptyQR(configFile.getProperty("QR_PDF_FILE"));
						table.addCell(image1);
					}
					}else
					{
						Image image1=getEmptyQR(configFile.getProperty("QR_PDF_FILE"));
						table.addCell(image1);
					}
					Oqrcode.setQrcode_filename(imageName);
					if(baseDAO.getInstance().SaveEntityQR(Oqrcode))
					{		
					resultList.add(Oqrcode.getQrcode_unique_number());
					}
				}
				document.add(table);
				document.close();
				ByteArrayInputStream newInput=new ByteArrayInputStream(baosPDF.toByteArray());
				baseDAO.getInstance().uploadQR(newInput, "0xf_qrcode_image", imageName);
				map_response.put("Qrcode_unique_number", resultList);
				//uploadQR(new FileInputStream(filename),"0xf_qrcode_image",  oqrcode_user.getQcr() + ".pdf");
				return map_response(true, map_response);
			}
			else
				return map_response(false, 5005); 
		} catch (Exception excExcep) {
			excExcep.printStackTrace();
			return map_response(false, 5060);
		}
		
	}
	public Image getImageFromQR(String ReferalCode) {
		 ByteArrayOutputStream out = QRCode.from(ReferalCode).withSize(90, 90)
	                .to(ImageType.PNG).stream();
			 ByteArrayInputStream inputStream=null;
			 try {
				 inputStream=new ByteArrayInputStream(out.toByteArray()); 
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
          BufferedImage buff_image = ImageIO.read(inputStream);
          ImageIO.write(buff_image, "png", baos);
          baos.flush();
          byte[] imageInByte = baos.toByteArray();
          baos.close();
          Image image = Image.getInstance(imageInByte);
          return image;
			 }
			 catch(Exception ex)
			 {
				 return null;
			 }
	} 
	public Map<String, Object> qrVerify(qrcode Oqrcode) {
		if (Oqrcode == null)
			return map_response(false, 5000); // Invalid JSON Format for Class
		
		qrcode objQrcode= null;
		try {
			
			if(Oqrcode.getQrcode_unique_number()==null || Oqrcode.getQrcode_unique_number()==""){
				return map_response(false, 5000); // Invalid JSON Format for Class
			}

			objQrcode = baseDAO.getInstance().getQRdetails("from qrcode where qrcode_unique_number='"+Oqrcode.getQrcode_unique_number()+"'");
			if(objQrcode==null)
			{
				return map_response(false, 5056); // Invalid QR Code
			}
			try
			{
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date from = sdf.parse(objQrcode.getOqrcode_user().getFrom().toString()); 
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				objQrcode.getOqrcode_user().setFrom(sdf.format(from));
				
				Date to = sdf.parse(objQrcode.getOqrcode_user().getTo().toString()); 
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				objQrcode.getOqrcode_user().setTo(sdf.format(to));
			
			}catch(Exception excExcep){
				excExcep.printStackTrace();
				return map_response(false, 5061);
			}
					switch(objQrcode.getQr_status()){
					case 0:
						return map_response(true, objQrcode); // "Successfully Verified"
						
					case 1:
						return map_response(false, 5058); // QR Code Inactive
						
					default:
					    return map_response(false, 5056); //  Invalid QR Code
					}
					
		}
		catch (Exception excExcep) {
			excExcep.printStackTrace();
		}
			return map_response(false, 5005); // "Server is busy..."
	}
	
	//getHistory
	public Map<String, Object> getHistory(int draw,int length,int start,String searchValue,String access_id) {
		List<invoice> lst_invoice=new ArrayList<invoice>();
		invoice oinvoice=null;
		booking obooking=null;
		Map<String,Object> map_response=new HashMap<String,Object>();
		
			oDatatable = baseDAO.getInstance().getEntityClassList_DTinvoice("from invoice where obooking.odrivers.driver_id="+baseDAO.getInstance().getIntKeyValue("select driver_id from drivers where access_id='"+access_id+"'")+"   order by date(created_on) desc",length,start);
			for(int nIdx=0;nIdx<oDatatable.getGet_invoice().size();nIdx++)
		{
			oinvoice=new invoice();
				obooking= new booking();
			if(oDatatable.getGet_invoice().get(nIdx).getTotal()!=0)
			oinvoice.setAmount(oDatatable.getGet_invoice().get(nIdx).getTotal());
			else
				oinvoice.setAmount(0);
			if(oDatatable.getGet_invoice().get(nIdx).getDistance()!=0)
				oinvoice.setDistance(oDatatable.getGet_invoice().get(nIdx).getDistance());
			else
				oinvoice.setDistance(0);
			if(oDatatable.getGet_invoice().get(nIdx).getTotal_mins()!=0)
				oinvoice.setTotal_mins(oDatatable.getGet_invoice().get(nIdx).getTotal_mins());
				else
			    oinvoice.setTotal_mins(0);
			oinvoice.setInvoice_id(oDatatable.getGet_invoice().get(nIdx).getInvoice_id());
			obooking=oDatatable.getGet_invoice().get(nIdx).getObooking();
			if(obooking.getSource()==null)
				obooking.setSource("");
			if(obooking.getDestination()==null)
				obooking.setDestination("");
			oinvoice.setObooking(obooking);
			oinvoice.setOtariff(oDatatable.getGet_invoice().get(nIdx).getOtariff());
			oinvoice.setTotal_mins(oDatatable.getGet_invoice().get(nIdx).getTotal_mins());
			lst_invoice.add(oinvoice);
		}
		map_response.put("draw",draw);
		map_response.put("recordsTotal",oDatatable.getSize());
		map_response.put("recordsFiltered",oDatatable.getSize());
		map_response.put("data",lst_invoice);		
		return map_response;
	}
	public Map<String, Object> getHistorybyDate(String fromDate,String toDate,int draw,int length,int start,String searchValue,String access_id) {
		List<invoice> lst_invoice=new ArrayList<invoice>();
		invoice oinvoice=null;
		booking obooking=null;
		Map<String,Object> map_response=new HashMap<String,Object>();
		
			oDatatable = baseDAO.getInstance().getEntityClassList_DTinvoice("from invoice where obooking.odrivers.driver_id="+baseDAO.getInstance().getIntKeyValue("select driver_id from drivers where access_id='"+access_id+"'")+"  and date(obooking.created_on) between '"+fromDate+"' and '"+toDate+"'",length,start);
		
			for(int nIdx=0;nIdx<oDatatable.getGet_invoice().size();nIdx++)
		{
			oinvoice=new invoice();
				obooking= new booking();
			if(oDatatable.getGet_invoice().get(nIdx).getTotal()!=0)
			oinvoice.setAmount(oDatatable.getGet_invoice().get(nIdx).getTotal());
			else
				oinvoice.setAmount(0);
			if(oDatatable.getGet_invoice().get(nIdx).getDistance()!=0)
				oinvoice.setDistance(oDatatable.getGet_invoice().get(nIdx).getDistance());
			else
				oinvoice.setDistance(0);
			if(oDatatable.getGet_invoice().get(nIdx).getTotal_mins()!=0)
				oinvoice.setTotal_mins(oDatatable.getGet_invoice().get(nIdx).getTotal_mins());
				else
					oinvoice.setTotal_mins(0);

			oinvoice.setInvoice_id(oDatatable.getGet_invoice().get(nIdx).getInvoice_id());
			obooking=oDatatable.getGet_invoice().get(nIdx).getObooking();
			if(obooking.getSource()==null)
				obooking.setSource("");
			if(obooking.getDestination()==null)
				obooking.setDestination("");
			oinvoice.setObooking(obooking);
			oinvoice.setOtariff(oDatatable.getGet_invoice().get(nIdx).getOtariff());
			oinvoice.setTotal_mins(oDatatable.getGet_invoice().get(nIdx).getTotal_mins());
			lst_invoice.add(oinvoice);
		}
		map_response.put("draw",draw);
		map_response.put("recordsTotal",oDatatable.getSize());
		map_response.put("recordsFiltered",oDatatable.getSize());
		map_response.put("data",lst_invoice);		
		return map_response;
	}
	public Map<String, Object> deletelocation(location Olocation) {
		if (Olocation == null)
			return map_response(false, 5000);
		if(Olocation.getLocation_id()==0)
			return map_response(false, 5000);
	
		String hqlQueryCommand = " delete from location where location_id="
				+ Olocation.getLocation_id();
		if (baseDAO.getInstance().executeHQLQuery(hqlQueryCommand))
			return map_response(true, 5020);
		else
			return map_response(false, 5005);
		
	}
	public Map<String,Object> updatelocation(location Olocation)
	{	
		//Olocation.setChannel(getRandomWord(30));
		if(baseDAO.getInstance().executeHQLQuery("update location set location_name = '"+ Olocation.getLocation_name() +"',north_latitude="+ Olocation.getNorth_latitude() +","
				+ " south_latitude="+ Olocation.getSouth_latitude() +","
						+ "east_longitude="+ Olocation.getEast_longitude() +","
								+ "west_longitude="+ Olocation.getWest_longitude() +",updated_on=now() where location_id="+ Olocation.getLocation_id() +""))
			return map_response(true,5020); //"Updated Successfully..."
		else
			return map_response(false,5005); //"Server is busy..."
	}
	public Map<String, Object> getqrcode_images(qrcode Oqrcode) 
	{
		List<qrcode> qrcode_ModelsObj = baseDAO.getInstance().getqrcodes_images("select qrcode_filename"
				+ " from qrcode where oqrcode_user.qrcode_user_id='"+Oqrcode.getQrcode_id()+"'",0);
		if (!qrcode_ModelsObj.isEmpty())
			return map_response(true, qrcode_ModelsObj);
		else {
			return map_response(false, 5006);
		}
	}
	
	public Map<String,Object> delete_driver(drivers odriver)
	{
		if(odriver==null)
			 return map_response(false,5000); //Invalid JSON Format for Class
		StringBuilder queryBuilder = new StringBuilder();
		boolean is_removed=baseDAO.getInstance().executeHQLQuery(queryBuilder.append("update drivers set access_id='', is_deleted=true, updated_on=now() where access_id='").append(odriver.getAccess_id()).append("'").toString());
		queryBuilder=null;
		if(is_removed)
			return map_response(true,5020);
		else
			return map_response(false,5005);
	}
	public Map<String,Object> addcomments(booking obooking)
	{
		if(obooking==null)
			 return map_response(false,5000); //Invalid JSON Format for Class
		StringBuilder queryBuilder = new StringBuilder();
		boolean is_updated=baseDAO.getInstance().executeHQLQuery(queryBuilder.append("update booking set unbook_notes='"+obooking.getUnbook_notes()+"' where booking_number='").append(obooking.getBooking_number()).append("'").toString());
		queryBuilder=null;
		if(is_updated)
			return map_response(true,5020);
		else
			return map_response(false,5005);
	}
	public boolean updatepayment(String Responsecode,String Responsemessage,String unique_ID)
	{	
		return baseDAO.getInstance().executeHQLQuery("update payment_transaction set response_code = '"+ Responsecode +"',response_message='"+ Responsemessage +"',updated_on=now() where transaction_id='"+unique_ID+"'");
	}
	public Map<String, Object> getpassgenger_datatable(int draw,int length,int start,String searchValue) 
	{
		List<passenger> lst_passenger=new ArrayList<passenger>();
		Map<String,Object> map_response=new HashMap<String,Object>();
		passenger opassenger=null;
		if(searchValue.length()==0){
			oDatatable = baseDAO.getInstance().getEntityClassList_DTpassenger("from passenger where is_active=1 order by passenger_id desc",length,start);
		}else{
			oDatatable = baseDAO.getInstance().getEntityClassList_DTpassenger("from passenger where is_active=1 and passenger_name like '"+searchValue+"%' or "
					+ " mobile like '"+searchValue+"%' or "
							+ " wallet like '"+searchValue+"%' or email like '"+searchValue+"%'  order by passenger_id desc",length,start);
		}
		if(oDatatable==null){
			map_response.put("draw",draw);
			map_response.put("recordsTotal",0);
			map_response.put("recordsFiltered",0);
			map_response.put("data",lst_passenger);
		}else{
			for(int nIdx=0;nIdx<oDatatable.getGet_passenger().size();nIdx++)
			{
				opassenger=new passenger();
				opassenger.setPassenger_name((oDatatable.getGet_passenger().get(nIdx).getPassenger_name()));
				opassenger.setMobile((oDatatable.getGet_passenger().get(nIdx).getMobile()));
				opassenger.setEmail((oDatatable.getGet_passenger().get(nIdx).getEmail()));
				opassenger.setWallet(oDatatable.getGet_passenger().get(nIdx).getWallet());
				lst_passenger.add(opassenger);
			}		
		}
		map_response.put("draw",draw);
		map_response.put("recordsTotal",oDatatable.getSize());
		map_response.put("recordsFiltered",oDatatable.getSize());
		map_response.put("data",lst_passenger);
		return map_response;
	}
}
