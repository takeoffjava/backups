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
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:300,400,700'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/jquery.dataTables.min.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
 <link rel="stylesheet" href="css/jquery.mCustomScrollbar.css" />
<link rel="stylesheet" href="css/DateTimePicker.css" />
<!-- <link href="css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/> -->
<link rel="stylesheet" href="css/U2Popup.css" />
 <link rel="stylesheet" href="css/amaran.min.css">


<style>
div.dataTables_wrapper {
	min-height: 489px;
}
</style>
<script src="js/plugins/modernizr-2.6.2.min.js"></script>
<script src="https://cdn.pubnub.com/pubnub-3.7.20.js"></script>
</head>
<body>
	<header>
	<div id="header">
		<div id="headerBar">
			<div id="logo">
				<a href="home"><img src="images/logo.png" width="60" /></a>
			</div>
			<h3 class="mainHeadTitle" id="welcome_name"></h3>
		</div>
		<div id="topBar">
				<a class="sideIndivTopMenu" href="qrcode"><img src="images/qrCode.png" class="qrIco" /> <span>QR CODE</span></a>
				<a class="sideIndivTopMenu" href="#"><i class="icon_set_top icon_car"></i> <span>INFO</span></a>
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
           <!--  <a class="sideIndivMenu" href="lost"><i class="icon_set icon_lost"></i> <span>LOST</span></a> -->
        </div>
	</section>
	<section id="contentSection" style="height: 100%;">
	<div class="headerContentCont">
		<div class="mainHeader">LOCATIONS</div>
		<a href="#" id="create0" onclick="return pop_upcarmodals3(1);" style="width: 5%; text-align: center; height: 32px; float: right; background: url(images/add.png) center no-repeat;"></a>
	</div>
	<div class="partnersBox mCustomScrollbar _mCS_2 mCS-autoHide" style=" position: relative; overflow: visible; height: 327px; overflow: auto; margin-bottom: 20px; border-bottom: solid 1px #888;">
		<!--  <div class="mainContentCont"> -->
		<table id="example" class="display" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Location</th>
					<th>East </th>
					<th>West</th>
					<th>South</th>
					<th>North</th>
					<th>Channel Name</th>
					<th>Edit/Delete</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="mainHeader" style="width: 45%;">CAR MODELS</div>
	<a href="#" id="updatecarmodel" onclick="return pop_upcarmodals(1);" style="width: 5%; text-align: center; height: 32px; float: left; background: url(images/add.png) center no-repeat;"></a>
	<div class="mainHeader" style="width: 45%;">CAR FEATURES</div>
	<a href="#" id="updatecarfeature" onclick="return pop_upcarmodals2(1);" style="width: 5%; text-align: center; height: 32px; float: right; background: url(images/add.png) center no-repeat;"></a>
	<div class="partnersBox mCustomScrollbar _mCS_2 mCS-autoHide" style=" position: relative; overflow: visible; height: 227px; width: 50%; overflow: auto; float: left; border-right: solid 1px #D8D8D8;">
		<table id="example2" class="display" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Car Name</th>
					<th>Car Description</th>
					<th>Edit/Delete</th>
					
				</tr>
			</thead>
		</table>
	</div>
	<div style=" position: relative; overflow: visible; height: 227px; width: 50%; overflow: auto; float: left;" class="partnersBox mCustomScrollbar _mCS_2 mCS-autoHide">
		<table id="example3" class="display" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>Register Number</th>
					<th>Color</th>
					<th>Vehicle year</th>
					<th>Seat Count</th>
					<th>Car Model</th>
					<th>Edit/Delete</th>
				</tr>
			</thead>
		</table>
	</div>
	<div style="clear: both; margin-bottom: 20px;"></div>
	<!-- </div> -->
	<div id="dtBox"></div>
	<div id="divActionView" class="clsPopUp">
	
		<div class="formSection">
		<h3 class="subHeadTitle">Car Models</h3>
			<div class="fullRow">
				<div class="halfColumn">
					<div class="colTitle">Car Model&nbsp;:</div>
					<div class="colContentSingle"><input type="text" id="carmodal" maxlength="15"/></div>
				</div>
			</div>
			<div class="fullRow">
				<div class="halfColumn">
					<div class="colTitle">About Car&nbsp;:</div>
					<div class="colContentSingle"><textarea id="aboutcar" maxlength="100"></textarea></div>
				</div>
			</div>
			<div class="fullRow">
				<div class="fullColumn">
					<div class="colContentFull">
						<input type="button" id="btnSave" value="Save" class="clsButtonSmall" style="float: right;" onclick="return insertcarmodals();" />
						<input type="button" id="btnUPDATE" value="Update" class="clsButtonSmall" style="float: right;" onclick="return updatecarmodels_save();" />
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="divActionView2" class="clsPopUp">
		<div class="formSection">
		<h3 class="subHeadTitle">Car Features</h3>
			<div class="fullRow">
				<div class="halfColumn">
					<div class="colTitle">Car Register-No&nbsp;:</div>
					<div class="colContent"><input type="text" id="carregisterno" maxlength="15"/></div>
				</div>
				<div class="halfColumn">
					<div class="colTitle">Car Color&nbsp;:</div>
					<div class="colContent"><input type="text" id="carcolor" maxlength="15"></textarea></div>
				</div>
			</div>
			<div class="fullRow">
				<div class="halfColumn">
					<div class="colTitle">Vehicle Year&nbsp;:</div>
					<div class="colContent"><input type="text" id="vehileyear" maxlength="4" /></div>
				</div>
				<div class="halfColumn">
					<div class="colTitle">Seat Count&nbsp;:</div>
					<div class="colContent"><input type="text" id="seatcount" maxlength="2" /></div>
				</div>
			</div>
			<div class="fullRow">
				<div class="halfColumn">
					<div class="colTitle">Vehicle Model&nbsp;:</div>
					<div class="colContent">
					<select id="selVechileMode">
						                          <option value="-1">Select User Type</option>
					</select></div>
				</div>
			</div>
			<div class="fullRow">
				<div class="fullColumn">
					<div class="colContentFull">
						<input type="button" onclick="return insertcarfeatures();" id="btnSave_CarFeatures" value="Save" class="clsButtonSmall" style="float: right;" />
						<input type="button" onclick="return updatecarfeatures_save();" id="btnupdate_CarFeatures" value="Update" class="clsButtonSmall" style="float: right;" />
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="divActionView3" class="clsPopUp">
		<div class="formSection">
		<h3 class="subHeadTitle">Location Range</h3>
			<div class="fullRow">
				<div class="halfColumn">
					<div class="colTitle">Location Name&nbsp;:</div>
					<div class="colContent"><input type="text" id="locationname" maxlength="40"/></div>
				</div>
				<div class="halfColumn">
					<div class="colTitle">East&nbsp;:</div>
					<div class="colContent"><input type="text" id="east" maxlength="40" ></textarea></div>
				</div>
			</div>
			<div class="fullRow">
				<div class="halfColumn">
					<div class="colTitle">West&nbsp;:</div>
					<div class="colContent"><input type="text" id="west" maxlength="40" /></div>
				</div>
				<div class="halfColumn">
					<div class="colTitle">South&nbsp;:</div>
					<div class="colContent"><input type="text" id="south" maxlength="40" ></textarea></div>
				</div>
			</div>
			<div class="fullRow">
				<div class="halfColumn">
					<div class="colTitle">North&nbsp;:</div>
					<div class="colContent"><input type="text" id="north" maxlength="40"></textarea></div>
				</div>
			</div>
			<div class="fullRow">
				<div class="fullColumn">
					<div class="colContentFull">
						<input type="button" onclick="return insertlocations();" id="btnSave_location" value="Save" class="clsButtonSmall" style="float: right;" />
						<input type="button" onclick="return updatelocation_save();" id="btnupdate_location" value="Update" class="clsButtonSmall" style="float: right;" />
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="deletepopuplocation" class="clsPopUp">
      <div class="formSection">
      	<h3 class="subHeadTitle">Delete Location</h3>
      <p style="color:#838383;">Are you sure want to delete?</p>
      <div style="text-align:right;">
      	<input type="button" onclick="return deletelocation_YES();"  class="clsButtonSmall" value="Delete">
      	<input type="button"  class="clsButtonSmall popupcancel" value="Cancel" style="background:#bebebe">
      </div>
      </div>
      </div> 
      <div id="deletepopupfeature" class="clsPopUp">
      <div class="formSection">
      	<h3 class="subHeadTitle">Delete Car Feature</h3>
      <p style="color:#838383;">Are you sure want to delete?</p>
      <div style="text-align:right;">
      	<input type="button" onclick="return deletecarfeature_YES();"  class="clsButtonSmall" value="Delete">
      	<input type="button"  class="clsButtonSmall popupcancel" value="Cancel" style="background:#bebebe">
      </div>
      </div>
      </div> 
      <div id="deletepopupmodal" class="clsPopUp">
      <div class="formSection">
      	<h3 class="subHeadTitle">Delete Car Model</h3>
      <p style="color:#838383;">Are you sure want to delete?</p>
      <div style="text-align:right;">
      	<input type="button" onclick="return deletecarmodal_YES();"  class="clsButtonSmall" value="Delete">
      	<input type="button"  class="clsButtonSmall popupcancel" value="Cancel" style="background:#bebebe">
      </div>
      </div>
      </div> 
	</section>
	<script src="js/plugins/jquery-1.11.1.min.js"></script>
	<script src="js/plugins/jquery.amaran.js"></script>
	
	<script type="text/javascript" src="js/urlpath.js"></script>
	<script src="js/plugins/jquery.mCustomScrollbar.concat.min.js"></script>
	<script src="js/plugins/materialize.js"></script>
	<script src="js/plugins/jquery.dataTables.min.js"></script>
	<script src="js/plugins/DateTimePicker.js"></script>
	<script src="js/plugins/U2Popup.js"></script>
	<script type="text/javascript" src="js/base/utilities.js"></script>
	<script type="text/javascript" src="js/info.js"></script>
	
	<!-- This is commom properties Js -->
	<script type="text/javascript" src="js/plugins/general.js"></script>
	
<script>
(function($){
	$(window).load(function(){
		setcommon();
		pubsnotify();
		$(".partnersBox").mCustomScrollbar({
			theme:"minimal-dark",
            scrollButtons:{
				enable:false,
				scrollType:"stepless",
				scrollAmount:"auto"
			}
		});
	});
})(jQuery);
</script>
</body>
</html>
