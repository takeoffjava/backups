var table,table1,table2,CARMODEL_ID,CARFEATURE_ID,LOCATION_ID;
var URL_PATH=getUrlPath();
$(document)
		.ready(
				function() {
					/*********** Loading Function Start ***********/
					
					getcarmodals();
					
					/*********** Loading Function End ***********/
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
													url : URL_PATH+"getlocations_datatables",
													pages : 5
												}),
												"columns" : [  {
													"data" : "location_name",
													"defaultContent" : ""
												},
											    {
													"data" : "east_longitude",
													"defaultContent" : ""
												} , {
													"data" : "west_longitude",
													"defaultContent" : ""
												} , {
													"data" : "south_latitude",
													"defaultContent" : ""
												}, {
													"data" : "north_latitude",
													"defaultContent" : ""
												} , {
													"data" : "channel",
													"defaultContent" : ""
												} ,
												{
									                  'mRender': function (data, type, full) {
								                           var EditLinkText = '<a href="#" onclick="return updatelocation(this);" data-a=\''+full.location_id+'\' data-b=\''+full.location_name+'\' data-c=\''+full.east_longitude+'\' data-d=\''+full.west_longitude+'\' data-e=\''+full.south_latitude+'\' data-f=\''+full.north_latitude+'\'><img src=\'images/edit.png\'/></a> | <a href="#" onclick="return deletelocations(this);" data-a=\''+full.location_id+'\'><img src=\'images/delete.png\'/></a>';
								                           return EditLinkText;
									                  	}
												} 
												]
									});
				});

$(document).ready(table1 = $('#example2').DataTable(
				{
					"processing" : false,
					"serverSide" : true,
					"destroy" : true,
					"bPaginate": false,
					"bFilter": false,
					"ordering": false,
					"ajax" :{ 
						url : URL_PATH+"getcarmodels_datatables",
					},
							"columns" : [  {
								"data" : "car_model_name",
								"defaultContent" : ""
							},
						    {
								"data" : "car_model_description",
								"defaultContent" : ""
							} ,
							{
				                  'mRender': function (data, type, full) {
			                           var EditLinkText = '<a href="#" onclick="return updatecarmodals(this);" data-b=\''+full.car_model_id+'\,'+full.car_model_name+'\,'+full.car_model_description+'\' ><img src=\'images/edit.png\'/></a> | <a href="#" onclick="return deletecarmodals(this);"  data-a=\''+full.car_model_id+'\'><img src=\'images/delete.png\'/></a>';
			                           return EditLinkText;
				                  	}
							}
							]
				}));
$(document).ready(table2 = $('#example3').DataTable(
				{
					"processing" : false,
					"serverSide" : true,
					"destroy" : true,
					"bPaginate": false,
					"bFilter": false,
					"ordering": false,
					"ajax" :{ 
								url : URL_PATH+"getcarfeatures_datatables",
					},	
							"columns" : [  {
								"data" : "register_number",
								"defaultContent" : ""
							},
						    {
								"data" : "color",
								"defaultContent" : ""
							} , {
								"data" : "vehicle_year",
								"defaultContent" : ""
							}, {
								"data" : "seat_count",
								"defaultContent" : ""
							}, {
								"data" : "ocars_model.car_model_name",
								"defaultContent" : ""
							},
							{
				                  'mRender': function (data, type, full) {
			                           var EditLinkText = '<a href="#" onclick="return updatecarfeatures(this);" data-a=\''+full.cars_feature_ID+'\' data-b=\''+full.register_number+'\' data-c=\''+full.color+'\' data-d=\''+full.vehicle_year+'\' data-e=\''+full.seat_count+'\' data-f=\''+full.ocars_model.car_model_id+'\'><img src=\'images/edit.png\'/></a> | <a href="#" onclick="return deletecarfeatures(this);" data-a=\''+full.cars_feature_ID+'\'><img src=\'images/delete.png\'/></a>';
			                           return EditLinkText;
				                  	}
							} 
							]
				}));
