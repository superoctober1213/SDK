package com.example.sdk.Impl;

import android.app.Activity;
import android.util.Log;

import com.example.sdk.BluetoothLeClass;
import com.example.sdk.Config;

import com.example.sdk.HomeUtil;
import com.example.sdk.Interface.Wt1data;
import com.example.sdk.MyDateUtil;
import com.example.sdk.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by laiyiwen on 2017/4/26.
 */

/**
 * wt1  ���ݽ����Ļص��ӿ�
 */

public class ResolveWt1 implements Wt1data {



    private List<Byte> bytes = new ArrayList<Byte>();
    // 1:�豸�ѶϿ���2:�豸������.3:�����豸��... 4:�豸��֧������4.0��
    public static int BTBattery = 4;// ���0-3�ȼ�
    public static String tempVersion = ""; //�¶ȼư汾
    String time;
    public static int TEMPSTATE = 0;
    public static final int MACRO_CODE_3 = 3;
    public static final int MACRO_CODE_4 = 4;
    public static final int MACRO_CODE_5 = 5;
/** ���ӵĺ��һ�ν��յ��豸��״̬����
            */
    public boolean isFirstStatusPacket = true;
    /**
     * ���յ�0x03��ͷ������ ������Ǹ������� 0��У��ʱ�� 1������ʼ�������� 2�� �����ط����� 3�������������ݡ�����
     * 4:�����豸�洢������������
     */
    public int ResponseID = 0;
    /**
     * �Ƿ���յ�У��ʱ�����Ӧ����
     */
    public boolean isCheckTime = false;
    /**
     * �豸��һ���ж��ٸ���Ҫͬ�������ݿ顣
     */
    public int DataBlock = 0;
    /**
     * �Ѿ������ĸ����ݿ��ˡ�
     */
    public int WitchDateBlock = 0;
    /**
     * ����Ƿ��ǵ�һ�����ݰ�������
     */
    public int WitchPack = 0;
    public int total = 0,begin=0;
    public boolean IsFirstPack = true;
    public static boolean IsSyncIng = false;

    public static int firstIn=0;
    /**
     * ��¼���ݰ��Ĳ���ʱ�䡣��
     */
    double temp;
    private String BackTime;

    private OnWt1DataListener onWt1DataListener ;
    public interface OnWt1DataListener {
        void setTemp(Double temp);
         void ontempState(int stateCode);
        void onBTBattery(String bTBattery);
        void onVersion(String version);
        void onTime(String time);
        void ontotal(int total);
        void onsycnResult(float BlueTem, String TempID);
        void onSycnState(int begin, int total, String backtime);

    }

    public void setOnWt1DataListener(OnWt1DataListener l){
        onWt1DataListener = l;
    }

