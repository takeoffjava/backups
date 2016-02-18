package org.strobs.utoo.web.services.base;



import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import javax.imageio.ImageIO;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.strobs.utoo.web.services.classes.baseProperty;
import org.strobs.utoo.web.services.dao.baseDAO;
import org.strobs.utoo.web.services.eClasses.booking;
import org.strobs.utoo.web.services.eClasses.drivers;
import org.strobs.utoo.web.services.eClasses.passenger;
import org.strobs.utoo.web.services.security.CryptUtilities;

import sys.liz.provider.s5.algorithms.basic.s5security;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.itextpdf.text.Image;

public class utoo 
{
	private ResourceBundle rsbAR = ResourceBundle.getBundle("utoo");

	public int getOTP()
	{
		return new Random().nextInt(99999 - 10000 + 1) + 10000;
	}
	public int getPIN()
	{
		return new Random().nextInt(9999 - 1000 + 1) + 1000;
	}
	public String SendMessage(long Mobile,String Message)
	{
		StringBuffer response = null;
		String SMSLink="",API_KEY="16150uina6g14pg4q0352",
				SENDER="STROBS";
		
		SMSLink="http://smssolution.net.in/api/web2sms.php?";
		@SuppressWarnings("deprecation")
		String content =
			    "workingkey=" + URLEncoder.encode(API_KEY) +
			    "&to=" + URLEncoder.encode(""+Mobile) +
			    "&sender=" + URLEncoder.encode(SENDER) +
			    "&message=" + URLEncoder.encode (Message);
	    URL url;
	    HttpURLConnection connection = null;  
	    try 
	    {
	      url = new URL(SMSLink);
	      connection = (HttpURLConnection)url.openConnection();
	      connection.setRequestMethod("GET");
	      connection.setRequestProperty("Content-Length", "" + 
	               Integer.toString(content.getBytes().length));
	      connection.setRequestProperty("Content-Language", "en-US");  
	      connection.setUseCaches (false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);

	      //Send request
	      DataOutputStream wr = new DataOutputStream (
	                  connection.getOutputStream ());
	      wr.writeBytes (content);
	      wr.flush ();
	      wr.close ();

	      //Get Response	
	      InputStream is = connection.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	      String line;
	      response=new StringBuffer();
	      while((line = rd.readLine()) != null) 
	      {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();
	    } 
	    catch (Exception e) 
	    {
	  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(e));
	    	return e.toString();
	    } 
	    finally 
	    {
	      if(connection != null) {
	        connection.disconnect(); 
	      }
	    }
	    //
	    return response.toString();
	}
	public Object mergeOneToOneObject(Object obj, Object update){
	    if(!obj.getClass().isAssignableFrom(update.getClass())){
	        return null;
	    }

	    Method[] methods = obj.getClass().getMethods();

	    for(Method fromMethod: methods){
	        if(fromMethod.getDeclaringClass().equals(obj.getClass())
	                && fromMethod.getName().startsWith("get")){

	            String fromName = fromMethod.getName();
	            String toName = fromName.replace("get", "set");

	            try {
	                Method toMetod = obj.getClass().getMethod(toName, fromMethod.getReturnType());
	                Object value = fromMethod.invoke(update, (Object[])null);
	                if(value != null){
	                    toMetod.invoke(obj, value);
	                }
	            } catch (Exception e) {
	    	  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(e));
	                e.printStackTrace();
	            } 
	        }
	    }
	    return obj;
	}

	
	public static baseProperty oBasePropertyInfo;
	static 
	{
		oBasePropertyInfo=new baseProperty();
		Properties configFile = new Properties();
		try 
		{
			configFile.load(passenger.class.getClassLoader().getResourceAsStream("utoo.properties"));
			oBasePropertyInfo.setFTPHostUrlOrIP(configFile.getProperty("FTP_SERVER_HOST_NAME"));
			oBasePropertyInfo.setFTPUsername(configFile.getProperty("FTP_USER_NAME"));
			oBasePropertyInfo.setFTPPassword(configFile.getProperty("FTP_PASSWORD"));
			oBasePropertyInfo.setANROIDPushKeyCode(configFile.getProperty("ANDROID_KEY_CODE"));
			oBasePropertyInfo.setPassengerANROIDPushKeyCode(configFile.getProperty("PASSENGER_ANDROID_KEY_CODE"));
			oBasePropertyInfo.setIOSUser(configFile.getProperty("IOS_USER"));
			oBasePropertyInfo.setIOSPushKeyFile(configFile.getProperty("IOS_KEY_FILE"));
			
		} catch (Exception expbasepath) {
	  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(expbasepath));
		}
	}
	 public passenger checkAuthentication(passenger Opassenger)
	 {
		 passenger ODB_passenger=null;
			try 
			{
				 ODB_passenger	=baseDAO.getInstance().getPassengers("From passenger where mobile=" + Opassenger.getMobile());
				if(ODB_passenger==null)
					return null;

				ODB_passenger.setPassword(s5security.passwordUnmask(ODB_passenger.getPassword()));
				if(CryptUtilities.generateStorngPasswordHash(ODB_passenger.getPassword(),Opassenger.getPassword_key_type()).equals(Opassenger.getPassword()))
					return ODB_passenger;
			} 
			catch (NoSuchAlgorithmException e) {
				 ODB_passenger=null;
		  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(e));
		        }
			catch (InvalidKeySpecException e) {
				 ODB_passenger=null;
		  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(e));
		       }
			return null; // Invalid credentials
    }
	 public Map<String,Object> map_response(boolean status,Object responseObject)
		{
			Map<String,Object> map_response=new HashMap<String,Object>();
			if(status)
			{
				map_response.put("status", 1);
				map_response.put("response",responseObject);
			}
			else
			{
				map_response.put("status", 0);
				map_response.put("error",responseObject);
			}
			return map_response;
		}
		public Map<String,Object> map_response(boolean status,int response_code)
		{
			Map<String,Object> map_response=new HashMap<String,Object>();
			if(status)
			{
				map_response.put("status", 1);
				map_response.put("response",response_code);
			}
			else
			{
				map_response.put("status", 0);
				map_response.put("error",response_code);
			}
			return map_response;
		}
		public Map<String,Object> map_response(boolean status,int s_response_code,int f_response_code)
		{
			Map<String,Object> map_response=new HashMap<String,Object>();
			if(status)
			{
				map_response.put("status", 1);
				map_response.put("response",s_response_code);
			}
			else
			{
				map_response.put("status", 0);
				map_response.put("error",f_response_code);
			}
			return map_response;
		}
		public Map<String,Object> map_response(List<Object> lstEntityCls,int response_code)
		{
			if(lstEntityCls!=null){
			if(lstEntityCls.isEmpty())
				return map_response(false,5006); //No records in the list
			if(lstEntityCls.size()>0)
				return map_response(true,lstEntityCls);
			}
			return map_response(false,response_code);
		}
		public Map<String,Object> map_response(Object EntityCls,int response_code)
		{
			if(EntityCls==null || EntityCls=="")
				return map_response(false,response_code);
			return map_response(true,EntityCls);
		}
		public Map<String,Object> map_response(Map<String,Object> map_content,int response_code)
		{
			return (map_content==null)?map_response(false,response_code):(map_content.isEmpty())?map_response(false,response_code):map_response(true,map_content);
		}
		public enum userType
		{
			passenger,drivers,users;
		}
		public String uploadFile(InputStream base64InStrm,String uploadFolderName,String filename)
		{
			filename=getSeconds()+filename;
			SimpleFTPClient thrd_FTP_Cls=new SimpleFTPClient();
			try
			{
				thrd_FTP_Cls.setHost(utoo.oBasePropertyInfo.getFTPHostUrlOrIP());
				thrd_FTP_Cls.setUser(utoo.oBasePropertyInfo.getFTPUsername());
				thrd_FTP_Cls.setPassword(utoo.oBasePropertyInfo.getFTPPassword());
				thrd_FTP_Cls.setRemoteFile("/" + uploadFolderName + "/" + filename);
				thrd_FTP_Cls.connect();
				thrd_FTP_Cls.uploadFile(base64InStrm);
				base64InStrm.close();
				uploadFolderName=null;
				thrd_FTP_Cls=null;
				return filename;
			}
			catch(Exception exp)
			{
		  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(exp));
				return "";
			}
		}
		public String uploadQR(InputStream base64InStrm,String uploadFolderName,String filename)
		{
			SimpleFTPClient thrd_FTP_Cls=new SimpleFTPClient();
			try
			{
				thrd_FTP_Cls.setHost(utoo.oBasePropertyInfo.getFTPHostUrlOrIP());
				thrd_FTP_Cls.setUser(utoo.oBasePropertyInfo.getFTPUsername());
				thrd_FTP_Cls.setPassword(utoo.oBasePropertyInfo.getFTPPassword());
				thrd_FTP_Cls.setRemoteFile("/" + uploadFolderName + "/" + filename);
				thrd_FTP_Cls.connect();
				thrd_FTP_Cls.uploadFile(base64InStrm);
				return filename;
			}
			catch(Exception exp)
			{
		  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(exp));
				return "";
			}
		}
		
		public String getSeconds()
		{
			return new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		}
		private static Random random = new Random((new Date()).getTime());
	    public String getRandomWord(int length) 
	    {
	      char[] values = {'A','C','F','G','H','K','M','N','P','R','T',
	               'U','V','W','X','Y','Z','4','6','7','9'};
	      String out = "";
	      for (int i=0;i<length;i++) {
	          int idx=random.nextInt(values.length);
	        out += values[idx];
	      }
	      return out;
	    }
	    public String getRandomNumber(int length) 
	    {
	      char[] values = {'1','2','3','4','5','6','7','8','9','0'};
	      String out = "";
	      for (int i=0;i<length;i++) {
	          int idx=random.nextInt(values.length);
	        out += values[idx];
	      }
	      return out;
	    }
	    public String IsNullContent(String ObjValue)
		{
			return ObjValue==null?"":ObjValue;
		}
	    public String IsNullContentOrSetDefault(String ObjValue,String oDefault)
		{
			return ObjValue==null?oDefault:ObjValue;
		}
	    
	    
	    public boolean sendStartTripNotification(booking obooking) 
	    {
	    	Map<String, Object> push_map =null;
	    	try
	    	{
	    		push_map = new HashMap<String, Object>();
				push_map.put("booking_number", obooking.getBooking_number());
				push_map.put("book_status", obooking.getStatus());
				
				passenger oPassenger=baseDAO.getInstance().getPassengers("From passenger where access_id=(Select opassenger.access_id from booking where booking_number='" + obooking.getBooking_number() + "')");
				if(oPassenger.getDevice_type_name().equals("ios"))
					return SendMessageToIOSMobile(push_map,oPassenger.getDevice_id(),"Your ride is started");
				else
					
					return SendMessageToAndroidMobileNotSync(push_map,oPassenger.getDevice_id(),true);
	    	}
	    	catch(Exception expPushNotifyObject)
	    	{
		  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(expPushNotifyObject));
	    	}
			return false;
	    }
	    public boolean sendEndTripNotification(booking obooking) 
	    {
	    	Map<String, Object> push_map =null;
	    	try
	    	{
	    		push_map = new HashMap<String, Object>();
				push_map.put("booking_number", obooking.getBooking_number());
				push_map.put("book_status", obooking.getStatus());
				
				if(obooking.getOpassenger().getDevice_type_name().equals("ios"))
					return SendMessageToIOSMobile(push_map,obooking.getOpassenger().getDevice_id(),"Your ride is completed");
				else
					return SendMessageToAndroidMobileNotSync(push_map,obooking.getOpassenger().getDevice_id(),true);
	    	}
	    	catch(Exception expPushNotifyObject)
	    	{
		  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(expPushNotifyObject));
	    	}
			return false;
	    }
	    public boolean sendCancelBookingNotification(booking obooking) 
	    {
	    	Map<String, Object> push_map =null;
	    	try
	    	{
	    		
	    		push_map = new HashMap<String, Object>();
				push_map.put("booking_number", obooking.getBooking_number());
				push_map.put("book_status", obooking.getStatus());
				drivers oDrivers=null;
				if(obooking.getOdrivers()!=null)
				{
				oDrivers=baseDAO.getInstance().getDriversObject(obooking.getOdrivers().getAccess_id());
				obooking.setOdrivers(oDrivers);
				SendMessageToAndroidMobileNotSync(push_map,obooking.getOdrivers().getDevice_id(),false);
				}
					
				passenger oPassenger = baseDAO.getInstance().
							getPassengers("From passenger where access_id='" + obooking.getOpassenger().getAccess_id()+"'");
					
					obooking.setOpassenger(oPassenger);
				 	if(obooking.getOpassenger().getDevice_type_name().equals("ios"))
				 		 SendMessageToIOSMobile(push_map, obooking.getOpassenger().getDevice_id(), "Your ride is cancelled");
					else
						 SendMessageToAndroidMobileNotSync(push_map,obooking.getOpassenger().getDevice_id(),true);
				return true;
	    	}
	    	catch(Exception expPushNotifyObject)
	    	{
		  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(expPushNotifyObject));
	    	}
			return false;
	    }
	    public boolean SendMessageToAndroidMobileNotSync(Map<String,Object> map_PostMessageContent,String deviceTokenID,boolean isPassengerPush) 
		{
			Sender pshSender=null;
			Message pshMessage=null;
			boolean pshresponse=false;
			if(deviceTokenID==null)
				return pshresponse;
			
			if(deviceTokenID=="")
				return pshresponse;
			try 
			{
				pshSender = new Sender(isPassengerPush?oBasePropertyInfo.getPassengerANROIDPushKeyCode():oBasePropertyInfo.getANROIDPushKeyCode());
				JSONObject jsonConverter= new JSONObject(map_PostMessageContent);
				pshMessage = new Message.Builder().addData("message", jsonConverter.toString()).build();
				@SuppressWarnings("unused")
				Result rest=pshSender.send(pshMessage,deviceTokenID,1);
				pshresponse=true;
			} catch (Exception expSendMessageToAndroidMobile) {
		  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(expSendMessageToAndroidMobile));
		       }
			pshSender=null;
			pshMessage=null;
			return pshresponse;
		}
	    private boolean SendMessageToIOSMobile(Object oPostMessageContent,String deviceTokenID,String alert) 
		{
			PushNotificationPayload payloadContent=null;
			List<PushedNotification> notifications=null;
			List<Object> PayLoadContent=null;
			boolean bstatus=false;
			if(deviceTokenID==null)
				return bstatus;
			
			if(deviceTokenID=="")
				return bstatus;
			try 
			{
				PayLoadContent=new ArrayList<Object>();
				PayLoadContent.add(oPostMessageContent);
			
				try 
				{
					/*payloadContent.addBadge(nBadgeCount);*/
					payloadContent = PushNotificationPayload.fromJSON("{\"aps\":{\"content-available\":1,\"sound\":\"default\",\"alert\":\""+alert+"\"}}");
					if(PayLoadContent!=null)
						payloadContent.addCustomDictionary("utoo_payload", PayLoadContent);
		        }
				catch (JSONException expSendMessageToIOSMobile) 
				{
			  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(expSendMessageToIOSMobile));
			    }
				notifications = Push.payload(payloadContent, new File(oBasePropertyInfo.getIOSPushKeyFile().toString()), oBasePropertyInfo.getIOSUser(),true,deviceTokenID.replace(" ", ""));
				for (PushedNotification notification : notifications) 
						bstatus=notification.isSuccessful()?true:false;
			} catch (KeystoreException e) {
		  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(e));
		       
			} catch (CommunicationException e) {
		  	  	baseDAO.getInstance().logAnError("utoo",baseDAO.getInstance().stackTraceToString(e));
			}
			payloadContent=null; 
			return bstatus;
		}
	    public long convertStrToTimeStamp(String dateInString)
	    {
	  	  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	  	  try {

	  			Date date = formatter.parse(dateInString);
	  			return date.getTime();

	  		} catch (ParseException e) {
		  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(e));
		       }
	  	  return 0;
	    }
	    public enum response_codes
	    {
	    	Invalid_JSON_Format(5000),
	    	Mobile_number_is_already_exists(5001),
	    	OTP_has_been_successfully(5002),
	    	Invalid_Mobile_Number(5003),
	    	SMS_server_is_busy(5004),
	    	Unable_to_reach_server(5005),
	    	No_records_in_the_list(5006),
	    	Invalid_Request(5007),
	    	Mobile_number_is_not_exists(5008),
	    	OTP_is_verified(5009),
	    	OTP_is_not_valid(5010),
	    	OTP_is_pending(5011),
	    	New_Password_has_been_updated_successfully(5012),
	    	Invalid_Credential(5013),
	    	OTP_is_already_verified(5014),
	    	Token_Server_busy(5015),
	    	Upload_Failure(5016),
	    	Uploaded_Successfully(5017),
	    	Car_details_are_pending(5018),
	    	License_details_are_pending(5019),
	    	Updated_Successfully(5020),
	    	Mobile_number_is_not_activated(5021),
	    	New_Ride_is_booked(5022),
	    	Invalid_Object(5023),
	    	Thanks_for_your_rating(5024),
	    	Invalid_Access_ID(5025),
	    	Logout_Successfully(5026),
	    	Mobile_number_Or_License_Number_is_already_exists(5027),
	    	Account_has_been_deactivated_successfully(5028),
	    	You_have_been_blocked_the_driver(5029),
	    	Invalid_Booking_Number(5030),
	    	License_side_should_not_be_empty(5031),
	    	Resend_OTP_Chance_Expired(5032),
	    	Resend_OTP_Chance_Finished(5033),
	    	Resend_OTP_One_More_Chance_is_Left(5034),
	    	No_Drivers_are_available(5035),
	    	Rating_points_should_not_above_five(5036),
	    	Channel_added_successfully(5037),
	    	Ice_contacts_has_been_added_successfully(5038),
	    	Ice_contacts_list_empty(5039),
	    	Only_3_contacts_are_allowed(5040),
	    	Primary_Contact_had_been_changed(5041),
	    	One_ICE_contact_has_been_deleted(5042),
	    	You_have_been_blocked_the_passenger(5043),
	    	Invalid_ICE_contacts(5044),
	    	The_Promo_Code_is_valid(5045),
	    	The_Promo_Code_is_invalid(5046),
	    	Invalid_Tariff_ID(5047),
	    	Notify_ICE_has_been_successfully(5048),
	    	Invalid_Invoice_number(5049), 
	    	Paid_Successfully(5050),
	    	Booking_has_been_cancelled_successfully(5051),
	    	Invalid_QR_code(5054),
	    	Resend_OTP_Two_More_Chance_is_Left(5032),
	    	Invalid_QR_Code(5056),
	    	QR_Code_Isactive(5057),
	    	QR_Code_Inactive(5058),
	    	QR_Code_Is_Not_Verified(5059),
	    	FTP_Server_Is_Busy(5060),
	    	File_Not_Found(5061),
	    	Trip_Already_Started(5062),
	    	Only_3_Favourites_are_allowed(5063),
	    	Favourites_Added_Successfully(5064),
	    	Session_Expired(5065),
	    	Cannot_Rate_This_Driver(5066),
	    	Otp_Is_Expired(5067),
	    	You_Can_Book_After_Completing_Your_Current_Ride(5068),
	    	No_Bookings_are_available(5069),
	    	No_Records_are_available(5070),
	    	You_Can_Book_After_Completing_Your_Pending_Rides(5071);
	    	
	    	private int code=0;
	    	
	        private response_codes(int code) {
	                this.code = code;
	        }
	        public int getCode() {
	            return code;
	        }
	    };


