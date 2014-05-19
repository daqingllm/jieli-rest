var imageHead = "<center><img width='576' style='padding:3px;' src='";
var imageTail = "?imageView/4/w/572/h/432"+"'></center>";

/* 选中target(应该是一个textarea)的部分内容：第一个str */
function focusTextareaPart(target,str) {
    if (!target || !str || str.length == 0) return;
    var text = target.value;
    var start = text.indexOf(str);
    var end = start + str.length;
    if(target.setSelectionRange){
        target.setSelectionRange(start, end);
    }else{
        var range = target.createTextRange();
        range.collapse(true);
        range.moveStart('character', start);
        range.moveEnd('character', str.length);
        range.select();
    }
    target.focus();
}

/*ie7下无此函数*/
if (!Array.prototype.indexOf)
{
    Array.prototype.indexOf = function(elt /*, from*/)
    {
        var len = this.length >>> 0;
        var from = Number(arguments[1]) || 0;
        from = (from < 0)
            ? Math.ceil(from)
            : Math.floor(from);
        if (from < 0)
            from += len;
        for (; from < len; from++)
        {
            if (from in this &&
                this[from] === elt)
                return from;
        }
        return -1;
    };
}

/*格式化日期*/
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function test2227(str){
    if (str.indexOf("'")>-1 || str.indexOf("\"")>-1) {
        alert(str + "中含有英文的单引号或双引号，请改正为中文引号。");
        return false;
    }
    return true;
}

function generateJieLiContent(){
    var content = $("#form-field-textarea").val();
    var regbr = new RegExp("\n","g");

    if (imagesUploadAdmin && imagesUploadAdmin.length > 0) {
        for (var i=0; i < imagesUploadAdmin.length;i++) {
            var jsn_img = {"placeholder": "", "url": imagesUploadAdmin[i].url, "description": ""};
            content = content.replace("[图片"+imagesUploadAdmin[i].position+"]",imageHead+imagesUploadAdmin[i].url+imageTail);
        }
    }

    return  content.replace(regbr,"<br/>");
}
// 预览 关于青企协
function previewJieLi(){

    var content=generateJieLiContent();
    $("#dialog-message-preview").html(content);

    var dialog = $("#dialog-message-preview").removeClass('hide').dialog({
        modal: true,
        width: 600,
        title: "<div class='widget-header widget-header-small'><h5 class='smaller'><i class='fa fa-check'></i> 预览 </h5></div>",
        title_html: true,
        buttons: [
            {
                text: "取消",
                "class": "btn btn-xs",
                click: function () {
                    $(this).dialog("close");
                }
            }
        ]
    });

    window.scrollTo(0,0);
}

// 点击预览按钮
function previewThisArticle(textObj,images) {
    var previewinpage = $("#form-field-textarea").val() || "";
    var previewinlist;
    try {
        previewinlist = $("#grid-table").getGridParam("selrow");
        if (previewinlist) previewinlist = $("#grid-table > tbody > tr").eq(previewinlist).find("td").eq(9).html();
        else previewinlist = "";
    }catch (e){
        previewinlist = "";
    }
    var regbr = new RegExp("\n","g");
    var regns = new RegExp(" ","g");
    var regimg = new RegExp("<img src","g");
    if (previewinpage && previewinpage.length >= previewinlist.length)
        $("#dialog-message-preview").html(previewinpage.replace(regbr,"<br/>").replace(regimg,"<img width='576' style='padding:3px;' src"));
    else
        $("#dialog-message-preview").html(previewinlist.replace(regbr,"<br/>").replace(regimg,"<img width='576' style='padding:3px;' src"));

    if (textObj&&images){
        var text = $("#"+textObj).val() || $("#"+textObj).html();
        for (var i = 0; i < images.length; i++){
            text = text.replace("[图片"+images[i].position+"]",imageHead+images[i].url+imageTail);
        }
        $("#dialog-message-preview").html(text);
    }

    var dialog = $("#dialog-message-preview").removeClass('hide').dialog({
        modal: true,
        width: 600,
        title: "<div class='widget-header widget-header-small'><h5 class='smaller'><i class='fa fa-check'></i> 预览 </h5></div>",
        title_html: true,
        buttons: [
            {
                text: "取消",
                "class": "btn btn-xs",
                click: function () {
                    $(this).dialog("close");
                }
            }
        ]
    });

    window.scrollTo(0,0);
}

