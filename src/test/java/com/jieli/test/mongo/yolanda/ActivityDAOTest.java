package com.jieli.test.mongo.yolanda;

import com.jieli.activity.*;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-29
 * Time: 上午11:53
 * To change this template use File | Settings | File Templates.
 */
public class ActivityDAOTest {

    ActivityDAO activityDAO = new ActivityDAO();
    RelatedActivityDAO relatedActivityDAO = new RelatedActivityDAO();

    @Test
    public void testAll() {
//        activityDAO.save(prepareActivity());
        Iterable<Activity> activities = activityDAO.loadAll();
        for (Activity activity : activities) {
            System.out.println(activity.get_id());
        }
    }

    @Test
    public void testOfficial() {
//        activityDAO.clear();
        Activity activity = prepareActivity();
        activityDAO.save(activity);
        Iterable<Activity> activities = activityDAO.findOngoingOfficial("testAssId");
        for (Activity activity1 : activities) {
            System.out.println(activity1.get_id());
        }
    }

    private Activity prepareActivity() {
        Activity activity = new Activity();
        activity.associationId = "testAssId";
        activity.tag = AcivityTag.OFFICIAL;
        activity.beginDate = new Date(new Date().getTime() + 1000000);
        return activity;
    }

    @Test
    public void testActivityInfo() {
//        RelatedActivity relatedActivity = new RelatedActivity();
//        relatedActivity.userId = "test";
//        List<ActivityInfo> infos = new ArrayList<ActivityInfo>();
//        ActivityInfo info = new ActivityInfo();
//        info.activityId = "53365609ef863a506d4223d0";
//        infos.add(info);
//        ActivityInfo info1 = new ActivityInfo();
//        info1.activityId = "5336787cef8635d75a9b5c97";
//        infos.add(info1);
//        relatedActivity.infos = infos;
//        relatedActivityDAO.save(relatedActivity);

        List<ActivityInfo> activityInfos = relatedActivityDAO.findUserActivities("test");
        for (ActivityInfo activityInfo : activityInfos) {
            System.out.println(activityInfo.activityId);
        }
    }
}
