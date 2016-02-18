package com.nutansRplus.model;

public class AssertsCount 
{
private int UserCount;
private int TableCount;
private int DeviceCount;
private String StartMonth;
private String EndMonth;

private String Created_ON;
public int getUserCount() {
	return UserCount;
}
public void setUserCount(int userCount) {
	UserCount = userCount;
}
public int getTableCount() {
	return TableCount;
}
public void setTableCount(int tableCount) {
	TableCount = tableCount;
}
public int getDeviceCount() {
	return DeviceCount;
}
public void setDeviceCount(int deviceCount) {
	DeviceCount = deviceCount;
}
public String getStartMonth() {
	return StartMonth;
}
public void setStartMonth(String startMonth) {
	StartMonth = startMonth;
}
public String getEndMonth() {
	return EndMonth;
}
public void setEndMonth(String endMonth) {
	EndMonth = endMonth;
}

public String getCreated_ON() {
	return Created_ON;
}
public void setCreated_ON(String created_ON) {
	Created_ON = created_ON;
}
public AssertsCount(){}
}
