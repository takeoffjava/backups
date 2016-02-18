var TableArrayName;
var mostusedtable_tablename=new Array();
var mostusedtable_tablecount=new Array();
var overallrequestwater=new Array();
var overallrequestservice=new Array();
var overallrequestbill=new Array();
var overallrequestgoodservice=new Array();
var mostrequested_hour=new Array();
var mostrequested_count=new Array();
var autocancel_tablename=new Array();
var autocancel_count=new Array();
var daywise_date=new Array();
var daywise_service=new Array();
var daywise_like=new Array();
var daywise_water=new Array();
var daywise_bill=new Array();
var waiter_name=new Array();
var min_response=new Array();
var max_response=new Array();
var TotalRequest_name=new Array();
var Total_Count=new Array();
var Avg_username=new Array();
var Avg_tablearesponse=new Array();
var Productive_test=new Array();
//seperate table response
var Table_Name=new Array();
var Table_Service=new Array();
var Table_Drink=new Array();
var Table_Bill=new Array();
var Table_Like=new Array();
var test_localstorage,json;
var remote_typeJS="",PAGENO;

var daywisedata_date=new Array();
var daywisedata_requestcount=new Array();

var daywiserequestdata_date=new Array();
var daywiserequestdata_service=new Array();
var daywiserequestdata_water=new Array();
var daywiserequestdata_like=new Array();
var daywiserequestdata_bill=new Array();

var productive_hour=new Array();
var productive_count=new Array();

var billavg_tableno=new Array();
var billavg_count=new Array();

var drinkavg_tableno=new Array();
var drinkavg_count=new Array();

var serviceavg_tableno=new Array();
var serviceavg_count=new Array();

var overallrequest_name=new Array();
var overallrequest_count=new Array();

var autocancel_tableno=new Array();
var autocancel_count=new Array();

var tablewiserequest_tableno=new Array();
var tablewiserequest_service=new Array();
var tablewiserequest_drink=new Array();
var tablewiserequest_bill=new Array();
var tablewiserequest_like=new Array();

var mostused_tableno=new Array();
var mostused_count=new Array();

var monthwise_day=new Array();
var monthwise_count=new Array();

var waiterwiserequest_username=new Array();
var waiterwiserequest_service=new Array();
var waiterwiserequest_drink=new Array();
var waiterwiserequest_bill=new Array();
var waiterwiserequest_like=new Array();

/*Data Analytics Content Declaretion*/
var productive_besthour,productive_leasthour,productive_bestcount,productive_leastcount;
var daywiserequest_date,daywiserequest_count,bill_avg,bill_tableno,drink_avg,drink_tableno,service_avg,service_tableno;
var monthwise_month,monthwise_countnew,auto_tableno,auto_count;
var overall_requestcounts,overall_requesttype,most_used_table,most_used_count;

