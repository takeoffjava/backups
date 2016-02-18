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

@Table(name="u2_0xs_tbl_mst_passenger_otp") // U2 -> UTOO TBL-> TABLE LUP -> LOOKUP
@Entity
@JsonInclude(Include.NON_NULL)
public class passenger_otp 
{

	@Id
	@GeneratedValue
	@Column(name="pk_clm_passenger_otp_id",columnDefinition="bigint(10) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column
	private int passenger_otp_id;
	
	@Column(name="clm_otp",columnDefinition="int(5) not null")
	private int otp;
	
	@Column(name="clm_is_verify",columnDefinition="tinyint(1) default 0")
	private int is_Verify;
	
	@Column(name="clm_sent_on",columnDefinition="timestamp default now()")
	private Date sent_on;
	
	@Column(name="clm_verified_on",columnDefinition="timestamp null default null",nullable=true)
	private Date verified_on;
	
	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_passenger_id",name="fk_clm_passenger_id")
	private passenger opassenger;
	
	public passenger_otp(){}

	
	public passenger_otp(int otp) {
		super();
		this.otp = otp;
	}


	@JsonIgnore
	public int getPassenger_otp_id() {
		return passenger_otp_id;
	}


	public void setPassenger_otp_id(int passenger_otp_id) {
		this.passenger_otp_id = passenger_otp_id;
	}


	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public int getIs_Verify() {
		return is_Verify;
	}

	public void setIs_Verify(int is_Verify) {
		this.is_Verify = is_Verify;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getSent_on() {
		return sent_on;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setSent_on(Date sent_on) {
		this.sent_on = sent_on;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getVerified_on() {
		return verified_on;
	}
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setVerified_on(Date verified_on) {
		this.verified_on = verified_on;
	}

	public passenger getOpassenger() {
		return opassenger;
	}

	public void setOpassenger(passenger opassenger) {
		this.opassenger = opassenger;
	}
}
