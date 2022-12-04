package boot;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期时间工具类
 *
 * @author gaotx
 */
//@Slf4j
public class DateTimeUtils {

  private static final Logger logger = LoggerFactory.getLogger(DateTimeUtils.class);
  /**
   * 数据库存储日期格式（如开票日期)
   */
  public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

  public static final String defaultFormat = "yyyy-MM-dd HH:mm:ss";

  public static final String DEFAULT_FORMAT_24H = "yyyy-MM-dd HH:mm:ss";

  public static final String DATE_FORMAT = "yyyy-MM-dd";

  public static final String YYYYMMDD = "yyyyMMdd";

  public static final String YYYYMM = "yyyyMM";

  public static final String YYYY_MM = "yyyy-MM";

  public static final String PATTERN_YYYY_MM = "[0-9]{4}-[0-9]{2}";

  public static final String PATTERN_YYYY_MM_DD = "[0-9]{4}-[0-9]{2}-[0-9]{2}";

  public static final String PATTERN_YYYYMMDD = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229))$";

  private DateTimeUtils() {
  }

  public static boolean checkFormat(String str, String pattern) {
    Pattern r = Pattern.compile(pattern);
    Matcher m = r.matcher(str);
    return m.matches();
  }

  public static String getCurYearFirst(String format) {
    Date firstDay = DateUtils.truncate(new Date(), Calendar.YEAR);
    return convertToString(firstDay, format);
  }

  /**
   * @param @param  format
   * @param @param  yyyy
   * @param @return 参数说明
   * @return String    返回类型
   * @Title: getYearFirst
   * @Description: 获取指定年的第一时间点
   * @author niujs
   * @date 2016年12月31日 下午3:38:31
   */
  public static String getYearFirst(String format, String yyyy) {
    Date firstDay = null;
    try {
      firstDay = DateUtils.truncate(new SimpleDateFormat("yyyy").parse(yyyy), Calendar.YEAR);
    } catch (ParseException e) {
      logger.error("时间类型转换失败。", e);
      return null;
    }
    return convertToString(firstDay, format);
  }

  public static String getCurYearLast(String format) {
    Calendar cal = Calendar.getInstance();
    Date lastDay = DateUtils.ceiling(new Date(), Calendar.YEAR);
    cal.setTime(lastDay);
    cal.add(Calendar.SECOND, -1);
    lastDay = cal.getTime();
    return convertToString(lastDay, format);
  }

  /**
   * @param @param  format
   * @param @param  yyyy
   * @param @return 参数说明
   * @return String    返回类型
   * @Title: getYearLast
   * @Description: 获取指定年的最后一时间点
   * @author niujs
   * @date 2017年1月3日 下午4:04:41
   */
  public static String getYearLast(String format, String yyyy) {
    Calendar cal = Calendar.getInstance();
    Date lastDay = null;
    try {
      lastDay = DateUtils.ceiling(new SimpleDateFormat("yyyy").parse(yyyy), Calendar.YEAR);
    } catch (ParseException e) {
      logger.error("时间类型转换失败。", e);
      return null;
    }
    cal.setTime(lastDay);
    cal.add(Calendar.SECOND, -1);
    lastDay = cal.getTime();
    return convertToString(lastDay, format);
  }

  public static String getCurMonthFirst(String format) {
    Date firstDay = DateUtils.truncate(new Date(), Calendar.MONTH);
    return convertToString(firstDay, format);
  }

  /**
   * @param @param  format 返回的时间戳格式
   * @param @param  yyyyMM 月份
   * @param @return 参数说明
   * @return String    返回类型
   * @Title: getMonthFirst
   * @Description: 获取传输月份的第一刻时间戳
   * @author niujs
   * @date 2016年12月22日 下午10:33:42
   */
  public static String getMonthFirst(String format, String yyyyMM) {

    return getMonthFirst("yyyyMM", format, yyyyMM);
  }

  public static String getMonthFirst(String inputFormat, String outputFormat, String dateStr) {
    //TODO 代码整合复用
    Date firstDay = null;
    try {
      firstDay = DateUtils
          .truncate(new SimpleDateFormat(inputFormat).parse(dateStr), Calendar.MONTH);
    } catch (ParseException e) {
      logger.error("时间类型转换失败。", e);
      return null;
    }
    return convertToString(firstDay, outputFormat);
  }

  /**
   * 返回当前时间戳对应的 月份字符串
   *
   * @param format 返回月份格式，默认 yyyyMM
   * @return 月份字符串比如，202006
   */
  public static String getCurMonth(String format) {
    if (StringUtils.isEmpty(format)) {
      format = YYYYMM;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(new Date());
  }

  /**
   * 返回指定日期的对应月份字符串
   *
   * @param d      要计算的日期;如果为空，计算当前日期
   * @param format 返回月份格式，默认 yyyyMM
   * @return 月份字符串比如，202006
   */
  public static String getMonthByDate(Date d, String format) {
    if (d == null) {
      return getCurMonth(format);
    }
    if (StringUtils.isEmpty(format)) {
      format = YYYYMM;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(d);
  }

  public static String getCurMonthLast(String format) {
    Calendar cal = Calendar.getInstance();
    Date lastDay = DateUtils.ceiling(new Date(), Calendar.MONTH);
    cal.setTime(lastDay);
    cal.add(Calendar.SECOND, -1);
    lastDay = cal.getTime();
    return convertToString(lastDay, format);
  }

  /**
   * @param @param  format 返回的时间戳格式
   * @param @param  yyyyMM
   * @param @return 参数说明
   * @return String    返回类型
   * @Title: getMonthLast
   * @Description: 获取传输月份的最后一刻时间戳
   * @author niujs
   * @date 2016年12月22日 下午10:35:55
   */
  public static String getMonthLast(String format, String yyyyMM) {

    return getMonthLast("yyyyMM", format, yyyyMM);
  }

  public static String getMonthLast(String inputFormat, String outputFormat, String dateStr) {
    Calendar cal = Calendar.getInstance();
    Date lastDay = null;
    try {
      lastDay = DateUtils.ceiling(new SimpleDateFormat(inputFormat).parse(dateStr), Calendar.MONTH);
    } catch (ParseException e) {
      logger.error("时间类型转换失败。", e);
      return null;
    }
    cal.setTime(lastDay);
    cal.add(Calendar.SECOND, -1);
    lastDay = cal.getTime();
    return convertToString(lastDay, outputFormat);
  }

  /**
   * 格式化日期为指定格式
   *
   * @param date   日期,非空
   * @param format 日期格式，如yyyyMMddHHmmss,非空
   * @return 日期字符串
   */
  public static String convertToString(Date date, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
  }

  /**
   * 格式化日期为指定格式
   *
   * @param date   日期,非空
   * @param format 日期格式，如yyyyMMddHHmmss,非空
   * @return 日期字符串
   */
  public static String convertToString(LocalDate date, String format) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
    return dtf.format(date);
  }

  /**
   * 支持转换字符串为日期，默认支持"yyyyMMdd","yyyy-MM-dd"两种日期转换<br/> 如果obj为空，则返回空
   *
   * @return Date日期
   */
  public static Date toDate(Object obj) {
    return toDate(obj, null);
  }

  /**
   * 根据pattern转换日期
   *
   * @param obj     对象
   * @param pattern 日期格式
   */
  public static Date toDate(Object obj, String pattern) {
    if (obj == null) {
      return null;
    }
    if (Date.class.equals(obj.getClass())) {
      return (Date) obj;
    }
    String str = obj.toString();
    if (StringUtils.isEmpty(str)) {
      return null;
    }
    try {
      if (StringUtils.isNotEmpty(pattern)) {
        return DateUtils.parseDate(str, pattern);
      }
      return DateUtils.parseDate(str, DATE_FORMAT, YYYYMMDD);
    } catch (ParseException e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  public static String getCurrectTime() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date d = new Date();
    return formatter.format(d);
  }

  /**
   * 计算两个日期之间的月份差的绝对值(非负)
   * <p>
   * // 2020-01-01 // 2020-01-02   == 0
   * <p>
   * // 2020-09-01 // 2020-10-02   == 1
   * <p>
   * // 2020-12-01 // 2021-01-02  == 1
   *
   * @param date1
   * @param date2
   * @return
   */
  public static Integer calcMonthDifference(Date date1, Date date2) {
    Calendar start = Calendar.getInstance();
    Calendar end = Calendar.getInstance();
    start.setTime(date1);
    end.setTime(date2);
    int result = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
    int month = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12;
    return Math.abs(month + result);
  }

  /**
   * 计算两个日期之间日期差绝对值
   * @param date1
   * @param date2
   * @return
   */
  public static Long calcDayDifference(Date date1, Date date2){
    long diffInMills = Math.abs(date2.getTime() - date1.getTime());
    long diff = TimeUnit.DAYS.convert(diffInMills, TimeUnit.MILLISECONDS);
    return diff;
  }

  public String getCurrentMonth() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    return sdf.format(new Date());
  }

  public static String getPeriodForDate(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM);
    return sdf.format(date);
  }

  /**
   * @Title: getDateFirst
   * @Description:获取传入天数的第一时刻时间戳
   * @return:
   * @author huangyimiao
   * @date: 2017年3月23日
   */
  public static String getDateFirst(String format, String yyyyMMdd) {
    Date firstDay = null;
    try {
      firstDay =
          DateUtils.truncate(new SimpleDateFormat("yyyyMMdd").parse(yyyyMMdd), Calendar.DATE);
    } catch (ParseException e) {
      logger.error("时间类型转换失败。", e);
      return null;
    }
    return convertToString(firstDay, format);
  }

  public static String getDateEnd(String format, String yyyyMMdd) {
    Calendar cal = Calendar.getInstance();
    Date lastDay = null;
    try {
      lastDay = DateUtils.ceiling(new SimpleDateFormat("yyyyMMdd").parse(yyyyMMdd), Calendar.DATE);
    } catch (ParseException e) {
      logger.error("时间类型转换失败。", e);
      return null;
    }
    cal.setTime(lastDay);
    cal.add(Calendar.SECOND, -1);
    lastDay = cal.getTime();
    return convertToString(lastDay, format);
  }

  /**
   * 比较两个日期,空值为小
   */
  public static int compare(Date d1, Date d2) {
    if (d1 == null) {
      return -1;
    }
    if (d2 == null) {
      return 1;
    }
    return d1.compareTo(d2);
  }

  /**
   * 比较两个日期,返回较小的
   */
  public static Date compareMin(Date d1, Date d2) {
    if (d1.after(d2)) {
      return d2;
    }
    return d1;
  }

  /**
   * 比较两个日期,返回较大的
   */
  public static Date compareMax(Date d1, Date d2) {
    if (d1.after(d2)) {
      return d1;
    }
    return d2;
  }


  public static Date convertToDateViaSqlDate(LocalDate dateToConvert) {
    return java.sql.Date.valueOf(dateToConvert);
  }

  public static Date asDate(LocalDate localDate) {
    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
  }

  public static Date asDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  public static LocalDate asLocalDate(Date date) {
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
  }

  public static LocalDateTime asLocalDateTime(Date date) {
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  /**
   * 根据日期字符串，获取LocalDate
   */
  public static LocalDate getLocalDateFromStr(String dateStr) {
    return LocalDate.parse(dateStr);
  }

  /**
   * 根据Date对象获取LocalDate
   */
  public static LocalDate getLocalDateFromDate(Date date) {
    return asLocalDate(date);
  }

  /**
   * 根据LocalDate对象获取Date
   */
  public static Date getDateFromLocalDate(LocalDate localDate) {
    return asDate(localDate);
  }

  /**
   * 根据LocalDateTime对象获取Date
   */
  public static Date getDateFromLocalDateTime(LocalDateTime dateTime) {
    return asDate(dateTime);
  }

  /**
   * 根据LocalDate对象获取该月份的第一天
   */
  public static LocalDate getFirstDayOfMonth(LocalDate localDate) {
    return localDate.with(TemporalAdjusters.firstDayOfMonth());
  }

  /**
   * 根据字符串对象获取该月份的第一天
   */
  public static LocalDate getFirstDayOfMonth(String dateStr) {
    return LocalDate.parse(dateStr).with(TemporalAdjusters.firstDayOfMonth());
  }

  /**
   * 根据Timestamp对象获取该月份的第一天
   */
  public static LocalDate getFirstDayOfMonth(Timestamp timestamp) {
    return getLocalDateFromTimestamp(timestamp).with(TemporalAdjusters.firstDayOfMonth());
  }

  /**
   * 根据LocalDate对象获取该月份的第一天
   */
  public static LocalDate getFirstDayOfMonth(Date date) {
    return getLocalDateFromDate(date).with(TemporalAdjusters.firstDayOfMonth());
  }

  public static LocalDate getLastDayOfMonth(Date date) {
    return getLocalDateFromDate(date).with(TemporalAdjusters.lastDayOfMonth());
  }

  /**
   * 根据LocalDate对象获取该月份的最后一天
   */
  public static LocalDate getLastDayOfMonth(LocalDate localDate) {
    return localDate.with(TemporalAdjusters.lastDayOfMonth());
  }

  /**
   * 根据timeStamp对象获取LocalDate
   */
  public static LocalDate getLocalDateFromTimestamp(Timestamp timestamp) {
    return timestamp.toLocalDateTime().toLocalDate();
  }

  /**
   * 获取两个Date对象之间的月份间隔
   */
  public static int getPeriodMonthsBetweenDates(LocalDate localDate1, LocalDate localDate2) {
    Period period = Period.between(localDate1, localDate2);
    return period.getMonths();
  }

  /**
   * 获取两个日期之间有多少天
   *
   * @param startDate
   * @param endDate
   * @return
   */
  public static long getDaysBetweenDates(String startDateStr, String endDateStr) {
    LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_DATE);
    LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_DATE);
    return ChronoUnit.DAYS.between(startDate,endDate);
  }

  /**
   *获取两个日期之间有多少天
//   */
//  public static int getPeriodDaysBetweenDates(String startDateStr, String endDateStr) {
//    LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_DATE);
//    LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_DATE);
//    return getPeriodDaysBetweenDates(startDate,endDate);
//  }

  /**
   * 获取业务起始时间
   */
  public static Date getBusiBeginDay() {
    return DateTimeUtils.toDate("1970-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
  }

  /**
   * 获取日期起始时间
   */
  public static Date getStartOfDay(Date date) {
    LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(
        date.getTime()), ZoneId.systemDefault());
    //2020-06-19 17:08:08 add by gaotx 防止出现mysql自动四舍五入的问题
    LocalDateTime startOfDay = localDateTime.with(LocalTime.of(0, 0, 0));
    return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
  }

  /**
   * 获取日期截止时间
   */
  public static Date getEndOfDay(Date date) {
    LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(
        date.getTime()), ZoneId.systemDefault());
    //2020-06-19 17:08:08 add by gaotx 防止出现mysql自动四舍五入的问题
    LocalDateTime endOfDay = localDateTime.with(LocalTime.of(23, 59, 59));
    return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
  }

  /**
   * 判断是否过期
   *
   * @param endDate "yyyy-MM-dd HH:mm:ss"
   * @return
   */
  public static boolean judgeOverdue(String endDate) {
    try {
      long time = DateUtils.parseDate(endDate, DateTimeUtils.defaultFormat)
          .getTime();
      //NOSONAR
      return time <= new Date().getTime();
    } catch (ParseException e) {
      logger.error("时间类型转换失败: ", e);
      return true;
    }
  }

  /**
   * localDate 转化成Date
   *
   * @param localDate
   * @return
   */
  public static Date localDate2Date(LocalDate localDate) {
    if (null == localDate) {
      return null;
    }
    ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
    return Date.from(zonedDateTime.toInstant());
  }

  /**
   * 入参，指定的日期,  返回 制定日期当月最后一毫秒的时间点
   */
  public static Date getLastMonthEndTime(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
    c.set(Calendar.HOUR_OF_DAY, 23);
    c.set(Calendar.MINUTE, 59);
    c.set(Calendar.SECOND, 59);
    c.set(Calendar.MILLISECOND, 999);
    return c.getTime();
  }

  public static Date getLastDayWithTime(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
    c.set(Calendar.HOUR_OF_DAY, 23);
    c.set(Calendar.MINUTE, 59);
    c.set(Calendar.SECOND, 59);
    return c.getTime();
  }

  /**
   * 入参，指定的日期,  返回 制定日期当月最后一毫秒的时间点
   */
  public static String getNextMonthStartTime(String date) {
    final String[] yearMonth = date.split("-");
    return LocalDate
        .of(Integer.parseInt(yearMonth[0]), Integer.parseInt(yearMonth[1]), 1)
        .plusMonths(1)
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }

  public static String addMonth(String date, String split, int month) {
    final String[] splits = date.split(split);
    return LocalDate.of(Integer.parseInt(splits[0]), Integer.parseInt(splits[1]), 1).plusMonths(1)
        .format(DateTimeFormatter.ofPattern(YYYY_MM));
  }

  public static LocalDate localDate(String date, String split) {
    final String[] splits = date.split(split);
    return LocalDate.of(Integer.parseInt(splits[0]), Integer.parseInt(splits[1]), 1);
  }

  public static String format(Date date, String pattern) {
    final SimpleDateFormat format = new SimpleDateFormat(pattern);
    return format.format(date);
  }

  public static List<Date> getMonthPeriods(Date start,Date end){
    Calendar c1 = Calendar.getInstance();
    c1.setTime(start);
    List<Date> periods = new ArrayList<>();
    while (!start.after(end)) {
      periods.add(start);
      c1.add(2, 1);
      start = c1.getTime();
    }
    return periods;
  }

  /**
   * 获取所属期月份的第一天
   */
  public static Date getPeriodMonthStart(String period) {
    SimpleDateFormat format = new SimpleDateFormat(YYYY_MM);
    Date date = null;
    try {
      date = format.parse(period);
    } catch (ParseException e) {
//      log.error(e.getMessage(), e);
    }
    return convertToDateViaSqlDate(DateTimeUtils.getFirstDayOfMonth(date));
  }

  /**
   * 获取所属期月份的最后一天
   */
  public static Date getPeriodMonthEnd(String period) {
    SimpleDateFormat format = new SimpleDateFormat(YYYY_MM);
    Date date = null;
    try {
      date = format.parse(period);
    } catch (ParseException e) {
//      log.error(e.getMessage(), e);
    }
    return convertToDateViaSqlDate(getLastDayOfMonth(date));
  }

  /**
   * 获取两个日期之间的所有月份 (年月)
   *
   * @param startTime
   * @param endTime
   * @return：YYYY-MM
   */
  public static List<String> getMonthBetweenDate(String startTime, String endTime){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    // 声明保存日期集合
    List<String> list = new ArrayList<>();
    try {
      // 转化成日期类型
      Date startDate = sdf.parse(startTime);
      Date endDate = sdf.parse(endTime);

      //用Calendar 进行日期比较判断
      Calendar calendar = Calendar.getInstance();
      while (startDate.getTime()<=endDate.getTime()){
        // 把日期添加到集合
        list.add(sdf.format(startDate));
        // 设置日期
        calendar.setTime(startDate);
        //把日期增加一天
        calendar.add(Calendar.MONTH, 1);
        // 获取增加后的日期
        startDate=calendar.getTime();
      }
    } catch (ParseException e) {
//      log.error("", e);
    }
    return list;
  }
}
