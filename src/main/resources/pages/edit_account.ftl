<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
    <title>${associationName} 账户管理</title>
    <meta name="description" content="接力"/>
    <!-- basic styles -->

    <link href="/assets/css/bootstrap.min.css" rel="stylesheet"/>

    <link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">

    <!-- page specific plugin styles -->

    <link rel="stylesheet" href="/assets/css/jquery-ui-1.10.3.full.min.css"/>
    <link rel="stylesheet" href="/assets/css/colorbox.css"/>

    <link rel="stylesheet" href="/assets/css/jquery.gritter.css" />
    <link rel="stylesheet" href="/assets/css/datepicker.css" />

    <link rel="stylesheet" href="/assets/css/bootstrap-multiselect.css" type="text/css"/>
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
            <a href="/app/bnews/list"> 账户管理 </a>
        </li>

        <li class="active"> 编辑账户 </li>
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
        编辑用户的姓名、性别、协会身份、电话
        <small>
            <i class="fa fa-angle-double-right"></i>
            其他信息只能查看
        </small>
    </h1>
</div>
<!-- /.page-header -->

<div class="row">
<div class="col-xs-12">
<!-- PAGE CONTENT BEGINS -->

<form class="form-horizontal" role="form">

<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-name"> 姓名 </label>

    <div class="col-sm-9">
        <input type="text" id="form-field-name" placeholder="姓名" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;" value=''/>
    </div>
</div>

<div class="space-4"></div>




<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for=""> 用户头像 </label>

    <div class="col-sm-9">
        <img id="form-field-userFace" src="" class="col-xs-10 col-sm-7"
             style="padding-left: 7px;"/>
    </div>
</div>

<div class="space-4"></div>



<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-select-sex"> 性别 </label>

    <div class="col-sm-9">
        <select class="col-xs-10 col-sm-7" id="form-field-select-sex"
                style="padding: 5px 4px;font-size: 14px;">
            <option value="0" selected="selected">男</option>
            <option value="1">女</option>
        </select>
    </div>
</div>


<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-identity"> 协会身份 </label>

    <div class="col-sm-9">
        <select class="col-xs-10 col-sm-7" id="form-field-identity"
                style="padding: 5px 4px;font-size: 14px;">
        ${identityOps}
        </select>
    </div>
</div>

<div class="space-4"></div>




<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-phone"> 电话 </label>

    <div class="col-sm-9">
        <input type="text" id="form-field-phone" placeholder="电话" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;" value=''/>
    </div>
</div>

<div class="space-4"></div>



<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-birthday"> 生日 </label>

    <div class="col-sm-9">
        <input readonly="readonly" type="text" id="form-field-birthday" placeholder="生日" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;" value=""/>
    </div>
</div>

<div class="space-4"></div>


<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-constellation"> 星座 </label>

    <div class="col-sm-9">
        <input readonly="readonly" type="text" id="form-field-constellation" placeholder="星座" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;" value=""/>
    </div>
</div>

<div class="space-4"></div>



<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-interests"> 兴趣 </label>

    <div class="col-sm-9">
        <input readonly="readonly" type="text" id="form-field-interests" placeholder="兴趣" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;" value=""/>
    </div>
</div>

<div class="space-4"></div>


<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-degree"> 教育程度 </label>

    <div class="col-sm-9">
        <input readonly="readonly" type="text" id="form-field-degree" placeholder="教育程度" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;" value=""/>
    </div>
</div>

<div class="space-4"></div>



<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-profession"> 专业行业 </label>

    <div class="col-sm-9">
        <input readonly="readonly" type="text" id="form-field-profession" placeholder="专业行业" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;" value=""/>
    </div>
</div>


<div class="space-4"></div>

<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-mail"> 邮箱 </label>

    <div class="col-sm-9">
        <input readonly="readonly" type="text" id="form-field-mail" placeholder="邮箱" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;" value=""/>
    </div>
</div>


<div class="space-4"></div>

<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-weixin"> 微信号 </label>

    <div class="col-sm-9">
        <input readonly="readonly" type="text" id="form-field-weixin" placeholder="微信号" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;" value=""/>
    </div>
</div>

<div class="space-4"></div>

<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-enterpriseName"> 公司名称 </label>

    <div class="col-sm-9">
        <input readonly="readonly" type="text" id="form-field-enterpriseName" placeholder="公司名称" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;" value=""/>
    </div>
</div>


<div class="space-4"></div>

<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-job"> 公司职务 </label>

    <div class="col-sm-9">
        <input readonly="readonly" type="text" id="form-field-job" placeholder="公司职务" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;" value=""/>
    </div>
</div>


<div class="space-4"></div>

<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-school"> 就读学校 </label>

    <div class="col-sm-9">
        <input readonly="readonly" type="text" id="form-field-school" placeholder="就读学校" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;" value=""/>
    </div>
</div>

<div class="space-4"></div>