// 点击完成按钮
function postThisArticle(images){
    // content 是内容 in html
    // overview 是列表页中使用的预览-需要生产一下 ： 去掉img，center表情

    var json = {};
    json["_id"] = null;

    var isEdit = false;
    var p_id = window.location.href.indexOf("artid=");
    if (p_id > -1) {
        json["_id"] = window.location.href.substr(p_id + "artid=".length);
        if (json["_id"].indexOf("&") > -1) {
            json["_id"] = json["_id"].substr(0, json["_id"].indexOf("&"));
        }
        json["_id"] = json["_id"].replace("#","");
        isEdit = true;
    }

    var p_assid,p_title,p_type,p_overview,p_content,p_images;

    p_assid = $("#selectAssociationIds").val();
    if (p_assid == null || p_assid == "") {alert("请选择协会！");return;}
    if (!test2227(p_assid)) return;
    //json["associationId"] = p_assid[0];

    var p_pt,p_it;
    p_pt = $("#form-field-select-pro").val();
    if (p_pt == null || p_pt == "") {p_pt = "";}
    json["professionTag"] = p_pt;

    p_it = $("#selectInterest").val();
    if (p_it == null || p_it.length == 0){p_it = [];}
    json["interestTags"] = [];
    for (var i = 0; i < p_it.length; i++){
        json["interestTags"].push(p_it[i]);
    }


    p_title = $("#form-field-title").val();
    if (p_title == null || p_title == "") {alert("请填写标题！");return;}
    if (!test2227(p_title)) return;
    json["title"] = p_title;

    p_type  = $("#form-field-select-type").val();
    if (p_type == null || p_type == "") {alert("请选择类型");return;}
    json["type"] = p_type;

    if (p_type == "history"){
        var p_otime = $("#form-field-occDate").val();
        if (p_otime == null || p_otime == "") {alert("请输入时间");return;}
        json["time"] = p_otime;
    }else{
        json["time"] = null;
    }

    var regbr = new RegExp("\n","g");
    var regns = new RegExp(" ","g");

    p_content = $("#form-field-textarea").val();
    p_content = p_content.replace(regbr,"<br/>");

    if (p_content == null || p_content == "") {alert("请填写内容！");return;}
    json["content"] = p_content;

    p_overview = p_content.replace(new RegExp("<(.*?)>","g"),"").substr(0,30);
    json["overview"] = p_overview;

    var phph1 = "<center><img width='576' style='padding:3px;' src='";
    var phph2 = "'></center>";

    var idxs = p_content.indexOf(phph1);
    var idxe;
    json["images"] = [];

    if (images && images.length > 0) {
        for (var i=0; i < images.length;i++) {
            var jsn_img = {"placeholder": "", "url": images[i].url, "description": p_title || ""};
            json["images"].push(jsn_img);
            p_content = p_content.replace("[图片"+images[i].position+"]",imageHead+images[i].url+imageTail);
        }
        json["content"] = p_content;
    }

    $("#upload-img-list li").each(function(){
        if ($(this).children("a") && $(this).children("a").length != 0) {
            var _url = $(this).children("a").attr("href");
            if (_url && _url.indexOf("http://") > -1) {
                var jsn_img = {"placeholder": "", "url": _url, "description": p_title || ""};
                json["images"].push(jsn_img);
            }
        }
    });
    while(idxs >= 0 && idxs < 0){
        idxe = p_content.indexOf(phph2,idxs);
        var st = 1+phph1.length;
        if (idxe-st < idxs) break;

        var _url = p_content.substr(st,idxe-st);

        idxs = p_content.indexOf(phph1,idxe);

        var jsn_img = {"placeholder":"","url":_url,"description":p_title || ""};
        json["images"].push(jsn_img);
    }
    json["imagesCount"] = 0;
    json["appreciateUserIds"] = [];
    json["appreciateCount"] = 0;
    json["addTime"] = null;

    var p_addurl = "/app/news/?newsId="+(isEdit?json["_id"]:"")+"&force="+$("#form-field-checkbox").is(':checked');
    var suc = true;
    // if edit , there is only one element in p_assid
    for (var i = 0; i <p_assid.length;i++) {
        json["associationId"] = p_assid[i];
        $.ajax({
            type: "POST",
            url: p_addurl,
            data: JSON.stringify(json),
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            async: false,
            success: function (ret) {
                if (ret.code != 200) suc = false;
            }
        });
    }

    if (suc) {
        if (p_id > -1) {alert("已经成功编辑此资讯");window.location.href = "/app/bnews/list";}
        else {alert("已经成功添加此资讯");window.location.href = "/app/bnews/list";}
    }
    else  alert("添加资讯失败");
}

