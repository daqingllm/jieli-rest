<!DOCTYPE html>
<html lang="zh">
<<<<<<< HEAD
<head>
    <meta charset="utf-8" />
    <title>Ω”¡¶ µ«¬º</title>
    <meta name="keywords" content="Ω”¡¶" />
    <meta name="description" content="Ω”¡¶" />
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

    <!-- <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" /> -->
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

    <script>
        function showMsg(msg) {
            if (msg == 'µ«¬Ω≥…π¶') {
                msg = "’˝‘⁄Ã¯◊™£¨«Î…‘∫Û";
                $.gritter.add({
                    title : 'µ«¬Ω≥…π¶',
                    text : "&nbsp;&nbsp;" + msg,
                    class_name : 'gritter-info gritter-center '
                });
            } else {
                $.gritter.add({
                    title : 'µ«¬Ω ß∞‹',
                    text : "&nbsp;&nbsp;" + msg,
                    class_name : 'gritter-info '
                });
            }
        }

        function login() {
            //showMsg("µ«¬Ω≥…π¶");
            //showMsg("√‹¬Î¥ÌŒÛ");

            var char34 = "\"";
            var _sd = "{\"username\":\"" + $("#login-username").val() + "\",\"password\":\"" + $("#login-password").val() + "\"}";
            var _d = $.parseJSON(_sd);
            //alert(_d);

            $.ajax({
                type : "POST",
                url : "/rest/account/login",
                async : false,
                data : _sd,
                contentType : "application/json; charset=utf-8",
                dataType : 'json',
                success : function(jsn) {
                    //alert(jsn);
                    if (jsn.code == 200) {
                        document.cookie="sessionId="+jsn.sessionId;
                        //$.cookie('sessionId', jsn.sessionId);
                    }
                    showMsg(jsn.msg);
                },
                error : function() {
                    showMsg("¡¨Ω” ß∞‹£¨«Î÷ÿ ‘");
                }
            });
        }
    </script>
</head>

