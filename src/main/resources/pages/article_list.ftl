<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
    <title>${associationName} 资讯</title>
    <meta name="description" content="接力"/>
    <!-- basic styles -->

    <link href="/assets/css/bootstrap.min.css" rel="stylesheet"/>

    <link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">

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
            <a href="#" class="navbar-brand">
                <small>
                    <i class="fa fa-leaf"></i>
                    ${associationName} 后台管理系统
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

                        <i class="fa fa-caret-down"></i>
                    </a>

                    <ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <li>
                            <a href="#" onclick="document.cookie='u=;path=/';window.location.href='/app/baccount/login'">
                                <i class="fa fa-power-off"></i>
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
                        <i class="fa fa-signal" style="display: none"></i>
                    </button>

                    <button class="btn btn-info">
                        <i class="fa fa-pencil" style="display: none"></i>
                    </button>

                    <button class="btn btn-warning">
                        <i class="fa fa-users" style="display: none"></i>
                    </button>

                    <button class="btn btn-danger">
                        <i class="fa fa-cogs" style="display: none"></i>
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
                <i class="fa fa-angle-double-left" data-icon1="fa fa-angle-double-left" data-icon2="fa fa-angle-double-right"></i>
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
                        <i class="fa fa-home home-icon"></i>
                        <a href="/index.html">首页</a>
                    </li>

                    <li>
                        <a href="/app/bactivity/list"> 资讯 </a>
                    </li>

                    <li class="active"> 资讯列表 </li>
                </ul>
                <!-- .breadcrumb -->

                <div class="nav-search" id="nav-search" style="display: none">
                    <form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="搜索 ..." class="nav-search-input"
                                           id="nav-search-input" autocomplete="off"/>
									<i class="fa fa-search nav-search-icon" style="position: absolute;top: 1px;bottom: 1px;left: 3px;"></i>
								</span>
                    </form>
                </div>
                <!-- #nav-search -->
            </div>

            <div class="page-content">
                <div class="page-header">
                    <h1>
                        资讯列表
                        <small>
                            <i class="fa fa-angle-double-right"></i>
                            请先选中目标资讯再点击编辑，预览或者删除按钮
                        </small>
                    </h1>
                </div>
                <!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->

                        <div class="form-group  <#if isSuper==false>hidden</#if>" style="height: 40px;">
                            <label class="col-sm-1 control-label no-padding-right" for="selectAssociationIds" style="min-width: 70px;"> 选择协会 </label>

                            <div class="col-sm-4">
                                <select class="form-control" id="selectAssociationIds" onchange="window.location.href='/app/bnews/list?aid=' + (this.value)">${assIdOptionList}</select>
                            </div>
                        </div>

                        <button class="btn btn-success" type="button" style="font-weight:bold;margin-bottom: 20px;" onclick="window.location.href = '/app/bnews/new'">
                            <i class="fa fa-plus bigger-110"></i>
                            添加资讯
                        </button>
                        &nbsp;&nbsp;&nbsp;&nbsp;

                        <button class="btn btn-info" type="button" style="font-weight:bold;margin-bottom: 20px;" onclick="previewThisArticle()">
                            <i class="fa fa-search-plus bigger-110"></i>
                            预览资讯
                        </button>
                        &nbsp;&nbsp;&nbsp;&nbsp;

                        <button class="btn btn-warning" type="button" style="font-weight:bold;margin-bottom: 20px;" id="setTitleImgClick">
                            <i class="fa fa-search-plus bigger-110"></i>
                            设置置顶
                        </button>
                        &nbsp;&nbsp;&nbsp;&nbsp;

                        <button class="btn" type="button" style="font-weight:bold;margin-bottom: 20px;" id="unTitleImgClick">
                            <i class="fa fa-search-plus bigger-110"></i>
                            取消置顶
                        </button>
                        &nbsp;&nbsp;&nbsp;&nbsp;

                        <button class="btn btn-danger" type="button" style="font-weight:bold;margin-bottom: 20px;" id="deleteArtBtn">
                            <i class="fa fa-trash-o bigger-110"></i>
                            删除资讯
                        </button>
                        &nbsp;&nbsp;&nbsp;&nbsp;

                        <div class="alert alert-warning" style="margin-left: 15px; width: 450px; position: relative;display: inline-block;padding: 10px;">&nbsp;小提示:&nbsp;如果您修改了置顶资讯的第一张图片，请重新将此资讯置顶</div>

                        <table id="grid-table"></table>
                        <div id="grid-pager"></div>
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->

                <div id="dialog-message-preview" class="hide">
                </div><!-- #dialog-message -->

            </div>
            <!-- /.page-content -->
        </div>
        <!-- /.main-content -->

        <div class="ace-settings-container" id="ace-settings-container">
            <div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
                <i class="fa fa-cog bigger-150"></i>
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

                <div>
                    <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar"/>
                    <label class="lbl" for="ace-settings-navbar"> Fixed Navbar</label>
                </div>

                <div>
                    <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar"/>
                    <label class="lbl" for="ace-settings-sidebar"> Fixed Sidebar</label>
                </div>

                <div>
                    <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs"/>
                    <label class="lbl" for="ace-settings-breadcrumbs"> Fixed Breadcrumbs</label>
                </div>

                <div>
                    <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl"/>
                    <label class="lbl" for="ace-settings-rtl"> Right To Left (rtl)</label>
                </div>

                <div>
                    <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container"/>
                    <label class="lbl" for="ace-settings-add-container">
                        Inside
                        <b>.container</b>
                    </label>
                </div>
            </div>
        </div>
        <!-- /#ace-settings-container -->
    </div>
    <!-- /.main-container-inner -->

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="fa fa-angle-double-up icon-only bigger-110"></i>
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
<script src="/assets/js/jqGrid/i18n/grid.locale-zh-art.js"></script>


