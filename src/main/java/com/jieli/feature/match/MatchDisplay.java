package com.jieli.feature.match;

import java.io.Serializable;
import java.util.List;

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
    public String phone1;

    public String userId2;
    public String name2;
    public String userFace2;
    public String phone2;

    public int score;

    public List<String> infos;

    public String getUserId1() {
        return userId1;
    }

    public void setUserId1(String userId1) {
        this.userId1 = userId1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getUserFace1() {
        return userFace1;
    }

    public void setUserFace1(String userFace1) {
        this.userFace1 = userFace1;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getUserId2() {
        return userId2;
    }

    public void setUserId2(String userId2) {
        this.userId2 = userId2;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getUserFace2() {
        return userFace2;
    }

    public void setUserFace2(String userFace2) {
        this.userFace2 = userFace2;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getInfos() {
        return infos;
    }

    public void setInfos(List<String> infos) {
        this.infos = infos;
    }
}
