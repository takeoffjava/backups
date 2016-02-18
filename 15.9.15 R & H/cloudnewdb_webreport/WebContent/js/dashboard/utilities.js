function jQAjaxCallForJSON(servletPath, method, contentType, objectForPost,
		callBack) {
	var dataInJSON = '';

	if (objectForPost == null) {
		objectForPost = {};
	}

	if ('DELETE' != method) {
		dataInJSON = JSON.stringify(objectForPost);
		$.ajax({
			async : true,
			url : servletPath,
			type : method,
			crossDomain : true,
			beforeSend : function(jqXHR) {
				jqXHR.setRequestHeader("Content-type", contentType);

			},
			data : dataInJSON,
			dataType : 'json',
			processData : false,
			statusCode : {
				401 : function() {

					window.location = path + '/index.jsp';
				}
			},
			success : function(resultObject) {
				var callBackfunction = window[callBack];
				callBackfunction(resultObject);
			},
			error : function(xhr, ajaxOptions, thrownError) {
				/*console.info('server error');*/
			}
		});
	}
}
function jQAjaxCallForJSON_Token(servletPath, method, contentType,
		objectForPost, callBack) {
	var dataInJSON = '';

	var accessToken = getAccessToken(getNewRefreshToken());
	
	var accessTokenVal = "bearer "+accessToken;

	// URL = path.concat(servletPath);

	if (objectForPost == null) {
		objectForPost = {};
	}

	// console.info(method);
	if ('DELETE' != method) {
		dataInJSON = JSON.stringify(objectForPost);
		$.ajax({
			async : true,
			url : servletPath,
			type : method,
			crossDomain : true,
			beforeSend : function(jqXHR) {
				jqXHR.setRequestHeader("Authorization", accessTokenVal);
				jqXHR.setRequestHeader("Content-type", contentType);
				
			},
			data : dataInJSON,
			dataType : 'json',
			processData : false,
			statusCode : {
				401 : function() {
					window.location = path + '/index.jsp';
				}
			},
			success : function(resultObject) {
				var callBackfunction = window[callBack];
				callBackfunction(resultObject);
			},
			error : function(xhr, ajaxOptions, thrownError) {
				/* console.info('server error'); */
			}
		});
	}
}
function jQAjaxCallForAccountJSON(servletPath, method, contentType,
		objectForPost, callBack) {
	var dataInJSON = '';

	// URL = path.concat(servletPath);

	if (objectForPost == null) {
		objectForPost = {};
	}

	// console.info(method);
	if ('DELETE' != method) {
		dataInJSON = JSON.stringify(objectForPost);

		$.ajax({
			async : false,
			url : servletPath,
			type : method,
			crossDomain : true,
			beforeSend : function(jqXHR) {
				jqXHR.setRequestHeader("Content-type", contentType);

			},
			data : dataInJSON,
			dataType : 'json',
			processData : false,
			statusCode : {
				401 : function() {
					window.location = path + '/index.jsp';
				}
			},
			success : function(resultObject) {
				var callBackfunction = window[callBack];
				callBackfunction(resultObject);
			},
			error : function(xhr, ajaxOptions, thrownError) {
				/* console.info('server error'); */
			}
		});
	}
}