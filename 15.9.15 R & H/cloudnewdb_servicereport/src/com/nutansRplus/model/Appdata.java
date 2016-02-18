package com.nutansRplus.model;

public class Appdata {
private int User_ID;
private String username;
private String Table_Ref_No;
private String Device_ID;
private String Requested_ON;
public String Start_Date;
public String End_Date;
private String Cancelled_ON;
private String Auto_Cancelled_ON;
public Appdata(){}
public int getUser_ID() {
	return User_ID;
}
public void setUser_ID(int user_ID) {
	User_ID = user_ID;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getTable_Ref_No() {
	return Table_Ref_No;
}
public void setTable_Ref_No(String table_Ref_No) {
	Table_Ref_No = table_Ref_No;
}
public String getDevice_ID() {
	return Device_ID;
}
public void setDevice_ID(String device_ID) {
	Device_ID = device_ID;
}
public String getRequested_ON() {
	return Requested_ON;
}
public void setRequested_ON(String requested_ON) {
	Requested_ON = requested_ON;
}
public String getStart_Date() {
	return Start_Date;
}
public void setStart_Date(String start_Date) {
	Start_Date = start_Date;
}
public String getEnd_Date() {
	return End_Date;
}
public void setEnd_Date(String end_Date) {
	End_Date = end_Date;
}
public String getCancelled_ON() {
	return Cancelled_ON;
}
public void setCancelled_ON(String cancelled_ON) {
	Cancelled_ON = cancelled_ON;
}
public String getAuto_Cancelled_ON() {
	return Auto_Cancelled_ON;
}
public void setAuto_Cancelled_ON(String auto_Cancelled_ON) {
	Auto_Cancelled_ON = auto_Cancelled_ON;
}

}
