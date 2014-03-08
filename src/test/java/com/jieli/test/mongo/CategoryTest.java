package com.jieli.test.mongo;

import com.jieli.dao.CategoryDAO;
import com.jieli.entity.Category;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-8
 * Time: 下午9:31
 * To change this template use File | Settings | File Templates.
 */
public class CategoryTest {

    @Test
    public void testDB() {
        CategoryDAO categoryDAO = new CategoryDAO();
        Category category = new Category();
        category.name = "First Category";
        category.displayName = "步行街";
        categoryDAO.save(category);
        Iterable<Category> categories = categoryDAO.loadAll();
        for (Category cate : categories) {
            System.out.println(cate.displayName);
        }
        categoryDAO.deleteByName("First Category");
    }
}