function reports_branch_load(pageno){
	test_localstorage=localStorage.getItem("resultobject");
    json = JSON.parse(test_localstorage);
    remote_typeJS=json.response[0].obranches[0].remotetype;
	var branch_str="";
	PAGENO=pageno;
	if(pageno===1){
	document.getElementById("nobranches").innerHTML=json.response[0].obranches.length;
	}
	for(var indx=0;indx<json.response[0].obranches.length;indx++){
		branch_str=branch_str+"<option value="+indx+">"+json.response[0].obranches[indx].branch_name+"</option>";
	}
	document.getElementById("branches").innerHTML=branch_str;
	document.getElementById("username").innerHTML ="Hi "+localStorage.getItem("username");
	dashboard_reports(0);
	
}
function dashboard_reports(index){
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'reports';
	var method = "POST";
	remote_typeJS=json.response[0].obranches[index].remotetype;
		objectForPost = {	
				dbserver : json.response[0].obranches[index].dbserver,
				dbuser : json.response[0].obranches[index].dbuser,
				dbpassword :json.response[0].obranches[index].dbpassword ,
				dbport : json.response[0].obranches[index].dbport,
				dbname : json.response[0].obranches[index].dbname,
				remote_type : remote_typeJS
		};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'dashboardreportsCallback');
}
function dashboardreportsCallback(resultobject){
	if(resultobject.Daywiserequestdata.length===0 && resultobject.tablewiserequestData.length==0){
		noty( {"text":"No data for this branch","layout":"bottomRight","type":"error"});
		return false;	}
	else{
		daywise_date=new Array();
		daywise_water=new Array();
		daywise_service=new Array();
		daywise_bill=new Array();
		daywise_like=new Array();
		for(var i=0;i<resultobject.Daywiserequestdata.length;i++){
			 myDate = new Date(resultobject.Daywiserequestdata[i].date);
			 
			daywise_date[i]=myDate.toDateString();
			daywise_water[i]=resultobject.Daywiserequestdata[i].water;
			daywise_service[i]=resultobject.Daywiserequestdata[i].service;
			//daywise_wa[i]=resultobject.daywiseData[i].drink;
			daywise_bill[i]=resultobject.Daywiserequestdata[i].bill;
			daywise_like[i]=resultobject.Daywiserequestdata[i].goodService;
			}
		if(remote_typeJS==="white"){
			daywisecount_white();
			}else{
				daywisecount_black();
			}
		daywiserequestdata_date=new Array();
		daywiserequestdata_service=new Array();
		daywiserequestdata_water=new Array();
		daywiserequestdata_like=new Array();
		daywiserequestdata_bill=new Array();
		for(var i=0;i<resultobject.Daywiserequestdata.length;i++){
			
			daywiserequestdata_date[i]=resultobject.Daywiserequestdata[i].date;
			daywiserequestdata_service[i]=resultobject.Daywiserequestdata[i].service;
			daywiserequestdata_water[i]=resultobject.Daywiserequestdata[i].water;
			daywiserequestdata_like[i]=resultobject.Daywiserequestdata[i].like;
			daywiserequestdata_bill[i]=resultobject.Daywiserequestdata[i].bill;
			}
		if(remote_typeJS==="white"){
			daywiserequest_functionwhite();
			}else{
				daywiserequest_function();
			}
		autocancel_tableno=new Array();
		autocancel_count=new Array();
	
	for(var i=0;i<resultobject.autocanceltablesData.length;i++){
		autocancel_tableno[i]=resultobject.autocanceltablesData[i].table_No;
		autocancel_count[i]=resultobject.autocanceltablesData[i].autocancelcount;
		}
	autocanceltable();
	$('#alltableresponsecount').html("");
	tablewiserequest_tableno=new Array();
	tablewiserequest_service=new Array();
	tablewiserequest_drink=new Array();
	tablewiserequest_bill=new Array();
	tablewiserequest_like=new Array();
	if(remote_typeJS==="white"){
	for(var i=0;i<resultobject.tablewiserequestData.length;i++){
		
		tablewiserequest_tableno[i]=resultobject.tablewiserequestData[i].tableno;
		tablewiserequest_service[i]=resultobject.tablewiserequestData[i].service;
		tablewiserequest_drink[i]=resultobject.tablewiserequestData[i].water;
		tablewiserequest_bill[i]=resultobject.tablewiserequestData[i].bill;
		tablewiserequest_like[i]=resultobject.tablewiserequestData[i].goodService;		
	}
	for(var i=0;i<tablewiserequest_tableno.length;i++){
		if(tablewiserequest_service[i]!==0&&tablewiserequest_drink[i]!==0&&tablewiserequest_bill!==0&&tablewiserequest_like!==0){
        $('#alltableresponsecount').append('<div id='+"'"+tablewiserequest_tableno[i]+"'"+' ondesktop="span3" ontablet="span6" style="height:250px;width:350px; border:0px solid #ff0000;float:left;"></div>');
        alltabledata_donutwhite(tablewiserequest_tableno[i],tablewiserequest_service[i],tablewiserequest_drink[i],tablewiserequest_bill[i],tablewiserequest_like[i]);
		}
    }
	}else{
		for(var i=0;i<resultobject.tablewiserequestData.length;i++){
			tablewiserequest_tableno[i]=resultobject.tablewiserequestData[i].tableno;
			tablewiserequest_service[i]=resultobject.tablewiserequestData[i].service;
			tablewiserequest_drink[i]=resultobject.tablewiserequestData[i].water;
			tablewiserequest_bill[i]=resultobject.tablewiserequestData[i].bill;
		}
		for(var i=0;i<tablewiserequest_tableno.length;i++){
			if(tablewiserequest_service[i]!==0&&tablewiserequest_drink[i]!==0&&tablewiserequest_bill!==0&&tablewiserequest_like!==0){
	        $('#alltableresponsecount').append('<div id='+"'"+tablewiserequest_tableno[i]+"'"+' ondesktop="span3" ontablet="span6" style="height:250px;width:350px; border:0px solid #ff0000;float:left;"></div>');
	        alltabledata_donutblack(tablewiserequest_tableno[i],tablewiserequest_service[i],tablewiserequest_drink[i],tablewiserequest_bill[i]);
			}
	    }
	}
	mostused_tableno=new Array();
	mostused_count=new Array();
	for(var i=0;i<resultobject.mostusedtableData.length;i++){
		mostused_tableno[i]=resultobject.mostusedtableData[i].table_No;
		mostused_count[i]=resultobject.mostusedtableData[i].total;
		}
	mostusedtable_function();
	monthwise_day=new Array();
	monthwise_count=new Array();
	for(var i=0;i<resultobject.monthwiserequestData.length;i++){
		monthwise_day[i]=resultobject.monthwiserequestData[i].date;
		monthwise_count[i]=resultobject.monthwiserequestData[i].total_count;
		}
	monthwise_function_bar();
	}
}
function protective_Hours(){
    $('#prodective').highcharts({
	        chart: {
	            zoomType: 'x'
	        },
	        title: {
	            text: 'Productive Hours'
	        },
	        xAxis: {
	            categories: mostrequested_hour,
	            
	        },
	        yAxis: {
	            title: {
	                text: 'Requests Count'
	            },
	            min: 0
	        },
	        xAxis: {
	            title: {
	                text: 'Hours'
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
	                lineWidth: 2,
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
	            name: 'Request Count',
	            data: mostrequested_count,
	        }]
   });
}
function overall_requestcount_white(){
//	alert(overall_requesttype[3]);
	
	$('#overall').highcharts({
	        chart: {
	            type: 'pie',
	            options3d: {
	                enabled: true,
	                alpha: 45,
	                beta: 0
	            }
	        },
	        title: {
	            text: 'Overall Request Count '
	        },
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.y}</b>'
	        },
	    exporting: { enabled: false },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                depth: 15,
	                dataLabels: {
	                    enabled: true,
	                    distance: -30,
	                    format: '{point.y}'
	                },
                    showInLegend: true
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: 'Request Count',
	            data: [
	                   	["Service",overallrequestservice[0]],
	                   	["Bill",overallrequestbill[0]],
	                   	["Drink",overallrequestwater[0]],
	                   	["GoodService",overallrequestgoodservice[0]]
	            ]
	        }]
	    }); 
	}
