package com.example.demo.dto;


import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.opencsv.bean.CsvBindByPosition;

import lombok.Data;
/**
 * レース情報DTO
 */
@Component
@Data
public class Race {
	/**
	 * 行番号
	 */
	private int rownum;
	/**
	 * レースID
	 */
	private int id;
	/**
	 * 年
	 */
    @CsvBindByPosition(position = 0, required = true)
	private String year;
	/**
	 * 月
	 */
    @CsvBindByPosition(position = 1, required = true)
	private String month;
	/**
	 * 日
	 */
    @CsvBindByPosition(position = 2, required = true)
	private String day;
	/**
	 * 会場
	 */
    @CsvBindByPosition(position = 3, required = true)
	private String place;
	/**
	 * レース番号
	 */
    @CsvBindByPosition(position = 4, required = true)
	private String no;
	/**
	 * クラス
	 */
    @CsvBindByPosition(position = 5, required = true)
	private String grade;
	/**
	 * 出走条件
	 */
    @CsvBindByPosition(position = 6, required = true)
	private String conditions;
	/**
	 * 馬場状態
	 */
    @CsvBindByPosition(position = 7, required = true)
	private String kind;
	/**
	 * 距離
	 */
    @CsvBindByPosition(position = 8, required = true)
	private String distance;
	/**
	 * 頭数
	 */
    @CsvBindByPosition(position = 9, required = true)
	private String horse;
    /**
     * 行の背景色
     */
	private String rowColor;
    /**
     * 1着人気の入力フォーム属性、readonly
     */
	private boolean input1FormStatus;
    /**
     * 2着人気の入力フォーム属性、readonly
     */
	private boolean input2FormStatus;
    /**
     * 3着人気の入力フォーム属性、readonly
     */
	private boolean input3FormStatus;
    /**
     * 登録ボタン、活性/非活性
     */
	private boolean resisterButtonStatus;
    /**
     * 修正ボタン、活性/非活性
     */
	private boolean fixButtonStatus;
    /**
     * フォーム復帰チェックボックス、オン/オフ
     */
	private boolean reactCheckBox;
    /**
     * プルダウン項目入力フォーム属性、readonly
     */
	private boolean inputAcdStatus;
	/**
	 * レースの人気番号の着順―覧
	 */
	private Official offical;
    /**
     * 作成日時
     */
	private Timestamp createdAt;
    /**
     * 更新日時
     */
	private Timestamp updatedAt;
    /**
     * 削除フラグ
     */
	private String isDeleted;
	/**
	 *
	 */
	private String winner;
	/**
	 *
	 */
	private String runnerUp;
	/**
	 *
	 */
	private String thirdPlace;
	/**
	 *
	 */
	private String accidentCode;
	/**
	 *
	 */
	private String accidentValue;

	/**
	 * レース情報の年月日から曜日を返却する
	 * @return String
	 */
	public String getDayOfWeek(String y, String m, String d) {
		Calendar cal = Calendar.getInstance();
		int yyyy = Integer.parseInt(y);
		int mm =  Integer.parseInt(m)-1;
		int dd =  Integer.parseInt(d);
		cal.set(yyyy, mm, dd);

		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		    case Calendar.SUNDAY:     // Calendar.SUNDAY:1
		        return "日";
		    case Calendar.MONDAY:     // Calendar.MONDAY:2
		        return "月";
		    case Calendar.TUESDAY:    // Calendar.TUESDAY:3
		        return "火";
		    case Calendar.WEDNESDAY:  // Calendar.WEDNESDAY:4
		        return "水";
		    case Calendar.THURSDAY:   // Calendar.THURSDAY:5
		        return "木";
		    case Calendar.FRIDAY:     // Calendar.FRIDAY:6
		        return "金";
		    case Calendar.SATURDAY:   // Calendar.SATURDAY:7
		        return "土";
		}
		return "";
	}

	/**
	 *
	 */
	public String getDayZeroPadding(String d) {
		if(d.length() == 1) {
			return "0".concat(d);
		}
		return d;
	}
}
