package com.nutansRplus.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nutansRplus.dao.JDBCConnectionAPI;
import com.nutansRplus.dao.loginuser;
import com.nutansRplus.model.Appdata;
import com.nutansRplus.model.Appstatus;
import com.nutansRplus.model.AutocancelReports;
import com.nutansRplus.model.Averageresponse;
import com.nutansRplus.model.Dashboardcount;
import com.nutansRplus.model.DayWiseReport;
import com.nutansRplus.model.DayWiseRequestReport;
import com.nutansRplus.model.MonthwiseReport;
import com.nutansRplus.model.MostlyusedtableReport;
import com.nutansRplus.model.Overallrequestcount;
import com.nutansRplus.model.Productivehours;
import com.nutansRplus.model.TableAnalyticsReport;
import com.nutansRplus.model.Tablewisereqcount;
import com.nutansRplus.model.UsersAnalytics;

@Controller
@RequestMapping(value = "/service/nutansr")
public class HomeController extends Utilities
{
	private static ResourceBundle rsbAR = ResourceBundle.getBundle("ApplicationResource");
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> login(@RequestBody String login_user) 
	{
		loginuser Ologin = new loginuser();
		Ologin = super.convertJSONToEntityClass(login_user,loginuser.class);
		Map<String, Object> jdbc_response = new HashMap<String, Object>();
		jdbc_response = super.ResponseData("{'username':'" + Ologin.getUsername()+ "','password':'" + Ologin.getPassword() + "','mobile':" + Ologin.getMobilenumber() + "}", rsbAR.getString("loginpath"), "POST","");
		if (jdbc_response.get("status").toString().equals("0")) {
			jdbc_response.put("status", 0);
			return jdbc_response;
		}
		return jdbc_response;
	}

