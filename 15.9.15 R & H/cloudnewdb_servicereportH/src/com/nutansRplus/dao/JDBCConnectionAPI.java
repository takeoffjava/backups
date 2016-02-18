package com.nutansRplus.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

public class JDBCConnectionAPI
{
	
   Connection _CONNECTION = null;
   Statement _STATEMENT = null;
   String _SERVER="";
   String _DATABASE="";
   String _USER="";
   String _PASSWORD="";
   int _PORT=0;
   public JDBCConnectionAPI()
   {
	   _SERVER="";
	   _DATABASE="";
	   _USER="";
	   _PASSWORD="";
	   _PORT=0;
   }
   public JDBCConnectionAPI(String Server,String Database,String user,String passwd,int Port)
   {
	   _SERVER=Server;
	   _DATABASE=Database;
	   _USER=user;
	   _PASSWORD=passwd;
	   _PORT=Port;
   }
   public boolean OpenConnection()
   {
		try
		{
 			  Class.forName("com.mysql.jdbc.Driver");
		      _CONNECTION = DriverManager.getConnection("jdbc:mysql://"+_SERVER+":"+_PORT +"/"+_DATABASE, _USER, _PASSWORD);
		      _STATEMENT = _CONNECTION.createStatement();
		      return true;
		}
		catch(Exception ExpOpenConnection)
		{
			//General.writeLog(ExpOpenConnection.toString(), "");
		}
		return false;
   }

   public boolean CloseConnection(ResultSet _RESULT_SET)
   {
	   try
	   {
		   if(_RESULT_SET!=null)
		   {
			   if(!_RESULT_SET.isClosed())
				   _RESULT_SET.close();
		   }
		   if(_STATEMENT!=null)
		   {
			   if(!_STATEMENT.isClosed())
				   _STATEMENT.close();
		   }
		   if(_CONNECTION!=null)
		   {
			   if(!_CONNECTION.isClosed())
				   _CONNECTION.close();
		   }
	   }
	   catch(Exception ExpCloseConnection)
	   {
		  // General.writeLog(ExpCloseConnection.toString(), "");
	   }
	   _STATEMENT=null;
	   _CONNECTION=null;
	   _RESULT_SET=null;
	   return true;
   }
   public boolean QueryExecutor(String QueryCommand)
   {
	   try 
	   {
		   if(!OpenConnection()) return false;
			_STATEMENT.executeUpdate(QueryCommand);
			CloseConnection(null);
			return true;
	   }
	   catch(Exception ExpQueryExecutor)
	   {
		   //General.writeLog(ExpQueryExecutor.toString(), "");
		   if(ExpQueryExecutor.toString().toLowerCase().contains("duplicate"))
		   {
			   CloseConnection(null);
			   return true;
		   }
	   }
	   CloseConnection(null);
	   return false;
   }
   public String QueryExecuteBatch(List<String> queries)
   {
	   String InsertedIDs="";
	   long DupID=0;
	   try 
	   {
		   if(!OpenConnection()) return null;
		   
		   for(int nIdx=0;nIdx<queries.size();nIdx++)
			   _STATEMENT.addBatch(queries.get(nIdx));
		   
		   _STATEMENT.executeBatch();
		   
	   }
	   catch(Exception ExpQueryExecutor)
	   {
		   String DuplicateID[]=ExpQueryExecutor.getMessage().split("'");
		   if(DuplicateID.length>1)
		   {
			   if(isNumeric(DuplicateID[1]))
				   DupID=Long.parseLong(DuplicateID[1]);
		   }
		  // General.writeLog(ExpQueryExecutor.toString(), "");
	   }
	   try
	   {
		   ResultSet rsLastInsertedIDs = _STATEMENT.getGeneratedKeys();
		   while (rsLastInsertedIDs.next()) 
			   InsertedIDs+=rsLastInsertedIDs.getInt(1)+",";
		   rsLastInsertedIDs.close();
	   }catch(Exception ExpQueryExecutor){}
	   CloseConnection(null);
	   return InsertedIDs + DupID;
   }
   public boolean isNumeric(String s) 
   {  
	    return s.matches("[-+]?\\d*\\.?\\d+");  
   }  
   public List<Productivehours> getProductiveReport() 
   {
	   if(!OpenConnection()) return null;
		Productivehours OProductivehours = null;
		List<Productivehours> LstProductivehours = null;
		ResultSet _RESULT_SET=null;
		try {

			String query = "select tbl_hourly.hours,if(rescount is NULL,0,rescount) as rescount from (select hour(requested_ON) as hours,count(*) as rescount "+ 
							" from device_request_updater group by hour(requested_ON) order by requested_on desc) as productivehours right join tbl_hourly on "+ 
							" tbl_hourly.hours=productivehours.hours;";
			_RESULT_SET = _STATEMENT.executeQuery(query);
			LstProductivehours = new ArrayList<Productivehours>();
			while (_RESULT_SET.next()) 
			{
				OProductivehours = new Productivehours();
				OProductivehours.setHours( _RESULT_SET.getInt("hours"));
				OProductivehours.setRescount(_RESULT_SET.getInt("rescount"));
				LstProductivehours.add(OProductivehours);
			}
			
		} catch (Exception ExpProductiveReport) {
			
		}
		CloseConnection(_RESULT_SET);
		return LstProductivehours;
	}
   public List<DayWiseReport> getDayWiseReport() 
   {
	    if(!OpenConnection()) return null;
	    List<DayWiseReport> lstDayWiseReport=null;
	    DayWiseReport ODayWiseReport = null;
	    ResultSet _RESULT_SET=null;
		try 
		{
			 _RESULT_SET = _STATEMENT.executeQuery("select Date(device_request_updater.requested_ON) as 'date',count(*) as requestcount from device_request_updater "+ 
					 						" group by Date(device_request_updater.requested_ON) order by requested_on desc limit 10;");
			lstDayWiseReport = new ArrayList<DayWiseReport>();
			while (_RESULT_SET.next()) 
			{
				ODayWiseReport = new DayWiseReport();
				ODayWiseReport.setDate(_RESULT_SET.getTimestamp("date"));
				ODayWiseReport.setRequestcount(_RESULT_SET.getInt("requestcount"));
				lstDayWiseReport.add(ODayWiseReport);
			}
		} catch (Exception ExpDayWiseReport) {
			ODayWiseReport = null;
		}
		CloseConnection(_RESULT_SET);
		return lstDayWiseReport;
	}
	
