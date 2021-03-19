package com.example.sdk;

import android.content.Context;
import android.widget.Toast;

import com.example.sdk.entity.Peripheral;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by apple on 16/7/23.
 */
public class DataCheckUtil extends Toast {
    public DataCheckUtil(Context context) {
        super(context);
    }

    public static boolean checkData(String data) {
        boolean result = true;
        String str = data;
        Pattern pattern = Pattern.compile(
                "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            result = false;
        }
        return result;
    }

    /** �ж������ʽ */
    public static boolean checkUri(String data) {
        boolean result = true;
        String str = data;
        Pattern pattern = Pattern
                .compile(
                        "((http|https|ftp|rtsp|mms):(\\/\\/|\\\\\\\\){1}(([\\w-])+[.]){1,3}(net|com|cn|org|cc|tv|[0-9]{1,3})(\\S*\\/)((\\S)+[.]{1}(html|swf|jsp|php|jpg|gif|bmp|png)))",
                        Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            result = false;
        }
        return result;
    }


    public static boolean checkMainData(String data, int min, int max) {
        boolean result = false;
        String str = data;
        Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{" + min + "," + max + "}$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);

        if (matcher.matches()) {
            result = true;
        }
        return result;
    }

    /** �Ƿ���ĸ������ */
    public static boolean checkMainData2(String data) {
        boolean result = false;
        String str = data;
        Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            result = true;
        }
        return result;
    }

    // �ж�Ѫѹ
    public static boolean checkBloodPressureArea(String data) {
        boolean result = false;
        Pattern pattern = Pattern.compile("1\\d\\d", Pattern.CASE_INSENSITIVE);
        Pattern pattern1 = Pattern.compile("[1-9]\\d", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);
        Matcher matcher1 = pattern1.matcher(data);
        if (matcher.matches() || matcher1.matches()) {
            result = true;
        }

        return result;
    }


    public static boolean checkBloodSugarArea(String data) {
        boolean result = false;
        Pattern pattern5 = Pattern.compile("3[0-2]\\.\\d", Pattern.CASE_INSENSITIVE);
        Pattern pattern4 = Pattern.compile("33\\.[0]", Pattern.CASE_INSENSITIVE);
        Pattern pattern3 = Pattern.compile("3[0-3]{1}", Pattern.CASE_INSENSITIVE);
        Pattern pattern = Pattern.compile("[1-2]{1}\\d", Pattern.CASE_INSENSITIVE);
        Pattern pattern0 = Pattern.compile("[1-2]{1}\\d\\.\\d", Pattern.CASE_INSENSITIVE);
        Pattern pattern1 = Pattern.compile("[0-9].\\d", Pattern.CASE_INSENSITIVE);
        Pattern pattern2 = Pattern.compile("[1-9]{1}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);
        Matcher matcher0 = pattern0.matcher(data);
        Matcher matcher1 = pattern1.matcher(data);
        Matcher matcher2 = pattern2.matcher(data);
        Matcher matcher3 = pattern3.matcher(data);
        Matcher matcher4 = pattern4.matcher(data);
        Matcher matcher5 = pattern5.matcher(data);
        if (matcher.matches() || matcher0.matches() || matcher1.matches() || matcher2.matches()
                || matcher3.matches() || matcher4.matches() || matcher5.matches()) {
            result = true;
        }
        return result;
    }

    // ת���ո�
    public static String replaceNull(String str) {
        return str;
    }

    // ת���س���
    public static String consertTo(String str) {
        return str;
    }

    public static String stringFormat(String url) {
        String temp = url;

        // �滻�س�
        // temp=temp.replaceAll("\n\n", "aa").replaceAll("\n",
        // "aa").replaceAll("\r", "aa").replaceAll("\r\n",
        // "aa").replaceAll("<br>", "aa");
        temp = temp.replaceAll("/(.*)\r\n|\n|\r/g", "aa");

        // �ж�%
        if (url.contains("%")) {
            Pattern p = Pattern.compile("\\%");// ����������ʽ���ո�հ��ַ�
            Matcher m = p.matcher(temp);// ƥ��
            temp = m.replaceAll("%25");// �滻���е�%Ϊ"%25"

        }
        // �ж�#
        if (url.contains("#")) {
            Pattern p = Pattern.compile("\\#");// ����������ʽ���ո�հ��ַ�
            Matcher m = p.matcher(temp);// ƥ��
            temp = m.replaceAll("%23");// �滻���е�#Ϊ"%23"

        }
        // �ж�"
        if (url.contains("\"")) {
            Pattern p = Pattern.compile("\\\"");// ����������ʽ���
            Matcher m = p.matcher(temp);// ƥ��
            temp = m.replaceAll("%22");// �滻���е�"Ϊ"%22"

        }

        // �ж�\
        if (url.contains("\\")) {
            Pattern p = Pattern.compile("\\\\");// ����������ʽ���
            Matcher m = p.matcher(temp);// ƥ��
            temp = m.replaceAll("%5C");// �滻���е�\Ϊ"%5C"
        }
        // �ж�+
        if (url.contains("+")) {
            Pattern p = Pattern.compile("\\+");// ����������ʽ���
            Matcher m = p.matcher(temp);// ƥ��
            temp = m.replaceAll("%2B");// �滻���е�+Ϊ"%2B"

        }
        // �ж�^
        if (url.contains("^")) {
            Pattern p = Pattern.compile("\\^");// ����������ʽ���
            Matcher m = p.matcher(temp);// ƥ��
            temp = m.replaceAll("%5E");// �滻���е�^Ϊ"%5E"

        }
        // �жϡ�
        if (url.contains("`")) {
            Pattern p = Pattern.compile("\\`");// ����������ʽ���
            Matcher m = p.matcher(temp);// ƥ��
            temp = m.replaceAll("%60");// �滻���е�^Ϊ"%5E"

        }

        // �ж�<
        if (url.contains("<")) {
            Pattern p = Pattern.compile("\\<");// ����������ʽ���
            Matcher m = p.matcher(temp);// ƥ��
            temp = m.replaceAll("%3C");// �滻���е�^Ϊ"%5E"
        }

        // �ж�>
        if (url.contains(">")) {
            Pattern p = Pattern.compile("\\>");// ����������ʽ���
            Matcher m = p.matcher(temp);// ƥ��
            temp = m.replaceAll("%3E");// �滻���е�^Ϊ"%5E"
        }

        // �жϿո�
        String[] str = temp.split("\\?");
        if (str.length > 1) {
            String content = temp.substring(str[0].length() + 1);
            // Pattern p = Pattern.compile("\\s*|\t");// ����������ʽ���
            // Matcher m = p.matcher(content);// ƥ��
            // content= m.replaceAll("%2O");// �滻���еĿո�Ϊ""
            content = content.replaceAll(" ", "+");
            content = content.replaceAll("��", "+");
            temp = str[0] + "?" + content;
        }
        // Log.e("test", "temp:"+temp);
        return temp;

    }

    //
    public static String removeNull(String str) {
        return str.replace("null", "");
    }


    public static String convertTo(String str) {
        // �ж�&
        if (str.contains("&")) {
            Pattern p = Pattern.compile("\\&");// ����������ʽ���
            Matcher m = p.matcher(str);// ƥ��
            str = m.replaceAll("%26");// �滻���е�&Ϊ"%26"

        }
        ;
        return str;
    }

    /**
     * �ж��Ƿ�null���߿��ַ� ����
     *
     * @param string
     */
    public static boolean isNull(String string) {
        boolean isNull = true;
        if (string != null && !"".equals(string) && !"null".equals(string) && !"��".equals(string)) {
            isNull = false;
        }
        return isNull;
    }

    /**
     * �ж��Ƿ�null���߿��ַ� ����
     *
     * @param string
     */
    public static String isNullRePlace(String string) {
        String str = "";
        if (!"ȫ��".equals(string) && string != null && !"".equals(string) && !"null".equals(string)) {
            return string;
        } else {
            return str;
        }

    }

    // �ж��Ƿ��������
    public static boolean StringFilter(String str) {
        // ֻ������ĸ������
        // String regEx = "[^a-zA-Z0-9]";
        // ��������������ַ�
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~��@#��%����&*��������+|{}������������������������]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    // �ж��Ƿ��������
    public static boolean StringFilter2(String str) {
        // ֻ������ĸ������
        // String regEx = "[^a-zA-Z0-9]";
        // ��������������ַ�
        String regEx = "[']";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        // return m.replaceAll("").trim();
        return m.find();
    }

    /**
     * �ж���Ϊ������
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {

        Pattern pattern = Pattern.compile("[0-9]*");

        Matcher isNum = pattern.matcher(str);

        if (!isNum.matches()) {

            return false;

        }

        return true;

    }

    // ������ʾ��ʾ��
    public static void showToast(String message, Context context) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    // ������ʾ��ʾ��
    public static void showToast(int messageId, Context context) {
        Toast toast = Toast.makeText(context, messageId, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * ����Ѫ���豸�����к�
     * @param hexStr
     * @return
     */
    public static String resolveBleMsg(String hexStr) {
        //00010001313530363031363533000000
        //000100304d3730435f30303400000000
        int lenth = 2;
        int i = 4;
        String currentResult = "";
        while (i * lenth < hexStr.length()) {
            String targetStr = "";
            if (i * lenth + lenth > hexStr.length()) {
                targetStr = hexStr.substring(i * lenth, hexStr.length() );
            }else {
                targetStr = hexStr.substring(i * lenth, i * lenth + lenth );
            }

            long hexValue = StringUtil.strtoul(targetStr, 16);
            int value = Integer.valueOf(String.valueOf(hexValue));
            if ((char)value == 0) {
                break;
            }
            currentResult = currentResult.concat((char) value + "");
            i++;
        }

        return  currentResult;
    }


    /**
     * ������ȡ������������16��������
     * @param hexStr
     * @return ���ص�Peripheral�����е�modelΪ���ε��ַ���
     */
    public static Peripheral resolveBleMsg_bp(String hexStr) {
        //00010001313530363031363533000000
        int lenth = 2;
        int i = 4;
        String currentResult = "";
        while (i * lenth < hexStr.length()) {
            String targetStr = "";
            if (i * lenth + lenth > hexStr.length()) {
                targetStr = hexStr.substring(i * lenth, hexStr.length() );
            }else {
                targetStr = hexStr.substring(i * lenth, i * lenth + lenth );
            }

            long hexValue = StringUtill.strtoul(targetStr, 16);
            int value = Integer.valueOf(String.valueOf(hexValue));
            if ((char)value == 0) {
                break;
            }
            currentResult = currentResult.concat((char) value + "");
            i++;
        }
        long protocolVersion = StringUtill.strtoul(hexStr.substring(2, 4), 16);
        long deviceModel = StringUtill.strtoul(hexStr.substring(6, 8), 16);

        Peripheral peripheral = new Peripheral();
        peripheral.setModel(String.valueOf(deviceModel));
        peripheral.setPreipheralSN(currentResult);
        peripheral.setProtocolVer(protocolVersion);
        return  peripheral;
    }



}