    public static int byteToInt(byte b) {
        //Java ���ǰ� byte �����з��������ǿ���ͨ������� 0xFF ���ж�������õ������޷�ֵ
        return b & 0xFF;
    }
    @Override
    public synchronized void calculateData_WT1(byte[] datas,BluetoothLeClass mBLE, Activity activity, Config config)  {
        if (datas != null) {
            for (int i = 0; i < datas.length; i++) {
                bytes.add(datas[i]);
            }
            Log.e("length",""+bytes.size());

            int length = bytes.size();
            for (int j = 0; j < bytes.size(); j++) {
                /**
                 * ��ͷ�жϷ�������ͷ�̶�Ϊ0xAA���������ܳ���0xAA�ĵط�Ϊ�����ݶΡ�У��Checksum���ҳ�����
                 * 1.��ͷΪ0xAAʱ������һ����0xAA 2.����Ϊ0xAAʱ������һ��0xAA
                 * 3.У��Ϊ0xAAʱ��������һ����ͷ0xAA�������ѵ���β
                 */
                if (bytes.get(j) == -86 && j < length - 1 && bytes.get(j + 1) != -86) {

                    int n =byteToInt(bytes.get(j + 1)) ; // ��������ܳ���n��������������0xAA����һ�����ȣ��ܳ��Ȱ���У����ֽ�
                    int sum = n; // �ۼӺͣ���ͷ��Checksum�ֽڼ��������ݵ��ۼӣ������ܳ����ֽ�
                    // ������ݲ�����������ѭ����


                    if (j + 1 + n > length) {

                        break;
                    }
                    // �ҵ�У��λ��ͬʱ�����ۼӺ�sum
                    for (int k = 0; k < n - 1; k++) {
                        // ������ݲ�����������ѭ����
                        if (j + 1 + n > length) {

                            break;
                        }
                        // �����д���0xAA��������һ��0xAA������n+1(��Ϊ��õ��ֽڳ��Ȳ�����0xaa)
                        if (bytes.get(j + 1 + k + 1) == -86) {
                            if (bytes.get(j + 1 + k + 2) == -86) {
                                k++;
                                n++;
                            } else {
                                // �����쳣----
//                                Log.d("test", "�����쳣,�����д��ڲ�������0xAA");
                                // bytes.clear();
                                return;
                            }
                        }
                        int add = byteToInt(bytes.get(j + 1 + k + 1));
                        sum = sum + add;//��ͷ��Checksum�ֽڼ��������ݵ��ۼӣ������ܳ����ֽ�
                        sum=sum%256;
                    }
                    // ������ݲ�����������ѭ����
                    if (j + 1 + n > length) {

                        break;
                    }
////                     ��ȡУ����ֽ�
                    int checksum = byteToInt(bytes.get(j + 1 + n));// ��ʱ��nΪ�����˶���0xAA��ʵ�ʳ���
//                  Log.e("checksum----",""+checksum+"j="+j+",---n="+n+"sum="+sum+",sum % 256="+sum % 256);
//                    for (int i=0;i<bytes.size();i++)
//                    {
//                        Log.e("?","��"+i+"����"+bytes.get(i).intValue());
//                    }
//            //   У��
//
                    if (checksum != sum % 256) {
                        // У��ʧ�ܣ������쳣

                        if ((checksum + 256) != sum % 256)
                            continue;
                    }

                    List<Byte> bytesTwo = new ArrayList<Byte>();
                    //��ȡ���ݿ�,ȥ������0xaa
                    for (int m = 0; m < n - 1; m++) {
                        bytesTwo.add(bytes.get(j + 1 + m + 1));
                        if (bytes.get(j + 1 + m + 1) == -86 && bytes.get(j + 1 + m + 2) == -86) {
                            m++;
                        }
                    }

//                    for (int i=0;i<bytesTwo.size();i++)
//                    {
//                        Log.e("���ڳ��ֵ�bytesTwo��ʲô?","��"+i+"����"+bytesTwo.get(i).intValue());
//                    }
//                    for (Byte b:bytesTwo)
//                    {
//                        Log.e("",""+b.intValue());
//                    }


                    int pID = bytesTwo.get(0);
                    Log.d("test", "pID =" + pID);

                    switch (pID) {
                        case 1://����������
                            bytes.clear();//�������,��ֹ���ݵ���
                            break;
                        case 2://�����¼�״̬
                            bytes.clear();
                            break;
                        case 3:
                            // ���յ���Ӧ���ݰ�����������Ĵ����߼�
//                            Log.e("kkk", "ID����0x03����������Ӧ���ݰ�����ֻ�и���ͷ��������û�����ݵ�");
                            // ��������ѯ�豸����������0x67��103�� ��ѯ���¼����ݴ洢���
                            switch (ResponseID) {
                                case 0:

//                                    SendForAll(mBLE);
                                    isCheckTime = true;
                                    break;
                                case 1:
                                    // ���յ����������ݵ���Ӧ����
//                                    Log.e("test", " ���յ����������ݵ���Ӧ��::��Ӧ������");
                                    break;
                                case 2:
                                    // ���յ������ط����ݵ���Ӧ������
//                                    Log.e("test", "���յ������ط����ݵ���Ӧ��::��Ӧ������");
                                    break;
                                case 3:
                                    // ���յ������������ݵ���Ӧ������
//                                    Log.e("test", " ���յ������������ݵ���Ӧ��::��Ӧ������");
                                    break;
                                case 4:
                                    // �����豸�����ݴ洢������
//                                    Log.e("test", "�����豸�����ݴ洢����::��Ӧ������");
                                    break;
                                default:
                                    break;
                            }
                            bytes.clear();
                            break;
                        case 17://��������0x11(��Э��)
                            if (bytesTwo.get(1) == 0) {//�����¶�
                                TEMPSTATE = 0;
                                int tempH = bytesTwo.get(3);//���¸�8λ
                                int tempL = bytesTwo.get(2);//���µ�8λ
                                if (tempH < 0) {
                                    tempH += 256;
                                }
                                if (tempL < 0) {
                                    tempL += 256;
                                }
                                 temp = ((tempH * 256 + tempL) * 0.01);//�����¶�
                                Log.e("test", "case 17::tempH =" + tempH);
                                Log.e("test", "case 17::tempL =" + tempL);
                                Log.e("test", "case 17::temp =" + temp);
//                                Log.e("��������", "111111111");


//                                Message msg = new Message();
//                                msg.what = MACRO_CODE_3;//�¶ȱ��1003,���͵�FirstMainActivity�е�FirstMainHandler�д���
//                                msg.obj = temp;//���������¶ȴ���FirstMainActivity�е��߳��д���
//                                config.getMyFragmentHandler().sendMessage(msg);//�����̷߳���message
                            } else if (bytesTwo.get(1) == 1) {//�¶ȹ���(>50)
                                Log.i("test", "SENDTEMPHIGHT");
                                TEMPSTATE = 1;

//                                onWt1DataListener.ontempState(MACRO_CODE_4);
//                                Message msg = new Message();
//                                msg.what = MACRO_CODE_4;
//                                config.getMyFragmentHandler().sendMessage(msg);//��ʾ�¶ȹ���
//
                            } else if (bytesTwo.get(1) == 2) {//�¶ȹ���(<0)
//                                onWt1DataListener.ontempState(MACRO_CODE_5);
//                                Message msg = new Message();
//                                msg.what = MACRO_CODE_5;
//                                config.getMyFragmentHandler().sendMessage(msg);//��ʾ�¶ȹ���
//                                Log.d("test", "SENDTEMPLOW");
                                TEMPSTATE = 2;
                            }
                            bytes.clear();
                            break;
                        case 18://���¼�״̬0x12(��Э��)


                                int BTBatteryCopy = bytesTwo.get(1);
                                if (BTBatteryCopy < 0) {
                                    BTBattery = (bytesTwo.get(1) + 256) % 16;//��������ĵ���,��16����,ȡ�����4λ
                                } else {
                                    BTBattery = bytesTwo.get(1) % 16;
                                }

//                            onWt1DataListener.onBTBattery(""+BTBattery);

                                int version = bytesTwo.get(2);//�����汾��X.Y,��4λλX,��4λΪY
                                tempVersion = Integer.toString((version / 16)) + "."
                                        + Integer.toString((version % 16));

//                            onWt1DataListener.onVersion(""+version);
                                int three = bytesTwo.get(3) < 0 ? bytesTwo.get(3) + 256
                                        : bytesTwo.get(3);
                                int four = bytesTwo.get(4) < 0 ? bytesTwo.get(4) + 256
                                        : bytesTwo.get(4);
                                int five = bytesTwo.get(5) < 0 ? bytesTwo.get(5) + 256
                                        : bytesTwo.get(5);
                                int six = bytesTwo.get(6) < 0 ? bytesTwo.get(6) + 256
                                        : bytesTwo.get(6);
                            bytes.clear();
                                int[] times = {three, four, five, six};
                                 time = HomeUtil.BuleToTime(times);

//
                            break;
                        case 20:
//                            Log.e("IsFirstPack", "" + IsFirstPack);
//                            if (IsFirstPack) {
//                                IsFirstPack = false;
                            WitchDateBlock=bytesTwo.get(1)+bytesTwo.get(2)*256;
                            WitchPack=bytesTwo.get(3)+bytesTwo.get(4)*256;
//                            if (WitchDateBlock<DataBlock)//���С���ܿ�����
//                            {

                                    for (n=5;n<bytesTwo.size();n=n+2)
                                    {
                                        float BlueTem = (float) ((((bytesTwo.get(n) < 0 ? bytesTwo
                                                .get(n) + 256 : bytesTwo.get(n)) + (bytesTwo
                                                .get(n+1) < 0 ? bytesTwo.get(n+1) + 256
                                                : bytesTwo.get(n+1)) * 256)) * 0.01);
//                                        Log.e("test", "��" + n + "�����ݵ��¶ȣ���" + BlueTem);
                                    }

//                                if (WitchPack==total-1)
//                                {
//
////
//                                        bytes.clear();
//                                        WitchDateBlock++;
//                                        SendRequestForDate(WitchDateBlock,mBLE);
////
//                                }
//                            }

                                // HomeUtil.DegreesToFahrenheit
                                // ���ݲ���ʱ��ת���ɵ�TempID�������ݿ⣬������ڼ�¼�����жϲ���ʱ���ǰ�󣬱���ʱ��������ݡ�
                                //�ж��ǲ��ǿͻ�ģʽ����ǿͻ�ģʽ��ʲô������

                                String TempID = HomeUtil.Date2ID(BackTime);
                                Log.e("���ص�ʱ��",""+TempID);

                                bytes.clear();
//                                if (WitchDateBlock < DataBlock) {
//                                    SendRequestForDate(WitchDateBlock,mBLE);
//                                    MyReSendPackTask(WitchDateBlock,mBLE);
//                                    WitchDateBlock++;
//                                } else {
//                                    Log.e("test", "�豸������ͬ����ϣ��������Է��ͣ����������ݷ����ˣ���");
//                                    MySendSyncEnd(0,activity,mBLE);
//                                }
//
////                                onWt1DataListener.onsycnResult(BlueTem,TempID);
//                            }

//                            bytes.clear();
//                            onWt1DataListener.onsycnResult(BlueTem,TempID);

//                            bytes.clear();
                            break;
                        case 21:
//                            IsFirstPack = true;
//                            Log.e("test", "ͬ�����ݿ�ʼ״̬������");
//                            Log.e("test", "��ʼ״̬�����������ݿ�ı�ţ���"
//                                    + (bytesTwo.get(1) + bytesTwo.get(2) * 256));
                             begin=bytesTwo.get(1) + bytesTwo.get(2) * 256;
//                            Log.e("test", "��ʼ״̬�����������ݿ�����ݰ���������"
//                                    + (bytesTwo.get(7) + bytesTwo.get(8) * 256));
                             total=bytesTwo.get(7) + bytesTwo.get(8) * 256;
                            BackTime = HomeUtil.BuleToTime(new int[]{
                                    bytesTwo.get(3), bytesTwo.get(4),
                                    bytesTwo.get(5), bytesTwo.get(6)});
//                            Log.e("test", "��ʼ״̬�����������ݿ��ʱ�䣺��" + BackTime);
                            bytes.clear();
//                            bytes.clear();
//                            onWt1DataListener.onSycnState(begin,total,BackTime);
//                            MyReSendPackTask(WitchDateBlock,mBLE);
//                            WitchDateBlock++;
//                            WitchDateBlock++;
//                            bytes.clear();
                            break;
                        case 22:
                            int all = bytesTwo.get(1) + bytesTwo.get(2) * 256;
                            bytes.clear();
//                            onWt1DataListener.ontotal(all);
//                            Log.e("test", "ID����0x16 ���¼����ݴ洢��:���ݿ�һ���ж��ٸ�::" + all);
                            if (all > 0) {
                                // ��ʼ�����豸������Ҫͬ�������ݡ�
                                DataBlock = all;
                                SendRequestForDate(WitchDateBlock,mBLE);
                            } else {
                                DataBlock = 0;
                                IsSyncIng = false;
                                // ��¼����ͬ��ʱ�䡣��
//                                Log.e("test", "��Ҫͬ��������Ϊ0����");
                                SharedPreferencesUtil
                                        .setEquipmentSynchronizationTime(
                                                activity,
                                                MyDateUtil.getDateFormatToString("yyyy-MM-dd HH:mm"));
                            }
                            bytes.clear();
//                            onWt1DataListener.ontotal(all);
//                            bytes.clear();
                            break;

                    }



                }
                onWt1DataListener.setTemp(temp);
                if (TEMPSTATE==1)
                {
                    onWt1DataListener.ontempState(MACRO_CODE_4);
                }
                if (TEMPSTATE==2)
                {
                    onWt1DataListener.ontempState(MACRO_CODE_5);
                }
                onWt1DataListener.onBTBattery("" + BTBattery);
                onWt1DataListener.onVersion("" + tempVersion);
                onWt1DataListener.onTime(time);

            }


        }
    }


