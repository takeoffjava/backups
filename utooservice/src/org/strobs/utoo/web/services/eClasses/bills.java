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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table(name="u2_0xs_tbl_mst_bills") // U2 -> UTOO TBL-> TABLE MST -> MASTER
@Entity
@JsonInclude(Include.NON_NULL)
public class bills {

	@Id
	@GeneratedValue
	@Column(name = "pk_clm_bill_id", columnDefinition = "bigint(15) unsigned not null", nullable = false)
	private int bill_id;

	@Column(name = "clm_invoice_amount", columnDefinition = "float(13,2) default 0")
	private float invoice_amount;

	@Column(name = "clm_paid_amount", columnDefinition = "float(13,2) default 0")
	private float paid_amount;

	@Column(name="clm_is_cash_payment")
	private boolean is_cash_payment;
	
	@Column(name = "clm_remaining_amount", columnDefinition = "float(13,2) default 0")
	private float remaining_amount;
	
	@Column(name="clm_card_holder_name",columnDefinition="varchar(30) default null",length=30)
	private String card_holder_name;
	
	@Column(name="clm_card_number",columnDefinition="varchar(45) default null",length=45)
	private String car_number;
	
	@Column(name="clm_description",columnDefinition="text default null")
	private String description;
	
	@Column(name="clm_updated_on",columnDefinition="timestamp null default null",nullable=true)
	private Date updated_on;
	
	@Column(name = "clm_bill_number", columnDefinition = "varchar(30) default null", length = 30)
	private String bill_number;
	
	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_invoice_id",name="fk_clm_invoice_id")
	private invoice oinvoice;  
	
	public bills(){}

	public int getBill_id() {
		return bill_id;
	}

	public void setBill_id(int bill_id) {
		this.bill_id = bill_id;
	}

	public float getInvoice_amount() {
		return invoice_amount;
	}

	public void setInvoice_amount(float invoice_amount) {
		this.invoice_amount = invoice_amount;
	}

	public float getPaid_amount() {
		return paid_amount;
	}

	public void setPaid_amount(float paid_amount) {
		this.paid_amount = paid_amount;
	}

	public boolean isIs_cash_payment() {
		return is_cash_payment;
	}

	public void setIs_cash_payment(boolean is_cash_payment) {
		this.is_cash_payment = is_cash_payment;
	}

	public float getRemaining_amount() {
		return remaining_amount;
	}

	public void setRemaining_amount(float remaining_amount) {
		this.remaining_amount = remaining_amount;
	}

	public String getCard_holder_name() {
		return card_holder_name;
	}

	public void setCard_holder_name(String card_holder_name) {
		this.card_holder_name = card_holder_name;
	}

	public String getCar_number() {
		return car_number;
	}

	public void setCar_number(String car_number) {
		this.car_number = car_number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getUpdated_on() {
		return updated_on;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}

	public String getBill_number() {
		return bill_number;
	}

	public void setBill_number(String bill_number) {
		this.bill_number = bill_number;
	}

	public invoice getOinvoice() {
		return oinvoice;
	}

	public void setOinvoice(invoice oinvoice) {
		this.oinvoice = oinvoice;
	}
	
	
	
	
		
}
