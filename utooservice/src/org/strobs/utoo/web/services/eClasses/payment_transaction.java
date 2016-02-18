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

@Table(name="u2_0xs_tbl_mst_payment")
@Entity
@JsonInclude(Include.NON_NULL)
public class payment_transaction 
{
	@Id
	@GeneratedValue 
	@Column(name="pk_clm_payment_id",columnDefinition="bigint(20) unsigned not null",nullable=false)
	private long payment_id;
	
	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_passenger_id",name="fk_clm_passenger_id")
	private passenger opassenger;
	
	@Column(name="clm_updated_on",columnDefinition="timestamp default now()")
	private Date updated_on;
	
	@Column(name="clm_payment_cash")
	private String payment_cash;
	
	@Column(name="clm_response_code")
	private String response_code;
	
	@Column(name="clm_response_message")
	private String response_message;
	
	@Column(name="clm_transactionid")
	private String transaction_id;

	public long getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(long payment_id) {
		this.payment_id = payment_id;
	}

	public passenger getOpassenger() {
		return opassenger;
	}

	public void setOpassenger(passenger opassenger) {
		this.opassenger = opassenger;
	}

	public String getPayment_cash() {
		return payment_cash;
	}

	public void setPayment_cash(String payment_cash) {
		this.payment_cash = payment_cash;
	}

	public String getResponse_code() {
		return response_code;
	}

	public void setResponse_code(String response_code) {
		this.response_code = response_code;
	}

	public String getResponse_message() {
		return response_message;
	}

	public void setResponse_message(String response_message) {
		this.response_message = response_message;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	
	
}
