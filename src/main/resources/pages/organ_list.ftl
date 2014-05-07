<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
    <title>接力 分组管理</title>
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
                <a href="#"> 分组管理 </a>
            </li>

            <li class="active"> 分组列表 </li>
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
        <div class="page-header" style="display: none;">
            <h1>
                分组列表
            </h1>
        </div>
        <!-- /.page-header -->


        <div class="row" style="display: none">
            <div class="col-xs-12">
                <!-- PAGE CONTENT BEGINS -->
                <table id="grid-table" style="display: none"></table>
                <div id="grid-pager" style="display: none"></div>
            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->

        <div id="dialog-message-add-group" class="hide">
            <form>
                <fieldset>
                    <label class="block clearfix">
                				<span class="block input-icon input-icon-right">
									<input type="text" class="form-control" placeholder="分组名" id="add-group-name" style="margin-top: 20px;" />
									<i class="icon-group"></i>
                				</span>
                    </label>

                <#if isSuper>
                    <label class="block clearfix">
                                	<span class="block input-icon input-icon-right">
				                		<select class="form-control" id="add-group-assoc">
                                        ${associationOps}
                                        </select>
			                        </span>
                    </label>
                </#if>
                </fieldset>
            </form>
        </div><!-- #dialog-message -->

        <div id="dialog-message-add-user" class="hide">
            <form>
                <fieldset>
                    <label class="block clearfix">
                				<span class="block input-icon input-icon-right">
									<!--<input type="text" class="form-control" placeholder="用户名" id="add-user-name" style="margin-top: 20px;" />-->
									<select id="add-user-name" style="margin-top: 20px;" class="form-control">

                                    </select>
                				</span>
                    </label>
                </fieldset>
            </form>
        </div>

        <h3 class="header blue lighter smaller">
            <i class="icon-list smaller-90"></i>
            分组列表
        </h3>

        <div id="accordion" class="accordion-style2">
        <#list groups as group>
            <div class="group" id="gn${group.name}">
                <h3 class="accordion-header" onclick="loadThisGroup('${group.name}',false)">${group.name}</h3>
                <div onclick="return false;">
                    <p><p>
                    <ul></ul>
                </div>
            </div>
        </#list>
        </div>

        <div class="clearfix form-actions">
            <div class="col-md-offset-3 col-md-9">
                <button class="btn btn-info" type="button" onclick="addAGroup()">
                    <i class="icon-add bigger-110"></i>
                    添加一个分组
                </button>

                &nbsp; &nbsp; &nbsp;
                <button class="btn" type="reset" onclick="deleteCurrentGroup()">
                    <i class="icon-remove bigger-110"></i>
                    删除当前分组
                </button>
            </div>
        </div>

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

<script src="/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>

<!--
<script src="/assets/js/chosen.jquery.min.js"></script>
<script src="/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>
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
jQuery(function ($) {
    /* sidebar */
<#if isSuper>
    $("#sidebar-shortcuts-navlist").load("/sidebar_super.html",function(){$("#nav_list_8_1").addClass("active open");$("#nav_list_8").addClass("active");});
<#else>
    $("#sidebar-shortcuts-navlist").load("/sidebar_admin.html",function(){$("#nav_list_8_1").addClass("active open");$("#nav_list_8").addClass("active");});
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

jQuery(function($){
    $( "#accordion" ).accordion({
        collapsible: true ,
        heightStyle: "content",
        animate: 250,
        header: ".accordion-header"
    }).sortable({
        axis: "y",
        handle: ".accordion-header",
        stop: function( event, ui ) {
            // IE doesn't register the blur when sorting
            // so trigger focusout handlers to remove .ui-state-focus
            ui.item.children( ".accordion-header" ).triggerHandler( "focusout" );
        }
    });

//override dialog's title function to allow for HTML titles
    $.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
        _title: function(title) {
            var $title = this.options.title || '&nbsp;'
            if( ("title_html" in this.options) && this.options.title_html == true )
                title.html($title);
            else title.text($title);
        }
    }));

    loadThisGroup("${firstGroupName}",true);
});

