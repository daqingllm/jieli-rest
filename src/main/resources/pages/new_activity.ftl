<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
    <title>接力 活动管理</title>
    <meta name="description" content="接力"/>
    <!-- basic styles -->

    <link href="/assets/css/bootstrap.min.css" rel="stylesheet"/>
    <#--<link rel="stylesheet" href="/assets/css/font-awesome.min.css"/>-->

    <!--[if IE 7]>
    <!--<link rel="stylesheet" href="/assets/css/font-awesome-ie7.min.css"/>-->
    <#--<![endif]&ndash;&gt;-->

    <link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="/assets/css/bootstrap-multiselect.css" type="text/css"/>
    <link rel="stylesheet" href="/assets/css/custom.css"/>

    <link rel="stylesheet" href="/assets/css/jquery-ui-1.10.3.full.min.css"/>
    <link rel="stylesheet" href="/assets/css/colorbox.css"/>

    <link rel="stylesheet" href="/assets/css/jquery.gritter.css" />

    <link rel="stylesheet" href="/assets/css/datepicker.css" />
    <link rel="stylesheet" href="/assets/css/daterangepicker.css" />

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

    <style>
        .arrangement-detail{
            height: 31px;
            margin-top: 2px;
            margin-bottom: 2px;
        }
        .service-info{
            height: 31px;
            margin-top: 2px;
            margin-bottom: 2px;
        }
        .fa-times-bigger{
            margin: 7px 0px 0px 6px;
            font-size: 20px;
            cursor: pointer;
        }
        .fa-plus-bigger{
            margin: 7px 0px 0px 6px;
            font-size: 20px;
            cursor: pointer;
        }
    </style>
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
                    <i class="fa fa-leaf"></i>
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
            <a href="/app/bactivity/list"> 活动管理 </a>
        </li>

        <li class="active"> 新建活动</li>
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
        在这里新建一个<#if isSuper>推荐活动<#else>官方活动</#if>
    </h1>
</div>
<!-- /.page-header -->

<div class="row">
<div class="col-xs-12">
<!-- PAGE CONTENT BEGINS -->

<form class="form-horizontal" role="form">

<!--标题-->
<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-title"> 活动标题 </label>

    <div class="col-sm-9">
        <input type="text" id="form-field-title" placeholder="标题" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;"/>
    </div>
</div>
<div class="space-4"></div>

<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-imgurl"> 标题图 </label>

    <div class="col-sm-9">
        <div class="col-sm-9">
            <img width="150" height="150" src="" id="form-field-imgurl" style="display: none;float:left">
            <i id="delTitleImage" class="fa fa-times fa-times-bigger" onclick="deleteTitleImage()" style="display: none"></i>
        </div>
    </div>
</div>

<div class="space-4"></div>

<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for=""></label>

    <div class="col-sm-9" id="service-info">
        <!--<textarea id="form-field-textarea-service" class="autosize-transition col-xs-10 col-sm-7"
                  style="min-height: 140px;" placeholder="请用冒号和回车分隔服务信息"></textarea>-->
        <div><div class="btn btn-success btn-purple" id="uploadTitleImageClick" onclick="$('#bootbox-upload-image').click()">
            <i class="fa fa-cloud-upload bigger-110"></i>
            上传标题图片
        </div></div>
    </div>
</div>

<!--<#if isSuper>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-associations"> 协会范围 </label>

        <div class="col-sm-9">
            <select class="col-xs-10 col-sm-7" id="form-field-associations" style="padding: 5px 4px;font-size: 14px;">
            ${associationList}
            </select>
        </div>
    </div>
    <div class="space-4"></div>
</#if>-->

<div class="form-group <#if isSuper==false>hidden</#if>">
    <label class="col-sm-3 control-label no-padding-right" for="selectAssociationIds"> 选择协会 </label>

    <div class="col-sm-9">
        <select id="selectAssociationIds" multiple="multiple" class="multiselect">${associationList}</select>
    </div>
</div>

<div class="space-4"></div>

<!--时间-->
<!--<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-actDate"> 活动时间 </label>
    <div class="col-sm-3">
        <div class="input-group input-group-sm">
            <input type="text" id="form-field-actDate" class="form-control hasDatepicker"/>
            <span class="input-group-addon">
                <i class="fa fa-calendar"></i>
            </span>
        </div>
    </div>
</div>-->
<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-actDate"> 活动时间 </label>
    <div class="col-sm-3">
        <div class="input-group">
            <input type="text" id="form-field-actDate" class="form-control hasDatepicker"/>
                        		<span class="input-group-addon">
                        			<i class="fa fa-calendar"></i>
                        		</span>
        </div>
    </div>
</div>

<div class="space-4"></div>

<!--地点-->
<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-location"> 活动地点 </label>

    <div class="col-sm-9">
        <input type="text" id="form-field-location" placeholder="地点" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;"/>
    </div>
</div>
<div class="space-4"></div>

