<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
    <title>接力 互帮互助管理</title>
    <meta name="description" content="接力"/>
    <!-- basic styles -->

    <link href="/assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="/assets/css/font-awesome.min.css"/>

    <!--[if IE 7]>
    <link rel="stylesheet" href="/assets/css/font-awesome-ie7.min.css"/>
    <![endif]-->

    <!-- page specific plugin styles -->

    <link rel="stylesheet" href="/assets/css/jquery-ui-1.10.3.full.min.css"/>
    <link rel="stylesheet" href="/assets/css/datepicker.css" />
    <link rel="stylesheet" href="/assets/css/ui.jqgrid.css" />

    <!-- fonts -->

    <link rel="stylesheet" href="/assets/css/font-google.css"/>

    <!-- ace styles -->

    <link rel="stylesheet" href="/assets/css/ace.min.css"/>
    <link rel="stylesheet" href="/assets/css/ace-rtl.min.css"/>
    <link rel="stylesheet" href="/assets/css/ace-skins.min.css"/>

    <!--[if lte IE 8]>
    <link rel="stylesheet" href="/assets/css/ace-ie.min.css"/>
    <![endif]-->

    <!-- inline styles related to this page -->

    <!-- ace settings handler -->

    <script src="/assets/js/ace-extra.min.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

    <!--[if lt IE 9]>
    <script src="/assets/js/html5shiv.js"></script>
    <script src="/assets/js/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<div class="navbar navbar-default" id="navbar">
    <script type="text/javascript">
        try {
            ace.settings.check('navbar', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="navbar-container" id="navbar-container">
        <div class="navbar-header pull-left">
            <a href="index.html" class="navbar-brand">
                <small>
                    <i class="icon-leaf"></i>
                    接力 后台管理系统
                </small>
            </a><!-- /.brand -->
        </div>
        <!-- /.navbar-header -->

        <div class="navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <li class="light-blue">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <img class="nav-user-photo" src="/assets/avatars/user.jpg" alt="Jason's Photo"/>
								<span class="user-info">
									<small>欢迎光临,</small>
                                ${username}
								</span>

                        <i class="icon-caret-down"></i>
                    </a>

                    <ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <!--<li>
                            <a href="#">
                                <i class="icon-cog"></i>
                                设置
                            </a>
                        </li>

                        <li>
                            <a href="#">
                                <i class="icon-user"></i>
                                个人资料
                            </a>
                        </li>

                        <li class="divider"></li>-->

                        <li>
                            <a href="#" onclick="document.cookie='u=;path=/';window.location.href='/rest/baccount/login'">
                                <i class="icon-off"></i>
                                退出
                            </a>
                        </li>
                    </ul>
                </li>

            </ul>
            <!-- /.ace-nav -->

        </div>
        <!-- /.navbar-header -->
    </div>
    <!-- /.container -->
</div>

<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>

        <div class="sidebar" id="sidebar">
            <script type="text/javascript">
                try {
                    ace.settings.check('sidebar', 'fixed')
                } catch (e) {
                }
            </script>

            <div class="sidebar-shortcuts">
                <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                    <button class="btn btn-success">
                        <i class="icon-signal" style="display: none"></i>
                    </button>

                    <button class="btn btn-info">
                        <i class="icon-pencil" style="display: none"></i>
                    </button>

                    <button class="btn btn-warning">
                        <i class="icon-group" style="display: none"></i>
                    </button>

                    <button class="btn btn-danger">
                        <i class="icon-cogs" style="display: none"></i>
                    </button>
                </div>

                <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                    <span class="btn btn-success"></span>

                    <span class="btn btn-info"></span>

                    <span class="btn btn-warning"></span>

                    <span class="btn btn-danger"></span>
                </div>
            </div>
            <!-- #sidebar-shortcuts -->

            <ul class="nav nav-list"  id="sidebar-shortcuts-navlist">

            </ul>

            <div class="sidebar-collapse" id="sidebar-collapse">
                <i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
            </div>
        </div>

        <div class="main-content">
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try {
                        ace.settings.check('breadcrumbs', 'fixed')
                    } catch (e) {
                    }
                </script>

                <ul class="breadcrumb">
                    <li>
                        <i class="icon-home home-icon"></i>
                        <a href="index.html">首页</a>
                    </li>

                    <li>
                        <a href="#"> 互帮互助管理 </a>
                    </li>

                    <li class="active"> 互帮互助列表 </li>
                </ul>
                <!-- .breadcrumb -->

                <div class="nav-search" id="nav-search">
                    <form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="搜索 ..." class="nav-search-input"
                                           id="nav-search-input" autocomplete="off"/>
									<i class="icon-search nav-search-icon"></i>
								</span>
                    </form>
                </div>
                <!-- #nav-search -->
            </div>

            <div class="page-content">
                <div class="page-header">
                    <h1>
                        互帮互助列表
                    </h1>
                </div>
                <!-- /.page-header -->

                <div class="row">
                <#if isSuper>
                    <label class="col-sm-3 control-label no-padding-right" for="form-field-select-type"> 请选择协会 </label>
                    <div class="col-sm-9">
                        <select class="col-xs-10 col-sm-7" id="association-select" style="padding: 5px 4px;font-size: 14px;">
                            <#list associationList as associations>
                                <option value="${associations.name}" <#if associations_index = 0>selected="selected" </#if>>${associations.name}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="space-4"></div>
                </#if>
                    <label class="col-sm-3 control-label no-padding-right" for="form-field-select-type"> 请选择帮助类型 </label>
                    <div class="col-sm-9">
                        <select class="col-xs-10 col-sm-7" id="help-select" style="padding: 5px 4px;font-size: 14px;">
                            <option value="需求" selected="selected">供给</option>
                            <option value="供给">需求</option>
                        </select>
                    </div>
                    <div class="space-4"></div>
                    <div class="col-xs-12">

                        <!-- PAGE CONTENT BEGINS -->

                        <div><table id="grid-table"></table></div>

                        <div id="grid-pager"></div>
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.page-content -->
        </div>
        <!-- /.main-content -->

        <div class="ace-settings-container" id="ace-settings-container">
            <div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
                <i class="icon-cog bigger-150"></i>
            </div>

            <div class="ace-settings-box" id="ace-settings-box">
                <div>
                    <div class="pull-left">
                        <select id="skin-colorpicker" class="hide">
                            <option data-skin="default" value="#438EB9">#438EB9</option>
                            <option data-skin="skin-1" value="#222A2D">#222A2D</option>
                            <option data-skin="skin-2" value="#C6487E">#C6487E</option>
                            <option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
                        </select>
                    </div>
                    <span>&nbsp; Choose Skin</span>
                </div>
            </div>
        </div>
        <!-- /#ace-settings-container -->
    </div>
    <!-- /.main-container-inner -->

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="icon-double-angle-up icon-only bigger-110"></i>
    </a>
</div>
<!-- /.main-container -->

<!-- basic scripts -->

<!--[if !IE]> -->

<script src="/assets/js/jquery-2.0.3.min.js"></script>

<!-- <![endif]-->

<!--[if IE]>
<script src="/assets/js/jquery-1.10.2.min.js"></script>
<![endif]-->

<!--[if !IE]> -->

<script type="text/javascript">
    window.jQuery || document.write("<script src='/assets/js/jquery-2.0.3.min.js'>" + "<" + "/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='/assets/js/jquery-1.10.2.min.js'>" + "<" + "/script>");
</script>
<![endif]-->

<script type="text/javascript">
    if ("ontouchend" in document) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
</script>
<script src="/assets/js/bootstrap.min.js"></script>
<script src="/assets/js/typeahead-bs2.min.js"></script>

<!-- page specific plugin scripts -->
<script src="/assets/js/jquery.colorbox-min.js"></script>
<script src="/assets/js/date-time/bootstrap-datepicker.min.js"></script>
<script src="/assets/js/jqGrid/jquery.jqGrid.min.js"></script>
<script src="/assets/js/jqGrid/i18n/grid.locale-en.js"></script>


<!--[if lte IE 8]>
<script src="/assets/js/excanvas.min.js"></script>
<![endif]-->

<!-- ace scripts -->

<script src="/assets/js/ace-elements.min.js"></script>
<script src="/assets/js/ace.min.js"></script>

<!-- inline scripts related to this page -->
<script>
    function loadThisVote(){
        var voteid = request.getParameter("voteId");
        if (artid == null || artid.length < 1) return;

        $.ajax({
            type:"GET",
            url:"/rest/feature/vote/detail?voteId="+voteid,
            async:true,
            success:function(data){
                alert(data);
            }
        });
        ;
    }
</script>

<script type="text/javascript">
jQuery(function ($) {
<#if isSuper>
    $("#sidebar-shortcuts-navlist").load("/sidebar_super.html",function(){$("#nav_list_4_1").addClass("active open");$("#nav_list_2").addClass("active");});
<#else>
    $("#sidebar-shortcuts-navlist").load("/sidebar_admin.html",function(){$("#nav_list_4_1").addClass("active open");$("#nav_list_2").addClass("active");});
</#if>
    var colorbox_params = {
        reposition: true,
        scalePhotos: true,
        scrolling: false,
        previous: '<i class="icon-arrow-left"></i>',
        next: '<i class="icon-arrow-right"></i>',
        close: '&times;',
        current: '{current} of {total}',
        maxWidth: '100%',
        maxHeight: '100%',
        onOpen: function () {
            document.body.style.overflow = 'hidden';
        },
        onClosed: function () {
            document.body.style.overflow = 'auto';
        },
        onComplete: function () {
            $.colorbox.resize();
        }
    };

    $('.ace-thumbnails [data-rel="colorbox"]').colorbox(colorbox_params);
    $("#cboxLoadingGraphic").append("<i class='icon-spinner orange'></i>");//let's add a custom loading icon

    /**$(window).on('resize.colorbox', function() {
					try {
						//this function has been changed in recent versions of colorbox, so it won't work
						$.fn.colorbox.load();//to redraw the current frame
					} catch(e){}
				});*/
});

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function parseHelpData(data){

    for (var i = 0 ; i < data.length; i++){
        if(data[i].type == 0) {
            data[i].type = "需求";
        }
        else {
            data[i].type = "供给";
        }

        var adt = data[i].addTime;
        // ..
        //adt = adt.substr(0,12);
        var now = new Date();now.setTime(adt);
        var nowStr = now.Format("yyyy-MM-dd");

        data[i].addTime = nowStr;

        data[i].content = data[i].content.substr(0, 30);
        //data[i].content = data[i].content.substr(0,30);

    }
    return data;
}


//it causes some flicker when reloading or navigating grid
//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
//or go back to default browser checkbox styles for the grid
function styleCheckbox(table) {
    /**
     $(table).find('input:checkbox').addClass('ace')
     .wrap('<label />')
     .after('<span class="lbl align-top" />')


     $('.ui-jqgrid-labels th[id*="_cb"]:first-child')
     .find('input.cbox[type=checkbox]').addClass('ace')
     .wrap('<label />').after('<span class="lbl align-top" />');
     */
}


//unlike navButtons icons, action icons in rows seem to be hard-coded
//you can change them like this in here if you want
function updateActionIcons(table) {
    /**
     var replacement =
     {
         'ui-icon-pencil' : 'icon-pencil blue',
         'ui-icon-trash' : 'icon-trash red',
         'ui-icon-disk' : 'icon-ok green',
         'ui-icon-cancel' : 'icon-remove red'
     };
     $(table).find('.ui-pg-div span.ui-icon').each(function(){
		var icon = $(this);
		var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
		if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
	})
     */
}

//replace icons with FontAwesome icons like above
function updatePagerIcons(table) {
    var replacement =
    {
        'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
        'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
        'ui-icon-seek-next' : 'icon-angle-right bigger-140',
        'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
    };
    $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
        var icon = $(this);
        var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

        if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
    })
}

