package com.jieli.dao;

import com.jieli.entity.Category;
import com.jieli.mongo.GenericDAO;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-8
 * Time: 下午9:07
 * To change this template use File | Settings | File Templates.
 */
public class CategoryDAO extends GenericDAO<Category> {

    public Iterable<Category> loadAll() {
        return col.find().as(Category.class);
    }
}
