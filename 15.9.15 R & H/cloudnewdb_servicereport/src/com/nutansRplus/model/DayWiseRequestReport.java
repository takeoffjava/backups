package com.nutansRplus.model;


public class DayWiseRequestReport 
{
private String date;
private int Water;
private int Service;
private int GoodService;
private int Bill;

public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public int getWater() {
	return Water;
}
public void setWater(int water) {
	Water = water;
}
public int getService() {
	return Service;
}
public void setService(int service) {
	Service = service;
}
public int getGoodService() {
	return GoodService;
}
public void setGoodService(int goodService) {
	GoodService = goodService;
}
public int getBill() {
	return Bill;
}
public void setBill(int bill) {
	Bill = bill;
}

}
