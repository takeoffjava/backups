var mostusedtable_tablenameN=new Array();
var mostusedtable_tablecountN=new Array();
var overall_requesttypeN=new Array();
var overallrequest_countN=new Array();
var mostrequested_hourN=new Array();
var mostrequested_countN=new Array();
var autocancel_tablenameN=new Array();
var autocancel_countN=new Array();
var daywise_dateN=new Array();
var daywise_serviceN=new Array();
var daywise_drinkN=new Array();
var daywise_likeN=new Array();
var daywise_billN=new Array();
var waiter_nameN=new Array();
var min_responseN=new Array();
var max_responseN=new Array();
var TotalRequest_name=new Array();
var Total_Count=new Array();
var Avg_usernameN=new Array();
var Avg_tablearesponseN=new Array();
//seperate table response
var Table_NameN=new Array();
var Table_ServiceN=new Array();
var Table_DrinkN=new Array();
var Table_BillN=new Array();
var Table_likeN=new Array();

function databydate(){
	var Startdate=document.getElementById("sdatepicker").value;
	var Enddate=document.getElementById("edatepicker").value;
	if(Startdate===""||Enddate===""){
		noty( {"text":"Date should not be empty... ","layout":"top","type":"error"});
		return false;
	}else{
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'datedashboard';
	
	var method = "POST";
	
		objectForPost = {
				Start_Date : Startdate,
				End_Date : Enddate,
				remote_type : remote_typeJS
		};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'databydateCallback');
	}
}
function databydateCallback(resultobject){
	if(resultobject.mostusedtable.length===0){
		document.getElementById('daywisedata').innerHTML = "No Data Between This Date";
	}
	else{
		daywise_dateN=new Array();
		daywise_serviceN=new Array();
		daywise_drinkN=new Array();
		daywise_billN=new Array();
		daywise_likeN=new Array();
	for(var i=0;i<resultobject.daywiseData.length;i++){
			 myDate = new Date(resultobject.daywiseData[i].requested_ON);
			daywise_dateN[i]=myDate.toDateString();
			daywise_serviceN[i]=resultobject.daywiseData[i].service;
			daywise_drinkN[i]=resultobject.daywiseData[i].water;
			daywise_billN[i]=resultobject.daywiseData[i].bill;
			daywise_likeN[i]=resultobject.daywiseData[i].like;
			}
	
	document.getElementById('daywisedata').innerHTML = "";
	daywisecount_new(daywise_dateN,daywise_serviceN,daywise_drinkN,daywise_billN,daywise_likeN);
}
	
if(resultobject.mostusedtable.length===0){
	document.getElementById('container').innerHTML = "No Data Between This Date";
}
else{
	for(var i=0;i<resultobject.mostusedtable.length;i++){
		mostusedtable_tablenameN[i]=resultobject.mostusedtable[i].table_No;
		mostusedtable_tablecountN[i]=resultobject.mostusedtable[i].total;
		}
	document.getElementById('container').innerHTML = "";
	mostusedtable_functionN(mostusedtable_tablenameN,mostusedtable_tablecountN);
	}
if(resultobject.waiterResponse.length===0){
	document.getElementById('waiterresponse').innerHTML = "No Data Between This Date";
}else{
	for(var i=0;i<resultobject.waiterResponse.length;i++){
		waiter_nameN[i]=resultobject.waiterResponse[i].username;
		min_responseN[i]=resultobject.waiterResponse[i].minResponse;
		max_responseN[i]=resultobject.waiterResponse[i].maxResponse;
		}
	document.getElementById('waiterresponse').innerHTML = "";
	waiterresponseN(waiter_nameN,min_responseN,max_responseN);
}
if(resultobject.autoCancel.length===0){
	document.getElementById('nonpresstable').innerHTML = "No Data Between This Date";
}else{
		for(var i=0;i<resultobject.autoCancel.length;i++){
			autocancel_tablenameN[i]=resultobject.autoCancel[i].table_Ref_no;
			autocancel_countN[i]=resultobject.autoCancel[i].autocancelcount;
			}
	 document.getElementById('nonpresstable').innerHTML = "";
	 noncanceltable_functionN(autocancel_tablenameN,autocancel_countN);
}   
if(resultobject.avgresponse_waiter.length===0){
	document.getElementById('waiterAvgresponse').innerHTML = "No Data Between This Date";
}else{
	 for(var i=0;i<resultobject.avgresponse_waiter.length;i++){
			Avg_usernameN[i]=[resultobject.avgresponse_waiter[i].username,resultobject.avgresponse_waiter[i].avgResponse];
		}
	 document.getElementById('waiterAvgresponse').innerHTML = "";
	 avgwaiter_barN(Avg_usernameN);
} 
if(resultobject.avgresponse_table.length===0){
	document.getElementById('tableAvgresponse').innerHTML = "No Data Between This Date";
}else{
	 for(var i=0;i<resultobject.avgresponse_table.length;i++){
			Avg_tablearesponseN[i]=[resultobject.avgresponse_table[i].table_ref_no,resultobject.avgresponse_table[i].avgresponse];
		}
	 document.getElementById('tableAvgresponse').innerHTML = "";
	 avgtable_barN(Avg_tablearesponseN);
}
if(resultobject.responsecount_alltable.length===0){
	document.getElementById('alltableresponsecount').innerHTML = "No Data Between This Date";
}else{
	 for(var i=0;i<resultobject.responsecount_alltable.length;i++){
			Table_NameN[i]=resultobject.responsecount_alltable[i].table_ref_no;
			Table_ServiceN[i]=resultobject.responsecount_alltable[i].service;
			Table_DrinkN[i]=resultobject.responsecount_alltable[i].water;
			Table_BillN[i]=resultobject.responsecount_alltable[i].bill;
			Table_likeN[i]=resultobject.responsecount_alltable[i].like;
		}
	 
	 document.getElementById('alltableresponsecount').innerHTML = "";
		for(var i=0;i<Table_NameN.length;i++){
			 $('#alltableresponsecount').append('<div class="donutclass span3"  id='+"'"+Table_NameN[i]+"'"+' ondesktop="span3" ontablet="span6" style="height:200px;"></div>');
	        alltabledata_donutN(Table_NameN[i],Table_ServiceN[i],Table_DrinkN[i],Table_BillN[i],Table_likeN[i]);
	    }
}
}
function protective_HoursN(mostrequested_hourN,mostrequested_countN){
    $('#prodective').highcharts({
	        chart: {
	            zoomType: 'x'
	        },
	        title: {
	            text: 'productive Hours'
	        },
	        xAxis: {
	            categories: mostrequested_hourN,
	        },
	        yAxis: {
	            title: {
	                text: 'Request Count'
	            }
	        },
	        legend: {
	            enabled: false
	        },
	        tooltip: {
	            formatter: function() {
	                return 'Hour :<b>'+this.x +'</b> Counts:<b>' + this.y+'</b>';
	            }
	        
	        },
        exporting: { enabled: false },
	        plotOptions: {
	            area: {
	                fillColor: {
	                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
	                    stops: [
	                        [0, Highcharts.getOptions().colors[0]],
	                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
	                    ]
	                },
	                marker: {
	                    radius: 2
	                },
	                lineWidth: 1,
	                states: {
	                    hover: {
	                        lineWidth: 1
	                    }
	                },
	                threshold: null
	            }
	        },

	        series: [{
	            type: 'area',
	            name: 'Count',
	            data: mostrequested_countN
	        }]
   });
} function overall_requestcountN(overall_requesttypeN,overallrequest_countN){
	$('#overall').highcharts({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45
            }
        },
        title: {
            text: 'OverAll Request Count'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.y}</b>'
        },
        exporting: { enabled: false },
        plotOptions: {
            pie: {
                innerSize: 100,
                depth: 85
            }
        },
        series: [{
            name: 'OverAll Request Count',
            data: [
                   [overall_requesttypeN[0],overallrequest_countN[0]],
                   [overall_requesttypeN[1],overallrequest_countN[1]],
                   [overall_requesttypeN[2],overallrequest_countN[2]]
               ]
        }]
    }); 
}
function mostusedtable_functionN(mostusedtable_tablenameN,mostusedtable_tablecountN){
    
    $('#container').highcharts({
      chart: {
          renderTo: 'container',
          type: 'column',
          margin: 75,
          options3d: {
              enabled: true,
              alpha: 15,
              beta: 15,
              depth: 50,
              viewDistance: 25
          }
      },
      title: {
          text: 'Mostly Used Table'
      },
	        xAxis: {
	            categories: mostusedtable_tablenameN,
	        },
      plotOptions: {
          column: {
              depth: 25
          }
      },
      tooltip: {
          formatter: function() {
              return 'Request Count :<b>' + this.y+'</b>' ;
          }
      },
        exporting: { enabled: false },
      series: [{
    	  name:"Tables",
          data: mostusedtable_tablecountN
      }]
  });
      }
