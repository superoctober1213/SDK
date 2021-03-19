package com.example.sdk.Interface;

import com.example.sdk.BluetoothLeClass;
import com.example.sdk.Impl.ResolveWf100;

/**
 * Created by laiyiwen on 2017/4/28.
 */

public interface Mf100data  {

    void setOnWF100DataListener(ResolveWf100.OnWF100DataListener l);
    void resolveBPData_wf(String datas);
    void SetVoice(BluetoothLeClass mBLE, byte[] datas);
}
