package com.jieli.news;

import com.jieli.activity.Activity;
import com.jieli.mongo.GenericDAO;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 14-3-31
 * Time: PM8:07
 * To change this template use File | Settings | File Templates.
 */
public class NewsDAO extends GenericDAO<News> {

    public Iterable<Activity> findOngoingOfficial(String associationId) {
        Date now = new Date();
        return col.find("{tag:#, associationId:#, beginDate:{$gt:#}}", "OFFICIAL", associationId, now).sort("{beginDate:-1}").as(Activity.class);
    }

    private boolean check(int pageNo, int pageSize){
        boolean result = true;
        if(pageNo<0 || pageSize<=0)
            return false;
        return result;
    }


    public List<News> paginate(int pageNo, int pageSize, String query, Object... params){
        if(!check(pageNo, pageSize))
            return null;

        pageNo = pageNo<=0?1:pageNo;
        Iterable<News> iterable = col.find(query, params).sort("{_id:-1}").skip((pageNo-1)*pageSize).limit(pageSize).as(News.class);

        List<News> records = new LinkedList<News>();
        for(News t : iterable){
            records.add(t);
        }

        return records;
    }

    public List<News> findWithLimit(int limit, String query, Object... params){
        if(limit<=0)
            return null;

        Iterable<News> iterable = col.find(query, params).sort("{_id:-1}").limit(limit).as(News.class);

        List<News> records = new LinkedList<News>();
        for(News t : iterable){
            records.add(t);
        }

        return records;
    }





}
