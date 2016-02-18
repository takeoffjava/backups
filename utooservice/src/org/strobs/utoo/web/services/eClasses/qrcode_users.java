package org.strobs.utoo.web.services.eClasses;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table(name="u2_0xs_tbl_mst_qrcode_users") // U2 -> UTOO TBL-> TABLE MST -> MASTER
@Entity
@JsonInclude(Include.NON_NULL)
public class qrcode_users 
{
	@Id
	@GeneratedValue
	@Column(name="pk_clm_qrcode_user_id",columnDefinition="bigint(15) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column
	private long qrcode_user_id;
	
	@Column(name="clm_qrcode_username",columnDefinition="varchar(30)")
	private String qrcode_username;
	
	@Column(name="clm_qrcode_mobile_number",columnDefinition="bigint(10) default 0")
	private long qrcode_mobile_number;
	
	@Column(name = "clm_destination",columnDefinition="text")
	private String booked_destination;
	
	@Column(name="clm_is_used")
	private boolean is_used;
	
	@Column(name="clm_qrcode_qcr",columnDefinition="varchar(50)")
	private String qcr;
	
	@Column(name="clm_advance_amount",columnDefinition="float(13,2) default 0")
	private float advance_amount;
	
	@Column(name="clm_principle_amount",columnDefinition="float(13,2) default 0")
	private float principle_amount;
	
	@Column(name="clm_created_on",columnDefinition="timestamp default now()")
	private Date created_on;
	
	@Column(name="clm_updated_on",columnDefinition="timestamp null default null",nullable=true)
	private Date updated_on;	
	
	@Column(name="clm_message")
	private String message;
	
	@Column(name = "clm_from", columnDefinition = "timestamp null default null")
	private String From;
	
	@Column(name = "clm_to", columnDefinition = "timestamp null default null")
	private String To;
	
	@Column(name="clm_latitude",columnDefinition="float(13,8)")
	private double latitude;
	
	@Column(name="clm_longitude",columnDefinition="float(13,8)")
	private double longitude;
	
	public qrcode_users() 
	{}

	public long getQrcode_user_id() {
		return qrcode_user_id;
	}

	public void setQrcode_user_id(long qrcode_user_id) {
		this.qrcode_user_id = qrcode_user_id;
	}

	public String getQrcode_username() {
		return qrcode_username;
	}

	public void setQrcode_username(String qrcode_username) {
		this.qrcode_username = qrcode_username;
	}

	

	public long getQrcode_mobile_number() {
		return qrcode_mobile_number;
	}

	public void setQrcode_mobile_number(long qrcode_mobile_number) {
		this.qrcode_mobile_number = qrcode_mobile_number;
	}

	public String getBooked_destination() {
		return booked_destination;
	}

	public void setBooked_destination(String booked_destination) {
		this.booked_destination = booked_destination;
	}

	public boolean isIs_used() {
		return is_used;
	}

	public void setIs_used(boolean is_used) {
		this.is_used = is_used;
	}

	public String getQcr() {
		return qcr;
	}

	public void setQcr(String qcr) {
		this.qcr = qcr;
	}

	public float getAdvance_amount() {
		return advance_amount;
	}

	public void setAdvance_amount(float advance_amount) {
		this.advance_amount = advance_amount;
	}

	public float getPrinciple_amount() {
		return principle_amount;
	}

	public void setPrinciple_amount(float principle_amount) {
		this.principle_amount = principle_amount;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public String getFrom() {
		return From;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setFrom(String from) {
		From = from;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public String getTo() {
		return To;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setTo(String to) {
		To = to;
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

		
}
