var table,TARIFF_ID,URL_PATH=getUrlPath();
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
													url : URL_PATH+"gettariff",
													pages : 5
												}),
										"columns" : [
										 {
											"data" : "min_kms",
											"defaultContent" : ""
										},
										{
							                  'mRender': function (data, type, full) {
						                           var EditLinkText = '<img src="images/rupees.png"></img>&nbsp&nbsp<span>'+full.flat_amount+'</span>';
						                           return EditLinkText;
							                  	}},
										{
							                  'mRender': function (data, type, full) {
						                           var EditLinkText = '<img src="images/rupees.png"></img>&nbsp&nbsp<span>'+full.min_charge+'</span>';
						                           return EditLinkText;
							                  	}}
										 ,{
							                  'mRender': function (data, type, full) {
						                           var EditLinkText = '<img src="images/rupees.png"></img>&nbsp&nbsp<span>'+full.waitingchrg_per_min+'</span>';
						                           return EditLinkText;
							                  	}}
										 ,{
											"data" : "carmodel_id.car_model_name",
											"defaultContent" : ""
										},{
							                  'mRender': function (data, type, full) {
							                	 
						                           var EditLinkText = '<a href="#" onclick="return updatetariff(this);" data-a=\''+full.tariff_id+'\' data-b=\''+full.min_kms+'\' data-c=\''+full.min_charge+'\' data-d=\''+full.waitingchrg_per_min+'\' data-e=\''+full.carmodel_id.car_model_name+'\' ><img src=\'images/edit.png\'/></a>';
						                           return EditLinkText;
							                  	}
										} ]
									});
				});
function pop_up()
{	
    $('#divActionView').U2Popup({
        title: 'Tariff'
        , width: 800
        , height: 300
        , containerShowOnPopup: 'dtpicker-content'
        	 ,  onClose: function() {
             	$('#divActionView input[type="text"]').val("");
             	$('#divActionView select').val("-1");
             }
    });	
}
/*function inserttariff(){
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'settariff';
	var fromdate = $("#fromdate").val();
	var todate = $("#todate").val();
	
	var flatamount=$("#flatamount").val();
	var mincharge=$("#mincharge").val();
	var waitingcharge=$("#waitingcharge").val();
	var selVechileMode=$("#selVechileMode").val();
	if(fromdate==="" || todate===""|| flatamount===""|| mincharge===""|| waitingcharge===""){
		errMsg("Field Empty");
		return false;
	}
	var method = "POST";
		objectForPost = {	
				min_charge : mincharge,
				min_kms : 0,
				after_minkms : 0 ,
				waitingchrg_per_min : waitingcharge,
				flat_amount : flatamount ,
				effective_from : fromdate ,
				effective_to : todate ,
				carmodel_id : {
					car_model_id : selVechileMode
				}
				
		};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'insertlocationsCallback');
}
function insertlocationsCallback(reslutObject){
	if(reslutObject.status===1)
		window.location.reload();
	else
		alert("Error");
}*/
function getcarmodals(){
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'getcarmodels';
	
	var method = "GET";
		objectForPost = {				
		};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'getcarmodalsCallback');
}
function getcarmodalsCallback(reslutObject){
	/*var carmodel_option="";
	for(var indx=0;indx<reslutObject.response.length;indx++){
		carmodel_option=carmodel_option+"<option value="+reslutObject.response[indx].car_model_id+">"+reslutObject.response[indx].car_model_name+"</option>";
	}
	$("#selVechileMode").html(carmodel_option);*/
	$('#selVechileMode').empty();
    $("#selVechileMode").append($('<option></option>').val(-1).html("Select Vehicle Model"));
    $.each(reslutObject.response, function (key, value) {
        $("#selVechileMode").append($('<option></option>').val(value.car_model_id).html(value.car_model_name));
    });
}
function updatetariff(obj){
	var user_Type=localStorage.getItem("usertype");
	if(user_Type!="superuser"){
		errMsg("You have No Permission To Edit");
		return false;
	}
	pop_uptariff();
	TARIFF_ID=obj.dataset.a;
	$("#minkms").val(obj.dataset.b);
	$("#mincharge").val(obj.dataset.c);
	$("#ridecharge").val(obj.dataset.d);
	$("#selVechileMode option:selected").text(obj.dataset.e);
}
function updatetariff_save(){
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'updatetariff';
	
	var minkms=$("#minkms").val().trim();
	var mincharge=$("#mincharge").val().trim();
	var ridecharge=$("#ridecharge").val().trim();
	
	var minkms_validation=textbox_validation_dot(minkms);
	var mincharge_validation=textbox_validation_dot(mincharge);
	var ridecharge_validation=textbox_validation_dot(ridecharge);
	
	if(minkms_validation===0 || mincharge_validation===0 || ridecharge_validation===0){
		errMsg("Please enter a valid input");
		return false;
	}
	if(minkms===""|| mincharge===""||ridecharge==="")
		{
		errMsg("Field Empty");
		return false;
		}
	var method = "POST";
	$("#btnSave").prop("disabled",true);
	objectForPost = {	
			tariff_id : TARIFF_ID,
			min_kms : minkms,
			min_charge : mincharge,
			waitingchrg_per_min : ridecharge/*,
			carmodel_id :{
				car_model_id : $("#selVechileMode option:selected").val()
			}*/
	};
	jQAjaxCallForAccountJSON(servletPath, method, contentType,
			objectForPost, 'updatetariff_save_saveCallback');
}
function updatetariff_save_saveCallback(reslutObject){
	$("#btnSave").prop("disabled",false);
	if(reslutObject.status===1)
		window.location.reload();
	else
		errMsg("Error");
}
function pop_uptariff(){	
    $('#divActionView').U2Popup({
        title: 'Tariff'
        , width: 800
        , height: 300
    });	
}