	@RequestMapping(value = "/getDataFordashboard", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getAllData(@RequestBody String DB_Details) 
	{
		loginuser objlogin = new loginuser();
		objlogin = super.convertJSONToEntityClass(DB_Details,loginuser.class);
		
		Map<String, Object> analytical_data = new HashMap<String, Object>();
		
		List<Productivehours>Productive=new ArrayList<Productivehours>();
		List<Overallrequestcount>Overall=new ArrayList<Overallrequestcount>();
		List<Dashboardcount>Dashboardcount=new ArrayList<Dashboardcount>();
		/*JDBCConnectionAPI _JDBCConnectionAPI = new JDBCConnectionAPI(
				Security.decrypt(objlogin.getDbserver()).trim(),
				Security.decrypt(objlogin.getDbname()).trim(), 
				Security.decrypt(objlogin.getDbuser()),
				Security.decrypt(objlogin.getDbpassword()),
				objlogin.getDbport());*/
		JDBCConnectionAPI _JDBCConnectionAPI = new JDBCConnectionAPI(
				"192.168.101.190",
				"apollo", 
				"nutansr",
				"nutansr",
				5050);
		
		Productive=_JDBCConnectionAPI.getProductiveReport();
		Dashboardcount=_JDBCConnectionAPI.getDashboardcount();
		Overall=_JDBCConnectionAPI.getoverallReport();
		
		analytical_data.put("Dashboard", Dashboardcount);
		analytical_data.put("productivehoursData", Productive);
		analytical_data.put("overallrequestData", Overall);		
		return analytical_data;
	}
	@RequestMapping(value = "/getDataForReports", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getAllData2(@RequestBody String DB_Details) {
		loginuser objlogin = new loginuser();
		objlogin = super.convertJSONToEntityClass(DB_Details,
				loginuser.class);
		
		Map<String, Object> analytical_data = new HashMap<String, Object>();
		/*JDBCConnectionAPI _JDBCConnectionAPI = new JDBCConnectionAPI(Security.decrypt(objlogin.getDbserver()),Security.decrypt(objlogin.getDbname()).trim(), Security.decrypt(objlogin.getDbuser()),
				Security.decrypt(objlogin.getDbpassword()), objlogin.getDbport());*/
		JDBCConnectionAPI _JDBCConnectionAPI = new JDBCConnectionAPI(
				"192.168.101.190",
				"apollo", 
				"nutansr",
				"nutansr",
				5050);
	
		List<DayWiseReport>DaywisePerformance=new ArrayList<DayWiseReport>();
		List<DayWiseRequestReport>DaywiseRequestPerformance=new ArrayList<DayWiseRequestReport>();
		List<AutocancelReports>Autocancel=new ArrayList<AutocancelReports>();
		List<Tablewisereqcount>Tablewise=new ArrayList<Tablewisereqcount>();
		List<MostlyusedtableReport>Mostlyusedtable=new ArrayList<MostlyusedtableReport>();
		List<MonthwiseReport>Monthwise=new ArrayList<MonthwiseReport>();
				
		//DaywisePerformance=objjdbc.getDayWiseReport();
		DaywiseRequestPerformance=_JDBCConnectionAPI.getDayWiseRequestReport();	
		Autocancel=_JDBCConnectionAPI.getautocancelReport();
		Tablewise=_JDBCConnectionAPI.getbedwiseReport();
		Mostlyusedtable=_JDBCConnectionAPI.getmostlyusedtableReport();
		Monthwise=_JDBCConnectionAPI.monthwisereport();
		//objjdbc.connection_close();
				
		//analytical_data.put("Daywisedata", DaywisePerformance);
		analytical_data.put("Daywiserequestdata", DaywiseRequestPerformance);
		analytical_data.put("tablewiserequestData", Tablewise);
		analytical_data.put("mostusedtableData", Mostlyusedtable);
		analytical_data.put("monthwiserequestData", Monthwise);
		analytical_data.put("autocanceltablesData", Autocancel);
		
		return analytical_data;
	}
	@RequestMapping(value = "/getDataForanalytics", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getAllDataanalytics(@RequestBody String DB_Details) {
		loginuser objlogin = new loginuser();
		objlogin = super.convertJSONToEntityClass(DB_Details,
				loginuser.class);
		
		Map<String, Object> analytical_data = new HashMap<String, Object>();
		/*JDBCConnectionAPI _JDBCConnectionAPI = new JDBCConnectionAPI(Security.decrypt(objlogin.getDbserver()),Security.decrypt(objlogin.getDbname()).trim(), Security.decrypt(objlogin.getDbuser()),
				Security.decrypt(objlogin.getDbpassword()), objlogin.getDbport());*/
		JDBCConnectionAPI _JDBCConnectionAPI = new JDBCConnectionAPI(
				"192.168.101.190",
				"apollo", 
				"nutansr",
				"nutansr",
				5050);
	
		List<DayWiseReport>DaywisePerformance=new ArrayList<DayWiseReport>();
		List<DayWiseRequestReport>DaywiseRequestPerformance=new ArrayList<DayWiseRequestReport>();
		List<Productivehours>Productive=new ArrayList<Productivehours>();
		List<Averageresponse>Sosaverage=new ArrayList<Averageresponse>();
		List<Averageresponse>Nurseaverage=new ArrayList<Averageresponse>();
		List<Averageresponse>Cleanaverage=new ArrayList<Averageresponse>();
		List<Overallrequestcount>Overall=new ArrayList<Overallrequestcount>();
		List<AutocancelReports>Autocancel=new ArrayList<AutocancelReports>();
		List<Tablewisereqcount>Tablewise=new ArrayList<Tablewisereqcount>();
		List<MostlyusedtableReport>Mostlyusedtable=new ArrayList<MostlyusedtableReport>();
		List<UsersAnalytics>Topwaiter=new ArrayList<UsersAnalytics>();
		List<UsersAnalytics>Lastwaiter=new ArrayList<UsersAnalytics>();
		List<TableAnalyticsReport>Costlytable=new ArrayList<TableAnalyticsReport>();
		List<TableAnalyticsReport>Lasttable=new ArrayList<TableAnalyticsReport>();
		List<MonthwiseReport>Monthwise=new ArrayList<MonthwiseReport>();
		List<Dashboardcount>Dashboardcount=new ArrayList<Dashboardcount>();
		
		Productive=_JDBCConnectionAPI.getProductiveReport();
		Dashboardcount=_JDBCConnectionAPI.getDashboardcount();
		DaywisePerformance=_JDBCConnectionAPI.getDayWiseReport();
		DaywiseRequestPerformance=_JDBCConnectionAPI.getDayWiseRequestReport();
		Sosaverage=_JDBCConnectionAPI.getsosaverageReport();
		Nurseaverage=_JDBCConnectionAPI.getnurseaverageReport();
		Cleanaverage=_JDBCConnectionAPI.getcleanaverageReport();
		Overall=_JDBCConnectionAPI.getoverallReport();
		Autocancel=_JDBCConnectionAPI.getautocancelReport();
		Tablewise=_JDBCConnectionAPI.getbedwiseReport();
		Mostlyusedtable=_JDBCConnectionAPI.getmostlyusedtableReport();
		Topwaiter=_JDBCConnectionAPI.topuser();
		Lastwaiter=_JDBCConnectionAPI.lastuser();
		Costlytable=_JDBCConnectionAPI.costlytable();
		Lasttable=_JDBCConnectionAPI.lasttable();
		Monthwise=_JDBCConnectionAPI.monthwisereport();
		
		analytical_data.put("Dashboard", Dashboardcount);
		analytical_data.put("Daywisedata", DaywisePerformance);
		analytical_data.put("Daywiserequestdata", DaywiseRequestPerformance);
		analytical_data.put("productivehoursData", Productive);
		analytical_data.put("Sosaverage", Sosaverage);
		analytical_data.put("Nurseaverage", Nurseaverage);
		analytical_data.put("Cleanaverage", Cleanaverage);
		analytical_data.put("overallrequestData", Overall);
		analytical_data.put("autocanceltablesData", Autocancel);
		analytical_data.put("tablewiserequestData", Tablewise);
		analytical_data.put("mostusedtableData", Mostlyusedtable);
		analytical_data.put("topwaiterData", Topwaiter);
		analytical_data.put("lastwaiterData", Lastwaiter);
		analytical_data.put("costlytableData", Costlytable);
		analytical_data.put("lowusetableData", Lasttable);
		analytical_data.put("monthwiserequestData", Monthwise);
		
		return analytical_data;
	}
	@RequestMapping(value = "/getDataForReportsdate", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getdateData(@RequestBody String DB_Details) {
		loginuser objlogin = new loginuser();
		objlogin = super.convertJSONToEntityClass(DB_Details,loginuser.class);
		
		Map<String, Object> analytical_data = new HashMap<String, Object>();
		/*String Server,String Database,String user,String passwd,int Port*/
		/*JDBCConnectionAPI _JDBCConnectionAPI = new JDBCConnectionAPI();*/
		/*JDBCConnectionAPI _JDBCConnectionAPI = new JDBCConnectionAPI(Security.decrypt(objlogin.getDbserver()),
				Security.decrypt(objlogin.getDbname()).trim(),
				Security.decrypt(objlogin.getDbuser()),
				Security.decrypt(objlogin.getDbpassword()), objlogin.getDbport());*/
		JDBCConnectionAPI _JDBCConnectionAPI = new JDBCConnectionAPI(
				"192.168.101.190",
				"apollo", 
				"nutansr",
				"nutansr",
				5050);
		
		String Start_Date=objlogin.getStart_Date();
		String End_Date=objlogin.getEnd_Date();
		
		List<DayWiseRequestReport>DaywiseRequestPerformance=new ArrayList<DayWiseRequestReport>();
		List<AutocancelReports>Autocancel=new ArrayList<AutocancelReports>();
		List<Tablewisereqcount>Tablewise=new ArrayList<Tablewisereqcount>();
		List<MostlyusedtableReport>Mostlyusedtable=new ArrayList<MostlyusedtableReport>();
		List<MonthwiseReport>Monthwise=new ArrayList<MonthwiseReport>();
				
		DaywiseRequestPerformance=_JDBCConnectionAPI.date_getDayWiseRequestReport(Start_Date,End_Date);
		Autocancel=_JDBCConnectionAPI.date_getautocancelReport(Start_Date,End_Date);
		Tablewise=_JDBCConnectionAPI.date_gettablewiseReport(Start_Date,End_Date);
		Mostlyusedtable=_JDBCConnectionAPI.date_getmostlyusedtableReport(Start_Date,End_Date);
		Monthwise=_JDBCConnectionAPI.date_monthwisereport(Start_Date,End_Date);
		
		analytical_data.put("Daywiserequestdata", DaywiseRequestPerformance);
		analytical_data.put("autocanceltablesData", Autocancel);
		analytical_data.put("tablewiserequestData", Tablewise);
		analytical_data.put("mostusedtableData", Mostlyusedtable);
		analytical_data.put("monthwiserequestData", Monthwise);
		
		return analytical_data;
	}
	@RequestMapping(value = "/getappdata", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getappdata(@RequestBody String DB_Details) {
		Map<String, Object> response = new HashMap<String, Object>();
		loginuser objlogin = new loginuser();
		objlogin = super.convertJSONToEntityClass(DB_Details,
				loginuser.class);
		JDBCConnectionAPI _JDBCConnectionAPI = new JDBCConnectionAPI(Security.decrypt(objlogin.getDbserver()),Security.decrypt(objlogin.getDbname()).trim(), Security.decrypt(objlogin.getDbuser()),
				Security.decrypt(objlogin.getDbpassword()), objlogin.getDbport());
		List<Appdata> appdatas = _JDBCConnectionAPI.getappdata();
		if (appdatas.isEmpty()) {
			response.put("status", 0);
		} else {
			response.put("status", 1);
			response.put("appdata", appdatas);
		}
		return response;
	}
	@RequestMapping(value = "/appstatus", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> appstatus(@RequestBody String DB_Details) {
		Map<String, Object> response = new HashMap<String, Object>();
		
		loginuser objlogin = new loginuser();
		objlogin = super.convertJSONToEntityClass(DB_Details,
				loginuser.class);
		JDBCConnectionAPI _JDBCConnectionAPI = new JDBCConnectionAPI(Security.decrypt(objlogin.getDbserver()),Security.decrypt(objlogin.getDbname()).trim(), Security.decrypt(objlogin.getDbuser()),
				Security.decrypt(objlogin.getDbpassword()), objlogin.getDbport());
		List<Appstatus> appstatus = _JDBCConnectionAPI.appstatus();
		if (appstatus.isEmpty() || appstatus == null) {
			response.put("response", 0);
		} else {
			response.put("response", appstatus);
		}
		return response;
	}
}
