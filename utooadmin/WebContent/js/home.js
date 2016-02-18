var LAST_DRIVER_ID = 1, DRIVER_DIV = "", SCROLL_POS=0, MAP, MARKER,BOOKING_ID;
var MARKER_List=[];
var infowindow;

function getdrivers(Scroll_POS, resultsMap) {	
	MAP = resultsMap;
	SCROLL_POS = Scroll_POS;
	var driver_name = $("#txtSearch").val();
	var driver_status = $("#driver_status").val();
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = "";
	var method = "POST";
	if (Scroll_POS === 0) {
		servletPath = 'getall_Initialdrivers';
		objectForPost = {
			driver_name : ""
		};
	} else {
		servletPath = 'getall_driverspagination';
		objectForPost = {
			driver_id : LAST_DRIVER_ID,
			MaximumRow : "10",
			driver_name : driver_name,
			status : driver_status
		};
	}

	jQAjaxCallForAccountJSON(servletPath, method, contentType, objectForPost,
			'getdriversCallback');
	 infowindow = new google.maps.InfoWindow({
			maxWidth : 160
		});
}
function getdriversCallback(resultObject) {
	var empty_occupiedimage = "",Address="";
	if(resultObject.status===0){
		errMsg("No Drivers");
		return false;
	}
	if(resultObject.response.length===0){
		errMsg("No Drivers");
		return false;
	}
	if (SCROLL_POS === 0) {
		if (resultObject.status === 1) {
			document.getElementById("welcome_name").innerHTML = "Welcome "
					+ localStorage.getItem("username");
			+" !";
			for (var indx = 0; indx < resultObject.response.length; indx++) {
				if (resultObject.response[indx].status === 0){
					empty_occupiedimage = "class='partnerEmpty'";
				}
				else if (resultObject.response[indx].status === 1){
					empty_occupiedimage = "class='partnerOccupied'";

				}
				else{
					empty_occupiedimage = "class='partnerOffline'";

					
				}
				var resString = JSON.stringify(resultObject.response[indx]);
				if(typeof resultObject.response[indx].address ==="undefined")
					Address=resultObject.response[indx].mobile;
				else
					Address=resultObject.response[indx].address;
				DRIVER_DIV = DRIVER_DIV
						+ "<div class='partnerBox' data-resultset='"
						+ resString
						+ "'>"
						+ "<div><img src='"+localStorage.getItem("base_path")+"/"+localStorage.getItem("base_folder")+"/"+localStorage.getItem("driver_profile_photo")+"/"+resultObject.response[indx].photo_file_id+"' class='partnerImg' /> "
						+ "<div "
						+ empty_occupiedimage
						+ "></div></div><div class='partnerContent'><div class='contentHeader'>"
						+ "" + resultObject.response[indx].driver_name
						+ "</div><div class='contentSection'> " + ""
						+ Address + " "
						+ "</div></div></div>";
				LAST_DRIVER_ID = resultObject.response[indx].driver_id;
				create_Marker(resultObject.response[indx].latitude,
						resultObject.response[indx].longitude,
						resultObject.response[indx].driver_name, indx);

			}
		} else {
			return false;
		}
		$("#drivers_content").append(DRIVER_DIV);
	} else {
		
		if (resultObject.status === 1)
		{
			var paginate_driver_div = "",Address="";
			if(LAST_DRIVER_ID===1)
			removeMarkers();
			for (var indx = 0; indx < resultObject.response.length; indx++) {
				if (resultObject.response[indx].status === 0){
					empty_occupiedimage = "class='partnerEmpty'";
				}
				else if (resultObject.response[indx].status === 1){
					empty_occupiedimage = "class='partnerOccupied'";
				}
				else{
					empty_occupiedimage = "class='partnerOffline'";
					
				}
				var resString = JSON.stringify(resultObject.response[indx]);
				if(typeof resultObject.response[indx].address ==="undefined")
					Address=resultObject.response[indx].mobile;
				else
					Address=resultObject.response[indx].address;
				paginate_driver_div = paginate_driver_div
						+ "<div class='partnerBox' data-resultset='"
						+ resString
						+ "' >"
						+ "<div><img src='"+localStorage.getItem("base_path")+"/"+localStorage.getItem("base_folder")+"/"+localStorage.getItem("driver_profile_photo")+"/"+resultObject.response[indx].photo_file_id+"' class='partnerImg' /> "
						+ "<div "
						+ empty_occupiedimage
						+ "></div></div><div class='partnerContent'><div class='contentHeader'>"
						+ "" + resultObject.response[indx].driver_name
						+ "</div><div class='contentSection'> " + ""
						+ Address + " "
						+ "</div></div></div>";
				LAST_DRIVER_ID = resultObject.response[indx].driver_id;
				
				
				create_Marker(resultObject.response[indx].latitude,
						resultObject.response[indx].longitude,
						resultObject.response[indx].driver_name, indx);
				
			}
			$("#drivers_content").append(paginate_driver_div);
		} else {
			return false;
		}
	}
}

