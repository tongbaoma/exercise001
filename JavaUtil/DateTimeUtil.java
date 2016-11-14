package jp.microad.blade.batch.kizasi.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DateTimeUtil {

    public static final String DATEFORMAT_SHORT = "yyyyMMdd";
    public static final String DATEFORMAT_YMDHMS = "yyyyMMddHHmmss";
    public static final String DATEFORMAT_YMDHMS2 = "yyyy-MM-dd HH:mm:ss";

    /**
     * 時間のformatを取得
     *
     * @return
     */
    public static String formatShort(Date date) {
        return format(date, DATEFORMAT_SHORT);
    }

    public static String formatYmdHms(Date date) {
        return format(date, DATEFORMAT_YMDHMS);
    }

    public static String formatYmdHms2(Date date) {
        return format(date, DATEFORMAT_YMDHMS2);
    }

    public static String format(Date date, String pattern) {
        if (date == null || pattern == null) {
            return null;
        }
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 現在の実行時刻を返す
     *
     * @return
     */
    public static Date getExecTime() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 両方の日付を比較
     *
     * @return
     */
    public static int getDiffDate(int yyyy, int mm, int dd) {
        Calendar nowCal = Calendar.getInstance();

        Calendar diffCal = Calendar.getInstance();
        diffCal.clear();
        diffCal.set(Calendar.YEAR, yyyy);
        diffCal.set(Calendar.MONTH, mm - 1);
        diffCal.set(Calendar.DAY_OF_MONTH, dd);

        return getDaysDelta(nowCal.getTime(), diffCal.getTime());
    }

    public static int getDaysDelta(Date a, Date b) {
        a = DateUtils.truncate(a, Calendar.DATE);
        b = DateUtils.truncate(b, Calendar.DATE);
        return (int) ((b.getTime() - a.getTime()) / DateUtils.MILLIS_PER_DAY);
    }

    /**
     * 両方の時刻を分までで比較し、引数より進んでいたら 0秒なのかをチェック
     * 
     * @param pastDate
     * @return
     */
    public static boolean checkExecTime(Date pastDate) {
        if (pastDate == null) {
            return false;
        }
        Calendar pastCal = Calendar.getInstance();
        pastCal.setTime(pastDate);

        Calendar nowCal = Calendar.getInstance();

        if (nowCal.get(Calendar.YEAR) > pastCal.get(Calendar.YEAR)
                || nowCal.get(Calendar.MONTH) > pastCal.get(Calendar.MONTH)
                || nowCal.get(Calendar.DAY_OF_MONTH) > pastCal.get(Calendar.DAY_OF_MONTH)
                || nowCal.get(Calendar.HOUR_OF_DAY) > pastCal.get(Calendar.HOUR_OF_DAY)
                || nowCal.get(Calendar.MINUTE) > pastCal.get(Calendar.MINUTE)) {
            int nowSecond = nowCal.get(Calendar.SECOND);
            if (nowSecond == 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * "-"が付いてる文字列の日付をDate型に変換する。
     * 
     * @param databeseSt 文字列の日付
     * @return 変換後のDate型日付
     */
    public static final Date getDate(String databeseSt) {
        databeseSt = databeseSt.replaceAll("-", "");
        String year = databeseSt.substring(0, 4);
        String month = databeseSt.substring(4, 6);
        String day = databeseSt.substring(6, 8);

        Calendar cal = Calendar.getInstance();
        cal.setLenient(false);
        cal.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day), 0, 0, 0);
        long longtime = (cal.getTime().getTime() / 1000l) * 1000l;
        Date ret = new Date(longtime);
        return ret;
    }

    /**
     * "/"が付いてる文字列の日付をDate型に変換する。
     * 
     * @param databeseSt 文字列の日付
     * @return 変換後のDate型日付
     */
    public static final Date getDateSlash(String databeseSt) {
        if (StringUtils.isEmpty(databeseSt)) {
            return null;
        }
        databeseSt = databeseSt.replaceAll("/", "");
        String year = databeseSt.substring(0, 4);
        String month = databeseSt.substring(4, 6);
        String day = databeseSt.substring(6, 8);

        Calendar cal = Calendar.getInstance();
        cal.setLenient(false);
        cal.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day), 0, 0, 0);
        long longtime = (cal.getTime().getTime() / 1000l) * 1000l;
        Date ret = new Date(longtime);
        return ret;
    }

    /**
     * "yyyy/MM/dd HH:mm:ss"形式の文字列の日付をDate型に変換する。
     * 
     * @param value 文字列の日付
     * @return 変換後の日付
     */
    public static final Date getDateSlashWithTime(String value) {
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            return formatter.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 文字列の日付(yyyy/MM/dd)の比較。
     * 
     * @param startDate 一番目日付
     * @param endDate 二番目日付
     * @return 一番目日付　>　二番目日付の場合、-1を返す<br />
     *             一番目日付　<　二番目日付の場合、1を返す<br />
     *             一番目日付　=　二番目日付の場合、0を返す
     */
    public static int dateCompare(String startDate, String endDate) {
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        try {
            long start = formatter.parse(startDate).getTime();

            long end = formatter.parse(endDate).getTime();

            if (start > end) {
                return -1;
            } else if (start < end) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 文字列の日付(yyyy/MM/dd HH:mm:ss)の比較。
     * 
     * @param startDate 一番目日付
     * @param endDate 二番目日付
     * @return 一番目日付　>　二番目日付の場合、-1を返す<br />
     *             一番目日付　<　二番目日付の場合、1を返す<br />
     *             一番目日付　=　二番目日付の場合、0を返す
     */
    public static int dateCompareWithTime(String startDate, String endDate) {
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            long start = formatter.parse(startDate).getTime();

            long end = formatter.parse(endDate).getTime();

            if (start > end) {
                return -1;
            } else if (start < end) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 本日日付を取得する。
     * 時分秒以下は切り捨てている。
     * 
     * @return 本日日付
     */
    public static Date getToday() {
        return DateUtils.truncate(new Date(), Calendar.DATE);
    }

    /**
     * 比較の文字列の日付が同じかをチェックする。
     * @param updateTime 対象日付
     * @param updateTimeDB 対象日付
     * 
     * @return 同じの場合trueを返す
     */
    public static boolean chkUpdateTimeSame(String updateTime, String updateTimeDB) {
        if (StringUtils.isEmpty(updateTime) || StringUtils.isEmpty(updateTimeDB)) {
            return false;
        }
        if (updateTime.equals(updateTimeDB)) {
            return true;
        }
        return false;
    }

    /**
     * ある日付の何日ごの日付を計算。
     * 
     * @param n 日数
     * @param date 対象日付
     * @return 計算した後の日付
     */
    public static Date nDaysAfter(int n, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    /**
     * 対象日付の週の開始日を取得する。
     * 
     * @param date 対象日付
     * @return 週の開始日
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return c.getTime();
    }

    /**
     * 対象日付の週の最終日を取得する。
     * 
     * @param date 対象日付
     * @return 週の最終日
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
        return c.getTime();
    }

    /**
     * 対象日付の月の開始日を取得する。
     * 
     * @param date 対象日付
     * @return 月の開始日
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
        return cal.getTime();
    }

    /**
     *  対象日付の月の終了日を取得する。
     * 
     * @param date 対象日付
     * @return 月の終了日
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 1, 0, 0, 0);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
        return cal.getTime();
    }

    /**
     * 対象日付の間の週間の数(weeks)を取得する。
     * 
     * @param startDate 開始対象日付
     * @param endDate 終了対象日付
     * 
     * @return 週間の数
     */
    public static int getWeeksBetweenDates(Date startDate, Date endDate) {

        long weeks = (((getLastDayOfWeek(endDate).getTime() - getFirstDayOfWeek(startDate).getTime())
                / (1000 * 60 * 60 * 24) + 1) / 7);
        return Integer.parseInt(weeks + "");
    }

    /**
     *  対象日付の間の月間の数(months)を取得する。
     * 
     * @param startDate 開始対象日付
     * @param endDate 終了対象日付
     * 
     * @return 月間の数
     */
    public static int getMonthsBetweenDates(Date startDate, Date endDate) {
        
        Calendar dateFrom = Calendar.getInstance();
        Calendar dateTo = Calendar.getInstance();
        dateTo.setTime(endDate);
        dateFrom.setTime(startDate);
        
        int year1 = dateFrom.get(Calendar.YEAR);
        int month1 = dateFrom.get(Calendar.MONTH);

        int year2 = dateTo.get(Calendar.YEAR);
        int month2 = dateTo.get(Calendar.MONTH);

        int months = ((year2 * 12) + month2) - ((year1 * 12) + month1) + 1;
        return months;
    }
    
    /**
     * 対象日付に指定された年の数を加算または減算する。
     * 
     * @param date 対象日付
     * @param years 指定された年の数
     * @return 加算または減算された日付
     */
    public static Date addYears(Date date, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR) + years, calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        return calendar.getTime(); 
    }
    
    /**
     * 対象日付に指定された年の数を加算または減算する。
     * 
     * @param date 対象日付
     * @param years 指定された年の数
     * @return 加算または減算された日付
     */
    public static Date diffYears(Date date, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR) - years, calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        return calendar.getTime(); 
    }
    
    /**
     * 対象日付の間の週を取得する。
     * 
     * @param startDate 開始日
     * @param endDate 終了日
     * @return 週のリスト
     */
    public static List<Map<String, Date>> getWeekSequenceBetweenDates(Date startDate, Date endDate) {

        List<Map<String, Date>> listDateMap = new ArrayList<Map<String, Date>>();
        Date tempDate = null;
        int i = getWeeksBetweenDates(startDate, endDate);

        Map<String, Date> dateMap = null;
        for (int j = 1; j <= i; j++) {
            dateMap = new HashMap<String, Date>();
            if (j == 1) {
                dateMap.put("startDate", startDate);
                dateMap.put("endDate", getLastDayOfWeek(startDate));
                tempDate = nDaysAfter(1, getLastDayOfWeek(startDate));
            } else if (j == i) {
                dateMap.put("startDate", getFirstDayOfWeek(endDate));
                dateMap.put("endDate", endDate);
            } else {
                dateMap.put("startDate", tempDate);
                dateMap.put("endDate", getLastDayOfWeek(tempDate));
                tempDate = nDaysAfter(1, getLastDayOfWeek(tempDate));
            }
            listDateMap.add(dateMap);
        }
        return listDateMap;
    }

    /**
     * 対象日付の間の月を取得する。
     * 
     * @param startDate 開始日
     * @param endDate 終了日
     * @return 月のリスト
     */
    public static List<Map<String, Date>> getMonthSequenceBetweenDates(Date startDate, Date endDate) {

        List<Map<String, Date>> listDateMap = new ArrayList<Map<String, Date>>();
        Date tempDate = null;
        int i = getMonthsBetweenDates(startDate, endDate);

        Map<String, Date> dateMap = null;
        for (int j = 1; j <= i; j++) {
            dateMap = new HashMap<String, Date>();
            if (j == 1) {
                dateMap.put("startDate", startDate);
                dateMap.put("endDate", getLastDayOfMonth(startDate));
                tempDate = nDaysAfter(1, getLastDayOfMonth(startDate));
            } else if (j == i) {
                dateMap.put("startDate", getFirstDayOfMonth(endDate));
                dateMap.put("endDate", endDate);
            } else {
                dateMap.put("startDate", tempDate);
                dateMap.put("endDate", getLastDayOfMonth(tempDate));
                tempDate = nDaysAfter(1, getLastDayOfMonth(tempDate));
            }
            listDateMap.add(dateMap);
        }
        return listDateMap;
    }
    
    /**
     * String型の日付をフォーマットに合わせてDate型に変換します。
     * 
     * @param date 日付
     * @param format フォーマットタイプ
     * @return 日付
     */
    public static Date getStringToFomratDate(String date, String format) {
        try {
            return DateUtils.parseDate(date, new String[]{format});
        }   catch (ParseException e) {
            throw new RuntimeException("フォーマットと異なります。");
        }
    }
    
    /**
     * 二つのデータは同じ週にあるかを判断する
     * 
     * @param date 日付
     * @param anotherDate 日付
     * @return true 同じ週
     */
    public static Boolean isSameWeek(Date date, Date anotherDate) {
        if (date == null || anotherDate == null) {
            return false;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(date);
        
        Calendar anotherCal = Calendar.getInstance();
        anotherCal.setFirstDayOfWeek(Calendar.MONDAY);
        anotherCal.setTime(anotherDate);
        
        int subWeek = cal.get(Calendar.WEEK_OF_YEAR) - anotherCal.get(Calendar.WEEK_OF_YEAR);
        if (subWeek == 0) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 二つのデータは同じ月にあるかを判断する
     * 
     * @param date 日付
     * @param anotherDate 日付
     * @return true 同じ月
     */
    public static Boolean isSameMonth(Date date, Date anotherDate) {
        if (date == null || anotherDate == null) {
            return false;
        }
        
        Calendar cal = Calendar.getInstance();
        Calendar anotherCal = Calendar.getInstance();
        cal.setTime(date);
        anotherCal.setTime(anotherDate);
        
        int subYear = cal.get(Calendar.YEAR) - anotherCal.get(Calendar.YEAR);
        int subMonth = cal.get(Calendar.MONTH) - anotherCal.get(Calendar.MONTH);
        if (subYear == 0 && subMonth == 0) {
            return true;
        }
        
        return false;
    }
    
    /**
     *  対象日付の先月の終了日を取得する。
     * 
     * @param date 対象日付
     * @return 先月の終了日
     */
    public static Date getBeforeMonthLastDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
}
