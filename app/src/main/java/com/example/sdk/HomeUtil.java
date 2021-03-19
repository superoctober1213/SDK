package com.example.sdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import android.util.Log;

/**
 * ��ҳ�õ��ĸ��ֹ��߷�����
 * 
 * @author omesoft_tkz
 * 
 *         2015-3-23����9:15:36
 */
@SuppressLint("SimpleDateFormat")
public class HomeUtil {


    private final static String TAG = "HomeUtil";

    public static String getNum(String str) {
	if (str == null) {
	    return "";
	} else {
	    String regEx = "[^0-9]";
	    Pattern p = Pattern.compile(regEx);
	    Matcher m = p.matcher(str);
	    return m.replaceAll("").trim();
	}
    }

    /**
     * ĳ���ĳ�����ж�����
     * 
     * @param year
     * @param morch
     * @return
     */
    public static int getdays(int year, int morch) {
	if (morch < 13 && morch > 0) {
	    int[] monDays = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
		    30, 31 };
	    if (((year) % 4 == 0 && (year) % 100 != 0 && morch == 2)
		    || (year) % 400 == 0) {
		return monDays[morch - 1] + 1;
	    } else {
		return monDays[morch - 1];
	    }
	}
	return 0;
    }



    public static int getMonthFirstDayWeek(long startday) {
	Calendar cal = Calendar.getInstance();
	cal.setTimeInMillis(startday);
	cal.set(Calendar.DAY_OF_MONTH, 1);
	return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * �������� �ó����µĵ�һ�������ڼ���
     * 
     * @param
     * @return
     */
    public static int getMonthFirstDayWeek(int Year, int Month) {
	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.YEAR, Year);
	cal.set(Calendar.MONTH, Month);
	cal.set(Calendar.DAY_OF_MONTH, 1);
	return cal.get(Calendar.DAY_OF_WEEK);
    }

    /** �������ڣ��������ڼ��� */
    public static int getMonthFirstDayWeek(int year, int month, int date) {
	Calendar cal = Calendar.getInstance();
	cal.set(year, month, date);
	return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }


    /**
     * ��ȡ�����ID��
     * 
     * @return
     */
    public static String getTodayID() {
	Calendar cal = Calendar.getInstance();
	return cal.get(Calendar.YEAR) + TwoChange(cal.get(Calendar.MONTH) + 1)
		+ TwoChange(cal.get(Calendar.DATE));
    }

    /**
     * ��ȡ�����ID��
     * 
     * @return
     */
    public static String getTomorrowID() {
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DATE, 1);
	return cal.get(Calendar.YEAR) + TwoChange(cal.get(Calendar.MONTH) + 1)
		+ TwoChange(cal.get(Calendar.DATE));
    }

    /**
     * �ж������ǲ���δ���ġ�
     * 
     * @param context
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static boolean IsFutureDate(Context context, int year, int month,
	    int day) {
	// ��ȡϵͳ�����ڣ����ڱȽϣ����û�����������Ƿ���δ���ġ�
	Calendar c = Calendar.getInstance();
	c.set(Calendar.YEAR, year);
	c.set(Calendar.MONTH, month - 1);
	c.set(Calendar.DATE, day);
		// ����δ��������
		return c.getTimeInMillis() > Calendar.getInstance().getTimeInMillis();

    }

    public static String TwoChange(int num) {
	if (num > 9) {
	    return num + "";
	} else {
	    return "0" + num;
	}
    }

    /**
     * ����ID��ʽ���������� ���� ����20150202������2015-02-02
     * 
     * @param ID
     * @return
     */
    public static String ID2Date(String ID) {
	return ID.substring(0, 4) + "-" + ID.substring(4, 6) + "-"
		+ ID.substring(6, 8);
    }

    /**
     * �������� ����ID ���� ����2015-02-02������20150202
     * 
     * @param
     * @return
     */
    public static String Date2ID(String Date) {
	return Date.substring(0, 4) + Date.substring(5, 7)
		+ Date.substring(8, 10);
    }

    /**
     * ��ҳ��ע��ת����Str2Boolean[]
     * 
     * @param str
     * @return
     */
    public static boolean[] Str2Boolean(String str) {
	boolean[] re = new boolean[] { false, false, false, false, false, false };
	// Log.e("bbb", "str::" + str);
	if (str != null && !str.equals("") && !str.equals("null")) {
	    String[] m = str.split(",");
	    int size = m.length;
	    try {
		for (int i = 0; i < size; i++) {
		    int witch = Integer.parseInt(m[i]);
		    if (witch > 5) {
			witch = 5;
		    }
		    re[witch] = true;
		}
	    } catch (Exception e) {

	    }
	}
	return re;
    }

    /**
     * ��ҳ��ע��ת����boolean[]ת����Str���Ա������ݿ⡣
     * 
     * @param mInt
     * @return
     */
    public static String Boolean2Str(boolean[] mInt) {
	String reString = "";
	boolean isFirst = true;
	for (int i = 0; i < 6; i++) {
	    if (mInt[i]) {
		if (isFirst) {
		    reString = reString + i;
		    isFirst = false;
		} else {
		    reString = reString + "," + i;
		}
	    }
	}
	return reString;
    }

    public static int dip2px(Context context, float dipValue) {
	final float scale = context.getResources().getDisplayMetrics().density;
	return (int) (dipValue * scale + 0.5f);
    }

    /**
     * ����Ԥ�⣬Ӧ�÷���3�����ڵ����ݰɣ�Ĭ�ϴӽ���������������ڡ�
     * 
     * @param StartDate
     *            ��ʽ��2015-12-12���м���-�ָ�
     * @param Cycle
     *            ��������
     * @param Period
     *            �¾�����
     * @return
     */

    /**
     * ����Ԥ����¾��ڡ���
     * 
     * @param StartDate
     * @param
     * @param
     * @return
     */

    /**
     * ����δ���������ڡ���
     * 
     * @param context
     * @param StartDate
     * @param Cycle
     * @param Period
     * @param Phase
     * @param FutureMenstruation
     * @return
     */

    /**
     * �����ȥ�������ڡ���
     * 
     * @param context
     * @param months
     * @return
     */


    /**
     * Calendar ת�ַ�����ʱ���ʽ��ת����
     * 
     * @param
     * @return
     */
    public static String getDateFormatToString(Calendar c) {
	Date date = c.getTime();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	String dateStr = dateFormat.format(date);
	return dateStr;
    }

    /**
     * ���չ��֡�Ĭ��25�ꡣ
     * 
     * @param c
     * @return
     */
    public static String getDateFormatToString2(Calendar c, int much) {
	c.add(Calendar.YEAR, much);
	Date date = c.getTime();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	String dateStr = dateFormat.format(date);
	return dateStr;
    }

    public static String getDateFormatToString(Date date) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	String dateStr = dateFormat.format(date);
	return dateStr;
    }

    /**
     * ���� һ�����ڡ�����һ���µ��ַ������� ���� ����201503���򷵻�String[]{"201502","201503","201504"}
     * 
     * @param month
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String[] getMonths(String month) {
	String[] months = new String[3];
	months[1] = month;

	String y = month.substring(0, 4);
	String m = month.substring(4, 6);
	Calendar calendar = Calendar.getInstance();
	// 0����һ�¡�
	calendar.set(Calendar.YEAR, Integer.parseInt(y));
	calendar.set(Calendar.MONTH, Integer.parseInt(m) - 1);
	calendar.add(Calendar.MONTH, -1);
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
	months[0] = dateFormat.format(calendar.getTime());
	calendar.add(Calendar.MONTH, 2);
	months[2] = dateFormat.format(calendar.getTime());

	return months;
    }

    /**
     * �жϷ�������ȡ�����������Ƿ����Ҫ�� 12-50�ꡣ
     * 
     * @param myString
     * @return
     */
    public static boolean myBirthDay(String myString) {
	String max = getDateFormatToString2(Calendar.getInstance(), -50);
	String min = getDateFormatToString2(Calendar.getInstance(), -12);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date mindle = new Date();
	Date maxdate = new Date();
	Date mindate = new Date();
	try {
	    mindle = sdf.parse(myString);
	    maxdate = sdf.parse(max);
	    mindate = sdf.parse(min);
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	boolean flag = mindle.before(maxdate);
	boolean is = mindate.before(mindle);
		return flag && is;
    }

    /**
     * �������������������졣(��ʽ :yyyyMMdd,yyyy-MM-dd )
     * 
     * @param one
     * @param two
     * @return
     */
    public static int DifferDays(String one, String two) {
	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
	Long c = null;
	try {
	    c = sf.parse(one).getTime() - sf.parse(two).getTime();
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	long d = c / 1000 / 60 / 60 / 24;// ��
	// Log.v("test", "������������" + d);
	return (int) d;
    }

    /** �����������뵱ǰʱ�������� */
    public static int DifferTime(int time) {
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date date = new Date();
	Long c = null;
	try {
	    c = date.getTime() - sf.parse("2000-01-01 00:00:00").getTime();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	long d = c / 1000 - time;
	// Log.e("test", "d::" + d);
	return (int) d;
    }

    /** ��ȡ��ǰʱ�����2000-01-01 00:00:00 �ж����롣 */
    public static int[] getTimeByte() {
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date date = new Date();
	Long c = null;
	try {
	    c = date.getTime() - sf.parse("2000-01-01 00:00:00").getTime();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	int d = (int) (c / 1000);
	int six = d / (256 * 256 * 256);
	int five = (d - six * 256 * 256 * 256) / (256 * 256);
	int four = (d - six * 256 * 256 * 256 - five * 256 * 256) / 256;
	int three = (d - six * 256 * 256 * 256 - five * 256 * 256 - four * 256);
	return new int[] { three, four, five, six };
    }

    /**
     * ��ȡ���ݿ��ID������
     * 
     * @param num
     * @return
     */
    public static int[] getDateID(int num) {
	int two = num / 256;
	int one = num - two * 256;
	return new int[] { one, two };
    }

    /** һ�������ʱ�� 00��00��00 */
    public static int EARLIEST_TIME = 1;
    /** һ�������ʱ�� 23��59��59 */
    public static int LATEST_TIME = 2;

    /**
     * ��ȡһ������������ʱ�䡣
     * 
     * @param ID
     * @param witch
     * @return
     */
    public static String getTempTime(String ID, int witch) {
	String dateString = ID2Date(ID);
	if (witch == EARLIEST_TIME) {
	    dateString = dateString + " 00:00:00";
	} else {
	    dateString = dateString + " 23:59:59";
	}
	return dateString;
    }

    /**
     * �����������������µ�ʱ�䡣����
     * 
     * @return
     */
    public static String BuleToTime(int[] times) {
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	for (int i = 0; i < times.length; i++) {
	    // Log.e("test", "BuleToTime::" + times[i]);
	    times[i] = times[i] > 0 ? times[i] : (times[i] + 256);
	}
	int time = times[3] * 256 * 256 * 256 + times[2] * 256 * 256 + times[1]
		* 256 + times[0];
	Long c = null;
	try {
	    c = sf.parse("2000-01-01 00:00:00").getTime();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	Date d = new Date((long) time * (long) 1000 + c);
	return sf.format(d);
    }

    /**
     * ���������Ҳ����0xAA�������Ҫ�ഫһ��0xAA���������ֽں�У���ֽڱ��ֲ��䡣���ʱҪ��ȥ�������0xAA���ټ��У�顣
     * 
     * @return
     */
    public static byte[] CheckByte(List<Byte> sendbytes) {
	if (sendbytes.size() > 0) {
	    List<Byte> sendbytes2 = new ArrayList<Byte>();
	    int size = sendbytes.size();
	    sendbytes2.add(sendbytes.get(0));
	    sendbytes2.add(sendbytes.get(1));
	    for (int i = 2; i < size - 1; i++) {
		if (sendbytes.get(i) == -86) {
		    sendbytes2.add(sendbytes.get(i));
		    sendbytes2.add(sendbytes.get(i));
		} else {
		    sendbytes2.add(sendbytes.get(i));
		}
	    }
	    sendbytes2.add(sendbytes.get(size - 1));
	    size = sendbytes2.size();
	    byte[] wbyte = new byte[size];
	    for (int i = 0; i < size; i++) {
		wbyte[i] = sendbytes2.get(i);
	    }
	    return wbyte;
	} else {
	    return null;
	}
    }

    /**
     * �������������������졣(��ʽ yyyy-MM-dd��yyyyMMdd )
     * 
     * @param one
     * @param two
     * @return
     */
    public static int DifferDays1(String one, String two) {
	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
	// Log.v("HomeFragment", "one::" + one + "::two::" + two);
	Long c = null;
	try {
	    c = sf.parse(two).getTime() - sf1.parse(one).getTime();
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	long d = c / 1000 / 60 / 60 / 24;// ��
	// Log.v("test", "������������" + d);
	return (int) d;
    }

    /**
     * �������������������졣(��ʽyyyyMMdd�� yyyy-MM-dd )
     * 
     * @param one
     * @param two
     * @return
     */
    public static int DifferDays2(String one, String two) {
	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
	Long c = null;
	try {
	    c = sf.parse(one).getTime() - sf1.parse(two).getTime();
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	long d = c / 1000 / 60 / 60 / 24;// ��
	// Log.v("test", "������������" + d);
	return (int) d;
    }

    /**
     * �������������������졣(��ʽyyyyMMdd�� yyyyMMdd )
     * 
     * @param one
     * @param two
     * @return
     */
    public static int DifferDays3(String one, String two) {
	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
	Long c = null;
	try {
	    c = sf.parse(one).getTime() - sf1.parse(two).getTime();
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	long d = c / 1000 / 60 / 60 / 24;// ��
	// Log.v("test", "������������" + d);
	return (int) d;
    }

    /**
     * �������������������졣(��ʽyyyy-MM-dd�� yyyy-MM-dd )
     * 
     * @param one
     * @param two
     * @return
     */
    public static int DifferDays4(String one, String two) {
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
	Long c = null;
	try {
	    c = sf.parse(one).getTime() - sf1.parse(two).getTime();
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	long d = c / 1000 / 60 / 60 / 24;// ��
	// Log.v("test", "������������" + d);
	return (int) d;
    }

    /**
     * ���ز���ֵ��true:������ť��ѡ�У�false:������ť��ѡ�С�
     * 
     * @param MenstrualDays
     * @param StartDate
     * @return
     */
    public static boolean IsEnd(int MenstrualDays, String TempID,
	    String StartDate) {
	SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
	Long c = null;
	try {
	    c = sf.parse(TempID).getTime() - sf1.parse(StartDate).getTime();
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	long d = c / 1000 / 60 / 60 / 24;// ��
	// Log.v("test", "������������" + d);
		return (int) d == (MenstrualDays - 1);
    }

    /**
     * ��ȡ���ڽ��������ڡ�
     * 
     * @param Date
     * @param i
     * @return
     */
    public static String getEndDate(String Date, int i) {
	SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
	Date date = null;
	try {
	    date = sf1.parse(Date);
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	cal.add(Calendar.DATE, i);
	return sf1.format(cal.getTime());
    }

    public static String getYMD2ID(int y, int m, int d) {
	return y + "-" + TwoChange(m) + "-" + TwoChange(d);
    }

    public static String getYMD2ID2(int y, int m, int d) {
	return y + TwoChange(m) + TwoChange(d);
    }

    /**
     * һ���¾����ڵ�ȫ��ID��
     * 
     * @param StartDate
     * @param days
     * @return
     */
    public static String[] getCylceDate(String StartDate, int days) {
	String[] mDates = new String[days];
	Date d = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	try {
	    d = dateFormat.parse(StartDate);
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	Calendar cal = Calendar.getInstance();
	cal.setTime(d);
	for (int i = 0; i < days; i++) {
	    mDates[i] = cal.get(Calendar.YEAR)
		    + TwoChange(cal.get(Calendar.MONTH) + 1)
		    + TwoChange(cal.get(Calendar.DATE));
	    cal.add(Calendar.DATE, 1);
	}
	return mDates;
    }

    /**
     * �Ƚ��������ڵĴ�С������һ���Ǵ󣬷���true���ڶ����Ǵ󣬷���false��
     * 
     * @param YID
     * @param TYID
     * @return
     */
    public static boolean InputComparingDate(String YID, String TYID) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date d = new Date(), d1 = new Date();
	try {
	    d = dateFormat.parse(YID);
	    d1 = dateFormat.parse(TYID);
	} catch (ParseException e) {
	    e.printStackTrace();
	    // Log.e("TAG", "e::" + e.toString());
	}
		return !d.before(d1);
    }

    /**
     * ���㱾���¾�������һ���¾��������졣
     * 
     * @param PastStartMenstruation
     * @return
     */
    public static int[] getPastDayOfOvulation(String[] PastStartMenstruation) {
	// Log.v(TAG, "getPastDayOfOvulation::");
	if (PastStartMenstruation.length > 1) {
	    int[] cycle = new int[PastStartMenstruation.length - 1];
	    // ���������¾���ʼ���������졣
	    for (int i = 0; i < PastStartMenstruation.length - 1; i++) {
		int days = DifferDays4(PastStartMenstruation[i + 1],
			PastStartMenstruation[i]) + 1;
		// Log.v(TAG, "PastStartMenstruation[i + 1]::" +
		// PastStartMenstruation[i + 1]);
		// Log.v(TAG, "PastStartMenstruation[i  ]::" +
		// PastStartMenstruation[i]);
		// Log.v(TAG, "days::" + days);
		cycle[i] = days - 14;
	    }
	    return cycle;
	}
	return null;
    }

    /**
     * �Ƚ������¾���¼��ʱ�䣬first��Ļ����򷵻�ture��last��Ļ��򷵻�false�� last ����Ϊ�յĻ�����lastȡ��ǰϵͳʱ�䡣
     * 
     * @param first
     * @param last
     * @return
     */
    public static boolean DiffTwoTime(String first, String last) {
	String format = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat dateFormat = new SimpleDateFormat(format);
	Long c = (long) 0;

	try {
	    Date aDate = dateFormat.parse(first);
	    Date bDate = dateFormat.parse(last);
	    c = aDate.getTime() - bDate.getTime();
	} catch (ParseException e) {
	    e.printStackTrace();
	    // Log.e("TAG", "DiffTwoTime::e::" + e.toString());
	}
	// Log.e("TAG", "DiffTwoTime::c::" + c);
		return c < 0;
    }

    /**
     * �����¶�����2λС���㡣����
     * 
     * @param editText
     */
    public static void setPricePoint(final EditText editText) {
	editText.addTextChangedListener(new TextWatcher() {
	    @Override
	    public void onTextChanged(CharSequence s, int start, int before,
		    int count) {
		if (s.toString().contains(".")) {
		    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
			s = s.toString().subSequence(0,
				s.toString().indexOf(".") + 3);
			editText.setText(s);
			editText.setSelection(s.length());
		    }
		}
		if (s.toString().trim().substring(0).equals(".")) {
		    s = "0" + s;
		    editText.setText(s);
		    editText.setSelection(2);
		}
		if (s.toString().startsWith("0")
			&& s.toString().trim().length() > 1) {
		    if (!s.toString().substring(1, 2).equals(".")) {
			editText.setText(s.subSequence(0, 1));
			editText.setSelection(1);
			return;
		    }
		}
	    }

	    @Override
	    public void beforeTextChanged(CharSequence s, int start, int count,
		    int after) {
	    }

	    @Override
	    public void afterTextChanged(Editable s) {
	    }
	});
    }

    /**
     * ���϶�ת���϶ȡ�
     * 
     * @param Degrees
     * @return
     */
    public static float DegreesToFahrenheit(float Degrees) {
	// ���϶ȡ�9/5+32=���϶�
	return TwoReservations((Degrees * 9.0F) / 5.0F + 32.0F);
    }

    /**
     * ������λС���㡣
     * 
     * @param f
     * @return
     */
    public static float TwoReservations(float f) {
	BigDecimal bg = new BigDecimal(f);
	float f1 = (float) bg.setScale(2, BigDecimal.ROUND_HALF_UP)
		.doubleValue();
	return f1;
    }

    public static float FahrenheitToDegrees(float Fahrenheit) {
	// ���϶� = (���϶�-32)*5/9
	return TwoReservations((Fahrenheit - 32.0F) * 5.0F / 9.0F);
    }

    /**
     * ��ȡ�ص�����View�����¸���������
     * 
     * @param context
     * @return
     */

    // /**
    // * ���뵭���Ķ���Ч����
    // *
    // * @param context
    // * @return
    // */
    // public static Animation getAnimation2(Context context) {
    // Animation animation = new AnimationUtils().loadAnimation(context,
    // R.anim.alpha_in);
    // animation.setAnimationListener(new Animation.AnimationListener() {
    // @Override
    // public void onAnimationStart(Animation animation) {
    //
    // }
    //
    // @Override
    // public void onAnimationRepeat(Animation animation) {
    // }
    //
    // @Override
    // public void onAnimationEnd(Animation animation) {
    // // animation.start();
    // }
    // });
    // return animation;
    // }

    /**
     * @param
     */
    // public static void getWitchIdentification(Context context, TempDTO
    // mTempDTO, View view_two) {
    // /** ���ڵ��¼�ѡ�� ��ͬ������ð��ʧ�ߣ����ƣ���ҩ������Чʱ��������£� */
    // boolean[] mEvent;
    // boolean have_remark = false, have_tem = false;
    //
    // if (mTempDTO.getTemp() > 0) {
    // // ���¶ȼ�¼������
    // have_tem = true;
    // } else {
    // have_tem = false;
    //
    // }
    // if (mTempDTO.getEvent() != null && !mTempDTO.getEvent().equals("")
    // && !mTempDTO.getEvent().equals("null")) {
    // // ���¼���¼����Ҫ��ϸ�����Ǹ�ѡ�С���
    // mEvent = HomeUtil.Str2Boolean(mTempDTO.getEvent());
    // } else {
    // mEvent = new boolean[] { false, false, false, false, false };
    // }
    // if (mTempDTO.getRemark() != null && !mTempDTO.getRemark().equals("")
    // && !mTempDTO.getRemark().equals("null")) {
    // // ��ע�м�¼������
    // have_remark = true;
    // } else {
    // have_remark = false;
    // }
    // if (mEvent[0]) {
    // // ͬ�� ��������ʶ�ĵȼ��ߡ���
    // if (view_two != null) {
    // view_two.setVisibility(View.VISIBLE);
    // view_two.setBackground(context.getResources().getDrawable(R.drawable.home_love));
    // }
    // } else {
    // if (mEvent[1] | mEvent[2] | mEvent[3] | mEvent[4]) {
    // // ��ʾ�м�¼��
    // if (view_two != null) {
    // view_two.setVisibility(View.VISIBLE);
    // view_two.setBackground(context.getResources().getDrawable(R.drawable.home_record));
    // }
    // } else if (have_remark | have_tem) {
    // // ��ʾ�м�¼��
    // if (view_two != null) {
    // view_two.setVisibility(View.VISIBLE);
    // view_two.setBackground(context.getResources().getDrawable(R.drawable.home_record));
    // }
    // } else {
    // // ��ʾû�м�¼��
    // if (view_two != null) {
    // view_two.setVisibility(View.INVISIBLE);
    // }
    // }
    // }
    // }

    public static void MysetText(EditText v, float value) {
	DecimalFormat decimalFormat = new DecimalFormat("0.00");// ���췽�����ַ���ʽ�������С������2λ,����0����.
	v.setText((decimalFormat.format(value) + "").replace(",", "."));
    }

    public static void MysetText(TextView v, float value) {
	DecimalFormat decimalFormat = new DecimalFormat("0.00");// ���췽�����ַ���ʽ�������С������2λ,����0����.
	v.setText((decimalFormat.format(value) + "").replace(",", "."));
    }

    public static String MysetText(float value) {
	DecimalFormat decimalFormat = new DecimalFormat("0.00");// ���췽�����ַ���ʽ�������С������2λ,����0����.
	// v.setText(decimalFormat.format(value) + "");
	return (decimalFormat.format(value) + "").replace(",", ".");
    }

    /**
     * ��ȡ��һ��ͬ����ʱ�䡣
     * 
     * @param context
     * @param res
     * @return
     */
    public static String getLastSyncTime(Context context, int res) {
	// if
	// (SharedPreferencesUtil.getEquipmentSynchronizationTime(context).equals(""))
	// {
	// // û�л�ȡ���ϴθ��豸ͬ����ʱ����ʲôʱ��
	// return context.getResources().getString(res);
	// } else {
	// return context.getResources().getString(res) + " "
	// + context.getResources().getString(R.string.last_time_sync)
	// + SharedPreferencesUtil.getEquipmentSynchronizationTime(context);
	//
	// }

	return context.getResources().getString(res);
    }



}
