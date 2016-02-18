package org.strobs.utoo.web.services.classes;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class rating 
{
	private int rating_points;
	private Date rated_on;
	private String booking_number;
	private String driver_access_id;
	private String passenger_access_id;
	
	public String getPassenger_access_id() {
		return passenger_access_id;
	}

	public void setPassenger_access_id(String passenger_access_id) {
		this.passenger_access_id = passenger_access_id;
	}

	public rating() {}

	public int getRating_points() {
		return rating_points;
	}

	public void setRating_points(int rating_points) {
		this.rating_points = rating_points;
	}

	public Date getRated_on() {
		return rated_on;
	}

	public void setRated_on(Date rated_on) {
		this.rated_on = rated_on;
	}

	public String getBooking_number() {
		return booking_number;
	}

	public void setBooking_number(String booking_number) {
		this.booking_number = booking_number;
	}

	public String getDriver_access_id() {
		return driver_access_id;
	}

	public void setDriver_access_id(String driver_access_id) {
		this.driver_access_id = driver_access_id;
	}
	
}