// 清空图片列表
function clearImgList(){
    $("#form-field-title").val("");
    $("#form-field-select-type").get(0).selectedIndex = 0;
    $("#form-field-textarea").val("");
    $("#img-list-invisible").nextAll().remove();
    $("#img-list-invisible").attr("style","border-width:0;display:block");
}

function setTitleImg(news){
    try {delete news["associationName"];}catch (e){}
    if (news.type!="协会动态" && "合作展示"!=news.type && news.type != "association" && news.type != "enterprise") {alert("该类型无法设置头图"); return;}
    if (news.topPic) {alert("该资讯已经设置过头图"); return; }
    if (news.type=="协会动态") news.type = "association";
    if (news.type=="合作展示") news.type = "enterprise";

    if (!news.images || news.images.length == 0) {alert("该资讯中无图片"); return;}

    $.ajax({
        type:"POST",
        url:"/app/news/cover",
        async:false,
        data:JSON.stringify(news),
        contentType:"application/json",
        cache:false,
        processData:false,
        success:function(ret){
            if (ret.code == 200) {
                news.topPic = true;
                $.ajax({
                    url:"/app/news/?newsId="+news["_id"]+"&force=false",
                    type:"POST",
                    async:false,
                    data:JSON.stringify(news),
                    contentType:"application/json",
                    cache:false,
                    processData:false,
                    success:function(ret){
                        if (ret.code == 200)
                            alert("已设置为头图");
                        else
                            alert("设置头图失败");
                    },
                    error:function(err){
                        alert("设置头图失败");
                    }
                })
            }
            else
                alert("设置头图失败");
        }
    });
}

// 删除一个图片
function deletePic(ph){
    var _src=ph.replace("<center><img width='576' style='padding:3px;' src='","");
    _src = _src.replace("'></center>","");
    _src = _src.replace("<center><img width='576' style='padding:3px;' src=","");
    _src = _src.replace("></center>","");
    _src = _src.replace("<center><img width=576 style=padding:3px; src=","");
    _src = _src.replace("></center>","");

    // clear textarea
    var otextarea = $("#form-field-textarea").val().trim();
    //otextarea = otextarea.replace(ph,"");
    otextarea = otextarea.replace("<center><img width='576' style='padding:3px;' src='"+_src+"'></center>","");
    $("#form-field-textarea").val(otextarea);

    // delete pic
    $("a[href=\'"+_src+"\']").parent().remove();

    if ($("#img-list-invisible").parent().children().length == 1)
        $("#img-list-invisible").attr("style","border-width:0;display:block");
}

