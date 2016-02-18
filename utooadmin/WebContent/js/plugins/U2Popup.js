; (function ($) {
    'use strict';

    $.fn.U2Popup = function (options, callback) {
    	var $popup = this
        , d = $(document)
        , w = window
	    , $w = $(w)
        , $popupId
        , $divElem
        , $dialog = $('.modalDialog')
        , autoCloseDuration;
    	
        /*if ($.isFunction(options)) {
            callback = options;
            options = null;
        }*/

        var o = $.extend({}, $.fn.U2Popup.defaults, options);
        
        $popup.close = function () {
            close();
        };

        return this.each(function () {
        	if(options === 'close') {
    			close();
    		} else {
        		if ($(this).data('U2Popup')) return; //POPUP already exists?
                init();
        	}
        });

        function init() {
            functionProtoTypeCall(o.onOpen);
            open();
        }

        function open() {
            // MODAL OVERLAY
            if (o.modal) {
                $('<div id="openModal" class="modalDialog"><div id="popUpContainer"><span class="u2close">X</span></div>').css({ backgroundColor: o.modalColor, zIndex: o.zIndex }).appendTo(o.appendElem).fadeTo(o.speed, o.opacity);
                onCompleteCallback(true);
                setPopUpCenter();
                $('.u2close').css({ position: 'fixed' });
                addHeader();
            }

            // POPUP
            $popup.data('U2Popup', o).each(function () {
			    $(this).appendTo(o.appendElem);
			});
        };

        function close() {
            if (o.modal) {
                $('.modalDialog, #' + $popupId)
	                .fadeTo(o.speed, 0, function () {
	                    $(this).remove();
	                });
            }
            functionProtoTypeCall(o.onClose);

            // Clean up
            unbindEvents();
            clearTimeout(autoCloseDuration);
        }
        
        function bindEvents() {
            $popup.delegate('.u2close,.popupcancel', 'click', close);

            d.mouseup(function (e) {
                var container = $('.clsContainerDiv');
                var bContains = false;

                if(o.containerShowOnPopup != null) {
                	if (!container.is(e.target) && container.has(e.target).length === 0) {
                        bContains = true;
                    }
                	
                	var elemArr = o.containerShowOnPopup.split(',');
                	var container2 = "";
                    for(var eIdx = 0; eIdx < elemArr.length; eIdx++) {
                    	container2 = $('.' + elemArr[eIdx]);
                    	if (!container2.is(e.target) && container2.has(e.target).length === 0) {
                            bContains = true;
                        }
        			}
                    
                    if(!bContains) {close();}
                } else {
                	if(o.containerShowOnPopup == null) {
                    	if (!container.is(e.target) && container.has(e.target).length === 0) {
                            close();
                        }
                    }
                }
            });

            if (o.modalClose) {
                $('.u2close').css('cursor', 'pointer');
                d.on('click', '.u2close,.popupcancel', function (e) {
                    close();
                });
            }

            if (o.escClose) {
                d.on('keydown', function (e) {
                    if (e.which == 27) {  //escape
                        close();
                    }
                });
            }
        };

        function unbindEvents() {
            $('.u2close,.popupcancel').off('click');
            d.off('keydown');
            $popup.removeClass('clsContainerDiv');
            $popup.undelegate('.u2close,.popupcancel', 'click', close).data('U2Popup', null);
        };

        function onCompleteCallback(open) {
            if (open) {
                bindEvents();
                functionProtoTypeCall(callback);
                if (o.autoClose) {
                    autoCloseDuration = setTimeout(close, o.autoCloseDuration);
                }
            } else {
                $popup.hide();
                functionProtoTypeCall(o.onClose);
            }
        };

        function addHeader() {
            $('.U2PopupHeader').remove();
            $popup.prepend('<div class="U2PopupHeader">' + o.title + '</div>');
            $('.U2PopupHeader').css({ backgroundColor: o.titleColor });
        };

        function setPopUpCenter() {
            var w = windowWidth();
            var h = windowHeight();
            var rW = w - o.width;
            var rH = h - o.height;
            var p = rW / 2;
            var pH = rH / 2;
            $popup.addClass('clsContainerDiv').css({ zIndex: o.zIndex + 1, left: p, right: p, top: pH, bottom: pH, width: o.width + 'px', height: o.height + 'px' });
            $('.formSection').css({ height: (o.height - 10) + 'px' });
        }
        
        function IsContainerContain(container) {
        	if (!container.is(e.target) && container.has(e.target).length === 0) {
                close();
            }
        }

        function windowHeight() {
            return $w.height();
        }

        function windowWidth() {
            return $w.width();
        }

        function functionProtoTypeCall(func, arg) {
            $.isFunction(func) && func.call($popup, arg);
        };
    };

    $.fn.U2Popup.defaults = {
        appendElem: 'body'
		, autoClose: false
		, escClose: true
        , modal: true
        , modalClose: true
        , modalColor: '#000'
        , onClose: false
        , onOpen: false
        , opacity: 0.9
        , position: 'center' // center
        , zIndex: 99999
        , title: 'U2'
        , titleColor: '#FBAB56'
        , height: 400
        , width: 600
        , containerShowOnPopup: null
    };
})(jQuery);
