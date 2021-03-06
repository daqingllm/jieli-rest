<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
    <title>${associationName} 账号管理</title>
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
                        <a href="/app/baccount/list"> 账号管理 </a>
                    </li>

                    <li class="active"> 账号列表 </li>
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
                        账号列表
                    </h1>
                </div>
                <!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->


                        <div class="form-group  <#if isSuper==false>hidden</#if>" style="height: 40px;">
                            <label class="col-sm-1 control-label no-padding-right" for="selectAssociationIds" style="min-width: 70px;"> 选择协会 </label>

                            <div class="col-sm-4">
                                <select class="form-control" id="selectAssociationIds" onchange="window.location.href='/app/baccount/list?aid=' + (this.value)">${assIdOptionList}</select>
                            </div>
                        </div>

                        <button class="btn btn-success" type="button" style="font-weight:bold;margin-bottom: 20px;" onclick="window.location.href = '/app/baccount/register'">
                            <i class="fa fa-plus bigger-110"></i>
                            添加账号
                        </button>
                        &nbsp;&nbsp;&nbsp;&nbsp;

                        <button class="btn btn-danger" type="button" style="font-weight:bold;margin-bottom: 20px;" id='deleteAccountBtn'>
                            <i class="fa fa-trash-o bigger-110"></i>
                            删除账号
                        </button>
                        &nbsp;&nbsp;&nbsp;&nbsp;

                        <button class="btn btn-info" type="button" style="font-weight:bold;margin-bottom: 20px;" id='changeVerifyBtn'>
                            <i class="fa fa-pencil bigger-110"></i>
                            修改管理员密码
                        </button>

                        &nbsp;&nbsp;&nbsp;&nbsp;

                        <button class="btn btn-purple" type="button" style="font-weight:bold;margin-bottom: 20px;">
                            <i class="fa fa-cloud-download bigger-110"></i>
                            <a href="/assets/Template.xlsx" style="color: #ffffff">下载XLSX表格模板</a>
                        </button>
                        &nbsp;&nbsp;&nbsp;&nbsp;

                        <button class="btn btn-warning" type="button" style="font-weight:bold;margin-bottom: 20px;" id='uploadCSV'>
                            <i class="fa fa-cloud-upload bigger-110"></i>
                            上传账户CSV表格
                        </button>
                        <div class="alert alert-warning" style="margin-left: 15px; width: 300px; position: relative;display: inline-block;padding: 10px;">&nbsp;小提示:&nbsp;您可以将XLSX文件另存为CSV文件</div>

                        <br>
                        <div class="alert alert-warning" id="failedImport" style="display: none">
                            <button type="button" class="close" onclick="$(this).parent().hide();" style="float: left;margin-right:10px;">
                                <i class="fa fa-times"></i>
                            </button>
                            <strong id="failedImportNameList"></strong>
                        </div>


                        <table id="grid-table"></table>
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
<script src="/assets/js/jqGrid/i18n/grid.locale-zh-acc.js"></script>


<!--[if lte IE 8]>
<script src="/assets/js/excanvas.min.js"></script>
<![endif]-->

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

<script src="/assets/js/jquery.form.js"></script>
<script src="/common-jieli.js"></script>

<!-- ace scripts -->

<script src="/assets/js/ace-elements.min.js"></script>
<script src="/assets/js/ace.min.js"></script>

<!-- inline scripts related to this page -->
<script>

    var adminList = [];

    function loadThisArticle(){
        var artid = request.getParameter("artid");
        if (artid == null || artid.length < 1) return;

        $.ajax({
            type:"GET",
            url:"/app/news/load?new_id="+artid,
            async:true,
            success:function(data){
                alert(data);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert("无法获取内容，错误码："+XMLHttpRequest.status);
                return;
            }
        });
        ;
    }

    function loadAllUsers(sid,state){
        var d = {"id":sid,"state":state};
        $.ajax({
            type:"GET",
            url:"/app/association/user",
            async:ture,
            data:d,
            success:function(jsn){
                var ulist;
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert("无法获取用户列表，错误码："+XMLHttpRequest.status);
                return;
            }
        });
    }
</script>

