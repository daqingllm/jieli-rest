<!DOCTYPE html>
    <html lang="zh">
            <head>
            <meta charset="utf-8"/>
            <title>接力 投票管理</title>
    <meta name="description" content="接力"/>
        <!-- basic styles -->

            <link href="/assets/css/bootstrap.min.css" rel="stylesheet"/>

    <link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">

            <!-- page specific plugin styles -->
                <link rel="stylesheet" href="/assets/css/custom.css"/>

                <link rel="stylesheet" href="/assets/css/jquery-ui-1.10.3.full.min.css"/>
                <link rel="stylesheet" href="/assets/css/colorbox.css"/>

                <link rel="stylesheet" href="/assets/css/jquery.gritter.css" />

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
                        <!--<li>
                            <a href="#">
                                <i class="fa fa-cog"></i>
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
                <a href="/app/bvote/list"> 投票管理 </a>
            </li>

            <li class="active">
            <#if newVote>
                发布投票
            <#else>
                <#if isEditable>编辑投票
                <#else>查看投票
                </#if>
            </#if>
            </li>
        </ul>
        <!-- .breadcrumb -->

        <div class="nav-search" id="nav-search" style="display: none">
            <form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="搜索 ..." class="nav-search-input"
                                           id="nav-search-input" autocomplete="off"/>
									<i class="fa fa-search nav-search-icon"></i>
								</span>
            </form>
        </div>
        <!-- #nav-search -->
    </div>

    <div class="page-content">
        <div class="page-header">
            <h1>
            <#if newVote>
                发布投票
            <#else>
                <#if isEditable>编辑投票
                <#else>查看投票
                </#if>
            </#if>
            </h1>
        </div>
        <!-- /.page-header -->

        <div class="row">
            <div class="col-xs-12">
                <!-- PAGE CONTENT BEGINS -->

                <form class="form-horizontal" role="form">

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-title"> 投票标题 </label>

                        <div class="col-sm-9">
                            <input type="text" id="form-field-title"
                                   class="col-xs-10 col-sm-7"
                                   style="padding-left: 7px;"/>
                        </div>
                    </div>

                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-select-type"> 投票类型 </label>

                        <div class="col-sm-9">
                            <select class="col-xs-10 col-sm-7" id="form-field-select-type"
                                    style="padding: 5px 4px;font-size: 14px;">
                                <option value="S" selected="selected">单项选择</option>
                                <option value="M">多项选择</option>
                            </select>
                        </div>
                    </div>

                    <div class="space-4"></div>

                    <div class="form-group" style="display: none">
                        <label class="col-sm-3 control-label no-padding-right" for="force-type1"> 是否强推 </label>

                        <div class="col-sm-9">
                            <select class="col-xs-10 col-sm-7" id="force-type1"
                                    style="padding: 5px 4px;font-size: 14px;">
                                <option value="Y" selected="selected">是</option>
                                <option value="N">否</option>
                            </select>
                        </div>
                    </div>

                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-textarea"> 截止时间 </label>
                        <div class="col-sm-3">

                            <div class="input-group input-group-sm">
                                <input type="text" id="form-field-date" class="form-control hasDatepicker"/>
													<span class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</span>
                            </div>
                        </div>
                    </div>
                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-textarea"> 投票正文 </label>

                        <div class="col-sm-9">
                            <textarea id="form-field-textarea" class="autosize-transition col-xs-10 col-sm-7 textarea-no-resize"
                                      style="min-height: 140px;"></textarea>
                        </div>

                    </div>

                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-input-readonly"> 正文图片(可选) </label>

                        <div class="col-sm-9">
                            <!--<button class="btn btn-info"> 上传标题图 </button>-->

                            <!--<br/>-->

                            <!--<img src="/assets/images/gallery/image-4.jpg" style="max-width: 400px;"/> -->
                            <div class="row-fluid">
                                <ul class="ace-thumbnails" id="upload-img-list">
                                    <li id="img-list-invisible" style="border-width:0;display: block">暂无图片</li>
                                </ul>
                            </div>
                            <#if newVote || isEditable>
                                <button class="btn btn-success btn-purple" id="bootbox-upload-image"
                                        style="float:left;font-weight:bold" onclick="$('#externUpload').click();return false;">
                                    <i class="icon-cloud-upload bigger-110"></i>
                                    上传图片
                                </button>
                                <div class="alert alert-info" style="float: left;padding: 2px 14px;margin-left: 15px;margin-top: 7px;"> 请上传572*364的图片 </div>
                            </#if>

                        </div>
                    </div>

                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-title"> 投票选项 </label>

                        <div id="vote-choices" class="col-sm-9">
                            <div>
                                <div class="icon-plus"></div>
                            </div>
                        </div>
                    </div>

                    <div class="form-group" style="display:none;">
                        <label class="col-sm-3 control-label no-padding-right" for="force-type"> 强推选项 </label>

                        <div class="col-sm-9">
                            <input style="margin-right: 25px;float: left;" type="checkbox" id="force-type" >
                            <div class="alert alert-info" style="float: left;padding: 2px 14px;"> 选择强推后，用户在锁屏状态下也能收到资讯通知 </div>
                        </div>
                    </div>

                    <div class="space-4"></div>

                </form>
                <!-- vote statistics begin -->

                <!-- vote statistics end -->
                <#if !newVote && !isEditable>
                <div class="table-responsive">
                    <table id="comment-table" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>id</th>
                            <th>评论人</th>
                            <th class="hidden-480">评论内容</th>

                            <th>
                                <i class="icon-time bigger-110 hidden-480"></i>
                                评论时间
                            </th>
                            <th class="hidden-480">状态</th>

                        </tr>
                        </thead>

                        <tbody>
                        <#list commentList as comment>
                        <tr>
                            <td>${comment_index}</td>
                            <td>${comment.commentUserInfo.name}</td>
                            <td class="hidden-480">${comment.content}</td>
                            <td>${comment.addTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                            <td class="hidden-480">
                                <span class="label label-sm label-success">正常</span>
                            </td>
                        </tr>
                        </#list>


                        </tbody>
                    </table>
                </div>
                </#if>
                <div class="clearfix form-actions">
                    <div class="col-md-offset-3 col-md-9">
                        <#if newVote>


                            &nbsp; &nbsp; &nbsp;
                            <button class="btn btn-success" type="button" style="display:none;font-weight:bold">
                                <i class="icon-question bigger-110"></i>
                                预览
                            </button>

                            &nbsp; &nbsp; &nbsp;
                            <button class="btn btn-info" type="button" style="font-weight:bold" onclick="postNewVote();">
                                <i class="icon-ok bigger-110"></i>
                                发布
                            </button>

                            &nbsp; &nbsp; &nbsp;
                            <button class="btn" type="reset" style="display:none;font-weight:bold" onclick="clearImgList();return true;">
                                <i class="icon-undo bigger-110"></i>
                                清空
                            </button>
							
							
                            &nbsp; &nbsp; &nbsp;
                            <button id="externUpload" class="btn" style="display:none;font-weight:bold" >
                                <i class="icon-undo bigger-110"></i>
                                Upload
                            </button>

                        <#else>
                        <#if isEditable>

                            &nbsp; &nbsp; &nbsp;
                        <button class="btn btn-info" type="button" style="font-weight:bold" onclick="postEditVote('${voteId}');">
                            <i class="icon-ok bigger-110"></i>
                            修改
                        </button>

                        &nbsp; &nbsp; &nbsp;
                        <button class="btn" type="reset" style="font-weight:bold" onclick="alert('not implemented');return true;">
                            <i class="icon-undo bigger-110"></i>
                            刪除
                        </button>
                        </#if>
                        </#if>
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

<script src="/assets/js/jquery-ui-1.10.3.full.min.js"></script>

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

<script src="/common-jieli.js"></script>

<!-- inline scripts related to this page -->
<script>
    function clearImgList(){
        $("#form-field-title").val("");
        $("#form-field-select-type").get(0).selectedIndex = 0;
        $("#form-field-textarea").val("");
        $("#img-list-invisible").nextAll().remove();
        $("#img-list-invisible").attr("style","border-width:0;display:block");
    }

    function deletePic2(ph){
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
        var spin_img = "<div id='upload-loading-img' style='margin-left:30px;margin-top:10px;display: none;'><i class='fa fa-spinner icon-spin orange bigger-125'></i></div>";
        spin_img = "";
        bootbox.dialog({
            //message: "<input type='file' id='upload-image-files' name='upload-image-files' >",
            message: "<form id='rest-upload-form' action='/app/upload' method='post' enctype='multipart/form-data' acceptcharset='UTF-8'>\n<input id='rest-upload-file' type='file' name='file' size='50' />"+spin_img+"</form>",
            buttons: {
                "upload": {
                    "label": "<i class='fa fa-check'></i> 上传 ",
                    "className": "btn-sm btn-success",
                    "callback": function () {

                        // show loading image first
                        //$("#upload-loading-img").attr("style","display:block");

                        //Example.show("great success");
                        // upload Image !
                        var d = new FormData(document.getElementById('rest-upload-form'));
                        $.ajax({
                            url: '/app/upload',
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
                                    var uploadImgSrc = jsn.body + "?imageView/2/h/576/w/432";

                                    uploadImgSrc = "<img-pLAcehOLDer" + uploadImgSrc + ">";
                                    var otextarea = $("#form-field-textarea").val().trim();
                                    var otextarea_head = "";
                                    var otextarea_tail;
                                    var pos = getTextAreaCursorPosition() || 0;
                                    if (pos > 1)
                                        otextarea_head = otextarea.substring(0, pos);
                                    otextarea_tail = otextarea.substring(pos);

                                    //alert(otextarea_head + "[+]" + otextarea_tail);

                                    $("#form-field-textarea").val(otextarea_head + uploadImgSrc + otextarea_tail);

                                    // 更新图片集
                                    var imgsrc=uploadImgSrc;
                                    var newImgHtml = "<li>";
                                    newImgHtml += "<a href='"+jsn.body+"' data-rel='colorbox'>";
                                    newImgHtml += "<img alt='150x150' width='150' height='150' src='"+jsn.body+"' />";
                                    newImgHtml += "</a>";
                                    newImgHtml += "<div class='tools tools-right' style='height:30px;'>";
                                    // must be " , ' no use
                                    newImgHtml += "<a href='#' onclick='deletePic(\""+uploadImgSrc+"\")'><i class='fa fa-times red'></i></a></div></li>";
                                    clearImgList();
                                    $("#upload-img-list > li").eq(0).after(newImgHtml);

                                    $("#img-list-invisible").attr("style","border-width:0;display:none");

                                    $('.ace-thumbnails [data-rel="colorbox"]').colorbox(colorbox_params);
                                    $("#cboxLoadingGraphic").append("<i class='fa fa-spinner orange'></i>");//let's add a custom loading icon

                                } else {
                                    alert("上传失败！");
                                }
                            }
                        });

                        //$("#upload-loading-img").attr("style","display: none");
                    }
                    /*"callback": function () {
                        $('#rest-upload-form').ajaxSubmit(uploadArticleImageOptions);
                    }*/
                },
                "cancel": {
                    "label": "<i class='fa fa-times'></i> 取消",
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
    $("#sidebar-shortcuts-navlist").load("/sidebar_super.html",function(){
        $("#nav_list_4").addClass("active");});
<#else>
    $("#sidebar-shortcuts-navlist").load("/sidebar_admin.html",function(){
            <#if newVote>$("#nav_list_4_1")<#elseif isEditable>$("#nav_list_4_3")<#else>$("#nav_list_4_2")</#if>.addClass("active open");
        $("#nav_list_4").addClass("active");});
</#if>

	$("#externUpload").on("click", function () {
            var spin_img = "<div id='upload-loading-img' style='margin-left:30px;margin-top:10px;display: none;'><i class='fa fa-spinner icon-spin orange bigger-125'></i></div>";
            spin_img = "";
            bootbox.dialog({
                //message: "<input type='file' id='upload-image-files' name='upload-image-files' >",
                message: "<form id='rest-upload-form' method='post' enctype='multipart/form-data' acceptcharset='UTF-8'>\n<input id='rest-upload-file' type='file' name='file' size='50' />"+spin_img+"</form>",
                buttons: {
                    "upload": {
                        "label": "<i class='fa fa-check'></i> 上传 ",
                        "className": "btn-sm btn-success",
                        "callback": function () {
                            $('#rest-upload-form').ajaxSubmit(uploadArticleImageOptions);
                        }
                    },
                    "cancel": {
                        "label": "<i class='fa fa-times'></i> 取消",
                        "className": "btn-sm",
                        "callback": function () {
                            var objFile = document.getElementById('rest-upload-file');
                            objFile.outerHTML = objFile.outerHTML.replace(/(value=\").+\"/i, "$1\"");
                        }
                    }
                }
            });
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
    $('#spinner2').ace_spinner({value: 0, min: 0, max: 10000, step: 100, touch_spinner: true, icon_up: 'icon-caret-up', icon_down: 'fa fa-caret-down'});
    $('#spinner3').ace_spinner({value: 0, min: -100, max: 100, step: 10, on_sides: true, icon_up: 'icon-plus smaller-75', icon_down: 'icon-minus smaller-75', btn_up_class: 'btn-success', btn_down_class: 'btn-danger'});


    $('.date-picker').datepicker({autoclose: true}).next().on(ace.click_event, function () {
        $(this).prev().focus();
    });
    $('input[name=date-range-picker]').daterangepicker().prev().on(ace.click_event, function () {
        $(this).next().focus();
    });

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
<script type="application/javascript">
jQuery(function($){
    <#if newVote>
        newSettings();
    <#else>
        <#if isEditable>
            voteInfo('${voteId}', editSettings);
        <#else>
            voteInfo('${voteId}', viewSettings);
        </#if>
    </#if>

});

function voteOptionDeleteImg(ph){
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

function voteOptionUploadImg(voteOption) {
    var spin_img = "<div id='upload-loading-img' style='margin-left:30px;margin-top:10px;display: none;'><i class='fa fa-spinner icon-spin orange bigger-125'></i></div>";
    spin_img = "";
    bootbox.dialog({
        //message: "<input type='file' id='upload-image-files' name='upload-image-files' >",
        message: "<form id='rest-upload-form' action='/upload' method='post' enctype='multipart/form-data' acceptcharset='UTF-8'>\n<input id='rest-upload-file' type='file' name='file' size='50' />"+spin_img+"</form>",
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
                        url: '/app/upload',
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

                                var newImgHtml = "";

                                newImgHtml += "<img class='vote-img' width='150' height='150' src='"+jsn.body+"' />";

                                //newImgHtml += "<div class='tools tools-right' style='height:30px;'>";
                                // must be " , ' no use
                                //newImgHtml += "<a href='#' onclick='deletePic(\""+"\")'><i class='icon-remove red'></i></a></div></div>";
                                $(newImgHtml).insertAfter(voteOption.children('.icon-remove'));
                                voteOption.children('.vote-choice-img').hide();
                                voteOption.css({height : '200px'});

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
function deleteVoteOption() {
    if ($('.vote-choice').length > 1) {
        $(this).parent().remove();
    }
    else
        alert('每个投票至少要有一个选项');
}
function addVoteOption(value, percent) {
    if (value == undefined)
        value = "";
    var voteOption = $('<div class="vote-choice">' +
            '<input type="text" value="'+value+'" placeholder="选项内容，不填写为无效选项" class="col-xs-10 col-sm-6 vote-choice-text" style="padding-left: 7px;">' +
            <#if !newVote && !isEditable>
            '<div class="col-xs-5"><div class="progress" data-percent="'+percent+'%"><div class="progress-bar" style="width:'+percent+'%;"></div></div></div>' +
            </#if>
            '<button type="button" class="btn btn-xs btn-info vote-choice-img">Pic</button>' +
            '<div class="icon-remove"></div>' +
            '</div>');
    voteOption.insertBefore($('.icon-plus').parent());
    <#if newVote>
    voteOption.children('.icon-remove').click(deleteVoteOption);
    voteOption.children('.vote-choice-img').click(function() {
        voteOptionUploadImg(voteOption);
    });
    </#if>
}

function check(request) {
    if (request.title == '')
        return "必须编写投票标题";
    if (isNaN(request.deadLine.getDate()))
        return "必须设定投票截止时间";
    if (request.options.length < 1)
        return "有效选项至少为一个";
    return null;

}

function voteInfo(voteId, callback) {
    $.ajax({
        url: '/app/feature/vote/detail?voteId=' + voteId,
        type: 'GET',
        success: function (response) {
            if (response.code == 200) {
                $('#form-field-title').val(response.body.title);
                $('#form-field-textarea').val(response.body.description);
                var deadLine = new Date(response.body.deadLine);
                var dateString = deadLine.toISOString().substr(0, 10); //assume the program will not run after year 10000
                $('#form-field-date').val(dateString);
                $('#form-field-select-type').val(response.body.multiple ? 'M' : 'S');
                /*$('#force-type').val(response.body.force ? 'Y' : 'N');*/
                var options = response.body.options;
                $.ajax({
                    url: '/app/feature/vote/result?voteId=' + voteId,
                    type: 'GET',
                    success: function (response2) {
                        if (response2.code == 200) {
                            var totalVote = response2.body.totalVote;
                            var optionVotes = response2.body.optionVotes;
                            var optionPercent = {};

                            if (totalVote == 0) {
                                for (var index in optionVotes) {
                                    optionPercent[index] = 0;
                                }
                            }
                            else {
                                for (var index in optionVotes) {
                                    optionPercent[index] = Math.round(optionVotes[index] / totalVote * 100);
                                }
                            }
                            var voteOptionsNumber = 0;
                            for (var index in response.body.options) {
                                voteOptionsNumber++;
                            }
                            for (var index = 0; index < voteOptionsNumber; index++) {
                                addVoteOption(response.body.options[index.toString()], optionPercent[index.toString()]);
                            }
                            callback();
                        }
                    }
                });




            }
        }
    });
}

function newSettings() {
    enableDatePicker();
    $('.icon-plus').click(function (){ addVoteOption(); });
    $('.progress-bar').hide();
    addVoteOption();
    addVoteOption();
    addVoteOption();
}

function editSettings() {
    $('#form-field-select-type').attr({disabled : true});
    $('.vote-choice-img').hide();
    $('.icon-remove').remove();
    $('.icon-plus').hide();
    $('.vote-choice-text').attr({readOnly : true});
    $('.progress-bar').hide();
    enableDatePicker();
}

function viewSettings() {
    $('#form-field-select-type').attr({disabled : true});
    $('#force-type').attr({disabled : true});
    $('.vote-choice-img').hide();
    $('.icon-remove').remove();
    $('.icon-plus').hide();
    $('#form-field-title').attr({readOnly : true});
    $('#form-field-textarea').attr({readOnly : true});
    $('#form-field-date').attr({readOnly : true});
    $('.form-actions').hide();
    $('.vote-choice-text').attr({readOnly : true});
}

function enableDatePicker() {
    $( "#form-field-date" ).datepicker({
        showOtherMonths: true,
        selectOtherMonths: false,
        format: "yyyy-mm-dd",
        //isRTL:true,



        changeMonth: true,
        changeYear: true,

        showButtonPanel: true,
        beforeShow: function() {
            //change button colors
            var datepicker = $(this).datepicker( "widget" );
            setTimeout(function(){
                var buttons = datepicker.find('.ui-datepicker-buttonpane')
                        .find('button');
                buttons.eq(0).addClass('btn btn-xs');
                buttons.eq(1).addClass('btn btn-xs btn-success');
                buttons.wrapInner('<span class="bigger-110" />');
            }, 0);
        }

    });

}

function postNewVote() {
    var request = {};
    request.title = $('#form-field-title').val();
    request.multiple = $('#form-field-select-type').val() == 'M' ? true : false;
    //request.force = $('#force-type').val() == 'Y' ? true : false;
    request.force = $("#force-type").is(':checked');
    request.deadLine = new Date($('#form-field-date').val());
    request.description = $('#form-field-textarea').val();
    request.options = {};
    var index=0;
    $('.vote-choice-text').each(function() {
        if ($(this).val() != '')
            request.options[index++] = $(this).val();
    });

    var reqCheck = check(request);
    if (reqCheck != null) {
        alert(reqCheck);
        return;
    }
    $.ajax({
        url : '/app/feature/vote/addvote',
        data : JSON.stringify(request),
        type: 'POST',
        contentType: "application/json",
        success:function(json) {
            if(json.code == 200) {
                window.location.href = '/app/bvote/list';
            }
        }
    });
}
function postEditVote(voteId) {
    var request = {};
    request.title = $('#form-field-title').val();
    request.multiple = $('#form-field-select-type').val() == 'M' ? true : false;
    request.deadLine = new Date($('#form-field-date').val());
    request.description = $('#form-field-textarea').val();
    request.options = {};
    var index=0;
    $('.vote-choice-text').each(function() {
        if ($(this).val() != '')
            request.options[index++]=$(this).val();
    });

    var reqCheck = check(request);
    if (reqCheck != null) {
        alert(reqCheck);
        return;
    }
    $.ajax({
        url : '/app/feature/vote/modify?voteId='+voteId,
        data : JSON.stringify(request),
        type: 'POST',
        contentType: "application/json",
        success:function(json) {
            if(json.code == 200) {
                alert("投票修改成功");
                window.location.href = '/app/bvote/list';
            }
            else if(json.code == 1115) {
                alert("投票已过期，不可编辑！");
            }
            else {
                alert("出错啦！\n请联系万能的技术人员");
            }
        }
    });
}
</script>
</body>
</html>
