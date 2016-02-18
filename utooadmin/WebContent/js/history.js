var table,URL_PATH=getUrlPath();
$(document)
		.ready(
				function() {

					getProfile();
					$.fn.dataTable.pipeline = function(opts) {
						// Configuration options
						var conf = $.extend({
							pages : 5, // number of pages to cache
							method : 'GET' // Ajax HTTP method
						}, opts);

						// Private variables for storing the cache
						var cacheLower = -1;
						var cacheUpper = null;
						var cacheLastRequest = null;
						var cacheLastJson = null;

						return function(request, drawCallback, settings) {
							var ajax = false;
							var requestStart = request.start;
							var drawStart = request.start;
							var requestLength = request.length;
							var requestEnd = requestStart + requestLength;

							if (settings.clearCache) {
								// API requested that the cache be cleared
								ajax = true;
								settings.clearCache = false;
							} else if (cacheLower < 0
									|| requestStart < cacheLower
									|| requestEnd > cacheUpper) {
								// outside cached data - need to make a request
								ajax = true;
							} else if (JSON.stringify(request.order) !== JSON
									.stringify(cacheLastRequest.order)
									|| JSON.stringify(request.columns) !== JSON
											.stringify(cacheLastRequest.columns)
									|| JSON.stringify(request.search) !== JSON
											.stringify(cacheLastRequest.search)) {
								// properties changed (ordering, columns, searching)
								ajax = true;
							}

							// Store the request for checking next time around
							cacheLastRequest = $.extend(true, {}, request);

							if (ajax) {
								// Need data from the server
								if (requestStart < cacheLower) {
									requestStart = requestStart
											- (requestLength * (conf.pages - 1));

									if (requestStart < 0) {
										requestStart = 0;
									}
								}

								cacheLower = requestStart;
								cacheUpper = requestStart
										+ (requestLength * conf.pages);

								request.start = requestStart;
								request.length = requestLength * conf.pages;

								// Provide the same `data` options as DataTables.
								if ($.isFunction(conf.data)) {
									// As a function it is executed with the data object as an arg
									// for manipulation. If an object is returned, it is used as the
									// data object to submit
									var d = conf.data(request);
									if (d) {
										$.extend(request, d);
									}
								} else if ($.isPlainObject(conf.data)) {
									// As an object, the data given extends the default
									$.extend(request, conf.data);
								}

								settings.jqXHR = $.ajax({
									"type" : conf.method,
									"url" : conf.url,
									"data" : request,
									"pageResize": true,
									"dataType" : "json",
									"cache" : false,
									"scrollY": "200px",
									"success" : function(json) {
										cacheLastJson = $
												.extend(true, {}, json);

										if (cacheLower != drawStart) {
											json.data.splice(0, drawStart
													- cacheLower);
										}
										json.data.splice(requestLength,
												json.data.length);

										drawCallback(json);
									}
								});
							} else {
								json = $.extend(true, {}, cacheLastJson);
								json.draw = request.draw; // Update the echo for each response
								json.data.splice(0, requestStart - cacheLower);
								json.data.splice(requestLength,
										json.data.length);

								drawCallback(json);
							}
						}
					};

					// Register an API method that will empty the pipelined data, forcing an Ajax
					// fetch on the next draw (i.e. `table.clearPipeline().draw()`)
					$.fn.dataTable.Api.register('clearPipeline()', function() {
						return this.iterator('table', function(settings) {
							settings.clearCache = true;
						});
					});

					table = $('#example')
							.DataTable(
									{
										"processing" : false,
										"serverSide" : true,
										"destroy" : true,
										"sDom": '‘<“top”t>t<tp>',
										"ordering": false,
										"ajax" : $.fn.dataTable
										.pipeline({
											url : URL_PATH+"getHistory/"+ sessionStorage.getItem("access_id") ,
											pages : 5
											
										}),
										"columns" : [ {
											"data" : "obooking.pbr_number",
											"defaultContent" : ""
										}, {
											"data" : "obooking.booked_source",
											"defaultContent" : ""
										}, {
											"data" : "obooking.booked_destination",
											"defaultContent" : ""
										} ,
										{
							                  'mRender': function (data, type, full) {
						                           var EditLinkText = '<span>'+convertDate(full.obooking.created_on)+'</span>';
						                           return EditLinkText;
							                  	}},
							                  	{
											"data" : "total_mins",
											"defaultContent" : ""
										}
										,
										{
							                  'mRender': function (data, type, full) {
						                           var EditLinkText = '<img src="images/rupees.png"></img>&nbsp&nbsp<span>'+full.amount+'</span>';
						                           return EditLinkText;
							                  	}}
										  , {
											"data" : "distance",
											"defaultContent" : ""
										}
												],
												
												 "error": function(reason) {
												        console.log("error encountered ! ");
												        // process reason here to know the type of the error
												        // and then take appropriate action
												    }
									});
				});

