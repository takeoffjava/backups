package org.strobs.utoo.web.services.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.strobs.utoo.web.services.dao.baseDAO;

public class UserDetailsWithPwd implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
    	
    
    	String arrTemp[] = username.split(":");
    	String passwords= baseDAO.getInstance().getStringKeyValue("Select password from " + arrTemp[2] + " where mobile=" + arrTemp[0]);
		String computedPassword = "";
		  
		try {
			computedPassword = CryptUtilities.generateStorngPasswordHash(passwords,arrTemp[1]);
		} catch (NoSuchAlgorithmException e) {
	  	  	baseDAO.getInstance().logAnError("UserDetailsWithPwd", baseDAO.getInstance().stackTraceToString(e));
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
	  	  	baseDAO.getInstance().logAnError("UserDetailsWithPwd", baseDAO.getInstance().stackTraceToString(e));
			e.printStackTrace();
		}
		  
		/*  catch (Exception e){
			  System.out.println("MyCrypt error is :- " + e.getMessage());
		  }*/
        List<GrantedAuthority> authorities = new ArrayList<>();
      
		/*  for (UserRole userRole : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(userRole.getRoleName()));
        }
*/
       
        /*return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getIsEnabled(), true, true, true, authorities);*/
        return new org.springframework.security.core.userdetails.User(arrTemp[0],computedPassword.trim(), authorities);
    }

}