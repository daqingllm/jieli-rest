<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
    <title>上海市青企协 活动管理</title>
    <meta name="description" content="接力"/>
    <!-- basic styles -->

    <link href="/assets/css/bootstrap.min.css" rel="stylesheet"/>

    <link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">

    <!-- page specific plugin styles -->

    <link rel="stylesheet" href="/assets/css/bootstrap-multiselect.css" type="text/css"/>
    <link rel="stylesheet" href="/assets/css/jquery-ui-1.10.3.full.min.css"/>
    <link rel="stylesheet" href="/assets/css/colorbox.css"/>

    <link rel="stylesheet" href="/assets/css/jquery.gritter.css" />

    <link rel="stylesheet" href="/assets/css/custom.css"/>
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
                            <a href="#"  onclick="document.cookie='u=;path=/';window.location.href='/app/baccount/login'">
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
            <a href="#"> 活动管理 </a>
        </li>

        <li class="active"> 查看活动</li>
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
        在这里查看<#if isSuper>推荐活动<#else>官方活动</#if>
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
        <input type="text" id="form-field-type" placeholder="类型" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;"/>
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
<div class="form-group">
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
<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-textarea-sponsor"> 赞助展示 </label>

    <div class="col-sm-9">
        <textarea id="form-field-textarea-sponsor" class="autosize-transition col-xs-10 col-sm-7"
                  style="min-height: 140px;"></textarea>
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
    <label class="col-sm-3 control-label no-padding-right" for="form-field-imgurl"> 标题图 </label>

    <div class="col-sm-9">
        <div class="col-sm-9">
            <img width="150" height="150" src="" id="form-field-imgurl" style="display: none;float:left">
            <i id="delTitleImage" class="fa fa-times fa-times-bigger" onclick="deleteTitleImage()" style="display: none"></i>
        </div>
    </div>
</div>


<div class="space-4"></div>

<div class="form-group" style="display: none" >
    <label class="col-sm-3 control-label no-padding-right" for="form-field-checkbox"> 强推选项 </label>

    <div class="col-sm-9">
        <input type="checkbox" id="form-field-checkbox" >
    </div>
</div>

<div class="space-4"></div>

<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-input-readonly"> 用户上传的活动图片 </label>

    <div class="col-sm-9">
        <div class="row-fluid">
            <ul class="ace-thumbnails" id="upload-img-list">
                <li id="img-list-invisible" style="border-width:0;display: block">暂无</li>
            </ul>
        </div>
    </div>
</div>
<div class="space-4"></div>

</form>

<div class="clearfix form-actions" style="display: none;">
    <div class="col-md-offset-3 col-md-9">
        <button class="btn btn-success btn-purple" id="bootbox-upload-image"
                style="font-weight:bold">
            <i class="fa fa-cloud-upload bigger-110"></i>
            上传标题图片
        </button>


        &nbsp; &nbsp; &nbsp;
        <button class="btn btn-info" type="button" style="font-weight:bold" onclick="return false;">
            <i class="fa fa-check bigger-110"></i>
            完成
        </button>

        &nbsp; &nbsp; &nbsp;
        <button class="btn" type="reset" style="font-weight:bold" onclick="return false;">
            <i class="fa fa-undo bigger-110"></i>
            清空
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
<script src="/assets/js/bootstrap-multiselect.js"></script>

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

<!-- ace scripts -->

<script src="/assets/js/ace-elements.min.js"></script>
<script src="/assets/js/ace.min.js"></script>

<script src="/common-jieli.js"></script>
<script src="/assets/js/bootstrap-multiselect.js"></script>

<!-- inline scripts related to this page -->
<script>
    var data = null;
