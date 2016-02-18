package org.strobs.utoo.web.services.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "sub_json_class_list")

public class sub_json_class {

	private int sub_id;
	@JsonIgnore
	private String sub_name;
	
	public sub_json_class() {
		// TODO Auto-generated constructor stub
	}

	public int getSub_id() {
		return sub_id;
	}

	public void setSub_id(int sub_id) {
		this.sub_id = sub_id;
	}

	public String getSub_name() {
		return sub_name;
	}

	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
	}

	
}
