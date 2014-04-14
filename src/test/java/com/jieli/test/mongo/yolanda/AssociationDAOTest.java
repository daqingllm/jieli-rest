package com.jieli.test.mongo.yolanda;

import com.jieli.association.Association;
import com.jieli.association.AssociationDAO;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-23
 * Time: 下午4:23
 * To change this template use File | Settings | File Templates.
 */
public class AssociationDAOTest {

    private AssociationDAO associationDAO = new AssociationDAO();

    @Test
    public void initAssoc() {
        Association association = new Association();
        association.name = "上海";
        associationDAO.save(association);
    }

    @Test
    public void testGet() {
        Association association = associationDAO.loadByName("上海");
        System.out.println(association.get_id());
        System.out.println(association.name);
    }

//    @Test
    public void clearAll() {
        associationDAO.clear();
    }
}