public Image getEmptyQR(String basePath)
	{
		Image image=null;
		  try {
			   image = Image.getInstance(extractBytes(basePath + "default.png"));

		  } catch (Exception expEmptyQR) {
		  } 
		  return image;
		   
	}

public byte[] extractBytes (String ImageName) throws IOException {
	 // open image
	 File imgPath = new File(ImageName);
	 BufferedImage bufferedImage = ImageIO.read(imgPath);

	 // get DataBufferBytes from Raster
	 WritableRaster raster = bufferedImage .getRaster();
	 DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

	 return ( data.getData() );
	}
@SuppressWarnings("unchecked")
public Map<String, Object> ResponseData(String json, String ServiceURL,
		String Method,String heardeName) {
	String url = null, respJson = null;
	StringBuilder urlBuilder = null;
	Map<String, Object> mapResponse = null;
	ObjectMapper mapper = new ObjectMapper();

	try {
		String gpsURL=rsbAR.getString("GPSURL");

		urlBuilder = new StringBuilder();

		url = urlBuilder.append(gpsURL).append(ServiceURL)
				.toString();
		
		respJson = (String) utooBackendUtil.URLFetch(url, Method,
				"application/json", json,heardeName);

		mapResponse = mapper.readValue(respJson, HashMap.class);

	} catch (Exception e) {
		e.printStackTrace();

	}
	return mapResponse;
}
}
