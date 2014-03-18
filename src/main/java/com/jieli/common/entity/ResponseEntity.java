package com.jieli.common.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-14
 * Time: 下午11:44
 * To change this template use File | Settings | File Templates.
 */
public class ResponseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public int code;

    public String msg;

    public Object body;
}
