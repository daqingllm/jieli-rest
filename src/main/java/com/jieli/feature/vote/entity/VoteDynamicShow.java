package com.jieli.feature.vote.entity;


import java.util.Date;

/**
 * Created by YolandaLeo on 14-5-6.
 */
public class VoteDynamicShow {
    public String voteId;
    public VoteDynamicType type;
    public Date time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoteDynamicShow)) return false;

        VoteDynamicShow info = (VoteDynamicShow) o;

        if (voteId != null ? !voteId.equals(info.voteId) : info.voteId != null) return false;
        if (type != info.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = voteId != null ? voteId.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
