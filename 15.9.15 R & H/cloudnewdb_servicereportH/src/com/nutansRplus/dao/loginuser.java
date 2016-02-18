package com.nutansRplus.dao;

public class loginuser 
{
private String Username;
private String Password;
private String Mobilenumber;
private String dbserver;
private String dbuser;
private String dbpassword;
private int dbport;
private String dbname;
private String start_Date;
private String end_Date;
private String remote_type;
public String getUsername() {
	return Username;
}
public void setUsername(String username) {
	Username = username;
}
public String getPassword() {
	return Password;
}
public void setPassword(String password) {
	Password = password;
}
public String getMobilenumber() {
	return Mobilenumber;
}
public void setMobilenumber(String mobilenumber) {
	Mobilenumber = mobilenumber;
}
public String getDbserver() {
	return dbserver;
}
public void setDbserver(String dbserver) {
	this.dbserver = dbserver;
}
public String getDbuser() {
	return dbuser;
}
public void setDbuser(String dbuser) {
	this.dbuser = dbuser;
}
public String getDbpassword() {
	return dbpassword;
}
public void setDbpassword(String dbpassword) {
	this.dbpassword = dbpassword;
}
public int getDbport() {
	return dbport;
}
public void setDbport(int dbport) {
	this.dbport = dbport;
}
public String getDbname() {
	return dbname;
}
public void setDbname(String dbname) {
	this.dbname = dbname;
}
public String getStart_Date() {
	return start_Date;
}
public void setStart_Date(String start_Date) {
	this.start_Date = start_Date;
}
public String getEnd_Date() {
	return end_Date;
}
public void setEnd_Date(String end_Date) {
	this.end_Date = end_Date;
}
public String getRemote_type() {
	return remote_type;
}
public void setRemote_type(String remote_type) {
	this.remote_type = remote_type;
}

}
