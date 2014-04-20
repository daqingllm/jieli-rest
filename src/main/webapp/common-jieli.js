function test2227(str){
    if (str.indexOf("'")>-1 || str.indexOf("\"")>-1) {
        alert(str + "中含有英文的单引号或双引号，请改正为中文引号。");
        return false;
    }
    return true;
}

// 点击预览按钮
function previewThisArticle() {
    $("#dialog-message-preview").html($("#form-field-textarea").val());

    //override dialog's title function to allow for HTML titles
    $.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
        _title: function(title) {
            var $title = this.options.title || '&nbsp;'
            if( ("title_html" in this.options) && this.options.title_html == true )
                title.html($title);
            else title.text($title);
        }
    }));

    var dialog = $("#dialog-message-preview").removeClass('hide').dialog({
        modal: true,
        width: 500,
        title: "<div class='widget-header widget-header-small'><h5 class='smaller'><i class='icon-ok'></i> 添加一个分组 </h5></div>",
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

    //var assIds = json["associationId"].split(",");
    var suc = true;
    for (var i = 0; i <p_assid.length;i++) {
        json["associationId"] = p_assid[i];
        $.ajax({
            type: "POST",
            url: "/rest/news/add",
            data: JSON.stringify(json),
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            success: function (ret) {
                if (ret.code != 200) suc = false;
            }
        });
    }

    if (suc) {alert("已经成功添加此资讯");window.location.href = "/rest/bnews/list";}
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