// 获取文本框中光标位置
function getTextAreaCursorPosition() {
    return $("#form-field-textarea").getCursorPosition();
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

// 资讯中上传图片
var uploadArticleImageOptions = {
    url: '/app/upload',
    type:'post',
    dataType:'json',
    contentType:'text/plain',
    success:function( jsn ) {
        var msg = jsn.msg;
        if (jsn.code == 200) {
            var uploadImgSrc = jsn.body + "";

            uploadImgSrc = "<center><img width='576' style='padding:3px;' src='" + uploadImgSrc + "'></center>";
            var otextarea = $("#form-field-textarea").val().trim();
            var otextarea_head = "";
            var otextarea_tail;
            var pos = getTextAreaCursorPosition() || 0;
            if (pos > 1)
                otextarea_head = otextarea.substring(0, pos);
            otextarea_tail = otextarea.substring(pos);

            $("#form-field-textarea").val(otextarea_head + uploadImgSrc + otextarea_tail);

            // 更新图片集
            var imgsrc = uploadImgSrc;
            var newImgHtml = "<li>";
            newImgHtml += "<a href='" + jsn.body + "' data-rel='colorbox'>";
            newImgHtml += "<img alt='150x150' width='150' height='150' src='" + jsn.body + "' />";
            newImgHtml += "</a>";
            newImgHtml += "<div class='tools tools-right' style='height:30px;'>";
            // must be " , ' no use
            var re = new RegExp("\'", "g");
            newImgHtml += "<a href='#' title='删除' onclick='deletePic(\""+uploadImgSrc.replace(re,"")+"\")'><i class='fa fa-times red'></i></a>";
            newImgHtml += "</div></li>";

            $("#upload-img-list > li").eq(0).after(newImgHtml);

            $("#img-list-invisible").attr("style", "border-width:0;display:none");

            $("#cboxLoadingGraphic").append("<i class='fa fa-spinner orange'></i>");//let's add a custom loading icon
        } else {
            alert("上传失败");
        }
    }
};



function finishActivity(type){

    var act = {};
    var p_id = window.location.href.indexOf("actid=");
    var isEdit  = false;
    if (p_id > -1) {
        act["_id"] = window.location.href.substr(p_id + "actid=".length);
        if (act["_id"].indexOf("&") > -1)
            act["_id"] = act["_id"].substr(0, act["_id"].indexOf("&"));
        act["_id"] = act["_id"].replace("#","");
        isEdit = true;
    }

    if (type == 0)
        act.tag = "RECOMMEND";
    else
        act.tag = "OFFICIAL";

    act.title=$("#form-field-title").val();
    act.actDate=new Date($("#form-field-actDate").val());
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

    act.serviceInfo = {};
    $(".service-info").each(function(){
        var head = $(this).children("input").eq(0).val();
        var tail = $(this).children("input").eq(1).val();
        if (head != ''){
            try {
                eval("act.serviceInfo." + head + "='" + tail + "'");
            }catch (err){;}
        }
    });


    /*sponsorInfo2:普通赞助*/
    var sponsorPuTong = {};
    $(".sponsor-choice").each(function(){
        var text =$(this).children("input") ? $(this).children("input").val() || "" : "";
        var img = $(this).children("img") ? $(this).children("img").attr("src") || "" : "";
        if (text != "" && img != "")
            sponsorPuTong[text] = img;
    });
    act["sponsorInfo2"] = sponsorPuTong;

    /*diamondInfo:钻石赞助*/
    var sponsorZshi = {};
    $(".diamond-choice").each(function(){
        var text =$(this).children("input") ? $(this).children("input").val() || "" : "";
        var img = $(this).children("img") ? $(this).children("img").attr("src") || "" : "";
        if (text != "" && img != "")
            sponsorZshi[text] = img;
    });
    act["diamondInfo"] = sponsorZshi;


    if (isEdit){
        act["appreciateUserIds"] = data["appreciateUserIds"];
        act["appreciateCount"] = data["appreciateCount"];
        act["addTime"] = data["addTime"];
    }


    act.album = {};
    act.officialAlbum = {};
    act.beginDate=new Date($("#form-field-dlDate").val());
    //act.beginDate=null;
    act.fee=$("#form-field-fee").val();
    act.maxMembers=$("#form-field-max").val();
    act.url=$("#form-field-imgurl").attr("src");


    var p_assid = $("#selectAssociationIds").val();

    act.addTime=null;
    //act.tag=null;
    act.followMembers=[];
    act.joinMembers={};
    act.invitees=[];

    if (isEdit){
        act.album = data.album;
        act.addTime = data.addTime;
        act.followMembers = data.followMembers;
        act.joinMembers = data.joinMembers;
        act.invitees = data.invitees;
    }

    var chk = check(act);
    if (chk != null) {alert(chk);return;}

    var suc = true;
    var url = "/app/activity/?activityId="+(isEdit?act["_id"]:"")+"&force="+$("#form-field-checkbox").is(':checked');
    for (var i = 0; i <p_assid.length;i++) {
        act.associationId = p_assid[i];
        $.ajax({
            type:"POST",
            url:url,
            async:false,
            data:JSON.stringify(act),
            contentType:"application/json",
            cache:false,
            processData:false,
            success:function(data){
                if (data.code != 200){
                    suc = false;
                }
            }
        });
    }

    if (suc) {
        if (p_id > -1) {alert("已经成功编辑此活动");window.location.href = "/app/bactivity/list";}
        else {alert("已经成功添加此活动");window.location.href = "/app/bactivity/list";}
    }
    else  alert("添加活动失败");
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
    if (act.maxMembers == '')
        return "必须填最大人数";

    if (isNaN(act.actDate.getDate()))
        return "必须设定活动时间";
    if (isNaN(act.beginDate.getDate()))
        return "必须设定截止时间";

    if ($("#selectAssociationIds").val() == '')
        return "必须选择至少一个协会";

    return null;
}

function deleteTitleImage(){
    $("#delTitleImage").css("display","none");
    $("#form-field-imgurl").attr("src","");
    $("#form-field-imgurl").css("display","none");
    $("#delTitleImage").css("display","none");
}

function deleteServiceInfo(){
    if ($('.service-info').length > 1){
        $(this).parent().remove();
    }else if ($('.service-info').length == 1){
        $(".service-info").children("input").val("");
    }
}

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

function addServiceInfo(){
    var serviceInfo = $('<div class="service-info">' +
        '<input type="text" placeholder="服务名称" class="col-xs-10 col-sm-2 service-info-name" style="padding-left: 7px;" >' +
        '<span style="padding:10px;float: left;"></span>' +
        '<input type="text" placeholder="服务内容" class="col-xs-10 col-sm-5 service-info-content" style="padding-left: 7px;">' +
        '<div class="fa fa-times fa-times-bigger"></div>' +
        '</div>');
    serviceInfo.insertBefore($('#icon-plus-si').parent());
    serviceInfo.children(".fa-times").click(deleteServiceInfo);
}

function addArrangementDetail() {
    var beginDate =$("#form-field-actDate").val();
    if (!beginDate) {alert("请先输入活动时间！");return;}

    var lastArrangementDetail = $('#icon-plus-ad').parent().prev().children().eq(0).val();
    if (lastArrangementDetail) lastArrangementDetail =  lastArrangementDetail.split(" ");
    else lastArrangementDetail = [GetDate10(beginDate,-1),'下午'];

    var arrangementDetail = $('<div class="arrangement-detail">' +
        '<input type="text"  placeholder="日程安排时间" class="col-xs-10 col-sm-2 arrangement-detail-time" style="padding-left: 7px;" >' +
        '<span style="padding:10px;float: left;"></span>' +
        '<input type="text"  placeholder="日程安排，不填即为此时间段无活动内容" class="col-xs-10 col-sm-5 arrangement-detail-content" style="padding-left: 7px;">' +
        '<div class="fa fa-times fa-times-bigger"></div>' +
        '</div>');
    arrangementDetail.insertBefore($('#icon-plus-ad').parent());
    arrangementDetail.children('.fa-times').click(deleteArrangementDetail);
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

var uploadActivityImageOptions = {
    url: '/app/upload',
    type:'post',
    dataType:'json',
    contentType:'text/plain',
    success:function( jsn ) {
        var msg = jsn.msg;
        if (jsn.code == 200) {
            var uploadImgSrc = jsn.body + "";

            $("#form-field-imgurl").attr("src",uploadImgSrc);
            $("#form-field-imgurl").css("display","block");
            $("#delTitleImage").css("display","inline");

        } else {
            alert("上传失败");
        }
    }
}
/*活动中上传图片*/
function uploadImgBox() {
    var spin_img = "<div id='upload-loading-img' style='margin-left:30px;margin-top:10px;display: none;'><i class='fa fa-spinner icon-spin orange bigger-125'></i></div>";
    spin_img = "";
    bootbox.dialog({
        message: "<form id='rest-upload-form' action='/app/upload' method='post' enctype='multipart/form-data' acceptcharset='UTF-8'>\n<input id='rest-upload-file' type='file' name='file' size='50' />"+spin_img+"</form>",
        buttons: {
            "upload": {
                "label": "<i class='fa fa-check'></i> 上传 ",
                "className": "btn-sm btn-success",
                "callback": function () {
                    $('#rest-upload-form').ajaxSubmit(uploadActivityImageOptions);
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
}

function addSponsorOption() {
    var voteOption = $('<div class="sponsor-choice">' +
        '<input type="text" value="" placeholder="赞助名称" class="col-xs-10 col-sm-5 vote-choice-text" style="padding-left: 7px;margin-right: 7px;">' +
        '<button type="button" class="btn btn-xs btn-info vote-choice-img" style="margin-left: 5px;margin-right: 5px;">Pic</button>' +
        '<div class="fa fa-times fa-times-bigger"></div>' +
        '</div>');
    voteOption.insertBefore($('.icon-plus-sp').parent());
    voteOption.children('.fa-times-bigger').click(deleteSponsorOption);
    voteOption.children('.vote-choice-img').click(function() {
        SponsorOptionUploadImg(voteOption);
    });
}

function deleteSponsorOption(){
    if ($('.sponsor-choice').length > 1) {
        $(this).parent().remove();
    }else{
        $(this).parent().children("img").remove();
        $(this).parent().children("button").show();
        $(this).parent().children("input").val("");
        $(this).parent().css("height","auto");
    }
}

function addDiamondOption() {
    var voteOption = $('<div class="diamond-choice">' +
        '<input type="text" value="" placeholder="赞助名称" class="col-xs-10 col-sm-5 vote-choice-text" style="padding-left: 7px;margin-right: 7px;">' +
        '<button type="button" class="btn btn-xs btn-info vote-choice-img" style="margin-left: 5px;margin-right: 5px;">Pic</button>' +
        '<div class="fa fa-times fa-times-bigger"></div>' +
        '</div>');
    voteOption.insertBefore($('.icon-plus-di').parent());
    voteOption.children('.fa-times-bigger').click(deleteDiamondOption);
    voteOption.children('.vote-choice-img').click(function() {
        SponsorOptionUploadImg(voteOption);
    });
}

function deleteDiamondOption(){
    if ($('.diamond-choice').length > 1) {
        $(this).parent().remove();
    }else{
        $(this).parent().children("img").remove();
        $(this).parent().children("button").show();
        $(this).parent().children("input").val("");
        $(this).parent().css("height","auto");
    }
}

function SponsorOptionUploadImg(voteOption) {
    var spin_img = "<div id='upload-loading-img' style='margin-left:30px;margin-top:10px;display: none;'><i class='fa fa-spinner icon-spin orange bigger-125'></i></div>";
    spin_img = "";
    bootbox.dialog({
        message: "<form id='rest-upload-form' action='/upload' method='post' enctype='multipart/form-data' acceptcharset='UTF-8'>\n<input id='rest-upload-file' type='file' name='file' size='50' />"+spin_img+"</form>",
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

                                var newImgHtml = "";

                                newImgHtml += "<img style='margin-top: 0' class='vote-img' width='150' height='150' src='"+jsn.body+"' />";

                                //newImgHtml += "<div class='tools tools-right' style='height:30px;'>";
                                // must be " , ' no use
                                //newImgHtml += "<a href='#' onclick='deletePic(\""+"\")'><i class='icon-remove red'></i></a></div></div>";
                                $(newImgHtml).insertBefore(voteOption.children('.fa-times-bigger'));
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

/* 在dropZoneId内 */
/* 删除图片imageArray[idx] , 即[图片{idxPreview}]*/
/* 如果需要 更新textarea[textAreaId]的内容 */
function CustomRemoveFile(dropZoneId,imageArray,idx,textAreaId){
    var dwTextArea = (textAreaId && textAreaId != "");
    var idxPreview = idx + 1;
    var position = "";
    if (idx < imageArray.length){
        // 获取[图片n]
        position = imageArray[idx].position;

        // 删掉[图片n]
        var otextarea = "";
        if (dwTextArea) otextarea = $("#"+textAreaId).val().trim();
        if (dwTextArea) otextarea = otextarea.replace("[图片"+position+"]","");

        // 获取[图片n+...]
        var imageSliceTail = imageArray.slice(idx+1,imageArray.length) || [];
        for (var i=0; i < imageSliceTail.length; i++) {
            // 将图片[n+...] 向前移动一位：[图片n+1]变成了[图片n]. imagesUpload&&textarea 同时操作
            if (dwTextArea) otextarea = otextarea.replace("[图片"+imageSliceTail[i].position+"]","[图片"+(imageSliceTail[i].position-1)+"]");
            imageSliceTail[i].position --;
        }

        if (dwTextArea) $("#"+textAreaId).val(otextarea);
        imageArray = imageArray.slice(0,idx).concat( imageSliceTail );

        // 更新图片名称
        var num = -1;
        var count = 0;
        $("#"+ dropZoneId +" .dz-preview").each(function(){
            var ele = $(this).find(".dz-filename").eq(0).children("span").html();
            var curIdx = parseInt(ele.substr(3));
            if (curIdx == idxPreview) num = count;
            if (curIdx > idxPreview) $(this).find(".dz-filename").eq(0).children("span").html("[图片" + (curIdx-1) + "]");

            count ++;
        });

        if (num > -1)
            $("#"+dropZoneId+" .dz-preview").eq(num).remove();

        if (imageArray.length == 0) $("#"+dropZoneId).removeClass("dz-started");
    }else{
        // 可能上传的文件太大了
        ;
    }

    return imageArray;
}

/*obj is a img element*/
function selectText(obj){
    var str = $(obj).parent().children(".dz-filename").eq(0).children("span").html();
    focusTextareaPart($("#"+textAreaId)[0],str);
}
