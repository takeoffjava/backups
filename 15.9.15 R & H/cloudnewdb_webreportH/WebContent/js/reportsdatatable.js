var test_localstorage,json;
function loaddata_table(){
	test_localstorage=localStorage.getItem("resultobject");
    json = JSON.parse(test_localstorage);
	var branch_str="";
	for(var indx=0;indx<json.response[0].obranches.length;indx++){
		branch_str=branch_str+"<option value="+indx+">"+json.response[0].obranches[indx].branch_name+"</option>";
	}
	document.getElementById("branches").innerHTML=branch_str;
	datatable_creation(0);
}
function datatable_creation(index){
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'getappdata';
	var method = "POST";

	objectForPost = {	
			dbserver : json.response[0].obranches[index].dbserver,
			dbuser : json.response[0].obranches[index].dbuser,
			dbpassword :json.response[0].obranches[index].dbpassword ,
			dbport : json.response[0].obranches[index].dbport,
			dbname : json.response[0].obranches[index].dbname
	};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'datatable_creationCallBack');
}
function datatable_creationCallBack(resultobject){
	//LocalStorage Get branches and UserDetails
	if(resultobject.status===0){
		$("#appdatas").html("<thead><tr><th>S.No</th><th>Username</th><th>Table No</th><th>Device No</th><th>Requested Date</th><th>Cancelled Date</th></tr></thead>");
		noty( {"text":"No Records Found...","layout":"bottomRight","type":"error"});
		return false;
	}else{
	var table_head=null,table_body=null;
	var jndx=1;
	for(var i=0;i<resultobject.appdata.length;i++){
		var cancelledon="";
		if(typeof resultobject.appdata[i].cancelled_ON==="undefined"||resultobject.appdata[i].cancelled_ON===null){
			cancelledon=resultobject.appdata[i].auto_Cancelled_ON+" : Autocancel ";
		}else{
			cancelledon=resultobject.appdata[i].cancelled_ON;
		}
		table_head="<thead><tr><th>S.No</th><th>Username</th><th>Table No</th><th>Device No</th><th>Requested Date</th><th>Cancelled Date</th></tr></thead>";
		table_body=table_body+"<tr><td>"+jndx+"</td><td>"+resultobject.appdata[i].username+"</td><td>"+resultobject.appdata[i].table_Ref_No+"</td>" +
				"<td>"+resultobject.appdata[i].device_ID+"</td><td>"+resultobject.appdata[i].requested_ON+"</td><td>"+cancelledon+"</td></tr>";
		jndx++;
		}
	$("#appdatas").html(table_head+"<tbody>"+table_body+"</tbody>");
}
}


function datatable_creationonchange(index){
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'getappdata';
	var method = "POST";

	objectForPost = {	
			dbserver : json.response[0].obranches[index].dbserver,
			dbuser : json.response[0].obranches[index].dbuser,
			dbpassword :json.response[0].obranches[index].dbpassword ,
			dbport : json.response[0].obranches[index].dbport,
			dbname : json.response[0].obranches[index].dbname
	};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'datatable_creationonchange_creationCallBack');
}
function datatable_creationonchange_creationCallBack(resultobject){
if(resultobject.status===0){
	$('#appdatas').dataTable().fnClearTable();
	$("#appdatas").html("<thead><tr><th>S.No</th><th>Username</th><th>Table No</th><th>Device No</th><th>Requested Date</th><th>Cancelled Date</th></tr></thead>");
	noty( {"text":"No Records Found...","layout":"bottomRight","type":"error"});
	return false;
	}else{
	var msgContent=[];
	var TotMsgContent=[];
	var jndx=1;
	
	$('#appdatas').dataTable().fnClearTable();
	for(var i=0;i<resultobject.appdata.length;i++){
		msgContent.push(jndx,resultobject.appdata[i].username,resultobject.appdata[i].table_Ref_No,resultobject.appdata[i].device_ID,resultobject.appdata[i].requested_ON,resultobject.appdata[i].cancelled_ON);
		TotMsgContent.push(msgContent);	
		jndx++;
		msgContent=[];
	}
	$('#appdatas').dataTable().fnAddData(TotMsgContent);
	}
}