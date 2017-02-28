/**
 * 
 */
var CMSProduct = {

};

jQuery(function() {
    SimpleUtil.pagination("#cmsProductIndexPagination",
	    "#cmsProductIndexQueryForm");

    
    var cmsProductRichContent = jQuery("#cmsProductRichContent");
    if (cmsProductRichContent.length == 1) {
	tinymce.init({
	    selector : "#cmsProductRichContent",
	    menubar : false,
	    // language: "zh_CN",
	    plugins : "link image"
	});
    }
    
    
    /*var cmsProductIndexChannelTreeview = jQuery("#cmsProductIndexChannelTreeview");
    if (cmsProductIndexChannelTreeview.length == 1) {
	var url = cmsProductIndexChannelTreeview.attr("dataUrl");
	jQuery.getJSON(url,{},function(data,  textStatus,  jqXHR){
	    cmsProductIndexChannelTreeview.treeview({data:data});
	});
    }*/
    
    var files = jQuery("#cmsProductEditFiles");

    if (files.length == 1) {

	var uploadUrl = files.attr("dataUrl");
	var deleteUrl = uploadUrl.replace("upload", "delete");
	var queryUrl = uploadUrl.replace("upload", "query");

	var module = files.attr("dataModule");
	var entity = files.attr("dataEntity");
	var attr = files.attr("dataAttr");
	var param = {
	    module : module,
	    entity : entity,
	    attr : attr,
	    // thumbnail: "160x160"
	};

	    
	jQuery.getJSON(queryUrl, param, function(data, textStatus, jqXHR) {
	    // jQuery.each(data.initialPreviewConfig, function(i, e) {
		// e.url = deleteUrl;
		// e.width = "120px";
		// delete e.filetype;
	    // });
	    files.fileinput({
		language : "zh",
		uploadUrl : uploadUrl,
		uploadAsync : false,
		minFileCount : 0,
		maxFileCount : 5,
		minFileSize : 1,
		maxFileSize : 5120,
		overwriteInitial : false,
		initialPreviewAsData : true,
		initialPreview : data.initialPreview,
		initialPreviewConfig : data.initialPreviewConfig,
		initialPreviewFileType : 'image',
		initialPreviewShowDelete: true,
		removeFromPreviewOnError: true,
		initialPreviewCount: data.initialPreview.length,
		// initialPreviewShowDelete : false,
		allowedFileTypes : [ "image" ],
		dataAllowedFileExtensions : [ "png", "jpg", "jpeg" ],
		previewFileType : true,
		// showCaption: false,
		showCancel : false,
		showClose : false,
		showBrowse : true,
		showUpload : false,
		showRemove : false,
		showPreview : true,
		showUploadedThumbs : false,
		validateInitialCount: true,
		fileActionSettings: {showUpload:false,showZoom:false,showDrag:false},
		// initialCaption : "请选择产品图片上传...",
		uploadExtraData : param
	    }).on("filebatchselected", function(_event, _files) {
		files.fileinput("upload");
	    });
	});
    }

    var tabs = jQuery("#cmsProductCreateTab");
    if (tabs.length == 1) {
	jQuery("#cmsProductCreateTab a").click(function(e) {
	    e.preventDefault();
	    if (!jQuery(this).hasClass("disabled")) {
		jQuery(this).tab("show");
	    }
	});
    }
    
});