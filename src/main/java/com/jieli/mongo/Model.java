package com.jieli.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bson.types.ObjectId;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-8
 * Time: 下午8:53
 * To change this template use File | Settings | File Templates.
 */
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("_id")
    protected ObjectId objectId;

    @XmlJavaTypeAdapter(ObjectIdAdaptor.class)
    public org.bson.types.ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(org.bson.types.ObjectId id) {
        this.objectId = id;
    }

    public int id = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model)) return false;

        Model model = (Model) o;

        if (!objectId.equals(model.objectId)) return false;
        if (id != model.id) return false;

        return true;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public int hashCode() {
        int result = objectId.hashCode();
        result = 31 * result + id;
        return result;
    }
}
