package com.jieli.feature.vote.dao;

import com.jieli.feature.vote.entity.VoteDynamic;
import com.jieli.feature.vote.entity.VoteDynamicShow;
import com.jieli.mongo.GenericDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by YolandaLeo on 14-5-6.
 */
public class VoteDynamicDAO extends GenericDAO<VoteDynamic> {
    public List<VoteDynamicShow> findUserVote(String userId) {
        VoteDynamic voteDynamic = col.findOne("{userId:#}", userId).as(VoteDynamic.class);
        if (voteDynamic == null) {
            return new ArrayList<VoteDynamicShow>();
        }
        Collections.sort(voteDynamic.infos, new InfoComparator());
        return voteDynamic.infos;
    }

    public VoteDynamic findByUserId(String userId) {
        VoteDynamic voteDynamic = col.findOne("{userId:#}", userId).as(VoteDynamic.class);
        return voteDynamic;
    }

    public class InfoComparator implements Comparator<VoteDynamicShow> {

        @Override
        public int compare(VoteDynamicShow o1, VoteDynamicShow o2) {
            return 0 - o1.voteId.compareTo(o2.voteId);
        }
    }
}
