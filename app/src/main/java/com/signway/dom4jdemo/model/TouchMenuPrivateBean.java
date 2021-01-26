package com.signway.dom4jdemo.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author: zjz
 * @email: jz.zhao@signway.cn
 * @description:
 * @date :2020/10/27 19:31
 */
public class TouchMenuPrivateBean implements Serializable {
    private List<TouchMenuClientBean> client;

    public List<TouchMenuClientBean> getClient() {
        return client;
    }

    public void setClient(List<TouchMenuClientBean> client) {
        this.client = client;
    }
}
