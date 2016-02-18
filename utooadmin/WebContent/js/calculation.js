/**
 * 
 */
var carmodel_id,booking_id,booking_status,booking_type,booking_number,tariff_id;
var passenger_name="";
var date;
var LOCATION_NAME;
var LOCATION_LAT;
var LOCATION_LAN;
var timetaken;
function calculateRide(kmValue,time,carModel_id)
{
	var retrievedObject = localStorage.getItem('tariff');
	var parsedObject=JSON.parse(retrievedObject);
	var strBaseMinKms="",strAfterMinKms="",strBaseWaitingChrge="";
	var currentPrice;
	var strBaseFare;
	var strPeakCount;
	for(var i=0;i<parsedObject.length;i++)
	{
		var parsedModelID=parsedObject[i].carmodel_id.car_model_id;
	if(parsedModelID===carModel_id)
		{
		tariff_id=parsedObject[i].tariff_id;
	 strBaseMinKms=parsedObject[i].min_kms;
	 strAfterMinKms=parsedObject[i].after_minkms;
	 strBaseWaitingChrge=parsedObject[i].waitingchrg_per_min;
	 strPeakCount=1;
	 strBaseFare=parsedObject[i].flat_amount;
	 }}
	if (kmValue > parseFloat(strBaseMinKms)) {
	    currentPrice = parseFloat(strBaseFare)
	            + ((kmValue - parseFloat(strBaseMinKms)) * (parseFloat(strAfterMinKms)))
	            + ((time * parseFloat(strBaseWaitingChrge)) * parseFloat(strPeakCount));
	} else {
	 currentPrice = parseFloat(strBaseFare)
	            + ((time * parseFloat(strBaseWaitingChrge)) * parseFloat(strPeakCount));
	
}
	
	callDriverEndTrip(kmValue,timetaken,currentPrice,booking_number,tariff_id);
return currentPrice;
	}

function getDriver_calculation()
{
	

	var access_id=  $("#selDrivers option:selected").val();
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'getDriverBooking';
	var method = "POST";
		objectForPost = {	
				odrivers:{
				access_id : access_id	}
		};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'getDriver_calculationCallback');
}
function getDriver_calculationCallback(resultObject){
	var kmDriver=$("#txtKms").val();
	var kmDriver_validation=textbox_validation_dot(kmDriver);
	if(kmDriver_validation===0 ){
		errMsg("Please enter a valid input");
		return false;
	}
	if(kmDriver_validation==="")
		{
		errMsg("Field Empty");
		return false;
		}
	if(resultObject.status===1)
		{
		var kmDriver=$("#txtKms").val();
		
		var carModel_id=resultObject.response.odrivers.ocars_feature.ocars_model.car_model_id;
		if(typeof(resultObject.response.departure_dat)==="undefined")
			errMsg("Ride Start time is not available");
		timetaken=getTimeDifference(resultObject.response.admin_reached);
		booking_number=resultObject.response.booking_number;
		var totalPrice=calculateRide(kmDriver,timetaken,carModel_id);
		$("#divContentCalc").html("<h3 style='font-weight:300;margin:0 0 10px;'>Booking Details</h3><div class='halfColumn' id='tblResult'><p>Booking Number</p><p>Source</p><p>Destination</p><p>Ride Time</p><p>Ride Charge</p></div>");
		var resultRow="<div class='halfColumn resCalc'><p>"+resultObject.response.booking_number+"</p><p>"+resultObject.response.booked_source+"</p><p>"+LOCATION_NAME+"</p><p>"+timetaken+" minutes</p><p>"+totalPrice+"</p><div>";
		$("#divContentCalc").append(resultRow);
		$('#endtrippopup').U2Popup().close();
		}
	else
		errMsg("No booking available for that driver");
}
function loadDriver()
{
	var retrievedObject = localStorage.getItem('drivers');
	var parsedObject=JSON.parse(retrievedObject);
	 $('#selDrivers').empty();
     $("#selDrivers").append($('<option></option>').val(-1).html("Select Driver"));
     $.each(parsedObject, function (key, value) {
         $("#selDrivers").append($('<option></option>').val(value.access_id).html(value.driver_name));
     });}
     function refreshClick()
     {
    	 $("#txtKms").val("");
    	 window.location.reload();
      	
     }
     function getTimeDifference(departureDate)
     {
    	  date = new Date();
    	  var dt  = departureDate.split(/\-|\s/);
    	    var dat = new Date(dt.slice(0,3).reverse().join('/')+' '+dt[3]);
  
    	 var difference = date.getTime() - dat.getTime(); // This will give difference in milliseconds
    	 var resultInMinutes = Math.round(difference / 60000);
    	 return resultInMinutes;
     }
   
     function callDriverEndTrip(distance,timetaken,amount,bookingNo,tariff_id)
     {
    		var driver_access_id=  $("#selDrivers option:selected").val();
    	
    		//var dformat=date.getFullYear() + "-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
    		 var now_utc = new Date(date.getUTCFullYear(), date.getUTCMonth(), date.getUTCDate(),  date.getUTCHours(), date.getUTCMinutes(), date.getUTCSeconds());
     		var dformat=now_utc.getFullYear() + "-"+(now_utc.getMonth()+1)+"-"+now_utc.getDate()+" "+now_utc.getHours()+":"+now_utc.getMinutes()+":"+now_utc.getSeconds();
    		 var objectForPost = null;
    		var contentType = 'application/json; charset=utf-8';
    		var servletPath = 'invoice';
    		var method = "POST";
    		var kmDriver_validation=textbox_validation_dot(distance);
    		if(kmDriver_validation===0 ){
    			errMsg("Please enter a valid input");
    			return false;
    		}
    		if(kmDriver_validation==="")
    			{
    			errMsg("Field Empty");
    			return false;
    			}
    			objectForPost = {	
    					distance : distance,
    					total_mins : timetaken,
    					amount : amount,
    					obooking :
    					{booking_number :bookingNo,
    					booked_destination : LOCATION_NAME,
    					reaching_dat : dformat,
    					reaching_latitude : LOCATION_LAT,
    					reaching_longitude: LOCATION_LAN,
    					},
    					otariff :{ tariff_id : tariff_id},
    					odrivers : {access_id : driver_access_id}
    			};
    		
    			jQAjaxCallForAccountJSON(servletPath, method, contentType,
    					objectForPost, 'callDriverEndTripCallback'); 
     }
     function callDriverEndTripCallback(resultObject)
     {
     }
     function endtrippopup(){
    	 
    		if($("#selDrivers option:selected").val()==="-1"){
    			errMsg("Select Drivers");
    			return false;
    		}
    		if($("#txtKms").val().trim()===""){
    			errMsg("Kilometers should not be empty");
    			return false;
    		}
    		if($("#searchTextField").val().trim()===""){
    			errMsg("Select Destination");
    			return false;
    		}
    		$('#endtrippopup').U2Popup(
    				{
    					title : 'End Trip',
    					width : 600,
    					height : 175,
    					onClose : function() {
    					
    					}
    				});
    		}
     
     function initialize() {
    	 $('#searchTextField').val("");
    	    var input = document.getElementById('searchTextField');
    	    var autocomplete = new google.maps.places.Autocomplete(input);
    	    google.maps.event.addListener(autocomplete, 'place_changed', function () {
    	        var place = autocomplete.getPlace();
    	         LOCATION_NAME=place.name;
    	         LOCATION_LAT=place.geometry.location.lat();
    	         LOCATION_LAN=place.geometry.location.lng();
    	        

    	    });
    	}
    	google.maps.event.addDomListener(window, 'load', initialize);