    /**
     *  ���ͽ�������������� 0�����ͳɹ����� 1 ���� �����쳣����
     * @param num
     * @param activity
     * @param mBLE
     */


    public void MySendSyncEnd(int num, Activity activity, BluetoothLeClass mBLE) {

        List<Byte> sendforAll = new ArrayList<Byte>();
        sendforAll.add((byte) 0xAA);// ��ͷ���̶�Ϊ0xAA�� -86������
        sendforAll.add((byte) 0x04);// ���� ���ݿ�ĳ���
        // ���ݿ�
        sendforAll.add((byte) 0x6A);

        sendforAll.add((byte) num);// 0
        sendforAll.add((byte) 0x00);// ����
        // У��
        int CRS = 0;
        for (int i = 2; i < sendforAll.size(); i++) {
            CRS += sendforAll.get(i);
        }
        sendforAll.add((byte) CRS);
//        Log.e("test", "����������������������ݡ�::" + num);
        if (mBLE != null) {
            mBLE.writeCharacteristic(HomeUtil
                    .CheckByte(sendforAll));
        }
        ResponseID = 3;
        IsSyncIng = false;
        SharedPreferencesUtil.setEquipmentSynchronizationTime(activity,
                MyDateUtil.getDateFormatToString("yyyy-MM-dd HH:mm"));
    }

