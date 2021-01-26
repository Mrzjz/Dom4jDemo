package com.signway.dom4jdemo.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author: zjz
 * @email: jz.zhao@signway.cn
 * @description:
 * @date :2020/10/27 19:31
 */
public class TouchMenuClientBean implements Serializable {
    private List<TouchMenuFiBean> fi;
    private String sn;

    public List<TouchMenuFiBean> getFi() {
        return fi;
    }

    public void setFi(List<TouchMenuFiBean> fi) {
        this.fi = fi;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