function noncanceltable_function(Div_ID){
    $('#nonpresstable').highcharts({
    	/*colors: ['#BC49BC'],*/
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
          text: ' UnAttended Table  '
      },
	        xAxis: {
	            categories: autocancel_tablename,
	        },
	        yAxis: {
	            title: {
	                text: 'Number of Times'
	            }
	        },
	        tooltip: {
	            formatter: function() {
	                return 'Request Count :<b>' + this.y+'</b>';
	            }
	        },
      plotOptions: {
          column: {
              depth: 25
          }
      },
        exporting: { enabled: false },
      series: [{
    	  name:"Tables",
          data: autocancel_count
      }]
  });
      } 
function daywisecount_white(){
	
	$('#daywisedata').highcharts({
		/*colors: ['#BC49BC','#6F49BC','#49BCA3','#BCBC49'],*/
        chart: {
            type: 'column'
        },
        title: {
            text: 'Daywise Request Count'
        },
        exporting: { enabled: false },
        xAxis: {
            categories: daywise_date
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
            data: daywise_service

        }, {
            name: 'Water',
            data: daywise_water

        }, {
            name: 'Bill',
            data: daywise_bill

        }, {
            name: 'Like',
            data: daywise_like

        }]
    });

}
function waiterresponse(){
	$('#waiterresponse').highcharts({
        chart: {
            type: 'bar'
        },
        title: {
            text: 'Waiter Response Time(Min/Max)'
        },
        xAxis: {
            categories: waiter_name
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Total response time (in sec)'
            }
        },
        legend: {
            reversed: true
        },
        plotOptions: {
            series: {
                stacking: 'normal'
            }
        },
        tooltip: {
            formatter: function() {
                return 'Response Time :<b>' + this.y+'</b>';
            }
        },
        exporting: { enabled: false },
        series: [{
            name: 'Maximum Response',
            data: max_response
        }, {
            name: 'Minimum Response',
            data: min_response
        }]
    });
}
function avgwaiter_bar(){
	 //console.log(Avg_username);
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
	            data: Avg_username,
	            dataLabels: {
	                enabled: true,
	                rotation: -90,
	                color: '#FFFFFF',
	                align: 'right',
	                format: '{point.y}', // one decimal
	                y: 10, // 10 pixels down from the top
	                style: {
	                    fontSize: '13px',
	                    fontFamily: 'Verdana, sans-serif'
	                }
	            }
	        }]
	    });
}
function avgtable_bar(){
	
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
	            data: Avg_tablearesponse,
	            dataLabels: {
	                enabled: true,
	                rotation: -90,
	                color: '#FFFFFF',
	                align: 'right',
	                format: '{point.y}', // one decimal
	                y: 20, // 10 pixels down from the top
	                style: {
	                    fontSize: '10px',
	                    fontFamily: 'Verdana, sans-serif'
	                }
	            }
	        }]
	    });
}
function alltabledata_donut_white(DivID,serviceCount,drinkCount,billCount,likecount){
	/* Highcharts.setOptions({
	     colors: ['#7cb5ec', '#b8b8b8', '#ff0080', '#24CBE5', '#64E572', '#b8b8b8', '#FFF263','#6AF9C4']
	    });*/
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
	                ['Service',serviceCount],
	                ['Drink', drinkCount],
	                ['Bill',billCount],
	                ['Like',likecount]
	               
	            ]
	        }]

    });
}
function overall_requestcount_black(){
//	alert(overall_requesttype[3]);

	$('#overall').highcharts({
	        chart: {
	            type: 'pie',
	            options3d: {
	                enabled: true,
	                alpha: 45,
	                beta: 0
	            }
	        },
	        title: {
	            text: 'Overall Request Count '
	        },
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.y}</b>'
	        },
	    exporting: { enabled: false },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                depth: 15,
	                dataLabels: {
	                    enabled: true,
	                    distance: -30,
	                    format: '{point.y}'
	                },
                    showInLegend: true
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: 'Request Count',
	            data: [
	                ["Service",overallrequestservice[0]],
	                ["Bill",overallrequestbill[0]],
	                ["Drink",overallrequestwater[0]]
	            ]
	        }]
	    }); 
	}
