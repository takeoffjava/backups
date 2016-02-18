package org.strobs.utoo.web.services.base;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import net.iharder.Base64;

import org.strobs.utoo.web.services.dao.baseDAO;


/**
 * Created by chandrasekar.kuppusa on 04-10-2015.
 */
public class hashPwd {

    public String getHashForPassword(String password) {

        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();

        }
        catch (NoSuchAlgorithmException e)
        {
			baseDAO.getInstance().logAnError("hashPwd", baseDAO.getInstance().stackTraceToString(e));
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public String getTotalHash(String hashPassword, String saltValue)
    {
        int iterations = 1000;
        char[] chars = hashPassword.toCharArray();
        byte[] salt = saltValue.getBytes();
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 160);
        SecretKeyFactory skf = null;
        try {
            skf = SecretKeyFactory.getInstance ("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
			baseDAO.getInstance().logAnError("hashPwd", baseDAO.getInstance().stackTraceToString(e));
            e.printStackTrace();
        }
        byte[] hash = null;
        try {
            hash = skf.generateSecret(spec).getEncoded ();
        } catch (InvalidKeySpecException e) {
			baseDAO.getInstance().logAnError("hashPwd", baseDAO.getInstance().stackTraceToString(e));
            e.printStackTrace();
        }
        String totalHash="";
        try
        {
        	totalHash= new String(Base64.encodeBytes(hash, 0));
        }
        catch(Exception s)
        {
			baseDAO.getInstance().logAnError("hashPwd", baseDAO.getInstance().stackTraceToString(s));

        }
        //System.out.println(totalHash);
        return totalHash;
    }

    public String getSalt()
    {
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstance ("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
			baseDAO.getInstance().logAnError("hashPwd", baseDAO.getInstance().stackTraceToString(e));
            e.printStackTrace();
        }
        byte[] salt = new byte[16];
        sr.nextBytes (salt);
        return salt.toString();
    }
}
