var test_localstorage,json;
function onload_appstatus(){
	test_localstorage=localStorage.getItem("resultobject");
    json = JSON.parse(test_localstorage);
    var branch_str="";
	for(var indx=0;indx<json.response[0].obranches.length;indx++){
		branch_str=branch_str+"<option value="+indx+">"+json.response[0].obranches[indx].branch_name+"</option>";
	}
	document.getElementById("branches").innerHTML=branch_str;
    appstatus(0);
}
function appstatus(index){	
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'appstatus';
	var method = "POST";
	//remote_type=json.response[0].obranches[index].remotetype;
	
		objectForPost = {	
				dbserver : json.response[0].obranches[index].dbserver,
				dbuser : json.response[0].obranches[index].dbuser,
				dbpassword :json.response[0].obranches[index].dbpassword ,
				dbport : json.response[0].obranches[index].dbport,
				dbname : json.response[0].obranches[index].dbname
		};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'appstatusCallback');
}
function appstatusCallback(resultobject){
	//LocalStorage Get branches and UserDetails
	if(resultobject.response===0){
		$("#appdatas").html("<thead><tr><th>S.No</th><th>Message</th><th>Appstatus Type</th><th>Exported Time</th><th>Title</th></tr></thead>");
		noty( {"text":"No Records Found...","layout":"bottomRight","type":"error"});
		return false;
	}else{
	var table_head=null,table_body=null;
	var jndx=1;
	for(var i=0;i<resultobject.response.length;i++){
		
		table_head="<thead><tr><th>S.No</th><th>Message</th><th>Appstatus Type</th><th>Exported Time</th><th>Title</th></tr></thead>";
		table_body=table_body+"<tr><td>"+jndx+"</td><td>"+resultobject.response[i].message+"</td><td>"+resultobject.response[i].type+"</td>" +
				"<td>"+resultobject.response[i].exportedTime+"</td><td>"+resultobject.response[i].title+"</td></tr>";
		jndx++;
		}
	$("#appdatas").html(table_head+"<tbody>"+table_body+"</tbody>");
	}
}
function datatable_creationonchangeappstatus(index){
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'appstatus';
	var method = "POST";
	objectForPost = {	
			dbserver : json.response[0].obranches[index].dbserver,
			dbuser : json.response[0].obranches[index].dbuser,
			dbpassword :json.response[0].obranches[index].dbpassword ,
			dbport : json.response[0].obranches[index].dbport,
			dbname : json.response[0].obranches[index].dbname
	};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'datatable_creationonchange_creationappstatusCallBack');
}
function datatable_creationonchange_creationappstatusCallBack(resultobject){
	if(resultobject.response===0){
		$('#appdatas').dataTable().fnClearTable();
		$("#appdatas").html("<thead><tr><th>S.No</th><th>Message</th><th>Appstatus Type</th><th>Exported Time</th><th>Title</th></tr></thead>");
		noty( {"text":"No Records Found...","layout":"bottomRight","type":"error"});
		return false;
		}else{
	var msgContent=[];
	var TotMsgContent=[];
	var jndx=1;
	
	$('#appdatas').dataTable().fnClearTable();
	for(var i=0;i<resultobject.response.length;i++){
		msgContent.push(jndx,resultobject.response[i].message,resultobject.response[i].type,resultobject.response[i].exportedTime,resultobject.response[i].title);
		TotMsgContent.push(msgContent);	
		jndx++;
		msgContent=[];
	}
	$('#appdatas').dataTable().fnAddData(TotMsgContent);
		}
}