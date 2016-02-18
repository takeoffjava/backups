package org.strobs.utoo.web.services.base;

import static java.lang.System.out;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;

public class pubnubnotify 
{
	Pubnub pubnub;
	String publish_key = "pub-c-a1b06bc3-561d-4b01-91b2-d31a3c4ff0d0";
	String subscribe_key = "sub-c-540cdff2-c19c-11e5-a9b2-02ee2ddab7fe";
	String secret_key = "sec-c-ZDIyY2I2ODgtNTgzNS00NDJiLTljZWQtMTM2YTVjM2FjZDIx";
	String cipher_key = "";
	boolean SSL;
	
	public void publish(String channel,String message, boolean store) {
		pubnub = new Pubnub(publish_key,subscribe_key,secret_key,cipher_key,store);
        pubnub.setCacheBusting(false);
		Callback cb = new Callback() {
			@Override
			public void successCallback(String channel, Object message) {
				notifyUser("PUBLISH : " + message);
			}

			@Override
			public void errorCallback(String channel, PubnubError error) {
				notifyUser("PUBLISH : " + error);
			}
		};

			try {
				Integer i = Integer.parseInt(message);
				pubnub.publish(channel, i, store, cb);
			} catch (Exception e) {

			}
			try {
				Double d = Double.parseDouble(message);
				pubnub.publish(channel, d, store, cb);
			} catch (Exception e) {

			}
			try {
				JSONArray js = new JSONArray(message);
				pubnub.publish(channel, js, store, cb);
			} catch (Exception e) {

			}
			try {
				JSONObject js = new JSONObject(message);
				pubnub.publish(channel, js, store, cb);
			} catch (Exception e) {

			}
			pubnub.publish(channel, message, store, cb);
		

	}
	private void notifyUser(Object message) {
		out.println(message.toString());
	}
}
