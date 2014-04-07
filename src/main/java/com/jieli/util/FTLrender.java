package com.jieli.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 14-4-4
 * Time: PM9:32
 * To change this template use File | Settings | File Templates.
 */
public abstract class FTLrender {

    private static final Map<String, Template> templateRepo = new ConcurrentHashMap<String, Template>();

    /**
     * render ftl toString
     * @param ftlName must be under the path of "resources/pages/"
     * @param params key-value
     * @return
     */
    public static String getResult(String ftlName, Map<String, Object> params) {
        try {
            Template template = getTemplate(ftlName);
            String result = render(template, params);
            return result;
        } catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }
    }



    private static Template getTemplate(String ftlName) throws IOException {
        Template template = templateRepo.get(ftlName);
        if(template==null){
            Configuration cfg = new Configuration();
            ClassPathResource loader = new ClassPathResource("/pages/"+ftlName, FTLrender.class.getClassLoader());
            InputStream inputStream = loader.getInputStream();
            OutputStream outputStream = new FileOutputStream(ftlName);
            byte[] bs = new byte[1024];
            int len;
            while ((len = inputStream.read(bs)) != -1) {
                outputStream.write(bs, 0, len);
            }
            outputStream.close();
            inputStream.close();

            template = cfg.getTemplate(ftlName,"GBK");
            templateRepo.put(ftlName, template);

            File file = new File(ftlName);
            file.delete();
        }
        return template;
    }


    private static String render(Template template, Map<String, Object> params) throws IOException, TemplateException {
        String result = "";
        StringWriter sw = null;
        try {
            sw = new StringWriter();
            template.process(params, sw);
            StringBuffer sb = sw.getBuffer();
            result = sb.toString();
        } finally {
            if(sw!=null)
                sw.close();
        }
        return result;
    }

}
