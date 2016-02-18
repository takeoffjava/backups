package org.strobs.utoo.web.services.service;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import org.strobs.utoo.web.services.base.utoo;
import org.strobs.utoo.web.services.dao.baseDAO;
import org.strobs.utoo.web.services.eClasses.passenger;
import org.strobs.utoo.web.services.eClasses.payment_transaction;

public class srvePayment extends utoo {
	static final String SECURE_SECRET = "AF5AFE8202AA096A12364DEAEE9C86E2";
	private ResourceBundle objresource = ResourceBundle.getBundle("payment");
	static final char[] HEX_TABLE = new char[] { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	@SuppressWarnings("unchecked")
	public Map<String, Object> cash_Payment(payment_transaction opayment_transaction) {
		Map<String, Object> response = new HashMap<String, Object>();
		Map fields = new HashMap();
		passenger opassenger=new passenger();
		try {
			String Amount = opayment_transaction.getPayment_cash();
			if (Amount.trim().length() == 0) {
				response.put("status", 0);
				response.put("error", 5005);
				return response;
			}
			if ((Amount != null) && (Amount.length() > 0)) {
				fields.put("vpc_Amount", Amount);
			}
			opayment_transaction.setTransaction_id(UUID.randomUUID().toString().replace("-",""));
			fields.put("vpc_AccessCode",
					objresource.getString("vpc_AccessCode"));
			fields.put("vpc_Merchant", objresource.getString("vpc_Merchant"));
			fields.put("vpc_Command", objresource.getString("vpc_Command"));
			fields.put("vpc_OrderInfo", objresource.getString("vpc_OrderInfo"));
			fields.put("SubButL", objresource.getString("SubButL"));
			fields.put("vpc_ReturnURL", objresource.getString("vpc_ReturnURL"));
			fields.put("Title", objresource.getString("Title"));
			fields.put("vpc_Version", objresource.getString("vpc_Version"));
			fields.put("virtualPaymentClientURL",
					objresource.getString("virtualPaymentClientURL"));
			fields.put("vpc_Locale", objresource.getString("vpc_Locale"));
			fields.put("vpc_MerchTxnRef",
					objresource.getString("vpc_MerchTxnRef"));
			fields.put("unique_ID",opayment_transaction.getTransaction_id().toString());
			// no need to send the vpc url, EnableAVSdata and submit button to
			// the vpc
			String vpcURL = (String) fields.remove("virtualPaymentClientURL");
			fields.remove("SubButL");
			fields.put("AgainLink", objresource.getString("vpc_ReturnURL"));

			if (SECURE_SECRET != null && SECURE_SECRET.length() > 0) {
				String secureHash = hashAllFields(fields);
				fields.put("vpc_SecureHash", secureHash);
			}
			fields.remove("virtualPaymentClientURL");
			fields.remove("SubButL");
			// Create a redirection URL
			StringBuffer buf = new StringBuffer();
			buf.append(vpcURL).append('?');
			appendQueryFields(buf, fields);

			opassenger=baseDAO.getInstance().getPassengers("from passenger where access_id='"+opayment_transaction.getOpassenger().getAccess_id()+"'");
			if(opassenger==null){
				response.put("status", 0);
				response.put("error", 5005);
			}
			opayment_transaction.setOpassenger(opassenger);
			
			if (baseDAO.getInstance().SaveEntityClass(opayment_transaction)) {
				response.put("status", 1);
				response.put("url", buf.toString());
			} else {
				response.put("status", 0);
				response.put("error", 5005);
			}
		} catch (Exception ex) {
			response.put("status", 0);
			response.put("error", 5005);
		}
		return response;
	}

	String hashAllFields(Map fields) {
		// create a list and sort it
		List fieldNames = new ArrayList(fields.keySet());
		Collections.sort(fieldNames);
		// create a buffer for the md5 input and add the secure secret first
		StringBuffer buf = new StringBuffer();
		buf.append(SECURE_SECRET);

		// iterate through the list and add the remaining field values
		Iterator itr = fieldNames.iterator();

		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) fields.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				buf.append(fieldValue);
			}
		}

		MessageDigest md5 = null;
		byte[] ba = null;
		// create the md5 hash and UTF-8 encode it
		try {
			md5 = MessageDigest.getInstance("MD5");
			ba = md5.digest(buf.toString().getBytes("UTF-8"));
		} catch (Exception e) {
		} // wont happen

		return hex(ba);

	}

	static String hex(byte[] input) {
		StringBuffer sb = new StringBuffer(input.length * 2);

		for (int i = 0; i < input.length; i++) {
			sb.append(HEX_TABLE[(input[i] >> 4) & 0xf]);
			sb.append(HEX_TABLE[input[i] & 0xf]);
		}
		return sb.toString();
	}

	void appendQueryFields(StringBuffer buf, Map fields) {

		List fieldNames = new ArrayList(fields.keySet());
		Iterator itr = fieldNames.iterator();

		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) fields.get(fieldName);

			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// append the URL parameters
				buf.append(URLEncoder.encode(fieldName));
				buf.append('=');
				buf.append(URLEncoder.encode(fieldValue));
			}
			if (itr.hasNext()) {
				buf.append('&');
			}
		}

	}
	
	public Map<String,Object> getpayment(payment_transaction opayment_transaction)
	{
		if(opayment_transaction==null)  
			return map_response(false,5000);
		
		if(opayment_transaction.getOpassenger().getAccess_id()==""){
			opayment_transaction=null;
			return map_response(false,5030);}
		
		return map_response(baseDAO.getInstance().getpayment("From payment_transaction where opassenger.access_id='"+ opayment_transaction.getOpassenger().getAccess_id() +"'"
				+ " order by updated_on desc",1),5006);
	}
	public Map<String,Object>payment_wallet(passenger opassenger)
	{
		float wallet_amount=0.0f;
		float topay=0.0f,paidfrom_wallet=0.0f;
		Map<String,Object>wallet_response=new HashMap<String, Object>();
		passenger opassengerdb=baseDAO.getInstance().getPassengers("From passenger where access_id='"+ opassenger.getAccess_id() +"'");
		if(opassengerdb.getWallet()>0.0 && opassenger.getWallet()>0.0)
		{
			if(opassengerdb.getWallet()<= opassenger.getWallet())
			{
				topay=opassenger.getWallet()-opassengerdb.getWallet();
				paidfrom_wallet=opassengerdb.getWallet();
				wallet_amount=0;
				
				String hqlQueryCommand = " update passenger set wallet="+wallet_amount+" where access_id='"+ opassenger.getAccess_id() +"'";
				if (baseDAO.getInstance().executeHQLQuery(hqlQueryCommand)){
					wallet_response.put("status", 1);
					wallet_response.put("paidfromwallet", paidfrom_wallet);
				    wallet_response.put("topay", topay);
				}
				else{
					wallet_response.put("status", 0);
					wallet_response.put("error", 5005);
				}
			}
			else if(opassengerdb.getWallet()> opassenger.getWallet())
			{
			wallet_amount=opassengerdb.getWallet()-opassenger.getWallet();
			String hqlQueryCommand = " update passenger set wallet="+wallet_amount+" where access_id='"+ opassenger.getAccess_id() +"'";
			if (baseDAO.getInstance().executeHQLQuery(hqlQueryCommand)){
				wallet_response.put("status", 1);
    			wallet_response.put("paidfromwallet", opassenger.getWallet());
    			wallet_response.put("topay", 0);
			}
			else{
				wallet_response.put("status", 0);
				wallet_response.put("error", 5005);
			}
			}
			else{
				wallet_response.put("status", 0);
				wallet_response.put("error", 5005);
			}
		}
		else
			return map_response(false,"You have "+opassengerdb.getWallet()+" only in wallet...");
		
		return wallet_response;
    }
	public Map<String,Object> getpayment_wallet(passenger opassenger)
	{
		if(opassenger==null)  
			return map_response(false,5000);
		
		if(opassenger.getAccess_id()==""){
			opassenger=null;
			return map_response(false,5030);}
		
		return map_response(baseDAO.getInstance().getPassengers("From passenger where access_id='"+ opassenger.getAccess_id() +"'"
				+ " "),5006);
	}
}
