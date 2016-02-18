<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- start: Meta -->
	<meta charset="utf-8">
	<title>Nutans-R+</title>
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
	
	<!-- start: Favicon -->
	<link rel="shortcut icon" href="images/Nlogo.png">
	<!-- end: Favicon -->
	<script src="js/dashboardscript.js"></script>
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
				<a class="brand" href="javascript:void(0);"><img src="img/Nlogo.png" style="margin-right:5px;"/><span>NUTANS-R+</span></a>
								
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
								<li><a href="logout" ><i class="halflings-icon off"></i> Logout</a></li>
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
						
                        <!-- <li><a href="http://www.nutansrplus.com/appstatus"><i class="icon-user"></i><span class="hidden-tablet"> Admin</span></a></li> -->	
                        <li class="active"><a href="home"><i class="icon-bar-chart"></i><span class="hidden-tablet"> Dashboard</span></a></li>	
						<li><a href="reportsearch"><i class="icon-search"></i><span class="hidden-tablet"> Reports</span></a></li>	
						<li><a href="dataanalytics"><i class="icon-globe"></i><span class="hidden-tablet"> Data Analytics</span></a></li>
						<li><a href="records"><i class="icon-folder-close-alt"></i><span class="hidden-tablet"> Records</span></a></li>	
						 <li><a href="appstatus"><i class="icon-random"></i><span class="hidden-tablet"> Appstatus</span></a></li> 	
						<!-- <li><a href="#"><i class="icon-info-sign"></i><span class="hidden-tablet"> Support</span></a></li> -->	

					</ul>
				</div>
			</div>
			<!-- end: Main Menu -->
			<div id="loader" class="loading"  ><!-- style="top:150px;left:578px;position:absolute;z-index:999999;visibility:hidden;"  -->
			<img src="images/loader.gif" />
			</div>
			<noscript>
				<div class="alert alert-block span10">
					<h4 class="alert-heading">Warning!</h4>
					<p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
				</div>
			</noscript>
			
			<!-- start: Content -->
			<div id="content" class="span10">
			
			
			<ul class="breadcrumb">
				<li>
					<i class="icon-home"></i>
					<a href="#">Home</a> 
					<i class="icon-angle-right"></i>
				</li>
				<li><a href="#">Dashboard</a></li>
			</ul>
<div class="row-fluid">
<select id="branches" onchange="branchdb_dashboard();">
</select></div>
			<div class="row-fluid">
				
				<div class="span3 statbox purple" onTablet="span6" onDesktop="span3">
					<div style="font-size:64px"><i class="icon-upload-alt"></i></div>
					<div class="number" id="requestcount"></div>
					<div class="title">Request Count</div>
					
				</div>
				<div class="span3 statbox green" onTablet="span6" onDesktop="span3">
					<div style="font-size:64px"><i class="icon-trash"></i></div>
					<div class="number" id="junkcount"></div>
					<div class="title">Autocancel Count</div>
				</div>
				<div class="span3 statbox blue noMargin" onTablet="span6" onDesktop="span3">
					<div style="font-size:64px"><i class="icon-star"></i></div>
					<div class="number" id="nobranches"></div>
					<div class="title">No of Branches</div>
				</div>
				<div class="span3 statbox yellow" onTablet="span6" onDesktop="span3">
					<div style="font-size:64px"><i class="icon-calendar"></i></div>
					<div class="number" id="datacount"></div>
					<div class="title">Days Data</div>
				</div>	
				
			</div>		

			<div class="row-fluid">
				
				<div class="span8 widget" onTablet="span7" onDesktop="span8" style="border:1px solid #ccc;">
					
					<div id="prodective"  style="height:282px" ></div>
					
				</div>
				<div id="overall" class="span4 widget" style="height:302px;border:1px solid #ccc;"></div>
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
	<script src="js/plugins/jquery-1.9.1.min.js"></script>
	 <script src="js/plugins/jquery-migrate-1.0.0.min.js"></script>
	<script src="js/plugins/jquery-ui-1.10.0.custom.min.js"></script>
	<script src="js/plugins/jquery.ui.touch-punch.js"></script>
	<script src="js/plugins/modernizr.js"></script>
	<script src="js/plugins/bootstrap.min.js"></script>
	<script src="js/plugins/jquery.cookie.js"></script>
	<!--<script src='js/plugins/jquery.dataTables.min.js'></script>
    <script src="js/plugins/excanvas.js"></script>
	<script src="js/plugins/jquery.flot.js"></script>
	<script src="js/plugins/jquery.flot.pie.js"></script>
	<script src="js/plugins/jquery.flot.stack.js"></script>
	<script src="js/plugins/jquery.flot.resize.min.js"></script>
    <script src="js/plugins/jquery.sparkline.min.js"></script>
    <script src="js/plugins/custom.js"></script> -->    
    <!-- DashBoard Js Imports start -->
	<script src="js/dashboard/highcharts.js"></script>
	<script src="js/dashboard/exporting.js"></script>
	<script src="js/dashboard/funnel.js"></script>
	<script src="js/dashboard/highcharts-3d.js"></script>
	<script src="js/utilities.js"></script>
	<script src="js/plugins/custom.js"></script>
</body>
<script>
$(document).ready(function(){
	
	document.getElementById("loader").style.visibility="visible";
	branch_load(1); 
	document.getElementById("loader").style.visibility="hidden";
});
function branchdb_dashboard(){
	var branch_index=document.getElementById("branches").value;
	document.getElementById("loader").style.visibility="visible";
	dashboard(branch_index);
	document.getElementById("loader").style.visibility="hidden";
	//dashboard_count();
}
window.history.forward();
function noBack() {
   window.history.forward();
}
</script>
</html>