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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table(name="u2_0xs_tbl_mst_accident") // U2 -> UTOO TBL-> TABLE MST -> MASTER
@Entity
@JsonInclude(Include.NON_NULL)
public class accident 
{
	@Id
	@GeneratedValue
	@Column(name="pk_clm_accident_id",columnDefinition="bigint(15) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column
	private long accident_id;
	
	@Column(name="clm_accident_photo_filename",columnDefinition="varchar(50) default null")
	private String accident_photo_filename;
	
	@Column(name="clm_accident_note",columnDefinition="varchar(255) default null")
	private String accident_note;
	
	@Column(name="clm_latitude",columnDefinition="float(13,8)")
	private double latitude;
	
	@Column(name="clm_longitude",columnDefinition="float(13,8)")
	private double longitude;

	@Column(name="clm_accident_voice_note_filename",columnDefinition="varchar(50) default null")
	private String voice_note_filename;
	
	@Column(name="clm_accident_complaint_id",columnDefinition="varchar(50) default null")
	private String complaint_id;

	@Column(name="clm_created_on",columnDefinition="timestamp default now()")
	private Date created_on;
	
	@Column(name="clm_updated_on",columnDefinition="timestamp null default null",nullable=true)
	private Date updated_on;
	
	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_driver_id",name="fk_clm_driver_id")
	private drivers odrivers;
	
	@Transient
	private String created_sON;
	
	
	public accident() {
	}

	public long getAccident_id() {
		return accident_id;
	}

	public void setAccident_id(long accident_id) {
		this.accident_id = accident_id;
	}

	public String getAccident_photo_filename() {
		return accident_photo_filename;
	}

	public void setAccident_photo_filename(String accident_photo_filename) {
		this.accident_photo_filename = accident_photo_filename;
	}

	public String getAccident_note() {
		return accident_note;
	}

	public void setAccident_note(String accident_note) {
		this.accident_note = accident_note;
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

	public String getVoice_note_filename() {
		return voice_note_filename;
	}

	public void setVoice_note_filename(String voice_note_filename) {
		this.voice_note_filename = voice_note_filename;
	}

	public String getComplaint_id() {
		return complaint_id;
	}

	public void setComplaint_id(String complaint_id) {
		this.complaint_id = complaint_id;
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

	public drivers getOdrivers() {
		return odrivers;
	}

	public void setOdrivers(drivers odrivers) {
		this.odrivers = odrivers;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public String getCreated_sON() {
		return created_sON;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setCreated_sON(String created_sON) {
		this.created_sON = created_sON;
	}

	

}
