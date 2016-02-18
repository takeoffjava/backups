package org.strobs.utoo.web.services.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


public class CryptUtilities {

	public static String generateStorngPasswordHash(String password, String saltValue) throws NoSuchAlgorithmException, InvalidKeySpecException
    
	{
        int iterations = 1000;
//        String saltValue = getSalt();
        char[] chars = password.toCharArray();
        //byte[] salt = getSalt().getBytes();
         byte[] saltByte = saltValue.getBytes();
        PBEKeySpec spec = new PBEKeySpec(chars, saltByte, iterations, 160);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        //String s = new sun.misc.BASE64Encoder().encode(hash);
        String sa = new String(Base64.encode(hash));
        //String sa2 = new String(Base64.encode(hash2));
        //System.out.println("++++++++hash2" +sa2);
        //return iterations + ":" + toHex(salt) + ":" + sa;
        return sa;
    }
   
	public static String getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }
    
	/*private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
    private static String getStaticSalt() throws NoSuchAlgorithmException
    {
        final byte[] SALT = {
            (byte) 0xde, (byte) 0x40, (byte) 0x10, (byte) 0x33,
            (byte) 0xde, (byte) 0x40, (byte) 0x10, (byte) 0x33, };
        return toHex(SALT);
    }*/
}
