package org.strobs.utoo.web.services.base;

import org.strobs.utoo.web.services.dao.baseDAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class common 
{
	public static <T> T convertJSON2EntityClass(String json, Class<T> type) 
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.serializeNulls();
		Gson gson = gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		try{
			return (T) gson.fromJson(json, type);
		} catch (Exception expJSONToClassConvertor) {
			baseDAO.getInstance().logAnError("common", baseDAO.getInstance().stackTraceToString(expJSONToClassConvertor));
			return null;
		}
	}

}
