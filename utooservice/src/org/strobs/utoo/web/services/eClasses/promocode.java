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

@Table(name="u2_0xs_tbl_mst_promocode")
@Entity
@JsonInclude(Include.NON_NULL)
public class promocode {
@Id
@GeneratedValue
@Column(name="pk_clm_promocode_id",columnDefinition="bigint(20) unsigned not null",nullable=false)
private long promo_id;

@Column(name="clm_promocode",length=10)
private String promocode;

@Column(name="clm_isexpired")
private boolean is_expired;

@Column(name="clm_isused")
private boolean is_used;

@Column(name="clm_expireddate",columnDefinition="timestamp null default null")
private Date expired_date;

@Column(name="clm_useddate",columnDefinition="timestamp null default null")
private Date used_date;

@OneToOne
@JoinColumn(referencedColumnName="pk_clm_passenger_id",name="fk_clm_passenger_id")
private passenger opassenger;
@Transient
private String expired_Sdate;
@Transient
private String used_Sdate;

public promocode(){}
public long getPromo_id() {
	return promo_id;
}
public void setPromo_id(long promo_id) {
	this.promo_id = promo_id;
}
public String getPromocode() {
	return promocode;
}
public void setPromocode(String promocode) {
	this.promocode = promocode;
}
public boolean isIs_expired() {
	return is_expired;
}
public void setIs_expired(boolean is_expired) {
	this.is_expired = is_expired;
}
public boolean isIs_used() {
	return is_used;
}
public void setIs_used(boolean is_used) {
	this.is_used = is_used;
}

@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
public Date getExpired_date() {
	return expired_date;
}

@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
public void setExpired_date(Date expired_date) {
	this.expired_date = expired_date;
}
@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
public Date getUsed_date() {
	return used_date;
}
@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
public void setUsed_date(Date used_date) {
	this.used_date = used_date;
}
public passenger getOpassenger() {
	return opassenger;
}
public void setOpassenger(passenger opassenger) {
	this.opassenger = opassenger;
}
@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
public String getExpired_Sdate() {
	return expired_Sdate;
}
@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
public void setExpired_Sdate(String expired_Sdate) {
	this.expired_Sdate = expired_Sdate;
}
@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
public String getUsed_Sdate() {
	return used_Sdate;
}
@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
public void setUsed_Sdate(String used_Sdate) {
	this.used_Sdate = used_Sdate;
}
 
 

}
