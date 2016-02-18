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

@Table(name="u2_0xs_tbl_mst_blocked_drivers") // U2 -> UTOO TBL-> TABLE MST -> MASTER
@Entity
@JsonInclude(Include.NON_NULL)
public class blocked_drivers {

	@Id
	@GeneratedValue
	@Column(name = "pk_clm_block_id", columnDefinition = "bigint(10) unsigned not null", nullable = false)
	private int block_id;

	@Column(name = "clm_blocked_on", columnDefinition = "timestamp default now()", nullable = true)
	private Date blocked_on;
	
	@Column(name="clm_block_reason",columnDefinition="varchar(100) default null")
	private String Block_Reason;
	
	@Column(name="clm_status",columnDefinition="tinyint(1) default 1")
	private int status;
	
	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_booking_id",name="fk_clm_booking_id")
	private booking obooking; 
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getBlocked_on() {
		return blocked_on;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setBlocked_on(Date blocked_on) {
		this.blocked_on = blocked_on;
	}

	public int getBlock_id() {
		return block_id;
	}

	public void setBlock_id(int block_id) {
		this.block_id = block_id;
	}

	public String getBlock_Reason() {
		return Block_Reason;
	}

	public void setBlock_Reason(String block_Reason) {
		Block_Reason = block_Reason;
	}

	public booking getObooking() {
		return obooking;
	}

	public void setObooking(booking obooking) {
		this.obooking = obooking;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