<body class="login-layout">
<div class="main-container">
    <div class="main-content">
        <div class="row">
            <div class="col-sm-10 col-sm-offset-1">
                <div class="login-container">
                    <div class="center" style="margin-top: 40px;margin-bottom: 20px;">
                        <h1><i class="icon-leaf green"></i><span class="red">Ω”¡¶</span><span class="white">∫ÛÃ®π‹¿ÌœµÕ≥</span></h1>
                        <h4 class="blue">&copy; Company Name</h4>
                    </div>

                    <div class="space-6"></div>

                    <div class="position-relative" style="font-family: 'Œ¢»Ì—≈∫⁄'">
                        <div id="login-box" class="login-box visible widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header blue lighter bigger"><i class="icon-coffee green"></i><!-- Please Enter Your Information --></h4>

                                    <div class="space-6"></div>

                                    <form id="form-login">
                                        <fieldset>
                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="”√ªß√˚" id="login-username" />
															<i class="icon-user"></i> </span> </label>

                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="√‹¬Î" id="login-password" />
															<i class="icon-lock"></i> </span> </label>

                                            <div class="space"></div>

                                            <div class="clearfix">
                                                <!--<label class="inline">
                                                <input type="checkbox" class="ace" />
                                                <span class="lbl"> º«◊°Œ“</span> </label>-->

                                                <button type="button" class="width-35 pull-right btn btn-sm btn-primary" onclick="login()">
                                                    <i class="icon-key"></i>
                                                    µ«¬º
                                                </button>
                                            </div>

                                            <div class="space-4"></div>
                                        </fieldset>
                                    </form>

                                </div><!-- /widget-main -->

                                <div class="toolbar clearfix">
                                    <div>
                                        <a href="#" onclick="show_box('forgot-box'); return false;" class="forgot-password-link">
                                            <i class="icon-arrow-left"></i>
                                            Õ¸º«√‹¬Î
                                        </a>
                                    </div>

                                    <div>
                                        <a href="#" onclick="show_box('signup-box'); return false;" class="user-signup-link">
                                            ◊¢≤·’À∫≈
                                            <i class="icon-arrow-right"></i>
                                        </a>
                                    </div>
                                </div>
                            </div><!-- /widget-body -->
                        </div><!-- /login-box -->

                        <div id="forgot-box" class="forgot-box widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header red lighter bigger"><i class="icon-key"></i> ’“ªÿ√‹¬Î </h4>

                                    <div class="space-6"></div>
                                    <p>
                                        «Î ‰»ÎΩ” ’–¬√‹¬Îµƒ” œ‰
                                    </p>

                                    <form>
                                        <fieldset>
                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="icon-envelope"></i> </span> </label>

                                            <div class="clearfix">
                                                <button type="button" class="width-35 pull-right btn btn-sm btn-danger">
                                                    <i class="icon-lightbulb"></i>
                                                    ∑¢ÀÕ
                                                </button>
                                            </div>
                                        </fieldset>
                                    </form>
                                </div><!-- /widget-main -->

                                <div class="toolbar center">
                                    <a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
                                        ªÿ»•µ«¬º
                                        <i class="icon-arrow-right"></i>
                                    </a>
                                </div>
                            </div><!-- /widget-body -->
                        </div><!-- /forgot-box -->

                        <div id="signup-box" class="signup-box widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header green lighter bigger"><i class="icon-group blue"></i> –¬”√ªß◊¢≤· </h4>

                                    <div class="space-6"></div>
                                    <p>
                                        «Î ‰»Î“‘œ¬–≈œ¢:
                                    </p>

                                    <form>
                                        <fieldset>
                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="icon-envelope"></i> </span> </label>

                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="”√ªß√˚" />
															<i class="icon-user"></i> </span> </label>

                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="√‹¬Î" />
															<i class="icon-lock"></i> </span> </label>

                                            <label class="block clearfix"> <span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="»∑»œ√‹¬Î" />
															<i class="icon-retweet"></i> </span> </label>

                                            <div class="space-24"></div>

                                            <div class="clearfix">
                                                <button type="reset" class="width-30 pull-left btn btn-sm" style="">
                                                    <i class="icon-refresh"></i>
                                                    ÷ÿÃÓ
                                                </button>

                                                <button type="button" class="width-65 pull-right btn btn-sm btn-success">
                                                    ◊¢≤·
                                                    <i class="icon-arrow-right icon-on-right"></i>
                                                </button>
                                            </div>
                                        </fieldset>
                                    </form>
                                </div>

                                <div class="toolbar center">
                                    <a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
                                        <i class="icon-arrow-left"></i>
                                        ªÿ»•µ«¬º
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

<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>

<!-- <![endif]-->

