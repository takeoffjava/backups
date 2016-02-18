var map;
var map_marker;
var lat = null;
var lng = null;
var lineCoordinatesArray = [];
var bookingID;
var positions=[];
var numDeltas = 100;
var delay = 10; //milliseconds
var i = 0;
var deltaLat;
var deltaLng;
function track(){
	bookingID = window.location.search.substr(1);
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'track';
	var method = "POST";
	
		objectForPost = {				
				booking_number : bookingID
		};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'trackCallback');
}
function trackCallback(resultobject){
	
	$("#pbrnumber").html(resultobject.response[0].pbr_number);
	$("#driver_name").parent().hide();
	$("#passenger_name").parent().hide();
	if(resultobject.status == 0){
		return false;
	}else{
		if(resultobject.response[0].status==2)
			{
			$("#driver_name").html(resultobject.response[0].odrivers.driver_name).show();
			$("#passenger_name").html(resultobject.response[0].opassenger.passenger_name).show();
			
			// sets your location as default
	/*		if (navigator.geolocation) {
			  navigator.geolocation.getCurrentPosition(function(position) { 
			    var locationMarker = null;
			    if (locationMarker){
			      // return if there is a locationMarker bug
			      return;
			    }
			    console.log(position);
			    lat = position.coords["latitude"];
			    lng = position.coords["longitude"];
			    positions.push(lat);
			    positions.push(lng);
			    // calls PubNub
			    pubs();

			    // initialize google maps
			    google.maps.event.addDomListener(window, 'load', initialize());
			    google.maps.event.addDomListener(window, 'resize', function() {
			    map.setCenter(center);
			});
			  },
			  function(error) {
			    console.log("Error: ", error);
			  },
			  {
			    enableHighAccuracy: true
			  }
			  );
			} */   
			lat =resultobject.response[0].departure_latitude;
		    lng = resultobject.response[0].departure_longitude;
		    positions.push(lat);
		    positions.push(lng);
		    // calls PubNub
		    pubs();

		    // initialize google maps
		    google.maps.event.addDomListener(window, 'load', initialize());
		  /*  google.maps.event.addDomListener(window, 'resize', function() {
		    map.setCenter(center);});*/

			}
		else if(resultobject.response[0].status==4) {
			$("#map-canvas").html("<p class='trackText'>It seems you are tracking for cancelled booking</p>");
		}
		else if(resultobject.response[0].status==3) {
			$("#map-canvas").html("<p class='trackText'>You can't Track in Completed booking</p>");
			}
		else {
			$("#map-canvas").html("<p class='trackText'>Tracking unavailable</p>");
		}
		
	}
}


function initialize() {
  var latlng = new google.maps.LatLng(positions[0], positions[1]);
  map = new google.maps.Map(document.getElementById('map-canvas'), {
    zoom: 13,
    center: latlng,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  });

  map_marker = new google.maps.Marker({position: latlng, map: map,icon:"images/car.png",rotation:45});
  map_marker.setMap(map);
}

// moves the marker and center of map
function redraw() {
  map.setCenter({lat: lat, lng : lng, alt: 0});
  map_marker.setPosition({lat: lat, lng : lng, alt: 0});
  /*  pushCoordToArray(lat, lng);

  var lineCoordinatesPath = new google.maps.Polyline({
    path: lineCoordinatesArray,
    geodesic: true,
    strokeColor: '#2E10FF',
    strokeOpacity: 1.0,
    strokeWeight: 2
  });
   lineCoordinatesPath.setMap(map);*/
}


function pushCoordToArray(latIn, lngIn) {
  lineCoordinatesArray.push(new google.maps.LatLng(latIn, lngIn));
}

function transition(result){
    i = 0;
    deltaLat = (result[0] - positions[0])/numDeltas;
    deltaLng = (result[1] - positions[1])/numDeltas;
    moveMarker();

}

function moveMarker(){
    positions[0] += deltaLat;
    positions[1] += deltaLng;
    var latlng = new google.maps.LatLng(positions[0], positions[1]);
    map_marker.setPosition(latlng);
    if(i!=numDeltas){
        i++;
        setTimeout(moveMarker, delay);
    }
    
}


function pubs() {
  pubnub = PUBNUB.init({
    publish_key: 'pub-c-91dfeb77-0e7d-4d8e-b1f7-dbeed1e7285b',
    subscribe_key: 'sub-c-b5842618-c008-11e5-bcee-0619f8945a4f'
  });
 
  pubnub.subscribe({
    channel: bookingID,
    message: function(message, channel) {
      lat = parseFloat(message.latlong.latitude);
      lng = parseFloat(message.latlong.longitude);
      var result = [lat,lng];
      transition(result);
      //custom method
      redraw();
    },
    connect: function() {console.log("PubNub Connected");}
  });
}