<!--类型-->
<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-type"> 活动类型 </label>

    <div class="col-sm-9">
        <select id="form-field-type" class="col-xs-10 col-sm-7" style="padding-left:3px;">
            <option value="下午茶" selected="selected">下午茶</option>
            <option value="论坛">论坛</option>
            <option value="学习讲座">学习讲座</option>
            <option value="游学">游学</option>
            <option value="酒会">酒会</option>
            <option value="运动">运动</option>
        </select>
    </div>
</div>

<div class="space-4"></div>
<div class="space-4"></div>

<!--简介-->
<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-textarea-description"> 活动简介 </label>

    <div class="col-sm-9">
        <textarea id="form-field-textarea-description" class="autosize-transition col-xs-10 col-sm-7"
                  style="min-height: 140px;"></textarea>
    </div>
</div>
<div class="space-4"></div>

<!--安排-->
<div class="form-group" style="display: none">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-textarea-arrangement"> 概要安排 </label>

    <div class="col-sm-9">
        <textarea id="form-field-textarea-arrangement" class="autosize-transition col-xs-10 col-sm-7"
                  style="min-height: 140px;"></textarea>
    </div>
</div>
<div class="space-4"></div>

<!--详细安排-->
<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="arrangement-details"> 日程安排 </label>

    <div id="arrangement-details" class="col-sm-9">
        <div>
            <div class="fa fa-plus fa-plus-bigger" id="icon-plus-ad"></div>
        </div>
    </div>
</div>
<div class="space-4"></div>
<div class="space-4"></div>

<!--服务-->
<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="ervice-info"> 服务指南 </label>

    <div class="col-sm-9" id="service-info">
        <!--<textarea id="form-field-textarea-service" class="autosize-transition col-xs-10 col-sm-7"
                  style="min-height: 140px;" placeholder="请用冒号和回车分隔服务信息"></textarea>-->
        <div><div class="fa fa-plus fa-plus-bigger" id="icon-plus-si"></div></div>
    </div>
</div>
<div class="space-4"></div>

<!--赞助-->
<div class="form-group" style="display: none">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-textarea-sponsor"> 赞助展示 </label>

    <div class="col-sm-9">
        <textarea id="form-field-textarea-sponsor" class="autosize-transition col-xs-10 col-sm-7"
                  style="min-height: 140px;"></textarea>
    </div>
</div>


<!-- 普通 赞助 -->
<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for=""> 普通赞助 </label>

    <div id="divSponsorInfo" class="col-sm-9">
        <div>
            <div class="fa fa-plus fa-plus-bigger icon-plus-sp"></div>
        </div>
    </div>
</div>

<div class="space-4"></div>

<!-- 钻石 赞助 -->
<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for=""> 钻石赞助 </label>

    <div id="divDiamondInfo" class="col-sm-9">
        <div>
            <div class="fa fa-plus fa-plus-bigger icon-plus-di"></div>
        </div>
    </div>
</div>

<div class="space-4"></div>

<div class="space-4"></div>

<!--截止-->
<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-dlDate"> 报名截止时间 </label>
    <div class="col-sm-3">
        <div class="input-group input-group-sm">
            <input type="text" id="form-field-dlDate" class="form-control hasDatepicker"/>
                        		<span class="input-group-addon">
                        			<i class="fa fa-calendar"></i>
                        		</span>
        </div>
    </div>
</div>
<div class="space-4"></div>

<!--费用-->
<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-fee"> 活动费用 </label>

    <div class="col-sm-9">
        <input type="text" id="form-field-fee" placeholder="费用" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;"/>
    </div>
</div>
<div class="space-4"></div>

<!--人数限制-->
<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-max"> 人数限制 </label>

    <div class="col-sm-9">
        <input type="text" id="form-field-max" placeholder="最大人数" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;"/>
    </div>
</div>
<div class="space-4"></div>

<div class="space-4"></div>

<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-checkbox"> 强推选项 </label>

    <div class="col-sm-9">
        <input style="margin-right: 25px;float: left;" type="checkbox" id="form-field-checkbox" >
        <div class="alert alert-info" style="float: left;padding: 2px 14px;"> 选择强推后，用户在锁屏状态下也能收到资讯通知 </div>
    </div>
</div>

<div class="space-4"></div>
</form>

<div class="clearfix form-actions">
    <div class="col-md-offset-3 col-md-9">
        <button class="btn btn-success btn-purple" id="bootbox-upload-image"
                style="font-weight:bold;display: none">
            <i class="fa fa-cloud-upload bigger-110"></i>
            上传标题图片
        </button>


        &nbsp; &nbsp; &nbsp;
        <button class="btn btn-info" type="button" style="font-weight:bold" onclick="<#if isSuper>finishActivity(0)<#else>finishActivity(1)</#if>;">
            <i class="fa fa-check bigger-110"></i>
            发布
        </button>

        &nbsp; &nbsp; &nbsp;
        <button class="btn" type="reset" style="font-weight:bold" onclick="deleteTitleImage();return true;">
            <i class="fa fa-undo bigger-110"></i>
            清空
        </button>

        &nbsp; &nbsp; &nbsp;
        <button class="btn btn-danger" type="reset" style="font-weight:bold" onclick="window.location.href='/app/bactivity/list';return true;">
            <i class="fa fa-mail-reply bigger-110"></i>
            返回活动列表
        </button>
    </div>
