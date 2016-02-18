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

@Entity
@Table(name="u2_0xs_tbl_mst_lost_found")
@JsonInclude(Include.NON_NULL)
public class lost_found {
	@Id
	@GeneratedValue
	@Column(name = "pk_clm_lf_id", columnDefinition = "bigint(10) unsigned not null", nullable = false)
	private int lostfound_id;
	
	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_booking_id",name="fk_clm_booking_id")
	private booking obooking; 
	
	@Column(length=50)
	private String lostobject;
	
	@Column(length=100)
	private String description;

	public int getLostfound_id() {
		return lostfound_id;
	}

	public void setLostfound_id(int lostfound_id) {
		this.lostfound_id = lostfound_id;
	}

	public booking getObooking() {
		return obooking;
	}

	public void setObooking(booking obooking) {
		this.obooking = obooking;
	}

	public String getLostobject() {
		return lostobject;
	}

	public void setLostobject(String lostobject) {
		this.lostobject = lostobject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public lost_found(){}
	
}
