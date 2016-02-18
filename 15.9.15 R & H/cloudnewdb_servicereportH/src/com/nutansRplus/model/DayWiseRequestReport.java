package com.nutansRplus.model;


public class DayWiseRequestReport 
{
private String date;
private int Sos;
private int Nurse;
private int Clean;

public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public int getSos() {
	return Sos;
}
public void setSos(int sos) {
	Sos = sos;
}
public int getNurse() {
	return Nurse;
}
public void setNurse(int nurse) {
	Nurse = nurse;
}
public int getClean() {
	return Clean;
}
public void setClean(int clean) {
	Clean = clean;
}



}
