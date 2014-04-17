<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
    <title>接力 资讯管理</title>
    <meta name="description" content="接力"/>
    <!-- basic styles -->

    <link href="/assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="/assets/css/font-awesome.min.css"/>

    <!--[if IE 7]>
    <link rel="stylesheet" href="/assets/css/font-awesome-ie7.min.css"/>
    <![endif]-->

    <!-- page specific plugin styles -->

    <link rel="stylesheet" href="/assets/css/jquery-ui-1.10.3.full.min.css"/>
    <link rel="stylesheet" href="/assets/css/colorbox.css"/>

    <link rel="stylesheet" href="/assets/css/jquery.gritter.css" />

    <link rel="stylesheet" href="/assets/css/bootstrap-multiselect.css" type="text/css"/>

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
                <a href="#"> 资讯管理 </a>
            </li>

            <li class="active"> 发布资讯</li>
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
                在这里发布一篇新闻、资讯或者企业动态
                <small>
                    <i class="icon-double-angle-right"></i>
                    上传图片后文本框内会产生&lt;img src='...'&gt;标签，您可以通过移动该标签调整图片位置
                </small>
            </h1>
        </div>
        <!-- /.page-header -->

        <div class="row">
            <div class="col-xs-12">
                <!-- PAGE CONTENT BEGINS -->

                <form class="form-horizontal" role="form">

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-title"> 资讯标题 </label>

                        <div class="col-sm-9">
                            <input type="text" id="form-field-title" placeholder="标题" class="col-xs-10 col-sm-7"
                                   style="padding-left: 7px;"/>
                        </div>
                    </div>

                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-select-type"> 资讯类型 </label>

                        <div class="col-sm-9">
                            <select class="col-xs-10 col-sm-7" id="form-field-select-type"
                                    style="padding: 5px 4px;font-size: 14px;">
                                <#if isSuper>
                                    <option value="news" selected>新闻</option>
                                    <option value="association">资讯</option>
                                    <option value="enterprise">企业动态</option>
                                <#else>
                                    <option value="association" selected>资讯</option>
                                    <option value="enterprise">企业动态</option>
                                </#if>
                            </select>
                        </div>
                    </div>

                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-textarea"> 资讯正文 </label>

                        <div class="col-sm-9">
                            <textarea id="form-field-textarea" class="autosize-transition col-xs-10 col-sm-7"
                                      style="min-height: 140px;"></textarea>
                        </div>

                    </div>

                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-input-readonly"> 资讯图片 </label>

                        <div class="col-sm-9">
                            <!--<button class="btn btn-info"> 上传标题图 </button>-->

                            <!--<br/>-->

                            <!--<img src="/assets/images/gallery/image-4.jpg" style="max-width: 400px;"/> -->
                            <div class="row-fluid">
                                <ul class="ace-thumbnails" id="upload-img-list">
                                    <li id="img-list-invisible" style="border-width:0;display: block">暂无图片</li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <div class="space-4"></div>

                    <div class="form-group <#if isSuper==false>hidden</#if>">
                        <label class="col-sm-3 control-label no-padding-right" for="form-input-readonly"> 选择协会 </label>

                        <div class="col-sm-9">
                            <select id="selectAssociationIds" multiple="multiple" class="multiselect">${assIdOptionList}</select>
                        </div>
                    </div>

                    <div class="space-4"></div>

                </form>

                <div class="clearfix form-actions">
                    <div class="col-md-offset-3 col-md-9">
                        <button class="btn btn-success btn-purple" id="bootbox-upload-image"
                                style="font-weight:bold">
                            <i class="icon-cloud-upload bigger-110"></i>
                            上传图片
                        </button>

                        &nbsp; &nbsp; &nbsp;
                        <button class="btn btn-success" type="button" style="font-weight:bold">
                            <i class="icon-question bigger-110"></i>
                            预览
                        </button>

                        &nbsp; &nbsp; &nbsp;
                        <button class="btn btn-info" type="button" style="font-weight:bold" onclick="postThisArticle()">
                            <i class="icon-ok bigger-110"></i>
                            完成
                        </button>

                        &nbsp; &nbsp; &nbsp;
                        <button class="btn" type="reset" style="font-weight:bold" onclick="clearImgList();return true;">
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

<!-- inline scripts related to this page -->
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
            $("#sidebar-shortcuts-navlist").load("/sidebar_super.html",function(){$("#nav_list_2_2").addClass("active open");$("#nav_list_2").addClass("active");});
        <#else>
            $("#sidebar-shortcuts-navlist").load("/sidebar_admin.html",function(){$("#nav_list_2_2").addClass("active open");$("#nav_list_2").addClass("active");});
        </#if>
        /*$("#sidebar").load("/sidebar_super.html");$("#nav_list_2_2").addClass("active open");$("#nav_list_2").addClass("active");*/

        $('#selectAssociationIds').multiselect({
            numberDisplayed:10,
            buttonClass: 'btn-link btn ',
            selectAllText: '全选',
            selectAllValue: '全部',
            nonSelectedText: '请选择',
            nSelectedText: ' 被选中了',
            maxHeight:400
        });


        $("#bootbox-upload-image").on("click", function () {
            var spin_img = "<div id='upload-loading-img' style='margin-left:30px;margin-top:10px;display: none;'><i class='icon-spinner icon-spin orange bigger-125'></i></div>";
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

                                        uploadImgSrc = "<center><img src='" + uploadImgSrc + "'></center>";
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
                                        var re = new RegExp("\'","g");
                                        newImgHtml += "<a href='#' onclick='deletePic(\""+uploadImgSrc.replace(re,"")+"\")'><i class='icon-remove red'></i></a></div></li>";

                                        /*$("#upload-img-list > li").eq(0).after(newImgHtml);*/
                                        $("#upload-img-list > li").last().before(newImgHtml);

                                        $("#img-list-invisible").attr("style","border-width:0;display:none");

                                        $('.ace-thumbnails [data-rel="colorbox"]').colorbox(colorbox_params);
                                        $("#cboxLoadingGraphic").append("<i class='icon-spinner orange'></i>");//let's add a custom loading icon

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
        });

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
        $('[data-rel=tooltip]').tooltip({container: 'body'});
        $('[data-rel=popover]').popover({container: 'body'});

        //$('textarea[class*=autosize]').autosize({append: "\n"});
        //$.mask.definitions['~'] = '[+-]';

        $('.date-picker').datepicker({autoclose: true}).next().on(ace.click_event, function () {
            $(this).prev().focus();
        });
        $('input[name=date-range-picker]').daterangepicker().prev().on(ace.click_event, function () {
            $(this).next().focus();
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