	public List<DayWiseRequestReport> getDayWiseRequestReport() {
		 if(!OpenConnection()) return null;
		DayWiseRequestReport objdaywiserequest = null;
		List<DayWiseRequestReport> daywise_response = new ArrayList<DayWiseRequestReport>();
		String query="";
		 ResultSet _RESULT_SET=null;
		try {
				query = "select date(requested_ON) as date,sum(if(device_request_type_ID=118,1,0))as Sos , "+
						" sum(if(device_request_type_ID=218,1,0))as Nurse, "+ 
						" sum(if(device_request_type_ID=318,1,0))as  Clean from device_request_updater "+  
						" group by date(requested_ON) order by requested_ON desc limit 10;";
			 _RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				objdaywiserequest = new DayWiseRequestReport();
				String requested_ON = _RESULT_SET.getString("date");
				int sos = _RESULT_SET.getInt("Sos");
				int nurse = _RESULT_SET.getInt("Nurse");
				int clean = _RESULT_SET.getInt("Clean");
				
				objdaywiserequest.setDate(requested_ON);
				objdaywiserequest.setSos(sos);
				objdaywiserequest.setNurse(nurse);
				objdaywiserequest.setClean(clean);
				daywise_response.add(objdaywiserequest);
			}
			
		} catch (Exception ex) {
			objdaywiserequest = null;
		}
		CloseConnection(_RESULT_SET);
		return daywise_response;
	}
	
		public List<Averageresponse> getsosaverageReport() {
			 if(!OpenConnection()) return null;
		Averageresponse obj_average = null;
		List<Averageresponse> average_response = new ArrayList<Averageresponse>();
		 ResultSet _RESULT_SET=null;
		try {

			String query = "select table_master.table_Ref_No as tableno, "+ 
							" if(round(avg(timestampdiff(second,requested_ON,cancelled_ON))/60,2)<21,round(avg(timestampdiff(second,requested_ON,cancelled_ON))/60,2),20) as avgsosresponse "+
							" from device_request_updater,table_master,device_updater where device_request_updater.device_updater_ID=device_updater.device_updater_ID "+ 
							" and device_request_updater.device_request_type_ID=118 "+
							" and device_updater.table_ID=table_master.table_ID "+ 
							" group by device_request_updater.device_updater_ID order by RIGHT(table_master.table_Ref_No,2) asc;";
			 _RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				obj_average = new Averageresponse();
				String tableno = _RESULT_SET.getString("tableno");
				float avgbillresponse = _RESULT_SET.getFloat("avgsosresponse");
				obj_average.setAvgsosresponse(avgbillresponse);
				obj_average.setTableno(tableno);
				average_response.add(obj_average);
			}
			
		} catch (Exception ex) {
			obj_average = null;
		}
		CloseConnection(_RESULT_SET);
		return average_response;
	}
	public List<Averageresponse> getnurseaverageReport() {
		 if(!OpenConnection()) return null;
		Averageresponse obj_average = null;
		List<Averageresponse> average_response = new ArrayList<Averageresponse>();
		String query="";
		 ResultSet _RESULT_SET=null;
		try {
			
				 query = "select table_master.table_Ref_No as tableno, "+ 
						" if(round(avg(timestampdiff(second,requested_ON,cancelled_ON))/60,2)<21,round(avg(timestampdiff(second,requested_ON,cancelled_ON))/60,2),20) as avgnurseresponse "+
						" from device_request_updater,table_master,device_updater where device_request_updater.device_updater_ID=device_updater.device_updater_ID "+ 
						" and device_request_updater.device_request_type_ID=218 "+
						" and device_updater.table_ID=table_master.table_ID "+ 
						" group by device_request_updater.device_updater_ID order by RIGHT(table_master.table_Ref_No,2) asc;";
			 _RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				obj_average = new Averageresponse();
				String tableno = _RESULT_SET.getString("tableno");
				float avgdrinkresponse = _RESULT_SET.getFloat("avgnurseresponse");
				obj_average.setAvgnurseresponse(avgdrinkresponse);
				obj_average.setTableno(tableno);
				average_response.add(obj_average);
			}
			CloseConnection(_RESULT_SET);
		} catch (Exception ex) {
			obj_average = null;
		}
		return average_response;
	}
	public List<Averageresponse> getcleanaverageReport() {
		 if(!OpenConnection()) return null;
		Averageresponse obj_average = null;
		List<Averageresponse> average_response = new ArrayList<Averageresponse>();
		String query ="";
		ResultSet _RESULT_SET=null;
		try {
			 query = "select table_master.table_Ref_No as tableno, "+ 
						" if(round(avg(timestampdiff(second,requested_ON,cancelled_ON))/60,2)<21,round(avg(timestampdiff(second,requested_ON,cancelled_ON))/60,2),20) as avgcleanresponse "+
						" from device_request_updater,table_master,device_updater where device_request_updater.device_updater_ID=device_updater.device_updater_ID "+ 
						" and device_request_updater.device_request_type_ID=318 "+
						" and device_updater.table_ID=table_master.table_ID "+ 
						" group by device_request_updater.device_updater_ID order by RIGHT(table_master.table_Ref_No,2) asc;";
			 _RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				obj_average = new Averageresponse();
				String tableno = _RESULT_SET.getString("tableno");
				float avgserviceresponse = _RESULT_SET.getFloat("avgcleanresponse");
				obj_average.setAvgnurseresponse(avgserviceresponse);
				obj_average.setTableno(tableno);
				average_response.add(obj_average);
			}
			CloseConnection(_RESULT_SET);
		} catch (Exception ex) {
			obj_average = null;
		}
		return average_response;
	}
	public List<Overallrequestcount> getoverallReport() {
		 if(!OpenConnection()) return null;
		Overallrequestcount obj_overall = null;
		List<Overallrequestcount> overall_response = new ArrayList<Overallrequestcount>();
		String query ="";
		ResultSet _RESULT_SET=null;
		try{
					 query = "select sum(if(device_request_type_ID=118,1,0))as Sos, "+
							 " sum(if(device_request_type_ID=218,1,0))as Nurse, "+ 
							 " sum(if(device_request_type_ID=318,1,0))as  Clean "+
							 " from device_request_updater order by requested_on desc;";
			 _RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				obj_overall = new Overallrequestcount();
				int Sos = _RESULT_SET.getInt("Sos");
				int Nurse = _RESULT_SET.getInt("Nurse");
				int Clean = _RESULT_SET.getInt("Clean");
				
				obj_overall.setSos(Sos);
				obj_overall.setClean(Clean);
				obj_overall.setNurse(Nurse);
				
				overall_response.add(obj_overall);
			}
			CloseConnection(_RESULT_SET);
		} catch (Exception ex) {
			obj_overall = null;
		}
		return overall_response;
	}
	public List<AutocancelReports> getautocancelReport() {
		 if(!OpenConnection()) return null;
		AutocancelReports obj_autocancel = null;
		List<AutocancelReports> autocancel_response = new ArrayList<AutocancelReports>();
		ResultSet _RESULT_SET=null;
		try {

			String query = "select  table_master.table_Ref_No as table_No,count(*) as autocancelcount from device_request_updater,table_master,device_updater "+
							" where device_request_updater.device_updater_id=device_updater.device_updater_id and device_updater.table_ID=table_master.table_ID "+ 
							" and device_request_updater.Is_Auto_Cancelled=1  group by table_master.table_Ref_No  order by RIGHT(table_master.table_Ref_No ,2) asc;";
			
			 _RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				obj_autocancel = new AutocancelReports();
				String table_No = _RESULT_SET.getString("table_No");
				int autocancel_count=_RESULT_SET.getInt("autocancelcount");
				
				obj_autocancel.setTable_No(table_No);
				obj_autocancel.setAutocancelcount(autocancel_count);
				
				autocancel_response.add(obj_autocancel);
			}
			CloseConnection(_RESULT_SET);
		} catch (Exception ex) {
			obj_autocancel = null;
		}
		return autocancel_response;
	}
	public List<Tablewisereqcount> getbedwiseReport() {
		 if(!OpenConnection()) return null;
		Tablewisereqcount obj_tablewise = null;
		List<Tablewisereqcount> tablewise_response = new ArrayList<Tablewisereqcount>();
		String query="";
		ResultSet _RESULT_SET=null;
		try {
				 query = "select temp_table.table_no as tableno,if(Sos is null,0,Sos) as Sos, "+
						  " if(Nurse is null,0,Nurse) as Nurse, "+ 
						  " if(Clean is null,0,Clean) as Clean from "+ 
						  " (select table_master.table_Ref_No as table_no,sum(if(device_request_type_ID=118,1,0))as Sos , "+
						  " sum(if(device_request_type_ID=218,1,0))as Nurse, "+ 
						  " sum(if(device_request_type_ID=318,1,0))as  Clean from "+
						  " device_request_updater,table_master,device_updater where device_request_updater.device_updater_id=device_updater.device_updater_id and "+ 
						  " device_updater.table_ID=table_master.table_ID "+ 
						  " group by table_master.table_Ref_No  order by RIGHT(table_master.table_Ref_No ,2) asc )as temp_table; "; 
			 _RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				obj_tablewise = new Tablewisereqcount();
				String table_No = _RESULT_SET.getString("tableno");
				int Sos=_RESULT_SET.getInt("Sos");
				int Nurse=_RESULT_SET.getInt("Nurse");
				int Clean=_RESULT_SET.getInt("Clean");
				
				obj_tablewise.setTableno(table_No);
				obj_tablewise.setSos(Sos);
				obj_tablewise.setNurse(Nurse);
				obj_tablewise.setClean(Clean);
				
				tablewise_response.add(obj_tablewise);
			}
			CloseConnection(_RESULT_SET);
		} catch (Exception ex) {
			obj_tablewise = null;
		}
		return tablewise_response;
	}
	public List<MostlyusedtableReport> getmostlyusedtableReport() {
		 if(!OpenConnection()) return null;
		MostlyusedtableReport obj_mostlyusedtable = null;
		List<MostlyusedtableReport> mostlyusedtable_response = new ArrayList<MostlyusedtableReport>();
		ResultSet _RESULT_SET=null;
		try {

			String query = "select table_Ref_No as table_No,count(*) as total from device_request_updater,table_master,device_updater where "+ 
							" device_request_updater.device_updater_id=device_updater.device_updater_id "+
							" and device_updater.table_ID=table_master.table_ID  group by table_master.table_ID  order by RIGHT(table_master.table_Ref_No,2) asc ;"; 
			 _RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				obj_mostlyusedtable = new MostlyusedtableReport();
				String table_No = _RESULT_SET.getString("table_No");
				int total=_RESULT_SET.getInt("total");
				
				obj_mostlyusedtable.setTable_No(table_No);
				obj_mostlyusedtable.setTotal(total);
				
				mostlyusedtable_response.add(obj_mostlyusedtable);
			}
			CloseConnection(_RESULT_SET);
		} catch (Exception ex) {
			obj_mostlyusedtable = null;
		}
		return mostlyusedtable_response;
	}
	public List<UsersAnalytics> topuser() {
		 if(!OpenConnection()) return null;
		UsersAnalytics obj_topuser = null;
		List<UsersAnalytics> topuser_response = new ArrayList<UsersAnalytics>();
		ResultSet _RESULT_SET=null;
		try {

			String query = "select topuser,avgresponse as maxresponse from (select user.Username as topuser, "+ 
							" if(round(avg(timestampdiff(second,requested_ON,cancelled_ON))/60,2)<21,round(avg(timestampdiff(second,requested_ON,cancelled_ON))/60,2),20) as avgresponse "+ 
							 " from device_request_updater,user,device_updater "+
							 " where device_request_updater.Device_Updater_ID=device_updater.Device_Updater_ID "+ 
							 " and device_updater.User_ID=user.user_ID group by user.Username) as temp order by avgresponse  limit 1;";
			 _RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				obj_topuser = new UsersAnalytics();
				String topuser = _RESULT_SET.getString("topuser");
				int avgresponse=_RESULT_SET.getInt("maxresponse");
				obj_topuser.setTopuser(topuser);
				obj_topuser.setAvgresponse(avgresponse);
				
				topuser_response.add(obj_topuser);
			}
			CloseConnection(_RESULT_SET);
		} catch (Exception ex) {
			obj_topuser = null;
		}
		return topuser_response;
	}
	public List<UsersAnalytics> lastuser() {
		 if(!OpenConnection()) return null;
		UsersAnalytics obj_topuser = null;
		List<UsersAnalytics> topuser_response = new ArrayList<UsersAnalytics>();
		ResultSet _RESULT_SET=null;
		try {

			String query = "select topuser,avgresponse as maxresponse from (select user.Username as topuser, "+ 
					" if(round(avg(timestampdiff(second,requested_ON,cancelled_ON))/60,2)<21,round(avg(timestampdiff(second,requested_ON,cancelled_ON))/60,2),20) as avgresponse "+ 
					 " from device_request_updater,user,device_updater "+
					 " where device_request_updater.Device_Updater_ID=device_updater.Device_Updater_ID "+ 
					 " and device_updater.User_ID=user.user_ID group by user.Username) as temp order by avgresponse desc limit 1;";
			 _RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				obj_topuser = new UsersAnalytics();
				String topuser = _RESULT_SET.getString("topuser");
				int avgresponse=_RESULT_SET.getInt("maxresponse");
				obj_topuser.setTopuser(topuser);
				obj_topuser.setAvgresponse(avgresponse);
				
				topuser_response.add(obj_topuser);
			}
			CloseConnection(_RESULT_SET);
		} catch (Exception ex) {
			obj_topuser = null;
		}
		return topuser_response;
	}
	public List<TableAnalyticsReport> costlytable() {
		 if(!OpenConnection()) return null;
		TableAnalyticsReport obj_toptable = null;
		List<TableAnalyticsReport> toptable_response = new ArrayList<TableAnalyticsReport>();
		ResultSet _RESULT_SET=null;
		try {

			String query = "select tablename,max(costlytable) as costlytable from(select table_master.Table_Ref_No as tablename,count(*) costlytable "+ 
							" from device_request_updater,table_master,device_updater where "+ 
							" device_request_updater.Device_Updater_ID=device_updater.Device_Updater_ID and "+ 
							" device_updater.Table_ID=table_master.Table_ID group by table_master.Table_ID order by costlytable desc limit 1) as tempatable ;";
			 _RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				obj_toptable = new TableAnalyticsReport();
				String toptable = _RESULT_SET.getString("tablename");
				int count=_RESULT_SET.getInt("costlytable");
				
				obj_toptable.setCostlytable(count);
				obj_toptable.setTablename(toptable);
				
				toptable_response.add(obj_toptable);
			}
			CloseConnection(_RESULT_SET);
		} catch (Exception ex) {
			obj_toptable = null;
		}
		return toptable_response;
	}
	public List<TableAnalyticsReport> lasttable() {
		 if(!OpenConnection()) return null;
		TableAnalyticsReport obj_toptable = null;
		List<TableAnalyticsReport> toptable_response = new ArrayList<TableAnalyticsReport>();
		ResultSet _RESULT_SET=null;
		try {

			String query = "select tablename,max(lasttable) as lasttable from(select table_master.Table_Ref_No as tablename,count(*) lasttable "+ 
					" from device_request_updater,table_master,device_updater where "+ 
					" device_request_updater.Device_Updater_ID=device_updater.Device_Updater_ID and "+ 
					" device_updater.Table_ID=table_master.Table_ID group by table_master.Table_ID order by lasttable limit 1) as tempatable ;";
			_RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				obj_toptable = new TableAnalyticsReport();
				String toptable = _RESULT_SET.getString("tablename");
				int count=_RESULT_SET.getInt("lasttable");
				
				obj_toptable.setLasttable(count);
				obj_toptable.setTablename(toptable);
				
				toptable_response.add(obj_toptable);
			}
			CloseConnection(_RESULT_SET);
		} catch (Exception ex) {
			obj_toptable = null;
		}
		return toptable_response;
	}
	public List<MonthwiseReport> monthwisereport() {
		 if(!OpenConnection()) return null;
		MonthwiseReport obj_monthwise = null;
		List<MonthwiseReport> monthwise_response = new ArrayList<MonthwiseReport>();
		ResultSet _RESULT_SET=null;
		try {

			String query = "select date_format(requested_ON,'%a')as 'date',count(*) as total_count "+
							" from device_request_updater group by date_format(requested_ON,'%a') order by requested_ON desc;";
			_RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				obj_monthwise = new MonthwiseReport();
				String date = _RESULT_SET.getString("date");
				int count=_RESULT_SET.getInt("total_count");
				
				obj_monthwise.setDate(date);
				obj_monthwise.setTotal_count(count);
				
				monthwise_response.add(obj_monthwise);
			}
			CloseConnection(_RESULT_SET);
		} catch (Exception ex) {
			obj_monthwise = null;
		}
		return monthwise_response;
	}
	public List<Dashboardcount> getDashboardcount() {
		 if(!OpenConnection()) return null;
		Dashboardcount objdashboardcount = null;
		List<Dashboardcount> dashboardcount_response = new ArrayList<Dashboardcount>();
		ResultSet _RESULT_SET=null;
		try {

			String query = "select (select count(*) from device_request_updater)as requestcount, "+
							" (select count(*) from device_request_updater where Is_Auto_Cancelled=1)as autocancelcount, "+ 
							" (select count(*) from (select count(*) from device_request_updater group by DATE(requested_ON))as test) as daycount;";
			_RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				objdashboardcount = new Dashboardcount();
				int requestcount = _RESULT_SET.getInt("requestcount");
				int autocancelcount = _RESULT_SET.getInt("autocancelcount");
				int daycount = _RESULT_SET.getInt("daycount");
				
				objdashboardcount.setDaycount(daycount);
				objdashboardcount.setRequestcount(requestcount);
				objdashboardcount.setAutocancelcount(autocancelcount);
				
				dashboardcount_response.add(objdashboardcount);
			}
			CloseConnection(_RESULT_SET);
		} catch (Exception ex) {
			objdashboardcount = null;
		}
		return dashboardcount_response;
	}
	public List<Appdata> getappdata() {
		 if(!OpenConnection()) return null;
		Appdata objappdata = null;
		List<Appdata> appdata_response = new ArrayList<Appdata>();
		ResultSet _RESULT_SET=null;
		try {

			String query = "select devicedata.userID as User_ID,rusers.username,rtables.tableNo as Table_Ref_No,rdevices.deviceID as Device_ID, "+
							" devicedata.CancelledON as Cancelled_ON,devicedata.AutoCancelled_ON as Auto_Cancelled_ON, "+
							" devicedata.requestedON as Requested_ON from(select rdevicepair.tableID, "+ 
							" rdevicepair.deviceRefID,rdevicepair.userID,rdevicerequestupdate.requestedON,rdevicerequestupdate.CancelledON,rdevicerequestupdate.AutoCancelled_ON from "+ 
							" rdevicerequestupdate,rdevicepair where rdevicerequestupdate.devicepairid=rdevicepair.devicepairid) "+ 
							" as devicedata ,rusers,rtables,rdevices where rusers.userID=devicedata.userID "+
							" and rtables.tableID=devicedata.tableID and rdevices.deviceRefID=devicedata.deviceRefID order by devicedata.requestedON desc limit 50;";
			_RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				objappdata = new Appdata();
				int User_ID = _RESULT_SET.getInt("User_ID");
				String  username = _RESULT_SET.getString("username");
				String  Table_Ref_No = _RESULT_SET.getString("Table_Ref_No");
				String  Device_ID = _RESULT_SET.getString("Device_ID");
				String  Cancelled_ON = _RESULT_SET.getString("Cancelled_ON");
				String  Auto_Cancelled_ON = _RESULT_SET.getString("Auto_Cancelled_ON");
				String  Requested_ON = _RESULT_SET.getString("Requested_ON");				
				
				objappdata.setUser_ID(User_ID);
				objappdata.setUsername(username);
				objappdata.setTable_Ref_No(Table_Ref_No);
				objappdata.setDevice_ID(Device_ID);
				objappdata.setCancelled_ON(Cancelled_ON);
				objappdata.setAuto_Cancelled_ON(Auto_Cancelled_ON);
				objappdata.setRequested_ON(Requested_ON);
				appdata_response.add(objappdata);
			}
			CloseConnection(_RESULT_SET);
		} catch (Exception ex) {
			objappdata = null;
		}
		return appdata_response;
	}
	public List<Appstatus> appstatus() {
		 if(!OpenConnection()) return null;
		Appstatus Oappdata = null;
		List<Appstatus> dashboard_count = new ArrayList<Appstatus>();
		ResultSet _RESULT_SET=null;
		try {
			String query = "SELECT appStatustypename as 'Title', appupdatedon as 'AppTime ',updatedon as 'ExportedTime' , "+ 
						   " Description as 'Message',appstatustype.appstatustypeid as 'Type' FROM appstatus,appstatustype "+ 
						   " where appstatus.appstatustypeid=appstatustype.appstatustypeid order by appstatus.appstatustypeid desc limit 50;";
			_RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				Oappdata = new Appstatus();
				String Type = _RESULT_SET.getString("Type");
				String Message = _RESULT_SET.getString("Message");
				String ExportedTime = _RESULT_SET.getString("ExportedTime");
				//String AppTime = rs.getString("AppTime");
				String Title = _RESULT_SET.getString("Title");
				
				Oappdata.setType(Type);
				Oappdata.setMessage(Message);
				Oappdata.setExportedTime(ExportedTime);
				//Oappdata.setAppTime(AppTime);
				Oappdata.setTitle(Title);
				dashboard_count.add(Oappdata);

			}
		} catch (Exception ex) {
		}
		return dashboard_count;
	}
	

	//*************************************** DateWise Report Start ***************************************//*
	public List<DayWiseRequestReport> date_getDayWiseRequestReport(String startdate,String enddate) {
		 if(!OpenConnection()) return null;
		DayWiseRequestReport objdaywiserequest = null;
		List<DayWiseRequestReport> daywise_response = new ArrayList<DayWiseRequestReport>();
		String query="";
		ResultSet _RESULT_SET=null;
		try {
			
				 query = "select date(requested_ON) as date,sum(if(device_request_type_ID=118,1,0))as Sos , "+
							" sum(if(device_request_type_ID=218,1,0))as Nurse, "+ 
							" sum(if(device_request_type_ID=318,1,0))as  Clean from device_request_updater where"+
							" date(requested_ON) between '"+startdate+"' and  '"+enddate+"' "+
							" group by date(requested_ON) order by requested_ON desc limit 10;";
				 
			_RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				objdaywiserequest = new DayWiseRequestReport();
				String requested_ON = _RESULT_SET.getString("date");
				int Sos = _RESULT_SET.getInt("Sos");
				int Nurse = _RESULT_SET.getInt("Nurse");
				int Clean = _RESULT_SET.getInt("Clean");
				
				objdaywiserequest.setDate(requested_ON);
				objdaywiserequest.setSos(Sos);
				objdaywiserequest.setClean(Clean);
				objdaywiserequest.setNurse(Nurse);
				daywise_response.add(objdaywiserequest);
			}
			CloseConnection(_RESULT_SET);
		} catch (Exception ex) {
			objdaywiserequest = null;
		}
		return daywise_response;
	}
	public List<AutocancelReports> date_getautocancelReport(String startdate,String enddate ) {
		 if(!OpenConnection()) return null;
		AutocancelReports obj_autocancel = null;
		List<AutocancelReports> autocancel_response = new ArrayList<AutocancelReports>();
		ResultSet _RESULT_SET=null;
		try {
			
			String query = "select  table_master.table_Ref_No as table_No,count(*) as autocancelcount from device_request_updater,table_master,device_updater "+
					" where device_request_updater.device_updater_id=device_updater.device_updater_id and device_updater.table_ID=table_master.table_ID "+
					" and date(requested_ON) between '"+startdate+"' and  '"+enddate+"' "+ 
					" and device_request_updater.Is_Auto_Cancelled=1  group by table_master.table_Ref_No  order by RIGHT(table_master.table_Ref_No ,2) asc;";
			
			_RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				obj_autocancel = new AutocancelReports();
				String table_No = _RESULT_SET.getString("table_No");
				int autocancel_count=_RESULT_SET.getInt("autocancelcount");
				
				obj_autocancel.setTable_No(table_No);
				obj_autocancel.setAutocancelcount(autocancel_count);
				
				autocancel_response.add(obj_autocancel);
			}
			CloseConnection(_RESULT_SET);
		} catch (Exception ex) {
			obj_autocancel = null;
		}
		return autocancel_response;
	}
	public List<Tablewisereqcount> date_gettablewiseReport(String startdate,String enddate) {
		 if(!OpenConnection()) return null;
		Tablewisereqcount obj_tablewise = null;
		List<Tablewisereqcount> tablewise_response = new ArrayList<Tablewisereqcount>();
		String query="";
		ResultSet _RESULT_SET=null;
		try {
				 
				 query = "select temp_table.table_no as tableno,if(Sos is null,0,Sos) as Sos, "+
						  " if(Nurse is null,0,Nurse) as Nurse, "+ 
						  " if(Clean is null,0,Clean) as Clean from "+ 
						  " (select table_master.table_Ref_No as table_no,sum(if(device_request_type_ID=118,1,0))as Sos , "+
						  " sum(if(device_request_type_ID=218,1,0))as Nurse, "+ 
						  " sum(if(device_request_type_ID=318,1,0))as  Clean from "+
						  " device_request_updater,table_master,device_updater where device_request_updater.device_updater_id=device_updater.device_updater_id and "+ 
						  " device_updater.table_ID=table_master.table_ID and"+ 
						  " date(requested_ON) between '"+startdate+"' and  '"+enddate+"' "+
						  " group by table_master.table_Ref_No  order by RIGHT(table_master.table_Ref_No ,2) asc )as temp_table; "; 
			
			_RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				obj_tablewise = new Tablewisereqcount();
				String table_No = _RESULT_SET.getString("tableno");
				int Sos=_RESULT_SET.getInt("Sos");
				int Nurse=_RESULT_SET.getInt("Nurse");
				int Clean=_RESULT_SET.getInt("Clean");
				
				obj_tablewise.setTableno(table_No);
				obj_tablewise.setSos(Sos);
				obj_tablewise.setNurse(Nurse);
				obj_tablewise.setClean(Clean);
				
				tablewise_response.add(obj_tablewise);
			}
			CloseConnection(_RESULT_SET);
		} catch (Exception ex) {
			obj_tablewise = null;
		}
		return tablewise_response;
	}
	public List<MostlyusedtableReport> date_getmostlyusedtableReport(String startdate,String enddate) {
		 if(!OpenConnection()) return null;
		MostlyusedtableReport obj_mostlyusedtable = null;
		List<MostlyusedtableReport> mostlyusedtable_response = new ArrayList<MostlyusedtableReport>();
		ResultSet _RESULT_SET=null;
		try {

			String query = "select table_Ref_No as table_No,count(*) as total from device_request_updater,table_master,device_updater where "+ 
					" device_request_updater.device_updater_id=device_updater.device_updater_id and"+
					" date(requested_ON) between '"+startdate+"' and  '"+enddate+"' "+
					" and device_updater.table_ID=table_master.table_ID  group by table_master.table_ID  order by RIGHT(table_master.table_Ref_No,2) asc ;";
			
			_RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				obj_mostlyusedtable = new MostlyusedtableReport();
				String table_No = _RESULT_SET.getString("table_No");
				int total=_RESULT_SET.getInt("total");
				
				obj_mostlyusedtable.setTable_No(table_No);
				obj_mostlyusedtable.setTotal(total);
				
				mostlyusedtable_response.add(obj_mostlyusedtable);
			}
			CloseConnection(_RESULT_SET);
		} catch (Exception ex) {
			obj_mostlyusedtable = null;
		}
		return mostlyusedtable_response;
	}
	public List<MonthwiseReport> date_monthwisereport(String startdate,String enddate) {
		 if(!OpenConnection()) return null;
		MonthwiseReport obj_monthwise = null;
		List<MonthwiseReport> monthwise_response = new ArrayList<MonthwiseReport>();
		ResultSet _RESULT_SET=null;
		try {
			
			String query = "select date_format(requested_ON,'%a')as 'date',count(*) as total_count "+
					" from device_request_updater where date(requested_ON) between '"+startdate+"' and  '"+enddate+"' "
							+ "group by date_format(requested_ON,'%a') order by requested_ON desc;";
			
			_RESULT_SET = _STATEMENT.executeQuery(query);
			while (_RESULT_SET.next()) {
				obj_monthwise = new MonthwiseReport();
				String date = _RESULT_SET.getString("date");
				int count=_RESULT_SET.getInt("total_count");
				
				obj_monthwise.setDate(date);
				obj_monthwise.setTotal_count(count);
				monthwise_response.add(obj_monthwise);
			}
			CloseConnection(_RESULT_SET);
		} catch (Exception ex) {
			obj_monthwise = null;
		}
		return monthwise_response;
	}
}
