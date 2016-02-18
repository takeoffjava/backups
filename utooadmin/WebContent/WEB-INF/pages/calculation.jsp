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
                        <a class="sideIndivMenu active" href="calculator"><i class="icon_set icon_calci"></i> <span>CALCULATOR</span></a>
            <!-- <a class="sideIndivMenu" href="lost"><i class="icon_set icon_lost"></i> <span>LOST</span></a> -->
        </div>
    </section>
    <section id="contentSection">
    	<div class="headerContentCont">
    		<div class="mainHeader">
    			Calculation
    		</div>

    			<!-- <div class="headerPanelElemStyle">
    				<input type="text" id="fromdate" class="clsTxtbox bookingFrom" placeholder="FROM" data-field="date" data-format="yyyy-MM-dd" data-view="Dropdown" data-startend="start" data-startendelem=".bookingTo" readonly />
    			</div>
    			<div class="headerPanelElemStyle">
    				<input type="text" id="todate" onchange="return getbookings();" class="clsTxtbox bookingTo" placeholder="TO" data-field="date" data-format="yyyy-MM-dd" data-view="Dropdown" data-startend="end" data-startendelem=".bookingFrom" readonly />
    			</div> -->
    		</div>
<!--     	</div>
 -->        <div class="mainContentCont">
        
			<div class="contentScet">
           		<div class="section group">
               <div class="fullRow">
                   <div class="halfColumn">
                       <div class="colTitle">
                           Driver Name&nbsp;:
                       </div>
                       <div class="colContent">
					<select id="selDrivers">
						                          <option value="-1">Select Driver</option>
					</select></div>	     </div>
                  
                   <div class="halfColumn">
                       <div class="colTitle">
                           Kilometers&nbsp;:
                       </div>
                       <div class="colContent">
					<input id="txtKms" type="text" maxlength="6"/>                  
   					</div>
                   </div>
               </div>
               <div class="fullRow" >
                   <div class="halfColumn">
                       <div class="colTitle">
                        Destination &nbsp;:
                       </div>
                       <div class="colContent">
    					<input id="searchTextField" type="text" class="qrControls" placeholder="Enter a location" autocomplete="on" runat="server" />  
                       </div>
                   </div>
               </div>
               <!--  <div class="fullRow" id="div1">
                   <div class="halfColumn">
                       <div class="colTitle">
                          Passenger Name&nbsp;:
                       </div>
                       <div class="colContent">
						<div id="divPassenger"></div>                  
                       </div>
                   </div>
                   <div class="halfColumn">
                       <div class="colTitle">
                           Time Taken&nbsp;:
                       </div>
                       <div class="colContent">
					<div id="divTimeTaken"></div>                  
                       </div>
                   </div>
               </div>-->
               <!-- <div class="fullRow" id="div2" >
                   <div class="halfColumn">
                       <div class="colTitle">
                         Kilometers&nbsp;:
                       </div>
                       <div class="colContent">
						<div id="divKms"></div>                  
                       </div>
                   </div>
                   <div class="halfColumn">
                       <div class="colTitle">
                           Ride Charge&nbsp;:
                       </div>
                       <div class="colContent">
					<div id="divRideCharge"></div>                  
                       </div>
                   </div>
               </div> -->
               
          <button type="button" id="calculateButton" onclick="return endtrippopup();" class="qrBtn">Calculate</button>
          <button type="button" id="refreshButton" onclick="return refreshClick();" style="position: absolute; top: -15px; right: 20px; font-size: 12px; border: 1px solid rgb(221, 221, 221); padding: 5px 10px; background: rgb(255, 255, 255) none repeat scroll 0px 0px; color: rgb(207, 127, 42);">Refresh</button>
           </div></div>
       </div>
       <div  class="mainContentCont">
       	<div class="contentScet  section group" id="divContentCalc"></div>
       </div>
       <div id="endtrippopup" class="clsPopUp">
      <div class="formSection">
      	<h3 class="subHeadTitle">End Trip</h3>
      <p style="color:#838383;">Are you sure want to finish this ride?</p>
      <div style="text-align:right;">
      	<input type="button" onclick="return getDriver_calculation();"  class="clsButtonSmall" value="Complete Trip">
      	<input type="button"  class="clsButtonSmall popupcancel" value="Cancel" style="background:#bebebe">
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
     <script type="text/javascript" src="js/calculation.js"></script>
     <script type="text/javascript" src="js/plugins/general.js"></script>
     
    <script>
    	 $(document).ready(function() { 
    		 loadDriver();
    		 
			
		 }); 
    </script>
</body>
</html>
