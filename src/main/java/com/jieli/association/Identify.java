package com.jieli.association;

import com.jieli.mongo.Model;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-5-7
 * Time: 下午8:17
 * To change this template use File | Settings | File Templates.
 */
public class Identify extends Model {

    public String name;

    public String getAssociationId() {
        return associationId;
    }

    public String getName() {
        return name;
    }

    public String associationId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        if (!super.equals(o)) return false;

        Group group = (Group) o;

        if (!associationId.equals(group.associationId)) return false;
        if (!name.equals(group.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + associationId.hashCode();
        return result;
    }
}
