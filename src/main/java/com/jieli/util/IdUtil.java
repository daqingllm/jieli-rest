package com.jieli.util;

import com.jieli.dao.IdDAO;
import com.jieli.entity.common.IdEntity;

/**
 * 新增对象ID获取
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-15
 * Time: 上午12:00
 * To change this template use File | Settings | File Templates.
 */
public abstract class IdUtil {

    private static IdDAO idDAO = new IdDAO();

    public static int getUserId() {
        IdEntity idEntity = handleInit();
        int userId = idEntity.userId++;
        idDAO.save(idEntity);
        return userId;
    }

    public static int getNewsId() {
        IdEntity idEntity = handleInit();
        int newsId = idEntity.newsId++;
        idDAO.save(idEntity);
        return newsId;
    }

    public static int getEventId() {
        IdEntity idEntity = handleInit();
        int eventId = idEntity.eventId++;
        idDAO.save(idEntity);
        return eventId;
    }

    private static IdEntity handleInit() {
        IdEntity idEntity = idDAO.loadById(1);
        if (idEntity == null) {
            idEntity = new IdEntity();
            idEntity.id = 1;
        }
        return idEntity;
    }
}
