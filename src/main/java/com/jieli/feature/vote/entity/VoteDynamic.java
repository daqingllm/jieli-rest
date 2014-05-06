package com.jieli.feature.vote.entity;

import com.jieli.mongo.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YolandaLeo on 14-5-6.
 */
public class VoteDynamic extends Model {
    public String userId;
    public List<VoteDynamicShow> infos = new ArrayList<VoteDynamicShow>();

}
