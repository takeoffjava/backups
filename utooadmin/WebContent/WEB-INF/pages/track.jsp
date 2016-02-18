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
     <meta name="description" content="">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <script src="http://cdn.pubnub.com/pubnub-3.7.1.min.js"></script>
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
 <link rel="stylesheet" href="css/amaran.min.css">

    <style>
      html, body, #map-canvas {
        height: 100%;
        width:100%;
        margin: 0px;
        padding: 0px
      }
        .title {
            position: absolute;
            font-size: 19px;
            color: #727272;
            padding: 5px 10px;
            top: 10px;
            left: 50%;
            margin-left: -55px;
        }
        .divInside {
            background: #fbab56;
            padding: 10px;
            width: 100px;
        }
         .trackText {
      	text-align:center;
      	font-size:25px;
      	margin-top:15%;
      }
    </style>

    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>

  </head>
  <body>
    <div style="position:fixed;top:0;width:100%;background:#fff none repeat scroll 0 0;font-size:18px;z-index:9999;text-align:center;box-shadow:1px 1px 4px 3px #ccc;"><div class="divInside"><img src="images/logo.png" width="55" />
       <p class="title">TRACK RIDE FOR <span id="pbrnumber" style="color:#fbab56;font-weight:bold;"></span></p>
      </div> 
      </div>
   <p style="position:absolute;right:0;background:#ffffff;font-size:14px;padding:5px;display:inline-block;top:55px;box-shadow:1px 1px 4px 3px #ccc;">Passenger Name : <span id="passenger_name" style="color:#fbab56;font-weight:bold;"></span></p>
        <p style="position:absolute;bottom:0;font-size:14px;padding:5px;display:inline-block;box-shadow:1px 1px 4px 3px #ccc;">Driver Name : <span id="driver_name" style="color:#fbab56;font-weight:bold;"></span></p>
        
<!--    <div class="container"><h1 style="text-align:center;">UTOO</h1></div>-->
    <div id="map-canvas"></div>
     <script src="js/plugins/jquery-1.11.1.min.js"></script>
         <script src="js/plugins/jquery.amaran.js"></script>
     
     <script type="text/javascript" src="js/urlpath.js"></script>
    <script src="js/plugins/materialize.js"></script>
    <script src="js/plugins/jquery.dataTables.min.js"></script>
    <script src="js/plugins/DateTimePicker.js"></script>
    <script src="js/plugins/U2Popup.js"></script>
     <script type="text/javascript" src="js/base/utilities.js"></script>
     <script type="text/javascript" src="js/track.js"></script>
    <script>
    	$(document).ready(function() {
    		// setcommon();
    		 track();
    		 //pubsnotify();
    	});
    </script>
</body>
</html>
