package com.jieli.test.mongo;

import com.jieli.feature.help.dao.HelpDAO;
import com.jieli.feature.help.entity.HelpInfo;
import org.junit.Test;

import java.util.Date;

/**
 * Created by YolandaLeo on 14-3-30.
 */
public class HelpDaoTest {
    HelpDAO helpDao = new HelpDAO();
    @Test
    public void addHelpTest() {
        HelpInfo helpInfo = new HelpInfo();
        helpInfo.setUserId("5336bbe13004cc09f49432e6");
        helpInfo.setAttentionNum(0);
        helpInfo.setAddTime(new Date());
        helpInfo.setAssociationId();
    }
}
