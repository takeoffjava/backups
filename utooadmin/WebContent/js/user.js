var table, URL_PATH = getUrlPath();
var access_id = "",DELETE_OBJ;
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
										"ordering" : false,
										"ajax" : $.fn.dataTable.pipeline({
											url : URL_PATH
													+ "getuser_datatable",
											pages : 5
										}),
										"columns" : [
												{
													"data" : "username",
													"defaultContent" : ""
												},
												{
													"data" : "genderStr",
													"defaultContent" : ""
												},
												{
													"data" : "mobile",
													"defaultContent" : ""
												},
												{
													"data" : "objuser_type.usertype_name",
													"defaultContent" : ""
												},
												{
													"data" : "access_id",
													"defaultContent" : ""
												},
												{
													'mRender' : function(data,
															type, full) {
														var EditLinkText = '<a href="#" data-a=\''
																+ full.access_id
																+ '\' data-b=\''
																+ full.username
																+ '\' data-c=\''
																+ full.genderStr
																+ '\' data-d=\''
																+ full.objuser_type.user_typeid
																+ '\' data-e=\''
																+ full.mobile
																+ '\' onclick=\'return updateUser(this);\'><img src=\'images/edit.png\'/></a> | <a href="#" data-a=\''
																+ full.access_id
																+ '\' data-b=\''
																+ full.objuser_type.user_typeid
																+ '\' onclick=\'return deleteUser(this);\'><img src=\'images/delete.png\'/></a>';
														return EditLinkText;
													}
												} ]
									});
				});

function pop_up(buttonType) {
	var user_Type = localStorage.getItem("usertype");
	if (user_Type === "executive") {
		errMsg("You have no permission to add users");
		return false;
	}
	if (buttonType == 1) {
		//$('#txtpassword').removeAttr("disabled");
		 $("#txtpassword").prop("disabled", false); 
		$('#btnSave').css('display', 'block');
		$('#btnUpdate').css('display', 'none');
	} else if (buttonType == 2) {
		$('#btnUpdate').css('display', 'block');
		$('#btnSave').css('display', 'none');

	}
	$('#divActionView').U2Popup(
			{
				title : 'User Details',
				width : 800,
				height : 300,
				onClose : function() {
					$('#divActionView input[type="text"]').val("");
					$('#divActionView input[type="radio"][name="gender"]')
							.removeAttr('checked');
					$('#divActionView select').val("-1");
				}
			});
}
function getUserType() {
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'getUserType';
	var METHOD = {
		DEL : "DELETE",
		GET : "GET",
		POST : "POST",
		PUT : "PUT"
	};

	objectForPost = {

	};
	jQAjaxCallForAccountJSON(servletPath, METHOD.GET, contentType,
			objectForPost, 'loadUserType');
}

function loadUserType(resultObject) {

	$('#user_type').empty();
	$("#user_type").append(
			$('<option></option>').val(-1).html("Select User Type"));

	$.each(resultObject.response, function(key, value) {

		$("#user_type").append(
				$('<option></option>').val(value.user_typeid).html(
						value.usertype_name));
	});

}
function add_user() {

	$("#txtpassword").removeAttr("disabled");
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'add_user';
	var user_type_id = $("#user_type option:selected").val();
	var user_Type = localStorage.getItem("usertype");
	var txtUsername = $("#txtUsername").val();
	var validation=textbox_validation_dot(txtUsername);
	if(validation===0){
		errMsg("Please enter a valid username");
		return false;
	}
	if(user_type_id==="-1"){
		errMsg("Please select the usertype");
		return false;
	}
	
	if (user_Type === "admin" && user_type_id === "1") {
		errMsg("You have no permission to add superuser");
		return false;
	} else if (user_Type === "admin" && user_type_id === "2") {
		errMsg("You have no permission to add admin");
		return false;
	}
	var gender_val;
	var gender_radio = $('input[name=gender]:checked').val();
	if (typeof gender_radio === "undefined") {
		errMsg("select gender");
		return false;
	}
	if (gender_radio === "Female")
		gender_val = true;
	else
		gender_val = false;
	var txtmobile = $("#txtmobile").val();
	if (txtmobile.length < 10) {
		errMsg("Please enter a valid mobile number");
		return false;
	} else if (txtmobile.substr(0, 1) != "7" && txtmobile.substr(0, 1) != "8"
			&& txtmobile.substr(0, 1) != "9") {
		errMsg("Please enter a valid mobile number");
		return false;
	}
	
	var txtpassword = $("#txtpassword").val();
	var method = "POST";
	if (txtUsername === ""){
		errMsg("Username should not be Empty");
		return false;
	}
	else if(txtUsername.trim().length <=2){
		errMsg("Username should be minimum 3 characters");
		return false;
	}
	else if (gender_val === ""){
		errMsg("Gender should not be Empty");
		return false;
	}
	else if (txtmobile === ""){
		errMsg("Mobile should not be Empty");
		return false;
	}
	else if (txtpassword === ""){
		errMsg("Password should not be Empty");
		return false;
	}
	if(txtpassword.trim().length<5){
		errMsg("Password should be minimum 5 characters");
		return false;
	}
	else {
		$("#btnSave").prop("disabled",true);
		
		objectForPost = {
			username : txtUsername,
			gender : gender_val,
			mobile : txtmobile,
			password : txtpassword,
			objuser_type : {
				user_typeid : user_type_id
			}
		};

		jQAjaxCallForAccountJSON(servletPath, method, contentType,
				objectForPost, 'add_userCallBack');
		return true;
	}

	return false;

}
function add_userCallBack(resultObject) {
	$("#btnSave").prop("disabled",false);
	if (resultObject.status == 1)
		window.location.reload();
}

