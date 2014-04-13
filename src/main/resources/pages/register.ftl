<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8" />
    <title>接力 注册</title>
    <meta name="keywords" content="接力" />
    <meta name="description" content="接力" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <!-- basic styles -->

    <link href="/assets/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="/assets/css/font-awesome.min.css" />

    <!--[if IE 7]>
    <link rel="stylesheet" href="/assets/css/font-awesome-ie7.min.css" />
    <![endif]-->

    <!-- page specific plugin styles -->

    <link rel="stylesheet" href="/assets/css/jquery-ui-1.10.3.custom.min.css" />
    <link rel="stylesheet" href="/assets/css/jquery.gritter.css" />

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
                        <h1><i class="icon-leaf green"></i><span class="red">接力</span><span class="white">后台管理系统</span></h1>
                        <h4 class="blue">&copy; Company Name</h4>
                    </div>

                    <div class="space-6"></div>

                    <div class="position-relative" style="font-family: '微软雅黑'">
                        <div id="login-box" class="login-box widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header blue lighter bigger"><i class="icon-coffee green"></i><!-- Please Enter Your Information --></h4>

                                    <div class="space-6"></div>

                                    <form id="form-login">
                                        <fieldset>
                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="用户名" id="login-username" />
															<i class="icon-user"></i> </span> </label>

                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="密码" id="login-password" />
															<i class="icon-lock"></i> </span> </label>

                                            <div class="space"></div>

                                            <div class="clearfix">
                                                <!--<label class="inline">
                                                <input type="checkbox" class="ace" />
                                                <span class="lbl"> 记住我</span> </label>-->

                                                <button type="button" class="width-35 pull-right btn btn-sm btn-primary" onclick="login()">
                                                    <i class="icon-key"></i>
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
                                    <i class="icon-arrow-left"></i>
                                    忘记密码
                                    </a>
                                    </div>

                                    <div>
                                    <a href="#" onclick="show_box('signup-box'); return false;" class="user-signup-link">
                                    注册账号
                                    <i class="icon-arrow-right"></i>
                                    </a>
                                    </div>-->
                                </div>
                            </div><!-- /widget-body -->
                        </div><!-- /login-box -->

                        <div id="forgot-box" class="forgot-box widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header red lighter bigger"><i class="icon-key"></i> 找回密码 </h4>

                                    <div class="space-6"></div>
                                    <p>
                                        请输入接收新密码的邮箱
                                    </p>

                                    <form>
                                        <fieldset>
                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="icon-envelope"></i> </span> </label>

                                            <div class="clearfix">
                                                <button type="button" class="width-35 pull-right btn btn-sm btn-danger">
                                                    <i class="icon-lightbulb"></i>
                                                    发送
                                                </button>
                                            </div>
                                        </fieldset>
                                    </form>
                                </div><!-- /widget-main -->

                                <div class="toolbar center">
                                    <a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
                                        回去登录
                                        <i class="icon-arrow-right"></i>
                                    </a>
                                </div>
                            </div><!-- /widget-body -->
                        </div><!-- /forgot-box -->

                        <div id="signup-box" class="signup-box visible widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header green lighter bigger"><i class="icon-group blue"></i> 新用户注册 </h4>

                                    <div class="space-6"></div>

                                    <form>
                                        <fieldset>
                                            <!--<label class="block clearfix"> <span class="block input-icon input-icon-right">
                                            <input type="email" class="form-control" placeholder="Email" />
                                            <i class="icon-envelope"></i> </span> </label>-->

                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="用户名" id="register-username" />
															<i class="icon-user"></i> </span> </label>

                                        <#if isSuper>
                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
														
							                            <select class="form-control" id="register-assoc">
                                                        ${associationOps}
                                                        </select>
						                            </span>
                                            </label>
                                        </#if>

                                            <div class="clearfix">
                                                <button type="button" class="width-65 pull-right btn btn-sm btn-success" onclick="register();">
                                                    注册
                                                    <i class="icon-arrow-right icon-on-right"></i>
                                                </button>
                                            </div>

                                        </fieldset>
                                    </form>

                                </div>
                                <!--
                                <div class="toolbar center">
                                <a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
                                <i class="icon-arrow-left"></i>
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

<script src="/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>
<script src="/assets/js/bootbox.min.js"></script>
<script src="/assets/js/jquery.easy-pie-chart.min.js"></script>
<script src="/assets/js/jquery.gritter.min.js"></script>
<script src="/assets/js/spin.min.js"></script>

<!-- ace scripts -->

<script src="/assets/js/ace-elements.min.js"></script>
<script src="/assets/js/ace.min.js"></script>

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
    function showMsg(title, msg) {
        if (title == '注册成功') {
            $.gritter.add({
                title: title,
                sticky:true,
                time:'',
                text: "&nbsp;&nbsp;" + msg,
                class_name: 'gritter-info gritter-center',
                after_close:function(){window.location.href="/rest/baccount/list";}
            });
        } else {
        }
            $.gritter.add({
                title: title,
                text: "&nbsp;&nbsp;" + msg,
                class_name: 'gritter-info '
            });
        }
    }

    function register() {
        //var _sd = "{\"username\":\""+$("#register-username").val() + "\"<#if isSuper>,\"associationId\":\""+$("#register-assoc").val()+"\"</#if>}";
        var _d = {"username":$("#register-username").val()<#if isSuper>,"associationId":$("#register-assoc").val()</#if>};
        var _sd = JSON.stringify(_d);

        $.ajax({
            type:"POST",
        <#if isSuper>
            url:"/rest/account/auth",
        <#else>
            url:"/rest/account/register",
        </#if>
            async:false,
            data:_sd,
            contentType:"application/json; charset=utf-8",
            dataType:'json',
            success:function(jsn){
                var jsn_body = "";
                if (jsn.code == 200){
                    eval("jsn_body="+jsn.body);
                    showMsg("注册成功", "用户"+_d.username+"的密码是："+jsn_body.password);
                } else
                    showMsg("注册失败", jsn.msg);
            },
            error : function() {
                showMsg("注册失败", "连接失败，请重试");
            }
        });
    }

</script>

</body>
</html>