/* 载入此分组用户列表 */
function loadThisGroup(name,refresh){
    if ($("#gn"+name).length == 0) return;
    if (refresh == false
            && $("#gn"+name).find("ul")
            && $("#gn"+name).find("ul").children("li").length > 0) return;

    $.ajax({
        type:"GET",
        url:"/rest/association/group",
        data:{"group":name},
        success:function(jsn){
            if (jsn.code == 200){


                var jsn_body;
                var btn_html = "<button id='addtocg' style='margin-left:15px;' class='btn btn-xs btn-success' onclick='addAUser()'>向此分组中添加用户</button>";
                btn_html +="<button id='delfromcg' style='margin-left:15px;' class='btn btn-danger btn-xs' onclick='removeAUser()'>从分组中删除选中的用户</button>";

                if (!(jsn.body) || jsn.body.length == 0){
                    $("#gn"+name).children("div").eq(0).children("p").eq(0).html("暂无用户属于"+name + btn_html);
                    $("#gn"+name).find("ul").html("");
                }
                else {
                    //eval("jsn_body={"+jsn.body+"}");
                    jsn_body = jsn.body;
                    $("#gn"+name).children("div").eq(0).children("p").eq(0).html("一共有"+$("#gn"+name).length+"个用户属于"+name + btn_html);
                    loadGroupMembers(name,jsn_body,refresh);
                }
            }
        }
    });
}

var lastSelectedItem = null;

/* 选择一个用户  */
function selectUser(obj){
    if (lastSelectedItem == null) lastSelectedItem = obj;
    else if (lastSelectedItem == obj) lastSelectedItem = null;
    else {
        var oh = $(lastSelectedItem).html().trim();
        if (oh.substr(0,1) == "√") {
            $(lastSelectedItem).html(oh.substr(1).trim());
            //$(lastSelectedItem).css("background-color","");
            lastSelectedItem = obj;
        }
    }

    var h = $(obj).html().trim();
    if (h.substr(0,1) == "√") {
        $(obj).html(h.substr(1).trim());
        //$(obj).css("background-color","");
    }
    else {
        $(obj).html("√ " + h);
        //$(obj).css("background-color","#ffb752");
    }

}

/* 显示此分组中的用户列表 */
function loadGroupMembers(groupName,members,refresh){
    if ( groupName != "" &&
            refresh == false &&
            $("#gn"+groupName).find("ul").length > 0 &&
            $("#gn"+groupName).find("ul").children("li").length > 0) return;

    var html = "";
    for (var i = 0; i < members.length; i++){
        html += "<li onclick='selectUser(this);' style='width:100px; margin-right:15px; float:left;' value='"+members[i]._id+"'>"+members[i].name+"</li>";
    }

    if (groupName == "") ;
    else $("#gn"+groupName).find("ul").html(html);
}

/* 获取当前选中的分组名称 */
function getCurrentGroupName(){
    return $(".ui-corner-top").html().substr($(".ui-corner-top").html().lastIndexOf("</span>")+7)
}

/* 获取用户列表，产生select共添加用户至分组操作使用  */
function InitUserList(){
    $.ajax({
        type:"get",
        url:"/rest/buser/all",
        success:function(data){
            var html = "";
            for (var i = 0; i < data.body.length; i ++){
                html += "<option value='"+data.body[i]._id+"'>"+data.body[i].name+"</option>";
            }
            $("#add-user-name").html(html);
        }
    });
}

/* 从分组中移除一个用户 */
function removeAUser(){

    if (lastSelectedItem == null) {alert("请先选择一个用户！");return false;}

    else {
        var cg = getCurrentGroupName();
        var oh = $(lastSelectedItem).attr("value");

        var name = $(lastSelectedItem).html().replace("√","").trim();

        if (oh && name && cg &&
                oh.length > 0 &&
                name.length > 0 &&
                cg.length >0 &&
                confirm("确认要从分组"+cg+"中删除用户"+name+"?")){
            //alert(oh);
            $.ajax({
                type:"POST",
                url:"/rest/baccount/dfgroup?uname="+oh+"&group="+cg,
                success:function(data){
                    if (data.code == 200){
                        alert("已成功移除用户"+name);
                    }else{
                        alert("操作失败，"+data.msg);
                    }

                    loadThisGroup(cg,true);
                }
            });
        }
    }
}