function pop_up(){	
	    $('#divActionView').U2Popup({
	        title: 'Map'
            , width: 800
            , height: 300
	    });	
}

function getProfile()
{
	 var objectForPost = null;
		var contentType = 'application/json; charset=utf-8';
		var servletPath = 'getDriverProfile';
		var method = "POST";
		
		
			objectForPost = {
				access_id : sessionStorage.getItem("access_id")

			};

			jQAjaxCallForAccountJSON(servletPath, method, contentType,
					objectForPost, 'getProfileCallBack');
			return true;
			
		return false;
	}
		function getProfileCallBack(resultObject)
		{
			if(resultObject.status==1)
				{
				
				$("#driverDetails").html("<div class='media'><div class='media-body'><p class='partnerTitle' id='slider_drivername'>"+resultObject.response.driver_name+"</p> <p class='margin_1' id='rating_stars'></p></div></div>");
				
				switch (resultObject.response.total_rating) {
				case 0:
					$("#rating_stars")
							.html(
									"<i class='icon_userLanding icon_starEmpty'></i>"
											+ "<i class='icon_userLanding icon_starEmpty'></i>"
											+ "<i class='icon_userLanding icon_starEmpty'></i>"
											+ "<i class='icon_userLanding icon_starEmpty'></i>"
											+ "<i class='icon_userLanding icon_starEmpty'></i>");
					break;
				case 1:
					$("#rating_stars")
							.html(
									"<i class='icon_userLanding icon_starFull'></i>"
											+ "<i class='icon_userLanding icon_starEmpty'></i>"
											+ "<i class='icon_userLanding icon_starEmpty'></i>"
											+ "<i class='icon_userLanding icon_starEmpty'></i>"
											+ "<i class='icon_userLanding icon_starEmpty'></i>");
					break;
				case 2:
					$("#rating_stars")
							.html(
									"<i class='icon_userLanding icon_starFull'></i>"
											+ "<i class='icon_userLanding icon_starFull'></i>"
											+ "<i class='icon_userLanding icon_starEmpty'></i>"
											+ "<i class='icon_userLanding icon_starEmpty'></i>"
											+ "<i class='icon_userLanding icon_starEmpty'></i>");
					break;
				case 3:
					$("#rating_stars")
							.html(
									"<i class='icon_userLanding icon_starFull'></i>"
											+ "<i class='icon_userLanding icon_starFull'></i>"
											+ "<i class='icon_userLanding icon_starFull'></i>"
											+ "<i class='icon_userLanding icon_starEmpty'></i>"
											+ "<i class='icon_userLanding icon_starEmpty'></i>");
					break;
				case 4:
					$("#rating_stars")
							.html(
									"<i class='icon_userLanding icon_starFull'></i>"
											+ "<i class='icon_userLanding icon_starFull'></i>"
											+ "<i class='icon_userLanding icon_starFull'></i>"
											+ "<i class='icon_userLanding icon_starFull'></i>"
											+ "<i class='icon_userLanding icon_starEmpty'></i>");
					break;
				default:
					$("#rating_stars")
							.html(
									"<i class='icon_userLanding icon_starFull'></i>"
											+ "<i class='icon_userLanding icon_starFull'></i>"
											+ "<i class='icon_userLanding icon_starFull'></i>"
											+ "<i class='icon_userLanding icon_starFull'></i>"
											+ "<i class='icon_userLanding icon_starFull'></i>");
				}
				}
			}
		function handleAjaxError( xhr, textStatus, error ) {
		    if ( textStatus === 'timeout' ) {
		    	errMsg( 'The server took too long to send the data.' );
		    }
		    else {
		    	errMsg( 'An error occurred on the server. Please try again in a minute.' );
		    }
		    $("#example").fnProcessingIndicator( false );
		}
	function getHistoryByDate()
	{
		var fromdate = $("#fromdate").val();
		var todate = $("#todate").val();
		
		

		$('#example').dataTable().fnClearTable();
		if (fromdate === "" || todate === "") {
			Materialize.toast('Date should not be empty!', 4000);
			return false;
		}
		table = $('#example')
		.DataTable(
				{
					"processing" : false,
					"serverSide" : true,
					"destroy" : true,
					"sDom": '‘<“top”t>t<tp>',
					"ordering": false,
					"ajax" : $.fn.dataTable
					.pipeline({
						url : URL_PATH+"getHistoryByDate/"+ sessionStorage.getItem("access_id")+"/"+fromdate+"/"+todate,
						pages : 5
						
					}),
					"columns" : [ {
						"data" : "obooking.pbr_number",
						"defaultContent" : ""
					}, {
						"data" : "obooking.booked_source",
						"defaultContent" : ""
					}, {
						"data" : "obooking.booked_destination",
						"defaultContent" : ""
					} ,
					{
		                  'mRender': function (data, type, full) {
	                           var EditLinkText = '<span>'+convertDate(full.obooking.created_on)+'</span>';
	                           return EditLinkText;
		                  	}},
		                  	{
						"data" : "total_mins",
						"defaultContent" : ""
					}
					,
					{
		                  'mRender': function (data, type, full) {
	                           var EditLinkText = '<img src="images/rupees.png"></img>&nbsp&nbsp<span>'+full.amount+'</span>';
	                           return EditLinkText;
		                  	}}
					  , {
						"data" : "distance",
						"defaultContent" : ""
					}
							],
							 "error": function(reason) {
							        console.log("error encountered ! ");
							        // process reason here to know the type of the error
							        // and then take appropriate action
							    }
				});
	}
	
	function convertDate(myDate) {

		var parts, date, time, dt, ms;

	    parts = myDate.split(/[T ]/); // Split on `T` or a space to get date and time
	    date = parts[0];
	    time = parts[1];

	    dt = new Date();

	    parts = date.split(/[-\/]/);  // Split date on - or /
	    dt.setFullYear(parseInt(parts[0], 10));
	    dt.setMonth(parseInt(parts[1], 10) - 1); // Months start at 0 in JS
	    dt.setDate(parseInt(parts[2], 10));

	    parts = time.split(/:/);    // Split time on :
	    dt.setHours(parseInt(parts[0], 10));
	    dt.setMinutes(parseInt(parts[1], 10));
	    dt.setSeconds(parseInt(parts[2], 10));

	    ms = dt.getDate()+"-"+(dt.getMonth()+1)+"-"+dt.getFullYear();
	    return ms;
	}
	
/*function GetAddress(latitude,longitude) {
    var lat = parseFloat(latitude);
    var lng = parseFloat(longitude);
    var latlng = new google.maps.LatLng(lat, lng);
    var geocoder = geocoder = new google.maps.Geocoder();
    geocoder.geocode({ 'latLng': latlng }, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            if (results[1]) {
                alert("Location: " + results[1].formatted_address);
            }
        }
    });
}*/
	
