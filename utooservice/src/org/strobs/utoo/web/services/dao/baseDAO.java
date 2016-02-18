package org.strobs.utoo.web.services.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.strobs.utoo.web.services.base.utoo;
import org.strobs.utoo.web.services.classes.rating;
import org.strobs.utoo.web.services.dto.tdriver;
import org.strobs.utoo.web.services.eClasses.accident;
import org.strobs.utoo.web.services.eClasses.booking;
import org.strobs.utoo.web.services.eClasses.car_features;
import org.strobs.utoo.web.services.eClasses.car_models;
import org.strobs.utoo.web.services.eClasses.datatablelist;
import org.strobs.utoo.web.services.eClasses.driverapp_status;
import org.strobs.utoo.web.services.eClasses.drivers;
import org.strobs.utoo.web.services.eClasses.favourites;
import org.strobs.utoo.web.services.eClasses.ice;
import org.strobs.utoo.web.services.eClasses.invoice;
import org.strobs.utoo.web.services.eClasses.location;
import org.strobs.utoo.web.services.eClasses.lost_found;
import org.strobs.utoo.web.services.eClasses.passenger;
import org.strobs.utoo.web.services.eClasses.passenger_otp;
import org.strobs.utoo.web.services.eClasses.payment_transaction;
import org.strobs.utoo.web.services.eClasses.promocode;
import org.strobs.utoo.web.services.eClasses.qrcode;
import org.strobs.utoo.web.services.eClasses.qrcode_users;
import org.strobs.utoo.web.services.eClasses.tariff;
import org.strobs.utoo.web.services.eClasses.users;

import com.google.android.gcm.server.Message;

