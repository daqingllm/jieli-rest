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

    public Iterable<Image> loadCoverImages(String associationId) {
        return col.find("{associationId:#}", associationId).sort("{_id:-1}").limit(4).as(Image.class);
    }

    public Iterable<Image> loadCoverImages(String associationId, String type){
        if (NewsType.enterpriseType.equals(type) ||
                NewsType.associationType.equals(type)) {
            return col.find("{associationId:#,type:#}", associationId, type).sort("{_id:-1}").limit(4).as(Image.class);
        } else {
            return col.find("{associationId:#}", associationId).sort("{_id:-1}").limit(4).as(Image.class);
        }
    }

    public void deleteByNewsId(String newsId){
        col.remove("{newsId:#}",newsId);
    }
}
