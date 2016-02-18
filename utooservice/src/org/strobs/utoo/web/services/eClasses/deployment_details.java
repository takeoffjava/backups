package org.strobs.utoo.web.services.eClasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table(name="u2_0xs_tbl_info_server_path") // U2 -> UTOO TBL-> TABLE MST -> MASTER
@Entity
@JsonInclude(Include.NON_NULL)
public class deployment_details {

	@Id
	@GeneratedValue 
	@Column(name="pk_clm_server_path_id",columnDefinition="tinyint(1) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column
	private long server_path_id;

	@Column(name="clm_base_path",columnDefinition="varchar(100) default null",length=100)
	private String base_path;
	
	@Column(name="clm_base_folder",columnDefinition="varchar(100) default null",length=100)
	private String base_folder;

	@Column(name="clm_car",columnDefinition="varchar(100) default null",length=100)
	private String car;

	@Column(name="clm_license",columnDefinition="varchar(100) default null",length=100)
	private String license;

	@Column(name="clm_finger_print",columnDefinition="varchar(100) default null",length=100)
	private String finger_print;

	@Column(name="clm_profile_photo",columnDefinition="varchar(100) default null",length=100)
	private String profile_photo;
	
	@Column(name="clm_passenger_profile_photo",columnDefinition="varchar(100) default null",length=100)
	private String passenger_profile_photo;
	
	@Column(name="clm_accident_audio",columnDefinition="varchar(100) default null",length=100)
	private String accident_audio;

	@Column(name="clm_accident_image",columnDefinition="varchar(100) default null",length=100)
	private String accident_image;
	
	@Column(name="clm_ibeacon_id",columnDefinition="varchar(10) default null")
	private String ibeacon_uuid;
	
	public deployment_details() {
		// TODO Auto-generated constructor stub
	}

	@JsonIgnore
	public long getServer_path_id() {
		return server_path_id;
	}


	public void setServer_path_id(long server_path_id) {
		this.server_path_id = server_path_id;
	}


	public String getBase_path() {
		return base_path;
	}

	public void setBase_path(String base_path) {
		this.base_path = base_path;
	}

	public String getBase_folder() {
		return base_folder;
	}

	public void setBase_folder(String base_folder) {
		this.base_folder = base_folder;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getFinger_print() {
		return finger_print;
	}

	public void setFinger_print(String finger_print) {
		this.finger_print = finger_print;
	}

	public String getProfile_photo() {
		return profile_photo;
	}

	public void setProfile_photo(String profile_photo) {
		this.profile_photo = profile_photo;
	}
	public String getAccident_audio() {
		return accident_audio;
	}

	public void setAccident_audio(String accident_audio) {
		this.accident_audio = accident_audio;
	}

	public String getAccident_image() {
		return accident_image;
	}

	public void setAccident_image(String accident_image) {
		this.accident_image = accident_image;
	}

	public String getPassenger_profile_photo() {
		return passenger_profile_photo;
	}

	public void setPassenger_profile_photo(String passenger_profile_photo) {
		this.passenger_profile_photo = passenger_profile_photo;
	}

	public String getIbeacon_uuid() {
		return ibeacon_uuid;
	}

	public void setIbeacon_uuid(String ibeacon_uuid) {
		this.ibeacon_uuid = ibeacon_uuid;
	}
	

}
