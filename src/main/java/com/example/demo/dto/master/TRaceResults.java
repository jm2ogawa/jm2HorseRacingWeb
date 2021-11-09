package com.example.demo.dto.master;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * レース結果登録テーブル DTO
 * @author hirofumi.ogawa
 * @param
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TRaceResults implements Serializable {
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 行番号
	 */
	private String rownum;
	/**
	 * レース日時
	 */
	private String raceday;
	/**
	 * 年
	 */
	private String year;
	/**
	 * 月
	 */
	private String month;
	/**
	 * 日
	 */
	private String day;
	/**
	 * レース番号
	 */
	private String racenum;
	/**
	 * 会場
	 */
	private String place;
	/**
	 * 1着人気番号
	 */
	private String winner;
	/**
	 * 2着人気番号
	 */
	private String runnerUp;
	/**
	 * 3着人気番号
	 */
	private String thirdPlace;
	/**
	 * アクシデントコード
	 */
	private String accidentCode;
	/**
	 * アクシデント人気番号
	 */
	private String accidentValue;
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
}
