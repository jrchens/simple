/**
 * 
 */
var CMSChannel = {
    queryChildren: function(parentValue,queryFormSelector){
	var _form = jQuery(queryFormSelector);
	if (_form.length == 0) {
	    return;
	}
	// jQuery("#channelName", _form).val(channelValue);
	jQuery("#parentName", _form).val(parentValue);
	_form.submit();
    }
};
jQuery(function() {
    SimpleUtil.pagination("#cmsChannelIndexPagination",
	    "#cmsChannelIndexQueryForm");
});