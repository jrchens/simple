/**
 * 
 */
var SimpleUtil = {

    pagination : function(paginationSelector, queryFormSelector) {
	var _form = jQuery(queryFormSelector);
	if (_form.length == 0) {
	    return;
	}
	var _pagination = jQuery(paginationSelector);
	if (_pagination.length == 0) {
	    return;
	}
	var _currentPage = parseInt(_pagination.attr("currentPage"));
	var _totalPage = parseInt(_pagination.attr("totalPage"));

	_pagination.twbsPagination({
	    startPage : _currentPage,
	    totalPages : _totalPage,
	    onPageClick : function(event, page) {
		jQuery("#currentPage", _form).val(page);
		_form.submit();
	    }
	});

    },

    query : function(currentPage, queryFormSelector) {
	var _form = jQuery(queryFormSelector);
	if (_form.length == 0) {
	    return;
	}

	jQuery("#currentPage", _form).val(currentPage);

	_form.submit();
    },

    save : function(saveFormSelector) {
	var _form = jQuery(saveFormSelector);
	if (_form.length == 0) {
	    return;
	}
	_form.submit();
    },

    update : function(updateFormSelector) {
	var _form = jQuery(updateFormSelector);
	if (_form.length == 0) {
	    return;
	}
	_form.submit();
    },

    remove : function(removeUrl, removeFormSelector) {
	var _form = jQuery(removeFormSelector);
	if (_form.length == 0) {
	    return;
	}
	_form.attr({
	    "action" : removeUrl,
	    "method" : "post"
	});
	_form.submit();
    }
};