package com.jieli.feature.help.entity;

import java.util.Date;

/**
 * Created by YolandaLeo on 14-5-6.
 */
public class HelpDynamicShow {
    public String helpId;
    public HelpDynamicType type;
    public Date time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HelpDynamicShow)) return false;

        HelpDynamicShow info = (HelpDynamicShow) o;

        if (helpId != null ? !helpId.equals(info.helpId) : info.helpId != null) return false;
        if (type != info.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = helpId != null ? helpId.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
