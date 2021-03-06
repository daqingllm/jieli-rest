<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8" />
    <title>上海市青企协 注册</title>
    <meta name="keywords" content="接力" />
    <meta name="description" content="接力" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <!-- basic styles -->

    <link href="/assets/css/bootstrap.min.css" rel="stylesheet" />

    <link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">

    <!-- page specific plugin styles -->

    <link rel="stylesheet" href="/assets/css/jquery-ui-1.10.3.custom.min.css" />
    <link rel="stylesheet" href="/assets/css/jquery.gritter.css" />
    <link rel="stylesheet" href="/assets/css/datepicker.css" />

    <!-- fonts -->

    <link rel="stylesheet" href="/assets/css/font-google.css" />

    <!-- ace styles -->

    <link rel="stylesheet" href="/assets/css/ace.min.css" />
    <link rel="stylesheet" href="/assets/css/ace-rtl.min.css" />

    <!-- ace styles -->

    <link rel="stylesheet" href="/assets/css/ace-skins.min.css" />

    <!--[if lte IE 8]>
    <link rel="stylesheet" href="/assets/css/ace-ie.min.css" />
    <![endif]-->

    <!-- inline styles related to this page -->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

    <!--[if lt IE 9]>
    <script src="/assets/js/html5shiv.js"></script>
    <script src="/assets/js/respond.min.js"></script>
    <![endif]-->

</head>

