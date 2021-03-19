package com.example.sdk.entity;

import java.io.Serializable;

/**
 * Created by laiyiwen on 2017/5/12.
 */

public class SycnData implements Serializable {
    private String TempID;//ʱ��id
    private String Temp;//�¶�ֵ

    public String getTempID() {
        return TempID;
    }

    public void setTempID(String tempID) {
        TempID = tempID;
    }

    public String getTemp() {
        return Temp;
    }

    public void setTemp(String temp) {
        Temp = temp;
    }
}
