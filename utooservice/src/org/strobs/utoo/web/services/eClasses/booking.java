package org.strobs.utoo.web.services.eClasses;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table(name="u2_0xs_tbl_mst_booking") // U2 -> UTOO TBL-> TABLE MST -> MASTER
@Entity
@JsonInclude(Include.NON_NULL)
public class booking {
	
	@Id
	@GeneratedValue 
	@Column(name="pk_clm_booking_id",columnDefinition="bigint(20) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column
	private long booking_id;
	
	@Column(name="clm_bto_name",columnDefinition="varchar(50) default null")
	private String bto_name; // BTO ----> Book to Other
	
	@Column(name="clm_bto_mobile",columnDefinition="bigint(10) unsigned not null default 0")
	private long bto_mobile;
	
	@Column(name="clm_booked_dat",columnDefinition="timestamp null default null")
	private Date booked_dat; // DAT ---> Date And Time

	@Column(name="clm_departure_dat",columnDefinition="timestamp null default null")
	private Date departure_dat; // DAT ---> Date And Time
	
	@Column(name="clm_departure_latitude",columnDefinition="float(13,8)")
	private double departure_latitude;
	
	@Column(name="clm_departure_longitude",columnDefinition="float(13,8)")
	private double departure_longitude;
	
	@Column(name="clm_actual_source_latitude",columnDefinition="float(13,8)")
	private double actual_source_latitude;
	
	@Column(name="clm_actual_source_longitude",columnDefinition="float(13,8)")
	private double actual_source_longitude;
	
	@Column(name="clm_actual_dest_latitude",columnDefinition="float(13,8)")
	private double actual_dest_latitude;
	
	@Column(name="clm_actual_dest_longitude",columnDefinition="float(13,8)")
	private double actual_dest_longitude;

	@Column(name = "clm_actual_source",columnDefinition="text")
	private String actual_source;

	@Column(name = "clm_actual_dest",columnDefinition="text")
	private String actual_dest;
	
	@Column(name="clm_reaching_dat",columnDefinition="timestamp null default null")
	private Date reaching_dat; // DAT ---> Date And Time
	
	@Column(name="clm_reaching_latitude",columnDefinition="float(13,8)")
	private double reaching_latitude;
	
	@Column(name="clm_reaching_longitude",columnDefinition="float(13,8)")
	private double reaching_longitude;

	@Column(name="clm_pbr_number",columnDefinition="varchar(6) not null")
	private String pbr_number; // Passenger Booking Record
	
	@Column(name="clm_status",columnDefinition="tinyint(1) default 0")
	private int status; 
	/*
	
		0 - Confirm Book
		1 - Guide  for meet the Customer - GUIDE
		2 - Ride with customer - Start Trip
		3 - Ride completed - End Trip
		4 - Cancel - Cancel Trip
		5 - Pending 
		
	*/ 
	
	@Column(name="clm_created_on",columnDefinition="timestamp default now()")
	private Date created_on;
	
	@Column(name="clm_updated_on",columnDefinition="timestamp null default null",nullable=true)
	private Date updated_on;
	
	@JoinColumn(name="clm_booking_type",columnDefinition="tinyint(1) default 0")
	private int booking_type;
	
/*
*	1  Single
*	2  BTO
*	3  Ride Later
*	4  Long Trip      
*	5  Quick  
 * */
	
	@Column(name="clm_group_book_key",columnDefinition="varchar(10)")
	private String group_book_key; // Passenger Booking Record
	
	@Column(name="clm_booking_number",columnDefinition="varchar(70)",length=70,nullable=false)
	private String booking_number;
	
	@Column(name = "clm_rating_points", columnDefinition = "tinyint(1) not null")
	private int rating_points;

	@Column(name = "clm_rating_from_driver", columnDefinition = "tinyint(1) not null")
	private int rating_from_driver;
	

	@Column(name = "clm_rated_on", columnDefinition = "timestamp null default null")
	private Date rated_on;
	
	@Column(name = "clm_reason_id", columnDefinition = "bigint(15) default 0")
	private long reason_ID;
	
	@Column(name = "clm_unbook_description",columnDefinition="varchar(255)")
	private String unbook_notes;
	
	@Column(name = "clm_source",columnDefinition="text")
	private String booked_source;
	
	@Column(name = "clm_destination",columnDefinition="text")
	private String booked_destination;
	
	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_passenger_id",name="fk_clm_passenger_id")
	private passenger opassenger;
	
	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_driver_id",name="fk_clm_driver_id",columnDefinition="bigint(10) unsigned null",nullable=true)
	private drivers odrivers;
	
	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_car_model_id",name="fk_clm_car_model_id",columnDefinition="tinyint(1) unsigned null",nullable=true)
	private car_models ocars_model;
	
	@Column(name="clm_promo_code",columnDefinition="varchar(10) default null")
	private String promo_code;
	
	@Transient
	private double passenger_latitude;
	
	@Transient
	private String admin_booked;
	
	@Transient
	private String admin_reached;
	
	@Transient
	private double passenger_longitude;
	
	@Transient
	private String mobile_type;
	
	@Transient
	private String start_date;
	
	@Transient
	private String source;
	
	@Transient
	private String destination;
	
	@Transient
	private String end_date;

	@Transient
	private String qrcode_unique_number;
	

	@Transient
	private List<drivers> driver_list;
	
	public booking() {}

	
	public long getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(long booking_id) {
		this.booking_id = booking_id;
	}

	public String getBto_name() {
		return bto_name;
	}

	public void setBto_name(String bto_name) {
		this.bto_name = bto_name;
	}

	public long getBto_mobile() {
		return bto_mobile;
	}

	public void setBto_mobile(long bto_mobile) {
		this.bto_mobile = bto_mobile;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getDeparture_dat() {
		return departure_dat;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setDeparture_dat(Date departure_dat) {
		this.departure_dat = departure_dat;
	}
	
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getBooked_dat() {
		return booked_dat;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setBooked_dat(Date booked_dat) {
		this.booked_dat = booked_dat;
	}


	public double getDeparture_latitude() {
		return departure_latitude;
	}

	public void setDeparture_latitude(double departure_latitude) {
		this.departure_latitude = departure_latitude;
	}

	public double getDeparture_longitude() {
		return departure_longitude;
	}

	public void setDeparture_longitude(double departure_longitude) {
		this.departure_longitude = departure_longitude;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getReaching_dat() {
		return reaching_dat;
	}
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setReaching_dat(Date reaching_dat) {
		this.reaching_dat = reaching_dat;
	}

	public double getReaching_latitude() {
		return reaching_latitude;
	}

	public void setReaching_latitude(double reaching_latitude) {
		this.reaching_latitude = reaching_latitude;
	}

	public double getReaching_longitude() {
		return reaching_longitude;
	}

	public void setReaching_longitude(double reaching_longitude) {
		this.reaching_longitude = reaching_longitude;
	}

	public String getPbr_number() {
		return pbr_number;
	}

	public void setPbr_number(String pbr_number) {
		this.pbr_number = pbr_number;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBooking_type() {
		return booking_type;
	}

	public void setBooking_type(int booking_type) {
		this.booking_type = booking_type;
	}

	public passenger getOpassenger() {
		return opassenger;
	}

	public void setOpassenger(passenger opassenger) {
		this.opassenger = opassenger;
	}

	public drivers getOdrivers() {
		return odrivers;
	}

	public void setOdrivers(drivers odrivers) {
		this.odrivers = odrivers;
	}
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getCreated_on() {
		return created_on;
	}
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getUpdated_on() {
		return updated_on;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}

	public String getGroup_book_key() {
		return group_book_key;
	}

	public void setGroup_book_key(String group_book_key) {
		this.group_book_key = group_book_key;
	}

	public int getRating_points() {
		return rating_points;
	}

	public void setRating_points(int rating_points) {
		this.rating_points = rating_points;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getRated_on() {
		return rated_on;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setRated_on(Date rated_on) {
		this.rated_on = rated_on;
	}

	public String getBooking_number() {
		return booking_number;
	}

	public void setBooking_number(String booking_number) {
		this.booking_number = booking_number;
	}

	public double getPassenger_latitude() {
		return passenger_latitude;
	}

	public void setPassenger_latitude(double passenger_latitude) {
		this.passenger_latitude = passenger_latitude;
	}

	public double getPassenger_longitude() {
		return passenger_longitude;
	}

	public void setPassenger_longitude(double passenger_longitude) {
		this.passenger_longitude = passenger_longitude;
	}

	public String getMobile_type() {
		return mobile_type;
	}

	public void setMobile_type(String mobile_type) {
		this.mobile_type = mobile_type;
	}


	public String getUnbook_notes() {
		return unbook_notes;
	}

	public void setUnbook_notes(String unbook_notes) {
		this.unbook_notes = unbook_notes;
	}

	public long getReason_ID() {
		return reason_ID;
	}

	public void setReason_ID(long reason_ID) {
		this.reason_ID = reason_ID;
	}	
	public String getStart_date() {
		return start_date;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public String getEnd_date() {
		return end_date;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public int getRating_from_driver() {
		return rating_from_driver;
	}

	public void setRating_from_driver(int rating_from_driver) {
		this.rating_from_driver = rating_from_driver;
	}

	public String getBooked_source() {
		return booked_source;
	}

	public void setBooked_source(String booked_source) {
		this.booked_source = booked_source;
	}

	public String getBooked_destination() {
		return booked_destination;
	}

	public void setBooked_destination(String booked_destination) {
		this.booked_destination = booked_destination;
	}

	public car_models getOcars_model() {
		return ocars_model;
	}

	public void setOcars_model(car_models ocars_model) {
		this.ocars_model = ocars_model;
	}

	public String getPromo_code() {
		return promo_code;
	}

	public void setPromo_code(String promo_code) {
		this.promo_code = promo_code;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}


	public String getQrcode_unique_number() {
		return qrcode_unique_number;
	}


	public void setQrcode_unique_number(String qrcode_unique_number) {
		this.qrcode_unique_number = qrcode_unique_number;
	}
	
	public List<drivers> getDriver_list() {
		return driver_list;
	}


	public void setDriver_list(List<drivers> driver_list) {
		this.driver_list = driver_list;
	}


	public String getAdmin_booked() {
		return admin_booked;
	}


	public void setAdmin_booked(String admin_booked) {
		this.admin_booked = admin_booked;
	}


	public String getAdmin_reached() {
		return admin_reached;
	}


	public void setAdmin_reached(String admin_reached) {
		this.admin_reached = admin_reached;
	}


	public double getActual_source_latitude() {
		return actual_source_latitude;
	}


	public void setActual_source_latitude(double actual_source_latitude) {
		this.actual_source_latitude = actual_source_latitude;
	}


	public double getActual_source_longitude() {
		return actual_source_longitude;
	}


	public void setActual_source_longitude(double actual_source_longitude) {
		this.actual_source_longitude = actual_source_longitude;
	}


	public double getActual_dest_latitude() {
		return actual_dest_latitude;
	}


	public void setActual_dest_latitude(double actual_dest_latitude) {
		this.actual_dest_latitude = actual_dest_latitude;
	}


	public double getActual_dest_longitude() {
		return actual_dest_longitude;
	}


	public void setActual_dest_longitude(double actual_dest_longitude) {
		this.actual_dest_longitude = actual_dest_longitude;
	}


	public String getActual_source() {
		return actual_source;
	}


	public void setActual_source(String actual_source) {
		this.actual_source = actual_source;
	}


	public String getActual_dest() {
		return actual_dest;
	}


	public void setActual_dest(String actual_dest) {
		this.actual_dest = actual_dest;
	}
	
	
}