/* 删除当前分组 */
function deleteCurrentGroup(){
    var cg = getCurrentGroupName();
    if (!cg) { alert("请先展开一个分组"); return false;}
    if (cg.length > 0 && confirm("确认删除分组"+cg+"?")){
        $.ajax({
            type:"POST",
            url:"/rest/bgroup/del?group="+cg,
            success:function(data){
                if (data.code == 200) alert("已成功删除分组"+cg);
                else alert(data.msg);

                window.location.reload();
            }
        });
    }
}

/* 向当前分组中加入用户 */
function addAUser(){
    var cg = getCurrentGroupName();
    var t = "<div class='widget-header widget-header-small'><h5 class='smaller'><i class='icon-ok'></i> 向分组\""+cg+"\"中添加一个用户</h5></div>";
    //alert(cg);

    if ($("#add-user-name").children("option").length == 0){
        InitUserList();
    }

    var dialog = $("#dialog-message-add-user").removeClass('hide').dialog({
        modal:true,
        title: t,
        title_html:true,
        buttons:[{
            text:"取消",
            "class" : "btn btn-xs",
            click: function() {
                $("#add-user-name").val("");
                $( this ).dialog( "close" );
            }
        },
            {
                text: "确定",
                "class" : "btn btn-primary btn-xs",
                click: function() {
                    $( this ).dialog( "close" );

                    var uname = $("#add-user-name").val();
                    if (!uname || uname == "") return;

                    //alert($("#add-user-name").val());
                    $.ajax({
                        type:"POST",
                        url:"/rest/baccount/atgroup?uname="+uname+"&group="+cg,
                        async:true,
                        success:function(jsn){
                            if (jsn.code == 200){
                                alert("已将用户"+jsn.body+"添加至"+cg);
                                loadThisGroup(cg,true);
                                loadThisGroup(jsn.msg,true);
                            }else{
                                alert(jsn.msg);
                            }
                        }
                    });
                    $("#add-user-name").val("");
                }
            }]
    });
}

