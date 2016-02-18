package org.strobs.utoo.web.services.eClasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table(name="u2_0xs_tbl_mst_blocked_reasons") // U2 -> UTOO TBL-> TABLE MST -> MASTER
@Entity
@JsonInclude(Include.NON_NULL)
public class blocked_reasons
{
	@Id
	@GeneratedValue
	@Column(name="pk_clm_blocked_reason_id",columnDefinition="tinyint(2) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column
	private long blocked_reason_id;
	
	@Column(name="clm_reason_type_name",columnDefinition="varchar(50) default null")
	private String reason_type_name;

	public blocked_reasons(){}
	
	public long getBlocked_reason_id() {
		return blocked_reason_id;
	}

	public void setBlocked_reason_id(long blocked_reason_id) {
		this.blocked_reason_id = blocked_reason_id;
	}

	public String getReason_type_name() {
		return reason_type_name;
	}

	public void setReason_type_name(String reason_type_name) {
		this.reason_type_name = reason_type_name;
	}
	
	/*
	* Changed my Mind
	* Driver is late
	* Driver denied duty
	* Booked another cab
	 * */
	
	
}
