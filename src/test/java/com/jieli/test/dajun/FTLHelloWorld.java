package com.jieli.test.dajun;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 14-4-4
 * Time: PM8:56
 * To change this template use File | Settings | File Templates.
 */
public class FTLHelloWorld {


    public static void main(String[] args) throws Exception {
        String result = "";
        Configuration cfg = new Configuration();
        Template template = cfg.getTemplate("src/main/webapp/WEB-INF/pages/helloworld.ftl");
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("message", "Hello World!好吃");

        StringWriter sw = null;
        BufferedWriter bw = null;
        try {
            sw = new StringWriter();
            template.process(data, sw);
            bw = new BufferedWriter(sw);
            bw.write(result);
            bw.flush();
            StringBuffer sb = sw.getBuffer();
            System.out.println(sb);
        } finally {
            if(sw!=null)
                sw.close();
            if(bw!=null)
                bw.close();
        }

    }


}
