package com.signway.dom4jdemo.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author: zjz
 * @email: jz.zhao@signway.cn
 * @description:
 * @date :2020/10/27 19:31
 */

public class TouchMenuBean implements Serializable {
    private String id;
    @JSONField(alternateNames = "public")
    private TouchMenuPublicBean publics;
    @JSONField(alternateNames = "private")
    private TouchMenuPrivateBean privates;
    private String version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TouchMenuPublicBean getPublics() {
        return publics;
    }

    public void setPublics(TouchMenuPublicBean publics) {
        this.publics = publics;
    }

    public TouchMenuPrivateBean getPrivates() {
        return privates;
    }

    public void setPrivates(TouchMenuPrivateBean privates) {
        this.privates = privates;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