/* 添加分组 */
function addAGroup(){
    var dialog = $("#dialog-message-add-group").removeClass('hide').dialog({
        modal: true,
        title: "<div class='widget-header widget-header-small'><h5 class='smaller'><i class='icon-ok'></i> 添加一个分组 </h5></div>",
        title_html: true,
        buttons: [
            {
                text: "取消",
                "class" : "btn btn-xs",
                click: function() {
                    $( this ).dialog( "close" );
                }
            },
            {
                text: "确定",
                "class" : "btn btn-primary btn-xs",
                click: function() {
                    $( this ).dialog( "close" );
                    //alert($("#add-group-assoc").val());
                    //alert($("#add-group-name").val());
                    var gname = $("#add-group-name").val();
                    if (gname != null && gname.length > 0) {
                        var group = {};
                        group.name = gname;
                    <#if isSuper>
                        group.associationId = $("#add-group-assoc").val();
                    </#if>

                        $.ajax({
                        url:"/rest/association/group"<#if isSuper>+"?id="+$("#add-group-assoc").val()</#if>,
                            type:"POST",
                            data:JSON.stringify(group),
                            contentType : "application/json; charset=utf-8",
                            dataType : 'json',
                            success: function(jsn){
                                if (jsn.code == 200){
                                    window.location.reload();
                                }else{
                                    alert(jsn.msg);
                                }
                            }
                        })
                    }
                }
            }
        ]
    });

<#if !isSuper>
    $("#add-group-assoc").parent().parent().css("display","none");
</#if>

    return false;
}
/*
jQuery(function($) {
    var raw_data_test = [
        {_id:1,associationId:"sh",name:"测试1",type:"读书会",tag:"OFFICIAL",description:"测试内容1",beginDate:"20140203T12:13:14.443GMT0+800"},
        {_id:2,associationId:"bj",name:"测试2",type:"运动",tag:"RECOMMEND",description:"测试内容2",beginDate:"20140203T12:13:14.443GMT0+800"}
    ];

    /* init page data !*-/
    var raw_data = ${groupList};

    var grid_data = parseActData(raw_data);

    var grid_selector = "#grid-table";
    var pager_selector = "#grid-pager";

    jQuery(grid_selector).jqGrid({
        data: grid_data,
        datatype: "local",
        height: 330,
        colNames:['_id','所属协会','分组名称'],
        colModel:[
            {name:"_id",index:"_id",width:10,editable:false,hidden:true},
            {name:"associationId",index:"associationId",width:40,editable:false<#if isSuper><#else>,hidden:true</#if>},
            {name:"name",index:"name",width:"100",editable:false}
        ],
        viewrecords : true,
        rowNum: 10,
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

        caption: "分组",
        autowidth: true
    });

    //override dialog's title function to allow for HTML titles
    $.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
        _title: function(title) {
            var $title = this.options.title || '&nbsp;'
            if( ("title_html" in this.options) && this.options.title_html == true )
                title.html($title);
            else title.text($title);
        }
    }));

    jQuery(grid_selector).jqGrid('navGrid',pager_selector,
            { 	//navbar options
                add: true,
                addicon : 'icon-plus-sign purple',
                addfunc : (function(){
                    var dialog = $("#dialog-message-add-group").removeClass('hide').dialog({
                        modal: true,
                        title: "<div class='widget-header widget-header-small'><h5 class='smaller'><i class='icon-ok'></i> 添加一个分组 </h5></div>",
                        title_html: true,
                        buttons: [
                            {
                                text: "取消",
                                "class" : "btn btn-xs",
                                click: function() {
                                    $( this ).dialog( "close" );
                                }
                            },
                            {
                                text: "确定",
                                "class" : "btn btn-primary btn-xs",
                                click: function() {
                                    $( this ).dialog( "close" );
                                    //alert($("#add-group-assoc").val());
                                    //alert($("#add-group-name").val());
                                    var gname = $("#add-group-name").val();
                                    if (gname != null && gname.length > 0) {
                                        var group = {};
                                        group.name = gname;
                                        <#if isSuper>
                                            group.associationId = $("#add-group-assoc").val();
                                        </#if>

                                        $.ajax({
                                            url:"/rest/association/group"<#if isSuper>+"?id="+$("#add-group-assoc").val()</#if>,
                                            type:"POST",
									        data:JSON.stringify(group),
									        contentType : "application/json; charset=utf-8",
									        dataType : 'json',
									        success: function(jsn){
									        	if (jsn.code == 200){
									        		window.location.reload();
									        	}else{
									        		alert(jsn.msg);
									        	}
									        }
                                        })
                                    }
                                }
                            }
                        ]
                    });

                <#if !isSuper>
                    $("#add-group-assoc").parent().parent().css("display","none");
                </#if>

                    return false;
                }),

                edit: true,
                editicon : 'icon-pencil blue',
                editfunc : (function(){
                    var id = $("#grid-table").getGridParam("selrow");
                    id=$("#grid-table > tbody > tr").eq(id).find("td").eq(1).attr("title");
                    window.location.href = '/rest/bactivity/edit?actid='+id;
                }),

                del: true,
                delicon : 'icon-trash red',
                delfunc : (function(){/*alert("删除操作!");-/
                    if (!confirm("确认删除选中的活动?")){
                        return false;
                    }

                    var id = $("#grid-table").getGridParam("selrow");
                    id=$("#grid-table > tbody > tr").eq(id).find("td").eq(1).attr("title");

                    $.ajax({
                        url:"/rest/bactivity/del?actid="+id,
                        type:"POST",
                        success:function(data){
                            alert(data.msg);
                            window.location.reload();
                        }
                    });
                    return false;
                }),

                search: false,
                searchicon : 'icon-search orange',
                searchfunc: (function(){alert("s");return false;}),

                refresh: false,

                view: false,
                viewicon : 'icon-zoom-in grey',
                viewfunc: (function(){alert("预览操作!");return false;})
            }
    );

    $(".ui-jqgrid-htable").css("font-family","微软雅黑");
});*/

jQuery(function ($) {
    $('[data-rel=tooltip]').tooltip({container: 'body'});
    $('[data-rel=popover]').popover({container: 'body'});

    //$('textarea[class*=autosize]').autosize({append: "\n"});
    //$.mask.definitions['~'] = '[+-]';

    $('.date-picker').datepicker({autoclose: true}).next().on(ace.click_event, function () {
        $(this).prev().focus();
    });
    /*$('input[name=date-range-picker]').daterangepicker().prev().on(ace.click_event, function () {
        $(this).next().focus();
    });*/

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
