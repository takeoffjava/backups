package com.nutansRplus.model;

public class RestaurantMaster 
{
private int Restaturant_ID;
private String Restaturant_Name;
private String Restaturant_Address;
private String Location_Name;
private String Restaturant_Contact_No1;
private String Restaturant_Contact_No2;
private String Username;
private String Password;
private boolean isdeleted;
private String databasename;
private String dbusername;
private String dbpassword;
private String hostaddress;
private String port;
public RestaurantMaster(){}
public int getRestaturant_ID() {
	return Restaturant_ID;
}
public void setRestaturant_ID(int restaturant_ID) {
	Restaturant_ID = restaturant_ID;
}
public String getRestaturant_Name() {
	return Restaturant_Name;
}
public void setRestaturant_Name(String restaturant_Name) {
	Restaturant_Name = restaturant_Name;
}
public String getRestaturant_Address() {
	return Restaturant_Address;
}
public void setRestaturant_Address(String restaturant_Address) {
	Restaturant_Address = restaturant_Address;
}
public String getLocation_Name() {
	return Location_Name;
}
public void setLocation_Name(String location_Name) {
	Location_Name = location_Name;
}
public String getRestaturant_Contact_No1() {
	return Restaturant_Contact_No1;
}
public void setRestaturant_Contact_No1(String restaturant_Contact_No1) {
	Restaturant_Contact_No1 = restaturant_Contact_No1;
}
public String getRestaturant_Contact_No2() {
	return Restaturant_Contact_No2;
}
public void setRestaturant_Contact_No2(String restaturant_Contact_No2) {
	Restaturant_Contact_No2 = restaturant_Contact_No2;
}
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
public boolean isIsdeleted() {
	return isdeleted;
}
public void setIsdeleted(boolean isdeleted) {
	this.isdeleted = isdeleted;
}
public String getDatabasename() {
	return databasename;
}
public void setDatabasename(String databasename) {
	this.databasename = databasename;
}
public String getDbusername() {
	return dbusername;
}
public void setDbusername(String dbusername) {
	this.dbusername = dbusername;
}
public String getDbpassword() {
	return dbpassword;
}
public void setDbpassword(String dbpassword) {
	this.dbpassword = dbpassword;
}
public String getHostaddress() {
	return hostaddress;
}
public void setHostaddress(String hostaddress) {
	this.hostaddress = hostaddress;
}
public String getPort() {
	return port;
}
public void setPort(String port) {
	this.port = port;
}

}
