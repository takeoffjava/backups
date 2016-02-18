<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
 <!--<![endif]-->
<head>
    <title>UTOO Admin Panel</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!--<meta name="viewport" content="width=device-width, initial-scale=1">-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300,400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/DateTimePicker.css" />
    <link rel="stylesheet" href="css/amaran.min.css">
    
     
    <!-- <link href="css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/> -->
    <link rel="stylesheet" href="css/U2Popup.css" />
    <script src="js/plugins/modernizr-2.6.2.min.js"></script>
    <script src="https://cdn.pubnub.com/pubnub-3.7.20.js"></script>
    <script src="https://maps.google.com/maps/api/js?libraries=places"></script>
</head>
<body>
    <header>
        <div id="header">
        	<div id="headerBar">
        		<div id="logo"><a href="home"><img src="images/logo.png" width="60" /></a></div>
				 <h3 class="mainHeadTitle" id="welcome_name"></h3>
        	</div>
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
            <a class="sideIndivMenu" href="home"><i class="icon_set icon_dash"></i> <span>DASHBOARD</span></a>
            <a class="sideIndivMenu" href="drivers"><i class="icon_set icon_driver"></i> <span>DRIVERS</span></a>
             <a class="sideIndivMenu" href="passenger"><i class="icon_set icon_pass"></i> <span>PASSENGER</span></a>
            <a class="sideIndivMenu" href="booking"><i class="icon_set icon_book"></i> <span>BOOKING</span></a>
            <a class="sideIndivMenu" href="tariff"><i class="icon_set icon_tariff"></i> <span>TARIFF</span></a>
            <a class="sideIndivMenu" href="promo"><i class="icon_set icon_promo"></i> <span>PROMO</span></a>
            <a class="sideIndivMenu" href="accident"><i class="icon_set icon_acci"></i> <span>ACCIDENT</span></a>
            <a class="sideIndivMenu" href="calculator"><i class="icon_set icon_calci"></i> <span>CALCULATOR</span></a>
           <!--  <a class="sideIndivMenu" href="lost"><i class="icon_set icon_lost"></i> <span>LOST</span></a> -->
        </div>
    </section>
    <section id="contentSection">
    	<div class="headerContentCont">
    		<div class="mainHeader">
    			QR CODE
    		</div>
    		
      	</div>
    	<div class="mainContentCont">
    		<div class="contentScet">
    			<div class="section group">
    				
    					<input type="text" id="name" class="qrControls" placeholder="Name" maxlength="21"/>
    					<input type="text" id="mobile" class="qrControls" placeholder="Mobile" maxlength="10" />
    					<input type="text" id="qrcount" class="qrControls" placeholder="QR Codes Count" maxlength="5" />
    					<input id="searchTextField" type="text" class="qrControls" placeholder="Enter a location" autocomplete="on" runat="server" />  
    					<input type="text" id="advanceamount" class="qrControls" placeholder="Advance Amount" maxlength="7" />    					
	    				<input type="text" id="fromdate" class="qrControls" placeholder="From" data-field="datetime" data-format="yyyy-MM-dd HH:mm:ss" data-view="Dropdown" data-startend="start" data-startendelem=".bookingTo" readonly />    			
    					<input type="text" id="todate" class="qrControls" placeholder="To" data-field="datetime" data-format="yyyy-MM-dd HH:mm:ss" data-view="Dropdown" data-startend="end" data-startendelem=".bookingFrom" readonly />
    					<span>Offer &nbsp&nbsp&nbsp</span><input type="range" id="offerrange" class="qrControlsrange" min="0" max="100" step="5"/><span id="rangevalue" style="margin-left:10px;">50%</span>     			
    					<textarea class="qrTextarea" id="message" placeholder="Message" maxlength="150"></textarea>
    					
    					<button type="button" id="createQr" class="qrBtn" onclick="create_qrcode();">Create QR</button>
    			</div>	
    		</div>	
    	</div>
    	<div id="dtBox"></div>
    	 <div class="mainContentCont">
    	 <table id="example" class="display" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>Name</th>
						<th>Mobile</th>
						<th>Destination</th>
						<th>From</th>
						<th>To</th>
						<th>Message</th>
						<th>Advance Amount</th>
						<th>QR Codes</th>
						</tr>
				</thead>				
			</table>
    	 </div>
    	<div id="divActionView" class="clsPopUp">
           <div class="formSection">
           
           <div class="section group">
           		<div class="ContHalfPop">
           		
           		
           		</div>
           </div>
           </div>
       </div>
     </section>
    
    
    <script src="js/plugins/jquery-1.11.1.min.js"></script>
    <script src="js/plugins/jquery.amaran.js"></script>
    
    <script type="text/javascript" src="js/urlpath.js"></script>
    <script src="js/plugins/materialize.js"></script>
    <script src="js/plugins/jquery.dataTables.min.js"></script>
    <script src="js/plugins/DateTimePicker.js"></script>
    <script src="js/plugins/U2Popup.js"></script>
    <script type="text/javascript" src="js/base/utilities.js"></script>
    <script type="text/javascript" src="js/qrcode.js"></script>
    
    <!-- This is commom properties Js -->
	<script type="text/javascript" src="js/plugins/general.js"></script>
    
    <script>
    	$(document).ready(function() {
    		
    		$("#dtBox").DateTimePicker();
    	//	$("#dtBox").DateTimePicker({ minDate:0});
    		setcommon();
    		pubsnotify();
    		$("#offerrange").change( function(e){
    	        $("#rangevalue").html($(this).val()+" %");
    	    });
		});
    </script> 
	
</body>
</html>
