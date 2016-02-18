package org.strobs.utoo.web.services.eClasses;


import java.util.Date;

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
/*
 * 
 *  Base Fare : 80 / First 4 KM
 *  After 4 KM : Rs 10/ Km
 *  Waiting Time : Rs. 1 /mins
 *  Total Fare = BaseFare+(kms after 4kms * 10)+(Duration Traveled * Waiting Time)
 *  Peak Hours
 *  
 * */
@Table(name="u2_0xs_tbl_mst_tariff") // U2 -> UTOO TBL-> TABLE LUP -> LOOKUP
@Entity
@JsonInclude(Include.NON_NULL)
public class tariff  
{
	@Id
	@GeneratedValue
	@Column(name="pk_clm_tariff_id",columnDefinition="smallint(3) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column\
	private int tariff_id;
	
	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_car_model_id",name="fk_clm_car_model_id")
	private car_models carmodel_id;
	
	@Column(name="clm_min_charge",columnDefinition="float(13,2) default 0")
	private float min_charge;
	
	@Column(name="clm_base_min_kms",columnDefinition="float(13,8) default 0")
	private float min_kms;
	
	@Column(name="clm_after_min_kms",columnDefinition="float(13,8) default 0")
    private float after_minkms;
    
    @Column(name="clm_ride_charge_per_min",columnDefinition="float(13,8) default 0")
    private float waitingchrg_per_min;

    @Column(name="clm_base_fare",columnDefinition="float(13,8) default 0")
    private float flat_amount;
    
    @Column(name="clm_effective_from",columnDefinition="timestamp null default null")
    private Date effective_from;

    @Column(name="clm_effective_to",columnDefinition="timestamp null default null")
    private Date effective_to;
    @Transient
    private String effective_Sfrom;
    @Transient
    private String effective_Sto;
    @Transient
    private int indexno;
    
    public tariff() {}

    public int getTariff_id() {
		return tariff_id;
	}

	public void setTariff_id(int tariff_id) {
		this.tariff_id = tariff_id;
	}

	public car_models getCarmodel_id() {
		return carmodel_id;
	}

	public void setCarmodel_id(car_models carmodel_id) {
		this.carmodel_id = carmodel_id;
	}

	public float getMin_charge() {
		return min_charge;
	}

	public void setMin_charge(float min_charge) {
		this.min_charge = min_charge;
	}

	
	public void setMin_kms(int min_kms) {
		this.min_kms = min_kms;
	}

	public float getAfter_minkms() {
		return after_minkms;
	}

	public void setAfter_minkms(float after_minkms) {
		this.after_minkms = after_minkms;
	}

	public float getWaitingchrg_per_min() {
		return waitingchrg_per_min;
	}

	public void setWaitingchrg_per_min(float waitingchrg_per_min) {
		this.waitingchrg_per_min = waitingchrg_per_min;
	}

	public float getFlat_amount() {
		return flat_amount;
	}

	public void setFlat_amount(float flat_amount) {
		this.flat_amount = flat_amount;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getEffective_from() {
		return effective_from;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setEffective_from(Date effective_from) {
		this.effective_from = effective_from;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getEffective_to() {
		return effective_to;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setEffective_to(Date effective_to) {
		this.effective_to = effective_to;
	}

	public String getEffective_Sfrom() {
		return effective_Sfrom;
	}

	public void setEffective_Sfrom(String effective_Sfrom) {
		this.effective_Sfrom = effective_Sfrom;
	}

	public String getEffective_Sto() {
		return effective_Sto;
	}

	public void setEffective_Sto(String effective_Sto) {
		this.effective_Sto = effective_Sto;
	}

	public int getIndexno() {
		return indexno;
	}

	public void setIndexno(int indexno) {
		this.indexno = indexno;
	}    
	public float getMin_kms() {
		return min_kms;
	}

	public void setMin_kms(float min_kms) {
		this.min_kms = min_kms;
	}	

}
