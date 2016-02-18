package org.strobs.utoo.web.services.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class lookup {

	private boolean is_response_codes;
	private boolean is_server_details;
	private boolean is_car_model_details;
	private boolean is_car_features_details;


	private boolean is_tariff_details;
	private boolean is_need_driver_list;
	private boolean is_need_unbook_reason_list;
	private boolean is_need_location_list;
	private boolean is_need_blocked_list;
	private boolean is_all;
    private String access_id;
 	
	public lookup(){}

	@JsonIgnore
	public boolean isIs_response_codes() {
		return is_response_codes;
	}

	public void setIs_response_codes(boolean is_response_codes) {
		this.is_response_codes = is_response_codes;
	}
	@JsonIgnore
	public boolean isIs_all() {
		return is_all;
	}

	public void setIs_all(boolean is_all) {
		this.is_all = is_all;
	}
	@JsonIgnore
	public boolean isIs_server_details() {
		return is_server_details;
	}

	public void setIs_server_details(boolean is_server_details) {
		this.is_server_details = is_server_details;
	}
	@JsonIgnore
	public boolean isIs_car_model_details() {
		return is_car_model_details;
	}

	public void setIs_car_model_details(boolean is_car_model_details) {
		this.is_car_model_details = is_car_model_details;
	}
	@JsonIgnore
	public boolean isIs_car_features_details() {
		return is_car_features_details;
	}

	public void setIs_car_features_details(boolean is_car_features_details) {
		this.is_car_features_details = is_car_features_details;
	}
	@JsonIgnore
	public boolean isIs_tariff_details() {
		return is_tariff_details;
	}

	public void setIs_tariff_details(boolean is_tariff_details) {
		this.is_tariff_details = is_tariff_details;
	}

	public boolean isIs_need_driver_list() {
		return is_need_driver_list;
	}

	public void setIs_need_driver_list(boolean is_need_driver_list) {
		this.is_need_driver_list = is_need_driver_list;
	}

	public boolean isIs_need_unbook_reason_list() {
		return is_need_unbook_reason_list;
	}

	public void setIs_need_unbook_reason_list(boolean is_need_unbook_reason_list) {
		this.is_need_unbook_reason_list = is_need_unbook_reason_list;
	}

	public boolean isIs_need_location_list() {
		return is_need_location_list;
	}

	public void setIs_need_location_list(boolean is_need_location_list) {
		this.is_need_location_list = is_need_location_list;
	}

	public boolean isIs_need_blocked_list() {
		return is_need_blocked_list;
	}

	public void setIs_need_blocked_list(boolean is_need_blocked_list) {
		this.is_need_blocked_list = is_need_blocked_list;
	}
	public String getAccess_id() {
		return access_id;
	}

	public void setAccess_id(String access_id) {
		this.access_id = access_id;
	}
}
