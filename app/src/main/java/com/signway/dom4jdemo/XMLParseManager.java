package com.signway.dom4jdemo;

import android.util.Log;


import com.signway.dom4jdemo.model.TouchMenuBaseBean;
import com.signway.dom4jdemo.model.TouchMenuBean;
import com.signway.dom4jdemo.model.TouchMenuClientBean;
import com.signway.dom4jdemo.model.TouchMenuFiBean;
import com.signway.dom4jdemo.model.TouchMenuPrivateBean;
import com.signway.dom4jdemo.model.TouchMenuPublicBean;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: zjz
 * @email: jz.zhao@signway.cn
 * @description: TouchMenu 解析
 * @date :2020/6/17 15:20
 */
public class XMLParseManager {
    private final String TAG = XMLParseManager.class.getSimpleName();
    private static class Holder {
        private static final XMLParseManager INSTANCE = new XMLParseManager();
    }

    public static final XMLParseManager getInstance() {
        return XMLParseManager.Holder.INSTANCE;
    }


    public TouchMenuBaseBean parseTouchMenu(String path) {
        Log.i(TAG,"parseTouchMenu --- > path = " + path);
        File file = new File(path);
        if (!file.exists()) {
            Log.i(TAG,"parseTouchMenu --- > file is not exists");
            return null;

        }

        if (!file.canRead()) {
            Log.i(TAG,"parseTouchMenu --- > file is not canRead");
            return null;
        }


        try {
            SAXReader reader = new SAXReader();
            //2、通过read方法加载XML文档，并获得Document文档对象
            Document doc = reader.read(file);

            //3、获得根元素
            Element e = doc.getRootElement();
            //4、遍历根元素下面所有子元素
            TouchMenuBean touchMenuBean = praseChildElement(e);

            TouchMenuBaseBean baseBean = new TouchMenuBaseBean();
            baseBean.setTouchMenu(touchMenuBean);
            return baseBean;
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return null;
    }


    //遍历指定元素下面的所有子元素
    private TouchMenuBean praseChildElement(Element e) {
        System.out.println(e.getName());
        //获取元素下面的所有子元素
        Element elementPublic = e.element(TouchMenuTag.TAG_PUBLIC);
        Element elementPrivate = e.element(TouchMenuTag.TAG_PRIVATE);
        TouchMenuBean bean = new TouchMenuBean();

        bean.setId(e.attributeValue(TouchMenuTag.TAG_ID));
        bean.setVersion(e.attributeValue(TouchMenuTag.TAG_VERSION));

        bean.setPublics(parsePublicEle(elementPublic));
        bean.setPrivates(parsePrivateEle(elementPrivate));

        Log.i(TAG,"praseChildElement ------- end");
        return bean;
    }


    private TouchMenuPublicBean parsePublicEle(Element element) {
        TouchMenuPublicBean publicBean = new TouchMenuPublicBean();
        List<Element> list = element.elements(TouchMenuTag.TAG_FI);
        List<TouchMenuFiBean> fiBeans = new ArrayList<>();
        for(Element c:list){
            TouchMenuFiBean fiBean = new TouchMenuFiBean();
            fiBean.setMode2(c.attributeValue(TouchMenuTag.TAG_MODE2));
            fiBean.setName(c.attributeValue(TouchMenuTag.TAG_NAME));
            fiBean.setPath(c.attributeValue(TouchMenuTag.TAG_PATH));
            fiBean.setPath2(c.attributeValue(TouchMenuTag.TAG_PATH2));
            fiBean.setSize(c.attributeValue(TouchMenuTag.TAG_SIZE));
            fiBean.setSignature(c.attributeValue(TouchMenuTag.TAG_SIGNATURE));

            Element s3url = c.element(TouchMenuTag.TAG_S3URL);
            if (s3url != null) {
                String url = s3url.getText();
                if (url.contains("<![CDATA[")) {
                    url = trimStr(url,"<![CDATA[");
                    url = trimStr(url,"]]>");
                }
                //Log.i(TAG,"praseChildElement --- > url = " + url);
                fiBean.setS3URL(url);
            }

            fiBeans.add(fiBean);
        }

        publicBean.setFi(fiBeans);

        return publicBean;
    }

    private TouchMenuPrivateBean parsePrivateEle(Element element) {
        TouchMenuPrivateBean privateBean = new TouchMenuPrivateBean();
        List<Element> list = element.elements(TouchMenuTag.TAG_CLIENT);

        List<TouchMenuClientBean> clientBeans = new ArrayList<>();
        for(Element c:list){

            TouchMenuClientBean clientBean = new TouchMenuClientBean();
            List<TouchMenuFiBean> fiBeans = new ArrayList<>();



            List<Element> fiEles = c.elements(TouchMenuTag.TAG_FI);
            for (Element f : fiEles) {
                TouchMenuFiBean fiBean = new TouchMenuFiBean();
                //fiBean.setMode2(fiEle.attributeValue(TouchMenuTag.TAG_MODE2));
                fiBean.setName(f.attributeValue(TouchMenuTag.TAG_NAME));
                fiBean.setPath(f.attributeValue(TouchMenuTag.TAG_PATH));
                fiBean.setPath2(f.attributeValue(TouchMenuTag.TAG_PATH2));
                fiBean.setSize(f.attributeValue(TouchMenuTag.TAG_SIZE));
                fiBean.setSignature(f.attributeValue(TouchMenuTag.TAG_SIGNATURE));

                Element s3url = f.element(TouchMenuTag.TAG_S3URL);
                if (s3url != null) {
                    String url = s3url.getText();
                    if (url.contains("<![CDATA[")) {
                        url = trimStr(url,"<![CDATA[");
                        url = trimStr(url,"]]>");
                    }
                    //Log.i(TAG,"parsePrivateEle --- > url = " + url);
                    fiBean.setS3URL(url);
                }
                fiBeans.add(fiBean);
            }


            clientBean.setFi(fiBeans);

            Element snEle = c.element(TouchMenuTag.TAG_SN);
            String sn = snEle.getText();
            //Log.i(TAG,"parsePrivateEle --- > sn = " + sn);
            clientBean.setSn(sn);

            clientBeans.add(clientBean);
        }

        privateBean.setClient(clientBeans);

        return privateBean;
    }

    private static String trimStr(String str, String indexStr){
        if(str == null){
            return null;
        }
        StringBuilder newStr = new StringBuilder(str);
        if(newStr.indexOf(indexStr) == 0){
            newStr = new StringBuilder(newStr.substring(indexStr.length()));//在开头

        }else if(newStr.indexOf(indexStr) == newStr.length() - indexStr.length()){
            newStr = new StringBuilder(newStr.substring(0,newStr.lastIndexOf(indexStr)));//在结尾

        }else if(newStr.indexOf(indexStr) < (newStr.length() - indexStr.length())){
            newStr =  new StringBuilder(newStr.substring(0,newStr.indexOf(indexStr))
                    +newStr.substring(newStr.indexOf(indexStr)+indexStr.length(),newStr.length()));//在中间

        }
        return newStr.toString();
    }


    class TouchMenuTag {
        public static final String TAG_ID = "id";

        public static final String TAG_VERSION = "version";

        public static final String TAG_PUBLIC = "public";

        public static final String TAG_FI = "fi";

        public static final String TAG_PRIVATE = "private";

        public static final String TAG_MODE2 = "mode2";

        public static final String TAG_NAME = "name";

        public static final String TAG_PATH = "path";

        public static final String TAG_PATH2 = "path2";

        public static final String TAG_SIZE = "size";

        public static final String TAG_SIGNATURE = "signature";

        public static final String TAG_S3URL = "S3URL";

        public static final String TAG_CLIENT = "client";

        public static final String TAG_SN = "sn";


    }
}
