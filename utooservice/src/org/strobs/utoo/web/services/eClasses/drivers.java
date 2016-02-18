package org.strobs.utoo.web.services.eClasses;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table(name="u2_0xs_tbl_mst_drivers") // U2 -> UTOO TBL-> TABLE MST -> MASTER
@Entity
@JsonInclude(Include.NON_NULL)
public class drivers 
{
	@Id
	@GeneratedValue
	@Column(name="pk_clm_driver_id",columnDefinition="bigint(10) unsigned null",nullable=true) // PK -> Primary Key CLM -> Column
	private int driver_id;
	
	@Column(name="clm_driver_name",columnDefinition="varchar(50) default null")
	private String driver_name;
	
	@Column(name="clm_gender",columnDefinition="bit not null default 1")
	private boolean gender;
	
	@Column(name="clm_mobile",columnDefinition="bigint(10) unsigned not null default 0",nullable=false)
	private long mobile;
	
	@Column(name="clm_password",columnDefinition="varchar(100) default null")
	private String password;
	
	@Column(name="clm_email",columnDefinition="varchar(255) default null")
	private String email;
	
	@Column(name="clm_address",columnDefinition="varchar(255) default null")
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
	
	@Column(name="clm_device_id",columnDefinition="varchar(255)")
	private String device_id;
	
	@Column(name="clm_license_no",columnDefinition="varchar(50) default null")
	private String license_number;

	@Column(name="clm_license_front_file_id",length=100)
	private String license_front_file_ID;
	
	@Column(name="clm_license_back_file_id",length=100)
	private String license_back_file_ID;
	
	@Column(name="clm_status",columnDefinition="tinyint(1) default 0")
	private int status;
	
	@Column(name="clm_is_deleted",columnDefinition="bit(1) default 0")
	private boolean is_deleted;
	
	/*
	 *0- Available 
	 *1- Busy
	 *2- Offline
	 * */
	
	
	
	@Column(name="clm_total_rating",columnDefinition="tinyint(1) default 0",nullable=false)
	private int total_rating;
	
	@Column(name="clm_finger_print_file_id",columnDefinition="varchar(50) default null")
	private String finger_print_file_ID;
	
	@Column(name="clm_car_file_id",columnDefinition="varchar(50) default null")
	private String car_file_ID;
	
	@Column(name="clm_pass_code",columnDefinition="varchar(50) default null")
	private String pass_code;
	
	@Column(name="clm_health_checkup_settings",columnDefinition="bit(1) default 0")
	private boolean health_checkup_settings;
	
	@Column(name="clm_miles_drived_settings",columnDefinition="bit(1) default 0")
	private boolean miles_drived_settings;
	
	@Column(name="clm_car_plate_no",columnDefinition="varchar(20) default null")
	private String car_plate_no;
	
	
	@Column(name="clm_minor_value",columnDefinition="int(6) default 0")
	private int minor_value;
	
	@Column(name="clm_major_value",columnDefinition="int(6) default 0")
	private int major_value;
	
	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_car_feature_id",name="fk_clm_car_feature_id",columnDefinition="smallint(3) default 1")
	private car_features ocars_feature;
	
	@Transient
	private String password_key_type;   //salt
	@Transient
	private int MaximumRow=0;
	@Transient
	private int index_glob;
	@Transient
	private float meter;

	public drivers() {}

	public int getDriver_id() {
		return driver_id;
	}


	public void setDriver_id(int driver_id) {
		this.driver_id = driver_id;
	}


	public String getDriver_name() {
		return driver_name;
	}


	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
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


	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getCreated_on() {
		return created_on;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}


	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getUpdated_on() {
		return updated_on;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}


	public String getDevice_id() {
		return device_id;
	}


	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}


	public String getLicense_number() {
		return license_number;
	}


	public void setLicense_number(String license_number) {
		this.license_number = license_number;
	}


	public String getLicense_front_file_ID() {
		return license_front_file_ID;
	}


	public void setLicense_front_file_ID(String license_front_file_ID) {
		this.license_front_file_ID = license_front_file_ID;
	}


	public String getLicense_back_file_ID() {
		return license_back_file_ID;
	}


	public void setLicense_back_file_ID(String license_back_file_ID) {
		this.license_back_file_ID = license_back_file_ID;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public int getTotal_rating() {
		return total_rating;
	}


	public void setTotal_rating(int total_rating) {
		this.total_rating = total_rating;
	}

	
	public car_features getOcars_feature() {
		return ocars_feature;
	}


	public void setOcars_feature(car_features ocars_feature) {
		this.ocars_feature = ocars_feature;
	}

	@JsonIgnore
	public String getPassword_key_type() {
		return password_key_type;
	}


	public String getFinger_print_file_ID() {
		return finger_print_file_ID;
	}

	@JsonIgnore
	public void setFinger_print_file_ID(String finger_print_file_ID) {
		this.finger_print_file_ID = finger_print_file_ID;
	}


	public void setPassword_key_type(String password_key_type) {
		this.password_key_type = password_key_type;
	}

	public String getPass_code() {
		return pass_code;
	}
	public void setPass_code(String pass_code) {
		this.pass_code = pass_code;
	}
	
	@JsonIgnore
	public boolean isHealth_checkup_settings() {
		return health_checkup_settings;
	}
	public void setHealth_checkup_settings(boolean health_checkup_settings) {
		this.health_checkup_settings = health_checkup_settings;
	}
	
	@JsonIgnore
	public boolean isMiles_drived_settings() {
		return miles_drived_settings;
	}
	public void setMiles_drived_settings(boolean miles_drived_settings) {
		this.miles_drived_settings = miles_drived_settings;
	}
	
	
	public String getCar_file_ID() {
		return car_file_ID;
	}
	public void setCar_file_ID(String car_file_ID) {
		this.car_file_ID = car_file_ID;
	}
	
	public String getCar_plate_no() {
		return car_plate_no;
	}

	public void setCar_plate_no(String car_plate_no) {
		this.car_plate_no = car_plate_no;
	}

	public int getMinor_value() {
		return minor_value;
	}

	public void setMinor_value(int minor_value) {
		this.minor_value = minor_value;
	}

	public int getMajor_value() {
		return major_value;
	}

	public void setMajor_value(int major_value) {
		this.major_value = major_value;
	}

	public int getMaximumRow() {
		return MaximumRow;
	}

	public void setMaximumRow(int maximumRow) {
		MaximumRow = maximumRow;
	}

	public int getIndex_glob() {
		return index_glob;
	}

	public void setIndex_glob(int index_glob) {
		this.index_glob = index_glob;
	}
	public float getMeter() {
		return meter;
	}

	public void setMeter(float meter) {
		this.meter = meter;
	}

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}	
}