public class baseDAO extends utoo
{
	private static baseDAO baseDAOInstance =null;
	public static ResourceBundle BaseInfo = ResourceBundle.getBundle("utoo");	
	public baseDAO(){}
	public static baseDAO getInstance()
	{
		 if (baseDAOInstance==null)  
			 baseDAOInstance = new baseDAO();
		 return baseDAOInstance;
	}
	private boolean close_connection(Session sn_utoo)
	{
		try
		{
			sn_utoo.clear();
			sn_utoo.flush();
			sn_utoo.close();
			return true;
		}
		catch(Exception expclose_connection)
		{
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expclose_connection));
			writeLog(expclose_connection.toString(),"");
		}
		return false;
	}
	public boolean SaveEntityClass(Object Entity_Cls) 
	{
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			sn_utoo.saveOrUpdate(Entity_Cls);
			sn_utoo.getTransaction().commit();
			close_connection(sn_utoo);
			Entity_Cls=null;
			return true;
		} 
		catch (Exception expSaveEntityClass) 
		{
			Entity_Cls=null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expSaveEntityClass));
			writeLog(expSaveEntityClass.toString(),"");
		}
		close_connection(sn_utoo);
		return false;
	}
	public boolean SaveEntityQR(Object Entity_Cls) 
	{
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			sn_utoo.save(Entity_Cls);
			sn_utoo.getTransaction().commit();
			close_connection(sn_utoo);
			Entity_Cls=null;
			return true;
		} 
		catch (Exception expSaveEntityClass) 
		{
			Entity_Cls=null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expSaveEntityClass));
			writeLog(expSaveEntityClass.toString(),"");
		}
		close_connection(sn_utoo);
		return false;
	}
	public boolean UpdateEntityClass(Object Entity_Cls) 
	{
		
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			sn_utoo.merge(Entity_Cls);
			sn_utoo.getTransaction().commit();
			close_connection(sn_utoo);
			sn_utoo=null;
			Entity_Cls=null;
			return true;
		} 
		catch (Exception expUpdateEntityClass) 
		{
			Entity_Cls=null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expUpdateEntityClass));
			writeLog(expUpdateEntityClass.toString(),"");
		}
		close_connection(sn_utoo);
		return false;
	}
	public boolean DeleteEntityClass(Object Entity_Cls) 
	{
		
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			sn_utoo.delete(Entity_Cls);
			sn_utoo.getTransaction().commit();
			close_connection(sn_utoo);
			Entity_Cls=null;
			sn_utoo=null;
			return true;
		} 
		catch (Exception expDeleteEntityClass) 
		{
			Entity_Cls=null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expDeleteEntityClass));
			writeLog(expDeleteEntityClass.toString(),"");
		}
		close_connection(sn_utoo);
		return false;
	}
	@SuppressWarnings("unchecked")
	public float giveDriverRating(rating oRating)
	{
		List<Object> Entity_Cls_Lst = null;
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Entity_Cls_Lst=new ArrayList<Object>();
			Entity_Cls_Lst=sn_utoo.createSQLQuery("CALL U2_0xP_STR_PRO_Rating(:p_rating_points, :p_booking_number, :p_driver_access_id)")
						.setParameter("p_rating_points", oRating.getRating_points())
						.setParameter("p_booking_number",oRating.getBooking_number())
						.setParameter("p_driver_access_id",oRating.getDriver_access_id()).list();
			sn_utoo.getTransaction().commit();
			close_connection(sn_utoo);
			sn_utoo=null;
			return (Entity_Cls_Lst.isEmpty())?-1:(Entity_Cls_Lst.size()==0)?-2:Integer.parseInt(Entity_Cls_Lst.get(0).toString());
		} 
		catch (Exception expExecuteQuery) 
		{ 
			Entity_Cls_Lst = null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expExecuteQuery));
			writeLog(expExecuteQuery.toString(),"");
		}
		close_connection(sn_utoo);
		return -1; 
	}
	@SuppressWarnings("unchecked")
	public float givePassengerRating(rating oRating)
	{
		List<Object> Entity_Cls_Lst = null;
		Session sn_utoo = null;
		try 
		{
			
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Entity_Cls_Lst=new ArrayList<Object>();
			Entity_Cls_Lst=sn_utoo.createSQLQuery("CALL U2_0xP_STR_PRO_Rating_From_Driver(:p_rating_points, :p_booking_number, :p_passenger_access_id)")
						.setParameter("p_rating_points", oRating.getRating_points())
						.setParameter("p_booking_number",oRating.getBooking_number())
						.setParameter("p_passenger_access_id",oRating.getPassenger_access_id()).list();
			sn_utoo.getTransaction().commit();
			close_connection(sn_utoo);
			return (Entity_Cls_Lst.isEmpty())?-1:(Entity_Cls_Lst.size()==0)?-2:Integer.parseInt(Entity_Cls_Lst.get(0).toString());
		} 
		catch (Exception expExecuteQuery) 
		{  
			
			Entity_Cls_Lst = null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expExecuteQuery));
			writeLog(expExecuteQuery.toString(),"");
		}
		close_connection(sn_utoo);
		return -1; 
	}
	
	public boolean executeHQLQuery(String hqlQueryCommand) 
	{
		int response_db=0;
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			response_db=sn_utoo.createQuery(hqlQueryCommand).executeUpdate();
			sn_utoo.getTransaction().commit();
			hqlQueryCommand=null;
		} 
		catch (Exception expExecuteQuery) 
		{ 
			//expExecuteQuery.printStackTrace();
			hqlQueryCommand=null;
			writeLog(expExecuteQuery.toString(),"");
		}
		close_connection(sn_utoo);
		return (response_db>0)?true:false;
	}
	@SuppressWarnings("unchecked")
	public List<Object> getEntityClassList(String hqlQueryCommand,int MAX_RECORDS) 
	{

		List<Object> Entity_Cls_Lst = null;
		Session sn_utoo = null;
		try 
		{
			Entity_Cls_Lst=new ArrayList<Object>();
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			if(MAX_RECORDS==0)
				Entity_Cls_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			else
				Entity_Cls_Lst = sn_utoo.createQuery(hqlQueryCommand).setMaxResults(MAX_RECORDS).list();
			hqlQueryCommand=null;
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_Lst;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public long getLongKeyValue(String hqlQueryCommand) {

		List<Object> Entity_Cls_Lst = null;
		Session sn_utoo = null;
		
		try 
		{
			Entity_Cls_Lst=new ArrayList<Object>();
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Entity_Cls_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			hqlQueryCommand=null;
		}
		catch (Exception expgetLongKeyValue)
		{
			hqlQueryCommand=null;
			Entity_Cls_Lst = null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expgetLongKeyValue));
			writeLog(expgetLongKeyValue.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_Lst.isEmpty()?0:Entity_Cls_Lst.isEmpty()?0:Entity_Cls_Lst.size()==0?0:Long.parseLong(Entity_Cls_Lst.get(0).toString());
	}
	@SuppressWarnings("unchecked")
	public int getIntKeyValue(String hqlQueryCommand) {

		List<Object> Entity_Cls_Lst = null;
		Session sn_utoo = null;
		try 
		{
			Entity_Cls_Lst=new ArrayList<Object>();
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Entity_Cls_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			hqlQueryCommand=null;
		}
		catch (Exception expgetLongKeyValue)
		{
			hqlQueryCommand=null;
			Entity_Cls_Lst=null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expgetLongKeyValue));
			writeLog(expgetLongKeyValue.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_Lst.isEmpty()?0:Entity_Cls_Lst.isEmpty()?0:Entity_Cls_Lst.size()==0?0:Integer.parseInt(Entity_Cls_Lst.get(0).toString());
	}
	
	
	@SuppressWarnings("unchecked")
	@Deprecated
	public int getIntKeyValueX(String hqlQueryCommand) {

		List<Object> Entity_Cls_Lst = null;

		Session sn_utoo = null;
		try 
		{
			Entity_Cls_Lst=new ArrayList<Object>();
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Entity_Cls_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			hqlQueryCommand=null;
		}
		catch (Exception expgetLongKeyValue)
		{
			hqlQueryCommand=null;
			Entity_Cls_Lst = null;
		    logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expgetLongKeyValue));
			writeLog(expgetLongKeyValue.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_Lst.isEmpty()?-1:Entity_Cls_Lst.isEmpty()?-1:Entity_Cls_Lst.size()==0?-1:Integer.parseInt(Entity_Cls_Lst.get(0).toString());
	}
	@SuppressWarnings("unchecked")
	public String getStringKeyValue(String hqlQueryCommand) {

		List<Object> Entity_Cls_Lst = null;

		Session sn_utoo = null;
		try 
		{
			Entity_Cls_Lst=new ArrayList<Object>();
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Entity_Cls_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			hqlQueryCommand=null;
		}
		catch (Exception expgetLongKeyValue)
		{
			hqlQueryCommand=null;
			Entity_Cls_Lst = null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expgetLongKeyValue));
	    	writeLog(expgetLongKeyValue.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_Lst.isEmpty()?"":Entity_Cls_Lst.isEmpty()?"":Entity_Cls_Lst.size()==0?"":Entity_Cls_Lst.get(0).toString();
	}
	
	@SuppressWarnings("unchecked")
	public qrcode getQRdetails(String hqlQueryCommand) {

		qrcode Entity_Cls_qrcode = null;
		List<qrcode> Entity_Cls_qrcode_Lst = null;

		Session sn_utoo = null;
		try 
		{
			Entity_Cls_qrcode_Lst=new ArrayList<qrcode>();
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Entity_Cls_qrcode_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			hqlQueryCommand=null;
			if(!Entity_Cls_qrcode_Lst.isEmpty())
			{
				Entity_Cls_qrcode=new qrcode();
				Entity_Cls_qrcode=Entity_Cls_qrcode_Lst.get(0);
				Entity_Cls_qrcode_Lst.clear();
			}
			
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_qrcode_Lst = null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
	    	writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_qrcode;
	}
	
	@SuppressWarnings("unchecked")
	public passenger getPassengers(String hqlQueryCommand) {

		passenger Entity_Cls_passenger = null;
		List<passenger> Entity_Cls_passenger_Lst = null;
		Session sn_utoo = null;
		
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Entity_Cls_passenger_Lst=new ArrayList<passenger>();
			Entity_Cls_passenger_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			hqlQueryCommand=null;
			if(!Entity_Cls_passenger_Lst.isEmpty())
			{
				Entity_Cls_passenger=new passenger();
				Entity_Cls_passenger=Entity_Cls_passenger_Lst.get(0);
				Entity_Cls_passenger_Lst.clear();
			}
			
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
	    	writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_passenger;
	}
	@SuppressWarnings("unchecked")
	public drivers getDriversObject(String accessID) {

		drivers Entity_Cls_drivers = null;
		List<drivers> Entity_Cls_drivers_Lst = null;
		Session sn_utoo = null;
		
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Entity_Cls_drivers_Lst=new ArrayList<drivers>();
			Entity_Cls_drivers_Lst = sn_utoo.createQuery("From drivers where access_id='" + accessID + "'").list();
			accessID=null;
			if(!Entity_Cls_drivers_Lst.isEmpty())
			{
				Entity_Cls_drivers=new drivers();
				Entity_Cls_drivers=Entity_Cls_drivers_Lst.get(0);
				Entity_Cls_drivers_Lst.clear();
			}
		}
		catch (Exception expEntityClassList)
		{
			accessID=null;
			Entity_Cls_drivers_Lst = null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
	    	writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_drivers;
	}
	@SuppressWarnings("unchecked")
	public invoice getInvoice(String hqlQueryCommand) {

		invoice Entity_Cls_invoice = null;
		List<invoice> Entity_Cls_invoice_Lst = null;

		Session sn_utoo = null;
		try 
		{
			Entity_Cls_invoice_Lst=new ArrayList<invoice>();
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Entity_Cls_invoice_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			hqlQueryCommand=null;
			if(!Entity_Cls_invoice_Lst.isEmpty())
			{
				Entity_Cls_invoice=new invoice();
				Entity_Cls_invoice=Entity_Cls_invoice_Lst.get(0);
				Entity_Cls_invoice_Lst.clear();
			}
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_invoice_Lst = null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
	    	writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_invoice;
	}
	
	@SuppressWarnings("unchecked")
	public users getUsers(String hqlQueryCommand,int MAX_RECORDS) {

		users Entity_Cls_user = null;
		List<users> Entity_Cls_user_Lst = null;

		Session sn_utoo = null;
		try 
		{
			Entity_Cls_user_Lst=new ArrayList<users>();
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			if(MAX_RECORDS==0)
				Entity_Cls_user_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			else
				Entity_Cls_user_Lst = sn_utoo.createQuery(hqlQueryCommand).setMaxResults(MAX_RECORDS).list();
			
			hqlQueryCommand=null;
			if(!Entity_Cls_user_Lst.isEmpty())
			{
				Entity_Cls_user=new users();
				Entity_Cls_user=Entity_Cls_user_Lst.get(0);
				Entity_Cls_user_Lst.clear();
			}
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_user_Lst = null;
			Entity_Cls_user = null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
	    	writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_user;
	}

	@SuppressWarnings("unchecked")
	public drivers getdrivers(String hqlQueryCommand,int MAX_RECORDS) {

		drivers Entity_Cls_drivers = null;
		List<drivers> Entity_Cls_drivers_Lst = null;

		Session sn_utoo = null;
		try 
		{
			Entity_Cls_drivers_Lst=new ArrayList<drivers>();	
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			if(MAX_RECORDS==0)
				Entity_Cls_drivers_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			else
				Entity_Cls_drivers_Lst = sn_utoo.createQuery(hqlQueryCommand).setMaxResults(MAX_RECORDS).list();
			hqlQueryCommand=null;
			if(!Entity_Cls_drivers_Lst.isEmpty())
			{
				Entity_Cls_drivers=new drivers();
				Entity_Cls_drivers=Entity_Cls_drivers_Lst.get(0);
				Entity_Cls_drivers_Lst.clear();
			}
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_drivers = null;
			Entity_Cls_drivers_Lst = null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
	    	writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_drivers;
	}
	@SuppressWarnings("unchecked")
	public List<qrcode> getqrcodes_images(String hqlQueryCommand,int MAX_RECORDS) {

		List<qrcode> Entity_Cls_qrcode_Lst = null;

		Session sn_utoo = null;
		try 
		{
			Entity_Cls_qrcode_Lst=new ArrayList<qrcode>();	
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			if(MAX_RECORDS==0)
				Entity_Cls_qrcode_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			else
				Entity_Cls_qrcode_Lst = sn_utoo.createQuery(hqlQueryCommand).setMaxResults(MAX_RECORDS).list();
			
			hqlQueryCommand=null;
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_qrcode_Lst = null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
	    	writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_qrcode_Lst;
	}
	
	
	@SuppressWarnings("unchecked")
	public passenger_otp getPassenger_OTP(String hqlQueryCommand,int MAX_RECORDS) {

		passenger_otp Entity_Cls_user_otp = null;
		List<passenger_otp> Entity_Cls_user_otp_Lst = null;
		Session sn_utoo = null;
		try 
		{
			Entity_Cls_user_otp_Lst=new ArrayList<passenger_otp>();
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			if(MAX_RECORDS==0)
				Entity_Cls_user_otp_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			else
				Entity_Cls_user_otp_Lst = sn_utoo.createQuery(hqlQueryCommand).setMaxResults(MAX_RECORDS).list();
			
			if(!Entity_Cls_user_otp_Lst.isEmpty())
			{
				/*Entity_Cls_user_otp=new passenger_otp();*/
				Entity_Cls_user_otp=Entity_Cls_user_otp_Lst.get(0);
				Entity_Cls_user_otp_Lst.clear();
			}
			hqlQueryCommand=null;
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_user_otp_Lst = null;
			Entity_Cls_user_otp = null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_user_otp;
	}
	public static void writeLog(String Message,String FILENAME)
	{
		String LogTime="",Filename="";
		try 
		{
			Filename=new SimpleDateFormat("MM_dd_yyyy_HH").format(Calendar.getInstance().getTime());
			FILENAME=FILENAME.length()>0?FILENAME:BaseInfo.getString("baseDirectoryPath") + "log_" + Filename +".txt";
			
			LogTime=new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
			File logFile = new File(FILENAME);
			FileWriter fileWriter = new FileWriter(logFile, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(LogTime + " : " + Message + "\r\n\r\n");
			fileWriter.flush();
			fileWriter.close();
			bufferedWriter.flush();
			bufferedWriter.close();
			FILENAME=null;
			logFile=null;
			LogTime=null;
			Filename=null;
		} catch (Exception ExpwriteLog) {
			FILENAME=null;
			LogTime=null;
			Filename=null;
	  	  	baseDAO.getInstance().logAnError("baseDAO",baseDAO.getInstance().stackTraceToString(ExpwriteLog));
		}
	}
	
	
	public car_models getcarmodel(int modelId) 
	{
		car_models carmodels = new car_models();
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			carmodels=(car_models) sn_utoo.get(car_models.class,modelId);
			sn_utoo.getTransaction().commit();
			close_connection(sn_utoo);
		} 
		catch (Exception expSaveEntityClass) 
		{
			carmodels=null;
	  	  	logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expSaveEntityClass));
			writeLog(expSaveEntityClass.toString(),"");
		}
		close_connection(sn_utoo);
		return carmodels;

	}
	
	public car_features getcarfeatures(int featuresID) {
		car_features carfeatures = new car_features();
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			carfeatures=(car_features) sn_utoo.get(car_features.class,featuresID);
			sn_utoo.getTransaction().commit();
			close_connection(sn_utoo);
			carfeatures=null;
		} 
		catch (Exception expSaveEntityClass) 
		{
			carfeatures=null;
	  	  	logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expSaveEntityClass));
			//expSaveEntityClass.printStackTrace();
			writeLog(expSaveEntityClass.toString(),"");
		}
		close_connection(sn_utoo);
		return carfeatures;

	}
	
	
	public Map<String, Object> updateCarDetails(drivers Odriver) {
		if (Odriver == null)
			return map_response(false, 5000);
		if (Odriver.getAccess_id() == null)
			return map_response(false, 5006);
		StringBuilder queryBuilder = new StringBuilder();
		if (baseDAO.getInstance().executeHQLQuery(queryBuilder.append("update drivers set car_plate_no='").append(Odriver.getCar_plate_no()).append("',ocars_feature.cars_feature_ID=").append(Odriver.getOcars_feature().getCars_feature_ID()).append(" where access_id='").append(Odriver.getAccess_id()).append("'").toString()))
		{
			queryBuilder = null;
			return map_response(true, 5020);
		}
		else
		{
			queryBuilder = null;
			return map_response(false, 5006);
		}
	}
	
		@SuppressWarnings("unchecked")
	public List<booking> getbookinghistory(String hqlQueryCommand ,int maxRecord) 
	{

		List<booking> Entity_Cls_booking_Lst = null;
		Session sn_utoo = null;
		try 
		{
			Entity_Cls_booking_Lst=new ArrayList<booking>();
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			if(maxRecord==0)
				Entity_Cls_booking_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			else
				Entity_Cls_booking_Lst = sn_utoo.createQuery(hqlQueryCommand).setMaxResults(maxRecord).list();
			hqlQueryCommand=null;
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
	  	  	logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_booking_Lst;
	}
	@SuppressWarnings("unchecked")
	public tariff gettariff(String hqlQueryCommand,int MAX_RECORDS) {

		tariff Entity_Cls_drivers = null;
		List<tariff> Entity_Cls_drivers_Lst = null;

		Session sn_utoo = null;
		try 
		{
			Entity_Cls_drivers_Lst=new ArrayList<tariff>();	
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			if(MAX_RECORDS==0)
				Entity_Cls_drivers_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			else
				Entity_Cls_drivers_Lst = sn_utoo.createQuery(hqlQueryCommand).setMaxResults(MAX_RECORDS).list();
			hqlQueryCommand=null;
			if(!Entity_Cls_drivers_Lst.isEmpty())
			{
				/*Entity_Cls_drivers=new tariff();*/
				Entity_Cls_drivers=Entity_Cls_drivers_Lst.get(0);
				Entity_Cls_drivers_Lst.clear();
			}
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_drivers_Lst = null;
			Entity_Cls_drivers = null;
	  	  	logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_drivers;
	}
	@SuppressWarnings("unchecked")
	public promocode getpromocode(String hqlQueryCommand,int MAX_RECORDS) {

		promocode Entity_Cls_drivers = null;
		List<promocode> Entity_Cls_drivers_Lst = null;

		Session sn_utoo = null;
		try 
		{
			Entity_Cls_drivers_Lst=new ArrayList<promocode>();	
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			if(MAX_RECORDS==0)
				Entity_Cls_drivers_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			else
				Entity_Cls_drivers_Lst = sn_utoo.createQuery(hqlQueryCommand).setMaxResults(MAX_RECORDS).list();
			hqlQueryCommand=null;
			if(!Entity_Cls_drivers_Lst.isEmpty())
			{
				/*Entity_Cls_drivers=new promocode();*/
				Entity_Cls_drivers=Entity_Cls_drivers_Lst.get(0);
				Entity_Cls_drivers_Lst.clear();
			}
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_drivers = null;
			writeLog(expEntityClassList.toString(),"");
			//Entity_Cls_drivers_Lst.clear();
			Entity_Cls_drivers_Lst = null;
		}
		close_connection(sn_utoo);
		return Entity_Cls_drivers;
	}
	@SuppressWarnings("unchecked")
	public users getusers(String hqlQueryCommand,int MAX_RECORDS) {

		users Entity_Cls_drivers = null;
		List<users> Entity_Cls_drivers_Lst = null;

		Session sn_utoo = null;
		try 
		{
			Entity_Cls_drivers_Lst=new ArrayList<users>();	
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			if(MAX_RECORDS==0)
				Entity_Cls_drivers_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			else
				Entity_Cls_drivers_Lst = sn_utoo.createQuery(hqlQueryCommand).setMaxResults(MAX_RECORDS).list();
			hqlQueryCommand=null;
			if(!Entity_Cls_drivers_Lst.isEmpty())
			{
				/*Entity_Cls_drivers=new users();*/
				Entity_Cls_drivers=Entity_Cls_drivers_Lst.get(0);
				Entity_Cls_drivers_Lst.clear();
			}
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_drivers = null;
			writeLog(expEntityClassList.toString(),"");
			//Entity_Cls_drivers_Lst.clear();
			Entity_Cls_drivers_Lst = null;
		}
		close_connection(sn_utoo);
		return Entity_Cls_drivers;
	}
	@SuppressWarnings("unchecked")
	public car_features getcarfeatures(String hqlQueryCommand,int MAX_RECORDS) {

		car_features Entity_Cls_carfeatures = null;
		List<car_features> Entity_Cls_carfeatures_Lst = null;

		Session sn_utoo = null;
		try 
		{
			Entity_Cls_carfeatures_Lst=new ArrayList<car_features>();	
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			if(MAX_RECORDS==0)
				Entity_Cls_carfeatures_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			else
				Entity_Cls_carfeatures_Lst = sn_utoo.createQuery(hqlQueryCommand).setMaxResults(MAX_RECORDS).list();
			
			hqlQueryCommand=null;
			if(!Entity_Cls_carfeatures_Lst.isEmpty())
			{
				/*Entity_Cls_carfeatures=new car_features();*/
				Entity_Cls_carfeatures=Entity_Cls_carfeatures_Lst.get(0);
				Entity_Cls_carfeatures_Lst.clear();
			}
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_carfeatures_Lst = null;
			Entity_Cls_carfeatures = null;
	  	  	logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_carfeatures;
	}
	@SuppressWarnings("unchecked")
	public car_models getcarmodals(String hqlQueryCommand,int MAX_RECORDS) {

		car_models Entity_Cls_carfeatures = null;
		List<car_models> Entity_Cls_carfeatures_Lst = null;

		Session sn_utoo = null;
		try 
		{
			Entity_Cls_carfeatures_Lst=new ArrayList<car_models>();	
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			if(MAX_RECORDS==0)
				Entity_Cls_carfeatures_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			else
				Entity_Cls_carfeatures_Lst = sn_utoo.createQuery(hqlQueryCommand).setMaxResults(MAX_RECORDS).list();
			hqlQueryCommand=null;
			if(!Entity_Cls_carfeatures_Lst.isEmpty())
			{
				/*Entity_Cls_carfeatures=new car_models();*/
				Entity_Cls_carfeatures=Entity_Cls_carfeatures_Lst.get(0);
				Entity_Cls_carfeatures_Lst.clear();
			}
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;			
			Entity_Cls_carfeatures_Lst = null;
			Entity_Cls_carfeatures = null;
	  	  	logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_carfeatures;
	}
	
		@SuppressWarnings("unchecked")
	public invoice getinvoice(String hqlQueryCommand) {

		invoice Entity_Cls_invoice = null;
		List<invoice> Entity_Cls_invoice_Lst = null;

		Session sn_utoo = null;
		try 
		{
			Entity_Cls_invoice_Lst=new ArrayList<invoice>();	
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Entity_Cls_invoice_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			if(!Entity_Cls_invoice_Lst.isEmpty())
			{
				/*Entity_Cls_invoice=new invoice();*/
				Entity_Cls_invoice=Entity_Cls_invoice_Lst.get(0);
				Entity_Cls_invoice_Lst.clear();
			}
			hqlQueryCommand=null;
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_invoice = null;
			Entity_Cls_invoice_Lst = null;
	  	  	logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_invoice;
	}
		@SuppressWarnings("unchecked")
		public drivers getdriverdetails(String access_id) {

			drivers Entity_Cls_Drivers = null;
			List<drivers> Entity_Cls_drivers_Lst = null;

			Session sn_utoo = null;
			try 
			{
				Entity_Cls_drivers_Lst=new ArrayList<drivers>();	
				sn_utoo = baseConnector.getSessionFactory().openSession();
				sn_utoo.beginTransaction();
				StringBuilder queryBuilder = new StringBuilder();
				Entity_Cls_drivers_Lst = sn_utoo.createQuery(queryBuilder.append("From drivers where access_id='").append( access_id ).append("'").toString()).list();
				queryBuilder =null;
				access_id=null;
				if(!Entity_Cls_drivers_Lst.isEmpty())
				{
					/*Entity_Cls_Drivers=new drivers();*/
					Entity_Cls_Drivers=Entity_Cls_drivers_Lst.get(0);
					Entity_Cls_drivers_Lst.clear();
				}
			}
			catch (Exception expEntityClassList)
			{
				access_id=null;
				Entity_Cls_drivers_Lst = null;
				Entity_Cls_Drivers = null;
		  	  	logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
				writeLog(expEntityClassList.toString(),"");
			}
			close_connection(sn_utoo);
			return Entity_Cls_Drivers;
		}
        public car_features updatecarfeature(car_features Rawcarfeature)
	       {
		car_features DBcarfeature=new car_features();
		car_features Newcarfeature=new car_features();
	
		try {
			
			StringBuilder queryBuilder = new StringBuilder();
			DBcarfeature=getcarfeatures(queryBuilder.append("From car_features where cars_feature_ID='").append(Rawcarfeature.getCars_feature_ID()).append("'").toString(),0);
			if(DBcarfeature==null)
				return null;
			Rawcarfeature.setCars_feature_ID(DBcarfeature.getCars_feature_ID());
			Newcarfeature=(car_features) mergeOneToOneObject(DBcarfeature,Rawcarfeature);		
			UpdateEntityClass(Newcarfeature);
			Rawcarfeature=null;
			DBcarfeature=null;
			
		} catch (Exception expUpdateUserProfile) {
			Newcarfeature=null;
			Rawcarfeature=null;
			DBcarfeature=null;
	  	  	logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expUpdateUserProfile));
			writeLog("[Class.baseDAO] : updateCarfeature(...) : " + expUpdateUserProfile.getStackTrace().toString(), "");
		}
		return Newcarfeature;
	}
	public car_models updatecarmodals(car_models Rawcarmodal)
	{
		car_models DBcarmodal=new car_models();
		car_models Newcarmodal=new car_models();
	
		try {
			DBcarmodal=getcarmodals("From car_models where car_model_id='"+ Rawcarmodal.getCar_model_id()+"'",0);
			if(DBcarmodal==null)
				return null;
			Rawcarmodal.setCar_model_id(DBcarmodal.getCar_model_id());
			Newcarmodal=(car_models) mergeOneToOneObject(DBcarmodal,Rawcarmodal);		
			UpdateEntityClass(Newcarmodal);
			Rawcarmodal=null;
			DBcarmodal=null;
		} catch (Exception expUpdateUserProfile) {
			Rawcarmodal=null;
			DBcarmodal=null;
			Newcarmodal=null;
	  	  	logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expUpdateUserProfile));
			writeLog("[Class.baseDAO] : updateCarfeature(...) : " + expUpdateUserProfile.getStackTrace().toString(), "");
		}
		return Newcarmodal;
	}
	public tariff updatetariff(tariff Rawtariff)
	{
		tariff DBtariff=new tariff();
		tariff Newtariff=new tariff();
	
		try {
			DBtariff=gettariff("From tariff where tariff_id='"+ Rawtariff.getTariff_id()+"'",0);
			if(DBtariff==null)
				return null;
			Rawtariff.setTariff_id(DBtariff.getTariff_id());
			Rawtariff.setFlat_amount(DBtariff.getFlat_amount());
			Rawtariff.setCarmodel_id(DBtariff.getCarmodel_id());
			Rawtariff.setAfter_minkms(DBtariff.getAfter_minkms());
	
			Newtariff=(tariff) mergeOneToOneObject(DBtariff,Rawtariff);	
			Rawtariff =null;
			DBtariff=null;
			UpdateEntityClass(Newtariff);
		} catch (Exception expUpdateUserProfile) {
			Rawtariff =null;
			DBtariff=null;
			Newtariff=null;
	  	  	logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expUpdateUserProfile));
			writeLog("[Class.baseDAO] : updateCarfeature(...) : " + expUpdateUserProfile.getStackTrace().toString(), "");
		}
		return Newtariff;
	}
	@SuppressWarnings("unchecked")
	public ice getICE(String accessID,long mobilenumber) 
	{
		ice Entity_ICE = null;
		List<ice> Entity_Cls_ice_Lst = null;

		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			StringBuilder queryBuilder = new StringBuilder();
			Entity_Cls_ice_Lst=new ArrayList<ice>();
			Entity_Cls_ice_Lst = sn_utoo.createQuery(queryBuilder.append("From ice where mobile=").append(mobilenumber).append(" and opassenger.passenger_id='").append(accessID).append("'").toString()).setMaxResults(1).list();
			queryBuilder=null;
			accessID=null;
			if(!Entity_Cls_ice_Lst.isEmpty())
			{
				/*Entity_ICE=new ice();*/
				Entity_ICE=Entity_Cls_ice_Lst.get(0);
				Entity_Cls_ice_Lst.clear();
			}
		}
		catch (Exception expEntityClassList)
		{
			accessID=null;
			Entity_ICE = null;
			Entity_Cls_ice_Lst = null;
	  	  	logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_ICE;
	}
	@SuppressWarnings("unchecked")
	public booking getBooking(String bookingNumber) 
	{
		booking Entity_Cls_booking = null;
		List<booking> Entity_Cls_booking_Lst = null;
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			StringBuilder queryBuilder = new StringBuilder();
			Entity_Cls_booking_Lst=new ArrayList<booking>();
			Entity_Cls_booking_Lst = sn_utoo.createQuery(queryBuilder.append("From booking where booking_number='").append(bookingNumber).append("'").toString()).setMaxResults(1).list();
			queryBuilder=null;
			if(!Entity_Cls_booking_Lst.isEmpty())
			{
				/*Entity_Cls_booking=new booking();*/
				Entity_Cls_booking=Entity_Cls_booking_Lst.get(0);
				Entity_Cls_booking_Lst.clear();
			}
			bookingNumber=null;
		}
		catch (Exception expEntityClassList)
		{
			bookingNumber=null;
			Entity_Cls_booking = null;
			Entity_Cls_booking_Lst = null;
	  	  	logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_booking;
	}
	
		@SuppressWarnings("unchecked")
		public List<tdriver> getTdriver(String access_id) {

			List<tdriver> Entity_Cls_Lst = null;
			Session sn_utoo = null;
			try 
			{
				Entity_Cls_Lst=new ArrayList<tdriver>();
				sn_utoo = baseConnector.getSessionFactory().openSession();
				sn_utoo.beginTransaction();
				Criteria cr = sn_utoo.createCriteria(drivers.class)
					    .setProjection(Projections.projectionList()
					      .add(Projections.property("ocars_feature"), "ocars_feature")
					      .add(Projections.property("car_plate_no"), "car_plate_no")
					     .add(Projections.property("miles_drived_settings"), "miles_drived_settings")
					      .add(Projections.property("health_checkup_settings"), "health_checkup_settings")
					      .add(Projections.property("car_file_ID"), "car_file_ID")
					      .add(Projections.property("finger_print_file_ID"), "finger_print_file_ID")
					      .add(Projections.property("total_rating"), "total_rating")
					      .add(Projections.property("status"), "status")
					      .add(Projections.property("license_back_file_ID"), "license_back_file_ID")
					      .add(Projections.property("license_front_file_ID"), "license_front_file_ID")
					      .add(Projections.property("license_number"), "license_number")
					      .add(Projections.property("device_id"), "device_id")
					      .add(Projections.property("updated_on"), "updated_on")
					      .add(Projections.property("created_on"), "created_on")
					      .add(Projections.property("photo_file_id"), "photo_file_id")
					      .add(Projections.property("access_id"), "access_id")
					      .add(Projections.property("driver_name"), "driver_name")
					      .add(Projections.property("is_active"), "is_active")
					      .add(Projections.property("longitude"), "longitude")
					      .add(Projections.property("latitude"), "latitude")
					      .add(Projections.property("address"), "address")
					      .add(Projections.property("email"), "email")
					      .add(Projections.property("mobile"), "mobile")
					      .add(Projections.property("gender"), "gender")
					      .add(Projections.property("driver_id"), "driver_id"))
					      .add(Restrictions.eq("access_id", access_id))
					     
					    .setResultTransformer(Transformers.aliasToBean(tdriver.class));

			Entity_Cls_Lst = cr.list();
			}
			catch (Exception expEntityClassList)
			{
				 Entity_Cls_Lst=null;
		  	  	logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
				writeLog(expEntityClassList.toString(),"");
			}
			close_connection(sn_utoo);
			return Entity_Cls_Lst;
		}
	//	public static writelogFrom4J
		
		@SuppressWarnings("deprecation")
		public void logAnError(String className,String exception)
		{
			DateFormat df = new SimpleDateFormat("mm_ss");
			DateFormat df2 = new SimpleDateFormat("yyyy-dd-MM");
			Date today = Calendar.getInstance().getTime();        
			String reportDate = df.format(today);
			String reportDate1 = df2.format(today);
			  
			PatternLayout layout = new PatternLayout();
			String conversionPattern = "[%p] %d %c %M - %m%n";
			layout.setConversionPattern(conversionPattern);

			DailyRollingFileAppender rollingAppender = new DailyRollingFileAppender();
			rollingAppender.setFile(utoo.oBasePropertyInfo.getFTPHostUrlOrIP()+"/logs"+"/"+className+"/"+reportDate1+"/" + today.getHours() + "/"+reportDate+".log");
			rollingAppender.setDatePattern("'.'yyyy-MM-dd HH:mm:ss");
			rollingAppender.setLayout(layout);
			rollingAppender.activateOptions();

			Logger rootLogger = Logger.getRootLogger();
			rootLogger.setLevel(Level.ERROR);
			rootLogger.addAppender(rollingAppender);
			/*rollingAppender.close();*/

			Logger logger = Logger
					.getLogger(baseDAO.class);

			logger.error(exception);
		}
		public String stackTraceToString(Throwable e) {
		    StringBuilder sb = new StringBuilder();
		    for (StackTraceElement element : e.getStackTrace()) {
		        sb.append(element.toString());
		        sb.append("\n");
		    }
		    return sb.toString();
		}
		@SuppressWarnings("unchecked")
	public datatablelist getEntityClassList_DTbooking(String hqlQueryCommand,int MAX_RECORDS,int start) {

		List<booking> Entity_Cls_Lst = null;
		datatablelist oDatatable=null;
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			
			Query query=sn_utoo.createQuery(hqlQueryCommand);
			hqlQueryCommand=null;
			int size=query.list().size();
			oDatatable = new datatablelist();
			oDatatable.setSize(size);
			Entity_Cls_Lst = query.setFirstResult(start).setMaxResults(MAX_RECORDS).list();
			oDatatable.setGet_bookings(Entity_Cls_Lst);
			//Entity_Cls_Lst.clear();
			
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_Lst = null;
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return oDatatable;
	}
		@SuppressWarnings("unchecked")
		public datatablelist getEntityClassList_DTusers(String hqlQueryCommand,int MAX_RECORDS,int start) {

			List<users> Entity_Cls_Lst = null;
			datatablelist oDatatable=new datatablelist();
			Session sn_utoo = null;
			try 
			{
				sn_utoo = baseConnector.getSessionFactory().openSession();
				sn_utoo.beginTransaction();
				
				Query query=sn_utoo.createQuery(hqlQueryCommand);
				int size=query.list().size();
				oDatatable.setSize(size);
				Entity_Cls_Lst = query.setFirstResult(start).setMaxResults(MAX_RECORDS).list();
				oDatatable.setGet_users(Entity_Cls_Lst);
				/*Entity_Cls_Lst.clear();*/
				hqlQueryCommand=null;
			}
			catch (Exception expEntityClassList)
			{
				hqlQueryCommand=null;
				Entity_Cls_Lst=null;
				writeLog(expEntityClassList.toString(),"");
			}
			close_connection(sn_utoo);
			return oDatatable;
		}
	@SuppressWarnings("unchecked")
	public datatablelist getEntityClassList_DTdrivers(String hqlQueryCommand,int MAX_RECORDS,int start) {

		List<drivers> Entity_Cls_Lst = null;
		datatablelist oDatatable=new datatablelist();
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Query query=sn_utoo.createQuery(hqlQueryCommand);
			int size=query.list().size();
			oDatatable.setSize(size);
			Entity_Cls_Lst = query.setFirstResult(start).setMaxResults(MAX_RECORDS).list();
			oDatatable.setGet_drivers(Entity_Cls_Lst);
			hqlQueryCommand=null;
			/*Entity_Cls_Lst.clear();*/
			
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_Lst = null;
			oDatatable=null;
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return oDatatable;
	}
	@SuppressWarnings("unchecked")
	public datatablelist getEntityClassList_DTtariff(String hqlQueryCommand,int MAX_RECORDS,int start) {

		List<tariff> Entity_Cls_Lst = null;
		datatablelist oDatatable=new datatablelist();
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Query query=sn_utoo.createQuery(hqlQueryCommand);
			hqlQueryCommand=null;
			int size=query.list().size();
			oDatatable.setSize(size);
			Entity_Cls_Lst = query.setFirstResult(start).setMaxResults(MAX_RECORDS).list();
			oDatatable.setGet_tariff(Entity_Cls_Lst);
			/*Entity_Cls_Lst.clear();*/
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_Lst = null;
			oDatatable=null;
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return oDatatable;
	}
	@SuppressWarnings("unchecked")
	public datatablelist getEntityClassList_DTpromocode(String hqlQueryCommand,int MAX_RECORDS,int start) {

		List<passenger> Entity_Cls_Lst = null;
		datatablelist oDatatable=new datatablelist();
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Query query=sn_utoo.createQuery(hqlQueryCommand);
			hqlQueryCommand=null;
			int size=query.list().size();
			oDatatable.setSize(size);
			Entity_Cls_Lst = query.setFirstResult(start).setMaxResults(MAX_RECORDS).list();
			oDatatable.setGet_promocode(Entity_Cls_Lst);
			/*Entity_Cls_Lst.clear();*/
		}
		catch (Exception expEntityClassList)
		{
			 hqlQueryCommand=null;
			 oDatatable=null;
			 Entity_Cls_Lst = null;
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return oDatatable;
	}
	@SuppressWarnings("unchecked")
	public datatablelist getEntityClassList_DTaccident(String hqlQueryCommand,int MAX_RECORDS,int start) {

		List<accident> Entity_Cls_Lst = null;
		datatablelist oDatatable=new datatablelist();
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Query query=sn_utoo.createQuery(hqlQueryCommand);
			hqlQueryCommand=null;
			int size=query.list().size();
			oDatatable.setSize(size);
			Entity_Cls_Lst = query.setFirstResult(start).setMaxResults(MAX_RECORDS).list();
			oDatatable.setGet_accident(Entity_Cls_Lst);
			/*Entity_Cls_Lst.clear();*/
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_Lst = null;
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return oDatatable;
	}
	@SuppressWarnings("unchecked")
	public datatablelist getEntityClassList_DTlost_found(String hqlQueryCommand,int MAX_RECORDS,int start) {

		List<lost_found> Entity_Cls_Lst = null;
		datatablelist oDatatable=new datatablelist();
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Query query=sn_utoo.createQuery(hqlQueryCommand);
			hqlQueryCommand=null;
			int size=query.list().size();
			oDatatable.setSize(size);
			Entity_Cls_Lst = query.setFirstResult(start).setMaxResults(MAX_RECORDS).list();
			oDatatable.setGet_lost_found(Entity_Cls_Lst);
			/*Entity_Cls_Lst.clear();*/
			
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_Lst = null;
			oDatatable=null;
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return oDatatable;
	}
	@SuppressWarnings("unchecked")
	public datatablelist getEntityClassList_DTcarmodels(String hqlQueryCommand,int MAX_RECORDS,int start) {

		List<car_models> Entity_Cls_Lst = null;
		datatablelist oDatatable=new datatablelist();
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Query query=sn_utoo.createQuery(hqlQueryCommand);
			hqlQueryCommand=null;
			int size=query.list().size();
			oDatatable.setSize(size);
			Entity_Cls_Lst = query.setFirstResult(start).setMaxResults(MAX_RECORDS).list();
			oDatatable.setGet_car_models(Entity_Cls_Lst);
			/*Entity_Cls_Lst.clear();*/
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_Lst = null;
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return oDatatable;
	}
	@SuppressWarnings("unchecked")
	public datatablelist getEntityClassList_DTcarfeatures(String hqlQueryCommand,int MAX_RECORDS,int start) {

		List<car_features> Entity_Cls_Lst = null;
		datatablelist oDatatable=new datatablelist();
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Query query=sn_utoo.createQuery(hqlQueryCommand);
			hqlQueryCommand=null;
			int size=query.list().size();
			oDatatable.setSize(size);
			Entity_Cls_Lst = query.setFirstResult(start).setMaxResults(MAX_RECORDS).list();
			oDatatable.setGet_car_features(Entity_Cls_Lst);
			/*Entity_Cls_Lst.clear();*/
			
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_Lst = null;
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return oDatatable;
	}
	@SuppressWarnings("unchecked")
	public datatablelist getEntityClassList_DTinvoice(String hqlQueryCommand,int MAX_RECORDS,int start) {


		List<invoice> Entity_Cls_Lst = null;
		datatablelist oDatatable=null;
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			
			Query query=sn_utoo.createQuery(hqlQueryCommand);
			hqlQueryCommand=null;
			int size=query.list().size();
			oDatatable = new datatablelist();
			oDatatable.setSize(size);
			Entity_Cls_Lst = query.setFirstResult(start).setMaxResults(MAX_RECORDS).list();
			oDatatable.setGet_invoice(Entity_Cls_Lst);
			//Entity_Cls_Lst.clear();
			
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_Lst = null;
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return oDatatable;
	}
	@SuppressWarnings("unchecked")
	public datatablelist getEntityClassList_DTlocation(String hqlQueryCommand,int MAX_RECORDS,int start) {

		List<location> Entity_Cls_Lst = null;
		datatablelist oDatatable=new datatablelist();
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Query query=sn_utoo.createQuery(hqlQueryCommand);
			hqlQueryCommand=null;
			int size=query.list().size();
			oDatatable.setSize(size);
			Entity_Cls_Lst = query.setFirstResult(start).setMaxResults(MAX_RECORDS).list();
			oDatatable.setGet_location(Entity_Cls_Lst);
			/*Entity_Cls_Lst.clear();*/
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_Lst = null;
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return oDatatable;
	}
	@SuppressWarnings("unchecked")
	public datatablelist getEntityClassList_DTqrcode_users(String hqlQueryCommand,int MAX_RECORDS,int start) {

		List<qrcode_users> Entity_Cls_Lst = null;
		datatablelist oDatatable=new datatablelist();
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Query query=sn_utoo.createQuery(hqlQueryCommand);
			hqlQueryCommand=null;
			int size=query.list().size();
			oDatatable.setSize(size);
			Entity_Cls_Lst = query.setFirstResult(start).setMaxResults(MAX_RECORDS).list();
			oDatatable.setGet_qrcodeusers(Entity_Cls_Lst);
			/*Entity_Cls_Lst.clear();*/
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_Lst = null;
			oDatatable=null;
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return oDatatable;
	}
	@SuppressWarnings("unchecked")
	public datatablelist getEntityClassList_DTpassenger(String hqlQueryCommand,int MAX_RECORDS,int start) {

		List<passenger> Entity_Cls_Lst = null;
		datatablelist oDatatable=new datatablelist();
		Session sn_utoo = null;
		try 
		{
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Query query=sn_utoo.createQuery(hqlQueryCommand);
			hqlQueryCommand=null;
			int size=query.list().size();
			oDatatable.setSize(size);
			Entity_Cls_Lst = query.setFirstResult(start).setMaxResults(MAX_RECORDS).list();
			oDatatable.setGet_passenger(Entity_Cls_Lst);
			/*Entity_Cls_Lst.clear();*/
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_Lst = null;
			oDatatable=null;
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return oDatatable;
	}
	public promocode updatepromocode(promocode Rawpromocode)
	{
		promocode DBpromocode=new promocode();
		promocode Newpromocode=new promocode();
	
		try {
			DBpromocode=getpromocode("From promocode where promo_id='"+ Rawpromocode.getPromo_id()+"'",0);
			if(DBpromocode==null)
				return null;
			Rawpromocode.setPromo_id(DBpromocode.getPromo_id());
			Newpromocode=(promocode) mergeOneToOneObject(DBpromocode,Rawpromocode);	
			Rawpromocode=null;
			DBpromocode=null;
			UpdateEntityClass(Newpromocode);
		} catch (Exception expUpdateUserProfile) {
			Rawpromocode=null;
			DBpromocode=null;
			Newpromocode=null;
			writeLog("[Class.baseDAO] : updateCarfeature(...) : " + expUpdateUserProfile.getStackTrace().toString(), "");
		}
		return Newpromocode;
	}
	public users updateusers(users Rawusers)
	{
		users DBusers=new users();
		users Newusers=new users();
	
		try {
			DBusers=getusers("From users where access_id='"+ Rawusers.getAccess_id()+"'",0);
			if(DBusers==null)
				return null;
			Rawusers.setUser_id(DBusers.getUser_id());
			Newusers=(users) mergeOneToOneObject(DBusers,Rawusers);	
			Rawusers= null;
			DBusers=null;
			Newusers.setUpdated_on(new Date());
			UpdateEntityClass(Newusers);
		} catch (Exception expUpdateUserProfile) {
			Rawusers= null;
			DBusers=null;
			Newusers=null;
			writeLog("[Class.baseDAO] : updateCarfeature(...) : " + expUpdateUserProfile.getStackTrace().toString(), "");
		}
		return Newusers;
	}
	@SuppressWarnings("unchecked")
	public invoice EndTripInvoice(invoice oinvoice)
	{
		List<invoice> Entity_Cls_invoice_Lst = null;
		List<Object> Entity_Cls_Lst = null;
		invoice Entity_Cls_invoice=new invoice();
		Session sn_utoo = null;
		try 
		{
			Entity_Cls_invoice_Lst=new ArrayList<invoice>();
			Entity_Cls_Lst=new ArrayList<Object>();
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Entity_Cls_Lst=sn_utoo.createSQLQuery("CALL U2_0xP_STR_PRO_Invoice_Processing"
					+ "(:pTotalKM, :pTotalMins, :pAmount, :pBooking_number, :pTariff_ID, "
					+ ":pBooked_destination, :reaching_dat, :reaching_latitude, :reaching_longitude,"
					+ ":acutal_dest_latitude, :acutal_dest_longitude, :acutal_dest)")
						.setParameter("pTotalKM", oinvoice.getDistance())
						.setParameter("pTotalMins",oinvoice.getTotal_mins())
						.setParameter("pAmount", oinvoice.getAmount())
						.setParameter("pBooking_number",oinvoice.getObooking().getBooking_number())
						.setParameter("pTariff_ID", oinvoice.getOtariff().getTariff_id())
						.setParameter("pBooked_destination",oinvoice.getObooking().getBooked_destination())
						.setParameter("reaching_dat", oinvoice.getObooking().getReaching_dat())
						.setParameter("reaching_latitude",oinvoice.getObooking().getReaching_latitude())
						.setParameter("reaching_longitude", oinvoice.getObooking().getReaching_longitude())
						.setParameter("acutal_dest_latitude",oinvoice.getObooking().getActual_dest_latitude())
						.setParameter("acutal_dest_longitude",oinvoice.getObooking().getActual_dest_longitude())
					    .setParameter("acutal_dest",oinvoice.getObooking().getActual_dest()).list();
			            
			sn_utoo.getTransaction().commit();
			oinvoice=null;
			if(!Entity_Cls_Lst.isEmpty())
			{
				String raw_response_db=String.valueOf(Entity_Cls_Lst.get(0));
				if(isNumeric(raw_response_db))
					Entity_Cls_invoice.setError_code(Integer.parseInt(String.valueOf(raw_response_db)));	
				else
				{
					Entity_Cls_invoice_Lst = sn_utoo.createQuery("From invoice where invoice_no='" + raw_response_db + "'").list();
					raw_response_db=null;
					if(!Entity_Cls_invoice_Lst.isEmpty())
						Entity_Cls_invoice=Entity_Cls_invoice_Lst.get(0);
				}
			}
		} 
		catch (Exception expExecuteQuery) 
		{ 
			if(expExecuteQuery.toString().contains("java.lang.Short cannot be cast"))
			{
				try
				{   Entity_Cls_invoice=new invoice();
					Entity_Cls_invoice.setError_code(Integer.parseInt(String.valueOf(Entity_Cls_invoice_Lst.get(0))));
				}
				catch(Exception err)
				{
					Entity_Cls_invoice.setError_code(response_codes.Unable_to_reach_server.getCode());
				}
			}
				
			Entity_Cls_invoice=null;
			Entity_Cls_invoice=null;
			oinvoice=null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expExecuteQuery));
			writeLog(expExecuteQuery.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_invoice; 
	}
	public boolean isNumeric(String s) {  
	    return s.matches("[-+]?\\d*\\.?\\d+");  
	}  
	@SuppressWarnings("rawtypes")
	public List getMapListObject(String hqlQueryCommand)
	{
		List listObject=null;
		Session sn_utoo = null;
		sn_utoo = baseConnector.getSessionFactory().openSession();
		sn_utoo.beginTransaction();
		SQLQuery query = sn_utoo.createSQLQuery(hqlQueryCommand);
		hqlQueryCommand=null;
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        listObject=new ArrayList();
        listObject = query.list();
 		close_connection(sn_utoo);
 		sn_utoo= null;
 		query=null;
        return listObject;
	}
	@SuppressWarnings("unchecked")
	public List<favourites> getFavourites(String hqlQueryCommand,int MAX_RECORDS) {

		List<favourites> Entity_Cls_favourites_Lst = null;

		Session sn_utoo = null;
		try 
		{
			Entity_Cls_favourites_Lst=new ArrayList<favourites>();	
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			if(MAX_RECORDS==0)
				Entity_Cls_favourites_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			else
				Entity_Cls_favourites_Lst = sn_utoo.createQuery(hqlQueryCommand).setMaxResults(MAX_RECORDS).list();
			
			hqlQueryCommand=null;
			/*if(!Entity_Cls_favourites_Lst.isEmpty())
			{
				Entity_Cls_favourites=new favourites();
				Entity_Cls_favourites=Entity_Cls_favourites_Lst.get(0);
				//Entity_Cls_favourites_Lst.clear();
			}*/
		}
		catch (Exception expEntityClassList)
		{
			Entity_Cls_favourites_Lst = null;
	  	  	logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_favourites_Lst;
	}	
	public boolean isValidAccessID(String access_id,int type)
	{
		if(access_id=="")
			return false;
		else 
		{
			String sql="";
			sql=(type==1) ? "select driver_id from drivers where access_id='"+access_id+"'" : "select passenger_id from passenger where access_id='"+access_id+"'";
			int  getLongKeyValue=getIntKeyValue(sql);
			if(getLongKeyValue!=0)
				return true;
			else
				return false;
		}
	}
	@SuppressWarnings("unchecked")
	public payment_transaction getpayment(String hqlQueryCommand,int MAX_RECORDS) {

		payment_transaction Entity_Cls_payment = null;
		List<payment_transaction> Entity_Cls_payment_Lst = null;

		Session sn_utoo = null;
		try 
		{
			Entity_Cls_payment_Lst =  new ArrayList<payment_transaction>();
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			if(MAX_RECORDS==0)
				Entity_Cls_payment_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			else
				Entity_Cls_payment_Lst = sn_utoo.createQuery(hqlQueryCommand).setMaxResults(MAX_RECORDS).list();
			hqlQueryCommand=null;
			if(!Entity_Cls_payment_Lst.isEmpty())
			{
				Entity_Cls_payment=new payment_transaction();
				Entity_Cls_payment=Entity_Cls_payment_Lst.get(0);
				Entity_Cls_payment_Lst.clear();
			}
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			Entity_Cls_payment_Lst = null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
	    	writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_payment;
	}
	public String utcToISTFormat(Date utcDate){
		 
         if(utcDate==null)
          {
	         return "";
	      }
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String pattern = "dd-MM-yyyy HH:mm:ss";
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat(pattern);
		
		try {
			
			String formattedDate = formatter.format(utcDate);
			Date ISTDate = sdf.parse(formattedDate);
			formatter.setTimeZone(TimeZone.getTimeZone("IST"));
			String ISTDateString = formatter.format(ISTDate);
			return ISTDateString;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			// 2013-01-09 04:02:49 UTC
			// 2013-01-09 09:32:49
			return "";
		}

	 	}	
	public booking getLastBookingDAO(String hqlQueryCommand) 
	{

		List<booking> Entity_Cls_booking_Lst = null;
		Session sn_utoo = null;
		try 
		{
			Entity_Cls_booking_Lst= new ArrayList<booking>();
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			Entity_Cls_booking_Lst = sn_utoo.createQuery(hqlQueryCommand).setMaxResults(1).list();
			hqlQueryCommand=null;
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
	  	  	logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return (Entity_Cls_booking_Lst.isEmpty())?null:Entity_Cls_booking_Lst.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<driverapp_status> getDriverAppStatusList(String hqlQueryCommand,int MAX_RECORDS) 
	{

		List<driverapp_status> Entity_Cls_Lst = null;
		Session sn_utoo = null;
		try 
		{
			Entity_Cls_Lst=new ArrayList<driverapp_status>();
			sn_utoo = baseConnector.getSessionFactory().openSession();
			sn_utoo.beginTransaction();
			if(MAX_RECORDS==0)
				Entity_Cls_Lst = sn_utoo.createQuery(hqlQueryCommand).list();
			else
				Entity_Cls_Lst = sn_utoo.createQuery(hqlQueryCommand).setMaxResults(MAX_RECORDS).list();
			hqlQueryCommand=null;
		}
		catch (Exception expEntityClassList)
		{
			hqlQueryCommand=null;
			logAnError("baseDAO", baseDAO.getInstance().stackTraceToString(expEntityClassList));
			writeLog(expEntityClassList.toString(),"");
		}
		close_connection(sn_utoo);
		return Entity_Cls_Lst;
	}
	public String MailByVelocity(invoice oinvoice)
			throws Exception {
	
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty("runtime.log.logsystem.class",
				"org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
		ve.setProperty("runtime.log.logsystem.log4j.category", "velocity");
		ve.setProperty("runtime.log.logsystem.log4j.logger", "velocity");
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());

		ve.init();

		String templateName = "velocityMail";
		final String templatePath = "velocityTemp/" + templateName + ".vm";

		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream(templatePath);
		if (input == null) {
			throw new IOException("Template file doesn't exist");
		}

		VelocityContext context = new VelocityContext();
		context.put("passengerName", oinvoice.getObooking().getOpassenger().getPassenger_name());
		context.put("pbrNumber", oinvoice.getObooking().getPbr_number());
		
		String arraySourceLocation[]= oinvoice.getObooking().getActual_source().split(",", 2);
		context.put("sourceLocation1",arraySourceLocation[0] );
		context.put("sourceLocation2",arraySourceLocation[1] );
		
		String arrayDestLocation[]= oinvoice.getObooking().getActual_dest().split(",", 2);
		context.put("destLocation1",arrayDestLocation[0]);
		context.put("destLocation2",arrayDestLocation[1]);

		context.put("totalDistance",oinvoice.getDistance());
		
		float rideTime=oinvoice.getTotal_mins();
		
		if(rideTime<1)
		{
			context.put("totalMinutes",0);
		}
		else
		{
			context.put("totalMinutes",String.format("%1.f",rideTime));

		}

		context.put("totalAmount",String.format("%.2f", oinvoice.getTotal()));
		context.put("baseFare",oinvoice.getOtariff().getFlat_amount());
		context.put("baseMinKms",oinvoice.getOtariff().getMin_kms());
		
		context.put("rideTime",rideTime);
		context.put("cabType",oinvoice.getOtariff().getCarmodel_id().getCar_model_name());
		context.put("bookingDate",oinvoice.getObooking().getBooked_dat());
		context.put("bookingEmail",IsNullContent(oinvoice.getObooking().getOpassenger().getEmail()));
		context.put("afterMinKms",oinvoice.getOtariff().getAfter_minkms());
		
		context.put("waitingCharge",oinvoice.getOtariff().getWaitingchrg_per_min());
		
		float rateAfterMinKms = oinvoice.getTotal()-oinvoice.getOtariff().getFlat_amount();
		
		context.put("rateAfterMinKms",String.format("%.2f", rateAfterMinKms));
		
		String logoPath= BaseInfo.getString("mailImagePath")+"UTOO_logo.png";
		context.put("logoPath",logoPath);
		
	    String rupeesPath= BaseInfo.getString("mailImagePath")+"rupeesym.png";
		context.put("rupeesPath",rupeesPath);

		String arrowIcon= BaseInfo.getString("mailImagePath")+"arrow_ico.png";
		context.put("arrowIcon",arrowIcon);

		Template template = ve.getTemplate(templatePath, "UTF-8");
		StringWriter writer = new StringWriter();

		template.merge(context, writer);
		String htmlTemplate = writer.toString();
		

 		return htmlTemplate;
		// if (!ve.evaluate(context, writer, templatePath, reader)) {
		// throw new Exception("Failed to convert the template into html.");
		// }
	}

}
