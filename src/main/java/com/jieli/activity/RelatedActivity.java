package com.jieli.activity;

import com.jieli.mongo.Model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-29
 * Time: 下午2:46
 * To change this template use File | Settings | File Templates.
 */
public class RelatedActivity extends Model {

    public String userId;

    public List<ActivityInfo> infos;

}