<script src="/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>
<script src="/common-jieli.js"></script>

<!--[if lte IE 8]>
<script src="/assets/js/excanvas.min.js"></script>

<![endif]-->


<!--
<script src="/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>
<script src="/assets/js/chosen.jquery.min.js"></script>
<script src="/assets/js/fuelux/fuelux.spinner.min.js"></script>
<script src="/assets/js/date-time/bootstrap-datepicker.min.js"></script>
<script src="/assets/js/date-time/bootstrap-timepicker.min.js"></script>
<script src="/assets/js/date-time/moment.min.js"></script>
<script src="/assets/js/date-time/daterangepicker.min.js"></script>
<script src="/assets/js/bootstrap-colorpicker.min.js"></script>
<script src="/assets/js/jquery.knob.min.js"></script>
<script src="/assets/js/jquery.autosize.min.js"></script>
<script src="/assets/js/jquery.inputlimiter.1.3.1.min.js"></script>
<script src="/assets/js/jquery.maskedinput.min.js"></script>
<script src="/assets/js/bootstrap-tag.min.js"></script>
<script src="/assets/js/jquery.gritter.min.js"></script>
<script src="/assets/js/bootbox.min.js"></script>
-->

<!-- ace scripts -->

<script src="/assets/js/ace-elements.min.js"></script>
<script src="/assets/js/ace.min.js"></script>

<!-- inline scripts related to this page -->
<script type="text/javascript">
var raw_data;

//override dialog's title function to allow for HTML titles
$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
    _title: function(title) {
        var $title = this.options.title || '&nbsp;'
        if( ("title_html" in this.options) && this.options.title_html == true )
            title.html($title);
        else title.text($title);
    }
}));

