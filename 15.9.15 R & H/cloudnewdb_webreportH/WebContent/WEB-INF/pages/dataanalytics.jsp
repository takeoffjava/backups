<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Nutans H+ Data Analytics</title>
<link id="bootstrap-style" href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/bootstrap-responsive.min.css" rel="stylesheet">
<link id="base-style" href="css/style.css" rel="stylesheet">
<link id="base-style-responsive" href="css/style-responsive.css"
	rel="stylesheet">

<link type="text/css" rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,800,600|Raleway:300,600,900">
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/style1.css">
<script type="text/javascript" src="js/plugins/modernizr.js"></script>
<link rel="shortcut icon" href="images/Nlogo.png">	
</head>
<body style="background-color: white;">
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
				</a> <a class="brand" href="home"><img src="img/Nlogo.png"
					style="margin-right: 5px;" /><span>NUTANS-H+</span></a>

				<!-- start: Header Menu -->
				<div class="nav-no-collapse header-nav">
					<ul class="nav pull-right">
						<li class="dropdown hidden-phone"><a
							class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						</a></li>
						<!-- start: User Dropdown -->
						<li class="dropdown"><a class="btn dropdown-toggle"
							data-toggle="dropdown" href="#"> <i
								class="halflings-icon white user"></i><span id="username"></span>
								<span class="caret"></span>
						</a>
							<ul class="dropdown-menu">
								<li><a href="logout"><i class="halflings-icon off"></i>Logout</a></li>
							</ul></li>
						<!-- end: User Dropdown -->
					</ul>
				</div>
				<!-- end: Header Menu -->

			</div>
		</div>
	</div>
	<div class="container-fluid-full">

		<div class="row-fluid">
			<div id="sidebar-left" class="span2">
				<div class="nav-collapse sidebar-nav">
					<ul class="nav nav-tabs nav-stacked main-menu">
						<!--    <li><a href="http://www.nutansrplus.com/appstatus"><i class="icon-user"></i><span class="hidden-tablet"> Admin</span></a></li> -->
						<li><a href="home"><i class="icon-bar-chart"></i><span
								class="hidden-tablet"> Dashboard</span></a></li>
						<li><a href="reportsearch"><i class="icon-search"></i><span class="hidden-tablet"> Reports</span></a></li>
						 <li class="active"><a href="dataanalytics"><i class="icon-globe"></i><span class="hidden-tablet"> Data Analytics</span></a></li>
						<!-- <li><a href="records"><i class="icon-folder-close-alt"></i><span class="hidden-tablet"> Records</span></a></li>
						<li><a href="appstatus"><i class="icon-random"></i><span class="hidden-tablet"> Appstatus</span></a></li> -->
						<!-- <li><a href="#"><i class="icon-globe"></i><span class="hidden-tablet"> Locations</span></a></li> -->

					</ul>
				</div>
			</div>
			<div id="loader" class="loading">
				<!-- style="top:150px;left:578px;position:absolute;z-index:999999;visibility:hidden;"  -->
				<img src="images/loader.gif" />
			</div>
			<div id="content" class="span1" style="border: 0px solid #ff0000;">
				<ul class="breadcrumb">
					<li><i class="icon-home"></i> <a href="home">Home</a> <i
						class="icon-angle-right"></i></li>
					<li><a href="#">Reports</a> <i class="icon-angle-right"></i></li>
					<!-- <li><a href="#">Select Branch</a></li>
					<li><select id="branches" onchange="analytical_dashboard();"></select></li> -->
				</ul>
				<div class="container" style="border: 0px solid #ff0000;">
					<div class="mainCont">
						<div class="headCont">
							<div class="media">
								<img src="images/NlogoLarge.png" width="120"
									class="media-object pull-left" />
								<div class="media-body pull-left">
									<p class="dateText" id="date0"></p>
									<p class="logoText">NUTANS - H+</p>
								</div>
							</div>
						</div>

						<div class="contentCont">
							<br>
							<p class="contentText">This report aims in providing
								actionable insights to transform your services
								establishment.</p>
							<br>
							<p class="contentText">The inputs for this report were
								obtained from data captured through Strobilanthes' IoT platform-
								Nutans H+, which are in turn, analyzed, ascertained and
								classified to improvise operational excellence and customer
								experience.</p>
							<br> <br>

							<div class="section group">
								<h2 class="smTitle">1. Nurse Details</h2>

								<div class="width50">
									<div id="topuser" style='font-size: 16px'></div>
								</div>
								<div class="width50">
									<div id="pooruser" style='font-size: 16px'></div>
								</div>
							</div>
							<br> <br>

							<div class="section group">
								<h2 class="smTitle">2. Bed Details</h2>

								<div class="width50">
									<div id="toptable" style='font-size: 16px'></div>
								</div>
								<div class="width50">
									<div id="poortable" style='font-size: 16px'></div>
								</div>
							</div>
						</div>

						<div class="footCont">
							<p class="footerText">&copy; 2015 Strobilanthes Technology
								solutions</p>
						</div>
					</div>
				</div>

				<div class="container">
					<div class="mainCont">

						<div class="headCont">
							<div class="media">
								<img src="images/NlogoLarge.png" width="120"
									class="media-object pull-left" />
								<div class="media-body pull-left">
									<p class="dateText" id="date1"></p>
									<p class="logoText">NUTANS - H+</p>
								</div>
							</div>
						</div>

						<div class="contentCont">
							<h4 class="smTitle">Day wise Performance</h4>
							<p class="contentText">This analytical graph explains the
								past few weeks performance day-wise showcasing the highest and
								the lowest performance in minutes per day.</p>
							<table>
								<thead>
									<tr>
										<th>S.no</th>
										<th>Date</th>
										<th>Request Count</th>
									</tr>
								</thead>
								<tbody id="daywisetablebody">

								</tbody>
							</table>
							<div id="daywisedata"></div>
						</div>

						<div class="footCont">
							<p class="footerText">&copy; 2015 Strobilanthes Technology
								solutions</p>
						</div>
					</div>
				</div>

				<div class="container">
					<div class="mainCont">

						<div class="headCont">
							<div class="media">
								<img src="images/NlogoLarge.png" width="120"
									class="media-object pull-left" />
								<div class="media-body pull-left">
									<p class="dateText" id="date2"></p>
									<p class="logoText">NUTANS - H+</p>
								</div>
							</div>
						</div>

						<div class="contentCont">
							<h4 class="smTitle">Day wise Request Performance</h4>
							<p class="contentText">Referring to the more clean and
								specific view of the requests performance (clean, sos & nurse) this report will show the number of times each option from
								the remote has been used.</p>
							<table>
								<thead>
									<tr>
										<th>S.no</th>
										<th>Date</th>
										<th>Sos</th>
										<th>Nurse</th>
										<th>Clean</th>
									</tr>
								</thead>
								<tbody id="daywiserequesttablebody">

								</tbody>
							</table>
							<div id="daywiserequestdata"></div>

						</div>

						<div class="footCont">
							<p class="footerText">&copy; 2015 Strobilanthes Technology
								solutions</p>
						</div>
					</div>
				</div>

				<div class="container">
					<div class="mainCont">

						<div class="headCont">
							<div class="media">
								<img src="images/NlogoLarge.png" width="120"
									class="media-object pull-left" />
								<div class="media-body pull-left">
									<p class="dateText" id="date3"></p>
									<p class="logoText">NUTANS - H+</p>
								</div>
							</div>
						</div>

						<div class="contentCont">
							<h4 class="smTitle">Productive Hours</h4>
							<p class="contentText">The below figure refers to the most
								efficient and peak working hours of the hospitals giving you
								more knowledge of the highest as well as the lowest rate of
								service.</p>
							<table>
								<thead>
									<tr>
										<th>S.no</th>
										<th>Hours (0-23)</th>
										<th>Request Count</th>
									</tr>
								</thead>
								<tbody id="productivetablebody">

								</tbody>
							</table>
							<div id="productive"></div>
						</div>

						<div class="footCont">
							<p class="footerText">&copy; 2015 Strobilanthes Technology
								solutions</p>
						</div>
					</div>
				</div>
				<div class="container">
					<div class="mainCont">

						<div class="headCont">
							<div class="media">
								<img src="images/NlogoLarge.png" width="120"
									class="media-object pull-left" />
								<div class="media-body pull-left">
									<p class="dateText" id="date4"></p>
									<p class="logoText">NUTANS - H+</p>
								</div>
							</div>
						</div>

						<div class="contentCont">
							<h4 class="smTitle">SOS Average Response</h4>
							<p class="contentText">The duration time (in minutes) at which
								your customers have received their Sos from the Nurse has
								been clearly put to view.</p>
							<table>
								<thead>
									<tr>
										<th>S.no</th>
										<th>Bed-No</th>
										<th>Average Response</th>
									</tr>
								</thead>
								<tbody id="billavgtablebody">

								</tbody>
							</table>
							<div id="billavg"></div>
						</div>

						<div class="footCont">
							<p class="footerText">&copy; 2015 Strobilanthes Technology
								solutions</p>
						</div>
					</div>
				</div>
				<div class="container">
					<div class="mainCont">

						<div class="headCont">
							<div class="media">
								<img src="images/NlogoLarge.png" width="120"
									class="media-object pull-left" />
								<div class="media-body pull-left">
									<p class="dateText" id="date5"></p>
									<p class="logoText">NUTANS - H+</p>
								</div>
							</div>
						</div>

						<div class="contentCont">
							<h4 class="smTitle">Nurse Average Response</h4>
							<p class="contentText">The duration time (in minutes)at which
								your customers have received their service after pressing the
								Nurse option from the remote.</p>
							<table>
								<thead>
									<tr>
										<th>S.no</th>
										<th>Bed-No</th>
										<th>Average Response</th>
									</tr>
								</thead>
								<tbody id="drinkavgtablebody">

								</tbody>
							</table>
							<div id="drinkavg"></div>
						</div>

						<div class="footCont">
							<p class="footerText">&copy; 2015 Strobilanthes Technology
								solutions</p>
						</div>
					</div>
				</div>
				<div class="container">
					<div class="mainCont">

						<div class="headCont">
							<div class="media">
								<img src="images/NlogoLarge.png" width="120"
									class="media-object pull-left" />
								<div class="media-body pull-left">
									<p class="dateText" id="date6"></p>
									<p class="logoText">NUTANS - H+</p>
								</div>
							</div>
						</div>

						<div class="contentCont">
							<h4 class="smTitle">Clean Average Response</h4>
							<p class="contentText">The duration time (in minutes)after the
								Clean button is clicked and at what exact minute the nurse
								has served them is shown in this graph to give the clear
								potential of the Nurses.</p>
							<table>
								<thead>
									<tr>
										<th>S.no</th>
										<th>Bed-No</th>
										<th>Average Response</th>
									</tr>
								</thead>
								<tbody id="serviceavgtablebody">

								</tbody>
							</table>
							<div id="serviceavg"></div>
						</div>

						<div class="footCont">
							<p class="footerText">&copy; 2015 Strobilanthes Technology
								solutions</p>
						</div>
					</div>
				</div>
				<div class="container">
					<div class="mainCont">

						<div class="headCont">
							<div class="media">
								<img src="images/NlogoLarge.png" width="120"
									class="media-object pull-left" />
								<div class="media-body pull-left">
									<p class="dateText" id="date7"></p>
									<p class="logoText">NUTANS - H+</p>
								</div>
							</div>
						</div>

						<div class="contentCont">
							<h4 class="smTitle">Over All Request Count</h4>
							<p class="contentText">The overall requests that have been
								made by the customers i,e.sos, nurse, clean has also been
								shown in numericals in the pie chart providing information.</p>
							<table>
								<thead>
									<tr>
										<th>S.no</th>
										<th>Request Name</th>
										<th>Total Count</th>
									</tr>
								</thead>
								<tbody id="overalltablebody">
								</tbody>
							</table>
							<div id="overallrequest"></div>
						</div>

						<div class="footCont">
							<p class="footerText">&copy; 2015 Strobilanthes Technology
								solutions</p>
						</div>
					</div>
				</div>
				<div class="container">
					<div class="mainCont">

						<div class="headCont">
							<div class="media">
								<img src="images/NlogoLarge.png" width="120"
									class="media-object pull-left" />
								<div class="media-body pull-left">
									<p class="dateText" id="date8"></p>
									<p class="logoText">NUTANS - H+</p>
								</div>
							</div>
						</div>

						<div class="contentCont">
							<h4 class="smTitle">Month wise Request Performance</h4>
							<p class="contentText">This graph specifies each days of the
								month showing an exact data in which day there was highest and
								the lowest response made.</p>
							<table>
								<thead>
									<tr>
										<th>S.no</th>
										<th>Day</th>
										<th>Request Count</th>
									</tr>
								</thead>
								<tbody id="monthwisetablebody">

								</tbody>
							</table>
							<div id="monthwise" class="row-fluid"></div>
						</div>

						<div class="footCont">
							<p class="footerText">&copy; 2015 Strobilanthes Technology
								solutions</p>
						</div>
					</div>
				</div>
				<div class="container">
					<div class="mainCont">

						<div class="headCont">
							<div class="media">
								<img src="images/NlogoLarge.png" width="120"
									class="media-object pull-left" />
								<div class="media-body pull-left">
									<p class="dateText" id="date9"></p>
									<p class="logoText">NUTANS - H+</p>
								</div>
							</div>
						</div>

						<div class="contentCont">
							<h4 class="smTitle">Auto cancel Beds</h4>
							<p class="contentText">This bar chart explains the
								automatically cancelled bed after the request has been
								made certain cases when the nurse do not press cancel after
								the service complete then that is the case where auto cancel
								occurs.</p>
							<table>
								<thead>
									<tr>
										<th>S.no</th>
										<th>Autocancelled-Bed-No</th>
										<th>Total Count</th>
									</tr>
								</thead>
								<tbody id="autocancelledtablebody">

								</tbody>
							</table>
							<div id="autocancel" class="row-fluid"></div>
						</div>

						<div class="footCont">
							<p class="footerText">&copy; 2015 Strobilanthes Technology
								solutions</p>
						</div>
					</div>
				</div>
				<div class="container">
					<div class="mainCont">

						<div class="headCont">
							<div class="media">
								<img src="images/NlogoLarge.png" width="120"
									class="media-object pull-left" />
								<div class="media-body pull-left">
									<p class="dateText" id="date10"></p>
									<p class="logoText">NUTANS - H+</p>
								</div>
							</div>
						</div>

						<div class="contentCont">
							<h4 class="smTitle">Mostly Requested Beds</h4>
							<p class="contentText">This bar chart explains the most requested
								bed (or) mostly used bed.</p>
							<table>
								<thead>
									<tr>
										<th>S.no</th>
										<th>Mostly Used Bed</th>
										<th>TotalCount</th>
									</tr>
								</thead>
								<tbody id="mostlyusedtablebody">

								</tbody>
							</table>
							<div id="mostusedtable" class="row-fluid"></div>
						</div>

						<div class="footCont">
							<p class="footerText">&copy; 2015 Strobilanthes Technology
								solutions</p>
						</div>
					</div>
				</div>
				<div class="container">
					<div class="mainCont">

						<div class="headCont">
							<div class="media">
								<img src="images/NlogoLarge.png" width="120"
									class="media-object pull-left" />
								<div class="media-body pull-left">
									<p class="dateText" id="date11"></p>
									<p class="logoText">NUTANS - H+</p>
								</div>
							</div>
						</div>

						<div class=""
							style="padding:150px 10px 10px 10px;height: 100%;">
							<h4 class="smTitle">Bed wise Request Counts</h4>
							<p class="contentText">This graph specifies overall device
								requests in bed-wise.</p>
							<!-- <table>
                    <thead>
                        <tr>
                            <th>S.no</th>
                            <th>Data Source</th>
                            <th>Task Analysed</th>
                            <th>Report</th>
                        </tr>   
                    </thead>
                    <tbody>
                        <tr><td>1</td><td>Documents</td><td>Business Transcation</td><td>26th June</td></tr>   
                        <tr><td>1</td><td>Documents</td><td>Business Transcation</td><td>26th June</td></tr>   
                        <tr><td>1</td><td>Documents</td><td>Business Transcation</td><td>26th June</td></tr>   
                        <tr><td>1</td><td>Documents</td><td>Business Transcation</td><td>26th June</td></tr>   
                        <tr><td>1</td><td>Documents</td><td>Business Transcation</td><td>26th June</td></tr>   
                    </tbody>
                   </table> -->
							<div id="alltableresponsecount"
								style=""></div>
						</div>

						<!-- <div class="footCont">
                    <p class="footerText">&copy; 2015 Strobilanthes Technology solutions</p>
                </div> -->
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
<script src="js/plugins/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/1.2.1/jquery-migrate.js"></script>
<script src="js/plugins/jquery-migrate-1.0.0.min.js"></script>
<script src="js/plugins/jquery-ui-1.10.0.custom.min.js"></script>
<script src="js/plugins/jquery.ui.touch-punch.js"></script>

<script src="js/plugins/modernizr.js"></script>
<script src="js/plugins/bootstrap.min.js"></script>
<script src="js/plugins/jquery.cookie.js"></script>
<script src="js/plugins/jquery.noty.js"></script>
<!-- DashBoard Js Imports start -->
<script src="js/dashboard/highcharts.js"></script>
<script src="js/dashboard/exporting.js"></script>
<script src="js/dashboard/funnel.js"></script>
<script src="js/dashboard/data.js"></script>
<script src="js/dashboard/highcharts-more.js"></script>
<script src="js/dashboard/highcharts-3d.js"></script>
<script src="js/dashboardscript.js"></script>
<script src="js/utilities.js"></script>
<script src="js/plugins/custom.js"></script>

<!-- dashboard(); -->
<script>
	$(document).ready(function() {
		//document.getElementById("loader").style.visibility = "visible";
		branch_load(2);
		//document.getElementById("loader").style.visibility = "hidden";
	});
	function analytical_dashboard() {
		var branch_index = document.getElementById("branches").value;
		document.getElementById("loader").style.visibility = "visible";
		dashboard(branch_index);
		document.getElementById("loader").style.visibility = "hidden";

	}
</script>
</html>