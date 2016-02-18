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

            <!-- <a class="sideIndivMenu" href="lost"><i class="icon_set icon_lost"></i> <span>LOST</span></a> -->
        </div>
    </section>
    <section id="contentSection">
    	<div class="headerContentCont">
    		<div class="mainHeader">
    			Invoice Insights
    		</div>
    		
    	</div>
        <div class="mainContentCont">
        		<div class="section group">
        		<div style="width:48%;float:left;margin:1%;">
        			 <iframe src="https://charts.qlikcloud.com/56485c35a8cbbcf8360939b9/chart.html" style="width:100%;height:500px;border:1px solid #ccc;"></iframe> 
        		</div>
        		<div style="width:48%;float:left;margin:1%;">
        			 <iframe src="https://charts.qlikcloud.com/56485c78a8cbbcf8360939ba/chart.html" style="width:100%;height:500px;border:1px solid #ccc;"></iframe> 
        		</div>
        		
        		</div>
        
			<!-- <table id="example" class="display" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>Accident ID</th>
						<th>Accident Note</th>
						<th>Accident Date</th>
					 	<th>Driver Name</th>
						<th>Driver Mobile</th>
						<th>Car Registration No</th>
						<th>Voice</th>
						<th>Accident Image</th>
					</tr>
				</thead>				
			</table> -->
       </div>
       <div id="dtBox"></div>
       <div id="divActionView" class="clsPopUp">
           <div class="formSection" >
               <img alt="" src="" style="float: left;" id="accidentimage">
           </div>
       </div>
        <div id="divInsertView" class="clsPopUp">
            <div class="formSection">
                <div class="fullRow">
                    <div class="halfColumn">
                        <div class="colTitle">
                            Date&nbsp;:
                        </div>
                        <div class="colContent">
                            21/10/2015
                        </div>
                    </div>
                    <div class="halfColumn">
                        <div class="colTitle">
                            Pickup Location&nbsp;:
                        </div>
                        <div class="colContent">
                            Saidapet
                        </div>
                    </div>
                </div>
                <div class="fullRow">
                    <div class="halfColumn">
                        <div class="colTitle">
                            Drop Location&nbsp;:
                        </div>
                        <div class="colContent">
                            Kumbakonam
                        </div>
                    </div>
                    <div class="halfColumn">
                        <div class="colTitle">
                            Passenger&nbsp;:
                        </div>
                        <div class="colContent">
                            Rajkumar
                        </div>
                    </div>
                </div>
                <div class="fullRow">
                    <div class="halfColumn">
                        <div class="colTitle">
                            Partner&nbsp;:
                        </div>
                        <div class="colContent">
                            Saravanan
                        </div>
                    </div>
                    <div class="halfColumn">
                        <div class="colTitle">
                            Total Amount&nbsp;:
                        </div>
                        <div class="colContent">
                            500 Rs
                        </div>
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
    
    <script>
    	$(document).ready(function() {
    		pubsnotify();    		
    		setcommon();
			
			$('body').on('click', '.actionPopup', function () {
			    $('#divActionView').U2Popup({
			        title: 'Booking View'
                    , width: 800
                    , height: 300
			    });
			});

			
			
			$("#cornerIcon").click(function() {
				
				$("#cornerCont").toggle();			
		});
		
		});
    </script>
</body>
</html>