function getdrivers_onchange() {
	var driver_name = $("#txtSearch").val();
	var driver_status = $("#driver_status").val();
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = "getall_driverspagination";
	var method = "POST";
	objectForPost = {
		driver_id : 0,
		MaximumRow : "10",
		driver_name : driver_name,
		status : driver_status
	};

	jQAjaxCallForAccountJSON(servletPath, method, contentType, objectForPost,
			'getdrivers_onchangeCallback');
}
function getdrivers_onchangeCallback(resultObject) {
	/*var infowindow = new google.maps.InfoWindow({
		maxWidth : 160
	});*/
	if(resultObject.status===0){
		removeMarkers();
		errMsg("No Drivers");
		return false;
	}/*
	if(resultObject.response.length===0){
		$("#drivers_content").hide();
		errMsg("No Drivers");
	}*/else{
		$("#drivers_content").show();
	}
	var empty_occupiedimage = "";
	if (resultObject.status === 1) {
		if(resultObject.response.length===0){
			Materialize.toast('No Drivers ...!', 4000);
		}else{
		DRIVER_DIV = "";
		$("#drivers_content").html("");
		if (SCROLL_POS === 0)
		removeMarkers();
		for (var indx = 0; indx < resultObject.response.length; indx++) {
			if (resultObject.response[indx].status === 0){
				empty_occupiedimage = "class='partnerEmpty'";
			}
			else if (resultObject.response[indx].status === 1){
				empty_occupiedimage = "class='partnerOccupied'";
			}
			else{
				empty_occupiedimage = "class='partnerOffline'";				
			}
			var resString = JSON.stringify(resultObject.response[indx]);
			if(typeof resultObject.response[indx].address ==="undefined")
				Address=resultObject.response[indx].mobile;
			else
				Address=resultObject.response[indx].address;
			DRIVER_DIV = DRIVER_DIV
					+ "<div class='partnerBox' data-resultset='"
					+ resString
					+ "'><div><img src='"+localStorage.getItem("base_path")+"/"+localStorage.getItem("base_folder")+"/"+localStorage.getItem("driver_profile_photo")+"/"+resultObject.response[indx].photo_file_id+"' class='partnerImg' /> "
					+ "<div "
					+ empty_occupiedimage
					+ "></div></div><div class='partnerContent'><div class='contentHeader'>"
					+ "" + resultObject.response[indx].driver_name
					+ "</div><div class='contentSection'> "
					+ Address + " "
					+ "</div></div></div>";
			LAST_DRIVER_ID = resultObject.response[indx].driver_id;
			
			create_Marker(resultObject.response[indx].latitude,
					resultObject.response[indx].longitude,
					resultObject.response[indx].driver_name, indx);
			if($("#txtSearch").val()!==""){
			infowindow.setContent("<p style='margin:5px 0;'>"
					+ resultObject.response[indx].driver_name + "</p>");
			infowindow.open(MAP, MARKER);
			}
		}
		}
	} else {
		return false;
	}
	$("#drivers_content").append(DRIVER_DIV);
}
function getdrivers_onchangelocation(lat, lng, resultsMap) {
	MAP = resultsMap;
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = "getall_driverslocation";
	var method = "POST";
	objectForPost = {
		latitude : lat,
		longitude : lng
	};
	jQAjaxCallForAccountJSON(servletPath, method, contentType, objectForPost,
			'getdrivers_onchangelocationCallback');
}
function getdrivers_onchangelocationCallback(resultObject) {
	if(resultObject.status===0){
		errMsg("No Drivers");
		return false;
	}else{
/*		$("#drivers_content").show();*/
	}
	var empty_occupiedimage = "";
	if (resultObject.status === 1) {
		if(resultObject.response.length===0){
			Materialize.toast('No Drivers ...!', 4000);
		}else{
		DRIVER_DIV = "";
		$("#drivers_content").html("");
		for (var indx = 0; indx < resultObject.response.length; indx++) {
			if (resultObject.response[indx].status === 0){
				empty_occupiedimage = "class='partnerEmpty'";
			}
			else if (resultObject.response[indx].status === 1){
				empty_occupiedimage = "class='partnerOccupied'";
			}
			else{
				empty_occupiedimage = "class='partnerOffline'";				
			}
			var resString = JSON.stringify(resultObject.response[indx]);
			if(typeof resultObject.response[indx].address ==="undefined")
				Address=resultObject.response[indx].mobile;
			else
				Address=resultObject.response[indx].address;
			DRIVER_DIV = DRIVER_DIV
					+ "<div class='partnerBox' data-resultset='"
					+ resString
					+ "'><div><img src='"+localStorage.getItem("base_path")+"/"+localStorage.getItem("base_folder")+"/"+localStorage.getItem("driver_profile_photo")+"/"+resultObject.response[indx].photo_file_id+"' class='partnerImg' /> "
					+ "<div "
					+ empty_occupiedimage
					+ "></div></div><div class='partnerContent'><div class='contentHeader'>"
					+ "" + resultObject.response[indx].driver_name
					+ "</div><div class='contentSection'> "
					+ Address + " "
					+ "</div></div></div>";
			
			create_Marker(resultObject.response[indx].latitude,
					resultObject.response[indx].longitude,
					resultObject.response[indx].driver_name, indx);
		}
		}
	} else {
		return false;
	}
	$("#drivers_content").append(DRIVER_DIV);
}
/* Slider Start */

