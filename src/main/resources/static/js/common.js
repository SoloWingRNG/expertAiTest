window.commonLib = {

    equals: function (obj1, obj2, flEqual) {
        if (flEqual == null) {
            flEqual = true;
        }

        try {
            for (var o in obj1) {
                if (isObject(obj1[o]) && isObject(obj2[o])) {
                    flEqual = this(obj1, obj2, flEqual);
                } else if (obj1[o] != obj2[o]) {
                    flEqual = false;
                }

                if (flEqual == false) {
                    return flEqual;
                }
            }
        } catch (e) {
            return false;
        }

        return flEqual;
    },

    jsonParse: function (string) {
        if (!string) {
            return null;
        } else {
            return JSON.parse(string);
        }
    },
    
    showNotification: function (mex, type, delay) {

    	if(!type){
    		type = "info";
    	}
    	type = type.toLowerCase();
    	
    	let classGlyphicon = null;

    	if (type == 'info') {
    		classGlyphicon = 'glyphicon-info-sign';
    	} else if (type == 'danger') {
    		classGlyphicon = 'glyphicon-remove';
    	} else if (type == 'warning') {
    		classGlyphicon = 'glyphicon-warning-sign';
    	} else if (type == 'success') {
    		classGlyphicon = 'glyphicon-ok';
    	}
    	
    	if(!delay){
    		delay = 2000;
    	}
    	
    	$.notify({
    		icon: "glyphicon " + classGlyphicon,
    		//title: "",
    		message: mex
    		//,url: "",
    		//target: "_blank"
    	},{
    		type: type,
    		allow_dismiss: true,
    		newest_on_top: true,
    		placement: {
    			from: "top",
    			align: "center"
    		},
    		offset: {
    			x: 0,
    			y: 150
    		},
    		delay: delay,
    		mouse_over: "pause"
    	});
    }

};

String.prototype.replaceAll = function (stringToFind, stringToReplace) {
    if (stringToFind === stringToReplace) return this;
    var temp = this;
    var index = temp.indexOf(stringToFind);
    while (index != -1) {
        temp = temp.replace(stringToFind, stringToReplace);
        index = temp.indexOf(stringToFind);
    }
    return temp;
};


jQuery.fn.initServerSideDataTable = function (idTable, requestObject, urlToCall, columns, customOptions, functionComplete, functionBeforeSend) {

    var options = {
        processing: true,
        serverSide : true,
        ajax : {
            url : urlToCall,
            type : "POST",
            data : function(d) {

                /*
                 * set requestObject
                 */
                var myRO = null;
                if(requestObject && requestObject != null) {
                    myRO = $.isFunction(requestObject) ? requestObject() : requestObject;
                    myRO = JSON.stringify(myRO);
                }
                d.requestObject = myRO;

                /*
                 * set indiceOrder
                 */
                var indiceOrder = d.order[0].column;

                /*
                 * set orderby
                 */
                if(options.columns && options.columns != null &&
                    options.columns[indiceOrder] && options.columns[indiceOrder] != null &&
                    options.columns[indiceOrder].orderby && options.columns[indiceOrder].orderby != null) {

                    d.orderby = columns[indiceOrder].orderby;

                } else if (options.columnDefs && options.columnDefs != null) {

                    $.each(options.columnDefs, function(index, element) {
                        if (element.targets == indiceOrder) {

                            if(element.orderby && element.orderby != null) {
                                d.orderby = element.orderby;
                            }
                            return false; //break
                        }
                    });
                }

            },

            beforeSend : function(jqXHR) {

                ajaxSetupObj.beforeSend(jqXHR);

                if (functionBeforeSend && functionBeforeSend != null) {
                    functionBeforeSend();
                }
            },
            complete : function(jqXHR) {

                ajaxSetupObj.complete(jqXHR);

                if (functionComplete && functionComplete != null) {
                    functionComplete(jqXHR);
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {

                ajaxSetupObj.error(jqXHR, textStatus, errorThrown);

            }
        },
        columns : columns
    };

    if(customOptions) {
        $.extend(true, options, customOptions);
    }

    var dataTable = $('#' + idTable).DataTable(options);

    return dataTable;

}


jQuery.fn.initSimpleDataTable = function(idTable, requestObject, urlToCall, columns, customOptions, functionComplete, functionBeforeSend) {

    var options = {
        processing: true,
        serverSide : false,
        ajax : {
            url : urlToCall,
            type : "POST",
            data : function(d) {

                /*
                 * set requestObject
                 */
                var myRO = null;
                if(requestObject && requestObject != null) {
                    myRO = $.isFunction(requestObject) ? requestObject() : requestObject;
                    myRO = JSON.stringify(myRO);
                }
                d.requestObject = myRO;

                /*
                 * set indiceOrder
                 */
                var indiceOrder = d.order[0].column;

                /*
                 * set orderby
                 */
                if(options.columns && options.columns != null &&
                    options.columns[indiceOrder] && options.columns[indiceOrder] != null &&
                    options.columns[indiceOrder].orderby && options.columns[indiceOrder].orderby != null) {

                    d.orderby = columns[indiceOrder].orderby;

                } else if (options.columnDefs && options.columnDefs != null) {

                    $.each(options.columnDefs, function(index, element) {
                        if (element.targets == indiceOrder) {

                            if(element.orderby && element.orderby != null) {
                                d.orderby = element.orderby;
                            }
                            return false; //break
                        }
                    });
                }

            },

            beforeSend : function(jqXHR) {

                ajaxSetupObj.beforeSend(jqXHR);

                if (functionBeforeSend && functionBeforeSend != null) {
                    functionBeforeSend();
                }
            },
            complete : function(jqXHR) {

                ajaxSetupObj.complete(jqXHR);

                if (functionComplete && functionComplete != null) {
                    functionComplete(jqXHR);
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {

                ajaxSetupObj.error(jqXHR, textStatus, errorThrown);

            }
        },
        columns : columns
    };

    if(customOptions) {
        $.extend(true, options, customOptions );
    }

    var dataTable = $('#' + idTable).DataTable(options);

    return dataTable;

}

jQuery.fn.callAjax = function (urlToCall, objectRequest, functionSuccess, functionBeforeSend, functionComplete, isDataType, isAsync) {

    var options = {
        url : urlToCall,
        type : "POST",
        contentType : "application/json",
        //dataType : "json",
        data : objectRequest != null ? JSON.stringify(objectRequest) : null,
        success : function(result, textStatus, jqXHR) {


            var isOK = handleMessages(result);

            if (isOK && functionSuccess && functionSuccess != null) {
                functionSuccess(result);
            }

        },
        beforeSend : function(jqXHR) {

            ajaxSetupObj.beforeSend(jqXHR);

            if (functionBeforeSend && functionBeforeSend != null) {
                functionBeforeSend(jqXHR);
            }
        },
        complete : function(jqXHR) {

            ajaxSetupObj.complete(jqXHR);

            if (functionComplete && functionComplete != null) {
                functionComplete(jqXHR);
            }
        }
    };

    if(isDataType === undefined || isDataType == null ) {
        options.dataType = "json";
    }else {
        options.dataType = isDataType;
    }

    if(isAsync === undefined || isAsync == null ) {
        options.async = true;
    }else {
        options.async = isAsync;
    }

    // call ajax
    $.ajax(options);
}
