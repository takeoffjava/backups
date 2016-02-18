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
													url : URL_PATH+"getaccidentdrivers",
													pages : 5
												}),
												 
										"columns" : [ {
											"data" : "accident_note",
											"defaultContent" : ""
										}, {
											"data" : "created_sON",
											"defaultContent" : ""
										}, {
											"data" : "odrivers.driver_name",
											"defaultContent" : ""
										}, {
											"data" : "odrivers.mobile",
											"defaultContent" : ""
										},  {
											"data" : "odrivers.ocars_feature.register_number",
											"defaultContent" : ""
										}, {
							                  'mRender': function (data, type, row) {
							                	  var EditLinkAudio=""; 
							                	  if(row.voice_note_filename==="")
							                		  EditLinkAudio = '<span>No Voice Message...</span>';
							                		  else
							                			  EditLinkAudio ='<a href="#" data-a=\''+row.voice_note_filename+'\' onclick=\'return pop_upaudio(this);\'>PLAY</a>';
						                           return EditLinkAudio;
						                  } }, {
							                  'mRender': function (data, type, row) {
						                           var EditLinkText = '<a href="#" data-a=\''+row.accident_photo_filename+'\' onclick=\'return pop_upaccident(this);\'> <img src=\'images/accident.png\'/></a>';
						                           return EditLinkText;
						                  } }]
									});
					
				});

function pop_upaccident(obj){	
	var source = obj.dataset.a;	
	if(source==="")
		source="default.png";
	    $('#divActionView').U2Popup({
	        title: 'Accident Image'
            , width: 550
            , height: 400
	    });	
	    $('#accidentimage').attr("src",localStorage.getItem('base_path')+'/'+localStorage.getItem('base_folder')+'/'+localStorage.getItem('accident_image')+'/'+source);
}
function pop_upaudio(obj){	
	var audioSource = obj.dataset.a;	
	//if(source==="")
		//source="default.png";
	    $('#divAudioView').U2Popup({
	        title: 'Accident Note'
            , width: 350
            , height: 150
            ,onClose:function() {
            	$('#audioDiv').html('');
            }
	    });	
	    if(audioSource!=="")
	    $('#audioDiv').html('<audio controls ><source src=\''+localStorage.getItem('base_path')+'/'+localStorage.getItem('base_folder')+'/'+localStorage.getItem('accident_audio')+'/'+audioSource+'\' type=audio/mp3></audio>');
	    else 
	    	$('#audioDiv').html("<p>No Audio</p>");
}

