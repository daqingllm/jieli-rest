package com.jieli.feature.help.dao;

import com.jieli.feature.help.entity.HelpDynamic;
import com.jieli.feature.help.entity.HelpDynamicShow;
import com.jieli.mongo.GenericDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by YolandaLeo on 14-5-6.
 */
public class HelpDynamicDAO extends GenericDAO<HelpDynamic> {
    public List<HelpDynamicShow> findUserHelp(String userId) {
        HelpDynamic helpDynamic = col.findOne("{userId:#}", userId).as(HelpDynamic.class);
        if (helpDynamic == null) {
            return new ArrayList<HelpDynamicShow>();
        }
        Collections.sort(helpDynamic.infos, new InfoComparator());
        return helpDynamic.infos;
    }

    public HelpDynamic findByUserId(String userId) {
        HelpDynamic helpDynamic = col.findOne("{userId:#}", userId).as(HelpDynamic.class);
        return helpDynamic;
    }

    public class InfoComparator implements Comparator<HelpDynamicShow> {

        @Override
        public int compare(HelpDynamicShow o1, HelpDynamicShow o2) {
            return 0 - o1.helpId.compareTo(o2.helpId);
        }
    }
}