function daywisecount_black(){
	
	$('#daywisedata').highcharts({
		/*colors: ['#BC49BC','#6F49BC','#49BCA3','#BCBC49'],*/
        chart: {
            type: 'column'
        },
        title: {
            text: 'Daywise Request Count'
        },
        exporting: { enabled: false },
        xAxis: {
            categories: daywise_date
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
            data: daywise_service

        }, {
            name: 'Water',
            data: daywise_water

        }, {
            name: 'Bill',
            data: daywise_bill

        }]
    });

}
function alltabledata_donut_black(DivID,serviceCount,drinkCount,billCount){
	/* Highcharts.setOptions({
	     colors: ['#7cb5ec', '#b8b8b8', '#ff0080', '#24CBE5', '#64E572', '#b8b8b8', '#FFF263','#6AF9C4']
	    });*/
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
	                ['Service',serviceCount],
	                ['Drink', drinkCount],
	                ['Bill',billCount]
	               
	            ]
	        }]

    });
	
}
function monthwise_function(){    
    $('#monthwise').highcharts({
    	 chart: {
             zoomType: 'xy'
         },
         title: {
             text: ''//Daywise count in month
         },
         xAxis: [{
             categories: monthwise_day
         }],
         yAxis: [{ // Primary yAxis
             title: {
                 text: 'Count',
                 style: {
                     color: Highcharts.getOptions().colors[1]
                 }
             }
         }, { // Secondary yAxis
             opposite: true
         }],
         exporting: { enabled: false },
         tooltip: {
             shared: true
         },

         series: [{
             name: 'Days',
             type: 'column',
             yAxis: 1,
             data: monthwise_count
         }, {
             name: 'Growth',
             type: 'spline',
             data: monthwise_count
             
         }]
  });
      }
