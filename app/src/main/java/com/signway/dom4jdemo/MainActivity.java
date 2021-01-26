package com.signway.dom4jdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.signway.dom4jdemo.model.TouchMenuBaseBean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parseXml();
    }

    private void parseXml() {

        InputStream abpath = getClass().getResourceAsStream("/assets/546.xml");
        try {
//            String path = new String(InputStreamToByte(abpath));
            String path = "/mnt/sdcard/546.xml";
            Log.i(TAG,"parseXml --- > path = " + path);
            TouchMenuBaseBean baseBean = XMLParseManager.getInstance().parseTouchMenu(path);
            if (baseBean == null) {
                Log.i(TAG,"parseXml --- > baseBean == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();
        return imgdata;
    }

}
