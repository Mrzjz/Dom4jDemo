package com.signway.dom4jdemo.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author: zjz
 * @email: jz.zhao@signway.cn
 * @description:
 * @date :2020/10/27 19:31
 */
public class TouchMenuPublicBean implements Serializable {
    private List<TouchMenuFiBean> fi;

    public List<TouchMenuFiBean> getFi() {
        return fi;
    }

    public void setFi(List<TouchMenuFiBean> fi) {
        this.fi = fi;
    }
}
