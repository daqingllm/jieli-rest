package com.jieli.news;

import com.jieli.mongo.GenericDAO;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-5-14
 * Time: 上午1:39
 * To change this template use File | Settings | File Templates.
 */
public class ImageDAO extends GenericDAO<Image> {

    public Iterable<Image> loadCoverImages() {
        return col.find().sort("{_id:-1}").limit(4).as(Image.class);
    }
}
