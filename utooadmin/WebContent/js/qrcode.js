var table,URL_PATH=getUrlPath();
var LOCATION_NAME,LOCATION_LAT,LOCATION_LAN;
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
										"sDom": '‘<“top”>o<tp>',
										"ajax" : $.fn.dataTable
												.pipeline({
													url : URL_PATH+"getqrcode",
													pages : 5
												}),
										"columns" : [ {
											"data" : "qrcode_username",
											"defaultContent" : ""
										}, {
											"data" : "qrcode_mobile_number",
											"defaultContent" : ""
										},
										{
											"data" : "booked_destination",
											"defaultContent" : ""
										},
										{
											"data" : "from",
											"defaultContent" : ""
										},
										{
											"data" : "to",
											"defaultContent" : ""
										},
										{
											"data" : "message",
											"defaultContent" : ""
										},
										{
											"data" : "advance_amount",
											"defaultContent" : ""
										}, {
							                  'mRender': function (data, type, row) {
							                	var EditLinkText = '<a href="#" id="qrcodeimages" data-a=\''+row.qrcode_user_id+'\'  onclick="return get_qrcodeimages(this);"> <img src=\'images/pdf1.png\'/></a>';
							                    return EditLinkText;
						                           
						                  }
										}
										]
									});
				});
function create_qrcode(){
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'getqrcode';
	
	var message_input=$("#message").val();
	var name_input=$("#name").val();
	
	if(name_input==="" || $("#mobile").val()===""|| 
			$("#qrcount").val()===""|| $("#destination").val()===""|| 
			$("#fromdate").val()===""|| $("#todate").val()===""|| $("#message").val()===""|| 
			$("#advanceamount").val()==="" || LOCATION_NAME==="" || $.trim(name_input)==="" ||  $.trim(message_input)==="")
		{
		errMsg("Field Empty");
		return false;
		} else if ($("#mobile").val().length < 10) {
			errMsg("Please enter a valid mobile number");
			return false;
		} else if ($("#mobile").val().substr(0, 1) != "7"
				&& $("#mobile").val().substr(0, 1) != "8"
				&& $("#mobile").val().substr(0, 1) != "9") {
			errMsg("Please enter a Correct mobile number");
			return false;
		}
			if(typeof LOCATION_NAME==="undefined"){
		errMsg("Location Empty");
		return false;
	}
	if(	$("#qrcount").val()<50)
		{
		errMsg("Count must atleast 50");
		return false;
		}
	if($("#name").val().trim().length <=2){
		errMsg("Name should be minimum 3 characters");
		return false;
	}
	
	var from=$("#fromdate").val()+":00";
	var to=$("#todate").val()+":00";
	
	if(checkDate(from)===false)
	return false;
	if(checkDate(to)===false)
	return false;
	
	if(from_tocompareDate(from,to)===false)
		return false;
	
	var name_input_validation=textbox_validation_dot(name_input);
	var message_input_validation=textbox_validation_dot(message_input);
	if(name_input_validation===0 || message_input_validation===0){
		errMsg("Please enter a valid input");
		return false;
	}
	var method = "POST";
	$("#createQr").prop("disabled",true);
	objectForPost = {	
			count : $("#qrcount").val(),
			oqrcode_user :{
				qrcode_username : name_input,
				qrcode_mobile_number : $("#mobile").val(),
				booked_destination : LOCATION_NAME,
				advance_amount : $("#advanceamount").val(),
				message : message_input,
				From : from,
				To : to,
				latitude : LOCATION_LAT,
				longitude : LOCATION_LAN
			}
	
	};
	jQAjaxCallForAccountJSON(servletPath, method, contentType,
			objectForPost, 'create_qrcodeCallback');
}
function create_qrcodeCallback(reslutObject){
	$("#createQr").prop("disabled",false);
	if(reslutObject.status===1)
		window.location.reload();
	else
		errMsg("Error");
}
             
function get_qrcodeimages(obj)
{
	var qrcode_userid=obj.dataset.a;
	
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'getqrcodeimages';
	var method = "POST";
	objectForPost = {	
			qrcode_id : qrcode_userid
	};
	jQAjaxCallForAccountJSON(servletPath, method, contentType,
			objectForPost, 'get_qrcodeimagesCallback');
}
function get_qrcodeimagesCallback(reslutObject){
	
	var pdf=reslutObject.response[0];
	ArryList1 = pdf.split(",");
	window.open(localStorage.getItem("base_path")+"/"+localStorage.getItem("base_folder")+"/"+localStorage.getItem("qr_image")+"/"+ArryList1[0]);
}
function initialize() {
    var input = document.getElementById('searchTextField');
    var autocomplete = new google.maps.places.Autocomplete(input);
    google.maps.event.addListener(autocomplete, 'place_changed', function () {
        var place = autocomplete.getPlace();
         LOCATION_NAME=place.name;
         LOCATION_LAT=place.geometry.location.lat();
         LOCATION_LAN=place.geometry.location.lng();
        

    });
}
google.maps.event.addDomListener(window, 'load', initialize);