<script type="text/javascript">
jQuery(function ($) {
<#if isSuper>
    $("#sidebar-shortcuts-navlist").load("/sidebar_super.html",function(){updateByAID();$("#nav_list_8").addClass("active");});
<#else>
    $("#sidebar-shortcuts-navlist").load("/sidebar_admin.html",function(){updateByAID();$("#nav_list_7").addClass("active");});
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
    var states = {"DISABLE":"禁用","ENABLE":"普通用户","ADMIN":"协会管理员","SUPPER":"超级管理员"};
    for (var i = 0 ; i < data.length; i++){
        if (data[i].state == "ADMIN") {
            data[i].name = data[i].username;
            data[i].identity = "协会管理员";
            adminList.push(data[i]["_id"]);
            data[i].verifyCode = "";
        } else {
            data[i].verifyCode = data[i].password;
        }

        data[i].state = states[data[i].state];

        if (data[i].identity == undefined || data[i].identity == null || data[i].identity == "") data[i].identity = "";
        if (data[i].group == undefined || data[i].group == null || data[i].group == "") data[i].group = "";

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
         'ui-fa fa-pencil' : 'fa fa-pencil blue',
         'ui-fa fa-trash-o' : 'fa fa-trash-o red',
         'ui-fa fa-disk' : 'fa fa-ok green',
         'ui-fa fa-cancel' : 'fa fa-remove red'
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

jQuery(function($) {
    var raw_data = [
        {_id:1,associationId:"1",title:"测试1",type:"news",overview:"",content:"测试内容",images:[],imagesCount:2,appreciateUserIds:[],appreciateCount:12,addTime:"20140203T12:13:14.443GMT0+800"},
        {_id:2,associationId:"2",title:"测试2",type:"news",overview:"",content:"测试内容",images:[],imagesCount:2,appreciateUserIds:[],appreciateCount:12,addTime:"20140203T12:13:14.443GMT0+800"}
    ];

    //raw_data.empty();
    raw_data = ${jsonAccList};

    var grid_data = parseArtData(raw_data);
    //var grid_data = raw_data;

    var grid_selector = "#grid-table";
    var pager_selector = "#grid-pager";

    jQuery(grid_selector).jqGrid({
        data: grid_data,
        datatype: "local",
        height: 490,
        colNames:['_id','协会','用户名','姓名','状态','协会身份','验证码','分组'],
        colModel:[
            {name:"_id",index:"_id",width:10,editable:false,hidden:true},
            {name:"association",index:"association",width:40,editable:false,<#if isSuper><#else>hidden:true</#if>},
            {name:"username",index:"username",width:"100",editable:false,hidden:true},
            {name:"name",index:"name",width:"75",editable:false,formatter:function getUrl(cellValue, options, rowObject) {
                    var url = "<a href=\"/app/baccount/edit?u=" + rowObject._id + "\">" + cellValue + "</a>";
                    if (adminList.indexOf(rowObject._id) > -1) return "<span style='color:rgb(226, 64, 64)'>[管理员]</span>&nbsp;<span style='color:#428bca'>" + cellValue + "</span>";
                    else return url;
            }},
            {name:"state",index:"state",width:"60",editable:false,hidden:true},
            {name:"identity",index:"identity",width:"60",editable:false},
            {name:"verifyCode",index:"verifyCode",width:"75",editable:false,hidden:true},
            {name:"group",index:"group",width:"75",editable:false}
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

        caption: "",
        autowidth: true
    });

    function makeAccount(){

        var id = $("#grid-table").getGridParam("selarrrow");
        if (id == null || id.length == 0) return;

        var accs = [];

        var states={"禁用":"DISABLE","普通用户":"ENABLE","协会管理员":"ADMIN","超级管理员":"SUPPER"};
        for (var i = 0; i < id.length; i ++) {
            var _id_ = $("#grid-table > tbody > tr").eq(id[i]).find("td").eq(1).attr("title");
            var curpagenum = $("#grid-table").jqGrid('getGridParam', 'page');
            var realIndex = (curpagenum-1)*15 + parseInt(id[i]) - 1;

            if (realIndex >= raw_data.length && realIndex < 0 || raw_data[realIndex]._id != _id_){continue;}

            var a = raw_data[realIndex];
            delete a["name"];
            delete a["identity"];
            delete a["group"];
            delete a["phone"];
            delete a["verifyCode"];
            delete a["association"];
            if (a.state == "禁用" ||
                    a.state == "普通用户" ||
                    a.state == "协会管理员" ||
                    a.state == "超级管理员")
            a.state = states[a.state];
            accs.push(a);
            continue;

            var acc = {};
            acc.associationId = $("#grid-table > tbody > tr").eq(id).find("td").eq(2).attr("title");
            acc.username = $("#grid-table > tbody > tr").eq(id).find("td").eq(3).attr("title");
            acc.state = $("#grid-table > tbody > tr").eq(id).find("td").eq(4).attr("title");
            acc.state = states[acc.state];
            acc.password = "";
            accs.push(acc);
        }

        return accs;
    }

    function deleteAccount(){
        var accs=makeAccount();
        /*if (acc.state != 0) return;*/
        //acc.password="";//donot change password
        //acc.associationId="";//donot change association
        //acc.state=0;
        var includeAdmin = false;
        var selectAdmin = "";
        for (var i = 0; i < accs.length; i ++){
            if (accs[i].state == "ADMIN"){includeAdmin = true;selectAdmin=accs[i].username;break;}
        }

        if(accs.length > 0 && confirm("确认删除选中账号？"+(includeAdmin?"请注意：您选中了管理员账号":"")+selectAdmin)){
            var suc = true;
            var erro = false;
            for (var i =0;i < accs.length; i ++) {
                var acc = accs[i];
                acc.password="";//donot change password
                acc.associationId="";//donot change association
                acc.state=0;
                $.ajax({
                    type: "POST",
                    url: "/app/account",
                    async: false,
                    data: JSON.stringify(acc),
                    contentType: "application/json; charset=utf-8",
                    success: function (jsn) {
                        if (jsn.code == 200) {
                        }
                        else suc=false;
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        alert("操作失败，错误码："+XMLHttpRequest.status);
                        erro = true;
                        return;
                    }
                });
            }

            if (erro) return;

            if (suc) {alert("删除成功");window.location.reload();}
            else alert("删除失败");

        }
    }

    $("#deleteAccountBtn").click(deleteAccount);

    function changeVerify(){

        var uname = "";
        var assid = "";
        var acc = makeAccount();

        if (!acc || acc.length == 0 || acc[0].state == "DISABLE" || acc[0].state == "ENABLE"){
            alert("请先选中一个管理员账号！");
            return ;
        } else if (acc[0].state == "ADMIN") {
            /*if (!confirm("确认修改管理员的密码吗？")) return;*/
        } else if (acc[0].state == "SUPPER") {
            /*if (!confirm("确认修改管理员的密码吗？")) return;*/
        } else {alert("无法修改！");return;}

        bootbox.prompt("请输入 管理员 ‘"+acc[0].username+"’的新密码：", function(result) {
            // 这里不改状态
            if (result !== null) {
                if (result.length <= 3){
                    alert("密码长度必须大于3");
                }else{
                    acc[0].password=result;
                    $.ajax({
                        type:"POST",
                        url:"/app/baccount/changepassword",
                        async:false,
                        data:JSON.stringify(acc[0]),
                        contentType:"application/json; charset=utf-8",
                        success:function(jsn){
                            if (jsn.code==200) alert("密码已经修改成"+result);
                            else alert("密码修改失败");
                        },
                        error: function(XMLHttpRequest, textStatus, errorThrown) {
                            alert("操作失败，错误码："+XMLHttpRequest.status);
                            return;
                        }
                    });
                }
            }
        });

    }

    $("#changeVerifyBtn").click(changeVerify);
    $("#uploadCSV").click(uploadCSV);

    jQuery(grid_selector).jqGrid('navGrid',pager_selector,
            { 	//navbar options
                add: false,
                addicon : 'fa fa-pencil purple',
                addfunc : (function(){
                    var uname = "";
                    var assid = "";
                    var acc = makeAccount();

                    if (acc.state == 1){
                        return ;
                    } else if (acc.state == 2) {
                        if (!confirm("确认修改管理员的密码吗？")) return;
                    } else {
                        return;
                    }

                    bootbox.prompt("请输入用户 ‘"+acc.username+"’的新密码：", function(result) {
                        // 这里不改状态
                        if (result !== null) {
                            if (result.length <= 3){
                                alert("密码长度必须大于3");
                            }else{
                                acc.password=result;
                                $.ajax({
                                    type:"POST",
                                    url:"/app/account/",
                                    async:false,
                                    data:JSON.stringify(acc),
                                    contentType:"application/json; charset=utf-8",
                                    success:function(jsn){
                                        if (jsn.code==200) alert("密码已经修改成"+result);
                                        else alert("密码修改失败，因为"+jsn.msg);
                                    },
                                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                                        alert("操作失败，错误码："+XMLHttpRequest.status);
                                        return;
                                    }
                                });
                            }
                        }
                    });
                }),

            <#if isSuper>
                edit: false,
                editicon : 'fa fa-arrow-up blue',
                editfunc : (function(){
                    var acc=makeAccount();
                    if (acc.state == 2) return;
                    acc.state=2;
                    acc.password="";//donot change password
                    acc.associationId="";//donot change association
                    if(confirm("确认升级账户"+acc.username+"为管理员？")){
                        $.ajax({
                            type:"POST",
                            url:"/app/account/",
                            async:true,
                            data:JSON.stringify(acc),
                            contentType:"application/json; charset=utf-8",
                            success:function(jsn){
                                if(jsn.code==200) alert("用户"+acc.username+"已经升级为管理员");
                                else alert("操作失败，"+jsn.msg);
                            },
                            error: function(XMLHttpRequest, textStatus, errorThrown) {
                                alert("操作失败，错误码："+XMLHttpRequest.status);
                                return;
                            }
                        });
                    }
                }),
            <#else>
                edit: false,
            </#if>

                del: false,
                delicon : 'fa fa-trash-o red',
                delfunc : (function(){
                    var username = "";
                    var acc=makeAccount();
                    /*if (acc.state != 0) return;*/
                    acc.password="";//donot change password
                    acc.associationId="";//donot change association
                    acc.state=0;
                    if(confirm("确认删除选中账号"+acc.username+"？")){
                        $.ajax({
                            type:"POST",
                            url:"/app/account/",
                            async:true,
                            data:JSON.stringify(acc),
                            contentType:"application/json; charset=utf-8",
                            success:function(jsn){
                                if(jsn.code==200) {alert("用户"+acc.username+"已被删除");window.location.reload();}
                                else alert("操作失败，"+jsn.msg);
                            },
                            error: function(XMLHttpRequest, textStatus, errorThrown) {
                                alert("操作失败，错误码："+XMLHttpRequest.status);
                                return;
                            }
                        });
                    }
                }),

                search: false,
                refresh: false,

                view: false
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
