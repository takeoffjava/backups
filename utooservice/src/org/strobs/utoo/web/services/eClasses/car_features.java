package org.strobs.utoo.web.services.eClasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table(name="u2_0xs_tbl_lkp_car_features") // U2 -> UTOO TBL-> TABLE LUP -> LOOKUP
@Entity
@JsonInclude(Include.NON_NULL)
public class car_features 
{
	@Id
	@GeneratedValue
	@Column(name="pk_clm_car_feature_id",columnDefinition="smallint(3) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column
	private int cars_feature_ID;
	
	@Column(name="clm_photo_file_id",columnDefinition="varchar(100) default null")
	private String photo_file_ID;
	
	@Column(name="clm_color",columnDefinition="varchar(15) default null")
	private String color;

	@Column(name="clm_register_no",columnDefinition="varchar(50) default null")
	private String register_number;

	@Column(name="clm_vehicle_year",columnDefinition="int(4) unsigned default 0")
	private int vehicle_year;
	
	@Column(name="clm_seat_count",columnDefinition="tinyint(1) default 2")
	private int seat_count;
	
	@Column(name="clm_is_deleted",columnDefinition="bit(1) default 0")
	private boolean is_deleted;

	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_car_model_id",name="fk_clm_car_model_id")
	private car_models ocars_model;
	
	public car_features() {}
	
	public int getCars_feature_ID() {
		return cars_feature_ID;
	}

	public void setCars_feature_ID(int cars_feature_ID) {
		this.cars_feature_ID = cars_feature_ID;
	}

	public String getPhoto_file_ID() {
		return photo_file_ID;
	}

	public void setPhoto_file_ID(String photo_file_ID) {
		this.photo_file_ID = photo_file_ID;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getRegister_number() {
		return register_number;
	}

	public void setRegister_number(String register_number) {
		this.register_number = register_number;
	}

	public int getVehicle_year() {
		return vehicle_year;
	}

	public void setVehicle_year(int vehicle_year) {
		this.vehicle_year = vehicle_year;
	}

	public int getSeat_count() {
		return seat_count;
	}

	public void setSeat_count(int seat_count) {
		this.seat_count = seat_count;
	}

	public car_models getOcars_model() {
		return ocars_model;
	}

	public void setOcars_model(car_models ocars_model) {
		this.ocars_model = ocars_model;
	}

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}
}
