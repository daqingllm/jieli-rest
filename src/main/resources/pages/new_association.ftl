<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8" />
    <title>接力 创建协会</title>
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
                        
                        <div id="forgot-box" class="forgot-box visible widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header red lighter bigger"><i class="icon-key"></i> 创建协会 </h4>

                                    <div class="space-6"></div>
                                    <p>
                                        请输入协会名称
                                    </p>

                                    <form>
                                        <fieldset>
                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="协会名称" id="association-name" />
															<i class="icon-envelope"></i> </span> </label>

                                            <div class="clearfix">
                                                <button type="button" class="width-35 pull-right btn btn-sm btn-danger" onclick="createAssociation();">
                                                    <i class="icon-lightbulb"></i>
                                                    创建
                                                </button>
                                            </div>
                                        </fieldset>
                                    </form>
                                </div><!-- /widget-main -->

                            </div><!-- /widget-body -->
                        </div><!-- /forgot-box -->

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
    function showMsg(title,msg) {
        var pos = (title == '创建成功' ? 'gritter-center' : '');
        if (title == '创建成功') {
            $.gritter.removeAll();

            $.gritter.add({
                title: title,
                text: "&nbsp;&nbsp;" + msg,
                class_name: 'gritter-info gritter-light ' + pos,
                after_close:function(){
                    window.location.href="/app/bassociation/list";
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

    function createAssociation(){
        var name = $("#association-name").val();
		if (name && name.length > 0){
			var _d = {"name":name};
			var _sd = JSON.stringify(_d);
			$.ajax({
				type:"POST",
				url:"/app/association/",
				data:_sd,
                contentType:"application/json; charset=utf-8",
				dataType:"json",
				success:function(jsn){
					if (jsn.code == 200) {
						showMsg("创建成功","已经成功创建"+name+"协会");
					}else{
						showMsg("创建失败",jsn_body.msg||"");
					}
				},
				error:function(){
					window.location.href="/app/baccount/login";
				}
			});
		}
		else{
			showMsg("创建失败","请输入协会名称");
		}

    }

</script>

</body>
</html>
