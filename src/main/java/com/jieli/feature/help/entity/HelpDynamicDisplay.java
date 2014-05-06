package com.jieli.feature.help.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-28
 * Time: 下午11:06
 * To change this template use File | Settings | File Templates.
 */
public class HelpDynamicDisplay implements Serializable {

    private static final long serialVersionUID = 1L;

    public String helpId;

    public String title;

    public HelpDynamicType type;

    public Date time;
}
