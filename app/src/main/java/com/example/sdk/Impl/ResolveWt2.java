package com.example.sdk.Impl;

import android.app.Activity;
import android.util.Log;

import com.example.sdk.BluetoothLeClass;
import com.example.sdk.Config;

import com.example.sdk.HomeUtil;
import com.example.sdk.Interface.Wt2data;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by laiyiwen on 2017/4/28.
 */

public  class ResolveWt2 implements Wt2data {



    public static int TEMPSTATE = 0;
    private List<Byte> bytes = new ArrayList<Byte>();
    // 1:�豸�ѶϿ���2:�豸������.3:�����豸��... 4:�豸��֧������4.0��
    public static boolean isHadTrimmedValue = false;
    public static int BTBattery = 4;// ���0-3�ȼ�
    public static String tempVersion = ""; //�¶ȼư汾
    //wt2�Ĳ���
    /**
     * ���ӵĺ��һ�ν��յ��豸��״̬����
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
    public boolean IsFirstPack = true;
    public static boolean IsSyncIng = false;
    /**
     * ��¼���ݰ��Ĳ���ʱ�䡣��
     */
    private String BackTime;

    public String time_sycn,ver;
    public int bettray;
    public Double unblanceTemp,balancetemp=0.0;

    private OnWt2DataListener onWt2DataListener ;
    public interface OnWt2DataListener{

        void setUnbalanceTemp(Double unbalanceTemp);
        void setBanlaceTemp(Double banlaceTemp, int btBattery);
        void setWt2ver(String wt2ver);
        void onsycnResult(String time);

    }
    public void setOnWt2DataListener(OnWt2DataListener l){
        onWt2DataListener = l;
    }