function pop_upcarmodals(req_from){	
	if(req_from===1){
		$("#btnUPDATE").hide();
		$("#btnSave").show();
	}else{
		$("#btnUPDATE").show();
		$("#btnSave").hide();
	}
	    $('#divActionView').U2Popup({
	        title: 'Car Modals'
            , width: 800
            , height: 300
            ,  onClose: function() {
            	$('#divActionView input[type="text"]').val("");
            	$('#divActionView textarea').val("");
            	$('#divActionView2 select').val("-1");

            	/*$('#divActionView input[type="radio"][name="gender"]').removeAttr('checked');
            	$('#divActionView select').val("-1");*/
            }
	    });	
}
function pop_upcarmodals2(req_from){
	if(req_from===1){
		$("#btnupdate_CarFeatures").hide();
		$("#btnSave_CarFeatures").show();
	}else{
		$("#btnupdate_CarFeatures").show();
		$("#btnSave_CarFeatures").hide();
	}
    $('#divActionView2').U2Popup({
        title: 'Car Features'
        , width: 800
        , height: 350
        ,  onClose: function() {
        	$('#divActionView2 input[type="text"]').val("");
        	$('#divActionView2 textarea').val("");
        	$('#divActionView2 select').val("-1");
        	/*$('#divActionView input[type="radio"][name="gender"]').removeAttr('checked');
        	*/
        }
    });	
}
function pop_upcarmodals3(req_from){
	if(req_from===1){
		$("#btnupdate_location").hide();
		$("#btnSave_location").show();
	}else{
		$("#btnupdate_location").show();
		$("#btnSave_location").hide();
	}
    $('#divActionView3').U2Popup({
        title: 'Car Features'
        , width: 700
        , height: 320
        ,  onClose: function() {
        	$('#divActionView3 input[type="text"]').val("");
        	$('#divActionView3 textarea').val("");
        	$('#divActionView3 select').val("-1");

        	/*$('#divActionView input[type="radio"][name="gender"]').removeAttr('checked');
        	$('#divActionView select').val("-1");*/
        }
    });	
}
function closeModal_Car() {
	$('#divActionView').U2Popup('close');
}
					/************************ Car Models Functions Start ******************************/

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
	/*for(var indx=0;indx<reslutObject.response.length;indx++){
		carmodel_option=carmodel_option+"<option value="+reslutObject.response[indx].car_model_id+">"+reslutObject.response[indx].car_model_name+"</option>";
	}
	$("#selVechileMode").html(carmodel_option);*/
	 $('#selVechileMode').empty();
     $("#selVechileMode").append($('<option></option>').val(-1).html("Select Vehicle Model"));
     $.each(reslutObject.response, function (key, value) {
         $("#selVechileMode").append($('<option></option>').val(value.car_model_id).html(value.car_model_name));
     });
}
function insertcarmodals(){
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'setcarmodels';
	 
	var carmodal=$("#carmodal").val();
	var aboutcar=$("#aboutcar").val();
	var carmodal_validation=textbox_validation_dot(carmodal);
	var aboutcar_validation=textbox_validation_dot(aboutcar);
	if(carmodal_validation===0 || aboutcar_validation===0){
		errMsg("Please enter a valid input");
		return false;
	}
	
	if(aboutcar.trim()==="" || carmodal.trim()===""){
		errMsg("Field Empty");
		return false;
	}
	var method = "POST";
	$("#btnSave").prop("disabled",true);
		objectForPost = {	
				car_model_name : carmodal,
				car_model_description : aboutcar		
		};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'insertcarmodalsCallback');
}
function insertcarmodalsCallback(reslutObject){
	$("#btnSave").prop("disabled",false);
	if(reslutObject.status===1)
		window.location.reload();
	else
		errMsg("Error");
}
function deletecarmodals(obj){
	DELETE_OBJCARMODAL=obj;
	deletepopupmodal();
}
function deletecarmodal_YES(){
	var user_Type=localStorage.getItem("usertype");
	if(user_Type!="superuser"){
		errMsg("You have No Permission To Edit");
		return false;
	}
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'deletecarmodels';
	var carmodelid=DELETE_OBJCARMODAL.dataset.a;	
	var method = "POST";
		objectForPost = {	
				car_model_id : carmodelid	
		};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'deletecarmodalsCallback');
}
function deletecarmodalsCallback(reslutObject){
	if(reslutObject.status===1)
		window.location.reload();
	else
		errMsg("Error");
}
function deletepopupmodal(){
	$('#deletepopupmodal').U2Popup(
			{
				title : 'Car Modal',
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
function updatecarmodals(obj){
	var user_Type=localStorage.getItem("usertype");
	if(user_Type!="superuser"){
		errMsg("You have No Permission To Edit");
		return false;
	}
	var updatedatas=obj.dataset.b;
	updatedata_split = updatedatas.split(",");
	pop_upcarmodals(2);
	CARMODEL_ID=updatedata_split[0];
	$("#carmodal").val(updatedata_split[1]);
	$("#aboutcar").val(updatedata_split[2]);
}
function updatecarmodels_save(){
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'updatecarmodels';
	if($("#carmodal").val().trim()==="" || $("#aboutcar").val().trim()==="")
		{
		errMsg("Field Empty");
		return false;
		}
	var carmodal=$("#carmodal").val().trim();
	var aboutcar=$("#aboutcar").val().trim();
	var carmodal_validation=textbox_validation_dot(carmodal);
	if(carmodal_validation===0){
		errMsg("Please enter a valid carmodal");
		return false;
	}
	var aboutcar_validation=textbox_validation_dot(aboutcar);
	if(aboutcar_validation===0){
		errMsg("Please enter a valid carmodal description");
		return false;
	}
	if(carcolor_validation===0 || carregisterno_validation===0){
		errMsg("Please enter a valid input");
		return false;
	}
	var method = "POST";
	$("#btnUPDATE").prop("disabled",true);
	objectForPost = {	
			car_model_id : CARMODEL_ID,
			car_model_name : carmodal, 
			car_model_description : aboutcar
	};
	jQAjaxCallForAccountJSON(servletPath, method, contentType,
			objectForPost, 'updatecarmodels_saveCallback');
}
function updatecarmodels_saveCallback(reslutObject){
	$("#btnUPDATE").prop("disabled",false);
	if(reslutObject.status===1)
		window.location.reload();
	else
		errMsg("Error");
}

/************************ Car Models Functions End ********************************/

/************************ Car Features Functions Start ******************************/
function insertcarfeatures(){
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'setcarfeatures';
	 
	var carregisterno=$("#carregisterno").val().trim();
	var carcolor=$("#carcolor").val().trim();
	var vehileyear=$("#vehileyear").val().trim();
	var seatcount=$("#seatcount").val().trim();
	
	var carregisterno_validation=textbox_validation_dot(carregisterno);
	var carcolor_validation=textbox_validation_dot(carcolor);
	if(carcolor_validation===0 || carregisterno_validation===0){
		errMsg("Please enter a valid input");
		return false;
	}
	if(carregisterno==="" || carcolor===""||vehileyear==="" || seatcount===""){
		errMsg("Field Empty");
		return false;
	}
	if($("#selVechileMode option:selected").val()==="-1"){
		errMsg("Select car model");
		return false;
	}
	var method = "POST";
	$("#btnSave_CarFeatures").prop("disabled",true);
		objectForPost = {	
				color : carcolor,
				register_number : carregisterno,
				vehicle_year : vehileyear,
				seat_count : seatcount,
				ocars_model :{
					car_model_id : $("#selVechileMode option:selected").val()
				}
		};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'insertcarfeaturesCallback');
}
function insertcarfeaturesCallback(reslutObject){
	$("#btnSave_CarFeatures").prop("disabled",false);
	if(reslutObject.status===1)
		window.location.reload();
	else
		errMsg("Error");
}
function deletecarfeatures(obj){
	DELETE_OBJCARFEATURE=obj;
	deletepopupfeature();
}
function deletecarfeature_YES(){
	var user_Type=localStorage.getItem("usertype");
	if(user_Type!="superuser"){
		errMsg("You have No Permission To Edit");
		return false;
	}
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'deletecarfeatures';
	var carsfeatureID=DELETE_OBJCARFEATURE.dataset.a;	
	var method = "POST";
		objectForPost = {	
				cars_feature_ID : carsfeatureID	
		};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'deletecarfeaturesCallback');
}
function deletecarfeaturesCallback(reslutObject){
	if(reslutObject.status===1)
		window.location.reload();
	else
		errMsg("Error");
}
function deletepopupfeature(){
	$('#deletepopupfeature').U2Popup(
			{
				title : 'Car Features',
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
function updatecarfeatures(obj){
	var user_Type=localStorage.getItem("usertype");
	if(user_Type!="superuser"){
		errMsg("You have No Permission To Edit");
		return false;
	}
	CARFEATURE_ID=obj.dataset.a;
	var register_no=obj.dataset.b;
	var color=obj.dataset.c;
	var vehicle_year=obj.dataset.d;
	var seat_count=obj.dataset.e;
	var car_model=obj.dataset.f;
	pop_upcarmodals2(2);
/*	$("#selVechileMode option:selected").text(car_model);
*/				
	$("#selVechileMode").val(car_model);
	$("#carregisterno").val(register_no);
	$("#carcolor").val(color);
	$("#vehileyear").val(vehicle_year);
	$("#seatcount").val(seat_count);
}
function updatecarfeatures_save(){
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'updatecarfeatures';
	
	var method = "POST";
	var carregisterno=$("#carregisterno").val().trim();
	var carcolor=$("#carcolor").val().trim();
	var vehileyear=$("#vehileyear").val().trim();
	var seatcount=$("#seatcount").val().trim();
	
	var carregisterno_validation=textbox_validation_dot(carregisterno);
	var carcolor_validation=textbox_validation_dot(carcolor);
	
	if(carregisterno_validation===0 || carcolor_validation===0){
		errMsg("Please enter a valid input");
		return false;
	}
	if(carregisterno==="" || carcolor===""|| vehileyear===""
		|| seatcount==="")
		{
		errMsg("Field Empty");
		return false;
		}
	if($("#selVechileMode option:selected").val()==="-1"){
		errMsg("Select vechile model");
		return false;
	}
	$("#btnupdate_CarFeatures").prop("disabled",true);
	objectForPost = {	
			cars_feature_ID : CARFEATURE_ID,
			register_number : carregisterno,
			vehicle_year : vehileyear,
			seat_count : seatcount,
			color :  carcolor,
			ocars_model :{
				car_model_id : $("#selVechileMode option:selected").val().trim()
			}
	};
	jQAjaxCallForAccountJSON(servletPath, method, contentType,
			objectForPost, 'updatecarfeatures_saveCallback');
}
function updatecarfeatures_saveCallback(reslutObject){
	$("#btnupdate_CarFeatures").prop("disabled",false);
	if(reslutObject.status===1)
		window.location.reload();
	else
		errMsg("Error");
}

/************************ Car Features Functions End ********************************/

/************************ Location Functions Start ********************************/

function insertlocations(){
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'setlocation';
	 
	var locationname=$("#locationname").val().trim();
	var east=$("#east").val().trim();
	var west=$("#west").val().trim();
	var south=$("#south").val().trim();
	var north=$("#north").val().trim();
	if(locationname==="" || east===""|| south===""|| north===""|| west===""){
		errMsg("Field Empty");
		return false;
	}
	var locationname_validation=textbox_validation_dot(locationname);
	var east_validation=textbox_validation_dot(east);
	var west_validation=textbox_validation_dot(west);
	var south_validation=textbox_validation_dot(south);
	var north_validation=textbox_validation_dot(north);
	if(locationname_validation===0 || east_validation===0 || 
			west_validation===0 || south_validation===0  || north_validation===0){
		errMsg("Please enter a valid input");
		return false;
	}
	var method = "POST";
	$("#btnSave_location").prop("disabled",true);
		objectForPost = {	
				north_latitude : north,
				south_latitude : south,
				east_longitude : east ,
				west_longitude : west,
				location_name : locationname
				
		};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'insertlocationsCallback');
}
function insertlocationsCallback(reslutObject){
	$("#btnSave_location").prop("disabled",false);
	if(reslutObject.status===1)
		window.location.reload();
	else
		errMsg("Error");
}
function deletelocations(obj){
	DELETE_OBJLOCATION=obj;
	deletepopup();
}
function deletelocation_YES(){
	var user_Type=localStorage.getItem("usertype");
	if(user_Type==="executive"){
		errMsg("You have No Permission To Edit");
		return false;
	}
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'deletelocation';
	var locationID=DELETE_OBJLOCATION.dataset.a;	
	var method = "POST";
		objectForPost = {	
				location_id : locationID	
		};
		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'deletelocationsCallback');
}
function deletelocationsCallback(reslutObject){
	if(reslutObject.status===1)
		window.location.reload();
	else
		errMsg("Error");
}

function deletepopup(){
	$('#deletepopuplocation').U2Popup(
			{
				title : 'Location Details',
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

function updatelocation(obj){
	var user_Type=localStorage.getItem("usertype");
	if(user_Type==="executive"){
		errMsg("You have No Permission To Edit");
		return false;
	}
	pop_upcarmodals3(2);
	
	LOCATION_ID=obj.dataset.a;
	$("#locationname").val(obj.dataset.b);
	$("#east").val(obj.dataset.c);
	$("#west").val(obj.dataset.d);
	$("#south").val(obj.dataset.e);
	$("#north").val(obj.dataset.f);
}
function updatelocation_save(){
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'updatelocation';
	
	var locationname=$("#locationname").val();
	var east=$("#east").val().trim();
	var west=$("#west").val().trim();
	var south=$("#south").val().trim();
	var north=$("#north").val().trim();
	
	var locationname_validation=textbox_validation_dot(locationname);
	var east_validation=textbox_validation_dot(east);
	var west_validation=textbox_validation_dot(west);
	var south_validation=textbox_validation_dot(south);
	var north_validation=textbox_validation_dot(north);
	
	if(locationname_validation===0 || east_validation===0 || 
			west_validation===0 || south_validation===0  || north_validation===0){
		errMsg("Please enter a valid input");
		return false;
	}
	if(locationname==="" || east===""|| south===""|| north===""|| west===""){
		errMsg("Field Empty");
		return false;
	}
	
	var method = "POST";
	$("#btnupdate_location").prop("disabled",true);
	objectForPost = {	
			location_id : LOCATION_ID,
			north_latitude : north,
			south_latitude : south,
			east_longitude : east,
			west_longitude : west,
			location_name : locationname
					
		
	};
	jQAjaxCallForAccountJSON(servletPath, method, contentType,
			objectForPost, 'updatecarmodels_saveCallback');
}
function updatecarmodels_saveCallback(reslutObject){
	$("#btnupdate_location").prop("disabled",false);
	if(reslutObject.status===1)
		window.location.reload();
	else
		errMsg("Error");
}
/************************ Location Functions End ********************************/
