package com.jieli.feature.vote.entity;

import com.jieli.feature.help.entity.HelpDynamicType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-28
 * Time: 下午11:06
 * To change this template use File | Settings | File Templates.
 */
public class VoteDynamicDisplay implements Serializable {

    private static final long serialVersionUID = 1L;

    public String voteId;

    public String title;

    public VoteDynamicType type;

    public Date time;
}
