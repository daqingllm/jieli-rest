package com.jieli.service;

import com.jieli.common.entity.ResponseEntity;
import com.jieli.util.UploaderUtils;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xianxing on 2014/3/22.
 * 上传图像
 */
//@Path("file")
public class UploadService {
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA+ ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail){

        ResponseEntity responseEntity = new ResponseEntity();
        String FileName =fileDetail.getFileName();
		String FileDirectory = "uploadFiles\\" + new SimpleDateFormat("yyyyMM").format(new Date()) + "\\";
        try {
            FileName = new String(FileName.getBytes(System.getProperty("file.encoding")), "UTF-8");
        }catch (Exception e){}
        if (FileName == null && FileName == "") {
            responseEntity.code = 2001;
            responseEntity.msg="请选择文件";
            return Response.status(200).entity(responseEntity).build();
        }
		try{
        String FilePath = UploaderUtils.writeToFile(uploadInputStream,FileDirectory,FileName);
        responseEntity.code = 200;
        responseEntity.body = FilePath;
        responseEntity.msg = "已经成功上传文件：" + FileName;
		}catch(Exception e){
		responseEntity.code = 2002;
        responseEntity.msg = "上传失败";}
        return Response.status(200).entity(responseEntity).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON+";charset=UTF-8").build();
    }

    // test use
    @GET
    @Path("/upload")
    @Produces(MediaType.TEXT_HTML+ ";charset=utf-8")
    public Response uploadFile(){
        return Response.status(200).entity("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title></title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form action=\"/rest/file/upload\" method=\"post\" enctype=\"multipart/form-data\" acceptcharset=\"UTF-8\">\n" +
                "    <input type=\"file\" name=\"file\" size=\"500\" />\n" +
                "    <input type=\"submit\" value=\"Upload\" />\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>").header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML + ";charset=UTF-8").build();
    }
}
