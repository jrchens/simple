/**
 * 
 */
jQuery.fn.twbsPagination.defaults = {
    totalPages : 1,
    startPage : 1,
    visiblePages : 10,
    initiateStartPageClick : false,
    hideOnlyOnePage : true,
    href : false,
    pageVariable : '{{page}}',
    totalPagesVariable : '{{total_pages}}',
    page : null,
    first : '首页',
    prev : '上一页',
    next : '下一页',
    last : '尾页',
    loop : false,
    onPageClick : null,
    paginationClass : 'pagination pagination-sm',
    nextClass : 'page-item next',
    prevClass : 'page-item prev',
    lastClass : 'page-item last',
    firstClass : 'page-item first',
    pageClass : 'page-item',
    activeClass : 'active',
    disabledClass : 'disabled',
    anchorClass : 'page-link'
};

jQuery("[data-toggle='tooltip']").tooltip();