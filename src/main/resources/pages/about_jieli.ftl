<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
    <title>${associationName} 关于${associationName}</title>
    <meta name="description" content="接力"/>
    <!-- basic styles -->

    <link href="/assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="/assets/css/bootstrap-multiselect.css" type="text/css"/>
    <link rel="stylesheet" href="/assets/css/custom.css"/>

    <link rel="stylesheet" href="/assets/css/jquery-ui-1.10.3.full.min.css"/>
    <link rel="stylesheet" href="/assets/css/colorbox.css"/>

    <link rel="stylesheet" href="/assets/css/jquery.gritter.css" />

    <link rel="stylesheet" href="/assets/css/datepicker.css" />
    <link rel="stylesheet" href="/assets/css/daterangepicker.css" />
    <link rel="stylesheet" href="/assets/css/dropzone.css" />

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
                        <a href="#"> 关于协会 </a>
                    </li>

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
                        编辑关于${associationName}
                        <small>
                            <i class="fa fa-angle-double-right"></i>
                            请在下面的文本框中输入内容
                        </small>
                    </h1>

                </div>
                <!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->

                        <form class="form-horizontal" role="form">

                            <!--简介-->
                            <div class="form-group">
                                <#--<label class="col-sm-3 control-label no-padding-right" for="form-field-content"> 活动简介 </label>-->

                                <div class="col-sm-9">
                                    <textarea id="form-field-textarea" class="autosize-transition col-xs-10 col-sm-7"
                                              style="margin-top:30px;margin-left:100px;min-height: 140px;"></textarea>
                                </div>
                            </div>
                            <div class="space-4"></div>


                            <div class="form-group" style="display: none">
                                <label class="col-sm-1 control-label no-padding-right" for="form-input-readonly"> 图片 </label>

                                <div class="col-sm-9">
                                    <div class="row-fluid">
                                        <ul class="ace-thumbnails" id="upload-img-list">
                                            <li id="img-list-invisible" style="border-width:0;display: block"></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <div class="space-4"></div>

                            <div class="form-group" style="display:none;">
                                <label class="col-sm-3 control-label no-padding-right" for="">  </label>

                                <div class="col-sm-9">
                                    <div class="col-xs-10 col-sm-7 alert alert-warning" style="margin-bottom: 0">裁剪图片请到&nbsp;<a href="http://xiuxiu.web.meitu.com/main.html" target="_blank">http://xiuxiu.web.meitu.com/main.html</a></div>
                                </div>
                            </div>

                            <div class="space-4"></div>
                        </form>


                        <div class="form-group" style="display:none;">
                            <#--<label class="col-sm-3 control-label no-padding-right" for="form-input-readonly" style="text-align: right"> 上传图片 </label>-->

                            <div class="col-sm-9" style="padding:2px;">
                                <div id="dropzone" class="autosize-transition col-xs-10 col-sm-7"  style="margin-top:30px;margin-left:100px;min-height: 140px;">
                                    <form action="/app/upload" class="dropzone" id="adminUploaded" style="min-height: 180px;">
                                        <div class="fallback">
                                            <input name="file" type="file" multiple="" />
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>


                        <form class="form-horizontal" role="form">

                            <div class="space-4"></div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right" for="form-field-select-pro2">  </label>

                                <div class="col-sm-9">
                                    <div class="col-xs-10 col-sm-7" id="form-field-select-pro2" > &nbsp; </div>
                                </div>
                            </div>


                            <div class="space-4"></div>

                        </form>


                        <div id="dialog-message-preview" class="hide">
                        </div><!-- #dialog-message -->

                        <div class="clearfix form-actions" style="padding-left:320px;">

                            <button class="btn btn-success btn-purple" id="bootbox-upload-image"
                                    style="display:none;font-weight:bold;">
                                <i class="fa fa-cloud-upload bigger-110"></i>
                                上传标题图片
                            </button>

                            &nbsp; &nbsp; &nbsp;
                            <button class="btn btn-btn-success" type="button" style="font-weight:bold" onclick="previewJieLi();">
                                <i class="fa fa-check bigger-110"></i>
                                预览
                            </button>
                            &nbsp; &nbsp; &nbsp;

                            <button class="btn btn-info" type="button" style="font-weight:bold" onclick="postAboutJieli();">
                                <i class="fa fa-check bigger-110"></i>
                                完成
                            </button>

                            &nbsp; &nbsp; &nbsp;
                            <button class="btn" type="reset" style="display:none;font-weight:bold">
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
<script src="/assets/js/dropzone.min.js"></script>

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

    var textAreaId = "form-field-textarea";
    var imageTemp = [];
    var imagesUploadAdmin = [];
    var adminUploadedImagesId = "adminUploaded";

    function addUploadedImages(){
        if (textAreaId) {
            var otextarea = $("#" + textAreaId).val().trim();
            var start = 0;
            var end;
            var temp = 0;

            while((tmp = otextarea.indexOf(imageHead,start))>-1){
                start = tmp+imageHead.length;
                end = otextarea.indexOf(imageTail,start);
                var url = otextarea.substr(start,end-start);
                imageTemp.push(url);
            }
        }

        for (var ii = 0; ii < imageTemp.length; ii ++) {
            addAnImage(imageTemp[ii],adminUploadedImagesId);
        }
    }

    /* add img[url] to ImageArray-DivImageList-TextArea */
    function addAnImage(url,divId){
        var len = imagesUploadAdmin.length;
        var curImage = {"url":url,"position":(len+1)};
        imagesUploadAdmin.push(curImage);

        // 设置图片的名字
        var ImageDiv = "<div class=\"dz-preview dz-processing dz-image-preview\">"+
                "<div class=\"dz-details\">"+
                "<div class=\"dz-filename\"><span data-dz-name>[图片"+curImage.position+"]</span></div>"+
                "<img height=\"100\" width=\"100\" src=\""+curImage.url+"\" onclick=\"selectText(this)\" />"+
                "</div>" +
                "<a onclick=\"imagesUploadAdmin = CustomRemoveFile(\'"+divId+"\',imagesUploadAdmin,"+len+",\'"+textAreaId+"\');return false;\" class=\"dz-remove\" href=\"javascript:undefined;\">删除</a>" +
                "</div>";

        var jqImageDiv = $(ImageDiv);
        $("#"+divId).append(jqImageDiv);

        if (imagesUploadAdmin.length == 1) $("#"+divId).addClass("dz-started");

        // 更新textarea
        if (textAreaId) {
            var otextarea = $("#" + textAreaId).val().trim();
            otextarea = otextarea.replace(imageHead + curImage.url + imageTail, "[图片" + curImage.position + "]");
            $("#" + textAreaId).val(otextarea);
        }
    }


    $("#bootbox-upload-image").on("click", function () {
        var spin_img = "<div id='upload-loading-img' style='margin-left:30px;margin-top:10px;display: none;'><i class='fa fa-spinner icon-spin orange bigger-125'></i></div>";
        spin_img = "";
        bootbox.dialog({
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


    $.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
        _title: function(title) {
            var $title = this.options.title || '&nbsp;'
            if( ("title_html" in this.options) && this.options.title_html == true )
                title.html($title);
            else title.text($title);
        }
    }));

    function postAboutJieli(){
        var content = generateJieLiContent();
        if (!content || content.length == 0){
            alert("请填写内容！");return;
        }
//        var re = new RegExp("\"","g");
//        content = content.replace(re,"\u0022");
//        re = new RegExp("\'","g");
//        content = content.replace(re,"\u0027");
        $.ajax({
            url:"/app/sys/aboutJieli",
            type:"POST",
            data:content,
            contentType:"application/json; charset=utf-8",
            success:function(ret){
                if (ret.code == 200){
                    alert("已经更新关于上海市青企协的内容");
                    window.location.reload();
                }else{
                    alert(ret.body);
                    return;
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert("操作失败，错误码："+XMLHttpRequest.status);
            }
        });
    }

    jQuery(function ($) {

        $("#sidebar-shortcuts-navlist").load("/sidebar_admin.html",function(){updateByAID();$("#nav_list_11").addClass("active");});

        $('#selectAssociationIds').multiselect({
            numberDisplayed:10,
            buttonClass: 'btn-link btn ',
            selectAllText: '全选',
            selectAllValue: '全部',
            nonSelectedText: '请选择',
            nSelectedText: ' 被选中了',
            maxHeight:400
        });

    });

    jQuery(function($){
        var content = "${content}" || "";
        $("#form-field-textarea").val(content);

        try {
            $("#"+adminUploadedImagesId).dropzone({
                url:"/app/upload",
                paramName: "file", // The name that will be used to transfer the file
                maxFilesize: 1.5, // MB

                addRemoveLinks : true,
                dictDefaultMessage :
                        '<span class="bigger-150 bolder"> \
                        <span style="font-size:16px;font-family:Microsoft YaHei" class="grey">拖拽/点击上传（图片建议尺寸472像素*354像素）<br>您可通过移动文本框内[图片N]标签调整图片所在文本中的位置</span> <br /> \
                        <i class="upload-icon fa fa-cloud-upload blue icon-3x"></i>'
                ,
                dictResponseError: 'Error while uploading file!',

                //change the previewTemplate to use Bootstrap progress bars
                previewTemplate: "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-details\">\n    <div class=\"dz-filename\"><span data-dz-name></span></div>\n    <div style=\"display: none\" class=\"dz-size\" data-dz-size></div>\n    <img data-dz-thumbnail onclick=\"selectText(this)\" />\n  </div>\n  <div style=\"display: none\" class=\"progress progress-small progress-striped active\"><div class=\"progress-bar progress-bar-success\" data-dz-uploadprogress></div></div>\n  <div class=\"dz-success-mark\"><span></span></div>\n  <div class=\"dz-error-mark\"><span></span></div>\n  <div class=\"dz-error-message\"><span data-dz-errormessage></span></div>\n</div>",

                success: function(file,response){
                    //alert(response);
                    if (response.code == 200){
                        var len = imagesUploadAdmin.length;
                        var curImage = {"url":response.body,"position":(len+1)};
                        imagesUploadAdmin.push(curImage);

                        // 设置图片的名字
                        $(file.previewElement).find(".dz-filename").eq(0).children("span").html("[图片"+curImage.position+"]");

                        // 更新textarea
                        var pos = getTextAreaCursorPosition() || 0;
                        var otextarea = $("#"+textAreaId).val().trim();
                        var otextarea_head = pos > 0 ? otextarea.substring(0, pos) : "";
                        var otextarea_tail = otextarea.substring(pos);
                        $("#"+textAreaId).val(otextarea_head + "[图片"+curImage.position+"]" + otextarea_tail);
                    }
                },
                removedfile: function(file){
                    // file.previewElement 之前还有一个元素 dz-default dz-message
                    var idx = $(file.previewElement).index() - 1;
                    imagesUpload = CustomRemoveFile(adminUploadedImagesId,imagesUploadAdmin,idx,textAreaId);
                    if (file.previewElement) $(file.previewElement).remove();
                }
            });
            $("#"+adminUploadedImagesId).css("min-height","180px");
            $("#"+adminUploadedImagesId+" .dz-message").css("margin-top","-81px");

            addUploadedImages();
        } catch(e) {
            alert('Dropzone.js does not support older browsers!');
        }
    });
</script>
</body>
</html>
