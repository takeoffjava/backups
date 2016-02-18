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

@Table(name="u2_0xs_tbl_mst_favourites") // U2 -> UTOO TBL-> TABLE MST -> MASTER
@Entity
@JsonInclude(Include.NON_NULL)
public class favourites {

	@Id
	@GeneratedValue
	@Column(name="pk_clm_favourite_id",columnDefinition="bigint(10) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column
	private long favourite_id;
	
	@Column(name="clm_latitude",columnDefinition="float(13,8)")
	private float latitude;
	
	@Column(name="clm_longitude",columnDefinition="float(13,8)")	
	private float longitude;
	
	@Column(name="clm_address",columnDefinition="varchar(255) default null",length=255)
	private String address;
	
	@Column(name="clm_created_on",columnDefinition="timestamp default now()")
	private Date created_on;
	
	@Column(name="clm_favourite_name",columnDefinition="varchar(50) default null",length=50)
	private String favourite_name;

	@Column(name="clm_updated_on",columnDefinition="timestamp null default null",nullable=true)
	private Date updated_on;
	
	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_passenger_id",name="fk_clm_passenger_id")
	private passenger opassenger;
	
	@Column(name="clm_is_removed",columnDefinition="bit(1) default 0")
	private boolean is_removed;

	
	public long getFavourite_id() {
		return favourite_id;
	}

	public void setFavourite_id(long favourite_id) {
		this.favourite_id = favourite_id;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
@JsonIgnore
	public passenger getOpassenger() {
		return opassenger;
	}

	public void setOpassenger(passenger opassenger) {
		this.opassenger = opassenger;
	}

	public boolean isIs_removed() {
		return is_removed;
	}

	public void setIs_removed(boolean is_removed) {
		this.is_removed = is_removed;
	}

	public String getFavourite_name() {
		return favourite_name;
	}

	public void setFavourite_name(String favourite_name) {
		this.favourite_name = favourite_name;
	}
	
}






	


