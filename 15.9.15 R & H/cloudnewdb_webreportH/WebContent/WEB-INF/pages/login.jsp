<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- start: Meta -->
	<meta charset="utf-8">
	<title>Nutans-H+</title>
	<meta name="description" content="Bootstrap Metro Dashboard">
	<meta name="author" content="Dennis Ji">
	<meta name="keyword" content="Metro, Metro UI, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
	<!-- end: Meta -->
	<!-- start: Mobile Specific -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- end: Mobile Specific -->	
	<!-- start: CSS -->
	<link id="bootstrap-style" href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/bootstrap-responsive.min.css" rel="stylesheet">
	<link href="css/responsiveLogin.css" rel="stylesheet">
	<link id="base-style" href="css/style.css" rel="stylesheet">
	<link id="base-style-responsive" href="css/style-responsive.css" rel="stylesheet">
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext' rel='stylesheet' type='text/css'>
	<!-- end: CSS -->
	<!-- start: Favicon -->
	<link rel="shortcut icon" href="images/Nlogo.png">	
	<!-- end: Favicon -->
	
<style type="text/css">
body {
       background: url("img/beds.jpg") 100% 100%  fixed no-repeat transparent;
       background-size:100% 100%;
     }

</style>
</head>
<body  onload="noBack();" onpageshow="if(event.persisted) noBack();">
    <div class="container-fluid-full">
		<div class="row-fluid">
        <div class="loginSide">
        		<div style="margin-bottom:10px;text-align:center;">
        		<img src="img/NlogoLarge.png" style="width:50%;height:50%" />
        		</div>
                <h1>NUTANS - H+</h1>
            <div class="loginControlTypes">
            <input type="text" name="username" class="loginControl" placeholder="Username" id="username"/>
            <input type="password" name="password" class="loginControl" id="password" placeholder="Password"  onkeyup="inputKeyUp(event)"/>
            <div style="text-align:right;margin-bottom:20px;">
            <button class="btn btn-large purple" onclick="return login();">Login</button>
                </div>
                <div id="errortext"></div>
           <!--  <p class="loginLinks"><a href="#">Forgot Password?</a></p> -->
            </div>
            <p>Copyright &copy; Strobilanthes Technology Solutions PVT Ltd India.</p>
            </div>
        </div>
    </div>
	    <!-- start: JavaScript-->
		<script src="js/plugins/jquery-1.9.1.min.js"></script>
		<script src="js/plugins/jquery-migrate-1.0.0.min.js"></script>
		<script src="js/plugins/jquery-ui-1.10.0.custom.min.js"></script>	
		<script src="js/plugins/jquery.ui.touch-punch.js"></script>
		<script src="js/plugins/modernizr.js"></script>
		<script src="js/plugins/bootstrap.min.js"></script>
		<script src="js/plugins/jquery.cookie.js"></script>
		<script src="js/plugins/jquery.noty.js"></script>
		<script src="js/login.js"></script>
		<script src="js/utilities.js"></script>
	<!-- end: JavaScript-->
</body>
<script>
function inputKeyUp(e) {
    e.which = e.which || e.keyCode;
    if(e.which == 13) {
    	login();
    }
}
</script>
<script type="text/javascript">
    window.history.forward();
    function noBack() {
       window.history.forward();
    }
</script>

</html>