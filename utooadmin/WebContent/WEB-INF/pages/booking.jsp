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
    <!-- <link href="css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/> -->
    <link rel="stylesheet" href="css/U2Popup.css" />
     <link rel="stylesheet" href="css/amaran.min.css">
    

    <script src="js/plugins/modernizr-2.6.2.min.js"></script>
     <script src="https://cdn.pubnub.com/pubnub-3.7.20.js"></script>
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
            <a class="sideIndivMenu active" href="booking"><i class="icon_set icon_book"></i> <span>BOOKING</span></a>
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
    			BOOKING - <span id="bookingcount"></span>
    		</div>
    		
    	</div>
    	<div class="mainContentCont">
    	<div class="section group">
    		<div class="headerPanel">
    			<div class="headerLbl headerPanelElemStyle">
    				BILLING DATE :
    			</div>
    			<div class="headerPanelElemStyle">
    				<input type="text" id="fromdate" class="clsTxtbox bookingFrom" placeholder="FROM" data-field="date" data-format="yyyy-MM-dd" data-view="Dropdown" data-startend="start" data-startendelem=".bookingTo" readonly />
    			</div>
    			<div class="headerPanelElemStyle">
    				<input type="text" id="todate" onchange="return getbookings();" class="clsTxtbox bookingTo" placeholder="TO" data-field="date" data-format="yyyy-MM-dd" data-view="Dropdown" data-startend="end" data-startendelem=".bookingFrom" readonly />
    			</div>
    		</div>
    		<div class="divsColors">
    				<p class="titleText">Total Booking</p>
    				<span id="totalBooking" class="textBlock">-</span>
    			</div>
    			
    			<div class="divsColors">
					<p class="titleText">Completed Booking</p>    			
    				<span id="completedBooking" class="textBlock">-</span>
    			</div>
    			
    			<div class="divsColors">
    						<p class="titleText">Cancelled Booking</p>
    						<span id="cancelledBooking" class="textBlock">-</span>
    			</div>
    			
    			<div class="divsColors">
    						<p class="titleText">Total Amount</p>	
    						<span id="totalAmount" class="textBlock">-</span>
    			</div>
    		
    	</div>
    	</div>
        <div class="mainContentCont">
        
			<table id="example" class="display" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>PBR Number</th>
						<th>Mobile</th>
						<th>Driver Name</th>
						<th>Passenger Name</th>
						<th>Booked Date</th>
						<th>Reached Date</th>
						<th>Booking Status</th>
						<th>Booking Type</th>
						<th>Reasons</th>
						<th>Comments</th>
						<th>Map / Invoice</th>
						</tr>
				</thead>				
			</table>
       </div>
       <div id="dtBox"></div>
       <div id="divActionView" class="clsPopUp">
           <div class="formSection" >
           	
            	<div id="statMap"></div>   
               <h3 class="subHeadTitle">Ride Details</h3>
               <table class="noBorder">
           			<tbody>
                    <tr><td class="dim"><p>Source</p><p id="sourceTb" style="color:#000;">-</p></td>
              			<td class="dim"><p>Destination</p><p id="destTb" style="color:#000;">-</p></td></tr>
                    </tbody></table>
           </div>
       </div>
       
        <div id="divInvoiceView" class="clsPopUp">
           <div class="formSection">
           
           <div class="section group">
           		<div class="ContHalfPop">
           		<h3 class="subHeadTitle">Ride Details</h3>
           		<table class="noBorder">
           			<tbody><tr><td class="dim"><p>Name</p></td><td><p id="btoname">---</p></td></tr>
                    <tr><td class="dim"><p>Invoice</p></td><td><p id="txtInvoice">---</p></td></tr>
                    <tr><td class="dim"><p>Mobile</p></td><td><p id="btomobile">---</p></td></tr>
                    <tr><td class="dim"><p>Source</p></td><td><p id="source">---</p></td></tr>
                    <tr><td class="dim"><p>Destination</p></td><td><p id="destination">-</p></td></tr>
                    </tbody></table>
                   <h3 class="subHeadTitle">Bill Details</h3>
                   <table class="noBorder">
           			<tbody><tr><td class="dim"><p>Distance Travelled</p></td><td><p id="txtDistance">-</p></td></tr>
                    <tr><td class="dim"><p>Time Taken</p></td><td><p id="txtTotalmins">-</p></td></tr>
                    <tr><td class="dim"><p>Amount</p></td><td><p id="txtAmount">-</p></td></tr>
                    <tr><td class="dim"><p>TAX</p></td><td><p id="txtTax">-</p></td></tr>
                    <tr><td class="dim"><p>Total</p></td><td><p id="txtTotal">-</p></td></tr>
                    </tbody></table>
           		</div>
           </div>
           </div>
       </div>
          <div id="divcommentspopup" class="clsPopUp">
           <div class="formSection">
           
           <div class="section group">
           		<div class="ContHalfPop">
           		<h3 class="subHeadTitle">Comments</h3>
           		<table class="noBorder">
                    <tr><td><textarea id="txtComments" class="qrTextarea" style="width:70%;" placeholder="Post your Feedback" class="dim"></textarea></td></tr>
                   
                    <tr><td><button type="button" onclick="return addComments();" class="clsButtonSmall popupcancel">SUBMIT</button></td></tr>
                    </tbody></table>
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
     <script type="text/javascript" src="js/bookings.js"></script>
    <script>
    	$(document).ready(function() {
    		setcommon();
    		pubsnotify();
			$("#dtBox").DateTimePicker();
			
		});
    </script>
</body>
</html>
