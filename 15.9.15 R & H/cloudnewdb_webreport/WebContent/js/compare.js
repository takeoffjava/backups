var Start_date,End_date;
function alldatacompare(){
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'alldatacompare';
	
	var method = "POST";
		objectForPost = {				
		};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'alldatacompareCallback');
}
function alldatacompareCallback(resultobject){
	
	var date=resultobject.TotalCounts[0].requestedDate;
	//var month = date.slice(5,7); 
	document.getElementById("month").innerHTML="All";
	document.getElementById("usercount").innerHTML=resultobject.UserCount[0].userCount;
	document.getElementById("tablecount").innerHTML=resultobject.TableCount[0].tableCount;
	document.getElementById("devicecount").innerHTML=resultobject.DeviceCount[0].deviceCount;
	document.getElementById("servicecount").innerHTML=resultobject.TotalCounts[0].serviceCount;
	document.getElementById("drinkcount").innerHTML=resultobject.TotalCounts[0].waterCount;
	document.getElementById("billcount").innerHTML=resultobject.TotalCounts[0].billCount;
	document.getElementById("likecount").innerHTML=resultobject.TotalCounts[0].likeCount;
}
function datedatacompare(){
	document.getElementById("table2").style.visibility="visible";
	document.getElementById("table1").style.visibility="visible";
	var Startdate=document.getElementById("sdatepicker").value;
	var Enddate=document.getElementById("edatepicker").value;
	/*Start_date=Startdate.slice(8,11);
	End_date= Enddate.slice(8,11);*/
	document.getElementById("month").innerHTML=Startdate;
	document.getElementById("month1").innerHTML=Enddate;
	if(Startdate===""||Enddate===""){
		noty( {"text":"Date should not be empty... ","layout":"top","type":"error"});
		return false;
	}else{
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'datedatacompare';
	var method = "POST";
		objectForPost = {	
				StartMonth : Startdate ,
				EndMonth : Enddate
		};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'datedatacompareCallback');
	}
}
function datedatacompareCallback(resultobject){
	if(resultobject.UserCount===0 && resultobject.TotalCounts===0){
		document.getElementById("table2").style.visibility="hidden";
		document.getElementById("table1").style.visibility="hidden";
		return false;
	} 
	
	if(resultobject.UserCount!==0)
	document.getElementById("usercount").innerHTML=resultobject.UserCount[0].userCount;
	if(resultobject.TableCount!==0)
	document.getElementById("tablecount").innerHTML=resultobject.TableCount[0].tableCount;
	if(resultobject.DeviceCount!==0)
	document.getElementById("devicecount").innerHTML=resultobject.DeviceCount[0].deviceCount;
	document.getElementById("servicecount").innerHTML=resultobject.TotalCounts[0].serviceCount;
	document.getElementById("drinkcount").innerHTML=resultobject.TotalCounts[0].waterCount;
	document.getElementById("billcount").innerHTML=resultobject.TotalCounts[0].billCount;
	document.getElementById("likecount").innerHTML=resultobject.TotalCounts[0].likeCount;
	//if(typeof neverDeclared == "undefined") //no errors

	if(resultobject.UserCount!==0)
	document.getElementById("usercount1").innerHTML=resultobject.UserCount[resultobject.UserCount.length-1].userCount;
	if(resultobject.TableCount!==0)
	document.getElementById("tablecount1").innerHTML=resultobject.TableCount[resultobject.TableCount.length-1].tableCount;
	if(resultobject.DeviceCount!==0)
	document.getElementById("devicecount1").innerHTML=resultobject.DeviceCount[resultobject.DeviceCount.length-1].deviceCount;
	document.getElementById("servicecount1").innerHTML=resultobject.TotalCounts[resultobject.TotalCounts.length-1].serviceCount;
	document.getElementById("drinkcount1").innerHTML=resultobject.TotalCounts[resultobject.TotalCounts.length-1].waterCount;
	document.getElementById("billcount1").innerHTML=resultobject.TotalCounts[resultobject.TotalCounts.length-1].billCount;
	document.getElementById("likecount1").innerHTML=resultobject.TotalCounts[0].likeCount;
}