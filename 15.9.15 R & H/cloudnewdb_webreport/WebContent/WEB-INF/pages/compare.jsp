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
	
	<!-- start: Favicon -->
	<link rel="shortcut icon" href="images/Nlogo.png">	
	<!-- end: Favicon -->
</head>

<body>
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
								<li><a href="login"><i class="halflings-icon off"></i> Logout</a></li>
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
                      <!--  <li><a href="http://www.nutansrplus.com/appstatus"><i class="icon-user"></i><span class="hidden-tablet"> Admin</span></a></li> -->	
                        <li><a href="home"><i class="icon-bar-chart"></i><span class="hidden-tablet"> Dashboard</span></a></li>	
						<li><a href="reportsearch"><i class="icon-search"></i><span class="hidden-tablet"> Report Search</span></a></li>	
						<li><a href="records"><i class="icon-folder-close-alt"></i><span class="hidden-tablet"> Records</span></a></li>	
						<li  class="active"><a href="compare"><i class="icon-random"></i><span class="hidden-tablet"> Compare</span></a></li>	
						<!-- <li><a href="#"><i class="icon-globe"></i><span class="hidden-tablet"> Locations</span></a></li> -->	

					</ul>
				</div>
			</div>
			<!-- end: Main Menu -->
			
			
			<!-- start: Content -->
			<div id="content" class="span10" style="z-index:999999999">
			
			
			<ul class="breadcrumb">
				<li>
					<i class="icon-home"></i>
					<a href="home">Home</a> 
					<i class="icon-angle-right"></i>
				</li>
				<li><a href="#">Compare</a></li>
			</ul>

                <div class="breadcrumb" style="border:1px solid #ccc;">
                <div class="span12" style="background:#f7f7f7;" >
				<div class="span3">
                    <div class="control-group">
							  <label class="control-label" for="start_date">Start Date</label>
							  <div class="controls">
								<!-- <input type="text" class="input-xlarge datepicker" id="start_date" > -->
								<input type="text" class="input-xlarge datepicker" id="sdatepicker" style="width:100%;padding:7px 6px;" />
							  </div>
							</div>
							<div id="errortext"></div>
                </div>
				<div class="span3">
                    <div class="control-group">
							  <label class="control-label" for="end_date">End Date</label>
							  <div class="controls">
								<!-- <input type="text" class="input-xlarge datepicker" id="end_date" > -->
								<input type="text" class="input-xlarge datepicker" id="edatepicker" style="width:100%;padding:7px 6px;" />
							  </div>
							</div>
                </div>
				<div class="span4">
                    <label class="control-label">&nbsp;</label>
                <button class="btn btn-primary" type="submit" onclick="return datedatacompare();">Search</button>
                 <button class="btn" onclick="return reset();">Reset</button>
                </div>
				</div>
				<div class="clearfix"></div>
			</div>		
                
			<div class="row-fluid">
				
                <div class="span6 noMargin" onTablet="span12" onDesktop="span6" id="table1">
					<div class="box-content">
						<ul class="dashboard-list metro">
						  <li>
							<a href="#">
							  <i class="icon-comment yellow"></i>
							  <strong id="month">&nbsp&nbsp&nbsp</strong>
							  Start-Day                                
							</a>
						  </li>
							<li>
								<a href="#">
									<i class="icon-arrow-up green"></i>                               
									<strong id="usercount">&nbsp&nbsp&nbsp</strong>
									Users                         
								</a>
							</li>
						  <li>
							<a href="#">
							  <i class="icon-arrow-down red"></i>
							  <strong id="tablecount">&nbsp&nbsp&nbsp</strong>
							  Tables
							</a>
						  </li>
						  <li>
							<a href="#">
							  <i class="icon-minus blue"></i>
							  <strong id="devicecount">&nbsp&nbsp&nbsp</strong>
							  Devices                              
							</a>
						  </li>
						  <li>
							<a href="#">
							  <i class="icon-comment yellow"></i>
							  <strong id="servicecount">&nbsp&nbsp&nbsp</strong>
							  Services                                    
							</a>
						  </li>
						  <li>
							<a href="#">
							  <i class="icon-arrow-up green"></i>                               
							  <strong id="drinkcount">&nbsp&nbsp&nbsp</strong>
							  Drinks                                   
							</a>
						  </li>
						  <li>
							<a href="#">
							  <i class="icon-arrow-down red"></i>
							  <strong id="billcount">&nbsp&nbsp&nbsp</strong>
							  Bills
							</a>
						  </li>
						  <li>
							<a href="#">
							  <i class="icon-comment yellow"></i>
							  <strong id="likecount">&nbsp&nbsp&nbsp</strong>
							  Likes
							</a>
						  </li>
						
						</ul>
					</div>
				</div>
                
                
                <!--2nd div-->
                
                <div class="span6 noMargin" onTablet="span12" onDesktop="span6" id="table2">
					<div class="box-content">
						<ul class="dashboard-list metro">
						<li>
							<a href="#">
							  <i class="icon-comment yellow"></i>
							  <strong id="month1">&nbsp&nbsp&nbsp</strong>
							  End-Day                               
							</a>
						  </li>
							<li>
								<a href="#">
									<i class="icon-arrow-up green"></i>                               
									<strong id="usercount1">&nbsp&nbsp&nbsp</strong>
									Users                         
								</a>
							</li>
						  <li>
							<a href="#">
							  <i class="icon-arrow-down red"></i>
							  <strong id="tablecount1">&nbsp&nbsp&nbsp</strong>
							  Tables
							</a>
						  </li>
						  <li>
							<a href="#">
							  <i class="icon-minus blue"></i>
							  <strong id="devicecount1">&nbsp&nbsp&nbsp</strong>
							  Devices                              
							</a>
						  </li>
						  <li>
							<a href="#">
							  <i class="icon-comment yellow"></i>
							  <strong id="servicecount1">&nbsp&nbsp&nbsp</strong>
							  Services                                    
							</a>
						  </li>
						  <li>
							<a href="#">
							  <i class="icon-arrow-up green"></i>                               
							  <strong id="drinkcount1">&nbsp&nbsp&nbsp</strong>
							  Drinks                                   
							</a>
						  </li>
						  <li>
							<a href="#">
							  <i class="icon-arrow-down red"></i>
							  <strong id="billcount1">&nbsp&nbsp&nbsp</strong>
							  Bills
							</a>
						  </li>
						  <li>
							<a href="#">
							  <i class="icon-comment yellow"></i>
							  <strong id="likecount1">&nbsp&nbsp&nbsp</strong>
							  Likes
							</a>
						  </li>
						</ul>
					</div>
				</div>                
			</div>		
	</div><!--/.fluid-container-->
	
			<!-- end: Content -->
		</div><!--/#content.span10-->
		</div><!--/fluid-row-->	
	<footer>

		<p>
			<span style="text-align:left;float:left">&copy; 2015 <a href="http://www.strobilanthes.com" >Strobilathes Technology Solutions pvt ltd</a></span>
			
		</p>

	</footer>
	 <script src="js/plugins/jquery-1.9.1.min.js"></script> 
		<script src="js/plugins/jquery-migrate-1.0.0.min.js"></script>
		<script src="js/plugins/jquery-ui-1.10.0.custom.min.js"></script>	
		<script src="js/plugins/jquery.ui.touch-punch.js"></script>
		<script src="js/plugins/modernizr.js"></script>
		<script src="js/plugins/bootstrap.min.js"></script>
		<script src="js/plugins/jquery.cookie.js"></script>
		<script src="js/plugins/jquery.noty.js"></script>
      <!-- DashBoard Js Imports start -->
	<script src="js/datedashboard.js"></script>
	<script src="js/dashboard/highcharts.js"></script>
	<script src="js/dashboard/exporting.js"></script>
	<script src="js/dashboard/funnel.js"></script>
	<script src="js/dashboard/highcharts-3d.js"></script>
	<script src="js/dashboardscript.js"></script>
	<script src="js/utilities.js"></script>
	<script src="js/login.js"></script>
	<script src="js/compare.js"></script>
	<script src="js/plugins/custom.js"></script>
	
	<!-- start: JavaScript-->
</body>
<script>
$(document).ready(function(){
	 $( "#sdatepicker" ).datepicker();
	 $( "#edatepicker" ).datepicker();
	 topname();
	 alldatacompare();
	
});
</script>
</html>