<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- start: Meta -->
	<meta charset="utf-8">
	<title>Nutans - R +</title>
	<meta name="description" content="Bootstrap Metro Dashboard">
	<meta name="author" content="Strobilanthes Technology Solution India PVT Ltd">
	<meta name="keyword" content="">
	<!-- end: Meta -->
	<!--Data taable css starts  -->
	  <link rel="Stylesheet" href="css/bootstrap.mindata.css" /> 
	<link rel="Stylesheet" href="css/dataTables.bootstrap.css" />
	<!--Data taable css end  -->
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
	<link rel="shortcut icon" href="images/Nlogo.png">	
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
				<a class="brand" href="home"><img src="img/Nlogo.png" style="margin-right:5px;"/><span>NUTANS-R+</span></a>
								
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
								<li><a href="logout"><i class="halflings-icon off"></i> Logout</a></li>
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
                   <!--     <li><a href="http://www.nutansrplus.com/appstatus"><i class="icon-user"></i><span class="hidden-tablet"> Admin</span></a></li> -->	
                        <li><a href="home"><i class="icon-bar-chart"></i><span class="hidden-tablet"> Dashboard</span></a></li>	
						<li><a href="reportsearch"><i class="icon-search"></i><span class="hidden-tablet"> Reports</span></a></li>	
						<li><a href="dataanalytics"><i class="icon-globe"></i><span class="hidden-tablet"> Data Analytics</span></a></li>
						<li class="active"><a href="records"><i class="icon-folder-close-alt"></i><span class="hidden-tablet"> Records</span></a></li>
						 <li><a href="appstatus"><i class="icon-random"></i><span class="hidden-tablet"> Appstatus</span></a></li> 
						<!-- <li><a href="#"><i class="icon-globe"></i><span class="hidden-tablet"> Locations</span></a></li> -->	

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
					<a href="home">Home</a> 
					<i class="icon-angle-right"></i>
				</li>
				<li><a href="#">Records</a></li>
				<li><a href="#">Select Branch</a></li>
				<li><select id="branches" onchange="branchdb_datatable();"></select></li>
			</ul>
			 <div style="z-index:-9999;padding:1px 0px 0px 30px;width:90%;;border:0px solid #ff00ff;"> 
		<table id="appdatas" class="table table-striped table-bordered" cellspacing="0" style="font-size:12px;" >
		</table>
		<!-- </table> -->
	</div>
			</div>

	</div><!--/.fluid-container-->
	
			<!-- end: Content -->
		</div><!--/#content.span10-->
		</div><!--/fluid-row-->
    <div class="clearfix"></div>
	
	<footer>

		<p>
			<span style="text-align:left;float:left">&copy; 2015 <a href="http://www.strobilanthes.com" >Strobilathes Technology Solutions pvt ltd</a></span>
			
		</p>

	</footer>
</body>
<!-- start: JavaScript-->
   	 <script src="js/plugins/jquery-1.9.1.min.js"></script> 
		<script src="js/plugins/jquery-migrate-1.0.0.min.js"></script>
		<script src="js/plugins/jquery-ui-1.10.0.custom.min.js"></script>	
		<script src="js/plugins/jquery.ui.touch-punch.js"></script>
		<script src="js/plugins/modernizr.js"></script>
		<script src="js/plugins/bootstrap.min.js"></script>
		<script src="js/plugins/jquery.cookie.js"></script>
		<script src="js/plugins/jquery.noty.js"></script>
    <!-- Datatable Imports Start -->
    <script src="js/datatable/jquery.dataTables.js"></script>
	<script src="js/datatable/jquery.dataTables.min.js"></script>
	<script src="js/datatable/dataTables.bootstrap.js"></script>
	 <!-- Datatable Imports End -->
      <!-- DashBoard Js Imports start -->
	<script src="js/datedashboard.js"></script>
	<script src="js/dashboard/highcharts.js"></script>
	<script src="js/dashboard/exporting.js"></script>
	<script src="js/dashboard/funnel.js"></script>
	<script src="js/dashboard/highcharts-3d.js"></script>
	<script src="js/dashboardscript.js"></script>
	<script src="js/utilities.js"></script>
	<script src="js/login.js"></script>
	<script src="js/reportsdatatable.js"></script>
	<script src="js/plugins/custom.js"></script>
<script>
$(document).ready(function(){
	 $( "#sdatepicker" ).datepicker();
	 $( "#edatepicker" ).datepicker();
	 document.getElementById("loader").style.visibility="visible";
	 loaddata_table();
	 topname();
	document.getElementById("loader").style.visibility="hidden";
});
</script>
<script>
$(document).ready(function() {
	//datatable_creation();
    $('#appdatas').DataTable({
    	"order": [[ 0, "asc" ]],
    	"iDisplayLength": 10
    }
    );   
});
function branchdb_datatable(){
	var branch_index=document.getElementById("branches").value;
	 document.getElementById("loader").style.visibility="visible";
	datatable_creationonchange(branch_index);
	document.getElementById("loader").style.visibility="hidden";
	
}
window.history.forward();
function noBack() {
   window.history.forward();
}
</script>
</html>