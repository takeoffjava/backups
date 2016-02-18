var table,URL_PATH=getUrlPath();
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
									"dataType" : "json",
									"cache" : false,
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
										"ordering": false,
										"ajax" : $.fn.dataTable
												.pipeline({
													url : URL_PATH+"getalldriver",
													pages : 5
												}),

												   "fnInitComplete": function(oSettings, json) {
														$("#availableDrivers").text(json.driversCount.availableDrivers);
														$("#busyDrivers").text(json.driversCount.busyDrivers);
														$("#offlineDrivers").text(json.driversCount.offlineDrivers);
														
													    }/*,
													    "fnRowCallback" : function(nRow, aData, iDisplayIndex){      
													    	var index = iDisplayIndex +1;
													    	$('td:eq(0)',nRow).html(index);
													    	return nRow;
									                },
													    "fnRowCallback" : function(nRow, aData, iDisplayIndex){
											                $("td:first", nRow).html(iDisplayIndex +1);
											               return nRow;
											            }*/,
										"columns" : [  
										      {
											"data" : "driver_name",
											"defaultContent" : ""
										}, {
											"data" : "password",
											"defaultContent" : ""
										}/*,
										{
											"data" : "access_id",
											"defaultContent" : ""
										}*/,
										{
											"data" : "mobile",
											"defaultContent" : ""
										},
										{
											"data" : "car_plate_no",
											"defaultContent" : ""
										},
										{
											"data" : "ocars_feature.register_number",
											"defaultContent" : ""
										},
										{
											"data" : "ocars_feature.ocars_model.car_model_name",
											"defaultContent" : ""
										}
										,
										{
							                  'mRender': function (data, type, full) {
						                           var EditLinkText = '<a href="#" data-a=\''+full.access_id+'\'  onclick=\'return gotoHistory(this);\'><img src=\'images/driverdetails.png\'/></a>';
						                           return EditLinkText;
							                  	}},
							                  	{
							                  		'mRender': function (data, type, full) {
							                  			var EditLinkText = '<a href="#" onclick="return delDriverpopup(this);" data-a=\''+full.access_id+'\'><img src=\'images/delete.png\'/></a>';
								                           return EditLinkText;
							                  	}},
							                  	
										]
									});
				});

function gotoHistory(obj)
{
	var access_id=obj.dataset.a;
	sessionStorage.setItem("access_id",access_id);
	window.location.href="history";
}

/*
 *0- Available 
 *1- Busy
 *2- Offline
 * */
function delDriverpopup(obj){
	DELETE_OBJDRIVER=obj;

	$('#deletepopupdriver').U2Popup(
			{
				title : 'Drivers',
				width : 600,
				height : 175,
				onClose : function() {
					$('#divActionView input[type="text"]').val("");
					$('#divActionView input[type="radio"][name="gender"]')
							.removeAttr('checked');
					$('#divActionView select').val("-1");
				}
			});
	}
function deletedriver_YES()
{
	var user_Type=localStorage.getItem("usertype");
	if(user_Type==="executive"){
		errMsg("You have No Permission To Edit");
		return false;
	}
	var access_id=DELETE_OBJDRIVER.dataset.a;
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'deletedriver';
	var method = "POST";
		objectForPost = {	
				access_id : access_id	
		};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'deleteDriverCallback');
}
function deleteDriverCallback(reslutObject){
	if(reslutObject.status===1)
		window.location.reload();
	else
		errMsg("Error");
}
