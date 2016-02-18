package org.strobs.utoo.web.services.eClasses;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table(name="u2_0xs_tbl_mst_driverapp_status") // U2 -> UTOO TBL-> TABLE MST -> MASTER
@Entity
@JsonInclude(Include.NON_NULL)
public class driverapp_status {
	@Id
	@GeneratedValue
	@Column(name="pk_clm_driverapp_status_id",columnDefinition="bigint(15) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column
	private long driverapp_status_id;
	
	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_driver_id",name="fk_clm_driver_id")
	private drivers odrivers;
	
	@Column(name="clm_created_on",columnDefinition="timestamp default now()")
	private Date created_on;
	
	@Column(name="clm_status",columnDefinition="tinyint(1) default 0")
	private int status; 
	
	@Column(name="clm_open_latitude",columnDefinition="float(13,8)")
	private double open_latitude;
	
	@Column(name="clm_open_longitude",columnDefinition="float(13,8)")
	private double open_longitude;

	public long getDriverapp_status_id() {
		return driverapp_status_id;
	}

	public void setDriverapp_status_id(long driverapp_status_id) {
		this.driverapp_status_id = driverapp_status_id;
	}

	public drivers getOdrivers() {
		return odrivers;
	}

	public void setOdrivers(drivers odrivers) {
		this.odrivers = odrivers;
	}

	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getOpen_latitude() {
		return open_latitude;
	}

	public void setOpen_latitude(double open_latitude) {
		this.open_latitude = open_latitude;
	}

	public double getOpen_longitude() {
		return open_longitude;
	}

	public void setOpen_longitude(double open_longitude) {
		this.open_longitude = open_longitude;
	}
	
}



	