</script>
<script>

    function loadThisActivity(){
    <#if got?length==0 || got=="old">
        data = ${act_data};

        $("option[value="+data["associationId"]+"]").attr("selected","selected");

        $("#form-field-title").val(data.title);
        if (data.actDate && data.actDate.length >= 10)
            $("#form-field-actDate").val(new Date(data.actDate.substr(0,10)).Format("yyyy-MM-dd"));
        else
            $("#form-field-actDate").val(GetDate10(new Date(),0));

        $("#form-field-location").val(data.location);
        $("#form-field-type").val(data.type);
        $("#form-field-textarea-description").val(data.description);
        $("#form-field-textarea-arrangement").val(data.arrangement);

        if (data.details && data.details.length > 0){
            for(var i=0;i<data.details.length;i++){
                var dtl = data.details[i];
                var arrangementDetail = $('<div class="arrangement-detail">' +
                        '<input type="text"  placeholder="日程安排时间" class="col-xs-10 col-sm-2 arrangement-detail-time" style="padding-left: 7px;" >' +
                        '<span style="padding:10px;float: left;"></span>' +
                        '<input type="text"  placeholder="日程安排，不填即为此时间段无活动内容" class="col-xs-10 col-sm-5 arrangement-detail-content" style="padding-left: 7px;" >' +
                        '<div class="fa fa-times fa-times-bigger"></div>' +
                        '</div>');
                arrangementDetail.insertBefore($('#icon-plus-ad').parent());

                arrangementDetail.children('.arrangement-detail-time').val(dtl.title);
                arrangementDetail.children('.arrangement-detail-content').val(dtl.content);
            }
        }else{
            addArrangementDetail();
        }

        if (data.serviceInfo){
            var count = 0;
            for ( var o in data.serviceInfo){
                count ++;
                //alert(o);
                //alert(data.serviceInfo[o]);
                var serviceInfo = $('<div class="service-info">' +
                        '<input type="text" placeholder="服务名称" class="col-xs-10 col-sm-2 service-info-name" style="padding-left: 7px;" >' +
                        '<span style="padding:10px;float: left;"></span>' +
                        '<input type="text" placeholder="服务内容" class="col-xs-10 col-sm-5 service-info-content" style="padding-left: 7px;">' +
                        '<div class="fa fa-times fa-times-bigger"></div>' +
                        '</div>');
                serviceInfo.insertBefore($('#icon-plus-si').parent());

                serviceInfo.children('.service-info-name').val(o);
                serviceInfo.children('.service-info-content').val(data.serviceInfo[o]);
            }
            if (count == 0){
                addServiceInfo();
            }
        }else{
            addServiceInfo();
        }

        var album = data.album;
        var album_len = 0;
        if (album){
            var uids = [];
            var unames = [];
            for (var o in album){
                uids.push(o);
            }
            $.ajax({
                type:"POST",
                url:"/app/user/load",
                data:JSON.stringify(uids),
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                success:function(ret){
                    for (var ii = 0; ii < ret.body.length; ii++){
                        unames.push(ret.body[ii].name);
                    }

                    var count = -1;
                    for (var o in album){
                        count ++;
                        for (var ii = 0; ii < album[o].length; ii ++) {
                            var uploadImgSrc = album[o][ii];
                            var newImgHtml = "<li>";
                            newImgHtml += "<a href='" + uploadImgSrc + "' data-rel='colorbox'>";
                            newImgHtml += "<img alt='150x150' width='150' height='150' src='" + uploadImgSrc + "' />";
                            newImgHtml += "<div class='text'><div class='inner'>"+unames[count]+"</div></div>";
                            newImgHtml += "</a>";
                            newImgHtml += "<div class='tools tools-right' style='height:30px;'>";
                            // must be " , ' no use
                            var re = new RegExp("\'", "g");
                            newImgHtml += "<a href='javascript:void(0);return false;'><i class='fa fa-times red'></i></a></div></li>";
                            $("#upload-img-list > li").last().before(newImgHtml);
                        }
                    }
                    window.scrollTo(0,0);
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    alert("载入活动图片失败，错误码："+XMLHttpRequest.status);
                }
            });
        }

        if (album_len == 0)
            $("#img-list-invisible").attr("style","border-width:0;display:none");
        //$("#form-field-textarea-service").val(data.serviceInfo);

        $("#form-field-textarea-sponsor").val(data.sponsorInfo);
        $("#form-field-dlDate").val(new Date(data.beginDate).Format("yyyy-MM-dd"));
        $("#form-field-fee").val(data.fee);
        $("#form-field-max").val(data.maxMembers);
        if (data.url && data.url.length > 0){
            $("#form-field-imgurl").attr("src",data.url);
            $("#form-field-imgurl").css("display","block");
            $("#delTitleImage").css("display","block");
        }
            <#if isSuper>$("form-field-associations").val(data.associationId);</#if>

    </#if>
    }

</script>

<script type="text/javascript">
    jQuery(function ($) {
    <#if isSuper>
        $("#sidebar-shortcuts-navlist").load("/sidebar_super.html",function(){$("#nav_list_3").addClass("active");});
    <#else>
        $("#sidebar-shortcuts-navlist").load("/sidebar_admin.html",function(){$("#nav_list_3").addClass("active");});
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

    $('#form-field-dlDate').datepicker({autoclose:true,format: "yyyy-mm-dd"});
    $('#form-field-actDate').datepicker({autoclose:true,format: "yyyy-mm-dd"});

    loadThisActivity();


</script>
</body>
</html>
