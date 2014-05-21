<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8" />
    <title>Э������ ��¼</title>
    <meta name="keywords" content="����" />
    <meta name="description" content="����" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <!-- basic styles -->

    <link href="/assets/css/bootstrap.min.css" rel="stylesheet" />

    <link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">

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
                        <h1><i class="fa fa-leaf green"></i><span class="red">Э������</span><span class="white">��̨����ϵͳ</span></h1>
                        <h4 class="blue">&copy; Company Name</h4>
                    </div>

                    <div class="space-6"></div>

                    <div class="position-relative" style="font-family: '΢���ź�'">
                        <div id="login-box" class="login-box visible widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header blue lighter bigger"><i class="fa fa-coffee green"></i> �û���¼ </h4>

                                    <div class="space-6"></div>

                                    <form id="form-login">
                                        <fieldset>
                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="�û���" id="login-username" />
															<i class="fa fa-user" style="position: absolute;left:auto;right: 8px;top:11px;"></i> </span> </label>

                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="����" id="login-password" />
															<i class="fa fa-lock" style="position: absolute;left:auto;right: 8px;top:11px;"></i> </span> </label>

                                            <div class="space"></div>

                                            <div class="clearfix">
                                                <!--<label class="inline">
                                                <input type="checkbox" class="ace" />
                                                <span class="lbl"> ��ס��</span> </label>-->

                                                <button type="button" class="width-35 pull-right btn btn-sm btn-primary" onclick="login()">
                                                    <i class="fa fa-key"></i>
                                                    ��¼
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
                                            ��������
                                        </a>
                                    </div>

                                    <div>
                                        <a href="#" onclick="show_box('signup-box'); return false;" class="user-signup-link">
                                            ע���˺�
                                            <i class="fa fa-arrow-right"></i>
                                        </a>
                                    </div>-->
                                </div>
                            </div><!-- /widget-body -->
                        </div><!-- /login-box -->

                        <div id="forgot-box" class="forgot-box widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header red lighter bigger"><i class="fa fa-key"></i> �һ����� </h4>

                                    <div class="space-6"></div>
                                    <p>
                                        ��������������������
                                    </p>

                                    <form>
                                        <fieldset>
                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="icon-envelope"></i> </span> </label>

                                            <div class="clearfix">
                                                <button type="button" class="width-35 pull-right btn btn-sm btn-danger">
                                                    <i class="icon-lightbulb"></i>
                                                    ����
                                                </button>
                                            </div>
                                        </fieldset>
                                    </form>
                                </div><!-- /widget-main -->

                                <div class="toolbar center">
                                    <a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
                                        ��ȥ��¼
                                        <i class="fa fa-arrow-right"></i>
                                    </a>
                                </div>
                            </div><!-- /widget-body -->
                        </div><!-- /forgot-box -->

                        <div id="signup-box" class="signup-box widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header green lighter bigger"><i class="fa fa-users blue"></i> ���û�ע�� </h4>

                                    <div class="space-6"></div>
                                    <p>
                                        ������������Ϣ:
                                    </p>

                                    <form>
                                        <fieldset>
                                            <!--<label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="icon-envelope"></i> </span> </label>-->

                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="�û���" id="register-username" />
															<i class="fa fa-user"></i> </span> </label>

                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="����" id="register-password" />
															<i class="fa fa-lock"></i> </span> </label>

                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="ȷ������" id="repeat-password" />
															<i class="fa fa-retweet"></i> </span> </label>

                                            <div class="space-24"></div>

                                            <div class="clearfix">
                                                <button type="reset" class="width-30 pull-left btn btn-sm" style="">
                                                    <i class="fa fa-refresh"></i>
                                                    ����
                                                </button>

                                                <button type="button" class="width-65 pull-right btn btn-sm btn-success">
                                                    ע��
                                                    <i class="fa fa-arrow-right icon-on-right"></i>
                                                </button>
                                            </div>
                                        </fieldset>
                                    </form>
                                </div>

                                <div class="toolbar center">
                                    <a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
                                        <i class="fa fa-arrow-left"></i>
                                        ��ȥ��¼
                                    </a>
                                </div>
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
    function showMsg(title,msg) {
        var pos = (title == '��½�ɹ�' ? 'gritter-center' : '');
        if (title == '��½�ɹ�') {
            $.gritter.removeAll();
            setTimeout(function(){window.location.href="/app/bindex";},1000);

            $.gritter.add({
                title: title,
                text: "&nbsp;&nbsp;" + "���Ժ�...",
                sticky:false,
                time:850,
                class_name: 'gritter-info gritter-light ' + pos,
                after_close:function(){
                    window.location.href="/app/bindex";
                }
            });
        }else{
            $.gritter.add({
                title: title,
                text: "&nbsp;&nbsp;" + msg,
                class_name: 'gritter-info gritter-light ' + pos
            });
        }
    }

    function login() {
        //showMsg("��½�ɹ�");
        //showMsg("�������");

        var char34 = "\"";
        //var _sd = "{\"username\":\"" + $("#login-username").val() + "\",\"password\":\"" + $("#login-password").val() + "\"}";
        //var _d = $.parseJSON(_sd);
        var _d = {"username":$("#login-username").val(),"password":$("#login-password").val()};
        var _sd = JSON.stringify(_d);
        //alert(_d);

        $.ajax({
            type : "POST",
            url : "/app/baccount/login",
            async : false,
            data : _sd,
            contentType : "application/json; charset=utf-8",
            dataType : 'json',
            success : function(jsn) {
                var jsn_body="";
                //alert(jsn);
                if (jsn.code == 200) {
                    eval("jsn_body="+jsn.body);var d = new Date();
                    d.setTime(d.getTime()+(365*24*60*60*1000));
                    var expires = "expires="+d.toGMTString();
                    document.cookie="u="+jsn_body.sessionId+"; path=/; "+expires;
                    document.cookie="a="+jsn_body.associationId+"; path=/; "+expires;
                    document.cookie="r="+jsn_body.role+"; path=/; "+expires;
                    //$.cookie('sessionId', jsn_body.sessionId);
                    showMsg("��½�ɹ�",jsn.msg);
                }
                else showMsg("��½ʧ��",jsn.msg);
            },
            error : function() {
                showMsg("��½ʧ��","����ʧ�ܣ�������");
            }
        });
    }

    function register(){
        var psw = $("#register-password").val();
        var rpt_psw = $("#repeat-password").val();

        if (psw != rpt_psw)
            showMsg("ע��ʧ��","���벻һ��");

        //$.ajax({})
    }
</script>

</body>
</html>