<body class="login-layout">
<div class="main-container">
    <div class="main-content">
        <div class="row">
            <div class="col-sm-10 col-sm-offset-1">
                <div class="login-container">
                    <div class="center" style="margin-top: 40px;margin-bottom: 20px;">
                        <h1><i class="fa fa-leaf green"></i><span class="red">${associationName}</span><span class="white">后台管理系统</span></h1>
                        <h4 class="blue">&copy; 蕊蕾文化传播</h4>
                    </div>

                    <div class="space-6"></div>

                    <div class="position-relative" style="font-family: '微软雅黑'">
                        <div id="login-box" class="login-box widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header blue lighter bigger"><i class="fa fa-coffee green"></i><!-- Please Enter Your Information --></h4>

                                    <div class="space-6"></div>

                                    <form id="form-login">
                                        <fieldset>
                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="用户名" id="login-username" />
															<i class="fa fa-user"></i> </span> </label>

                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="密码" id="login-password" />
															<i class="fa fa-lock"></i> </span> </label>

                                            <div class="space"></div>

                                            <div class="clearfix">
                                                <!--<label class="inline">
                                                <input type="checkbox" class="ace" />
                                                <span class="lbl"> 记住我</span> </label>-->

                                                <button type="button" class="width-35 pull-right btn btn-sm btn-primary" onclick="login()">
                                                    <i class="fa fa-key"></i>
                                                    登录
                                                </button>
                                            </div>

                                            <div class="space-4"></div>
                                        </fieldset>
                                    </form>

                                </div><!-- /widget-main -->

                                <div class="toolbar clearfix">
                                    <!--<div>
                                    <a href="#" onclick="show_box('forgot-box'); return false;" class="forgot-password-link">
                                    <i class="fa fa-arrow-left"></i>
                                    忘记密码
                                    </a>
                                    </div>

                                    <div>
                                    <a href="#" onclick="show_box('signup-box'); return false;" class="user-signup-link">
                                    注册账号
                                    <i class="fa fa-arrow-right"></i>
                                    </a>
                                    </div>-->
                                </div>
                            </div><!-- /widget-body -->
                        </div><!-- /login-box -->

                        <div id="forgot-box" class="forgot-box widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header red lighter bigger"><i class="fa fa-key"></i> 找回密码 </h4>

                                    <div class="space-6"></div>
                                    <p>
                                        请输入接收新密码的邮箱
                                    </p>

                                    <form>
                                        <fieldset>
                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="fa fa-envelope"></i> </span> </label>

                                            <div class="clearfix">
                                                <button type="button" class="width-35 pull-right btn btn-sm btn-danger">
                                                    <i class="fa fa-lightbulb-o"></i>
                                                    发送
                                                </button>
                                            </div>
                                        </fieldset>
                                    </form>
                                </div><!-- /widget-main -->

                                <div class="toolbar center">
                                    <a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
                                        回去登录
                                        <i class="fa fa-arrow-right"></i>
                                    </a>
                                </div>
                            </div><!-- /widget-body -->
                        </div><!-- /forgot-box -->

                        <div id="signup-box" class="signup-box visible widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header green lighter bigger"><i class="fa fa-users blue"></i> 新用户注册
                                        <small style="color: rgb(245, 130, 101);margin-left:10px;font-family:Microsoft Yahei">请先上传头像(400*400)</small>
                                    </h4>

                                    <div class="space-6"></div>

                                    <form>
                                        <fieldset>
                                            <!--<label class="block clearfix"> <span class="block input-icon input-icon-right">
                                            <input type="email" class="form-control" placeholder="Email" />
                                            <i class="fa fa-envelope"></i> </span> </label>-->

                                        <#if isSuper>
                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
														
							                            <select class="form-control" id="register-assoc">
                                                        ${associationOps}
                                                        </select>
						                            </span>
                                            </label>
                                        </#if>

                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="姓名" id="register-u-name" />
															<i class=""></i> </span> </label>

                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
												<!--			<input type="text" class="form-control" placeholder="性别" id="register-u-sex" /> -->
                                                <select class="form-control" id="register-u-sex">
                                                    <option value="0" selected>男</option>
                                                    <option value="1">女</option>
                                                </select>
															<i class=""></i> </span> </label>

                                            <label class="block clearfix" style="display: none;"> <span class="block input-icon input-icon-right">
                                                            <input onblur="setConstellation()" type="text" class="form-control" style="width: 40%;float: left;" placeholder="生日 年" id="register-u-birthday-y" />
                                                            <input onblur="setConstellation()" type="text" class="form-control" style="width: 25%;float: left; margin-left: 5%;" placeholder="月" id="register-u-birthday-m" />
                                                            <input onblur="setConstellation()" type="text" class="form-control" style="width: 25%;float: left; margin-left: 5%;" placeholder="日" id="register-u-birthday-d" />
												<!--			<input type="text" class="form-control" placeholder="生日" id="register-u-birthday" /> -->
                                                <!--<input type="text" id="register-u-birthday" class="form-control hasDatepicker"/>
                                                <span class="input-group-addon">
                                                    <i class="fa fa-calendar"></i>
                                                </span>-->
															<i class=""></i> </span> </label>

                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="星座" id="register-u-constellation" />
															<i class=""></i> </span> </label>


                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="手机" id="register-u-phone" />
															<i class=""></i> </span> </label>

                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="邮箱" id="register-u-mail" />
															<i class=""></i> </span> </label>

                                            <#--<label class="block clearfix" style="display: none;"> <span class="block input-icon input-icon-right">-->
															<#--<input type="text" class="form-control" placeholder="行业" id="register-u-profession" />-->
															<#--<i class=""></i> </span> </label>-->

                                            <#--<label class="block clearfix" style="display: none;"> <span class="block input-icon input-icon-right">-->
															<#--<input type="text" class="form-control" placeholder="公司" id="register-u-enterprise" />-->
															<#--<i class=""></i> </span> </label>-->

                                            <#--<label class="block clearfix" style="display: none;">-->

                                                <#--<span class="block input-icon input-icon-right">-->
															<#--<input type="text" class="form-control" placeholder="协会身份" id="register-u-identity" />-->
															<#--<i class=""></i> </span>-->
                                            <#--</label>-->

                                        <#if isSuper>
                                            <!-- supper do not need this field -->
                                        <#else>
                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">

							                            <select class="form-control" id="register-identi">
                                                        ${identityOps}
                                                        </select>
						                            </span>
                                            </label>
                                        </#if>

                                        <#if isSuper>
                                            <!-- supper do not need this field -->
                                        <#else>
                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">

							                            <select class="form-control" id="register-group">
                                                        ${groupOps}
                                                        </select>
						                            </span>
                                            </label>
                                        </#if>


                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
							                            <select class="form-control" id="register-profession">
                                                        ${professionOpt}
                                                        </select>
						                            </span>
                                            </label>


                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="公司名称" id="register-u-cname" />
															<i class=""></i> </span> </label>

                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="公司职务" id="register-u-job" />
															<i class=""></i> </span> </label>

                                            <label class="clearfix" id="userFaceImage" style="display: none;"> <span class="block input-icon input-icon-right">
															<img src="" width="290" id="register-u-userFace" />
															<i id="register-u-userFaceHeight" class="fa fa-times" onclick="$(this).prev().attr('src','');$('#userFaceImage').hide();" style="font-size: 18px;cursor: pointer;"></i> </span> </label>

                                            <div class="clearfix">
                                                <button class="btn btn-danger" type="reset" style="font-weight:bold" onclick="window.location.href='/app/baccount/list';return true;">
                                                    <i class="fa fa-mail-reply bigger-110"></i>
                                                    返回
                                                </button>
                                                <button class="btn btn-info" type="reset" style="font-weight:bold" id="uploadUserFace">
                                                    <i class="fa fa-cloud-upload bigger-110"></i>
                                                    上传头像
                                                </button>
                                                <button type="button" class="btn pull-right btn-success" style="width:90px" onclick="register();">
                                                    注册
                                                    <i class="fa fa-arrow-right icon-on-right"></i>
                                                </button>
                                            </div>

                                        </fieldset>
                                    </form>

                                </div>
                                <!--
                                <div class="toolbar center">
                                <a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
                                <i class="fa fa-arrow-left"></i>
                                回去登录
                                </a>
                                </div>
                                -->
                            </div><!-- /widget-body -->
                        </div><!-- /signup-box -->
                    </div><!-- /position-relative -->
                </div>
            </div><!-- /.col -->
        </div><!-- /.row -->
    </div>
</div><!-- /.main-container -->

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
<script src="/assets/js/dropzone.min.js"></script>

