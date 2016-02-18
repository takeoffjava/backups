package org.strobs.utoo.web.services.eClasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table(name="u2_0xs_tbl_lkp_response_codes") // U2 -> UTOO TBL-> TABLE MST -> MASTER
@Entity
@JsonInclude(Include.NON_NULL)
public class response_codes {

	@Id
	@GeneratedValue
	@Column(name="pk_clm_response_id",columnDefinition="tinyint(1) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column
	private int response_id;
	
	@Column(name="clm_code",columnDefinition="smallint(4) not null")
	private int response_code;
	
	@Column(name="clm_response_description",columnDefinition="varchar(50) default null")
	private String response_description;
	
	public response_codes() {	}

	@JsonIgnore
	public int getResponse_id() {
		return response_id;
	}

	public void setResponse_id(int response_id) {
		this.response_id = response_id;
	}

	public int getResponse_code() {
		return response_code;
	}

	public void setResponse_code(int response_code) {
		this.response_code = response_code;
	}

	public String getResponse_description() {
		return response_description;
	}

	public void setResponse_description(String response_description) {
		this.response_description = response_description;
	}
}
