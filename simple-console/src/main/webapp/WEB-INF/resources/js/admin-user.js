/**
 * 
 */
var AdminUser = {
    save : function(saveFormSelector) {
	var _form = jQuery(saveFormSelector);
	if (_form.length == 0) {
	    return;
	}
        var _username = jQuery('#username',_form).val();
        var _password = jQuery('#password',_form).val();
        jQuery('#password',_form).val(CryptoJS.HmacSHA512(_password,_username).toString());
	_form.submit();
    },
    resetPassword : function(resetPasswordUrl,resetPasswordFormSelector) {
	var _form = jQuery(resetPasswordFormSelector);
	if (_form.length == 0) {
	    return;
	}
	_form.attr({
	    action: resetPasswordUrl,
	    method: "post"
	});
	_form.submit();
    }
};
jQuery(function() {
    SimpleUtil.pagination("#adminUserIndexPagination",
	    "#adminUserIndexQueryForm");
});