<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-enterpriseWebsite"> 公司网址 </label>

    <div class="col-sm-9">
        <input readonly="readonly" type="text" id="form-field-enterpriseWebsite" placeholder="公司网址" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;" value=""/>
    </div>
</div>

<div class="space-4"></div>

<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-enterpriseFoundDate"> 公司成立年份 </label>

    <div class="col-sm-9">
        <input readonly="readonly" type="text" id="form-field-enterpriseFoundDate" placeholder="公司成立年份" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;" value=""/>
    </div>
</div>

<div class="space-4"></div>

<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-enterpriseDescription"> 公司简介 </label>

    <div class="col-sm-9">
        <input readonly="readonly" type="text" id="form-field-enterpriseDescription" placeholder="公司简介" class="col-xs-10 col-sm-7"
               style="padding-left: 7px;" value=""/>
    </div>
</div>

<div class="space-4"></div>


</form>

<div id="dialog-message-preview" class="hide">
</div><!-- #dialog-message -->

<div class="clearfix form-actions">
    <div class="col-md-offset-3 col-md-9">
        <button class="btn btn-info" type="button" style="font-weight:bold" onclick="updateUser()">
            <i class="fa fa-check bigger-110"></i>
            确认
        </button>

        &nbsp; &nbsp; &nbsp;
        <button class="btn btn-danger" type="reset" style="font-weight:bold" onclick="window.location.href='/app/baccount/list';return true;">
            <i class="fa fa-mail-reply bigger-110"></i>
            返回账户列表
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
<script src="/assets/js/jquery-ui-1.10.3.full.min.js"></script>
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
<script src="/assets/js/jqGrid/jquery.jqGrid.min.js"></script>
<script src="/assets/js/jqGrid/i18n/grid.locale-zh-art-cmt.js"></script>

<!-- ace scripts -->

<script src="/assets/js/ace-elements.min.js"></script>
<script src="/assets/js/ace.min.js"></script>

<script src="/common-jieli.js"></script>

<!-- inline scripts related to this page -->
<script>
    var data = null;
</script>
<script>
    function updateUser(){
        data["name"] = $("#form-field-name").val();
        data["sex"] = $("#form-field-select-sex").val();
        data["identity"] = $("#form-field-identi").val();
        data["phone"] = $("#form-field-phone").val();

        $.ajax({
            type:"POST",
            url:"/app/user/edit?userId="+data["_id"],
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            success:function(ret){
                if (ret.code == 200){
                    alert("用户修改成功！");
                    window.location.href = "/app/baccount/list";
                }else{
                    alert("用户修改失败 " + (ret.msg || ""));
                }
            },
            error:function(){
                ;
            }
        });
    }

    <#if got?length==0>
    try{data = ${user};

        $("#form-field-name").val(data["name"] || "");
        $("#form-field-userFace").attr("src",data["userFace"] || "");
        if (data["sex"] == 1) $("#form-field-select-sex").val("1");
        $("#form-field-identity").val(data["identity"] || "");
        $("#form-field-phone").val(data["phone"] || "");
        try {
            if (data["birthday"])  $("#form-field-birthday").val(data["birthday"].Format("yyyy年MM月dd日"));
        }catch (e){}
        $("#form-field-constellation").val(data["constellation"] || "");

        var intlist = "";
        for (var i in data["interests"])
            intlist += data["interests"][i] + ",";
        if (intlist.length > 0) intlist = intlist.substring(0,intlist.length-1);

        $("#form-field-interests").val(intlist);
        var degrees = ["EMBA","MBA","学士","硕士","博士","其他"];
        if (data["degree"]) $("#form-field-degree").val(degrees[data["degree"]]);

        $("#form-field-profession").val(data["profession"] || "");
        $("#form-field-mail").val(data["mail"] || "");
        $("#form-field-weixin").val(data["weixin"] || "");
        $("#form-field-enterpriseName").val(data["enterpriseName"] || "");
        $("#form-field-job").val(data["job"] || "");
        $("#form-field-school").val(data["school"] || "");
        $("#form-field-enterpriseWebsite").val(data["enterpriseWebsite"] || "");
        $("#form-field-enterpriseFoundDate").val(data["enterpriseFoundDate"] || "");
        $("#form-field-enterpriseDescription").val(data["enterpriseDescription"] || "");
    }
    catch (err){data = {};}
    <#else>
    if(confirm("${got}"));
    window.location.href = "/app/baccount/list";
    </#if>
</script>
<script type="text/javascript">

    jQuery(function ($) {
    <#if isSuper>
        $("#sidebar-shortcuts-navlist").load("/sidebar_super.html",function(){$("#nav_list_7").addClass("active");});
    <#else>
        $("#sidebar-shortcuts-navlist").load("/sidebar_admin.html",function(){$("#nav_list_7").addClass("active");});
    </#if>

    });

</script>
</body>
</html>