<!--[if IE]>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
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
</body>
=======
	<head>
		<meta charset="utf-8" />
		<title>Êé•Âäõ ÁôªÂΩï</title>
		<meta name="keywords" content="Êé•Âäõ" />
		<meta name="description" content="Êé•Âäõ" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<!-- basic styles -->

		<link href="assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="assets/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<!-- fonts -->

		<!-- <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" /> -->
		<link rel="stylesheet" href="assets/css/font-google.css" />

		<!-- ace styles -->

		<link rel="stylesheet" href="assets/css/ace.min.css" />
		<link rel="stylesheet" href="assets/css/ace-rtl.min.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="assets/js/html5shiv.js"></script>
		<script src="assets/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body class="login-layout">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<i class="icon-leaf green"></i>
									<span class="red">Êé•Âäõ</span>
									<span class="white">ÂêéÂè∞ÁÆ°ÁêÜÁ≥ªÁªü</span>
								</h1>
								<h4 class="blue">&copy; Company Name</h4>
							</div>

							<div class="space-6"></div>

							<div class="position-relative" style="font-family: 'ÂæÆËΩØÈõÖÈªë'">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="icon-coffee green"></i>
												<!-- Please Enter Your Information -->
											</h4>

											<div class="space-6"></div>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="Áî®Êà∑Âêç" />
															<i class="icon-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="ÂØÜÁ†Å" />
															<i class="icon-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
														<label class="inline">
															<input type="checkbox" class="ace" />
															<span class="lbl"> ËÆ∞‰ΩèÊàë</span>
														</label>

														<button type="button" class="width-35 pull-right btn btn-sm btn-primary">
															<i class="icon-key"></i>
															ÁôªÂΩï
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>
										</div><!-- /widget-main -->

										<div class="toolbar clearfix">
											<div>
												<a href="#" onclick="show_box('forgot-box'); return false;" class="forgot-password-link">
													<i class="icon-arrow-left"></i>
													ÂøòËÆ∞ÂØÜÁ†Å
												</a>
											</div>

											<div>
												<a href="#" onclick="show_box('signup-box'); return false;" class="user-signup-link">
													Ê≥®ÂÜåË¥¶Âè∑
													<i class="icon-arrow-right"></i>
												</a>
											</div>
										</div>
									</div><!-- /widget-body -->
								</div><!-- /login-box -->

								<div id="forgot-box" class="forgot-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header red lighter bigger">
												<i class="icon-key"></i>
												ÊâæÂõûÂØÜÁ†Å
											</h4>

											<div class="space-6"></div>
											<p>
												ËØ∑ËæìÂÖ•Êé•Êî∂Êñ∞ÂØÜÁ†ÅÁöÑÈÇÆÁÆ±
											</p>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="icon-envelope"></i>
														</span>
													</label>

													<div class="clearfix">
														<button type="button" class="width-35 pull-right btn btn-sm btn-danger">
															<i class="icon-lightbulb"></i>
															ÂèëÈÄÅ
														</button>
													</div>
												</fieldset>
											</form>
										</div><!-- /widget-main -->

										<div class="toolbar center">
											<a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
												ÂõûÂéªÁôªÂΩï
												<i class="icon-arrow-right"></i>
											</a>
										</div>
									</div><!-- /widget-body -->
								</div><!-- /forgot-box -->

								<div id="signup-box" class="signup-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header green lighter bigger">
												<i class="icon-group blue"></i>
												Êñ∞Áî®Êà∑Ê≥®ÂÜå
											</h4>

											<div class="space-6"></div>
											<p> ËØ∑ËæìÂÖ•‰ª•‰∏ã‰ø°ÊÅØ: </p>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="icon-envelope"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="Áî®Êà∑Âêç" />
															<i class="icon-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="ÂØÜÁ†Å" />
															<i class="icon-lock"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Á°ÆËÆ§ÂØÜÁ†Å" />
															<i class="icon-retweet"></i>
														</span>
													</label>


													<div class="space-24"></div>

													<div class="clearfix">
														<button type="reset" class="width-30 pull-left btn btn-sm" style="">
															<i class="icon-refresh"></i>
															ÈáçÂ°´
														</button>

														<button type="button" class="width-65 pull-right btn btn-sm btn-success">
															Ê≥®ÂÜå
															<i class="icon-arrow-right icon-on-right"></i>
														</button>
													</div>
												</fieldset>
											</form>
										</div>

										<div class="toolbar center">
											<a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
												<i class="icon-arrow-left"></i>
												ÂõûÂéªÁôªÂΩï
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

		<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<![endif]-->

		<!--[if !IE]> -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>

		<!-- inline scripts related to this page -->

		<script type="text/javascript">
			function show_box(id) {
			 jQuery('.widget-box.visible').removeClass('visible');
			 jQuery('#'+id).addClass('visible');
			}
		</script>
	</body>
>>>>>>> d435f081973d5c4ce547c1a621f354d4adcc0359
</html>
