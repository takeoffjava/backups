<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<head>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
<title>UTOO Admin Panel</title>
<!-- Custom FavIcon -->
<link rel="icon" href="img/UTOO%20logo.ico" type="image/x-icon">
<!-- Custom CSS Fonts -->
    <!-- <link href="css/font-awesome.min.css" rel="stylesheet" type="text/css"> -->
<!-- Custom CSS Style -->
<link href="css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>
    <link href="css/style1.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/amaran.min.css">   
</head>
<!--This script for avoid back button in browser-->
<body class="bg_img" onload="noBack();" onpageshow="if(event.persisted) noBack();" onunload="">
<!--This div for logo positioning--> 
<div  id="container">
  <img src="img/UTOO%20logo.png"  id="logo_img_possition"/>
</div> 
  <div id="container2">
    <div>
      <div>
        <div class="term"><input type="text" placeholder="MobileNumber" maxlength="10" id="mobilenumber" class="loginInp" /></div>
      </div>
      <div>
      <div class="term"><input type="password" placeholder="Password" maxlength="15" id="password" onkeyup="inputKeyUp(event)" class="loginInp" /></div>
      </div>
  <div>
      <input type="button" class="clsButton" id="btnSignIn" value="SIGN IN" /></div>
      </div>
    </div>
<!--This for Popup box--> 
<div class="modalDialog" id="popup_login">
<div  id="container3">
<a href="#close" title="close" class="close">X</a>
    <div>
      <div>
        <div class="term"><input type="password" id="otpcode" placeholder="Enter OTP Value" maxlength="5" id="otpbox" class="textboxText" onkeyup="inputKeyUpotp(event)"/></div>
      </div>
      <div>
<div class="term"><input type="button"  class="clsButton" value="OTP VERIFICATION" id="otpverification" onclick="return otpverification();"/></div>
      </div>   
<div>
     <!--  <input type="button" class="clsButton" id="resendotp" value="RE-SEND OTP" /> -->
</div>
      </div>
    </div> 
</div>
</body>
<script type="application/javascript" src="js/plugins/jquery-1.11.1.min.js"></script>
<script src="js/plugins/jquery.amaran.js"></script>
<script type="text/javascript" src="js/urlpath.js"></script>

<!-- This is commom properties Js -->
<script type="text/javascript" src="js/plugins/general.js"></script>
<!--Custom JS Error Message-->
<script src="js/plugins/materialize.js"></script>
<script type="text/javascript" src="js/base/utilities.js"></script>
<script type="text/javascript" src="js/login.js"></script>
</html>
