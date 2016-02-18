package org.strobs.utoo.web.services.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({ "name", "id" ,"MIN","machine_code"})
public class json_class {
    @JsonProperty("ids") 
    private int id;
    
    @JsonProperty("theNames") 
    private String name;
	
    private String machine_code;
	
    private String MIN; // Machine identify number
	
	@JsonManagedReference
	private sub_json_class Osub_json_class;
	
	public json_class() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JsonIgnore
	public String getMachine_code() {
		return machine_code;
	}
	public void setMachine_code(String machine_code) {
		this.machine_code = machine_code;
	}
	public String getMIN() {
		return MIN;
	}
	public void setMIN(String mIN) {
		MIN = mIN;
	}
	public sub_json_class getOsub_json_class() {
		return Osub_json_class;
	}
	public void setOsub_json_class(sub_json_class osub_json_class) {
		Osub_json_class = osub_json_class;
	}
	
	

}
