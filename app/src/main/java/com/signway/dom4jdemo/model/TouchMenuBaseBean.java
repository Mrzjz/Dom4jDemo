package com.signway.dom4jdemo.model;

import java.io.Serializable;

/**
 * @author: zjz
 * @email: jz.zhao@signway.cn
 * @description:
 * @date :2020/10/27 19:31
 */

public class TouchMenuBaseBean implements Serializable {
    private TouchMenuBean TouchMenu;

    public TouchMenuBean getTouchMenu() {
        return TouchMenu;
    }

    public void setTouchMenu(TouchMenuBean touchMenu) {
        TouchMenu = touchMenu;
    }
}
