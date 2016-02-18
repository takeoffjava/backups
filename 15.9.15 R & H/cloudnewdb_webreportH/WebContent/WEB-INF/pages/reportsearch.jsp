<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- start: Meta -->
	<meta charset="utf-8">
	<title>Nutans - H+</title>
	<meta name="description" content="Bootstrap Metro Dashboard">
	<meta name="author" content="Strobilanthes Technology Solution India PVT Ltd">
	<meta name="keyword" content="">
	<!-- end: Meta -->
	
	<!-- start: Mobile Specific -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- end: Mobile Specific -->
	
	<!-- start: CSS -->

	<link id="bootstrap-style" href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/bootstrap-responsive.min.css" rel="stylesheet">
	<link id="base-style" href="css/style.css" rel="stylesheet">
	<link id="base-style-responsive" href="css/style-responsive.css" rel="stylesheet">
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
	
	<!-- <link rel="stylesheet" href="/resources/demos/style.css"> -->
	<!-- start: Favicon -->
	<link rel="shortcut icon" href="images/Nlogo.png">	
	<!-- end: Favicon -->
</head>

<body onload="noBack();" onpageshow="if(event.persisted) noBack();">
		<!-- start: Header -->
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
				<a class="brand" href="home"><img src="img/Nlogo.png" style="margin-right:5px;"/><span>NUTANS-H+</span></a>
								
				<!-- start: Header Menu -->
				<div class="nav-no-collapse header-nav">
					<ul class="nav pull-right">
						<li class="dropdown hidden-phone">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
							</a>
						</li>
						<!-- start: User Dropdown -->
						<li class="dropdown">
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
								<i class="halflings-icon white user"></i><span id="username"></span>
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<li><a href="logout"><i class="halflings-icon off"></i>Logout</a></li>
							</ul>
						</li>
						<!-- end: User Dropdown -->
					</ul>
				</div>
				<!-- end: Header Menu -->
				
			</div>
		</div>
	</div>
	<!-- start: Header -->
	
		<div class="container-fluid-full">
		
		<div class="row-fluid">
				
			<!-- start: Main Menu -->
			<div id="sidebar-left" class="span2">
				<div class="nav-collapse sidebar-nav">
					<ul class="nav nav-tabs nav-stacked main-menu">
                      <!--    <li><a href="http://www.nutansrplus.com/appstatus"><i class="icon-user"></i><span class="hidden-tablet"> Admin</span></a></li> -->	
                        <li><a href="home"><i class="icon-bar-chart"></i><span class="hidden-tablet"> Dashboard</span></a></li>	
						<li class="active"><a href="reportsearch"><i class="icon-search"></i><span class="hidden-tablet"> Reports</span></a></li>
						<li><a href="dataanalytics"><i class="icon-globe"></i><span class="hidden-tablet"> Data Analytics</span></a></li>	
						<!-- <li><a href="records"><i class="icon-folder-close-alt"></i><span class="hidden-tablet"> Records</span></a></li>	
						 <li><a href="appstatus"><i class="icon-random"></i><span class="hidden-tablet"> Appstatus</span></a></li>  -->
						<!-- <li><a href="#"><i class="icon-globe"></i><span class="hidden-tablet"> Locations</span></a></li> -->		

					</ul>
				</div>
			</div>
			<!-- end: Main Menu -->
		
			
			<!-- start: Content -->
			<div id="content" class="span10">
			
			
			<ul class="breadcrumb">
				<li>
					<i class="icon-home"></i>
					<a href="home">Home</a> 
					<i class="icon-angle-right"></i>
				</li>
				<li><a href="#">Reports Search</a>
				<i class="icon-angle-right"></i></li>
				<!-- <li><a href="#">Select Branch</a></li>
				<li><select id="branches" onchange="branchdb_dashboard();"></select></li> -->
			</ul>
                
			<div class="breadcrumb" style="border:1px solid #ccc;">
                <div class="span12" style="background:#f7f7f7;">
                
				<div class="span3">
                    <div class="control-group">
							  <label class="control-label" for="start_date">Start Date</label>
							  <div class="controls">
								<!-- <input type="text" class="input-xlarge datepicker" id="start_date" > -->
								<input type="text" class="input-xlarge datepicker" id="sdatepicker" placeholder="yyyy/mm/dd" style="width:100%;padding:7px 6px;" />
							  </div>
							</div>
							
                </div>
				<div class="span3">
                    <div class="control-group">
							  <label class="control-label" for="end_date">End Date</label>
							  <div class="controls">
								<!-- <input type="text" class="input-xlarge datepicker" id="end_date" > -->
								<input type="text" class="input-xlarge datepicker" id="edatepicker" placeholder="yyyy/mm/dd" style="width:100%;padding:7px 6px;" />
							  </div>
							</div>
                </div>
				<div class="span4">
                    <label class="control-label">&nbsp;</label>
                <button class="btn btn-primary" type="submit" onclick="return dashboard_reportsdate();">Search</button>
                <button class="btn" onclick="return reset();">Reset</button>
                </div>
				</div>		
				<div class="clearfix"></div>		
			</div>
			<div class="row-fluid">
			<div class="login">
			<span class="logintext">Day Wise Request Count</span></div>
			<div id="daywisedata"  style="padding:70px;"></div>
			<div class="login">
			<span class="logintext">Most Requested Bed </div>		
			<div id="mostusedtable" style="padding:70px;"></div>
			<div class="login">
			<span class="logintext">UnAttended Bed</span></div>
			<div id="autocancel"  style="padding:70px;"></div>
			<!-- <div class="login">
			<span class="logintext">Waiter Response Time</span></div>
			<div id="waiterresponse"  style="padding:70px;"></div>
			<div class="login">
			<span class="logintext">Waiter Average Response Time</span></div>
			<div id="waiterAvgresponse"  style="padding:70px;"></div> -->
			<div class="login">
			<span class="logintext">Month wise Request Count</span></div>
			<div id="monthwise"  style="padding:70px;"></div>
			<div class="login">
			<span class="logintext">All Bed Response Count</span></div>
			<div id="alltableresponsecount" class="row-fluid"></div>
			</div>
							
			</div>
	</div>
		</div>
	<footer>
		<p>
			<span style="text-align:left;float:left">&copy; 2015 <a href="http://www.strobilanthes.com" >Strobilathes Technology Solutions pvt ltd</a></span>			
		</p>

	</footer>
	
	<!-- start: JavaScript-->
    	<script src="js/plugins/jquery.min.js"></script>
		<script src="js/plugins/jquery-migrate-1.0.0.min.js"></script>
		<script src="js/plugins/jquery-ui-1.10.0.custom.min.js"></script>	
		<script src="js/plugins/jquery.ui.touch-punch.js"></script>
		<script src="js/plugins/modernizr.js"></script>
		<script src="js/plugins/bootstrap.min.js"></script>
		<script src="js/plugins/jquery.cookie.js"></script>
		<script src="js/plugins/jquery.noty.js"></script>
      <!-- DashBoard Js Imports start -->
	<!-- <script src="js/datedashboard.js"></script> -->
	<script src="js/dashboard/highcharts.js"></script>
	<script src="js/dashboard/exporting.js"></script>
	<script src="js/dashboard/funnel.js"></script>
	<script src="js/dashboard/highcharts-3d.js"></script>
	<script src="js/reports.js"></script>
	 <script src="js/datereports.js"></script> 
	<script src="js/utilities.js"></script>
	<script src="js/plugins/custom.js"></script>
	
</body>
<script>
$(document).ready(function(){
	 $( "#sdatepicker" ).datepicker();
	 $( "#edatepicker" ).datepicker();
	 reports_branch_load(0);
	
});
function branchdb_dashboard(){
	
	var branch_index=document.getElementById("branches").value;
	dashboard_reports(branch_index);
	
}
window.history.forward();
function noBack() {
   window.history.forward();
}
</script>
</html>