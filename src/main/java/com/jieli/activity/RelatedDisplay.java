package com.jieli.activity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-28
 * Time: 下午11:06
 * To change this template use File | Settings | File Templates.
 */
public class RelatedDisplay implements Serializable {

    private static final long serialVersionUID = 1L;

    public String activityId;

    public String title;

    public RelatedType type;

    public Date time;
}