function deleteUser(obj) {
	DELETE_OBJ=obj;
	deletepopup();
}
function deleteUser_YES() {
	var user_Type = localStorage.getItem("usertype");
	if (user_Type === "executive") {
		errMsg("You have no permission to delete");
		return false;
	}
	var OuserTypename = DELETE_OBJ.dataset.d;
	
	if(user_Type === "admin" && OuserTypename==="1"){
		errMsg("You have no permission to delete");
		return false;
	}else if(user_Type === "admin" && OuserTypename==="2"){
		errMsg("You have no permission to delete");
		return false;
	}
	if (user_Type === "admin" && DELETE_OBJ.dataset.b === "1" || user_Type === "admin"
			&& DELETE_OBJ.dataset.b === "2") {
		errMsg("You have no permission to delete");
		return false;
	}
	if (user_Type === "executive") {
		errMsg("You have no permission to delete users");
		return false;
	}
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'deleteUser';
	var accessID = DELETE_OBJ.dataset.a;

	objectForPost = {
		access_id : accessID,
	};

	jQAjaxCallForAccountJSON(servletPath, "POST", contentType, objectForPost,
			'deleteUserCallBack');
	return true;

}
function deleteUserCallBack(resultObject) {
	if (resultObject.status == 1)
		window.location.reload();
}

function updateUser(obj) {
	var user_Type = localStorage.getItem("usertype");
	if (user_Type === "executive") {
		errMsg("You have no permission to edit");
		return false;
	}
	var OuserTypename = obj.dataset.d;
	
	if(user_Type === "admin" && OuserTypename==="1"){
		errMsg("You have no permission to edit");
		return false;
	}else if(user_Type === "admin" && OuserTypename==="2"){
		errMsg("You have no permission to edit");
		return false;
	}
	pop_up(2);
	if(user_Type !== "superuser")
	$("#txtpassword").attr("disabled", "disabled");
	var OuserAccess_id = obj.dataset.a;
	var Ousername = obj.dataset.b;
	var Ogender = obj.dataset.c;
	
	var Ousermobile = obj.dataset.e;
	
	$('input[name="gender"][value="' + Ogender + '"]').prop('checked', true);
	$("#txtUsername").val(Ousername);
	$("#txtmobile").val(Ousermobile);
	$("#user_type").val(OuserTypename);
	$("#txtpassword").val("123456");
	access_id = OuserAccess_id;
}
function updateDetails()
{
	var user_Type = localStorage.getItem("usertype");
	var objectForPost = null;
	var contentType = 'application/json; charset=utf-8';
	var servletPath = 'update_user';
	var user_type_id = $("#user_type option:selected").val();
	
	var gender_val;
	var gender_radio = $('input[name=gender]:checked').val();
	
	if (typeof gender_radio === "undefined") {
		errMsg("select gender");
		return false;
	}
	if(user_type_id==="-1"){
		errMsg("select usertype");
		return false;
	}
	if (gender_radio === "Female")
		gender_val = true;
	else
		gender_val = false;
	
	var txtUsername = $("#txtUsername").val();
	var txtpassword = $("#txtpassword").val();
	var admintype="0";
	if(user_Type === "superuser")
	{
		admintype="1";
	if (txtpassword === ""){
		errMsg("Password should not be Empty");
		return false;
	}
	if(txtpassword.trim().length<5){
		errMsg("Password should be minimum 5 characters");
		return false;
	}
}
	var validation=textbox_validation_dot(txtUsername);
	if(validation===0){
		errMsg("Please enter a valid username");
		return false;
	}
	if (txtUsername === ""){
		errMsg("Username should not be Empty");
		return false;
	}
	else if(txtUsername.trim().length <=2){
		errMsg("Username should be minimum 3 characters");
		return false;
	}
	var txtmobile = $("#txtmobile").val();
	
	if (txtmobile.length < 10) {
		errMsg("Please enter a valid mobile number");
		return false;
	} else if (txtmobile.substr(0, 1) != "7" && txtmobile.substr(0, 1) != "8"
			&& txtmobile.substr(0, 1) != "9") {
		errMsg("Please enter a valid mobile number");
		return false;
	}
	if (user_Type === "admin" && user_type_id === "1") {
		errMsg("You have no permission to add superuser");
		return false;
	} else if (user_Type === "admin" && user_type_id === "2") {
		errMsg("You have no permission to add admin");
		return false;
	}
	var method = "POST";

	objectForPost = {
		username : txtUsername,
		gender : gender_val,
		mobile : txtmobile,
		password : txtpassword,
		adminUsertype : admintype,
		objuser_type : {
			user_typeid : user_type_id
		},
		access_id : access_id

	};

	jQAjaxCallForAccountJSON(servletPath, method, contentType, objectForPost,
			'update_userCallBack');
	return true;
	return false;
}
function update_userCallBack(resultObject) {
	if (resultObject.status == 1)
		window.location.reload();
}
function deletepopup(){
$('#deletepopup').U2Popup(
		{
			title : 'User Details',
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