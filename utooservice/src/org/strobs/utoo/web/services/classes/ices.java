package org.strobs.utoo.web.services.classes;

import java.util.List;

import org.strobs.utoo.web.services.eClasses.ice;

public class ices {

	private String passenger_access_id;
	private List<ice> ices_list;
	public ices() {
		// TODO Auto-generated constructor stub
	}
	
	public String getPassenger_access_id() {
		return passenger_access_id;
	}

	public void setPassenger_access_id(String passenger_access_id) {
		this.passenger_access_id = passenger_access_id;
	}

	public List<ice> getIces_list() {
		return ices_list;
	}
	public void setIces_list(List<ice> ices_list) {
		this.ices_list = ices_list;
	}
}
