package com.example.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

/**
 * Created by qindachang on 2017/2/23.
 */

public class LocationUtils {

    private LocationUtils() {
        //no instance
    }

    /**
     * �ж��Ƿ�������λ����
     *
     * @param context context
     * @return �Ƿ�������λ����
     */
    public static boolean isOpenLocService(final Context context) {
        boolean isGps = false; //�ж�GPS��λ�Ƿ�����
        boolean isNetwork = false; //�ж����綨λ�Ƿ�����
        if (context != null) {
            LocationManager locationManager
                    = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                //ͨ��GPS���Ƕ�λ����λ������Ծ�ȷ���֣�ͨ��24�����Ƕ�λ��������Ϳտ��ĵط���λ׼ȷ���ٶȿ죩
                isGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                //ͨ��WLAN���ƶ�����(3G/2G)ȷ����λ�ã�Ҳ����AGPS������GPS��λ����Ҫ���������ڻ��ڸ������Ⱥ��ï�ܵ����ֵȣ��ܼ��ĵط���λ��
                isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            }
            if (isGps || isNetwork) {
                return true;
            }
        }
        return false;
    }

    /**
     * ��ת��λ�������
     *
     * @param activity activity
     */
    public static void gotoLocServiceSettings(Activity activity) {
        final Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    /**
     * ��ת��λ�������
     *
     * @param activity    activity
     * @param requestCode requestCode
     */
    public static void gotoLocServiceSettings(Activity activity, int requestCode) {
        final Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, requestCode);
    }
}
