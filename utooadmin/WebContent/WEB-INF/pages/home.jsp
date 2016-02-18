<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<head>

 <title>UTOO Admin Panel</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
<link href='http://fonts.googleapis.com/css?family=Open+Sans:300,400,700' rel='stylesheet' type='text/css'>
<!-- <link href="css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>  -->
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/jquery.mCustomScrollbar.css" />
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
 <link rel="stylesheet" href="css/amaran.min.css">
 <script src="js/plugins/jquery-1.11.1.min.js"></script>
    <script src="js/plugins/materialize.js"></script>
    <script src="js/plugins/modernizr-2.6.2.min.js"></script>
    <script type="text/javascript" src="js/base/utilities.js"></script>
    <script type="text/javascript" src="js/plugins/general.js"></script>
	<script src="https://cdn.pubnub.com/pubnub-3.7.20.js"></script>
   <script>
   
         var map;
        function initMap() {
        	//pubs_notify();
        	//getdrivers(0,null);
            map = new google.maps.Map(document.getElementById('mapSection'), {
                center: { lat: 13.116018, lng: 80.231665 },
                zoom: 13, 
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                zoomControl: true,
                zoomControlOptions: {
                    position: google.maps.ControlPosition.LEFT_CENTER
                },

            });
              
         // Create the search box and link it to the UI element.
  var input = document.getElementById('pac-input');
  var searchBox = new google.maps.places.SearchBox(input);
  map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

  // Bias the SearchBox results towards current map's viewport.
  map.addListener('bounds_changed', function() {
    searchBox.setBounds(map.getBounds());
    var bounds = map.getBounds();
  });

  var markers = [];
  var geocoder = new google.maps.Geocoder();

  searchBox.addListener('places_changed', function() {
    var places = searchBox.getPlaces();

    if (places.length == 0) {
      return;
    }
    geocodeAddress(geocoder, map);    
    // Clear out the old markers.
      markers.forEach(function(marker) {
      marker.setMap(null);
    });
    markers = [];  
    // For each place, get the icon, name and location.
    var bounds = new google.maps.LatLngBounds();
    map.fitBounds(bounds);
  });
  // [END region_getplaces]
  getdrivers(0,map);
  
  function geocodeAddress(geocoder, resultsMap) {
	 
        	  var address = document.getElementById('pac-input').value;
        	  geocoder.geocode({'address': address}, function(results, status) {
        	    if (status === google.maps.GeocoderStatus.OK) {
        	      resultsMap.setCenter(results[0].geometry.location);
        	      getdrivers_onchangelocation(results[0].geometry.location.lat(),results[0].geometry.location.lng(),resultsMap);
        	    } else {
        	      alert('Geocode was not successful for the following reason: ' + status);
        	    }
        	  });
        	}
}         
    </script> 
     <script src="https://maps.google.com/maps/api/js?libraries=places&callback=initMap" async defer></script> 
</head>
<body>
	<div class="floatIcon"><a href="javascript:void(0)" class="cornerIco" id="cornerIcon"><img src="images/DAIcon.png" width="65"/></a>
		<div id="cornerCont">
			<a class="corSubs" href="1" >Bookings</a>
			<a class="corSubs" href="2" >Invoice Insights</a>
			<a class="corSubs" href="3" >Completed Trip</a>
			<a class="corSubs" href="4" >Kilometers Driven</a>
			</div>
	
	</div>
    <header>
        <div id="header">
            <div id="logo"><a href="#"><img src="images/logo.png" width="60" /></a></div>
            <h3 class="mainHeadTitle" id="welcome_name"></h3>
            <div id="topBar">
            <a class="sideIndivTopMenu" href="qrcode"><img src="images/qrCode.png" class="qrIco" /> <span>QR CODE</span></a>
				<a class="sideIndivTopMenu" href="info"><i class="icon_set_top icon_car"></i> <span>INFO</span></a>
				<a class="sideIndivTopMenu" href="user"><i class="icon_set_top icon_admin"></i> <span id="usertype"></span></a>
			<!-- 	<a class="sideIndivTopMenu"><i class="icon_set_top icon_executive"></i> <span>EXECUTIVE</span></a> -->
				<a class="sideIndivTopMenu"><i class="icon_set_top icon_logout" onclick="return logout();"></i> <span>LOGOUT</span></a>
			</div>
        </div>
    </header>
    <section id="mainSection">