$('body')
		.on(
				"click",
				".partnerBox",
				function() {
					var licenseFront="",licenseBack="",car_photo="";
					var resObjset = $(this).data("resultset");
					

					if(resObjset.license_front_file_ID===null || resObjset.license_front_file_ID==="")
						{
						licenseFront="default.png";
						}
					else
						licenseFront=resObjset.license_front_file_ID;
					if(resObjset.license_back_file_ID===null || resObjset.license_back_file_ID==="")
					{
						licenseBack="default.png";
					}
					else
						licenseBack=resObjset.license_back_file_ID;
				
					if(typeof resObjset.car_file_ID==="undefined"){
						car_photo="default.png";
					}else{
						car_photo=resObjset.car_file_ID;
					}
					
					$("#slider_drivername").html(resObjset.driver_name);
					$("#slider_mobile").html(resObjset.mobile);
					$("#slider_email").html(resObjset.email);
					$("#slider_licenceno").html(resObjset.license_number);
					$("#partnerIMG1").attr("src", localStorage.getItem("base_path")+"/"+localStorage.getItem("base_folder")+"/"+localStorage.getItem("driver_profile_photo")+"/"+resObjset.photo_file_id);	
					if(typeof resObjset.ocars_feature!=="undefined")
					$("#carImageDriver").attr("src", localStorage.getItem("base_path")+"/"+localStorage.getItem("base_folder")+"/"+localStorage.getItem("car")+"/"+car_photo);
					else
						$("#carImageDriver").attr("src", localStorage.getItem("base_path")+"/"+localStorage.getItem("base_folder")+"/"+localStorage.getItem("car")+"/default.png");
					$("#licenseFront").attr("src", localStorage.getItem("base_path")+"/"+localStorage.getItem("base_folder")+"/"+localStorage.getItem("license")+"/"+licenseFront);
					$("#licenseBack").attr("src", localStorage.getItem("base_path")+"/"+localStorage.getItem("base_folder")+"/"+localStorage.getItem("license")+"/"+licenseBack);
					$("#slider_driverid").html(resObjset.driver_id);
					if(typeof resObjset.ocars_feature!=="undefined")
					$("#slider_carmodel").html(resObjset.ocars_feature.ocars_model.car_model_name);
					else
						$("#slider_carmodel").html("");
					if(typeof resObjset.ocars_feature!=="undefined")
					$("#slider_carcolor").html(resObjset.ocars_feature.color);
					else
						$("#slider_carcolor").html("");
					if(typeof resObjset.ocars_feature!=="undefined")
					$("#slider_carmanuyear").html(
							resObjset.ocars_feature.vehicle_year);
					else
						$("#slider_carmanuyear").html("");
					if(typeof resObjset.ocars_feature!=="undefined")
					$("#slider_carseats").html(
							resObjset.ocars_feature.seat_count);
					else
						$("#slider_carseats").html("");

					$("#partnerstatus").removeAttr("class");
					if (resObjset.status === 0) {// 0-Available (Free)
						$("#partnerstatus").addClass("partnerStatus");
						$("#partnerstatus").text("Available");
					} else if (resObjset.status === 1) {// 1-Busy (Occupied)
						$("#partnerstatus").addClass("partnerStatusbusy");
						$("#partnerstatus").text("Occupied");
					} else {// 2-Offline
						$("#partnerstatus").addClass("partnerStatusoffline");
						$("#partnerstatus").text("Offline");
					}
					/*if (resObjset.total_rating === 0) {

					}*/
					switch (resObjset.total_rating) {

					case 0:
						$("#rating_stars")
								.html(
										"<i class='icon_userLanding icon_starEmpty'></i>"
												+ "<i class='icon_userLanding icon_starEmpty'></i>"
												+ "<i class='icon_userLanding icon_starEmpty'></i>"
												+ "<i class='icon_userLanding icon_starEmpty'></i>"
												+ "<i class='icon_userLanding icon_starEmpty'></i>");
						break;
					case 1:
						$("#rating_stars")
								.html(
										"<i class='icon_userLanding icon_starFull'></i>"
												+ "<i class='icon_userLanding icon_starEmpty'></i>"
												+ "<i class='icon_userLanding icon_starEmpty'></i>"
												+ "<i class='icon_userLanding icon_starEmpty'></i>"
												+ "<i class='icon_userLanding icon_starEmpty'></i>");
						break;
					case 2:
						$("#rating_stars")
								.html(
										"<i class='icon_userLanding icon_starFull'></i>"
												+ "<i class='icon_userLanding icon_starFull'></i>"
												+ "<i class='icon_userLanding icon_starEmpty'></i>"
												+ "<i class='icon_userLanding icon_starEmpty'></i>"
												+ "<i class='icon_userLanding icon_starEmpty'></i>");
						break;
					case 3:
						$("#rating_stars")
								.html(
										"<i class='icon_userLanding icon_starFull'></i>"
												+ "<i class='icon_userLanding icon_starFull'></i>"
												+ "<i class='icon_userLanding icon_starFull'></i>"
												+ "<i class='icon_userLanding icon_starEmpty'></i>"
												+ "<i class='icon_userLanding icon_starEmpty'></i>");
						break;
					case 4:
						$("#rating_stars")
								.html(
										"<i class='icon_userLanding icon_starFull'></i>"
												+ "<i class='icon_userLanding icon_starFull'></i>"
												+ "<i class='icon_userLanding icon_starFull'></i>"
												+ "<i class='icon_userLanding icon_starFull'></i>"
												+ "<i class='icon_userLanding icon_starEmpty'></i>");
						break;
					default:
						$("#rating_stars")
								.html(
										"<i class='icon_userLanding icon_starFull'></i>"
												+ "<i class='icon_userLanding icon_starFull'></i>"
												+ "<i class='icon_userLanding icon_starFull'></i>"
												+ "<i class='icon_userLanding icon_starFull'></i>"
												+ "<i class='icon_userLanding icon_starFull'></i>");
					}
					if ($(this).hasClass('partnerBoxSelected')) {
						$(this).removeClass('partnerBoxSelected');
						$('#partnersDetails').animate({
							'margin-left' : '0'
						});
						$('.close').css({
							'display' : 'none'
						});
					} else {
						$('#partnersDetails').css({
							'display' : 'none'
						});
						$('.close').css({
							'display' : 'none'
						});
						$('#partnersDetails').animate({
							'margin-left' : '0'
						});
						$('.partnerBox').removeClass('partnerBoxSelected');
						$(this).addClass('partnerBoxSelected');
						$('#partnersDetails').css({
							'display' : 'block'
						});
						$('#partnersDetails').animate({
							'margin-left' : '25%'
						}, function() {
							$('.close').css({
								'display' : 'block'
							});
						});
					}
					if(resObjset.status!=1){
						$("#trackbuttondiv").hide();
					}else{
						getbooking_NO(resObjset.driver_id);
						$("#trackbuttondiv").show();
					}
				});

