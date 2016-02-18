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
								"bAutoWidth": true, 
								"ajax" : $.fn.dataTable
										.pipeline({
											url : URL_PATH+"getpassenger",
											pages : 5
										}),
										 
								"columns" : [ {
									"data" : "passenger_name",
									"defaultContent" : ""
								}, {
									"data" : "mobile",
									"defaultContent" : ""
								}, {
									"data" : "email",
									"defaultContent" : ""
								},{
									"data" : "wallet",
									"defaultContent" : ""
								}, {
					                  'mRender': function (data, type, row) {
					                	  var EditLinkAudio='<a href="#" data-a=\''+row.passenger_name+'\' data-b=\''+row.mobile+'\' data-c=\''+row.wallet+'\' onclick="return pop_up(this);"><img src=\'images/edit.png\'/></a>';
				                           return EditLinkAudio;
				                  } }]
							});
				});
function pop_up(obj)
{	
	var user_Type = localStorage.getItem("usertype");
	if (user_Type === "executive") {
		errMsg("You have no Permission To edit");
		return false;
	}
	$("#passenger_name").val(obj.dataset.a);
	$("#passenger_mobile").val(obj.dataset.b);	
	$("#wallet").val(obj.dataset.c);	
    $('#divActionView').U2Popup({
        title: 'Wallet'
        , width: 800
        , height: 300
        , containerShowOnPopup: 'dtpicker-content'
        	
    });	
}
function updatewallet_save(){
		
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'setwallet';
	
	var wallet_money=$("#wallet").val().trim();
	var wallet_mobile=$("#passenger_mobile").val();	
	
	
	if(wallet_money===0 ){
		errMsg("Please enter a valid input");
		return false;
	}
	if(wallet_money==="")
		{
		errMsg("Field Empty");
		return false;
		}
	var method = "POST";
	$("#btnSave").prop("disabled",true);
	objectForPost = {	
			wallet : wallet_money,
			mobile : wallet_mobile
	};
	jQAjaxCallForAccountJSON(servletPath, method, contentType,
			objectForPost, 'updatewallet_save_saveCallback');
}
function updatewallet_save_saveCallback(resultobject){
	if(resultobject.status===1){
		$("#btnSave").prop("disabled",false);
		window.location.reload();
	}
	else{
		$("#btnSave").prop("disabled",false);
		errMsg("Error");
	}
}