<div id="sideBar">
            <a class="sideIndivMenu active" href="home"><i class="icon_set icon_dash"></i> <span>DASHBOARD</span></a>
            <a class="sideIndivMenu" href="drivers"><i class="icon_set icon_driver"></i> <span>DRIVERS</span></a>
             <a class="sideIndivMenu" href="passenger"><i class="icon_set icon_pass"></i> <span>PASSENGER</span></a>
            <a class="sideIndivMenu" href="booking"><i class="icon_set icon_book"></i> <span>BOOKING</span></a>
            <a class="sideIndivMenu" href="tariff"><i class="icon_set icon_tariff"></i> <span>TARIFF</span></a>
            <a class="sideIndivMenu" href="promo"><i class="icon_set icon_promo"></i> <span>PROMO</span></a>
            <a class="sideIndivMenu" href="accident"><i class="icon_set icon_acci"></i> <span>ACCIDENT</span></a>
            <a class="sideIndivMenu" href="calculator"><i class="icon_set icon_calci"></i> <span>CALCULATOR</span></a>
           
        </div>
            </section>
    <section id="contentSection">
        <div id="contentContainer">
            <!--- partner Selected-->
            <div id="partnersDetails">
                <div id="close" class="close">
                    <img src="images/close.png" width="16"/>
                </div>
                <div class="scrollPartner">
                <div class="partnerSelected">
                    
                    <div class="media">
                     <img id="partnerIMG1" class="partnerImg" src="images/userimg/user-icon.png" />
                    <div class="media-body">
                        <div class="pull-right"><span  id="partnerstatus"></span></div>
                        <p class="partnerTitle" id="slider_drivername"></p>
                        <p class="margin_1" id="rating_stars">
                      
                        </p>
                    </div>
                    </div>
                    <h3 class="subHeadTitle">Personal Info</h3>
                    <table class="noBorder">
                    <tr><td class="dim"><p>Mobile</p></td><td><p id="slider_mobile"></p></td></tr>
                    <tr><td class="dim"><p>Email</p></td><td><p id="slider_email"></p></td></tr>
                    <tr><td class="dim"><p>License No</p></td><td><p id="slider_licenceno"></p></td></tr>
                    <tr><td class="dim"><p>Driver ID</p></td><td><p id="slider_driverid"></p></td></tr>
                    </table>
                    
                    <h3 class="subHeadTitle">Car Details</h3>
                    <table class="noBorder">
                    <tr><td class="dim"><p>Model</p></td><td><p id="slider_carmodel"></p></td></tr>
                    <tr><td class="dim"><p>Color</p></td><td><p id="slider_carcolor"></p></td></tr>
                    <tr><td class="dim"><p>Manufacture Year</p></td><td><p id="slider_carmanuyear"></p></td></tr>
                    <tr><td class="dim"><p>Seats</p></td><td><p id="slider_carseats"></p></td></tr>
                    </table>
                    <h3 class="subHeadTitle">Car Image</h3>
                    <div class="section group">
                     <div class="pull-left margin_lr_5"><img id="carImageDriver" src="images/taxi.png" width="140" height="100" /></div>
                    </div>
                    <h3 class="subHeadTitle">License Details</h3>
                    <div class="section group">
                        <div class="pull-left margin_lr_5"><img id="licenseFront" src="images/driverli1.jpg" width="140" height="100" /></div>
                        <div class="pull-left margin_lr_5"><img id="licenseBack" src="images/driverli2.jpg" width="140" height="100" /></div>
                        
                    </div>
                </div>
                    </div>
                <!--end of scroll-->
                <div class="partBtnCont" id="trackbuttondiv">
                    <button type='button' class='partBtn' id='trackbutton' onclick='return trackcar();'>TRACK</button>
                </div>
                
            </div>
            <!--- partner Selected-->
            
            <div id="partnerSection">
                <div class="searchBox">
                    <div class="containerSearch">
                        <input type="search" id="txtSearch" placeholder="Search Driver" onkeyup="inputKeyUp(event)" maxlength="50"/>
                        <div class="icon" onclick="return getdrivers_onchange();"><button class="searchIcon"><img src="images/search.png" /></button></div>
                    </div>
                </div>
                <div class="partnerslistBox">
                    <div class="headerBox">
                        <div class="headerLbl">
                             Partners
                        </div>
                        <div class="drpDwnCont">
                            <select class="drpDwn" id="driver_status" onchange="return getdrivers_onchange();">
                            <option name="All" value="-1">All</option>
                             <option name="Free" value="0">Available</option>
                            <option name="Occupied" value="1">Occupied</option>
                            <option name="Free" value="2">Offline</option>
                            </select>
                        </div>
                    </div>
                    <div class="partnersBox" id="partnersBox">
                       <div id="drivers_content"></div>
                    </div>
                </div>
            </div>
            <!-- <div id="userssection">
                <span class="headerLbl">Click to Toggle</span>
            </div> -->
            <!-- <div id="userpanelsection">
                <div class="usersdetail">
                    <div class="countsection">
                        282
                    </div>
                    <div class="typesection">
                        Users
                    </div>
                </div>
                <div class="usersdetail">
                    <div class="countsection">
                        282
                    </div>
                    <div class="typesection">
                        Users
                    </div>
                </div>
                <div class="usersdetail">
                    <div class="countsection">
                        282
                    </div>
                    <div class="typesection">
                        Users
                    </div>
                </div>
                <div class="usersdetail">
                    <div class="countsection">
                        282
                    </div>
                    <div class="typesection">
                        Users
                    </div>
                </div>
            </div> -->
            <input type="search" id="pac-input" class="controls" placeholder="Search Location." onkeyup="keyup_location(event)" />
            <div id="mapSection">
            </div>
        </div>
    </section>
    
	<script src="js/plugins/jquery.amaran.js"></script>
    <script type="text/javascript" src="js/urlpath.js"></script>
    <script src="js/plugins/jquery.mCustomScrollbar.concat.min.js"></script>
    
	<script type="text/javascript" src="js/home.js"></script>
	
	<script type="text/javascript" src="js/urlpath.js"></script>
    <script>
   
        $(document).ready(function () {
        	
            $('.partnerBox').click(function () {
                if ($(this).hasClass('partnerBoxSelected')) {
                    $(this).removeClass('partnerBoxSelected');
                    $('#partnersDetails').animate({ 'margin-left': '0' });
                    $('.close').css({ 'display': 'none' });
                } else {
                    $('#partnersDetails').css({ 'display': 'none' });
                    $('.close').css({ 'display': 'none' });
                    $('#partnersDetails').animate({ 'margin-left': '0' });
                    $('.partnerBox').removeClass('partnerBoxSelected');
                    $(this).addClass('partnerBoxSelected');
                    $('#partnersDetails').css({ 'display': 'block' });
                    $('#partnersDetails').animate({ 'margin-left': '25%' }, function () {
                        $('.close').css({ 'display': 'block' });
                    });
                }
            })
            
           /*  $('#userssection').click(function () {
                if ($('#userpanelsection').is(":visible")) {
                    $('#userpanelsection').animate({ 'margin-top': '0' });
                    $('#userpanelsection').css({ 'opacity': '0', 'display': 'none' });
                } else {
                    $('#userpanelsection').css('display', 'block');
                    $('#userpanelsection').animate({ 'margin-top': '40px' }).css({ 'opacity': '1' });
                }
            }) */

            $('.close').click(function () {
                $('.partnerBox').removeClass('partnerBoxSelected');
                $('#partnersDetails').animate({ 'margin-left': '0' });
                $('.close').css({ 'display': 'none' });
            });

            $(document).keyup(function (e) {
                if (e.keyCode == 27) {
                    // escape key maps to keycode `27`
                    if ($('#partnersDetails').is(':visible')) {
                        $('.partnerBox').removeClass('partnerBoxSelected');
                        $('#partnersDetails').animate({ 'margin-left': '0' });
                        $('.close').css({ 'display': 'none' });
                    }
                    

                    if ($('#userpanelsection').is(":visible")) {
                        $('#userpanelsection').animate({ 'margin-top': '0' });
                        $('#userpanelsection').css({ 'opacity': '0', 'display': 'none' });
                    }
                }
            });
            
        });
        
         (function($){
			$(window).load(function(){
				
				$(".scrollPartner").mCustomScrollbar({
					theme:"minimal-dark",
                    scrollButtons:{
						enable:false,
						scrollType:"stepless",
						scrollAmount:"auto"
					}
				});
				
				
                $("#partnersBox").mCustomScrollbar({
					theme:"minimal-dark",
                    scrollButtons:{
						enable:false,
						scrollType:"stepless",
						scrollAmount:"auto"
					}, callbacks:{
						onTotalScroll:function(){
							getdrivers(1);							
				        },
				        onTotalScrollOffset:100,
				        alwaysTriggerOffset:false
				    }
				});
				
			});
		})(jQuery);
   function inputKeyUp(e) {
		e.which = e.which || e.keyCode;
		if (e.which == 13) {
			getdrivers_onchange();
		}
	}
   function keyup_location(e){
	   e.which = e.which || e.keyCode;
		if (e.which == 13) {
			getdrivers_onchangelocation();
		}
   }
   $(document).ready(function () {
	    setcommon();
	    pubsnotify();
		$("#cornerIcon").click(function() {
				$("#cornerCont").toggle();			
		});
	    });
    setInterval(
		   function()
		   {
			   getdrivers_markers();
			   },
			   100000); 
   /* Frontend Function End */
    </script>	
</body>
</html>
