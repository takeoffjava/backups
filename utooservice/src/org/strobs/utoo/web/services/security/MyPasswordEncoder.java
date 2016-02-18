package org.strobs.utoo.web.services.security;

import org.springframework.security.authentication.encoding.PasswordEncoder;

@SuppressWarnings("deprecation")
public class MyPasswordEncoder implements PasswordEncoder {

    @SuppressWarnings("unused")
	private static int BCRYPT_WORKING_FACTOR = 10;

    @Override
    public String encodePassword(String rawPass, Object salt) {
        return null;
    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        boolean pwdStatus;
        pwdStatus = false;
        
       /* System.out.println("Inside my pwd encoder encPass -> " + encPass);
        System.out.println("Inside my pwd encoder rawPass -> " + rawPass);
        System.out.println("Inside my pwd encoder salt -> " + salt);*/
        
    	return pwdStatus;
    	/*return BCrypt.checkpw(rawPass, encPass);*/
    }
 
}