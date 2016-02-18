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

@Table(name="u2_0xs_tbl_mst_location") // U2 -> UTOO TBL-> TABLE MST -> MASTER
@Entity
@JsonInclude(Include.NON_NULL)
public class location {
	@Id
	@GeneratedValue
	@Column(name="pk_clm_location_id",columnDefinition="bigint(15) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column
	private long location_id;
	
	@Column(name="clm_north_latitude",columnDefinition="float(13,8)")
	private double north_latitude;
	
	@Column(name="clm_south_latitude",columnDefinition="float(13,8)")
	private double south_latitude;
	
	@Column(name="clm_east_longitude",columnDefinition="float(13,8)")
	private double east_longitude;
	
	@Column(name="clm_west_longitude",columnDefinition="float(13,8)")
	private double west_longitude;
	
	@Column(name="clm_location_name",columnDefinition="varchar(70) default null")
	private String location_name;

	@Column(name="clm_channel",columnDefinition="varchar(30) default null")
	private String channel;
	
	@Column(name="clm_created_on",columnDefinition="timestamp default now()")
	private Date created_on;
	
	@Column(name="clm_updated_on",columnDefinition="timestamp null default null",nullable=true)
	private Date updated_on;

	public long getLocation_id() {
		return location_id;
	}

	public void setLocation_id(long location_id) {
		this.location_id = location_id;
	}

	public String getLocation_name() {
		return location_name;
	}

	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public double getNorth_latitude() {
		return north_latitude;
	}

	public void setNorth_latitude(double north_latitude) {
		this.north_latitude = north_latitude;
	}

	public double getSouth_latitude() {
		return south_latitude;
	}

	public void setSouth_latitude(double south_latitude) {
		this.south_latitude = south_latitude;
	}

	public double getEast_longitude() {
		return east_longitude;
	}

	public void setEast_longitude(double east_longitude) {
		this.east_longitude = east_longitude;
	}

	public double getWest_longitude() {
		return west_longitude;
	}

	public void setWest_longitude(double west_longitude) {
		this.west_longitude = west_longitude;
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
}
