package org.strobs.utoo.web.services.eClasses;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table(name="u2_0xs_tbl_mst_passenger") // U2 -> UTOO TBL-> TABLE MST -> MASTER
@Entity
@JsonInclude(Include.NON_NULL)
public class passenger 
{
	@Id
	@GeneratedValue
	@Column(name="pk_clm_passenger_id",columnDefinition="bigint(10) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column
	private long passenger_id;
	
	@Column(name="clm_passenger_name",columnDefinition="varchar(50) default null",length=50)
	private String passenger_name;
	
	@Column(name="clm_gender")
	private boolean gender;
	
	@Column(name="clm_mobile",columnDefinition="bigint(10) unsigned not null default 0",nullable=false)
	private long mobile;
	
	@Column(name="clm_password",length=350)
	private String password;
	
	@Column(name="clm_email",columnDefinition="varchar(255) default null",length=100)
	private String email;
	
	@Column(name="clm_address",columnDefinition="varchar(255) default null",length=255)
	private String address;

	@Column(name="clm_latitude",columnDefinition="float(13,8)")
	private double latitude;
	
	@Column(name="clm_longitude",columnDefinition="float(13,8)")
	private double longitude;

	@Column(name="clm_is_active",columnDefinition="tinyint(1) default 0")
	private int is_active;
	
	@Column(name="clm_access_id",columnDefinition="varchar(70) not null",length=70,nullable=false)
	private String access_id;
	
	@Column(name="clm_photo_file_id",length=100)
	private String photo_file_id;

	@Column(name="clm_created_on",columnDefinition="timestamp default now()")
	private Date created_on;
	
	@Column(name="clm_updated_on",columnDefinition="timestamp null default null",nullable=true)
	private Date updated_on;
	
	@Column(name="clm_device_id",columnDefinition="varchar(255) not null")
	private String device_id;
	
	@Column(name="clm_status",columnDefinition="tinyint(1) default 0")
	private int status;
	
	@Column(name="clm_promo_code",columnDefinition="varchar(10) default null")
	private String promo_code;
	
	@Column(name="clm_used_promo_code")
	private boolean is_used_promo_code;
	
	@Column(name="clm_total_rating",columnDefinition="tinyint(1) default 0")
	private int total_rating;
	
	@Column(name="clm_device_type_name")
	private String device_type_name;   //IOS, Android, Web
	
	@Column(name="clm_wallet",columnDefinition="float(13,8) default 0")
	private float wallet;
	
	/*
	 * Those fields won't affect database
	 * 
	 * */
	
	@Transient
	private String password_key_type;   //salt
	
	
	@Transient
	private int otp;
	
	@Transient
	private Date login_time;
	
	@Transient
	private Date logout_time;
	
	@Transient
	private boolean Invalid_Number;
	
	@Transient
	private boolean is_need_driver_list;
	
	@Transient
	private boolean is_need_blocked_list;
	
	@Transient
	private boolean is_need_ice_list;
	
	

	public passenger() {}

	
	public passenger(boolean invalid_Number) {
		super();
		Invalid_Number = invalid_Number;
	}

	public String getPassenger_name() {
		return passenger_name;
	}
	
	public long getPassenger_id() {
		return passenger_id;
	}


	public void setPassenger_id(long passenger_id) {
		this.passenger_id = passenger_id;
	}


	public void setPassenger_name(String passenger_name) {
		this.passenger_name = passenger_name;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	@JsonIgnore
	public int getIs_active() {
		return is_active;
	}

	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}

	public String getAccess_id() {
		return access_id;
	}

	public void setAccess_id(String access_id) {
		this.access_id = access_id;
	}

	public String getPhoto_file_id() {
		return photo_file_id;
	}

	public void setPhoto_file_id(String photo_file_id) {
		this.photo_file_id = photo_file_id;
	}

	@JsonIgnore
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getCreated_on() {
		return created_on;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}
	@JsonIgnore
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getUpdated_on() {
		return updated_on;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}
	@JsonIgnore
	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	@JsonIgnore
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPromo_code() {
		return promo_code;
	}

	public void setPromo_code(String promo_code) {
		this.promo_code = promo_code;
	}
	@JsonIgnore
	public String getPassword_key_type() {
		return password_key_type;
	}

	public void setPassword_key_type(String password_key_type) {
		this.password_key_type = password_key_type;
	}

	public String getDevice_type_name() {
		return device_type_name;
	}

	public void setDevice_type_name(String device_type_name) {
		this.device_type_name = device_type_name;
	}
	@JsonIgnore
	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}
	
	@JsonIgnore
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getLogin_time() {
		return login_time;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
	}
	@JsonIgnore
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getLogout_time() {
		return logout_time;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setLogout_time(Date logout_time) {
		this.logout_time = logout_time;
	}
	@JsonIgnore
	public boolean isInvalid_Number() {
		return Invalid_Number;
	}

	public void setInvalid_Number(boolean invalid_Number) {
		Invalid_Number = invalid_Number;
	}

	@JsonIgnore
	public boolean isIs_need_driver_list() {
		return is_need_driver_list;
	}


	public void setIs_need_driver_list(boolean is_need_driver_list) {
		this.is_need_driver_list = is_need_driver_list;
	}

	@JsonIgnore
	public boolean isIs_need_blocked_list() {
		return is_need_blocked_list;
	}


	public void setIs_need_blocked_list(boolean is_need_blocked_list) {
		this.is_need_blocked_list = is_need_blocked_list;
	}

	public int getTotal_rating() {
		return total_rating;
	}

	public void setTotal_rating(int total_rating) {
		this.total_rating = total_rating;
	}

	@JsonIgnore
	public boolean isIs_need_ice_list() {
		return is_need_ice_list;
	}


	public void setIs_need_ice_list(boolean is_need_ice_list) {
		this.is_need_ice_list = is_need_ice_list;
	}


	public boolean isIs_used_promo_code() {
		return is_used_promo_code;
	}


	public void setIs_used_promo_code(boolean is_used_promo_code) {
		this.is_used_promo_code = is_used_promo_code;
	}


	public float getWallet() {
		return wallet;
	}


	public void setWallet(float f) {
		this.wallet = f;
	}

	
	
}

