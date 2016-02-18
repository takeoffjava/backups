package com.nutansRplus.controller;
import java.security.Key;
import java.util.Date;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

//Access restriction: The type BASE64Decoder is not accessible due to restriction on required library C:\Program Files\Java\jre7\lib\rt.jar
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class Security 
{

    private static final String S_ALGORITHM_NAME = "AES";
    private static byte[] by_value=null;
    private static Key ky_generate_key =null;
    private static Cipher cip_algorithm_Ins=null;
    private static String s_response_enORdecrypted_value="";
    
    public Security(){}
    public static String encrypt(String s_encrypt_data)
    {
    	s_encrypt_data=SYSEncoder(s_encrypt_data);
    	try
    	{
	        ky_generate_key = generateKey();
	        cip_algorithm_Ins = Cipher.getInstance(S_ALGORITHM_NAME);
	        cip_algorithm_Ins.init(Cipher.ENCRYPT_MODE, ky_generate_key);
	        by_value = cip_algorithm_Ins.doFinal(s_encrypt_data.getBytes());
	        s_response_enORdecrypted_value = new BASE64Encoder().encode(by_value).trim();
	    }
    	catch(Exception exp_encrypt)
    	{
    		return s_response_enORdecrypted_value;
    	}
        return s_response_enORdecrypted_value;
    }

    public static String decrypt(String s_decrypt_data)
    {
    	
    	byte[] by_decorded_value=null;
    	try
    	{
    		ky_generate_key = generateKey();
	        cip_algorithm_Ins = Cipher.getInstance(S_ALGORITHM_NAME);
	        cip_algorithm_Ins.init(Cipher.DECRYPT_MODE, ky_generate_key);
	        by_decorded_value = new BASE64Decoder().decodeBuffer(s_decrypt_data);
	        by_value = cip_algorithm_Ins.doFinal(by_decorded_value);
	        s_response_enORdecrypted_value = new String(by_value).trim();
	        s_response_enORdecrypted_value=SYSDecoder(s_response_enORdecrypted_value);
    	}
        catch(Exception exp_decrypt)
    	{
	        return s_response_enORdecrypted_value;
    	}
        return s_response_enORdecrypted_value;
    }

    private static Key generateKey() throws Exception {
    	return new SecretKeySpec(new String(" mmCO4nXKq1vVw==").getBytes(), S_ALGORITHM_NAME);
    }
    private static String SYSEncoder(String DATA)
    {
		return new String(Base64.encodeBase64(DATA.getBytes()));
    }
    private static String SYSDecoder(String ENDATA)
    {
		return new String(Base64.decodeBase64(ENDATA.getBytes()));
    }
    private static Random random = new Random((new Date()).getTime());
    public static String getActivationCode(int length) {
      char[] values = {'A','C','F','G','H','K','M','N','P','R','T',
               'U','V','W','X','Y','Z','4','6','7','9'};
      String out = "";
      for (int i=0;i<length;i++) {
          int idx=random.nextInt(values.length);
        out += values[idx];
      }
      return out;
    }
    
}
