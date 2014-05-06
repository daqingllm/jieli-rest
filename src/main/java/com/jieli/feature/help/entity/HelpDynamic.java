package com.jieli.feature.help.entity;

import com.jieli.mongo.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YolandaLeo on 14-5-6.
 */
public class HelpDynamic extends Model {
    public String userId;
    public List<HelpDynamicShow> infos = new ArrayList<HelpDynamicShow>();

}
