package com.jieli.feature.help.dao;

import com.jieli.feature.help.entity.Comment;
import com.jieli.feature.help.entity.HelpInfo;
import com.jieli.feature.help.entity.SimpleHelpInfo;
import com.jieli.mongo.GenericDAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 互帮互助数据库读取
 * Created by YolandaLeo on 14-3-19.
 */
public class HelpDAO extends GenericDAO<HelpInfo>{
    /**
     * 获取单个互帮互助详情
     * @param helpId
     * @return
     */
    public HelpInfo loadHelpInfo(String helpId) {
        return col.findOne("helpId:#",helpId).as(HelpInfo.class);
    }

    /**
     * 获取协会内互帮互助帖子列表
     * @param associationId
     * @return
     */
    public List<SimpleHelpInfo> getHelpInfoList(String associationId) {
        Iterator<SimpleHelpInfo> iterator = col.find("associationId:#", associationId).as(SimpleHelpInfo.class).iterator();
        List<SimpleHelpInfo> resultList = new ArrayList<SimpleHelpInfo>();
        for(;iterator.hasNext();) {
            resultList.add(iterator.next());
        }
        return resultList;
    }

}
