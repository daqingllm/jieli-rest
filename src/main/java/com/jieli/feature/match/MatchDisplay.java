package com.jieli.feature.match;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-20
 * Time: 下午7:57
 * To change this template use File | Settings | File Templates.
 */
public class MatchDisplay implements Serializable {

    private static final long serialVersionUID = 1L;

    public String userId1;
    public String name1;
    public String userFace1;

    public String userId2;
    public String name2;
    public String userFace2;

    public int score;
}