<script src="/assets/js/jquery.form.js"></script>
<script src="/assets/js/jqGrid/jquery.jqGrid.min.js"></script>
<script src="/assets/js/jqGrid/i18n/grid.locale-zh-art-cmt.js"></script>

<!-- ace scripts -->

<script src="/assets/js/ace-elements.min.js"></script>
<script src="/assets/js/ace.min.js"></script>

<script src="/common-jieli.js"></script>

<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='/assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

<script type="text/javascript">
    if ("ontouchend" in document)
        document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
</script>

<!-- inline scripts related to this page -->

<script type="text/javascript">
    function show_box(id) {
        jQuery('.widget-box.visible').removeClass('visible');
        jQuery('#' + id).addClass('visible');
    }
</script>

<script>
    var birthd = null;
    function setConstellation(){
        var y = $("#register-u-birthday-y").val();
        var m = $("#register-u-birthday-m").val();
        var d = $("#register-u-birthday-d").val();

        if (y != "" && m != "" && d != ""){
            try {
                birthd = new Date();
                birthd.setFullYear(parseInt(y));
                birthd.setMonth(parseInt(m)-1);
                birthd.setDate(parseInt(d));

                var c = getAstro(parseInt(m)-1, parseInt(d));
                $("#register-u-constellation").val(c+"座");
            }catch (err){birthd = null;}
        }
    }

    function showMsg(title, msg) {
        if (title == '注册成功') {
            $.gritter.add({
                title: title,
                sticky:true,
                time:'',
                text: "&nbsp;&nbsp;" + msg,
                class_name: 'gritter-info gritter-center',
                after_close:function(){window.location.href="/app/baccount/list";}
            });
        } else {
            $.gritter.add({
                title: title,
                text: "&nbsp;&nbsp;" + msg,
                class_name: 'gritter-info '
            });
        }
    }

    function register() {
        var _d = {"username":new Date().getTime()<#if isSuper>,"associationId":$("#register-assoc").val()</#if>};
        var _sd = JSON.stringify(_d);

        var user= checkInput();

        if (user != null) {
            $.ajax({
                type: "POST",
            <#if isSuper>
                url: "/app/account/auth",
            <#else>
                url: "/app/account/register",
            </#if>
                async: false,
                data: _sd,
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                success: function (jsn) {
                    var jsn_body = "";
                    if (jsn.code == 200) {
                        eval("jsn_body=" + jsn.body);


                        if (user != null) {
                            user._id = jsn_body.userId;
                            $.ajax({
                                type: "POST",
                                url: '/app/baccount/reuser',
                                data: JSON.stringify(user),
                                contentType: "application/json; charset=utf-8",
                                dataType: 'json',
                                success: function (ret) {
                                    if (ret.code == 200)
                                        showMsg("注册成功", "用户" + user.name);
                                    else
                                        showMsg("注册失败", ret.msg || "");
                                },
                                error: function(XMLHttpRequest, textStatus, errorThrown) {
                                    alert("操作失败，错误码："+XMLHttpRequest.status);
                                    return;
                                }
                            });
                        }

                    } else
                        showMsg("注册失败", jsn.msg);
                },
                error: function () {
                    showMsg("注册失败", "连接失败，请重试");
                }
            });
        }
    }

    function checkInput(){
        var u = {};
        if ($("#register-u-name").val()==""){
            alert("请添加用户姓名！");
            return null;
        }
        u.name = $("#register-u-name").val();
        u.sex = parseInt($("#register-u-sex").val());
        /*
        var y = parseInt($("#register-u-birthday-y").val());
        var m = parseInt($("#register-u-birthday-m").val());
        m = (m < 10 ? "0":"") + m;
        var d = parseInt($("#register-u-birthday-d").val());
        d = (d < 10 ? "0":"") + d;
        if (isNaN(y) || isNaN(m) || isNaN(d)) {alert("必须输入生日！");return null;}

        u.birthday = y + "-" + m + "-" + d;
        */
        if (birthd) {
            u.birthday = birthd;
            u.constellation = $("#register-u-constellation").val();
        }

        if ($("#register-identi").val() != "")
            u.identity = $("#register-identi").val();
        if ($("#register-group").val() != "")
            u.group = $("#register-group").val();
        if ($("#register-profession").val() != "")
            u.profession = $("#register-profession").val();

        var pphone;
        try{
            pphone = parseInt($("#register-u-phone").val());
        }catch (err){
            alert("请确认手机号码格式正确！");
            return null;
        }
        if (pphone != "" && !isNaN(pphone)) {
            u.phone = pphone;
        }else{
            alert("请输入手机号码！");
            return null;
        }

        u.mail = $("#register-u-mail").val();

        if ($("#register-u-cname").val() != "")
            u.enterpriseName = $("#register-u-cname").val();
        if ($("#register-u-job").val() != "")
            u.job = $("#register-u-job").val();

        u.userFace = $("#register-u-userFace").attr("src") || "";

        return u;
    }
</script>

<script>

    jQuery(function($){
        $("#uploadUserFace").click(uploadRegister);
    })
</script>
</body>
</html>
