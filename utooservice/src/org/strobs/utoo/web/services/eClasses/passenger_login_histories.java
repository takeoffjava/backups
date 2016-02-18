package org.strobs.utoo.web.services.eClasses;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table(name="u2_0xs_tbl_mst_passenger_login_histories") // U2 -> UTOO TBL-> TABLE 
@Entity
@JsonInclude(Include.NON_NULL)
public class passenger_login_histories {

	@Id
	@GeneratedValue
	@Column(name="pk_clm_login_history_id",columnDefinition="bigint(20) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column
	private long login_history_id;
	
	@Column(name="clm_latitude",columnDefinition="float(13,8)")
	private double latitude;
	
	@Column(name="clm_longitude",columnDefinition="float(13,8)")
	private double longitude;
	
	@Column(name="clm_login_date_time",columnDefinition="timestamp default now()")
	private Date login_time;
	
	@Column(name="clm_logout_date_time",columnDefinition="timestamp null default null")
	private Date logout_time;
	
	@Column(name="clm_mobile_type",columnDefinition="varchar(7) null")
	private String device_type_name;
	
	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_passenger_id",name="fk_clm_passenger_id")
	private passenger opassenger;
		
	public passenger_login_histories(){}
	@JsonIgnore
	public long getLogin_history_id() {
		return login_history_id;
	}

	public void setLogin_history_id(long login_history_id) {
		this.login_history_id = login_history_id;
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

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getLogin_time() {
		return login_time;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getLogout_time() {
		return logout_time;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setLogout_time(Date logout_time) {
		this.logout_time = logout_time;
	}

	public passenger getOpassenger() {
		return opassenger;
	}

	public void setOpassenger(passenger opassenger) {
		this.opassenger = opassenger;
	}
	public String getDevice_type_name() {
		return device_type_name;
	}
	public void setDevice_type_name(String device_type_name) {
		this.device_type_name = device_type_name;
	}
	
	

		
}
