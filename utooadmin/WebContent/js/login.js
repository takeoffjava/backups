function login(){
	
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'login';
	var mobile=document.getElementById("mobilenumber").value;
	var password=document.getElementById("password").value;
	var method = "POST";
	if(mobile==""){
		Materialize.toast('Mobilenumber should not be empty!', 4000);
		return false;
		}else if (mobile.length < 10) {
			Materialize.toast("please enter a valid mobile number", 4000);
			return false;
		} else if ($("#mobilenumber").val().substr(0, 1) != "7"
				&& $("#mobilenumber").val().substr(0, 1) != "8"
				&& $("#mobilenumber").val().substr(0, 1) != "9") {
			Materialize.toast("Please enter a valid mobile number", 4000);
			return false;
		}else if(password==""){
			Materialize.toast('Password should not be empty...!', 4000);
			return false;
		}
	
		objectForPost = {				
				mobile : mobile,
				password : password
		};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'loginCallback');
}
function loginCallback(resultobject){
	if(resultobject.status===0){
		Materialize.toast('Invalid Credentials', 4000);
		return false;
	}else{
		   $('input[type="text"],input[type="password"]').val('');
		   localStorage.setItem("username", resultobject.response.username);
		   localStorage.setItem("mobilenumber", resultobject.response.mobile);
		   localStorage.setItem("usertype", resultobject.response.objuser_type.usertype_name);
		   localStorage.setItem("accessid", resultobject.response.access_id);
		   getLookUp();
		   if(resultobject.response.objuser_type.usertype_name==="superuser"){
		   location.href="#popup_login";
		   
		   }
		   else
		   window.location.href="home";
		
	}
}
function otpverification()
{
	var superuserotp=$("#otpcode").val();
	if(superuserotp==="")
		errMsg("Invalid OTP");
	
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = "";
	var method = "POST";
	
		servletPath = 'verifyotp';
		objectForPost = {
				access_id: localStorage.getItem("accessid"),
				superuser_otp:superuserotp
			
		};
	
	jQAjaxCallForAccountJSON(servletPath, method, contentType, objectForPost,
			'getOtpCallback');
}

function getOtpCallback(resultObject)
{
	
	if(resultObject.status===1)
		{
	    getLookUp();
	    window.location.href="home";
		}
	else
		{
		errMsg("Invalid OTP");
		}
	
}
function getLookUp() {
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = "";
	var method = "POST";
	
		servletPath = 'getLookup';
		objectForPost = {
				is_all : false,
				is_response_codes : false,
				is_server_details : true,
				is_car_model_details : true,
				is_car_features_details : true,
				is_tariff_details : true,
				is_need_unbook_reason_list : true,
				is_need_location_list : false,
				is_need_driver_list : true
		};
	
	jQAjaxCallForAccountJSON(servletPath, method, contentType, objectForPost,
			'getLookupCallback');
}
function getLookupCallback(resultObject)
{
	if(resultObject.status===1)
		{
		localStorage.setItem("base_path", resultObject.response.server_path[0].base_path);
		localStorage.setItem("base_folder", resultObject.response.server_path[0].base_folder);
		localStorage.setItem("car", resultObject.response.server_path[0].car);
		localStorage.setItem("license", resultObject.response.server_path[0].license);
		localStorage.setItem("driver_profile_photo", resultObject.response.server_path[0].profile_photo);
		localStorage.setItem("passenger_profile_photo", resultObject.response.server_path[0].passenger_profile_photo);
		localStorage.setItem("accident_audio", resultObject.response.server_path[0].accident_audio);
		localStorage.setItem("accident_image", resultObject.response.server_path[0].accident_image);
		localStorage.setItem("qr_image", resultObject.response.server_path[0].qr_image);
		localStorage.setItem('unbook_reasons', JSON.stringify(resultObject.response.unbook_reasons));
		localStorage.setItem('tariff', JSON.stringify(resultObject.response.tariff));
		localStorage.setItem('drivers', JSON.stringify(resultObject.response.drivers));

		// Retrieve the object from storage

		}
}