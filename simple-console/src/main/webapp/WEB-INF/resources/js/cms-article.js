/**
 * 
 */
var CMSArticle = {

};

jQuery(function() {
    SimpleUtil.pagination("#cmsArticleIndexPagination",
	    "#cmsArticleIndexQueryForm");

    
    var cmsArticleRichContent = jQuery("#cmsArticleRichContent");
    if (cmsArticleRichContent.length == 1) {
	tinymce.init({
	    selector : "#cmsArticleRichContent",
	    menubar : false,
	    // language: "zh_CN",
	    plugins : "link image"
	});
    }
    
    var cmsArticleIndexChannelTreeview = jQuery("#cmsArticleIndexChannelTreeview");
    if (cmsArticleIndexChannelTreeview.length == 1) {
	var url = cmsArticleIndexChannelTreeview.attr("dataUrl");
	jQuery.getJSON(url,{all:true},function(data,  textStatus,  jqXHR){
	    cmsArticleIndexChannelTreeview.treeview({
		data : data,
		onNodeSelected : function(event, _data) {
		    var _form = jQuery("#cmsArticleIndexQueryForm");
		    jQuery("#channelName",_form).val(_data.name);
		    jQuery("#channelId",_form).val(_data.nodeId);
		    _form.submit();
		}
	    });
	    var channelId = _.parseInt(cmsArticleIndexChannelTreeview.attr("dataChannelId"));
	    if(channelId >= 0){ // _.isNumber(channelId) && 
	        cmsArticleIndexChannelTreeview.treeview('selectNode',[ channelId, { silent: true } ]);
	    }
	});
    }
    
    var cmsArticleCreateChannelTreeview = jQuery("#cmsArticleCreateChannelTreeview");
    if (cmsArticleCreateChannelTreeview.length == 1) {
	var url = cmsArticleCreateChannelTreeview.attr("dataUrl");
	jQuery.getJSON(url,{},function(data,  textStatus,  jqXHR){
	    cmsArticleCreateChannelTreeview.treeview({
		data : data,
		onNodeSelected : function(event, _data) {
		    var _form = jQuery("#cmsArticleCreateSaveForm");
		    jQuery("#channelNameValue",_form).val(_data.text)
		    jQuery("#channelName",_form).val(_data.name)
		}
	    });
	});
    }
    
});