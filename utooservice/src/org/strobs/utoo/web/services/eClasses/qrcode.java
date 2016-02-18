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

import org.codehaus.jackson.annotate.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table(name="u2_0xs_tbl_mst_qrcode") // U2 -> UTOO TBL-> TABLE MST -> MASTER
@Entity
@JsonInclude(Include.NON_NULL)
public class qrcode 
{
	@Id
	@GeneratedValue
	@Column(name="pk_clm_qrcode_id",columnDefinition="bigint(15) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column
	private long qrcode_id;
	
	@Column(name="clm_qrcode_filename",columnDefinition="varchar(50) default null")
	private String qrcode_filename;
	
	@Column(name="clm_qrcode_unique_number",columnDefinition="varchar(10) default null")
	private String qrcode_unique_number;
	
	@Column(name="clm_updated_on",columnDefinition="timestamp default now()",nullable=false)
	private Date updated_on;
	
	@Column(name="clm_qr_status",columnDefinition="tinyint(1) default 0")
	private int qr_status;
	


	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_qrcode_user_id",name="fk_clm_qrcode_user_id")
	private qrcode_users oqrcode_user;
	
	@Transient
	private int count;
	
	public qrcode() {
	}
	
    @JsonIgnore
	public long getQrcode_id() {
		return qrcode_id;
	}

	public void setQrcode_id(long qrcode_id) {
		this.qrcode_id = qrcode_id;
	}

	public String getQrcode_filename() {
		return qrcode_filename;
	}

	public void setQrcode_filename(String qrcode_filename) {
		this.qrcode_filename = qrcode_filename;
	}

	public String getQrcode_unique_number() {
		return qrcode_unique_number;
	}

	public void setQrcode_unique_number(String qrcode_unique_number) {
		this.qrcode_unique_number = qrcode_unique_number;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getUpdated_on() {
		return updated_on;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}


	public qrcode_users getOqrcode_user() {
		return oqrcode_user;
	}

	public void setOqrcode_user(qrcode_users oqrcode_user) {
		this.oqrcode_user = oqrcode_user;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	public int getQr_status() {
		return qr_status;
	}

	public void setQr_status(int qr_status) {
		this.qr_status = qr_status;
	}	
}