function monthwise_function_bar(){	
		 $('#monthwise').highcharts({
		    	/*colors: ['#BC49BC'],*/
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
		          text: ' Monthwise Request Count  '
		      },
			        xAxis: {
			            categories: monthwise_day,
			        },
			        yAxis: {
			            title: {
			                text: 'Request Counts'
			            }
			        },
			        tooltip: {
			            formatter: function() {
			                return 'Request Count :<b>' + this.y+'</b>';
			            }
			        },
		      plotOptions: {
		          column: {
		              depth: 25
		          }
		      },
		        exporting: { enabled: false },
		      series: [{
		    	  name:"Days",
		          data: monthwise_count
		      }]
		  });
}
function mostusedtable_function(){    
    $('#mostusedtable').highcharts({
      chart: {
          type: 'column',
      },
      title: {
          text: ''//Mostly Used Table
      },
	        xAxis: {
	            categories: mostused_tableno,
	        },
	        yAxis: {
	            title: {
	                text: 'Request Count'
	            }
	        },
	        tooltip: {
	            formatter: function() {
	                return 'Request Count :<b>' + this.y+'</b>' ;
	            }
	        },
      plotOptions: {
          column: {
              depth: 25
          }
      },
        exporting: { enabled: false },
      series: [{
    	  name:"Table Request Count",
          data: mostused_count,
          dataLabels: {
              enabled: true,
              rotation: 0,
              color: '#FFFFFF',
              align: 'right',
              format: '{point.y}', // one decimal
              y: 10, // 10 pixels down from the top
              style: {
                  fontSize: '10px',
                  fontFamily: 'Verdana, sans-serif'
              }
          }
      }]
  });
      }
