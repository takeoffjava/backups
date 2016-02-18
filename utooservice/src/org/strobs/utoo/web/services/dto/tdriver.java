package org.strobs.utoo.web.services.dto;


import java.util.Date;

import org.strobs.utoo.web.services.eClasses.car_features;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class tdriver 
{
	private int driver_id;
	private String driver_name;
	private boolean gender;
	private long mobile;
	private String email;
	private String address;
	private double latitude;
	private double longitude;
	private int is_active;
	private String access_id;
	private String photo_file_id;
	private Date created_on;
	private Date updated_on;
	private String device_id;
	private String license_number;
	private String license_front_file_ID;
	private String license_back_file_ID;
	private int status;
	private int total_rating;
	private String finger_print_file_ID;
	private String car_file_ID;
	private boolean health_checkup_settings;
	private boolean miles_drived_settings;
	private String car_plate_no;
	private car_features ocars_feature;
	
	public tdriver() {}
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

	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

	public Date getUpdated_on() {
		return updated_on;
	}

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

	public String getFinger_print_file_ID() {
		return finger_print_file_ID;
	}


	public void setFinger_print_file_ID(String finger_print_file_ID) {
		this.finger_print_file_ID = finger_print_file_ID;
	}

	public boolean isHealth_checkup_settings() {
		return health_checkup_settings;
	}
	public void setHealth_checkup_settings(boolean health_checkup_settings) {
		this.health_checkup_settings = health_checkup_settings;
	}
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
	public int getDriver_id() {
		return driver_id;
	}
	public void setDriver_id(int driver_id) {
		this.driver_id = driver_id;
	}
}
