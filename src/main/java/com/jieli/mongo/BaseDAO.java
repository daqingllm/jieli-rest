package com.jieli.mongo;

import com.jieli.util.CollectionUtils;
import org.jongo.MongoCollection;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 14-4-1
 * Time: PM8:07
 * To change this template use File | Settings | File Templates.
 */
public class BaseDAO<T> {

    protected static final Object[] NO_PARAMETERS = {};
    protected static final String ALL = "{}";

    protected final Class<T> clazz;
    protected final MongoCollection collection;

    public BaseDAO(String collectionName, Class<T> clazz) {
        this.collection = JongoClient.getInstance().getCollection(collectionName);
        this.clazz = clazz;
    }

    private boolean check(int pageNo, int pageSize){
        boolean result = true;
        if(pageNo<0 || pageSize<=0)
            return false;
        return result;
    }

    public T findOne(String query, Object... params){
        return collection.findOne(query, params).as(this.clazz);
    }

    public List<T> find(String query, Object... params){
        Iterable<T> iterable = collection.find(query, params).as(this.clazz);
        List<T> records = new LinkedList<T>();
        for(T t : iterable){
            records.add(t);
        }
        if(CollectionUtils.isEmpty(records)){
            return null;
        }
        return records;
    }

    public boolean upsert(T t, String query, Object... params){
        collection.update(query, params).upsert().merge(t);
        return true;
    }

    public void batchUpsert(List<T> list, String query, Object... params){
        if(CollectionUtils.isEmpty(list))
            return;
        for(T t : list){
            upsert(t, query, params);
        }
    }

    public boolean remove(){
        collection.remove();
        return true;
    }

    public boolean remove(String query){
        collection.remove(query);
        return true;
    }

    public boolean remove(String query, Object... params){
        collection.remove(query, params);
        return true;
    }

    public boolean save(T t){
        collection.save(t);
        return true;
    }



    public long count(){
        return collection.count(ALL);
    }

    public long count(String query) {
        return collection.count(query);
    }

    public long count(String query, Object... params) {
        return collection.count(query, params);
    }



    public List<T> paginate(int pageNo, int pageSize){
        return paginate(pageNo, pageSize, ALL);
    }

    public List<T> paginate(int pageNo, int pageSize, String query){
        return paginate(pageNo, pageSize, query, NO_PARAMETERS);
    }

    public List<T> paginate(int pageNo, int pageSize, String query, Object... params){
        if(!check(pageNo, pageSize))
            return null;

        pageNo = pageNo<=0?1:pageNo;
        Iterable<T> iterable = collection.find(query, params).sort("{_id:-1}").skip((pageNo-1)*pageSize).limit(pageSize).as(this.clazz);

        List<T> records = new LinkedList<T>();
        for(T t : iterable){
            records.add(t);
        }
        if(CollectionUtils.isEmpty(records)){
            return null;
        }

        return records;
    }

}
