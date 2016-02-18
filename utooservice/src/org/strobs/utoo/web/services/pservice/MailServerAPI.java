package org.strobs.utoo.web.services.pservice;

import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.Future;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.strobs.utoo.web.services.base.utoo;


@Service("mailService")
public class MailServerAPI extends utoo implements MailServerApiInf{
	public static ResourceBundle BaseInfo = ResourceBundle.getBundle("utoo");


	@Async
	public  Future<String> SendMailToServer(String htmlTemplate,String ReceiverMailId) {
			 
			String host = BaseInfo.getString("Host");
			String serverUserName=BaseInfo.getString("ServerUserName");
			String serverPassword=BaseInfo.getString("ServerPassword");
			
			Properties props = System.getProperties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", BaseInfo.getString("Port"));
			props.put("mail.smtps.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
						
			try {

				Session session = Session.getInstance(props, null);
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(serverUserName));

				InternetAddress address = new InternetAddress(ReceiverMailId);

				message.setRecipient(Message.RecipientType.TO, address);
				message.setSubject("Utoo");

				Multipart messageBodyPart = new MimeMultipart();
				MimeBodyPart htmlPart = new MimeBodyPart();
				htmlPart.setContent(htmlTemplate, "text/html; charset=utf-8");
				messageBodyPart.addBodyPart(htmlPart);
				message.setContent(messageBodyPart);

				try {
					Transport tr = session.getTransport("smtp");
					tr.connect(host, serverUserName, serverPassword);
					tr.sendMessage(message, message.getAllRecipients());

					tr.close();

				} catch (SendFailedException sfe) {

					return new AsyncResult<String>("Failure");
					
				}
			} catch (Exception e) {
				return new AsyncResult<String>("Failure");
			}

			return new AsyncResult<String>("Success");
		}

}
