package org.strobs.utoo.web.services.eClasses;

public class excelQR {
	private String name;
    private long mobile;
    private String destination;
    public excelQR() {
    }
 
    /*public String toString() {
        return String.format("%s - %s - %s", name, mobile, destination);
    }*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
    
 
}