function create_Marker(lat, lan, drivername, indx) {
	
	MARKER = new google.maps.Marker({
		position : new google.maps.LatLng(lat, lan),
		map : MAP,
		icon : "images/car.png",
		title : drivername,
		animation : google.maps.Animation.DROP
	});
	MARKER_List.push(MARKER);
	google.maps.event.addListener(MARKER, 'click', (function(marker, indx) {
		return function() {
			infowindow.setContent("<div class='content' style='max-height:300px; font-size:12px;';'>" + drivername
					+ "</div>");
			infowindow.open(MAP, marker);
		}
	})(MARKER, indx));
}
function removeMarkers() {
	for (var i = 0; i < MARKER_List.length; i++) {
		MARKER_List[i].setMap(null);
	}
}
/*   Slider End */

function trackcar(){
	 window.open("http://www.utootaxi.com/utooadmin/track?"+BOOKING_ID);
}
function getbooking_NO(driverID){
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = "gettrackbookingno";
	var method = "POST";
		objectForPost = {
				odrivers : {
					driver_id : driverID
				}
		};
	
	jQAjaxCallForAccountJSON(servletPath, method, contentType, objectForPost,
			'getbooking_NOCallback');
}
function getbooking_NOCallback(resulobject){
	BOOKING_ID=resulobject.response[0];
}
function getdrivers_markers() {
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = "";
	var method = "POST";
		servletPath = 'getall_Initialdrivers';
		objectForPost = {
			driver_name : ""
		}
		jQAjaxCallForAccountJSON(servletPath, method, contentType, objectForPost,
		'getdrivers_markersCallback');
}
function getdrivers_markersCallback(resultObject){
	removeMarkers();
	for (var indx = 0; indx < resultObject.response.length; indx++) {
		create_Marker(resultObject.response[indx].latitude,
				resultObject.response[indx].longitude,
				resultObject.response[indx].driver_name, indx);		
	}
}
function setMapOnAll(map) {
	  for (var i = 0; i < markers.length; i++) {
	    markers[i].setMap(map);
	  }
	}
