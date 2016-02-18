package org.strobs.utoo.web.services.classes;


public class tokenDetails {
	private long mobile;
	private String password;
	private String password_key_type;   //salt
	
	public tokenDetails(long mobileNumber,String password_temp,String pass_keyType)
	{
		this.mobile=mobileNumber;
		this.password=password_temp;
		this.password_key_type=pass_keyType;
	}
	public long getMobile() {
		return mobile;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword_key_type() {
		return password_key_type;
	}
	public void setPassword_key_type(String password_key_type) {
		this.password_key_type = password_key_type;
	}
}
