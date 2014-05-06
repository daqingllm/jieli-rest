
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

// 点击预览按钮
function previewThisArticle() {
    var previewinpage = $("#form-field-textarea").val();
    var previewinlist;
    try {
        previewinlist = $("#grid-table").getGridParam("selrow");
        //if (previewinlist) previewinlist = $("#grid-table > tbody > tr").eq(previewinlist).find("td").eq(5).attr("title");
        if (previewinlist) previewinlist = $("#grid-table > tbody > tr").eq(previewinlist).find("td").eq(9).html();
    }catch (e){
        previewinlist = "";
    }
    if (previewinpage && previewinpage.length >= previewinlist.length)
        $("#dialog-message-preview").html(previewinpage);
    else
        $("#dialog-message-preview").html(previewinlist);

    var dialog = $("#dialog-message-preview").removeClass('hide').dialog({
        modal: true,
        width: 500,
        title: "<div class='widget-header widget-header-small'><h5 class='smaller'><i class='icon-ok'></i> 预览 </h5></div>",
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

    /*$.gritter.add({
        title: "预览",
        text: ""+$("#form-field-textarea").val(),
        sticky:true,
        class_name: 'gritter-info gritter-center gritter-light '
    });*/
}

// 点击完成按钮
function postThisArticle(){
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
        isEdit = true;
    }

    var p_assid,p_title,p_type,p_overview,p_content,p_images;

    p_assid = $("#selectAssociationIds").val();
    if (p_assid == null || p_assid == "") {alert("请选择协会！");return;}
    if (!test2227(p_assid)) return;
    //json["associationId"] = p_assid[0];

    var p_pt,p_it;
    p_pt = $("#form-field-select-pro").val();
    if (p_pt == null || p_pt == "") {alert("请选择行业标签！");return;}
    json["professionTag"] = p_pt;

    p_it = $("#selectInterest").val();
    if (p_it == null || p_it.length == 0){alert("请选择兴趣标签！");return;}
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

    p_content = $("#form-field-textarea").val();
    if (p_content == null || p_content == "") {alert("请填写内容！");return;}
    json["content"] = p_content;

    p_overview = p_content.replace(new RegExp("<(.*?)>","g"),"").substr(0,30);
    json["overview"] = p_overview;

    var phph1 = "<center><img src='";
    var phph2 = "'></center>";

    var idxs = p_content.indexOf(phph1);
    var idxe;
    json["images"] = [];
    while(idxs >= 0){
        idxe = p_content.indexOf(phph2,idxs);
        var st = idxs+phph1.length;
        if (idxe-st < idxs) break;

        var _url = p_content.substr(st,idxe-st);

        //var head = p_overview.substr(0,idxs) || "";
        //var tail =  p_overview.substr(idxe+1) || "";
        //p_overview = head + "<center><img src='"+_url+"'></center>";

        idxs = p_content.indexOf(phph1,idxe);

        //jsn += "{\"placeholder\":\""+phph+_url+">\",\"url\":\""+_url+"\",\"description\":\" \"},";
        var jsn_img = {"placeholder":"","url":_url,"description":""};
        json["images"].push(jsn_img);
    }
    json["imagesCount"] = 0;
    json["appreciateUserIds"] = [];
    json["appreciateCount"] = 0;
    json["addTime"] = null;

    if (isEdit){
        json["appreciateUserIds"] = data["appreciateUserIds"];
        json["appreciateCount"] = data["appreciateCount"];
        json["addTime"] = data["addTime"];
    }

    var suc = true;
    // if edit , there is only one element in p_assid
    for (var i = 0; i <p_assid.length;i++) {
        json["associationId"] = p_assid[i];
        $.ajax({
            type: "POST",
            url: "/rest/news/add?force="+$("#form-field-checkbox").is(':checked'),
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
        if (p_id > -1) {alert("已经成功编辑此资讯");window.location.href = "/rest/bnews/list";}
        else {alert("已经成功添加此资讯");window.location.href = "/rest/bnews/list";}
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

// 删除一个图片
function deletePic(ph){
    var _src=ph.replace("<center><img src='","");
    _src = _src.replace("'></center>","");
    _src = _src.replace("<center><img src=","");
    _src = _src.replace("></center>","");

    // clear textarea
    var otextarea = $("#form-field-textarea").val().trim();
    //otextarea = otextarea.replace(ph,"");
    otextarea = otextarea.replace("<center><img src='"+_src+"'></center>","");
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
    url: '/rest/upload',
    type:'post',
    dataType:'json',
    contentType:'text/plain',
    success:function( jsn ) {
        var msg = jsn.msg;
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
            var imgsrc = uploadImgSrc;
            var newImgHtml = "<li>";
            newImgHtml += "<a href='" + jsn.body + "' data-rel='colorbox'>";
            newImgHtml += "<img alt='150x150' width='150' height='150' src='" + jsn.body + "' />";
            newImgHtml += "</a>";
            newImgHtml += "<div class='tools tools-right' style='height:30px;'>";
            // must be " , ' no use
            var re = new RegExp("\'", "g");
            newImgHtml += "<a href='#' onclick='deletePic(\"" + uploadImgSrc.replace(re, "") + "\")'><i class='icon-remove red'></i></a></div></li>";

            $("#upload-img-list > li").eq(0).after(newImgHtml);

            $("#img-list-invisible").attr("style", "border-width:0;display:none");

            $('.ace-thumbnails [data-rel="colorbox"]').colorbox(colorbox_params);
            $("#cboxLoadingGraphic").append("<i class='icon-spinner orange'></i>");//let's add a custom loading icon
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
        isEdit = true;
    }

    if (type == 0)
    act.tag = "RECOMMEND";
    else
    act.tag = "OFFICIAL";

    act.title=$("#form-field-title").val();
    act.actDate=new Date($("#form-field-actDate").val());
    //act.actDate=null;
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
    /*
     var serviceRaw = $("#form-field-textarea-service").val();
     var serviceList = serviceRaw.split("\n");
     for (var i = 0; i < serviceList.length; i++){
     var findex = serviceList[i].indexOf(":");
     if (serviceList[i].indexOf(":") > -1 || serviceList[i].indexOf("：") > -1){
     if (findex < 0) findex = serviceList[i].indexOf("：");
     var head = serviceList[i].substr(0,findex);
     var tail = serviceList[i].substr(findex);
     eval("act.serviceInfo."+head+"='"+tail+"'");
     }else{
     eval("act.serviceInfo."+serviceList[i]+"='"+serviceList[i]+"'");;
     }
     }
     //act.serviceInfo=$("#form-field-textarea-service").val();
     */
    $(".service-info").each(function(){
        var head = $(this).children("input").eq(0).val();
        var tail = $(this).children("input").eq(1).val();
        if (head != '' && tail != ''){
            try {
                eval("act.serviceInfo." + head + "='" + tail + "'");
            }catch (err){;}
        }
    });
    act.album = {};
    act.sponsorInfo=$("#form-field-textarea-sponsor").val();
    // dlDate is beginDate ......
    act.beginDate=new Date($("#form-field-dlDate").val());
    //act.beginDate=null;
    act.fee=$("#form-field-fee").val();
    act.maxMembers=$("#form-field-max").val();
    act.url=$("#form-field-imgurl").attr("src");

//  <#if isSuper>
//      act.associationId=$("#form-field-associations").val();
//  <#else>
//      act.associationId="";
//  </#if>
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
    var url = "/rest/activity/?activityId="+(isEdit?act["_id"]:"")+"&force="+$("#form-field-checkbox").is(':checked');
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
        if (p_id > -1) {alert("已经成功编辑此活动");window.location.href = "/rest/bactivity/list";}
        else {alert("已经成功添加此活动");window.location.href = "/rest/bactivity/list";}
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
    if (act.maxNumbers == '')
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
        '<div class="icon-remove"></div>' +
        '</div>');
    serviceInfo.insertBefore($('#icon-plus-si').parent());
    serviceInfo.children(".icon-remove").click(deleteServiceInfo);
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
        '<div class="icon-remove"></div>' +
        '</div>');
    arrangementDetail.insertBefore($('#icon-plus-ad').parent());
    arrangementDetail.children('.icon-remove').click(deleteArrangementDetail);
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


function uploadImgBox() {
    var spin_img = "<div id='upload-loading-img' style='margin-left:30px;margin-top:10px;display: none;'><i class='icon-spinner icon-spin orange bigger-125'></i></div>";
    spin_img = "";
    bootbox.dialog({
        //message: "<input type='file' id='upload-image-files' name='upload-image-files' >",
        message: "<form id='rest-upload-form' action='/rest/upload' method='post' enctype='multipart/form-data' acceptcharset='UTF-8'>\n<input id='rest-upload-file' type='file' name='file' size='50' />"+spin_img+"</form>",
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

                                $("#form-field-imgurl").attr("src",uploadImgSrc);
                                $("#form-field-imgurl").css("display","block");
                                $("#delTitleImage").css("display","block");

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
