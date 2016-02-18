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

@Table(name="u2_0xs_tbl_mst_driver_miles") // U2 -> UTOO TBL-> TABLE MST -> MASTER
@Entity
@JsonInclude(Include.NON_NULL)
public class driver_miles 
{
	@Id
	@GeneratedValue
	@Column(name="pk_clm_driver_miles_id",columnDefinition="bigint(10) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column
	private int driver_miles_id;
	
	@Column(name="clm_target_miles",columnDefinition="int default 0")
	private int target_miles;
	
	@Column(name="clm_miles_achieved",columnDefinition=" float(13,8) default 0")
	private float miles_achieved;
	//
	@Column(name="clm_status",columnDefinition="bit(1) default 0")
	private boolean status; // 0 T
	
	@Column(name="clm_start_on",columnDefinition="timestamp default now()",length=50)
	private Date started_on; // DAT ---> Date And Time
	
	@Column(name="clm_ended_on",columnDefinition="timestamp null default null",length=50)
	private Date ended_on; // DAT ---> Date And Time
	
	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_driver_id",name="fk_clm_driver_id")
	private drivers odrivers;
	
	public driver_miles() {
	}
	
	
	public driver_miles(int target_miles, float miles_achieved, boolean status,
			Date started_on, Date ended_on, drivers odrivers) {
		super();
		this.target_miles = target_miles;
		this.miles_achieved = miles_achieved;
		this.status = status;
		this.started_on = started_on;
		this.ended_on = ended_on;
		this.odrivers = odrivers;
	}


	@JsonIgnore
	public int getDriver_miles_id() {
		return driver_miles_id;
	}

	public void setDriver_miles_id(int driver_miles_id) {
		this.driver_miles_id = driver_miles_id;
	}

	public int getTarget_miles() {
		return target_miles;
	}

	public void setTarget_miles(int target_miles) {
		this.target_miles = target_miles;
	}

	public float getMiles_achieved() {
		return miles_achieved;
	}

	public void setMiles_achieved(float miles_achieved) {
		this.miles_achieved = miles_achieved;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getStarted_on() {
		return started_on;
	}
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setStarted_on(Date started_on) {
		this.started_on = started_on;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getEnded_on() {
		return ended_on;
	}
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setEnded_on(Date ended_on) {
		this.ended_on = ended_on;
	}

	public drivers getOdrivers() {
		return odrivers;
	}

	public void setOdrivers(drivers odrivers) {
		this.odrivers = odrivers;
	}

	
}
