package com.example.sdk.Interface;

import android.app.Activity;

import com.example.sdk.BluetoothLeClass;
import com.example.sdk.Config;
import com.example.sdk.Impl.ResolveWt1;

/**
 * Created by laiyiwen on 2017/4/26.
 */

public interface Wt1data {

      void calculateData_WT1(byte[] datas, BluetoothLeClass mBLE, Activity activity, Config config);
      void setOnWt1DataListener(ResolveWt1.OnWt1DataListener listener);
      /**
       * ���ͽ�������������� 0�����ͳɹ����� 1 ���� �����쳣������
       * @param num
       * @param activity
       * @param mBLE
       */
      void MySendSyncEnd(int num, Activity activity, BluetoothLeClass mBLE);

      /**
       * 15������� �ж��Ƿ���յ�ͬ�����ݿ�ʼ״̬��������յ��ˣ���ȥ�����񣬷���������������������ݿ顣
       * @param witch
       * @param mBLE
       */
      void MyReSendPackTask(int witch, final BluetoothLeClass mBLE);


      /**
       * ���������ط���
       * @param num
       * @param mBLE
       */
      void SendRepeatRequest(int num, BluetoothLeClass mBLE);


      /**
       * ����ʼ�������ݡ�
       * @param num
       * @param mBLE
       */

      void SendRequestForDate(int num, BluetoothLeClass mBLE);


      /**
       *  5������� �ж��Ƿ���յ�ͬ�����ݿ�ʼ״̬��������յ��ˣ���ȥ�����񣬷���������������������ݿ顣
       * @param witch
       * @param mBLE
       */

      void MyReSendTask(int witch, final BluetoothLeClass mBLE);

      /**
       *  ��������У׼�豸ʱ�䡣����
       * @param mBLE
       */
      void SendForTime(BluetoothLeClass mBLE);


      /**
       * ����յ���3���յ��ͳ���,û���յ��ͼ�����
       * @param mBLE
       */
      void MyTimeTask(final BluetoothLeClass mBLE);

      /**
       * ���������ȡ���¼����ݴ洢״̬
       * @param mBLE
       */
      void SendForAll(BluetoothLeClass mBLE);

}
