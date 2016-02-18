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
            <a class="sideIndivMenu active" href="home"><i class="icon_set icon_dash"></i> <span>DASHBOARD</span></a>
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
    			User
    		</div>
    		<div class="headerPanel">
				 <div class="partBtnCont">
                    <a href="#" id="createUser" onclick="return pop_up(1);" style="width: 34px; text-align: right; height: 32px; position: absolute;right: 35px;   background: url(images/add.png) center no-repeat;"></a>
                </div>    			
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
        
			<table id="example" class="display" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>Username</th>
						<th>Gender</th>
						<th>Mobile</th>
						<th>User Type</th>
						<th>Access_id</th>
						<th>Edit/Delete</th>
					</tr>
				</thead>				
			</table>
       </div>
       <div id="dtBox"></div>
       <div id="divActionView" class="clsPopUp">
           <div class="formSection">
           		<h3 class="subHeadTitle">User Registration</h3>
               <div class="fullRow">
                   <div class="halfColumn">
                       <div class="colTitle">
                           Username&nbsp;:
                       </div>
                       <div class="colContent">
			<input id="txtUsername" type="text" maxlength="21" placeholder="Username"/>                  
	     </div>
                   </div>
                   <div class="halfColumn">
                       <div class="colTitle">
                           Gender&nbsp;:
                       </div>
                       <div class="colContent">
		<input type="radio" name="gender" value="Male">Male &nbsp;&nbsp;
		<input type="radio" name="gender" value="Female">Female                    
   </div>
                   </div>
               </div>
               <div class="fullRow">
                   <div class="halfColumn">
                       <div class="colTitle">
                          Mobile&nbsp;:
                       </div>
                       <div class="colContent">
			<input id="txtmobile" type="text"  maxlength="10" placeholder="mobile" />                  
                       </div>
                   </div>
                   <div class="halfColumn">
                       <div class="colTitle">
                           Password&nbsp;:
                       </div>
                       <div class="colContent">
			<input id="txtpassword" type="password" maxlength="15"  placeholder="password"/>                  
                       </div>
                   </div>
               </div>
               <div class="fullRow">
                   <div class="halfColumn">
                       <div class="colTitle">
                           UserType&nbsp;:
                       </div>
                        <div class="colContent">
                            <select class="drpDwn" id="user_type"  >
                          <option value="-1">Select User Type</option>
                              <!--  <option name="Free" value="0">Available</option>
                            <option name="Occupied" value="1">Occupied</option>
                            <option name="Free" value="2">Offline</option> -->
                            </select>
                        </div>
                   </div>
               </div>
           <input type="button" id="btnSave" value="Add User" class="clsButtonSmall" style="float: right; display:none;" onclick="return add_user();" />
           <input type="button" id="btnUpdate" value="Update" class="clsButtonSmall" style="float: right; display:none;" onclick="return updateDetails();" />
               
           </div>
       </div>
      <div id="deletepopup" class="clsPopUp">
      <div class="formSection">
      	<h3 class="subHeadTitle">Delete User</h3>
      <p style="color:#838383;">Are you sure want to delete?</p>
      <div style="text-align:right;">
      	<input type="button" onclick="return deleteUser_YES();"  class="clsButtonSmall" value="Delete">
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
     <script type="text/javascript" src="js/user.js"></script>
     <script type="text/javascript" src="js/plugins/general.js"></script>
     
    <script>
    	 $(document).ready(function() { 
    		 getUserType();
    		 setcommon();
    		 pubsnotify();
			$("#dtBox").DateTimePicker();
			
		 }); 
    </script>
</body>
</html>