</div>
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

<!-- ace scripts -->

<script src="/assets/js/ace-elements.min.js"></script>
<script src="/assets/js/ace.min.js"></script>
<script src="/common-jieli.js"></script>

<!-- inline scripts related to this page -->
<script src="/assets/js/bootstrap-multiselect.js"></script>
<script>

    (function ($, undefined) {
        $.fn.getCursorPosition = function () {
            var el = $(this).get(0);
            var pos = 0;
            if ('selectionStart' in el) {
                pos = el.selectionStart;
            } else if ('selection' in document) {
                el.focus();
                var Sel = document.selection.createRange();
                var SelLength = document.selection.createRange().text.length;
                Sel.moveStart('character', -el.value.length);
                pos = Sel.text.length - SelLength;
            }
            return pos;
        }
    })(jQuery);
</script>

<script type="text/javascript">

    jQuery(function ($) {

    <#if isSuper>
        $("#sidebar-shortcuts-navlist").load("/sidebar_super.html",function(){$("#nav_list_3_2").addClass("active open");$("#nav_list_3").addClass("active");
            $("#nav_list_3_2 i").css({"position":"absolute","left":"10px","top":"11px","font-size":"12px","width":"18px","text-align":"center","color":"#c86139","display":"inline"});});
    <#else>
        $("#sidebar-shortcuts-navlist").load("/sidebar_admin.html",function(){$("#nav_list_3_2").addClass("active open");$("#nav_list_3").addClass("active");
            $("#nav_list_3_2 i").css({"position":"absolute","left":"10px","top":"11px","font-size":"12px","width":"18px","text-align":"center","color":"#c86139","display":"inline"});});
    </#if>

        $('#selectAssociationIds').multiselect({
            numberDisplayed:10,
            buttonClass: 'btn-link btn ',
            selectAllText: '全选',
            selectAllValue: '全部',
            nonSelectedText: '请选择',
            nSelectedText: ' 被选中了',
            maxHeight:400
        });

        $("#bootbox-upload-image").on("click", uploadImgBox);

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

    jQuery(function ($) {

        $('.date-picker').datepicker({autoclose: true}).next().on(ace.click_event, function () {
            $(this).prev().focus();
        });

    });
</script>
<script>


    jQuery(function($){
        $("#form-field-actDate").val(GetDate10(new Date(),0));
        addArrangementDetail();
        addServiceInfo();
        /*No1.  2014年5月7日22:04:35 初始化4种类型*/
        $(".service-info").last().children("input").eq(0).val("停车指引");
        $(".service-info").last().children("input").eq(0).attr("disabled",true);
        $(".service-info").last().children(".icon-remove").remove();

        addServiceInfo();
        $(".service-info").last().children("input").eq(0).val("住宿安排");
        $(".service-info").last().children("input").eq(0).attr("disabled",true);
        $(".service-info").last().children(".icon-remove").remove();

        addServiceInfo();
        $(".service-info").last().children("input").eq(0).val("其他信息");
        $(".service-info").last().children("input").eq(0).attr("disabled",true);
        $(".service-info").last().children(".icon-remove").remove();

        addServiceInfo();
        $(".service-info").last().children("input").eq(0).val("联系工作人员");
        $(".service-info").last().children("input").eq(0).attr("disabled",true);
        $(".service-info").last().children(".icon-remove").remove();
        /*No.1  */

        $('#icon-plus-ad').click(addArrangementDetail);
        $('#icon-plus-si').click(addServiceInfo);

        addSponsorOption();
        $('.icon-plus-sp').click(addSponsorOption);


        addDiamondOption();
        $('.icon-plus-di').click(addDiamondOption);


        /*$('input[name=date-range-picker]').daterangepicker({
                    format: 'YYYY-MM-DD'},
                function(start, end, label) {
                    //alert('A date range was chosen: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD'));
                    if (start.format('YYYY-MM-DD') == end.format('YYYY-MM-DD')){
                        $('input[name=date-range-picker]').val(start.format('YYYY-MM-DD'));
                    }else{
                        $('input[name=date-range-picker]').val(start.format('YYYY-MM-DD') + ' 至 ' + end.format('YYYY-MM-DD'));
                    }
                });
                */
        $('#form-field-dlDate').datepicker({autoclose:true,format: "yyyy-mm-dd"});
        $('#form-field-actDate').datepicker({autoclose:true,format: "yyyy-mm-dd"});
    });



</script>
</body>
</html>
