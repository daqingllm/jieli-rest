package com.jieli.service;

import com.jieli.common.entity.ResponseEntity;
import com.jieli.util.UploaderUtils;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * Created by xianxing on 2014/3/22.
 * 上传图像
 */
@Singleton
@Path("/")
public class UploadService {
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA+ ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail){

        ResponseEntity responseEntity = new ResponseEntity();
        String FileName = "";
		String FileDirectory = "F:\\";
        FileDirectory = System.getProperty("user.dir")+"\\";

        try {
            FileName = fileDetail.getFileName();
            FileName = new String(FileName.getBytes(System.getProperty("file.encoding")), "UTF-8");
        }catch (Exception e){}
        if (FileName == null && FileName == "") {
            responseEntity.code = 2001;
            responseEntity.msg = "请选择文件";
            return Response.status(200).entity(responseEntity).build();
        }
        String FilePath = "";
		try{
            FilePath = UploaderUtils.writeToFile(uploadInputStream,FileDirectory,FileName);

            responseEntity.code = 200;
            responseEntity.body = FilePath;
            responseEntity.msg = "已经成功上传文件：" + FileName;
		}catch(Exception e){
		    responseEntity.code = 2002;
            responseEntity.msg = "上传失败";
        }

		String RetFileName = "http://"+UploaderUtils.GetBucketName()+".qiniudn.com/"+UploaderUtils.Upload7Niu(FileDirectory,FilePath);
        if (responseEntity.code == 200) responseEntity.body = RetFileName;

        return Response.status(200).entity(responseEntity).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON+";charset=UTF-8").build();
    }

    // test use
    @GET
    @Path("/upload")
    @Produces(MediaType.TEXT_HTML+ ";charset=utf-8")
    public Response uploadFile(){
	
		/*return Response.status(200).entity("<!DOCTYPE html>" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title></title>\n" +
                "    <script type=\"text/javascript\" src=\"http://www.w3school.com.cn/jquery/jquery.js\"></script>" +
                "    <script type='text/javascript'>" +
                "        function submitUpload4(){" +
                "           var t = $(\":file\")[0].value;" +
                "           t = t.substr(t.indexOf('fakepath')+9);" +
                "           $('#key').val(t);" +
                "           var form1 = document.getElementById(\"form1\");" +
                "           var data1 = new FormData(form1);" +
                "           $.ajax({url:'http://up.qiniu.com',data:data1,cache:false,contentType:false,processData:false,type:'POST',success:function(data){alert(data);}});" +
                "        }" +
                "        function submitUpload(){" +
                "            var t = $(\":file\")[0].value;" +
                "            t = t.substr(t.indexOf('fakepath')+9);" +
                "            $('#key').val(t);" +
                "            $('#form1').submit();"+
                "        }" +
                "    </script>" +
                "</head>\n" +
                "<body>\n" +
                "<form id='form1' method='post' action='http://up.qiniu.com/' enctype='multipart/form-data'>"+
                "<input type='file' name='file' size='500' />" +
                "<input type='hidden' name='key' value='upload-from-html' id='key' />" +
                "<input type='hidden' name='x:username' value='xianxing-myself' />" +
                "<input type='hidden' name='token' value='BC1Z-BtDVW8dVxdbBBUXc59k1fIZievdyGCQfFj9:WSVoOZWZ1XjH5xYAHRIPqtO5bXM=:eyJzY29wZSI6InhpYW54aW5nLXRlc3QiLCJkZWFkbGluZSI6MTM5NjEwMjk0N30=' />" +
                "<input type=\"button\" value=\"Upload\" onclick='submitUpload()' />\n" +
                "</form>"+
                "</body>\n" +
                "</html>").header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML + ";charset=UTF-8").build();
		*/

        /*return Response.status(200).entity("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title></title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form action=\"/app/file/upload\" method=\"post\" enctype=\"multipart/form-data\" acceptcharset=\"UTF-8\">\n" +
                "    <input type=\"file\" name=\"file\" size=\"500\" />\n" +
                "    <input type=\"submit\" value=\"Upload\" />\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>").header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML + ";charset=UTF-8").build();*/

        return Response.status(200).build();
    }
}
