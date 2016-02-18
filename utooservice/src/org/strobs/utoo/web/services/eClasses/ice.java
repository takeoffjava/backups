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

@Table(name="u2_0xs_tbl_mst_ice") // U2 -> UTOO TBL-> TABLE MST -> MASTER
@Entity
@JsonInclude(Include.NON_NULL)
public class ice 
{
	@Id
	@GeneratedValue
	@Column(name="pk_clm_ice_id",columnDefinition="bigint(10) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column
	private long ice_id;
	
	@Column(name="clm_name",columnDefinition="varchar(50) default null")
	private String name;
	
	@Column(name="clm_mobile",columnDefinition="bigint(10) unsigned not null default 0",nullable=false)
	private long mobile;
	
	@Column(name="clm_email",columnDefinition="varchar(255) default null")
	private String email;
	
	@Column(name="clm_is_primary",columnDefinition="bit(1) default 0")
	private boolean is_primary;
	
	@Column(name="clm_is_active",columnDefinition="bit(1) default 0")
	private boolean is_active;

	@Column(name="clm_created_on",columnDefinition="timestamp default now()")
	private Date created_on;
	
	@Column(name="clm_updated_on",columnDefinition="timestamp null default null",nullable=true)
	private Date updated_on;
	
	@Column(name="clm_is_deleted",columnDefinition="bit(1) default 0")
	private boolean is_deleted;
	
	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_passenger_id",name="fk_clm_passenger_id")
	private passenger opassenger;
	
	@Transient
	private String passenger_access_id;
	
	@Transient
	private long primary_mobile;
	
	@Transient
	private String booking_number;
	
	public ice() {
	}
	
	@JsonIgnore
	public long getIce_id() {
		return ice_id;
	}

	public void setIce_id(long ice_id) {
		this.ice_id = ice_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public boolean isIs_primary() {
		return is_primary;
	}

	public void setIs_primary(boolean is_primary) {
		this.is_primary = is_primary;
	}

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
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
	public boolean isIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}
	@JsonIgnore
	public passenger getOpassenger() {
		return opassenger;
	}

	public void setOpassenger(passenger opassenger) {
		this.opassenger = opassenger;
	}

	public String getPassenger_access_id() {
		return passenger_access_id;
	}

	public void setPassenger_access_id(String passenger_access_id) {
		this.passenger_access_id = passenger_access_id;
	}

	public long getPrimary_mobile() {
		return primary_mobile;
	}

	public void setPrimary_mobile(long primary_mobile) {
		this.primary_mobile = primary_mobile;
	}

	public String getBooking_number() {
		return booking_number;
	}

	public void setBooking_number(String booking_number) {
		this.booking_number = booking_number;
	}

}
