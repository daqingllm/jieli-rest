package com.jieli.association;

import com.jieli.mongo.Model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-22
 * Time: 下午5:40
 * To change this template use File | Settings | File Templates.
 */
public class Association extends Model {

    public String name;

    //协会内非普通的成员
    List<Identity> headers;

    //协会身份
    public class Identity {

        String userId;

        String identity;
    }
}
