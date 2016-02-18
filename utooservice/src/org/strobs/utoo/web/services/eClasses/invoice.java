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

@Table(name = "u2_0xs_tbl_mst_invoice")
// U2 -> UTOO TBL-> TABLE LUP -> LOOKUP
@Entity
@JsonInclude(Include.NON_NULL)
public class invoice {
	@Id
	@GeneratedValue
	@Column(name = "pk_clm_invoice_id", columnDefinition = "bigint(20) unsigned not null", nullable = false)
	private long invoice_id;

	@Column(name = "clm_invoice_no", columnDefinition = "varchar(30) default null", length = 30)
	private String invoice_no;

	@Column(name = "clm_distance", columnDefinition = "float(13,2) default 0")
	private float distance;
	
	@Column(name = "clm_total_mins", columnDefinition = "float(13,2) default 0.0", length = 30)
	private float total_mins;

	@Column(name = "clm_amount", columnDefinition = "float(13,2) default 0")
	private float amount;

	@Column(name = "clm_tax", columnDefinition = "float(13,2) default 0")
	private float tax;

	@Column(name = "clm_total", columnDefinition = "float(13,2) default 0")
	private float total;

	@Column(name="clm_created_on",columnDefinition="timestamp default now()")
	private Date created_on;
	
	@Column(name="clm_updated_on",columnDefinition="timestamp null default null",nullable=true)
	private Date updated_on;
	
	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_booking_id",name="fk_clm_booking_id")
	private booking obooking;//change to Booking ID 
	
	@OneToOne
	@JoinColumn(referencedColumnName = "pk_clm_tariff_id", name = "fk_clm_tariff_id")
	private tariff otariff;
	
	@Transient
	private int error_code;
	
	public invoice() {
	}
	
	@JsonIgnore
	public long getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(long invoice_id) {
		this.invoice_id = invoice_id;
	}

	public booking getObooking() {
		return obooking;
	}

	public void setObooking(booking obooking) {
		this.obooking = obooking;
	}

	
	public String getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getTax() {
		return tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
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

	public tariff getOtariff() {
		return otariff;
	}

	public void setOtariff(tariff otariff) {
		this.otariff = otariff;
	}

	public float getTotal_mins() {
		return total_mins;
	}

	public void setTotal_mins(float total_mins) {
		this.total_mins = total_mins;
	}
	@JsonIgnore
	public int getError_code() {
		return error_code;
	}

	public void setError_code(int error_code) {
		this.error_code = error_code;
	}


}
