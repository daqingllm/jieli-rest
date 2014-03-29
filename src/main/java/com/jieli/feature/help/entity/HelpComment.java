package com.jieli.feature.help.entity;

import com.jieli.feature.BaseComment;

/**
 * Created by YolandaLeo on 14-3-29.
 */
public class HelpComment extends BaseComment {
    public String helpId;
    public boolean isTop;

    public String getHelpId() {
        return helpId;
    }

    public void setHelpId(String helpId) {
        this.helpId = helpId;
    }

    public boolean isTop() { return isTop; }

    public void setTop(boolean isTop) {
        this.isTop = isTop;
    }
}
