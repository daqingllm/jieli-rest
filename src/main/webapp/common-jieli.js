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
        if (previewinlist) previewinlist = $("#grid-table > tbody > tr").eq(previewinlist).find("td").eq(5).attr("title");
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

    var p_id = window.location.href.indexOf("artid=");
    if (p_id > -1) {
        json["_id"] = window.location.href.substr(p_id + "artid=".length);
        if (json["_id"].indexOf("&") > -1)
            json["_id"] = json["_id"].substr(0, json["_id"].indexOf("&"));
    }

    var p_assid,p_title,p_type,p_overview,p_content,p_images;

    p_assid = $("#selectAssociationIds").val();
    if (p_assid == null || p_assid == "") {alert("请选择协会！");return;}
    if (!test2227(p_assid)) return;
    //json["associationId"] = p_assid[0];

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
    var _src=ph.replace("<center><img src=","");
    _src = _src.substr(0,_src.length-"></center>".length);

    // clear textarea
    var otextarea = $("#form-field-textarea").val().trim();
    otextarea = otextarea.replace(ph,"");
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