jQuery(function ($) {
<#if isSuper>
    $("#sidebar-shortcuts-navlist").load("/sidebar_super.html",function(){updateByAID();$("#nav_list_2").addClass("active");});
<#else>
    $("#sidebar-shortcuts-navlist").load("/sidebar_admin.html",function(){updateByAID();$("#nav_list_2").addClass("active");});
</#if>
    var colorbox_params = {
        reposition: true,
        scalePhotos: true,
        scrolling: false,
        previous: '<i class="fa fa-arrow-left"></i>',
        next: '<i class="fa fa-arrow-right"></i>',
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
    $("#cboxLoadingGraphic").append("<i class='fa fa-spinner orange'></i>");//let's add a custom loading icon

    /**$(window).on('resize.colorbox', function() {
					try {
						//this function has been changed in recent versions of colorbox, so it won't work
						$.fn.colorbox.load();//to redraw the current frame
					} catch(e){}
				});*/
});

function setTitleImgClickFunc(){
    var id = $("#grid-table").getGridParam("selrow") - 1;
    //id = $("#grid-table > tbody > tr").eq(id).find("td").eq(1).attr("title");
    var news = raw_data[id];
    if (news.title) news.title=news.title.replace("<span style='color:rgb(226, 64, 64)'>[置顶]</span>&nbsp;","");

    setTitleImg(news);
}

function unTitleImgClickFunc(){
    var id = $("#grid-table").getGridParam("selrow") - 1;
    //id = $("#grid-table > tbody > tr").eq(id).find("td").eq(1).attr("title");
    var news = raw_data[id];
    if (news.title) news.title=news.title.replace("<span style='color:rgb(226, 64, 64)'>[置顶]</span>&nbsp;","");

    unTitleImg(news);
}

$("#setTitleImgClick").click(setTitleImgClickFunc);
$("#unTitleImgClick").click(unTitleImgClickFunc);

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

function parseArtData(data){
    var types = {"news":"同舟发布","association":"协会动态","enterprise":"小组风采","history":"协会事记","benefit":"慈善公益"};
    for (var i = 0 ; i < data.length; i++){
        data[i].type = types[data[i].type];
        var adt = data[i].addTime;
        // ..
        //adt = adt.substr(0,12);
        var now = new Date();now.setTime(adt);
        var nowStr = now.Format("yyyy-MM-dd");

        data[i].addTime = nowStr;

        //data[i].content = data[i].overview;
        if (data[i].topPic){
            data[i].title = "<span style='color:rgb(226, 64, 64)'>[置顶]</span>&nbsp;" + data[i].title;
        }

    }
    return data;
}

function styleCheckbox(table) {
}


function updateActionIcons(table) {
}

//replace icons with FontAwesome icons like above
function updatePagerIcons(table) {
    var replacement =
    {
        'ui-icon-seek-first' : 'fa fa-angle-double-left bigger-140',
        'ui-icon-seek-prev' : 'fa fa-angle-left bigger-140',
        'ui-icon-seek-next' : 'fa fa-angle-right bigger-140',
        'ui-icon-seek-end' : 'fa fa-angle-double-right bigger-140'
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

var total = "??";
var records = ${ti};
var page = ${cp};

jQuery(function($) {
    raw_data = [
        {_id:1,associationId:"1",title:"测试1",type:"news",overview:"",content:"测试内容",images:[],imagesCount:2,appreciateUserIds:[],appreciateCount:12,addTime:"20140203T12:13:14.443GMT0+800"},
        {_id:2,associationId:"2",title:"测试2",type:"news",overview:"",content:"测试内容",images:[],imagesCount:2,appreciateUserIds:[],appreciateCount:12,addTime:"20140203T12:13:14.443GMT0+800"}
    ];

    //raw_data.empty();
    raw_data = ${newsList};

    var grid_data = parseArtData(raw_data);

    var grid_selector = "#grid-table";
    var pager_selector = "#grid-pager";

    jQuery(grid_selector).jqGrid({
        data: grid_data,
        datatype: "local",
        height: 490,
        colNames:['_id','协会','资讯标题','资讯类型', '资讯内容', '添加日期', '图片数量', '点赞数量','资讯内容html'],
        colModel:[
            {name:"_id",index:"_id",width:10,editable:false,hidden:true},
            {name:"associationId",index:"associationId",width:40,editable:false,hidden:true},
            {name:"title",index:"title",width:"100",editable:false,
                formatter:function getUrl(cellValue, options, rowObject) {
                    var url = "<a href=\"/app/bnews/edit?artid=" + rowObject._id + "\">" + cellValue + "</a>";
                    return url;
                }
            },
            {name:"type",index:"type",width:"45",editable:false},
            {name:"overview",index:"overview",width:"330",editable:false},
            {name:"addTime",index:"addTime",width:"120",editable:false,sorttype:"date"},
            {name:"imagesCount",index:"imagesCount",width:"40",editable:false},
            {name:"appreciateCount",index:"appreciateCount",width:"40",editable:false},
            {name:"content",index:"content",width:"4000",hidden:true}
        ],
        viewrecords : true,
        rowNum:15,
        /*rowList:[10,20,30],*/
        pager : pager_selector,
        /*altRows: true,*/
        multiselect: true,
        /*multiboxonly: true,*/

        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                styleCheckbox(table);

                updateActionIcons(table);
                updatePagerIcons(table);
                enableTooltips(table);
            }, 0);
        },

        caption: "在这里编辑或删除文章",
        autowidth: true,
        onPaging: function(pgButton){item_select('',pgButton);}
    });

    function deleteArticles(){
        var ids = $("#grid-table").getGridParam("selarrrow");
        if (ids.length == 0){
            alert("请先选中资讯");
            return;
        }else {
            if (ids.length == 0 || !confirm("确认删除选中的资讯？")) return;
            var suc = true;
            var erro = false;
            for (var i = 0; i < ids.length; i++){
                var id = $("#grid-table > tbody > tr").eq(ids[i]).find("td").eq(1).attr("title");
                if (!id || id.length == 0) continue;

                $.ajax({
                    type: "POST",
                    url: "/app/bnews/del?artid=" + id,
                    async: false,
                    contentType: "application/json; charset=utf-8",
                    success: function (jsn) {
                        if (jsn.code != 200) {
                            suc = false;
                        }
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        alert("操作失败，错误码："+XMLHttpRequest.status);
                        erro = true;
                        return;
                    }
                });
            }

            if (erro) return;

            if (suc) {
                alert("删除成功！");
                window.location.reload();
            }else{
                alert("删除失败或部分资讯删除失败！");
            }
        }
    }

    $("#deleteArtBtn").click(deleteArticles);

    jQuery(grid_selector).jqGrid('navGrid',pager_selector,
            { 	//navbar options
                add: false,
                addicon : 'fa fa-plus-circle purple',
                addfunc : (function(){window.location.href="/app/bnews/new";/*alert("添加操作!");*/return false;}),

                edit: false,
                editicon : 'fa fa-pencil blue',
                editfunc : (function(){
                    var id = $("#grid-table").getGridParam("selrow");
                    if (id) id=$("#grid-table > tbody > tr").eq(id).find("td").eq(1).attr("title");
                    if (!id || id.length == 0) alert("请先选择一则资讯");
                    else window.location.href = '/app/bnews/edit?artid='+id;
                }),

                del: false,
                delicon : 'fa fa-trash-o red',
                delfunc : (function(){/*alert("删除操作!");*/
                    var id = $("#grid-table").getGridParam("selrow");
                    if (id) id=$("#grid-table > tbody > tr").eq(id).find("td").eq(1).attr("title");
                    if (!id || id.length == 0) alert("请先选择一则资讯");
                    if (confirm("确认删除选中的资讯？")) {
                        $.ajax({
                            type: "POST",
                            url: "/app/bnews/del?artid=" + id,
                            success: function (jsn) {
                                if (jsn.code == 200) {
                                    alert("已删除该资讯");
                                    window.location.reload();
                                } else {
                                    alert("删除失败" + jsn.msg);
                                }
                            },
                            error: function(XMLHttpRequest, textStatus, errorThrown) {
                                alert("操作失败，错误码："+XMLHttpRequest.status);
                                return;
                            }
                        });
                    }
                }),

                search: false,
                searchicon : 'fa fa-list orange',
                searchfunc: (function(){
                    var id = $("#grid-table").getGridParam("selrow");
                    if (id) id=$("#grid-table > tbody > tr").eq(id).find("td").eq(1).attr("title");
                    if (!id || id.length == 0) alert("请先选择一则资讯");
                    else window.location.href = '/app/bnews/comment?artid='+id;
                }),

                refresh: false,

                view: false,
                viewicon : 'fa fa-search-plus grey',
                viewfunc: (function(){previewThisArticle();/*alert("预览操作!");*/return false;})
            }
    );

    $("#grid-pager_left .ui-state-disabled").remove();

    $(".ui-jqgrid-htable").css("font-family","微软雅黑");

    $(".ui-pg-input").val(page);

    $(".ui-pg-input").bind('keypress',function(event){
        if (event.keyCode=='13'){
            var v_input = $(".ui-pg-input").val();
            try{
                if (!isNaN(parseInt(v_input)) && parseInt(v_input) > 0){
                    /* 如果输入输入值太大 */
                    if (parseInt(v_input) > page && records < 15) {
                        alert("输入的页数太大了!");return false;
                    }
                    else{
                        var str = "rowNum=${rowNum}&";
                        str += "page="+parseInt(v_input);
                        var aid = getUrlParam("aid");
                        if (aid) str += "&aid="+aid;
                        window.location.href = "/app/bnews/list?rowNum="+str;
                    }
                }
            }catch (e){
                alert("请输入有效的页数！");
            }
        }
    });

    $("#first_grid-pager").removeClass("ui-state-disabled");
    $("#last_grid-pager").addClass("ui-state-disabled");
    $("#prev_grid-pager").removeClass("ui-state-disabled");
    $("#next_grid-pager").removeClass("ui-state-disabled");
});


function item_select(o,pgButton){
    switch (pgButton) {
        case 'first_grid-pager' :
            page = 1;
            break;
        case 'last_grid-pager' :
            return false;
            break;
        case 'prev_grid-pager' :
            page = page - 1;
            break;
        case 'next_grid-pager' :
            page = page + 1;
            break;
        default  :
            break;
    }

    if (pgButton == 'next_grid-pager' && records < 15)
    {page = page - 1;return false;}

    if (page == 0)
    {page = 1; return false;}

    var str = "rowNum=${rowNum}&";
    str += "page="+page;

    var aid = getUrlParam("aid");
    if (aid) str += "&aid="+aid;
    window.location.href = "/app/bnews/list?" + str;
}

jQuery(function ($) {
    $('[data-rel=tooltip]').tooltip({container: 'body'});
    $('[data-rel=popover]').popover({container: 'body'});

    //$('textarea[class*=autosize]').autosize({append: "\n"});
    //$.mask.definitions['~'] = '[+-]';

    $('.date-picker').datepicker({autoclose: true}).next().on(ace.click_event, function () {
        $(this).prev().focus();
    });

    //$('input[name=date-range-picker]').daterangepicker().prev().on(ace.click_event, function () {
    //   $(this).next().focus();
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
