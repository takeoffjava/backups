package org.strobs.utoo.web.services.classes;

import java.util.List;

import org.strobs.utoo.web.services.eClasses.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class bookings {

	private List<booking> book_list;
	public bookings() {
	}
	public List<booking> getBook_list() {
		return book_list;
	}
	public void setBook_list(List<booking> book_list) {
		this.book_list = book_list;
	}
}
