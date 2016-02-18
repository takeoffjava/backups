/*This script for avoid back button in browser*/
window.history.forward();
function noBack() {
	window.history.forward();
}

/* Custom JS for Popup WEindow */
$(document).ready(function() {
	$('#btnSignIn').click(function() {
		login();
	});
});

/* This for enterkey acting in page buttons */
function inputKeyUp(e) {
	e.which = e.which || e.keyCode;
	if (e.which == 13) {
		login();
	}
}

function inputKeyUpotp(e) {
	e.which = e.which || e.keyCode;
	if (e.which == 13) {
		otpverification();
	}
}

/* Mobilenumber Validation JQuery keycode and QRCode advance amount & Allow only numbers*/
/*$('#mobilenumber,#mobile,#advanceamount,#qrcount,#txtmobile,#minkms,#mincharge,#ridecharge,#vehileyear').keypress(function(e) {
 if ((e.which >= 32 && e.which <= 47) || (e.which >= 58 && e.which <= 126))		
 return false;

 });*/
/*^([0-9]?[0-9]?[0-9]?|[0-9]+[\.]+[\@]+[\-])+()$*/
/*function NumAndTwoDecimals(e, field) {
	var val = field.value;
	var re = /^([0-9]?[0-9]?[0-9]?|[0-9]+)$/g;
	var re1 = /^([0-9]?[0-9]?[0-9]?|[0-9]+)/g;
	if (re.test(val)) {
		//do something here

	} else {
		val = re1.exec(val);
		if (val) {
			field.value = val[0];
		} else {
			field.value = "";
		}
	}
}

function NumAnddot(e, field) {
	var val = field.value;
	var re = /^([0-9]?[0-9]?[0-9]?|[0-9]+[\.])+()$/g;
	var re1 = /^([0-9]?[0-9]?[0-9]?|[0-9]+[\.])/g;
	if (re.test(val)) {
		//do something here

	} else {
		val = re1.exec(val);
		if (val) {
			field.value = val[0];
		} else {
			field.value = "";
		}
	}
}*/

/*Allow Only Numbers*/
$('#mobilenumber,#mobile,#txtmobile,#vehileyear,#seatcount,#otpbox,#qrcount,#advanceamount').on('input', function (event) { 
    this.value = this.value.replace(/[^0-9]/g, '');
});

/*Allow Only Numbers and Dots*/
$('#east,#west,#south,#north,#minkms,#mincharge,#ridecharge,#wallet,#txtKms').on('input', function (event) { 
    this.value = this.value.replace(/[^0-9.]/g, '');
});

/*function NumAnddot(e, field) {
	var val = field.value;
	var re = /^([0-9]?[0-9]?[0-9]?|[0-9]+[\.])+()$/g;
	var re1 = /^([0-9]?[0-9]?[0-9]?|[0-9]+[\.])/g;
	if (re.test(val)) {
		//do something here

	} else {
		val = re1.exec(val);
		if (val) {
			field.value = val[0];
		} else {
			field.value = "";
		}
	}
}*/
/*This for QRCode name only allow character ,dot and space*/
$('#name,#txtUsername').keypress(
		function(e) {
			if ((e.which >= 33 && e.which <= 45)
					|| (e.which >= 47 && e.which <= 64)
					|| (e.which >= 91 && e.which <= 96)
					|| (e.which >= 123 && e.which <= 126))
				return false;
		});

/* locationname Validation JQuery keycode */
$('#locationname').keypress(
		function(e) {
			if ((e.which >= 33 && e.which <= 45)
					|| (e.which >= 58 && e.which <= 64)
					|| (e.which >= 91 && e.which <= 96)
					|| (e.which >= 123 && e.which <= 126) || (e.which === 47))
				return false;
		});

/* Password Validation JQuery keycode */
$('#password,#txtpassword').keypress(
		function(e) {
			if (e.which === 32 || e.which === 34 || e.which === 37
					|| e.which === 38 || e.which === 39 || e.which === 40
					|| e.which === 41 || e.which === 43 || e.which === 44
					|| e.which === 47 || e.which === 58 || e.which === 59
					|| e.which === 60 || e.which === 61 || e.which === 62
					|| e.which === 63 || e.which === 91 || e.which === 93
					|| e.which === 94 || e.which === 96 || e.which === 123
					|| e.which === 124 || e.which === 125 || e.which === 126)
				return false;
		});

/* OTP Validation JQuery keycode */
$('#otpbox').keypress(
		function(e) {
			if ((e.which >= 32 && e.which <= 47)
					|| (e.which >= 58 && e.which <= 64)
					|| (e.which >= 91 && e.which <= 96)
					|| (e.which >= 123 && e.which <= 126))
				return false;
		});

/* OTP popup text box clear jquery */
$("#otpverification,#resendotp").on('click', function() {
	$('input[type="text"]').val('');
});

function textbox_validation_dot(value){
	var array = value.split(".");
	for(var indx=0;indx<array.length;indx++){
		if(array[indx]===""&&array[indx++]===""){
			return 0;
		}
	}
	return 1;
}
function checkDate(field)
{
	var dateformat = field.split(" ");
	
	var date0=Date.parse(dateformat[0]).toString();
	var date1=date0.substr(0, 7);
	var date2=Date.now().toString();
	var date3=date2.substr(0, 7);
	
	if(Date.parse(dateformat[0]) > Date.now()){
		return true;
	}
	else
	{
		errMsg("Check the date");
		return false;
	}
	if(date1===date3){
		errMsg("Same Date...");
		return false;
	}
	return true;
}
function from_tocompareDate(from,to)
{
	var fromdate = from.split(" ");
	var todate = to.split(" ");
	
	var date0=Date.parse(fromdate[0]).toString();
	var date1=date0.substr(0, 7);
	var date2=Date.parse(todate[0]).toString();
	var date3=date2.substr(0, 7);
	
	if(date1 > date3){
		errMsg("Check the date");
		return false;
	}
	else
	{		
		return true;
	}
	return true;
}