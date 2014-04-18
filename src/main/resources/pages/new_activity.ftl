<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
    <title>接力 活动管理</title>
    <meta name="description" content="接力"/>
    <!-- basic styles -->

    <link href="/assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="/assets/css/font-awesome.min.css"/>

    <!--[if IE 7]>
    <link rel="stylesheet" href="/assets/css/font-awesome-ie7.min.css"/>
    <![endif]-->

    <!-- page specific plugin styles -->
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
        .icon-remove{
            margin: 7px 0px 0px 6px;
            font-size: 20px;
            cursor: pointer;
        }
        .icon-plus{
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
                            <a href="#">
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
            <a href="#"> 活动管理 </a>
        </li>

        <li class="active"> 新建活动</li>
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

    <#if isSuper>
        <!--协会-->
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" for="form-field-associations"> 协会范围 </label>

            <div class="col-sm-9">
                <select class="col-xs-10 col-sm-7" id="form-field-associations" style="padding: 5px 4px;font-size: 14px;">
                ${associationList}
                </select>
            </div>
        </div>
        <div class="space-4"></div>
    </#if>

    <!--时间-->
    <!--<div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-actDate"> 活动时间 </label>
        <div class="col-sm-3">
            <div class="input-group input-group-sm">
                <input type="text" id="form-field-actDate" class="form-control hasDatepicker"/>
                <span class="input-group-addon">
                    <i class="icon-calendar"></i>
                </span>
            </div>
        </div>
    </div>-->
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-actDate"> 活动时间 </label>
        <div class="col-sm-3">
            <div class="input-group">
								<span class="input-group-addon">
									<i class="icon-calendar bigger-110"></i>
								</span>

                <input class="form-control" type="text" name="date-range-picker" id="form-field-actDate" />
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
                <div class="icon-plus"></div>
            </div>
        </div>
    </div>
    <div class="space-4"></div>
    <div class="space-4"></div>

    <!--服务-->
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-textarea-service"> 服务信息 </label>

        <div class="col-sm-9">
            <textarea id="form-field-textarea-service" class="autosize-transition col-xs-10 col-sm-7"
                      style="min-height: 140px;"></textarea>
        </div>
    </div>
    <div class="space-4"></div>

    <!--赞助-->
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-textarea-sponsor"> 赞助信息 </label>

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
                        			<i class="icon-calendar"></i>
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
                <i id="delTitleImage" class="icon-remove" onclick="deleteTitleImage()" style="display: none"></i>
            </div>
        </div>
    </div>


</form>

<div class="clearfix form-actions">
    <div class="col-md-offset-3 col-md-9">
        <button class="btn btn-success btn-purple" id="bootbox-upload-image"
                style="font-weight:bold">
            <i class="icon-cloud-upload bigger-110"></i>
            上传标题图片
        </button>

        &nbsp; &nbsp; &nbsp;
        <button class="btn btn-success" type="button" style="font-weight:bold">
            <i class="icon-question bigger-110"></i>
            预览
        </button>

        &nbsp; &nbsp; &nbsp;
        <button class="btn btn-info" type="button" style="font-weight:bold" onclick="finish();">
            <i class="icon-ok bigger-110"></i>
            完成
        </button>

        &nbsp; &nbsp; &nbsp;
        <button class="btn" type="reset" style="font-weight:bold" onclick="deleteTitleImage();return true;">
            <i class="icon-undo bigger-110"></i>
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

<!-- inline scripts related to this page -->
<script>
    function clearImgList(){
        $("#form-field-title").val("");
        $("#form-field-select-type").get(0).selectedIndex = 0;
        $("#form-field-textarea").val("");
        $("#img-list-invisible").nextAll().remove();
        $("#img-list-invisible").attr("style","border-width:0;display:block");
    }

    function deletePic(ph){
        var _src=ph.replace("<img-pLAcehOLDer","");
        _src = _src.substr(0,_src.length-1);

        // clear textarea
        var otextarea = $("#form-field-textarea").val().trim();
        otextarea = otextarea.replace(ph,"");
        $("#form-field-textarea").val(otextarea);

        // delete pic
        $("a[href=\'"+_src+"\']").parent().remove();

        if ($("#img-list-invisible").parent().children().length == 1)
            $("#img-list-invisible").attr("style","border-width:0;display:block");
    }

    function getTextAreaCursorPosition() {
        return $("#form-field-textarea").getCursorPosition();
    }
    function uploadImgBox() {
        var spin_img = "<div id='upload-loading-img' style='margin-left:30px;margin-top:10px;display: none;'><i class='icon-spinner icon-spin orange bigger-125'></i></div>";
        spin_img = "";
        bootbox.dialog({
            //message: "<input type='file' id='upload-image-files' name='upload-image-files' >",
            message: "<form id='rest-upload-form' action='/rest/upload' method='post' enctype='multipart/form-data' acceptcharset='UTF-8'>\n<input id='rest-upload-file' type='file' name='file' size='50' />"+spin_img+"</form>",
            buttons: {
                "upload": {
                    "label": "<i class='icon-ok'></i> 上传 ",
                    "className": "btn-sm btn-success",
                    "callback": function () {
                        // show loading image first
                        //$("#upload-loading-img").attr("style","display:block");

                        //Example.show("great success");
                        // upload Image !
                        var d = new FormData(document.getElementById('rest-upload-form'));
                        $.ajax({
                            url: '/rest/upload',
                            type: 'POST',
                            contentType: false,
                            data: d,
                            cache: false,
                            processData: false,
                            async: false,
                            success: function (jsn) {
                                //alert(jsn);
                                // untested , but it should be like : code:200,body:filepath,msg...

                                if (jsn.code == 200) {
                                    var uploadImgSrc = jsn.body + "";

                                    $("#form-field-imgurl").attr("src",uploadImgSrc);
                                    $("#form-field-imgurl").css("display","block");

                                } else {
                                    alert("上传失败！");
                                }
                            }
                        });

                        //$("#upload-loading-img").attr("style","display: none");
                    }
                },
                "cancel": {
                    "label": "<i class='icon-remove'></i> 取消",
                    "className": "btn-sm",
                    "callback": function () {
                        //Example.show("uh oh, look out!");
                        var objFile = document.getElementById('rest-upload-file');
                        objFile.outerHTML = objFile.outerHTML.replace(/(value=\").+\"/i, "$1\"");
                    }
                }
            }
        });
    }
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
    $("#sidebar-shortcuts-navlist").load("/sidebar_super.html",function(){$("#nav_list_3_2").addClass("active open");$("#nav_list_3").addClass("active");});
<#else>
    $("#sidebar-shortcuts-navlist").load("/sidebar_admin.html",function(){$("#nav_list_3_2").addClass("active open");$("#nav_list_3").addClass("active");});
</#if>

    $("#bootbox-upload-image").on("click", uploadImgBox);

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

jQuery(function ($) {
    $('#id-disable-check').on('click', function () {
        var inp = $('#form-input-readonly').get(0);
        if (inp.hasAttribute('disabled')) {
            inp.setAttribute('readonly', 'true');
            inp.removeAttribute('disabled');
            inp.value = "This text field is readonly!";
        }
        else {
            inp.setAttribute('disabled', 'disabled');
            inp.removeAttribute('readonly');
            inp.value = "This text field is disabled!";
        }
    });


    $(".chosen-select").chosen();
    $('#chosen-multiple-style').on('click', function (e) {
        var target = $(e.target).find('input[type=radio]');
        var which = parseInt(target.val());
        if (which == 2) $('#form-field-select-4').addClass('tag-input-style');
        else $('#form-field-select-4').removeClass('tag-input-style');
    });


    $('[data-rel=tooltip]').tooltip({container: 'body'});
    $('[data-rel=popover]').popover({container: 'body'});

    $('textarea[class*=autosize]').autosize({append: "\n"});
    $('textarea.limited').inputlimiter({
        remText: '%n character%s remaining...',
        limitText: 'max allowed : %n.'
    });

    $.mask.definitions['~'] = '[+-]';
    $('.input-mask-date').mask('99/99/9999');
    $('.input-mask-phone').mask('(999) 999-9999');
    $('.input-mask-eyescript').mask('~9.99 ~9.99 999');
    $(".input-mask-product").mask("a*-999-a999", {placeholder: " ", completed: function () {
        alert("You typed the following: " + this.val());
    }});


    $("#input-size-slider").css('width', '200px').slider({
        value: 1,
        range: "min",
        min: 1,
        max: 8,
        step: 1,
        slide: function (event, ui) {
            var sizing = ['', 'input-sm', 'input-lg', 'input-mini', 'input-small', 'input-medium', 'input-large', 'input-xlarge', 'input-xxlarge'];
            var val = parseInt(ui.value);
            $('#form-field-4').attr('class', sizing[val]).val('.' + sizing[val]);
        }
    });

    $("#input-span-slider").slider({
        value: 1,
        range: "min",
        min: 1,
        max: 12,
        step: 1,
        slide: function (event, ui) {
            var val = parseInt(ui.value);
            $('#form-field-5').attr('class', 'col-xs-' + val).val('.col-xs-' + val);
        }
    });


    $("#slider-range").css('height', '200px').slider({
        orientation: "vertical",
        range: true,
        min: 0,
        max: 100,
        values: [ 17, 67 ],
        slide: function (event, ui) {
            var val = ui.values[$(ui.handle).index() - 1] + "";

            if (!ui.handle.firstChild) {
                $(ui.handle).append("<div class='tooltip right in' style='display:none;left:16px;top:-6px;'><div class='tooltip-arrow'></div><div class='tooltip-inner'></div></div>");
            }
            $(ui.handle.firstChild).show().children().eq(1).text(val);
        }
    }).find('a').on('blur', function () {
        $(this.firstChild).hide();
    });

    $("#slider-range-max").slider({
        range: "max",
        min: 1,
        max: 10,
        value: 2
    });

    $("#eq > span").css({width: '90%', 'float': 'left', margin: '15px'}).each(function () {
        // read initial values from markup and remove that
        var value = parseInt($(this).text(), 10);
        $(this).empty().slider({
            value: value,
            range: "min",
            animate: true

        });
    });


    $('#id-input-file-1 , #id-input-file-2').ace_file_input({
        no_file: 'No File ...',
        btn_choose: 'Choose',
        btn_change: 'Change',
        droppable: false,
        onchange: null,
        thumbnail: false //| true | large
        //whitelist:'gif|png|jpg|jpeg'
        //blacklist:'exe|php'
        //onchange:''
        //
    });

    $('#id-input-file-3').ace_file_input({
        style: 'well',
        btn_choose: 'Drop files here or click to choose',
        btn_change: null,
        no_icon: 'icon-cloud-upload',
        droppable: true,
        thumbnail: 'small'//large | fit
        //,icon_remove:null//set null, to hide remove/reset button
        /**,before_change:function(files, dropped) {
						//Check an example below
						//or examples/file-upload.html
						return true;
					}*/
        /**,before_remove : function() {
						return true;
					}*/,
        preview_error: function (filename, error_code) {
            //name of the file that failed
            //error_code values
            //1 = 'FILE_LOAD_FAILED',
            //2 = 'IMAGE_LOAD_FAILED',
            //3 = 'THUMBNAIL_FAILED'
            //alert(error_code);
        }

    }).on('change', function () {
        //console.log($(this).data('ace_input_files'));
        //console.log($(this).data('ace_input_method'));
    });


    //dynamically change allowed formats by changing before_change callback function
    $('#id-file-format').removeAttr('checked').on('change', function () {
        var before_change
        var btn_choose
        var no_icon
        if (this.checked) {
            btn_choose = "Drop images here or click to choose";
            no_icon = "icon-picture";
            before_change = function (files, dropped) {
                var allowed_files = [];
                for (var i = 0; i < files.length; i++) {
                    var file = files[i];
                    if (typeof file === "string") {
                        //IE8 and browsers that don't support File Object
                        if (!(/\.(jpe?g|png|gif|bmp)$/i).test(file)) return false;
                    }
                    else {
                        var type = $.trim(file.type);
                        if (( type.length > 0 && !(/^image\/(jpe?g|png|gif|bmp)$/i).test(type) )
                                || ( type.length == 0 && !(/\.(jpe?g|png|gif|bmp)$/i).test(file.name) )//for android's default browser which gives an empty string for file.type
                                ) continue;//not an image so don't keep this file
                    }

                    allowed_files.push(file);
                }
                if (allowed_files.length == 0) return false;

                return allowed_files;
            }
        }
        else {
            btn_choose = "Drop files here or click to choose";
            no_icon = "icon-cloud-upload";
            before_change = function (files, dropped) {
                return files;
            }
        }
        var file_input = $('#id-input-file-3');
        file_input.ace_file_input('update_settings', {'before_change': before_change, 'btn_choose': btn_choose, 'no_icon': no_icon})
        file_input.ace_file_input('reset_input');
    });


    $('#spinner1').ace_spinner({value: 0, min: 0, max: 200, step: 10, btn_up_class: 'btn-info', btn_down_class: 'btn-info'})
            .on('change', function () {
                //alert(this.value)
            });
    $('#spinner2').ace_spinner({value: 0, min: 0, max: 10000, step: 100, touch_spinner: true, icon_up: 'icon-caret-up', icon_down: 'icon-caret-down'});
    $('#spinner3').ace_spinner({value: 0, min: -100, max: 100, step: 10, on_sides: true, icon_up: 'icon-plus smaller-75', icon_down: 'icon-minus smaller-75', btn_up_class: 'btn-success', btn_down_class: 'btn-danger'});


    $('.date-picker').datepicker({autoclose: true}).next().on(ace.click_event, function () {
        $(this).prev().focus();
    });
    /*$('input[name=date-range-picker]').daterangepicker().prev().on(ace.click_event, function () {
        $(this).next().focus();
    });*/

    $('#timepicker1').timepicker({
        minuteStep: 1,
        showSeconds: true,
        showMeridian: false
    }).next().on(ace.click_event, function () {
        $(this).prev().focus();
    });

    $('#colorpicker1').colorpicker();
    $('#simple-colorpicker-1').ace_colorpicker();


    $(".knob").knob();


    //we could just set the data-provide="tag" of the element inside HTML, but IE8 fails!
    var tag_input = $('#form-field-tags');
    if (!( /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase()))) {
        tag_input.tag(
                {
                    placeholder: tag_input.attr('placeholder'),
                    //enable typeahead by specifying the source array
                    source: ace.variable_US_STATES//defined in ace.js >> ace.enable_search_ahead
                }
        );
    }
    else {
        //display a textarea for old IE, because it doesn't support this plugin or another one I tried!
        tag_input.after('<textarea id="' + tag_input.attr('id') + '" name="' + tag_input.attr('name') + '" rows="3">' + tag_input.val() + '</textarea>').remove();
        //$('#form-field-tags').autosize({append: "\n"});
    }


    /////////
    $('#modal-form input[type=file]').ace_file_input({
        style: 'well',
        btn_choose: 'Drop files here or click to choose',
        btn_change: null,
        no_icon: 'icon-cloud-upload',
        droppable: true,
        thumbnail: 'large'
    })

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
<script>

    function deleteTitleImage(){
        $("#delTitleImage").css("display","none");
        $("#form-field-imgurl").attr("src","");
        $("#form-field-imgurl").css("display","none");
    }

    /*function deleteVoteOption() {
        if ($('.vote-choice').length > 1) {
            $(this).parent().remove();
        }
        else
            alert('每个投票至少要有一个选项');
    }*/
    function deleteArrangementDetail() {
        if ($(".arrangement-detail").length > 1) {
            $(this).parent().remove();
        }else if ($(".arrangement-detail").length == 1){
            $(".arrangement-detail").children("input").eq(1).val("");
            var bd = $("#form-field-actDate").val();
            if (bd && bd.substr(0,10).length==10) $(".arrangement-detail").children("input").eq(0).val(bd.substr(0,10)+" 上午");
            else $(".arrangement-detail").children("input").eq(0).val("");
        }
    }
    /*function addVoteOption() {
        var voteOption = $('<div class="vote-choice">' +
                '<input type="text"  placeholder="选项内容，不填写为无效选项" class="col-xs-10 col-sm-7 vote-choice-text" style="padding-left: 7px;">' +
                '<button type="button" class="btn btn-xs btn-info vote-choice-img">Pic</button>' +
                '<div class="icon-remove"></div>' +
                '</div>');
        voteOption.insertBefore($('.icon-plus').parent());
        voteOption.children('.icon-remove').click(deleteVoteOption);
        voteOption.children('.vote-choice-img').click(function() {
            voteOptionUploadImg(voteOption);
        });
    }*/
    function addArrangementDetail() {
        var beginDate =$("#form-field-actDate").val();
        if (!beginDate) {alert("请先输入活动时间！");return;}

        var lastArrangementDetail = $('.icon-plus').parent().prev().children().eq(0).val();
        if (lastArrangementDetail) lastArrangementDetail =  lastArrangementDetail.split(" ");
        else lastArrangementDetail = [GetDate10(beginDate,-1),'下午'];

        var arrangementDetail = $('<div class="arrangement-detail">' +
                '<input type="text"  placeholder="日程安排时间" class="col-xs-10 col-sm-2 arrangement-detail-time" style="padding-left: 7px;" >' +
                '<span style="padding:10px;float: left;"></span>' +
                '<input type="text"  placeholder="日程安排，不填即为此时间段无活动内容" class="col-xs-10 col-sm-5 arrangement-detail-content" style="padding-left: 7px;">' +
                '<div class="icon-remove"></div>' +
                '</div>');
        arrangementDetail.insertBefore($('.icon-plus').parent());
        arrangementDetail.children('.icon-remove').click(deleteArrangementDetail);
        if (lastArrangementDetail[1] == "上午"){
            arrangementDetail.children('.arrangement-detail-time').val(lastArrangementDetail[0]+" 下午");
        }else{
            var d = GetDate10(lastArrangementDetail[0],1)+" 上午";
            arrangementDetail.children('.arrangement-detail-time').val(d);
        }
    }
    function GetDate10(dateStr,offsetDay) {
        var datetime=new Date(dateStr);
        datetime.setTime(datetime.getTime()+86400000*offsetDay);

        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        var day = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();

        return year+"-"+month+"-"+day;
    }
    jQuery(function($){

        $('.icon-remove').click(deleteArrangementDetail);

        $("#form-field-actDate").val(GetDate10(new Date(),0));
        $('.icon-plus').click(addArrangementDetail);
        addArrangementDetail();

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
        $('input[name=date-range-picker]').datepicker({autoclose:true,format: "yyyy-mm-dd"});
        $('#form-field-dlDate').datepicker({autoclose:true});
    });

    function finish(){
        var act = {};
        act.title=$("#form-field-title").val();
        act.actDate=new Date($("#form-field-actDate").val());
        //act.actDate=null;
        act.location=$("#form-field-location").val();
        act.type=$("#form-field-type").val();
        act.description=$("#form-field-textarea-description").val();
        act.arrangement=$("#form-field-textarea-arrangement").val();
        act.details=[];

        $(".arrangement-detail").each(function() {
            if ($(this).children("input").eq(0).val() != ''
                    && $(this).children("input").eq(1).val() != ''){
                act.details.push({
                    "title":$(this).children("input").eq(0).val(),
                    "content":$(this).children("input").eq(1).val()
                });
            }
        });

        act.serviceInfo=$("#form-field-textarea-service").val();
        act.sponsorInfo=$("#form-field-textarea-sponsor").val();
        // dlDate is beginDate ......
        act.beginDate=new Date($("#form-field-dlDate").val());
        //act.beginDate=null;
        act.fee=$("#form-field-fee").val();
        act.maxMembers=$("#form-field-max").val();
        act.url=$("#form-field-imgurl").attr("src");

    <#if isSuper>
        act.associationId=$("#form-field-associations").val();
    <#else>
        act.associationId="";
    </#if>

        act.addTime=null;
        act.tag=null;
        act.followMembers=null;
        act.joinMembers=null;
        act.invitees=null;

        var chk = check(act);
        if (chk != null) {alert(chk);return;}

        $.ajax({
            type:"POST",
            url:"/rest/bactivity/add",
            async:false,
            data:JSON.stringify(act),
            contentType:"application/json",
                cache:false,
                processData:false
        });
    }

    function check(act) {
        if (act.title == '')
            return "必须填标题";
        if (act.location == '')
            return "必须填地点";
        if (act.type == '')
            return "必须填类型";
        if (act.description == '')
            return "必须填简介";
        if (act.fee == '')
            return "必须填费用";
        if (act.maxNumbers == '')
            return "必须填最大人数";

        if (isNaN(act.actDate.getDate()))
            return "必须设定活动时间";
        if (isNaN(act.beginDate.getDate()))
            return "必须设定截止时间";

        <#if isSuper>
            if ($("#form-field-associations").val() == '')
                return "必须选择至少一个活动";
        </#if>

        return null;

    }


</script>
</body>
</html>