    /**
     * 15������� �ж��Ƿ���յ�ͬ�����ݿ�ʼ״̬��������յ��ˣ���ȥ�����񣬷���������������������ݿ顣
     */

    public void MyReSendPackTask(int witch, final BluetoothLeClass mBLE) {
        final Timer timer = new Timer();
        final int num = witch;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (num == WitchDateBlock - 1 && IsFirstPack) {
                    // ������������ݿ�����ݡ�
                    SendRepeatRequest(WitchDateBlock,mBLE);
                } else {
                    timer.cancel();
                }
            }
        };
        timer.schedule(task, 15000);
    }

    /**
     * ���������ط�����
     *
     * @param num
     */
    public void SendRepeatRequest(int num,BluetoothLeClass mBLE) {

        List<Byte> sendforAll = new ArrayList<Byte>();
        sendforAll.add((byte) 0xAA);// ��ͷ���̶�Ϊ0xAA�� -86������
        sendforAll.add((byte) 0x07);// ���� ���ݿ�ĳ���
        // ���ݿ�
        sendforAll.add((byte) 0x69);
        // byte2 byte3�����ݿ��� ��0��ʼ����
        int[] ID = HomeUtil.getDateID(num);
        sendforAll.add((byte) ID[0]);
        sendforAll.add((byte) ID[1]);
        sendforAll.add((byte) 0x00);// 0
        sendforAll.add((byte) 0x00);// 0
        sendforAll.add((byte) 0x00);// ����
        // У��
        int CRS = 0;
        for (int i = 2; i < sendforAll.size(); i++) {
            CRS += sendforAll.get(i);
        }
        sendforAll.add((byte) CRS);
//        Log.e("test", "���������������������ݿ�num�����ݰ���::" + num);
        if (mBLE != null) {
            mBLE.writeCharacteristic(HomeUtil
                    .CheckByte(sendforAll));
        }
        ResponseID = 2;
    }

    /**
     * ����ʼ�������ݡ�
     *
     * @param num
     */
    public void SendRequestForDate(int num,BluetoothLeClass mBLE) {

        List<Byte> sendforAll = new ArrayList<Byte>();
        sendforAll.add((byte) 0xAA);// ��ͷ���̶�Ϊ0xAA�� -86������
        sendforAll.add((byte) 0x05);// ���� ���ݿ�ĳ���
        // ���ݿ�
        sendforAll.add((byte) 0x68);
        sendforAll.add((byte) 0x00);// ����
        // byte2 byte3�����ݿ��� ��0��ʼ����
        int[] ID = HomeUtil.getDateID(num);
        sendforAll.add((byte) ID[0]);
        sendforAll.add((byte) ID[1]);
        // У��
        int CRS = 0;
        for (int i = 2; i < sendforAll.size(); i++) {
            CRS += sendforAll.get(i);
        }
        sendforAll.add((byte) CRS);
//        Log.e("test", "���������ȡ���¼ƴ洢�����ݿ�::" + num);
        if (mBLE != null) {
            mBLE.writeCharacteristic(HomeUtil
                    .CheckByte(sendforAll));
            ResponseID = 1;
            // ���﷢���ˣ����5���û���յ�ͬ�����ݿ�ʼ״̬��������Ҫ���������ط�����
            // ��������
//            MyReSendTask(num,mBLE);
        }
    }

    /**
     * 5������� �ж��Ƿ���յ�ͬ�����ݿ�ʼ״̬��������յ��ˣ���ȥ�����񣬷���������������������ݿ顣
     */
    public void MyReSendTask(int witch, final BluetoothLeClass mBLE) {
//        Log.e("", "���������������ݿ�::");
        final Timer timer = new Timer();
        final int num = witch;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (num == WitchDateBlock) {
                    // ������������ݿ�����ݡ�
                    SendRequestForDate(WitchDateBlock,mBLE);
                } else {
                    timer.cancel();
                }
            }
        };
        timer.schedule(task, 5000);
    }

    /**
     * ��������У׼�豸ʱ�䡣����
     */
    public void SendForTime(BluetoothLeClass mBLE) {
//        Log.e("", "��������У׼�豸ʱ�䡣����::");
        int[] nowTime = HomeUtil.getTimeByte();
        // appʱ�����豸ʱ��������60�룬У׼�豸ʱ�䡣byte2-byte5��ǰʱ�� byte
        // 6����
        // byte0 ��ͷ byte1����
        // ��ͷ��0xAA�� ���� ���ݿ� У��Checksum
        // ��ͷ���̶�Ϊ0xAA��
        // ���ȣ�Ϊ���ֽ�֮���������ݵĳ��ȣ�����У����ֽڣ����ֽڲ���Ϊ0xAA��
        // ���ݿ飺����ľ����������ݡ�
        // Checksum��У���ֽڣ�Ϊ��ͷ��Checksum�ֽڼ��������ݵ��ۼ�У��͡�
        List<Byte> sendbytes = new ArrayList<Byte>();
        sendbytes.add((byte) 0xAA);// ��ͷ���̶�Ϊ0xAA��
        // //170������
        sendbytes.add((byte) 0x08);// ���� ���ݿ�ĳ���
        // ����7byte+1byteУ��
        // ���������ݿ顣
        sendbytes.add((byte) 0x6B);// beye0
        sendbytes.add((byte) 0x00);// beye1����
        // byte2-byte5��ǰʱ�䡣
        sendbytes.add((byte) nowTime[0]);// beye2
        sendbytes.add((byte) nowTime[1]);// beye3
        sendbytes.add((byte) nowTime[2]);// beye4
        sendbytes.add((byte) nowTime[3]);// beye5
        // 6 ����
        sendbytes.add((byte) 0x00);// beye6
        // �����У��λ��
        int size = sendbytes.size();
        // crcУ��
        byte crc = 0x00;
        for (int i = 0; i < size; i++) {
            crc += sendbytes.get(i);
        }
        sendbytes.add(crc);
        if (mBLE != null) {
            mBLE.writeCharacteristic(HomeUtil
                    .CheckByte(sendbytes));
            ResponseID = 0;
            isCheckTime = false;
            // �����߳� ��3������isCheckTime��Ϊtrue�Ļ������·�������У��ʱ�䡣��
            MyTimeTask(mBLE);
        }
    }

    /**
     * 3������� �ж��Ƿ���յ�У׼ʱ�����Ӧ��������յ��ˣ���ȥ�����񣬷����������У׼ʱ������
     */
    public void MyTimeTask(final BluetoothLeClass mBLE) {
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (isCheckTime) {
                    timer.cancel();
                } else {
                    SendForTime(mBLE);
                }
            }
        };
        timer.schedule(task, 3000);
    }

    /**
     * ���������ȡ���¼����ݴ洢״̬
     */
      public void SendForAll(BluetoothLeClass mBLE) {
//        Log.e("show", "���������ȡ���¼����ݴ洢״̬::");
        IsSyncIng = true;
        ResponseID = 4;
        List<Byte> sendforAll = new ArrayList<Byte>();
        sendforAll.add((byte) 0xAA);// ��ͷ���̶�Ϊ0xAA�� //170������
        sendforAll.add((byte) 0x03);// ���� ���ݿ�ĳ���
        // ���ݿ�
        sendforAll.add((byte) 0x67);
        sendforAll.add((byte) 0x00);
        // У��
        sendforAll.add((byte) 0x67);
//        Log.e("test", "���������ȡ���¼����ݴ洢״̬::");
        if (mBLE != null) {
            mBLE.writeCharacteristic(HomeUtil
                    .CheckByte(sendforAll));
        }
    }

}
