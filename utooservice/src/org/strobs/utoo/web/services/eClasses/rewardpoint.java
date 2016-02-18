package org.strobs.utoo.web.services.eClasses;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table(name="u2_0xs_tbl_mst_rewardpoint") // U2 -> UTOO TBL-> TABLE MST -> MASTER
@Entity
@JsonInclude(Include.NON_NULL)
public class rewardpoint {
	@Id
	@GeneratedValue 
	@Column(name="pk_clm_reward_id",columnDefinition="bigint(20) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column
	private long reward_id;
	
	@Column(name="clm_reward_point",columnDefinition="int default 0",length=50)
	private int reward_point;
	
	@Column(name="clm_redeemed_point",columnDefinition="int default 0",length=50)
	private int redeemed_point;
	
	@Column(name="clm_last_redeem",columnDefinition="timestamp null default null",length=50)
	private Date last_redeem;
	
	@OneToOne
	@JoinColumn(referencedColumnName="pk_clm_passenger_id",name="fk_clm_passenger_id")
	private passenger opassenger;
	
	public rewardpoint(){}

	@JsonIgnore
	public long getReward_id() {
		return reward_id;
	}

	public void setReward_id(long reward_id) {
		this.reward_id = reward_id;
	}

	public int getReward_point() {
		return reward_point;
	}

	public void setReward_point(int reward_point) {
		this.reward_point = reward_point;
	}

	public int getRedeemed_point() {
		return redeemed_point;
	}

	public void setRedeemed_point(int redeemed_point) {
		this.redeemed_point = redeemed_point;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public Date getLast_redeem() {
		return last_redeem;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC")
	public void setLast_redeem(Date last_redeem) {
		this.last_redeem = last_redeem;
	}

	public passenger getOpassenger() {
		return opassenger;
	}

	public void setOpassenger(passenger opassenger) {
		this.opassenger = opassenger;
	}
}
