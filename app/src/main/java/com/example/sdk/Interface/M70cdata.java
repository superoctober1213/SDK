package com.example.sdk.Interface;

import com.example.sdk.Impl.ResolveM70c;

/**
 * Created by laiyiwen on 2017/4/28.
 */

public interface M70cdata {
    void calculateData_M70c(String rawData);
    void setOnM70cDataListener(ResolveM70c.OnM70cDataListener listener);

}
