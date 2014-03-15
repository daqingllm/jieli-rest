package com.jieli.mongo;

import org.apache.commons.lang.StringUtils;
import org.jongo.MongoCollection;

import java.lang.reflect.ParameterizedType;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-8
 * Time: 下午8:49
 * To change this template use File | Settings | File Templates.
 */
public abstract class GenericDAO<T extends Model> {
    protected final MongoCollection col;

    public GenericDAO() {
        this.col = JongoClient.getInstance().getCollection(StringUtils.lowerCase(getType().getSimpleName()));
    }

    public T loadById(int id) {
        return col.findOne("{id:#}", id).as(getType());
    }

    public Iterable<T> find(String query, Object... params) {
        return col.find(query, params).as(getType());
    }

    public T save(T t) {
        col.save(t);
        return t;
    }

    public T updateById(T t) {
        col.update("{id:'" + t.id + "'}").merge(t);
        return t;
    }

    public void deleteById(int id) {
        col.remove("{id:#}", id);
    }

    //慎用，测试使用
    public void clear() {
        col.remove();
    }

    private Class<T> getType(){
        ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[0];
    }
}
