package org.strobs.utoo.web.services.eClasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table(name="u2_0xs_tbl_lkp_car_models") // U2 -> UTOO TBL-> TABLE LUP -> LOOKUP
@Entity
@JsonInclude(Include.NON_NULL)
public class car_models 
{
	@Id
	@GeneratedValue
	@Column(name="pk_clm_car_model_id",columnDefinition="tinyint(1) unsigned null",nullable=true) // PK -> Primary Key CLM -> Column
	private int car_model_id;
	
	@Column(name="clm_car_model_name",columnDefinition="varchar(30) default null",length=30)
	private String car_model_name;
	
	@Column(name="clm_car_model_description",columnDefinition="varchar(100) default null")
	private String car_model_description;
	
	@Column(name="clm_is_deleted",columnDefinition="bit(1) default 0")
	private boolean is_deleted;

	public car_models() {}
	
	public int getCar_model_id() {
		return car_model_id;
	}

	public void setCar_model_id(int car_model_id) {
		this.car_model_id = car_model_id;
	}

	public String getCar_model_name() {
		return car_model_name;
	}

	public void setCar_model_name(String car_model_name) {
		this.car_model_name = car_model_name;
	}

	public String getCar_model_description() {
		return car_model_description;
	}

	public void setCar_model_description(String car_model_description) {
		this.car_model_description = car_model_description;
	}

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}
}
