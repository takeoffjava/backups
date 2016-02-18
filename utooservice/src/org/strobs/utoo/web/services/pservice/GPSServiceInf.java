package org.strobs.utoo.web.services.pservice;

import java.util.Map;
import java.util.concurrent.Future;

public interface GPSServiceInf {
	public Future<String> SendMessageToAndroidMobile(Map<String,Object> map_PostMessageContent,String deviceTokenID,boolean isPassengerPush);
	public Future<String> SendMessageToIOSMobile(Object oPostMessageContent,String deviceTokenID,String alert);
}
