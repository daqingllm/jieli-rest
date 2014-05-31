package com.jieli.service;

import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.Account;
import com.jieli.common.entity.AccountState;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.util.IdentityUtils;
import com.jieli.util.UploaderUtils;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xianxing on 2014/3/22.
 * 上传图像
 */
@Singleton
@Path("/")
public class UploadService {
    private AccountDAO accountDAO = new AccountDAO();

    @POST
    @Path("/uploadExcel")
    @Consumes(MediaType.MULTIPART_FORM_DATA+ ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response uploadExcelFile(@CookieParam("u")String sessionId,
            @FormDataParam("file") InputStream uploadInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail){
        Account account = accountDAO.loadById(sessionId);

        if (account == null || account.state != AccountState.ADMIN) {
            return Response.status(403).build();
        }

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

            ArrayList<String> nameList = new ArrayList<String>();
            int [] result = null;

            if (FileName.endsWith("csv"))
                result = UploaderUtils.InitUserByCSV(FilePath,nameList,account.associationId);
            else {
                responseEntity.code = 2004;
                responseEntity.msg = "请将您的表格文件另存为csv格式再尝试导入用户";
                return Response.status(200).entity(responseEntity).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON+";charset=UTF-8").build();
            }

            if (result == null || result.length == 0){
                responseEntity.code = 2003;
                responseEntity.msg = "无数据或无法解析文件数据";
            } else if (result.length == 3){
                responseEntity.code = 200;
                responseEntity.msg = "从文件解析到" + result[0] + "条数据，成功导入" + result[1] + "条，失败" + result[2] + "条";
                if (result[2] != 0){
                    responseEntity.msg += ". 可能的失败原因有：性别不是男/女，生日格式不对，分组不存在，职位不存在，该手机号码已被其他用户注册，信息不全！";
                }
                responseEntity.body = nameList;
            } else {
                responseEntity.code = 2003;
                responseEntity.msg = "未知错误";
            }

        }catch(Exception e){
            responseEntity.code = 2002;
            responseEntity.msg = "导入失败，错误信息"+e.toString();
        }

        return Response.status(200).entity(responseEntity).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON+";charset=UTF-8").build();
    }

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
        String RetFileName = "";

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
            RetFileName = "http://"+UploaderUtils.GetBucketName()+".qiniudn.com/"+UploaderUtils.Upload7Niu(FileDirectory,FilePath);

            responseEntity.code = 200;
            responseEntity.msg = "已经成功上传文件：" + FileName;
            responseEntity.body = RetFileName;
		}catch(Exception e){
		    responseEntity.code = 2002;
            responseEntity.msg = "上传失败";
        }

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