function alltabledata_donutwhite(DivID,serviceCount,drinkCount,billCount,likecount){
	
	$('#'+DivID).highcharts({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 15
            }
        },
        title: {
            text: '<span style="font-size:19px;font-family:arial;font-weight:bold;color:#c1004f;">' +DivID + '</span>' 
        },
        subtitle: {
            text: ''
        },
        exporting:{enabled:false},
        plotOptions: {
            pie: {
                innerSize: 50,
                depth: 15
            }
        },
        series: [{
            name: 'TableNo - '+DivID,
            data: [
                ['Service : '+serviceCount, serviceCount],
                ['Drink : '+drinkCount, drinkCount],
                ['Bill : '+billCount, billCount],
                ['GoodService : '+likecount, likecount]
            ],
            center: [150,60],
            size: 120
        }
		
        ]
	  });
}
function alltabledata_donutblack(DivID,serviceCount,drinkCount,billCount){
	
	$('#'+DivID).highcharts({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 15
            }
        },
        title: {
            text: '<span style="font-size:19px;font-family:arial;font-weight:bold;color:#c1004f;">' +DivID + '</span>' 
        },
        subtitle: {
            text: ''
        },
        exporting:{enabled:false},
        plotOptions: {
            pie: {
                innerSize: 50,
                depth: 15
            }
        },
        tooltip: {
        	shared: true,
            useHTML: true,
            headerFormat: '<small>{point.key}</small><table>',
            formatter: function() {
                return 'Request Count :<b>' + this.y+'</b>';
            }
        },
        series: [{
            name: 'TableNo - '+DivID,
            data: [
                ['Service : '+serviceCount, serviceCount],
                ['Drink : '+drinkCount, drinkCount],
                ['Bill : '+billCount, billCount]/*,
                ['Like : '+likecount, likecount]*/
            ],
            center: [150,60],
            size: 120
        }
		
        ]
	  });
}
function autocanceltable(){	
	    $('#autocancel').highcharts({
	    	/*colors: ['#BC49BC'],*/
	      chart: {
	          type: 'column',
	      },
	      title: {
	          text: ''//UnAttended Table
	      },
		        xAxis: {
		            categories: autocancel_tableno,
		        },
		        yAxis: {
		            title: {
		                text: 'Number of Times'
		            }
		        },
		        tooltip: {
		            formatter: function() {
		                return 'Request Count :<b>' + this.y+'</b>';
		            }
		        },
	      plotOptions: {
	          column: {
	              depth: 25
	          }
	      },
	        exporting: { enabled: false },
	      series: [{
	    	  name:"Tables",
	          data: autocancel_count,
	          dataLabels: {
	              enabled: true,
	              rotation: 0,
	              color: '#FFFFFF',
	              align: 'right',
	              format: '{point.y}', // one decimal
	              y: 10, // 10 pixels down from the top
	              style: {
	                  fontSize: '10px',
	                  fontFamily: 'Verdana, sans-serif'
	              }
	          }
	      }]
	  });
	      } 
function overall_requestcount(){
	$('#overallrequest').highcharts({ 
	
	        chart: {
	            type: 'pie',
	            options3d: {
	                enabled: true,
	                alpha: 35
	            }
	        },
	        title: {
	            text: ''//Over All Request Count
	        },
	        subtitle: {
	            text: ''
	        },
	        exporting:{enabled:false},
	        plotOptions: {
	            pie: {
	                innerSize: 70,
	                depth: 25
	            }
	        },
	        series: [{
	            name: 'Request Count ',
	            data: [
	                   ["Service : "+overallrequestservice[0],overallrequestservice[0]],
		                ["Bill : "+overallrequestbill[0],overallrequestbill[0]],
		                ["Drink : "+overallrequestwater[0],overallrequestwater[0]]
	            ],
	            //center: [550,140],
	            size: 250
	        }
			
	        ]
		  });
	}
function overall_requestcount1(){
	$('#overallrequest').highcharts({ 
	
	        chart: {
	            type: 'pie',
	            options3d: {
	                enabled: true,
	                alpha: 35
	            }
	        },
	        title: {
	            text: ''//Over All Request Count
	        },
	        subtitle: {
	            text: ''
	        },
	        exporting:{enabled:false},
	        plotOptions: {
	            pie: {
	                innerSize: 70,
	                depth: 25
	            }
	        },
	        series: [{
	            name: 'Request Count ',
	            data: [
	                   ["Service : "+overallrequestservice[0],overallrequestservice[0]],
	                   ["Bill : "+overallrequestbill[0],overallrequestbill[0]],
	                   ["Drink : "+overallrequestwater[0],overallrequestwater[0]],
	                   ["GoodService : "+overallrequestgoodservice[0],overallrequestgoodservice[0]]
	            ],
	            //center: [550,140],
	            size: 250
	        }
			
	        ]
		  });
	}
