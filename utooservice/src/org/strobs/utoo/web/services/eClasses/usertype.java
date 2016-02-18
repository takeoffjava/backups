package org.strobs.utoo.web.services.eClasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table(name="u2_0xs_tbl_mst_usertypes") 
@Entity
@JsonInclude(Include.NON_NULL)
public class usertype 
{
	@Id
	@GeneratedValue
	@Column(name="pk_clm_user_typeid",columnDefinition="bigint(10) unsigned not null",nullable=false)
	private int user_typeid;
	
	@Column
	private String usertype_name;

	public int getUser_typeid() {
		return user_typeid;
	}

	public void setUser_typeid(int user_typeid) {
		this.user_typeid = user_typeid;
	}

	public String getUsertype_name() {
		return usertype_name;
	}

	public void setUsertype_name(String usertype_name) {
		this.usertype_name = usertype_name;
	}
	
	
}
