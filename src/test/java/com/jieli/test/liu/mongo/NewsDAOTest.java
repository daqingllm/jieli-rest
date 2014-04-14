package com.jieli.test.liu.mongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.news.News;
import com.jieli.news.NewsDAO;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-11
 * Time: 下午10:52
 * To change this template use File | Settings | File Templates.
 */
public class NewsDAOTest {

    NewsDAO newsDAO = new NewsDAO();

    @Test
    public void testNews() throws JsonProcessingException {
        News news = new News();
        news.title = "abnews";
        newsDAO.save(news);
        System.out.println(new ObjectMapper().writeValueAsString(news));
    }
}