function serviceavg_function(){    
    $('#serviceavg').highcharts({
    	chart: {
            type: 'column'
        },
      title: {
          text: ''//Service Average Response
      },
	        xAxis: {
	            categories: serviceavg_tableno,
	        },
	        yAxis: {
	            title: {
	                text: 'Service Average (in minutes)'
	            }
	        },
	        tooltip: {
	            formatter: function() {
	                return 'Request Count :<b>' + this.y+'</b>' ;
	            }
	        },
      plotOptions: {
          column: {
              depth: 25
          }
      },
        exporting: { enabled: false },
      series: [{
    	  name:"Table No",
          data: serviceavg_count,
          dataLabels: {
              enabled: true,
              rotation: 0,
              color: '#FFFFFF',
              align: 'right',
              format: '{point.y}', // one decimal
              y: 10, // 10 pixels down from the top
              style: {
                  fontSize: '10px',
                  fontFamily: 'Verdana, sans-serif'
              }
          }
      }]
  });
      }
function drinkavg_function(){    
    $('#drinkavg').highcharts({
    	chart: {
            type: 'column'
        },
      title: {
          text: ''//Drink Average Response
      },
	        xAxis: {
	            categories: drinkavg_tableno,
	        },
	        yAxis: {
	            title: {
	                text: 'Drink Average (in minutes)'
	            }
	        },
	        tooltip: {
	            formatter: function() {
	                return 'Request Count :<b>' + this.y+'</b>' ;
	            }
	        },
      plotOptions: {
          column: {
              depth: 25
          }
      },
        exporting: { enabled: false },
      series: [{
    	  name:"Table No",
          data: drinkavg_count,
          dataLabels: {
              enabled: true,
              rotation: 0,
              color: '#FFFFFF',
              align: 'right',
              format: '{point.y}', // one decimal
              y: 10, // 10 pixels down from the top
              style: {
                  fontSize: '10px',
                  fontFamily: 'Verdana, sans-serif'
              }
          }
      }]
  });
      }
function billavg_function(){    
    $('#billavg').highcharts({
    	chart: {
            type: 'column'
        },
      title: {
          text: ''//Bill Average Response
      },
      xAxis: {
          type: 'category',
          labels: {
              rotation: -95,
              style: {
                  fontSize: '13px',
                  fontFamily: 'Verdana, sans-serif'
              }
          }
      }
      ,
      xAxis: {
          categories: billavg_tableno
      },
      yAxis: {
          min: 0,
          title: {
              text: 'Response (in Minutes)'
          }
      },
	        tooltip: {
	            formatter: function() {
	                return 'Request Count :<b>' + this.y+'</b>' ;
	            }
	        },
      plotOptions: {
          column: {
              depth: 25
          }
      },
        exporting: { enabled: false },
      series: [{
    	  name:"Table No",
          data: billavg_count,
          dataLabels: {
              enabled: true,
              rotation: 0,
              color: '#FFFFFF',
              align: 'right',
              format: '{point.y}', // one decimal
              y: 10, // 10 pixels down from the top
              style: {
                  fontSize: '10px',
                  fontFamily: 'Verdana, sans-serif'
              }
          }
      }]
  });
      }
function daywisedata_function(){    
    $('#daywisedata').highcharts({
      chart: {
          renderTo: 'container',
          type: 'column',
          margin: 85,
          options3d: {
              enabled: true,
              alpha: 3,
              beta: 15,
              depth: 50,
              viewDistance: 120
          }
      },
      title: {
          text: ''//Daywise Performance
      },
	        xAxis: {
	            categories: daywisedata_date
	        },
	        yAxis: {
	            title: {
	                text: 'Request Count'
	            }
	        },
	        tooltip: {
	            formatter: function() {
	                return 'Request Count :<b>' + this.y+'</b>' ;
	            }
	        },
      plotOptions: {
          column: {
              depth: 25
          }
      },
        exporting: { enabled: false },
      series: [{
    	  name:"Request Count",
          data: daywisedata_requestcount
      }]
  });
      }
