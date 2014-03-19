package com.jieli.service;

import com.jieli.feature.help.entity.Comment;

import java.util.List;

/**
 * 功能列表页接口
 * 包括互帮互助、默契匹配、投票列表
 * Created by YolandaLeo on 14-3-19.
 */
public interface FeatureService {
    /**
     * 互帮互助详情页获取评论列表
     * @param helpId
     * @return
     */
    public List<Comment> getHelpComment(Integer helpId);

    /**
     * 获取互帮互助置顶评论，大于1个
     * 按照时间排序，最早的在最前面
     * @param helpId
     * @return
     */
    public List<Comment> getTopComment(Integer helpId);

    /**
     * 增加评论
     * @param helpId
     * @param userId
     * @return
     */
    public Integer addComment(Integer helpId, Integer userId);

    /**
     * 删除评论
     * @param helpId
     * @return
     */
    public boolean deleteComment(Integer helpId, Integer userId);

    /**
     * 加关注
     * @param helpId
     */
    public void addFocus(Integer helpId);
}
