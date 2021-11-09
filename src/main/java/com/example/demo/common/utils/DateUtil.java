package com.example.demo.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.thymeleaf.util.DateUtils;

import lombok.experimental.UtilityClass;
/**
 * 日付ユーティリティ
 * @author jm2og
 *
 */
@UtilityClass
public class DateUtil {
	/**
	 * カレンダークラスから当日日付を取得する
	 * @param c
	 * @return
	 */
	public Calendar createToday() {
		return DateUtils.createToday();
	}
	/**
	 * 年、月、日を指定してカレンダークラスを返却する
	 * @param c
	 * @return
	 */
	public Calendar createCalenderSetYMD(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, month-1, day);
		return c;
	}
	/**
	 * カレンダークラスから当日日付をYYYYMMDDのフォーマットで取得する
	 * @param c
	 * @return
	 */
	public String createTodayByFormatInYYYYMMDD() {
		Calendar c = DateUtils.createToday();
		return getCalenderBySimpleDateFormat(c, "yyyyMMdd");
	}
	/**
	 * 年、月、日を指定して日付の文字列ををYYYYMMDDのフォーマットで取得する
	 * @param c
	 * @return
	 */
	public String createDayByFormatInYYYYMMDD(int year, int month, int day) {
		Calendar c = createCalenderSetYMD(year,month,day);
		return getCalenderBySimpleDateFormat(c, "yyyyMMdd");
	}
	/**
	 * 指定のフォーマットにして日付を文字列で返却する
	 * @param c
	 * @return
	 */
	private String getCalenderBySimpleDateFormat(Calendar c, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(c.getTime());
	}
	/**
	 * カレンダークラスから日を返却する
	 * @param c
	 * @return int
	 */
	public int getDayOfCalendar(Calendar c) {
		return c.get(Calendar.DATE);
	}
	/**
	 * カレンダークラスから月を返却する
	 * -1で持ってくるため+1している
	 * @param c
	 * @return int
	 */
	public int getMonthOfCalendar(Calendar c) {
		return c.get(Calendar.MONTH)+1;
	}
	/**
	 * 次回レースCSVファイルの読み込み確認
	 */
	public int getNextTargetDay(Calendar c) {
		int day = c.get(Calendar.DATE);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
			case Calendar.SUNDAY:		//日曜日
				break;
			case Calendar.MONDAY:		//月曜日
				day = day+5;
				break;
			case Calendar.TUESDAY:		//火曜日
				day = day+4;
				break;
			case Calendar.WEDNESDAY:	//水曜日
				day = day+3;
				break;
			case Calendar.THURSDAY:	//木曜日
				day = day+2;
				break;
			case Calendar.FRIDAY:		//金曜日
				day = day+1;
				break;
			case Calendar.SATURDAY:	//土曜日
				break;
		}
		return day;
	}
	/**
	 * 年月日の先頭をゼロサプレスする
	 */
	public String trimDateZeroSuppress(String ymd) {
		Pattern p = Pattern.compile("^0+([0-9]+.*)");
		Matcher m = p.matcher(ymd);
		if (m.matches()) {
			return m.group(1);
		}
		return ymd;
	}
}
