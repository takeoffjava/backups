package org.strobs.utoo.web.services.pservice;

import java.util.concurrent.Future;

public interface MailServerApiInf {
	public  Future<String> SendMailToServer(String htmlTemplate,String ReceiverMailId) ;
}
