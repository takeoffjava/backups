var table, URL_PATH = getUrlPath();
var booking_click_number="";
var BOOKING_TYPE;
$(document)
		.ready(
				function() {

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
										"sDom": '‘<“top”>t<p>',
										"ordering": false,
										"ajax" : $.fn.dataTable
												.pipeline({
													url : URL_PATH+"getbookings_datatable",
													pages : 5
												}),
												   "fnInitComplete": function(oSettings, json) {
													   oSettings.fnDrawCallback= $("#bookingcount").text(oSettings._iRecordsTotal);
													    $("#completedBooking").text(json.bookingCount.endtrip);
														$("#cancelledBooking").text(json.bookingCount.canceltrip);
														$("#totalBooking").text(json.recordsTotal);
														$("#totalAmount").text(json.bookingCount.totalamount);
													    },
												"columns" : [{
													"data" : "pbr_number",
													"defaultContent" : ""
												},
											     {
													"data" : "bto_mobile",
													"defaultContent" : ""
												} , {
													"data" : "odrivers.driver_name",
													"defaultContent" : ""
												}, {
													"data" : "opassenger.passenger_name",
													"defaultContent" : ""
												} , {
													"data" : "admin_booked",
													"defaultContent" : ""
												}  , {
													"data" : "admin_reached",
													"defaultContent" : ""
												} ,{
					                                'mRender' : function(data, type, row) {
					                                    var span_str = "";
					                                    if (row.status === 1)
					                                        span_str = '<div class=\'guide\'>Guide</div>';
					                                    else if (row.status === 2)
					                                        span_str = '<div class=\'starttrip\'>Started</div>';
					                                    else if (row.status === 3)
					                                        span_str = '<div class=\'endtrip\'>Completed</div>';
					                                    else if (row.status === 4)
					                                        span_str = '<div class=\'canceltrip\'>Cancelled</div>';
					                                    else if (row.status === 5)
					                                        span_str = '<div class=\'pending\'>Pending</div>';
					                                    else if (row.status === 0)
					                                        span_str = '<div class=\'confirmbooked\'>Confirmed</div>';
					                                    
					                                    var EditLinkText = span_str;

					                                    return EditLinkText;
					                                }
					                            } ,{
					                                'mRender' : function(data, type, row) {
					                                    var span_str = "";
					                                    if (row.booking_type === 1)
					                                        span_str = '<div class=\'guide\'>Single</div>';
					                                    else if (row.booking_type === 2)
					                                        span_str = '<div class=\'starttrip\'>BTO</div>';
					                                    else if (row.booking_type === 3)
					                                        span_str = '<div class=\'endtrip\'>Ride Later</div>';
					                                    else if (row.booking_type === 4)
					                                        span_str = '<div class=\'canceltrip\'>Long Trip</div>';
					                                    else if (row.booking_type === 5)
					                                        span_str = '<div class=\'pending\'>Quick Book</div>';
					                                    else if (row.booking_type === 6)
					                                        span_str = '<div class=\'confirmbooked\'>QR Booking</div>';
					                                    
					                                    var EditLinkText = span_str;

					                                    return EditLinkText;
					                                }
					                            },{'mRender': function (data, type, row) {
													var reason_name=getunbookreason(row.reason_ID);
													 var EditLinkText ="";
													if(typeof(reason_name)==="undefined")
														{EditLinkText = '<div></div>';}
													else{
													  EditLinkText = '<div>'+reason_name+'</div>';}
							                           return EditLinkText;
												}
												},{'mRender': function (data, type, row) {
													var reason_name=row.unbook_notes;

													 var EditLinkText ="";
													if(typeof(reason_name)==="undefined")
														{
								                		   EditLinkText = '<a href="#" id="bookingmap" data-a=\''+row.booking_number+'\' onclick=\'return comments_pop_up(this);\'><img src=\'images/commends.png\'/></a> ';

														}
													else{
													  EditLinkText = '<div style="word-wrap: break-word;word-break: break-all;">'+reason_name+'</div>';}
							                           return EditLinkText;
												}
												} , {
									                  'mRender': function (data, type, row) {
									                	  if(row.status === 3){
									                		  var EditLinkText = '<a href="#" id="bookingmap" data-a=\''+row.booked_source+'\' data-b=\''+row.destination+'\' onclick=\'return pop_up(this);\'> <img src=\'images/markIco.png\'/></a> | <a href=\'#\' id="invoice"  onclick="return getInvoiceDetails(\''+row.booking_number+'\',\''+row.bto_name+'\',\''+row.bto_mobile+'\',\''+row.booked_source+'\',\''+row.destination+'\',\''+row.booking_type+'\');"> <img src=\'images/billIco.png\'/></a>';
									                           return EditLinkText;
									                	  }else{
									                		  var EditLinkText = '<a href="#" id="bookingmap" data-a=\''+row.booked_source+'\' data-b=\''+row.destination+'\'  onclick="return false;"> <img src=\'images/markIco.png\' style="opacity:0.2;"/></a> | <a href=\'#\' id="invoice"  onclick="return getInvoiceDetails(\''+row.booking_number+'\',\''+row.bto_name+'\',\''+row.bto_mobile+'\',\''+row.booked_source+'\',\''+row.destination+'\',\''+row.booking_type+'\');"> <img src=\'images/billIco.png\'/></a>';
									                           return EditLinkText;
									                	  }
								                           
								                  }
												}
													]
										}	
									);
					});
	
	function getbookings() {		
		
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
									url : URL_PATH+"getbookingsbydate/"
											+ fromdate + "/" + todate,
									pages : 5
								}),

								"fnInitComplete": function(oSettings, json) {
									   oSettings.fnDrawCallback= $("#bookingcount").text(oSettings._iRecordsTotal);
										    $("#completedBooking").text(json.bookingCount.endtrip);
											$("#cancelledBooking").text(json.bookingCount.canceltrip);
											$("#totalBooking").text(json.recordsTotal);
											$("#totalAmount").text(json.bookingCount.totalamount);
									    },
								"columns" : [{
									"data" : "pbr_number",
									"defaultContent" : ""
								},
							     {
									"data" : "bto_mobile",
									"defaultContent" : ""
								} , {
									"data" : "odrivers.driver_name",
									"defaultContent" : ""
								}, {
									"data" : "opassenger.passenger_name",
									"defaultContent" : ""
								}, {
									"data" : "admin_booked",
									"defaultContent" : ""
								} , {
									"data" : "admin_reached",
									"defaultContent" : ""
								} ,{
	                                'mRender' : function(data, type, row) {
	                                    var span_str = "";
	                                    if (row.status === 1)
	                                        span_str = '<div class=\'guide\'>Guide</div>';
	                                    else if (row.status === 2)
	                                        span_str = '<div class=\'starttrip\'>Started</div>';
	                                    else if (row.status === 3)
	                                        span_str = '<div class=\'endtrip\'>Completed</div>';
	                                    else if (row.status === 4)
	                                        span_str = '<div class=\'canceltrip\'>Cancelled</div>';
	                                    else if (row.status === 5)
	                                        span_str = '<div class=\'pending\'>Pending</div>';
	                                    else if (row.status === 0)
	                                        span_str = '<div class=\'confirmbooked\'>Confirmed</div>';
	                                    var EditLinkText = span_str;

	                                    return EditLinkText;
	                                }
	                            } ,{
	                                'mRender' : function(data, type, row) {
	                                    var span_str = "";
	                                    if (row.booking_type === 1)
	                                        span_str = '<div class=\'guide\'>Single</div>';
	                                    else if (row.booking_type === 2)
	                                        span_str = '<div class=\'starttrip\'>BTO</div>';
	                                    else if (row.booking_type === 3)
	                                        span_str = '<div class=\'endtrip\'>Ride Later</div>';
	                                    else if (row.booking_type === 4)
	                                        span_str = '<div class=\'canceltrip\'>Long Trip</div>';
	                                    else if (row.booking_type === 5)
	                                        span_str = '<div class=\'pending\'>Quick Book</div>';
	                                    else if (row.booking_type === 6)
	                                        span_str = '<div class=\'confirmbooked\'>QR Booking</div>';
	                                    var EditLinkText = span_str;

	                                    return EditLinkText;
	                                }
	                            },{'mRender': function (data, type, row) {
									var reason_name=getunbookreason(row.reason_ID);
									 var EditLinkText ="";
									if(typeof(reason_name)==="undefined")
										{EditLinkText = '<div></div>';}
									else{
									  EditLinkText = '<div>'+reason_name+'</div>';}
			                           return EditLinkText;
								}
								},{'mRender': function (data, type, row) {
									var reason_name=row.unbook_notes;
									 var EditLinkText ="";
										if(typeof(reason_name)==="undefined")
											{
					                		   EditLinkText = '<a href="#" id="bookingmap" data-a=\''+row.booking_number+'\' onclick=\'return comments_pop_up(this);\'><img src=\'images/commends.png\'/></a> ';

											}
										else{
										  EditLinkText = '<div style="word-wrap: break-word;word-break: break-all;">'+reason_name+'</div>';}
				                           return EditLinkText;
								}
								} , {
					                  'mRender': function (data, type, row) {
					                	  if(row.status === 3){
					                		  var EditLinkText = '<a href="#" id="bookingmap" data-a=\''+row.booked_source+'\' data-b=\''+row.destination+'\' onclick=\'return pop_up(this);\'> <img src=\'images/markIco.png\'/></a> | <a href=\'#\' id="invoice"  onclick="return getInvoiceDetails(\''+row.booking_number+'\',\''+row.bto_name+'\',\''+row.bto_mobile+'\',\''+row.booked_source+'\',\''+row.destination+'\',\''+row.booking_type+'\');"> <img src=\'images/billIco.png\'/></a>';
					                           return EditLinkText;
					                	  }else{
					                		  var EditLinkText = '<a href="#" id="bookingmap" data-a=\''+row.booked_source+'\' data-b=\''+row.destination+'\'  onclick="return false;"> <img src=\'images/markIco.png\' style="opacity:0.2;"/></a> | <a href=\'#\' id="invoice"  onclick="return getInvoiceDetails(\''+row.booking_number+'\',\''+row.bto_name+'\',\''+row.bto_mobile+'\',\''+row.booked_source+'\',\''+row.destination+'\',\''+row.booking_type+'\');"> <img src=\'images/billIco.png\'/></a>';
					                           return EditLinkText;
					                	  }
				                           
				                  }
								}]
						}
					);
	
	}
	function pop_up(obj){	
		var source = obj.dataset.a;
		var destination = obj.dataset.b;
		$("#sourceTb").html(source);
		$("#destTb").html(destination);
		$("#statMap").html("<img src='https://maps.googleapis.com/maps/api/staticmap?center=13.082680,80.270718&zoom=12&size=550x300&maptype=roadmap&markers=icon:http://utootaxi.com/utooserver/base/marker.png|"+source+"|"+destination+"'/>");
		/*$("#statMap").html("<img src='https://maps.googleapis.com/maps/api/staticmap?center=13.082680,80.270718&zoom=12&size=550x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C13.116018,80.231665%7C13.113691,80.232843&key=AIzaSyBcgDRm0TiVAJ9f7F4M6na_aw6J15EbmF8'/>");*/
	    $('#divActionView').U2Popup({
	        title: 'Map View'
	            , width: 600
	            , height: 500
		    });	
	}
	
	function getInvoiceDetails(booking_number,btoname,btomobile,source,destination,bookingtype)
	{
		BOOKING_TYPE=bookingtype;
		if(typeof source ==="undefined")
			source="";
		if(typeof destination ==="undefined"){
			destination="";
		}
			
		$("#btoname").text(btoname);
		$("#btomobile").text(btomobile);
		$("#source").text(source);
		$("#destination").text(destination);
		var objectForPost = null;
		var contentType = 'application/json; charset=utf-8';
	    var servletPath = 'getInvoiceDetails';
	    var method = "POST";
	
		objectForPost = {
			booking_number : booking_number
		};
	
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'getInvoiceDetailsCallback');
			return true;
		return false;
	
	}
	
	function getInvoiceDetailsCallback(resultObject)
	{
		if(resultObject!=null)
			{
			if("response" in resultObject)
			{
				$("#txtInvoice").text(resultObject.response.invoice_no);
				$("#txtDistance").text(resultObject.response.distance+" "+ "kms");
				$("#txtTotalmins").text(resultObject.response.total_mins);
				$("#txtAmount").text("Rs. "+resultObject.response.amount);
				$("#txtTax").text(resultObject.response.tax+" %");
				$("#txtTotal").text("Rs. "+resultObject.response.total);
				invoice_pop_up();
				}
			else
				{

				if(BOOKING_TYPE==="4" || BOOKING_TYPE==="5" || BOOKING_TYPE==="2"|| BOOKING_TYPE==="3")
				{
					$("#txtDistance").text("---");
					$("#txtTotalmins").text("---");
					$("#txtAmount").text("---");
					$("#txtTax").text("---");
					$("#txtTotal").text("---");
					invoice_pop_up();
				}else{
				errMsg("No Records");
				}
				}
			
			}
	}
	function invoice_pop_up(){	
	
		$("#invoicedetails").html("");
	    $('#divInvoiceView').U2Popup({
	        title: 'Booking Details'
	            , width: 800
	            , height: 500
		    });	
	}
	
function getunbookreason(id)
{
	var retrievedObject = localStorage.getItem('unbook_reasons');

	var parsedObject=JSON.parse(retrievedObject);

	for(var i=0;i<parsedObject.length;i++)
	{

	if(parsedObject[i].reason_ID===id)
		{
		return parsedObject[i].reason_type_name;}
	else if(parsedObject[i].reason_ID===0)
		{
		return "";
		}
	}}
function comments_pop_up(obj){	
	$("#txtComments").val("");
	 booking_click_number = obj.dataset.a;

    $('#divcommentspopup').U2Popup({
        title: 'Comments'
            , width: 640
            , height: 315
	    });	
}
    function addComments()
    {
		var comments =$("#txtComments").val();
		var objectForPost = null;
		var contentType = 'application/json; charset=utf-8';
	    var servletPath = 'comments';
	    var method = "POST";
	
		objectForPost = {
			booking_number : booking_click_number,
			unbook_notes : comments
		};
	
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'addCommentsCallback');
			return true;
		return false;
    }
    function addCommentsCallback(resultObject)
    {
    	if(resultObject.status===1)
    		window.location.reload();
    	else
    		errMsg("Unable to add comments");
    }


