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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


	@Table(name="u2_0xs_tbl_mst_users") // U2 -> UTOO TBL-> TABLE MST -> MASTER
	@Entity
	@JsonInclude(Include.NON_NULL)
	public class users 
	{
		@Id
		@GeneratedValue
		@Column(name="pk_clm_user_id",columnDefinition="bigint(10) unsigned not null",nullable=false) // PK -> Primary Key CLM -> Column
		private int user_id;
		
		@Column(name="clm_username",columnDefinition="varchar(50) default null")
		private String username;
		
		@Column(name="clm_gender",columnDefinition="bit(1) default 1")
		private boolean gender;
		
		@Column(name="clm_mobile",columnDefinition="bigint(10) unsigned not null default 0",nullable=false)
		private long mobile;
		
		@Column(name="clm_password",columnDefinition="varchar(100) default null")
		private String password;
		
		@OneToOne
		@JoinColumn(referencedColumnName="pk_clm_user_typeid",name="fk_clm_user_typeid",columnDefinition="bigint(10) unsigned not null")
		private usertype objuser_type;

		@Column(name="clm_access_id",columnDefinition="varchar(70) not null",length=70,nullable=false)
		private String access_id;

		@Column(name="clm_created_on",columnDefinition="timestamp default now()")
		private Date created_on;
		
		@Column(name="clm_updated_on",columnDefinition="timestamp null default null",nullable=true)
		private Date updated_on;
		
		@Column(name="clm_is_deleted",columnDefinition="tinyint(1) default 0")
		private boolean isDeleted;
		
		@Column(name="clm_superuser_otp",columnDefinition="smallint(4) default 0")
		private int superuser_otp;
		
		
		@Transient
		private String genderStr;
		
		@Transient
		private String adminUsertype;
		
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

		public users(){}

		@JsonIgnore
		public int getUser_id() {
			return user_id;
		}

		public void setUser_id(int user_id) {
			this.user_id = user_id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public boolean isGender() {
			return gender;
		}

		public void setGender(boolean gender) {
			this.gender = gender;
		}

		public long getMobile() {
			return mobile;
		}

		public void setMobile(long mobile) {
			this.mobile = mobile;
		}
		@JsonIgnore
		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		

		public String getAccess_id() {
			return access_id;
		}

		public void setAccess_id(String access_id) {
			this.access_id = access_id;
		}

		public usertype getObjuser_type() {
			return objuser_type;
		}

		public void setObjuser_type(usertype objuser_type) {
			this.objuser_type = objuser_type;
		}

		public String getGenderStr() {
			return genderStr;
		}

		public void setGenderStr(String genderStr) {
			this.genderStr = genderStr;
		}

		public boolean isDeleted() {
			return isDeleted;
		}

		public void setDeleted(boolean isDeleted) {
			this.isDeleted = isDeleted;
		}
		@JsonIgnore
		public int getSuperuser_otp() {
			return superuser_otp;
		}
		public void setSuperuser_otp(int superuser_otp) {
			this.superuser_otp = superuser_otp;
		}
		public String getAdminUsertype() {
			return adminUsertype;
		}
		public void setAdminUsertype(String adminUsertype) {
			this.adminUsertype = adminUsertype;
		}
	
		
	}
