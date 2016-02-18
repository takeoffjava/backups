package org.strobs.utoo.web.services.eClasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table(name="u2_0xs_tbl_mst_unbook_reason") // U2 -> UTOO TBL-> TABLE MST -> MASTER
@Entity
@JsonInclude(Include.NON_NULL)
public class unbook_reasons
{
	@Id
	@GeneratedValue
	@Column(name="pk_clm_reason_id",columnDefinition="bigint(15) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column
	private long reason_ID;
	
	@Column(name="clm_reason_type_name",columnDefinition="varchar(50) default null")
	private String reason_type_name;
	
	/*
	* Changed my Mind
	* Driver is late
	* Driver denied duty
	* Booked another cab
	 * */
	public unbook_reasons(){}
	public long getReason_ID() {
		return reason_ID;
	}
	public void setReason_ID(long reason_ID) {
		this.reason_ID = reason_ID;
	}
	public String getReason_type_name() {
		return reason_type_name;
	}
	public void setReason_type_name(String reason_type_name) {
		this.reason_type_name = reason_type_name;
	}

}