function enableTooltips(table) {
    $('.navtable .ui-pg-button').tooltip({container:'body'});
    $(table).find('.ui-pg-div').tooltip({container:'body'});
}

jQuery(function($) {
    var raw_data = [
        {_id:1,associationId:"1",title:"测试1",type:"news",overview:"",content:"测试内容",images:[],imagesCount:2,appreciateUserIds:[],appreciateCount:12,addTime:"20140203T12:13:14.443GMT0+800"},
        {_id:2,associationId:"2",title:"测试2",type:"news",overview:"",content:"测试内容",images:[],imagesCount:2,appreciateUserIds:[],appreciateCount:12,addTime:"20140203T12:13:14.443GMT0+800"}
    ];

    //raw_data.empty();
    raw_data = ${jsonHelpList};


    var grid_data = parseHelpData(raw_data);

    var grid_selector = "#grid-table";
    var pager_selector = "#grid-pager";

    jQuery(grid_selector).jqGrid({
        data: grid_data,
        datatype: "local",
        height: 330,
        colNames:['_id',<#if isSuper>'协会',</#if>'帮助标题','帮助类型', '帮助描述', '添加日期', '关注人数'],
        colModel:[
            {name:"_id",index:"_id",width:10,editable:false,hidden:true},
            //{name:"associationId",index:"associationId",width:40,editable:false, hidden:true},
            {name:"associationName",index:"associationName",width:40,editable:false<#if isSuper><#else>,hidden:true</#if>},
            {name:"title",index:"title",width:"100",editable:false},
            {name:"type",index:"type",width:"45",editable:false},
            {name:"content",index:"content",width:"270",editable:false},
            {name:"addTime",index:"addTime",width:"75",editable:false,sorttype:"date"},
            {name:"attentionNum",index:"attentionNum",width:"40",editable:false}
        ],
        viewrecords : true,
        rowNum:10,
        rowList:[10,20,30],
        pager : pager_selector,
        altRows: true,
        multiselect: true,
        multiboxonly: true,

        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                styleCheckbox(table);

                updateActionIcons(table);
                updatePagerIcons(table);
                enableTooltips(table);
            }, 0);
        },

        caption: "帮助列表",
        autowidth: true
    });


    jQuery(grid_selector).jqGrid('navGrid',pager_selector,
            { 	//navbar options
                add: true,
                addicon : 'icon-plus-sign purple',
                addfunc : (function(){window.location.href="/rest/bvote/new";/*alert("添加操作!");*/return false;}),

                edit: true,
                editicon : 'icon-pencil blue',
                editfunc : (function(){var id = $("#grid-table").getGridParam("selrow");id=$("#grid-table > tbody > tr").eq(id).find("td").eq(1).attr("title");window.location.href = '/rest/bnews/edit?voteId='+id;}),

                del: false,
                delicon : 'icon-trash red',
                delfunc : (function(){alert("删除操作!");return false;}),

                search: false,
                searchicon : 'icon-search orange',
                searchfunc: (function(){alert("s");return false;}),

                refresh: false,

                view: true,
                viewicon : 'icon-zoom-in grey',
                viewfunc: (function(){alert("预览操作!");return false;})
            }
    );

    $(".ui-jqgrid-htable").css("font-family","微软雅黑");
});

jQuery(function ($) {
    $('[data-rel=tooltip]').tooltip({container: 'body'});
    $('[data-rel=popover]').popover({container: 'body'});

    //$('textarea[class*=autosize]').autosize({append: "\n"});
    //$.mask.definitions['~'] = '[+-]';

    $('.date-picker').datepicker({autoclose: true}).next().on(ace.click_event, function () {
        $(this).prev().focus();
    });
    //$('input[name=date-range-picker]').daterangepicker().prev().on(ace.click_event, function () {
    //    $(this).next().focus();
    //});

    //chosen plugin inside a modal will have a zero width because the select element is originally hidden
    //and its width cannot be determined.
    //so we set the width after modal is show
    $('#modal-form').on('shown.bs.modal', function () {
        $(this).find('.chosen-container').each(function () {
            $(this).find('a:first-child').css('width', '210px');
            $(this).find('.chosen-drop').css('width', '210px');
            $(this).find('.chosen-search input').css('width', '200px');
        });
    })
    /**
     //or you can activate the chosen plugin after modal is shown
     //this way select element becomes visible with dimensions and chosen works as expected
     $('#modal-form').on('shown', function () {
					$(this).find('.modal-chosen').chosen();
				})
     */

});
</script>
</body>
</html>