    @Override
    public void calculateData_WT2(byte[] datas, BluetoothLeClass mBLE, Activity activity,Config config) {
        // �������ӽ�����App ���յ� ���¼Ƶ�״̬�����������¼Ʊ�������ʱ����App������ʱ����1����ʱ��App
        // ��Ҫ���ʹ���������¼���ͬ��ʱ�䡣

        // ��ȡ���¼��е���ʷ���ݡ�������
        // У��ʱ�䡣����������豸ʱ���appʱ������һ���ӣ���У���豸ʱ�䡣

        // Ȼ���ѯ������������ѭ�����͡������ݿ�ID��0��ʼ ���¼��ѷ��͵����ݣ������ٱ��档��

        if (datas != null) {
            for (int i = 0; i < datas.length; i++) {
                bytes.add(datas[i]);
            }
            int length = bytes.size();
            for (int j = 0; j < bytes.size(); j++) {
                /**
                 * ��ͷ�жϷ�������ͷ�̶�Ϊ0xAA���������ܳ���0xAA�ĵط�Ϊ�����ݶΡ�У��Checksum���ҳ�����
                 * 1.��ͷΪ0xAAʱ������һ����0xAA 2.����Ϊ0xAAʱ������һ��0xAA
                 * 3.У��Ϊ0xAAʱ��������һ����ͷ0xAA�������ѵ���β
                 */
                if (bytes.get(j) == -86 && j < length - 1
                        && bytes.get(j + 1) != -86) {
                    int n = bytes.get(j + 1); // �ܳ��ȣ�������������0xAA����һ�����ȣ��ܳ��Ȱ���У����ֽ�
                    int sum = n; // �ۼӺͣ���ͷ��Checksum�ֽڼ��������ݵ��ۼӣ������ܳ����ֽ�
                    // ������ݲ�����������ѭ����
                    if (j + 1 + n > length) {
                        break;
                    }
                    // �ҵ�У��λ��ͬʱ�����ۼӺ�
                    for (int k = 0; k < n - 1; k++) {
                        // ������ݲ�����������ѭ����
                        if (j + 1 + n > length) {
                            break;
                        }
                        // �����д���0xAA��������һ��0xAA������n+1
                        if (bytes.get(j + 1 + k + 1) == -86) {
                            if (bytes.get(j + 1 + k + 2) == -86) {
                                k++;
                                n++;
                            } else {
                                // �����쳣----
//                                Log.e("ll", "�����쳣,�����д��ڲ�������0xAA");
                                // bytes.clear();
                                return;
                            }
                        }
                        int add = bytes.get(j + 1 + k + 1);
                        sum = sum + add;
                    }
                    // ������ݲ�����������ѭ����
                    if (j + 1 + n > length) {
                        break;
                    }
                    // ��ȡУ����ֽ�
                    int checksum = 0;
                    try {
                        checksum = bytes.get(j + 1 + n); // ��ʱ��nΪ�����˶���0xAA��ʵ�ʳ���
                    } catch (Exception e) {

                    }
                    // У��
                    if (checksum != sum % 256) {
                        // У��ʧ�ܣ������쳣
                        if ((checksum + 256) != sum % 256)
                            continue;
                    }

                    List<Byte> bytesTwo = new ArrayList<Byte>();
                    for (int m = 0; m < n - 1; m++) {
                        bytesTwo.add(bytes.get(j + 1 + m + 1));
                        if (bytes.get(j + 1 + m + 1) == -86
                                && bytes.get(j + 1 + m + 2) == -86) {
                            m++;
                        }
                    }
                    int pID = 0;
                    try {
                        pID = bytesTwo.get(0);
                    } catch (Exception e) {

                    }
                    switch (pID) {
                        case 1:
                            bytes.clear();
                            break;
                        case 2:
                            bytes.clear();
                            break;
                        case 3:
                            // ʱ��У�飬 ����ʼ�������ݡ����������ط��������������� ��4������� ��App
                            // �·������¼Ƶ��������Ҫ���¼Ʒ���IDΪ0x03
                            // ����Ӧ���ݰ���App 3s��δ�յ���Ӧ���ݰ���Ӧ�����·���������¼ơ�App
                            // ���յ���Ӧ���ݰ�����������Ĵ����߼�
//                            Log.e("ll", "ID����0x03����������Ӧ���ݰ�����ֻ�и���ͷ��������û�����ݵ�");
                            // ��������ѯ�豸����������0x67��103�� ��ѯ���¼����ݴ洢���
                            switch (ResponseID) {
                                case 0:
                                    // ���յ�����ʱ�����Ӧ����
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
                            break;
                        case 17:
                            if ((byte) ((bytesTwo.get(1) >> 0) & 0x1)
                                    + (byte) ((bytesTwo.get(1) >> 1) & 0x1) == 0) {
                                TEMPSTATE = 0;
                                int tempH = bytesTwo.get(3);
                                int tempL = bytesTwo.get(2);
                                if (tempH < 0) {
                                    tempH += 256;
                                } else {
                                }
                                if (tempL < 0) {
                                    tempL += 256;
                                } else {
                                }
                                double temp = ((tempH * 256 + tempL) * 0.01);//��ȡ����ֵ
//                                Message msg = new Message();
//                                msg.obj = temp;
//                                Log.e("test", "bytesTwo.get(1)::" + bytesTwo.get(1));
//                                Log.e(TAG, "msg.obj::" + msg.obj);
                                if ((byte) ((bytesTwo.get(1) >> 2) & 0x1)
                                        + (byte) ((bytesTwo.get(1) >> 3) & 0x1) == 0) {
                                    Log.e("kk", "unbalance����");
//                                    msg.what = MACRO_CODE_6;
//                                    isHadTrimmedValue = false;
                                    unblanceTemp=temp;
//                                    onWt2DataListener.setUnbalanceTemp(temp);

                                } else if ((byte) ((bytesTwo.get(1) >> 2) & 0x1)
                                        + (byte) ((bytesTwo.get(1) >> 3) & 0x1) == 1) {
                                    Log.e("kk", "balance����");
//                                    if (!isHadTrimmedValue) {
                                        //û�о���ƽ��ֵ, �ﵽƽ��֮�������
//                                        msg.what = MACRO_CODE_7;
                                        balancetemp=temp;
//                                        onWt2DataListener.setBanlaceTemp(temp);
//                                        isHadTrimmedValue = true;
//                                    } else {

//                                        onWt2DataListener.setUnbalanceTemp(temp);;
//                                        msg.what = MACRO_CODE_6;
//                                    }
                                } else {

                                }
//                                config.getMyFragmentHandler().sendMessage(msg);
                                // config.sendhideTabmsg(msg);
                                // if( handler1!=null){
                                // handler1.sendMessage(msg);
                                // }
                                // } else if (bytesTwo.get(1) == 1) {
                            } else if ((byte) ((bytesTwo.get(1) >> 0) & 0x1)
                                    + (byte) ((bytesTwo.get(1) >> 1) & 0x1) == 1) {
                                TEMPSTATE = 1;
//                                Message msg = new Message();
//                                msg.what = MainActivity.SENDTEMPHIGHT;
//                                config.getFirstMainHandler().sendMessage(msg);
                                // } else if (bytesTwo.get(1) == 2) {
                            } else if ((byte) ((bytesTwo.get(1) >> 0) & 0x1)
                                    + (byte) ((bytesTwo.get(1) >> 1) & 0x1) == 2) {
                                TEMPSTATE = 2;
                            }
                            bytes.clear();
                            onWt2DataListener.setUnbalanceTemp(unblanceTemp);

                            break;
                        case 18:

                            // Byte3-Byte6���豸�ĵ�ǰʱ�䡣��ҪУ��app���豸֮���ʱ�䡣���������60�룬������������豸��ʱ�䡣��
//                            Log.e("test", "���յ�������״̬������");
                            if (isFirstStatusPacket) {
                                isFirstStatusPacket = false;
                                int three = bytesTwo.get(3) < 0 ? bytesTwo.get(3) + 256
                                        : bytesTwo.get(3);
                                int four = bytesTwo.get(4) < 0 ? bytesTwo.get(4) + 256
                                        : bytesTwo.get(4);
                                int five = bytesTwo.get(5) < 0 ? bytesTwo.get(5) + 256
                                        : bytesTwo.get(5);
                                int six = bytesTwo.get(6) < 0 ? bytesTwo.get(6) + 256
                                        : bytesTwo.get(6);
                                /**
                                 * ���ﷵ��һ����ǰʱ��
                                 */
                                int[] times={three,four,five,six};
                               String time=HomeUtil.BuleToTime(times);
                                if (Math.abs(HomeUtil.DifferTime(six * 256 * 256
                                        * 256 + five * 256 * 256 + four * 256
                                        + three)) <= 60) {
                                    // appʱ�����豸ʱ�����С��60�룬���账��
                                    // ��������ѯ�豸����������0x67��103�� ��ѯ���¼����ݴ洢���
//                                    SendForAll(mBLE);
                                    Log.e("buyong","no need to sync"+""+time);
                                    time_sycn=time;
//                                    onWt2DataListener.onsycnResult(time);
                                } else {
                                    SendForTime(mBLE);
                                }
                            }
                            int BTBatteryCopy = bytesTwo.get(1);
                            if (BTBatteryCopy < 0) {
                                BTBattery = (bytesTwo.get(1) + 256) % 16;
                            } else {
                                BTBattery = bytesTwo.get(1) % 16;
                            }
                            bettray=BTBattery;
//                            onWt2DataListener.setBTBattery(BTBattery);
                            Log.v("tkz", "in the bluetooth state the BTBattery ����" + BTBattery);
                            int version = bytesTwo.get(2);
                            tempVersion = Integer.toString((version / 16)) + "."
                                    + Integer.toString((version % 16));
                            ver=tempVersion;
//                            onWt2DataListener.setWt2ver(tempVersion);
                            bytes.clear();
                            break;
                    }
                    Log.e("time_sycn::",""+time_sycn+"bettray::"+bettray+"ver::"+ver+"unblanceTemp::"+unblanceTemp+"balancetemp::"+balancetemp);
                    onWt2DataListener.onsycnResult(time_sycn);
                    onWt2DataListener.setWt2ver(ver);
                    onWt2DataListener.setUnbalanceTemp(unblanceTemp);
                    onWt2DataListener.setBanlaceTemp(balancetemp,bettray);
//                    onWt2DataListener.setUnbalanceTemp(unblanceTemp);
                }
            }
        }


    }


//    /**
//     * ��������У׼�豸ʱ�䡣����
//     */
    public void SendForTime(BluetoothLeClass mBLE) {
        Log.e("kk", "��������У׼�豸ʱ�䡣����::");
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

//    /**
//     * 3������� �ж��Ƿ���յ�У׼ʱ�����Ӧ��������յ��ˣ���ȥ�����񣬷����������У׼ʱ������
//     */
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





}