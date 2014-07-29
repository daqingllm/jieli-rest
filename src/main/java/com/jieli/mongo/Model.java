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

    @XmlJavaTypeAdapter(ObjectIdAdaptor.class)
    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    @JsonProperty("_id")
    protected ObjectId _id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model)) return false;

        Model model = (Model) o;

        if (!_id.equals(model._id)) return false;

        return true;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public int hashCode() {
        if (_id != null)
            return _id.hashCode();
        else
            return -1;
    }
}
