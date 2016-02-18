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
				<a class="brand" href="index.html"><span>NUTANS-R+</span></a>
								
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
								<li><a href="login"><i class="halflings-icon off"></i>Logout</a></li>
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
                       <li class="active"><a href="http://www.nutansrplus.com/appstatus"><i class="icon-user"></i><span class="hidden-tablet"> Admin</span></a></li>	
                        <li><a href="home"><i class="icon-bar-chart"></i><span class="hidden-tablet"> Dashboard</span></a></li>	
						<li><a href="reportsearch"><i class="icon-search"></i><span class="hidden-tablet"> Report Search</span></a></li>	
						<li><a href="records"><i class="icon-folder-close-alt"></i><span class="hidden-tablet"> Records</span></a></li>	
						<li><a href="compare"><i class="icon-random"></i><span class="hidden-tablet"> Compare</span></a></li>	
						<!-- <li><a href="#"><i class="icon-globe"></i><span class="hidden-tablet"> Locations</span></a></li> -->		
					</ul>
				</div>
			</div>
			<!-- end: Main Menu -->
			
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
					<a href="index.html">Home</a> 
					<i class="icon-angle-right"></i>
				</li>
				<li><a href="#">Admin</a></li>
			</ul>

            <div class="row-fluid">
			 <div class="box span12" onTablet="span12" onDesktop="span12">
					<div class="box-header">
						<h2>Thalapakatti Briyani</h2>
				    </div>
					<div class="box-content">
						<div class="span2"><img alt="" src="http://www.pinkcurtains.in/wp-content/uploads/2013/06/dindigul-thalappakatti.gif" width="122" height="122" /></div>
						
                        <div class="span2 quick-button metro yellow">
                        <i class="icon-user"></i>
							<p>SERVICE</p>
							<span class="notification green">167</span>
                        </div>
						<div class="span2 quick-button metro green">
                        <i class="icon-money"></i>
							<p>BILL</p>
							<span class="notification green">167</span>
                        </div>
                        <div class="span2 quick-button metro pink">
                        <i class="icon-glass"></i>
							<p>DRINK</p>
							<span class="notification green">167</span>
                        </div>
                        <div class="span2 quick-button metro red">
                        <i class="icon-warning-sign"></i>
							<p>DAYS MISSING</p>
							<span class="notification green">167</span>
                        </div>
                        <div class="span2 quick-button metro blue">
                        <i class="icon-star"></i>
							<p>SERVER STATUS</p>
							<span class="notification green">Running</span>
                        </div>
                        		<div class="clearfix"></div>

					</div>
				</div>	
				
				
			</div>		

                <!--2nd box-->
                <div class="row-fluid">
			 <div class="box span12" onTablet="span12" onDesktop="span12">
					<div class="box-header">
						<h2>Thalapakatti Briyani</h2>
				    </div>
					<div class="box-content">
						<div class="span2"><img alt="" src="http://www.pinkcurtains.in/wp-content/uploads/2013/06/dindigul-thalappakatti.gif" width="122" height="122" /></div>
						
                        <div class="span2 quick-button metro yellow">
                        <i class="icon-user"></i>
							<p>SERVICE</p>
							<span class="notification green">167</span>
                        </div>
						<div class="span2 quick-button metro green">
                        <i class="icon-money"></i>
							<p>BILL</p>
							<span class="notification green">167</span>
                        </div>
                        <div class="span2 quick-button metro pink">
                        <i class="icon-glass"></i>
							<p>DRINK</p>
							<span class="notification green">167</span>
                        </div>
                        <div class="span2 quick-button metro red">
                        <i class="icon-warning-sign"></i>
							<p>DAYS MISSING</p>
							<span class="notification green">167</span>
                        </div>
                        <div class="span2 quick-button metro blue">
                        <i class="icon-star"></i>
							<p>SERVER STATUS</p>
							<span class="notification green">Running</span>
                        </div>
                        		<div class="clearfix"></div>

					</div>
				</div>	
    </div>	
        <!--another style-->
                <div class="row-fluid">
                <table class="table table-striped  bootstrap-datatable datatable">
						  <thead>
							  <tr>
								  <th>Restaurant Name</th>
								  <th>Branch</th>
								  <th>Bill</th>
								  <th>Service</th>
								  <th>Drink</th>
								  <th>Data Missing</th>
								  <th>Server Status</th>
							  </tr>
						  </thead>   
						  <tbody>
							<tr>
								<td>Thalapakatti</td>
								<td>Anna nagar</td>
								<td class="green"><i class="icon-money"></i> 15</td>
								<td class="yellow"><i class="icon-user"></i> 22323</td>
								<td class="pink"><i class="icon-glass"> 12122</td>
								<td class="red"><i class="icon-warning-sign"></i> 12122</td>
								<td class="center">
									<span class="label label-success">Active</span>
								</td>
				            </tr>
                              <tr>
								<td>Thalapakatti</td>
								<td>Anna nagar</td>
								<td class="green"><i class="icon-money"></i> 15</td>
								<td class="yellow"><i class="icon-user"></i> 22323</td>
								<td class="pink"><i class="icon-glass"> 12122</td>
								<td class="red"><i class="icon-warning-sign"></i> 12122</td>
								<td class="center">
									<span class="label label-success">Active</span>
								</td>
				            </tr>
                              <tr>
								<td>Thalapakatti</td>
								<td>Anna nagar</td>
								<td class="green"><i class="icon-money"></i> 15</td>
								<td class="yellow"><i class="icon-user"></i> 22323</td>
								<td class="pink"><i class="icon-glass"> 12122</td>
								<td class="red"><i class="icon-warning-sign"></i> 12122</td>
								<td class="center">
									<span class="label label-success">Active</span>
								</td>
				            </tr>
                              <tr>
								<td>Thalapakatti</td>
								<td>Anna nagar</td>
								<td class="green"><i class="icon-money"></i> 15</td>
								<td class="yellow"><i class="icon-user"></i> 22323</td>
								<td class="pink"><i class="icon-glass"> 12122</td>
								<td class="red"><i class="icon-warning-sign"></i> 12122</td>
								<td class="center">
									<span class="label label-success">Active</span>
								</td>
				            </tr>
                    </tbody>
                    </table>
                </div>
			
			
						
			
			
			
				
			
			<!--/row-->
			
       

	</div><!--/.fluid-container-->
	
			<!-- end: Content -->
		</div><!--/#content.span10-->
		</div><!--/fluid-row-->
		<div class="clearfix"></div>
	
	<footer>

		<p>
			<span style="text-align:left;float:left">&copy; 2015 <a href="http://www.stroblanthes.com" >Strobilathes Technology Solutions pvt ltd</a></span>
			
		</p>

	</footer>
	
	 <script src="js/plugins/jquery-1.9.1.min.js"></script> 
      <!-- DashBoard Js Imports start -->
	<script src="js/datedashboard.js"></script>
	<script src="js/dashboard/highcharts.js"></script>
	<script src="js/dashboard/exporting.js"></script>
	<script src="js/dashboard/funnel.js"></script>
	<script src="js/dashboard/highcharts-3d.js"></script>
	<script src="js/dashboardscript.js"></script>
	<script src="js/utilities.js"></script>
	<script src="js/login.js"></script>
	<script src="js/plugins/jquery-ui.js"></script>

	<!-- end: JavaScript-->
	
</body>
<script>
$(document).ready(function(){
	 topname();
	
});
window.history.forward();
function noBack() {
   window.history.forward();
}
</script>
</html>