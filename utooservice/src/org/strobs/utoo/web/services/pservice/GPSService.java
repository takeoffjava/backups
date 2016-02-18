package org.strobs.utoo.web.services.pservice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.strobs.utoo.web.services.base.utoo;
import org.strobs.utoo.web.services.dao.baseDAO;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

@Service("reportsService")
public class GPSService extends utoo implements GPSServiceInf 
{
	@Async
	public Future<String> SendMessageToAndroidMobile(Map<String,Object> map_PostMessageContent,String deviceTokenID,boolean isPassengerPush) 
	{
		Sender pshSender=null;
		Message pshMessage=null;
		if(deviceTokenID==null)
			return new AsyncResult<String>("0");
		
		if(deviceTokenID=="")
			return new AsyncResult<String>("0");
		try 
		{
			pshSender = new Sender(isPassengerPush?oBasePropertyInfo.getPassengerANROIDPushKeyCode():oBasePropertyInfo.getANROIDPushKeyCode());
			JSONObject jsonConverter= new JSONObject(map_PostMessageContent);
			pshMessage = new Message.Builder().addData("message", jsonConverter.toString()).build();
			@SuppressWarnings("unused")
			Result rest=pshSender.send(pshMessage,deviceTokenID,1);
		} catch (Exception expSendMessageToAndroidMobile) {
	  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(expSendMessageToAndroidMobile));
	       }
		return new AsyncResult<String>("1");
	}
	@Async
	public Future<String> SendMessageToIOSMobile(Object oPostMessageContent,String deviceTokenID,String alert) 
	{
		PushNotificationPayload payloadContent=null;
		List<PushedNotification> notifications=null;
		List<Object> PayLoadContent=null;
		boolean bstatus=false;
		if(deviceTokenID==null)
			return new AsyncResult<String>("0");
		
		if(deviceTokenID=="")
			return new AsyncResult<String>("0");
		try 
		{
			PayLoadContent=new ArrayList<Object>();
			PayLoadContent.add(oPostMessageContent);
			
			try 
			{
				payloadContent = PushNotificationPayload.fromJSON("{\"aps\":{\"content-available\":1,\"sound\":\"default\",\"alert\":\""+alert+"\"}}");
				if(PayLoadContent!=null)
					payloadContent.addCustomDictionary("utoo_payload", PayLoadContent);
	        }
			catch (JSONException expSendMessageToIOSMobile) 
			{
		  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(expSendMessageToIOSMobile));
		    }
			notifications = Push.payload(payloadContent, new File(oBasePropertyInfo.getIOSPushKeyFile().toString()), 
					oBasePropertyInfo.getIOSUser(),true,deviceTokenID.replace(" ", ""));
			for (PushedNotification notification : notifications) 
					bstatus=notification.isSuccessful()?true:false;
		} catch (KeystoreException e) {
	  	  	baseDAO.getInstance().logAnError("utoo", baseDAO.getInstance().stackTraceToString(e));
	       
		} catch (CommunicationException e) {
	  	  	baseDAO.getInstance().logAnError("utoo",baseDAO.getInstance().stackTraceToString(e));
		}
		return new AsyncResult<String>(bstatus?"1":"0");
	}
}