function noncanceltable_functionN(autocancel_tablenameN,autocancel_countN){
    $('#nonpresstable').highcharts({
      chart: {
          renderTo: 'container',
          type: 'column',
          margin: 75,
          options3d: {
              enabled: true,
              alpha: 15,
              beta: 15,
              depth: 50,
              viewDistance: 25
          }
      },
      title: {
          text: 'UnAttended Table'
      },
	        xAxis: {
	            categories: autocancel_tablenameN,
	        },
      plotOptions: {
          column: {
              depth: 25
          }
      },
      tooltip: {
          formatter: function() {
              return 'Request Count :<b>' + this.y+'</b>';
          }
      },
        exporting: { enabled: false },
      series: [{
          data: autocancel_countN
      }]
  });
      } 
function daywisecount_new(daywise_dateN,daywise_serviceN,daywise_drinkN,daywise_billN,daywise_likeN){
	$('#daywisedata').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: 'Daywise Request Count'
        },
        exporting: { enabled: false },
        xAxis: {
            categories: daywise_dateN
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Request Count'
            }
        },
        tooltip: {
            formatter: function() {
                return 'Request Count :<b>' + this.y+'</b>';
            }
        },
      
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: 'Service',
            data: daywise_serviceN

        }, {
            name: 'Water',
            data: daywise_drinkN

        }, {
            name: 'Bill',
            data: daywise_billN

        }, {
            name: 'Like',
            data: daywise_likeN

        }]
    });

}
function waiterresponseN(waiter_nameN,min_responseN,max_responseN){
	
	$('#waiterresponse').highcharts({
        chart: {
            type: 'bar'
        },
        title: {
            text: 'Waiter Response Time(Min/Max)'
        },
        xAxis: {
            categories: waiter_nameN
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Total response time'
            }
        },
        legend: {
            reversed: true
        },
        plotOptions: {
            series: {
                stacking: 'normal'
            }
        },tooltip: {
            formatter: function() {
                return 'Response Time :<b>' + this.y+'</b>';
            }
        },
        exporting: { enabled: false },
        series: [{
            name: 'Maximum Response',
            data: max_responseN
        }, {
            name: 'Minimum Response',
            data: min_responseN
        }]
    });
}
function pramidRequest(DivID){
	$('#'+DivID).highcharts({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45
            }
        },
        title: {
            text: 'OverAll Request Count'
        },
        exporting: { enabled: false },
        plotOptions: {
            pie: {
                innerSize: 100,
                depth: 45
            }
        },
        series: [{
            name: 'OverAll Request Count',
            data: [
                   [overall_requesttype[0],overallrequest_count[0]],
                   [overall_requesttype[1],overallrequest_count[1]],
                   [overall_requesttype[2],overallrequest_count[2]]
               ]
        }]
    });
}
function avgwaiter_barN(Avg_usernameN,Avg_avgResponseN){
	 $('#waiterAvgresponse').highcharts({
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: 'Waiter Average Response Time'
	        },
	        xAxis: {
	            type: 'category',
	            labels: {
	                rotation: -45,
	                style: {
	                    fontSize: '13px',
	                    fontFamily: 'Verdana, sans-serif'
	                }
	            }
	        },
	        exporting: { enabled: false },
	        yAxis: {
	            min: 0,
	            title: {
	                text: 'Response Time (in sec)'
	            }
	        },
	        tooltip: {
	            formatter: function() {
	                return 'Response Time :<b>' + this.y+'</b>';
	            }
	        },
	        legend: {
	            enabled: false
	        },
	        series: [{
	            name: 'Response Time',
	            data: Avg_usernameN,
	            dataLabels: {
	                enabled: true,
	                rotation: -90,
	                color: '#FFFFFF',
	                align: 'right',
	                format: '{point.y:.1f}', // one decimal
	                y: 10, // 10 pixels down from the top
	                style: {
	                    fontSize: '13px',
	                    fontFamily: 'Verdana, sans-serif'
	                }
	            }
	        }]
	    });
}
function avgtable_barN(Avg_tablearesponseN){
	
	 $('#tableAvgresponse').highcharts({
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: 'Table Average Response Time'
	        },
	        xAxis: {
	            type: 'category',
	            labels: {
	                rotation: -45,
	                style: {
	                    fontSize: '13px',
	                    fontFamily: 'Verdana, sans-serif'
	                }
	            }
	        },
	        exporting: { enabled: false },
	        yAxis: {
	            min: 0,
	            title: {
	                text: 'Response Time (in sec)'
	            }
	        },
	        legend: {
	            enabled: false
	        },
	        tooltip: {
	            formatter: function() {
	                return 'Response Time :<b>' + this.y+'</b>';
	            }
	        },
	        series: [{
	            name: 'Response Time',
	            data: Avg_tablearesponseN,
	            dataLabels: {
	                enabled: true,
	                rotation: -90,
	                color: '#FFFFFF',
	                align: 'right',
	                format: '{point.y:.1f}', // one decimal
	                y: 20, // 10 pixels down from the top
	                style: {
	                    fontSize: '10px',
	                    fontFamily: 'Verdana, sans-serif'
	                }
	            }
	        }]
	    });
}
function alltabledata_donutN(DivID,serviceCount,drinkCount,billCount,likecount){
	 Highcharts.setOptions({
	     colors: ['#7cb5ec', '#b8b8b8', '#ff0080', '#24CBE5', '#64E572', '#b8b8b8', '#FFF263','#6AF9C4']
	    });
	$('#'+DivID).highcharts({
		 chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: DivID
	        },
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.y}</b>'
	        },
	        exporting: { enabled: false },
	        plotOptions: {
	            pie: {
	                allowPointSelect: false,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    distance: -20,
	                    format: '<b>{point.y}</b>',
	                    style: {
	                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	                    }
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: 'Request Count',
	            data: [
	                ['Water',serviceCount],
	                ['Service', drinkCount],
	                ['Bill',billCount],
	                ['Like',likecount]
	               
	            ]
	        }]

    });
}
function reset(){
	document.getElementById("sdatepicker").value="";
	document.getElementById("edatepicker").value="";
	document.getElementById("errortext").innerHTML="<span style='color:red'></span>";
	dashboard();
}