function daywiserequest_function(){    
	 $('#daywiserequestdata').highcharts({
	        chart: {
	            type: 'bar'
	        },
	        title: {
	            text: ''//Daywise Request
	        },
	        xAxis: {
	            categories: daywiserequestdata_date
	        },
	        exporting: { enabled: false },
	        yAxis: {
	            min: 0,
	            title: {
	                text: ''
	            }
	        },
	        legend: {
	            reversed: true
	        },
	        plotOptions: {
	            series: {
	                stacking: 'normal'
	            }
	        },
	        series: [{
	            name: 'Service',
	            data: daywiserequestdata_service
	        }, {
	            name: 'Drink',
	            data: daywiserequestdata_water
	        }/*, {
	            name: 'Like',
	            data: daywiserequestdata_like
	        }*/, {
	            name: 'Bill',
	            data: daywiserequestdata_bill
	        }]
	    });
      }
function daywiserequest_functionwhite(){    
	 $('#daywiserequestdata').highcharts({
	        chart: {
	            type: 'bar'
	        },
	        title: {
	            text: ''//Daywise Request
	        },
	        xAxis: {
	            categories: daywiserequestdata_date
	        },
	        exporting: { enabled: false },
	        yAxis: {
	            min: 0,
	            title: {
	                text: ''
	            }
	        },
	        legend: {
	            reversed: true
	        },
	        plotOptions: {
	            series: {
	                stacking: 'normal'
	            }
	        },
	        series: [{
	            name: 'Service',
	            data: daywiserequestdata_service
	        }, {
	            name: 'Drink',
	            data: daywiserequestdata_water
	        }, {
	            name: 'GoodService',
	            data: daywiserequestdata_like
	        }, {
	            name: 'Bill',
	            data: daywiserequestdata_bill
	        }]
	    });
     }
function waiterwiserequest_function(){    
	 $('#waiterwise').highcharts({
	        chart: {
	            type: 'bar'
	        },
	        title: {
	            text: ''//Waiterwiserequest
	        },
	        xAxis: {
	            categories: waiterwiserequest_username
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: ''
	            }
	        },
	        exporting: { enabled: false },
	        legend: {
	            reversed: true
	        },
	        plotOptions: {
	            series: {
	                stacking: 'normal'
	            }
	        },
	        series: [{
	            name: 'Service',
	            data: waiterwiserequest_service
	        }, {
	            name: 'Drink',
	            data: waiterwiserequest_drink
	        }, {
	            name: 'Like',
	            data: waiterwiserequest_like
	        }, {
	            name: 'Bill',
	            data: waiterwiserequest_bill
	        }]
	    });
     }
function protuctive_Hours(){
    $('#productive').highcharts({
	        chart: {
	            zoomType: 'x'
	        },
	        title: {
	            text: ''//Productive Hours
	        },
	        yAxis: {
	            title: {
	                text: 'Requests Count'
	            },
	            min: 0
	        },
	        xAxis: {
	            title: {
	                text: 'Hours'
	            },
	            categories: mostrequested_hour,
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
	                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]]
	                },
	                marker: {
	                    radius: 2
	                },
	                lineWidth: 2,
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
	            name: 'Requests Count',
	            data: mostrequested_count,
	        }]
   });
}
function orderby(TableNoArray,pTableArrayName){
	TableArrayName=new Array();
	TableArrayName=pTableArrayName;
	for(var nIdx=0;nIdx<TableNoArray.length;nIdx++)
	{
		for(var nNIdx=nIdx+1;nNIdx<TableNoArray.length;nNIdx++)
		{
			if(TableNoArray[nIdx]<TableNoArray[nNIdx])
			{
				nvalue=TableNoArray[nIdx];
				TableNoArray[nIdx]=TableNoArray[nNIdx];
				TableNoArray[nNIdx]=nvalue;
				
				nkey=TableArrayName[nIdx];
				TableArrayName[nIdx]=TableArrayName[nNIdx];
				TableArrayName[nNIdx]=nkey;
				//nNIdx=TableNoArray.length;
			}
		}
	}
	return TableNoArray;
}